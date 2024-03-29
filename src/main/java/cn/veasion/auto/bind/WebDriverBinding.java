package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.InitializingBinding;
import cn.veasion.auto.core.BindingProxy;
import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.JavaScriptCore;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.core.WebDriverUtils;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.ArgsCommandOption;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.ConfigVars;
import cn.veasion.auto.util.Constants;
import cn.veasion.auto.util.JavaScriptContextUtils;
import cn.veasion.auto.util.JavaScriptUtils;
import cn.veasion.auto.util.Pair;
import cn.veasion.auto.util.ScriptHttpUtils;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * WebDriverBinding
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@SuppressWarnings({"restriction", "unused"})
@Api.ClassInfo(value = JavaScriptCore.DRIVER, root = true)
public class WebDriverBinding extends SearchContextBinding<WebDriver> implements InitializingBinding<WebDriver> {

    @ResultProxy
    @Api("打开页面并等待页面加载")
    public void open(String url) throws InterruptedException {
        binding.getWebDriver().get(url);
        waitForPageLoaded((Integer) ConfigVars.TIMEOUT_PAGE_LOAD.read(binding.getEnv()));
        Thread.sleep(200);
    }

    @ResultProxy
    @Api("打开页面不等待")
    public void openNotWait(String url) {
        binding.getWebDriver().get(url);
    }

    @Api(value = "动作", result = Actions.class)
    @ResultProxy(log = false)
    public Object newActions() {
        return new Actions(binding.getWebDriver());
    }

    @Api("点击坐标")
    @ResultProxy(interval = true)
    public void clickPoint(@Api.Param(jsType = "number") Object x, @Api.Param(jsType = "number") Object y) {
        int pointX = new BigDecimal(x.toString()).intValue();
        int pointY = new BigDecimal(y.toString()).intValue();
        new Actions(binding.getWebDriver()).moveByOffset(pointX, pointY).click().perform();
    }

    @ResultProxy
    @Api("向浏览器驱动执行 js 代码")
    public Object executeScript(String jsCode) {
        return executeScriptByParams(jsCode);
    }

    @Override
    @ResultProxy
    @Api("向浏览器驱动执行 js 代码")
    @SuppressWarnings("rawtypes")
    public Object executeScriptByParams(String jsCode, @Api.Param(allowNone = true) Object... args) {
        if (args != null && args.length > 0) {
            Object[] params = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof JavaScriptBinding) {
                    params[i] = ((JavaScriptBinding) args[i]).getBinding().getBean();
                } else {
                    params[i] = args[i];
                }
            }
            return ((JavascriptExecutor) binding.getWebDriver()).executeScript(jsCode, params);
        } else {
            return ((JavascriptExecutor) binding.getWebDriver()).executeScript(jsCode);
        }
    }

    @Api("等待页面加载")
    @ResultProxy(interval = true)
    public void waitForPageLoaded(@Api.Param(allowNone = true) Integer seconds) {
        // 浏览器加载完成
        onWait(driver -> Objects.equals("complete", executeScript("return document.readyState")), seconds);
    }

    @ResultProxy
    @Api("iframe")
    public void iframe(String target, @Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        if (fun == null || !fun.isFunction()) {
            throw new AutomationException("iframe `fun` is not function");
        }
        try {
            binding.getWebDriver().switchTo().frame(findElement(target));
            fun.call(null);
        } finally {
            binding.getWebDriver().switchTo().defaultContent();
        }
    }

    @Api("截图")
    @ResultProxy(value = false, log = false)
    public boolean screenshot(@Api.Param(allowNull = true) String path) {
        try {
            if (JavaScriptUtils.isNull(path)) {
                path = String.format("%s\\screenshot_%d.png", binding.getEnv().get(Constants.DESKTOP_DIR), System.currentTimeMillis());
            }
            File image = ((TakesScreenshot) binding.getWebDriver()).getScreenshotAs(OutputType.FILE);
            if (image != null) {
                try (FileInputStream is = new FileInputStream(image)) {
                    Files.copy(is, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("截图失败", e);
        }
        return false;
    }

    @Api("获取当前窗口句柄")
    @ResultProxy(value = false, log = false)
    public String getWindowHandle() {
        return binding.getWebDriver().getWindowHandle();
    }

    @Api("打开并切换到新的窗口")
    @ResultProxy(value = false)
    public String openNewWindow() {
        executeScript("window.open('about:blank');");
        return switchToNextWindow(null);
    }

    @Api("在新的窗口中执行函数")
    @ResultProxy(value = false)
    public void withNewWindow(@Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        if (fun == null || !fun.isFunction()) {
            throw new AutomationException("withNewWindow params is not function");
        }
        final String currentHandle = binding.getWebDriver().getWindowHandle();
        try {
            String newWindow = openNewWindow();
            fun.call(null, newWindow);
            binding.getWebDriver().close();
        } finally {
            switchToNextWindow(currentHandle);
        }
    }

    @Api("切换窗口")
    @ResultProxy(value = false)
    public String switchToNextWindow(@Api.Param(desc = "指定窗口句柄，为 null 则切换为下一个窗口", allowNone = true) String windowHandle) {
        if (JavaScriptUtils.isNull(windowHandle)) {
            int currentIndex = -1;
            final String currentHandle = binding.getWebDriver().getWindowHandle();
            String[] windowHandles = binding.getWebDriver().getWindowHandles().toArray(new String[]{});
            for (int i = 0; i < windowHandles.length; i++) {
                if (Objects.equals(windowHandles[i], currentHandle)) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex == -1 || currentIndex >= windowHandles.length - 1) {
                currentIndex = 0;
            } else {
                currentIndex++;
            }
            windowHandle = windowHandles[currentIndex];
        }
        binding.getWebDriver().switchTo().window(windowHandle);
        return windowHandle;
    }

    @ResultProxy
    @Api(value = "在新的浏览器驱动中执行脚本", result = Environment.class)
    public Object runScriptWithNewDriver(final Object env,
                                         final String path,
                                         @Api.Param(desc = "是否异步，true 异步时返回 null可以通过 env.putSystemVar 来传递数据") boolean async) throws ExecutionException, InterruptedException {
        Objects.requireNonNull(path);
        File scriptFile = new File(path);
        if (!scriptFile.exists() || !scriptFile.isFile()) {
            throw new AutomationException("脚本文件错误：" + scriptFile.getPath());
        }
        final ArgsCommandOption option = binding.getEnv().getOption();
        FutureTask<Environment> task = new FutureTask<>(() -> runScriptWithNewDriver(option, env, scriptFile));
        new Thread(task).start();
        if (async) {
            return null;
        } else {
            return task.get();
        }
    }

    @SuppressWarnings("unchecked")
    private Environment runScriptWithNewDriver(ArgsCommandOption option, Object env, File scriptFile) {
        WebDriver newDriver = null;
        try {
            Environment newEnv = new Environment(option);
            newEnv.setDebug(false);
            String configPath = newEnv.getString("configPath");
            if (configPath == null || "".equals(configPath)) {
                configPath = JavaScriptUtils.getFilePath("config.json");
            }
            newEnv.loadGlobalConfig(configPath);
            if (!JavaScriptUtils.isNull(env)) {
                Object object = JavaScriptUtils.toJavaObject(env);
                if (object instanceof Map) {
                    newEnv.putAll((Map<String, Object>) object);
                } else {
                    throw new AutomationException("env参数格式");
                }
            }
            newDriver = WebDriverUtils.getWebDriver(newEnv);
            JavaScriptContextUtils.get(newDriver, newEnv).execute(scriptFile);
            return newEnv;
        } catch (Exception e) {
            LOGGER.error("执行 withNewDriver 异常", e);
            return null;
        } finally {
            JavaScriptContextUtils.remove();
            if (newDriver != null) {
                newDriver.quit();
            }
        }
    }

    @ResultProxy
    @Api("运行新的脚本")
    public void runNewScript(String path) throws ExecutionException, InterruptedException {
        Objects.requireNonNull(path);
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    runNewScript(f.getPath());
                }
            }
        } else {
            runNewScriptFile(file);
        }
    }

    private void runNewScriptFile(File file) throws ExecutionException, InterruptedException {
        if (!file.exists()) {
            throw new AutomationException("脚本文件不存在：" + file.getPath());
        }
        if (file.isFile()) {
            if (binding.getEnv().get(Constants.ENV_GLOBAL_FILE_PATH) == null) {
                binding.getEnv().put(Constants.ENV_GLOBAL_FILE_PATH, file.getPath());
                binding.getEnv().put(Constants.ENV_GLOBAL_FILE_NAME, file.getName());
            }
            FutureTask<Boolean> task = new FutureTask<>(() -> {
                try {
                    JavaScriptContextUtils.get(binding.getWebDriver(), binding.getEnv()).execute(file);
                    return true;
                } catch (Exception e) {
                    LOGGER.info("info: {}", JavaScriptCore.getCurrentJsInfo());
                    LOGGER.error("运行 js 文件异常，文件：{}", file.getPath(), e);
                    return false;
                } finally {
                    JavaScriptContextUtils.remove();
                }
            });
            new Thread(task).start();
            if (!Boolean.TRUE.equals(task.get())) {
                throw new AutomationException("运行 js 文件失败：" + file.getPath());
            }
        } else {
            throw new AutomationException("脚本文件错误: " + file.getPath());
        }
    }

    private ChromeDriverBinding chrome;

    @Api(result = ChromeDriverBinding.class)
    @ResultProxy(value = false, log = false)
    public synchronized Object toChromeDriver() {
        if (!(binding.getWebDriver() instanceof ChromeDriver)) {
            throw new AutomationException(binding.getWebDriver().getClass().getSimpleName() + " 非 ChromeDriver");
        }
        if (chrome == null && binding.getWebDriver() instanceof ChromeDriver) {
            ChromeDriverBinding chromeBinding = new ChromeDriverBinding();
            chromeBinding.setBinding(binding);
            chrome = BindingProxy.create(chromeBinding);
        }
        return chrome;
    }

    @Api("HTTP 请求")
    @ResultProxy(value = false)
    public Object request(@Api.Param(desc = "请求url/uri", allowNull = true) String url,
                          @Api.Param(desc = "请求方式 POST/GET 默认GET", allowNone = true) String method,
                          @Api.Param(desc = "请求body内容", allowNone = true) Object content,
                          @Api.Param(desc = "请求头", allowNone = true) Object headers) throws IOException {
        Pair<String, String> urlPair = ScriptHttpUtils.getUrlPair(url, this::executeScript);
        Integer timeout = (Integer) binding.getEnv().readConfigVar(ConfigVars.TIMEOUT_WAIT);
        return ScriptHttpUtils.requestByXHR(urlPair.getLeft(), urlPair.getRight(), JavaScriptUtils.isEmpty(method) ? "GET" : method, content, headers, timeout);
    }

    @Api("下载")
    @ResultProxy(value = false)
    public Object download(@Api.Param(desc = "请求url/uri", allowNull = true) String url,
                           @Api.Param(desc = "文件路径", allowNone = true) String filePath) throws IOException {
        if (JavaScriptUtils.isEmpty(filePath) || filePath.endsWith("\\") || filePath.endsWith("/")) {
            int idx = url.indexOf("?");
            int lastIdx = url.lastIndexOf("/");
            String fileName = idx > 0 ? url.substring(lastIdx + 1, idx) : url.substring(lastIdx + 1);
            if (lastIdx <= 6 || "".equals(fileName.trim())) {
                fileName = System.currentTimeMillis() + ".html";
            }
            filePath = (JavaScriptUtils.isEmpty(filePath) ? (binding.getEnv().get(Constants.DESKTOP_DIR) + File.separator) : filePath) + fileName;
        }
        Pair<String, String> urlPair = ScriptHttpUtils.getUrlPair(url, this::executeScript);
        ScriptHttpUtils.download(urlPair.getLeft(), urlPair.getRight(), filePath);
        return filePath;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public WebDriver initBean(WebDriver webDriver, Environment env) {
        return webDriver;
    }

}
