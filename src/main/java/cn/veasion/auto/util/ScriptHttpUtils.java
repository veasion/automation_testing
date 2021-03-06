package cn.veasion.auto.util;

import com.alibaba.fastjson.JSON;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScriptHttpUtils
 *
 * @author luozhuowei
 * @date 2020/12/15
 */
public class ScriptHttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptHttpUtils.class);

    private static final ClientConfig DEFAULT_CLIENT_CONFIG = ClientConfig.defaultConfig();
    private static final HttpClient.Factory DEFAULT_CLIENT_FACTORY = HttpClient.Factory.createDefault();

    /**
     * 脚本请求
     */
    @SuppressWarnings("unchecked")
    public static Object request(String url, String uri, String method, Object content, Object headers, Integer timeout) throws IOException {
        LOGGER.info("HTTP {} {}{}", method, url, uri);
        ClientConfig clientConfig = DEFAULT_CLIENT_CONFIG.baseUrl(new URL(url));
        clientConfig = clientConfig.readTimeout(Duration.ofSeconds(timeout));
        clientConfig = clientConfig.connectionTimeout(Duration.ofSeconds(timeout));
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
        HttpResponse response = client.execute(request);
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

}
