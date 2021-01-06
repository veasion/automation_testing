package cn.veasion.auto.bind.bean;

import cn.veasion.auto.bind.WebElementBinding;
import cn.veasion.auto.captcha.ocr.OcrResult;
import cn.veasion.auto.captcha.ocr.OcrServiceFactory;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.Base64;
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
    public OcrResult ocr(String imgUrl) {
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), imgUrl);
    }

    @Api(value = "根据图片链接OCR识别验证码")
    public OcrResult captcha(String imgUrl) {
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), imgUrl);
    }

    @Api(value = "根据元素OCR识别")
    public OcrResult ocrByElement(WebElementBinding element) throws InterruptedException {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), imgData);
    }

    @Api(value = "根据元素OCR识别验证码")
    public OcrResult captchaByElement(WebElementBinding element) throws InterruptedException {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), imgData);
    }

    private String getImageBase64(WebElementBinding element) throws InterruptedException {
        WebElement bean = element.getBinding().getBean();
        String jsCode;
        if ("canvas".equalsIgnoreCase(bean.getTagName())) {
            jsCode = "return arguments[0].toDataURL('image/png');";
        } else if ("img".equalsIgnoreCase(bean.getTagName())) {
            jsCode = "let img=arguments[0];let c=document.createElement('canvas');c.width=img.width;c.height=img.height;let ctx=c.getContext('2d');ctx.drawImage(img,0,0,img.width,img.height);return c.toDataURL('image/png');";
        } else {
            jsCode = "if(!window.html2canvas){let script=document.createElement('script');script.setAttribute('type','text/javascript');script.setAttribute('src','https://html2canvas.hertzen.com/dist/html2canvas.js');document.body.insertBefore(script,document.body.lastChild);}";
            executeScript(jsCode);
            Thread.sleep(200);
            onWait(getBinding(), driver -> !JavaScriptUtils.isNull(executeScript("return window.html2canvas")), 10);
            jsCode = "return (await html2canvas(arguments[0])).toDataURL('image/png');";
        }
        String base64 = (String) executeScript(jsCode, bean);
        if (base64 == null) {
            return null;
        }
        return base64.replace("data:image/png;base64,", "");
    }

    private Object executeScript(String jsCode, Object... args) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(jsCode, args);
    }

}
