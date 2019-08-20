package com.zh.business.shucang.service;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.rxbus.RxBus;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.activity.login.LoginActivity;
import com.zh.business.shucang.utils.IntentUtils;

public class UserService {

    private String phone;

    private double sign;

    private String headImg;

    private double status;


    private UserService() {
    }

    private String token;

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public boolean isLogin(){
        if(StringUtils.isEmpty(token)){
            IntentUtils.doIntent( LoginActivity.class);
            return false;
        }
        return !StringUtils.isEmpty(token);
    }

    public void logout(){
        RxBus.getInstance().post("logout");
        setToken("");
        setStatus(-1);
        setSign(-1);
        setHeadImg("");
        setPhone("");
    }

    public boolean isLoginValue(){
        if(StringUtils.isEmpty(token)){
            return false;
        }
        return !StringUtils.isEmpty(token);
    }

    public String getToken() {
        if(StringUtils.isEmpty(token)){
            token = PreferencesUtils.getString(MAPP.mapp, MKey.TOKEN);
        }
        return token;
    }

    public void setToken(String token) {
        PreferencesUtils.putString(MAPP.mapp, MKey.TOKEN,token);
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSign() {
        return sign;
    }

    public void setSign(double sign) {
        this.sign = sign;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }
}
