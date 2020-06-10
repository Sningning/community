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

    public List<DiscussPost> findDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
