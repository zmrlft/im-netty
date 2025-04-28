package com.xpg.im.handler;

import cn.hutool.json.JSONUtil;
import com.xpg.im.Command;
import com.xpg.im.CommandType;
import com.xpg.im.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame frame) throws Exception {

        System.out.println("receive message"+frame.text());

        try {

            Command command = JSONUtil.toBean(frame.text(), Command.class);
            System.out.println("command:"+command);
            switch (CommandType.getByCode(command.getCode())) {
                case CONNECT:
                    System.out.println("connect");
                    ConnectionHandler.execute(channelHandlerContext, command);
                    break;
                case CHAT:
                    System.out.println("chat");
                    ChatHandler.execute(channelHandlerContext, frame);
                    break;
                case ERROR:
                    System.out.println("error");
                    channelHandlerContext.channel().writeAndFlush(Result.fail("error"));
                    break;
                default:
                    System.out.println("default");
                    channelHandlerContext.channel().writeAndFlush(Result.fail("unknown command"));
            }

        }catch (Exception e) {

            channelHandlerContext.channel().writeAndFlush(Result.fail(e.getMessage()));
            System.out.println("error:"+e.getMessage());

        }



    }

}

