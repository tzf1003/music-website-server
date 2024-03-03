package com.xiaosong.music.server.config.Captcha;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.code.kaptcha.Constants;
import com.xiaosong.music.server.config.JWT.LoginFailureHandler;
import com.xiaosong.music.server.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 验证码校验过滤器，用于登录请求的验证码校验。
 * 继承自Spring框架的OncePerRequestFilter，确保每次请求只通过一次filter，不需要重复执行。
 */
@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil; // 注入Redis工具类，用于验证码的存取

    @Autowired
    private LoginFailureHandler loginFailureHandler; // 注入登录失败处理器

    /**
     * 过滤器的核心方法，用于校验验证码的正确性。
     *
     * @param httpServletRequest 请求对象
     * @param httpServletResponse 响应对象
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();
        if ("/login".equals(url) && "POST".equals(httpServletRequest.getMethod())) {
            // 仅对POST方式的/login请求进行验证码校验
            try {
                validate(httpServletRequest); // 执行验证码校验逻辑
            } catch (CaptchaException e) {
                // 校验失败，调用登录失败处理器
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return; // 校验失败后，直接返回，不再调用后续的过滤器链
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse); // 校验通过，继续执行过滤器链
    }

    /**
     * 校验验证码的具体逻辑。
     *
     * @param httpServletRequest 请求对象，用于获取验证码及用户key
     */
    private void validate(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("userKey");

        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码错误");
        }

        if (!code.equals(redisUtil.hget(Constants.KAPTCHA_SESSION_KEY, key))) {
            throw new CaptchaException("验证码错误");
        }

        // 若验证码正确，执行以下语句
        // 一次性使用
        redisUtil.hdel(Constants.KAPTCHA_SESSION_KEY, key);
    }
}
