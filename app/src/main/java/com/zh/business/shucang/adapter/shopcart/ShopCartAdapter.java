package com.zh.business.shucang.adapter.shopcart;

import android.media.NotProvisionedException;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.common.GoodsItem;
import com.zh.business.shucang.databinding.ItemShopCartBinding;
import com.zh.business.shucang.fragment.ShopCartFragment;
import com.zh.business.shucang.service.GoodsService;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopCartAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();

    private QMUIDialog qmuiDialog;

    private int deleteIndex;

    private GoodsService goodsService = new GoodsService();

    private Map<Integer,GoodsBean> selectedGoodsBean = new HashMap<>();

    private WeakReference<ShopCartFragment> weakReference;

    private boolean allCheck = false;

    public ShopCartAdapter(List data,WeakReference<ShopCartFragment> weakReference) {
        super(R.layout.item_shop_cart, data);
        this.weakReference = weakReference;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemShopCartBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.cbCheck.setChecked(false);
        binding.tvTitle.setText(goodsBean.getName());
        binding.tvPrice.setText(goodsBean.getPrice());
        ImageUtils.showImage(goodsBean.getCover_image(), binding.ivImg);
        binding.tvNumTip.setText("x" + String.valueOf(goodsBean.getNum()));
        binding.tvNum.setText(String.valueOf(goodsBean.getNum()));
        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.toGoodsDetailActivity(goodsBean.getGid());
        });

        binding.vJia.setOnClickListener((v) -> {
//            goodsBean.setNum(goodsBean.getNum() + 1);
//            binding.tvNum.setText(String.valueOf(goodsBean.getNum()));
//            binding.tvNumTip.setText("x" +String.valueOf(goodsBean.getNum()));
            goodsService.cartItem(String.valueOf(goodsBean.getId()), GoodsItem.ADD);
            weakReference.get().getShopcartData();
            weakReference.get().calculateSelectedGoodsPrice(selectedGoodsBean);
        });

        binding.vJian.setOnClickListener((v) -> {

//            goodsBean.setNum(goodsBean.getNum() - 1);
//            binding.tvNum.setText(String.valueOf(goodsBean.getNum()));
//            binding.tvNumTip.setText("x" +String.valueOf(goodsBean.getNum()));
            goodsService.cartItem(String.valueOf(goodsBean.getId()), GoodsItem.REDUCE);
            weakReference.get().getShopcartData();
            weakReference.get().calculateSelectedGoodsPrice(selectedGoodsBean);
        });

        binding.vDelete.setOnClickListener((v) -> {
            deleteIndex = helper.getLayoutPosition();
            if (qmuiDialog == null) {
                initDialog();
            }
            qmuiDialog.show();
        });

        binding.vCheck.setOnClickListener((v)->{
            if(binding.cbCheck.isChecked()){
                binding.cbCheck.setChecked(false);
                selectedGoodsBean.remove(goodsBean.getId());
                weakReference.get().calculateSelectedGoodsPrice(null);
            }else{
                binding.cbCheck.setChecked(true);
                selectedGoodsBean.put(goodsBean.getId(),goodsBean);
                weakReference.get().calculateSelectedGoodsPrice(selectedGoodsBean);
            }
        });

        if(allCheck){
            binding.vCheck.performClick();
        }else{
            binding.cbCheck.setChecked(false);
        }
    }

    private void initDialog() {
        qmuiDialog = new QMUIDialog.MessageDialogBuilder(MAPP.mapp.getCurrentActivity())
                .setMessage("确定要删除商品?")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        qmuiDialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        qmuiDialog.dismiss();
                        goodsService.removeShopCart(String.valueOf(getData().get(deleteIndex).getId()));
                        selectedGoodsBean.remove(getData().get(deleteIndex).getId());
                        remove(deleteIndex);
                        notifyDataSetChanged();
                        weakReference.get().calculateSelectedGoodsPrice(selectedGoodsBean);
                    }
                })
                .create();
    }

    public Map<Integer, GoodsBean> getSelectedGoodsBean() {
        return selectedGoodsBean;
    }

    public void setSelectedGoodsBean(Map<Integer, GoodsBean> selectedGoodsBean) {
        this.selectedGoodsBean = selectedGoodsBean;
    }

    public boolean isAllCheck() {
        return allCheck;
    }

    public void setAllCheck(boolean allCheck) {
        this.allCheck = allCheck;
    }
}
