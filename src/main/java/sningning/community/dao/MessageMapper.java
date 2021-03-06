package sningning.community.dao;

import org.apache.ibatis.annotations.Mapper;
import sningning.community.entity.Message;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-08 11:25
 */
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表
     *
     * @param userId 用户id
     * @param offset 当前页起始行，用于分页
     * @param limit  每页数量，用于分页
     * @return 最新的会话排在最前
     */
    List<Message> selectConversations(Integer userId, Integer offset, Integer limit);

    /**
     * 查询当前用户的会话数量
     *
     * @param userId 用户id
     * @return 当前用户的会话数量
     */
    Integer selectConversationCount(Integer userId);

    /**
     * 查询某个会话所包含的私信列表
     * 优先显示最新消息
     *
     * @param conversationId 会话id
     * @param offset         当前页起始行，用于分页
     * @param limit          每页数量，用于分页
     * @return 最新的消息排在最前
     */
    List<Message> selectLettersDesc(String conversationId, Integer offset, Integer limit);

    /**
     * 查询某个会话所包含的私信列表
     * 优先显示最老的消息
     *
     * @param conversationId 会话id
     * @param offset         当前页起始行，用于分页
     * @param limit          每页数量，用于分页
     * @return 最新的消息排在最后
     */
    List<Message> selectLettersAsc(String conversationId, Integer offset, Integer limit);

    /**
     * 查询某个会话所包含的私信数量
     *
     * @param conversationId 会话id
     * @return
     */
    Integer selectLetterCount(String conversationId);

    /**
     * 查询未读私信数量
     *
     * @param userId         用户id
     * @param conversationId 会话id
     * @return conversationId 如果为空，返回所有未读私信数量；如果不为空，返回 conversationId 表示的会话中未读私信数量
     */
    Integer selectLetterUnreadCount(Integer userId, String conversationId);

    /**
     * 新增一条消息
     *
     * @return
     */
    Integer insertMessage(Message message);

    /**
     * 修改消息状态
     *
     * @param ids
     * @param status
     * @return
     */
    Integer updateStatus(List<Integer> ids, Integer status);

    /**
     * 查询某个主题下最新的通知
     *
     * @param userId
     * @param topic
     * @return
     */
    Message selectLatestNotice(Integer userId, String topic);

    /**
     * 查询某个主题所包含的通知数量
     *
     * @param userId
     * @param topic
     * @return
     */
    Integer selectNoticeCount(Integer userId, String topic);

    /**
     * 查询未读通知的数量
     *
     * @param userId
     * @param topic
     * @return
     */
    Integer selectNoticeUnreadCount(Integer userId, String topic);

    /**
     * 查询某个主题所包含的通知列表
     *
     * @param userId
     * @param topic
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectNotices(Integer userId, String topic, Integer offset, Integer limit);
}
