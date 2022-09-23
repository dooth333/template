package com.wenc.template.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenc
 * @since 2022-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 密码附加值
     */
    @TableField("salt")
    private String salt;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 电话号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 类型:0-普通用户; 1-超级管理员; 2-版主;
     */
    @TableField("type")
    private Integer type;

    /**
     * 状态:0-未激活; 1-已激活;
     */
    @TableField("status")
    private Integer status;

    /**
     * 激活码
     */
    @TableField("activation_code")
    private String activationCode;

    /**
     * 头像url
     */
    @TableField("header_url")
    private String headerUrl;

    /**
     * 注册时间
     */
    @TableField("create_time")
    private Date createTime;


}
