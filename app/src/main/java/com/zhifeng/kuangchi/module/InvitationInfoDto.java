package com.zhifeng.kuangchi.module;
/**
  *
  * @ClassName:     邀请链接实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 15:10
  * @Version:        1.0
 */

public class InvitationInfoDto {


    /**
     * status : 200
     * msg : success
     * data : {"url":"http://orepool.zhifengwangluo.com/Ewm/27880-qrcodee.png","avatar":"http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png","realname":"默认昵称","id":"027880","address":"eNcjvIP4OhEckGyvuBljayJyCkD8jB"}
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
         * url : http://orepool.zhifengwangluo.com/Ewm/27880-qrcodee.png
         * avatar : http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png
         * realname : 默认昵称
         * id : 027880
         * address : eNcjvIP4OhEckGyvuBljayJyCkD8jB
         */

        private String url;
        private String avatar;
        private String realname;
        private String id;
        private String address;
        private String reg_url;

        public String getReg_url() {
            return reg_url == null ? "" : reg_url;
        }

        public void setReg_url(String reg_url) {
            this.reg_url = reg_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
