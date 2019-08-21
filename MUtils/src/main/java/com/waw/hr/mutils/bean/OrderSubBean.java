package com.waw.hr.mutils.bean;

import java.io.Serializable;
import java.util.List;

public class OrderSubBean implements Serializable {

    private AddressBean address;

    private List<GoodsBean> goods;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }
}
