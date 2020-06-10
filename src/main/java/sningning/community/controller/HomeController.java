package sningning.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sningning.community.entity.DiscussPost;
import sningning.community.entity.Page;
import sningning.community.entity.User;
import sningning.community.service.DiscussPostService;
import sningning.community.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HomeController
 * @Description: TODO
 * @Author: Song Ningning
 * @Date: 2020-04-23 0:18
 */
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {

        // 方法调用前,SpringMVC 会自动实例化 Model 和 Page, 并将 Page 注入 Model.
        // 所以,在 thymeleaf 中可以直接访问 Page 对象中的数据.
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPost(0, page.getoffset(), page.getLimit());
        List<Map<String, Object>> discussPost = new ArrayList<>();
        if (list != null) {
            for(DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                User user = userService.findUserbyId(post.getUserId());
                map.put("post", post);
                map.put("user", user);
                discussPost.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPost);
        return "/index";
    }


}