package com.sand.payadmin.model.repository;

import com.sand.payadmin.model.entity.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xy on 2017/3/27.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * 通过用户名查找用户
     */
    public User findByName(String name);

    /**
     * 分页查询用户
     *
     * @param pageable
     * @return
     */
    public Page<User> findAll(Pageable pageable);

}
