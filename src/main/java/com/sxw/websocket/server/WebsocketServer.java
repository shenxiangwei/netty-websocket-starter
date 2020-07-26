package com.sxw.websocket.server;

import com.sxw.websocket.handler.WebSocketServerHandler;
import com.sxw.websocket.listener.MessageListener;
import com.sxw.websocket.listener.MessageListenerImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Websocket 服务端
 *
 * create by shenxiangwei on 2020/7/25 下午 2:54
 */
@Configuration
public class WebsocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Value("${websocket.port:8090}")
    private int port;

    private MessageListener messageListener;

    public WebsocketServer(MessageListener messageListener) {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.messageListener = messageListener;
    }

    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("http-codec", new HttpServerCodec());
                        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                        WebSocketServerHandler webSocketServerHandler = new WebSocketServerHandler();
                        webSocketServerHandler.setMessageListener(messageListener);
                        pipeline.addLast("handler", webSocketServerHandler);
                    }
                });
        b.bind(port).sync().channel();
        logger.info("websocket服务启动，端口：" + port);
    }

    public static void main(String[] args) throws Exception {
        int port = 8090;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new WebsocketServer(new MessageListenerImpl()).run(port);
    }

    @PostConstruct
    private void init() throws Exception {
        this.run(port);
    }

    @PreDestroy
    private void destroy(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
