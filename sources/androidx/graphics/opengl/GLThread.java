package androidx.graphics.opengl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLSurface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Trace;
import android.util.Log;
import android.view.Surface;
import androidx.graphics.opengl.egl.EGLManager;
import androidx.graphics.opengl.egl.EGLSpec;
import androidx.graphics.utils.HandlerUtilsKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: GLThread.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\b\u0000\u0018\u0000 F2\u00020\u0001:\u0002FGB8\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b¢\u0006\u0004\b\f\u0010\rJ\b\u0010\u001c\u001a\u0004\u0018\u00010\tJ\b\u0010\u001d\u001a\u00020\u001eH\u0016J2\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u00132\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u00132\u0006\u0010%\u001a\u00020&H\u0007J,\u0010'\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u00132\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)H\u0007J \u0010*\u001a\u00020\u001e2\u0016\u0010+\u001a\u0012\u0012\u0004\u0012\u00020\u001a0,j\b\u0012\u0004\u0012\u00020\u001a`-H\u0007J\u0010\u0010.\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u001aH\u0007J\u0010\u0010/\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u001aH\u0007J\u0010\u00100\u001a\u00020\u001e2\u0006\u00101\u001a\u00020)H\u0007J\"\u00102\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u00132\u0006\u00103\u001a\u0002042\b\u0010(\u001a\u0004\u0018\u00010)H\u0007J\u001a\u00105\u001a\u00020\u001e2\u0006\u00103\u001a\u0002042\b\u0010(\u001a\u0004\u0018\u00010)H\u0007J\u001c\u00106\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u00132\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)H\u0007J\b\u00107\u001a\u00020\tH\u0007J\u0010\u00108\u001a\u00020\u001e2\u0006\u00109\u001a\u00020\u0014H\u0003J\u0012\u0010:\u001a\u0004\u0018\u00010;2\u0006\u00109\u001a\u00020\u0014H\u0003J,\u0010<\u001a\u0004\u0018\u00010;2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u00132\u0006\u0010=\u001a\u00020&H\u0003J\u0012\u0010>\u001a\u00020\u001e2\b\u0010(\u001a\u0004\u0018\u00010)H\u0003J\u0010\u0010?\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u0013H\u0003J\u0010\u0010@\u001a\u00020\u001e2\u0006\u0010A\u001a\u00020\u0014H\u0003J \u0010B\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u0013H\u0003J\u001a\u0010C\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u00132\b\u0010(\u001a\u0004\u0018\u00010)H\u0003J\"\u0010D\u001a\u00020\u001e2\u0017\u0010E\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u001e0\b¢\u0006\u0002\b\u000bH\u0082\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u0011\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014`\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u001a0\u0019j\b\u0012\u0004\u0012\u00020\u001a`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Landroidx/graphics/opengl/GLThread;", "Landroid/os/HandlerThread;", "name", "", "mEglSpecFactory", "Lkotlin/Function0;", "Landroidx/graphics/opengl/egl/EGLSpec;", "mEglConfigFactory", "Lkotlin/Function1;", "Landroidx/graphics/opengl/egl/EGLManager;", "Landroid/opengl/EGLConfig;", "Lkotlin/ExtensionFunctionType;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "mIsTearingDown", "Ljava/util/concurrent/atomic/AtomicBoolean;", "mEglManager", "mSurfaceSessions", "Ljava/util/HashMap;", "", "Landroidx/graphics/opengl/GLThread$SurfaceSession;", "Lkotlin/collections/HashMap;", "mHandler", "Landroid/os/Handler;", "mEglContextCallback", "Ljava/util/HashSet;", "Landroidx/graphics/opengl/GLRenderer$EGLContextCallback;", "Lkotlin/collections/HashSet;", "getEGLManager", "start", "", "attachSurface", Constants.VERIFY_SUCESS_TOKEN, "surface", "Landroid/view/Surface;", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "renderer", "Landroidx/graphics/opengl/GLRenderer$RenderCallback;", "resizeSurface", CallMethod.ARG_CALLBACK, "Ljava/lang/Runnable;", "addEGLCallbacks", "callbacks", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "addEGLCallback", "removeEGLCallback", "execute", "runnable", "detachSurface", "cancelPending", "", "tearDown", "requestRender", "obtainEGLManager", "disposeSurfaceSession", "session", "obtainEGLSurfaceForSession", "Landroid/opengl/EGLSurface;", "createEGLSurfaceForSession", "surfaceRenderer", "releaseResourcesInternalAndQuit", "requestRenderInternal", "attachSurfaceSessionInternal", "surfaceSession", "resizeSurfaceSessionInternal", "detachSurfaceSessionInternal", "withHandler", "block", "Companion", "SurfaceSession", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLThread extends HandlerThread {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final boolean DEBUG = true;
    private static final String TAG = "MCRenderThread";
    private final Function1<EGLManager, EGLConfig> mEglConfigFactory;
    private final HashSet<GLRenderer.EGLContextCallback> mEglContextCallback;
    private EGLManager mEglManager;
    private final Function0<EGLSpec> mEglSpecFactory;
    private Handler mHandler;
    private final AtomicBoolean mIsTearingDown;
    private final HashMap<Integer, SurfaceSession> mSurfaceSessions;

    public /* synthetic */ GLThread(String str, Function0 function0, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "GLBaseThread" : str, function0, function1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public GLThread(String name, Function0<? extends EGLSpec> mEglSpecFactory, Function1<? super EGLManager, ? extends EGLConfig> mEglConfigFactory) {
        super(name);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(mEglSpecFactory, "mEglSpecFactory");
        Intrinsics.checkNotNullParameter(mEglConfigFactory, "mEglConfigFactory");
        this.mEglSpecFactory = mEglSpecFactory;
        this.mEglConfigFactory = mEglConfigFactory;
        this.mIsTearingDown = new AtomicBoolean(false);
        this.mSurfaceSessions = new HashMap<>();
        this.mEglContextCallback = new HashSet<>();
    }

    /* JADX INFO: renamed from: getEGLManager, reason: from getter */
    public final EGLManager getMEglManager() {
        return this.mEglManager;
    }

    @Override // java.lang.Thread
    public void start() {
        super.start();
        XLog.v(TAG, getName() + " Start", new Object[0]);
        Handler handler = new Handler(getLooper());
        handler.post(new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.obtainEGLManager();
            }
        });
        this.mHandler = handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void attachSurface$lambda$4$lambda$3(GLThread gLThread, int i, Surface surface, GLRenderer.RenderCallback renderCallback, int i2, int i3) {
        SurfaceSession surfaceSession = new SurfaceSession(i, surface, renderCallback);
        surfaceSession.setWidth(i2);
        surfaceSession.setHeight(i3);
        gLThread.attachSurfaceSessionInternal(surfaceSession);
    }

    public static /* synthetic */ void resizeSurface$default(GLThread gLThread, int i, int i2, int i3, Runnable runnable, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            runnable = null;
        }
        gLThread.resizeSurface(i, i2, i3, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void resizeSurface$lambda$6$lambda$5(GLThread gLThread, int i, int i2, int i3, Runnable runnable) {
        gLThread.resizeSurfaceSessionInternal(i, i2, i3);
        gLThread.requestRenderInternal(i);
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addEGLCallbacks$lambda$9$lambda$8(GLThread gLThread, ArrayList arrayList) {
        gLThread.mEglContextCallback.addAll(arrayList);
        EGLManager eGLManager = gLThread.mEglManager;
        if (eGLManager != null) {
            Iterator it = arrayList.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                Object next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                ((GLRenderer.EGLContextCallback) next).onEGLContextCreated(eGLManager);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addEGLCallback$lambda$12$lambda$11(GLThread gLThread, GLRenderer.EGLContextCallback eGLContextCallback) {
        gLThread.mEglContextCallback.add(eGLContextCallback);
        EGLManager eGLManager = gLThread.mEglManager;
        if (eGLManager != null) {
            eGLContextCallback.onEGLContextCreated(eGLManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeEGLCallback$lambda$14$lambda$13(GLThread gLThread, GLRenderer.EGLContextCallback eGLContextCallback) {
        gLThread.mEglContextCallback.remove(eGLContextCallback);
    }

    public final void detachSurface(final int token, boolean cancelPending, final Runnable callback) {
        INSTANCE.log$libXCEngine_release("dispatching request to detach surface w/ token: " + token);
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        if (cancelPending) {
            handler.removeCallbacksAndMessages(Integer.valueOf(token));
        }
        HandlerUtilsKt.post(handler, Integer.valueOf(token), new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.detachSurfaceSessionInternal(token, callback);
            }
        });
    }

    public static /* synthetic */ void requestRender$default(GLThread gLThread, int i, Runnable runnable, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            runnable = null;
        }
        gLThread.requestRender(i, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestRender$lambda$21$lambda$20(GLThread gLThread, int i, Runnable runnable) {
        gLThread.requestRenderInternal(i);
        if (runnable != null) {
            runnable.run();
        }
    }

    public final EGLManager obtainEGLManager() {
        EGLManager eGLManager = this.mEglManager;
        if (eGLManager == null) {
            eGLManager = new EGLManager(this.mEglSpecFactory.invoke());
            eGLManager.initialize();
            eGLManager.createContext(this.mEglConfigFactory.invoke(eGLManager));
            Iterator<GLRenderer.EGLContextCallback> it = this.mEglContextCallback.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                GLRenderer.EGLContextCallback next = it.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                next.onEGLContextCreated(eGLManager);
            }
            this.mEglManager = eGLManager;
        }
        return eGLManager;
    }

    private final void disposeSurfaceSession(SurfaceSession session) {
        EGLSurface eglSurface = session.getEglSurface();
        if (eglSurface == null || Intrinsics.areEqual(eglSurface, EGL14.EGL_NO_SURFACE)) {
            return;
        }
        obtainEGLManager().getMEglSpec().eglDestroySurface(eglSurface);
        session.setEglSurface(null);
    }

    private final EGLSurface obtainEGLSurfaceForSession(SurfaceSession session) {
        if (session.getEglSurface() != null) {
            return session.getEglSurface();
        }
        EGLSurface eGLSurfaceCreateEGLSurfaceForSession = createEGLSurfaceForSession(session.getSurface(), session.getWidth(), session.getHeight(), session.getSurfaceRenderer());
        session.setEglSurface(eGLSurfaceCreateEGLSurfaceForSession);
        return eGLSurfaceCreateEGLSurfaceForSession;
    }

    private final EGLSurface createEGLSurfaceForSession(Surface surface, int width, int height, GLRenderer.RenderCallback surfaceRenderer) {
        EGLManager eGLManagerObtainEGLManager = obtainEGLManager();
        if (surface == null) {
            return null;
        }
        EGLSpec mEglSpec = eGLManagerObtainEGLManager.getMEglSpec();
        EGLConfig mEglConfig = eGLManagerObtainEGLManager.getMEglConfig();
        Intrinsics.checkNotNull(mEglConfig);
        return surfaceRenderer.onSurfaceCreated(mEglSpec, mEglConfig, surface, width, height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void releaseResourcesInternalAndQuit(Runnable callback) {
        EGLManager eGLManagerObtainEGLManager = obtainEGLManager();
        Iterator<Map.Entry<Integer, SurfaceSession>> it = this.mSurfaceSessions.entrySet().iterator();
        while (it.hasNext()) {
            disposeSurfaceSession(it.next().getValue());
        }
        if (callback != null) {
            callback.run();
        }
        this.mSurfaceSessions.clear();
        Iterator<GLRenderer.EGLContextCallback> it2 = this.mEglContextCallback.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            GLRenderer.EGLContextCallback next = it2.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            next.onEGLContextDestroyed(eGLManagerObtainEGLManager);
        }
        this.mEglContextCallback.clear();
        eGLManagerObtainEGLManager.release();
        this.mEglManager = null;
        quit();
    }

    private final void requestRenderInternal(int token) {
        Trace.beginSection("onDrawFrame");
        try {
            SurfaceSession surfaceSession = this.mSurfaceSessions.get(Integer.valueOf(token));
            if (surfaceSession != null) {
                EGLManager eGLManagerObtainEGLManager = obtainEGLManager();
                EGLSurface eGLSurfaceObtainEGLSurfaceForSession = obtainEGLSurfaceForSession(surfaceSession);
                if (eGLSurfaceObtainEGLSurfaceForSession != null) {
                    EGLManager.makeCurrent$default(eGLManagerObtainEGLManager, eGLSurfaceObtainEGLSurfaceForSession, null, 2, null);
                } else {
                    EGLManager.makeCurrent$default(eGLManagerObtainEGLManager, eGLManagerObtainEGLManager.getMPBufferSurface(), null, 2, null);
                }
                int width = surfaceSession.getWidth();
                int height = surfaceSession.getHeight();
                if (width > 0 && height > 0) {
                    surfaceSession.getSurfaceRenderer().onDrawFrame(eGLManagerObtainEGLManager);
                }
                if (eGLSurfaceObtainEGLSurfaceForSession != null) {
                    eGLManagerObtainEGLManager.swapAndFlushBuffers();
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    private final void attachSurfaceSessionInternal(SurfaceSession surfaceSession) {
        this.mSurfaceSessions.put(Integer.valueOf(surfaceSession.getSurfaceToken()), surfaceSession);
    }

    private final void resizeSurfaceSessionInternal(int token, int width, int height) {
        SurfaceSession surfaceSession = this.mSurfaceSessions.get(Integer.valueOf(token));
        if (surfaceSession != null) {
            surfaceSession.setWidth(width);
            surfaceSession.setHeight(height);
            disposeSurfaceSession(surfaceSession);
            obtainEGLSurfaceForSession(surfaceSession);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void detachSurfaceSessionInternal(int token, Runnable callback) {
        SurfaceSession surfaceSessionRemove = this.mSurfaceSessions.remove(Integer.valueOf(token));
        if (surfaceSessionRemove != null) {
            disposeSurfaceSession(surfaceSessionRemove);
        }
        if (callback != null) {
            callback.run();
        }
    }

    private final void withHandler(Function1<? super Handler, Unit> block) {
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        block.invoke(handler);
    }

    /* JADX INFO: compiled from: GLThread.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0000¢\u0006\u0002\b\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/graphics/opengl/GLThread$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "DEBUG", "", "TAG", "", "log", "", "msg", "log$libXCEngine_release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void log$libXCEngine_release(String msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            Log.v(GLThread.TAG, msg);
        }
    }

    /* JADX INFO: compiled from: GLThread.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u000b\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000b\"\u0004\b\u001c\u0010\u0019¨\u0006\u001d"}, d2 = {"Landroidx/graphics/opengl/GLThread$SurfaceSession;", "", "surfaceToken", "", "surface", "Landroid/view/Surface;", "surfaceRenderer", "Landroidx/graphics/opengl/GLRenderer$RenderCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILandroid/view/Surface;Landroidx/graphics/opengl/GLRenderer$RenderCallback;)V", "getSurfaceToken", "()I", "getSurface", "()Landroid/view/Surface;", "getSurfaceRenderer", "()Landroidx/graphics/opengl/GLRenderer$RenderCallback;", "eglSurface", "Landroid/opengl/EGLSurface;", "getEglSurface", "()Landroid/opengl/EGLSurface;", "setEglSurface", "(Landroid/opengl/EGLSurface;)V", Layer.KEY_WIDTH, "getWidth", "setWidth", "(I)V", Layer.KEY_HEIGHT, "getHeight", "setHeight", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class SurfaceSession {
        private EGLSurface eglSurface;
        private int height;
        private final Surface surface;
        private final GLRenderer.RenderCallback surfaceRenderer;
        private final int surfaceToken;
        private int width;

        public SurfaceSession(int i, Surface surface, GLRenderer.RenderCallback surfaceRenderer) {
            Intrinsics.checkNotNullParameter(surfaceRenderer, "surfaceRenderer");
            this.surfaceToken = i;
            this.surface = surface;
            this.surfaceRenderer = surfaceRenderer;
        }

        public final int getSurfaceToken() {
            return this.surfaceToken;
        }

        public final Surface getSurface() {
            return this.surface;
        }

        public final GLRenderer.RenderCallback getSurfaceRenderer() {
            return this.surfaceRenderer;
        }

        public final EGLSurface getEglSurface() {
            return this.eglSurface;
        }

        public final void setEglSurface(EGLSurface eGLSurface) {
            this.eglSurface = eGLSurface;
        }

        public final int getWidth() {
            return this.width;
        }

        public final void setWidth(int i) {
            this.width = i;
        }

        public final int getHeight() {
            return this.height;
        }

        public final void setHeight(int i) {
            this.height = i;
        }
    }

    public final void attachSurface(final int token, final Surface surface, final int width, final int height, final GLRenderer.RenderCallback renderer) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        HandlerUtilsKt.post(handler, Integer.valueOf(token), new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                GLThread.attachSurface$lambda$4$lambda$3(this.f$0, token, surface, renderer, width, height);
            }
        });
    }

    public final void resizeSurface(final int token, final int width, final int height, final Runnable callback) {
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        HandlerUtilsKt.post(handler, Integer.valueOf(token), new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                GLThread.resizeSurface$lambda$6$lambda$5(this.f$0, token, width, height, callback);
            }
        });
    }

    public final void addEGLCallbacks(final ArrayList<GLRenderer.EGLContextCallback> callbacks) {
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        handler.post(new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                GLThread.addEGLCallbacks$lambda$9$lambda$8(this.f$0, callbacks);
            }
        });
    }

    public final void addEGLCallback(final GLRenderer.EGLContextCallback callbacks) {
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        handler.post(new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GLThread.addEGLCallback$lambda$12$lambda$11(this.f$0, callbacks);
            }
        });
    }

    public final void removeEGLCallback(final GLRenderer.EGLContextCallback callbacks) {
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        handler.post(new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                GLThread.removeEGLCallback$lambda$14$lambda$13(this.f$0, callbacks);
            }
        });
    }

    public final void execute(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        handler.post(runnable);
    }

    public final void tearDown(boolean cancelPending, final Runnable callback) {
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        if (cancelPending) {
            handler.removeCallbacksAndMessages(null);
        }
        handler.post(new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.releaseResourcesInternalAndQuit(callback);
            }
        });
        this.mIsTearingDown.set(true);
    }

    public final void requestRender(final int token, final Runnable callback) {
        Handler handler = this.mHandler;
        if (handler == null) {
            throw new IllegalStateException("Did you forget to call GLThread.start()?");
        }
        if (this.mIsTearingDown.get()) {
            return;
        }
        HandlerUtilsKt.post(handler, Integer.valueOf(token), new Runnable() { // from class: androidx.graphics.opengl.GLThread$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GLThread.requestRender$lambda$21$lambda$20(this.f$0, token, callback);
            }
        });
    }
}
