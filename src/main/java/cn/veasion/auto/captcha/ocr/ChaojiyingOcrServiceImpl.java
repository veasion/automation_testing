package cn.veasion.auto.captcha.ocr;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 超级鹰
 *
 * <p>http://www.chaojiying.com</p>
 *
 * @author luozhuowei
 * @date 2020/12/31
 */
public class ChaojiyingOcrServiceImpl implements CaptchaOcrService {

    private static final String OCR = "ocr_chaojiying";
    private static final String USER = "user";
    private static final String PASS = "pass";
    private static final String SOFTID = "softid";
    private static final String CODETYPE = "codetype";
    private static final String API_URL = "http://upload.chaojiying.net/Upload/Processing.php";

    @Override
    public OcrResult ocr(Environment environment, byte[] imgData) {
        try {
            Map<String, String> headers = new HashMap<>();
            Map<String, String> params = checkParams(environment, imgData);
            params.put("file_base64", new String(Base64.getEncoder().encode(imgData)));
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            String data = HttpClientUtils.postForm(API_URL, params, headers);
            if (data == null || "".equals(data)) {
                return null;
            }
            JSONObject jsonObject = JSON.parseObject(data);
            String picStr = jsonObject.getString("pic_str");
            if (picStr == null) {
                return null;
            }
            OcrResult result = new OcrResult();
            result.setWordsList(Collections.singletonList(new OcrResult.Words(picStr)));
            return result;
        } catch (Exception e) {
            throw new AutomationException("超级鹰验证码异常", e);
        }
    }

    @Override
    public OcrResult ocr(Environment environment, String imgUrl) {
        byte[] bytes;
        try {
            bytes = HttpClientUtils.get(imgUrl);
        } catch (Exception e) {
            throw new AutomationException("图片访问失败: " + imgUrl, e);
        }
        return ocr(environment, bytes);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> checkParams(Environment environment, byte[] imgData) {
        if (imgData == null) {
            throw new AutomationException("参数不能为空");
        }
        if (!environment.containsKey(OCR)) {
            throw new AutomationException("超级鹰OCR未配置: " + OCR);
        }
        Map<String, Object> params = (Map<String, Object>) environment.get(OCR);
        if (params == null
                || StringUtils.isEmpty(params.get(USER))
                || StringUtils.isEmpty(params.get(PASS))
                || StringUtils.isEmpty(params.get(SOFTID))
                || StringUtils.isEmpty(params.get(CODETYPE))) {
            throw new AutomationException("超级鹰OCR参数未配置");
        }
        Map<String, String> apiParams = new HashMap<>();
        params.forEach((k, v) -> apiParams.put(k, String.valueOf(v)));
        return apiParams;
    }

}
