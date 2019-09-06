package com.zhifeng.kuangchi.module;

/**
 * 公告详情实体类
 */
public class NoticeDetailDto {


    /**
     * status : 200
     * msg : success
     * data : {"id":5,"picture":"http://orepool.zhifengwangluo.com","title":"百度（测试数据）","type":2,"urllink":"https://www.baidu.com/","desc":"<p><a href=\"https://www.baidu.com/\">https://www.baidu.com/<\/a><\/p>","create_time":"2019-08-17","status":1}
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
         * id : 5
         * picture : http://orepool.zhifengwangluo.com
         * title : 百度（测试数据）
         * type : 2
         * urllink : https://www.baidu.com/
         * desc : <p><a href="https://www.baidu.com/">https://www.baidu.com/</a></p>
         * create_time : 2019-08-17
         * status : 1
         */

        private int id;
        private String picture;
        private String title;
        private int type;
        private String urllink;
        private String desc;
        private String create_time;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPicture() {
            return picture == null ? "" : picture;
        }

        public void setPicture(String picture) {
            this.picture = picture == null ? "" : picture;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title == null ? "" : title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrllink() {
            return urllink == null ? "" : urllink;
        }

        public void setUrllink(String urllink) {
            this.urllink = urllink == null ? "" : urllink;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc == null ? "" : desc;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time == null ? "" : create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
