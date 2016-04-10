package com.zzh.networkdemo.net;

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

    public static <T> void get(final String reqURL, final RespParser<T> parser,
                               final DateLoadCompleteListenner<T> dateLoadCompleteListenner){
        new AsyncTask<Void,Void,T>(){
            @Override
            protected T doInBackground(Void... params) {
                HttpURLConnection urlConnection = null;
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
                }finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
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
}
