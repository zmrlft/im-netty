package com.xpg.im;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MessageType {

    PRIVATE(1),
    GROUP(2),
    ERROR(-1);

    private Integer type;

    public static MessageType getMessageType(Integer type) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getType().equals(type)) {
                return messageType;
            }
        }
        return ERROR;
    }

}
