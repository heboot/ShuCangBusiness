package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class ImmediatelyBean implements Serializable {

    private AddressBean address;

    private GoodsBean goods;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }
}
