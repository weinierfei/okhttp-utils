package com.zhy.sample_okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.sample_okhttp.util.WebAPI;
import com.zhy.sample_okhttp.util.WebFrontUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Description:
 *
 * @author: guoyongping
 * @date: 2016/3/3 10:36
 */
public class MyTestActivity extends Activity {

    private static final String TAG = "MyTestActivity";

    @Bind(R.id.button)
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void get() {
        String url = "http://192.168.1.32:18080/services/cms/doc/v2/getMapList";
        Map<String, Object> params = new HashMap<>();
        params.put("sessionCustomerId", "1434505803474");
        params.put("useFlag", "3");
        params.put("attUseFlag", "8");
        params.put("logoUseFlag", "4");
        params.put("isValid", "1");
        params.put("sortType", "5");
        params.put("firstResult", "0");
        params.put("maxResult", "20");

        String jsonParam = new GsonBuilder().create().toJson(params);
        Map<String, Object> p = new HashMap<>();
        p.put("queryJson", jsonParam);

        OkHttpUtils.get().url(url).params(p).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "res==" + response);
            }
        });
    }

    @OnClick(R.id.btn_post)
    public void testPost() {
        Map<String, Object> params = WebFrontUtil.getBaseParam(this, null);
        params.put("email", "fg@qq.com");
        params.put("passwd", "111111");

        String jsonParam = new GsonBuilder().create().toJson(params);


        OkHttpUtils.postString().url(WebAPI.CUSTOMER_UNITE_LOGIN).content(jsonParam).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "res==" + response);
            }
        });

    }

    @OnClick(R.id.btn_put)
    public void testPut() {
        Map<String, Object> params = WebFrontUtil.getBaseParam(this, null);
        params.put("isRemind", "0");
        params.put("customerId", "1434505803474");
        params.put("reviewType", 7);
        params.put("refId", "1433294161928");
        String jsonParam = new GsonBuilder().create().toJson(params);
        OkHttpUtils.put().url(WebAPI.DELETE_DRAFT_LIST).requestBody(jsonParam).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "res==" + response);
            }
        });
    }

    @OnClick(R.id.btn_download)
    public void testDownload() {

        String url = "http://v.allinmd.cn/480_320/flv/4hs47c8k/1404239447.flv";
        OkHttpUtils.get().url(url).build().execute(new FileCallBack("mnt/sdcard/Allinmd","mytest.mp4") {
            @Override
            public void inProgress(float progress) {
                Log.i(TAG, "downing==" +  (100 * progress));
            }

            @Override
            public void onError(Call call, Exception e) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(File response) {
                Log.i(TAG, "file==" + response);
            }
        });


    }

}
