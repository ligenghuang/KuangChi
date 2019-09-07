package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class BalanceDto {

    /**
     * status : 200
     * msg : success
     * data : {"coin_address":[{"id":1,"coin_name":"FILECOIN","pay_type":8,"address":"1341416sdfasd1f321sdf","rate":"00000.100","tax_rate":"0.020"},{"id":2,"coin_name":"LAMB","pay_type":5,"address":"fdsf544gsadf354g35asf","rate":"00000.600","tax_rate":"0.040"},{"id":3,"coin_name":"USDT","pay_type":4,"address":"fsadgfgewart34546","rate":"00000.300","tax_rate":"0.050"},{"id":4,"coin_name":"BTC","pay_type":6,"address":"FGDSFTYWERMSD","rate":"00000.200","tax_rate":"0.030"},{"id":5,"coin_name":"ETH","pay_type":7,"address":"FGFVGXCFWERTG","rate":"00000.150","tax_rate":"0.030"}],"remainder_money":"0.00000000","withdrawal_lines":"0.02","withdrawal_max":10000,"withdrawal_rate":0.1}
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
         * coin_address : [{"id":1,"coin_name":"FILECOIN","pay_type":8,"address":"1341416sdfasd1f321sdf","rate":"00000.100","tax_rate":"0.020"},{"id":2,"coin_name":"LAMB","pay_type":5,"address":"fdsf544gsadf354g35asf","rate":"00000.600","tax_rate":"0.040"},{"id":3,"coin_name":"USDT","pay_type":4,"address":"fsadgfgewart34546","rate":"00000.300","tax_rate":"0.050"},{"id":4,"coin_name":"BTC","pay_type":6,"address":"FGDSFTYWERMSD","rate":"00000.200","tax_rate":"0.030"},{"id":5,"coin_name":"ETH","pay_type":7,"address":"FGFVGXCFWERTG","rate":"00000.150","tax_rate":"0.030"}]
         * remainder_money : 0.00000000
         * withdrawal_lines : 0.02
         * withdrawal_max : 10000
         * withdrawal_rate : 0.1
         */

        private String remainder_money;
        private String withdrawal_lines;
        private int withdrawal_max;
        private double withdrawal_rate;
        private List<CoinAddressBean> coin_address;

        public String getRemainder_money() {
            return remainder_money == null ? "" : remainder_money;
        }

        public void setRemainder_money(String remainder_money) {
            this.remainder_money = remainder_money == null ? "" : remainder_money;
        }

        public String getWithdrawal_lines() {
            return withdrawal_lines == null ? "" : withdrawal_lines;
        }

        public void setWithdrawal_lines(String withdrawal_lines) {
            this.withdrawal_lines = withdrawal_lines == null ? "" : withdrawal_lines;
        }

        public int getWithdrawal_max() {
            return withdrawal_max;
        }

        public void setWithdrawal_max(int withdrawal_max) {
            this.withdrawal_max = withdrawal_max;
        }

        public double getWithdrawal_rate() {
            return withdrawal_rate;
        }

        public void setWithdrawal_rate(double withdrawal_rate) {
            this.withdrawal_rate = withdrawal_rate;
        }

        public List<CoinAddressBean> getCoin_address() {
            if (coin_address == null) {
                return new ArrayList<>();
            }
            return coin_address;
        }

        public void setCoin_address(List<CoinAddressBean> coin_address) {
            this.coin_address = coin_address;
        }



        public static class CoinAddressBean {
            /**
             * id : 1
             * coin_name : FILECOIN
             * pay_type : 8
             * address : 1341416sdfasd1f321sdf
             * rate : 00000.100
             * tax_rate : 0.020
             */

            private int id;
            private String coin_name;
            private int pay_type;
            private String address;
            private String rate;
            private String tax_rate;
            private double to_usdt;
            private double to_lamb;
            private double to_rmb;
            boolean isClick;
            private double user_money;

            public double getUser_money() {
                return user_money;
            }

            public void setUser_money(double user_money) {
                this.user_money = user_money;
            }

            public double getTo_usdt() {
                return to_usdt;
            }

            public void setTo_usdt(double to_usdt) {
                this.to_usdt = to_usdt;
            }

            public double getTo_lamb() {
                return to_lamb;
            }

            public void setTo_lamb(double to_lamb) {
                this.to_lamb = to_lamb;
            }

            public double getTo_rmb() {
                return to_rmb;
            }

            public void setTo_rmb(double to_rmb) {
                this.to_rmb = to_rmb;
            }

            public boolean isClick() {
                return isClick;
            }

            public void setClick(boolean click) {
                isClick = click;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCoin_name() {
                return coin_name == null ? "" : coin_name;
            }

            public void setCoin_name(String coin_name) {
                this.coin_name = coin_name == null ? "" : coin_name;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address == null ? "" : address;
            }

            public String getRate() {
                return rate == null ? "" : rate;
            }

            public void setRate(String rate) {
                this.rate = rate == null ? "" : rate;
            }

            public String getTax_rate() {
                return tax_rate == null ? "" : tax_rate;
            }

            public void setTax_rate(String tax_rate) {
                this.tax_rate = tax_rate == null ? "" : tax_rate;
            }
        }
    }
}
