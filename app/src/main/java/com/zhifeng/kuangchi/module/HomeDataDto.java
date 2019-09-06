package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class HomeDataDto {

    /**
     * status : 200
     * msg : 获取成功
     * data : {"banners":[{"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/6b544de2732394e55991b01192be6c57.png","title":"布局全球","url":""},{"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/8161c100cd005341ade52714d71774fe.png","title":"protoschool技术沙龙","url":""},{"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/d9d1d292782a2ae08c7413fbe62e465c.png","title":"三年百倍回报","url":""}],"bouns":61.99999997,"day_bouns":61.99999997,"announce":[{"id":21,"title":"IPFS重塑互联网\u2014\u2014什么是IPFS？","link":"https://mp.weixin.qq.com/s/MHy80NnuBuW1JmlcNrJ3JA","desc":"","type":2,"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/cea8dd1dd92e192adb731bd56a8972fc.png"},{"id":9,"title":"工信部：鼓励企业积极参与区块链等关键技术攻关","link":"https://mp.weixin.qq.com/s/CtaTt1tebaJiIvSaI24ePw","desc":"","type":2,"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/814cfdba528f33594eb2c70cd3b262e0.jpeg"}],"hot_goods":[],"recommend_goods":[{"goods_id":76,"goods_name":"涅布拉云存储","img":"http://orepool.zhifengwangluo.com/upload/images/goods/2019090515676701801367.png","price":"12000.00","original_price":"9999.99"}],"goods_gift":{"goods_id":76,"goods_name":"涅布拉云存储","img":"http://orepool.zhifengwangluo.com/upload/images/goods/20190905156767028332119.png","price":"12000.00","original_price":"9999.99"}}
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
         * banners : [{"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/6b544de2732394e55991b01192be6c57.png","title":"布局全球","url":""},{"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/8161c100cd005341ade52714d71774fe.png","title":"protoschool技术沙龙","url":""},{"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/d9d1d292782a2ae08c7413fbe62e465c.png","title":"三年百倍回报","url":""}]
         * bouns : 61.99999997
         * day_bouns : 61.99999997
         * announce : [{"id":21,"title":"IPFS重塑互联网\u2014\u2014什么是IPFS？","link":"https://mp.weixin.qq.com/s/MHy80NnuBuW1JmlcNrJ3JA","desc":"","type":2,"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/cea8dd1dd92e192adb731bd56a8972fc.png"},{"id":9,"title":"工信部：鼓励企业积极参与区块链等关键技术攻关","link":"https://mp.weixin.qq.com/s/CtaTt1tebaJiIvSaI24ePw","desc":"","type":2,"picture":"http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/814cfdba528f33594eb2c70cd3b262e0.jpeg"}]
         * hot_goods : []
         * recommend_goods : [{"goods_id":76,"goods_name":"涅布拉云存储","img":"http://orepool.zhifengwangluo.com/upload/images/goods/2019090515676701801367.png","price":"12000.00","original_price":"9999.99"}]
         * goods_gift : {"goods_id":76,"goods_name":"涅布拉云存储","img":"http://orepool.zhifengwangluo.com/upload/images/goods/20190905156767028332119.png","price":"12000.00","original_price":"9999.99"}
         */

        private double bouns;
        private double day_bouns;
        private GoodsGiftBean goods_gift;
        private List<BannersBean> banners;
        private List<AnnounceBean> announce;
        private List<?> hot_goods;
        private List<RecommendGoodsBean> recommend_goods;
        private int not_read;

        public int getNot_read() {
            return not_read;
        }

        public void setNot_read(int not_read) {
            this.not_read = not_read;
        }

        public double getBouns() {
            return bouns;
        }

        public void setBouns(double bouns) {
            this.bouns = bouns;
        }

        public double getDay_bouns() {
            return day_bouns;
        }

        public void setDay_bouns(double day_bouns) {
            this.day_bouns = day_bouns;
        }

        public GoodsGiftBean getGoods_gift() {
            return goods_gift;
        }

        public void setGoods_gift(GoodsGiftBean goods_gift) {
            this.goods_gift = goods_gift;
        }

        public List<BannersBean> getBanners() {
            if (banners == null) {
                return new ArrayList<>();
            }
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public List<AnnounceBean> getAnnounce() {
            if (announce == null) {
                return new ArrayList<>();
            }
            return announce;
        }

        public void setAnnounce(List<AnnounceBean> announce) {
            this.announce = announce;
        }

        public List<?> getHot_goods() {
            if (hot_goods == null) {
                return new ArrayList<>();
            }
            return hot_goods;
        }

        public void setHot_goods(List<?> hot_goods) {
            this.hot_goods = hot_goods;
        }

        public List<RecommendGoodsBean> getRecommend_goods() {
            if (recommend_goods == null) {
                return new ArrayList<>();
            }
            return recommend_goods;
        }

        public void setRecommend_goods(List<RecommendGoodsBean> recommend_goods) {
            this.recommend_goods = recommend_goods;
        }

        public static class GoodsGiftBean {
            /**
             * goods_id : 76
             * goods_name : 涅布拉云存储
             * img : http://orepool.zhifengwangluo.com/upload/images/goods/20190905156767028332119.png
             * price : 12000.00
             * original_price : 9999.99
             */

            private int goods_id;
            private String goods_name;
            private String img;
            private String price;
            private String original_price;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name == null ? "" : goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name == null ? "" : goods_name;
            }

            public String getImg() {
                return img == null ? "" : img;
            }

            public void setImg(String img) {
                this.img = img == null ? "" : img;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price == null ? "" : price;
            }

            public String getOriginal_price() {
                return original_price == null ? "" : original_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price == null ? "" : original_price;
            }
        }

        public static class BannersBean {
            /**
             * picture : http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/6b544de2732394e55991b01192be6c57.png
             * title : 布局全球
             * url :
             */

            private String picture;
            private String title;
            private String url;

            public String getPicture() {
                return picture == null ? "" : picture;
            }

            public void setPicture(String picture) {
                this.picture = picture == null ? "" : picture;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title == null ? "" : title;
            }

            public String getUrl() {
                return url == null ? "" : url;
            }

            public void setUrl(String url) {
                this.url = url == null ? "" : url;
            }
        }

        public static class AnnounceBean {
            /**
             * id : 21
             * title : IPFS重塑互联网——什么是IPFS？
             * link : https://mp.weixin.qq.com/s/MHy80NnuBuW1JmlcNrJ3JA
             * desc :
             * type : 2
             * picture : http://orepool.zhifengwangluo.com/uploads/fixed_picture/20190905/cea8dd1dd92e192adb731bd56a8972fc.png
             */

            private int id;
            private String title;
            private String link;
            private String desc;
            private int type;
            private String picture;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title == null ? "" : title;
            }

            public String getLink() {
                return link == null ? "" : link;
            }

            public void setLink(String link) {
                this.link = link == null ? "" : link;
            }

            public String getDesc() {
                return desc == null ? "" : desc;
            }

            public void setDesc(String desc) {
                this.desc = desc == null ? "" : desc;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getPicture() {
                return picture == null ? "" : picture;
            }

            public void setPicture(String picture) {
                this.picture = picture == null ? "" : picture;
            }
        }

        public static class RecommendGoodsBean {
            /**
             * goods_id : 76
             * goods_name : 涅布拉云存储
             * img : http://orepool.zhifengwangluo.com/upload/images/goods/2019090515676701801367.png
             * price : 12000.00
             * original_price : 9999.99
             */

            private int goods_id;
            private String goods_name;
            private String img;
            private String price;
            private String original_price;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name == null ? "" : goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name == null ? "" : goods_name;
            }

            public String getImg() {
                return img == null ? "" : img;
            }

            public void setImg(String img) {
                this.img = img == null ? "" : img;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price == null ? "" : price;
            }

            public String getOriginal_price() {
                return original_price == null ? "" : original_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price == null ? "" : original_price;
            }
        }
    }
}
