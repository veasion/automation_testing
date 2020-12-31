package cn.veasion.auto.captcha.ocr;

import cn.veasion.auto.core.Environment;

/**
 * CaptchaOcrService
 *
 * @author luozhuowei
 * @date 2020/12/30
 */
public interface CaptchaOcrService {

    OcrResult ocr(Environment environment, byte[] imgData);

    OcrResult ocr(Environment environment, String imgUrl);

}
