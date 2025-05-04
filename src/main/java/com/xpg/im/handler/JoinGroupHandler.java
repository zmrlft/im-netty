package com.xpg.im.handler;

import com.xpg.im.Command;
import com.xpg.im.ImServer;
import com.xpg.im.Result;
import io.netty.channel.ChannelHandlerContext;

public class JoinGroupHandler {

    public static void execute(ChannelHandlerContext ctx, Command command) {

        if (!ImServer.USERS.containsKey(command.getName())) {
            ctx.channel().writeAndFlush(Result.fail("用户还未上线，无法加入群聊"));
            System.out.println("用户还未上线，无法加入群聊");
            return;
        }

        if (ImServer.GROUP.contains(ctx.channel())) {
            ctx.channel().writeAndFlush(Result.fail("已经加入系统默认群聊"));
            return;
        }

        ImServer.GROUP.add(ctx.channel());
        ctx.channel().writeAndFlush(Result.success("加入系统默认群聊成功"));
        System.out.println("加入系统默认群聊成功");
    }
}
