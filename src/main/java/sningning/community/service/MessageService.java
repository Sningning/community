package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sningning.community.dao.MessageMapper;
import sningning.community.entity.Message;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-08 17:34
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询某个用户的所有会话
     * @param userId 用户id
     * @param offset 当前页起始行，用于分页
     * @param limit 每页数量，用于分页
     * @return
     */
    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    /**
     * 查询指定用户的会话数量
     * @param userId 用户id
     * @return
     */
    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    /**
     * 查询私信
     * @param conversationId
     * @param offset 当前页起始行，用于分页
     * @param limit 每页数量，用于分页
     * @return
     */
    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    /**
     * 查询私信数量
     * @param conversationId
     * @return
     */
    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    /**
     * 查询指定用户的未读会话数量
     * @param userId 用户id
     * @param conversationId
     * @return conversationId 如果为空，返回所有未读私信数量；如果不为空，返回 conversationId 表示的会话中未读私信数量
     */
    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

}
