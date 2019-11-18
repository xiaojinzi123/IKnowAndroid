package com.iknow.module.datasource.network;

/**
 * @author Bacchus
 * @desc http请求配置
 * @date: 2019.04.28
 */
public class HttpConfig {
    public static final int HTTP_CONNECT_TIME = 120;//超时时间
    public static final int HTTP_READ_TIME = 120;//读取时间
    public static final int HTTP_WRITE_TIME = 120;//写入时间
    public static final String KEY_REQUEST_HEADER_SIGN = "x-ehi-sign";// 一嗨签名请求头key
    public static final String KEY_REQUEST_HEADER_PUBLIC_PARAMETER = "x-ehi-public-parameter";// 一嗨公共参数请求头key
    public static final String KEY_REQUEST_HEADER_TOKEN = "authorization";// 请求token字段key

    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1004;
    /**
     * 签名验证失败
     */
    public static final int SIGN_AUTH_FAILED = 1005;
    /**
     * 一嗨api业务流程失败
     * errorCode !=0
     */
    public static final int EHI_API_FAILED = 1005;
}
