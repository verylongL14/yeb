package com.jzq.server.config.security;


import com.jzq.server.config.custom.CustomFilter;
import com.jzq.server.config.custom.CustomUrlDecisionManger;
import com.jzq.server.pojo.Admin;
import com.jzq.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomUrlDecisionManger customUrlDecisionManger;



    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置放行访问
         web.ignoring().antMatchers(
                 "/api/login",
                 "/api/logout",
                 "/css/**",
                 "/js/**",
                 "/index.html",
                 "/favicon.ico",
                 "/doc.html",
                 "/webjars/**",
                 "/swagger-resources/**",
                 "/v2/api-docs/**",
                 "/api/captcha",
                 "/api/export/**",
                 "/api/import/**",
                 "/ws/**"

         );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用JWT，不需要csrf
        http.csrf()
                // 禁用csrf
                .disable()
                // 基于token， 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 允许登录访问
                //.antMatchers("/login", "/logout")
                //.permitAll()
                // 除了上面的请求，其他的都要认证
                .anyRequest()
                .authenticated()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManger);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()
                // 禁用缓存
                .headers()
                .cacheControl();

        // 添加  JWT 登录授权过滤器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权和未登录结果的返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandel())
                .authenticationEntryPoint(restAuthorizationEnrtyPoint());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {  // 重写的了返回的参数(Admin)
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (admin != null) {
                // 设置一下权限
                admin.setRoles(adminService.getRoles(admin.getId()));
                return admin;
            }

            throw new UsernameNotFoundException("用户名或密码不正确");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter() {
        return new JwtAuthencationTokenFilter();
    }

    @Bean
    public RestAuthorizationEnrtyPoint restAuthorizationEnrtyPoint() {
        return new RestAuthorizationEnrtyPoint();
    }

    @Bean
    public RestfulAccessDeniedHandel restfulAccessDeniedHandel() {
        return new RestfulAccessDeniedHandel();
    }
}
