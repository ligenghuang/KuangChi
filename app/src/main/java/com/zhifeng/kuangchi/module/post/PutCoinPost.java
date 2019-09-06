package com.zhifeng.kuangchi.module.post;
/**
  *
  * @ClassName:     充币请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 12:19
  * @Version:        1.0
 */

public class PutCoinPost {

    private String coin_type;
    private String money;
    private double input_money;
    private String proof_pic;
    private String address;

    public String getCoin_type() {
        return coin_type == null ? "" : coin_type;
    }

    public void setCoin_type(String coin_type) {
        this.coin_type = coin_type == null ? "" : coin_type;
    }

    public String getMoney() {
        return money == null ? "" : money;
    }

    public void setMoney(String money) {
        this.money = money == null ? "" : money;
    }

    public double getInput_money() {
        return input_money;
    }

    public void setInput_money(double input_money) {
        this.input_money = input_money;
    }

    public String getProof_pic() {
        return proof_pic == null ? "" : proof_pic;
    }

    public void setProof_pic(String proof_pic) {
        this.proof_pic = proof_pic == null ? "" : proof_pic;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address == null ? "" : address;
    }
}
