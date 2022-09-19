package com.nsu.comm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class CodeMsg {

    private int code;

    private String msg;
    /**

     * 失败

     */
    public static CodeMsg SERVER_ERROR = new CodeMsg(200, "数据类型错误");

}