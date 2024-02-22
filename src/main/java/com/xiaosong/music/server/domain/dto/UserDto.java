package com.xiaosong.music.server.domain.dto;

import lombok.Data;

@Data
public class UserDto {
    String account;
    String username;
    String email;
    String password;
}
