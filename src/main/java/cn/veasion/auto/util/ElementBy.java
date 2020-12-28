package cn.veasion.auto.util;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ElementBy
 *
 * @author luozhuowei
 * @date 2020/6/16
 */
public class ElementBy extends By {

    private static final Map<String, Function<String, By>> SELECT_MAP;

    static {
        SELECT_MAP = new HashMap<>();
        SELECT_MAP.put("id", By::id);
        SELECT_MAP.put("name", By::name);
        SELECT_MAP.put("xpath", By::xpath);
        SELECT_MAP.put("css", By::cssSelector);
        SELECT_MAP.put("tagName", By::tagName);
        SELECT_MAP.put("linkText", By::linkText);
        SELECT_MAP.put("className", By::className);
    }

    private By by;
    private String target;
    private boolean isParentElement;

    public ElementBy(String target, boolean isParentElement, By by) {
        this.by = by;
        this.target = target;
        this.isParentElement = isParentElement;
    }

    public static By by(String target) {
        target = target.trim();
        if (JavaScriptUtils.isNull(target)) {
            target = ".";
        }
        boolean isParentElement = target.startsWith("@");
        if (isParentElement) {
            target = target.substring(1);
        }
        By by;
        int index;
        if ((index = target.indexOf("=")) == -1 || target.startsWith("/")) {
            by = By.xpath(target);
        } else {
            String byType = target.substring(0, index).trim();
            String select = target.substring(index + 1).trim();
            if (!SELECT_MAP.containsKey(byType)) {
                throw new AutomationException("target格式错误，不支持方式：" + byType);
            }
            by = SELECT_MAP.get(byType).apply(select);
        }
        return new ElementBy(target, isParentElement, by);
    }

    public String getTarget() {
        return target;
    }

    public boolean isParentElement() {
        return isParentElement;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        return by.findElements(searchContext);
    }

}
