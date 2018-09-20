package com.bawei.chenxiaoxu.month1.model;

import java.util.HashMap;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public interface IModel {
    interface CallBack{
        void Success(String string);
    }
    void getRequest(int page, String urlString,CallBack callBack);

}
