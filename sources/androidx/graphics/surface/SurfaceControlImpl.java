package androidx.graphics.surface;

import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.view.AttachedSurfaceControl;
import android.view.SurfaceView;
import androidx.hardware.SyncFenceCompat;
import androidx.hardware.SyncFenceImpl;
import com.miui.maml.folme.AnimatedProperty;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: SurfaceControlImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b`\u0018\u00002\u00020\u0001:\u0002\u0006\u0007J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/surface/SurfaceControlImpl;", "", "isValid", "", "release", "", "Builder", "Transaction", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface SurfaceControlImpl {

    /* JADX INFO: compiled from: SurfaceControlImpl.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H&J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/surface/SurfaceControlImpl$Builder;", "", "setParent", "surfaceView", "Landroid/view/SurfaceView;", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "setName", "name", "", "build", "Landroidx/graphics/surface/SurfaceControlImpl;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Builder {
        SurfaceControlImpl build();

        Builder setName(String name);

        Builder setParent(SurfaceView surfaceView);

        Builder setParent(SurfaceControlCompat surfaceControl);
    }

    boolean isValid();

    void release();

    /* JADX INFO: compiled from: SurfaceControlImpl.kt */
    @Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\bf\u0018\u00002\u00060\u0001j\u0002`\u0002J\u0018\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0007H&J\u001a\u0010\n\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H&J\u0018\u0010\n\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH'J>\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0016\b\u0002\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0014H&J\u0018\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0018\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH'J\u001a\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010 \u001a\u0004\u0018\u00010!H&J\u0018\u0010\"\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$H&J\u001a\u0010%\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010&\u001a\u0004\u0018\u00010'H&J \u0010(\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020$H&J \u0010+\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020$H&J\u001a\u0010.\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010/\u001a\u00020\u0019H&J \u00100\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00101\u001a\u00020$2\u0006\u00102\u001a\u00020$H'J\u0018\u00103\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u0019H&J(\u00105\u001a\u00020\u00002\u0006\u00106\u001a\u00020\u00052\u0006\u00107\u001a\u00020$2\u0006\u00108\u001a\u00020\u00192\u0006\u00109\u001a\u00020\u0019H'J\u0010\u0010:\u001a\u00020\u00002\u0006\u00106\u001a\u00020\u0005H'J\b\u0010;\u001a\u00020\u0016H&J\b\u0010<\u001a\u00020\u0016H&J\u0010\u0010=\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\rH'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006>À\u0006\u0003"}, d2 = {"Landroidx/graphics/surface/SurfaceControlImpl$Transaction;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "setOpaque", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlImpl;", "isOpaque", "", "setVisibility", "visible", "reparent", "newParent", "attachedSurfaceControl", "Landroid/view/AttachedSurfaceControl;", "setBuffer", "buffer", "Landroid/hardware/HardwareBuffer;", "fence", "Landroidx/hardware/SyncFenceImpl;", "releaseCallback", "Lkotlin/Function1;", "Landroidx/hardware/SyncFenceCompat;", "", "setLayer", "z", "", "addTransactionCommittedListener", "executor", "Ljava/util/concurrent/Executor;", "listener", "Landroidx/graphics/surface/SurfaceControlCompat$TransactionCommittedListener;", "setDamageRegion", "region", "Landroid/graphics/Region;", "setAlpha", "alpha", "", "setCrop", "crop", "Landroid/graphics/Rect;", "setPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setScale", "scaleX", "scaleY", "setBufferTransform", "transformation", "setExtendedRangeBrightness", "currentBufferRatio", "desiredRatio", "setDataSpace", "dataSpace", "setFrameRate", "scImpl", "frameRate", "compatibility", "changeFrameRateStrategy", "clearFrameRate", "commit", SplitLayout.TAG_CLOSE, "commitTransactionOnDraw", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Transaction extends AutoCloseable {
        Transaction addTransactionCommittedListener(Executor executor, SurfaceControlCompat.TransactionCommittedListener listener);

        Transaction clearFrameRate(SurfaceControlImpl scImpl);

        @Override // java.lang.AutoCloseable
        void close();

        void commit();

        void commitTransactionOnDraw(AttachedSurfaceControl attachedSurfaceControl);

        Transaction reparent(SurfaceControlImpl surfaceControl, AttachedSurfaceControl attachedSurfaceControl);

        Transaction reparent(SurfaceControlImpl surfaceControl, SurfaceControlImpl newParent);

        Transaction setAlpha(SurfaceControlImpl surfaceControl, float alpha);

        Transaction setBuffer(SurfaceControlImpl surfaceControl, HardwareBuffer buffer, SyncFenceImpl fence, Function1<? super SyncFenceCompat, Unit> releaseCallback);

        Transaction setBufferTransform(SurfaceControlImpl surfaceControl, @SurfaceControlCompat.Companion.BufferTransform int transformation);

        Transaction setCrop(SurfaceControlImpl surfaceControl, Rect crop);

        Transaction setDamageRegion(SurfaceControlImpl surfaceControl, Region region);

        Transaction setDataSpace(SurfaceControlImpl surfaceControl, int dataSpace);

        Transaction setExtendedRangeBrightness(SurfaceControlImpl surfaceControl, float currentBufferRatio, float desiredRatio);

        Transaction setFrameRate(SurfaceControlImpl scImpl, float frameRate, int compatibility, int changeFrameRateStrategy);

        Transaction setLayer(SurfaceControlImpl surfaceControl, int z);

        Transaction setOpaque(SurfaceControlImpl surfaceControl, boolean isOpaque);

        Transaction setPosition(SurfaceControlImpl surfaceControl, float x, float y);

        Transaction setScale(SurfaceControlImpl surfaceControl, float scaleX, float scaleY);

        Transaction setVisibility(SurfaceControlImpl surfaceControl, boolean visible);

        /* JADX INFO: compiled from: SurfaceControlImpl.kt */
        @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
        public static final class DefaultImpls {
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ Transaction setBuffer$default(Transaction transaction, SurfaceControlImpl surfaceControlImpl, HardwareBuffer hardwareBuffer, SyncFenceImpl syncFenceImpl, Function1 function1, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setBuffer");
            }
            if ((i & 4) != 0) {
                syncFenceImpl = null;
            }
            if ((i & 8) != 0) {
                function1 = null;
            }
            return transaction.setBuffer(surfaceControlImpl, hardwareBuffer, syncFenceImpl, function1);
        }
    }
}
