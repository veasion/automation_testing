package cn.veasion.auto.bind.bean;

import cn.veasion.auto.bind.WebElementBinding;
import cn.veasion.auto.captcha.image.ImageUtil;
import cn.veasion.auto.captcha.ocr.OcrResult;
import cn.veasion.auto.captcha.ocr.OcrServiceFactory;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
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
    public OcrResult ocrByElement(WebElementBinding element) throws InterruptedException, IOException {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return Objects.requireNonNull(OcrServiceFactory.getDefault(getEnv())).ocr(getEnv(), imgData);
    }

    @Api(value = "根据元素OCR识别验证码")
    public OcrResult captchaByElement(WebElementBinding element) throws InterruptedException, IOException {
        String imageBase64 = getImageBase64(element);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", element.tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        return Objects.requireNonNull(OcrServiceFactory.getCaptcha(getEnv())).ocr(getEnv(), imgData);
    }

    @SuppressWarnings("unchecked")
    public static String getImageBase64(WebElementBinding element) throws InterruptedException, IOException {
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
            String jsCode = "return window.innerWidth > arguments[0].getBoundingClientRect().width && window.innerHeight > arguments[0].getBoundingClientRect().height";
            if ("true".equalsIgnoreCase(String.valueOf(executeScript(webDriver, jsCode, bean)))) {
                // 元素小于屏幕宽度高度
                element.scrollToCenter(null);
                jsCode = "let rect=arguments[0].getBoundingClientRect();return {left:rect.left,top:rect.top,width:rect.width,height:rect.height,innerWidth:window.innerWidth,innerHeight:window.innerHeight}";
                Map<String, Number> rect = (Map<String, Number>) executeScript(webDriver, jsCode, bean);
                File img = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                byte[] bytes = ImageUtil.subImage(img, rect.get("left").intValue(), rect.get("top").intValue(), rect.get("width").intValue(), rect.get("height").intValue());
                base64 = Base64.getEncoder().encodeToString(bytes);
            } else {
                // 元素超过屏幕宽度高度
                jsCode = "if(!window.html2canvas){let script=document.createElement('script');script.setAttribute('type','text/javascript');script.setAttribute('src','https://html2canvas.hertzen.com/dist/html2canvas.js');document.body.insertBefore(script,document.body.lastChild);}";
                executeScript(webDriver, jsCode);
                Thread.sleep(200);
                element.onWait(driver -> !JavaScriptUtils.isNull(executeScript(webDriver, "return window.html2canvas")), 10);
                jsCode = "return (await html2canvas(arguments[0])).toDataURL('image/png');";
                base64 = (String) executeScript(webDriver, jsCode, bean);
            }
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
