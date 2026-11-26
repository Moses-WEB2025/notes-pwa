package androidx.graphics.opengl;

import androidx.graphics.opengl.egl.EGLSpec;
import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrameBufferRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Landroidx/graphics/opengl/SyncStrategy;", "", "createSyncFence", "Landroidx/hardware/SyncFenceCompat;", "eglSpec", "Landroidx/graphics/opengl/egl/EGLSpec;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface SyncStrategy {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final SyncStrategy ALWAYS = new SyncStrategy() { // from class: androidx.graphics.opengl.SyncStrategy$Companion$ALWAYS$1
        @Override // androidx.graphics.opengl.SyncStrategy
        public SyncFenceCompat createSyncFence(EGLSpec eglSpec) {
            Intrinsics.checkNotNullParameter(eglSpec, "eglSpec");
            return SyncFenceCompat.INSTANCE.createNativeSyncFence();
        }
    };

    SyncFenceCompat createSyncFence(EGLSpec eglSpec);

    /* JADX INFO: compiled from: FrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0013\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0001¨\u0006\u0006"}, d2 = {"Landroidx/graphics/opengl/SyncStrategy$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ALWAYS", "Landroidx/graphics/opengl/SyncStrategy;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }
}
