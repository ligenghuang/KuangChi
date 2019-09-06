package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @ClassName:     客服对话列表实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 11:06
  * @Version:        1.0
 */

public class CustomerServiceListDto {


    /**
     * status : 200
     * msg : success
     * data : [{"id":13,"user_id":27880,"send_name":"默认昵称","send_avatar":"http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png","send_id":27880,"recive_name":"客服小小熊","recive_avatar":"https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws","recive_id":0,"content":"哈哈","create_time":1567395389,"send_type":1},{"id":14,"user_id":27880,"send_name":"默认昵称","send_avatar":"http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png","send_id":27880,"recive_name":"客服小小熊","recive_avatar":"https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws","recive_id":0,"content":"哈哈","create_time":1567395456,"send_type":1},{"id":15,"user_id":27880,"send_name":"默认昵称","send_avatar":"http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png","send_id":27880,"recive_name":"客服小小熊","recive_avatar":"https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws","recive_id":0,"content":"哈哈","create_time":1567395743,"send_type":1},{"id":18,"user_id":27880,"send_name":"默认昵称","send_avatar":"http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png","send_id":27880,"recive_name":"客服小小熊","recive_avatar":"https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws","recive_id":0,"content":"027880","create_time":1567499584,"send_type":1},{"id":49,"user_id":27880,"send_name":"默认","send_avatar":"http://orepool.zhifengwangluo.com/upload/images/tou/20190904156759802494896.png","send_id":27880,"recive_name":"客服小小熊","recive_avatar":"https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws","recive_id":0,"content":"很刚哈更会哈更很刚哈更很刚哈更很刚哈更很刚哈更","create_time":1567680293,"send_type":1},{"id":59,"user_id":27880,"send_name":"默认","send_avatar":"http://orepool.zhifengwangluo.com/upload/images/tou/20190904156759802494896.png","send_id":27880,"recive_name":"客服小小熊","recive_avatar":"https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws","recive_id":0,"content":"哈哈","create_time":1567733924,"send_type":1}]
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
         * id : 13
         * user_id : 27880
         * send_name : 默认昵称
         * send_avatar : http://orepool.zhifengwangluo.com/static/images/headimg/20190711156280864771502.png
         * send_id : 27880
         * recive_name : 客服小小熊
         * recive_avatar : https://dpic.tiankong.com/3w/h7/QJ6961899400.jpg?x-oss-process=style/670ws
         * recive_id : 0
         * content : 哈哈
         * create_time : 1567395389
         * send_type : 1
         */

        private int id;
        private int user_id;
        private String send_name;
        private String send_avatar;
        private int send_id;
        private String recive_name;
        private String recive_avatar;
        private int recive_id;
        private String content;
        private long create_time;
        private int send_type;

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

        public String getSend_name() {
            return send_name == null ? "" : send_name;
        }

        public void setSend_name(String send_name) {
            this.send_name = send_name == null ? "" : send_name;
        }

        public String getSend_avatar() {
            return send_avatar == null ? "" : send_avatar;
        }

        public void setSend_avatar(String send_avatar) {
            this.send_avatar = send_avatar == null ? "" : send_avatar;
        }

        public int getSend_id() {
            return send_id;
        }

        public void setSend_id(int send_id) {
            this.send_id = send_id;
        }

        public String getRecive_name() {
            return recive_name == null ? "" : recive_name;
        }

        public void setRecive_name(String recive_name) {
            this.recive_name = recive_name == null ? "" : recive_name;
        }

        public String getRecive_avatar() {
            return recive_avatar == null ? "" : recive_avatar;
        }

        public void setRecive_avatar(String recive_avatar) {
            this.recive_avatar = recive_avatar == null ? "" : recive_avatar;
        }

        public int getRecive_id() {
            return recive_id;
        }

        public void setRecive_id(int recive_id) {
            this.recive_id = recive_id;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content == null ? "" : content;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getSend_type() {
            return send_type;
        }

        public void setSend_type(int send_type) {
            this.send_type = send_type;
        }
    }
}
