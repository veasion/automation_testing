package cn.veasion.auto.util;

import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.core.BindingFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * JavaScript 引擎代码提示生成.
 *
 * <pre>
 *    IDEA 添加代码提示方法：
 *    Settings -> Languages & Frameworks -> JavaScript -> Libraries
 *    添加(Add):
 *      Name: driver.js
 *      Framework type: Prototype    Version: 1.0
 *      Visibility: Project
 *      +: Attach Files => src\main\resources\driver.js
 * </pre>
 *
 * @author luozhuowei
 * @date 2020/6/11
 */
public class ApiDocumentGenerator {

    private static final String LINE = "\r\n";
    private static final String SPACE_T = "    ";
    static final LocalVariableTableParameterNameDiscoverer localVarParam;
    private static final Set<String> JS_NATIVE_PARAMS = new HashSet<>(Arrays.asList("function", "undefined", "typeof", "void"));

    static {
        localVarParam = new LocalVariableTableParameterNameDiscoverer();
    }

    public static void main(String[] args) throws Exception {
        StringBuilder document = new StringBuilder();
        initDefaultDocument(document);
        String packageName = JavaScriptBinding.class.getPackage().getName();
        List<Class<?>> classes = new ClassSearcher().search(packageName, JavaScriptBinding.class);
        for (Class<?> clazz : classes) {
            if (JavaScriptBinding.class.isAssignableFrom(clazz) && (clazz.getModifiers() & Modifier.ABSTRACT) == 0) {
                generator(document, clazz);
            }
        }
        String abstractPath = "src\\main\\resources\\driver.js";
        String path = new File("").getCanonicalPath() + "\\" + abstractPath;
        File file = new File(path);
        write(file, document.toString());
        System.out.println("\n代码提示文档生成完成 ==> " + file.getName());
    }

    public static void generator(StringBuilder document, Class<?> clazz) {
        Api.ClassInfo classInfo = clazz.getAnnotation(Api.ClassInfo.class);
        boolean root = classInfo != null && classInfo.root();
        String var = classInfo != null ? classInfo.value() : null;
        if (root) {
            document.append(generator(clazz, null, true)).append(LINE);
        }
        document.append(generator(clazz, var, false)).append(LINE);
    }

    private static String generator(Class<?> clazz, String var, boolean global) {
        Method[] methods = clazz.getMethods();
        StringBuilder sb = new StringBuilder();
        if (!global) {
            sb.append("function ").append(clazz.getSimpleName()).append("() {").append(LINE);
        }
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            int modifiers = method.getModifiers();
            Class<?> declaringClass = method.getDeclaringClass();
            if (declaringClass.equals(Object.class)) {
                continue;
            }
            Api document = method.getAnnotation(Api.class);
            if (document != null && !document.generator()) {
                continue;
            }
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !isObjectMethod(method)) {
                sb.append(jsMethod(clazz, method, global));
            }
        }
        if (!global) {
            sb.append("}").append(LINE);
            if (var != null && !StringUtils.isEmpty(var)) {
                sb.append(LINE).append("const ").append(var).append(" = new ");
                sb.append(clazz.getSimpleName()).append("();").append(LINE);
            }
        }
        return sb.toString();
    }

    static boolean isObjectMethod(Method method) {
        for (Method m : Object.class.getMethods()) {
            if (m == method || m.getName().equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    private static String jsMethod(Class<?> selfClass, Method method, boolean root) {
        String methodName = method.getName();
        String[] parameterNames = getParameterNames(method);
        Api document = method.getAnnotation(Api.class);
        String methodDoc = document != null ? document.value() : methodName;
        String returnType = getReturnType(selfClass, method, document);
        int paramCount = method.getParameterCount();
        StringBuilder jsCode = new StringBuilder(root ? "" : SPACE_T);
        jsCode.append("/**").append(lineSpace(root));
        jsCode.append(" * ").append(methodDoc).append("<br>").append(lineSpace(root));
        jsCode.append(" * <code>").append(method.getDeclaringClass().getName()).append("#");
        jsCode.append(methodName).append("</code>").append(lineSpace(root));
        Parameter[] parameters = method.getParameters();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterType = parameterTypes[i];
            Api.Param param = parameter.getAnnotation(Api.Param.class);
            String desc = null;
            if (param != null && !StringUtils.isEmpty(param.desc())) {
                desc = param.desc();
            }
            String paramType;
            if (param != null && !StringUtils.isEmpty(param.jsType())) {
                paramType = "{" + param.jsType() + "}";
            } else if (param != null && !Object.class.equals(param.type()) && Collection.class.isAssignableFrom(parameterType)) {
                paramType = jsTypeArray(param.type());
            } else {
                paramType = jsType(parameterType);
            }
            if (param != null && paramType.contains("}")) {
                if (param.allowNone()) {
                    paramType = paramType.replace("}", "?}");
                } else if (param.allowNull()) {
                    paramType = paramType.replace("}", "|null}");
                }
            }
            jsCode.append(" * @param ").append(paramType).append(" ");
            jsCode.append(parameterNames[i]);
            if (desc != null) {
                jsCode.append(" ").append(desc);
            }
            jsCode.append(lineSpace(root));
        }
        jsCode.append(" * @return ").append(returnType).append(lineSpace(root));
        jsCode.append(" */").append(lineSpace(root));
        if (root) {
            jsCode.append("function ").append(methodName).append("(");
        } else {
            jsCode.append("this.").append(methodName).append(" = function(");
        }
        appendParams(jsCode, parameterNames, paramCount);
        jsCode.append(") { }").append(LINE);
        return jsCode.toString();
    }

    static String getReturnType(Class<?> selfClass, Method method, Api document) {
        Class<?> returnTypeClass = method.getReturnType();
        String returnType = jsType(returnTypeClass);
        if (!method.getDeclaringClass().equals(selfClass) && returnTypeClass.isAssignableFrom(selfClass)) {
            returnType = jsType(selfClass);
        }
        if (document != null) {
            Class<?> result = document.result();
            if (JavaScriptBinding.class.isAssignableFrom(result)) {
                if (Collection.class.isAssignableFrom(returnTypeClass)) {
                    returnType = jsTypeArray(result);
                } else {
                    returnType = jsType(result);
                }
            } else if (!result.equals(Object.class) && BindingFactory.convertClass(result) != null) {
                if (Collection.class.isAssignableFrom(returnTypeClass)) {
                    returnType = jsTypeArray(BindingFactory.convertClass(result));
                } else {
                    returnType = jsType(BindingFactory.convertClass(result));
                }
            }
        }
        return returnType;
    }

    private static String[] getParameterNames(Method method) {
        String[] parameterNames = localVarParam.getParameterNames(method);
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (JS_NATIVE_PARAMS.contains(parameterNames[i])) {
                    parameterNames[i] = parameterNames[i] + "Param";
                }
            }
        } else {
            parameterNames = new String[]{};
        }
        return parameterNames;
    }

    private static String lineSpace(boolean root) {
        return root ? LINE : LINE + SPACE_T;
    }

    static void appendParams(StringBuilder jsCode, String[] parameterNames, int paramCount) {
        appendParams(jsCode, parameterNames, paramCount, false);
    }

    static void appendParams(StringBuilder jsCode, String[] parameterNames, int paramCount, boolean quot) {
        String split = ", ";
        for (int i = 0; i < paramCount; i++) {
            if (quot) {
                jsCode.append("\"").append(parameterNames[i]).append("\"");
            } else {
                jsCode.append(parameterNames[i]);
            }
            jsCode.append(split);
        }
        if (paramCount > 0) {
            jsCode.setLength(jsCode.length() - split.length());
        }
    }

    private static void write(File file, String content) throws Exception {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static String jsTypeArray(Class<?> clazz) {
        String type = jsType(clazz);
        if (type.endsWith("[]}")) {
            return type;
        } else {
            return type.replace("}", "[]}");
        }
    }

    private static String jsType(Class<?> clazz) {
        if (String.class.equals(clazz)) {
            return "{string}";
        } else if (Boolean.class.equals(clazz)) {
            return "{boolean}";
        } else if (Number.class.isAssignableFrom(clazz)) {
            return "{number}";
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return "{array}";
        } else if (Object.class.equals(clazz)) {
            return "{object}";
        } else if (Object[].class.equals(clazz)) {
            return "{array}";
        } else if (JavaScriptBinding.class.isAssignableFrom(clazz)) {
            return "{" + clazz.getSimpleName() + "}";
        }
        String name = clazz.getName();
        if ("void".equals(name)) {
            return name;
        } else if ("[Ljava.lang.String;".equals(name)) {
            return "{string[]}";
        } else if ("[Ljava.lang.Object;".equals(name)) {
            return "{object[]}";
        } else if ("long".equals(name) || "int".equals(name) || "double".equals(name) || "float".equals(name)) {
            return "{number}";
        } else {
            return "{" + clazz.getSimpleName() + "}";
        }
    }

    private static void initDefaultDocument(StringBuilder document) {
        Arrays.asList("load(url)", "loadWithNewGlobal(url)").forEach(fun -> document.append("function ").append(fun).append(" {}").append(lineSpace(true)));
        document.append(lineSpace(true));
    }

}
