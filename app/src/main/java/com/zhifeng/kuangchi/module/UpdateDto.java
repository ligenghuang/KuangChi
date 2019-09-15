package com.zhifeng.kuangchi.module;

public class UpdateDto {


    /**
     * status : 200
     * msg : success
     * data : {"android":10,"url":"http://www.imnebula.com/Nebula.apk"}
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
         * android : 10
         * url : http://www.imnebula.com/Nebula.apk
         */

        private int android;
        private String url;

        public int getAndroid() {
            return android;
        }

        public void setAndroid(int android) {
            this.android = android;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url == null ? "" : url;
        }
    }
}
