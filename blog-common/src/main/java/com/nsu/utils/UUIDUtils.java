package com.nsu.utils;

import cn.hutool.core.lang.UUID;

/**
 * @Author Monica
 * @Date 2022/9/28 17:04
 **/
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
