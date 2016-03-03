package com.zhy.sample_okhttp.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 此类为一工具类，包含了各种常用共用方法 1、将文件转为Base64字符串 2、将Bitmap转为Base64字符串 3、保存Bitmap到文件
 * 4、将日期转换为一定格式的字符串 5、将一定格式的日期转换为毫秒数 6、显示提示信息 7、弹出警示对话框，并且有两个按钮 8、显示选择日期对话框到文本框中
 * 9、显示时间对话框 只显示时分秒 10、显示一个滚动进度条条对话框 11、关闭滚动进度条对话框 12、XXXX-XX-XX格式的日期字符串转成星期几
 * 13、XXXXX是否为手机号 14、XXXXX是否为邮箱 15、计算两个日期天数
 *
 * @author Administrator
 */
public class Tools {
    public static final String HTTP_ERROR = "网络不通，请重试！";
    // public static final String HTTP_ERROR = "网络不通，请查看您的网络环境再重试！";
    public static final String LOAD_ALL = "已经加载全部数据！";

    /**
     * 将文件转为Base64字符串
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 将Bitmap转为Base64字符串
     *
     * @param bitmap
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
            // 将bitmap做成输出流，保证图片质量100%
            bitmap.compress(CompressFormat.PNG, 100, baos);
            // 转换成byte数组
            byte[] appicon = baos.toByteArray();// 转为byte数组
            // 以base64编码将byte数组转换成字符串
            return Base64.encodeToString(appicon, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 将日期转换为一定格式的字符串
     *
     * @param date
     * @return
     */
    public static String DateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String DateFormat(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }


    /**
     * 获取指定日后 后 dayAddNum 天的 日期
     *
     * @param day       日期，格式为String："2013-9-3";
     * @param dayAddNum 增加天数 格式为int;
     * @return
     */
    public static String getDateStr(String day, int dayAddNum) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    /**
     * 显示时间对话框 只显示时分秒
     */
    public static void timePicker(Context context, final TextView textView) {
        Calendar c = Calendar.getInstance();
        TimePickerDialog d = new TimePickerDialog(context, new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                textView.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute));
            }
        }, // 回调函数
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        d.show();
    }

    // public static ProgressDialog progressDialog;
    //
    // /**
    // * 显示一个滚动条对话框
    // * */
    // public static void showProgressDialog(Context context, String message) {
    // try {
    // if (progressDialog == null) {
    // progressDialog = ProgressDialog.show(context, null, message);
    // progressDialog.setCanceledOnTouchOutside(true);
    // // progressDialog.setCancelable(true);
    // } else if (progressDialog != null) {
    // if (!progressDialog.isShowing())
    // progressDialog.setMessage(message);
    // }
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    //
    // /**
    // * 关闭滚动对话框
    // */
    // public static void closeProgressDialog() {
    // try {
    // if (progressDialog != null && progressDialog.isShowing()) {
    // progressDialog.cancel();
    // progressDialog = null;
    // }
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    /**
     * XXXX-XX-XX格式的日期字符串转成星期几
     *
     * @param dateString
     * @return
     */
    public static String showWeek(String dateString) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sf.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekofday = c.get(Calendar.DAY_OF_WEEK);
        String returnResault = "";
        switch (weekofday) {
            case 1:
                returnResault = "星期日";
                break;
            case 2:
                returnResault = "星期一";
                break;
            case 3:
                returnResault = "星期二";
                break;
            case 4:
                returnResault = "星期三";
                break;
            case 5:
                returnResault = "星期四";
                break;
            case 6:
                returnResault = "星期五";
                break;
            case 7:
                returnResault = "星期六";
                break;
        }
        return returnResault;
    }

    /**
     * 对返回的日期进行处理
     *
     * @param dateStr
     * @return
     */
    public static String DateStrToDateStr(String dateStr) {
        if (dateStr != null) {
            if (dateStr.length() > 18) {
                String date = dateStr.substring(0, 10);
                // String time = dateStr.substring(11, 19);
                // return date + " " + time;
                return date;
            } else {
                return dateStr;
            }
        } else {
            return "";
        }
    }

    /**
     * 判断是否为手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否为邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(long date1, long date2) {

        long between_days = (date2 - date1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 处理五角星评分显示，裁剪图片
     *
     * @param bitmap
     * @param scor
     * @return
     */
    public static Bitmap cutBitmap(Bitmap bitmap, float scor) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapResult = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        // 创建画布
        Canvas canvas = new Canvas(bitmapResult);

        float w = scor / 100 * (float) width;
        if (w == 0) {
            w += 1;
        }

        // 下面这句是关键,剪裁图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) w, height, null, false);
        // 将图片按照坐标0,0 去拼接到画布
        canvas.drawBitmap(bitmap, 0, 0, null);

        return bitmapResult;
    }

    /**
     * 获取当前应用程序的版本号
     */
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 是否是数字
     *
     * @param string
     * @return
     */
    public static boolean matcherNumber(String string) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**
     * 是否是字母
     *
     * @param string
     * @return
     */
    public static boolean matcherLetter(String string) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**
     * 是否是中文
     *
     * @param string
     * @return
     */
    public static boolean matcherCha(String string) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**
     * 获取输入的字节数（中文默认都是两个字节）
     *
     * @param str
     * @return
     */
    public static int resultNumInputString(String str) {
        int chatStr = 0;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        char[] strChrs = str.toCharArray();
        for (int i = 0; i < strChrs.length; i++) {
            int chat = 0;
            if (matcherCha(String.valueOf(strChrs[i]))) {
                chat = String.valueOf(strChrs[i]).length() * 2;
            } else if (matcherLetter(String.valueOf(strChrs[i]))) {
                chat = String.valueOf(strChrs[i]).length();
            } else if (matcherNumber(String.valueOf(strChrs[i]))) {
                chat = String.valueOf(strChrs[i]).length();
            }
            chatStr += chat;
        }
        return chatStr;
    }

    /**
     * 获取字符的长度（中文为2，英文为1）
     *
     * @param cha
     * @return
     */
    public static int resultNumInputChar(char cha) {
        int chatStr = 0;
        if (matcherCha(String.valueOf(cha))) {
            chatStr = String.valueOf(cha).length() * 2;
        } else if (matcherLetter(String.valueOf(cha))) {
            chatStr = String.valueOf(cha).length();
        } else if (matcherNumber(String.valueOf(cha))) {
            chatStr = String.valueOf(cha).length();
        }
        return chatStr;
    }

    /**
     * 　　 * 取字符串的前toCount个字符 (一个汉字2个字节,英文一个字节) 　　 * 　　 * @param str 被处理字符串 　　 * @param
     * toCount 截取长度 　　 * @param more 后缀字符串 　　 * @return String
     */
    public static String mysubstring(String str, int toCount, String more) throws Exception {
        int reInt = 0;
        String reStr = "";
        if (str == null)
            return "";
        char[] tempChar = str.toCharArray();
        for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
            String s1 = String.valueOf(tempChar[kk]);
            byte[] b = s1.getBytes();
            reInt += b.length;
            reStr += tempChar[kk];
        }
        if (toCount == reInt || (toCount == reInt - 1))
            reStr += more;
        return reStr;
    }

    /**
     * 获取输入的字符串个数（中文默认都是1个）
     *
     * @param str
     * @return
     */
    public static int resultNumInputStringMax(String str, int maxLength) {
        int chatStr = 0;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        char[] strChrs = str.toCharArray();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strChrs.length; i++) {
            int chat = 0;
            if (matcherCha(String.valueOf(strChrs[i]))) {
                chat = String.valueOf(strChrs[i]).length() * 2;
            } else if (matcherLetter(String.valueOf(strChrs[i]))) {
                chat = String.valueOf(strChrs[i]).length();
            } else if (matcherNumber(String.valueOf(strChrs[i]))) {
                chat = String.valueOf(strChrs[i]).length();
            }
            chatStr += chat;
            sb.append(strChrs[i]);
            if (chatStr == maxLength) {
                chatStr = sb.length();
                break;
            }
        }
        return chatStr;
    }


    /**
     * 计算年龄 getAge 日期
     *
     * @return
     */

    public static Date parseDate(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(s);
    }

    /**
     * 获取年龄
     *
     * @param birthDay yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static String getAge(String birthDay) throws Exception {

        Date birDay = parseDate(birthDay);

        Calendar cal = Calendar.getInstance();

        if (cal.before(birDay)) {
            throw new IllegalArgumentException("The birDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }

        return age + "";
    }

    /**
     * 获取手机系统版本
     *
     * @param context
     * @return chz
     */
    @SuppressLint("NewApi")
    public static String getSystemVersion(Context context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mtype = android.os.Build.MODEL; // 手机型号
        String sdk_v = android.os.Build.VERSION.SDK;// 手机系统版本
        String os_v = android.os.Build.VERSION.RELEASE;
        return mtype + "_android" + os_v;
    }

    /**
     * 判断网络连接状态
     *
     * @param context
     * @return chz
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断wifi状态
     *
     * @param context
     * @return chz
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.isConnected()) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断移动网络
     *
     * @param context
     * @return chz
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null && mMobileNetworkInfo.isConnected()) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取连接类型
     *
     * @param context
     * @return chz
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    /**
     * 移动View
     *
     * @param top
     * @param bottom
     * @param left
     * @param right
     * @param view
     */
    public static void move(int top, int bottom, int left, int right, View view) {
        FrameLayout.LayoutParams btnLp = (FrameLayout.LayoutParams) view.getLayoutParams();
        btnLp.setMargins(left, top, right, bottom);
        view.requestLayout();
    }

    /**
     * 定位某个控件
     *
     * @param top
     * @param bottom
     * @param left
     * @param right
     * @param view
     */
    public static void setViewLocation(int top, int bottom, int left, int right, View view) {
        FrameLayout.LayoutParams btnLp = (FrameLayout.LayoutParams) view.getLayoutParams();
        btnLp.setMargins(left, top, right, bottom);
        view.requestLayout();
    }

    /**
     * 将隐式启动转换为显示启动
     *
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    /**
     * 将字符串转换为map,字符串格式 messageNum=0, sourceId=1433752490915
     *
     * @param str
     * @return
     */
    public static Map<String, Object> getMap(String str) {
        Map map = new HashMap();
        String[] r = str.split(",");
        for (int i = 0; i < r.length; i++) {
            String s = r[i];
            String[] rs = s.split("=");
            if (rs.length >= 2) {
                String key = rs[0];
                String value = rs[1];
                map.put(key, value);
            }
        }
        return map;
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }

    /**
     * 判断一个Activity是否在后台
     *
     * @param context
     * @return true 代表在后台，false 代表在前台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将小图片转成大图
     *
     * @param url
     * @return
     */
    public static String imageUrlTrans(String url) {
        if (url.contains("_c_d_sp150_150")) {
            url = url.replace("_c_d_sp150_150", "_c");
        } else if (url.contains("_c_t_150_150")) {
            url = url.replace("_c_t_150_150", "_c");
        } else if (url.contains("_c_p_150_150")) {
            url = url.replace("_c_p_150_150", "_c");
        } else if (url.contains("_c.150_150")) {
            url = url.replace("_c.150_150", "_c");
        } else if (url.contains("_c_t_300_200")) {
            url = url.replace("_c_t_300_200", "_c");
        } else if (url.contains("_c_t_225_150")) {
            url = url.replace("_c_t_225_150", "_c");
        } else if (url.contains("_c_t_200_200")) {
            url = url.replace("_c_t_200_200", "_c");
        } else if (url.contains("_c_t_600_400")) {
            url = url.replace("_c_t_600_400", "_c");
        } else if (url.contains("_c_t_75_75")) {
            url = url.replace("_c_t_75_75", "_c");
        } else if (url.contains("_c_t_900_600")) {
            url = url.replace("_c_t_900_600", "_c");
        }
        return url;
    }


}
