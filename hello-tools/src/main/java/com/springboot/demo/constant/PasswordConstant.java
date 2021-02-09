package com.springboot.demo.constant;

public class PasswordConstant {
    /**
     * 修改密码随机验证码类型
     */
    public static final String VERIFY_CODE_TYPE_UPD = "verify_code_update";
    /**
     * 重置密码随机验证码类型
     */
    public static final String VERIFY_CODE_TYPE_RESET = "verify_code_reset";

    /**
     * 忘记密码找回邮件验证码类型
     */
    public static final String VERIFY_CODE_TYPE_FORGET = "verify_code_forget";

    /**
     * 密码过期验证码类型
     */
    public static final String VERIFY_CODE_TYPE_EXPIRED = "verify_code_expired";

    /**
     * 首次登陆强制修改密码验证码类型
     */
    public static final String VERIFY_CODE_TYPE_FORCE = "verify_code_force";

    /**
     * 登录 验证码类型
     */
    public static final String VERIFY_CODE_TYPE_LOGIN = "verify_code_login";

    /**
     * 注册 验证码类型
     */
    public static final String VERIFY_CODE_TYPE_REGISTER = "verify_code_register";
}
