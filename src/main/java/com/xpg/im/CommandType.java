package com.xpg.im;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandType {

    /**
     * 连接
     */
    CONNECT(10001),

    /**
     * 聊天
     */
    CHAT(10002),

    /**
     * 加入群聊
     */
    JOIN_GROUP(10003),


    ERROR(-1),

    ;

    private final Integer code;

    public static CommandType getByCode(Integer code) {
        //TODO 太多用MAP优化
        for (CommandType commandType : CommandType.values()) {
            if (commandType.getCode().equals(code)) {
                return commandType;
            }
        }
        return ERROR;
    }


}
