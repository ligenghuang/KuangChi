package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class EarningsListDto {


    /**
     * status : 200
     * msg : success
     * data : {"total":5,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":2226,"user_id":27760,"account_id":null,"balance":"12.00000000","source_type":1,"log_type":0,"source_id":"","note":"级差奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2227,"user_id":27760,"account_id":null,"balance":"0.24000000","source_type":2,"log_type":0,"source_id":"","note":"平级奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2228,"user_id":27760,"account_id":null,"balance":"12.00000000","source_type":3,"log_type":0,"source_id":"","note":"级差奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2229,"user_id":27760,"account_id":null,"balance":"0.48000000","source_type":4,"log_type":0,"source_id":"","note":"平级奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2230,"user_id":27760,"account_id":null,"balance":"12.00000000","source_type":5,"log_type":0,"source_id":"","note":"级差奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1}]}
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
         * total : 5
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":2226,"user_id":27760,"account_id":null,"balance":"12.00000000","source_type":1,"log_type":0,"source_id":"","note":"级差奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2227,"user_id":27760,"account_id":null,"balance":"0.24000000","source_type":2,"log_type":0,"source_id":"","note":"平级奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2228,"user_id":27760,"account_id":null,"balance":"12.00000000","source_type":3,"log_type":0,"source_id":"","note":"级差奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2229,"user_id":27760,"account_id":null,"balance":"0.48000000","source_type":4,"log_type":0,"source_id":"","note":"平级奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1},{"id":2230,"user_id":27760,"account_id":null,"balance":"12.00000000","source_type":5,"log_type":0,"source_id":"","note":"级差奖","bonus_from":null,"create_time":1564753522,"order_id":1,"old_balance":null,"balance_type":1}]
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
             * id : 2226
             * user_id : 27760
             * account_id : null
             * balance : 12.00000000
             * source_type : 1
             * log_type : 0
             * source_id :
             * note : 级差奖
             * bonus_from : null
             * create_time : 1564753522
             * order_id : 1
             * old_balance : null
             * balance_type : 1
             */

            private int id;
            private int user_id;
            private Object account_id;
            private String balance;
            private int source_type;
            private int log_type;
            private String source_id;
            private String note;
            private Object bonus_from;
            private long create_time;
            private int order_id;
            private Object old_balance;
            private int balance_type;

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

            public Object getAccount_id() {
                return account_id;
            }

            public void setAccount_id(Object account_id) {
                this.account_id = account_id;
            }

            public String getBalance() {
                return balance == null ? "" : balance;
            }

            public void setBalance(String balance) {
                this.balance = balance == null ? "" : balance;
            }

            public int getSource_type() {
                return source_type;
            }

            public void setSource_type(int source_type) {
                this.source_type = source_type;
            }

            public int getLog_type() {
                return log_type;
            }

            public void setLog_type(int log_type) {
                this.log_type = log_type;
            }

            public String getSource_id() {
                return source_id == null ? "" : source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id == null ? "" : source_id;
            }

            public String getNote() {
                return note == null ? "" : note;
            }

            public void setNote(String note) {
                this.note = note == null ? "" : note;
            }

            public Object getBonus_from() {
                return bonus_from;
            }

            public void setBonus_from(Object bonus_from) {
                this.bonus_from = bonus_from;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public Object getOld_balance() {
                return old_balance;
            }

            public void setOld_balance(Object old_balance) {
                this.old_balance = old_balance;
            }

            public int getBalance_type() {
                return balance_type;
            }

            public void setBalance_type(int balance_type) {
                this.balance_type = balance_type;
            }
        }
    }
}
