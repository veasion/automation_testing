package cn.veasion.auto.util;

import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.internal.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
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

    /**
     * 脚本请求
     */
    @SuppressWarnings("unchecked")
    public static Object request(String url, String uri, String method, Object content, Object headers, Integer timeout) throws IOException {
        LOGGER.info("HTTP {} {}{}", method, url, uri);
        HttpClient client = new OkHttpClient.Factory().builder().connectionTimeout(Duration.ofSeconds(timeout))
                .readTimeout(Duration.ofSeconds(timeout)).createClient(new URL(url));
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
            request.setContent(new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8)));
        }
        HttpResponse response = client.execute(request);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> respHeaders = new HashMap<>();
        result.put("status", response.getStatus());
        result.put("success", response.getStatus() == 200);
        result.put("data", response.getContentString());
        result.put("headers", respHeaders);
        result.put("targetHost", response.getTargetHost());
        response.getHeaderNames().forEach((name) -> {
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
