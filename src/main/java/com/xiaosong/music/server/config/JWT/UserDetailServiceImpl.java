package com.xiaosong.music.server.config.JWT;

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

/**
 * 用户详细信息服务实现类
 * 实现Spring Security的UserDetailsService接口，用于加载用户认证信息
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService sysUserService; // 注入用户服务

    /**
     * 根据用户名加载用户信息
     * @param account 用户账号
     * @return 返回用户认证信息
     * @throws UsernameNotFoundException 当用户不存在时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        // 调用UserService查询用户信息
        User sysUser = sysUserService.selectUserByUsername(account);
        if (sysUser == null) {
            // 用户不存在，抛出异常
            throw new UsernameNotFoundException("用户不存在");
        }

        // 返回包含用户信息和权限的AccountUser对象
        return new AccountUser(sysUser.getId().longValue(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
    }

    /**
     * 获取用户权限信息
     * 根据用户ID获取用户的角色和权限信息
     * @param userId 用户ID
     * @return 用户的权限信息列表
     */
    public List<GrantedAuthority> getUserAuthority(Integer userId) {
        // 示例方法，实际实现应根据具体数据表结构确定
        // 获取用户的权限字符串，如"ROLE_admin,ROLE_normal"
        String authority = sysUserService.getUserAuthorityInfo(userId);

        // 将逗号分隔的权限信息转换为GrantedAuthority对象列表
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
