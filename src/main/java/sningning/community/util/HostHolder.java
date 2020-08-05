package sningning.community.util;

import org.springframework.stereotype.Component;
import sningning.community.entity.User;

/**
 * 持有用户的信息，用于代替 session 对象
 * @author: Song Ningning
 * @date: 2020-08-04 20:38
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUsers(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
