package com.sand.payadmin.model.repository;

import com.sand.payadmin.model.entity.auth.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xy on 2017/3/27.
 */
@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    /**
     * 通过用户ID查找中间表列表
     */
    public List<UserGroupRepository> findByUserId(Long userId);

    /**
     * 通过用户组ID查找中间表列表
     */
    public List<UserGroupRepository> findByGroupId(Long groupId);

}
