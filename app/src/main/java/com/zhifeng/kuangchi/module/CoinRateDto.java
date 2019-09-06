package com.zhifeng.kuangchi.module;

public class CoinRateDto {


    /**
     * code : 200
     * msg : success
     * data : {"coin_name":"usdt","coin_type":"4","rate":"0.3"}
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
         * coin_name : usdt
         * coin_type : 4
         * rate : 0.3
         */

        private String coin_name;
        private String coin_type;
        private String rate;

        public String getCoin_name() {
            return coin_name == null ? "" : coin_name;
        }

        public void setCoin_name(String coin_name) {
            this.coin_name = coin_name == null ? "" : coin_name;
        }

        public String getCoin_type() {
            return coin_type == null ? "" : coin_type;
        }

        public void setCoin_type(String coin_type) {
            this.coin_type = coin_type == null ? "" : coin_type;
        }

        public String getRate() {
            return rate == null ? "0" : rate;
        }

        public void setRate(String rate) {
            this.rate = rate == null ? "" : rate;
        }
    }
}
