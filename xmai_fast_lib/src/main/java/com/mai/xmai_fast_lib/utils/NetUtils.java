package com.mai.xmai_fast_lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mai on 16/5/4.
 */
public class NetUtils {

    private static final int CMNET = 3;
    private static final int CMWAP = 2;
    private static final int WIFI = 1;

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

    /**
     * 获取当前的网络状态  -1：没有网络  1：WIFI网络 2：wap网络 3：net网络
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = CMNET;
            } else {
                netType = CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = WIFI;
        }
        return netType;
    }
}
