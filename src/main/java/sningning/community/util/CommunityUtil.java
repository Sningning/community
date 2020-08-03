package sningning.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Author: Song Ningning
 * @Date: 2020-08-03 12:14
 */
public class CommunityUtil {

    /**
     * 生成随机字符串
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * MD5 加密
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
