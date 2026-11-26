package com.miui.handwriting.common.ai.data.state;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LiveData;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RequestStateLiveData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lcom/miui/handwriting/common/ai/data/state/RequestStateLiveData;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/LiveData;", "Lcom/miui/handwriting/common/ai/data/state/RequestState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_requestState", "Lcom/miui/handwriting/common/ai/data/state/SingleLiveData;", "requestState", "getRequestState", "()Landroidx/lifecycle/LiveData;", "postLoading", "", "postSuccess", "postError", "message", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RequestStateLiveData<T> extends LiveData<RequestState> {
    private final SingleLiveData<RequestState> _requestState = new SingleLiveData<>();

    public final LiveData<RequestState> getRequestState() {
        return this._requestState;
    }

    public final void postLoading() {
        this._requestState.postValue(RequestState.Loading.INSTANCE);
    }

    public final void postSuccess() {
        this._requestState.postValue(RequestState.Success.INSTANCE);
    }

    public final void postError(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this._requestState.postValue(new RequestState.Error(message));
    }
}
