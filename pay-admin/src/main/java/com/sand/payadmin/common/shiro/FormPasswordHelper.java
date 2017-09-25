package com.sand.payadmin.common.shiro;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * Created by xy on 2017/3/28.
 */
@Component
public class FormPasswordHelper {
    private final RandomNumberGenerator randomNumberGenerator =
            new SecureRandomNumberGenerator();
    private final String algorithmName = "md5";
    private final int hashIterations = 2;

    public String makePassword(String username, String password, String salt) {
        return new SimpleHash(
                algorithmName,
                password, username + ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

}
