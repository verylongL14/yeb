package com.jzq.server.config.custom;

import com.jzq.server.pojo.Menu;
import com.jzq.server.pojo.Role;
import com.jzq.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url，分析请求所需角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    // 匹配url的实例
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取请求的Url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        // 获取菜单信息
        List<Menu> menuList = menuService.getMenusWithRole();
        for (Menu menu : menuList) {
            // 逐个与url进行匹配
            if (antPathMatcher.match(menu.getUrl(),requestUrl)) { // 注意这里，第一个是匹配规则，位置不要颠倒
                // 如果匹配成功(把这个路径对应的角色封装到一个数组)
                String[] strArray = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(strArray);
            }
        }
        // 如果匹配不上，就默认返回登录权限的路径
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
