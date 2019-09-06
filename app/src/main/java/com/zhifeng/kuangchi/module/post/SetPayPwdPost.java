package com.zhifeng.kuangchi.module.post;
/**
  *
  * @ClassName:     设置支付密码请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 9:25
  * @Version:        1.0
 */

public class SetPayPwdPost {
    private String phone;
    private String verify_code;
    private String password;

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone;
    }

    public String getVerify_code() {
        return verify_code == null ? "" : verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code == null ? "" : verify_code;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }
}
