package com.xiaosong.music.server.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserInfoDto {
    private Integer id;
    /**
     * 用户名，用于显示
     */
    private String username;

    /**
     * 头像链接
     */
    private String avatar;

    /**
     * 用户信息
     */
    private String description;

    /**
     * 邮件
     */
    private String email;
    /**
     * 权限
     * ADMIN 管理员
     * USER 普通用户
     */
    private String role;
}
