package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class MessageListDto {

    /**
     * status : 200
     * msg : success
     * data : [{"id":2,"user_id":27762,"description":"你委托购买的存储空间审核不通过，请查收。","order_id":3169,"title":"订单消息","type":2,"add_time":1567065840,"picture":"goods/20190828156696642086455.png","goods_name":"矿机","spec_key_name":"规格:全网16T,1T:16T","time":"2019-08-29 16:04:00","img":"http://orepool.com/upload/images/goods/20190828156696642086455.png"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * user_id : 27762
         * description : 你委托购买的存储空间审核不通过，请查收。
         * order_id : 3169
         * title : 订单消息
         * type : 2
         * add_time : 1567065840
         * picture : goods/20190828156696642086455.png
         * goods_name : 矿机
         * spec_key_name : 规格:全网16T,1T:16T
         * time : 2019-08-29 16:04:00
         * img : http://orepool.com/upload/images/goods/20190828156696642086455.png
         */

        private int id;
        private int user_id;
        private String description;
        private int order_id;
        private String title;
        private int type;
        private int add_time;
        private String picture;
        private String goods_name;
        private String spec_key_name;
        private String time;
        private String img;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getDescription() {
            return description == null ? "" : description;
        }

        public void setDescription(String description) {
            this.description = description == null ? "" : description;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
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

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getPicture() {
            return picture == null ? "" : picture;
        }

        public void setPicture(String picture) {
            this.picture = picture == null ? "" : picture;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name == null ? "" : goods_name;
        }

        public String getSpec_key_name() {
            return spec_key_name == null ? "" : spec_key_name;
        }

        public void setSpec_key_name(String spec_key_name) {
            this.spec_key_name = spec_key_name == null ? "" : spec_key_name;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time == null ? "" : time;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }
    }
}
