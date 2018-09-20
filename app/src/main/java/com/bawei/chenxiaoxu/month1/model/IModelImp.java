package com.bawei.chenxiaoxu.month1.model;

import com.bawei.chenxiaoxu.month1.utils.Okhttputils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public class IModelImp implements IModel {

    @Override
    public void getRequest( int page,String urlString, final CallBack callBack) {
        FormBody body = new FormBody.Builder()
                .add("uid", "71")
                .add("page", page + "")
                .build();
        Okhttputils.getInstance().post(urlString, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                callBack.Success(string);
            }
        });
    }
}
