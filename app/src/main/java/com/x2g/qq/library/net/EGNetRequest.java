package com.x2g.qq.library.net;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EGNetRequest {

    private static final int REQUEST_METHOD_GET = 0;
    private static final int REQUEST_METHOD_POST = 1;

    private OkHttpClient mClient;
    private long id;

    public EGNetRequest(OkHttpClient mClient, long id) {
        this.mClient = mClient;
        this.id = id;
    }

    private int method = REQUEST_METHOD_GET;
    private String url;
    private HashMap<String, String> paramMap = new HashMap<>();
    private HashMap<String, String> headerMap = new HashMap<>();
    private HashMap<String, String> fileMap = new HashMap<>();

    public EGNetRequest get() {
        this.method = REQUEST_METHOD_GET;
        return this;
    }

    public EGNetRequest post() {
        this.method = REQUEST_METHOD_POST;
        return this;
    }

    public EGNetRequest url(String url) {
        this.url = url;
        return this;
    }

    public EGNetRequest addParam(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            paramMap.put(key, value);
        }
        return this;
    }

    public EGNetRequest addHeader(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            headerMap.put(key, value);
        }
        return this;
    }

    public EGNetRequest addFile(String key, String filePath) {
        if (!TextUtils.isEmpty(key)) {
            fileMap.put(key, filePath);
        }
        return this;
    }

    public void enqueue(final EGNetListener listener) {
        Observable
                .create((ObservableOnSubscribe<String>) emitter -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" \n======================\t\t").append(id).append("\tRequest\tStart\t\t").append("======================\n");
                    Request.Builder builder = new Request.Builder();
                    if (method == REQUEST_METHOD_POST) {
                        builder.url(url);
                        FormBody.Builder bodyBuilder = new FormBody.Builder();
                        for (String key : paramMap.keySet()) {
                            bodyBuilder.add(key, paramMap.get(key));
                        }
                        builder.post(bodyBuilder.build());
                        sb.append("||\t").append("POST\t").append(url).append("\n");
                        sb.append("||\t").append("Params\t").append(paramMap.toString()).append("\n");
                    } else {
                        if (paramMap.size() > 0) {
                            url += "?";
                            for (String key : paramMap.keySet()) {
                                url += (key + "=" + paramMap.get(key) + "&");
                            }
                            url = url.substring(0, url.length() - 1);
                        }
                        builder.url(url);
                        builder.get();
                        sb.append("||\t").append("GET\t").append(url).append("\n");
                    }
                    for (String key : headerMap.keySet()) {
                        builder.addHeader(key, headerMap.get(key));
                    }
                    sb.append("||\t").append("Headers\t").append(headerMap.toString()).append("\n");
                    sb.append("======================\t\t").append(id).append("\tRequest\tEnd\t\t").append("======================\n");
                    EGNetLog.i(sb.toString());
                    final Request request = builder.build();
                    Call call = mClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            emitter.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            if (response.isSuccessful()) {
                                emitter.onNext(result);
                                emitter.onComplete();
                            } else {
                                emitter.onError(new RuntimeException(result));
                            }
                        }
                    });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        EGNetLog.i(" \n>>>>>>\tonSubscribe");
                        if (listener != null) {
                            listener.onRequestStart();
                        }
                    }

                    @Override
                    public void onNext(String json) {
                        EGNetLog.i(" \n>>>>>>\tResponse\t" + json);
                        if (listener != null) {
                            listener.onResponse(json);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        EGNetLog.e(" \n>>>>>>\tError\t" + e.getMessage(), e);
                        if (listener != null) {
                            listener.onRequestEnd();
                            listener.onError(e.getMessage(), e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        EGNetLog.i(" \n>>>>>>\tComplete\t");
                        if (listener != null) {
                            listener.onRequestEnd();
                        }
                    }
                });
    }

}
