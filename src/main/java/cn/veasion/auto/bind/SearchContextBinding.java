package cn.veasion.auto.bind;

import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.ElementBy;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * SearchContextBinding
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@SuppressWarnings("unused")
public abstract class SearchContextBinding<T extends SearchContext> implements JavaScriptBinding<T> {

    protected Binding<T> binding;

    @ResultProxy(interval = true)
    @Api(value = "查找一个元素", result = WebElement.class)
    public Object findOne(String target) {
        return findElement(target);
    }

    @ResultProxy(interval = true)
    @Api(value = "查找多个元素", result = WebElement.class)
    public List<?> find(String target) {
        List<WebElement> result = binding.getBean().findElements(ElementBy.by(target));
        return result != null ? result : new ArrayList<>();
    }

    @ResultProxy(interval = true)
    @Api(value = "查找多个元素", result = WebElement.class)
    public List<?> findList(String... targets) {
        List<WebElement> result = new ArrayList<>();
        for (String target : targets) {
            List<WebElement> elements = binding.getBean().findElements(ElementBy.by(target));
            if (elements != null && !elements.isEmpty()) {
                result.addAll(elements);
            }
        }
        return result;
    }

    @ResultProxy(interval = true)
    @Api(value = "查找多个可见元素", result = WebElement.class)
    public List<?> findDisplayed(String target) {
        List<WebElement> result = binding.getBean().findElements(ElementBy.by(target));
        if (result != null) {
            return result.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @ResultProxy
    @Api(value = "查找元素包含某文字的节点", result = WebElement.class)
    public Object findText(String tagName, String text, @Api.Param(desc = "是否模糊匹配") boolean fuzzyMatches) {
        return findText(tagName, text, fuzzyMatches, T::findElement);
    }

    @ResultProxy
    @Api(value = "查找元素包含某文字的节点", result = WebElement.class)
    public List<?> findTextAll(String tagName, String text, @Api.Param(desc = "是否模糊匹配") boolean fuzzyMatches) {
        return findText(tagName, text, fuzzyMatches, T::findElements);
    }

    private <R> R findText(String tagName, String text, boolean fuzzyMatches, BiFunction<T, By, R> function) {
        if (JavaScriptUtils.isEmpty(tagName)) {
            tagName = "*";
        }
        text = text.replace("'", "\\'");
        T bean = binding.getBean();
        String xpath = bean instanceof WebElement ? ("descendant::" + tagName) : ("//" + tagName);
        try {
            if (fuzzyMatches) {
                xpath += "[contains(text(), '" + text + "')]";
            } else {
                xpath += "[normalize-space(text())='" + text + "']";
            }
            return function.apply(bean, By.xpath(xpath));
        } catch (Exception e) {
            return null;
        }
    }

    @Api("查找一个元素并点击")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> click(String target) {
        By by = ElementBy.by(target);
        onWait(ExpectedConditions.elementToBeClickable(by));
        WebElement element = findElement(by);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            scrollToCenter(null);
            element.click();
        }
        return this;
    }

    @Api("查找一个元素并点击")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> tryClick(String target) {
        By by = ElementBy.by(target);
        onWait(ExpectedConditions.elementToBeClickable(by));
        WebElement element = findElement(by);
        if (element == null) {
            return this;
        }
        try {
            element.click();
        } catch (Exception e) {
            try {
                executeScriptByParams("arguments[0].click()", element);
            } catch (Exception ex) {
                LOGGER.warn("tryClick", ex);
            }
        }
        return this;
    }

    @Api("获取目标元素属性值")
    @ResultProxy(interval = true)
    public Object attr(String target, String attr) {
        return attr(findElement(ElementBy.by(target)), attr);
    }

    @Api("获取目标元素文本内容")
    @ResultProxy(interval = true)
    public String text(String target) {
        WebElement element = findElement(ElementBy.by(target));
        if (element == null) {
            return null;
        }
        return text(element);
    }

    @ResultProxy(interval = true)
    @Api("向目标元素发送文字/模拟按键")
    public SearchContextBinding<T> type(String target, Object key) {
        return sendKeys(target, key);
    }

    @ResultProxy(interval = true)
    @Api("向目标元素发送文字/模拟按键")
    public SearchContextBinding<T> sendKeys(String target, Object key) {
        By by = ElementBy.by(target);
        onWait(ExpectedConditions.visibilityOfElementLocated(by));
        sendKeys(() -> findElement(by), key);
        return this;
    }

    protected void sendKeys(Supplier<WebElement> supplier, Object key) {
        if (key instanceof Number) {
            key = new BigDecimal(key.toString()).toPlainString();
        }
        String keys = key == null ? "" : key.toString();
        String inputDelay = binding.getEnv().getString("INPUT_DELAY");
        if (inputDelay == null || "".equals(inputDelay) || !inputDelay.matches("\\d+")) {
            supplier.get().sendKeys(keys);
        } else if (new File(keys).exists()) {
            supplier.get().sendKeys(keys);
        } else {
            try {
                int delay = Integer.parseInt(inputDelay);
                for (String s : keys.split("")) {
                    supplier.get().sendKeys(s);
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                throw new AutomationException("发送文字按键异常: " + keys, e);
            }
        }
    }

    @Api("鼠标移动到目标元素")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> mouseOver(String target) {
        By by = ElementBy.by(target);
        onWait(ExpectedConditions.presenceOfElementLocated(by));
        new Actions(binding.getWebDriver()).moveToElement(findElement(by)).perform();
        return this;
    }

    @Api("滚动到目标元素")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> scrollToCenter(@Api.Param(allowNull = true) String target) {
        WebElement element;
        if (JavaScriptUtils.isEmpty(target) && this instanceof WebElementBinding) {
            element = ((WebElementBinding) this).getBinding().getBean();
        } else {
            element = findElement(target);
        }
        String jsCode = "arguments[0].scrollIntoView({behavior:'auto',block:'center',inline:'center'});";
        executeScriptByParams(jsCode, element);
        return this;
    }

    @Api("通过label选择下拉框")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> selectByLabel(String target, Object value) {
        return select(target, "label", value);
    }

    @Api("通过value选择下拉框")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> selectByValue(String target, Object value) {
        return select(target, "value", value);
    }

    @Api("选择下拉框")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> select(String target, String label, Object value) {
        return select(findElement(target), label, value);
    }

    @Api("等待元素显示（可见）")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> waitForElementDisplayed(String target, @Api.Param(allowNone = true) Integer seconds) {
        onWait(driver -> !CollectionUtils.isEmpty(findDisplayed(target)), seconds);
        return this;
    }

    @Api("等待元素隐藏（不可见）")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> waitForElementNotDisplayed(String target, @Api.Param(allowNone = true) Integer seconds) {
        onWait(driver -> CollectionUtils.isEmpty(findDisplayed(target)), seconds);
        return this;
    }

    @Api("等待元素出现")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> waitForElementPresent(String target, @Api.Param(allowNone = true) Integer seconds) {
        onWait(driver -> !CollectionUtils.isEmpty(find(target)), seconds);
        return this;
    }

    @Api("等待元素消失")
    @ResultProxy(interval = true)
    public SearchContextBinding<T> waitForElementNotPresent(String target, @Api.Param(allowNone = true) Integer seconds) {
        onWait(driver -> CollectionUtils.isEmpty(find(target)), seconds);
        return this;
    }

    protected SearchContextBinding<T> select(WebElement element, String label, Object value) {
        Select select = new Select(element);
        if ("label".equalsIgnoreCase(label)) {
            select.selectByVisibleText(String.valueOf(value));
        } else if ("index".equalsIgnoreCase(label)) {
            select.selectByIndex(Integer.parseInt(String.valueOf(value)));
        } else {
            select.selectByValue(String.valueOf(value));
        }
        return this;
    }

    protected Object attr(WebElement element, String attr) {
        String attribute = element.getAttribute(attr);
        if (attribute != null) {
            return attribute;
        }
        return executeScriptByParams(String.format("return arguments[0].%s;", attr), element);
    }

    protected WebElement findElement(String target) {
        return findElement(ElementBy.by(target));
    }

    protected WebElement findElement(By by) {
        WebElement element;
        try {
            element = binding.getBean().findElement(by);
        } catch (Exception e) {
            element = null;
        }
        if (element != null && !element.isDisplayed()) {
            List<WebElement> elements = binding.getBean().findElements(by);
            if (elements != null && !elements.isEmpty()) {
                elements = elements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
            }
            if (elements != null && !elements.isEmpty()) {
                return elements.get(0);
            }
        }
        return element;
    }

    protected String text(WebElement element) {
        String text = element.getText();
        if (text == null || "".equals(text.trim())) {
            try {
                text = executeScriptByParams("return (arguments[0].textContent || arguments[0].innerText);", element).toString();
            } catch (Exception e) {
                text = null;
            }
        }
        return text != null ? text.trim() : null;
    }

    protected Object executeScriptByParams(String jsCode, Object... params) {
        return ((JavascriptExecutor) binding.getWebDriver()).executeScript(jsCode, params);
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<T> binding) {
        this.binding = binding;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Binding<T> getBinding() {
        return binding;
    }

}
