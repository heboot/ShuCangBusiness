package com.waw.hr.mutils.bean;

import java.util.List;

public class IndexBean  {


    private List<AdvertBean> advert;
    private List<ClassifyBean.ClassBean> clasz;
    private List<GoodsBean> goods;

    public List<AdvertBean> getAdvert() {
        return advert;
    }

    public void setAdvert(List<AdvertBean> advert) {
        this.advert = advert;
    }

    public List<ClassifyBean.ClassBean> getClasz() {
        return clasz;
    }

    public void setClasz(List<ClassifyBean.ClassBean> clasz) {
        this.clasz = clasz;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class AdvertBean {
        /**
         * type : 1
         * ad_image : http://www.shop.com/uploads/20190817/4eb75e0792ff6aeb40cee6df5f405751.png
         */

        private int type;
        private String ad_image;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAd_image() {
            return ad_image;
        }

        public void setAd_image(String ad_image) {
            this.ad_image = ad_image;
        }
    }




}
