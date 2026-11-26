package androidx.graphics.surface;

import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.os.Build;
import android.view.AttachedSurfaceControl;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.hardware.SyncFenceCompat;
import androidx.hardware.SyncFenceImpl;
import androidx.hardware.SyncFenceV19;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SurfaceControlV29.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0001\u0018\u0000 \u00182\u00020\u0001:\u0003\u0016\u0017\u0018B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J6\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\tj\u0004\u0018\u0001`\f2\u001a\u0010\r\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\tj\u0004\u0018\u0001`\fJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u000bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\u000e\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\tj\u0004\u0018\u0001`\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV29;", "Landroidx/graphics/surface/SurfaceControlImpl;", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlWrapper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/surface/SurfaceControlWrapper;)V", "getSurfaceControl$libXCEngine_release", "()Landroidx/graphics/surface/SurfaceControlWrapper;", "updateReleaseCallback", "Lkotlin/Function1;", "Landroidx/hardware/SyncFenceCompat;", "", "Landroidx/graphics/surface/ReleaseCallback;", CallMethod.ARG_CALLBACK, "releaseCallback", "getPreviousReleaseFd", "", "transactionStats", "", "isValid", "", "release", "Builder", "Transaction", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SurfaceControlV29 implements SurfaceControlImpl {
    private static final Companion Companion = new Companion(null);
    private static final SyncFenceCompat DefaultSyncFence;
    private static final HardwareBuffer PlaceholderBuffer;
    private Function1<? super SyncFenceCompat, Unit> releaseCallback;
    private final SurfaceControlWrapper surfaceControl;

    public SurfaceControlV29(SurfaceControlWrapper surfaceControl) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        this.surfaceControl = surfaceControl;
    }

    /* JADX INFO: renamed from: getSurfaceControl$libXCEngine_release, reason: from getter */
    public final SurfaceControlWrapper getSurfaceControl() {
        return this.surfaceControl;
    }

    public final Function1<SyncFenceCompat, Unit> updateReleaseCallback(Function1<? super SyncFenceCompat, Unit> callback) {
        Function1 function1;
        synchronized (this) {
            function1 = this.releaseCallback;
            this.releaseCallback = callback;
        }
        return function1;
    }

    public final int getPreviousReleaseFd(long transactionStats) {
        return JniBindings.INSTANCE.nGetPreviousReleaseFenceFd(this.surfaceControl.getMNativeSurfaceControl(), transactionStats);
    }

    @Override // androidx.graphics.surface.SurfaceControlImpl
    public boolean isValid() {
        return this.surfaceControl.isValid();
    }

    @Override // androidx.graphics.surface.SurfaceControlImpl
    public void release() {
        this.surfaceControl.release();
    }

    /* JADX INFO: compiled from: SurfaceControlV29.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV29$Builder;", "Landroidx/graphics/surface/SurfaceControlImpl$Builder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "builder", "Landroidx/graphics/surface/SurfaceControlWrapper$Builder;", "setParent", "surfaceView", "Landroid/view/SurfaceView;", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "setName", "name", "", "build", "Landroidx/graphics/surface/SurfaceControlImpl;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder implements SurfaceControlImpl.Builder {
        private SurfaceControlWrapper.Builder builder = new SurfaceControlWrapper.Builder();

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl.Builder setParent(SurfaceView surfaceView) {
            Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
            SurfaceControlWrapper.Builder builder = this.builder;
            Surface surface = surfaceView.getHolder().getSurface();
            Intrinsics.checkNotNullExpressionValue(surface, "getSurface(...)");
            builder.setParent(surface);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl.Builder setParent(SurfaceControlCompat surfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.builder.setParent(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl.getScImpl()));
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl.Builder setName(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.builder.setDebugName(name);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Builder
        public SurfaceControlImpl build() {
            return new SurfaceControlV29(this.builder.build());
        }
    }

    /* JADX INFO: compiled from: SurfaceControlV29.kt */
    @Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001MB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u0018\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001a\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\bH\u0016J@\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u001a\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u001dj\u0004\u0018\u0001`\u001fH\u0016J\u0018\u0010 \u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010!\u001a\u00020\fH\u0016J\u0018\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0017J\u000e\u0010'\u001a\u00020\u00012\u0006\u0010%\u001a\u00020(J\u001a\u0010)\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\u0018\u0010,\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010-\u001a\u00020\u0014H\u0016J\u0018\u0010.\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010/\u001a\u000200H\u0016J\u001a\u00101\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\b\u00102\u001a\u0004\u0018\u000103H\u0017J \u00104\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u00105\u001a\u0002002\u0006\u00106\u001a\u000200H\u0017J \u00107\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u00108\u001a\u0002002\u0006\u00109\u001a\u000200H\u0017J\u001a\u0010:\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\b2\b\b\u0001\u0010;\u001a\u00020\fH\u0016J \u0010<\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010=\u001a\u0002002\u0006\u0010>\u001a\u000200H\u0017J\u0018\u0010?\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010@\u001a\u00020\fH\u0017J(\u0010A\u001a\u00020\u00002\u0006\u0010B\u001a\u00020\b2\u0006\u0010C\u001a\u0002002\u0006\u0010D\u001a\u00020\f2\u0006\u0010E\u001a\u00020\fH\u0016J\u0010\u0010F\u001a\u00020\u00012\u0006\u0010B\u001a\u00020\bH\u0016J\b\u0010G\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010H\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020\u000e2\u0006\u0010H\u001a\u00020IH\u0016J\f\u0010K\u001a\u00020L*\u00020\u001bH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007j\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u0007j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV29$Transaction;", "Landroidx/graphics/surface/SurfaceControlImpl$Transaction;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "transaction", "Landroidx/graphics/surface/SurfaceControlWrapper$Transaction;", "uncommittedBufferCallbackMap", "Ljava/util/HashMap;", "Landroidx/graphics/surface/SurfaceControlImpl;", "Landroidx/graphics/surface/SurfaceControlV29$Transaction$BufferData;", "Lkotlin/collections/HashMap;", "pendingSetTransformCalls", "", "commit", "", "updateReleaseCallbacks", "setPendingBufferTransform", "setVisibility", "surfaceControl", "visible", "", "reparent", "newParent", "setBuffer", "buffer", "Landroid/hardware/HardwareBuffer;", "fence", "Landroidx/hardware/SyncFenceImpl;", "releaseCallback", "Lkotlin/Function1;", "Landroidx/hardware/SyncFenceCompat;", "Landroidx/graphics/surface/ReleaseCallback;", "setLayer", "z", "addTransactionCommittedListener", "executor", "Ljava/util/concurrent/Executor;", "listener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "addTransactionCompletedListener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCompletedListener;", "setDamageRegion", "region", "Landroid/graphics/Region;", "setOpaque", "isOpaque", "setAlpha", "alpha", "", "setCrop", "crop", "Landroid/graphics/Rect;", "setPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setScale", "scaleX", "scaleY", "setBufferTransform", "transformation", "setExtendedRangeBrightness", "currentBufferRatio", "desiredRatio", "setDataSpace", "dataSpace", "setFrameRate", "scImpl", "frameRate", "compatibility", "changeFrameRateStrategy", "clearFrameRate", SplitLayout.TAG_CLOSE, "attachedSurfaceControl", "Landroid/view/AttachedSurfaceControl;", "commitTransactionOnDraw", "asSyncFenceCompat", "Landroidx/hardware/SyncFenceV19;", "BufferData", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Transaction implements SurfaceControlImpl.Transaction {
        private final SurfaceControlWrapper.Transaction transaction = new SurfaceControlWrapper.Transaction();
        private final HashMap<SurfaceControlImpl, BufferData> uncommittedBufferCallbackMap = new HashMap<>();
        private final HashMap<SurfaceControlImpl, Integer> pendingSetTransformCalls = new HashMap<>();

        /* JADX INFO: compiled from: SurfaceControlV29.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u001a\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006j\u0004\u0018\u0001`\t¢\u0006\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR%\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006j\u0004\u0018\u0001`\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV29$Transaction$BufferData;", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "releaseCallback", "Lkotlin/Function1;", "Landroidx/hardware/SyncFenceCompat;", "", "Landroidx/graphics/surface/ReleaseCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IILkotlin/jvm/functions/Function1;)V", "getWidth", "()I", "getHeight", "getReleaseCallback", "()Lkotlin/jvm/functions/Function1;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        private static final class BufferData {
            private final int height;
            private final Function1<SyncFenceCompat, Unit> releaseCallback;
            private final int width;

            /* JADX WARN: Multi-variable type inference failed */
            public BufferData(int i, int i2, Function1<? super SyncFenceCompat, Unit> function1) {
                this.width = i;
                this.height = i2;
                this.releaseCallback = function1;
            }

            public final int getWidth() {
                return this.width;
            }

            public final int getHeight() {
                return this.height;
            }

            public final Function1<SyncFenceCompat, Unit> getReleaseCallback() {
                return this.releaseCallback;
            }
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public void commit() {
            setPendingBufferTransform();
            updateReleaseCallbacks();
            this.uncommittedBufferCallbackMap.clear();
            this.pendingSetTransformCalls.clear();
            this.transaction.commit();
        }

        private final void updateReleaseCallbacks() {
            BufferData bufferData;
            final ArrayList arrayList = new ArrayList();
            for (SurfaceControlImpl surfaceControlImpl : this.uncommittedBufferCallbackMap.keySet()) {
                Intrinsics.checkNotNullExpressionValue(surfaceControlImpl, "next(...)");
                SurfaceControlImpl surfaceControlImpl2 = surfaceControlImpl;
                SurfaceControlV29 surfaceControlV29 = surfaceControlImpl2 instanceof SurfaceControlV29 ? (SurfaceControlV29) surfaceControlImpl2 : null;
                if (surfaceControlV29 != null && (bufferData = this.uncommittedBufferCallbackMap.get(surfaceControlImpl2)) != null) {
                    arrayList.add(new SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry(surfaceControlV29, bufferData.getReleaseCallback()));
                }
            }
            if (arrayList.size() > 0) {
                addTransactionCompletedListener(new SurfaceControlCompat.TransactionCompletedListener() { // from class: androidx.graphics.surface.SurfaceControlV29$Transaction$updateReleaseCallbacks$callbackListener$1
                    @Override // androidx.graphics.surface.SurfaceControlCompat.TransactionCompletedListener
                    public void onTransactionCompleted(long transactionStats) {
                        for (SurfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry : arrayList) {
                            SurfaceControlV29 surfaceControl = surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.getSurfaceControl();
                            Function1<SyncFenceCompat, Unit> function1UpdateReleaseCallback = surfaceControl.updateReleaseCallback(surfaceControlV29$Transaction$updateReleaseCallbacks$CallbackEntry.getCallback());
                            if (function1UpdateReleaseCallback != null) {
                                function1UpdateReleaseCallback.invoke(new SyncFenceCompat(new SyncFenceV19(surfaceControl.getPreviousReleaseFd(transactionStats))));
                            }
                        }
                        arrayList.clear();
                    }
                });
            }
        }

        private final void setPendingBufferTransform() {
            int height;
            int width;
            for (SurfaceControlImpl surfaceControlImpl : this.pendingSetTransformCalls.keySet()) {
                Intrinsics.checkNotNullExpressionValue(surfaceControlImpl, "next(...)");
                SurfaceControlImpl surfaceControlImpl2 = surfaceControlImpl;
                BufferData bufferData = this.uncommittedBufferCallbackMap.get(surfaceControlImpl2);
                if (bufferData != null) {
                    Integer orDefault = this.pendingSetTransformCalls.getOrDefault(surfaceControlImpl2, -1);
                    Intrinsics.checkNotNullExpressionValue(orDefault, "getOrDefault(...)");
                    int iIntValue = orDefault.intValue();
                    if (iIntValue != -1) {
                        if (iIntValue == 4 || iIntValue == 7) {
                            height = bufferData.getHeight();
                            width = bufferData.getWidth();
                        } else {
                            height = bufferData.getWidth();
                            width = bufferData.getHeight();
                        }
                        this.transaction.setGeometry(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControlImpl2), bufferData.getWidth(), bufferData.getHeight(), height, width, iIntValue);
                    }
                }
            }
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setVisibility(SurfaceControlImpl surfaceControl, boolean visible) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setVisibility(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), visible);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction reparent(SurfaceControlImpl surfaceControl, SurfaceControlImpl newParent) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.reparent(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), newParent != null ? SurfaceControlV29.Companion.asWrapperSurfaceControl(newParent) : null);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setBuffer(SurfaceControlImpl surfaceControl, HardwareBuffer buffer, SyncFenceImpl fence, Function1<? super SyncFenceCompat, Unit> releaseCallback) {
            BufferData bufferDataRemove;
            Function1<SyncFenceCompat, Unit> releaseCallback2;
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (buffer != null) {
                bufferDataRemove = this.uncommittedBufferCallbackMap.put(surfaceControl, new BufferData(buffer.getWidth(), buffer.getHeight(), releaseCallback));
            } else {
                bufferDataRemove = this.uncommittedBufferCallbackMap.remove(surfaceControl);
            }
            if (bufferDataRemove != null && (releaseCallback2 = bufferDataRemove.getReleaseCallback()) != null) {
                releaseCallback2.invoke(SurfaceControlV29.Companion.getDefaultSyncFence());
            }
            if (buffer == null) {
                buffer = SurfaceControlV29.Companion.getPlaceholderBuffer();
            }
            HardwareBuffer hardwareBuffer = buffer;
            if (fence == null) {
                SurfaceControlWrapper.Transaction.setBuffer$default(this.transaction, SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), hardwareBuffer, null, 4, null);
            } else {
                this.transaction.setBuffer(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), hardwareBuffer, asSyncFenceCompat(fence));
            }
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setLayer(SurfaceControlImpl surfaceControl, int z) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setLayer(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), z);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction addTransactionCommittedListener(Executor executor, SurfaceControlCompat.TransactionCommittedListener listener) {
            Intrinsics.checkNotNullParameter(executor, "executor");
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.transaction.addTransactionCommittedListener(executor, listener);
            return this;
        }

        public final SurfaceControlImpl.Transaction addTransactionCompletedListener(SurfaceControlCompat.TransactionCompletedListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.transaction.addTransactionCompletedListener$libXCEngine_release(listener);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setDamageRegion(SurfaceControlImpl surfaceControl, Region region) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setDamageRegion(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), region);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setOpaque(SurfaceControlImpl surfaceControl, boolean isOpaque) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setOpaque(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), isOpaque);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setAlpha(SurfaceControlImpl surfaceControl, float alpha) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setAlpha(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), alpha);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setCrop(SurfaceControlImpl surfaceControl, Rect crop) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setCrop(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), crop);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setPosition(SurfaceControlImpl surfaceControl, float x, float y) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setPosition(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), x, y);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setScale(SurfaceControlImpl surfaceControl, float scaleX, float scaleY) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setScale(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), scaleX, scaleY);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public Transaction setBufferTransform(SurfaceControlImpl surfaceControl, @SurfaceControlCompat.Companion.BufferTransform int transformation) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            if (Build.VERSION.SDK_INT >= 31) {
                this.transaction.setBufferTransform(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), transformation);
                return this;
            }
            this.pendingSetTransformCalls.put(surfaceControl, Integer.valueOf(transformation));
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setExtendedRangeBrightness(SurfaceControlImpl surfaceControl, float currentBufferRatio, float desiredRatio) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            throw new UnsupportedOperationException("Configuring the extended range brightness is only available on Android U+");
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction setDataSpace(SurfaceControlImpl surfaceControl, int dataSpace) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.transaction.setDataSpace(SurfaceControlV29.Companion.asWrapperSurfaceControl(surfaceControl), dataSpace);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public Transaction setFrameRate(SurfaceControlImpl scImpl, float frameRate, int compatibility, int changeFrameRateStrategy) {
            Intrinsics.checkNotNullParameter(scImpl, "scImpl");
            this.transaction.setFrameRate(SurfaceControlV29.Companion.asWrapperSurfaceControl(scImpl), frameRate, compatibility, changeFrameRateStrategy);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction clearFrameRate(SurfaceControlImpl scImpl) {
            Intrinsics.checkNotNullParameter(scImpl, "scImpl");
            setFrameRate(scImpl, 0.0f, 0, 0);
            return this;
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction, java.lang.AutoCloseable
        public void close() {
            this.transaction.close();
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public SurfaceControlImpl.Transaction reparent(SurfaceControlImpl surfaceControl, AttachedSurfaceControl attachedSurfaceControl) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            Intrinsics.checkNotNullParameter(attachedSurfaceControl, "attachedSurfaceControl");
            throw new UnsupportedOperationException("Reparenting to an AttachedSurfaceControl is only available on Android T+.");
        }

        @Override // androidx.graphics.surface.SurfaceControlImpl.Transaction
        public void commitTransactionOnDraw(AttachedSurfaceControl attachedSurfaceControl) {
            Intrinsics.checkNotNullParameter(attachedSurfaceControl, "attachedSurfaceControl");
            throw new UnsupportedOperationException("Committing transactions synchronously with the draw pass of an AttachedSurfaceControl is only available on Android T+.");
        }

        private final SyncFenceV19 asSyncFenceCompat(SyncFenceImpl syncFenceImpl) {
            if (syncFenceImpl instanceof SyncFenceV19) {
                return (SyncFenceV19) syncFenceImpl;
            }
            throw new IllegalArgumentException("Expected SyncFenceCompat implementation for API level 19");
        }
    }

    /* JADX INFO: compiled from: SurfaceControlV29.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\n\u0010\f\u001a\u00020\r*\u00020\u000eR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Landroidx/graphics/surface/SurfaceControlV29$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PlaceholderBuffer", "Landroid/hardware/HardwareBuffer;", "getPlaceholderBuffer", "()Landroid/hardware/HardwareBuffer;", "DefaultSyncFence", "Landroidx/hardware/SyncFenceCompat;", "getDefaultSyncFence", "()Landroidx/hardware/SyncFenceCompat;", "asWrapperSurfaceControl", "Landroidx/graphics/surface/SurfaceControlWrapper;", "Landroidx/graphics/surface/SurfaceControlImpl;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HardwareBuffer getPlaceholderBuffer() {
            return SurfaceControlV29.PlaceholderBuffer;
        }

        public final SyncFenceCompat getDefaultSyncFence() {
            return SurfaceControlV29.DefaultSyncFence;
        }

        public final SurfaceControlWrapper asWrapperSurfaceControl(SurfaceControlImpl surfaceControlImpl) {
            Intrinsics.checkNotNullParameter(surfaceControlImpl, "<this>");
            if (surfaceControlImpl instanceof SurfaceControlV29) {
                return ((SurfaceControlV29) surfaceControlImpl).getSurfaceControl();
            }
            throw new IllegalArgumentException("Parent implementation is only for Android T+.");
        }
    }

    static {
        HardwareBuffer hardwareBufferCreate = HardwareBuffer.create(1, 1, 1, 1, 2816L);
        Intrinsics.checkNotNullExpressionValue(hardwareBufferCreate, "create(...)");
        PlaceholderBuffer = hardwareBufferCreate;
        DefaultSyncFence = new SyncFenceCompat(new SyncFenceV19(-1));
    }
}
