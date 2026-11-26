package androidx.graphics.surface;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.view.Surface;
import androidx.graphics.utils.JniVisible;
import androidx.hardware.SyncFenceV19;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SurfaceControlWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0001\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/graphics/surface/JniBindings;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
@JniVisible
public final class JniBindings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    @JniVisible
    public static final native long nCreate(long j, String str);

    @JvmStatic
    @JniVisible
    public static final native long nCreateFromSurface(Surface surface, String str);

    @JvmStatic
    @JniVisible
    public static final native int nDupFenceFd(SyncFenceV19 syncFenceV19);

    @JvmStatic
    @JniVisible
    public static final native String nGetDisplayOrientation();

    @JvmStatic
    @JniVisible
    public static final native int nGetPreviousReleaseFenceFd(long j, long j2);

    @JvmStatic
    @JniVisible
    public static final native boolean nIsHwuiUsingVulkanRenderer();

    @JvmStatic
    @JniVisible
    public static final native void nRelease(long j);

    @JvmStatic
    @JniVisible
    public static final native void nSetBuffer(long j, long j2, HardwareBuffer hardwareBuffer, SyncFenceV19 syncFenceV19);

    @JvmStatic
    @JniVisible
    public static final native void nSetBufferAlpha(long j, long j2, float f);

    @JvmStatic
    @JniVisible
    public static final native void nSetBufferTransform(long j, long j2, int i);

    @JvmStatic
    @JniVisible
    public static final native void nSetBufferTransparency(long j, long j2, byte b);

    @JvmStatic
    @JniVisible
    public static final native void nSetCrop(long j, long j2, int i, int i2, int i3, int i4);

    @JvmStatic
    @JniVisible
    public static final native void nSetDamageRegion(long j, long j2, Rect rect);

    @JvmStatic
    @JniVisible
    public static final native void nSetDataSpace(long j, long j2, int i);

    @JvmStatic
    @JniVisible
    public static final native void nSetDesiredPresentTime(long j, long j2);

    @JvmStatic
    @JniVisible
    public static final native void nSetFrameRate(long j, long j2, float f, int i, int i2);

    @JvmStatic
    @JniVisible
    public static final native void nSetGeometry(long j, long j2, int i, int i2, int i3, int i4, int i5);

    @JvmStatic
    @JniVisible
    public static final native void nSetPosition(long j, long j2, float f, float f2);

    @JvmStatic
    @JniVisible
    public static final native void nSetScale(long j, long j2, float f, float f2);

    @JvmStatic
    @JniVisible
    public static final native void nSetVisibility(long j, long j2, byte b);

    @JvmStatic
    @JniVisible
    public static final native void nSetZOrder(long j, long j2, int i);

    @JvmStatic
    @JniVisible
    public static final native void nTransactionApply(long j);

    @JvmStatic
    @JniVisible
    public static final native long nTransactionCreate();

    @JvmStatic
    @JniVisible
    public static final native void nTransactionDelete(long j);

    @JvmStatic
    @JniVisible
    public static final native void nTransactionReparent(long j, long j2, long j3);

    @JvmStatic
    @JniVisible
    public static final native void nTransactionSetOnCommit(long j, SurfaceControlCompat.TransactionCommittedListener transactionCommittedListener);

    @JvmStatic
    @JniVisible
    public static final native void nTransactionSetOnComplete(long j, SurfaceControlCompat.TransactionCompletedListener transactionCompletedListener);

    /* JADX INFO: compiled from: SurfaceControlWrapper.kt */
    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0005\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0087 J\u0019\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0087 J\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u0005H\u0087 J\t\u0010\u000e\u001a\u00020\u0005H\u0087 J\u0011\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0005H\u0087 J\u0011\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0005H\u0087 J!\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005H\u0087 J\u0019\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0087 J\u0019\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0018H\u0087 J\u0011\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0087 J+\u0010\u001d\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u001cH\u0087 JA\u0010!\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u001aH\u0087 J!\u0010'\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010(\u001a\u00020)H\u0087 J!\u0010*\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u001aH\u0087 J#\u0010,\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010-\u001a\u0004\u0018\u00010.H\u0087 J\u0019\u0010/\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0087 J!\u00101\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u00102\u001a\u00020)H\u0087 J!\u00103\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u00104\u001a\u000205H\u0087 J9\u00106\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u00020\u001a2\u0006\u00109\u001a\u00020\u001a2\u0006\u0010:\u001a\u00020\u001aH\u0087 J)\u0010;\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010<\u001a\u0002052\u0006\u0010=\u001a\u000205H\u0087 J)\u0010>\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010?\u001a\u0002052\u0006\u0010@\u001a\u000205H\u0087 J!\u0010A\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u001aH\u0087 J!\u0010B\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010C\u001a\u00020\u001aH\u0087 J\t\u0010D\u001a\u00020\bH\u0087 J\t\u0010E\u001a\u00020FH\u0087 J\u0019\u0010G\u001a\u00020\u001a2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010H\u001a\u00020\u0005H\u0087 J1\u0010I\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010J\u001a\u0002052\u0006\u0010K\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020\u001aH\u0087 ¨\u0006M"}, d2 = {"Landroidx/graphics/surface/JniBindings$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "nCreate", "", "surfaceControl", "debugName", "", "nCreateFromSurface", "surface", "Landroid/view/Surface;", "nRelease", "", "nTransactionCreate", "nTransactionDelete", "surfaceTransaction", "nTransactionApply", "nTransactionReparent", "newParent", "nTransactionSetOnComplete", "listener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCompletedListener;", "nTransactionSetOnCommit", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "nDupFenceFd", "", "syncFence", "Landroidx/hardware/SyncFenceV19;", "nSetBuffer", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "acquireFieldFd", "nSetGeometry", "bufferWidth", "bufferHeight", "dstWidth", "dstHeight", "transformation", "nSetVisibility", "visibility", "", "nSetZOrder", "zOrder", "nSetDamageRegion", "rect", "Landroid/graphics/Rect;", "nSetDesiredPresentTime", "desiredPresentTime", "nSetBufferTransparency", "transparency", "nSetBufferAlpha", "alpha", "", "nSetCrop", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "nSetPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "nSetScale", "scaleX", "scaleY", "nSetBufferTransform", "nSetDataSpace", "dataSpace", "nGetDisplayOrientation", "nIsHwuiUsingVulkanRenderer", "", "nGetPreviousReleaseFenceFd", "transactionStats", "nSetFrameRate", "frameRate", "compatibility", "changeFrameRateStrategy", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @JniVisible
        public final long nCreate(long surfaceControl, String debugName) {
            return JniBindings.nCreate(surfaceControl, debugName);
        }

        @JvmStatic
        @JniVisible
        public final long nCreateFromSurface(Surface surface, String debugName) {
            return JniBindings.nCreateFromSurface(surface, debugName);
        }

        @JvmStatic
        @JniVisible
        public final int nDupFenceFd(SyncFenceV19 syncFence) {
            return JniBindings.nDupFenceFd(syncFence);
        }

        @JvmStatic
        @JniVisible
        public final String nGetDisplayOrientation() {
            return JniBindings.nGetDisplayOrientation();
        }

        @JvmStatic
        @JniVisible
        public final int nGetPreviousReleaseFenceFd(long surfaceControl, long transactionStats) {
            return JniBindings.nGetPreviousReleaseFenceFd(surfaceControl, transactionStats);
        }

        @JvmStatic
        @JniVisible
        public final boolean nIsHwuiUsingVulkanRenderer() {
            return JniBindings.nIsHwuiUsingVulkanRenderer();
        }

        @JvmStatic
        @JniVisible
        public final void nRelease(long surfaceControl) {
            JniBindings.nRelease(surfaceControl);
        }

        @JvmStatic
        @JniVisible
        public final void nSetBuffer(long surfaceTransaction, long surfaceControl, HardwareBuffer hardwareBuffer, SyncFenceV19 acquireFieldFd) {
            JniBindings.nSetBuffer(surfaceTransaction, surfaceControl, hardwareBuffer, acquireFieldFd);
        }

        @JvmStatic
        @JniVisible
        public final void nSetBufferAlpha(long surfaceTransaction, long surfaceControl, float alpha) {
            JniBindings.nSetBufferAlpha(surfaceTransaction, surfaceControl, alpha);
        }

        @JvmStatic
        @JniVisible
        public final void nSetBufferTransform(long surfaceTransaction, long surfaceControl, int transformation) {
            JniBindings.nSetBufferTransform(surfaceTransaction, surfaceControl, transformation);
        }

        @JvmStatic
        @JniVisible
        public final void nSetBufferTransparency(long surfaceTransaction, long surfaceControl, byte transparency) {
            JniBindings.nSetBufferTransparency(surfaceTransaction, surfaceControl, transparency);
        }

        @JvmStatic
        @JniVisible
        public final void nSetCrop(long surfaceTransaction, long surfaceControl, int left, int top, int right, int bottom) {
            JniBindings.nSetCrop(surfaceTransaction, surfaceControl, left, top, right, bottom);
        }

        @JvmStatic
        @JniVisible
        public final void nSetDamageRegion(long surfaceTransaction, long surfaceControl, Rect rect) {
            JniBindings.nSetDamageRegion(surfaceTransaction, surfaceControl, rect);
        }

        @JvmStatic
        @JniVisible
        public final void nSetDataSpace(long surfaceTransaction, long surfaceControl, int dataSpace) {
            JniBindings.nSetDataSpace(surfaceTransaction, surfaceControl, dataSpace);
        }

        @JvmStatic
        @JniVisible
        public final void nSetDesiredPresentTime(long surfaceTransaction, long desiredPresentTime) {
            JniBindings.nSetDesiredPresentTime(surfaceTransaction, desiredPresentTime);
        }

        @JvmStatic
        @JniVisible
        public final void nSetFrameRate(long surfaceTransaction, long surfaceControl, float frameRate, int compatibility, int changeFrameRateStrategy) {
            JniBindings.nSetFrameRate(surfaceTransaction, surfaceControl, frameRate, compatibility, changeFrameRateStrategy);
        }

        @JvmStatic
        @JniVisible
        public final void nSetGeometry(long surfaceTransaction, long surfaceControl, int bufferWidth, int bufferHeight, int dstWidth, int dstHeight, int transformation) {
            JniBindings.nSetGeometry(surfaceTransaction, surfaceControl, bufferWidth, bufferHeight, dstWidth, dstHeight, transformation);
        }

        @JvmStatic
        @JniVisible
        public final void nSetPosition(long surfaceTransaction, long surfaceControl, float x, float y) {
            JniBindings.nSetPosition(surfaceTransaction, surfaceControl, x, y);
        }

        @JvmStatic
        @JniVisible
        public final void nSetScale(long surfaceTransaction, long surfaceControl, float scaleX, float scaleY) {
            JniBindings.nSetScale(surfaceTransaction, surfaceControl, scaleX, scaleY);
        }

        @JvmStatic
        @JniVisible
        public final void nSetVisibility(long surfaceTransaction, long surfaceControl, byte visibility) {
            JniBindings.nSetVisibility(surfaceTransaction, surfaceControl, visibility);
        }

        @JvmStatic
        @JniVisible
        public final void nSetZOrder(long surfaceTransaction, long surfaceControl, int zOrder) {
            JniBindings.nSetZOrder(surfaceTransaction, surfaceControl, zOrder);
        }

        @JvmStatic
        @JniVisible
        public final void nTransactionApply(long surfaceTransaction) {
            JniBindings.nTransactionApply(surfaceTransaction);
        }

        @JvmStatic
        @JniVisible
        public final long nTransactionCreate() {
            return JniBindings.nTransactionCreate();
        }

        @JvmStatic
        @JniVisible
        public final void nTransactionDelete(long surfaceTransaction) {
            JniBindings.nTransactionDelete(surfaceTransaction);
        }

        @JvmStatic
        @JniVisible
        public final void nTransactionReparent(long surfaceTransaction, long surfaceControl, long newParent) {
            JniBindings.nTransactionReparent(surfaceTransaction, surfaceControl, newParent);
        }

        @JvmStatic
        @JniVisible
        public final void nTransactionSetOnCommit(long surfaceTransaction, SurfaceControlCompat.TransactionCommittedListener listener) {
            JniBindings.nTransactionSetOnCommit(surfaceTransaction, listener);
        }

        @JvmStatic
        @JniVisible
        public final void nTransactionSetOnComplete(long surfaceTransaction, SurfaceControlCompat.TransactionCompletedListener listener) {
            JniBindings.nTransactionSetOnComplete(surfaceTransaction, listener);
        }

        private Companion() {
        }
    }

    static {
        System.loadLibrary("graphics-core");
    }
}
