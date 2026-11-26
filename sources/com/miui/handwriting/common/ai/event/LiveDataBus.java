package com.miui.handwriting.common.ai.event;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.c.j;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LiveDataBus.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u000b\fB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007\"\u0004\b\u0000\u0010\t2\u0006\u0010\n\u001a\u00020\u0006R\u001e\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/handwriting/common/ai/event/LiveDataBus;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mStickyMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/miui/handwriting/common/ai/event/LiveDataBus$StickyLiveData;", "with", ExifInterface.GPS_DIRECTION_TRUE, j.b, "StickyLiveData", "StickyObserver", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LiveDataBus {
    public static final LiveDataBus INSTANCE = new LiveDataBus();
    private static final ConcurrentHashMap<String, StickyLiveData<?>> mStickyMap = new ConcurrentHashMap<>();

    private LiveDataBus() {
    }

    public final <T> StickyLiveData<T> with(String eventName) {
        Intrinsics.checkNotNullParameter(eventName, "eventName");
        ConcurrentHashMap<String, StickyLiveData<?>> concurrentHashMap = mStickyMap;
        StickyLiveData<T> stickyLiveData = (StickyLiveData) concurrentHashMap.get(eventName);
        if (stickyLiveData != null) {
            return stickyLiveData;
        }
        StickyLiveData<T> stickyLiveData2 = new StickyLiveData<>(eventName);
        concurrentHashMap.put(eventName, stickyLiveData2);
        return stickyLiveData2;
    }

    /* JADX INFO: compiled from: LiveDataBus.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u0011J\u0013\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011J\u0015\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011J&\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000 J \u0010!\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001c2\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000 H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\""}, d2 = {"Lcom/miui/handwriting/common/ai/event/LiveDataBus$StickyLiveData;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/LiveData;", j.b, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "mLiveDataVersion", "", "getMLiveDataVersion", "()I", "setMLiveDataVersion", "(I)V", "mStickyData", "getMStickyData", "()Ljava/lang/Object;", "setMStickyData", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "setStickyData", "", "stickyData", "postStickyData", "setValue", "value", "postValue", "observerSticky", "owner", "Landroidx/lifecycle/LifecycleOwner;", "sticky", "", "observer", "Landroidx/lifecycle/Observer;", "observe", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class StickyLiveData<T> extends LiveData<T> {
        private String eventName;
        private int mLiveDataVersion;
        private T mStickyData;

        public StickyLiveData(String eventName) {
            Intrinsics.checkNotNullParameter(eventName, "eventName");
            this.eventName = eventName;
        }

        public final int getMLiveDataVersion() {
            return this.mLiveDataVersion;
        }

        public final void setMLiveDataVersion(int i) {
            this.mLiveDataVersion = i;
        }

        public final T getMStickyData() {
            return this.mStickyData;
        }

        public final void setMStickyData(T t) {
            this.mStickyData = t;
        }

        public final void setStickyData(T stickyData) {
            this.mStickyData = stickyData;
            setValue(stickyData);
        }

        public final void postStickyData(T stickyData) {
            this.mStickyData = stickyData;
            postValue(stickyData);
        }

        @Override // androidx.lifecycle.LiveData
        protected void setValue(T value) {
            this.mLiveDataVersion++;
            super.setValue(value);
        }

        @Override // androidx.lifecycle.LiveData
        protected void postValue(T value) {
            super.postValue(value);
        }

        public final void observerSticky(LifecycleOwner owner, boolean sticky, Observer<? super T> observer) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(observer, "observer");
            owner.getLifecycle().addObserver(new LifecycleEventObserver() { // from class: com.miui.handwriting.common.ai.event.LiveDataBus$StickyLiveData$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.LifecycleEventObserver
                public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    LiveDataBus.StickyLiveData.observerSticky$lambda$0(this.f$0, lifecycleOwner, event);
                }
            });
            super.observe(owner, new StickyObserver(this, sticky, observer));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void observerSticky$lambda$0(StickyLiveData stickyLiveData, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Intrinsics.checkNotNullParameter(lifecycleOwner, "<unused var>");
            Intrinsics.checkNotNullParameter(event, "event");
            if (event == Lifecycle.Event.ON_DESTROY) {
                LiveDataBus.mStickyMap.remove(stickyLiveData.eventName);
            }
        }

        @Override // androidx.lifecycle.LiveData
        public void observe(LifecycleOwner owner, Observer<? super T> observer) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(observer, "observer");
            observerSticky(owner, false, observer);
        }
    }

    /* JADX INFO: compiled from: LiveDataBus.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0002¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/miui/handwriting/common/ai/event/LiveDataBus$StickyObserver;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/Observer;", "stickyLiveData", "Lcom/miui/handwriting/common/ai/event/LiveDataBus$StickyLiveData;", "sticky", "", "observer", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/handwriting/common/ai/event/LiveDataBus$StickyLiveData;ZLandroidx/lifecycle/Observer;)V", "mLastVersion", "", "onChanged", "", Constants.TIMESTAMP, "(Ljava/lang/Object;)V", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class StickyObserver<T> implements Observer<T> {
        private int mLastVersion;
        private final Observer<? super T> observer;
        private final boolean sticky;
        private final StickyLiveData<T> stickyLiveData;

        public StickyObserver(StickyLiveData<T> stickyLiveData, boolean z, Observer<? super T> observer) {
            Intrinsics.checkNotNullParameter(stickyLiveData, "stickyLiveData");
            Intrinsics.checkNotNullParameter(observer, "observer");
            this.stickyLiveData = stickyLiveData;
            this.sticky = z;
            this.observer = observer;
            this.mLastVersion = stickyLiveData.getMLiveDataVersion();
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(T t) {
            T mStickyData;
            if (this.mLastVersion >= this.stickyLiveData.getMLiveDataVersion()) {
                if (!this.sticky || (mStickyData = this.stickyLiveData.getMStickyData()) == null) {
                    return;
                }
                this.observer.onChanged(mStickyData);
                return;
            }
            this.observer.onChanged(t);
        }
    }
}
