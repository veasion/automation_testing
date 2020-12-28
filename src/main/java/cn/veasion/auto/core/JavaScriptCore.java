package cn.veasion.auto.core;

import cn.veasion.auto.bind.InitializingBinding;
import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.Constants;
import cn.veasion.auto.util.JavaScriptUtils;
import cn.veasion.auto.util.JavascriptException;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JavaScriptCore
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@SuppressWarnings("restriction")
public class JavaScriptCore {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptCore.class);

    private static boolean debug = false;
    public static final String ENV = "env";
    public static final String DRIVER = "driver";
    private static final NashornScriptEngineFactory SCRIPT_ENGINE_FACTORY;
    private static final int ENGINE_BINDINGS_SCOPE = ScriptContext.ENGINE_SCOPE;
    private static final Set<File> includeFiles = new HashSet<>();
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static final ThreadLocal<Environment> environment = new ThreadLocal<>();
    private static final ThreadLocal<ScriptEngine> scriptEngineThreadLocal = new ThreadLocal<>();

    static {
        SCRIPT_ENGINE_FACTORY = new NashornScriptEngineFactory();
    }

    private JavaScriptCore() {
    }

    public static synchronized void include(File... jsFiles) {
        Set<String> paths = includeFiles.stream().map(File::getPath).collect(Collectors.toSet());
        for (File jsFile : jsFiles) {
            if (!paths.contains(jsFile.getPath())) {
                includeFiles.add(jsFile);
            }
        }
    }

    public static Object execute(WebDriver driver, Environment env, File jsFile) {
        ScriptEngine scriptEngine = getScriptEngine(driver, env);
        if (jsFile != null) {
            String fileName = jsFile.getName();
            try {
                env.put(Constants.ENV_GLOBAL_FILE_NAME, fileName);
                env.put(Constants.ENV_GLOBAL_FILE_PATH, jsFile.getPath());
                return evalJsFile(scriptEngine, jsFile);
            } catch (Exception e) {
                LOGGER.info("info: {}", getCurrentJsInfo());
                JavascriptException jsException = JavaScriptUtils.getJavaScriptException(fileName, e);
                String message = fileName + "执行脚本异常！=> " + jsException.getMessage();
                LOGGER.error(message, e);
                throw jsException;
            } finally {
                if (debug) {
                    Debug.console(scriptEngine, engine -> initScriptEngine(engine, driver, env));
                }
            }
        } else if (debug) {
            Debug.console(scriptEngine, engine -> initScriptEngine(engine, driver, env));
            return null;
        } else {
            throw new AutomationException("jsFile is null !");
        }
    }

    private static ScriptEngine getScriptEngine(WebDriver driver, Environment env) {
        synchronized (scriptEngineThreadLocal) {
            String[] options = new String[]{"--language=es6", "--no-java"};
            if (debug) {
                // --log=source:info,apply2call:info,lower:info,compiler:info,methodhandles:info,scopedepths:info
                options = new String[]{"--language=es6", "--no-java", "--debug-locals", "-dump-on-error"};
            }
            ScriptEngine scriptEngine = SCRIPT_ENGINE_FACTORY.getScriptEngine(options);
            scriptEngineThreadLocal.set(scriptEngine);
            initScriptEngine(scriptEngine, driver, env);
            return scriptEngine;
        }
    }

    private static void initScriptEngine(ScriptEngine scriptEngine, WebDriver driver, Environment env) {
        try {
            webDriver.set(driver);
            environment.set(env);
            BindingFactory.delCache();
            scriptEngine.setContext(new SimpleScriptContext());
            loadGlobalMethod(scriptEngine, driver, env);
            doInclude(scriptEngine);
        } catch (Exception e) {
            throw new AutomationException("初始化脚本引擎失败", e);
        }
    }

    private static void doInclude(ScriptEngine scriptEngine) {
        for (File includeFile : includeFiles) {
            try {
                evalJsFile(scriptEngine, includeFile);
            } catch (Exception e) {
                JavascriptException jsException = JavaScriptUtils.getJavaScriptException(includeFile.getName(), e);
                LOGGER.error("执行公共脚本异常：{}", includeFile.getName(), e);
                throw jsException;
            }
        }
    }

    private static Object evalJsFile(ScriptEngine scriptEngine, File jsFile) throws ScriptException, FileNotFoundException {
        if (debug) {
            return scriptEngine.eval("load('" + jsFile.getPath().replace("\\", "/") + "');");
        } else {
            return scriptEngine.eval(new InputStreamReader(new FileInputStream(jsFile), StandardCharsets.UTF_8));
        }
    }

    private static void loadGlobalMethod(ScriptEngine scriptEngine, WebDriver driver, Environment env) throws IllegalAccessException, InstantiationException, ScriptException {
        Map<Class<?>, Class<? extends JavaScriptBinding<Object>>> bindingMap = BindingFactory.bindingMap();
        Set<Class<?>> keySet = bindingMap.keySet();
        for (Class<?> beanClass : keySet) {
            Class<?> bindingClass = BindingFactory.convertClass(beanClass);
            Api.ClassInfo classInfo = bindingClass.getAnnotation(Api.ClassInfo.class);
            if (classInfo != null && !StringUtils.isEmpty(classInfo.value())) {
                Object bean;
                if (InitializingBinding.class.isAssignableFrom(bindingMap.get(beanClass))) {
                    InitializingBinding<?> initializingBinding = (InitializingBinding<?>) bindingMap.get(beanClass).newInstance();
                    bean = initializingBinding.initBean(driver, env);
                } else {
                    bean = beanClass.newInstance();
                }
                functionBinding(scriptEngine, bean, classInfo.value());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void functionBinding(ScriptEngine scriptEngine, Object obj, String defaultKey) throws ScriptException {
        Object binding;
        Class<? extends JavaScriptBinding<Object>> bindingClass;
        if (obj instanceof JavaScriptBinding) {
            binding = obj;
            bindingClass = (Class<? extends JavaScriptBinding<Object>>) obj.getClass();
        } else {
            binding = BindingFactory.convert(obj);
            bindingClass = BindingFactory.convertClass(obj.getClass());
        }
        Api.ClassInfo classInfo = bindingClass.getAnnotation(Api.ClassInfo.class);
        if (classInfo == null) {
            return;
        }
        String bindingKey = defaultKey;
        if (!StringUtils.isEmpty(classInfo.value())) {
            bindingKey = classInfo.value();
        }
        scriptEngine.getBindings(ENGINE_BINDINGS_SCOPE).put(bindingKey, binding);
        if (classInfo.root()) {
            generatorRootFunction(scriptEngine, bindingClass, bindingKey);
        }
    }

    private static void generatorRootFunction(ScriptEngine scriptEngine, Class<?> bindingClass, String bindingKey) throws ScriptException {
        Method[] methods = bindingClass.getMethods();
        StringBuilder jsCode = new StringBuilder();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            Class<?> declaringClass = method.getDeclaringClass();
            if (declaringClass.equals(Object.class)) {
                continue;
            }
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                jsMethod(jsCode, method, bindingKey);
                jsCode.append("\n");
            }
        }
        scriptEngine.eval(jsCode.toString());
    }

    private static void jsMethod(StringBuilder jsCode, Method method, String binding) {
        String name = method.getName();
        int paramCount = method.getParameterCount();
        jsCode.append("function ").append(name).append("(");
        appendParams(jsCode, paramCount);
        jsCode.append("){ return ").append(binding).append(".").append(name).append("(");
        appendParams(jsCode, paramCount);
        jsCode.append(");}");
    }

    private static void appendParams(StringBuilder jsCode, int paramCount) {
        for (int i = 0; i < paramCount; i++) {
            jsCode.append("v").append(i + 1).append(",");
        }
        if (paramCount > 0) {
            jsCode.setLength(jsCode.length() - 1);
        }
    }

    static WebDriver getWebDriver() {
        return webDriver.get();
    }

    static Environment getEnvironment() {
        return environment.get();
    }

    public static String getCurrentJsInfo() {
        ScriptEngine scriptEngine = scriptEngineThreadLocal.get();
        if (scriptEngine != null) {
            try {
                Object result = scriptEngine.eval("(function(){" +
                        "let info = { line: __LINE__, file: __FILE__, dir: __DIR__, fileName: env.get('fileName'), filePath: env.get('filePath') };" +
                        "return info;" +
                        "})()");
                StringBuilder sb = new StringBuilder();
                JavaScriptUtils.appendObject(sb, result);
                return sb.toString();
            } catch (ScriptException e) {
                LOGGER.error("getCurrentJsInfo()", e);
            }
        }
        return null;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        JavaScriptCore.debug = debug;
    }

    public static final class JavaScriptContext {

        private WebDriver driver;
        private Environment env;
        private ScriptEngine scriptEngine;

        private JavaScriptContext() {
        }

        private JavaScriptContext(ScriptEngine scriptEngine, WebDriver driver, Environment env) {
            this();
            this.scriptEngine = scriptEngine;
            this.driver = driver;
            this.env = env;
        }

        public static JavaScriptContext getInstance(WebDriver driver, Environment env) {
            return new JavaScriptContext(JavaScriptCore.getScriptEngine(driver, env), driver, env);
        }

        public Object execute(String jsCode) {
            try {
                return scriptEngine.eval(jsCode);
            } catch (Exception e) {
                throw JavaScriptUtils.getJavaScriptException(e);
            }
        }

        public Object execute(File jsFile) {
            try {
                String path = jsFile.getPath().replace("\\", "/");
                scriptEngine.eval(String.format("env.put('fileName', '%s');env.put('filePath', '%s');", jsFile.getName(), path));
                return scriptEngine.eval(new InputStreamReader(new FileInputStream(jsFile), StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw JavaScriptUtils.getJavaScriptException(jsFile.getName(), e);
            }
        }

        public Object getValue(String envKey) {
            return env.get(envKey);
        }

        public WebDriver getDriver() {
            return driver;
        }

        public void destroy() {
            JavaScriptCore.webDriver.remove();
            JavaScriptCore.environment.remove();
            JavaScriptCore.scriptEngineThreadLocal.remove();
        }
    }

}
