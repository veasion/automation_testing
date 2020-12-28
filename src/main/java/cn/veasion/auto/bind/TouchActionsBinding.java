package cn.veasion.auto.bind;

import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

/**
 * TouchActionsBinding
 *
 * @author luozhuowei
 * @date 2020/10/16
 */
@SuppressWarnings("unused")
@Api.ClassInfo(desc = "触摸")
public class TouchActionsBinding implements JavaScriptBinding<TouchActions> {

    private WebElement element;
    private Binding<TouchActions> binding;

    public TouchActionsBinding() {
    }

    public TouchActionsBinding(WebElement element) {
        this.element = element;
    }

    @Api("单击")
    @ResultProxy
    public TouchActionsBinding click() {
        binding.getBean().singleTap(getElement()).perform();
        return this;
    }

    @Api("双击")
    @ResultProxy
    public TouchActionsBinding doubleClick() {
        binding.getBean().doubleTap(getElement()).perform();
        return this;
    }

    @Api("单次触摸点击")
    @ResultProxy
    public TouchActionsBinding singleTap() {
        binding.getBean().singleTap(getElement());
        return this;
    }

    @Api("两次触摸点击")
    @ResultProxy
    public TouchActionsBinding doubleTap() {
        binding.getBean().doubleTap(getElement());
        return this;
    }

    @Api("长按")
    @ResultProxy
    public TouchActionsBinding longPress() {
        binding.getBean().longPress(getElement());
        return this;
    }

    @Api("flick")
    @ResultProxy
    public TouchActionsBinding flick(int x, int y) {
        binding.getBean().flick(x, y);
        return this;
    }

    @Api("flick")
    @ResultProxy
    public TouchActionsBinding flickByElement(int xOffset, int yOffset, int speed) {
        binding.getBean().flick(getElement(), xOffset, yOffset, speed);
        return this;
    }

    @Api("down")
    @ResultProxy
    public TouchActionsBinding down(int x, int y) {
        binding.getBean().down(x, y);
        return this;
    }

    @Api("up")
    @ResultProxy
    public TouchActionsBinding up(int x, int y) {
        binding.getBean().up(x, y);
        return this;
    }

    @Api("move")
    @ResultProxy
    public TouchActionsBinding move(int x, int y) {
        binding.getBean().move(x, y);
        return this;
    }

    @Api("scroll")
    @ResultProxy
    public TouchActionsBinding scroll(int x, int y) {
        binding.getBean().scroll(x, y);
        return this;
    }

    @Api("scroll")
    @ResultProxy
    public TouchActionsBinding scrollByElement(int x, int y) {
        binding.getBean().scroll(getElement(), x, y);
        return this;
    }

    @Api("执行")
    @ResultProxy(log = false)
    public TouchActionsBinding perform() {
        binding.getBean().perform();
        return this;
    }

    private WebElement getElement() {
        return element;
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
