package com.zh.business.shucang.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.http.HttpClient;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityInfoBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.SignUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InfoActivity extends BaseActivity<ActivityInfoBinding> {

    private QMUIBottomSheet chooseAvatarSheet;

    private final int REQUEST_CAMERA = 40001, REQUEST_PHOTO = 40002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("用户资料");
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        member();
    }

    @Override
    public void initListener() {
        binding.tvSave.setOnClickListener((v) -> {
            if (StringUtils.isEmpty(binding.tvName.getText())) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入昵称", true);
                tipDialog.show();
                return;
            }
            editnick();
        });

        binding.vBg1.setOnClickListener((v) -> {
            if (UserService.getInstance().isLogin()) {
                if (chooseAvatarSheet == null) {
                    chooseAvatarSheet = DialogUtils.getAvatarBottomSheet(InfoActivity.this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                            if (position == 0) {
                                PictureSelector.create(InfoActivity.this)
                                        .openCamera(PictureMimeType.ofImage())
                                        .enableCrop(true)
                                        .withAspectRatio(1, 1)
                                        .maxSelectNum(1).compress(true)
                                        .forResult(REQUEST_CAMERA);
                                chooseAvatarSheet.dismiss();
                            } else {
                                PictureSelector.create(InfoActivity.this)
                                        .openGallery(PictureMimeType.ofImage())
                                        .maxSelectNum(1).enableCrop(true)
                                        .compress(true).withAspectRatio(1, 1)
                                        .forResult(REQUEST_PHOTO);
                                chooseAvatarSheet.dismiss();
                            }
                        }
                    });
                }
                chooseAvatarSheet.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 图片、视频、音频选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            avatarPath = selectList.get(0).getCompressPath();

            uploadAvatar();
//                UploadUtils.uploadImage(selectList.get(0).getCompressPath(), UploadUtils.getIDCardPath(), upCompletionHandler);
        }
    }

    private String avatarPath;

    private void uploadAvatar() {

        loadingDialog.show();

        File file = new File(avatarPath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        HttpClient.Builder.getServer().editIcon(UserService.getInstance().getToken(), body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getSuclDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
                ImageUtils.showImage(baseBean.getData(), binding.ivAvatar);
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void editnick() {
        params = SignUtils.getNormalParams();
        params.put(MKey.NICKNAME,binding.tvName.getText().toString());
        HttpClient.Builder.getServer().editName(UserService.getInstance().getToken(),params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
//                binding.tvName.setText((String) baseBean.getData().get("nickname"));
//                ImageUtils.showImage((String) baseBean.getData().get("avatar"), binding.ivAvatar);
                tipDialog = DialogUtils.getSuclDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                tipDialog = DialogUtils.getFailDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void member() {
        HttpClient.Builder.getServer().member(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                binding.tvName.setText((String) baseBean.getData().get("nickname"));
                ImageUtils.showImage((String) baseBean.getData().get("avatar"), binding.ivAvatar);
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                tipDialog = DialogUtils.getFailDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
