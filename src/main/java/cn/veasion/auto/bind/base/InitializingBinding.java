package cn.veasion.auto.bind.base;

import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import org.openqa.selenium.WebDriver;

/**
 * InitializingBinding
 *
 * @author luozhuowei
 * @date 2020/6/15
 */
public interface InitializingBinding<T> extends JavaScriptBinding<T> {

    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    T initBean(WebDriver webDriver, Environment env);

}
