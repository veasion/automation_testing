package cn.veasion.auto;

import cn.veasion.auto.core.Environment;
import cn.veasion.auto.core.WebDriverUtils;
import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.core.JavaScriptCore;
import cn.veasion.auto.util.JavaScriptUtils;
import org.opencv.core.Core;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Objects;

/**
 * Development
 *
 * @author luozhuowei
 * @date 2020/6/12
 */
public class Development {

    static {
        // -Djava.library.path=$PROJECT_DIR$/opencv/x64
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        WebDriver driver = null;
        try {
            JavaScriptCore.setDebug(true);
            Environment env = new Environment();
            String configPath = JavaScriptUtils.getFilePath("config.json");
            String includePath = JavaScriptUtils.getFilePath("include");
            driver = WebDriverUtils.getWebDriver(env, configPath, false, false);
            File[] files = new File(includePath).listFiles(name -> name.getName().endsWith(".js"));
            Development.printInfo();
            JavaScriptCore.include(Objects.requireNonNull(files));
            JavaScriptCore.execute(driver, env, null);
            // JavaScriptCore.execute(driver, env, new File(getFilePath("script/crawler.js")));
        } finally {
            Debug.closeSocketServer();
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static void printInfo() {
        JavaScriptUtils.println("\t-------------------------");
        JavaScriptUtils.println("\t      JS 自动化测试      ");
        JavaScriptUtils.println("\t-------------------------");
        JavaScriptUtils.println("1> 命令框中可以直接运行js代码");
        JavaScriptUtils.println("   如: open('https://www.baidu.com')");
        JavaScriptUtils.println("2> 运行js文件: run script/baidu.js");
        JavaScriptUtils.println("3> 查找并运行script目录下js文件: > xxx");
        JavaScriptUtils.println("4> 重新运行第一次执行的文件: reload");
        JavaScriptUtils.println("5> 重置脚本引擎: reset");
        JavaScriptUtils.println("6> 函数案例详见: readme.js");
        JavaScriptUtils.println("7> 退出: exit");
        JavaScriptUtils.println("8> 在线文档: https://veasion.github.io/automationjs-docs");
        JavaScriptUtils.println();
    }

}
