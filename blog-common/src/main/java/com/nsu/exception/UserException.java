package com.nsu.exception;

/**
 * @Author Monica
 * @Date 2022/10/12 9:57
 **/
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object... args) {
        super("user", code, args, null);
    }
}
