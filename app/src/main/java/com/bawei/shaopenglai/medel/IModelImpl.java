package com.bawei.shaopenglai.medel;

import com.bawei.shaopenglai.callback.MyCallBack;
import com.bawei.shaopenglai.okhttp.ICallBack;
import com.bawei.shaopenglai.okhttp.OkHttpUtil;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void requestDataGet(String url, String params, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtil.getInstance().getAsynchronization(url, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });
    }


    @Override
    public void requestDataPost(String url, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtil.getInstance().postAsynchronization(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });
    }
}
