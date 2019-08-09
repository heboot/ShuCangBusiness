package com.zh.business.shucang.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class IndexGridItemDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public IndexGridItemDecoration(Context context) {
        margin = QMUIDisplayHelper.dp2px(context, 15);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0   


        if (parent.getChildLayoutPosition(view) > 0 && parent.getChildLayoutPosition(view) % 1 == 0) {
            outRect.set(margin, 0, margin, 0);
        } else {
            outRect.set(0, 0, margin, 0);
        }
    }
}

