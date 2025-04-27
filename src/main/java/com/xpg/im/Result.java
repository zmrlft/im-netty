package com.xpg.im;

import cn.hutool.json.JSONUtil;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
public class Result {

    private String name;

    private LocalDateTime time;

    private String message;

    public static TextWebSocketFrame fail(String message) {
        return new TextWebSocketFrame(JSONUtil.toJsonStr(new Result("系统消息", LocalDateTime.now(), message)));
    }

    public static TextWebSocketFrame success(String message) {
        return new TextWebSocketFrame(JSONUtil.toJsonStr(new Result("系统消息", LocalDateTime.now(), message)));
    }

    public static TextWebSocketFrame success(String name, String message) {
        return new TextWebSocketFrame(JSONUtil.toJsonStr(new Result(name, LocalDateTime.now(), message)));
    }



}
