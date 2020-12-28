package cn.veasion.auto.util;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.JavaScriptCore;
import org.openqa.selenium.WebDriver;

/**
 * JavaScriptContextUtils
 *
 * @author luozhuowei
 * @date 2020/7/31
 */
public class JavaScriptContextUtils {

    private static final ThreadLocal<JavaScriptCore.JavaScriptContext> jsContextThreadLocal = new ThreadLocal<>();

    public static JavaScriptCore.JavaScriptContext get(WebDriver driver, Environment env) {
        synchronized (jsContextThreadLocal) {
            JavaScriptCore.JavaScriptContext context = jsContextThreadLocal.get();
            if (context == null) {
                context = JavaScriptCore.JavaScriptContext.getInstance(driver, env);
                jsContextThreadLocal.set(context);
            }
            return context;
        }
    }

    public static JavaScriptCore.JavaScriptContext peek() {
        return jsContextThreadLocal.get();
    }

    public static void remove() {
        synchronized (jsContextThreadLocal) {
            JavaScriptCore.JavaScriptContext context = jsContextThreadLocal.get();
            if (context != null) {
                context.destroy();
                jsContextThreadLocal.remove();
            }
        }
    }

}
