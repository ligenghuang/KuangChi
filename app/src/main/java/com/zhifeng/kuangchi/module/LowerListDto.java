package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class LowerListDto {


    /**
     * status : 200
     * msg : success
     * data : {"total":0,"per_page":20,"current_page":1,"last_page":0,"data":[{"id":27874,"level":1,"realname":"默认昵称","first_leader":27873}]}
     */

    private int status;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 0
         * per_page : 20
         * current_page : 1
         * last_page : 0
         * data : [{"id":27874,"level":1,"realname":"默认昵称","first_leader":27873}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
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
             * id : 27874
             * level : 1
             * realname : 默认昵称
             * first_leader : 27873
             */

            private int id;
            private int level;
            private String realname;
            private int first_leader;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public int getFirst_leader() {
                return first_leader;
            }

            public void setFirst_leader(int first_leader) {
                this.first_leader = first_leader;
            }
        }
    }
}
