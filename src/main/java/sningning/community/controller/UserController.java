package sningning.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Song Ningning
 * @Date: 2020-08-03 11:41
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }
}
