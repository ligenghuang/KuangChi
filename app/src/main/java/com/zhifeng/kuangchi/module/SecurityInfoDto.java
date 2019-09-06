package com.zhifeng.kuangchi.module;
/**
  *
  * @ClassName:     安全中心信息实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 16:25
  * @Version:        1.0
 */

public class SecurityInfoDto {


    /**
     * status : 200
     * msg : success
     * data : {"realname":"默认昵称","mobile":"15817046397","pwd":null,"phone":"158****6397"}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * realname : 默认昵称
         * mobile : 15817046397
         * pwd : null
         * phone : 158****6397
         */

        private String realname;
        private String mobile;
        private String pwd;
        private String phone;
        private int is_vip;
        private int is_nameapi;

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getIs_nameapi() {
            return is_nameapi;
        }

        public void setIs_nameapi(int is_nameapi) {
            this.is_nameapi = is_nameapi;
        }

        public String getRealname() {
            return realname == null ? "" : realname;
        }

        public void setRealname(String realname) {
            this.realname = realname == null ? "" : realname;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile == null ? "" : mobile;
        }

        public String getPwd() {
            return pwd == null ? "" : pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd == null ? "" : pwd;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? "" : phone;
        }
    }
}
