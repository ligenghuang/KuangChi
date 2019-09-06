package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class EntrustListDto {

    /**
     * status : 200
     * msg : success
     * data : {"total":2,"per_page":10,"current_page":"1","last_page":1,"Entrust_list":[{"rec_id":3402,"order_id":3221,"user_id":27893,"goods_id":76,"cat_id":26,"seller_id":0,"order_sn":"0","consignee":null,"mobile":null,"goods_name":"涅布拉云存储","goods_sn":"","goods_num":5,"final_price":"12000.00","goods_price":"12000.00","cost_price":"0.00","member_goods_price":"12000.00","give_integral":0,"sku_id":79,"item_id":0,"spec_key":"","spec_key_name":"规格:全网8T,1T:8T","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","add_time":1567648051,"realname":"我叫小然然","entrust_status":1,"create_time":"2019-09-05 09:47:31","T_num":40},{"rec_id":3411,"order_id":3230,"user_id":27893,"goods_id":76,"cat_id":26,"seller_id":0,"order_sn":"0","consignee":null,"mobile":null,"goods_name":"涅布拉云存储","goods_sn":"","goods_num":20,"final_price":"12000.00","goods_price":"12000.00","cost_price":"0.00","member_goods_price":"12000.00","give_integral":0,"sku_id":79,"item_id":0,"spec_key":"","spec_key_name":"规格:全网8T,1T:8T","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","add_time":1567663859,"realname":"我叫小然然","entrust_status":1,"create_time":"2019-09-05 14:10:59","T_num":160}]}
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
         * total : 2
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * Entrust_list : [{"rec_id":3402,"order_id":3221,"user_id":27893,"goods_id":76,"cat_id":26,"seller_id":0,"order_sn":"0","consignee":null,"mobile":null,"goods_name":"涅布拉云存储","goods_sn":"","goods_num":5,"final_price":"12000.00","goods_price":"12000.00","cost_price":"0.00","member_goods_price":"12000.00","give_integral":0,"sku_id":79,"item_id":0,"spec_key":"","spec_key_name":"规格:全网8T,1T:8T","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","add_time":1567648051,"realname":"我叫小然然","entrust_status":1,"create_time":"2019-09-05 09:47:31","T_num":40},{"rec_id":3411,"order_id":3230,"user_id":27893,"goods_id":76,"cat_id":26,"seller_id":0,"order_sn":"0","consignee":null,"mobile":null,"goods_name":"涅布拉云存储","goods_sn":"","goods_num":20,"final_price":"12000.00","goods_price":"12000.00","cost_price":"0.00","member_goods_price":"12000.00","give_integral":0,"sku_id":79,"item_id":0,"spec_key":"","spec_key_name":"规格:全网8T,1T:8T","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","add_time":1567663859,"realname":"我叫小然然","entrust_status":1,"create_time":"2019-09-05 14:10:59","T_num":160}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<EntrustListBean> Entrust_list;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

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



        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<EntrustListBean> getEntrust_list() {
            if (Entrust_list == null) {
                return new ArrayList<>();
            }
            return Entrust_list;
        }

        public void setEntrust_list(List<EntrustListBean> entrust_list) {
            Entrust_list = entrust_list;
        }

        public static class EntrustListBean {
            /**
             * rec_id : 3402
             * order_id : 3221
             * user_id : 27893
             * goods_id : 76
             * cat_id : 26
             * seller_id : 0
             * order_sn : 0
             * consignee : null
             * mobile : null
             * goods_name : 涅布拉云存储
             * goods_sn :
             * goods_num : 5
             * final_price : 12000.00
             * goods_price : 12000.00
             * cost_price : 0.00
             * member_goods_price : 12000.00
             * give_integral : 0
             * sku_id : 79
             * item_id : 0
             * spec_key :
             * spec_key_name : 规格:全网8T,1T:8T
             * bar_code :
             * is_comment : 0
             * prom_type : 0
             * prom_id : 0
             * is_send : 0
             * delivery_id : 0
             * sku :
             * add_time : 1567648051
             * realname : 我叫小然然
             * entrust_status : 1
             * create_time : 2019-09-05 09:47:31
             * T_num : 40
             */

            private int rec_id;
            private int order_id;
            private int user_id;
            private int goods_id;
            private int cat_id;
            private int seller_id;
            private String order_sn;
            private Object consignee;
            private Object mobile;
            private String goods_name;
            private String goods_sn;
            private int goods_num;
            private String final_price;
            private String goods_price;
            private String cost_price;
            private String member_goods_price;
            private int give_integral;
            private int sku_id;
            private int item_id;
            private String spec_key;
            private String spec_key_name;
            private String bar_code;
            private int is_comment;
            private int prom_type;
            private int prom_id;
            private int is_send;
            private int delivery_id;
            private String sku;
            private long add_time;
            private String realname;
            private int entrust_status;
            private String create_time;
            private int T_num;

            public int getRec_id() {
                return rec_id;
            }

            public void setRec_id(int rec_id) {
                this.rec_id = rec_id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getCat_id() {
                return cat_id;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public int getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(int seller_id) {
                this.seller_id = seller_id;
            }

            public String getOrder_sn() {
                return order_sn == null ? "" : order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn == null ? "" : order_sn;
            }

            public Object getConsignee() {
                return consignee;
            }

            public void setConsignee(Object consignee) {
                this.consignee = consignee;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public String getGoods_name() {
                return goods_name == null ? "" : goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name == null ? "" : goods_name;
            }

            public String getGoods_sn() {
                return goods_sn == null ? "" : goods_sn;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn == null ? "" : goods_sn;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getFinal_price() {
                return final_price == null ? "" : final_price;
            }

            public void setFinal_price(String final_price) {
                this.final_price = final_price == null ? "" : final_price;
            }

            public String getGoods_price() {
                return goods_price == null ? "" : goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price == null ? "" : goods_price;
            }

            public String getCost_price() {
                return cost_price == null ? "" : cost_price;
            }

            public void setCost_price(String cost_price) {
                this.cost_price = cost_price == null ? "" : cost_price;
            }

            public String getMember_goods_price() {
                return member_goods_price == null ? "" : member_goods_price;
            }

            public void setMember_goods_price(String member_goods_price) {
                this.member_goods_price = member_goods_price == null ? "" : member_goods_price;
            }

            public int getGive_integral() {
                return give_integral;
            }

            public void setGive_integral(int give_integral) {
                this.give_integral = give_integral;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public String getSpec_key() {
                return spec_key == null ? "" : spec_key;
            }

            public void setSpec_key(String spec_key) {
                this.spec_key = spec_key == null ? "" : spec_key;
            }

            public String getSpec_key_name() {
                return spec_key_name == null ? "" : spec_key_name;
            }

            public void setSpec_key_name(String spec_key_name) {
                this.spec_key_name = spec_key_name == null ? "" : spec_key_name;
            }

            public String getBar_code() {
                return bar_code == null ? "" : bar_code;
            }

            public void setBar_code(String bar_code) {
                this.bar_code = bar_code == null ? "" : bar_code;
            }

            public int getIs_comment() {
                return is_comment;
            }

            public void setIs_comment(int is_comment) {
                this.is_comment = is_comment;
            }

            public int getProm_type() {
                return prom_type;
            }

            public void setProm_type(int prom_type) {
                this.prom_type = prom_type;
            }

            public int getProm_id() {
                return prom_id;
            }

            public void setProm_id(int prom_id) {
                this.prom_id = prom_id;
            }

            public int getIs_send() {
                return is_send;
            }

            public void setIs_send(int is_send) {
                this.is_send = is_send;
            }

            public int getDelivery_id() {
                return delivery_id;
            }

            public void setDelivery_id(int delivery_id) {
                this.delivery_id = delivery_id;
            }

            public String getSku() {
                return sku == null ? "" : sku;
            }

            public void setSku(String sku) {
                this.sku = sku == null ? "" : sku;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public String getRealname() {
                return realname == null ? "" : realname;
            }

            public void setRealname(String realname) {
                this.realname = realname == null ? "" : realname;
            }

            public int getEntrust_status() {
                return entrust_status;
            }

            public void setEntrust_status(int entrust_status) {
                this.entrust_status = entrust_status;
            }

            public String getCreate_time() {
                return create_time == null ? "" : create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time == null ? "" : create_time;
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
