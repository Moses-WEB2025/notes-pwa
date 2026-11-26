package xc.engine.core.graphics.engine.gl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import xc.engine.core.graphics.engine.task.BaseGLThread;

/* JADX INFO: compiled from: GLEnv.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0005H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006\u0018"}, d2 = {"Lxc/engine/core/graphics/engine/gl/GLEnv;", "Lxc/engine/core/graphics/engine/task/BaseGLThread$IGLEnv;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sharedEglContext", "Landroid/opengl/EGLContext;", "eglContext", "eglDisplay", "Landroid/opengl/EGLDisplay;", "eglSurface", "Landroid/opengl/EGLSurface;", "eglConfig", "", "Landroid/opengl/EGLConfig;", "[Landroid/opengl/EGLConfig;", "initGLEnv", "", "onDestroy", "", "makeCurrent", "setSharedContext", "sharedContext", "Ljavax/microedition/khronos/egl/EGLContext;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GLEnv implements BaseGLThread.IGLEnv {
    private static final String TAG = "GLEnv";
    private EGLConfig[] eglConfig;
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface;
    private EGLContext sharedEglContext = EGL14.EGL_NO_CONTEXT;

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void setSharedContext(javax.microedition.khronos.egl.EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
    }

    public GLEnv() {
        EGLContext EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
        this.eglContext = EGL_NO_CONTEXT;
        EGLDisplay EGL_NO_DISPLAY = EGL14.EGL_NO_DISPLAY;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_DISPLAY, "EGL_NO_DISPLAY");
        this.eglDisplay = EGL_NO_DISPLAY;
        EGLSurface EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
        this.eglSurface = EGL_NO_SURFACE;
        this.eglConfig = new EGLConfig[1];
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public boolean initGLEnv() {
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        Intrinsics.checkNotNullExpressionValue(eGLDisplayEglGetDisplay, "eglGetDisplay(...)");
        this.eglDisplay = eGLDisplayEglGetDisplay;
        if (Intrinsics.areEqual(eGLDisplayEglGetDisplay, EGL14.EGL_NO_DISPLAY)) {
            Log.e(TAG, "xl- eglGetDisplay error");
            return false;
        }
        int[] iArr = new int[2];
        EGL14.eglInitialize(this.eglDisplay, iArr, 0, iArr, 1);
        String str = TAG;
        Log.d(str, "xl- egl version " + iArr[0] + StringUtils.SPACE + iArr[1]);
        EGL14.eglChooseConfig(this.eglDisplay, new int[]{R2.drawable.v8_theme_star_bg_3, 32, R2.drawable.v8_theme_star_bg_bottom, 8, R2.drawable.v8_theme_star_bg_top, 8, R2.drawable.v8_theme_star_grid_fg, 8, R2.drawable.v8_theme_white_share_bg, 8, R2.id.TOP_START, 64, R2.id.ALT, 1, R2.id.META}, 0, this.eglConfig, 0, 1, new int[1], 0);
        EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(this.eglDisplay, this.eglConfig[0], this.sharedEglContext, new int[]{R2.id.action_sheet_arrow_view, 3, R2.id.META}, 0);
        Intrinsics.checkNotNullExpressionValue(eGLContextEglCreateContext, "eglCreateContext(...)");
        this.eglContext = eGLContextEglCreateContext;
        if (Intrinsics.areEqual(eGLContextEglCreateContext, EGL14.EGL_NO_CONTEXT)) {
            Log.e(str, "xl- eglCreateContext error");
            return false;
        }
        EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(this.eglDisplay, this.eglConfig[0], new int[]{R2.id.accessibility_custom_action_24, 1, R2.id.accessibility_custom_action_23, 1, R2.id.META}, 0);
        Intrinsics.checkNotNullExpressionValue(eGLSurfaceEglCreatePbufferSurface, "eglCreatePbufferSurface(...)");
        this.eglSurface = eGLSurfaceEglCreatePbufferSurface;
        return true;
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void onDestroy() {
        EGL14.eglMakeCurrent(this.eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
        if (!Intrinsics.areEqual(this.eglSurface, EGL14.EGL_NO_SURFACE)) {
            EGL14.eglDestroySurface(this.eglDisplay, this.eglSurface);
            EGLSurface EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
            Intrinsics.checkNotNullExpressionValue(EGL_NO_SURFACE, "EGL_NO_SURFACE");
            this.eglSurface = EGL_NO_SURFACE;
        }
        if (!Intrinsics.areEqual(this.eglContext, EGL14.EGL_NO_CONTEXT)) {
            EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
            EGLContext EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
            Intrinsics.checkNotNullExpressionValue(EGL_NO_CONTEXT, "EGL_NO_CONTEXT");
            this.eglContext = EGL_NO_CONTEXT;
        }
        if (Intrinsics.areEqual(this.eglDisplay, EGL14.EGL_NO_DISPLAY)) {
            return;
        }
        EGL14.eglTerminate(this.eglDisplay);
        EGLDisplay EGL_NO_DISPLAY = EGL14.EGL_NO_DISPLAY;
        Intrinsics.checkNotNullExpressionValue(EGL_NO_DISPLAY, "EGL_NO_DISPLAY");
        this.eglDisplay = EGL_NO_DISPLAY;
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void makeCurrent() {
        EGLDisplay eGLDisplay = this.eglDisplay;
        EGLSurface eGLSurface = this.eglSurface;
        if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
            return;
        }
        Log.e(TAG, "xl- makeCurrent failed");
    }

    @Override // xc.engine.core.graphics.engine.task.BaseGLThread.IGLEnv
    public void setSharedContext(EGLContext sharedContext) {
        Intrinsics.checkNotNullParameter(sharedContext, "sharedContext");
        this.sharedEglContext = sharedContext;
    }
}
