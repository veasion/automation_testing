package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.AbstractInitializingBean;
import cn.veasion.auto.captcha.image.ImageUtil;
import cn.veasion.auto.captcha.ocr.OcrResult;
import cn.veasion.auto.captcha.ocr.OcrServiceFactory;
import cn.veasion.auto.opencv.ColorFinder;
import cn.veasion.auto.opencv.ColorUtils;
import cn.veasion.auto.opencv.ImageFinder;
import cn.veasion.auto.opencv.ImageWrapper;
import cn.veasion.auto.opencv.PointWrapper;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.HttpClientUtils;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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

    @Api("截图")
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
    public OcrResult captchaByElement(WebElementBinding element){
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

    @Api("查找图片")
    public PointWrapper findImage(@Api.Param(desc = "主图片") ImageWrapper image,
                                  @Api.Param(desc = "模板图片（被查找图片）") ImageWrapper template) {
        return findImage(image, template, 0.9f);
    }

    @Api("查找图片")
    public PointWrapper findImage(@Api.Param(desc = "主图片") ImageWrapper image,
                                  @Api.Param(desc = "模板图片（被查找图片）") ImageWrapper template,
                                  @Api.Param(desc = "相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果") float threshold) {
        return findImage(image, template, threshold, null);
    }

    @Api("查找图片")
    public PointWrapper findImage(@Api.Param(desc = "主图片") ImageWrapper image,
                                  @Api.Param(desc = "模板图片（被查找图片）") ImageWrapper template,
                                  @Api.Param(desc = "相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果") float threshold,
                                  @Api.Param(desc = "找图区域 [x, y, width, height]", allowNone = true) List<Integer> region) {
        return PointWrapper.ofPoint(ImageFinder.findImage(image, template, threshold, toRect(region)));
    }

    @Api("查找颜色")
    public PointWrapper findColor(@Api.Param(desc = "图片") ImageWrapper image,
                                  @Api.Param(desc = "颜色代码 eg: #FFFFFF") String color,
                                  @Api.Param(desc = "相识度 0-255，越小越匹配") int threshold) {
        return findColor(image, color, threshold, null);
    }

    @Api("查找颜色")
    public PointWrapper findColor(@Api.Param(desc = "图片") ImageWrapper image,
                                  @Api.Param(desc = "颜色代码 eg: #FFFFFF") String color,
                                  @Api.Param(desc = "相识度 0-255，越小越匹配") int threshold,
                                  @Api.Param(desc = "查找区域 [x, y, width, height]", allowNone = true) List<Integer> region) {
        return PointWrapper.ofPoint(ColorFinder.findColor(image, ColorUtils.parseColor(color), threshold, toRect(region)));
    }

    @Api("匹配多个颜色")
    public PointWrapper findMultiColors(@Api.Param(desc = "图片") ImageWrapper image,
                                        @Api.Param(desc = "颜色代码 eg: #FFFFFF") String firstColor,
                                        @Api.Param(desc = "相识度 0-255，越小越匹配") int threshold,
                                        @Api.Param(desc = "相对于第一个点的位置和颜色的数组, 如: [[x, y, color], [0, 3, '#FFFFFF'], [1, 6, '#000000']]") List<List<Object>> colorPoints) {
        return findMultiColors(image, firstColor, threshold, colorPoints, null);
    }

    @Api("匹配多个颜色")
    public PointWrapper findMultiColors(@Api.Param(desc = "图片") ImageWrapper image,
                                        @Api.Param(desc = "颜色代码 eg: #FFFFFF") String firstColor,
                                        @Api.Param(desc = "相识度 0-255，越小越匹配") int threshold,
                                        @Api.Param(desc = "相对于第一个点的位置和颜色的数组, 如: [[x, y, color], [0, 3, '#FFFFFF'], [1, 6, '#000000']]") List<List<Object>> colorPoints,
                                        @Api.Param(desc = "查找区域 [x, y, width, height]", allowNone = true) List<Integer> region) {
        int[] colors = null;
        if (colorPoints != null && !colorPoints.isEmpty()) {
            colors = new int[colorPoints.size() * 3];
            for (int i = 0; i < colorPoints.size(); i++) {
                List<Object> colorPoint = colorPoints.get(i);
                colors[i * 3] = Integer.parseInt(colorPoint.get(0).toString());
                colors[i * 3 + 1] = Integer.parseInt(colorPoint.get(1).toString());
                colors[i * 3 + 2] = ColorUtils.parseColor(colorPoint.get(2).toString());
            }
        }
        return PointWrapper.ofPoint(ColorFinder.findMultiColors(image, ColorUtils.parseColor(firstColor), threshold, toRect(region), colors));
    }

    @Api("查找颜色")
    public List<PointWrapper> findAllColor(@Api.Param(desc = "图片") ImageWrapper image,
                                           @Api.Param(desc = "颜色代码 eg: #FFFFFF") String color,
                                           @Api.Param(desc = "相识度 0-255，越小越匹配") int threshold) {
        return findAllColor(image, color, threshold, null);
    }

    @Api("查找颜色")
    public List<PointWrapper> findAllColor(@Api.Param(desc = "图片") ImageWrapper image,
                                           @Api.Param(desc = "颜色代码 eg: #FFFFFF") String color,
                                           @Api.Param(desc = "相识度 0-255，越小越匹配") int threshold,
                                           @Api.Param(desc = "查找区域 [x, y, width, height]", allowNone = true) List<Integer> region) {
        Point[] points = ColorFinder.findAllColor(image, ColorUtils.parseColor(color), threshold, toRect(region));
        if (points == null) {
            return null;
        }
        List<PointWrapper> list = new ArrayList<>(points.length);
        for (Point point : points) {
            list.add(PointWrapper.ofPoint(point));
        }
        return list;
    }

    private Rect toRect(List<Integer> region) {
        if (region == null) {
            return null;
        }
        if (region.size() >= 4) {
            return new Rect(region.get(0), region.get(1), region.get(2), region.get(3));
        } else if (region.size() >= 2) {
            return new Rect(region.get(0), region.get(1), 0, 0);
        } else {
            return new Rect();
        }
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
