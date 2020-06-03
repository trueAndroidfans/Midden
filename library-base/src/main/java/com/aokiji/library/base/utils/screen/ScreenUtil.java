package com.aokiji.library.base.utils.screen;

import android.content.Context;

public class ScreenUtil {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
