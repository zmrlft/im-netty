package com.xpg.im.command;

import com.xpg.im.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage extends Command {

    /**
     * 命令类型
     */
    private Integer type;

    /**
     * 目标(消息接受人)
     */
    private String target;

    /**
     * 消息内容
     */
    private String content;
}
