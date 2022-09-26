package com.nsu.comm;

public enum ResultCodeEnum {

    SUCCESS(0,"成功"),
    Fail(-1,"失败"),

    AuthFailed(-1004,"认证失败"),
    NoAuth(-1002,"无权限"),

    NoLogin(-1001,"未登录");

    //自定义信息 从-4001开始

    private Integer code;

    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
