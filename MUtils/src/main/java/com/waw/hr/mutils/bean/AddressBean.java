package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class AddressBean implements Serializable {
    /**
     * name : 张三
     * phone : 183293849322
     * address : 河南省 郑州市 管城区
     * info : 郑东商业中心18号
     * Id : 1
     */

    private String name;
    private String phone;
    private String address;
    private String info;
    private int Id;
    private int flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
