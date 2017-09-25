package com.sand.payadmin.controller;

import com.sand.payadmin.common.contant.ApiContant;
import com.sand.payadmin.common.util.JacksonUtils;
import com.sand.payadmin.model.entity.auth.Permission;
import com.sand.payadmin.model.pojo.RestResult;
import com.sand.payadmin.model.pojo.DataPageResponse;
import com.sand.payadmin.service.PermissionService;
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
public class PermissionController {

    private final static Logger log = LoggerFactory.getLogger(PermissionController.class);


    @Autowired
    private PermissionService permissionService;


    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public RestResult queryAllPermission(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = 100;
        }

        Page<Permission> permissionPage = permissionService.findAllPermission(page, size);

        List<Permission> permissions = permissionPage.getContent();
        Integer totalNum = permissionPage.getNumber();
        Integer totalPage = permissionPage.getTotalPages();

        DataPageResponse dataPageResponse = new DataPageResponse(totalNum,totalPage,page,size,permissions);

        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }


    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public RestResult createPermission(@RequestBody String request, HttpServletRequest httpServletRequest) {
        Map<String, Object> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        String permissionName = reqMap.get("name").toString();
        String detail = reqMap.get("detail").toString();
        Long parentId = Long.parseLong(reqMap.get("parent_id").toString());
        Permission permission = permissionService.createPermission(permissionName, detail, parentId);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, permission);

        return restResult;
    }




    @RequestMapping(value = "/permission/id/{permissionId}", method = RequestMethod.DELETE)
    public RestResult deletePermission(@PathVariable Integer permissionId) {
        Permission permission = permissionService.deletePermission(permissionId.longValue());
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, permission);
        return restResult;
    }


    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public RestResult updatePermission(@RequestBody String request) {

        Map<String, Object> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        Long id = Long.parseLong(reqMap.get("id").toString());
        String name = reqMap.get("name").toString();
        String detail = reqMap.get("detail").toString();

        permissionService.updatePermission(id,name,detail);

        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, null);
        return restResult;
    }


    @RequestMapping(value = "/permission/id/{permissionId}", method = RequestMethod.GET)
    public RestResult queryPermission(@PathVariable Integer permissionId) {
        Permission permission = permissionService.queryPermission(permissionId.longValue());

        List<Permission> permissions = new ArrayList<>();
        if (permission != null) {
            permissions.add(permission);
        }

        DataPageResponse dataPageResponse = new DataPageResponse(1,1, 0, null, permissions);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }


}
