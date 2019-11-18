package com.iknow.lib.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Bacchus
 * @desc 网络检测工具
 * @date: 2019.04.28
 */
public class NetWorkUtil {

    private static final String CONSTANT_MAC = "02:00:00:00:00:00";
    private static final String DEFAULT_IP = "192.168.1.1";  // 默认IP
    public static final int NO_NET_CONNECT = -1; // 没有可用网络
    public static final int WAP_CONNECTED = 0;  // wap网络可用
    public static final int NET_CONNECTED = 1;  // net网 可用
    public static final int WIFI_CONNECT = 2;  // wifi网络可用

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            return null != networkInfo && networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为WIFI
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 获取手机的WiFi mac地址
     */
    public static String getPhoneMAC(Context mContext) {
        String macAddress = "";
        try {
            WifiManager wifimanger = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiinfo = wifimanger.getConnectionInfo();
            macAddress = wifiinfo.getMacAddress();
        } catch (NullPointerException e) {//2018.7.23捕捉空指针异常
            // TODO 日志
//            EHiLog.e(e.getMessage());
        } catch (Exception e) {
            // TODO 日志
//            EHiLog.e(e.getMessage());
        }
        if (TextUtils.isEmpty(macAddress) || CONSTANT_MAC.equalsIgnoreCase(macAddress)) {
            macAddress = getMacWay1();
        }
        if (TextUtils.isEmpty(macAddress)) {
            macAddress = getMacWay2();
        }
        return macAddress == null ? "" : macAddress;
    }

    private static String getMacWay1() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                final StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (SocketException e) {

        }
        return "";
    }

    private static String getMacWay2() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception e) {
            // TODO 日志
//            EHiLog.e(e.getMessage());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                // TODO 日志
//                EHiLog.e(e.getMessage());
            }
        }
        return macSerial == null ? "" : macSerial;
    }


    private static String loadFileAsString(String filePath)
            throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
            return fileData.toString();
        }
    }


    /**
     * 查看连接网络类型
     *
     * @return int
     */
    public static int getNetworkType() {
        ConnectivityManager mConnMgr = (ConnectivityManager) ResourceUtil.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wifiManager = (WifiManager) ResourceUtil.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = null;
        int wifiState = WifiManager.WIFI_STATE_ENABLED;
        if (wifiManager != null) {
            wifiState = wifiManager.getWifiState();
            wifiInfo = wifiManager.getConnectionInfo();
        }

        if (wifiInfo != null && wifiInfo.getNetworkId() != -1 && (wifiState
                == WifiManager.WIFI_STATE_ENABLED)) {
            return WIFI_CONNECT;
        } else {
            NetworkInfo netInfo = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                return NO_NET_CONNECT;
            } else {
                String name = netInfo.getExtraInfo();
                if (name == null) {
                    return NO_NET_CONNECT;
                }
                if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE && "cmwap".equalsIgnoreCase(
                        name)) {
                    return WAP_CONNECTED;
                } else {
                    return NET_CONNECTED;
                }
            }
        }
    }

    // TODO 将此方法移至手机设备信息工具类
    /**
     * 获得设备ip地址
     */
    public static String getWifiIpAddress(Context mContext) {
        WifiManager wifimanger = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiinfo = wifimanger.getConnectionInfo();
        String ip = intToIp(wifiinfo.getIpAddress()); // 注：getIpAddress获取的为int型需要用intToIp方法
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    // TODO 将此方法移至手机设备信息工具类
    /**
     * 获得设备ip地址
     */
    public static String getLocalIpAddress() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        // 这里做了一步IPv4的判定
                        ip = inetAddress.getHostAddress();
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            // TODO 加日志
//            EHiLog.e(e.getMessage());
            ip = DEFAULT_IP;
            return ip;
        }
        return ip;
    }
}