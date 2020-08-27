package sningning.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sningning.community.entity.DiscussPost;
import sningning.community.entity.Page;
import sningning.community.entity.User;
import sningning.community.service.DiscussPostService;
import sningning.community.service.LikeService;
import sningning.community.service.UserService;
import sningning.community.util.CommunityConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Song Ningning
 * @date: 2020-04-23 0:18
 */
@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page,
                               @RequestParam(name = "orderMode", defaultValue = "0") int orderMode) {

        // 方法调用前,SpringMVC 会自动实例化 Model 和 Page, 并将 Page 注入 Model.
        // 所以,在 thymeleaf 中可以直接访问 Page 对象中的数据.
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index?orderMode=" + orderMode);

        List<DiscussPost> list = discussPostService.findDiscussPost(0, page.getOffset(), page.getLimit(), orderMode);
        List<Map<String, Object>> discussPost = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                User author = userService.findUserById(post.getUserId());
                map.put("post", post);
                map.put("author", author);

                // 点赞信息
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                discussPost.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPost);
        model.addAttribute("orderMode", orderMode);
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

    /**
     * 拒绝访问时的提示
     */
    @RequestMapping(path = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/error/404";
    }

}
