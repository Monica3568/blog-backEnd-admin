package com.nsu.exception;

import com.nsu.comm.ResultCodeEnum;
import lombok.Getter;

/**
 * @Author Monica
 * @Date 2022/9/22 14:30
 **/
@Getter
public class ApiException extends RuntimeException{
    private final String msg;
    private final ResultCodeEnum resultCode;

    public ApiException() {
        this(ResultCodeEnum.Fail);
    }

    public ApiException(String msg) {
        this(ResultCodeEnum.Fail,msg);
    }

    public ApiException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum,resultCodeEnum.getMsg());
    }

    public ApiException(ResultCodeEnum resultCode,String msg) {
        this.msg = msg;
        this.resultCode = resultCode;
    }
}
