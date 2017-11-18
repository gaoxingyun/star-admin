package com.sand.payadmin.controller;

import com.sand.payadmin.common.contant.ApiContant;
import com.sand.payadmin.common.contant.ShiroContant;
import com.sand.payadmin.common.jwt.JwtHelper;
import com.sand.payadmin.common.util.JacksonUtils;
import com.sand.payadmin.model.entity.auth.Permission;
import com.sand.payadmin.model.entity.auth.Role;
import com.sand.payadmin.model.entity.auth.User;
import com.sand.payadmin.model.pojo.RestResult;
import com.sand.payadmin.model.pojo.DataPageResponse;
import com.sand.payadmin.service.AuthDatabaseService;
import com.sand.payadmin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by xy on 2017/3/28.
 */
@RequestMapping("/api")
@RestController
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private AuthDatabaseService authDatabaseService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public RestResult queryAllUser(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 100;
        }
        Page<User> userPage = userService.findAllUser(page, size);
        Integer totalPage = userPage.getTotalPages();
        Integer totalNum = userPage.getNumber();
        List<User> users = userPage.getContent();

        DataPageResponse dataPageResponse = new DataPageResponse(totalNum, totalPage, page, size, users);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public RestResult updateUser(@RequestBody String request) {
        Map<String, Object> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        String username = reqMap.get("name").toString();
        String status = reqMap.get("status").toString();
        User user = userService.updateUserStatus(username, status);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, user);
        return restResult;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public RestResult createUser(@RequestBody String request, HttpServletRequest httpServletRequest) {

        String parentUsername = JwtHelper.parseJWT(JwtHelper.formatAuthHeadToken(httpServletRequest.getHeader(ShiroContant.AUTHORIZATION_HEADER))).get(ShiroContant.USER_ID).toString();
        User parentUser = authDatabaseService.findUserByUsername(parentUsername);

        Map<String, Object> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        String username = reqMap.get("name").toString();
        String password = reqMap.get("password").toString();
        String status = reqMap.get("status").toString();
        long parentId = parentUser.getId();
        User user = userService.createUser(username, password, parentId, status);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, user);

        return restResult;
    }


    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public RestResult userInfo(HttpServletRequest request) {
        String username = JwtHelper.parseJWT(JwtHelper.formatAuthHeadToken(request.getHeader(ShiroContant.AUTHORIZATION_HEADER))).get(ShiroContant.USER_ID).toString();

        Map<String, Object> dataMap = new HashMap<>();

        Set<Role> roles =  userService.findAllRoleByUsername(username);
        Set<Permission> permissions = userService.findAllPermissionByUsername(username);

        Set<String> rolesTemp = new HashSet<>();
        rolesTemp.add("sys-auth");

        dataMap.put("role", rolesTemp);
        dataMap.put("permission", permissions);
        dataMap.put("name", username);
        dataMap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataMap);
        return restResult;
    }

    @RequestMapping(value = "/user/name/{username}", method = RequestMethod.GET)
    public RestResult queryUser(@PathVariable String username) {
        User user = userService.queryUser(username);


        List<User> users = new ArrayList<>();
        if (user != null) {
            users.add(user);
        }

        DataPageResponse dataPageResponse = new DataPageResponse(1,1, 0, null, users);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }

    @RequestMapping(value = "/user/name/{username}", method = RequestMethod.DELETE)
    public RestResult deleteUser(@PathVariable String username) {
        User user = userService.deleteUser(username);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, user);
        return restResult;
    }

}
