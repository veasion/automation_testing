package cn.veasion.auto.core;

import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.util.ArgsCommandOption;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.JavaScriptUtils;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
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
        boolean h5 = option.getBoolean("h5");
        String userAgent = option.getOption("userAgent");
        boolean headless = option.getBoolean("headless");
        boolean gpuDisable = option.getBoolean("disable-gpu");
        List<String> driverArguments = (List<String>) env.get("DRIVER_ARGUMENTS");
        Map<String, Object> experimentalOptions = (Map<String, Object>) env.get("EXPERIMENTAL_OPTIONS");

        String driverPath = (String) env.get("CHROME_DRIVER_PATH");
        String firefoxDriverPath = (String) env.get("FIREFOX_DRIVER_PATH");
        if (driverPath != null) {
            if (!new File(driverPath).exists()) {
                throw new AutomationException("CHROME_DRIVER_PATH指向的文件不存在：" + driverPath);
            }
            System.setProperty("webdriver.chrome.driver", driverPath);
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.setHeadless(true);
                options.addArguments("--window-size=1920,1080");
            } else {
                options.addArguments("--start-maximized");
                String devCrx = JavaScriptUtils.getFilePath("devScriptCoding.crx");
                if (devCrx != null && env.isDebug()) {
                    options.addExtensions(new File(devCrx));
                }
            }
            if (h5) {
                String deviceName = "iPhone X"; // iPhone X / Galaxy S5
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", deviceName);
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
            }
            if (gpuDisable) {
                options.addArguments("--disable-gpu");
            }
            if (driverArguments != null) {
                options.addArguments(driverArguments);
            }
            if (experimentalOptions != null) {
                experimentalOptions.forEach(options::setExperimentalOption);
            }
            ChromeDriver chromeDriver;
            try {
                chromeDriver = new ChromeDriver(options);
            } catch (Exception e) {
                LOGGER.error("chrome浏览器驱动有问题或版本不匹配，chromedriver路径: {}\n下载驱动地址见: {}", driverPath, "http://npm.taobao.org/mirrors/chromedriver/");
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
            if (!new File(firefoxDriverPath).exists()) {
                throw new AutomationException("firefoxDriverPath指向的文件不存在：" + firefoxDriverPath);
            }
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            FirefoxOptions options = new FirefoxOptions();
            if (h5 || !StringUtils.isEmpty(userAgent)) {
                // iPhone X
                String useragent = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/12.0 Mobile/15A372 Safari/604.1";
                if (!StringUtils.isEmpty(userAgent)) {
                    useragent = userAgent;
                }
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("general.useragent.override", useragent);
                options.setProfile(firefoxProfile);
            }
            if (headless) {
                options.setHeadless(true);
                options.addArguments("--window-size=1920,1080");
            }
            if (gpuDisable) {
                options.addArguments("--disable-gpu");
            }
            if (driverArguments != null) {
                options.addArguments(driverArguments);
            }
            FirefoxDriver firefoxDriver = new FirefoxDriver(options);
            if (h5) {
                firefoxDriver.manage().window().setSize(new Dimension(375, 812));
            } else {
                firefoxDriver.manage().window().maximize();
            }
            return firefoxDriver;
        } else {
            throw new AutomationException("CHROME_DRIVER_PATH 驱动未设置！");
        }
    }

}
