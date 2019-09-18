package com.zhifeng.kuangchi.module;
/**
  *
  * @ClassName:     提币详情实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 10:20
  * @Version:        1.0
 */

public class CarryDetailDto {


    /**
     * status : 200
     * msg : success
     * data : {"id":10,"money":600000,"address":"gggi","user_id":27893,"coin_type":5,"status":0,"fronzen_money":"999999.99","add_time":1567587048,"tax_rate":"0.10","input_money":1000000,"tax":"100000.00"}
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
        private long add_time;
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

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getTax_rate() {
            return tax_rate == null ? "" : tax_rate;
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
            return tax == null ? "" : tax;
        }

        public void setTax(String tax) {
            this.tax = tax == null ? "" : tax;
        }
    }
}
