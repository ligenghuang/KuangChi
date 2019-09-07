package com.zhifeng.kuangchi.module.post;
/**
  *
  * @ClassName:     委托购买请求体
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 17:30
  * @Version:        1.0
 */

public class EntrustedPurchasePost {

    private int sku_id;
    private int cart_number;
    private int pay_type;
    private String proof_pic;
    private String pwsseord;

    public String getPwsseord() {
        return pwsseord == null ? "" : pwsseord;
    }

    public void setPwsseord(String pwsseord) {
        this.pwsseord = pwsseord == null ? "" : pwsseord;
    }

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public int getCart_number() {
        return cart_number;
    }

    public void setCart_number(int cart_number) {
        this.cart_number = cart_number;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getProof_pic() {
        return proof_pic == null ? "" : proof_pic;
    }

    public void setProof_pic(String proof_pic) {
        this.proof_pic = proof_pic == null ? "" : proof_pic;
    }

    @Override
    public String toString() {
        return "EntrustedPurchasePost{" +
                "sku_id=" + sku_id +
                ", cart_number=" + cart_number +
                ", pay_type=" + pay_type +
                '}';
    }
}
