package sningning.community.dao;

import org.apache.ibatis.annotations.Mapper;
import sningning.community.entity.User;

/**
 * @ClassName: UserMapper
 * @Author: Song Ningning
 * @Date: 2020-04-21 23:06
 */

@Mapper
public interface UserMapper {

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
