package com.zhifeng.kuangchi.module;

/**
 * 登录返回实体类
 */
public class LoginDto {


    /**
     * status : 200
     * msg : success
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQyIsImlhdCI6MTU2NDU1Mzk4NywiZXhwIjoxNTY0NTg5OTg3LCJ1c2VyX2lkIjoiMjc4NzUifQ.KzPbHromH5KuBIJDUfPr5OuV8euILuemwVcGzf0CCQg","mobile":"13202029884","id":"27875"}
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
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQyIsImlhdCI6MTU2NDU1Mzk4NywiZXhwIjoxNTY0NTg5OTg3LCJ1c2VyX2lkIjoiMjc4NzUifQ.KzPbHromH5KuBIJDUfPr5OuV8euILuemwVcGzf0CCQg
         * mobile : 13202029884
         * id : 27875
         */

        private String token;
        private String mobile;
        private String id;
        private int is_nameapi;
        private int pwd_exists;

        public int getIs_nameapi() {
            return is_nameapi;
        }

        public void setIs_nameapi(int is_nameapi) {
            this.is_nameapi = is_nameapi;
        }

        public int getPwd_exists() {
            return pwd_exists;
        }

        public void setPwd_exists(int pwd_exists) {
            this.pwd_exists = pwd_exists;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
