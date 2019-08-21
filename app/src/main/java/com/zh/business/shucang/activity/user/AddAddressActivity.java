package com.zh.business.shucang.activity.user;

import android.content.DialogInterface;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.bean.ImmediatelyBean;
import com.waw.hr.mutils.model.CityModel;
import com.waw.hr.mutils.model.ProvinceModel;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.NewAddressType;
import com.zh.business.shucang.common.OrderDetailType;
import com.zh.business.shucang.databinding.ActivityAddAddressBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.GetJsonDataUtil;
import com.zh.business.shucang.utils.IntentUtils;
import com.zh.business.shucang.utils.SignUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding> {

    private OptionsPickerView optionsPickerView;

    private List<String> options1Items = new ArrayList<>();

    private List<ArrayList<String>> options2Items = new ArrayList<>();

    private List<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private GetJsonDataUtil getJsonDataUtil = new GetJsonDataUtil();

    private NewAddressType newAddressType;

    private AddressBean editAddressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("新增收货地址");
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
//        options1Items = getJsonDataUtil.getAllProvinceDatas();
//        options2Items = getJsonDataUtil.getAllProvinceCityDatas();
//        options3Items = getJsonDataUtil.getAllProvinceCityAreaDatas();
        getAllProvinceCityDatas();
        newAddressType = (NewAddressType) getIntent().getExtras().get(MKey.TYPE);
        if (newAddressType == NewAddressType.EDIT) {
            editAddressBean = (AddressBean) getIntent().getExtras().get(MKey.DATA);
            binding.etPhone.setText(editAddressBean.getPhone());
            binding.etArea.setText(editAddressBean.getAddress());
            binding.etDetailAddr.setText(editAddressBean.getInfo());
            binding.etName.setText(editAddressBean.getName());
        }
    }

    public void getAllProvinceCityDatas() {
        List<ProvinceModel> allDatas = getJsonDataUtil.getAllProvinceData();
        for (ProvinceModel provinceModel : allDatas) {
            options1Items.add(provinceModel.getName());
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> province_areaList = new ArrayList<>();
            for (CityModel cityModel : provinceModel.getCity()) {
                cityList.add(cityModel.getName());
                ArrayList<String> city_AreaList = new ArrayList<>();
                if (cityModel.getArea() == null || cityModel.getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(cityModel.getArea());
                }
                province_areaList.add(city_AreaList);
            }
            options2Items.add(cityList);
            options3Items.add(province_areaList);
        }

    }

    @Override
    public void initListener() {
        binding.etArea.setOnClickListener((v) -> {
            if (optionsPickerView == null) {
                initPickView();
            }
            optionsPickerView.show();
        });
        binding.tvSave.setOnClickListener((v) -> {
            addAddress();

        });
    }

    private void initPickView() {
        optionsPickerView = new OptionsPickerBuilder(AddAddressActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1) + " "
                        + options2Items.get(options1).get(option2) + " "
                        + options3Items.get(options1).get(option2).get(options3);
                binding.etArea.setText(tx);
            }
        }).build();
        optionsPickerView.setPicker(options1Items, options2Items, options3Items);
    }

    private void addAddress() {

        if (StringUtils.isEmpty(binding.etPhone.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }
        if (StringUtils.isEmpty(binding.etName.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入姓名", true);
            tipDialog.show();
            return;
        }

        if (StringUtils.isEmpty(binding.etArea.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请选择地区", true);
            tipDialog.show();
            return;
        }

        if (StringUtils.isEmpty(binding.etDetailAddr.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入详细地址", true);
            tipDialog.show();
            return;
        }

        params = SignUtils.getNormalParams();
        if (newAddressType == NewAddressType.EDIT) {
            params.put(MKey.ID, editAddressBean.getId());
        }
        params.put(MKey.PHONE, binding.etPhone.getText().toString());
        params.put(MKey.NAME, binding.etName.getText().toString());
        params.put(MKey.ADDRESS, binding.etArea.getText().toString());
        params.put(MKey.INFO, binding.etDetailAddr.getText().toString());

        if (newAddressType == NewAddressType.EDIT) {
            HttpClient.Builder.getServer().addressedit(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
                @Override
                public void onSuccess(BaseBean<Object> baseBean) {
                    tipDialog = DialogUtils.getSuclDialog(AddAddressActivity.this, baseBean.getMsg(), true);
                    tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
                    tipDialog.show();
                }

                @Override
                public void onError(BaseBean<Object> baseBean) {
                    tipDialog = DialogUtils.getFailDialog(AddAddressActivity.this, baseBean.getMsg(), true);
                    tipDialog.show();
                }
            });
        } else if (newAddressType == NewAddressType.NEW) {
            HttpClient.Builder.getServer().addressadd(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
                @Override
                public void onSuccess(BaseBean<Object> baseBean) {
                    tipDialog = DialogUtils.getSuclDialog(AddAddressActivity.this, baseBean.getMsg(), true);
                    tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
                    tipDialog.show();
                }

                @Override
                public void onError(BaseBean<Object> baseBean) {
                    tipDialog = DialogUtils.getFailDialog(AddAddressActivity.this, baseBean.getMsg(), true);
                    tipDialog.show();
                }
            });
        }

    }

}
