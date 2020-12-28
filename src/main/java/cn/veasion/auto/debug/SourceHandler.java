package cn.veasion.auto.debug;

import com.alibaba.fastjson.JSONObject;
import cn.veasion.auto.util.Constants;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * SourceHandler
 *
 * @author luozhuowei
 * @date 2020/8/2
 */
public class SourceHandler implements SocketHandler {

    private static final String TYPE = "source";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void accept(JSONObject jsonObject, ChannelHandlerContext ctx) {
        try {
            Object filePath = Objects.requireNonNull(Debug.getJavaScriptContext()).getValue(Constants.ENV_GLOBAL_FILE_PATH);
            if (filePath == null) return;
            File file = new File(filePath.toString());
            if (!file.exists() || !file.isFile()) return;
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()), StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            ctx.channel().writeAndFlush(Debug.newSocketResult(jsonObject, TYPE, sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
