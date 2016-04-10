package com.zzh.networkdemo.net.parser;

import org.json.JSONException;

import java.sql.ResultSet;

/**
 * Created by zzh on 16/4/10.
 */
public class DeafaultParser implements RespParser<String> {
    @Override
    public String parseResponse(String result) throws JSONException {
        return result;
    }
}
