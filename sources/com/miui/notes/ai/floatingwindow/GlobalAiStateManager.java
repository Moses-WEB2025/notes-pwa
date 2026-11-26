package com.miui.notes.ai.floatingwindow;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GlobalAiStateManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u0000 *2\u00020\u0001:\u0005*+,-.B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\fJ\u000e\u0010#\u001a\u00020!2\u0006\u0010\"\u001a\u00020\fJ\u000e\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020\u0012J\u0010\u0010&\u001a\u00020!2\u0006\u0010%\u001a\u00020\u0012H\u0002J\u000e\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020\u0011J\u0006\u0010)\u001a\u00020!R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\"\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0013\u001a\u0004\u0018\u00010\u00122\b\u0010\u0007\u001a\u0004\u0018\u00010\u0012@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00020\u0016@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00020\u0016@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u001e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00020\u0016@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u001e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00020\u0016@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019¨\u0006/"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager;", "", "handler", "Landroid/os/Handler;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/os/Handler;)V", "mHandler", "value", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$RequestState;", "mCurrentRequestState", "getMCurrentRequestState", "()Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$RequestState;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", "mCurrentPanelState", "getMCurrentPanelState", "()Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", "mAiPanelStateCallback", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$AiPanelStateCallback;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", "mCurrentFlowState", "getMCurrentFlowState", "()Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", "", "mFirstInvokeFlow", "getMFirstInvokeFlow", "()Z", "mHasStopGenerating", "getMHasStopGenerating", "mFinishFlow", "getMFinishFlow", "mOnFlowDone", "getMOnFlowDone", "setPanelState", "", "panelState", "setPanelState2", "setFlowState", "flowState", "setFlowState2", "setAiPanelStateCallback", CallMethod.ARG_CALLBACK, "release", "Companion", "RequestState", "PanelState", "FlowState", "AiPanelStateCallback", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiStateManager {
    public static final String TAG = "GlobalAiStateManager";
    private AiPanelStateCallback mAiPanelStateCallback;
    private FlowState mCurrentFlowState;
    private PanelState mCurrentPanelState;
    private RequestState mCurrentRequestState;
    private boolean mFinishFlow;
    private boolean mFirstInvokeFlow;
    private final Handler mHandler;
    private boolean mHasStopGenerating;
    private boolean mOnFlowDone;

    /* JADX INFO: compiled from: GlobalAiStateManager.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\u0003H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0005H&J\b\u0010\u000e\u001a\u00020\u0003H&¨\u0006\u000fÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$AiPanelStateCallback;", "", "showLoadingPanel", "", "isAiEditorPrepared", "", "isFirstStart", "showGeneratingPanel", "showInputPanel", "imeHeight", "", "showCompletePanel", "resCode", "isShowEmpty", "showCompletePanelFromInput", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface AiPanelStateCallback {
        void showCompletePanel(int resCode, boolean isShowEmpty);

        void showCompletePanelFromInput();

        void showGeneratingPanel();

        void showInputPanel(int imeHeight);

        void showLoadingPanel(boolean isAiEditorPrepared, boolean isFirstStart);
    }

    /* JADX INFO: compiled from: GlobalAiStateManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$RequestState;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "STATE_GENERATING", "STATE_COMPLETE", "STATE_ERROR", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum RequestState {
        STATE_GENERATING,
        STATE_COMPLETE,
        STATE_ERROR;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<RequestState> getEntries() {
            return $ENTRIES;
        }
    }

    public GlobalAiStateManager(Handler handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.mHandler = handler;
    }

    public final RequestState getMCurrentRequestState() {
        return this.mCurrentRequestState;
    }

    public final PanelState getMCurrentPanelState() {
        return this.mCurrentPanelState;
    }

    public final FlowState getMCurrentFlowState() {
        return this.mCurrentFlowState;
    }

    public final boolean getMFirstInvokeFlow() {
        return this.mFirstInvokeFlow;
    }

    public final boolean getMHasStopGenerating() {
        return this.mHasStopGenerating;
    }

    public final boolean getMFinishFlow() {
        return this.mFinishFlow;
    }

    public final boolean getMOnFlowDone() {
        return this.mOnFlowDone;
    }

    public final void setPanelState(PanelState panelState) {
        Intrinsics.checkNotNullParameter(panelState, "panelState");
        Log.i(TAG, "setPanelState:" + panelState);
        if (panelState instanceof PanelState.Loading) {
            PanelState panelState2 = this.mCurrentPanelState;
            if ((panelState2 instanceof PanelState.Loading) && Intrinsics.areEqual(panelState2, panelState)) {
                return;
            }
            AiPanelStateCallback aiPanelStateCallback = this.mAiPanelStateCallback;
            if (aiPanelStateCallback != null) {
                PanelState.Loading loading = (PanelState.Loading) panelState;
                aiPanelStateCallback.showLoadingPanel(loading.isAiEditorPrepared(), loading.isFirstStart());
            }
        } else if (Intrinsics.areEqual(panelState, PanelState.Generating.INSTANCE)) {
            if (Intrinsics.areEqual(this.mCurrentPanelState, PanelState.Generating.INSTANCE)) {
                return;
            }
            AiPanelStateCallback aiPanelStateCallback2 = this.mAiPanelStateCallback;
            if (aiPanelStateCallback2 != null) {
                aiPanelStateCallback2.showGeneratingPanel();
            }
        } else {
            setPanelState2(panelState);
        }
        this.mCurrentPanelState = panelState;
    }

    public final void setPanelState2(PanelState panelState) {
        Intrinsics.checkNotNullParameter(panelState, "panelState");
        Log.i(TAG, "setPanelState2:" + panelState);
        if (panelState instanceof PanelState.InInput) {
            AiPanelStateCallback aiPanelStateCallback = this.mAiPanelStateCallback;
            if (aiPanelStateCallback != null) {
                aiPanelStateCallback.showInputPanel(((PanelState.InInput) panelState).getImeHeight());
                return;
            }
            return;
        }
        if (panelState instanceof PanelState.Complete) {
            PanelState panelState2 = this.mCurrentPanelState;
            if (panelState2 instanceof PanelState.Complete) {
                Intrinsics.checkNotNull(panelState2, "null cannot be cast to non-null type com.miui.notes.ai.floatingwindow.GlobalAiStateManager.PanelState.Complete");
                PanelState.Complete complete = (PanelState.Complete) panelState;
                if (((PanelState.Complete) panelState2).getResCode() == complete.getResCode()) {
                    PanelState panelState3 = this.mCurrentPanelState;
                    Intrinsics.checkNotNull(panelState3, "null cannot be cast to non-null type com.miui.notes.ai.floatingwindow.GlobalAiStateManager.PanelState.Complete");
                    if (((PanelState.Complete) panelState3).isShowEmpty() == complete.isShowEmpty()) {
                        return;
                    }
                }
            }
            if (this.mCurrentPanelState instanceof PanelState.InInput) {
                AiPanelStateCallback aiPanelStateCallback2 = this.mAiPanelStateCallback;
                if (aiPanelStateCallback2 != null) {
                    aiPanelStateCallback2.showCompletePanelFromInput();
                    return;
                }
                return;
            }
            AiPanelStateCallback aiPanelStateCallback3 = this.mAiPanelStateCallback;
            if (aiPanelStateCallback3 != null) {
                PanelState.Complete complete2 = (PanelState.Complete) panelState;
                aiPanelStateCallback3.showCompletePanel(complete2.getResCode(), complete2.isShowEmpty());
            }
        }
    }

    public final void setFlowState(FlowState flowState) {
        Intrinsics.checkNotNullParameter(flowState, "flowState");
        if (Intrinsics.areEqual(flowState, FlowState.FirstFlow.INSTANCE)) {
            if (Intrinsics.areEqual(this.mCurrentFlowState, FlowState.FirstFlow.INSTANCE)) {
                return;
            }
            this.mFirstInvokeFlow = true;
            this.mHasStopGenerating = false;
            this.mFinishFlow = false;
            this.mOnFlowDone = false;
        } else if (Intrinsics.areEqual(flowState, FlowState.Generating.INSTANCE)) {
            if (Intrinsics.areEqual(this.mCurrentFlowState, FlowState.Generating.INSTANCE)) {
                return;
            }
            this.mFirstInvokeFlow = false;
            this.mFinishFlow = false;
            this.mOnFlowDone = false;
        } else if (flowState instanceof FlowState.Interrupt) {
            FlowState flowState2 = this.mCurrentFlowState;
            if ((flowState2 instanceof FlowState.Interrupt) && Intrinsics.areEqual(flowState2, flowState)) {
                return;
            }
            this.mFirstInvokeFlow = false;
            boolean zIsEmpty = TextUtils.isEmpty(((FlowState.Interrupt) flowState).getEndTip());
            this.mHasStopGenerating = !zIsEmpty;
            if (!zIsEmpty) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiStateManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mHasStopGenerating = false;
                    }
                }, 1500L);
            }
        } else {
            setFlowState2(flowState);
        }
        this.mCurrentFlowState = flowState;
    }

    private final void setFlowState2(FlowState flowState) {
        if (Intrinsics.areEqual(flowState, FlowState.DataFinish.INSTANCE)) {
            if (Intrinsics.areEqual(this.mCurrentFlowState, FlowState.DataFinish.INSTANCE)) {
                return;
            }
            this.mFirstInvokeFlow = false;
            this.mFinishFlow = true;
            return;
        }
        if (Intrinsics.areEqual(flowState, FlowState.FlowDone.INSTANCE)) {
            if (Intrinsics.areEqual(this.mCurrentFlowState, FlowState.FlowDone.INSTANCE)) {
                return;
            }
            this.mFirstInvokeFlow = false;
            this.mHasStopGenerating = false;
            this.mOnFlowDone = true;
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.floatingwindow.GlobalAiStateManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mOnFlowDone = false;
                }
            }, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
            return;
        }
        if (Intrinsics.areEqual(flowState, FlowState.StartRefresh.INSTANCE)) {
            if (Intrinsics.areEqual(this.mCurrentFlowState, FlowState.StartRefresh.INSTANCE)) {
                return;
            }
            this.mFirstInvokeFlow = false;
            this.mHasStopGenerating = false;
            this.mOnFlowDone = false;
            return;
        }
        if (!Intrinsics.areEqual(flowState, FlowState.RefreshComplete.INSTANCE) || Intrinsics.areEqual(this.mCurrentFlowState, FlowState.RefreshComplete.INSTANCE)) {
            return;
        }
        this.mFirstInvokeFlow = false;
        this.mHasStopGenerating = false;
        this.mOnFlowDone = false;
    }

    public final void setAiPanelStateCallback(AiPanelStateCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mAiPanelStateCallback = callback;
    }

    public final void release() {
        this.mAiPanelStateCallback = null;
    }

    /* JADX INFO: compiled from: GlobalAiStateManager.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0004\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0004\b\t\n\u000b¨\u0006\f"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Loading", "Generating", "InInput", "Complete", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$Complete;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$Generating;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$InInput;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$Loading;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static abstract class PanelState {
        public /* synthetic */ PanelState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private PanelState() {
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$Loading;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", "isAiEditorPrepared", "", "isFirstStart", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZZ)V", "()Z", "component1", "component2", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final /* data */ class Loading extends PanelState {
            private final boolean isAiEditorPrepared;
            private final boolean isFirstStart;

            public static /* synthetic */ Loading copy$default(Loading loading, boolean z, boolean z2, int i, Object obj) {
                if ((i & 1) != 0) {
                    z = loading.isAiEditorPrepared;
                }
                if ((i & 2) != 0) {
                    z2 = loading.isFirstStart;
                }
                return loading.copy(z, z2);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final boolean getIsAiEditorPrepared() {
                return this.isAiEditorPrepared;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final boolean getIsFirstStart() {
                return this.isFirstStart;
            }

            public final Loading copy(boolean isAiEditorPrepared, boolean isFirstStart) {
                return new Loading(isAiEditorPrepared, isFirstStart);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Loading)) {
                    return false;
                }
                Loading loading = (Loading) other;
                return this.isAiEditorPrepared == loading.isAiEditorPrepared && this.isFirstStart == loading.isFirstStart;
            }

            public int hashCode() {
                return (Boolean.hashCode(this.isAiEditorPrepared) * 31) + Boolean.hashCode(this.isFirstStart);
            }

            public String toString() {
                return "Loading(isAiEditorPrepared=" + this.isAiEditorPrepared + ", isFirstStart=" + this.isFirstStart + ")";
            }

            public final boolean isAiEditorPrepared() {
                return this.isAiEditorPrepared;
            }

            public final boolean isFirstStart() {
                return this.isFirstStart;
            }

            public Loading(boolean z, boolean z2) {
                super(null);
                this.isAiEditorPrepared = z;
                this.isFirstStart = z2;
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$Generating;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Generating extends PanelState {
            public static final Generating INSTANCE = new Generating();

            private Generating() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$InInput;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", "imeHeight", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "getImeHeight", "()I", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final /* data */ class InInput extends PanelState {
            private final int imeHeight;

            public static /* synthetic */ InInput copy$default(InInput inInput, int i, int i2, Object obj) {
                if ((i2 & 1) != 0) {
                    i = inInput.imeHeight;
                }
                return inInput.copy(i);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final int getImeHeight() {
                return this.imeHeight;
            }

            public final InInput copy(int imeHeight) {
                return new InInput(imeHeight);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof InInput) && this.imeHeight == ((InInput) other).imeHeight;
            }

            public int hashCode() {
                return Integer.hashCode(this.imeHeight);
            }

            public String toString() {
                return "InInput(imeHeight=" + this.imeHeight + ")";
            }

            public final int getImeHeight() {
                return this.imeHeight;
            }

            public InInput(int i) {
                super(null);
                this.imeHeight = i;
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState$Complete;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$PanelState;", "resCode", "", "isShowEmpty", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IZ)V", "getResCode", "()I", "()Z", "component1", "component2", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final /* data */ class Complete extends PanelState {
            private final boolean isShowEmpty;
            private final int resCode;

            public static /* synthetic */ Complete copy$default(Complete complete, int i, boolean z, int i2, Object obj) {
                if ((i2 & 1) != 0) {
                    i = complete.resCode;
                }
                if ((i2 & 2) != 0) {
                    z = complete.isShowEmpty;
                }
                return complete.copy(i, z);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final int getResCode() {
                return this.resCode;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final boolean getIsShowEmpty() {
                return this.isShowEmpty;
            }

            public final Complete copy(int resCode, boolean isShowEmpty) {
                return new Complete(resCode, isShowEmpty);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Complete)) {
                    return false;
                }
                Complete complete = (Complete) other;
                return this.resCode == complete.resCode && this.isShowEmpty == complete.isShowEmpty;
            }

            public int hashCode() {
                return (Integer.hashCode(this.resCode) * 31) + Boolean.hashCode(this.isShowEmpty);
            }

            public String toString() {
                return "Complete(resCode=" + this.resCode + ", isShowEmpty=" + this.isShowEmpty + ")";
            }

            public final int getResCode() {
                return this.resCode;
            }

            public final boolean isShowEmpty() {
                return this.isShowEmpty;
            }

            public Complete(int i, boolean z) {
                super(null);
                this.resCode = i;
                this.isShowEmpty = z;
            }
        }
    }

    /* JADX INFO: compiled from: GlobalAiStateManager.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u0004\u0005\u0006\u0007\b\t\nB\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0007\u000b\f\r\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "FirstFlow", "Generating", "Interrupt", "DataFinish", "FlowDone", "StartRefresh", "RefreshComplete", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$DataFinish;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$FirstFlow;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$FlowDone;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$Generating;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$Interrupt;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$RefreshComplete;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$StartRefresh;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static abstract class FlowState {
        public /* synthetic */ FlowState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$FirstFlow;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class FirstFlow extends FlowState {
            public static final FirstFlow INSTANCE = new FirstFlow();

            private FirstFlow() {
                super(null);
            }
        }

        private FlowState() {
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$Generating;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Generating extends FlowState {
            public static final Generating INSTANCE = new Generating();

            private Generating() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$Interrupt;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", "endTip", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getEndTip", "()Ljava/lang/String;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final /* data */ class Interrupt extends FlowState {
            private final String endTip;

            public static /* synthetic */ Interrupt copy$default(Interrupt interrupt, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = interrupt.endTip;
                }
                return interrupt.copy(str);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final String getEndTip() {
                return this.endTip;
            }

            public final Interrupt copy(String endTip) {
                Intrinsics.checkNotNullParameter(endTip, "endTip");
                return new Interrupt(endTip);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Interrupt) && Intrinsics.areEqual(this.endTip, ((Interrupt) other).endTip);
            }

            public int hashCode() {
                return this.endTip.hashCode();
            }

            public String toString() {
                return "Interrupt(endTip=" + this.endTip + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Interrupt(String endTip) {
                super(null);
                Intrinsics.checkNotNullParameter(endTip, "endTip");
                this.endTip = endTip;
            }

            public final String getEndTip() {
                return this.endTip;
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$DataFinish;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class DataFinish extends FlowState {
            public static final DataFinish INSTANCE = new DataFinish();

            private DataFinish() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$FlowDone;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class FlowDone extends FlowState {
            public static final FlowDone INSTANCE = new FlowDone();

            private FlowDone() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$StartRefresh;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class StartRefresh extends FlowState {
            public static final StartRefresh INSTANCE = new StartRefresh();

            private StartRefresh() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: GlobalAiStateManager.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState$RefreshComplete;", "Lcom/miui/notes/ai/floatingwindow/GlobalAiStateManager$FlowState;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class RefreshComplete extends FlowState {
            public static final RefreshComplete INSTANCE = new RefreshComplete();

            private RefreshComplete() {
                super(null);
            }
        }
    }
}
