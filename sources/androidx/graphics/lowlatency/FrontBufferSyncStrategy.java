package androidx.graphics.lowlatency;

import android.opengl.GLES20;
import androidx.graphics.opengl.SyncStrategy;
import androidx.graphics.opengl.egl.EGLSpec;
import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrontBufferSyncStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Landroidx/graphics/lowlatency/FrontBufferSyncStrategy;", "Landroidx/graphics/opengl/SyncStrategy;", "usageFlags", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(J)V", "supportsFrontBufferUsage", "", "mFrontBufferVisible", "visibility", "isVisible", "()Z", "setVisible", "(Z)V", "createSyncFence", "Landroidx/hardware/SyncFenceCompat;", "eglSpec", "Landroidx/graphics/opengl/egl/EGLSpec;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrontBufferSyncStrategy implements SyncStrategy {
    private boolean mFrontBufferVisible;
    private final boolean supportsFrontBufferUsage;

    public FrontBufferSyncStrategy(long j) {
        this.supportsFrontBufferUsage = (j & 4294967296L) != 0;
    }

    /* JADX INFO: renamed from: isVisible, reason: from getter */
    public final boolean getMFrontBufferVisible() {
        return this.mFrontBufferVisible;
    }

    public final void setVisible(boolean z) {
        this.mFrontBufferVisible = z;
    }

    @Override // androidx.graphics.opengl.SyncStrategy
    public SyncFenceCompat createSyncFence(EGLSpec eglSpec) {
        Intrinsics.checkNotNullParameter(eglSpec, "eglSpec");
        if (!getMFrontBufferVisible()) {
            return SyncFenceCompat.INSTANCE.createNativeSyncFence();
        }
        if (this.supportsFrontBufferUsage) {
            GLES20.glFlush();
            return null;
        }
        SyncFenceCompat.INSTANCE.createNativeSyncFence().close();
        return null;
    }
}
