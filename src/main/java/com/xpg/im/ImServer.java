package com.xpg.im;

import com.xpg.im.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器启动类
 */
public class ImServer {

    public static final Map<String, Channel> USERS = new ConcurrentHashMap<>(1024);

    // 创建一个ChannelGroup，实现群聊功能
    public static final ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void start() throws Exception {
        // 创建两个EventLoopGroup实例，bossGroup用于处理连接请求，workerGroup用于处理已连接客户端的读写操作
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建ServerBootstrap实例，这是一个启动引导配置，用于窜连相关组件及其配置
        ServerBootstrap bootStart = new ServerBootstrap();
        // 设置EventLoopGroup，指定NioServerSocketChannel作为通道类型
        bootStart.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 设置通道初始化器，当有新的连接时，会调用此初始化器
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 重写initChannel方法，用于初始化通道
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 获取通道的pipeline，pipeline是处理通道事件的处理器链
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 添加HttpServerCodec处理器，用于处理HTTP编码和解码
                        pipeline.addLast(new HttpServerCodec())
                                // 添加ChunkedWriteHandler处理器，用于处理大数据流的写操作
                                .addLast(new ChunkedWriteHandler())
                                // 添加HttpObjectAggregator处理器，用于将多个HTTP消息聚合为一个完整的消息
                                .addLast(new HttpObjectAggregator(1024 * 24))
                                // 添加WebSocketServerProtocolHandler处理器，用于处理WebSocket协议的握手和数据帧
                                .addLast(new WebSocketServerProtocolHandler("/"))
                                // 添加自定义的WebSocketHandler处理器，用于处理WebSocket消息
                                .addLast(new WebSocketHandler());
                    }
                });

        // 绑定服务器到指定端口（8888），阻塞进程
        ChannelFuture future = bootStart.bind(8888).sync();
    }
}
