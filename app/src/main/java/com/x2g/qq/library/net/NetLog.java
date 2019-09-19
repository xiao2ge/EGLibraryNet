package com.x2g.qq.library.net;

import android.util.Log;

class NetLog {

    private static final String TAG = "NetLog";

    static void i(String msg) {
        if (NetManager.DEBUG) {
            Log.i(TAG, msg);
        }
    }

    static void e(String msg, Throwable e) {
        if (NetManager.DEBUG) {
            Log.e(TAG, msg, e);
        }
    }
}
