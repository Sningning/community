package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import sningning.community.dao.CommentMapper;
import sningning.community.entity.Comment;
import sningning.community.util.CommunityConstant;
import sningning.community.util.SensitiveFilter;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-07 14:55
 */
@Service
public class CommentService implements CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 通过 entity 查询评论
     *
     * @param entityType
     * @param entityId
     * @param offset     当前页的起始行
     * @param limit      页数上限
     * @return
     */
    public List<Comment> findCommentsByEntity(Integer entityType, Integer entityId, Integer offset, Integer limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    /**
     * 通过 entity 查询评论总数
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public Integer findCommentCount(Integer entityType, Integer entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    /**
     * 增加评论
     *
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        // 过滤格式问题
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        // 过滤敏感词
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        // 添加评论
        Integer rows = commentMapper.insertComment(comment);

        // 更新帖子的评论数量
        if (comment.getEntityType().equals(ENTITY_TYPE_POST)) {
            Integer count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }

        return rows;
    }

    /**
     * 通过帖子 id 查询帖子
     *
     * @param id 帖子 id
     * @return
     */
    public Comment findCommentById(Integer id) {
        return commentMapper.selectCommentById(id);
    }
}
