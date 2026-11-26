package androidx.graphics.opengl;

import android.hardware.HardwareBuffer;
import androidx.graphics.opengl.egl.EGLSpec;
import androidx.hardware.BufferPool;
import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: FrameBufferPool.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001:\u0001!B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0004\b\t\u0010\nJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0003J\u001a\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00122\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u0006\u0010 \u001a\u00020\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u001d\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001f¨\u0006\""}, d2 = {"Landroidx/graphics/opengl/FrameBufferPool;", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "format", "usage", "", "maxPoolSize", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIIJI)V", "mPool", "Landroidx/hardware/BufferPool;", "Landroidx/graphics/opengl/FrameBufferPool$FrameBufferProvider;", "allocationCount", "getAllocationCount", "()I", "obtain", "Landroidx/graphics/opengl/FrameBuffer;", "eglSpec", "Landroidx/graphics/opengl/egl/EGLSpec;", "updateSize", "", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "release", "frameBuffer", "fence", "Landroidx/hardware/SyncFenceCompat;", "isClosed", "", "()Z", SplitLayout.TAG_CLOSE, "FrameBufferProvider", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrameBufferPool {
    private final int format;
    private int height;
    private final BufferPool<FrameBufferProvider> mPool;
    private final int maxPoolSize;
    private final long usage;
    private int width;

    public FrameBufferPool(int i, int i2, int i3, long j, int i4) {
        this.width = i;
        this.height = i2;
        this.format = i3;
        this.usage = j;
        this.maxPoolSize = i4;
        this.mPool = new BufferPool<>(i4);
    }

    /* JADX INFO: compiled from: FrameBufferPool.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Landroidx/graphics/opengl/FrameBufferPool$FrameBufferProvider;", "Landroidx/hardware/BufferPool$BufferProvider;", "frameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/opengl/FrameBuffer;)V", "getFrameBuffer", "()Landroidx/graphics/opengl/FrameBuffer;", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "getHardwareBuffer", "()Landroid/hardware/HardwareBuffer;", "release", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class FrameBufferProvider implements BufferPool.BufferProvider {
        private final FrameBuffer frameBuffer;

        public FrameBufferProvider(FrameBuffer frameBuffer) {
            Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
            this.frameBuffer = frameBuffer;
        }

        public final FrameBuffer getFrameBuffer() {
            return this.frameBuffer;
        }

        @Override // androidx.hardware.BufferPool.BufferProvider
        public HardwareBuffer getHardwareBuffer() {
            return this.frameBuffer.getHardwareBuffer();
        }

        @Override // androidx.hardware.BufferPool.BufferProvider
        public void release() {
            this.frameBuffer.close();
        }
    }

    public final int getAllocationCount() {
        return this.mPool.getAllocationCount();
    }

    public final FrameBuffer obtain(EGLSpec eglSpec) {
        Intrinsics.checkNotNullParameter(eglSpec, "eglSpec");
        BufferPool<FrameBufferProvider> bufferPool = this.mPool;
        int i = this.width;
        int i2 = this.height;
        ReentrantLock reentrantLock = ((BufferPool) bufferPool).mLock;
        reentrantLock.lock();
        try {
            FrameBufferProvider frameBufferProviderObtainFromPool = bufferPool.obtainFromPool(i, i2);
            if (frameBufferProviderObtainFromPool == null) {
                HardwareBuffer hardwareBufferCreate = HardwareBuffer.create(this.width, this.height, this.format, 1, this.usage);
                Intrinsics.checkNotNullExpressionValue(hardwareBufferCreate, "create(...)");
                FrameBufferProvider frameBufferProvider = new FrameBufferProvider(new FrameBuffer(eglSpec, hardwareBufferCreate));
                bufferPool.insert(frameBufferProvider);
                frameBufferProviderObtainFromPool = frameBufferProvider;
            }
            return ((FrameBufferProvider) frameBufferProviderObtainFromPool).getFrameBuffer();
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void updateSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public static /* synthetic */ void release$default(FrameBufferPool frameBufferPool, FrameBuffer frameBuffer, SyncFenceCompat syncFenceCompat, int i, Object obj) {
        if ((i & 2) != 0) {
            syncFenceCompat = null;
        }
        frameBufferPool.release(frameBuffer, syncFenceCompat);
    }

    public final void release(FrameBuffer frameBuffer, SyncFenceCompat fence) {
        Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        this.mPool.release(frameBuffer.getHardwareBuffer(), fence);
    }

    public final boolean isClosed() {
        return this.mPool.isClosed();
    }

    public final void close() {
        this.mPool.close();
    }
}
