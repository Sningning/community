package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sningning.community.dao.DiscussPostMapper;
import sningning.community.entity.DiscussPost;

import java.util.List;

/**
 * @ClassName: DiscussPostService
 * @Author: Song Ningning
 * @Date: 2020-04-22 23:51
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

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
}
