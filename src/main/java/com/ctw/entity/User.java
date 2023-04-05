package com.ctw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @TableName user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId("id")
    private Object id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户身份，0普通用户，1管理者，2开发者
     */
    private Integer role;

    /**
     * 年龄
     */
    private Integer age;

    private static final long serialVersionUID = 1L;

}