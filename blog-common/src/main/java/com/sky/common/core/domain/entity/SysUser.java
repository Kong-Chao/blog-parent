package com.sky.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sky.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * @author admin
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity
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
    private String username;

    @TableField("nick_name")
    /** 用户昵称 */
    private String nickName;

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
    private  List<SysRole> roles;

    private  List<SysMenu> permissions;

    @TableField(exist = false)
    /** 角色组 */
    private  Long[] roleIds;

    @TableField(exist = false)
    /** 岗位组 */
    private  Long[] postIds;

    @TableField(exist = false)
    /** 角色ID */
    private  Long roleId;

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

}
