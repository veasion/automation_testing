package cn.veasion.auto.captcha.ocr;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度OCR
 *
 * <p>https://cloud.baidu.com/doc/OCR/s/vk3h7y58v</p>
 *
 * @author luozhuowei
 * @date 2020/12/31
 */
public class BaiduOcrServiceImpl implements OcrService {

    private static final String APP_ID = "appId";
    private static final String API_KEY = "apiKey";
    private static final String SECRET_KEY = "secretKey";
    private static final long OCR_MAX_IMAGE_SIZE = 4194304L; // 4 * 1024 * 1024
    private static final String TOKEN_API_URL = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}";
    private static final String OCR_API_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/general?access_token=${accessToken}";

    @Override
    public OcrResult ocr(Environment environment, byte[] imgData) {
        return ocr(environment, imgData, null);
    }

    @Override
    public OcrResult ocr(Environment environment, String imgUrl) {
        return ocr(environment, null, imgUrl);
    }

    @Override
    public boolean checkConfig(Environment environment) {
        return getParams(environment) != null;
    }

    @Override
    public String getKey() {
        return "baidu";
    }

    @SuppressWarnings("unchecked")
    private OcrResult ocr(Environment environment, byte[] imgData, String imgUrl) {
        try {
            Map<String, Object> params = checkParams(environment, imgData, imgUrl);
            String token = getToken(params);
            String apiUrl = OCR_API_URL.replace("${accessToken}", token);
            Map<String, Object> apiParams = (Map<String, Object>) params.get("params");
            return ocrRequest(apiUrl, apiParams, imgData, imgUrl);
        } catch (Exception e) {
            throw new AutomationException("调用百度OCR异常", e);
        }
    }

    private OcrResult ocrRequest(String url, Map<String, Object> apiParams, byte[] imgData, String imgUrl) throws IOException {
        Map<String, String> params = new HashMap<>();
        if (apiParams != null && !apiParams.isEmpty()) {
            for (String key : apiParams.keySet()) {
                params.put(key, String.valueOf(apiParams.get(key)));
            }
        }
        if (imgData != null) {
            String base64Content = new String(Base64.getEncoder().encode(imgData));
            if (base64Content.length() > OCR_MAX_IMAGE_SIZE) {
                throw new AutomationException("图片太大");
            }
            params.put("image", base64Content);
        } else {
            params.put("url", imgUrl.replace("https://", "http://"));
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String data = HttpClientUtils.postForm(url, params, headers);
        if (data == null || "".equals(data)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(data);
        JSONArray words = jsonObject.getJSONArray("words_result");
        OcrResult result = new OcrResult();
        List<OcrResult.Words> wordsList = new ArrayList<>();
        if (words != null && !words.isEmpty()) {
            for (int i = 0; i < words.size(); i++) {
                JSONObject wordsJson = words.getJSONObject(i);
                OcrResult.Words ocrWords = new OcrResult.Words();
                ocrWords.setWords(wordsJson.getString("words"));
                JSONObject location = wordsJson.getJSONObject("location");
                if (location != null) {
                    ocrWords.setLocation(location.toJavaObject(OcrResult.Location.class));
                }
                wordsList.add(ocrWords);
            }
        }
        result.setWordsList(wordsList);
        return result;
    }

    private String getToken(Map<String, Object> params) throws IOException {
        String url = TOKEN_API_URL.replace("${clientId}", (String) params.get(API_KEY)).replace("${clientSecret}", (String) params.get(SECRET_KEY));
        byte[] bytes = HttpClientUtils.get(url);
        JSONObject jsonObject = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
        return jsonObject.getString("access_token");
    }

    private Map<String, Object> checkParams(Environment environment, byte[] imgData, String imgUrl) {
        if (imgData == null && imgUrl == null) {
            throw new AutomationException("参数不能为空");
        }
        Map<String, Object> params = getParams(environment);
        if (params == null || params.isEmpty()) {
            throw new AutomationException("百度OCR未配置: " + getKey());
        }
        return params;
    }

    private Map<String, Object> getParams(Environment environment) {
        Map<String, Object> params = getOcrConfig(environment);
        if (params == null
                || StringUtils.isEmpty(params.get(APP_ID))
                || StringUtils.isEmpty(params.get(API_KEY))
                || StringUtils.isEmpty(params.get(SECRET_KEY))) {
            return null;
        }
        return params;
    }

}
