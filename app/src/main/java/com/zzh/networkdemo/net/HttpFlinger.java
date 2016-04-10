package com.zzh.networkdemo.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.zzh.networkdemo.net.parser.RespParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zzh on 16/4/10.
 */
public final class HttpFlinger {

    private HttpFlinger(){

    }

    private static HttpURLConnection urlConnection = null;


    /**
     * 在应用中get可能比较频繁，so，在get方法中没有关闭http的连接。另外提供了关闭的接口。
     * @param reqURL
     * @param parser
     * @param dateLoadCompleteListenner
     * @param <T>
     */
    public static <T> void get(final String reqURL, final RespParser<T> parser,
                               final DateLoadCompleteListenner<T> dateLoadCompleteListenner){
        new AsyncTask<Void,Void,T>(){
            @Override
            protected T doInBackground(Void... params) {

                try {
                    urlConnection = (HttpURLConnection) new URL(reqURL).openConnection();
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    String result = stream2String(urlConnection.getInputStream());
                    return parser.parseResponse(result);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                super.onPostExecute(t);
                if (dateLoadCompleteListenner != null){
                    dateLoadCompleteListenner.onComplete(t);
                }
            }
        }.execute();
    }

    private static String stream2String(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    public static void closeNetWork(){
        if(urlConnection != null){
            urlConnection.disconnect();
        }
    }

}
