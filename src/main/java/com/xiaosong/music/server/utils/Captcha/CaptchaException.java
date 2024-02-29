package com.xiaosong.music.server.utils.Captcha;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
