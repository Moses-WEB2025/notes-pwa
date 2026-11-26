package com.xiaomi.passport.uicontroller;

import com.xiaomi.accountsdk.futureservice.ClientFuture;

/* JADX INFO: loaded from: classes4.dex */
public abstract class UIControllerFuture<ModelDataType, UIDataType> extends ClientFuture<ModelDataType, UIDataType> {

    public interface UICallback<UIDataType> extends ClientFuture.ClientCallback<UIDataType> {
    }

    protected abstract UIDataType convertModelDataToUIData(ModelDataType modeldatatype) throws Throwable;

    protected UIControllerFuture(UICallback<UIDataType> uICallback) {
        super(uICallback);
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    protected final UIDataType convertServerDataToClientData(ModelDataType modeldatatype) throws Throwable {
        return convertModelDataToUIData(modeldatatype);
    }
}
