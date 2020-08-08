package sningning.community.entity;

import java.util.Date;

/**
 * @author: Song Ningning
 * @date: 2020-08-08 11:21
 */
public class Message {

    /**
     * 发件人
     * 1-系统用户; 其他为用户id
     */
    private int fromId;
    /**
     * 收件人
     */
    private int toId;
    /**
     * 会话id
     */
    private String conversationId;
    /**
     * 私信内容
     */
    private String content;
    /**
     * 私信状态
     * 0-未读;1-已读;2-已删除
     */
    private int status;
    /**
     * 发送时间
     */
    private Date createTime;

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
        return "Message{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", conversationId='" + conversationId + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
