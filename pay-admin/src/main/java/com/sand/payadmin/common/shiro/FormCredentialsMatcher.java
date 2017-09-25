package com.sand.payadmin.common.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 自定义密码验证
 * <p>
 * Created by xy on 2017/3/28.
 */
@Component
public class FormCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private FormPasswordHelper FormPasswordHelper;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authcToken;
        String inputPassword = String.valueOf(usernamePasswordToken.getPassword());
        SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo) info;
        String dbPassword = simpleAuthenticationInfo.getCredentials().toString();
        String checkPassword = FormPasswordHelper.makePassword(simpleAuthenticationInfo.getPrincipals().toString(), inputPassword, simpleAuthenticationInfo.getCredentialsSalt().toString());
        return dbPassword.equals(checkPassword);
    }

}
