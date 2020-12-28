package cn.veasion.auto.util;

import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * DevRunUtils
 *
 * @author luozhuowei
 * @date 2020/9/27
 */
public class DevRunUtils {

    /**
     * 运行作者脚本
     *
     * @param scriptEngine 引擎
     * @param author       作者名称
     */
    public static void runAuthorScript(ScriptEngine scriptEngine, String author) throws Exception {
        String scriptDir = (String) scriptEngine.eval("env.getSourcePath('/script');");
        String resultDir = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "/js_" + System.currentTimeMillis();
        File resultDirFile = new File(resultDir);
        if (!resultDirFile.exists()) {
            boolean success = resultDirFile.mkdirs();
            if (!success) {
                throw new AutomationException("创建目录异常: " + resultDir);
            }
        }
        List<JsFile> files = filterAuthor(new File(scriptDir), author);
        if (files.size() > 0) {
            for (JsFile jsFile : files) {
                try {
                    runScript(scriptEngine, jsFile, resultDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static synchronized void runScript(ScriptEngine scriptEngine, JsFile jsFile, String resultDir) {
        File file = jsFile.getFile();
        String author = jsFile.getAuthor();
        boolean runError = false;
        PrintStream ps = null;
        if (!StringUtils.isEmpty(author)) {
            resultDir = resultDir + "/" + author;
            File dir = new File(resultDir);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    resultDir = resultDir.substring(0, resultDir.lastIndexOf("/"));
                }
            }
        }
        String path = resultDir + "/" + file.getName();
        String logPath = path + ".log";
        String errorPath = path + ".error.log";
        String errorImage = path + ".error.png";
        try {
            ps = new PrintStream(logPath, "UTF-8");
            System.out.println(file.getPath());
            JavaScriptUtils.setLogPrintStream(ps);
            scriptEngine.eval("runNewJs('" + file.getPath().replace("\\", "/") + "');");
        } catch (Exception e) {
            // 执行异常
            runError = true;
            if (ps != null) {
                JavascriptException exception = JavaScriptUtils.getJavaScriptException(file.getName(), e);
                if (exception.getFileName() != null) {
                    ps.println("fileName: " + exception.getFileName());
                }
                if (exception.getLineNumber() != null) {
                    ps.println("lineNumber: " + exception.getLineNumber());
                }
                ps.println("error: " + exception.getMessage());
                exception.printStackTrace(ps);
                try {
                    // 保存截图
                    scriptEngine.eval("screenshot('" + errorImage.replace("\\", "/") + "');");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            JavaScriptUtils.setLogPrintStream(null);
            if (ps != null) {
                ps.close();
            }
        }
        if (runError) {
            boolean rename = new File(logPath).renameTo(new File(errorPath));
            if (!rename) {
                System.out.println(errorPath);
            }
        }
    }

    private static List<JsFile> filterAuthor(File scriptDirectory, String author) {
        List<JsFile> files = new ArrayList<>();
        File[] list = scriptDirectory.listFiles();
        if (list == null || list.length == 0) {
            return files;
        }
        for (File file : list) {
            if (file.isDirectory()) {
                files.addAll(filterAuthor(file, author));
                continue;
            }
            if (!file.getName().endsWith(".js")) {
                continue;
            }
            FileInputStream inputStream = null;
            InputStreamReader reader = null;
            BufferedReader br = null;
            try {
                String line;
                int index;
                boolean hasAuthor = false;
                inputStream = new FileInputStream(file);
                reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {
                    if ((index = line.indexOf("@author")) > -1) {
                        hasAuthor = true;
                        String jsAuthor = line.substring(index + 7).trim();
                        if (StringUtils.isEmpty(author) || "all".equals(author)) {
                            files.add(new JsFile(file, jsAuthor));
                        } else if (author.equals(jsAuthor)) {
                            files.add(new JsFile(file, jsAuthor));
                        }
                        break;
                    }
                }
                if (!hasAuthor) {
                    System.out.println("未知作者: " + file.getName());
                }
            } catch (Exception e) {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return files;
    }

    static class JsFile {
        private File file;
        private String author;

        JsFile(File file, String author) {
            this.file = file;
            this.author = author;
        }

        public File getFile() {
            return file;
        }

        public String getAuthor() {
            return author;
        }
    }
}
