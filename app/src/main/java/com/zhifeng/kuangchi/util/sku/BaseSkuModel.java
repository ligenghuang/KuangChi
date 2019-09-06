package com.zhifeng.kuangchi.util.sku;

public class BaseSkuModel {


    //base 属性
    private double price;//价格
    private long stock;//库存
    private int isPoint;//库存
    private int point;//库存
    private double vipPrice;

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getFormatNum() {
        return FormatNum;
    }

    public void setFormatNum(String formatNum) {
        FormatNum = formatNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String FormatNum;
    private String picture;
    private int voucher;

    public BaseSkuModel() {

    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getVoucher() {
        return voucher;
    }

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }

    public BaseSkuModel(BaseSkuModel
                                skuKeyBean) {
        this.price = skuKeyBean.getPrice();
        this.stock = skuKeyBean.getStock();
        this.FormatNum = skuKeyBean.getFormatNum();
        this.picture = skuKeyBean.getPicture();
        this.voucher = skuKeyBean.getVoucher();
        this.vipPrice = skuKeyBean.getVipPrice();
    }

    public BaseSkuModel(double price, long stock,int point,int voucher,double vipPrice) {
        this.price = price;
        this.point = point;
        this.stock = stock;
        this.voucher = voucher;
        this.vipPrice = vipPrice;
    }

    public int getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(int isPoint) {
        this.isPoint = isPoint;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }


    @Override
    public String toString() {
        return "BaseSkuModel{" +
                "price=" + price +
                ", stock=" + stock +
                ", isPoint=" + isPoint +
                ", point=" + point +
                ", vipPrice=" + vipPrice +
                ", FormatNum='" + FormatNum + '\'' +
                ", picture='" + picture + '\'' +
                ", voucher=" + voucher +
                '}';
    }
}
