package com.zhy.sample_okhttp.util;

/**
 * desc:
 * author： LiangYuanQi
 * date：2015/10/29.
 */
public class WebAPI {
    private static final String URL_PREFIX = "http://192.168.1.32:18080/services/";
    /**
     * getMapByI的数据传递queryJson
     */
    public static final String URLGETBYID_QUERYJSON_STRING = "queryJson";

    public static final String CUSTOMER_UNITE_INFO = URL_PREFIX + "customer/unite/v2/getMapById";

    public static final String CUSTOMER_UNITE_UPDATE = URL_PREFIX + "customer/unite/v2/update";

    public static final String CUSTOMER_UNITE_LOGIN = URL_PREFIX + "customer/unite/login";

    public static final String CUSTOMER_CAOS_LOGIN = URL_PREFIX + "customer/unite/caosLogin";

    /**
     * 清空草稿列表
     */
    public static final String DELETE_DRAFT_LIST = URL_PREFIX + "customer/draft/v2/update";
}
