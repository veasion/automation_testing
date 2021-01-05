package cn.veasion.auto.captcha.ocr;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.util.ClassSearcher;
import cn.veasion.auto.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OcrServiceFactory
 *
 * @author luozhuowei
 * @date 2021/1/5
 */
public class OcrServiceFactory {

    private static final Map<String, OcrService> OCR_MAP;

    static {
        OCR_MAP = new HashMap<>();
        ClassSearcher search = new ClassSearcher();
        List<Class<?>> list = search.search(OcrService.class.getPackage().getName(), OcrService.class);
        for (Class<?> clazz : list) {
            try {
                OcrService ocrService = (OcrService) clazz.newInstance();
                OCR_MAP.put(ocrService.getKey(), ocrService);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private OcrServiceFactory() {
    }

    @SuppressWarnings("unchecked")
    public static OcrService getDefault(Environment environment) {
        if (!environment.containsKey(Constants.CONFIG_OCR)) {
            return null;
        }
        Map<String, Object> ocrMap = (Map<String, Object>) environment.get(Constants.CONFIG_OCR);
        String key = (String) ocrMap.get(Constants.OCR_DEFAULT);
        if (!ocrMap.containsKey(key)) {
            return null;
        }
        return OCR_MAP.get(key);
    }


    @SuppressWarnings("unchecked")
    public static OcrService getCaptcha(Environment environment) {
        if (!environment.containsKey(Constants.CONFIG_OCR)) {
            return null;
        }
        Map<String, Object> ocrMap = (Map<String, Object>) environment.get(Constants.CONFIG_OCR);
        String key = (String) ocrMap.get(Constants.OCR_CAPTCHA);
        if (!ocrMap.containsKey(key)) {
            return null;
        }
        return OCR_MAP.getOrDefault(key, getDefault(environment));
    }

}
