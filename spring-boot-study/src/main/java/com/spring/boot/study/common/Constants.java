package com.spring.boot.study.common;

public interface Constants {
    /**
     * redis key 短信验证码
     */
    String MESSAGE_CODE = "MESSAGE_CODE:";

    /**
     * redis key 系统配置
     */
    String SYS_CONFIGURATIONS = "SYS_CONFIGURATIONS";

    /**
     * redis key 用户token
     */
    String USER_TOKEN = "USER_TOKEN:";

    /**
     * 阿里大于 短信验证码template_code
     */
    String IDENTIFY_TEMPLATE_CODE = "SMS_128890620";

    /**
     * 阿里大于 短信签名
     */
    String SIGN_NAME = "闪电";

    String SUCCESS = "success";

    String MESSAGE = "message";

    String AUTHORIZATION = "Authorization";

    String MAIL_CODE = "mail_code:";

    String IMAGE_TOKEN = "IMAGE_TOKEN:";
}
