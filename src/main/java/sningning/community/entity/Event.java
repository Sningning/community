package sningning.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件
 *
 * @author: Song Ningning
 * @date: 2020-08-15 21:57
 */
public class Event {

    /**
     * 主题
     */
    private String topic;

    /**
     * 消息的来源者
     */
    private Integer userId;

    /**
     * 消息的目标实体
     */
    private Integer entityType;

    /**
     * 消息的目标实体的id
     */
    private Integer entityId;

    /**
     * 消息的目标实体的作者
     */
    private Integer entityUserId;

    /**
     * 其他数据
     */
    private Map<String, Object> data = new HashMap<>();

    public String getTopic() {
        return topic;
    }

    /**
     * set 方法执行完后，返回当前对象，可以进行链式调用
     *
     * @param topic
     * @return 当前对象
     */
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Event setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public Event setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public Event setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public Integer getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(Integer entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
