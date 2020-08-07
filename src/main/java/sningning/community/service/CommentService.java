package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sningning.community.dao.CommentMapper;
import sningning.community.entity.Comment;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-07 14:55
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 通过 entity 查询评论
     * @param entityType
     * @param entityId
     * @param offset 当前页的起始行
     * @param limit 页数上限
     * @return
     */
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    /**
     * 通过 entity 查询评论总数
     * @param entityType
     * @param entityId
     * @return
     */
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }
}
