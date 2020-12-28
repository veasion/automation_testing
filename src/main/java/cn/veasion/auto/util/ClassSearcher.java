package cn.veasion.auto.util;

import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ClassSearcher
 *
 * @author luozhuowei
 * @date 2020/12/4
 */
public class ClassSearcher {

    private boolean allowSearchJar;

    public ClassSearcher() {
        String protocol = ClassSearcher.class.getResource("").getProtocol();
        allowSearchJar = "rsrc".equals(protocol) || "jar".equals(protocol);
    }

    @SuppressWarnings("unchecked")
    public List<Class<?>> search(String packageName, Class<?>... superType) {
        if (allowSearchJar) {
            return classSearch(packageName, superType);
        } else {
            Reflections reflections = new Reflections(packageName);
            List<Class<?>> classes = new ArrayList<>();
            for (Class<?> type : superType) {
                Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class<Object>) type);
                if (subTypesOf != null) {
                    classes.addAll(subTypesOf);
                }
            }
            return classes;
        }
    }

    private List<Class<?>> classSearch(String packageName, Class<?>[] superType) {
        String classPath = System.getProperty("java.class.path");
        String[] cps = classPath.split(";");
        List<Class<?>> classes = new ArrayList<>();
        for (String cp : cps) {
            File file = new File(cp);
            List<String> classNames = null;
            try {
                if (file.isDirectory()) {
                    classNames = searchFile(file, packageName, file.getAbsolutePath());
                } else if (allowSearchJar) {
                    classNames = searchJar(file, packageName);
                }
                if (classNames != null) {
                    for (String className : classNames) {
                        Class<?> clazz = Class.forName(className);
                        for (Class<?> superClass : superType) {
                            if (superClass.isAssignableFrom(clazz)) {
                                classes.add(clazz);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return classes;
    }

    private List<String> searchFile(File root, String packageName, String rootPath) {
        List<String> classNames = new LinkedList<>();
        File[] files = Objects.requireNonNull(root.listFiles());
        for (File file : files) {
            if (file.isDirectory()) {
                classNames.addAll(searchFile(file, packageName, rootPath));
            } else {
                if (file.getName().endsWith(".class")) {
                    String className = getClassName(file, rootPath);
                    if (className.startsWith(packageName)) {
                        classNames.add(className);
                    }
                }
            }
        }
        return classNames;
    }

    private List<String> searchJar(File jarFile, String packageName) throws IOException {
        List<String> classNames = new LinkedList<>();
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    String noSuffixName = name.substring(0, name.length() - 6);
                    String className = noSuffixName.replaceAll("[\\\\/]", ".");
                    if (className.startsWith(packageName)) {
                        classNames.add(className);
                    }
                }
            }
            return classNames;
        }
    }

    private String getClassName(File file, String rootPath) {
        String filename = file.getName();
        String noSuffixName = filename.substring(0, filename.length() - 6);

        String packagePath = file.getAbsolutePath().replace(rootPath, "").replace(filename, "");
        if (packagePath.startsWith("\\") || packagePath.startsWith("/")) {
            packagePath = packagePath.substring(1);
        }
        String packageName = packagePath.replaceAll("[\\\\/]", ".");

        return packageName + noSuffixName;
    }

}
