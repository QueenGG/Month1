package com.bawei.chenxiaoxu.month1.pesenter;

import com.bawei.chenxiaoxu.month1.model.IModel;
import com.bawei.chenxiaoxu.month1.model.IModelImp;
import com.bawei.chenxiaoxu.month1.view.IView;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/19.
 */

public class IPesenterImp implements IPesenter {
    IView iView;
    private final IModelImp iModel;

    public IPesenterImp(IView iView) {
        this.iView=iView;
        iModel = new IModelImp();
    }

    @Override
    public void getData(int page, String urlString) {
        iModel.getRequest(page,urlString, new IModel.CallBack() {
            @Override
            public void Success(String string) {
                iView.Show(string);
            }
        });
    }
}
