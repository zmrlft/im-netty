package com.xpg.im.handler;


import cn.hutool.json.JSONUtil;
import com.xpg.im.ImServer;
import com.xpg.im.MessageType;
import com.xpg.im.Result;
import com.xpg.im.command.ChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

public class ChatHandler {

    public static void execute(ChannelHandlerContext ctx, TextWebSocketFrame frame) {

        try{
            ChatMessage chat = JSONUtil.toBean(frame.text(), ChatMessage.class);
            System.out.println("receive chat"+chat);
            switch (MessageType.getMessageType(chat.getType())){
                case PRIVATE:
                    if (StringUtil.isNullOrEmpty(chat.getTarget())) {
                        ctx.channel().writeAndFlush(Result.fail("目标为空,请指定消息接受的目标对象"));
                        return;
                    }
                    Channel channel = ImServer.USERS.get(chat.getTarget());
                    if (channel == null || !channel.isActive()) {
                        ctx.channel().writeAndFlush(Result.fail("目标用户" + chat.getTarget() + "不在"));
                        System.out.println("error:"+"目标用户" + chat.getTarget() + "不在");
                        return;
                    } else {
                        channel.writeAndFlush(Result.success("私聊消息（" + chat.getName() + "）",chat.getContent()));
                        System.out.println("send private message to " + chat.getTarget() + ":" + chat.getContent());
                        //测试{"code":10002,"name":"b","type":"1","target":"a","content":"hi"}
                    }
                    break;
                default:
                    ctx.channel().writeAndFlush(Result.fail("不支持的消息类型"));

            }
        }catch(Exception e){
            ctx.channel().writeAndFlush(Result.fail("发送数据格式错误"));
            System.out.println("error:"+e.getMessage());
        }
    }
}
