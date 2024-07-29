package com.sky.common.core.domain.entity;


import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sky.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class UserBO extends BaseEntity implements UserDetails
{
    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    /** 用户ID */
    private Long userId;

    @TableField("dept_id")
    /** 部门ID */
    private Long deptId;

    @TableField("user_name")
    /** 用户账号 */
    private String userName;

    @TableField("nick_name")
    /** 用户昵称 */
    private String nickName;

    private String userType;

    @TableField("email")
    /** 用户邮箱 */
    private String email;

    @TableField("phonenumber")
    /** 手机号码 */
    private String phonenumber;

    @TableField("sex")
    /** 用户性别 */
    private String sex;

    @TableField("avatar")
    /** 用户头像 */
    private String avatar;

    @TableField("password")
    /** 密码 */
    private String password;

    @TableField("status")
    /** 帐号状态（0正常 1停用） */
    private String status;

    @TableField("del_flag")
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @TableField("login_ip")
    /** 最后登录IP */
    private String loginIp;

    @TableField("login_date")
    /** 最后登录时间 */
    private Date loginDate;

    @TableField(exist = false)
    /** 角色对象 */
    private Set<SysRole> roles;

    private Set<SysMenu> permissions;

    @TableField(exist = false)
    /** 角色组 */
    private  Long[] roleIds;

    @TableField(exist = false)
    /** 岗位组 */
    private  Long[] postIds;

    @TableField(exist = false)
    /** 角色ID */
    private  Long roleId;

    public UserBO()
    {

    }

    public UserBO(Long userId)
    {
        this.userId = userId;
    }


    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

   // @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

   // @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    // 账户是否未过期,过期无法验证
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 指定用户是否解锁,锁定的用户无法进行身份验证
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
    @JSONField(serialize = false)
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
