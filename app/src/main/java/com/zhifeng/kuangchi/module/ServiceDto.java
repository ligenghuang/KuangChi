package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceDto {

    /**
     * status : 200
     * msg : success
     * data : {"miner_info":[{"id":1,"user_id":27760,"Miner_id":"Miner---1","T_num":200,"Minimum_suanli":"0.01","Maximum_suanli":"0.015","Minimum_shouyi":"1","Maximum_shouyi":"15","expected_earnings":"16425","day_nums":1,"Miner_Address":"asdadasddasdsdgdfasda","expire_time":null,"createtime":1564416000},{"id":2,"user_id":27760,"Miner_id":"Miner---2","T_num":300,"Minimum_suanli":"0.01","Maximum_suanli":"0.015","Minimum_shouyi":"1","Maximum_shouyi":"15","expected_earnings":"16425","day_nums":1,"Miner_Address":"asdadasddasdsdgdfasda","expire_time":null,"createtime":1564416000}],"miner_nums":500,"user_money":"36.00000000","currency":{"currency_name":"lamb","currency_address":"湿布拉矿池","currency_address_url":"3m3m3knnadfayg89sdhshanagl"},"line_data":[]}
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
         * miner_info : [{"id":1,"user_id":27760,"Miner_id":"Miner---1","T_num":200,"Minimum_suanli":"0.01","Maximum_suanli":"0.015","Minimum_shouyi":"1","Maximum_shouyi":"15","expected_earnings":"16425","day_nums":1,"Miner_Address":"asdadasddasdsdgdfasda","expire_time":null,"createtime":1564416000},{"id":2,"user_id":27760,"Miner_id":"Miner---2","T_num":300,"Minimum_suanli":"0.01","Maximum_suanli":"0.015","Minimum_shouyi":"1","Maximum_shouyi":"15","expected_earnings":"16425","day_nums":1,"Miner_Address":"asdadasddasdsdgdfasda","expire_time":null,"createtime":1564416000}]
         * miner_nums : 500
         * user_money : 36.00000000
         * currency : {"currency_name":"lamb","currency_address":"湿布拉矿池","currency_address_url":"3m3m3knnadfayg89sdhshanagl"}
         * line_data : []
         */

        private int miner_nums;
        private String user_money;
        private CurrencyBean currency;
        private List<MinerInfoBean> miner_info;
        private Object line_data;

        public int getMiner_nums() {
            return miner_nums;
        }

        public void setMiner_nums(int miner_nums) {
            this.miner_nums = miner_nums;
        }

        public String getUser_money() {
            return user_money == null ? "" : user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money == null ? "" : user_money;
        }

        public CurrencyBean getCurrency() {
            return currency;
        }

        public void setCurrency(CurrencyBean currency) {
            this.currency = currency;
        }

        public List<MinerInfoBean> getMiner_info() {
            if (miner_info == null) {
                return new ArrayList<>();
            }
            return miner_info;
        }

        public void setMiner_info(List<MinerInfoBean> miner_info) {
            this.miner_info = miner_info;
        }

        public Object getLine_data() {
            return line_data;
        }

        public void setLine_data(Object line_data) {
            this.line_data = line_data;
        }

        public static class CurrencyBean {
            /**
             * currency_name : lamb
             * currency_address : 湿布拉矿池
             * currency_address_url : 3m3m3knnadfayg89sdhshanagl
             */

            private String currency_name;
            private String currency_address;
            private String currency_address_url;

            public String getCurrency_name() {
                return currency_name == null ? "" : currency_name;
            }

            public void setCurrency_name(String currency_name) {
                this.currency_name = currency_name == null ? "" : currency_name;
            }

            public String getCurrency_address() {
                return currency_address == null ? "" : currency_address;
            }

            public void setCurrency_address(String currency_address) {
                this.currency_address = currency_address == null ? "" : currency_address;
            }

            public String getCurrency_address_url() {
                return currency_address_url == null ? "" : currency_address_url;
            }

            public void setCurrency_address_url(String currency_address_url) {
                this.currency_address_url = currency_address_url == null ? "" : currency_address_url;
            }
        }

        public static class MinerInfoBean {
            /**
             * id : 1
             * user_id : 27760
             * Miner_id : Miner---1
             * T_num : 200
             * Minimum_suanli : 0.01
             * Maximum_suanli : 0.015
             * Minimum_shouyi : 1
             * Maximum_shouyi : 15
             * expected_earnings : 16425
             * day_nums : 1
             * Miner_Address : asdadasddasdsdgdfasda
             * expire_time : null
             * createtime : 1564416000
             */

            private int id;
            private int user_id;
            private String Miner_id;
            private int T_num;
            private String Minimum_suanli;
            private String Maximum_suanli;
            private String Minimum_shouyi;
            private String Maximum_shouyi;
            private String expected_earnings;
            private int day_nums;
            private String Miner_Address;
            private Object expire_time;
            private int createtime;

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

            public String getMiner_id() {
                return Miner_id == null ? "" : Miner_id;
            }

            public void setMiner_id(String miner_id) {
                Miner_id = miner_id == null ? "" : miner_id;
            }

            public int getT_num() {
                return T_num;
            }

            public void setT_num(int t_num) {
                T_num = t_num;
            }

            public String getMinimum_suanli() {
                return Minimum_suanli == null ? "" : Minimum_suanli;
            }

            public void setMinimum_suanli(String minimum_suanli) {
                Minimum_suanli = minimum_suanli == null ? "" : minimum_suanli;
            }

            public String getMaximum_suanli() {
                return Maximum_suanli == null ? "" : Maximum_suanli;
            }

            public void setMaximum_suanli(String maximum_suanli) {
                Maximum_suanli = maximum_suanli == null ? "" : maximum_suanli;
            }

            public String getMinimum_shouyi() {
                return Minimum_shouyi == null ? "" : Minimum_shouyi;
            }

            public void setMinimum_shouyi(String minimum_shouyi) {
                Minimum_shouyi = minimum_shouyi == null ? "" : minimum_shouyi;
            }

            public String getMaximum_shouyi() {
                return Maximum_shouyi == null ? "" : Maximum_shouyi;
            }

            public void setMaximum_shouyi(String maximum_shouyi) {
                Maximum_shouyi = maximum_shouyi == null ? "" : maximum_shouyi;
            }

            public String getExpected_earnings() {
                return expected_earnings == null ? "" : expected_earnings;
            }

            public void setExpected_earnings(String expected_earnings) {
                this.expected_earnings = expected_earnings == null ? "" : expected_earnings;
            }

            public int getDay_nums() {
                return day_nums;
            }

            public void setDay_nums(int day_nums) {
                this.day_nums = day_nums;
            }

            public String getMiner_Address() {
                return Miner_Address == null ? "" : Miner_Address;
            }

            public void setMiner_Address(String miner_Address) {
                Miner_Address = miner_Address == null ? "" : miner_Address;
            }

            public Object getExpire_time() {
                return expire_time;
            }

            public void setExpire_time(Object expire_time) {
                this.expire_time = expire_time;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }
        }
    }
}
