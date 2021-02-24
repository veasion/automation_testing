package cn.veasion.auto.bind;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.util.*;
import cn.veasion.auto.core.JavaScriptCore;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeDate;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.Objects;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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

    private static final Random RAND = new Random(System.currentTimeMillis());

    @ResultProxy
    @Api("打开页面并等待页面加载")
    public void open(String url) {
        binding.getWebDriver().get(url);
        waitForPageLoaded((Integer) ConfigVars.TIMEOUT_PAGE_LOAD.read(binding.getEnv()));
        sleep(200);
    }

    @ResultProxy
    @Api("暂停多少毫秒")
    public void pause(long millis) {
        sleep(millis);
    }

    @ResultProxy
    @Api("暂停多少毫秒")
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("sleep", e);
            Thread.currentThread().interrupt();
        }
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

    @Api("info")
    @ResultProxy(value = false)
    public String info() {
        return JavaScriptCore.getCurrentJsInfo();
    }

    @Api("断言")
    @ResultProxy(value = false)
    public void assertResult(boolean flag, Object message) {
        String msg = String.valueOf(message);
        if (!flag) {
            LOGGER.error("断言未通过: {}", msg);
            throw new AssertException(msg);
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

    @Api("格式化时间")
    @ResultProxy(value = false, log = false)
    public String formatDate(Object date, String pattern) {
        Date newDate = null;
        if (date instanceof NativeDate) {
            newDate = new Date((long) NativeDate.getTime(date));
        } else if (date instanceof Date) {
            newDate = (Date) date;
        } else if (date instanceof Number) {
            newDate = new Date(((Number) date).longValue());
        } else if (date instanceof ScriptObjectMirror) {
            ScriptObjectMirror obj = (ScriptObjectMirror) date;
            if ("Date".equalsIgnoreCase(obj.getClassName())) {
                double time = (double) obj.callMember("getTime");
                newDate = new Date((long) time);
            }
        }
        if (newDate == null) {
            return String.valueOf(date);
        }
        return new SimpleDateFormat(pattern).format(newDate);
    }

    @Api("随机字符串")
    @ResultProxy(value = false, log = false)
    public String randCode(Integer length) {
        if (length == null) {
            length = 8;
        }
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            if (length >= 8) {
                length -= 8;
                sb.append(String.format("%08d", RAND.nextInt(100000000)));
            } else {
                length -= 1;
                sb.append(RAND.nextInt(10));
            }
        }
        return sb.toString();
    }

    @Api("控制台打印")
    @ResultProxy(value = false, log = false)
    public void println(Object message, @Api.Param(allowNone = true) Object... args) {
        String str;
        if (message == null) {
            str = "null";
        } else if (message instanceof String) {
            str = message.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            JavaScriptUtils.appendObject(sb, message);
            str = sb.toString();
        }
        if (args != null && args.length > 0) {
            JavaScriptUtils.println(String.format(str, args));
        } else {
            JavaScriptUtils.println(str);
        }
    }

    @Api("计算")
    @ResultProxy(value = false, log = false)
    public String calculate(@Api.Param(desc = "运算式") String str, @Api.Param(desc = "保留几位小数") int n) {
        return CalculatorUtils.calculate(str, n);
    }

    @Api("写文本文件")
    @ResultProxy(value = false, log = false)
    public void writeText(String path, String context, boolean append, @Api.Param(allowNone = true) String charsetName) throws IOException {
        StandardOpenOption[] options;
        Path filePath = Paths.get(path);
        if (append) {
            options = new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND};
        } else {
            Files.deleteIfExists(filePath);
            options = new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE};
        }
        charsetName = JavaScriptUtils.isEmpty(charsetName) ? "UTF-8" : charsetName;
        Files.write(filePath, context.getBytes(charsetName), options);
    }

    @Api("读取文本")
    @ResultProxy(value = false, log = false)
    public String readText(String pathOrUrl, @Api.Param(allowNone = true) String charsetName) throws IOException {
        InputStream inputStream;
        charsetName = JavaScriptUtils.isEmpty(charsetName) ? "UTF-8" : charsetName;
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            inputStream = new URL(pathOrUrl).openStream();
        } else {
            inputStream = new FileInputStream(pathOrUrl);
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            return sb.toString();
        } finally {
            inputStream.close();
        }
    }

    @ResultProxy
    @Api("运行新的脚本")
    public void runNewScript(String path) throws ExecutionException, InterruptedException {
        if (path == null || "".equals(path)) {
            path = (String) binding.getEnv().get(Constants.ENV_GLOBAL_FILE_PATH);
        }
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
                    LOGGER.info("info: {}", info());
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
    public void openNewWindow() {
        executeScript("window.open('about:blank');");
        switchToNextWindow(null);
    }

    @Api("在新的窗口中执行函数")
    @ResultProxy(value = false)
    public void withNewWindow(@Api.Param(desc = "函数", jsType = "Function") ScriptObjectMirror fun) {
        if (fun == null || !fun.isFunction()) {
            throw new AutomationException("withNewWindow params is not function");
        }
        final String currentHandle = binding.getWebDriver().getWindowHandle();
        try {
            openNewWindow();
            fun.call(null);
            binding.getWebDriver().close();
        } finally {
            switchToNextWindow(currentHandle);
        }
    }

    @Api("切换窗口")
    @ResultProxy(value = false)
    public void switchToNextWindow(@Api.Param(desc = "指定窗口句柄，为 null 则切换为下一个窗口", allowNone = true) String windowHandle) {
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
    }

    @ResultProxy
    @Api(value = "数据库连接", result = java.sql.Connection.class)
    public Object createJdbcConnection(String jdbcUrl, String user, String password) throws SQLException {
        return JdbcDao.createConnection(jdbcUrl, user, password);
    }

    @ResultProxy
    @Api(value = "Mysql数据库连接", result = java.sql.Connection.class)
    public Object createMysqlConnection(String ip, int port, String database, String user, String password) throws SQLException {
        return JdbcDao.createConnection(JdbcDao.MYSQL_EVAL_URL, ip, port, database, user, password);
    }

    @ResultProxy(value = false, log = false)
    @Api(value = "输入")
    public String input(String title) {
        return Debug.input(title, null);
    }

    @Api("HTTP 请求")
    @ResultProxy(value = false)
    public Object request(@Api.Param(desc = "请求url/uri", allowNull = true) String url,
                          @Api.Param(desc = "请求方式 POST/GET 默认GET", allowNone = true) String method,
                          @Api.Param(desc = "请求body内容", allowNone = true) Object content,
                          @Api.Param(desc = "请求头", allowNone = true) Object headers) throws IOException {
        String url1, url2;
        if (url.trim().toLowerCase().startsWith("http")) {
            // url
            int idx = url.indexOf("/", url.indexOf("://") + 3);
            if (idx > 0) {
                url1 = url.substring(0, idx);
                url2 = url.substring(idx);
            } else {
                url1 = url;
                url2 = "";
            }
        } else {
            // uri
            url2 = url;
            url1 = (String) executeScript("return location.protocol + '//' + location.hostname");
        }
        Integer timeout = (Integer) binding.getEnv().readConfigVar(ConfigVars.TIMEOUT_WAIT);
        return ScriptHttpUtils.request(url1, url2, JavaScriptUtils.isEmpty(method) ? "GET" : method, content, headers, timeout);
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public WebDriver initBean(WebDriver webDriver, Environment env) {
        return webDriver;
    }

}
