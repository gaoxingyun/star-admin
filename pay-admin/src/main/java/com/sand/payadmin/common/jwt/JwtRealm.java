package com.sand.payadmin.common.jwt;

import com.sand.payadmin.common.contant.AuthConstant;
import com.sand.payadmin.model.entity.auth.User;
import com.sand.payadmin.service.AuthDatabaseService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义用户名密码安全数据源
 * <p>
 * Created by xy on 2017/3/27.
 */
@Component
public class JwtRealm extends AuthorizingRealm {

    private final static Logger log = LoggerFactory.getLogger(JwtRealm.class);

    @Autowired
    private AuthDatabaseService authDatabaseService;

    @Autowired
    private JwtCredentialsMatcher credentialsMatcher;


    /**
     * 验证token是否支持
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {

        log.debug("supports exec: {}", token);
        return token != null && token instanceof JwtToken;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.debug("doGetAuthenticationInfo exec: {}", token);

        JwtToken jwtToken = (JwtToken) token;
        String username = jwtToken.getUsername();

        if (username == null) {
            throw new AuthenticationException();
        }
        User user = authDatabaseService.findUserByUsername(username);

        if (user == null) {
            throw new UnknownAccountException();//没找到帐号 }
        }

        if (AuthConstant.USER_STATUS_LOCKED.equals(user.getStatus())) {
            throw new LockedAccountException(); //帐号锁定
        }

        SimpleAccount account = new SimpleAccount(user, jwtToken, getName());
        return account;
    }


    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        log.debug("doGetAuthorizationInfo exec: {}", principals);

        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roles = new HashSet<>();
        authDatabaseService.findRolesByUsername(username).forEach(role -> roles.add(role.getName()));
        authorizationInfo.setRoles(roles);

        Set<String> auths = new HashSet<>();
        authDatabaseService.findPermissionsByUsername(username).forEach(auth -> auths.add(auth.getName()));
        authorizationInfo.setStringPermissions(auths);

        return authorizationInfo;
    }


    /**
     * 自定义Token验证
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(credentialsMatcher);
    }

}
