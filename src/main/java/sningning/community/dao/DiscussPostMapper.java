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
     * @param userId 用户 id，为 0 时，不拼入 SQL 语句；不为 0 时，拼入 SQL 语句
     * @param offset 每页起始行行号
     * @param limit 每页显示的行数
     * @return 查询到的帖子集合
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param 注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    /**
     * 查询 DiscussPost 表中数据总数
     * @param userId 用户 id，为 0 时，不拼入 SQL 语句；不为 0 时，拼入 SQL 语句
     * @return DiscussPost 表中数据总数
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 插入帖子
     * @param discussPost
     * @return
     */
    int insertDiscussPost(DiscussPost discussPost);

}
