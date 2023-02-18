package cn.veasion.auto.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * ScriptHttpUtils
 *
 * @author luozhuowei
 * @date 2020/12/15
 */
public class ScriptHttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptHttpUtils.class);

    private static final HttpClient.Factory DEFAULT_CLIENT_FACTORY = HttpClient.Factory.createDefault();

    /**
     * XHR 请求
     */
    public static Object requestByXHR(String url, String uri, String method, Object content, Object headers, Integer timeout) throws IOException {
        LOGGER.info("HTTP {} {}{}", method, url, uri);
        HttpResponse response = request(url, uri, method, content, headers, timeout);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> respHeaders = new HashMap<>();
        result.put("status", response.getStatus());
        result.put("success", response.getStatus() == 200);
        result.put("data", Contents.string(response.getContent(), response.getContentEncoding()));
        result.put("headers", respHeaders);
        result.put("targetHost", response.getTargetHost());
        response.getHeaderNames().forEach(name -> {
            Iterable<String> iterable = response.getHeaders(name);
            if (iterable == null) {
                respHeaders.put(name, null);
                return;
            }
            List<String> values = new ArrayList<>();
            iterable.iterator().forEachRemaining(values::add);
            if (values.isEmpty()) {
                respHeaders.put(name, null);
            } else {
                respHeaders.put(name, values.size() > 1 ? values : values.get(0));
            }
        });
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("response: {}", result.get("data"));
        }
        return result;
    }

    /**
     * 下载文件
     */
    public static void download(String url, String uri, String filePath) throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            download(url, uri, outputStream);
        }
    }

    /**
     * 下载
     */
    public static void download(String url, String uri, OutputStream outputStream) throws IOException {
        LOGGER.info("HTTP {}{}", url, uri);
        HttpResponse response = request(url, uri, HttpMethod.GET.toString(), null, null, null);
        IOUtils.copy(response.getContent().get(), outputStream);
    }

    @SuppressWarnings("unchecked")
    private static HttpResponse request(String url, String uri, String method, Object content, Object headers, Integer timeout) throws IOException {
        ClientConfig clientConfig = ClientConfig.defaultConfig().baseUrl(new URL(url));
        if (timeout != null) {
            clientConfig = clientConfig.readTimeout(Duration.ofSeconds(timeout)).connectionTimeout(Duration.ofSeconds(timeout));
        }
        HttpClient client = DEFAULT_CLIENT_FACTORY.createClient(clientConfig);
        HttpMethod httpMethod = HttpMethod.valueOf(method);
        HttpRequest request = new HttpRequest(httpMethod, uri);
        if (!JavaScriptUtils.isNull(headers)) {
            Map<String, Object> map = (Map<String, Object>) JavaScriptUtils.toJavaObject(headers);
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if (key != null && !JavaScriptUtils.isNull(value)) {
                    request.addHeader(key, String.valueOf(value));
                }
            }
        }
        if (!JavaScriptUtils.isNull(content)) {
            LOGGER.info("request body: {}", content);
            content = JavaScriptUtils.toJavaObject(content);
            if (JavaScriptUtils.isEmpty(request.getHeader("Content-Type"))) {
                request.addHeader("Content-Type", "application/json;charset=UTF-8");
            }
            if (!(content instanceof String)) {
                content = JSON.toJSONString(content);
            }
            request.setContent(Contents.bytes(content.toString().getBytes(StandardCharsets.UTF_8)));
        }
        return client.execute(request);
    }

    public static Pair<String, String> getUrlPair(String url, Function<String, Object> executeScriptFun) {
        String url1;
        String url2;
        if (url.trim().toLowerCase().startsWith("http")) {
            // url
            int idx = url.indexOf("/", url.indexOf("://") + 3);
            if (idx > 0) {
                url1 = url.substring(0, idx);
                url2 = url.substring(idx);
            } else {
                url1 = url;
                url2 = "";
            }
        } else {
            // uri
            url2 = url;
            url1 = (String) executeScriptFun.apply("return location.protocol + '//' + location.hostname");
        }
        return Pair.of(url1, url2);
    }

}
