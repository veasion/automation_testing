package cn.veasion.auto.bind;

import cn.veasion.auto.core.Environment;
import org.openqa.selenium.WebDriver;

/**
 * Binding
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
public class Binding<T> {

    private WebDriver webDriver;
    private Environment env;
    private T bean;

    public Binding(WebDriver webDriver, Environment env, T bean) {
        this.webDriver = webDriver;
        this.env = env;
        this.bean = bean;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public Environment getEnv() {
        return env;
    }

    public T getBean() {
        return bean;
    }

}
