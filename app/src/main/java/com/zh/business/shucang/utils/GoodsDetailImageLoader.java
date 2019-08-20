package com.zh.business.shucang.utils;

import android.content.Context;
import android.widget.ImageView;

import com.waw.hr.mutils.bean.IndexBean;
import com.youth.banner.loader.ImageLoader;

public class GoodsDetailImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageUtils.showImage((String) path,imageView);
    }
}
