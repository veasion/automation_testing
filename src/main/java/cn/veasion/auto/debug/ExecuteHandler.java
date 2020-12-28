package cn.veasion.auto.debug;

import com.alibaba.fastjson.JSONObject;
import cn.veasion.auto.util.JavaScriptUtils;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * ExecuteHandler
 *
 * @author luozhuowei
 * @date 2020/8/2
 */
public class ExecuteHandler implements SocketHandler {

    private static final String TYPE = "execute";
    private static final String ERROR_TYPE = "executeError";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void accept(JSONObject jsonObject, ChannelHandlerContext ctx) {
        String jsCode = jsonObject.getString("data");
        if (!StringUtils.isEmpty(jsCode)) {
            try {
                Object result = Objects.requireNonNull(Debug.getJavaScriptContext()).execute(jsCode);
                if (result != null) {
                    JavaScriptUtils.println(result);
                }
                ctx.channel().writeAndFlush(Debug.newSocketResult(jsonObject, TYPE, result));
            } catch (Exception e) {
                ctx.channel().writeAndFlush(Debug.newSocketResult(jsonObject, ERROR_TYPE, e.getMessage()));
            }
        }
    }
}
