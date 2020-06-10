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

    public User findUserbyId(int id) {
        return userMapper.selectById(id);
    }
}
