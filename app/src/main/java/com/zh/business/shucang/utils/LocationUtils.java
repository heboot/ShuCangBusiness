package com.zh.business.shucang.utils;

import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.StringUtils;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.service.UserService;

public class LocationUtils {

//    //声明AMapLocationClient类对象
//    private static AMapLocationClient mLocationClient = null;
//    //声明定位回调监听器
//    private static AMapLocationListener mLocationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation aMapLocation) {
//            UserService.getInstance().setLocation(aMapLocation);
//            LogUtil.e("address", StringUtils.isEmpty(aMapLocation.getProvince())?"null":aMapLocation.getProvince());
////            mLocationClient.stopLocation();
//        }
//    };
//
//    //声明AMapLocationClientOption对象
//    private static AMapLocationClientOption mLocationOption = null;
//
//    public static void getLocation(){
//        //初始化定位
//        if(mLocationClient == null){
//            mLocationClient = new AMapLocationClient(MAPP.mapp);
//            //设置定位回调监听
//            mLocationClient.setLocationListener(mLocationListener);
//        }
//
//        if(mLocationOption == null){
//
//            //初始化AMapLocationClientOption对象
//            mLocationOption = new AMapLocationClientOption();
//
//            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//
//            //设置是否返回地址信息（默认返回地址信息）
//            mLocationOption.setNeedAddress(true);
//
//            mLocationOption.setInterval(2000);
//
//            //给定位客户端对象设置定位参数
//            mLocationClient.setLocationOption(mLocationOption);
//        }
//
//        //启动定位
//        mLocationClient.startLocation();
//    }


}
