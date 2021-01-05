package cn.veasion.auto.core;

import cn.veasion.auto.bind.Binding;
import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.bind.bean.AbstractInitializingBean;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.ClassSearcher;
import cn.veasion.auto.util.JavaScriptUtils;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * BindingFactory
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@SuppressWarnings("unchecked")
public class BindingFactory {

    private static final ThreadLocal<Map<Object, JavaScriptBinding<Object>>> cacheMap;
    private static final Map<Class<?>, Class<? extends JavaScriptBinding<Object>>> bindingMap;

    static {
        try {
            bindingMap = new ConcurrentHashMap<>();
            cacheMap = ThreadLocal.withInitial(ConcurrentHashMap::new);
            String packageName = JavaScriptBinding.class.getPackage().getName();
            List<Class<?>> classes = new ClassSearcher().search(packageName, JavaScriptBinding.class);
            for (Class<?> clazz : classes) {
                if (JavaScriptBinding.class.isAssignableFrom(clazz) && (clazz.getModifiers() & Modifier.ABSTRACT) == 0) {
                    Type actualType = JavaScriptUtils.getActualType(clazz);
                    if (actualType instanceof ParameterizedType) {
                        actualType = ((ParameterizedType) actualType).getRawType();
                    }
                    if (actualType != null && !"java.lang.Object".equals(actualType.getTypeName())) {
                        Class<?> bindingClass = Class.forName(actualType.getTypeName());
                        bindingMap.put(bindingClass, (Class<? extends JavaScriptBinding<Object>>) clazz);
                    } else if (actualType == null && AbstractInitializingBean.class.isAssignableFrom(clazz)) {
                        bindingMap.put(clazz, (Class<? extends JavaScriptBinding<Object>>) clazz);
                    }
                }
            }
        } catch (Exception e) {
            throw new AutomationException(e);
        }
    }

    private BindingFactory() {
    }

    public static <T, B extends JavaScriptBinding<T>> B of(T bean, Class<B> bindingClass) {
        try {
            B binding = bindingClass.newInstance();
            binding.setBinding(new Binding<>(JavaScriptCore.getWebDriver(), JavaScriptCore.getEnvironment(), bean));
            return binding;
        } catch (Exception e) {
            throw new AutomationException(e);
        }
    }

    static Object convert(Object value) {
        try {
            if (value instanceof Collection) {
                return convertList((Collection<Object>) value);
            } else if (value instanceof Object[]) {
                Object[] temp = (Object[]) value;
                Object[] result = new Object[temp.length];
                for (int i = 0; i < temp.length; i++) {
                    result[i] = convertObj(temp[i]);
                }
                return result;
            } else if (value instanceof Map && !bindingMap.containsKey(value.getClass())) {
                return convertMap(value);
            } else {
                return convertObj(value);
            }
        } catch (Exception e) {
            throw new AutomationException(value.getClass().getSimpleName() + " convert error !", e);
        }
    }

    private static Object convertMap(Object value) throws InstantiationException, IllegalAccessException {
        Map<Object, Object> temp = (Map<Object, Object>) value;
        if (temp.isEmpty()) {
            return value;
        }
        Map<Object, Object> result;
        try {
            result = (Map<Object, Object>) value.getClass().newInstance();
        } catch (Exception e) {
            result = new HashMap<>();
        }
        for (Object key : (temp).keySet()) {
            result.put(key, convertObj((temp).get(key)));
        }
        return result;
    }

    private static Collection<Object> convertList(Collection<Object> value) throws InstantiationException, IllegalAccessException {
        if (value == null || value.isEmpty()) {
            return value;
        }
        Collection<Object> result;
        try {
            result = value.getClass().newInstance();
        } catch (Exception e) {
            result = new ArrayList<>(value.size());
        }
        Iterator<?> iterator = value.iterator();
        while (iterator.hasNext()) {
            result.add(convertObj(iterator.next()));
        }
        return result;
    }

    private static Object convertObj(Object value) throws IllegalAccessException, InstantiationException {
        if (value instanceof org.springframework.cglib.proxy.Factory) {
            return value;
        }
        Class<? extends JavaScriptBinding<Object>> convert = convertClass(value.getClass());
        if (convert == null) {
            return BindingProxy.create(value);
        }
        synchronized (cacheMap) {
            Map<Object, JavaScriptBinding<Object>> cache = cacheMap.get();
            if (cache.containsKey(value)) {
                return cache.get(value);
            }
            JavaScriptBinding<Object> javaScriptBinding = convert.newInstance();
            javaScriptBinding.setBinding(new Binding<>(JavaScriptCore.getWebDriver(), JavaScriptCore.getEnvironment(), value));
            javaScriptBinding = BindingProxy.create(javaScriptBinding);
            cache.put(value, javaScriptBinding);
            return javaScriptBinding;
        }
    }

    public static Class<? extends JavaScriptBinding<Object>> convertClass(Class<?> clazz) {
        Class<? extends JavaScriptBinding<Object>> bindingClass = bindingMap.get(clazz);
        if (bindingClass == null) {
            Set<Class<?>> keySet = bindingMap.keySet();
            for (Class<?> c : keySet) {
                if (c.isAssignableFrom(clazz)) {
                    bindingClass = bindingMap.get(c);
                    break;
                }
            }
        }
        return bindingClass;
    }

    static void delCache() {
        cacheMap.remove();
    }

    static Map<Class<?>, Class<? extends JavaScriptBinding<Object>>> bindingMap() {
        return bindingMap;
    }

}
