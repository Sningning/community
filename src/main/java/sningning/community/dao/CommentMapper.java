package sningning.community.dao;

import org.apache.ibatis.annotations.Mapper;
import sningning.community.entity.Comment;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-07 11:23
 */
@Mapper
public interface CommentMapper {

    /**
     * 查询评论
     *
     * @param entityType
     * @param entityId
     * @param offset     当前页的起始行
     * @param limit      页数上限
     * @return
     */
    List<Comment> selectCommentsByEntity(Integer entityType, Integer entityId, Integer offset, Integer limit);

    /**
     * 通过实体类型查询评论总数
     *
     * @param entityType
     * @param entityId
     * @return 评论总数
     */
    Integer selectCountByEntity(Integer entityType, Integer entityId);

    /**
     * 插入评论
     *
     * @param comment
     * @return
     */
    Integer insertComment(Comment comment);

    /**
     * 通过帖子 id 查询对应的帖子
     *
     * @param id 帖子 id
     * @return
     */
    Comment selectCommentById(Integer id);
}
