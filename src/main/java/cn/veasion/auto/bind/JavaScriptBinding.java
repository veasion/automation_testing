package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.Binding;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.ApiDocumentGenerator;
import cn.veasion.auto.util.JavaScriptUtils;
import cn.veasion.auto.util.ConfigVars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

/**
 * JavaScriptBinding
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
public interface JavaScriptBinding<T> extends ApiDocumentGenerator.DocGenerator {

    Logger LOGGER = LoggerFactory.getLogger(JavaScriptBinding.class);

    void setBinding(Binding<T> binding);

    Binding<T> getBinding();

    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    default void onWait(ExpectedCondition<?> fun) {
        onWait(fun::apply, null);
    }

    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    default void onWait(Function<WebDriver, ?> fun) {
        onWait(fun, null);
    }

    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    default void onWait(Function<WebDriver, ?> fun, Integer seconds) {
        if (JavaScriptUtils.isNull(seconds)) {
            seconds = (Integer) getBinding().getEnv().readConfigVar(ConfigVars.ELEMENT_SOFT_WAIT);
        }
        WebDriverWait wait = new WebDriverWait(getBinding().getWebDriver(), Duration.ofSeconds(Math.max(1, seconds)));
        try {
            wait.until(fun);
        } catch (Exception e) {
            LOGGER.debug("onWait 等待超时");
        }
    }
}
