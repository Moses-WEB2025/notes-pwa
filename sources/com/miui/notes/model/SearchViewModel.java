package com.miui.notes.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: SearchViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/notes/model/SearchViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "data", "Landroidx/lifecycle/MutableLiveData;", "", "getData", "Landroidx/lifecycle/LiveData;", "setData", "", "newData", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SearchViewModel extends ViewModel {
    private final MutableLiveData<Boolean> data = new MutableLiveData<>();

    public final LiveData<Boolean> getData() {
        return this.data;
    }

    public final void setData(boolean newData) {
        this.data.setValue(Boolean.valueOf(newData));
    }
}
