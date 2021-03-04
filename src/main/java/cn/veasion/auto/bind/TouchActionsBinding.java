package cn.veasion.auto.bind;

import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.util.Objects;

/**
 * TouchActionsBinding
 *
 * @author luozhuowei
 * @date 2020/10/16
 */
@SuppressWarnings("unused")
@Api.ClassInfo(desc = "触摸")
public class TouchActionsBinding implements JavaScriptBinding<TouchActions> {

    private Binding<TouchActions> binding;

    @Api("单击-左键")
    @ResultProxy
    public TouchActionsBinding click() {
        binding.getBean().click();
        return this;
    }

    @Api("单击元素-左键")
    @ResultProxy
    public TouchActionsBinding click(WebElementBinding element) {
        binding.getBean().click(getWebElement(element));
        return this;
    }

    @Api("单击-右键")
    @ResultProxy
    public TouchActionsBinding contextClick() {
        binding.getBean().contextClick();
        return this;
    }

    @Api("单击元素-右键")
    @ResultProxy
    public TouchActionsBinding contextClick(WebElementBinding element) {
        binding.getBean().contextClick(getWebElement(element));
        return this;
    }

    @Api("单击并按住")
    @ResultProxy
    public TouchActionsBinding clickAndHold() {
        binding.getBean().clickAndHold();
        return this;
    }

    @Api("单击并按住")
    @ResultProxy
    public TouchActionsBinding clickAndHold(WebElementBinding element) {
        binding.getBean().clickAndHold(getWebElement(element));
        return this;
    }

    @Api("双击")
    @ResultProxy
    public TouchActionsBinding doubleClick() {
        binding.getBean().doubleClick();
        return this;
    }

    @Api("双击")
    @ResultProxy
    public TouchActionsBinding doubleClick(WebElementBinding element) {
        binding.getBean().doubleClick(getWebElement(element));
        return this;
    }

    @Api("单次触摸点击")
    @ResultProxy
    public TouchActionsBinding singleTap(WebElementBinding element) {
        binding.getBean().singleTap(getWebElement(element));
        return this;
    }

    @Api("两次触摸点击")
    @ResultProxy
    public TouchActionsBinding doubleTap(WebElementBinding element) {
        binding.getBean().doubleTap(getWebElement(element));
        return this;
    }

    @Api("长按")
    @ResultProxy
    public TouchActionsBinding longPress(WebElementBinding element) {
        binding.getBean().longPress(getWebElement(element));
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding flick(int x, int y) {
        binding.getBean().flick(x, y);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding flick(WebElementBinding element, int xOffset, int yOffset, int speed) {
        binding.getBean().flick(getWebElement(element), xOffset, yOffset, speed);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding down(int x, int y) {
        binding.getBean().down(x, y);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding up(int x, int y) {
        binding.getBean().up(x, y);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding move(int x, int y) {
        binding.getBean().move(x, y);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding moveByOffset(int xOffset, int yOffset) {
        binding.getBean().moveByOffset(xOffset, yOffset);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding moveToElement(WebElementBinding element) {
        binding.getBean().moveToElement(getWebElement(element));
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding moveToElement(WebElementBinding element, int xOffset, int yOffset) {
        binding.getBean().moveToElement(getWebElement(element), xOffset, yOffset);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding scroll(int x, int y) {
        binding.getBean().scroll(x, y);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding scroll(WebElementBinding element, int x, int y) {
        binding.getBean().scroll(getWebElement(element), x, y);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding dragAndDrop(WebElementBinding source, WebElementBinding target) {
        binding.getBean().dragAndDrop(getWebElement(source), getWebElement(target));
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding dragAndDrop(WebElementBinding source, int xOffset, int yOffset) {
        binding.getBean().dragAndDropBy(getWebElement(source), xOffset, yOffset);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding sendKeys(String key) {
        binding.getBean().sendKeys(key);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding sendKeys(WebElementBinding target, String key) {
        binding.getBean().sendKeys(getWebElement(target), key);
        return this;
    }

    @Api
    @ResultProxy
    public TouchActionsBinding pause(long millis) {
        binding.getBean().pause(millis);
        return this;
    }

    @Api("释放")
    @ResultProxy(log = false)
    public TouchActionsBinding release() {
        binding.getBean().release();
        return this;
    }

    @Api("释放")
    @ResultProxy(log = false)
    public TouchActionsBinding release(WebElementBinding target) {
        binding.getBean().release(getWebElement(target));
        return this;
    }

    @Api("执行")
    @ResultProxy(log = false)
    public TouchActionsBinding perform() {
        binding.getBean().perform();
        return this;
    }

    private WebElement getWebElement(WebElementBinding element) {
        return Objects.requireNonNull(element).getBinding().getBean();
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<TouchActions> binding) {
        this.binding = binding;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Binding<TouchActions> getBinding() {
        return binding;
    }
}
