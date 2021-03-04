package cn.veasion.auto.bind.bean;

import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JavaScriptUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * FileBean
 *
 * @author luozhuowei
 * @date 2021/3/2
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "file", desc = "文件")
public class FileBean extends AbstractInitializingBean {

    @Api("写文本文件")
    @ResultProxy(value = false, log = false)
    public void writeText(String path, String context, boolean append) throws IOException {
        writeText(path, context, append, null);
    }

    @Api("写文本文件")
    @ResultProxy(value = false, log = false)
    public void writeText(String path, String context, boolean append, @Api.Param(allowNone = true) String charsetName) throws IOException {
        StandardOpenOption[] options;
        Path filePath = Paths.get(path);
        if (append) {
            options = new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND};
        } else {
            Files.deleteIfExists(filePath);
            options = new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE};
        }
        charsetName = JavaScriptUtils.isEmpty(charsetName) ? "UTF-8" : charsetName;
        Files.write(filePath, context.getBytes(charsetName), options);
    }

    @Api("读取文本")
    @ResultProxy(value = false, log = false)
    public String readText(String pathOrUrl) throws IOException {
        return readText(pathOrUrl, null);
    }

    @Api("读取文本")
    @ResultProxy(value = false, log = false)
    public String readText(String pathOrUrl, @Api.Param(allowNone = true) String charsetName) throws IOException {
        InputStream inputStream;
        charsetName = JavaScriptUtils.isEmpty(charsetName) ? "UTF-8" : charsetName;
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            inputStream = new URL(pathOrUrl).openStream();
        } else {
            inputStream = new FileInputStream(pathOrUrl);
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            return sb.toString();
        } finally {
            inputStream.close();
        }
    }

}
