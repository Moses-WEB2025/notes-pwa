package androidx.graphics.surface;

import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.view.Surface;
import androidx.hardware.SyncFenceV19;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SurfaceControlWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006B\u0019\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\tJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0013\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0006\u0010\u0016\u001a\u00020\u0017J\b\u0010\u0018\u001a\u00020\u0017H\u0004R\u001a\u0010\n\u001a\u00020\u000bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Landroidx/graphics/surface/SurfaceControlWrapper;", "", "surfaceControl", "debugName", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/surface/SurfaceControlWrapper;Ljava/lang/String;)V", "surface", "Landroid/view/Surface;", "(Landroid/view/Surface;Ljava/lang/String;)V", "mNativeSurfaceControl", "", "getMNativeSurfaceControl$libXCEngine_release", "()J", "setMNativeSurfaceControl$libXCEngine_release", "(J)V", "isValid", "", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "release", "", "finalize", "Transaction", "Builder", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceControlWrapper {
    private long mNativeSurfaceControl;

    public SurfaceControlWrapper(SurfaceControlWrapper surfaceControl, String debugName) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        long jNCreate = JniBindings.INSTANCE.nCreate(surfaceControl.mNativeSurfaceControl, debugName);
        this.mNativeSurfaceControl = jNCreate;
        if (jNCreate == 0) {
            throw new IllegalArgumentException();
        }
    }

    public SurfaceControlWrapper(Surface surface, String debugName) {
        Intrinsics.checkNotNullParameter(surface, "surface");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        long jNCreateFromSurface = JniBindings.INSTANCE.nCreateFromSurface(surface, debugName);
        this.mNativeSurfaceControl = jNCreateFromSurface;
        if (jNCreateFromSurface == 0) {
            throw new IllegalArgumentException();
        }
    }

    /* JADX INFO: renamed from: getMNativeSurfaceControl$libXCEngine_release, reason: from getter */
    public final long getMNativeSurfaceControl() {
        return this.mNativeSurfaceControl;
    }

    public final void setMNativeSurfaceControl$libXCEngine_release(long j) {
        this.mNativeSurfaceControl = j;
    }

    /* JADX INFO: compiled from: SurfaceControlWrapper.kt */
    @Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0007J\u0015\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0000¢\u0006\u0002\b\u000bJ\u001a\u0010\f\u001a\u00020\u00002\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\t\u001a\u00020\u000fH\u0007J$\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007J\u0016\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u0019J\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010 \u001a\u0004\u0018\u00010!J\u0018\u0010\"\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010#\u001a\u0004\u0018\u00010\u0012J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020\u0005J\u0016\u0010&\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010'\u001a\u00020\u001cJ\u0016\u0010(\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010)\u001a\u00020*J\u001a\u0010+\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010,\u001a\u0004\u0018\u00010-H\u0007J \u0010.\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010/\u001a\u00020*2\u0006\u00100\u001a\u00020*H\u0007J \u00101\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00102\u001a\u00020*2\u0006\u00103\u001a\u00020*H\u0007J\u0018\u00104\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00105\u001a\u00020\u0019H\u0007J6\u00106\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00107\u001a\u00020\u00192\u0006\u00108\u001a\u00020\u00192\u0006\u00109\u001a\u00020\u00192\u0006\u0010:\u001a\u00020\u00192\u0006\u00105\u001a\u00020\u0019J&\u0010;\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010<\u001a\u00020*2\u0006\u0010=\u001a\u00020\u00192\u0006\u0010>\u001a\u00020\u0019J\u0006\u0010?\u001a\u00020\u0007J\u0006\u0010@\u001a\u00020\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Landroidx/graphics/surface/SurfaceControlWrapper$Transaction;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mNativeSurfaceTransaction", "", "commit", "", "addTransactionCompletedListener", "listener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCompletedListener;", "addTransactionCompletedListener$libXCEngine_release", "addTransactionCommittedListener", "executor", "Ljava/util/concurrent/Executor;", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "setBuffer", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlWrapper;", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "syncFence", "Landroidx/hardware/SyncFenceV19;", "setDataSpace", "dataSpace", "", "setVisibility", "visibility", "", "setLayer", "zOrder", "setDamageRegion", "region", "Landroid/graphics/Region;", "reparent", "newParent", "setDesiredPresentTime", "desiredPresentTimeNano", "setOpaque", "isOpaque", "setAlpha", "alpha", "", "setCrop", "crop", "Landroid/graphics/Rect;", "setPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setScale", "scaleX", "scaleY", "setBufferTransform", "transformation", "setGeometry", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "dstWidth", "dstHeight", "setFrameRate", "frameRate", "compatibility", "changeFrameRateStrategy", SplitLayout.TAG_CLOSE, "finalize", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Transaction {
        private long mNativeSurfaceTransaction;

        public final Transaction setBuffer(SurfaceControlWrapper surfaceControl, HardwareBuffer hardwareBuffer) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            return setBuffer$default(this, surfaceControl, hardwareBuffer, null, 4, null);
        }

        public Transaction() {
            long jNTransactionCreate = JniBindings.INSTANCE.nTransactionCreate();
            this.mNativeSurfaceTransaction = jNTransactionCreate;
            if (jNTransactionCreate == 0) {
                throw new IllegalArgumentException();
            }
        }

        public final void commit() {
            JniBindings.INSTANCE.nTransactionApply(this.mNativeSurfaceTransaction);
        }

        public final Transaction addTransactionCompletedListener$libXCEngine_release(SurfaceControlCompat.TransactionCompletedListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            JniBindings.INSTANCE.nTransactionSetOnComplete(this.mNativeSurfaceTransaction, listener);
            return this;
        }

        public final Transaction addTransactionCommittedListener(Executor executor, SurfaceControlCompat.TransactionCommittedListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            if (executor != null) {
                listener = new SurfaceControlWrapper$Transaction$addTransactionCommittedListener$1(executor, listener);
            }
            JniBindings.INSTANCE.nTransactionSetOnCommit(this.mNativeSurfaceTransaction, listener);
            return this;
        }

        public static /* synthetic */ Transaction setBuffer$default(Transaction transaction, SurfaceControlWrapper surfaceControlWrapper, HardwareBuffer hardwareBuffer, SyncFenceV19 syncFenceV19, int i, Object obj) {
            if ((i & 4) != 0) {
                syncFenceV19 = new SyncFenceV19(-1);
            }
            return transaction.setBuffer(surfaceControlWrapper, hardwareBuffer, syncFenceV19);
        }

        public final Transaction setBuffer(SurfaceControlWrapper surfaceControl, HardwareBuffer hardwareBuffer, SyncFenceV19 syncFence) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            Intrinsics.checkNotNullParameter(syncFence, "syncFence");
            JniBindings.INSTANCE.nSetBuffer(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), hardwareBuffer, syncFence);
            return this;
        }

        public final Transaction setDataSpace(SurfaceControlWrapper surfaceControl, int dataSpace) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetDataSpace(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), dataSpace);
            return this;
        }

        public final Transaction setVisibility(SurfaceControlWrapper surfaceControl, boolean visibility) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetVisibility(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), visibility ? (byte) 1 : (byte) 0);
            return this;
        }

        public final Transaction setLayer(SurfaceControlWrapper surfaceControl, int zOrder) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetZOrder(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), zOrder);
            return this;
        }

        public final Transaction setDamageRegion(SurfaceControlWrapper surfaceControl, Region region) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetDamageRegion(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), region != null ? region.getBounds() : null);
            return this;
        }

        public final Transaction reparent(SurfaceControlWrapper surfaceControl, SurfaceControlWrapper newParent) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nTransactionReparent(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), newParent != null ? newParent.getMNativeSurfaceControl() : 0L);
            return this;
        }

        public final Transaction setDesiredPresentTime(long desiredPresentTimeNano) {
            JniBindings.INSTANCE.nSetDesiredPresentTime(this.mNativeSurfaceTransaction, desiredPresentTimeNano);
            return this;
        }

        public final Transaction setOpaque(SurfaceControlWrapper surfaceControl, boolean isOpaque) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetBufferTransparency(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), isOpaque ? (byte) 2 : (byte) 0);
            return this;
        }

        public final Transaction setAlpha(SurfaceControlWrapper surfaceControl, float alpha) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (alpha < 0.0f || alpha > 1.0f) {
                throw new IllegalArgumentException("Alpha value must be between 0.0 and 1.0.");
            }
            JniBindings.INSTANCE.nSetBufferAlpha(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), alpha);
            return this;
        }

        public final Transaction setCrop(SurfaceControlWrapper surfaceControl, Rect crop) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (crop != null && (crop.width() < 0 || crop.height() < 0)) {
                throw new IllegalArgumentException("width and height must be non-negative");
            }
            if (crop == null) {
                JniBindings.INSTANCE.nSetCrop(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), 0, 0, 0, 0);
                return this;
            }
            JniBindings.INSTANCE.nSetCrop(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), crop.left, crop.top, crop.right, crop.bottom);
            return this;
        }

        public final Transaction setPosition(SurfaceControlWrapper surfaceControl, float x, float y) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetPosition(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), x, y);
            return this;
        }

        public final Transaction setScale(SurfaceControlWrapper surfaceControl, float scaleX, float scaleY) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetScale(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), scaleX, scaleY);
            return this;
        }

        public final Transaction setBufferTransform(SurfaceControlWrapper surfaceControl, int transformation) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetBufferTransform(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), transformation);
            return this;
        }

        public final Transaction setGeometry(SurfaceControlWrapper surfaceControl, int width, int height, int dstWidth, int dstHeight, int transformation) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetGeometry(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), width, height, dstWidth, dstHeight, transformation);
            return this;
        }

        public final Transaction setFrameRate(SurfaceControlWrapper surfaceControl, float frameRate, int compatibility, int changeFrameRateStrategy) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            JniBindings.INSTANCE.nSetFrameRate(this.mNativeSurfaceTransaction, surfaceControl.getMNativeSurfaceControl(), frameRate, compatibility, changeFrameRateStrategy);
            return this;
        }

        public final void close() {
            if (this.mNativeSurfaceTransaction != 0) {
                JniBindings.INSTANCE.nTransactionDelete(this.mNativeSurfaceTransaction);
                this.mNativeSurfaceTransaction = 0L;
            }
        }

        public final void finalize() {
            close();
        }
    }

    public final boolean isValid() {
        return this.mNativeSurfaceControl != 0;
    }

    public boolean equals(Object other) {
        if (Intrinsics.areEqual(other, this)) {
            return true;
        }
        if ((other == null) || (!Intrinsics.areEqual(other != null ? other.getClass() : null, SurfaceControlWrapper.class))) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.graphics.surface.SurfaceControlWrapper");
        return ((SurfaceControlWrapper) other).mNativeSurfaceControl == this.mNativeSurfaceControl;
    }

    public int hashCode() {
        return Long.hashCode(this.mNativeSurfaceControl);
    }

    public final void release() {
        if (this.mNativeSurfaceControl != 0) {
            JniBindings.INSTANCE.nRelease(this.mNativeSurfaceControl);
            this.mNativeSurfaceControl = 0L;
        }
    }

    protected final void finalize() {
        release();
    }

    /* JADX INFO: compiled from: SurfaceControlWrapper.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0005J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0007J\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\tJ\u0006\u0010\u000f\u001a\u00020\u0007R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/graphics/surface/SurfaceControlWrapper$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mSurface", "Landroid/view/Surface;", "mSurfaceControl", "Landroidx/graphics/surface/SurfaceControlWrapper;", "mDebugName", "", "setParent", "surface", "surfaceControlWrapper", "setDebugName", "debugName", "build", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private String mDebugName;
        private Surface mSurface;
        private SurfaceControlWrapper mSurfaceControl;

        public final Builder setParent(Surface surface) {
            Intrinsics.checkNotNullParameter(surface, "surface");
            this.mSurface = surface;
            this.mSurfaceControl = null;
            return this;
        }

        public final Builder setParent(SurfaceControlWrapper surfaceControlWrapper) {
            Intrinsics.checkNotNullParameter(surfaceControlWrapper, "surfaceControlWrapper");
            this.mSurface = null;
            this.mSurfaceControl = surfaceControlWrapper;
            return this;
        }

        public final Builder setDebugName(String debugName) {
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            this.mDebugName = debugName;
            return this;
        }

        public final SurfaceControlWrapper build() {
            Surface surface = this.mSurface;
            SurfaceControlWrapper surfaceControlWrapper = this.mSurfaceControl;
            String str = null;
            if (surface != null) {
                String str2 = this.mDebugName;
                if (str2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDebugName");
                } else {
                    str = str2;
                }
                return new SurfaceControlWrapper(surface, str);
            }
            if (surfaceControlWrapper != null) {
                String str3 = this.mDebugName;
                if (str3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDebugName");
                } else {
                    str = str3;
                }
                return new SurfaceControlWrapper(surfaceControlWrapper, str);
            }
            throw new IllegalStateException("");
        }
    }
}
