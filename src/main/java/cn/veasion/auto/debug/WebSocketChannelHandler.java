package cn.veasion.auto.debug;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * WebSocketHandler
 *
 * @author luozhuowei
 * @date 2020/8/2
 */
public class WebSocketChannelHandler extends SimpleChannelInboundHandler<WebSocketFrame> implements ChannelOutboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelHandler.class);

    private static final MessageToMessageEncoder<String> encoder;
    private BiConsumer<String, ChannelHandlerContext> handler;

    static {
        encoder = new MessageToMessageEncoder<String>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, String message, List<Object> out) {
                out.add(new TextWebSocketFrame(message));
            }
        };
    }

    public WebSocketChannelHandler(BiConsumer<String, ChannelHandlerContext> handler) {
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocket) {
        String message = webSocket.content().toString(CharsetUtil.UTF_8);
        if (logger.isDebugEnabled()) {
            logger.debug("websocket入站：{}", message);
        }
        handler.accept(message, ctx);
        ctx.fireChannelRead(message);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        encoder.bind(ctx, socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) throws Exception {
        encoder.connect(ctx, socketAddress, socketAddress1, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise channelPromise) throws Exception {
        encoder.disconnect(ctx, channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise channelPromise) throws Exception {
        encoder.close(ctx, channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise channelPromise) throws Exception {
        encoder.deregister(ctx, channelPromise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        encoder.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object o, ChannelPromise channelPromise) throws Exception {
        encoder.write(ctx, o, channelPromise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        encoder.flush(ctx);
    }
}
