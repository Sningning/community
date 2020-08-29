package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import sningning.community.dao.MessageMapper;
import sningning.community.entity.Message;
import sningning.community.util.CommunityConstant;
import sningning.community.util.SensitiveFilter;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-08 17:34
 */
@Service
public class MessageService implements CommunityConstant {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 查询某个用户的所有会话
     *
     * @param userId 用户id
     * @param offset 当前页起始行，用于分页
     * @param limit  每页数量，用于分页
     * @return
     */
    public List<Message> findConversations(Integer userId, Integer offset, Integer limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    /**
     * 查询指定用户的会话数量
     *
     * @param userId 用户id
     * @return
     */
    public Integer findConversationCount(Integer userId) {
        return messageMapper.selectConversationCount(userId);
    }

    /**
     * 查询私信(最新的在最前)
     *
     * @param conversationId
     * @param offset         当前页起始行，用于分页
     * @param limit          每页数量，用于分页
     * @return
     */
    public List<Message> findLettersDesc(String conversationId, Integer offset, Integer limit) {
        return messageMapper.selectLettersDesc(conversationId, offset, limit);
    }

    /**
     * 查询私信(最新的在最后)
     *
     * @param conversationId
     * @param offset         当前页起始行，用于分页
     * @param limit          每页数量，用于分页
     * @return
     */
    public List<Message> findLettersAsc(String conversationId, Integer offset, Integer limit) {
        return messageMapper.selectLettersAsc(conversationId, offset, limit);
    }

    /**
     * 查询私信数量
     *
     * @param conversationId
     * @return
     */
    public Integer findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    /**
     * 查询指定用户的未读会话数量
     *
     * @param userId         用户id
     * @param conversationId
     * @return conversationId 如果为空，返回所有未读私信数量；如果不为空，返回 conversationId 表示的会话中未读私信数量
     */
    public Integer findLetterUnreadCount(Integer userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    /**
     * 添加一条消息
     *
     * @param message
     * @return
     */
    public Integer addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    /**
     * 将消息设置为已读
     *
     * @param ids
     * @return
     */
    public Integer readMessage(List<Integer> ids) {
        return messageMapper.updateStatus(ids, READ);
    }

    /**
     * 查询某个主题下最新的通知
     *
     * @param userId
     * @param topic
     * @return
     */
    public Message findLatestNotice(Integer userId, String topic) {
        return messageMapper.selectLatestNotice(userId, topic);
    }

    /**
     * 查询某个主题所包含的通知数量
     *
     * @param userId
     * @param topic
     * @return
     */
    public Integer findNoticeCount(Integer userId, String topic) {
        return messageMapper.selectNoticeCount(userId, topic);
    }

    /**
     * 查询未读通知的数量
     *
     * @param userId
     * @param topic
     * @return
     */
    public Integer findNoticeUnreadCount(Integer userId, String topic) {
        return messageMapper.selectNoticeUnreadCount(userId, topic);
    }

    /**
     * 查询通知列表
     *
     * @param userId
     * @param topic
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> findNotices(Integer userId, String topic, Integer offset, Integer limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }

}
