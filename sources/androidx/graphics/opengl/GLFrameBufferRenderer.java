package androidx.graphics.opengl;

import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.graphics.lowlatency.BufferInfo;
import androidx.graphics.lowlatency.BufferTransformHintResolver;
import androidx.graphics.lowlatency.BufferTransformer;
import androidx.graphics.opengl.egl.EGLManager;
import androidx.graphics.surface.SurfaceControlCompat;
import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.pen.mark.MarkPropConfig;

/* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0085\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f*\u0001\u001b\b\u0007\u0018\u0000 R2\u00020\u0001:\u0006MNOPQRBC\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0004\b\u000f\u0010\u0010J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u0016\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007J5\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\u00122\u0006\u0010<\u001a\u00020\u00072\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¢\u0006\u0002\b@J\u0019\u0010A\u001a\u00020\u001f2\n\b\u0002\u0010B\u001a\u0004\u0018\u00010!H\u0000¢\u0006\u0002\bCJ\"\u0010D\u001a\u00020\u001f2\u0006\u0010E\u001a\u00020\u00182\u0010\b\u0002\u0010F\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010GH\u0007J'\u0010H\u001a\u00020\u001f2\u0006\u0010E\u001a\u00020\u00182\u0010\b\u0002\u0010I\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010GH\u0000¢\u0006\u0002\bJJ\u0006\u0010K\u001a\u00020\u0018J\u0006\u0010L\u001a\u00020\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010%\u001a\u00020\u00078F¢\u0006\f\u0012\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u0017\u0010*\u001a\u00020\t8F¢\u0006\f\u0012\u0004\b+\u0010'\u001a\u0004\b,\u0010-R\u0011\u0010\r\u001a\u00020\u000e8G¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0011\u00100\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b1\u00102R\u0011\u00103\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b4\u0010)R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer;", "", "surfaceControlProvider", "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider;", CallMethod.ARG_CALLBACK, "Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;", "mFormat", "", "mUsage", "", "mMaxBuffers", "mSyncStrategy", "Landroidx/graphics/opengl/SyncStrategy;", "glRenderer", "Landroidx/graphics/opengl/GLRenderer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider;Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;IJILandroidx/graphics/opengl/SyncStrategy;Landroidx/graphics/opengl/GLRenderer;)V", "mSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "mBufferPool", "Landroidx/graphics/opengl/FrameBufferPool;", "mRenderTarget", "Landroidx/graphics/opengl/GLRenderer$RenderTarget;", "mIsManagingGLRenderer", "", "mIsReleased", "mContextCallbacks", "androidx/graphics/opengl/GLFrameBufferRenderer$mContextCallbacks$1", "Landroidx/graphics/opengl/GLFrameBufferRenderer$mContextCallbacks$1;", "mGLRenderer", "execute", "", "runnable", "Ljava/lang/Runnable;", "onFrameBufferSizeChanged", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "bufferFormat", "getBufferFormat$annotations", "()V", "getBufferFormat", "()I", "usageFlags", "getUsageFlags$annotations", "getUsageFlags", "()J", "getGLRenderer", "()Landroidx/graphics/opengl/GLRenderer;", "syncStrategy", "getSyncStrategy", "()Landroidx/graphics/opengl/SyncStrategy;", "maxBuffers", "getMaxBuffers", "mCurrentFrameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "mCurrentFrameBufferSize", "Landroid/graphics/Point;", "createFrameBufferRenderer", "Landroidx/graphics/opengl/FrameBufferRenderer;", "surfaceControl", "inverseTransform", "bufferTransformer", "Landroidx/graphics/lowlatency/BufferTransformer;", "frameBufferPool", "createFrameBufferRenderer$libXCEngine_release", "drawAsync", "onComplete", "drawAsync$libXCEngine_release", "release", "cancelPending", "onReleaseCallback", "Lkotlin/Function0;", "detachTargets", "onReleaseComplete", "detachTargets$libXCEngine_release", "isValid", "render", "Builder", "Callback", "SurfaceControlProvider", "DefaultSurfaceControlProvider", "SurfaceViewProvider", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrameBufferRenderer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "GLFrameBufferRenderer";
    private FrameBufferPool mBufferPool;
    private final GLFrameBufferRenderer$mContextCallbacks$1 mContextCallbacks;
    private FrameBuffer mCurrentFrameBuffer;
    private Point mCurrentFrameBufferSize;
    private final int mFormat;
    private final GLRenderer mGLRenderer;
    private final boolean mIsManagingGLRenderer;
    private boolean mIsReleased;
    private final int mMaxBuffers;
    private GLRenderer.RenderTarget mRenderTarget;
    private SurfaceControlCompat mSurfaceControl;
    private final SyncStrategy mSyncStrategy;
    private final long mUsage;
    private final SurfaceControlProvider surfaceControlProvider;

    /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH'J*\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0017J\u001a\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0011H\u0017ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;", "", "onDrawFrame", "", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "onDrawComplete", "targetSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "transaction", "Landroidx/graphics/surface/SurfaceControlCompat$Transaction;", "frameBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "syncFence", "Landroidx/hardware/SyncFenceCompat;", "onBufferReleased", "releaseFence", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Callback {
        default void onBufferReleased(FrameBuffer frameBuffer, SyncFenceCompat releaseFence) {
            Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        }

        default void onDrawComplete(SurfaceControlCompat targetSurfaceControl, SurfaceControlCompat.Transaction transaction, FrameBuffer frameBuffer, SyncFenceCompat syncFence) {
            Intrinsics.checkNotNullParameter(targetSurfaceControl, "targetSurfaceControl");
            Intrinsics.checkNotNullParameter(transaction, "transaction");
            Intrinsics.checkNotNullParameter(frameBuffer, "frameBuffer");
        }

        void onDrawFrame(int width, int height, BufferInfo bufferInfo);
    }

    public static /* synthetic */ void getBufferFormat$annotations() {
    }

    public static /* synthetic */ void getUsageFlags$annotations() {
    }

    public final void release(boolean z) {
        release$default(this, z, null, 2, null);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.graphics.opengl.GLFrameBufferRenderer$mContextCallbacks$1] */
    public GLFrameBufferRenderer(SurfaceControlProvider surfaceControlProvider, final Callback callback, int i, long j, int i2, SyncStrategy mSyncStrategy, final GLRenderer gLRenderer) {
        Intrinsics.checkNotNullParameter(surfaceControlProvider, "surfaceControlProvider");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(mSyncStrategy, "mSyncStrategy");
        this.surfaceControlProvider = surfaceControlProvider;
        this.mFormat = i;
        this.mUsage = j;
        this.mMaxBuffers = i2;
        this.mSyncStrategy = mSyncStrategy;
        ?? r4 = new GLRenderer.EGLContextCallback() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$mContextCallbacks$1
            @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
            public void onEGLContextCreated(EGLManager eglManager) {
                Intrinsics.checkNotNullParameter(eglManager, "eglManager");
            }

            @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
            public void onEGLContextDestroyed(EGLManager eglManager) {
                Intrinsics.checkNotNullParameter(eglManager, "eglManager");
                FrameBufferPool frameBufferPool = this.this$0.mBufferPool;
                if (frameBufferPool != null) {
                    frameBufferPool.close();
                }
            }
        };
        this.mContextCallbacks = r4;
        if (i2 < 1) {
            throw new IllegalArgumentException("FrameBufferRenderer must have at least 1 buffer");
        }
        if (gLRenderer == null) {
            this.mIsManagingGLRenderer = true;
            gLRenderer = new GLRenderer(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
            GLRenderer.start$default(gLRenderer, null, 1, null);
        } else {
            this.mIsManagingGLRenderer = false;
            if (!gLRenderer.isRunning()) {
                throw new IllegalStateException("The provided GLRenderer must be running prior to creation of GLFrameBufferRenderer, did you forget to call GLRenderer#start()?");
            }
        }
        gLRenderer.registerEGLContextCallback((GLRenderer.EGLContextCallback) r4);
        this.mGLRenderer = gLRenderer;
        surfaceControlProvider.createSurfaceControl(new SurfaceControlProvider.Callback() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer.1
            @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider.Callback
            public void onSurfaceControlDestroyed() {
                GLFrameBufferRenderer.detachTargets$libXCEngine_release$default(GLFrameBufferRenderer.this, true, null, 2, null);
            }

            @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider.Callback
            public void onSurfaceControlCreated(SurfaceControlCompat surfaceControl, int width, int height, BufferTransformer bufferTransformer, int inverseTransform) {
                Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
                Intrinsics.checkNotNullParameter(bufferTransformer, "bufferTransformer");
                FrameBufferPool frameBufferPool = new FrameBufferPool(bufferTransformer.getBufferWidth(), bufferTransformer.getBufferHeight(), GLFrameBufferRenderer.this.mFormat, GLFrameBufferRenderer.this.mUsage, GLFrameBufferRenderer.this.mMaxBuffers);
                FrameBufferRenderer frameBufferRendererCreateFrameBufferRenderer$libXCEngine_release = GLFrameBufferRenderer.this.createFrameBufferRenderer$libXCEngine_release(surfaceControl, inverseTransform, bufferTransformer, frameBufferPool, callback);
                GLFrameBufferRenderer.this.mBufferPool = frameBufferPool;
                GLFrameBufferRenderer.this.mSurfaceControl = surfaceControl;
                GLFrameBufferRenderer.this.mRenderTarget = gLRenderer.createRenderTarget(width, height, frameBufferRendererCreateFrameBufferRenderer$libXCEngine_release);
            }

            @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider.Callback
            public void requestRender(Runnable renderComplete) {
                GLFrameBufferRenderer.this.drawAsync$libXCEngine_release(renderComplete);
            }
        });
        this.mCurrentFrameBufferSize = new Point();
    }

    /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007B1\b\u0010\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u000eJ\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0016J\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u000bJ\u0010\u0010\u001e\u001a\u00020\u00002\b\b\u0001\u0010\u001f\u001a\u00020\u000bJ\u000e\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0011J\u0010\u0010\"\u001a\u00020\u00002\b\u0010#\u001a\u0004\u0018\u00010\u0014J\u0006\u0010$\u001a\u00020%R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$Builder;", "", "surfaceView", "Landroid/view/SurfaceView;", CallMethod.ARG_CALLBACK, "Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/SurfaceView;Landroidx/graphics/opengl/GLFrameBufferRenderer$Callback;)V", "parentSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "transformHint", "(Landroidx/graphics/surface/SurfaceControlCompat;IIILandroidx/graphics/opengl/GLFrameBufferRenderer$Callback;)V", "mBufferFormat", "mUsageFlags", "", "mMaxBuffers", "mGLRenderer", "Landroidx/graphics/opengl/GLRenderer;", "mSyncStrategy", "Landroidx/graphics/opengl/SyncStrategy;", "mSurfaceControlProvider", "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider;", "mCallback", "setSyncStrategy", "syncStrategy", "setBufferFormat", "format", "setMaxBuffers", "numBuffers", "setUsageFlags", "usageFlags", "setGLRenderer", "glRenderer", "build", "Landroidx/graphics/opengl/GLFrameBufferRenderer;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private int mBufferFormat;
        private final Callback mCallback;
        private GLRenderer mGLRenderer;
        private int mMaxBuffers;
        private final SurfaceControlProvider mSurfaceControlProvider;
        private SyncStrategy mSyncStrategy;
        private long mUsageFlags;

        public Builder(SurfaceView surfaceView, Callback callback) {
            Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.mBufferFormat = 1;
            this.mUsageFlags = 2816L;
            this.mMaxBuffers = 3;
            this.mSyncStrategy = SyncStrategy.ALWAYS;
            this.mSurfaceControlProvider = new SurfaceViewProvider(surfaceView);
            this.mCallback = callback;
        }

        public Builder(SurfaceControlCompat parentSurfaceControl, int i, int i2, int i3, Callback callback) {
            Intrinsics.checkNotNullParameter(parentSurfaceControl, "parentSurfaceControl");
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.mBufferFormat = 1;
            this.mUsageFlags = 2816L;
            this.mMaxBuffers = 3;
            this.mSyncStrategy = SyncStrategy.ALWAYS;
            this.mSurfaceControlProvider = new DefaultSurfaceControlProvider(parentSurfaceControl, i, i2, i3);
            this.mCallback = callback;
        }

        public final Builder setSyncStrategy(SyncStrategy syncStrategy) {
            Intrinsics.checkNotNullParameter(syncStrategy, "syncStrategy");
            this.mSyncStrategy = syncStrategy;
            return this;
        }

        public final Builder setBufferFormat(int format) {
            this.mBufferFormat = format;
            return this;
        }

        public final Builder setMaxBuffers(int numBuffers) {
            if (numBuffers <= 0) {
                throw new IllegalArgumentException("Must have at least 1 buffer".toString());
            }
            this.mMaxBuffers = numBuffers;
            return this;
        }

        public final Builder setUsageFlags(long usageFlags) {
            this.mUsageFlags = usageFlags | 2816;
            return this;
        }

        public final Builder setGLRenderer(GLRenderer glRenderer) {
            this.mGLRenderer = glRenderer;
            return this;
        }

        public final GLFrameBufferRenderer build() {
            return new GLFrameBufferRenderer(this.mSurfaceControlProvider, this.mCallback, this.mBufferFormat, this.mUsageFlags, this.mMaxBuffers, this.mSyncStrategy, this.mGLRenderer);
        }
    }

    public final void execute(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        if (isValid()) {
            this.mGLRenderer.execute(runnable);
        } else {
            Log.w(TAG, "Attempt to execute runnable after GLFrameBufferRenderer has been released");
        }
    }

    public final void onFrameBufferSizeChanged(int width, int height) {
        FrameBufferPool frameBufferPool = this.mBufferPool;
        if (frameBufferPool != null) {
            frameBufferPool.updateSize(width, height);
        }
        this.mCurrentFrameBufferSize.set(width, height);
        this.mCurrentFrameBuffer = null;
    }

    /* JADX INFO: renamed from: getBufferFormat, reason: from getter */
    public final int getMFormat() {
        return this.mFormat;
    }

    /* JADX INFO: renamed from: getUsageFlags, reason: from getter */
    public final long getMUsage() {
        return this.mUsage;
    }

    /* JADX INFO: renamed from: getGLRenderer, reason: from getter */
    public final GLRenderer getMGLRenderer() {
        return this.mGLRenderer;
    }

    /* JADX INFO: renamed from: getSyncStrategy, reason: from getter */
    public final SyncStrategy getMSyncStrategy() {
        return this.mSyncStrategy;
    }

    /* JADX INFO: renamed from: getMaxBuffers, reason: from getter */
    public final int getMMaxBuffers() {
        return this.mMaxBuffers;
    }

    public final FrameBufferRenderer createFrameBufferRenderer$libXCEngine_release(SurfaceControlCompat surfaceControl, int inverseTransform, BufferTransformer bufferTransformer, FrameBufferPool frameBufferPool, Callback callback) {
        Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
        Intrinsics.checkNotNullParameter(bufferTransformer, "bufferTransformer");
        Intrinsics.checkNotNullParameter(frameBufferPool, "frameBufferPool");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return new FrameBufferRenderer(new GLFrameBufferRenderer$createFrameBufferRenderer$1(bufferTransformer, this, frameBufferPool, callback, surfaceControl, inverseTransform), this.mSyncStrategy);
    }

    public static /* synthetic */ void drawAsync$libXCEngine_release$default(GLFrameBufferRenderer gLFrameBufferRenderer, Runnable runnable, int i, Object obj) {
        if ((i & 1) != 0) {
            runnable = null;
        }
        gLFrameBufferRenderer.drawAsync$libXCEngine_release(runnable);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.graphics.opengl.GLFrameBufferRenderer$drawAsync$eglContextCallback$1] */
    public final void drawAsync$libXCEngine_release(final Runnable onComplete) {
        GLRenderer.RenderTarget renderTarget = this.mRenderTarget;
        final GLRenderer gLRenderer = this.mGLRenderer;
        if (renderTarget == null || !gLRenderer.isRunning()) {
            if (onComplete != null) {
                onComplete.run();
            }
        } else {
            final ?? r0 = new GLRenderer.EGLContextCallback() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$drawAsync$eglContextCallback$1
                @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
                public void onEGLContextCreated(EGLManager eglManager) {
                    Intrinsics.checkNotNullParameter(eglManager, "eglManager");
                }

                @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
                public void onEGLContextDestroyed(EGLManager eglManager) {
                    Intrinsics.checkNotNullParameter(eglManager, "eglManager");
                    Runnable runnable = onComplete;
                    if (runnable != null) {
                        runnable.run();
                    }
                    gLRenderer.unregisterEGLContextCallback(this);
                }
            };
            gLRenderer.registerEGLContextCallback((GLRenderer.EGLContextCallback) r0);
            GLRenderer.RenderTarget renderTarget2 = this.mRenderTarget;
            if (renderTarget2 != null) {
                renderTarget2.requestRender(new Function1() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return GLFrameBufferRenderer.drawAsync$lambda$1(onComplete, gLRenderer, r0, (GLRenderer.RenderTarget) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit drawAsync$lambda$1(Runnable runnable, GLRenderer gLRenderer, GLFrameBufferRenderer$drawAsync$eglContextCallback$1 gLFrameBufferRenderer$drawAsync$eglContextCallback$1, GLRenderer.RenderTarget it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (runnable != null) {
            runnable.run();
        }
        gLRenderer.unregisterEGLContextCallback(gLFrameBufferRenderer$drawAsync$eglContextCallback$1);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void release$default(GLFrameBufferRenderer gLFrameBufferRenderer, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gLFrameBufferRenderer.release(z, function0);
    }

    public final void release(boolean cancelPending, Function0<Unit> onReleaseCallback) {
        if (!this.mIsReleased) {
            detachTargets$libXCEngine_release(cancelPending, onReleaseCallback);
            this.surfaceControlProvider.release();
            this.mGLRenderer.unregisterEGLContextCallback(this.mContextCallbacks);
            if (this.mIsManagingGLRenderer) {
                GLRenderer.stop$default(this.mGLRenderer, false, null, 2, null);
            }
            this.mIsReleased = true;
            return;
        }
        Log.w(TAG, "Attempt to release already released GLFrameBufferRenderer");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void detachTargets$libXCEngine_release$default(GLFrameBufferRenderer gLFrameBufferRenderer, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gLFrameBufferRenderer.detachTargets$libXCEngine_release(z, function0);
    }

    public final void detachTargets$libXCEngine_release(boolean cancelPending, final Function0<Unit> onReleaseComplete) {
        final FrameBufferPool frameBufferPool = this.mBufferPool;
        GLRenderer.RenderTarget renderTarget = this.mRenderTarget;
        if (renderTarget != null) {
            GLRenderer.RenderTarget.detach$default(renderTarget, cancelPending, null, 2, null);
        }
        this.mGLRenderer.execute(new Runnable() { // from class: androidx.graphics.opengl.GLFrameBufferRenderer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GLFrameBufferRenderer.detachTargets$lambda$3(this.f$0, frameBufferPool, onReleaseComplete);
            }
        });
        this.mBufferPool = null;
        this.mSurfaceControl = null;
        this.mRenderTarget = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void detachTargets$lambda$3(GLFrameBufferRenderer gLFrameBufferRenderer, FrameBufferPool frameBufferPool, Function0 function0) {
        FrameBuffer frameBuffer = gLFrameBufferRenderer.mCurrentFrameBuffer;
        if (frameBuffer != null && frameBufferPool != null) {
            FrameBufferPool.release$default(frameBufferPool, frameBuffer, null, 2, null);
        }
        if (frameBufferPool != null) {
            frameBufferPool.close();
        }
        if (function0 != null) {
            function0.invoke();
        }
    }

    public final boolean isValid() {
        return !this.mIsReleased;
    }

    public final void render() {
        if (!this.mIsReleased) {
            GLRenderer.RenderTarget renderTarget = this.mRenderTarget;
            if (renderTarget != null) {
                GLRenderer.RenderTarget.requestRender$default(renderTarget, null, 1, null);
                return;
            }
            return;
        }
        Log.w(TAG, "renderer is released, ignoring request");
    }

    /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b`\u0018\u00002\u00020\u0001:\u0001\u0007J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider;", "", "createSurfaceControl", "", CallMethod.ARG_CALLBACK, "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider$Callback;", "release", "Callback", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface SurfaceControlProvider {
        void createSurfaceControl(Callback callback);

        void release();

        /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
        @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J0\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bH&J\u0014\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider$Callback;", "", "onSurfaceControlDestroyed", "", "onSurfaceControlCreated", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "bufferTransformer", "Landroidx/graphics/lowlatency/BufferTransformer;", "inverseTransform", "requestRender", "renderComplete", "Ljava/lang/Runnable;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public interface Callback {
            void onSurfaceControlCreated(SurfaceControlCompat surfaceControl, int width, int height, BufferTransformer bufferTransformer, int inverseTransform);

            void onSurfaceControlDestroyed();

            void requestRender(Runnable renderComplete);

            static /* synthetic */ void requestRender$default(Callback callback, Runnable runnable, int i, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: requestRender");
                }
                if ((i & 1) != 0) {
                    runnable = null;
                }
                callback.requestRender(runnable);
            }
        }
    }

    /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$DefaultSurfaceControlProvider;", "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider;", "surfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "transformHint", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/surface/SurfaceControlCompat;III)V", "bufferTransformer", "Landroidx/graphics/lowlatency/BufferTransformer;", "mSurfaceControlCallback", "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider$Callback;", "createSurfaceControl", "", CallMethod.ARG_CALLBACK, "release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultSurfaceControlProvider implements SurfaceControlProvider {
        private final BufferTransformer bufferTransformer;
        private final int height;
        private SurfaceControlProvider.Callback mSurfaceControlCallback;
        private final SurfaceControlCompat surfaceControl;
        private final int transformHint;
        private final int width;

        @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider
        public void release() {
        }

        public DefaultSurfaceControlProvider(SurfaceControlCompat surfaceControl, int i, int i2, int i3) {
            Intrinsics.checkNotNullParameter(surfaceControl, "surfaceControl");
            this.surfaceControl = surfaceControl;
            this.width = i;
            this.height = i2;
            this.transformHint = i3;
            this.bufferTransformer = new BufferTransformer();
        }

        @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider
        public void createSurfaceControl(SurfaceControlProvider.Callback callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            int iInvertBufferTransform = this.bufferTransformer.invertBufferTransform(this.transformHint);
            MarkPropConfig.screenRotation = iInvertBufferTransform;
            this.bufferTransformer.computeTransform(this.width, this.height, iInvertBufferTransform);
            callback.onSurfaceControlCreated(this.surfaceControl, this.width, this.height, this.bufferTransformer, iInvertBufferTransform);
            this.mSurfaceControlCallback = callback;
        }
    }

    /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001d\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\rH\u0000¢\u0006\u0002\b\u0011J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rJ\b\u0010\u0013\u001a\u00020\u000fH\u0002J\b\u0010\u0014\u001a\u00020\u000fH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceViewProvider;", "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider;", "surfaceView", "Landroid/view/SurfaceView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/SurfaceView;)V", "mTransformResolver", "Landroidx/graphics/lowlatency/BufferTransformHintResolver;", "mSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "mSurfaceHolderCallback", "Landroid/view/SurfaceHolder$Callback2;", "mSurfaceControlCallback", "Landroidx/graphics/opengl/GLFrameBufferRenderer$SurfaceControlProvider$Callback;", "createSurfaceControl", "", CallMethod.ARG_CALLBACK, "createSurfaceControl$libXCEngine_release", "destroySurfaceControl", "releaseSurfaceControl", "release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class SurfaceViewProvider implements SurfaceControlProvider {
        private SurfaceControlCompat mSurfaceControl;
        private SurfaceControlProvider.Callback mSurfaceControlCallback;
        private SurfaceHolder.Callback2 mSurfaceHolderCallback;
        private final BufferTransformHintResolver mTransformResolver = new BufferTransformHintResolver();
        private SurfaceView surfaceView;

        public SurfaceViewProvider(SurfaceView surfaceView) {
            this.surfaceView = surfaceView;
        }

        public final void createSurfaceControl$libXCEngine_release(SurfaceView surfaceView, SurfaceControlProvider.Callback callback) {
            Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
            Intrinsics.checkNotNullParameter(callback, "callback");
            destroySurfaceControl(callback);
            int width = surfaceView.getWidth();
            int height = surfaceView.getHeight();
            int bufferTransformHint = this.mTransformResolver.getBufferTransformHint(surfaceView);
            BufferTransformer bufferTransformer = new BufferTransformer();
            int iInvertBufferTransform = bufferTransformer.invertBufferTransform(bufferTransformHint);
            bufferTransformer.computeTransform(surfaceView.getWidth(), surfaceView.getHeight(), iInvertBufferTransform);
            SurfaceControlCompat surfaceControlCompatBuild = new SurfaceControlCompat.Builder().setName("GLFrameBufferRendererTarget").setParent(surfaceView).build();
            callback.onSurfaceControlCreated(surfaceControlCompatBuild, width, height, bufferTransformer, iInvertBufferTransform);
            this.mSurfaceControl = surfaceControlCompatBuild;
            this.mSurfaceControlCallback = callback;
        }

        @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider
        public void createSurfaceControl(SurfaceControlProvider.Callback callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            SurfaceView surfaceView = this.surfaceView;
            if (surfaceView != null) {
                GLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1 gLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1 = new GLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1(this, surfaceView, callback);
                SurfaceHolder holder = surfaceView.getHolder();
                holder.addCallback(gLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1);
                if (holder.getSurface() != null && holder.getSurface().isValid() && surfaceView.getWidth() > 0 && surfaceView.getHeight() > 0) {
                    createSurfaceControl$libXCEngine_release(surfaceView, callback);
                }
                this.mSurfaceHolderCallback = gLFrameBufferRenderer$SurfaceViewProvider$createSurfaceControl$1$surfaceHolderCallback$1;
            }
        }

        public final void destroySurfaceControl(SurfaceControlProvider.Callback callback) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            callback.onSurfaceControlDestroyed();
            releaseSurfaceControl();
        }

        private final void releaseSurfaceControl() {
            SurfaceControlCompat surfaceControlCompat = this.mSurfaceControl;
            if (surfaceControlCompat != null) {
                if (surfaceControlCompat.isValid()) {
                    new SurfaceControlCompat.Transaction().reparent(surfaceControlCompat, (SurfaceControlCompat) null).commit();
                    surfaceControlCompat.release();
                }
                this.mSurfaceControl = null;
            }
        }

        @Override // androidx.graphics.opengl.GLFrameBufferRenderer.SurfaceControlProvider
        public void release() {
            SurfaceHolder holder;
            releaseSurfaceControl();
            SurfaceView surfaceView = this.surfaceView;
            if (surfaceView != null && (holder = surfaceView.getHolder()) != null) {
                holder.removeCallback(this.mSurfaceHolderCallback);
            }
            this.surfaceView = null;
        }
    }

    /* JADX INFO: compiled from: GLFrameBufferRenderer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Landroidx/graphics/opengl/GLFrameBufferRenderer$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG$libXCEngine_release", "()Ljava/lang/String;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG$libXCEngine_release() {
            return GLFrameBufferRenderer.TAG;
        }
    }
}
