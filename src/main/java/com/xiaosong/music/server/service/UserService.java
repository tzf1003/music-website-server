package com.xiaosong.music.server.service;

import com.xiaosong.music.server.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.enums.UserStateEnum;

/**
 *
 */
public interface UserService extends IService<User> {
    public void registerUser(UserDto userDto);

    public User selectUserByEmail(String email);
    public User selectUserByUsername(String username);
    public String getUserAuthorityInfo(Integer userId);
}
