package com.jzq.server.config.security;


import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  JWT登录授权过滤器
 */
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenHeader);  // 获取token值
        // 存在token  （获取的token不为空，且token前面携带的表示是我们设置的标识）
        if (authHeader!=null && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring(tokenHead.length());  // 截取下除了头部标识以后的token
            String username = jwtTokenUtil.getUserNameFromToken(authToken);   // 通过token就可以拿到用户名(解析荷载部分)
            // 看看username存在，但是未登录(就是检测是不是设置在security全局中)
            if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                // 登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 验证token是否有效，重新设置用户对象
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        // 最后放行
        filterChain.doFilter(request,response);
    }
}
