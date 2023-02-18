package cn.veasion.auto.core;

import cn.veasion.auto.util.ArgsCommandOption;
import cn.veasion.auto.util.Constants;
import com.alibaba.fastjson.JSON;
import cn.veasion.auto.util.ConfigVars;
import org.openqa.selenium.Keys;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Environment
 *
 * @author luozhuowei
 * @date 2020/12/25
 */
public class Environment extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private boolean debug;
    private ArgsCommandOption option;

    public Environment(ArgsCommandOption option) {
        this.option = Objects.requireNonNull(option);
        boolean isDebug = option.getBoolean("debug", false);
        if (!isDebug) {
            isDebug = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("-agentlib:jdwp");
        }
        setDebug(isDebug);
        for (Keys key : Keys.values()) {
            put("KEY_" + key.name(), key.toString());
        }
        put(Constants.DESKTOP_DIR, javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
    }

    public Environment(ArgsCommandOption option, String configPath) throws Exception {
        this(option);
        loadGlobalConfig(configPath);
    }

    public String getString(Object key) {
        Object value = super.get(key);
        if (value == null || value instanceof String) {
            return (String) value;
        } else {
            return String.valueOf(value);
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public ArgsCommandOption getOption() {
        return option;
    }

    public void loadGlobalConfig(String configPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configPath), StandardCharsets.UTF_8))) {
            String configJson = br.lines().filter(l -> !l.trim().startsWith("//")).collect(Collectors.joining("\n"));
            putAll(JSON.parseObject(configJson));
        }
    }

    public Object readConfigVar(ConfigVars vars) {
        return getOrDefault(vars.name(), vars.getDefaultValue());
    }

}
