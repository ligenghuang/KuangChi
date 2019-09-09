package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @ClassName:     订单列表实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 14:43
  * @Version:        1.0
 */

public class OrderListDto {

    /**
     * data : {"total":0,"per_page":20,"current_page":"1","last_page":0,"carry_list":[{"user_id":27876,"order_amount":"200000.00","add_time":1566997907,"order_id":3166,"pay_type":5,"entrust_status":0,"goods_name":"矿机","spec_key_name":"规格:全网8T,1T:8T","T_num":"8"}]}
     * msg : success
     * status : 200
     */

    private DataBean data;
    private String msg;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * total : 0.0
         * per_page : 20.0
         * current_page : 1
         * last_page : 0.0
         * carry_list : [{"user_id":27876,"order_amount":"200000.00","add_time":1566997907,"order_id":3166,"pay_type":5,"entrust_status":0,"goods_name":"矿机","spec_key_name":"规格:全网8T,1T:8T","T_num":"8"}]
         */

        private double total;
        private double per_page;
        private int current_page;
        private double last_page;
        private List<CarryListBean> carry_list;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getPer_page() {
            return per_page;
        }

        public void setPer_page(double per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public double getLast_page() {
            return last_page;
        }

        public void setLast_page(double last_page) {
            this.last_page = last_page;
        }

        public List<CarryListBean> getCarry_list() {
            if (carry_list == null) {
                return new ArrayList<>();
            }
            return carry_list;
        }

        public void setCarry_list(List<CarryListBean> carry_list) {
            this.carry_list = carry_list;
        }

        public static class CarryListBean {
            /**
             * user_id : 27876
             * order_amount : 200000.00
             * add_time : 1566997907
             * order_id : 3166
             * pay_type : 5
             * entrust_status : 0
             * goods_name : 矿机
             * spec_key_name : 规格:全网8T,1T:8T
             * T_num : 8
             */

            private int user_id;
            private String order_amount;
            private long add_time;
            private int order_id;
            private int pay_type;
            private int entrust_status;
            private String goods_name;
            private String spec_key_name;
            private int T_num;
            private String m_pic;

            public String getM_pic() {
                return m_pic == null ? "" : m_pic;
            }

            public void setM_pic(String m_pic) {
                this.m_pic = m_pic == null ? "" : m_pic;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getOrder_amount() {
                return order_amount == null ? "" : order_amount;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount == null ? "" : order_amount;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public int getEntrust_status() {
                return entrust_status;
            }

            public void setEntrust_status(int entrust_status) {
                this.entrust_status = entrust_status;
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

            public int getT_num() {
                return T_num;
            }

            public void setT_num(int t_num) {
                T_num = t_num;
            }
        }
    }
}
