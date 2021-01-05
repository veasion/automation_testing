package cn.veasion.auto.bind.bean;

import cn.veasion.auto.bind.Binding;
import cn.veasion.auto.bind.InitializingBinding;
import cn.veasion.auto.core.Environment;
import org.openqa.selenium.WebDriver;

/**
 * AbstractInitializingBean
 *
 * @author luozhuowei
 * @date 2021/1/5
 */
public abstract class AbstractInitializingBean implements InitializingBinding<AbstractInitializingBean> {

    private Binding<AbstractInitializingBean> binding;

    @Override
    public AbstractInitializingBean initBean(WebDriver webDriver, Environment env) {
        try {
            AbstractInitializingBean bean = getClass().newInstance();
            bean.binding = new Binding<>(webDriver, env, bean);
            return bean;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setBinding(Binding<AbstractInitializingBean> binding) {
        this.binding = binding;
    }

    @Override
    public Binding<AbstractInitializingBean> getBinding() {
        return binding;
    }

    protected WebDriver getWebDriver() {
        return binding.getWebDriver();
    }

    protected Environment getEnv() {
        return binding.getEnv();
    }
}
