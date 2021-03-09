package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.AbstractInitializingBean;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JavaScriptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogBean
 *
 * @author luozhuowei
 * @date 2020/6/15
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "log", desc = "日志")
public class LogBean extends AbstractInitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogBean.class);

    @Api
    @ResultProxy(value = false, log = false)
    public void debug(Object message) {
        LOGGER.debug(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void warn(Object message) {
        LOGGER.warn(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void info(Object message) {
        LOGGER.info(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void error(Object message) {
        LOGGER.error(String.valueOf(message));
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void error(Object message, Object e) {
        if (e instanceof Throwable) {
            LOGGER.error(String.valueOf(message), (Throwable) e);
        } else {
            LOGGER.error(message + (e != null ? e.toString() : ""));
        }
    }

    @Api
    @ResultProxy(value = false, log = false)
    public void invokeMethod(String name, Object[] args) {
        this.info(JavaScriptUtils.log(name, args));
    }
}
