package cn.veasion.auto.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EvalAnalysisUtils<br>
 *
 * <p>解析表达式</p>
 *
 * @author luozhuowei
 * @date 2020/10/14
 */
public class EvalAnalysisUtils {

    private static final String _L = "[", _R = "]";
    private static final Pattern EL_PATTERN = Pattern.compile("\\$\\{([^}]*)\\}");
    private static final Pattern EL_UNIQUE_PATTERN = Pattern.compile("^\\$\\{([^}]*)\\}$");
    private static final Pattern LR_PATTERN = Pattern.compile("([^\\[\\]]*)\\[([^\\[\\]]*)\\]");

    public static void main(String[] args) {
        Map<String, Object> object = new HashMap<>();
        object.put("age", 18);
        object.put("var", "name");
        object.put("idx", 1);
        Map<String, Object> user = new HashMap<>();
        user.put("name", "veasion");
        user.put("var", "name");
        user.put("index", 0);
        object.put("list", new Object[]{user, new Integer("100")});
        object.put("common", (Function<String, ?>) (key) -> "common_" + key);

        System.out.println(eval("${abc|default}", object));
        System.out.println(eval("${list[0]}", object));
        System.out.println(eval("${list[idx].value}", object));
        System.out.println(eval("${common.test}", object));
        System.out.println(eval("${ $[0] }-${ $[1] }", new String[]{"name1", "name2"}));
        System.out.println(eval("name: ${list[0][list[list[0].index].var]}", object));
        System.out.println(eval("name1: ${list[0][var]}, name2: ${list[0]['name']}", object));
        System.out.println(evalMultiple("${age}, ${list[0].name}, ${$[0]}, ${name}, ${xxx}", object, new int[]{10, 20}, user, (Function<String, ?>) (key) -> "default_" + key));
    }

    /**
     * 解析表达式<br>
     *
     * <p>eg: <code>hello ${list[0].user.name}</code></p>
     */
    public static Object evalMultiple(String str, Object... objects) {
        return eval(str, (Function<String, ?>) (key) -> {
            for (Object object : objects) {
                if (object == null) {
                    continue;
                }
                Object val = parseObject(object, key);
                if (val == null && "$".equals(key) && isArray(object)) {
                    val = object;
                }
                if (val != null) {
                    return val;
                }
            }
            return null;
        });
    }

    /**
     * 解析表达式<br>
     *
     * <p>eg: <code>hello ${list[0].user.name}</code></p>
     */
    public static Object eval(String str, final Object object) {
        if (str == null) {
            return null;
        }
        if ("".equals(str.trim())) {
            return str;
        }
        Matcher matcher;
        if (str.startsWith("${") && str.endsWith("}")) {
            matcher = EL_UNIQUE_PATTERN.matcher(str);
            if (matcher.find()) {
                return parse(matcher.group(1), object);
            }
        }
        int index = 0;
        StringBuilder sb = new StringBuilder();
        matcher = EL_PATTERN.matcher(str);
        while (matcher.find()) {
            sb.append(str.substring(index, matcher.start()));
            sb.append(parse(matcher.group(1), object));
            index = matcher.end();
        }
        sb.append(str.substring(index));
        return sb.toString();
    }

    /**
     * 解析对象<br>
     *
     * <p>eg: <code>list[0].user.name</code></p>
     */
    public static Object parse(final String text, final Object object) {
        if (text == null) {
            return null;
        }
        if ("".equals(text.trim())) {
            return text;
        }
        String str = text.trim();
        Object defaultValue = null;
        int defIndex = str.lastIndexOf("|");
        if (defIndex > 0) {
            // matcher => |default
            defaultValue = str.substring(defIndex + 1).trim();
            str = str.substring(0, defIndex);
        }
        Object result;
        if (hasBrackets(str)) {
            result = parseGroup(SplitGroupUtils.group(str, _L, _R, true), object);
        } else {
            result = parseConsecutive(object, str);
        }
        return result != null ? result : defaultValue;
    }

    private static Object parseGroup(List<SplitGroupUtils.Group> groupList, Object object) {
        // matcher => list[0][list[0].var]
        Object result;
        StringBuilder sb = new StringBuilder();
        for (SplitGroupUtils.Group g : groupList) {
            if (g.getChildren() != null && g.getChildren().size() > 0) {
                result = parseGroup(g.getChildren(), object);
                if (result == null) {
                    throw new AutomationException(String.format("%s is null", g.getContext()));
                }
                sb.append(_L);
                if (isNumber(result)) {
                    sb.append(result);
                } else {
                    sb.append("'").append(result).append("'");
                }
                sb.append(_R);
            } else {
                sb.append(g.getValue());
            }
        }
        return parseConsecutive(object, sb.toString());
    }

    private static Object parseConsecutive(Object object, String str) {
        Object result = object;
        String[] split = str.trim().split("\\.");
        for (String key : split) {
            if (hasBrackets(key)) {
                // matcher => list['users'][0][var]
                result = parseBrackets(object, result, key);
            } else {
                // matcher => key
                result = parseObject(result, key);
            }
            if (result == null) {
                break;
            }
        }
        return result;
    }

    private static Object parseBrackets(final Object object, Object result, String text) {
        // matcher => list['users'][0][var]
        Matcher matcher = LR_PATTERN.matcher(text);
        while (matcher.find()) {
            String group = matcher.group();
            String groupKey = matcher.group(1).trim();
            text = matcher.group(2);
            if (!"".equals(groupKey) && !("$".equals(groupKey) && isArray(result) && isNumber(text))) {
                result = parseObject(result, groupKey);
            }
            if (isNumber(text)) {
                // matcher => [0]
                result = parseArray(result, group, Integer.parseInt(text));
            } else if (text.startsWith("'") && text.endsWith("'")) {
                // matcher => ['users']
                result = parseObject(result, text.substring(1, text.length() - 1));
            } else {
                // matcher => [var]
                Object varValue = parseObject(object, text);
                if (varValue == null) {
                    throw new AutomationException(String.format("%s 变量不存在 => %s", group, text));
                }
                String var = String.valueOf(varValue);
                if (isNumber(var) && isArray(result)) {
                    result = parseArray(result, group, Integer.parseInt(var));
                } else {
                    result = parseObject(result, var);
                }
            }
            if (result == null) {
                break;
            }
        }
        return result;
    }

    private static Object parseArray(Object object, String text, int index) {
        if (object != null) {
            try {
                if (object instanceof Collection) {
                    return ((Collection<?>) object).toArray()[index];
                } else if (object instanceof Object[]) {
                    Object[] array = (Object[]) object;
                    return array[index];
                } else if (object.getClass().isArray()) {
                    return Array.get(object, index);
                }
                throw new AutomationException(String.format("%s (%s) 不是一个 array 类型 => %s", object, object.getClass().getName(), text));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AutomationException(String.format("%s 数组越界 => %s", object, text));
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Object parseObject(Object object, String key) {
        if (object instanceof Map) {
            return ((Map<?, ?>) object).get(key);
        } else if (object instanceof Function) {
            return ((Function<String, ?>) object).apply(key);
        } else {
            try {
                return reflect(object, key);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private static boolean hasBrackets(String text) {
        return text != null && text.contains(_L) && text.contains(_R);
    }

    private static boolean isNumber(Object object) {
        return object != null && object.toString().matches("\\d+");
    }

    private static boolean isArray(Object object) {
        return object != null && (object instanceof Collection || object.getClass().isArray());
    }

    private static Object reflect(Object object, String key) throws InvocationTargetException, IllegalAccessException {
        // method
        Method getMethod = null;
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            if (method.getParameterCount() > 0) {
                if (methodName.equals("get")
                        && method.getParameterCount() == 1
                        && method.getParameterTypes()[0].isAssignableFrom(key.getClass())) {
                    getMethod = method;
                }
                continue;
            }
            if (methodName.equalsIgnoreCase("get" + key)) {
                return method.invoke(object);
            }
        }
        // field
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(key)) {
                return field.get(object);
            }
        }
        // get method
        if (getMethod != null) {
            return getMethod.invoke(object, key);
        }
        return null;
    }

}
