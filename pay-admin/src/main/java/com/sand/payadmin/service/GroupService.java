package com.sand.payadmin.service;

import com.sand.payadmin.model.entity.auth.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 组服务
 */
@Service
public class GroupService {

    @Autowired
    private AuthDatabaseService authDatabaseService;


    /**
     * 创建组
     *
     * @param name
     * @param detail
     * @param parentId
     * @return Group
     */
    public Group createGroup(String name, String detail, Long parentId) {
        Group group = new Group();
        group.setName(name);
        group.setDetail(detail);
        group.setParentId(parentId);
        return authDatabaseService.saveGroup(group);
    }


    /**
     * 查询组
     *
     * @param id
     * @return Group
     */
    public Group queryGroup(Long id) {
        Group group = authDatabaseService.findGroupById(id);
        return group;
    }

    /**
     * 删除组
     *
     * @param id
     * @return Group
     */
    public Group deleteGroup(Long id) {
        Group group = authDatabaseService.findGroupById(id);
        authDatabaseService.deleteGroup(group);
        return group;
    }


    /**
     * 查询所有组
     *
     * @return
     */
    public Page<Group> findAllGroup(Integer page, Integer size) {
        Page<Group> groupPage = authDatabaseService.findAllGroup(page, size);
        return groupPage;
    }

}
