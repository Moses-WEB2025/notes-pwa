package xc.engine.core.graphics.engine.gl;

import android.opengl.EGL14;
import android.opengl.EGLContext;

/* JADX INFO: loaded from: classes6.dex */
public class EGLContextManager {
    private static EGLContext sharedContext = EGL14.EGL_NO_CONTEXT;
    private static final Object lock = new Object();

    public static void setSharedContext(EGLContext eGLContext) {
        synchronized (lock) {
            sharedContext = eGLContext;
        }
    }

    public static EGLContext getSharedContext() {
        EGLContext eGLContext;
        synchronized (lock) {
            eGLContext = sharedContext;
        }
        return eGLContext;
    }
}
