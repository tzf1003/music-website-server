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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;
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
        userInfoDto.setRole(user.getRole());
        return ResultResponse.success(userInfoDto);
    }
//    修改用户信息
    @PutMapping("/user/update")
    @ApiOperation(value = "用户更新自己的信息", notes = "Put")
    public ResultResponse put(@RequestHeader("Authorization") String authHeader,@RequestBody User user) {
        //读取jwt中用户信息
        Claims claims = jwtUtils.getClaimsByToken(authHeader);
        String username = claims.getSubject();

        User olduser = userService.selectUserByUsername(username);
        //如果用户没有输入密码，则不更新密码。
        if (user.getPassword()==null || "".equals(user.getPassword())){
            user.setPassword(olduser.getPassword());
        }else {
            //密码加密存储
            //使用BCryptPasswordEncoder加密编码密码
            String RSAPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(RSAPassword);
        }
        //此处判断邮箱不是脱敏后提交的信息，直接提交。
        if(StringUtil.maskEmail(olduser.getEmail()).equals(user.getEmail())){
            //提交的参数等于修改后的参数，则复制原始给提交的参数
            user.setEmail(olduser.getEmail());
        }
        //不相信用户输入的ID,改为从token中读取
        user.setId(olduser.getId());
        //用户不能修改自己的ROLE
        user.setRole(olduser.getRole());
        //用户不能修改自己的状态
        user.setState(olduser.getState());
        boolean b = userService.updateById(user);
        if (b){
            return ResultResponse.success("","更新成功！");
        }else {
            return ResultResponse.error("更新失败！");
        }
    }
}
