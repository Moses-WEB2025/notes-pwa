package androidx.graphics.surface;

import android.view.SurfaceControl;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SurfaceControlV33.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007¨\u0006\f"}, d2 = {"Landroidx/graphics/surface/SurfaceControlTransactionVerificationHelperV33;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "setDataSpace", "", "transaction", "Landroid/view/SurfaceControl$Transaction;", "surfaceControl", "Landroid/view/SurfaceControl;", "dataspace", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
final class SurfaceControlTransactionVerificationHelperV33 {
    public static final SurfaceControlTransactionVerificationHelperV33 INSTANCE = new SurfaceControlTransactionVerificationHelperV33();

    private SurfaceControlTransactionVerificationHelperV33() {
    }

    public final void setDataSpace(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int dataspace) {
        Intrinsics.checkNotNullParameter(transaction, "transaction");
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        transaction.setDataSpace(surfaceControl, dataspace);
    }
}
