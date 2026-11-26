package com.miui.pad.feature.notes.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ShareViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006J\r\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000eR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\u000f"}, d2 = {"Lcom/miui/pad/feature/notes/share/ShareViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_isOneItemChecked", "Landroidx/lifecycle/MutableLiveData;", "", "isOneItemCheckedLiveData", "Landroidx/lifecycle/LiveData;", "()Landroidx/lifecycle/LiveData;", "setOneItemChecked", "", "mode", "getIsOneItemChecked", "()Ljava/lang/Boolean;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ShareViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _isOneItemChecked = new MutableLiveData<>();

    public final LiveData<Boolean> isOneItemCheckedLiveData() {
        return this._isOneItemChecked;
    }

    public final void setOneItemChecked(boolean mode) {
        if (Intrinsics.areEqual(this._isOneItemChecked.getValue(), Boolean.valueOf(mode))) {
            return;
        }
        this._isOneItemChecked.setValue(Boolean.valueOf(mode));
    }

    public final Boolean getIsOneItemChecked() {
        return this._isOneItemChecked.getValue();
    }
}
