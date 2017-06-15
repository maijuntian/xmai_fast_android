package com.mai.xmai_fast_lib.utils;

import android.content.Context;
import android.util.TypedValue;

public class DisplayUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  dpValue * scale + 0.5f;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  pxValue / scale + 0.5f;
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

}