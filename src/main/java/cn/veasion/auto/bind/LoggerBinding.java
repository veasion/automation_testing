package cn.veasion.auto.bind;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggerBinding
 *
 * @author luozhuowei
 * @date 2020/6/15
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "log", desc = "日志")
public class LoggerBinding implements InitializingBinding<Logger> {

    private Binding<Logger> binding;

    @Api
    @ResultProxy(value = false, log = false)
    public void debug(Object message) {
        binding.getBean().debug(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void warn(Object message) {
        binding.getBean().warn(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void info(Object message) {
        binding.getBean().info(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void error(Object message) {
        binding.getBean().error(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void error(Object message, Object e) {
        if (e instanceof Throwable) {
            binding.getBean().error(String.valueOf(message), (Throwable) e);
        } else {
            binding.getBean().error(message + (e != null ? e.toString() : ""));
        }
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void invokeMethod(String name, Object[] args) {
        this.info(JavaScriptUtils.log(name, args));
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Logger initBean(WebDriver webDriver, Environment env) {
        return LoggerFactory.getLogger(LoggerBinding.class);
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<Logger> binding) {
        this.binding = binding;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Binding<Logger> getBinding() {
        return binding;
    }

}
