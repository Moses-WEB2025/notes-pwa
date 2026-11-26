package com.miui.common.tool.event;

import androidx.lifecycle.Observer;

/* JADX INFO: loaded from: classes2.dex */
public class ObserverWrap<T> implements Observer<T> {
    private BusLiveData<T> liveData;
    private Observer<T> observer;

    protected ObserverWrap(Observer<T> observer, BusLiveData<T> busLiveData) {
        this.observer = observer;
        this.liveData = busLiveData;
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(T t) {
        Observer<T> observer;
        if (!this.liveData.mIsStartChangeData || (observer = this.observer) == null) {
            return;
        }
        observer.onChanged(t);
    }
}
