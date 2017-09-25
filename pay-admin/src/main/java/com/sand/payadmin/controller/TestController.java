package com.sand.payadmin.controller;


import com.alibaba.fastjson.JSON;
import com.sand.payadmin.model.entity.auth.User;
import com.sand.payadmin.model.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by xy on 2017/3/27.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(String username, String password)
    {
        Map<String, String> map = new HashMap<>();
        map.put("test","test");
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public Map userInfo(@RequestParam String token)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("code",20000);

        Map<String, Object> dataMap = new HashMap<>();

        Map<String, Set> roleMap = new HashMap<>();

        Set<String> roles = new HashSet<>();
        roles.add("admin");
        dataMap.put("role",roles);
        dataMap.put("name","admin");
        dataMap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

        map.put("data", dataMap);

        return map;
    }


    @RequestMapping("/hello")
    public String home(HttpServletRequest request, Model model) {
        return "test/hello";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(name = "/login", method = RequestMethod.POST)
    public String login() {
        return "index";
    }


    @ResponseBody
    @RequestMapping("/user/{userId}")
    public String findUser(@PathVariable Integer userId)
    {
        User user = userRepository.findOne(new Long(userId));

        return user.toString();
    }


    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(AuthorizationException e, Model model) {

        // you could return a 404 here instead (this is how github handles 403, so the user does NOT know there is a
        // resource at that location)
        log.debug("AuthorizationException was thrown", e);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "No message available");
        model.addAttribute("errors", map);

        return "error";
    }


}
