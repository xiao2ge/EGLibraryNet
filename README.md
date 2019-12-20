# EGLibraryNet
网络库
[![](https://jitpack.io/v/xiao2ge/EGLibraryNet.svg)](https://jitpack.io/#xiao2ge/EGLibraryNet)

#### 初始化
```
EGNetManager.init(debug);
```

#### 发送get请求
```
EGNetManager.getInstance()
            .newRequest()
            .get()
            .url(url)
            .addHeader(headKey, headValue)
            .addParam(key, value)
            .enqueue(netListener);
```

#### 发送post请求
```
EGNetManager.getInstance()
            .newRequest()
            .post()
            .url(url)
            .addHeader(headKey, headValue)
            .addParam(key, value)
            .enqueue(netListener);
```

##### EGNetListener
```
public interface EGNetListener {

    void onRequestStart();

    void onRequestEnd();

    void onResponse(String json);

    void onError(String msg, Throwable e);
}
```

##### 设置超时时间
```
EGNetManager.setOutTome(seconds); // 单位：秒
```
