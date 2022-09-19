package com.nsu.comm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
  /**
     * 返回状态码 除200其余全部失败
     */
    private int code;

    /**
     * 返回信息 除success其余全部失败
     */

    private String msg;
    /**
     * 泛型数据
     */
    private T data;
    /**
     * 成功时返回的类型
     *
     * @param data 数据
     * @return 泛型数据
     */
    public Result success(T data) {
        return new Result<>(200, "success", data);
    }
    public static Result fail(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }
}
