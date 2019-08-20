package com.waw.hr.mutils.bean;

import java.util.List;

public class ClassifyBean {


    private List<ClassBean> clasz;
    private List<InfoBean> info;

    public List<ClassBean> getClasz() {
        return clasz;
    }

    public void setClasz(List<ClassBean> clasz) {
        this.clasz = clasz;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class ClassBean {
        /**
         * id : 1
         * name : 洛塔瓷砖
         * image : /uploads/20190817/1921649aa0d2c9e4edbdb5e11633359e.png
         */

        private int Id;
        private String name;
        private String image;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class InfoBean {
        /**
         * title : 洛塔瓷砖
         * goods : []
         */

        private String title;
        private List<GoodsBean> goods;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }
    }
}
