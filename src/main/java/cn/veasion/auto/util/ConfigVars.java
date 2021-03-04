package cn.veasion.auto.util;

import cn.veasion.auto.core.Environment;

/**
 * ConfigVars
 *
 * @author luozhuowei
 * @date 2020/12/4
 */
public enum ConfigVars {

    /**
     * 命令执行间隔（毫秒）
     */
    COMMAND_INTERVAL(Integer.class, 100),

    /**
     * 点击、验证一个页面元素时的“软”等待（秒）
     */
    ELEMENT_SOFT_WAIT(Integer.class, 2),

    /**
     * 页面加载超时时间（秒）
     */
    TIMEOUT_PAGE_LOAD(Integer.class, 10),

    /**
     * 等待超时时间（秒）
     */
    TIMEOUT_WAIT(Integer.class, 10);

    final Class<?> valueType;
    final Object defaultValue;

    <T> ConfigVars(Class<T> valueType, T defaultValue) {
        this.valueType = valueType;
        this.defaultValue = defaultValue;
    }

    public Object read(Environment env) {
        Object value = env.get(name());
        if (value != null && !valueType.isAssignableFrom(value.getClass())) {
            throw new AutomationException(String.format("config %s required a value of type %s but got %s", this, valueType.getName(), value.getClass().getName()));
        }
        return value == null ? defaultValue : value;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
