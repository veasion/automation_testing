package cn.veasion.auto.bind;

import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JavaScriptUtils;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;
import java.util.Objects;

/**
 * ChromeDriverBinding
 *
 * @author luozhuowei
 * @date 2021/3/2
 */
@SuppressWarnings({"restriction", "unused"})
@Api.ClassInfo(desc = "chromeDriver")
public class ChromeDriverBinding extends WebDriverBinding {

    @Api("执行cdp命令")
    @ResultProxy(value = false)
    @SuppressWarnings("unchecked")
    public Object executeCdpCommand(String commandName, Object parameters) {
        Objects.requireNonNull(parameters);
        Map<String, Object> object = (Map<String, Object>) JavaScriptUtils.toJavaObject(parameters);
        return getChromeDriver().executeCdpCommand(commandName, object);
    }

    @Api
    @ResultProxy(value = false)
    public void setUserAgent(String userAgent) {
        executeCdpCommand("Network.setUserAgentOverride", ImmutableMap.of("userAgent", userAgent));
    }

//    public void test() {
//        org.openqa.selenium.devtools.DevTools devTools = getChromeDriver().getDevTools();
//        devTools.createSession();
//        devTools.getDomains().network().setUserAgent("xxx");
//        devTools.send(new org.openqa.selenium.devtools.Command<>("Network.setUserAgentOverride", com.google.common.collect.ImmutableMap.of("userAgent", "xxxx")));
//        org.openqa.selenium.devtools.Event<org.openqa.selenium.json.JsonInput> event = new org.openqa.selenium.devtools.Event<>("xxx", j -> j);
//        devTools.addListener(event, j -> {
//            System.out.println("listener: " + j);
//        });
//        devTools.getDomains().network().addRequestHandler((request) -> true, (request) -> {
//            System.out.println(request.getMethod() + ": " + request.getUri());
//            return null;
//        });
//    }

    private ChromeDriver getChromeDriver() {
        return (ChromeDriver) binding.getWebDriver();
    }

}
