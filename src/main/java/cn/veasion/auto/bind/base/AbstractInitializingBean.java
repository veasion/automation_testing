package cn.veasion.auto.bind.base;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
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
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
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
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<AbstractInitializingBean> binding) {
        this.binding = binding;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
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
