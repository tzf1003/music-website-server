package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.config.JWT.JwtUtils;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.domain.dto.UserInfoDto;
import com.xiaosong.music.server.service.UserService;

import com.xiaosong.music.server.utils.StringUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "User的控制层", tags = "用户相关的接口", description = "UserController")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册", notes = "注册")
    @ApiImplicitParam(name = "UserDto", value = "用户数据")
    public ResultResponse register(@RequestBody UserDto userDto){
        userService.registerUser(userDto);
        return ResultResponse.success(null,"注册成功");
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

        return ResultResponse.error("登录失败");
    }
    //根据用户名判断是否注册
    @ApiOperation(value = "根据用户名判断是否注册", notes = "判断")
    @GetMapping("/user/isRegisterByUsername")
    @ApiImplicitParam(name = "username", value = "用户名")
    public ResultResponse isRegisterByUsername(@RequestParam String username) {
        boolean isRegister=false;
        if (userService.selectUserByUsername(username)!=null){
            isRegister=true;
        }
        return ResultResponse.success(isRegister);
    }
    //根据邮箱判断是否已注册
    @ApiOperation(value = "根据邮件判断是否注册", notes = "判断")
    @GetMapping("/user/isRegisterByEmail")
    @ApiImplicitParam(name = "email", value = "email")
    public ResultResponse isRegisterByEmail(@RequestParam String email) {
        boolean isRegister=false;
        if (userService.selectUserByEmail(email)!=null){
            isRegister=true;
        }
        return ResultResponse.success(isRegister);
    }
    //获取用户信息
    @ApiOperation(value = "获取用户信息", notes = "获取")
    @GetMapping("/user/info")
//    @ApiImplicitParam(name = "authHeader", value = "token")
    public ResultResponse userInfo(@RequestHeader("Authorization") String authHeader) {
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();

        User user = userService.selectUserByUsername(username);
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setEmail(StringUtil.maskEmail(user.getEmail()));
        userInfoDto.setDescription(user.getDescription());
        userInfoDto.setAvatar(user.getAvatar());
        return ResultResponse.success(userInfoDto);

    }
}
