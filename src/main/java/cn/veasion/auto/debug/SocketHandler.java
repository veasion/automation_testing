package cn.veasion.auto.debug;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;

import java.util.function.BiConsumer;

/**
 * SocketHandler
 *
 * @author luozhuowei
 * @date 2020/12/25
 */
public interface SocketHandler extends BiConsumer<JSONObject, ChannelHandlerContext> {

    String getType();

}
