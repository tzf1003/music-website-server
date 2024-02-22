package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("user")
@Tag(name = "UserController", description = "User的控制层")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    @Operation(summary = "注册")
    public ResultResponse register(
            @Parameter(name = "UserDto", description = "用户数据")
            @RequestBody UserDto userDto)
    {
            userService.registerUser(userDto);
    return ResultResponse.success("注册成功");
    }
}
