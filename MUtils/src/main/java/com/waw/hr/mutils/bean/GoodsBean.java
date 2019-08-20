package com.waw.hr.mutils.bean;

public class GoodsBean {
    private int Id;
    private String name;
    private String cover_image;
    private String price;
    private int inven;
    private int sales;

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

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getInven() {
        return inven;
    }

    public void setInven(int inven) {
        this.inven = inven;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

}
