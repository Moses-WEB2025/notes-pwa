package com.miui.handwriting.common.ai.data.state;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LiveData;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BallStateLiveData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\fJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/BallStateLiveData;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/LiveData;", "Lcom/miui/handwriting/common/ai/data/state/BallState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_ballState", "Lcom/miui/handwriting/common/ai/data/state/SingleLiveData;", "ballState", "getBallState", "()Landroidx/lifecycle/LiveData;", "postLoading", "", "postSuccess", "postFilter", "postError", "message", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BallStateLiveData<T> extends LiveData<BallState> {
    private final SingleLiveData<BallState> _ballState = new SingleLiveData<>();

    public final LiveData<BallState> getBallState() {
        return this._ballState;
    }

    public final void postLoading() {
        this._ballState.postValue(BallState.Loading.INSTANCE);
    }

    public final void postSuccess() {
        this._ballState.postValue(BallState.Success.INSTANCE);
    }

    public final void postFilter() {
        this._ballState.postValue(BallState.Filter.INSTANCE);
    }

    public final void postError(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this._ballState.postValue(new BallState.Error(message));
    }
}
