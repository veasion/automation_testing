package cn.veasion.auto.captcha.ocr;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.util.Constants;

import java.util.Map;

/**
 * OcrService
 *
 * @author luozhuowei
 * @date 2020/12/30
 */
public interface OcrService {

    /**
     * 根据图片字节识别
     */
    OcrResult ocr(Environment environment, byte[] imgData);

    /**
     * 根据图片链接识别
     */
    OcrResult ocr(Environment environment, String imgUrl);

    /**
     * 是否配置
     */
    boolean checkConfig(Environment environment);

    /**
     * config key
     */
    String getKey();

    @SuppressWarnings("unchecked")
    default Map<String, Object> getOcrConfig(Environment environment) {
        if (!environment.containsKey(Constants.CONFIG_OCR)) {
            return null;
        }
        String ocrKey = getKey();
        Map<String, Object> ocrMap = (Map<String, Object>) environment.get(Constants.CONFIG_OCR);
        if (ocrMap == null || !ocrMap.containsKey(ocrKey)) {
            return null;
        }
        return (Map<String, Object>) ocrMap.get(ocrKey);
    }

}
