package sningning.community.service;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import sningning.community.dao.DiscussPostMapper;
import sningning.community.entity.DiscussPost;
import sningning.community.util.SensitiveFilter;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Song Ningning
 * @date: 2020-04-22 23:51
 */
@Service
public class DiscussPostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscussPostService.class);

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Value("${caffeine.posts.maxsize}")
    private Integer maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private long expireSeconds;

    /**
     * Caffeine 核心接口：Cache。两个常用子接口：LoadingCache, AsyncLoadingCache
     */

    /**
     * 帖子列表缓存
     */
    private LoadingCache<String, List<DiscussPost>> postListCache;

    /**
     * 帖子总数缓存
     */
    private LoadingCache<Integer, Integer> postRowsCache;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        // 初始化帖子列表缓存
        postListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<DiscussPost>>() {
                    @Nullable
                    @Override
                    public List<DiscussPost> load(@NonNull String key) throws Exception {
                        if (key.length() == 0) {
                            throw new IllegalArgumentException("参数错误！");
                        }

                        String[] params = key.split(":");
                        if (params.length != 2) {
                            throw new IllegalArgumentException("参数错误！");
                        }

                        Integer offset = Integer.parseInt(params[0]);
                        Integer limit = Integer.parseInt(params[1]);

                        // 省略了二级缓存：Redis

                        LOGGER.debug("Load post lists from DB.");
                        return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
                    }
                });

        // 初始化帖子总数缓存
        postRowsCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) throws Exception {

                        // 省略了二级缓存：Redis

                        LOGGER.debug("Load post rows from DB.");
                        return discussPostMapper.selectDiscussPostRows(key);
                    }
                });
    }

    /**
     * 分页查询帖子
     *
     * @param userId 用户 id，为 0 时，不查询所有；不为 0 时，查询指定用户的帖子
     * @param offset 每页起始行行号
     * @param limit  每页显示的行数
     * @return 查询到的帖子集合
     */
    public List<DiscussPost> findDiscussPost(Integer userId, Integer offset, Integer limit, Integer orderMode) {
        // 只有当显示首页并且查看热门帖子时才查缓存
        if (userId == 0 && orderMode == 1) {
            return postListCache.get(offset + ":" + limit);
        }
        LOGGER.debug("Load post lists from DB.");
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
    }

    /**
     * 查询帖子总数
     *
     * @param userId 用户 id。为 0 时，查询总行数；不为 0 时，查询指定用户的帖子总数。
     * @return 帖子总数
     */
    public Integer findDiscussPostRows(Integer userId) {
        if (userId == 0) {
            return postRowsCache.get(userId);
        }
        LOGGER.debug("Load post rows from DB.");
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    /**
     * 发布帖子
     *
     * @param post
     * @return
     */
    public Integer addDiscussPost(DiscussPost post) {
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
     *
     * @param id 帖子id
     * @return
     */
    public DiscussPost findDiscussPostById(Integer id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    /**
     * 更新帖子评论数量
     *
     * @param id           帖子id
     * @param commentCount 评论数量
     * @return
     */
    public Integer updateCommentCount(Integer id, Integer commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    /**
     * 更新帖子类型
     *
     * @param id   帖子id
     * @param type 帖子类型
     * @return
     */
    public Integer updateType(Integer id, Integer type) {
        return discussPostMapper.updateType(id, type);
    }

    /**
     * 更新帖子状态
     *
     * @param id     帖子id
     * @param status 状态
     * @return
     */
    public Integer updateStatus(Integer id, Integer status) {
        return discussPostMapper.updateStatus(id, status);
    }

    /**
     * 更新帖子分数
     *
     * @param id    帖子id
     * @param score 分数
     * @return
     */
    public Integer updateScore(Integer id, double score) {
        return discussPostMapper.updateScore(id, score);
    }
}
