package sningning.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sningning.community.entity.DiscussPost;

import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-04-22 22:29
 */
@Mapper
public interface DiscussPostMapper {

    /**
     * 分页查询帖子
     *
     * @param userId    用户 id。为 0 时，查询总行数；不为 0 时，查询指定用户的帖子总数。
     * @param offset    每页起始行行号
     * @param limit     每页显示的行数
     * @param orderMode 排序模式 0-最新；1-分数
     * @return 查询到的帖子集合
     */
    List<DiscussPost> selectDiscussPosts(Integer userId, Integer offset, Integer limit, Integer orderMode);

    // @Param 注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.

    /**
     * 查询 DiscussPost 表中数据总数
     *
     * @param userId 用户 id。为 0 时，查询总行数；不为 0 时，查询指定用户的帖子总数。
     * @return DiscussPost 表中数据总数
     */
    Integer selectDiscussPostRows(@Param("userId") Integer userId);

    /**
     * 插入帖子
     *
     * @param discussPost
     * @return
     */
    Integer insertDiscussPost(DiscussPost discussPost);

    /**
     * 根据id查看帖子详情
     *
     * @param id 帖子id
     * @return
     */
    DiscussPost selectDiscussPostById(Integer id);

    /**
     * 更新帖子评论数量
     *
     * @param id           帖子id
     * @param commentCount 评论数量
     * @return
     */
    Integer updateCommentCount(Integer id, Integer commentCount);

    /**
     * 更新帖子类型
     *
     * @param id   帖子id
     * @param type 帖子类型
     * @return
     */
    Integer updateType(Integer id, Integer type);

    /**
     * 更新帖子状态
     *
     * @param id     帖子id
     * @param status 帖子状态
     * @return
     */
    Integer updateStatus(Integer id, Integer status);

    /**
     * 更新帖子分数
     *
     * @param id    帖子id
     * @param score 帖子分数
     * @return
     */
    Integer updateScore(Integer id, double score);
}
