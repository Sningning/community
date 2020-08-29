package sningning.community.util;

/**
 * @author: Song Ningning
 * @date: 2020-08-03 17:03
 */
public interface CommunityConstant {

    /**
     * 激活成功
     */
    Integer ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    Integer ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    Integer ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登录凭证的超时时间为 12 小时
     */
    Integer DEFAULT_EXPIRED_TIME = 3600 * 12;

    /**
     * 记住我 状态下的登录凭证的超时时间为 3 个月
     */
    Integer REMEMBER_EXPIRED_TIME = 3600 * 24 * 100;

    /**
     * 实体类型：帖子
     */
    Integer ENTITY_TYPE_POST = 1;

    /**
     * 实体类型：评论
     */
    Integer ENTITY_TYPE_COMMENT = 2;

    /**
     * 实体类型：用户
     */
    Integer ENTITY_TYPE_USER = 3;

    /**
     * 消息未读
     */
    Integer UNREAD = 0;

    /**
     * 消息已读
     */
    Integer READ = 1;

    /**
     * 消息已删除
     */
    Integer DELETE = 2;

    /**
     * 主题：评论
     */
    String TOPIC_COMMENT = "comment";

    /**
     * 主题：点赞
     */
    String TOPIC_LIKE = "like";

    /**
     * 主题：关注
     */
    String TOPIC_FOLLOW = "follow";

    /**
     * 主题：发帖
     */
    String TOPIC_PUBLISH = "publish";

    /**
     * 主题：删帖
     */
    String TOPIC_DELETE = "delete";

    /**
     * 主题：分享
     */
    String TOPIC_SHARE = "share";

    /**
     * 系统用户 id
     */
    Integer SYSTEM_USER_ID = 1;

    /**
     * 普通用户
     */
    String AUTHORITY_USER = "user";

    /**
     * 管理员
     */
    String AUTHORITY_ADMIN = "admin";

    /**
     * 版主
     */
    String AUTHORITY_MODERATOR = "moderator";
}
