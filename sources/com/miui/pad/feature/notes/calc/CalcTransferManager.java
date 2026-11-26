package com.miui.pad.feature.notes.calc;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CalcTransferManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000 \u00112\u00020\u0001:\u0002\u0011\u0012B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u000e\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0005J\u0006\u0010\u0010\u001a\u00020\u000bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcTransferManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mListener", "Lcom/miui/pad/feature/notes/calc/CalcTransferManager$CalcLifeListener;", "mCalActive", "", "mLastHashCode", "", "calcCreate", "", "hashCode", "calcDestroy", "getCalcStatus", "listener", "removeListener", "Companion", "CalcLifeListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CalcTransferManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<CalcTransferManager> instance$delegate = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.miui.pad.feature.notes.calc.CalcTransferManager$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return CalcTransferManager.instance_delegate$lambda$0();
        }
    });
    private boolean mCalActive;
    private int mLastHashCode;
    private CalcLifeListener mListener;

    /* JADX INFO: compiled from: CalcTransferManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004À\u0006\u0003"}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcTransferManager$CalcLifeListener;", "", "onCalcDestroy", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface CalcLifeListener {
        void onCalcDestroy();
    }

    private CalcTransferManager() {
    }

    /* JADX INFO: compiled from: CalcTransferManager.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/miui/pad/feature/notes/calc/CalcTransferManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instance", "Lcom/miui/pad/feature/notes/calc/CalcTransferManager;", "getInstance", "()Lcom/miui/pad/feature/notes/calc/CalcTransferManager;", "instance$delegate", "Lkotlin/Lazy;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CalcTransferManager getInstance() {
            return (CalcTransferManager) CalcTransferManager.instance$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CalcTransferManager instance_delegate$lambda$0() {
        return new CalcTransferManager();
    }

    public final void calcCreate(int hashCode) {
        this.mCalActive = true;
        this.mLastHashCode = hashCode;
    }

    public final void calcDestroy(int hashCode) {
        if (this.mLastHashCode == hashCode) {
            this.mCalActive = false;
            CalcLifeListener calcLifeListener = this.mListener;
            if (calcLifeListener != null) {
                calcLifeListener.onCalcDestroy();
            }
        }
    }

    public final void getCalcStatus(CalcLifeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
        if (this.mCalActive || listener == null) {
            return;
        }
        listener.onCalcDestroy();
    }

    public final void removeListener() {
        this.mListener = null;
    }
}
