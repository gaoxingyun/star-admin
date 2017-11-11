package com.sand.payadmin.common.shiro;


import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sand.payadmin.common.contant.ShiroContant;
import com.sand.payadmin.common.jwt.JwtHelper;
import com.sand.payadmin.common.jwt.JwtToken;
import com.sand.payadmin.common.util.JacksonUtils;
import com.sand.payadmin.common.util.RemoteAddrUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public final class JWTOrFormAuthenticationFilter extends AuthenticatingFilter {

    private final static Logger log = LoggerFactory.getLogger(JWTOrFormAuthenticationFilter.class);

    public static final String USER_ID = ShiroContant.USER_ID;
    public static final String PASSWORD = ShiroContant.PASSWORD;
    public static final String CLIENT_ID = ShiroContant.CLIENT_ID;


    protected static final String AUTHORIZATION_HEADER = ShiroContant.AUTHORIZATION_HEADER;

    public JWTOrFormAuthenticationFilter() {
        setLoginUrl(DEFAULT_LOGIN_URL);
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = getLoginUrl();
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);
        this.appliedPaths.put(getLoginUrl(), null);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
                // 通过所有OPTIONS请求，解决跨域发送OPTIONS请求问题
                return true;
            }


            if (isLoginRequest(request, response) || isLoggedAttempt(request, response)) {

                boolean loggedIn = false;
                loggedIn = executeLogin(request, response);

                if (!loggedIn) {
                    HttpServletResponse httpResponse = WebUtils.toHttp(response);
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }

                log.debug("shiro result: {}", loggedIn);
                return loggedIn;
            }

            return false;
        } catch (Exception e) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 需要手动加CORS头
            httpResponse.setHeader("Access-Control-Allow-Credentials","true");
            httpResponse.setHeader("Access-Control-Allow-Origin","*");
            return false;
        }
    }



    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        if (isLoginRequest(request, response)) {
            String json = IOUtils.toString(request.getInputStream(), "UTF-8");

            if (json != null && !json.isEmpty()) {
                log.debug("create form  token");
                Map<String, String> map = JacksonUtils.jsonToBean(json, Map.class);
                String username = map.get(USER_ID);
                String password = map.get(PASSWORD);
                return new UsernamePasswordToken(username, password);
            }

        }

        if (isLoggedAttempt(request, response)) {
            String jwtToken = getAuthzHeader(request);
            if (jwtToken != null) {
                log.debug("create jwt  token");
                String clientAddr = RemoteAddrUtils.getIpAddr((HttpServletRequest) request);
                return createToken(clientAddr, jwtToken);
            }
        }

        return new UsernamePasswordToken();
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return false;
    }


    /**
     * token是否不为空
     *
     * @param request
     * @param response
     * @return
     */
    protected boolean isLoggedAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = getAuthzHeader(request);
        return authzHeader != null;
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return JwtHelper.formatAuthHeadToken(httpRequest.getHeader(AUTHORIZATION_HEADER));
    }

    /**
     * 创建jwt形式token
     *
     * @param token
     * @return
     */
    private JwtToken createToken(String clientAddr, String token) {
        Claims claims = JwtHelper.parseJWT(token);
        String username = claims.get(USER_ID).toString();
        String clientId = clientAddr;

        return new JwtToken(username, clientId, token);
    }

}