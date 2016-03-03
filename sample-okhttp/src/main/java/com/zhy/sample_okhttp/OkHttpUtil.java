package com.zhy.sample_okhttp;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.sample_okhttp.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:ok-http 网络请求
 * author： LiangYuanQi
 * date：2015/12/5.
 */
public class OkHttpUtil {

    /**
     * 无参get请求
     *
     * @param url
     * @param callback
     */
    public static void get(String url, StringCallback callback) {
        LogUtil.i("get url---:", url);
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    /**
     * 含有上下文，无参get请求
     *
     * @param url
     * @param callback
     */
    public static void get(Context context, String url, StringCallback callback) {

        LogUtil.i("get url---:", url);
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    /**
     * 不传上下文参数为map的get请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void get(String url, Map params, StringCallback callback) {
        String jsonParam = new GsonBuilder().create().toJson(params);
        Map p = new HashMap();
        p.put("queryJson", jsonParam);
        LogUtil.i("get url---:", url + "params=:" + p.toString());
        OkHttpUtils.get().url(url).params(p).build().execute(callback);
    }

    /**
     * 参数为map的get请求
     *
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    public static void get(Context context, String url, Map params, StringCallback callback) {

        String jsonParam = new GsonBuilder().create().toJson(params);
        Map p = new HashMap();
        p.put("queryJson", jsonParam);
        LogUtil.i("get url---:", url + "params--:" + p.toString());
        OkHttpUtils.get().url(url).params(p).build().execute(callback);
    }


    /**
     * put请求(一般用于更新)
     *
     * @param url
     * @param params
     * @param callback 不关心返回值的时,该参数传入null即可
     */
    public static void put(String url, Map params, StringCallback callback) {
        LogUtil.i("put url---:", url);
        LogUtil.i("put param---:", params);
        String jsonParam = new GsonBuilder().create().toJson(params);
        OkHttpUtils.put().url(url).requestBody(jsonParam).build().execute(callback);
    }


    /**
     * put请求(一般用于更新)
     *
     * @param context
     * @param url
     * @param params
     * @param callback 不关心返回值的时,该参数传入null即可
     */
    public static void put(Context context, String url, Map params, StringCallback callback) {

        LogUtil.i("put url---:", url);
        LogUtil.i("put param---:", params);
        String jsonParam = new GsonBuilder().create().toJson(params);
        OkHttpUtils.put().url(url).requestBody(jsonParam).build().execute(callback);
    }


    /**
     * post请求(一般用于创建create)
     *
     * @param url
     * @param params
     * @param callback 不关心返回值的时,该参数传入null即可
     */
    public static void post(String url, Map params, StringCallback callback) {
        LogUtil.i("post url---:", url);
        LogUtil.i("post param", params);
        String jsonParam = new GsonBuilder().create().toJson(params);
        OkHttpUtils.postString().url(url).content(jsonParam).build().execute(callback);
    }

    /**
     * post请求(一般用于创建create)
     *
     * @param context
     * @param url
     * @param params
     * @param callback 不关心返回值的时,该参数传入null即可
     */
    public static void post(Context context, String url, Map params, StringCallback callback) {

        LogUtil.i("post url---:", url);
        LogUtil.i("post param", params);
        String jsonParam = new GsonBuilder().create().toJson(params);
        OkHttpUtils.postString().url(url).content(jsonParam).build().execute(callback);
    }


}
