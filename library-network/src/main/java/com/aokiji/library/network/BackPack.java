package com.aokiji.library.network;

public class BackPack {

    // 0：Gank,1：WanAndroid
    public static final int MODULE_GANK = 0;
    public static final int MODULE_WANANDROID = 1;

    public static class Gank {
        // Base Url for Gank
        public static String BASE_URL = "http://gank.io/api/v2/data/";

        public static final int RESULT_SUCCESS = 100;
    }

    public static class WanAndroid {
        // Base Url for WanAndroid
        public static String BASE_URL = "https://www.wanandroid.com";

        public static final int RESULT_SUCCESS = 0;
    }
}
