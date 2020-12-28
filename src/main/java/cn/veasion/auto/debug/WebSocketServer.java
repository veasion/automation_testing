package cn.veasion.auto.debug;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.util.function.BiConsumer;

/**
 * WebSocketServer
 *
 * @author luozhuowei
 * @date 2020/8/2
 */
public class WebSocketServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    private WebSocketServer createBootstrap(String websocketPath, int maxFrameSize, BiConsumer<String, ChannelHandlerContext> handler) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                // .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // pipeline.addLast(new IdleStateHandler(600, 600, 600));
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(maxFrameSize));
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new CorsHandler(CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build()));
                        pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath, null, true, maxFrameSize));
                        pipeline.addLast(new WebSocketChannelHandler(handler));
                    }
                });
        return this;
    }

    public void bind(int port, String websocketPath, int maxFrameSize, BiConsumer<String, ChannelHandlerContext> handler) throws InterruptedException {
        if (bootstrap == null) {
            createBootstrap(websocketPath, maxFrameSize, handler);
        }
        bootstrap.bind(port).sync();
    }

    public void close() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

}
