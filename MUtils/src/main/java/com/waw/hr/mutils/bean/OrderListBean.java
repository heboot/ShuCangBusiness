package com.waw.hr.mutils.bean;

import java.util.List;

public class OrderListBean {
//
// "Id": 12,
//         "status": "1",
//         "total_price": "500.00",

    private String Id;

    private int status;

    private double total_price;

    private List<GoodsBean> deal;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<GoodsBean> getDeal() {
        return deal;
    }

    public void setDeal(List<GoodsBean> deal) {
        this.deal = deal;
    }
}
