package com.sand.payadmin.common.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义jwt形式token
 */
public class JwtToken implements AuthenticationToken {

    private String username;
    private String clientId;
    private String jwtToken;

    /**
     * 创建token
     *
     * @param username
     * @param clientId
     * @param jwtToken
     */
    public JwtToken(String username, String clientId, String jwtToken) {
        this.username = username;
        this.clientId = clientId;
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


    /**
     * 判断jwtToken相等
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JwtToken jwtToken1 = (JwtToken) o;

        if (username != null ? !username.equals(jwtToken1.username) : jwtToken1.username != null) return false;
        if (clientId != null ? !clientId.equals(jwtToken1.clientId) : jwtToken1.clientId != null) return false;
        return jwtToken != null ? jwtToken.equals(jwtToken1.jwtToken) : jwtToken1.jwtToken == null;
    }

}
