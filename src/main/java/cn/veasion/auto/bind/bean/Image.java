package cn.veasion.auto.bind.bean;

import cn.veasion.auto.captcha.ocr.OcrServiceFactory;
import cn.veasion.auto.util.Api;

import java.util.Objects;

/**
 * Image
 *
 * @author luozhuowei
 * @date 2021/1/5
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "image", desc = "图片")
public class Image extends AbstractInitializingBean {

    @Api(value = "根据图片链接OCR识别")
    public Object ocr(String imgUrl) {
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), imgUrl);
    }

    @Api(value = "根据图片链接OCR识别验证码")
    public Object captcha(String imgUrl) {
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), imgUrl);
    }

}
