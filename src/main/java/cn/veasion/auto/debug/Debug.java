package cn.veasion.auto.debug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.veasion.auto.core.JavaScriptCore;
import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.util.ApiCodeTips;
import cn.veasion.auto.util.AutomationException;
import cn.veasion.auto.util.ClassSearcher;
import cn.veasion.auto.util.Constants;
import cn.veasion.auto.util.DevRunUtils;
import cn.veasion.auto.util.JavaScriptUtils;
import cn.veasion.auto.core.Environment;
import cn.veasion.auto.util.JavaScriptContextUtils;
import io.netty.channel.ChannelHandlerContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Debug
 *
 * @author luozhuowei
 * @date 2020/8/2
 */
public class Debug {

    private static final Logger LOGGER = LoggerFactory.getLogger(Debug.class);

    private static WebSocketServer server;
    private static List<String> history = new ArrayList<>();
    private static Supplier<JavaScriptCore.JavaScriptContext> javaScriptContext;
    private static Map<String, Set<BiConsumer<JSONObject, ChannelHandlerContext>>> listenerMap = new ConcurrentHashMap<>();

    static {
        String packageName = SocketHandler.class.getPackage().getName();
        List<Class<?>> classes = new ClassSearcher().search(packageName, SocketHandler.class);
        for (Class<?> handlerClass : classes) {
            try {
                if (handlerClass == SocketHandler.class) {
                    continue;
                }
                SocketHandler handler = (SocketHandler) handlerClass.newInstance();
                Debug.addEventListener(handler.getType(), handler);
            } catch (Exception e) {
                LOGGER.error("初始化socket处理器失败！", e);
            }
        }
    }

    private Debug() {
    }

    private static void addEventListener(String type, BiConsumer<JSONObject, ChannelHandlerContext> handler) {
        listenerMap.compute(type, (key, handlers) -> {
            if (handlers == null) {
                handlers = new HashSet<>();
            }
            handlers.add(handler);
            return handlers;
        });
    }

    public static void initSocketServer(final WebDriver driver, final Environment env) throws Exception {
        ApiCodeTips.asynInit();
        server = new WebSocketServer();
        javaScriptContext = () -> {
            try {
                return JavaScriptContextUtils.get(driver, env);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
        server.bind(Constants.PORT, Constants.WEBSOCKET_PATH, Constants.MAX_PACK_LENGTH, (message, ctx) -> {
            final JSONObject json = JSON.parseObject(message);
            if (!json.containsKey("type")) {
                LOGGER.debug("socket message type is null, message: {}", message);
                return;
            }
            Set<BiConsumer<JSONObject, ChannelHandlerContext>> handlers = listenerMap.get(json.getString("type"));
            if (handlers != null) {
                handlers.forEach(o -> o.accept(json, ctx));
            }
        });
    }

    static JavaScriptCore.JavaScriptContext getJavaScriptContext() {
        return javaScriptContext != null ? javaScriptContext.get() : null;
    }

    public static void closeSocketServer() {
        if (server != null) {
            server.close();
        }
    }

    static String newSocketResult(JSONObject req, String type, Object data) {
        JSONObject returnObj = req != null ? new JSONObject(req) : new JSONObject();
        returnObj.put("type", type);
        if (data != null && (JavaScriptBinding.class.isAssignableFrom(data.getClass()))) {
            returnObj.put("data", data.toString());
        } else {
            returnObj.put("data", data);
        }
        return returnObj.toJSONString();
    }

    public static String input(String title, Scanner scanner) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        JavaScriptUtils.println(title);
        String input = scanner.nextLine().trim();
        if (input.startsWith("<<<") || input.startsWith(">>>")) {
            // 读多行 <<<input>>>
            StringBuilder lines = new StringBuilder(input.substring(3));
            while (scanner.hasNextLine()) {
                input = scanner.nextLine().trim();
                lines.append("\r\n").append(input);
                if (input.startsWith("<<<") || input.endsWith(">>>")) {
                    lines.setLength(lines.length() - 3);
                    break;
                }
            }
            input = lines.toString();
        }
        return input;
    }

    public static void console(ScriptEngine scriptEngine, Consumer<ScriptEngine> resetScriptEngine) {
        LOGGER.info("debug模式开启！");
        Scanner scanner = new Scanner(System.in);
        try {
            do {
                String jsCode = input("请输入js代码: ", scanner);
                if ("exit".equals(jsCode)) {
                    try {
                        Runtime.getRuntime().exec("taskkill /IM chromedriver.exe /F");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                } else if ("reset".equals(jsCode) && resetScriptEngine != null) {
                    resetScriptEngine.accept(scriptEngine);
                    continue;
                }
                jsCode = handleCommand(scriptEngine, jsCode);
                if (jsCode != null) {
                    evalJsCode(scriptEngine, jsCode);
                }
            } while (true);
        } finally {
            scanner.close();
        }
    }

    private static void evalJsCode(ScriptEngine scriptEngine, String jsCode) {
        try {
            Object result = scriptEngine.eval(jsCode);
            if (result != null) {
                if (result instanceof String || Number.class.isAssignableFrom(result.getClass())) {
                    JavaScriptUtils.println(result);
                } else {
                    StringBuilder sb = new StringBuilder();
                    JavaScriptUtils.appendObject(sb, result);
                    JavaScriptUtils.println(sb.toString());
                }
            }
            Thread.sleep(100);
        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.error("{} 执行异常 => {}", jsCode, JavaScriptUtils.getJavaScriptException(e).getMessage());
        }
    }

    private static String handleCommand(ScriptEngine scriptEngine, String jsCode) {
        if (jsCode.equals("top") || jsCode.startsWith("top ")) {
            int count = 1;
            String str = jsCode.substring(3).trim();
            if (!"".equals(str)) {
                count = Integer.parseInt(str);
            }
            for (int i = history.size() - Math.min(count, history.size()); i < history.size(); i++) {
                JavaScriptUtils.println(history.get(i));
            }
            return null;
        } else {
            history.add(jsCode);
        }
        if ("reload".equals(jsCode)) {
            jsCode = "runNewJs(env.getString('filePath'));";
        } else if (jsCode.startsWith("run ")) {
            jsCode = jsCode.substring(3).trim().replace("\\", "/");
            jsCode = "runNewJs(env.getSourcePath('/" + jsCode + "'));";
        } else if (jsCode.startsWith("> ")) {
            jsCode = jsCode.substring(1).trim();
            try {
                String scriptDir = (String) scriptEngine.eval("env.getSourcePath('/script');");
                File file = findFile(new File(scriptDir), jsCode);
                if (file != null) {
                    jsCode = "runNewJs('" + file.getPath().replace("\\", "/") + "');";
                } else {
                    throw new AutomationException("文件未找到：" + jsCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (jsCode.startsWith("author ")) {
            try {
                DevRunUtils.runAuthorScript(scriptEngine, jsCode.substring(6).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return jsCode;
    }

    private static File findFile(File file, String name) {
        File[] files;
        File result = null;
        if (file.isDirectory() && (files = file.listFiles()) != null) {
            for (File listFile : files) {
                result = findFile(listFile, name);
                if (result != null) {
                    break;
                }
            }
        } else if (file.getName().startsWith(name)) {
            result = file;
        }
        return result;
    }

}
