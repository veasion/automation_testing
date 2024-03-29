package cn.veasion.auto.bind;

import cn.veasion.auto.util.ImageWrapper;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;

import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.Constants;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * WebElementBinding
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@SuppressWarnings("unused")
@Api.ClassInfo(desc = "元素")
public class WebElementBinding extends SearchContextBinding<WebElement> {

    @ResultProxy
    @Api("点击")
    public WebElementBinding click() {
        try {
            binding.getBean().click();
        } catch (ElementClickInterceptedException e) {
            scrollToCenter(null);
            binding.getBean().click();
        }
        return this;
    }

    @ResultProxy(interval = true)
    @Api("点击这个元素区域")
    public WebElementBinding tryClick() {
        WebElement element = binding.getBean();
        try {
            element.click();
        } catch (Exception e) {
            try {
                executeScript("arguments[0].click()");
            } catch (Exception ex) {
                LOGGER.warn("tryClick", ex);
            }
        }
        return this;
    }

    @Api("获取属性值")
    @ResultProxy(log = false)
    public Object attr(String attr) {
        return attr(binding.getBean(), attr);
    }

    @Api("发送文字/模拟按键")
    @ResultProxy(interval = true)
    public WebElementBinding type(Object key) {
        return sendKeys(key);
    }

    @Api("发送文字/模拟按键")
    @ResultProxy(interval = true)
    public WebElementBinding sendKeys(Object key) {
        sendKeys(binding::getBean, key);
        return this;
    }

    @Api("获取文本内容")
    @ResultProxy(log = false)
    public String text() {
        return text(binding.getBean(), false);
    }

    @Api("获取可见文本内容")
    @ResultProxy(log = false)
    public String displayedText() {
        return text(binding.getBean(), true);
    }

    @Api("innerHTML")
    @ResultProxy(log = false)
    public String innerHtml() {
        Object value = executeScript("return (arguments[0].innerHTML);");
        return value != null ? value.toString() : null;
    }

    @Api("outerHTML")
    @ResultProxy(log = false)
    public String outerHtml() {
        Object value = executeScript("return (arguments[0].outerHTML);");
        return value != null ? value.toString() : null;
    }

    @Api("获取值")
    @ResultProxy(log = false)
    public String value() {
        Object value = executeScript("return (arguments[0].value);");
        return value != null ? value.toString() : null;
    }

    @Api("获取位置区域 x/y/width/height")
    @ResultProxy(value = false, log = false)
    public Object getRect() {
        return binding.getBean().getRect();
    }

    @Api("获取css值")
    @ResultProxy(log = false)
    public String cssValue(String propertyName) {
        return binding.getBean().getCssValue(propertyName);
    }

    @ResultProxy
    @Api("清空值")
    public WebElementBinding clear() {
        try {
            executeScript("return (arguments[0].value = '');");
            return this;
        } catch (Exception e) {
            return this;
        }
    }

    @Api("设值")
    @ResultProxy(interval = true)
    public WebElementBinding setValue(String text) {
        clear();
        binding.getBean().sendKeys(text);
        return this;
    }

    @Api("通过label选择下拉框")
    @ResultProxy(interval = true)
    public WebElementBinding selectByLabel(Object value) {
        return select("label", value);
    }

    @Api("通过value选择下拉框")
    @ResultProxy(interval = true)
    public WebElementBinding selectByValue(Object value) {
        return select("value", value);
    }

    @Api("选择下拉框")
    @ResultProxy(interval = true)
    public WebElementBinding select(String label, Object value) {
        return (WebElementBinding) select(binding.getBean(), label, value);
    }

    @ResultProxy
    @Api(value = "父元素", result = WebElement.class)
    public Object parent() {
        return binding.getBean().findElement(By.xpath(".."));
    }

    @ResultProxy
    @Api(value = "父元素", result = WebElement.class)
    public Object parentByTag(String tagName) {
        WebElement element = binding.getBean();
        do {
            element = element.findElement(By.xpath(".."));
            if (element != null && element.getTagName().equalsIgnoreCase(tagName)) {
                break;
            }
        } while (element != null);
        return element;
    }

    @ResultProxy
    @Api(value = "子元素集合", result = WebElement.class)
    public List<?> childList() {
        return binding.getBean().findElements(By.xpath("child::*"));
    }

    @ResultProxy
    @Api(value = "左边兄弟节点集合", result = WebElement.class)
    public List<?> leftSibling() {
        return binding.getBean().findElements(By.xpath("preceding-sibling::*"));
    }

    @ResultProxy
    @Api(value = "右边兄弟节点集合", result = WebElement.class)
    public List<?> rightSibling() {
        return binding.getBean().findElements(By.xpath("following-sibling::*"));
    }

    @ResultProxy
    @Api(value = "查找元素包含某文字的节点", result = WebElement.class)
    public Object findText(String tagName, String text) {
        return super.findText(tagName, text, false);
    }

    @ResultProxy
    @Api("tagName")
    public String tagName() {
        return binding.getBean().getTagName();
    }

    @Api("是否可见")
    @ResultProxy(value = false, log = false)
    public boolean isDisplayed() {
        return binding.getBean().isDisplayed();
    }

    @Api("xpath")
    @ResultProxy(log = false, value = false)
    public String xpath() {
        final String jsCode = "return (function(element){if(element.id!==''){return'//*[@id=\\\"'+element.id+'\\\"]'}if(element==document.body){return'/html/'+element.tagName.toLowerCase()}var ix=1,siblings=element.parentNode.childNodes;for(var i=0,l=siblings.length;i<l;i++){var sibling=siblings[i];if(sibling==element){return arguments.callee(element.parentNode)+'/'+element.tagName.toLowerCase()+'['+(ix)+']'}else if(sibling.nodeType==1&&sibling.tagName==element.tagName){ix++}}})(arguments[0]);";
        return (String) executeScriptByParams(jsCode, binding.getBean());
    }

    @Api("保存成图片")
    @ResultProxy(log = false, value = false)
    public void saveAsImage(@Api.Param(allowNull = true) String path) throws IOException {
        if (JavaScriptUtils.isNull(path)) {
            path = String.format("%s\\%s_%d.png", binding.getEnv().get(Constants.DESKTOP_DIR), tagName(), System.currentTimeMillis());
        }
        String imageBase64 = ImageBean.getImageBase64(this);
        if (imageBase64 == null) {
            throw new AutomationException(String.format("%s 元素转换图片失败", tagName()));
        }
        byte[] imgData = Base64.getDecoder().decode(imageBase64);
        Files.write(Paths.get(path), imgData, StandardOpenOption.CREATE);
    }

    @Api
    @ResultProxy(log = false)
    public void show() throws IOException {
        String base64 = Objects.requireNonNull(ImageBean.getImageBase64(this));
        ImageWrapper.ofImage(ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64)))).show();
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public String toString() {
        return toString(binding.getBean());
    }

    private Object executeScript(String jsCode) {
        return executeScriptByParams(jsCode, binding.getBean());
    }

    private String toString(WebElement bean) {
        String tagName = bean.getTagName();
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tagName);
        for (String attr : Arrays.asList("id", "name", "title", "value", "class")) {
            String v = bean.getAttribute(attr);
            if (!StringUtils.isEmpty(v)) {
                sb.append(" ").append(attr);
                sb.append("=\"").append(v).append("\"");
            }
        }
        if ("span".equals(tagName) || "label".equals(tagName) || "button".equals(tagName)) {
            sb.append(">").append(text()).append("</").append(tagName).append(">");
        } else {
            sb.append(" />");
        }
        return sb.toString();
    }

}
