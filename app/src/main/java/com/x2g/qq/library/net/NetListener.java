package com.x2g.qq.library.net;

public interface NetListener {

    void onRequestStart();

    void onRequestEnd();

    void onResponse(String json);

    void onError(String msg, Throwable e);
}
