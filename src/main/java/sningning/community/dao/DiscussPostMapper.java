package sningning.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sningning.community.entity.DiscussPost;

import java.util.List;

/**
 * @ClassName: DiscussPostMapper
 * @Description: TODO
 * @Author: Song Ningning
 * @Date: 2020-04-22 22:29
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param 注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

}
