package androidx.graphics.lowlatency;

import android.opengl.GLES20;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.exifinterface.media.ExifInterface;
import androidx.graphics.opengl.FrameBuffer;
import androidx.graphics.opengl.GLFrameBufferRenderer;
import androidx.graphics.opengl.GLRenderer;
import androidx.graphics.opengl.egl.EGLManager;
import androidx.graphics.surface.SurfaceControlCompat;
import androidx.hardware.SyncFenceCompat;
import androidx.opengl.EGLExt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: GLFrontBufferedRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0004\u0010\u001e!$\b\u0007\u0018\u0000 X*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0002XYB5\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0002J\u0006\u00108\u001a\u00020\bJ%\u0010@\u001a\u00020\u00172\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010A\u001a\u00020\n2\u0006\u0010B\u001a\u00020\nH\u0000¢\u0006\u0002\bCJ%\u0010D\u001a\u00020\u00172\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010A\u001a\u00020\n2\u0006\u0010B\u001a\u00020\nH\u0000¢\u0006\u0002\bEJ\u0006\u0010F\u001a\u00020:J\u0013\u0010G\u001a\u00020\u00172\u0006\u0010H\u001a\u00028\u0000¢\u0006\u0002\u0010IJ\u0006\u0010J\u001a\u00020\u0017J\u0006\u0010K\u001a\u00020\u0017J\u0006\u0010L\u001a\u00020\u0017J\b\u0010M\u001a\u00020\u0017H\u0002J\u0006\u0010N\u001a\u00020\u0017J\u000e\u0010O\u001a\u00020\u00172\u0006\u0010P\u001a\u00020\u001cJ'\u0010Q\u001a\u00020\u00172\u0006\u0010R\u001a\u00020:2\u0010\b\u0002\u0010S\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010TH\u0000¢\u0006\u0002\bUJ\"\u0010V\u001a\u00020\u00172\u0006\u0010R\u001a\u00020:2\u0010\b\u0002\u0010S\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010TH\u0007J\b\u0010W\u001a\u00020\u0017H\u0002R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u0010\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\"R\u0010\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0004\n\u0002\u0010%R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000,X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Landroidx/graphics/lowlatency/GLFrontBufferedRenderer;", ExifInterface.GPS_DIRECTION_TRUE, "", "surfaceView", "Landroid/view/SurfaceView;", CallMethod.ARG_CALLBACK, "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$Callback;", "glRenderer", "Landroidx/graphics/opengl/GLRenderer;", "bufferFormat", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/SurfaceView;Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$Callback;Landroidx/graphics/opengl/GLRenderer;I)V", "getBufferFormat", "()I", "mFrontBufferedCallbacks", "androidx/graphics/lowlatency/GLFrontBufferedRenderer$mFrontBufferedCallbacks$1", "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$mFrontBufferedCallbacks$1;", "mCommitCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "mFrontBufferReleaseFence", "Landroidx/hardware/SyncFenceCompat;", "waitForFrontBufferFence", "", "clearContents", "viewportWidth", "viewportHeight", "mClearFrontBufferRunnable", "Ljava/lang/Runnable;", "mContextCallbacks", "androidx/graphics/lowlatency/GLFrontBufferedRenderer$mContextCallbacks$1", "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$mContextCallbacks$1;", "mMultiBufferedRenderCallbacks", "androidx/graphics/lowlatency/GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1", "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1;", "mSurfaceCallbacks", "androidx/graphics/lowlatency/GLFrontBufferedRenderer$mSurfaceCallbacks$1", "Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$mSurfaceCallbacks$1;", "mPendingClear", "Ljava/util/concurrent/atomic/AtomicBoolean;", "mPendingRenderCount", "mSurfaceView", "mCancelRunnable", "mActiveSegment", "Landroidx/graphics/lowlatency/ParamQueue;", "mFrontLayerBuffer", "Landroidx/graphics/opengl/FrameBuffer;", "mMultiBufferedLayerSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "mFrontBufferedLayerSurfaceControl", "mFrontBufferSyncStrategy", "Landroidx/graphics/lowlatency/FrontBufferSyncStrategy;", "mWidth", "mHeight", "mTransform", "mGLRenderer", "getGLRenderer", "mIsManagingGLRenderer", "", "mFrontBufferedRenderer", "Landroidx/graphics/opengl/GLFrameBufferRenderer;", "mMultiBufferedRenderer", "mIsReleased", "isHaveDraw", "sizeChanged", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "sizeChanged$libXCEngine_release", "update", "update$libXCEngine_release", "isValid", "renderFrontBufferedLayer", "param", "(Ljava/lang/Object;)V", "renderMultiBufferedLayer", "clear", "commit", "commitInternal", ToDoStat.CANCEL, "execute", "runnable", "detachTargets", "cancelPending", "onReleaseComplete", "Lkotlin/Function0;", "detachTargets$libXCEngine_release", "release", "clearParamQueues", "Companion", "Callback", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLFrontBufferedRenderer<T> {
    public static final String TAG = "GLFrontBufferedRenderer";
    private final int bufferFormat;
    private boolean isHaveDraw;
    private final ParamQueue<T> mActiveSegment;
    private final Runnable mCancelRunnable;
    private final Runnable mClearFrontBufferRunnable;
    private final AtomicInteger mCommitCount;
    private final GLFrontBufferedRenderer$mContextCallbacks$1 mContextCallbacks;
    private volatile SyncFenceCompat mFrontBufferReleaseFence;
    private final FrontBufferSyncStrategy mFrontBufferSyncStrategy;
    private final GLFrontBufferedRenderer$mFrontBufferedCallbacks$1 mFrontBufferedCallbacks;
    private SurfaceControlCompat mFrontBufferedLayerSurfaceControl;
    private GLFrameBufferRenderer mFrontBufferedRenderer;
    private FrameBuffer mFrontLayerBuffer;
    private final GLRenderer mGLRenderer;
    private int mHeight;
    private final boolean mIsManagingGLRenderer;
    private boolean mIsReleased;
    private SurfaceControlCompat mMultiBufferedLayerSurfaceControl;
    private final GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1 mMultiBufferedRenderCallbacks;
    private GLFrameBufferRenderer mMultiBufferedRenderer;
    private final AtomicBoolean mPendingClear;
    private final AtomicInteger mPendingRenderCount;
    private final GLFrontBufferedRenderer$mSurfaceCallbacks$1 mSurfaceCallbacks;
    private SurfaceView mSurfaceView;
    private int mTransform;
    private int mWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GLFrontBufferedRenderer(SurfaceView surfaceView, Callback<T> callback) {
        this(surfaceView, callback, null, 0, 12, null);
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        Intrinsics.checkNotNullParameter(callback, "callback");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GLFrontBufferedRenderer(SurfaceView surfaceView, Callback<T> callback, GLRenderer gLRenderer) {
        this(surfaceView, callback, gLRenderer, 0, 8, null);
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        Intrinsics.checkNotNullParameter(callback, "callback");
    }

    public final void release(boolean z) {
        release$default(this, z, null, 2, null);
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [androidx.graphics.lowlatency.GLFrontBufferedRenderer$mContextCallbacks$1] */
    public GLFrontBufferedRenderer(SurfaceView surfaceView, Callback<T> callback, GLRenderer gLRenderer, int i) {
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.bufferFormat = i;
        this.mFrontBufferedCallbacks = new GLFrontBufferedRenderer$mFrontBufferedCallbacks$1(this, callback);
        this.mCommitCount = new AtomicInteger(0);
        this.mClearFrontBufferRunnable = new Runnable() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GLFrontBufferedRenderer.mClearFrontBufferRunnable$lambda$3(this.f$0);
            }
        };
        ?? r7 = new GLRenderer.EGLContextCallback() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$mContextCallbacks$1
            @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
            public void onEGLContextDestroyed(EGLManager eglManager) {
                Intrinsics.checkNotNullParameter(eglManager, "eglManager");
            }

            @Override // androidx.graphics.opengl.GLRenderer.EGLContextCallback
            public void onEGLContextCreated(EGLManager eglManager) {
                Intrinsics.checkNotNullParameter(eglManager, "eglManager");
                Log.d(GLFrontBufferedRenderer.TAG, "Supports KHR_FENCE_SYNC[" + eglManager.isExtensionSupported(EGLExt.EGL_KHR_FENCE_SYNC) + "] ANDROID_NATIVE_FENCE_SYNC: [" + eglManager.isExtensionSupported(EGLExt.EGL_ANDROID_NATIVE_FENCE_SYNC) + "]");
            }
        };
        this.mContextCallbacks = r7;
        this.mMultiBufferedRenderCallbacks = new GLFrontBufferedRenderer$mMultiBufferedRenderCallbacks$1(callback, this);
        GLFrontBufferedRenderer$mSurfaceCallbacks$1 gLFrontBufferedRenderer$mSurfaceCallbacks$1 = new GLFrontBufferedRenderer$mSurfaceCallbacks$1(this);
        this.mSurfaceCallbacks = gLFrontBufferedRenderer$mSurfaceCallbacks$1;
        this.mPendingClear = new AtomicBoolean(false);
        this.mPendingRenderCount = new AtomicInteger(0);
        this.mSurfaceView = surfaceView;
        this.mCancelRunnable = new Runnable() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GLFrontBufferedRenderer.mCancelRunnable$lambda$5(this.f$0);
            }
        };
        this.mActiveSegment = new ParamQueue<>();
        this.mWidth = -1;
        this.mHeight = -1;
        this.mTransform = -1;
        if (gLRenderer == null) {
            this.mIsManagingGLRenderer = true;
            GLRenderer gLRenderer2 = new GLRenderer(null, null, 3, null);
            GLRenderer.start$default(gLRenderer2, null, 1, null);
            gLRenderer = gLRenderer2;
        } else {
            this.mIsManagingGLRenderer = false;
            if (!gLRenderer.isRunning()) {
                throw new IllegalStateException("The provided GLRenderer must be running prior to creation of GLFrontBufferedRenderer, did you forget to call GLRenderer#start()?");
            }
        }
        gLRenderer.registerEGLContextCallback((GLRenderer.EGLContextCallback) r7);
        this.mGLRenderer = gLRenderer;
        this.mFrontBufferSyncStrategy = new FrontBufferSyncStrategy(FrontBufferUtils.INSTANCE.obtainHardwareBufferUsageFlags$libXCEngine_release());
        this.mSurfaceView = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        if (holder != null) {
            holder.addCallback(gLFrontBufferedRenderer$mSurfaceCallbacks$1);
            if (holder.getSurface() == null || !holder.getSurface().isValid()) {
                return;
            }
            update$libXCEngine_release(surfaceView, surfaceView.getWidth(), surfaceView.getHeight());
        }
    }

    public /* synthetic */ GLFrontBufferedRenderer(SurfaceView surfaceView, Callback callback, GLRenderer gLRenderer, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(surfaceView, callback, (i2 & 4) != 0 ? null : gLRenderer, (i2 & 8) != 0 ? 1 : i);
    }

    public final int getBufferFormat() {
        return this.bufferFormat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void waitForFrontBufferFence() {
        SyncFenceCompat syncFenceCompat = this.mFrontBufferReleaseFence;
        if (syncFenceCompat != null) {
            syncFenceCompat.awaitForever();
            this.mFrontBufferReleaseFence = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearContents(int viewportWidth, int viewportHeight) {
        GLES20.glViewport(0, 0, viewportWidth, viewportHeight);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16384);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mClearFrontBufferRunnable$lambda$3(GLFrontBufferedRenderer gLFrontBufferedRenderer) {
        FrameBuffer frameBuffer = gLFrontBufferedRenderer.mFrontLayerBuffer;
        if (frameBuffer != null) {
            XLog.v(TAG, "mClearFrontBufferRunnable: [%d]", Integer.valueOf(frameBuffer.getFrameBuffer()));
            if (!gLFrontBufferedRenderer.mPendingClear.compareAndSet(true, false) || frameBuffer.getIsClosed()) {
                return;
            }
            frameBuffer.makeCurrent();
            gLFrontBufferedRenderer.waitForFrontBufferFence();
            gLFrontBufferedRenderer.clearContents(frameBuffer.getHardwareBuffer().getWidth(), frameBuffer.getHardwareBuffer().getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mCancelRunnable$lambda$5(GLFrontBufferedRenderer gLFrontBufferedRenderer) {
        gLFrontBufferedRenderer.mPendingClear.set(true);
        gLFrontBufferedRenderer.mFrontBufferSyncStrategy.setVisible(false);
        SurfaceControlCompat surfaceControlCompat = gLFrontBufferedRenderer.mFrontBufferedLayerSurfaceControl;
        if (surfaceControlCompat != null) {
            new SurfaceControlCompat.Transaction().setVisibility(surfaceControlCompat, false).commit();
        }
    }

    /* JADX INFO: renamed from: getGLRenderer, reason: from getter */
    public final GLRenderer getMGLRenderer() {
        return this.mGLRenderer;
    }

    public final void sizeChanged$libXCEngine_release(SurfaceView surfaceView, int width, int height) {
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        if (width > 0 && height > 0) {
            XLog.v(TAG, "sizeChanged width: " + width + " height: " + height, new Object[0]);
            if (!(this.mWidth == width && this.mHeight == height) && isValid()) {
                this.mWidth = width;
                this.mHeight = height;
                GLFrameBufferRenderer gLFrameBufferRenderer = this.mFrontBufferedRenderer;
                if (gLFrameBufferRenderer != null) {
                    gLFrameBufferRenderer.onFrameBufferSizeChanged(width, height);
                }
                GLFrameBufferRenderer gLFrameBufferRenderer2 = this.mMultiBufferedRenderer;
                if (gLFrameBufferRenderer2 != null) {
                    gLFrameBufferRenderer2.onFrameBufferSizeChanged(width, height);
                    return;
                }
                return;
            }
            return;
        }
        Log.w(TAG, "Invalid dimensions provided, width and height must be > 0. width: " + width + " height: " + height);
    }

    public final void update$libXCEngine_release(SurfaceView surfaceView, int width, int height) {
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        if (width <= 0 || height <= 0) {
            Log.w(TAG, "Invalid dimensions provided, width and height must be > 0. width: " + width + " height: " + height);
            return;
        }
        int bufferTransformHint = new BufferTransformHintResolver().getBufferTransformHint(surfaceView);
        if (!(this.mWidth == width && this.mHeight == height && this.mTransform == bufferTransformHint) && isValid()) {
            detachTargets$libXCEngine_release$default(this, true, null, 2, null);
            SurfaceControlCompat surfaceControlCompatBuild = new SurfaceControlCompat.Builder().setParent(surfaceView).setName("MultiBufferedSurfaceControl").build();
            SurfaceControlCompat surfaceControlCompatBuild2 = new SurfaceControlCompat.Builder().setParent(surfaceControlCompatBuild).setName("FrontBufferedSurfaceControl").build();
            SurfaceControlCompat.Transaction transactionConfigureFrontBufferLayerFrameRate$default = FrontBufferUtils.Companion.configureFrontBufferLayerFrameRate$default(FrontBufferUtils.INSTANCE, surfaceControlCompatBuild2, 0.0f, null, 6, null);
            if (transactionConfigureFrontBufferLayerFrameRate$default != null) {
                transactionConfigureFrontBufferLayerFrameRate$default.commit();
            }
            GLFrameBufferRenderer gLFrameBufferRendererBuild = new GLFrameBufferRenderer.Builder(surfaceControlCompatBuild, width, height, bufferTransformHint, this.mMultiBufferedRenderCallbacks).setGLRenderer(this.mGLRenderer).setUsageFlags(2816L).setBufferFormat(this.bufferFormat).build();
            this.mFrontBufferedRenderer = new GLFrameBufferRenderer.Builder(surfaceControlCompatBuild2, width, height, bufferTransformHint, this.mFrontBufferedCallbacks).setGLRenderer(this.mGLRenderer).setMaxBuffers(1).setUsageFlags(FrontBufferUtils.INSTANCE.obtainHardwareBufferUsageFlags$libXCEngine_release()).setBufferFormat(this.bufferFormat).setSyncStrategy(this.mFrontBufferSyncStrategy).build();
            this.mFrontBufferedLayerSurfaceControl = surfaceControlCompatBuild2;
            this.mMultiBufferedLayerSurfaceControl = surfaceControlCompatBuild;
            this.mMultiBufferedRenderer = gLFrameBufferRendererBuild;
            this.mWidth = width;
            this.mHeight = height;
            this.mTransform = bufferTransformHint;
        }
    }

    public final boolean isValid() {
        return !this.mIsReleased;
    }

    public final void renderFrontBufferedLayer(T param) {
        if (isValid()) {
            this.mActiveSegment.add(param);
            if (this.mCommitCount.get() == 0) {
                GLFrameBufferRenderer gLFrameBufferRenderer = this.mFrontBufferedRenderer;
                if (gLFrameBufferRenderer != null) {
                    gLFrameBufferRenderer.render();
                    return;
                }
                return;
            }
            this.mPendingRenderCount.incrementAndGet();
            return;
        }
        Log.w(TAG, "Attempt to render to front buffered layer when GLFrontBufferedRenderer has been released");
    }

    public final void renderMultiBufferedLayer() {
        if (isValid()) {
            GLFrameBufferRenderer gLFrameBufferRenderer = this.mMultiBufferedRenderer;
            if (gLFrameBufferRenderer != null) {
                gLFrameBufferRenderer.render();
                return;
            }
            return;
        }
        Log.w(TAG, "Attempt to render to the multi buffered layer when GLFrontBufferedRenderer has been released");
    }

    public final void clear() {
        clearParamQueues();
        this.mPendingClear.set(true);
        GLFrameBufferRenderer gLFrameBufferRenderer = this.mMultiBufferedRenderer;
        if (gLFrameBufferRenderer != null) {
            gLFrameBufferRenderer.render();
        }
    }

    public final void commit() {
        if (this.mCommitCount.getAndIncrement() == 0) {
            commitInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void commitInternal() {
        if (isValid()) {
            this.mPendingRenderCount.set(0);
            GLFrameBufferRenderer gLFrameBufferRenderer = this.mMultiBufferedRenderer;
            if (gLFrameBufferRenderer != null) {
                gLFrameBufferRenderer.render();
                return;
            }
            return;
        }
        Log.w(TAG, "Attempt to render to the multi buffered layer when GLFrontBufferedRenderer has been released");
    }

    public final void cancel() {
        if (isValid()) {
            this.mActiveSegment.clear();
            this.mPendingRenderCount.set(0);
            this.mGLRenderer.execute(this.mCancelRunnable);
            return;
        }
        Log.w(TAG, "Attempt to cancel rendering to front buffer after GLFrontBufferedRenderer has been released");
    }

    public final void execute(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        if (isValid()) {
            this.mGLRenderer.execute(runnable);
        } else {
            Log.w(TAG, "Attempt to execute runnable after GLFrontBufferedRenderer has been released");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void detachTargets$libXCEngine_release$default(GLFrontBufferedRenderer gLFrontBufferedRenderer, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gLFrontBufferedRenderer.detachTargets$libXCEngine_release(z, function0);
    }

    public final void detachTargets$libXCEngine_release(boolean cancelPending, final Function0<Unit> onReleaseComplete) {
        GLFrameBufferRenderer gLFrameBufferRenderer = this.mMultiBufferedRenderer;
        if (gLFrameBufferRenderer != null) {
            GLFrameBufferRenderer.release$default(gLFrameBufferRenderer, cancelPending, null, 2, null);
        }
        GLFrameBufferRenderer gLFrameBufferRenderer2 = this.mFrontBufferedRenderer;
        if (gLFrameBufferRenderer2 != null) {
            GLFrameBufferRenderer.release$default(gLFrameBufferRenderer2, cancelPending, null, 2, null);
        }
        final SurfaceControlCompat surfaceControlCompat = this.mFrontBufferedLayerSurfaceControl;
        final SurfaceControlCompat surfaceControlCompat2 = this.mMultiBufferedLayerSurfaceControl;
        this.mGLRenderer.execute(new Runnable() { // from class: androidx.graphics.lowlatency.GLFrontBufferedRenderer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GLFrontBufferedRenderer.detachTargets$lambda$9(this.f$0, surfaceControlCompat, surfaceControlCompat2, onReleaseComplete);
            }
        });
        this.mMultiBufferedRenderer = null;
        this.mFrontBufferedRenderer = null;
        this.mFrontBufferedLayerSurfaceControl = null;
        this.mMultiBufferedLayerSurfaceControl = null;
        this.mWidth = -1;
        this.mHeight = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void detachTargets$lambda$9(GLFrontBufferedRenderer gLFrontBufferedRenderer, SurfaceControlCompat surfaceControlCompat, SurfaceControlCompat surfaceControlCompat2, Function0 function0) {
        gLFrontBufferedRenderer.clearParamQueues();
        if (surfaceControlCompat != null && surfaceControlCompat.isValid() && surfaceControlCompat2 != null && surfaceControlCompat2.isValid()) {
            new SurfaceControlCompat.Transaction().reparent(surfaceControlCompat, (SurfaceControlCompat) null).reparent(surfaceControlCompat2, (SurfaceControlCompat) null).commit();
            surfaceControlCompat.release();
            surfaceControlCompat2.release();
        }
        SyncFenceCompat syncFenceCompat = gLFrontBufferedRenderer.mFrontBufferReleaseFence;
        if (syncFenceCompat != null) {
            syncFenceCompat.awaitForever();
            syncFenceCompat.close();
            gLFrontBufferedRenderer.mFrontBufferReleaseFence = null;
        }
        FrameBuffer frameBuffer = gLFrontBufferedRenderer.mFrontLayerBuffer;
        if (frameBuffer != null) {
            frameBuffer.close();
        }
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void release$default(GLFrontBufferedRenderer gLFrontBufferedRenderer, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        gLFrontBufferedRenderer.release(z, function0);
    }

    public final void release(boolean cancelPending, Function0<Unit> onReleaseComplete) {
        SurfaceHolder holder;
        if (!isValid()) {
            Log.w(TAG, "Attempt to release GLFrontBufferedRenderer that is already released");
            return;
        }
        detachTargets$libXCEngine_release(cancelPending, onReleaseComplete);
        this.mGLRenderer.unregisterEGLContextCallback(this.mContextCallbacks);
        if (this.mIsManagingGLRenderer) {
            GLRenderer.stop$default(this.mGLRenderer, false, null, 2, null);
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null && (holder = surfaceView.getHolder()) != null) {
            holder.removeCallback(this.mSurfaceCallbacks);
        }
        this.mSurfaceView = null;
        this.mIsReleased = true;
    }

    private final void clearParamQueues() {
        this.mActiveSegment.clear();
    }

    /* JADX INFO: compiled from: GLFrontBufferedRenderer.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0001H'¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H'J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0017J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0017ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0003"}, d2 = {"Landroidx/graphics/lowlatency/GLFrontBufferedRenderer$Callback;", ExifInterface.GPS_DIRECTION_TRUE, "", "onDrawFrontBufferedLayer", "", "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "param", "(Landroidx/graphics/lowlatency/BufferInfo;Ljava/lang/Object;)V", "onDrawMultiBufferedLayer", "onFrontBufferedLayerRenderComplete", "frontBufferedLayerSurfaceControl", "Landroidx/graphics/surface/SurfaceControlCompat;", "transaction", "Landroidx/graphics/surface/SurfaceControlCompat$Transaction;", "onMultiBufferedLayerRenderComplete", "multiBufferedLayerSurfaceControl", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Callback<T> {
        void onDrawFrontBufferedLayer(BufferInfo bufferInfo, T param);

        void onDrawMultiBufferedLayer(BufferInfo bufferInfo);

        default void onFrontBufferedLayerRenderComplete(SurfaceControlCompat frontBufferedLayerSurfaceControl, SurfaceControlCompat.Transaction transaction) {
            Intrinsics.checkNotNullParameter(frontBufferedLayerSurfaceControl, "frontBufferedLayerSurfaceControl");
            Intrinsics.checkNotNullParameter(transaction, "transaction");
        }

        default void onMultiBufferedLayerRenderComplete(SurfaceControlCompat frontBufferedLayerSurfaceControl, SurfaceControlCompat multiBufferedLayerSurfaceControl, SurfaceControlCompat.Transaction transaction) {
            Intrinsics.checkNotNullParameter(frontBufferedLayerSurfaceControl, "frontBufferedLayerSurfaceControl");
            Intrinsics.checkNotNullParameter(multiBufferedLayerSurfaceControl, "multiBufferedLayerSurfaceControl");
            Intrinsics.checkNotNullParameter(transaction, "transaction");
        }

        /* JADX INFO: compiled from: GLFrontBufferedRenderer.kt */
        @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
        public static final class DefaultImpls {
            @Deprecated
            public static <T> void onFrontBufferedLayerRenderComplete(Callback<T> callback, SurfaceControlCompat frontBufferedLayerSurfaceControl, SurfaceControlCompat.Transaction transaction) {
                Intrinsics.checkNotNullParameter(frontBufferedLayerSurfaceControl, "frontBufferedLayerSurfaceControl");
                Intrinsics.checkNotNullParameter(transaction, "transaction");
                Callback.super.onFrontBufferedLayerRenderComplete(frontBufferedLayerSurfaceControl, transaction);
            }

            @Deprecated
            public static <T> void onMultiBufferedLayerRenderComplete(Callback<T> callback, SurfaceControlCompat frontBufferedLayerSurfaceControl, SurfaceControlCompat multiBufferedLayerSurfaceControl, SurfaceControlCompat.Transaction transaction) {
                Intrinsics.checkNotNullParameter(frontBufferedLayerSurfaceControl, "frontBufferedLayerSurfaceControl");
                Intrinsics.checkNotNullParameter(multiBufferedLayerSurfaceControl, "multiBufferedLayerSurfaceControl");
                Intrinsics.checkNotNullParameter(transaction, "transaction");
                Callback.super.onMultiBufferedLayerRenderComplete(frontBufferedLayerSurfaceControl, multiBufferedLayerSurfaceControl, transaction);
            }
        }
    }
}
