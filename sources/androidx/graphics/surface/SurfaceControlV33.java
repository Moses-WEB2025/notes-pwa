package androidx.graphics.surface;

import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;
import android.os.Build;
import android.view.AttachedSurfaceControl;
import android.view.SurfaceControl;
import android.view.SurfaceView;
import androidx.hardware.SyncFenceCompat;
import androidx.hardware.SyncFenceImpl;
import androidx.hardware.SyncFenceV33;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SurfaceControlV33.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0001\u0018\u0000 \u000e2\u00020\u0001:\u0003\f\r\u000eB\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV33;", "Landroidx/graphics/surface/SurfaceControlImpl;", "surfaceControl", "Landroid/view/SurfaceControl;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/SurfaceControl;)V", "getSurfaceControl$libXCEngine_release", "()Landroid/view/SurfaceControl;", "isValid", "", "release", "", "Builder", "Transaction", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceControlV33 implements SurfaceControlImpl {
    private static final Companion Companion = new Companion(null);
    private final SurfaceControl surfaceControl;

    public SurfaceControlV33(SurfaceControl surfaceControl) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        this.surfaceControl = surfaceControl;
    }

    /* JADX INFO: renamed from: getSurfaceControl$libXCEngine_release, reason: from getter */
    public final SurfaceControl getSurfaceControl() {
        return this.surfaceControl;
    }

    @Override // androidx.graphics.surface.SurfaceControlImpl
    public boolean isValid() {
        return this.surfaceControl.isValid();
    }

    @Override // androidx.graphics.surface.SurfaceControlImpl
    public void release() {
        this.surfaceControl.release();
    }

    /* JADX INFO: compiled from: SurfaceControlV33.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV33$Builder;", "Landroidx/graphics/surface/SurfaceControlImpl$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "builder", "Landroid/view/SurfaceControl$Builder;", "setParent", "surfaceView", "Landroid/view/SurfaceView;", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "setName", "name", "", "build", "Landroidx/graphics/surface/SurfaceControlImpl;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder implements SurfaceControlImpl.Builder {
        private final SurfaceControl.Builder builder = new SurfaceControl.Builder();

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl.Builder setParent(SurfaceView surfaceView) {
            Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
            this.builder.setParent(surfaceView.getSurfaceControl());
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl.Builder setParent(SurfaceControlCompat surfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.builder.setParent(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl.getScImpl()));
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public Builder setName(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.builder.setName(name);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl build() {
            SurfaceControl surfaceControlBuild = this.builder.build();
            Intrinsics.checkNotNullExpressionValue(surfaceControlBuild, "build(...)");
            return new SurfaceControlV33(surfaceControlBuild);
        }
    }

    /* JADX INFO: compiled from: SurfaceControlV33.kt */
    @Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\nH\u0016J:\u0010\r\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0013H\u0016J\u0018\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u001a\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\bH\u0016J\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\u001a\u0010\"\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0018\u0010%\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010&\u001a\u00020'H\u0016J\u001a\u0010(\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J \u0010+\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020'H\u0016J \u0010.\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010/\u001a\u00020'2\u0006\u00100\u001a\u00020'H\u0016J\u001a\u00101\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0001\u00102\u001a\u00020\u0018H\u0016J \u00103\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00104\u001a\u00020'2\u0006\u00105\u001a\u00020'H\u0017J\u0018\u00106\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00107\u001a\u00020\u0018H\u0016J(\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020\b2\u0006\u0010:\u001a\u00020'2\u0006\u0010;\u001a\u00020\u00182\u0006\u0010<\u001a\u00020\u0018H\u0016J\u0010\u0010=\u001a\u00020\u00012\u0006\u00109\u001a\u00020\bH\u0016J\b\u0010>\u001a\u00020\u0015H\u0016J\b\u0010?\u001a\u00020\u0015H\u0016J\u0010\u0010@\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\f\u0010A\u001a\u00020B*\u00020\u0011H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV33$Transaction;", "Landroidx/graphics/surface/SurfaceControlImpl$Transaction;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mTransaction", "Landroid/view/SurfaceControl$Transaction;", "setOpaque", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlImpl;", "isOpaque", "", "setVisibility", "visible", "setBuffer", "buffer", "Landroid/hardware/HardwareBuffer;", "fence", "Landroidx/hardware/SyncFenceImpl;", "releaseCallback", "Lkotlin/Function1;", "Landroidx/hardware/SyncFenceCompat;", "", "setLayer", "z", "", "reparent", "newParent", "attachedSurfaceControl", "Landroid/view/AttachedSurfaceControl;", "addTransactionCommittedListener", "executor", "Ljava/util/concurrent/Executor;", "listener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "setDamageRegion", "region", "Landroid/graphics/Region;", "setAlpha", "alpha", "", "setCrop", "crop", "Landroid/graphics/Rect;", "setPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setScale", "scaleX", "scaleY", "setBufferTransform", "transformation", "setExtendedRangeBrightness", "currentBufferRatio", "desiredRatio", "setDataSpace", "dataSpace", "setFrameRate", "scImpl", "frameRate", "compatibility", "changeFrameRateStrategy", "clearFrameRate", "commit", SplitLayout.TAG_CLOSE, "commitTransactionOnDraw", "asSyncFence", "Landroid/hardware/SyncFence;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Transaction implements SurfaceControlImpl.Transaction {
        private final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public /* bridge */ /* synthetic */ SurfaceControlImpl.Transaction setBuffer(SurfaceControlImpl surfaceControlImpl, HardwareBuffer hardwareBuffer, SyncFenceImpl syncFenceImpl, Function1 function1) {
            return setBuffer(surfaceControlImpl, hardwareBuffer, syncFenceImpl, (Function1<? super SyncFenceCompat, Unit>) function1);
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setOpaque(SurfaceControlImpl surfaceControl, boolean isOpaque) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setOpaque(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), isOpaque);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setVisibility(SurfaceControlImpl surfaceControl, boolean visible) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setVisibility(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), visible);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public Transaction setBuffer(SurfaceControlImpl surfaceControl, HardwareBuffer buffer, SyncFenceImpl fence, final Function1<? super SyncFenceCompat, Unit> releaseCallback) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setBuffer(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), buffer, fence != null ? asSyncFence(fence) : null, new Consumer() { // from class: androidx.graphics.surface.SurfaceControlV33$Transaction$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SurfaceControlV33.Transaction.setBuffer$lambda$0(releaseCallback, (SyncFence) obj);
                }
            });
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setBuffer$lambda$0(Function1 function1, SyncFence syncFence) {
            if (function1 != null) {
                Intrinsics.checkNotNull(syncFence);
                function1.invoke(new SyncFenceCompat(syncFence));
            }
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setLayer(SurfaceControlImpl surfaceControl, int z) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setLayer(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), z);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public Transaction reparent(SurfaceControlImpl surfaceControl, SurfaceControlImpl newParent) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.reparent(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), newParent != null ? SurfaceControlV33.Companion.asFrameworkSurfaceControl(newParent) : null);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction reparent(SurfaceControlImpl surfaceControl, AttachedSurfaceControl attachedSurfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            Intrinsics.checkNotNullParameter(attachedSurfaceControl, "attachedSurfaceControl");
            SurfaceControl.Transaction transactionBuildReparentTransaction = attachedSurfaceControl.buildReparentTransaction(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl));
            if (transactionBuildReparentTransaction != null) {
                this.mTransaction.merge(transactionBuildReparentTransaction);
            }
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction addTransactionCommittedListener(Executor executor, final SurfaceControlCompat.TransactionCommittedListener listener) {
            Intrinsics.checkNotNullParameter(executor, "executor");
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.mTransaction.addTransactionCommittedListener(executor, new SurfaceControl.TransactionCommittedListener() { // from class: androidx.graphics.surface.SurfaceControlV33$Transaction$$ExternalSyntheticLambda1
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    listener.onTransactionCommitted();
                }
            });
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setDamageRegion(SurfaceControlImpl surfaceControl, Region region) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setDamageRegion(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), region);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setAlpha(SurfaceControlImpl surfaceControl, float alpha) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setAlpha(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), alpha);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setCrop(SurfaceControlImpl surfaceControl, Rect crop) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setCrop(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), crop);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setPosition(SurfaceControlImpl surfaceControl, float x, float y) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setPosition(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), x, y);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setScale(SurfaceControlImpl surfaceControl, float scaleX, float scaleY) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setScale(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), scaleX, scaleY);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setBufferTransform(SurfaceControlImpl surfaceControl, @SurfaceControlCompat.Companion.BufferTransform int transformation) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.mTransaction.setBufferTransform(SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), transformation);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setExtendedRangeBrightness(SurfaceControlImpl surfaceControl, float currentBufferRatio, float desiredRatio) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (Build.VERSION.SDK_INT >= 34) {
                SurfaceControlTransactionVerificationHelperV34.INSTANCE.setExtendedRangeBrightness(this.mTransaction, SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), currentBufferRatio, desiredRatio);
                return this;
            }
            throw new UnsupportedOperationException("Configuring the extended range brightness is only available on Android U+");
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setDataSpace(SurfaceControlImpl surfaceControl, int dataSpace) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (Build.VERSION.SDK_INT >= 33) {
                SurfaceControlTransactionVerificationHelperV33.INSTANCE.setDataSpace(this.mTransaction, SurfaceControlV33.Companion.asFrameworkSurfaceControl(surfaceControl), dataSpace);
                return this;
            }
            throw new UnsupportedOperationException("Configuring the data space is only available on Android T+");
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public Transaction setFrameRate(SurfaceControlImpl scImpl, float frameRate, int compatibility, int changeFrameRateStrategy) {
            Intrinsics.checkNotNullParameter(scImpl, "scImpl");
            if (Build.VERSION.SDK_INT >= 31) {
                SurfaceControlVerificationHelperV31.INSTANCE.setFrameRate(this.mTransaction, SurfaceControlV33.Companion.asFrameworkSurfaceControl(scImpl), frameRate, compatibility, changeFrameRateStrategy);
                return this;
            }
            if (Build.VERSION.SDK_INT >= 30) {
                SurfaceControlVerificationHelperV30.INSTANCE.setFrameRate(this.mTransaction, SurfaceControlV33.Companion.asFrameworkSurfaceControl(scImpl), frameRate, compatibility);
            }
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction clearFrameRate(SurfaceControlImpl scImpl) {
            Intrinsics.checkNotNullParameter(scImpl, "scImpl");
            if (Build.VERSION.SDK_INT >= 34) {
                SurfaceControlVerificationHelperV34.INSTANCE.clearFrameRate(this.mTransaction, SurfaceControlV33.Companion.asFrameworkSurfaceControl(scImpl));
            }
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public void commit() {
            this.mTransaction.apply();
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction, java.lang.AutoCloseable
        public void close() {
            this.mTransaction.close();
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public void commitTransactionOnDraw(AttachedSurfaceControl attachedSurfaceControl) {
            Intrinsics.checkNotNullParameter(attachedSurfaceControl, "attachedSurfaceControl");
            attachedSurfaceControl.applyTransactionOnDraw(this.mTransaction);
        }

        private final SyncFence asSyncFence(SyncFenceImpl syncFenceImpl) {
            if (syncFenceImpl instanceof SyncFenceV33) {
                return ((SyncFenceV33) syncFenceImpl).getMSyncFence();
            }
            throw new IllegalArgumentException("Expected SyncFenceCompat implementation for API level 33");
        }
    }

    /* JADX INFO: compiled from: SurfaceControlV33.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0006¨\u0006\u0007"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV33$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "asFrameworkSurfaceControl", "Landroid/view/SurfaceControl;", "Landroidx/graphics/surface/SurfaceControlImpl;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SurfaceControl asFrameworkSurfaceControl(SurfaceControlImpl surfaceControlImpl) {
            Intrinsics.checkNotNullParameter(surfaceControlImpl, "<this>");
            if (surfaceControlImpl instanceof SurfaceControlV33) {
                return ((SurfaceControlV33) surfaceControlImpl).getSurfaceControl();
            }
            throw new IllegalArgumentException("Parent implementation is not for Android T");
        }
    }
}
