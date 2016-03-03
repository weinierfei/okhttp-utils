package com.zhy.sample_okhttp.util;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * json字符串，动态解析（根据json有多少个字段，动态保存到map集合里面）
 * 
 * @author lyq
 * @date 2015-4-14
 */
public class WebFrontUtil {

	/**
	 * 获取基本参数
	 * 
	 * @param paramMap
	 * @return
	 */
	public static Map getBaseParam(Context context, Map paramMap) {
		if (paramMap == null)
			paramMap = new HashMap();
		if (!paramMap.containsKey("visitSiteId")) {
			paramMap.put("visitSiteId", "6");
		}
		paramMap.put("opIp", "");
		if (context != null) {
			String advice = Tools.getSystemVersion(context);
			if (StringUtil.isNotEmpty(advice)) {
				advice = "无," + advice.replace("_android", ",android");// 浏览器类型,手机类型,系统版本号
				paramMap.put("opAdvice", advice);
			}
		}
		return paramMap;
	}

//	/**
//	 * 获取基本参数
//	 *
//	 * @param paramMap
//	 * @return
//	 */
//	public static RequestParams getBaseRequestParam(Context context,
//			RequestParams paramMap) {
//		if (paramMap == null)
//			paramMap = new RequestParams();
//		paramMap.setContentEncoding("UTF_8");
//		String ip = SystemSettingData.NET_IP;
//		paramMap.put("opIp", ip);
//		if (context != null) {
//			String advice = Tools.getSystemVersion(context);
//			if (StringUtil.isNotEmpty(advice)) {
//				advice = "无," + advice.replace("_android", ",android");// 浏览器类型,手机类型,系统版本号
//				paramMap.put("opAdvice", advice);
//			}
//		}
//		return paramMap;
//	}

	/**
	 * 分页参数转换 将pageIndex、pageIndex 转换成 firstResult、maxResult
	 * 
	 * @param paramMap
	 *            ()
	 */
	public static void handlePage(Map paramMap) {
		int pageIndex = 0;
		int pageSize = 0;
		String pageIndexString = paramMap.get("pageIndex") != null ? paramMap
				.get("pageIndex").toString() : "1";
		String pageSizeString = paramMap.get("pageSize") != null ? paramMap
				.get("pageSize").toString() : "10";

		if (pageIndexString.length() > 0) {
			pageIndex = Integer.valueOf(pageIndexString);
		} else {
			pageIndex = 1;
		}
		if (pageSizeString.length() > 0) {
			pageSize = Integer.valueOf(pageSizeString);
		} else {
			pageSize = 10;
		}

		int firstResult = (pageIndex - 1) * pageSize;
		int maxResult = pageSize;
		paramMap.put("firstResult", firstResult);
		paramMap.put("maxResult", maxResult);

	}


}
