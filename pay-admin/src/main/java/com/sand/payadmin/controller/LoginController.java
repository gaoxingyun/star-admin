package com.sand.payadmin.controller;

import com.sand.payadmin.common.contant.ApiContant;
import com.sand.payadmin.common.jwt.JwtHelper;
import com.sand.payadmin.common.util.JacksonUtils;
import com.sand.payadmin.common.util.RemoteAddrUtils;
import com.sand.payadmin.model.entity.auth.User;
import com.sand.payadmin.model.pojo.RestResult;
import com.sand.payadmin.service.AuthDatabaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xy on 2017/3/27.
 */
@RestController
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthDatabaseService authDatabaseService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResult loginUser(@RequestBody String request, HttpServletRequest httpServletRequest) {

        Map<String, String> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        String username = reqMap.get("username");
        String password = reqMap.get("password");

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (subject.isAuthenticated()) {
            authDatabaseService.updateLastLoginTimeByUsername(username);
            User userLogin = authDatabaseService.findUserByUsername(username);
            // 在session中存放用户信息
            subject.getSession().setAttribute("userLogin", userLogin);

            Map<String, String> map = new HashMap<>();
            map.put("token", JwtHelper.createJwt(subject.getSession().getId().toString(), username, RemoteAddrUtils.getIpAddr(httpServletRequest)));
            log.debug("登陆成功, 用户名: {}", username);
            return new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, map);
        } else {
            log.debug("登陆失败, 用户名: {}", username);
            token.clear();
            return new RestResult(ApiContant.RES_CODE_FAIL, ApiContant.RES_MSG_FAIL, null);
        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public RestResult logoutUser(HttpServletRequest httpServletRequest) {
        return new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, null);
    }

}
