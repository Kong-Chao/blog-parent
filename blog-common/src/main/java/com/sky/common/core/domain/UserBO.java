package com.sky.common.core.domain;


import com.sky.common.core.domain.entity.SysMenu;
import com.sky.common.core.domain.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBO implements UserDetails
{
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long deptId;
    private String username;
    private String nickName;
    private String userType;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    private String password;
    private String status;
    private String delFlag;
    private String loginIp;
    private Date loginDate;
    private Set<SysRole> roles;
    private Set<SysMenu> permissions;

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }

        return permissions.stream()
                .filter(menu -> menu.getPerms() != null && !menu.getPerms().trim().isEmpty())
                .map(menu -> new SimpleGrantedAuthority(menu.getPerms()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期,过期无法验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 指定用户是否解锁,锁定的用户无法进行身份验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否可用 ,禁用的用户不能身份验证
    @Override
    public boolean isEnabled() {
        return true;
    }
}
