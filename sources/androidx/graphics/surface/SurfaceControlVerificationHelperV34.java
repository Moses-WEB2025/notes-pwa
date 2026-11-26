package androidx.graphics.surface;

import android.view.SurfaceControl;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SurfaceControlV33.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007¨\u0006\n"}, d2 = {"Landroidx/graphics/surface/SurfaceControlVerificationHelperV34;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "clearFrameRate", "", "transaction", "Landroid/view/SurfaceControl$Transaction;", "surfaceControl", "Landroid/view/SurfaceControl;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
final class SurfaceControlVerificationHelperV34 {
    public static final SurfaceControlVerificationHelperV34 INSTANCE = new SurfaceControlVerificationHelperV34();

    private SurfaceControlVerificationHelperV34() {
    }

    public final void clearFrameRate(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        transaction.clearFrameRate(surfaceControl);
    }
}
