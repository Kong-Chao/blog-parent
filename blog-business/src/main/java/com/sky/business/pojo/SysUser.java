package com.sky.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author kc
 * @create 2023-05-05 20:52
 */
@Data
@TableName("ms_sys_user")
public class SysUser {
    @Id
    private Long id;

    /**
     * 账户
     */
    @TableField("account")
    private String account;

    /**
     * 是否是管理员
     */
    @TableField("admin")
    private Byte admin;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 注册时间
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Byte deleted;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 最后登录时间
     */
    @TableField("last_login")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Data lastLogin;

    /** 手机号码 */
    @TableField("mobile_phone_number")
    private String mobilePhoneNumber;

    /** 昵称 */
    @TableField("nickname")
    private String nickname;

    /** 密码 */
    @TableField("password")
    private String password;

    /** 加盐值 */
    @TableField("salt")
    private String salt;

    /** 状态 */
    @TableField("status")
    private String status;


}
