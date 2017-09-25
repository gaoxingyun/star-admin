package com.sand.payadmin.service;

import com.sand.payadmin.common.contant.AuthConstant;
import com.sand.payadmin.common.shiro.FormPasswordHelper;
import com.sand.payadmin.common.util.RandomStringGeneratorUtils;
import com.sand.payadmin.model.entity.auth.User;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * 用户服务
 */
@Service
public class UserService {

    @Autowired
    private AuthDatabaseService authDatabaseService;

    @Autowired
    private FormPasswordHelper userFormPasswordHelper;

    /**
     * 创建用户
     *
     * @param username
     * @param password
     */
    public User createUser(String username, String password, Long parentUserId, String status) {
        User user = new User();
        user.setName(username);
        user.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(AuthConstant.USER_TIME_FORMAT)));
        user.setStatus(status);
        user.setParentId(parentUserId);
        String salt = RandomStringGeneratorUtils.getRandomStringByLength(AuthConstant.USER_SALT_LENGTH);
        user.setSalt(salt);
        user.setPassword(userFormPasswordHelper.makePassword(username, password, ByteSource.Util.bytes(salt).toString()));
        return authDatabaseService.saveUser(user);
    }


    /**
     * 更新用户
     *
     * @param username
     * @return User
     */
    public User updateUserStatus(String username, String status) {
        User user = authDatabaseService.updateUserStatus(username, status);
        return user;
    }

    /**
     * 查询用户
     *
     * @param username
     * @return User
     */
    public User queryUser(String username) {
        User user = authDatabaseService.findUserByName(username);
        return user;
    }

    /**
     * 删除用户
     *
     * @param username
     * @return User
     */
    public User deleteUser(String username) {
        User user = authDatabaseService.deleteUserByName(username);
        return user;
    }


    /**
     * 查询所有用户
     *
     * @return
     */
    public Page<User> findAllUser(Integer page, Integer size) {
        Page<User> userPage = authDatabaseService.findAllUser(page, size);
        return userPage;
    }


}
