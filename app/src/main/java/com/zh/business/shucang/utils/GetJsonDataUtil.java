package com.zh.business.shucang.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.model.CityModel;
import com.waw.hr.mutils.model.ProvinceModel;
import com.zh.business.shucang.MAPP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetJsonDataUtil {

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public List<ProvinceModel> getAllProvinceData() {
        return JSON.parseArray(getJson(MAPP.mapp, "province.json"), ProvinceModel.class);
    }

    public List<String> getAllProvinceDatas() {
        List<ProvinceModel> allDatas = getAllProvinceData();
        List<String> result = new ArrayList<>();
        for (ProvinceModel provinceModel : allDatas) {
            result.add(provinceModel.getName());
        }
        return result;
    }

    public List<ArrayList<String>> getAllProvinceCityDatas() {
        List<ProvinceModel> allDatas = getAllProvinceData();
        List<ArrayList<String>> result = new ArrayList<>();
        for (ProvinceModel provinceModel : allDatas) {
            ArrayList<String> citys = new ArrayList<>();
            for (CityModel cityModel : provinceModel.getCity()) {
                citys.add(cityModel.getName());
            }
            result.add(citys);
        }
        return result;
    }


    public List<ArrayList<ArrayList<String>>> getAllProvinceCityAreaDatas() {

        List<ProvinceModel> allDatas = getAllProvinceData();

        List<ArrayList<ArrayList<String>>> result = new ArrayList<>();

        ArrayList<ArrayList<String>> result2 = new ArrayList<>();

        for (ProvinceModel provinceModel : allDatas) {
            ArrayList<String> areas = new ArrayList<>();
            for (CityModel cityModel : provinceModel.getCity()) {
                if(cityModel.getArea() == null || cityModel.getArea().size() == 0){
                    areas.add("");
                }else{
                    areas.addAll(cityModel.getArea());
                }

            }

            result2.add(areas);
            result.add(result2);
        }



        return result;
    }

}
