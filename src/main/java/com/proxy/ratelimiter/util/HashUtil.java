package com.proxy.ratelimiter.util;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {

    public static String hashData(String data, String salt) {
        return DigestUtils.md2Hex(data + salt);
    }

}
