package com.xiaosong.music.server.config.JWT;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类。
 * 用于生成和解析JWT Token，以及判断Token是否过期。
 * 通过application配置文件中的music.jwt前缀来配置属性。
 */
@Data
@Component
@ConfigurationProperties(prefix = "music.jwt")
public class JwtUtils {

    private long expire; // Token过期时间，单位秒。
    private String secret; // JWT加密解密密钥。
    private String header; // 存放Token的HTTP头部标识。

    /**
     * 生成JWT Token。
     *
     * @param username 用户名
     * @return 生成的JWT Token字符串
     */
    public String generateToken(String username) {
        Date nowDate = new Date(); // 当前时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire); // 过期时间

        // 构建JWT Token
        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 设置头部信息
                .setSubject(username) // 设置Token主题信息
                .setIssuedAt(nowDate) // 设置Token的签发时间
                .setExpiration(expireDate) // 设置Token的过期时间
                .signWith(SignatureAlgorithm.HS512, secret) // 签名算法以及密钥
                .compact();
    }

    /**
     * 解析JWT Token。
     *
     * @param jwt 需要解析的JWT Token字符串
     * @return 返回解析后的Claims对象，如果解析失败返回null
     */
    public Claims getClaimsByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret) // 设置解密密钥
                    .parseClaimsJws(jwt) // 解析Token
                    .getBody(); // 获取Token主体内容
        } catch (Exception e) {
            return null; // 解析失败，返回null
        }
    }

    /**
     * 判断JWT Token是否已经过期。
     *
     * @param claims 从JWT Token解析出来的Claims对象
     * @return 如果Token已过期返回true，否则返回false
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date()); // 判断Token过期时间是否在当前时间之前
    }

}
