package com.zhifeng.kuangchi.module.post;

public class MoblieLoginPwdPost {
    private String password;
    private String repassword;
    private String phone;
    private String verify_code;

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }

    public String getRepassword() {
        return repassword == null ? "" : repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword == null ? "" : repassword;
    }

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
}
