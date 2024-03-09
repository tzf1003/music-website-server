package com.xiaosong.music.server.config.JWT;

import cn.hutool.core.util.StrUtil;
import com.xiaosong.music.server.config.Captcha.CaptchaException;
import com.xiaosong.music.server.config.Captcha.CaptchaFilter;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器
 * 继承自BasicAuthenticationFilter，用于解析并验证JWT Token的有效性，并为通过验证的请求设置认证信息
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserService userService;
    /**
     * 构造函数，注入AuthenticationManager
     * @param authenticationManager 认证管理器
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 实现JWT认证流程
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param chain 过滤器链
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());

        // 如果请求头中没有JWT则放行，后续过滤器会处理身份验证
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        //清除掉头部的Bearer
        if (jwt.startsWith("Bearer ")){
            jwt = jwt.substring(7);
        }
        // 解析JWT获取Claims
        Claims claim = jwtUtils.getClaimsByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token 已过期");
        }

        // 从JWT获取用户账号，并加载用户权限信息
        String account = claim.getSubject();
        User user = userService.selectUserByUsername(account);

        // 构建认证信息并设置到SecurityContext中，实现自动登录
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account, null, userDetailService.getUserAuthority(user.getId()));
        SecurityContextHolder.getContext().setAuthentication(token);

        // 继续执行下一个过滤器
        chain.doFilter(request, response);
    }
}
