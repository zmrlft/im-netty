package com.xpg.im;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data //如果用了hutool库的JSONUtil.toBean()方法，得加这个注解，之前写成了@Getter老是报错Can not Converte
public class Command {

    //指令
    private Integer code;

    //昵称
    private String name;

    //测试数据
    //{"code":10001,"name":"a"}
}
