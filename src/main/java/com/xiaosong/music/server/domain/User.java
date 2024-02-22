package com.xiaosong.music.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.xiaosong.music.server.enums.UserStateEnum;
import lombok.Data;

/**
 * 
 * @TableName xs_user
 */
@TableName(value ="xs_user")
@Data
public class User implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名，用于显示
     */
    private String username;

    /**
     * 账号，用于登录
     */
    private String account;

    /**
     * 密码(md5算法)
     */
    private String password;

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
     * 邮件是否验证状态：
        0、未验证
        1、已验证
        2、被禁用
     */
    private UserStateEnum state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}