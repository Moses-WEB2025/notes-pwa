package androidx.graphics.surface;

import android.view.SurfaceControl;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SurfaceControlV33.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007¨\u0006\u000e"}, d2 = {"Landroidx/graphics/surface/SurfaceControlVerificationHelperV30;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "setFrameRate", "", "transaction", "Landroid/view/SurfaceControl$Transaction;", "surfaceControl", "Landroid/view/SurfaceControl;", "frameRate", "", "compatibility", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
final class SurfaceControlVerificationHelperV30 {
    public static final SurfaceControlVerificationHelperV30 INSTANCE = new SurfaceControlVerificationHelperV30();

    private SurfaceControlVerificationHelperV30() {
    }

    public final void setFrameRate(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float frameRate, int compatibility) {
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        transaction.setFrameRate(surfaceControl, frameRate, compatibility);
    }
}
