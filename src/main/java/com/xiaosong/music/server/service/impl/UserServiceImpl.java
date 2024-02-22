package com.xiaosong.music.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.domain.dto.UserDto;
import com.xiaosong.music.server.enums.UserStateEnum;
import com.xiaosong.music.server.error.BizException;
import com.xiaosong.music.server.service.UserService;
import com.xiaosong.music.server.mapper.UserMapper;
import com.xiaosong.music.server.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{
    @Autowired
    UserMapper userMapper;
    public void registerUser(UserDto userDto){
        //判断 email 格式是否正确
        if(!StringUtil.emailValidator(userDto.getEmail())){
            throw  new BizException("-1","邮箱格式不正确！");
        }
        //判断email是否重复
        if (selectUserByEmail(userDto.getEmail())!=null){
            throw  new BizException("-1","该邮箱已存在！");
        }
        //判断账号是否重复
        if (selectUserByAccount(userDto.getAccount())!=null){
            throw  new BizException("-1","该账号已存在！");
        }
        //判断账号密码是否合规
        if(!StringUtil.isAccountValid(userDto.getAccount()) ||
                !StringUtil.isPasswordValid(userDto.getPassword())){
        //账号或密码不合规。
            throw  new BizException("-1","账号或密码不符合规定！");
        }

        //实例化 数据库 数据
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setAccount(userDto.getAccount());
        //使用md5编码密码
        String md5Password = StringUtil.md5(userDto.getPassword());
        user.setPassword(md5Password);
        //设置状态为未验证邮件。
        user.setState(UserStateEnum.NO_VERIFICATION_EMAIL);
        //实例化数据
        userMapper.insert(user);
    }
    public User selectUserByEmail(String email){
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        QueryWrapper userQueryWrapper= new QueryWrapper();
        userQueryWrapper.allEq(userParams);
        User user= userMapper.selectOne(userQueryWrapper);
        return user;
    }
    public User selectUserByAccount(String account){
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("account", account);
        QueryWrapper userQueryWrapper= new QueryWrapper();
        userQueryWrapper.allEq(userParams);
        User user= userMapper.selectOne(userQueryWrapper);
        return user;
    }
}




