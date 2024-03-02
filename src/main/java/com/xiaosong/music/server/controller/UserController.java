package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.service.UserService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("user")
@Api(value = "User的控制层", tags = "用户相关的接口", description = "UserController")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "注册")
    @ApiImplicitParam(name = "UserDto", value = "用户数据")
    public ResultResponse register(@RequestBody UserDto userDto){
        userService.registerUser(userDto);
        return ResultResponse.success("注册成功");
    }
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "code", value = "验证码"),
            @ApiImplicitParam(name = "userKey", value = "验证码绑定key")
    })
    public ResultResponse login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String code,
            @RequestParam String userKey
    )
    {

        return ResultResponse.success("登录成功");
    }
}
