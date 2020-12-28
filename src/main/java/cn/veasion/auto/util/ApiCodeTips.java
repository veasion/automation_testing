package cn.veasion.auto.util;

import cn.veasion.auto.bind.JavaScriptBinding;
import cn.veasion.auto.bind.SearchContextBinding;
import cn.veasion.auto.bind.WebDriverBinding;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ApiCodeTips
 *
 * @author luozhuowei
 * @date 2020/8/3
 */
public class ApiCodeTips {

    private static final Set<Tips> menus = new HashSet<>();
    private static final Map<Character, Set<Tips>> rootTipsCache = new HashMap<>();
    private static final Map<String, Set<Tips>> varTipsCache = new HashMap<>();
    private static final Map<String, Set<Tips>> methodTipsCache = new HashMap<>();
    private static final Map<String, Set<Tips>> classTipsCache = new HashMap<>();

    public static void asynInit() {
        new Thread(ApiCodeTips::init).start();
    }

    public synchronized static void init() {
        List<Tips> list = initScanner();
        for (Tips tips : list) {
            if (tips.joinMenu) {
                menus.add(tips);
            }
            if (tips.methodName != null) {
                put(methodTipsCache, tips.methodName, tips);
            }
            if (tips.var != null && !"".equals(tips.var)) {
                put(varTipsCache, tips.var, tips);
            }
            if (tips.className != null) {
                put(classTipsCache, tips.className, tips);
            }
            if (tips.root && tips.methodName != null) {
                put(rootTipsCache, tips.methodName.charAt(0), tips);
            }
        }
    }

    private static <T> void put(Map<T, Set<Tips>> map, T key, Tips value) {
        map.compute(key, (k, v) -> {
            if (v == null) {
                v = new HashSet<>();
            }
            v.add(value);
            return v;
        });
    }

    private static List<Tips> initScanner() {
        List<Tips> list = new ArrayList<>(200);
        // java
        String packageName = JavaScriptBinding.class.getPackage().getName();
        List<Class<?>> classes = new ClassSearcher().search(packageName, JavaScriptBinding.class);
        for (Class<?> clazz : classes) {
            if (JavaScriptBinding.class.isAssignableFrom(clazz) && (clazz.getModifiers() & Modifier.ABSTRACT) == 0) {
                Api.ClassInfo classInfo = clazz.getAnnotation(Api.ClassInfo.class);
                boolean root = classInfo != null && classInfo.root();
                String var = classInfo != null ? classInfo.value() : null;
                list.addAll(buildTips(clazz, var, root));
            }
        }
        // include js
        String includePath = JavaScriptUtils.getFilePath("include");
        scannerJsFiles(list, includePath);
        return list;
    }

    public static Set<Tips> search(String code, SearchCallback callback) {
        if (code == null || "".equals(code)) {
            return rootTipsCache.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        }
        char lastChar = code.charAt(code.length() - 1);
        if (";),+-*/~!^%#${}[/\\<>".contains(String.valueOf(lastChar))) {
            callback.skip = true;
            return null;
        }
        int index = code.indexOf(".");
        if (index != -1) {
            // auto. & findOne(). & findOne().f
            String d1 = code.substring(0, index);
            String d2 = code.substring(index + 1);
            if ("".equals(d1)) return null;
            if (d1.endsWith(")")) {
                int f1 = d1.lastIndexOf("(");
                if (f1 == -1) return null;
                return searchReturn(d1.substring(0, f1), d2, callback);
            } else {
                Set<Tips> tips = varTipsCache.get(d1);
                return filterTips(tips, d2);
            }
        } else {
            Set<Tips> tips = new HashSet<>();
            // var
            varTipsCache.keySet().stream().filter(k -> k.startsWith(code)).forEach(var -> {
                Tips t = new Tips();
                t.methodName = var;
                t.codeTips = var;
                tips.add(t);
            });
            // root
            Set<Tips> rootTips = rootTipsCache.get(code.charAt(0));
            if (rootTips != null) {
                rootTips = filterTips(rootTips, code);
                if (rootTips != null) {
                    tips.addAll(rootTips);
                }
            }
            return tips;
        }
    }

    public static Set<Tips> searchReturn(String returnMethodName, String search, SearchCallback callback) {
        Set<Tips> tips = methodTipsCache.get(returnMethodName);
        if (tips == null) return null;
        Iterator<Tips> iterator = tips.iterator();
        while (iterator.hasNext()) {
            String resultClassName = iterator.next().resultClassName;
            if (resultClassName != null && !"".equals(resultClassName)) {
                if (callback != null) {
                    callback.resultClassName = resultClassName;
                }
                Set<Tips> classTips = classTipsCache.get(resultClassName);
                return filterTips(classTips, search);
            }
        }
        return null;
    }

    private static Set<Tips> filterTips(Set<Tips> tips, String startsWith) {
        if (tips != null) {
            if (startsWith == null || "".equals(startsWith)) {
                return tips;
            } else {
                return tips.stream().filter(o -> o.methodName.startsWith(startsWith)).collect(Collectors.toSet());
            }
        }
        return null;
    }

    private static List<Tips> buildTips(Class<?> clazz, String var, boolean root) {
        Method[] methods = clazz.getMethods();
        List<Tips> list = new ArrayList<>(methods.length);
        StringBuilder sb = new StringBuilder();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (method.getDeclaringClass().equals(Object.class)) {
                continue;
            }
            Api document = method.getAnnotation(Api.class);
            if (document != null && !document.generator()) {
                continue;
            }
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !ApiDocumentGenerator.isObjectMethod(method)) {
                String methodName = method.getName();
                String[] parameterNames = ApiDocumentGenerator.localVarParam.getParameterNames(method);
                Tips tips = new Tips();
                tips.api = document;
                tips.var = var;
                tips.root = root;
                tips.isJs = false;
                tips.methodName = methodName;
                sb.setLength(0);
                sb.append(methodName).append("(");
                ApiDocumentGenerator.appendParams(sb, parameterNames, method.getParameterCount());
                sb.append(")");
                tips.codeTips = sb.toString();
                if (method.getDeclaringClass().equals(SearchContextBinding.class) && clazz.equals(WebDriverBinding.class)) {
                    sb.setLength(0);
                    sb.append(methodName).append("(");
                    ApiDocumentGenerator.appendParams(sb, parameterNames, method.getParameterCount(), true);
                    sb.append(")");
                    tips.joinMenu = true;
                    tips.menuCode = sb.toString().replace("\"target\"", "$").replace("\"targets\"", "[$]");
                }
                tips.className = clazz.getSimpleName();
                tips.resultClassName = ApiDocumentGenerator.getReturnType(clazz, method, document).replace("{", "").replace("}", "");
                list.add(tips);
            }
        }
        return list;
    }

    private static void scannerJsFiles(List<Tips> list, String includePath) {
        File[] files = new File(includePath).listFiles(name -> name.getName().endsWith(".js"));
        for (File file : files) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(file.getPath()), Charset.forName("UTF-8"));
                for (String line : lines) {
                    if (line.matches(".*=\\s+function\\s+\\(.*")) {
                        int function = line.indexOf("function");
                        String varCode = line.substring(0, function).replace("=", "").trim();
                        Tips tips = new Tips();
                        if (varCode.contains(".")) {
                            String[] split = varCode.split("\\.");
                            tips.var = split[0];
                            tips.methodName = split[1];
                        } else {
                            tips.methodName = varCode;
                        }
                        tips.codeTips = tips.methodName;
                        tips.isJs = true;
                        list.add(tips);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Tips> getMenusTips() {
        return new ArrayList<>(menus);
    }

    public static List<Tips> getClassTips(String className) {
        Set<Tips> tips = classTipsCache.get(className);
        return tips != null ? new ArrayList<>(tips) : null;
    }

    public static class Tips {
        public Api api;
        public String var;
        public boolean isJs;
        public boolean root;
        public boolean joinMenu;
        public String codeTips;
        public String menuCode;
        public String methodName;
        public String className;
        public String resultClassName;
    }

    public static class SearchCallback {
        public boolean skip;
        public String resultClassName;
    }

}
