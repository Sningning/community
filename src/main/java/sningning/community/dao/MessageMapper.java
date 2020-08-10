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
     * @param userId 用户id
     * @param offset 当前页起始行，用于分页
     * @param limit 每页数量，用于分页
     * @return 最新的会话排在最前
     */
    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户的会话数量
     * @param userId 用户id
     * @return 当前用户的会话数量
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话所包含的私信列表
     * 优先显示最新消息
     * @param conversationId 会话id
     * @param offset 当前页起始行，用于分页
     * @param limit 每页数量，用于分页
     * @return 最新的消息排在最前
     */
    List<Message> selectLettersDesc(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信列表
     * 优先显示最老的消息
     * @param conversationId 会话id
     * @param offset 当前页起始行，用于分页
     * @param limit 每页数量，用于分页
     * @return 最新的消息排在最后
     */
    List<Message> selectLettersAsc(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信数量
     * @param conversationId 会话id
     * @return
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信数量
     * @param userId 用户id
     * @param conversationId 会话id
     * @return conversationId 如果为空，返回所有未读私信数量；如果不为空，返回 conversationId 表示的会话中未读私信数量
     */
    int selectLetterUnreadCount(int userId, String conversationId);

    /**
     * 新增一条消息
     * @return
     */
    int insertMessage(Message message);

    /**
     * 修改消息状态
     * @param ids
     * @param status
     * @return
     */
    int updateStatus(List<Integer> ids, int status);
}