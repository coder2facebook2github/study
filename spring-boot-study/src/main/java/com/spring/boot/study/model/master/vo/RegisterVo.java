package com.spring.boot.study.model.master.vo;


import com.spring.boot.study.common.validate.group.GroupA;
import com.spring.boot.study.common.validate.group.GroupB;
import com.spring.boot.study.model.master.SysUser;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@GroupSequence({GroupA.class, GroupB.class, RegisterVo.class})
public class RegisterVo extends SysUser implements Serializable {
    private static final long serialVersionUID = -8350901206738304265L;

    @NotBlank(message = "手机号不能为空", groups = {GroupA.class, GroupB.class})
    private String mobile;

    @NotBlank(message = "邮箱不能为空", groups = {GroupA.class})
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String validateCode;

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
