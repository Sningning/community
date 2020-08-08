package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import sningning.community.dao.DiscussPostMapper;
import sningning.community.entity.DiscussPost;
import sningning.community.util.SensitiveFilter;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-04-22 23:51
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 分页查询帖子
     * @param userId 用户 id，为 0 时，不拼入 SQL 语句；不为 0 时，拼入 SQL 语句
     * @param offset 每页起始行行号
     * @param limit 每页显示的行数
     * @return 查询到的帖子集合
     */
    public List<DiscussPost> findDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    /**
     * 查询帖子总数
     * @param userId 用户 id，为 0 时，不拼入 SQL 语句；不为 0 时，拼入 SQL 语句
     * @return 帖子总数
     */
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    /**
     * 发布帖子
     * @param post
     * @return
     */
    public int addDiscussPost(DiscussPost post) {
        // 判空
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 转义 html 标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    /**
     * 根据帖子id查看特定帖子
     * @param id 帖子id
     * @return
     */
     public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
     }

    /**
     * 更新帖子评论数量
     * @param id 帖子id
     * @param commentCount 评论数量
     * @return
     */
     public int updateCommentCount(int id, int commentCount) {
         return discussPostMapper.updateCommentCount(id, commentCount);
     }
}
