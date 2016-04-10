package com.zzh.networkdemo.net.parser;

import org.json.JSONException;

/**
 * Created by zzh on 16/4/10.
 */
public interface RespParser<T> {
    public T parseResponse(String result) throws JSONException;
}
