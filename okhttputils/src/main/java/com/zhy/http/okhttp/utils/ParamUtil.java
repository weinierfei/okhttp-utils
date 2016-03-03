package com.zhy.http.okhttp.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static java.net.URLEncoder.encode;

/**
 * desc: 参数处理
 * author： LiangYuanQi
 * date：2015/12/8.
 */
public class ParamUtil {

    /**
     * get 参数  url？key=value
     * 参数的名称和值需要通过encode转码
     *
     * @param url
     * @param params
     * @return
     */
    public static String appendParams(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        try {
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    sb.append(getEncodeString(entry.getKey(), "utf-8")).append("=").append(getEncodeString(entry.getValue(), "utf-8")).append("&");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        L.i(sb.toString());
        return sb.toString();
    }

    /**
     * 将map类型的参数转换为 指定的表单参数
     * @param builder 表单
     * @param params
     */
/*    public static void addParams(FormEncodingBuilder builder, Map<String,Object> params)
    {
        if(builder==null){
            throw new IllegalArgumentException("url put:builder can not be null");
        }
        if(params!=null && !params.isEmpty()){
            for(Map.Entry<String,Object> entry:params.entrySet()){
                builder.add(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "");
            }
        }

    }*/

    /**
     * encode 转码
     *
     * @param content 值
     * @param charset 编码格式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getEncodeString(Object content, String charset) throws UnsupportedEncodingException {
        if (content != null && TextUtils.isEmpty(content.toString())) {
            return "";
        }
        return encode(content.toString(), charset);
    }
}
