package com.zhifeng.kuangchi.module;
/**
  *
  * @ClassName:     充值详情实体类
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 10:20
  * @Version:        1.0
 */

public class PutDetailDto {


    /**
     * status : 200
     * msg : success
     * data : {"id":3,"coin_type":4,"money":10,"user_id":31032,"status":0,"release_money":"10.00","add_time":1568195749,"proof_pic":"http://www.imnebula.com/upload/images/proof_coin/20190911156819574971360.png","input_money":"10.00","address":"0xd8cc70675748b7f072baac8748aa7a162a9e86dc","change_time":0}
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
         * id : 3
         * coin_type : 4
         * money : 10
         * user_id : 31032
         * status : 0
         * release_money : 10.00
         * add_time : 1568195749
         * proof_pic : http://www.imnebula.com/upload/images/proof_coin/20190911156819574971360.png
         * input_money : 10.00
         * address : 0xd8cc70675748b7f072baac8748aa7a162a9e86dc
         * change_time : 0
         */

        private int id;
        private int coin_type;
        private double money;
        private int user_id;
        private int status;
        private String release_money;
        private int add_time;
        private String proof_pic;
        private String input_money;
        private String address;
        private int change_time;
        private String note;

        public String getNote() {
            return note == null ? "" : note;
        }

        public void setNote(String note) {
            this.note = note == null ? "" : note;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCoin_type() {
            return coin_type;
        }

        public void setCoin_type(int coin_type) {
            this.coin_type = coin_type;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRelease_money() {
            return release_money == null ? "" : release_money;
        }

        public void setRelease_money(String release_money) {
            this.release_money = release_money == null ? "" : release_money;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getProof_pic() {
            return proof_pic == null ? "" : proof_pic;
        }

        public void setProof_pic(String proof_pic) {
            this.proof_pic = proof_pic == null ? "" : proof_pic;
        }

        public String getInput_money() {
            return input_money == null ? "" : input_money;
        }

        public void setInput_money(String input_money) {
            this.input_money = input_money == null ? "" : input_money;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public int getChange_time() {
            return change_time;
        }

        public void setChange_time(int change_time) {
            this.change_time = change_time;
        }
    }
}
