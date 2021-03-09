package cn.veasion.auto.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.veasion.auto.bind.EnvironmentBinding;
import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.util.JavaScriptUtils;
import cn.veasion.auto.util.ConfigVars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * BindingProxy
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
public class BindingProxy<T> implements MethodInterceptor {

    private static final Map<Class<?>, Logger> LOGGER_MAP = new ConcurrentHashMap<>();

    private T obj;

    private BindingProxy(T obj) {
        this.obj = obj;
    }

    @Override
    public Object intercept(Object source, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.equals(Object.class) || Modifier.isPrivate(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
            return proxy.invokeSuper(source, args);
        }
        ResultProxy resultProxy = method.getAnnotation(ResultProxy.class);
        Environment environment = JavaScriptCore.getEnvironment();
        if (environment != null) {
            EnvironmentBinding env = (EnvironmentBinding) BindingFactory.convert(environment);
            executeLog(env, method, args, resultProxy);
            if (resultProxy != null && resultProxy.interval()) {
                ConfigVars commandInterval = ConfigVars.COMMAND_INTERVAL;
                Integer millis = (Integer) env.getOrDefault(commandInterval.name(), commandInterval.getDefaultValue());
                if (millis != null) {
                    Thread.sleep(millis);
                }
            }
        }
        Object result = method.invoke(obj, args);
        if (result == null) {
            return null;
        }
        Class<? extends JavaScriptBinding<Object>> bindingClass = BindingFactory.convertClass(result.getClass());
        if ((resultProxy == null && bindingClass == null) || (resultProxy != null && !resultProxy.value())) {
            return result;
        }
        Object convert = BindingFactory.convert(result);
        if (!method.getReturnType().isAssignableFrom(convert.getClass())) {
            throw new ClassCastException("使用@ResultProxy的方法，返回类型请用 Object！" + method.getReturnType().getName() + " cannot be cast to  " + convert.getClass().getName());
        }
        return convert;
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(T obj) {
        if (Modifier.isFinal(obj.getClass().getModifiers())) {
            return obj;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(new BindingProxy<>(obj));
        return (T) enhancer.create();
    }

    private void executeLog(EnvironmentBinding env, Method method, Object[] args, ResultProxy resultProxy) {
        if (resultProxy != null && !resultProxy.log()) {
            return;
        }
        if ("true".equals(env.getString("DISABLE_PROXY_LOG"))) {
            return;
        }
        String name = method.getName();
        Class<?> declaringClass = method.getDeclaringClass();
        if (!LOGGER_MAP.containsKey(declaringClass)) {
            LOGGER_MAP.put(declaringClass, LoggerFactory.getLogger(declaringClass));
        }
        String methodLog = JavaScriptUtils.log(name, args);
        LOGGER_MAP.get(declaringClass).info(methodLog);
    }

}
