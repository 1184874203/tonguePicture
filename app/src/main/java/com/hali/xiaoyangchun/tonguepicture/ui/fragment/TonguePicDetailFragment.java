package com.hali.xiaoyangchun.tonguepicture.ui.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hali.xiaoyangchun.tonguepicture.R;
import com.hali.xiaoyangchun.tonguepicture.bean.TongueResult;
import com.hali.xiaoyangchun.tonguepicture.bean.User;
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory;
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenerManager;
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenr;
import com.hali.xiaoyangchun.tonguepicture.model.net.CommonRequest;
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseRequestFragment;
import com.hali.xiaoyangchun.tonguepicture.utils.DataUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TonguePicDetailFragment extends BaseRequestFragment implements ChangeListenr {

    public static String TonguePicDetailFragment_TAG = "TonguePicDetailFragment_TAG";

    private TextView name, time, otherString, k1, k2, k3, k4, v1, v2, v3, v4;
    private ImageView tongueImg;
    private User user;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tongue_detail;
    }

    @Override
    public void initViews() {
        super.initViews();
        tongueImg = findView(R.id.iv_tongue);
        name = findView(R.id.tv_name);
        time = findView(R.id.tv_time);
        k1 = findView(R.id.tv_p1_k);
        k2 = findView(R.id.tv_p2_k);
        k3 = findView(R.id.tv_p3_k);
        k4 = findView(R.id.tv_p4_k);
        v1 = findView(R.id.tv_p1_v);
        v2 = findView(R.id.tv_p2_v);
        v3 = findView(R.id.tv_p3_v);
        v4 = findView(R.id.tv_p4_v);
        otherString = findView(R.id.tv_otherString);
    }

    @Override
    public void initData() {
        ChangeListenerManager.Companion.getInstance().registerListener("tongue_detail", this);
        user = new Gson().fromJson(mActivity.getIntent().getStringExtra("user"), User.class);
        Glide.with(this).load("https://" + user.getPicPath()).into(tongueImg);
        name.setText(user.getName() + "");
        time.setText(DataUtils.getSimpleDate(user.getTime(), "yyyy.MM.dd") + "");
        otherString.setText(user.getOtherString() + "");
        CommonRequest.INSTANCE.getTongueDetial(mActivity, user.getPicPath(), this);
        showProgressDialog();
    }

    @Override
    public void onSuccess(@Nullable Object response, int requestCode) {
        Log.i("诊断详情", response.toString());
        user.setIsRead(true);
        ManagerFactory.Companion.getInstance(mActivity).getUserManager().saveOrUpdate(user);
        ChangeListenerManager.Companion.getInstance().notifyDataChanged("tongue_detail", response);
        ChangeListenerManager.Companion.getInstance().notifyDataChanged("changeListenerManager_db_read", response);
        hideProgressDialog();
    }

    /**
     * 张晨昊的解析。。。。。
     *
     * @return
     */
    private String[] checkResult(String result) {
        return result.split("-");
    }

    @Override
    public void onError(@NotNull String error) {
        hideProgressDialog();
    }

    @Override
    public void onChange(@NotNull String key, @NotNull Object data) {
        if ("tongue_detail".equals(key)) {
            TongueResult result = new Gson().fromJson((String) data, TongueResult.class);
            String[] r1 = checkResult(result.getKey1());
            String[] r2 = checkResult(result.getKey2());
            String[] r3 = checkResult(result.getKey3());
            String[] r4 = checkResult(result.getKey4());
            k1.setText(r1[0] + "");
            k2.setText(r2[0] + "");
            k3.setText(r3[0] + "");
            k4.setText(r4[0] + "");
            v1.setText(r1[1] + "");
            v2.setText(r2[1] + "");
            v3.setText(r3[1] + "");
            v4.setText(r4[1] + "");
            String imgUrl = result.getImgAfterProcess();
            if (!TextUtils.isEmpty(imgUrl) && isAdded() && mActivity != null) {
                Glide.with(mActivity).load(imgUrl).into(tongueImg);
            }
        }
    }

    @NotNull
    @Override
    public String getRequestUrl() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.clear(tongueImg);
    }
}
