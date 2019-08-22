package com.waw.hr.mutils.bean;

import java.util.List;

public class GoodsDetailBean {


    /**
     * Id : 1
     * code : 1125484
     * name : 客厅地毯晶砖拼图式玄关过道瓷砖拼花
     * original_price : 358.00
     * price : 298.00
     * info_content : 1111
     * inven : 9999
     * sales : 152
     * info_images : ["http://www.shop.com/uploads/20190817/c32563d247759dcbeaf607abad0f1dfb.png","http://www.shop.com/uploads/20190817/c32563d247759dcbeaf607abad0f1dfb.png","http://www.shop.com/uploads/20190817/c32563d247759dcbeaf607abad0f1dfb.png"]
     * sign : 0
     */

    private int Id;
    private int code;
    private String name;
    private String original_price;
    private String price;
    private String info_content;
    private int inven;
    private int sales;
    private int sign;
    private List<String> info_images;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo_content() {
        return info_content;
    }

    public void setInfo_content(String info_content) {
        this.info_content = info_content;
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

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public List<String> getInfo_images() {
        return info_images;
    }

    public void setInfo_images(List<String> info_images) {
        this.info_images = info_images;
    }
}
