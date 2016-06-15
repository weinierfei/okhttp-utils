package com.zhy.sample_okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.sample_okhttp.util.LogUtil;
import com.zhy.sample_okhttp.util.WebAPI;
import com.zhy.sample_okhttp.util.WebFrontUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(String response, int id) {
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
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(String response, int id) {
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
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "res==" + response);
            }
        });
    }

    @OnClick(R.id.btn_download)
    public void testDownload() {

        String url = "http://v.allinmd.cn/480_320/flv/4hs47c8k/1404239447.flv";
        OkHttpUtils.get().url(url).build().execute(new FileCallBack("mnt/sdcard/Allinmd", "mytest.mp4") {

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                Log.i(TAG, "downing==" + (100 * progress));
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "Exception==" + e + "        call==" + call);
            }


            @Override
            public void onResponse(File response, int id) {

            }
        });
    }


    String fileContent = "";

    @OnClick(R.id.btn_base64)
    public void testBase64() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG, "开始编码");
                String path = "/mnt/sdcard/DCIM/Camera/IMG_20160411_024821.jpg";
                File file = new File(path);
                fileContent = getImageStr(file);
                LogUtil.i(TAG, "jieguo==" + fileContent);
            }
        }).start();
    }

    @OnClick(R.id.btn_upload_post)
    public void testPostUpload() {
        Map<String, Object> params = WebFrontUtil.getBaseParam(this, null);
        params.put("topicId", "1465985377937");
        params.put("extName", "jpg");
        params.put("fileContent", fileContent);

        Gson gson = new Gson();


        OkHttpUtils.postString()
                .url("http://192.168.1.32:18080/services/cms/topic/attachment/v2/create")
                .content(gson.toJson(params))
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                LogUtil.i(TAG, "jindu==" + response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                LogUtil.i(TAG, "jindu==" + progress);
            }
        });
    }

    public static String getImageStr(File file) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            if (file != null) {
                in = new FileInputStream(file);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 对字节数组Base64编码
        // encoder = new BASE64Encoder();
        return encode(data);// 返回Base64编码过的字节数组字符串
        // return encode(data);BASE64Encoder
    }

    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String encode(byte[] from) {
        if (from != null) {
            StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
            int num = 0;
            char currentByte = 0;
            for (int i = 0; i < from.length; i++) {
                num = num % 8;
                while (num < 8) {
                    switch (num) {
                        case 0:
                            currentByte = (char) (from[i] & lead6byte);
                            currentByte = (char) (currentByte >>> 2);
                            break;
                        case 2:
                            currentByte = (char) (from[i] & last6byte);
                            break;
                        case 4:
                            currentByte = (char) (from[i] & last4byte);
                            currentByte = (char) (currentByte << 2);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & lead2byte) >>> 6;
                            }
                            break;
                        case 6:
                            currentByte = (char) (from[i] & last2byte);
                            currentByte = (char) (currentByte << 4);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & lead4byte) >>> 4;
                            }
                            break;
                    }
                    to.append(encodeTable[currentByte]);
                    num += 6;
                }
            }
            if (to.length() % 4 != 0) {
                for (int i = 4 - to.length() % 4; i > 0; i--) {
                    to.append("=");
                }
            }
            return to.toString();
        } else {
            return "";
        }

    }
}
