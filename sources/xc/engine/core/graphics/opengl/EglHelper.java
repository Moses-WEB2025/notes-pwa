package xc.engine.core.graphics.opengl;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.GLUtils;
import defpackage.R2;

/* JADX INFO: loaded from: classes6.dex */
public class EglHelper {
    private static final String TAG = "EglHelper";
    public final int[] attributes;
    public final int[] c;
    public final EGLConfig[] mEGLConfigs;
    public EGLContext mEGLContext;
    public EGLDisplay mEGLDisplay;

    public EglHelper() {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        this.mEGLConfigs = eGLConfigArr;
        int[] iArr = {R2.id.action_sheet_arrow_view, 2, R2.id.META};
        this.attributes = iArr;
        int[] iArr2 = {R2.drawable.v8_theme_star_bg_3, 16, R2.drawable.v8_theme_star_bg_bottom, 8, R2.drawable.v8_theme_star_bg_top, 8, R2.drawable.v8_theme_star_grid_fg, 8, R2.drawable.v8_theme_white_share_bg, 8, R2.id.TOP_START, 4, R2.id.ALT, 4, R2.id.META};
        this.c = iArr2;
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.mEGLDisplay = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new IllegalStateException("gl display get error: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        }
        int[] iArr3 = new int[2];
        if (!EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr3, 0, iArr3, 1)) {
            throw new IllegalStateException("gl init error: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        }
        if (!EGL14.eglChooseConfig(this.mEGLDisplay, iArr2, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            throw new IllegalStateException("gl con error: " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
        }
        this.mEGLContext = EGL14.eglCreateContext(this.mEGLDisplay, eGLConfigArr[0], EGL14.EGL_NO_CONTEXT, iArr, 0);
        EGLContext eGLContext = EGL14.EGL_NO_CONTEXT;
    }
}
