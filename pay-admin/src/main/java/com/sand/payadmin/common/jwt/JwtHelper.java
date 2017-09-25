package com.sand.payadmin.common.jwt;


import com.sand.payadmin.common.contant.ShiroContant;
import com.sand.payadmin.common.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtHelper {

    private static Logger logger = Logger.getLogger(JwtHelper.class);

    private final static String base64Secret = ShiroContant.JWT_SECRET;


    /**
     * 去除request头'Bearer '数据
     *
     * @return
     */
    public static String formatAuthHeadToken(String authHeader) {
        return authHeader.substring(7);
    }

    /**
     * 解析jwt
     *
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {

            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception e) {
            logger.info("解析token失败", e);
            throw new AuthException(e);
        }
    }


    /**
     * 生成jwt
     *
     * @return
     */
    public static String createJwt(String jwtId, String username, String clientId) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim(ShiroContant.USER_ID, username)
                .claim(ShiroContant.CLIENT_ID, clientId)
                .signWith(signatureAlgorithm, signingKey)
                .setId(jwtId);
        //添加Token过期时间
        if (ShiroContant.TLT_MILLIS >= 0) {
            long expMillis = nowMillis + ShiroContant.TLT_MILLIS;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }
}