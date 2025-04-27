package com.xpg.im.handler;

import cn.hutool.json.JSONUtil;
import com.xpg.im.Command;
import com.xpg.im.ImServer;
import com.xpg.im.Result;
import io.netty.channel.ChannelHandlerContext;


public class ConnectionHandler {

    public static void execute(ChannelHandlerContext ctx, Command command) {

        if (ImServer.USERS.containsKey(command.getName())) {
            ctx.channel().writeAndFlush(Result.fail("用户名已存在"));
            ctx.channel().close();
            return;
        }

        ImServer.USERS.put(command.getName(), ctx.channel());
        ctx.channel().writeAndFlush(Result.success("与服务端连接成功"));
        ctx.channel().writeAndFlush(Result.success(JSONUtil.toJsonStr(ImServer.USERS.keySet())));

        System.out.println("connect success");
        System.out.println("users:"+ImServer.USERS.keySet());
    }
}
