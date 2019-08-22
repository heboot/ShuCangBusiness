package com.waw.hr.mutils.bean;

import java.io.Serializable;
import java.util.List;

public class OrderSubBean implements Serializable {

    private AddressBean adress;

    private List<GoodsBean> goods;

    public AddressBean getAdress() {
        return adress;
    }

    public void setAdress(AddressBean adress) {
        this.adress = adress;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }
}
