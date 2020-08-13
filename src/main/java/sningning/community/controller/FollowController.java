package sningning.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sningning.community.annotation.LoginRequired;
import sningning.community.entity.Page;
import sningning.community.entity.User;
import sningning.community.service.FollowService;
import sningning.community.service.UserService;
import sningning.community.util.CommunityConstant;
import sningning.community.util.CommunityUtil;
import sningning.community.util.HostHolder;

import java.util.List;
import java.util.Map;

/**
 * @author: Song Ningning
 * @date: 2020-08-13 21:49
 */
@Controller
public class FollowController implements CommunityConstant {

    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    /**
     * 关注用户
     * @param entityType
     * @param entityId
     * @return
     */
    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0, "已关注！");
    }

    /**
     * 取消关注
     * @param entityType
     * @param entityId
     * @return
     */
    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0, "已取消关注！");
    }

    /**
     * 获取指定用户所关注的人
     * @param userId 用户 id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(path = "/followee/{userId}", method = RequestMethod.GET)
    public String getFollowee(@PathVariable("userId") int userId, Page page, Model model) {

        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }
        model.addAttribute("user", user);

        // 分页信息
        page.setLimit(5);
        page.setPath("/followee/" + userId);
        page.setRows((int) followService.findFolloweeCount(userId, ENTITY_TYPE_USER));

        List<Map<String, Object>> followeeList = followService.findFollowee(userId, page.getOffset(), page.getLimit());
        if (followeeList != null) {
            for (Map<String, Object> map : followeeList) {
                User u = (User) map.get("user");
                map.put("hasFollowed", hasFollowed(u.getId()));
            }
        }
        model.addAttribute("followeeList", followeeList);

        return "/site/followee";
    }

    /**
     * 获取指定用户的粉丝
     * @param userId 用户 id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(path = "/follower/{userId}", method = RequestMethod.GET)
    public String getFollowers(@PathVariable("userId") int userId, Page page, Model model) {

        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }
        model.addAttribute("user", user);

        // 分页信息
        page.setLimit(5);
        page.setPath("/follower/" + userId);
        page.setRows((int) followService.findFollowerCount(ENTITY_TYPE_USER, userId));

        List<Map<String, Object>> followerList = followService.findFollowers(userId, page.getOffset(), page.getLimit());
        if (followerList != null) {
            for (Map<String, Object> map : followerList) {
                User u = (User) map.get("user");
                map.put("hasFollowed", hasFollowed(u.getId()));
            }
        }
        model.addAttribute("followerList", followerList);

        return "/site/follower";
    }

    /**
     * 查询当前登录用户是否已关注 id 为 userId 的用户
     * @param userId
     * @return
     */
    private boolean hasFollowed(int userId) {
        if (hostHolder.getUser() == null) {
            return false;
        }
        return followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
    }
}
