package com.zhifeng.kuangchi.module;

public class MyInfoDto {


    /**
     * status : 200
     * msg : success
     * data : {"realname":"默认昵称","id":27880,"mobile":"15817046397","address":"eNcjvIP4OhEckGyvuBljayJyCkD8jB","levelname":null,"avatar":"http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png","createtime":1567146416,"level_name":"普通会员","reg_time":"2019-08-30~14:26:56"}
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
         * id : 27880
         * mobile : 15817046397
         * address : eNcjvIP4OhEckGyvuBljayJyCkD8jB
         * levelname : null
         * avatar : http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png
         * createtime : 1567146416
         * level_name : 普通会员
         * reg_time : 2019-08-30~14:26:56
         */

        private String realname;
        private int id;
        private String mobile;
        private String address;
        private Object levelname;
        private String avatar;
        private int createtime;
        private String level_name;
        private String reg_time;
        private int is_vip;
        private int is_nameapi;

        public int getIs_nameapi() {
            return is_nameapi;
        }

        public void setIs_nameapi(int is_nameapi) {
            this.is_nameapi = is_nameapi;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getLevelname() {
            return levelname;
        }

        public void setLevelname(Object levelname) {
            this.levelname = levelname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }
    }
}
