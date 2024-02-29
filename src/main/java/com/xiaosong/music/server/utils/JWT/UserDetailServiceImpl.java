package com.xiaosong.music.server.utils.JWT;

import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.domain.dto.AccountUser;
import com.xiaosong.music.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        User sysUser = sysUserService.selectUserByAccount(account);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }


        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));

    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Integer userId) {
    	// 实际怎么写以数据表结构为准，这里只是写个例子
        // 角色(比如ROLE_admin)，菜单操作权限(比如sys:user:list)
        String authority = sysUserService.getUserAuthorityInfo(userId);     // 比如ROLE_admin,ROLE_normal,sys:user:list,...

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
