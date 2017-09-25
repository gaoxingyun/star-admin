package com.sand.payadmin.common.config;

import com.sand.payadmin.common.jwt.JwtRealm;
import com.sand.payadmin.common.shiro.FormRealm;
import com.sand.payadmin.common.shiro.JWTOrFormAuthenticationFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * 权限设置
 * Created by xy on 2017/3/27.
 */
@Configuration
public class ShiroConfig {

    private static Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(FormRealm formRealm, JwtRealm jwtRealm) {
        Set<Realm> realms = new HashSet<>();
        realms.add(formRealm);
        realms.add(jwtRealm);

        SecurityManager securityManager = new DefaultWebSecurityManager(realms);

        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }


    /**
     * 缓存控制器
     */
    @Bean
    public CacheManager cacheManager() {
        return null;
    }

    /**
     * 拦截器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setSuccessUrl("/");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
        //filterChainDefinitionManager.put("/logout", "logout");
        filterChainDefinitionManager.put("/login", "anon");
        filterChainDefinitionManager.put("/js/**", "anon");
        filterChainDefinitionManager.put("/css/**", "anon");
        filterChainDefinitionManager.put("/fonts/**", "anon");
        filterChainDefinitionManager.put("/image/**", "anon");
        //filterChainDefinitionManager.put("/api/user/login/**", "anon");
        //filterChainDefinitionManager.put("/api/**", "anon");
        filterChainDefinitionManager.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("authc", new JWTOrFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        return shiroFilterFactoryBean;
    }

}
