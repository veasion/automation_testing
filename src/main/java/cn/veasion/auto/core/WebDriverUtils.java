package cn.veasion.auto.core;

import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.util.ArgsCommandOption;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.Constants;
import cn.veasion.auto.util.JavaScriptUtils;
import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * WebDriverUtils
 *
 * @author luozhuowei
 * @date 2020/12/25
 */
public class WebDriverUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverUtils.class);

    private WebDriverUtils() {
    }

    /**
     * 浏览器驱动
     *
     * @param env env
     */
    @SuppressWarnings("unchecked")
    public static WebDriver getWebDriver(Environment env) throws Exception {
        ArgsCommandOption option = env.getOption();
        if (option == null) {
            option = new ArgsCommandOption();
        }
        String userAgent = option.getOption("userAgent");
        boolean headless = option.getBoolean("headless");
        boolean gpuDisable = option.getBoolean("disable-gpu");

        Map<String, Object> driverOptions = (Map<String, Object>) env.get(Constants.DRIVER_OPTIONS);
        Map<String, Object> proxy = driverOptions != null ? (Map<String, Object>) driverOptions.get("proxy") : null;
        List<String> driverArguments = driverOptions != null ? (List<String>) driverOptions.get("arguments") : null;
        Map<String, Object> capability = driverOptions != null ? (Map<String, Object>) driverOptions.get("capability") : null;

        String chromeDriverPath = (String) env.get("CHROME_DRIVER_PATH");
        String firefoxDriverPath = (String) env.get("FIREFOX_DRIVER_PATH");

        if (chromeDriverPath != null) {
            if ("auto".equalsIgnoreCase(chromeDriverPath)) {
                WebDriverManager.chromedriver().setup();
            } else {
                if (!new File(chromeDriverPath).exists()) {
                    throw new AutomationException("CHROME_DRIVER_PATH指向的文件不存在：" + chromeDriverPath);
                }
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            }
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");
            } else {
                options.addArguments("--start-maximized");
                String devCrx = JavaScriptUtils.getFilePath("devScriptCoding.crx");
                if (devCrx != null && env.isDebug()) {
                    options.addExtensions(new File(devCrx));
                }
            }
            if (gpuDisable) {
                options.addArguments("--disable-gpu");
            }
            if (driverArguments != null) {
                options.addArguments(driverArguments);
            }
            if (capability != null && !capability.isEmpty()) {
                capability.forEach(options::setCapability);
            }
            Map<String, Object> experimentalOptions = driverOptions != null ? (Map<String, Object>) driverOptions.get("experimentalOptions") : null;
            if (experimentalOptions != null) {
                experimentalOptions.forEach(options::setExperimentalOption);
            }
            if (proxy != null && !proxy.isEmpty()) {
                options.setProxy(new Proxy(proxy));
            }
            ChromeDriver chromeDriver;
            try {
                chromeDriver = new ChromeDriver(options);
            } catch (Exception e) {
                LOGGER.error("chrome浏览器驱动有问题或版本不匹配，chromedriver路径: {}\n下载驱动地址见: {}", chromeDriverPath, "http://npm.taobao.org/mirrors/chromedriver/");
                throw e;
            }
            if (env.isDebug()) {
                Debug.initSocketServer(chromeDriver, env);
            }
            if (!StringUtils.isEmpty(userAgent)) {
                chromeDriver.executeCdpCommand("Network.setUserAgentOverride", ImmutableMap.of("userAgent", userAgent));
            }
            return chromeDriver;
        } else if (firefoxDriverPath != null) {
            if ("auto".equalsIgnoreCase(firefoxDriverPath)) {
                WebDriverManager.firefoxdriver().setup();
            } else {
                if (!new File(firefoxDriverPath).exists()) {
                    throw new AutomationException("firefoxDriverPath指向的文件不存在：" + firefoxDriverPath);
                }
                System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            }
            FirefoxOptions options = new FirefoxOptions();
            if (!StringUtils.isEmpty(userAgent)) {
                options.addPreference("general.useragent.override", userAgent);
            }
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");
            }
            if (gpuDisable) {
                options.addArguments("--disable-gpu");
            }
            if (driverArguments != null) {
                options.addArguments(driverArguments);
            }
            if (capability != null && !capability.isEmpty()) {
                capability.forEach(options::setCapability);
            }
            if (proxy != null && !proxy.isEmpty()) {
                options.setProxy(new Proxy(proxy));
            }
            Map<String, Object> preference = driverOptions != null ? (Map<String, Object>) driverOptions.get("preference") : null;
            if (preference != null) {
                preference.forEach(options::addPreference);
            }
            FirefoxDriver firefoxDriver = new FirefoxDriver(options);
            firefoxDriver.manage().window().maximize();
            return firefoxDriver;
        } else {
            throw new AutomationException("CHROME_DRIVER_PATH 驱动未设置！");
        }
    }

}
