package com.miui.notes.assist;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class UnPeekLiveData<T> extends MutableLiveData<T> {
    private static final int START_VERSION = -1;
    private final AtomicInteger mCurrentVersion = new AtomicInteger(-1);

    @Override // androidx.lifecycle.LiveData
    public void observe(LifecycleOwner lifecycleOwner, Observer<? super T> observer) {
        super.observe(lifecycleOwner, createObserverWrapper(observer, this.mCurrentVersion.get()));
    }

    @Override // androidx.lifecycle.LiveData
    public void observeForever(Observer<? super T> observer) {
        super.observeForever(createObserverWrapper(observer, this.mCurrentVersion.get()));
    }

    @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
    public void setValue(T t) {
        this.mCurrentVersion.getAndIncrement();
        super.setValue(t);
    }

    class ObserverWrapper implements Observer<T> {
        private final Observer<? super T> mObserver;
        private int mVersion;

        public ObserverWrapper(Observer<? super T> observer, int i) {
            this.mObserver = observer;
            this.mVersion = i;
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(T t) {
            if (UnPeekLiveData.this.mCurrentVersion.get() > this.mVersion) {
                this.mObserver.onChanged(t);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.mObserver, ((ObserverWrapper) obj).mObserver);
        }

        public int hashCode() {
            return Objects.hash(this.mObserver);
        }
    }

    @Override // androidx.lifecycle.LiveData
    public void removeObserver(Observer<? super T> observer) {
        if (observer.getClass().isAssignableFrom(ObserverWrapper.class)) {
            super.removeObserver(observer);
        } else {
            super.removeObserver(createObserverWrapper(observer, -1));
        }
    }

    private UnPeekLiveData<T>.ObserverWrapper createObserverWrapper(Observer<? super T> observer, int i) {
        return new ObserverWrapper(observer, i);
    }
}
