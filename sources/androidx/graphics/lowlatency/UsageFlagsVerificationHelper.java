package androidx.graphics.lowlatency;

import android.hardware.HardwareBuffer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FrontBufferUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0001\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/graphics/lowlatency/UsageFlagsVerificationHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class UsageFlagsVerificationHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: FrontBufferUtils.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0001¢\u0006\u0002\b\bJ\b\u0010\t\u001a\u00020\u0007H\u0007¨\u0006\n"}, d2 = {"Landroidx/graphics/lowlatency/UsageFlagsVerificationHelper$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isSupported", "", "flag", "", "isSupported$libXCEngine_release", "obtainUsageFlagsV33", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isSupported$libXCEngine_release(long flag) {
            return HardwareBuffer.isSupported(1, 1, 1, 1, flag | 2816);
        }

        public final long obtainUsageFlagsV33() {
            return isSupported$libXCEngine_release(4294967296L) ? 4294970112L : 2864L;
        }
    }

    private UsageFlagsVerificationHelper() {
    }
}
