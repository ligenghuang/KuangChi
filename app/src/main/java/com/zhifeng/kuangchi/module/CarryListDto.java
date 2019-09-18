package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @ClassName:     提币明细列表实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 9:10
  * @Version:        1.0
 */

public class CarryListDto {

    /**
     * status : 200
     * msg : success
     * data : {"total":3,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":10,"money":600000,"address":"gggi","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"999999.99","add_time":1567587048,"tax_rate":"0.10","input_money":1000000,"tax":"100000.00"},{"id":11,"money":100,"address":"fdsf544gsadf354g35asf","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"000172.67","add_time":1567603347,"tax_rate":"0.10","input_money":60,"tax":"6.00"},{"id":12,"money":100,"address":"fdsf544gsadf354g35asf","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"000172.67","add_time":1567603359,"tax_rate":"0.10","input_money":60,"tax":"6.00"}]}
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
         * total : 3
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"id":10,"money":600000,"address":"gggi","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"999999.99","add_time":1567587048,"tax_rate":"0.10","input_money":1000000,"tax":"100000.00"},{"id":11,"money":100,"address":"fdsf544gsadf354g35asf","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"000172.67","add_time":1567603347,"tax_rate":"0.10","input_money":60,"tax":"6.00"},{"id":12,"money":100,"address":"fdsf544gsadf354g35asf","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"000172.67","add_time":1567603359,"tax_rate":"0.10","input_money":60,"tax":"6.00"}]
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
             * id : 10
             * money : 600000
             * address : gggi
             * user_id : 27893
             * coin_type : 5
             * status : 0
             * fronzen_money : 999999.99
             * add_time : 1567587048
             * tax_rate : 0.10
             * input_money : 1000000
             * tax : 100000.00
             */

            private int id;
            private double money;
            private String address;
            private int user_id;
            private int coin_type;
            private int status;
            private String fronzen_money;
            private int add_time;
            private String tax_rate;
            private double input_money;
            private String tax;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address == null ? "" : address;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getCoin_type() {
                return coin_type;
            }

            public void setCoin_type(int coin_type) {
                this.coin_type = coin_type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getFronzen_money() {
                return fronzen_money == null ? "" : fronzen_money;
            }

            public void setFronzen_money(String fronzen_money) {
                this.fronzen_money = fronzen_money == null ? "" : fronzen_money;
            }

            public int getAdd_time() {
                return add_time;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
            }

            public String getTax_rate() {
                return tax_rate == null ? "0" : tax_rate;
            }

            public void setTax_rate(String tax_rate) {
                this.tax_rate = tax_rate == null ? "" : tax_rate;
            }

            public double getInput_money() {
                return input_money;
            }

            public void setInput_money(double input_money) {
                this.input_money = input_money;
            }

            public String getTax() {
                return tax == null ? "0" : tax;
            }

            public void setTax(String tax) {
                this.tax = tax == null ? "" : tax;
            }
        }
    }
}
