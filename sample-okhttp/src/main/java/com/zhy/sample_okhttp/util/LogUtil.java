package com.zhy.sample_okhttp.util;

import android.util.Log;

/**
 * 日志统一管理
 *
 * @author ZhangChangAn
 */
public class LogUtil {

    public static void e(String tag, String msg) {
            Log.e(tag, msg);
    }

    public static void i(String tag, Object msg) {

        Log.i(tag, String.valueOf(msg));
    }


}
