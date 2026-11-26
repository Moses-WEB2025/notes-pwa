package com.miui.notes.doodle.feature;

import androidx.lifecycle.ViewModel;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: DoodlePageViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\t¨\u0006\n"}, d2 = {"Lcom/miui/notes/doodle/feature/DoodlePageViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_isShowConfigPopupWindow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isShowConfigPopupWindowState", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodlePageViewModel extends ViewModel {
    private final MutableStateFlow<Boolean> _isShowConfigPopupWindow;
    private final StateFlow<Boolean> isShowConfigPopupWindowState;

    public DoodlePageViewModel() {
        MutableStateFlow<Boolean> MutableStateFlow = StateFlowKt.MutableStateFlow(false);
        this._isShowConfigPopupWindow = MutableStateFlow;
        this.isShowConfigPopupWindowState = FlowKt.asStateFlow(MutableStateFlow);
    }

    public final StateFlow<Boolean> isShowConfigPopupWindowState() {
        return this.isShowConfigPopupWindowState;
    }
}
