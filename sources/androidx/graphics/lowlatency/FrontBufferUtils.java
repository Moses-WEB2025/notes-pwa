package androidx.graphics.lowlatency;

import android.os.Build;
import androidx.graphics.surface.SurfaceControlCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrontBufferUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0000\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/graphics/lowlatency/FrontBufferUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrontBufferUtils {
    public static final long BaseFlags = 2816;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String TAG = "FrontBufferUtils";
    public static final boolean UseCompatSurfaceControl = false;

    private FrontBufferUtils() {
    }

    /* JADX INFO: compiled from: FrontBufferUtils.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\b\u001a\u00020\u0007H\u0000¢\u0006\u0002\b\tJ&\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0000X\u0081T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Landroidx/graphics/lowlatency/FrontBufferUtils$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "BaseFlags", "", "obtainHardwareBufferUsageFlags", "obtainHardwareBufferUsageFlags$libXCEngine_release", "UseCompatSurfaceControl", "", "configureFrontBufferLayerFrameRate", "Landroidx/graphics/surface/SurfaceControlCompat$Transaction;", "frontBufferSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "frameRate", "", "transaction", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long obtainHardwareBufferUsageFlags$libXCEngine_release() {
            if (Build.VERSION.SDK_INT >= 33) {
                return UsageFlagsVerificationHelper.INSTANCE.obtainUsageFlagsV33();
            }
            return 2816L;
        }

        public static /* synthetic */ SurfaceControlCompat.Transaction configureFrontBufferLayerFrameRate$default(Companion companion, SurfaceControlCompat surfaceControlCompat, float f, SurfaceControlCompat.Transaction transaction, int i, Object obj) {
            if ((i & 2) != 0) {
                f = 1000.0f;
            }
            if ((i & 4) != 0) {
                transaction = null;
            }
            return companion.configureFrontBufferLayerFrameRate(surfaceControlCompat, f, transaction);
        }

        public final SurfaceControlCompat.Transaction configureFrontBufferLayerFrameRate(SurfaceControlCompat frontBufferSurfaceControl, float frameRate, SurfaceControlCompat.Transaction transaction) {
            Intrinsics.checkNotNullParameter(frontBufferSurfaceControl, "frontBufferSurfaceControl");
            if (Build.VERSION.SDK_INT >= 30) {
                if (transaction == null) {
                    transaction = new SurfaceControlCompat.Transaction();
                }
                transaction.setFrameRate(frontBufferSurfaceControl, frameRate, 0, 0);
            }
            return transaction;
        }
    }
}
