package com.sand.payadmin.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by xy on 2017/3/27.
 */
@Component
public class ShiroSecurity {
    @ModelAttribute
    public Subject subject() {
        return SecurityUtils.getSubject();
    }

}
