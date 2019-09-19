package com.x2g.qq.library.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class EGNetManager {

    private static EGNetManager mInstance;

    private static OkHttpClient mHttpClient;
    private long index;
    protected static boolean DEBUG = false;

    public static void init(boolean debug) {
        DEBUG = debug;
    }

    private EGNetManager() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static EGNetManager getInstance() {
        if (mInstance == null) {
            mInstance = new EGNetManager();
        }
        return mInstance;
    }

    public EGNetRequest newRequest() {
        index++;
        return new EGNetRequest(mHttpClient, index);
    }

    public static void setOutTome(int outTome) {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(outTome, TimeUnit.SECONDS)
                .build();
    }
}
