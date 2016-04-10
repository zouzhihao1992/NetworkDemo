package com.zzh.networkdemo.net;

/**
 * Created by zzh on 16/4/10.
 */
public interface DateLoadCompleteListenner<T> {
    void onComplete(T result);
}
