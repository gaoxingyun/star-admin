package com.sand.payadmin.controller;

import com.sand.payadmin.common.contant.ApiContant;
import com.sand.payadmin.common.util.JacksonUtils;
import com.sand.payadmin.model.entity.auth.Role;
import com.sand.payadmin.model.pojo.RestResult;
import com.sand.payadmin.model.pojo.DataPageResponse;
import com.sand.payadmin.service.AuthDatabaseService;
import com.sand.payadmin.service.RoleService;
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
public class RoleController {

    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthDatabaseService authDatabaseService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public RestResult queryAllRole(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 100;
        }
        Page<Role> rolePage = roleService.findAllRole(page, size);
        Integer totalPage = rolePage.getTotalPages();
        Integer totalNum = rolePage.getNumber();
        List<Role> roles = rolePage.getContent();

        DataPageResponse dataPageResponse = new DataPageResponse(totalNum, totalPage, page, size, roles);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }



    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public RestResult createRole(@RequestBody String request, HttpServletRequest httpServletRequest) {
        Map<String, Object> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        String roleName = reqMap.get("name").toString();
        String detail = reqMap.get("detail").toString();

        Role role = roleService.createRole(roleName, detail);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, role);

        return restResult;
    }




    @RequestMapping(value = "/role/id/{roleId}", method = RequestMethod.DELETE)
    public RestResult deleteRole(@PathVariable Integer roleId) {
        Role role = roleService.deleteRole(roleId.longValue());
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, role);
        return restResult;
    }


    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public RestResult updateRole(@RequestBody String request) {
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, null);
        return restResult;
    }


    @RequestMapping(value = "/role/id/{roleId}", method = RequestMethod.GET)
    public RestResult queryRole(@PathVariable Integer roleId) {
        Role role = roleService.queryRole(roleId.longValue());

        List<Role> roles = new ArrayList<>();
        if (role != null) {
            roles.add(role);
        }

        DataPageResponse dataPageResponse = new DataPageResponse(1,1, 0, null, roles);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }

}
