package com.x2g.qq.library.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class NetManager {

    private static NetManager mInstance;

    private static OkHttpClient mHttpClient;
    private long index;
    protected static boolean DEBUG = false;

    public static void init(boolean debug) {
        DEBUG = debug;
    }

    private NetManager() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static NetManager getInstance() {
        if (mInstance == null) {
            mInstance = new NetManager();
        }
        return mInstance;
    }

    public NetRequest newRequest() {
        index++;
        return new NetRequest(mHttpClient, index);
    }

    public static void setOutTome(int outTome) {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(outTome, TimeUnit.SECONDS)
                .build();
    }
}
