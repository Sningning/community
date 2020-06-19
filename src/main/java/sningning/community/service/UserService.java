package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sningning.community.dao.UserMapper;
import sningning.community.entity.User;


/**
 * @ClassName: UserService
 * @Author: Song Ningning
 * @Date: 2020-04-22 23:55
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户 id 查询用户
     * @param id 用户 id
     * @return 查询到的用户
     */
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }
}
