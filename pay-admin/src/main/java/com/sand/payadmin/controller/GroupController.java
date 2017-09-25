package com.sand.payadmin.controller;

import com.sand.payadmin.common.contant.ApiContant;
import com.sand.payadmin.common.util.JacksonUtils;
import com.sand.payadmin.model.entity.auth.Group;
import com.sand.payadmin.model.pojo.RestResult;
import com.sand.payadmin.model.pojo.DataPageResponse;
import com.sand.payadmin.service.AuthDatabaseService;
import com.sand.payadmin.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xy on 2017/3/28.
 */
@RequestMapping("/api")
@RestController
public class GroupController {

    private final static Logger log = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private AuthDatabaseService authDatabaseService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public RestResult queryAllGroup(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 100;
        }
        Page<Group> groupPage = groupService.findAllGroup(page, size);
        Integer totalPage = groupPage.getTotalPages();
        Integer totalNum = groupPage.getNumber();
        List<Group> groups = groupPage.getContent();

        DataPageResponse dataPageResponse = new DataPageResponse(totalNum, totalPage, page, size, groups);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }


    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public RestResult createGroup(@RequestBody String request, HttpServletRequest httpServletRequest) {
        Map<String, Object> reqMap = JacksonUtils.jsonToBean(request, Map.class);
        String groupName = reqMap.get("name").toString();
        String detail = reqMap.get("detail").toString();
        Long parentId = Long.parseLong(reqMap.get("parent_id").toString());

        Group group = groupService.createGroup(groupName, detail, parentId);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, group);

        return restResult;
    }


    @RequestMapping(value = "/group/id/{groupId}", method = RequestMethod.DELETE)
    public RestResult deleteGroup(@PathVariable Integer groupId) {
        Group group = groupService.deleteGroup(groupId.longValue());
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, group);
        return restResult;
    }


    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public RestResult updateGroup(@RequestBody String request) {
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, null);
        return restResult;
    }


    @RequestMapping(value = "/group/id/{groupId}", method = RequestMethod.GET)
    public RestResult queryGroup(@PathVariable Integer groupId) {
        Group group = groupService.queryGroup(groupId.longValue());

        List<Group> groups = new ArrayList<>();
        if (group != null) {
            groups.add(group);
        }

        DataPageResponse dataPageResponse = new DataPageResponse(1, 1, 0, null, groups);
        RestResult restResult = new RestResult(ApiContant.RES_CODE_SUCCESS, ApiContant.RES_MSG_SUCCESS, dataPageResponse);
        return restResult;
    }

}
