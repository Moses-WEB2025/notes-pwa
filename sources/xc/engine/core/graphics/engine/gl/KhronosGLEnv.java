package xc.engine.core.graphics.engine.gl;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.task.BaseGLThread;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: KhronosGLEnv.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0005H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lxc/engine/core/graphics/engine/gl/KhronosGLEnv;", "Lxc/engine/core/graphics/engine/task/BaseGLThread$IGLEnv;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sharedEglContext", "Ljavax/microedition/khronos/egl/EGLContext;", "eglContext", "eglDisplay", "Ljavax/microedition/khronos/egl/EGLDisplay;", "eglSurface", "Ljavax/microedition/khronos/egl/EGLSurface;", "eglConfig", "Ljavax/microedition/khronos/egl/EGLConfig;", "egl", "Ljavax/microedition/khronos/egl/EGL10;", "initGLEnv", "", "onDestroy", "", "makeCurrent", "setSharedContext", "sharedContext", "Landroid/opengl/EGLContext;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class KhronosGLEnv implements BaseGLThread.IGLEnv {
    private static final String TAG = "KhronosGLEnv";
    private EGL10 egl;
    private EGLConfig eglConfig;
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface;
    private EGLContext sharedEglContext;

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void setSharedContext(android.opengl.EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
    }

    public KhronosGLEnv() {
        EGLContext EGL_NO_CONTEXT = EGL10.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
        this.sharedEglContext = EGL_NO_CONTEXT;
        EGLContext EGL_NO_CONTEXT2 = EGL10.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT2, "EGL_NO_CONTEXT");
        this.eglContext = EGL_NO_CONTEXT2;
        EGLDisplay EGL_NO_DISPLAY = EGL10.EGL_NO_DISPLAY;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_DISPLAY, "EGL_NO_DISPLAY");
        this.eglDisplay = EGL_NO_DISPLAY;
        EGLSurface EGL_NO_SURFACE = EGL10.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
        this.eglSurface = EGL_NO_SURFACE;
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public boolean initGLEnv() {
        EGL egl = EGLContext.getEGL();
        Intrinsics.checkNotNull(egl, "null cannot be cast to non-null type javax.microedition.khronos.egl.EGL10");
        EGL10 egl10 = (EGL10) egl;
        this.egl = egl10;
        if (egl10 == null) {
            XLog.e(TAG, "Failed to get EGL instance", new Object[0]);
            return false;
        }
        Intrinsics.checkNotNull(egl10);
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        Intrinsics.checkNotNullExpressionValue(eGLDisplayEglGetDisplay, "eglGetDisplay(...)");
        this.eglDisplay = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
            XLog.e(TAG, "eglGetDisplay error", new Object[0]);
            return false;
        }
        EGL10 egl11 = this.egl;
        Intrinsics.checkNotNull(egl11);
        if (!egl11.eglInitialize(this.eglDisplay, new int[2])) {
            XLog.e(TAG, "eglInitialize error", new Object[0]);
            return false;
        }
        int[] iArr = {R2.drawable.v8_theme_star_bg_3, 32, R2.drawable.v8_theme_star_bg_bottom, 8, R2.drawable.v8_theme_star_bg_top, 8, R2.drawable.v8_theme_star_grid_fg, 8, R2.drawable.v8_theme_white_share_bg, 8, R2.id.TOP_START, 64, R2.id.ALT, 1, R2.id.META};
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr2 = new int[1];
        EGL10 egl12 = this.egl;
        Intrinsics.checkNotNull(egl12);
        if (!egl12.eglChooseConfig(this.eglDisplay, iArr, eGLConfigArr, 1, iArr2)) {
            XLog.e(TAG, "eglChooseConfig error", new Object[0]);
            return false;
        }
        if (iArr2[0] <= 0) {
            XLog.e(TAG, "No valid EGL configs found", new Object[0]);
            return false;
        }
        this.eglConfig = eGLConfigArr[0];
        int[] iArr3 = {R2.id.action_sheet_arrow_view, 3, R2.id.META};
        EGL10 egl13 = this.egl;
        Intrinsics.checkNotNull(egl13);
        EGLContext eGLContextEglCreateContext = egl13.eglCreateContext(this.eglDisplay, this.eglConfig, this.sharedEglContext, iArr3);
        Intrinsics.checkNotNullExpressionValue(eGLContextEglCreateContext, "eglCreateContext(...)");
        this.eglContext = eGLContextEglCreateContext;
        if (eGLContextEglCreateContext == EGL10.EGL_NO_CONTEXT) {
            XLog.e(TAG, "eglCreateContext error", new Object[0]);
            return false;
        }
        int[] iArr4 = {R2.id.accessibility_custom_action_24, 1, R2.id.accessibility_custom_action_23, 1, R2.id.META};
        EGL10 egl14 = this.egl;
        Intrinsics.checkNotNull(egl14);
        EGLSurface eGLSurfaceEglCreatePbufferSurface = egl14.eglCreatePbufferSurface(this.eglDisplay, this.eglConfig, iArr4);
        Intrinsics.checkNotNullExpressionValue(eGLSurfaceEglCreatePbufferSurface, "eglCreatePbufferSurface(...)");
        this.eglSurface = eGLSurfaceEglCreatePbufferSurface;
        if (eGLSurfaceEglCreatePbufferSurface != EGL10.EGL_NO_SURFACE) {
            return true;
        }
        XLog.e(TAG, "eglCreatePbufferSurface error", new Object[0]);
        return false;
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void onDestroy() {
        EGL10 egl10 = this.egl;
        if (egl10 == null) {
            return;
        }
        Intrinsics.checkNotNull(egl10);
        egl10.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        if (this.eglSurface != EGL10.EGL_NO_SURFACE) {
            EGL10 egl11 = this.egl;
            Intrinsics.checkNotNull(egl11);
            egl11.eglDestroySurface(this.eglDisplay, this.eglSurface);
            EGLSurface EGL_NO_SURFACE = EGL10.EGL_NO_SURFACE;
            Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
            this.eglSurface = EGL_NO_SURFACE;
        }
        if (this.eglContext != EGL10.EGL_NO_CONTEXT) {
            EGL10 egl12 = this.egl;
            Intrinsics.checkNotNull(egl12);
            egl12.eglDestroyContext(this.eglDisplay, this.eglContext);
            EGLContext EGL_NO_CONTEXT = EGL10.EGL_NO_CONTEXT;
            Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
            this.eglContext = EGL_NO_CONTEXT;
        }
        if (this.eglDisplay != EGL10.EGL_NO_DISPLAY) {
            EGL10 egl13 = this.egl;
            Intrinsics.checkNotNull(egl13);
            egl13.eglTerminate(this.eglDisplay);
            EGLDisplay EGL_NO_DISPLAY = EGL10.EGL_NO_DISPLAY;
            Intrinsics.checkNotNullExpressionValue(EGL_NO_DISPLAY, "EGL_NO_DISPLAY");
            this.eglDisplay = EGL_NO_DISPLAY;
        }
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void makeCurrent() {
        EGL10 egl10 = this.egl;
        if (egl10 != null) {
            Intrinsics.checkNotNull(egl10);
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = this.eglSurface;
            if (egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
                return;
            }
            XLog.e(TAG, "makeCurrent failed", new Object[0]);
        }
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void setSharedContext(EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
        this.sharedEglContext = sharedContext;
    }
}
