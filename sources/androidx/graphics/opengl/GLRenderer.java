package androidx.graphics.opengl;

import android.graphics.SurfaceTexture;
import android.opengl.EGLConfig;
import android.opengl.EGLSurface;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.graphics.opengl.egl.EGLConfigAttributes;
import androidx.graphics.opengl.egl.EGLManager;
import androidx.graphics.opengl.egl.EGLSpec;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GLRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 A2\u00020\u0001:\u0004>?@AB2\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0019\b\u0002\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\t¢\u0006\u0004\b\n\u0010\u000bJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007J0\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0016\b\u0003\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0006H\u0007J5\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0016\b\u0003\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0006H\u0000¢\u0006\u0002\b J\u0006\u0010!\u001a\u00020\u001dJ\u0012\u0010\"\u001a\u00020\u001a2\b\b\u0002\u0010#\u001a\u00020$H\u0007J(\u0010%\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00132\u0016\b\u0002\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0006H\u0007J8\u0010'\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\u0016\b\u0002\u0010+\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0006H\u0007J\u000e\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.J(\u0010/\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0016\b\u0002\u00100\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0006H\u0007J\u000e\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u0017J\u000e\u00103\u001a\u00020\u001a2\u0006\u00102\u001a\u00020\u0017J&\u00104\u001a\u00020\u00132\u0006\u00105\u001a\u0002062\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\u0006\u00107\u001a\u000208J\u001e\u00109\u001a\u00020\u00132\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\u0006\u00107\u001a\u000208J\u0016\u00104\u001a\u00020\u00132\u0006\u0010:\u001a\u00020;2\u0006\u00107\u001a\u000208J\u0016\u00104\u001a\u00020\u00132\u0006\u0010<\u001a\u00020=2\u0006\u00107\u001a\u000208R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00170\u0016j\b\u0012\u0004\u0012\u00020\u0017`\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Landroidx/graphics/opengl/GLRenderer;", "", "eglSpecFactory", "Lkotlin/Function0;", "Landroidx/graphics/opengl/egl/EGLSpec;", "eglConfigFactory", "Lkotlin/Function1;", "Landroidx/graphics/opengl/egl/EGLManager;", "Landroid/opengl/EGLConfig;", "Lkotlin/ExtensionFunctionType;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "mEglSpecFactory", "mEglConfigFactory", "mGLThread", "Landroidx/graphics/opengl/GLThread;", "getEGLManager", "mRenderTargets", "Ljava/util/ArrayList;", "Landroidx/graphics/opengl/GLRenderer$RenderTarget;", "Lkotlin/collections/ArrayList;", "mEglContextCallback", "Ljava/util/HashSet;", "Landroidx/graphics/opengl/GLRenderer$EGLContextCallback;", "Lkotlin/collections/HashSet;", "detach", "", TypedValues.AttributesType.S_TARGET, "cancelPending", "", "onDetachComplete", "detachInternal", "detachInternal$libXCEngine_release", "isRunning", "start", "name", "", "requestRender", "onRenderComplete", "resize", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "onResizeComplete", "execute", "runnable", "Ljava/lang/Runnable;", "stop", "onStop", "registerEGLContextCallback", CallMethod.ARG_CALLBACK, "unregisterEGLContextCallback", "attach", "surface", "Landroid/view/Surface;", "renderer", "Landroidx/graphics/opengl/GLRenderer$RenderCallback;", "createRenderTarget", "surfaceView", "Landroid/view/SurfaceView;", "textureView", "Landroid/view/TextureView;", "EGLContextCallback", "RenderCallback", "RenderTarget", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLRenderer {
    private static final AtomicInteger sToken = new AtomicInteger();
    private final Function1<EGLManager, EGLConfig> mEglConfigFactory;
    private final HashSet<EGLContextCallback> mEglContextCallback;
    private final Function0<EGLSpec> mEglSpecFactory;
    private GLThread mGLThread;
    private final ArrayList<RenderTarget> mRenderTargets;

    /* JADX INFO: compiled from: GLRenderer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Landroidx/graphics/opengl/GLRenderer$EGLContextCallback;", "", "onEGLContextCreated", "", "eglManager", "Landroidx/graphics/opengl/egl/EGLManager;", "onEGLContextDestroyed", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface EGLContextCallback {
        void onEGLContextCreated(EGLManager eglManager);

        void onEGLContextDestroyed(EGLManager eglManager);
    }

    public GLRenderer() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final void detach(RenderTarget target, boolean z) {
        Intrinsics.checkNotNullParameter(target, "target");
        detach$default(this, target, z, null, 4, null);
    }

    public final void requestRender(RenderTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        requestRender$default(this, target, null, 2, null);
    }

    public final void resize(RenderTarget target, int i, int i2) {
        Intrinsics.checkNotNullParameter(target, "target");
        resize$default(this, target, i, i2, null, 8, null);
    }

    public final void start() {
        start$default(this, null, 1, null);
    }

    public final void stop(boolean z) {
        stop$default(this, z, null, 2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public GLRenderer(Function0<? extends EGLSpec> eglSpecFactory, Function1<? super EGLManager, ? extends EGLConfig> eglConfigFactory) {
        Intrinsics.checkNotNullParameter(eglSpecFactory, "eglSpecFactory");
        Intrinsics.checkNotNullParameter(eglConfigFactory, "eglConfigFactory");
        this.mEglSpecFactory = eglSpecFactory;
        this.mEglConfigFactory = eglConfigFactory;
        this.mRenderTargets = new ArrayList<>();
        this.mEglContextCallback = new HashSet<>();
    }

    public /* synthetic */ GLRenderer(Function0 function0, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function0() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return EGLSpec.V14;
            }
        } : function0, (i & 2) != 0 ? new Function1() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GLRenderer._init_$lambda$1((EGLManager) obj);
            }
        } : function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final EGLConfig _init_$lambda$1(EGLManager eGLManager) {
        Intrinsics.checkNotNullParameter(eGLManager, "<this>");
        EGLConfig eGLConfigLoadConfig = eGLManager.loadConfig(EGLConfigAttributes.RGBA_8888);
        if (eGLConfigLoadConfig != null) {
            return eGLConfigLoadConfig;
        }
        throw new IllegalStateException("Unable to obtain config for 8 bit EGL configuration");
    }

    public final EGLManager getEGLManager() {
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            return gLThread.getMEglManager();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void detach$default(GLRenderer gLRenderer, RenderTarget renderTarget, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = null;
        }
        gLRenderer.detach(renderTarget, z, function1);
    }

    public final void detach(final RenderTarget target, boolean cancelPending, final Function1<? super RenderTarget, Unit> onDetachComplete) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (this.mRenderTargets.contains(target)) {
            detachInternal$libXCEngine_release(target, cancelPending, new Function1() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return GLRenderer.detach$lambda$2(target, onDetachComplete, (GLRenderer.RenderTarget) obj);
                }
            });
            this.mRenderTargets.remove(target);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit detach$lambda$2(RenderTarget renderTarget, Function1 function1, RenderTarget it) {
        Intrinsics.checkNotNullParameter(it, "it");
        renderTarget.release$libXCEngine_release();
        renderTarget.getOnDetach$libXCEngine_release().invoke();
        if (function1 != null) {
            function1.invoke(renderTarget);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void detachInternal$libXCEngine_release$default(GLRenderer gLRenderer, RenderTarget renderTarget, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = null;
        }
        gLRenderer.detachInternal$libXCEngine_release(renderTarget, z, function1);
    }

    public final void detachInternal$libXCEngine_release(final RenderTarget target, boolean cancelPending, final Function1<? super RenderTarget, Unit> onDetachComplete) {
        Intrinsics.checkNotNullParameter(target, "target");
        Runnable runnable = onDetachComplete != null ? new Runnable() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                onDetachComplete.invoke(target);
            }
        } : null;
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.detachSurface(target.getToken(), cancelPending, runnable);
        }
    }

    public final boolean isRunning() {
        return this.mGLThread != null;
    }

    public static /* synthetic */ void start$default(GLRenderer gLRenderer, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "MCRenderThread";
        }
        gLRenderer.start(str);
    }

    public final void start(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (this.mGLThread == null) {
            GLThread.INSTANCE.log$libXCEngine_release("starting thread...");
            GLThread gLThread = new GLThread(name, this.mEglSpecFactory, this.mEglConfigFactory);
            gLThread.start();
            if (!this.mEglContextCallback.isEmpty()) {
                gLThread.addEGLCallbacks(new ArrayList<>(this.mEglContextCallback));
            }
            this.mGLThread = gLThread;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void requestRender$default(GLRenderer gLRenderer, RenderTarget renderTarget, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        gLRenderer.requestRender(renderTarget, function1);
    }

    public final void requestRender(final RenderTarget target, final Function1<? super RenderTarget, Unit> onRenderComplete) {
        Intrinsics.checkNotNullParameter(target, "target");
        int token = target.getToken();
        Runnable runnable = onRenderComplete != null ? new Runnable() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                onRenderComplete.invoke(target);
            }
        } : null;
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.requestRender(token, runnable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void resize$default(GLRenderer gLRenderer, RenderTarget renderTarget, int i, int i2, Function1 function1, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            function1 = null;
        }
        gLRenderer.resize(renderTarget, i, i2, function1);
    }

    public final void resize(final RenderTarget target, int width, int height, final Function1<? super RenderTarget, Unit> onResizeComplete) {
        Intrinsics.checkNotNullParameter(target, "target");
        int token = target.getToken();
        Runnable runnable = onResizeComplete != null ? new Runnable() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                onResizeComplete.invoke(target);
            }
        } : null;
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.resizeSurface(token, width, height, runnable);
        }
    }

    public final void execute(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.execute(runnable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void stop$default(GLRenderer gLRenderer, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        gLRenderer.stop(z, function1);
    }

    public final void stop(boolean cancelPending, final Function1<? super GLRenderer, Unit> onStop) {
        GLThread.INSTANCE.log$libXCEngine_release("stopping thread...");
        final ArrayList arrayList = new ArrayList(this.mRenderTargets);
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.tearDown(cancelPending, new Runnable() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    GLRenderer.stop$lambda$7(arrayList, onStop, this);
                }
            });
        }
        this.mGLThread = null;
        this.mRenderTargets.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stop$lambda$7(ArrayList arrayList, Function1 function1, GLRenderer gLRenderer) {
        Iterator it = arrayList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            RenderTarget renderTarget = (RenderTarget) it.next();
            renderTarget.release$libXCEngine_release();
            renderTarget.getOnDetach$libXCEngine_release().invoke();
        }
        if (function1 != null) {
            function1.invoke(gLRenderer);
        }
    }

    public final void registerEGLContextCallback(EGLContextCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mEglContextCallback.add(callback);
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.addEGLCallback(callback);
        }
    }

    public final void unregisterEGLContextCallback(EGLContextCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mEglContextCallback.remove(callback);
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.removeEGLCallback(callback);
        }
    }

    /* JADX INFO: compiled from: GLRenderer.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J2\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0017J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0003"}, d2 = {"Landroidx/graphics/opengl/GLRenderer$RenderCallback;", "", "onSurfaceCreated", "Landroid/opengl/EGLSurface;", "spec", "Landroidx/graphics/opengl/egl/EGLSpec;", "config", "Landroid/opengl/EGLConfig;", "surface", "Landroid/view/Surface;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "onDrawFrame", "", "eglManager", "Landroidx/graphics/opengl/egl/EGLManager;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface RenderCallback {
        void onDrawFrame(EGLManager eglManager);

        /* JADX INFO: compiled from: GLRenderer.kt */
        @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
        public static final class DefaultImpls {
            @Deprecated
            public static EGLSurface onSurfaceCreated(RenderCallback renderCallback, EGLSpec spec, EGLConfig config, Surface surface, int i, int i2) {
                Intrinsics.checkNotNullParameter(spec, "spec");
                Intrinsics.checkNotNullParameter(config, "config");
                Intrinsics.checkNotNullParameter(surface, "surface");
                return RenderCallback.super.onSurfaceCreated(spec, config, surface, i, i2);
            }
        }

        default EGLSurface onSurfaceCreated(EGLSpec spec, EGLConfig config, Surface surface, int width, int height) {
            Intrinsics.checkNotNullParameter(spec, "spec");
            Intrinsics.checkNotNullParameter(config, "config");
            Intrinsics.checkNotNullParameter(surface, "surface");
            return spec.eglCreateWindowSurface(config, surface, null);
        }
    }

    public final RenderTarget attach(Surface surface, int width, int height, RenderCallback renderer) {
        Intrinsics.checkNotNullParameter(surface, "surface");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            int andIncrement = sToken.getAndIncrement();
            gLThread.attachSurface(andIncrement, surface, width, height, renderer);
            RenderTarget renderTarget = new RenderTarget(andIncrement, this, null, 4, null);
            this.mRenderTargets.add(renderTarget);
            return renderTarget;
        }
        throw new IllegalStateException("GLThread not started, did you forget to call start?");
    }

    public final RenderTarget createRenderTarget(int width, int height, RenderCallback renderer) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            int andIncrement = sToken.getAndIncrement();
            gLThread.attachSurface(andIncrement, null, width, height, renderer);
            RenderTarget renderTarget = new RenderTarget(andIncrement, this, null, 4, null);
            this.mRenderTargets.add(renderTarget);
            return renderTarget;
        }
        throw new IllegalStateException("GLThread not started, did you forget to call start?");
    }

    public final RenderTarget attach(SurfaceView surfaceView, RenderCallback renderer) {
        Intrinsics.checkNotNullParameter(surfaceView, "surfaceView");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            int andIncrement = sToken.getAndIncrement();
            SurfaceHolder holder = surfaceView.getHolder();
            GLRenderer$attach$callback$1 gLRenderer$attach$callback$1 = new GLRenderer$attach$callback$1(andIncrement, this, holder, gLThread, renderer);
            holder.addCallback(gLRenderer$attach$callback$1);
            if (holder.getSurface() != null && holder.getSurface().isValid()) {
                gLThread.attachSurface(andIncrement, holder.getSurface(), surfaceView.getWidth(), surfaceView.getHeight(), renderer);
            }
            this.mRenderTargets.add(gLRenderer$attach$callback$1.getRenderTarget());
            return gLRenderer$attach$callback$1.getRenderTarget();
        }
        throw new IllegalStateException("GLThread not started, did you forget to call start?");
    }

    public final RenderTarget attach(final TextureView textureView, RenderCallback renderer) {
        Intrinsics.checkNotNullParameter(textureView, "textureView");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            int andIncrement = sToken.getAndIncrement();
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            RenderTarget renderTarget = new RenderTarget(andIncrement, this, new Function0() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return GLRenderer.attach$lambda$11(textureView, countDownLatch);
                }
            });
            textureView.setSurfaceTextureListener(new AnonymousClass2(gLThread, andIncrement, renderer, renderTarget, countDownLatch));
            if (textureView.isAvailable()) {
                gLThread.attachSurface(andIncrement, new Surface(textureView.getSurfaceTexture()), textureView.getWidth(), textureView.getHeight(), renderer);
                RenderTarget.requestRender$default(renderTarget, null, 1, null);
            }
            this.mRenderTargets.add(renderTarget);
            return renderTarget;
        }
        throw new IllegalStateException("GLThread not started, did you forget to call start?");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit attach$lambda$11(final TextureView textureView, CountDownLatch countDownLatch) {
        Handler handler = textureView.getHandler();
        if (handler != null) {
            handler.post(new Runnable() { // from class: androidx.graphics.opengl.GLRenderer$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    textureView.setSurfaceTextureListener(null);
                }
            });
        }
        countDownLatch.countDown();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: androidx.graphics.opengl.GLRenderer$attach$2, reason: invalid class name */
    /* JADX INFO: compiled from: GLRenderer.kt */
    @Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J \u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0005H\u0016¨\u0006\u000f"}, d2 = {"androidx/graphics/opengl/GLRenderer$attach$2", "Landroid/view/TextureView$SurfaceTextureListener;", "onSurfaceTextureAvailable", "", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "onSurfaceTextureSizeChanged", "texture", "onSurfaceTextureDestroyed", "", "p0", "onSurfaceTextureUpdated", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass2 implements TextureView.SurfaceTextureListener {
        final /* synthetic */ CountDownLatch $detachLatch;
        final /* synthetic */ RenderTarget $renderTarget;
        final /* synthetic */ RenderCallback $renderer;
        final /* synthetic */ GLThread $thread;
        final /* synthetic */ int $token;

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
        }

        AnonymousClass2(GLThread gLThread, int i, RenderCallback renderCallback, RenderTarget renderTarget, CountDownLatch countDownLatch) {
            this.$thread = gLThread;
            this.$token = i;
            this.$renderer = renderCallback;
            this.$renderTarget = renderTarget;
            this.$detachLatch = countDownLatch;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            Intrinsics.checkNotNullParameter(surfaceTexture, "surfaceTexture");
            this.$thread.attachSurface(this.$token, new Surface(surfaceTexture), width, height, this.$renderer);
            RenderTarget.requestRender$default(this.$renderTarget, null, 1, null);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            Intrinsics.checkNotNullParameter(texture, "texture");
            RenderTarget.resize$default(this.$renderTarget, width, height, null, 4, null);
            RenderTarget.requestRender$default(this.$renderTarget, null, 1, null);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture p0) throws InterruptedException {
            Intrinsics.checkNotNullParameter(p0, "p0");
            RenderTarget renderTarget = this.$renderTarget;
            final CountDownLatch countDownLatch = this.$detachLatch;
            renderTarget.detachInternal$libXCEngine_release(true, new Function1() { // from class: androidx.graphics.opengl.GLRenderer$attach$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return GLRenderer.AnonymousClass2.onSurfaceTextureDestroyed$lambda$0(countDownLatch, (GLRenderer.RenderTarget) obj);
                }
            });
            this.$detachLatch.await();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit onSurfaceTextureDestroyed$lambda$0(CountDownLatch countDownLatch, RenderTarget it) {
            Intrinsics.checkNotNullParameter(it, "it");
            countDownLatch.countDown();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: GLRenderer.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B)\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0004\b\t\u0010\nJ\r\u0010\u0010\u001a\u00020\bH\u0000¢\u0006\u0002\b\u0011J \u0010\u0012\u001a\u00020\b2\u0016\b\u0003\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0014H\u0007J\u0006\u0010\u0015\u001a\u00020\u0016J0\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00032\u0016\b\u0003\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0014H\u0007J(\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00162\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0014H\u0007J-\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00162\u0016\b\u0002\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0014H\u0000¢\u0006\u0002\b\u001fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Landroidx/graphics/opengl/GLRenderer$RenderTarget;", "", Constants.VERIFY_SUCESS_TOKEN, "", "glManager", "Landroidx/graphics/opengl/GLRenderer;", "onDetach", "Lkotlin/Function0;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILandroidx/graphics/opengl/GLRenderer;Lkotlin/jvm/functions/Function0;)V", "getToken$libXCEngine_release", "()I", "getOnDetach$libXCEngine_release", "()Lkotlin/jvm/functions/Function0;", "mManager", "release", "release$libXCEngine_release", "requestRender", "onRenderComplete", "Lkotlin/Function1;", "isAttached", "", "resize", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "onResizeComplete", "detach", "cancelPending", "onDetachComplete", "detachInternal", "detachInternal$libXCEngine_release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class RenderTarget {
        private volatile GLRenderer mManager;
        private final Function0<Unit> onDetach;
        private final int token;

        public final void detach(boolean z) {
            detach$default(this, z, null, 2, null);
        }

        public final void requestRender() {
            requestRender$default(this, null, 1, null);
        }

        public final void resize(int i, int i2) {
            resize$default(this, i, i2, null, 4, null);
        }

        public RenderTarget(int i, GLRenderer glManager, Function0<Unit> onDetach) {
            Intrinsics.checkNotNullParameter(glManager, "glManager");
            Intrinsics.checkNotNullParameter(onDetach, "onDetach");
            this.token = i;
            this.onDetach = onDetach;
            this.mManager = glManager;
        }

        /* JADX INFO: renamed from: getToken$libXCEngine_release, reason: from getter */
        public final int getToken() {
            return this.token;
        }

        public /* synthetic */ RenderTarget(int i, GLRenderer gLRenderer, Function0 function0, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, gLRenderer, (i2 & 4) != 0 ? new Function0() { // from class: androidx.graphics.opengl.GLRenderer$RenderTarget$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Unit.INSTANCE;
                }
            } : function0);
        }

        public final Function0<Unit> getOnDetach$libXCEngine_release() {
            return this.onDetach;
        }

        public final void release$libXCEngine_release() {
            this.mManager = null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void requestRender$default(RenderTarget renderTarget, Function1 function1, int i, Object obj) {
            if ((i & 1) != 0) {
                function1 = null;
            }
            renderTarget.requestRender(function1);
        }

        public final void requestRender(Function1<? super RenderTarget, Unit> onRenderComplete) {
            GLRenderer gLRenderer = this.mManager;
            if (gLRenderer != null) {
                gLRenderer.requestRender(this, onRenderComplete);
            }
        }

        public final boolean isAttached() {
            return this.mManager != null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void resize$default(RenderTarget renderTarget, int i, int i2, Function1 function1, int i3, Object obj) {
            if ((i3 & 4) != 0) {
                function1 = null;
            }
            renderTarget.resize(i, i2, function1);
        }

        public final void resize(int width, int height, Function1<? super RenderTarget, Unit> onResizeComplete) {
            GLRenderer gLRenderer = this.mManager;
            if (gLRenderer != null) {
                gLRenderer.resize(this, width, height, onResizeComplete);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void detach$default(RenderTarget renderTarget, boolean z, Function1 function1, int i, Object obj) {
            if ((i & 2) != 0) {
                function1 = null;
            }
            renderTarget.detach(z, function1);
        }

        public final void detach(boolean cancelPending, Function1<? super RenderTarget, Unit> onDetachComplete) {
            GLRenderer gLRenderer = this.mManager;
            if (gLRenderer != null) {
                gLRenderer.detach(this, cancelPending, onDetachComplete);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void detachInternal$libXCEngine_release$default(RenderTarget renderTarget, boolean z, Function1 function1, int i, Object obj) {
            if ((i & 2) != 0) {
                function1 = null;
            }
            renderTarget.detachInternal$libXCEngine_release(z, function1);
        }

        public final void detachInternal$libXCEngine_release(boolean cancelPending, Function1<? super RenderTarget, Unit> onDetachComplete) {
            GLRenderer gLRenderer = this.mManager;
            if (gLRenderer != null) {
                gLRenderer.detachInternal$libXCEngine_release(this, cancelPending, onDetachComplete);
            }
        }
    }
}
