package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosong.music.server.domain.dto.UserDto;

/**
 *
 */
public interface UserService extends IService<User> {
    public void registerUser(UserDto userDto);
}
