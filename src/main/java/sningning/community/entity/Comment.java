package sningning.community.entity;

import java.util.Date;

/**
 * 评论
 *
 * @author: Song Ningning
 * @date: 2020-08-07 11:24
 */
public class Comment {

    private Integer id;
    private Integer userId;
    /**
     * 实体类型
     * 指明针对什么进行评论，比如：针对帖子进行评论、针对别人的评论进行回复
     */
    private Integer entityType;
    /**
     * 实体类型id
     * 用于指明指定类型中具体哪一项
     */
    private Integer entityId;
    /**
     * 目标id
     * 用于指明当前评论是针对用户 id 为 targetId 的评论
     */
    private Integer targetId;
    /**
     * 评论具体内容
     */
    private String content;
    /**
     * 帖子状态
     * 0-正常；1-被禁用
     */
    private Integer status;
    /**
     * 评论时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", targetId=" + targetId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
