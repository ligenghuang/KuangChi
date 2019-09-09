package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class KLineDto {


    /**
     * status : 200
     * msg : success
     * data : {"status":"ok","ch":"market.lambusdt.detail.merged","ts":1567997415521,"tick":{"amount":3.191272603273E7,"open":0.14365,"close":0.142167,"high":0.1498,"id":200148824535,"count":49906,"low":0.141641,"version":200148824535,"ask":[0.14217,7733.1543],"vol":4617280.0187053,"bid":[0.142165,14067.7573]}}
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
         * status : ok
         * ch : market.lambusdt.detail.merged
         * ts : 1567997415521
         * tick : {"amount":3.191272603273E7,"open":0.14365,"close":0.142167,"high":0.1498,"id":200148824535,"count":49906,"low":0.141641,"version":200148824535,"ask":[0.14217,7733.1543],"vol":4617280.0187053,"bid":[0.142165,14067.7573]}
         */

        private String status;
        private String ch;
        private long ts;
        private TickBean tick;

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status == null ? "" : status;
        }

        public String getCh() {
            return ch == null ? "" : ch;
        }

        public void setCh(String ch) {
            this.ch = ch == null ? "" : ch;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public TickBean getTick() {
            return tick;
        }

        public void setTick(TickBean tick) {
            this.tick = tick;
        }

        public static class TickBean {
            /**
             * amount : 3.191272603273E7
             * open : 0.14365
             * close : 0.142167
             * high : 0.1498
             * id : 200148824535
             * count : 49906
             * low : 0.141641
             * version : 200148824535
             * ask : [0.14217,7733.1543]
             * vol : 4617280.0187053
             * bid : [0.142165,14067.7573]
             */

            private double amount;
            private double open;
            private double close;
            private double high;
            private long id;
            private int count;
            private double low;
            private long version;
            private double vol;
            private List<Double> ask;
            private List<Double> bid;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getOpen() {
                return open;
            }

            public void setOpen(double open) {
                this.open = open;
            }

            public double getClose() {
                return close;
            }

            public void setClose(double close) {
                this.close = close;
            }

            public double getHigh() {
                return high;
            }

            public void setHigh(double high) {
                this.high = high;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public double getLow() {
                return low;
            }

            public void setLow(double low) {
                this.low = low;
            }

            public long getVersion() {
                return version;
            }

            public void setVersion(long version) {
                this.version = version;
            }

            public double getVol() {
                return vol;
            }

            public void setVol(double vol) {
                this.vol = vol;
            }

            public List<Double> getAsk() {
                if (ask == null) {
                    return new ArrayList<>();
                }
                return ask;
            }

            public void setAsk(List<Double> ask) {
                this.ask = ask;
            }

            public List<Double> getBid() {
                if (bid == null) {
                    return new ArrayList<>();
                }
                return bid;
            }

            public void setBid(List<Double> bid) {
                this.bid = bid;
            }
        }
    }
}
