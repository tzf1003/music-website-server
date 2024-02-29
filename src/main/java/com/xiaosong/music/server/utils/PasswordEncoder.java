package com.xiaosong.music.server.utils;

import cn.hutool.crypto.digest.BCrypt;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
public class PasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 接收到的前端的密码
        String pwd = rawPassword.toString();
        // 进行rsa解密
        try {
            pwd = RsaUtils.decryptByPrivateKey(pwd);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
        if (encodedPassword != null && encodedPassword.length() != 0) {
            return BCrypt.checkpw(pwd, encodedPassword);
        } else {
            return false;
        }
    }
}
