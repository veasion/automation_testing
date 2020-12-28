package cn.veasion.auto.util;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JavaScriptUtils
 *
 * @author luozhuowei
 * @date 2020/6/11
 */
@SuppressWarnings({"unused", "restriction"})
public class JavaScriptUtils {

    private static PrintStream logPrintStream = null;
    private static PrintStream systemOutput = System.out;

    public static void setLogPrintStream(PrintStream ps) {
        // JavaScriptUtils.logPrintStream = ps;
        System.setOut(ps != null ? new PrintStream(new MultipleStream(ps, systemOutput)) : systemOutput);
    }

    public static <T> List<T> toArray(ScriptObjectMirror o, Class<T> c) {
        if (o == null || o.isFunction()) {
            return null;
        }
        if (o.isEmpty()) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(JSONObject.toJSONString(o.values()), c);
    }

    public static <T> T toObject(ScriptObjectMirror o, Class<T> c) {
        if (o == null || o.isFunction()) {
            return null;
        }
        if (o.isArray()) {
            throw new ClassCastException("object is array");
        } else {
            return JSONObject.parseObject(JSONObject.toJSONString(o), c);
        }
    }

    public static Object toJavaObject(Object obj) {
        if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) obj;
            if (mirror.isFunction()) {
                return mirror;
            }
            if (mirror.isArray()) {
                List<Object> list = new ArrayList<>(mirror.size());
                for (Object value : mirror.values()) {
                    list.add(toJavaObject(value));
                }
                return list;
            } else {
                Map<String, Object> map = new HashMap<>(mirror.size());
                for (String key : mirror.keySet()) {
                    map.put(key, toJavaObject(mirror.get(key)));
                }
                return map;
            }
        }
        return obj;
    }

    public static Type getActualType(Class<?> clazz) {
        Type superClass = clazz.getGenericSuperclass();
        if (superClass != null && superClass.getTypeName().endsWith(">")) {
            return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        Type[] interfaces = clazz.getGenericInterfaces();
        for (Type inf : interfaces) {
            if (inf.getTypeName().endsWith(">")) {
                return ((ParameterizedType) inf).getActualTypeArguments()[0];
            }
        }
        return null;
    }

    public static String getFilePath(String path) {
        return JavaScriptUtils.class.getResource("/").getPath() + path;
    }

    public static JavascriptException getJavaScriptException(Exception e) {
        return getJavaScriptException(null, e);
    }

    public static JavascriptException getJavaScriptException(String fileName, Exception e) {
        Throwable throwable = e;
        if (e instanceof RuntimeException && e.getCause() != null) {
            throwable = e.getCause();
        }
        if (throwable instanceof InvocationTargetException) {
            throwable = ((InvocationTargetException) throwable).getTargetException();
        }
        return new JavascriptException(fileName, throwable != null ? throwable : e);
    }

    public static boolean isNull(Object object) {
        return object == null || "undefined".equals(String.valueOf(object));
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || "".equals(str);
    }

    public static String log(String name, Object[] args) {
        StringBuilder message = new StringBuilder();
        if (args != null && args.length > 0) {
            message.append("execute => ").append(name).append("(");
            try {
                for (Object arg : args) {
                    appendObject(message, arg);
                    message.append(", ");
                }
                message.setLength(message.length() - 2);
            } catch (Exception e) {
                message.append("...");
            }
            message.append(")");
        } else {
            message.append("execute => ").append(name).append("()");
        }
        if (logPrintStream != null) {
            logPrintStream.println(message.toString());
        }
        return message.toString();
    }

    public static void appendObject(StringBuilder sb, Object obj) {
        if (obj instanceof String) {
            if (((String) obj).indexOf("\n") > 0) {
                sb.append("...");
            } else {
                sb.append("\"").append(obj).append("\"");
            }
        } else if (obj instanceof ScriptFunction) {
            sb.append("[function]");
        } else if (obj instanceof NativeArray) {
            appendObject(sb, ((NativeArray) obj).asObjectArray());
        } else if (obj instanceof Object[]) {
            sb.append("[");
            Object[] array = (Object[]) obj;
            for (int i = 0; i < array.length; i++) {
                appendObject(sb, array[i]);
                if (i < array.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        } else if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror) obj;
            if (mirror.isArray()) {
                appendObject(sb, mirror.values().toArray());
            } else if (mirror.isFunction()) {
                sb.append("[function]");
            } else {
                sb.append("{");
                for (String key : mirror.keySet()) {
                    sb.append(key).append(": ");
                    appendObject(sb, mirror.get(key));
                    sb.append(", ");
                }
                if (mirror.keySet().size() > 0) {
                    sb.setLength(sb.length() - 2);
                }
                sb.append("}");
            }
        } else if (obj instanceof ScriptObject && obj.getClass().getSimpleName().startsWith("JO")) {
            sb.append("{");
            Property[] properties = ((ScriptObject) obj).getMap().getProperties();
            for (int i = 0; i < properties.length; i++) {
                sb.append(" ").append(properties[i].getKey()).append(": ");
                appendObject(sb, properties[i].getObjectValue((ScriptObject) obj, null));
                if (i < properties.length - 1) {
                    sb.append(", ");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("}");
        } else {
            sb.append(obj);
        }
    }

    public static void println(Object... args) {
        if (args == null || args.length == 0) {
            System.out.println();
        } else if (args.length == 1) {
            System.out.println(args[0]);
        } else {
            for (Object message : args) {
                System.out.println(message);
            }
        }
    }

    private static class MultipleStream extends OutputStream {
        private PrintStream[] printStreams;

        MultipleStream(PrintStream... printStreams) {
            this.printStreams = printStreams;
        }

        public void flush() {
            for (PrintStream ps : printStreams) {
                ps.flush();
            }
        }

        public void write(final byte[] b) throws IOException {
            for (PrintStream ps : printStreams) {
                ps.write(b);
            }
        }

        public void write(final byte[] b, final int off, final int len) {
            for (PrintStream ps : printStreams) {
                ps.write(b, off, len);
            }
        }

        public void write(final int b) {
            for (PrintStream ps : printStreams) {
                ps.write(b);
            }
        }
    }

}
