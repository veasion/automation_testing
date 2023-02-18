package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.Binding;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Objects;

/**
 * ActionsBinding
 *
 * @author luozhuowei
 * @date 2020/10/16
 */
@SuppressWarnings("unused")
@Api.ClassInfo(desc = "动作")
public class ActionsBinding implements JavaScriptBinding<Actions> {

    private Binding<Actions> binding;

    @Api("单击-左键")
    @ResultProxy
    public ActionsBinding click() {
        binding.getBean().click();
        return this;
    }

    @Api("单击元素-左键")
    @ResultProxy
    public ActionsBinding click(WebElementBinding element) {
        binding.getBean().click(getWebElement(element));
        return this;
    }

    @Api("单击-右键")
    @ResultProxy
    public ActionsBinding contextClick() {
        binding.getBean().contextClick();
        return this;
    }

    @Api("单击元素-右键")
    @ResultProxy
    public ActionsBinding contextClick(WebElementBinding element) {
        binding.getBean().contextClick(getWebElement(element));
        return this;
    }

    @Api("单击并按住")
    @ResultProxy
    public ActionsBinding clickAndHold() {
        binding.getBean().clickAndHold();
        return this;
    }

    @Api("单击并按住")
    @ResultProxy
    public ActionsBinding clickAndHold(WebElementBinding element) {
        binding.getBean().clickAndHold(getWebElement(element));
        return this;
    }

    @Api("双击")
    @ResultProxy
    public ActionsBinding doubleClick() {
        binding.getBean().doubleClick();
        return this;
    }

    @Api("双击")
    @ResultProxy
    public ActionsBinding doubleClick(WebElementBinding element) {
        binding.getBean().doubleClick(getWebElement(element));
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding moveByOffset(int xOffset, int yOffset) {
        binding.getBean().moveByOffset(xOffset, yOffset);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding moveToElement(WebElementBinding element) {
        binding.getBean().moveToElement(getWebElement(element));
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding moveToElement(WebElementBinding element, int xOffset, int yOffset) {
        binding.getBean().moveToElement(getWebElement(element), xOffset, yOffset);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding dragAndDrop(WebElementBinding source, WebElementBinding target) {
        binding.getBean().dragAndDrop(getWebElement(source), getWebElement(target));
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding dragAndDropBy(WebElementBinding element, int xOffset, int yOffset) {
        binding.getBean().dragAndDropBy(getWebElement(element), xOffset, yOffset);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding scrollToElement(WebElementBinding element) {
        binding.getBean().scrollToElement(getWebElement(element));
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding scrollByAmount(int deltaX, int deltaY) {
        binding.getBean().scrollByAmount(deltaX, deltaY);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding pause(int millis) {
        binding.getBean().pause(millis);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding keyDown(String key) {
        binding.getBean().keyDown(key);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding keyDown(WebElementBinding element, String key) {
        binding.getBean().keyDown(getWebElement(element), key);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding keyUp(String key) {
        binding.getBean().keyUp(key);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding keyUp(WebElementBinding element, String key) {
        binding.getBean().keyUp(getWebElement(element), key);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding sendKeys(String key) {
        binding.getBean().sendKeys(key);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding sendKeys(WebElementBinding target, String key) {
        binding.getBean().sendKeys(getWebElement(target), key);
        return this;
    }

    @Api
    @ResultProxy
    public ActionsBinding pause(long millis) {
        binding.getBean().pause(millis);
        return this;
    }

    @Api("释放")
    @ResultProxy(log = false)
    public ActionsBinding release() {
        binding.getBean().release();
        return this;
    }

    @Api("释放")
    @ResultProxy(log = false)
    public ActionsBinding release(WebElementBinding target) {
        binding.getBean().release(getWebElement(target));
        return this;
    }

    @Api("执行")
    @ResultProxy(log = false)
    public ActionsBinding perform() {
        binding.getBean().perform();
        return this;
    }

    private WebElement getWebElement(WebElementBinding element) {
        return Objects.requireNonNull(element).getBinding().getBean();
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<Actions> binding) {
        this.binding = binding;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Binding<Actions> getBinding() {
        return binding;
    }

}
