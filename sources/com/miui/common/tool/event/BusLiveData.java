package com.miui.common.tool.event;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/* JADX INFO: loaded from: classes2.dex */
public class BusLiveData<T> extends MutableLiveData<T> {
    public boolean mIsStartChangeData = false;

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void setValue(T t) {
        this.mIsStartChangeData = true;
        super.setValue(t);
    }

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void postValue(T t) {
        this.mIsStartChangeData = true;
        super.postValue(t);
    }

    @Override // androidx.lifecycle.LiveData
    public void observe(LifecycleOwner lifecycleOwner, Observer<? super T> observer) {
        super.observe(lifecycleOwner, new ObserverWrap(observer, this));
    }
}
