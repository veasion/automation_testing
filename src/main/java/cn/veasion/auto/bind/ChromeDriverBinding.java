package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.Binding;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.JavaScriptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.idealized.Network;
import org.openqa.selenium.devtools.idealized.OpaqueKey;
import org.openqa.selenium.devtools.idealized.target.model.SessionID;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ChromeDriverBinding
 *
 * @author luozhuowei
 * @date 2021/3/2
 */
@SuppressWarnings({"unused", "restriction"})
@Api.ClassInfo(desc = "chromeDriver")
public class ChromeDriverBinding extends WebDriverBinding {

    private ChromeDriver chromeDriver;
    private OpaqueKey requestHandlerKey;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final Map<Predicate<HttpRequest>, Function<HttpRequest, HttpResponse>> uriHandlers = new LinkedHashMap<>();

    @Api("执行cdp命令")
    @ResultProxy(value = false)
    @SuppressWarnings("unchecked")
    public Object executeCdpCommand(String commandName, Object parameters) {
        Objects.requireNonNull(parameters);
        Map<String, Object> object = (Map<String, Object>) JavaScriptUtils.toJavaObject(parameters);
        return chromeDriver.executeCdpCommand(commandName, object);
    }

    @Api
    @ResultProxy(value = false)
    public void setUserAgent(String userAgent) {
        executeCdpCommand("Network.setUserAgentOverride", ImmutableMap.of("userAgent", userAgent));
    }

    @Api
    @ResultProxy(value = false)
    public synchronized void activateDevTools() {
        DevTools devTools = chromeDriver.getDevTools();
        SessionID cdpSession = devTools.getCdpSession();
        if (cdpSession != null) {
            try {
                devTools.clearListeners();
                devTools.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        devTools.createSession();
        Network<?, ?> network = devTools.getDomains().network();
        if (requestHandlerKey != null) {
            network.removeRequestHandler(requestHandlerKey);
        }
        requestHandlerKey = network.addRequestHandler(request -> true, request -> {
            List<HttpResponse> list = uriHandlers.entrySet().stream()
                    .filter(entry -> {
                        try {
                            return entry.getKey().test(request);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    })
                    .map(Map.Entry::getValue).map(func -> {
                        try {
                            return func.apply(request);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull).collect(Collectors.toList());
            HttpResponse response;
            if (!list.isEmpty()) {
                response = list.get(0);
            } else {
                response = new HttpResponse();
                response.setHeader("Selenium-Interceptor", "Continue");
            }
            return response;
        });
    }

    @Api
    @ResultProxy(value = false)
    public List<Object> getAllResponse() {
        Logs logs = chromeDriver.manage().logs();
        Set<String> availableLogTypes = logs.getAvailableLogTypes();
        if (availableLogTypes.contains(LogType.PERFORMANCE)) {
            LogEntries logEntries = logs.get(LogType.PERFORMANCE);
            List<Object> list = new ArrayList<>();
            for (LogEntry entry : logEntries) {
                JSONObject message = JSON.parseObject(entry.getMessage()).getJSONObject("message");
                if ("Network.responseReceived".equalsIgnoreCase(message.getString("method"))) {
                    JSONObject params = message.getJSONObject("params");
                    // String type = params.getString("type"); // Document Image Script XHR  Font Stylesheet Other
                    String requestId = params.getString("requestId");
                    JSONObject response = params.getJSONObject("response");
                    try {
                        response.put("responseBody", getResponseBody(requestId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(response);
                }
            }
            return list;
        }
//        Event<Map<String, Object>> responseReceivedEvent = new Event<>("Network.responseReceived", input -> input.read((new TypeToken<Map<String, Object>>() {
//        }).getType()));
//        chromeDriver.getDevTools().addListener(responseReceivedEvent, responseReceived -> {
//            System.out.println("responseReceived => " + responseReceived);
//        });
        return null;
    }

    @SuppressWarnings("serial")
    private Map<String, Object> getResponseBody(String requestId) {
        return chromeDriver.getDevTools().send(new Command<>(
                "Network.getResponseBody",
                ImmutableMap.of("requestId", requestId),
                input -> input.read((new TypeToken<Map<String, Object>>() {
                }).getType())
        ));
    }

    @Api
    @ResultProxy(value = false)
    public void addRequestHandler(@Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror filter, @Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        if (filter == null || !filter.isFunction()) {
            throw new AutomationException("filter is not function");
        }
        uriHandlers.put(request -> {
            Object call = filter.call(null, request);
            return call != null && "true".equalsIgnoreCase(call.toString());
        }, requestHandler(fun));
    }

    @Api
    @ResultProxy(value = false)
    public void addRequestHandlerByUrlContains(String str, @Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        uriHandlers.put(request -> request.getUri().contains(Objects.requireNonNull(str)), requestHandler(fun));
    }

    @Api
    @ResultProxy(value = false)
    public void addRequestHandlerByUrlEquals(String url, @Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        uriHandlers.put(request -> request.getUri().equals(Objects.requireNonNull(url)), requestHandler(fun));
    }

    @Api
    @ResultProxy(value = false)
    public void addRequestHandlerByUrlPattern(String urlPattern, @Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        uriHandlers.put(request -> pathMatcher.match(urlPattern, request.getUri()), requestHandler(fun));
    }

    @SuppressWarnings("unchecked")
    private Function<HttpRequest, HttpResponse> requestHandler(ScriptObjectMirror fun) {
        if (fun == null || !fun.isFunction()) {
            throw new AutomationException("params is not function");
        }
        return request -> {
            Object object = fun.call(null, request);
            if (!JavaScriptUtils.isNull(object)) {
                Map<String, Object> result = (Map<String, Object>) JavaScriptUtils.toJavaObject(object);
                Map<String, Object> headers = (Map<String, Object>) result.get("headers");
                Object status = result.get("status");
                Object content = result.get("content");
                HttpResponse response = new HttpResponse();
                if (status != null) {
                    response.setStatus(Integer.parseInt(status.toString()));
                }
                if (headers != null) {
                    headers.forEach((k, v) -> response.addHeader(k, v != null ? v.toString() : null));
                }
                if (content != null) {
                    response.setContent(Contents.bytes(content.toString().getBytes(response.getContentEncoding())));
                }
                return response;
            }
            return null;
        };
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<WebDriver> binding) {
        super.setBinding(binding);
        chromeDriver = (ChromeDriver) binding.getWebDriver();
    }

}
