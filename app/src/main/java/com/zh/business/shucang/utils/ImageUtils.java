package com.zh.business.shucang.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void showImage(String url, ImageView imageView){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
