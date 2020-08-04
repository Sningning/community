package sningning.community.util;

/**
 * @Author: Song Ningning
 * @Date: 2020-08-03 17:03
 */
public interface CommunityConstant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登录凭证的超时时间为 12 小时
     */
    int DEFAULT_EXPIRED_TIME = 3600 * 12;

    /**
     * 记住我 状态下的登录凭证的超时时间为 3 个月
     */
    int REMEMBER_EXPIRED_TIME = 3600 * 24 * 100;
}
