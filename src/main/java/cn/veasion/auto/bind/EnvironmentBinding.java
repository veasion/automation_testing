package cn.veasion.auto.bind;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.util.EvalAnalysisUtils;
import cn.veasion.auto.core.JavaScriptCore;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * EnvironmentBinding
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = JavaScriptCore.ENV, desc = "环境变量")
public class EnvironmentBinding implements InitializingBinding<Environment> {

    private Environment env;
    private Map<String, Object> localMap = new HashMap<>();
    private static final Map<String, Object> SYSTEM_VAR_MAP = new ConcurrentHashMap<>();

    @Api("存储变量（当前脚本）")
    @ResultProxy(value = false, log = false)
    public void put(String key, Object value) {
        localMap.put(key, value);
    }

    @Api("存储全局变量（当前驱动）")
    public void putGlobal(String key, Object value) {
        env.put(key, JavaScriptUtils.toJavaObject(value));
    }

    @Api("存储系统变量（当前系统）")
    public void putSystemVar(String key, Object value) {
        SYSTEM_VAR_MAP.put(key, JavaScriptUtils.toJavaObject(value));
    }

    @Api("获取系统变量（当前系统）")
    public Object getSystemVar(String key) {
        return SYSTEM_VAR_MAP.get(key);
    }

    @Api("获取变量")
    @ResultProxy(value = false, log = false)
    public Object get(String key) {
        if (localMap.containsKey(key)) {
            return localMap.get(key);
        } else if (env.containsKey(key)) {
            return env.get(key);
        } else {
            return SYSTEM_VAR_MAP.get(key);
        }
    }

    @Api("获取变量")
    @ResultProxy(value = false, log = false)
    public String getString(String key) {
        Object o = get(key);
        return o != null ? o.toString() : null;
    }

    @Api("获取变量")
    @ResultProxy(value = false, log = false)
    public Object getOrDefault(String key, Object defaultVal) {
        Object o = get(key);
        return o != null ? o : defaultVal;
    }

    @Api("移除当前变量")
    @ResultProxy(value = false, log = false)
    public void remove(String key) {
        localMap.remove(key);
    }

    @Api("translate")
    @ResultProxy(log = false)
    public Object translate(String str) {
        return EvalAnalysisUtils.eval(str, (Function<String, ?>) (key) -> {
            if (localMap.containsKey(key)) {
                return localMap.get(key);
            } else {
                return env.get(key);
            }
        });
    }

    @Api("translate")
    @ResultProxy(log = false)
    public Object translate(String str, Object object) {
        if (object == null) {
            return translate(str);
        }
        return EvalAnalysisUtils.evalMultiple(str, localMap, env, JavaScriptUtils.toJavaObject(object));
    }

    @Api("获取class决定路径")
    @ResultProxy(value = false, log = false)
    public String getPath(String path) {
        return JavaScriptUtils.getFilePath(path);
    }

    @Api("获取源文件绝对路径")
    @ResultProxy(value = false, log = false)
    public String getSourcePath(String path) {
        return JavaScriptUtils.getSourcePath(path);
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Environment initBean(WebDriver webDriver, Environment env) {
        return env;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<Environment> binding) {
        if (binding.getBean() != null) {
            this.env = binding.getBean();
        } else {
            this.env = binding.getEnv();
        }
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Binding<Environment> getBinding() {
        return new Binding<>(null, env, env);
    }
}
