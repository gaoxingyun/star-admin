package com.sand.payadmin.common.jwt;

import com.sand.payadmin.common.shiro.FormPasswordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 自定义密码验证
 * <p>
 * Created by xy on 2017/3/28.
 */
@Component
public class JwtCredentialsMatcher extends SimpleCredentialsMatcher {

    private final static Logger log = LoggerFactory.getLogger(JwtCredentialsMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        SimpleAccount account = (SimpleAccount) info;
        JwtToken jwtToken = (JwtToken) account.getCredentials();

        boolean isMatch = jwtToken.equals(authcToken);

        log.debug("token 验证: {}", true);
        return isMatch;
    }

}
