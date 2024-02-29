package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("user")
@Api(value = "User的控制层", tags = "用户相关的接口", description = "UserController")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "注册")
    @ApiImplicitParam(name = "UserDto", value = "用户数据")
    public ResultResponse register(@RequestBody UserDto userDto){
        userService.registerUser(userDto);
        return ResultResponse.success("注册成功");
    }
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "登录")
    @ApiImplicitParam(name = "UserDto", value = "用户数据")
    public ResultResponse login(
            @RequestBody UserDto userDto)
    {
        userService.registerUser(userDto);
        return ResultResponse.success("注册成功");
    }
}
