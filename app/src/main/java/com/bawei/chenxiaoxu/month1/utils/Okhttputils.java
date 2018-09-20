package com.bawei.chenxiaoxu.month1.utils;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public class Okhttputils {
    private static Okhttputils okhttputils;
    private static OkHttpClient okHttpClient;
    public Okhttputils() {
        if(okhttputils==null){
            okHttpClient=new OkHttpClient();
        }
    }
    //单例
    public static Okhttputils getInstance(){
        if(okhttputils==null){
            synchronized (Okhttputils.class){
                if(okhttputils==null){
                    okhttputils=new Okhttputils();
                }
            }
        }
        return okhttputils;
    }

    //get请求
    public void get(String url_get, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url(url_get).build();
        okHttpClient.newCall(build).enqueue(callback);
    }
    //post请求
    public void post(String url_post, FormBody formBody,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request post = new Request.Builder().url(url_post).method("POST", formBody).build();
        okHttpClient.newCall(post).enqueue(callback);
    }
}
