package androidx.graphics.surface;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SurfaceControlCompat.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0000\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/graphics/surface/SurfaceControlVerificationHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceControlVerificationHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    private SurfaceControlVerificationHelper() {
    }

    /* JADX INFO: compiled from: SurfaceControlCompat.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007J\b\u0010\u0006\u001a\u00020\u0005H\u0007J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\bH\u0007¨\u0006\n"}, d2 = {"Landroidx/graphics/surface/SurfaceControlVerificationHelper$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createBuilderV33", "Landroidx/graphics/surface/SurfaceControlImpl$Builder;", "createBuilderV29", "createTransactionV33", "Landroidx/graphics/surface/SurfaceControlImpl$Transaction;", "createTransactionV29", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SurfaceControlImpl.Builder createBuilderV33() {
            return new SurfaceControlV33.Builder();
        }

        public final SurfaceControlImpl.Builder createBuilderV29() {
            return new SurfaceControlV29.Builder();
        }

        public final SurfaceControlImpl.Transaction createTransactionV33() {
            return new SurfaceControlV33.Transaction();
        }

        public final SurfaceControlImpl.Transaction createTransactionV29() {
            return new SurfaceControlV29.Transaction();
        }
    }
}
