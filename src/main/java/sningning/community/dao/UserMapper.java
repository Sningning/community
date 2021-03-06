package sningning.community.dao;

import org.apache.ibatis.annotations.Mapper;
import sningning.community.entity.User;

/**
 * @author: Song Ningning
 * @date: 2020-04-21 23:06
 */

@Mapper
public interface UserMapper {

    /**
     * 根据 id 查询用户
     *
     * @param id 用户 id
     * @return 查询到的用户信息
     */
    User selectById(Integer id);

    /**
     * 根据姓名查询用户
     *
     * @param username 用户名
     * @return 查询到的用户信息
     */
    User selectByName(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 用户邮箱
     * @return 查询到的用户信息
     */
    User selectByEmail(String email);

    /**
     * 添加用户
     *
     * @param user 待添加的用户信息
     * @return 影响数据库的行数
     */
    Integer insertUser(User user);

    /**
     * 更新用户状态
     *
     * @param id     用户 id
     * @param status 状态值
     * @return 影响数据库的行数
     */
    Integer updateStatus(Integer id, Integer status);

    /**
     * 更新用户头像
     *
     * @param id        用户 id
     * @param headerUrl 头像地址
     * @return 影响数据库的行数
     */
    Integer updateHeader(Integer id, String headerUrl);

    /**
     * 更新用户密码
     *
     * @param id       用户 id
     * @param password 新密码
     * @return 影响数据库的行数
     */
    Integer updatePassword(Integer id, String password);
}
