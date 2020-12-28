package cn.veasion.auto.core;

import cn.veasion.auto.util.Constants;
import com.alibaba.fastjson.JSON;
import cn.veasion.auto.util.ConfigVars;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Environment
 *
 * @author luozhuowei
 * @date 2020/12/25
 */
public class Environment extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Environment() {
        Arrays.asList(Keys.ENTER, Keys.BACK_SPACE,
                Keys.TAB, Keys.ARROW_UP, Keys.ARROW_DOWN, Keys.ARROW_LEFT, Keys.ARROW_RIGHT,
                Keys.PAGE_UP, Keys.PAGE_DOWN, Keys.END, Keys.HOME,
                Keys.SHIFT, Keys.CONTROL, Keys.ALT,
                Keys.ESCAPE, Keys.F12).forEach(key -> put("KEY_" + key.name(), key.toString())
        );
        put(Constants.DESKTOP_DIR, javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
    }

    public void loadGlobal(String configJson) {
        putAll(JSON.parseObject(configJson));
    }

    public Object readConfigVar(ConfigVars vars) {
        return getOrDefault(vars.name(), vars.getDefaultValue());
    }

}
