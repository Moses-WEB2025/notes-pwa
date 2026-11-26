package androidx.graphics.surface;

import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.os.Build;
import android.view.AttachedSurfaceControl;
import android.view.SurfaceView;
import androidx.graphics.utils.JniVisible;
import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import java.util.HashMap;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SurfaceControlCompat.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \f2\u00020\u0001:\u0005\f\r\u000e\u000f\u0010B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat;", "", "scImpl", "Landroidx/graphics/surface/SurfaceControlImpl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/surface/SurfaceControlImpl;)V", "getScImpl$libXCEngine_release", "()Landroidx/graphics/surface/SurfaceControlImpl;", "isValid", "", "release", "", "Companion", "Builder", "TransactionCompletedListener", "TransactionCommittedListener", "Transaction", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceControlCompat {
    public static final int BUFFER_TRANSFORM_IDENTITY = 0;
    public static final int BUFFER_TRANSFORM_MIRROR_HORIZONTAL = 1;
    public static final int BUFFER_TRANSFORM_MIRROR_VERTICAL = 2;
    public static final int BUFFER_TRANSFORM_ROTATE_180 = 3;
    public static final int BUFFER_TRANSFORM_ROTATE_270 = 7;
    public static final int BUFFER_TRANSFORM_ROTATE_90 = 4;
    public static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    public static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;
    public static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    public static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
    private final SurfaceControlImpl scImpl;

    /* JADX INFO: compiled from: SurfaceControlCompat.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "", "onTransactionCommitted", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    @JniVisible
    public interface TransactionCommittedListener {
        @JniVisible
        void onTransactionCommitted();
    }

    /* JADX INFO: compiled from: SurfaceControlCompat.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\ba\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat$TransactionCompletedListener;", "", "onTransactionCompleted", "", "transactionStats", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    @JniVisible
    public interface TransactionCompletedListener {
        @JniVisible
        void onTransactionCompleted(long transactionStats);
    }

    public SurfaceControlCompat(SurfaceControlImpl scImpl) {
        Intrinsics.checkNotNullParameter(scImpl, "scImpl");
        this.scImpl = scImpl;
    }

    /* JADX INFO: renamed from: getScImpl$libXCEngine_release, reason: from getter */
    public final SurfaceControlImpl getScImpl() {
        return this.scImpl;
    }

    public final boolean isValid() {
        return this.scImpl.isValid();
    }

    public final void release() {
        this.scImpl.release();
    }

    /* JADX INFO: compiled from: SurfaceControlCompat.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mBuilderImpl", "Landroidx/graphics/surface/SurfaceControlImpl$Builder;", "setParent", "surfaceView", "Landroid/view/SurfaceView;", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "setName", "name", "", "build", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final SurfaceControlImpl.Builder mBuilderImpl = INSTANCE.createImpl();

        public final Builder setParent(SurfaceView surfaceView) {
            Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
            this.mBuilderImpl.setParent(surfaceView);
            return this;
        }

        public final Builder setParent(SurfaceControlCompat surfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mBuilderImpl.setParent(surfaceControl);
            return this;
        }

        public final Builder setName(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.mBuilderImpl.setName(name);
            return this;
        }

        public final SurfaceControlCompat build() {
            return new SurfaceControlCompat(this.mBuilderImpl.build());
        }

        /* JADX INFO: compiled from: SurfaceControlCompat.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0080\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat$Builder$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createImpl", "Landroidx/graphics/surface/SurfaceControlImpl$Builder;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final SurfaceControlImpl.Builder createImpl() {
                if (Build.VERSION.SDK_INT >= 33) {
                    return SurfaceControlVerificationHelper.INSTANCE.createBuilderV33();
                }
                return SurfaceControlVerificationHelper.INSTANCE.createBuilderV29();
            }
        }
    }

    /* JADX INFO: compiled from: SurfaceControlCompat.kt */
    @Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\b\u0007\u0018\u0000 E2\u00060\u0001j\u0002`\u0002:\u0001EB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0016\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0011J\u0018\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0007J\u0018\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J>\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001eH\u0007J\u0016\u0010 \u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\bJ\u0018\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0007J\u0018\u0010'\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\b\u0010(\u001a\u0004\u0018\u00010)J\u0016\u0010*\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010+\u001a\u00020,J\u0018\u0010-\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\b\u0010.\u001a\u0004\u0018\u00010/J\u001e\u00100\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u00101\u001a\u00020,2\u0006\u00102\u001a\u00020,J\u001e\u00103\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u00104\u001a\u00020,2\u0006\u00105\u001a\u00020,J\u0018\u00106\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\b\b\u0001\u00107\u001a\u00020\bJ*\u00108\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u00109\u001a\u00020,2\b\b\u0001\u0010:\u001a\u00020\b2\b\b\u0001\u0010;\u001a\u00020\bJ\u000e\u0010<\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0007J$\u0010=\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\b\b\u0001\u0010>\u001a\u00020,2\b\b\u0001\u0010?\u001a\u00020,H\u0007J\u0016\u0010@\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010A\u001a\u00020\bJ\u0006\u0010B\u001a\u00020\u001fJ\b\u0010C\u001a\u00020\u001fH\u0016J\u0010\u0010D\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007R0\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat$Transaction;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mBufferTransforms", "Ljava/util/HashMap;", "Landroidx/graphics/surface/SurfaceControlCompat;", "", "Lkotlin/collections/HashMap;", "getMBufferTransforms$libXCEngine_release", "()Ljava/util/HashMap;", "mImpl", "Landroidx/graphics/surface/SurfaceControlImpl$Transaction;", "setOpaque", "surfaceControl", "isOpaque", "", "setVisibility", "visible", "reparent", "newParent", "attachedSurfaceControl", "Landroid/view/AttachedSurfaceControl;", "setBuffer", "buffer", "Landroid/hardware/HardwareBuffer;", "fence", "Landroidx/hardware/SyncFenceCompat;", "releaseCallback", "Lkotlin/Function1;", "", "setLayer", "z", "addTransactionCommittedListener", "executor", "Ljava/util/concurrent/Executor;", "listener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "setDamageRegion", "region", "Landroid/graphics/Region;", "setAlpha", "alpha", "", "setCrop", "crop", "Landroid/graphics/Rect;", "setPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setScale", "scaleX", "scaleY", "setBufferTransform", "transformation", "setFrameRate", "frameRate", "compatibility", "changeFrameRateStrategy", "clearFrameRate", "setExtendedRangeBrightness", "currentBufferRatio", "desiredRatio", "setDataSpace", "dataSpace", "commit", SplitLayout.TAG_CLOSE, "commitTransactionOnDraw", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Transaction implements AutoCloseable {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final HashMap<SurfaceControlCompat, Integer> mBufferTransforms = new HashMap<>();
        private final SurfaceControlImpl.Transaction mImpl = INSTANCE.createImpl();

        public final Transaction setBuffer(SurfaceControlCompat surfaceControl, HardwareBuffer hardwareBuffer) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            return setBuffer$default(this, surfaceControl, hardwareBuffer, null, null, 12, null);
        }

        public final Transaction setBuffer(SurfaceControlCompat surfaceControl, HardwareBuffer hardwareBuffer, SyncFenceCompat syncFenceCompat) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            return setBuffer$default(this, surfaceControl, hardwareBuffer, syncFenceCompat, null, 8, null);
        }

        public final HashMap<SurfaceControlCompat, Integer> getMBufferTransforms$libXCEngine_release() {
            return this.mBufferTransforms;
        }

        public final Transaction setOpaque(SurfaceControlCompat surfaceControl, boolean isOpaque) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setOpaque(surfaceControl.getScImpl(), isOpaque);
            return this;
        }

        public final Transaction setVisibility(SurfaceControlCompat surfaceControl, boolean visible) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setVisibility(surfaceControl.getScImpl(), visible);
            return this;
        }

        public final Transaction reparent(SurfaceControlCompat surfaceControl, SurfaceControlCompat newParent) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.reparent(surfaceControl.getScImpl(), newParent != null ? newParent.getScImpl() : null);
            return this;
        }

        public final Transaction reparent(SurfaceControlCompat surfaceControl, AttachedSurfaceControl attachedSurfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            Intrinsics.checkNotNullParameter(attachedSurfaceControl, "attachedSurfaceControl");
            this.mImpl.reparent(surfaceControl.getScImpl(), attachedSurfaceControl);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Transaction setBuffer$default(Transaction transaction, SurfaceControlCompat surfaceControlCompat, HardwareBuffer hardwareBuffer, SyncFenceCompat syncFenceCompat, Function1 function1, int i, Object obj) {
            if ((i & 4) != 0) {
                syncFenceCompat = null;
            }
            if ((i & 8) != 0) {
                function1 = null;
            }
            return transaction.setBuffer(surfaceControlCompat, hardwareBuffer, syncFenceCompat, function1);
        }

        public final Transaction setBuffer(SurfaceControlCompat surfaceControl, HardwareBuffer buffer, SyncFenceCompat fence, Function1<? super SyncFenceCompat, Unit> releaseCallback) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setBuffer(surfaceControl.getScImpl(), buffer, fence != null ? fence.getMImpl() : null, releaseCallback);
            return this;
        }

        public final Transaction setLayer(SurfaceControlCompat surfaceControl, int z) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setLayer(surfaceControl.getScImpl(), z);
            return this;
        }

        public final Transaction addTransactionCommittedListener(Executor executor, TransactionCommittedListener listener) {
            Intrinsics.checkNotNullParameter(executor, "executor");
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.mImpl.addTransactionCommittedListener(executor, listener);
            return this;
        }

        public final Transaction setDamageRegion(SurfaceControlCompat surfaceControl, Region region) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setDamageRegion(surfaceControl.getScImpl(), region);
            return this;
        }

        public final Transaction setAlpha(SurfaceControlCompat surfaceControl, float alpha) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setAlpha(surfaceControl.getScImpl(), alpha);
            return this;
        }

        public final Transaction setCrop(SurfaceControlCompat surfaceControl, Rect crop) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setCrop(surfaceControl.getScImpl(), crop);
            return this;
        }

        public final Transaction setPosition(SurfaceControlCompat surfaceControl, float x, float y) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setPosition(surfaceControl.getScImpl(), x, y);
            return this;
        }

        public final Transaction setScale(SurfaceControlCompat surfaceControl, float scaleX, float scaleY) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setScale(surfaceControl.getScImpl(), scaleX, scaleY);
            return this;
        }

        public final Transaction setBufferTransform(SurfaceControlCompat surfaceControl, @Companion.BufferTransform int transformation) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mBufferTransforms.put(surfaceControl, Integer.valueOf(transformation));
            this.mImpl.setBufferTransform(surfaceControl.getScImpl(), transformation);
            return this;
        }

        public final Transaction setFrameRate(SurfaceControlCompat surfaceControl, float frameRate, @Companion.FrameRateCompatibility int compatibility, @Companion.ChangeFrameRateStrategy int changeFrameRateStrategy) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (Build.VERSION.SDK_INT >= 30) {
                this.mImpl.setFrameRate(surfaceControl.getScImpl(), frameRate, compatibility, changeFrameRateStrategy);
            }
            return this;
        }

        public final Transaction clearFrameRate(SurfaceControlCompat surfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (Build.VERSION.SDK_INT >= 30) {
                this.mImpl.clearFrameRate(surfaceControl.getScImpl());
            }
            return this;
        }

        public final Transaction setExtendedRangeBrightness(SurfaceControlCompat surfaceControl, float currentBufferRatio, float desiredRatio) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setExtendedRangeBrightness(surfaceControl.getScImpl(), currentBufferRatio, desiredRatio);
            return this;
        }

        public final Transaction setDataSpace(SurfaceControlCompat surfaceControl, int dataSpace) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mImpl.setDataSpace(surfaceControl.getScImpl(), dataSpace);
            return this;
        }

        public final void commit() {
            this.mBufferTransforms.clear();
            this.mImpl.commit();
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mImpl.close();
        }

        public final void commitTransactionOnDraw(AttachedSurfaceControl attachedSurfaceControl) {
            Intrinsics.checkNotNullParameter(attachedSurfaceControl, "attachedSurfaceControl");
            this.mImpl.commitTransactionOnDraw(attachedSurfaceControl);
        }

        /* JADX INFO: compiled from: SurfaceControlCompat.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0080\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"Landroidx/graphics/surface/SurfaceControlCompat$Transaction$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createImpl", "Landroidx/graphics/surface/SurfaceControlImpl$Transaction;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final SurfaceControlImpl.Transaction createImpl() {
                if (Build.VERSION.SDK_INT >= 33) {
                    return SurfaceControlVerificationHelper.INSTANCE.createTransactionV33();
                }
                return SurfaceControlVerificationHelper.INSTANCE.createTransactionV29();
            }
        }
    }
}
