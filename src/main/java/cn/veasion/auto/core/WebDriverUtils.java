package cn.veasion.auto.core;

import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.JavaScriptUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WebDriverUtils
 *
 * @author luozhuowei
 * @date 2020/12/25
 */
public class WebDriverUtils {

    private WebDriverUtils() {
    }

    public static WebDriver getWebDriver(Environment env, String configPath, boolean h5) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configPath), StandardCharsets.UTF_8))) {
            String config = br.lines().filter(l -> !l.trim().startsWith("//")).collect(Collectors.joining("\n"));
            env.loadGlobal(config);
        }
        String driverPath = (String) env.get("CHROME_DRIVER_PATH");
        String firefoxDriverPath = (String) env.get("FIREFOX_DRIVER_PATH");
        if (driverPath != null) {
            if (!new File(driverPath).exists()) {
                throw new AutomationException("CHROME_DRIVER_PATH指向的文件不存在：" + driverPath);
            }
            System.setProperty("webdriver.chrome.driver", driverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            if (JavaScriptCore.isDebug()) {
                options.addExtensions(new File(JavaScriptUtils.getFilePath("devScriptCoding.crx")));
            }
            if (h5) {
                String deviceName = "iPhone X"; // iPhone X / Galaxy S5
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", deviceName);
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
            }
            ChromeDriver chromeDriver = new ChromeDriver(options);
            if (JavaScriptCore.isDebug()) {
                Debug.initSocketServer(chromeDriver, env);
            }
            return chromeDriver;
        } else if (firefoxDriverPath != null) {
            if (!new File(firefoxDriverPath).exists()) {
                throw new AutomationException("firefoxDriverPath指向的文件不存在：" + firefoxDriverPath);
            }
            for (int i = 0; i < 3; i++) {
                JavaScriptUtils.println("Javascript 开发建议使用Chrome驱动启动，会有插件自动生成代码加持！");
            }
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            FirefoxOptions options = new FirefoxOptions();
            if (h5) {
                // iPhone X
                String useragent = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/12.0 Mobile/15A372 Safari/604.1";
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("general.useragent.override", useragent);
                options.setProfile(firefoxProfile);
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