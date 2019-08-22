package com.waw.hr.mutils.bean;

import java.util.List;

public class OrderInfoBean {


    /**
     * Id : 7
     * ordernum : 20190821143914826897
     * pay_time :
     * add_time : 2019-08-21 14:39:14
     * status : 1
     * total_price : 500.00
     * emit_time :
     * sign_time :
     * addr : {"name":"李四","phone":"18523652145","address":"河南省 郑州市 二七区","info":"花园路25号"}
     * deal : [{"Id":8,"cover_image":"http://zonghongkeji.com/shopping/public/uploads/20190817/a6626eb0b025b3cc7c7d79a0e85978c2.png","name":"客厅地毯晶砖拼图式玄关过道瓷砖拼花-8","num":1,"price":"298.00"}]
     */

    private int Id;
    private String ordernum;

    private String add_time;
    private int status;
    private String total_price;
    private String pay_time;
    private String emit_time;
    private String sign_time;

    private String pay_time_mm;
    private String emit_time_mm;
    private String sign_time_mm;

    private AddressBean addr;
    private List<GoodsBean> deal;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getEmit_time() {
        return emit_time;
    }

    public void setEmit_time(String emit_time) {
        this.emit_time = emit_time;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public AddressBean getAddr() {
        return addr;
    }

    public void setAddr(AddressBean addr) {
        this.addr = addr;
    }

    public List<GoodsBean> getDeal() {
        return deal;
    }

    public void setDeal(List<GoodsBean> deal) {
        this.deal = deal;
    }

    public String getPay_time_mm() {
        return pay_time_mm;
    }

    public void setPay_time_mm(String pay_time_mm) {
        this.pay_time_mm = pay_time_mm;
    }

    public String getEmit_time_mm() {
        return emit_time_mm;
    }

    public void setEmit_time_mm(String emit_time_mm) {
        this.emit_time_mm = emit_time_mm;
    }

    public String getSign_time_mm() {
        return sign_time_mm;
    }

    public void setSign_time_mm(String sign_time_mm) {
        this.sign_time_mm = sign_time_mm;
    }
}
