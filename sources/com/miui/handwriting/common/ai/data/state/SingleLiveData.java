package com.miui.handwriting.common.ai.data.state;

import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class SingleLiveData<T> extends MutableLiveData<T> {
    private static final String TAG = "SingleLiveData";
    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @Override // androidx.lifecycle.LiveData
    public void observe(LifecycleOwner lifecycleOwner, final Observer<? super T> observer) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }
        super.observe(lifecycleOwner, new Observer<T>() { // from class: com.miui.handwriting.common.ai.data.state.SingleLiveData.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(T t) {
                if (SingleLiveData.this.mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void setValue(T t) {
        this.mPending.set(true);
        super.setValue(t);
    }

    public void call() {
        setValue(null);
    }
}
