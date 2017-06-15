/**
 * Project Name:Gokit
 * File Name:NetUtils.java
 * Package Name:com.xpg.gokit.utils
 * Date:2014-11-18 10:06:37
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.mai.xmai_fast_lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.List;

/**
 * 网络工具类.
 *
 * @author Sunny Ding
 *
 * *
 */
public class NetUtils {

    /**
     * 判断当前手机是否连上Wifi.
     *
     * @param context 上下文
     * @return boolean 是否连上网络
     *
     * *
     */
    static public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                if (mWiFiNetworkInfo.isAvailable()) {
                    return mWiFiNetworkInfo.isConnected();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前手机的网络是否可用.
     *
     * @param context 上下文
     * @return boolean 是否连上网络
     *
     * *
     */
    static public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                if (mMobileNetworkInfo.isAvailable()) {
                    return mMobileNetworkInfo.isConnected();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前网络是手机网络还是WIFI.
     *
     * @param context 上下文
     * @return ConnectedType 数据类型
     *
     * *
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取代表联网状态的NetWorkInfo对象
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            // 判断NetWorkInfo对象是否为空；判断当前的网络连接是否可用
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取当前WIFI的SSID.
     *
     * @param context 上下文
     * @return ssid
     *
     * *
     */
    public static String getCurentWifiSSID(Context context) {
        String ssid = "";
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ssid = wifiInfo.getSSID();
            if (TextUtils.isEmpty(ssid)) {
                return "";
            }
            if (ssid.substring(0, 1).equals("\"")
                    && ssid.substring(ssid.length() - 1).equals("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
        }
        return ssid;
    }

    /**
     * 用来获得手机扫描到的所有wifi的信息.
     *
     * @param c 上下文
     * @return the current wifi scan result
     */
    static public List<ScanResult> getCurrentWifiScanResult(Context c) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        return wifiManager.getScanResults();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取活动的网络连接信息
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        // 判断
        if (info == null) {
            // 当前没有已激活的网络连接（表示用户关闭了数据流量服务，也没有开启WiFi等别的数据服务）
            return false;
        } else {
            // 当前有已激活的网络连接，但是否可用还需判断
            return info.isAvailable();
        }
    }

}
