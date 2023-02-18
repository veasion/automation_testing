package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.AbstractInitializingBean;
import cn.veasion.auto.captcha.image.ImageUtil;
import cn.veasion.auto.util.ImageWrapper;
import cn.veasion.auto.captcha.ocr.OcrResult;
import cn.veasion.auto.captcha.ocr.OcrServiceFactory;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.HttpClientUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

/**
 * ImageBean
 *
 * @author luozhuowei
 * @date 2021/1/5
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "image", desc = "图片")
public class ImageBean extends AbstractInitializingBean {

    @Api("加载图片")
    public ImageWrapper load(String path) throws IOException {
        return ImageWrapper.ofImagePath(path);
    }

    @Api("加载网络图片")
    public ImageWrapper loadByUrl(String url) throws IOException {
        return ImageWrapper.ofImage(ImageIO.read(new ByteArrayInputStream(HttpClientUtils.get(url))));
    }

    @Api("浏览器截图")
    public ImageWrapper loadByScreenshot() throws IOException {
        byte[] bytes = ((TakesScreenshot) getBinding().getWebDriver()).getScreenshotAs(OutputType.BYTES);
        return ImageWrapper.ofImage(ImageIO.read(new ByteArrayInputStream(bytes)));
    }

    @Api("元素渲染成图片")
    public ImageWrapper loadByElement(WebElementBinding element) throws IOException {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return ImageWrapper.ofImage(ImageIO.read(new ByteArrayInputStream(imgData)));
    }

    @Api("根据图片链接OCR识别")
    public OcrResult ocrByUrl(String imgUrl) {
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), imgUrl);
    }

    @Api("根据图片链接OCR识别验证码")
    public OcrResult captchaByUrl(String imgUrl) {
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), imgUrl);
    }

    @Api("根据元素OCR识别")
    public OcrResult ocrByElement(WebElementBinding element) {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), imgData);
    }

    @Api("根据图片OCR识别")
    public OcrResult ocrByImage(ImageWrapper imageWrapper) throws IOException {
        BufferedImage image = imageWrapper.getImage();
        byte[] bytes = ImageUtil.imageToBytes(image, "png");
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), bytes);
    }

    @Api("根据元素OCR识别验证码")
    public OcrResult captchaByElement(WebElementBinding element) {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), imgData);
    }

    @Api("根据图片OCR识别验证码")
    public OcrResult captchaByImage(ImageWrapper imageWrapper) throws IOException {
        BufferedImage image = imageWrapper.getImage();
        byte[] bytes = ImageUtil.imageToBytes(image, "png");
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), bytes);
    }

    public static String getImageBase64(WebElementBinding element) {
        WebElement bean = element.getBinding().getBean();
        WebDriver webDriver = element.getBinding().getWebDriver();
        String base64;
        if ("canvas".equalsIgnoreCase(bean.getTagName())) {
            // 画布
            String jsCode = "return arguments[0].toDataURL('image/png');";
            base64 = (String) executeScript(webDriver, jsCode, bean);
        } else if ("img".equalsIgnoreCase(bean.getTagName())) {
            // 图片
            String jsCode = "let img=arguments[0];let c=document.createElement('canvas');c.width=img.width;c.height=img.height;let ctx=c.getContext('2d');ctx.drawImage(img,0,0,img.width,img.height);return c.toDataURL('image/png');";
            base64 = (String) executeScript(webDriver, jsCode, bean);
        } else {
            // 元素
            element.scrollToCenter(null);
            byte[] bytes = element.getBinding().getBean().getScreenshotAs(OutputType.BYTES);
            base64 = Base64.getEncoder().encodeToString(bytes);
        }
        if (base64 == null) {
            return null;
        }
        return base64.replace("data:image/png;base64,", "");
    }

    private static Object executeScript(WebDriver webDriver, String jsCode, Object... args) {
        return ((JavascriptExecutor) webDriver).executeScript(jsCode, args);
    }

}
