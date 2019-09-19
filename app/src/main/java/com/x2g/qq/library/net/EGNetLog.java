package com.x2g.qq.library.net;

import android.util.Log;

class EGNetLog {

    private static final String TAG = "EGNetLog";

    static void i(String msg) {
        if (EGNetManager.DEBUG) {
            Log.i(TAG, msg);
        }
    }

    static void e(String msg, Throwable e) {
        if (EGNetManager.DEBUG) {
            Log.e(TAG, msg, e);
        }
    }
}
