package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLDebugHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import defpackage.R2;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes3.dex */
public class GLTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLTextureView";
    private static final GLThreadManager sGLThreadManager = new GLThreadManager();
    private int mColorSpace;
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    protected GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private Renderer mRenderer;
    private final WeakReference<GLTextureView> mThisWeakRef;

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int i);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    public interface Renderer {
        boolean onDrawFrame(GL10 gl10);

        void onEngineFinished();

        void onSurfaceChanged(GL10 gl10, int i, int i2);

        void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig);

        void onSurfaceDestroyed();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public GLTextureView(Context context) {
        super(context);
        this.mThisWeakRef = new WeakReference<>(this);
        init();
    }

    public GLTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mThisWeakRef = new WeakReference<>(this);
        init();
    }

    protected void finalize() throws Throwable {
        try {
            GLThread gLThread = this.mGLThread;
            if (gLThread != null) {
                gLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    private void init() {
        super.setSurfaceTextureListener(this);
    }

    public void setGLWrapper(GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setDebugFlags(int i) {
        this.mDebugFlags = i;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.mPreserveEGLContextOnPause = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        if (renderer != null) {
            checkRenderThreadState();
            if (this.mEGLConfigChooser == null) {
                this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
            }
            if (this.mEGLContextFactory == null) {
                this.mEGLContextFactory = new DefaultContextFactory();
            }
            if (this.mEGLWindowSurfaceFactory == null) {
                this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
            }
            this.mRenderer = renderer;
            GLThread gLThread = new GLThread(this.mThisWeakRef);
            this.mGLThread = gLThread;
            gLThread.start();
        }
    }

    public void setEGLContextFactory(EGLContextFactory eGLContextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setEGLConfigChooser(EGLConfigChooser eGLConfigChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(z));
    }

    public void setEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new ComponentSizeChooser(i, i2, i3, i4, i5, i6));
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    public void setColorSpace(int i) {
        checkRenderThreadState();
        this.mColorSpace = i;
    }

    public void setRenderMode(int i) {
        this.mGLThread.setRenderMode(i);
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    @Override // android.view.TextureView
    @Deprecated
    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        if (this.mRenderer == null) {
            super.setSurfaceTextureListener(surfaceTextureListener);
        }
        Log.e(TAG, "setSurfaceTextureListener preserved, setRenderer() instead?");
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mGLThread.surfaceCreated();
        onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mGLThread.onWindowResize(i, i2);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mGLThread.surfaceDestroyed();
        Renderer renderer = this.mRenderer;
        if (renderer == null) {
            return true;
        }
        renderer.onSurfaceDestroyed();
        return true;
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable runnable) {
        this.mGLThread.queueEvent(runnable);
    }

    @Override // android.view.TextureView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            GLThread gLThread = this.mGLThread;
            int renderMode = gLThread != null ? gLThread.getRenderMode() : 1;
            GLThread gLThread2 = new GLThread(this.mThisWeakRef);
            this.mGLThread = gLThread2;
            if (renderMode != 1) {
                gLThread2.setRenderMode(renderMode);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = R2.id.action_sheet_arrow_view;
        }

        @Override // com.miui.libhandwritingpage.view.GLTextureView.EGLContextFactory
        public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = {this.EGL_CONTEXT_CLIENT_VERSION, GLTextureView.this.mEGLContextClientVersion, R2.id.META};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (GLTextureView.this.mEGLContextClientVersion == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        @Override // com.miui.libhandwritingpage.view.GLTextureView.EGLContextFactory
        public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                return;
            }
            Log.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
            EglHelper.throwEglException("eglDestroyContex", egl10.eglGetError());
        }
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        @Override // com.miui.libhandwritingpage.view.GLTextureView.EGLWindowSurfaceFactory
        public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj, int i) {
            int[] iArr;
            if (i > 0) {
                try {
                    iArr = new int[]{R2.id.action_sheet_dim_bg, i, R2.id.META};
                } catch (IllegalArgumentException e) {
                    Log.e(GLTextureView.TAG, "eglCreateWindowSurface", e);
                    return null;
                }
            } else {
                iArr = null;
            }
            return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, iArr);
        }

        @Override // com.miui.libhandwritingpage.view.GLTextureView.EGLWindowSurfaceFactory
        public void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] iArr) {
            this.mConfigSpec = filterConfigSpec(iArr);
        }

        @Override // com.miui.libhandwritingpage.view.GLTextureView.EGLConfigChooser
        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, i, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig eGLConfigChooseConfig = chooseConfig(egl10, eGLDisplay, eGLConfigArr);
            if (eGLConfigChooseConfig != null) {
                return eGLConfigChooseConfig;
            }
            throw new IllegalArgumentException("No config chosen");
        }

        private int[] filterConfigSpec(int[] iArr) {
            if (GLTextureView.this.mEGLContextClientVersion != 2 && GLTextureView.this.mEGLContextClientVersion != 3) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length + 2];
            int i = length - 1;
            System.arraycopy(iArr, 0, iArr2, 0, i);
            iArr2[i] = 12352;
            if (GLTextureView.this.mEGLContextClientVersion == 2) {
                iArr2[length] = 4;
            } else {
                iArr2[length] = 64;
            }
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{R2.drawable.v8_theme_white_share_bg, i, R2.drawable.v8_theme_star_grid_fg, i2, R2.drawable.v8_theme_star_bg_top, i3, R2.drawable.v8_theme_star_bg_bottom, i4, R2.drawable.v8_theme_white_share_bg_1, i5, R2.drawable.v8_theme_white_share_bg_2, i6, R2.id.META});
            this.mValue = new int[1];
            this.mRedSize = i;
            this.mGreenSize = i2;
            this.mBlueSize = i3;
            this.mAlphaSize = i4;
            this.mDepthSize = i5;
            this.mStencilSize = i6;
        }

        @Override // com.miui.libhandwritingpage.view.GLTextureView.BaseConfigChooser
        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            int length = eGLConfigArr.length;
            int i = 0;
            while (i < length) {
                EGLConfig eGLConfig = eGLConfigArr[i];
                ComponentSizeChooser componentSizeChooser = this;
                EGL10 egl11 = egl10;
                EGLDisplay eGLDisplay2 = eGLDisplay;
                int iFindConfigAttrib = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, R2.drawable.v8_theme_white_share_bg_1, 0);
                int iFindConfigAttrib2 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, R2.drawable.v8_theme_white_share_bg_2, 0);
                if (iFindConfigAttrib >= componentSizeChooser.mDepthSize && iFindConfigAttrib2 >= componentSizeChooser.mStencilSize) {
                    int iFindConfigAttrib3 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, R2.drawable.v8_theme_white_share_bg, 0);
                    int iFindConfigAttrib4 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, R2.drawable.v8_theme_star_grid_fg, 0);
                    int iFindConfigAttrib5 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, R2.drawable.v8_theme_star_bg_top, 0);
                    int iFindConfigAttrib6 = componentSizeChooser.findConfigAttrib(egl11, eGLDisplay2, eGLConfig, R2.drawable.v8_theme_star_bg_bottom, 0);
                    if (iFindConfigAttrib3 == componentSizeChooser.mRedSize && iFindConfigAttrib4 == componentSizeChooser.mGreenSize && iFindConfigAttrib5 == componentSizeChooser.mBlueSize && iFindConfigAttrib6 == componentSizeChooser.mAlphaSize) {
                        return eGLConfig;
                    }
                }
                i++;
                this = componentSizeChooser;
                egl10 = egl11;
                eGLDisplay = eGLDisplay2;
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue) ? this.mValue[0] : i2;
        }
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean z) {
            super(8, 8, 8, 0, z ? 16 : 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static class EglHelper {
        private static String Tag = "EglHelper";
        EGL10 mEgl;
        EGLConfig mEglConfig;
        public EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLTextureView> mGLTextureViewWeakRef;

        public EglHelper(WeakReference<GLTextureView> weakReference) {
            this.mGLTextureViewWeakRef = weakReference;
        }

        public void start() {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            this.mEgl = egl10;
            EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.mEglDisplay = eGLDisplayEglGetDisplay;
            if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            GLTextureView gLTextureView = this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                this.mEglConfig = gLTextureView.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                this.mEglContext = gLTextureView.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            } else {
                this.mEglConfig = null;
                this.mEglContext = null;
            }
            EGLContext eGLContext = this.mEglContext;
            if (eGLContext == null || eGLContext == EGL10.EGL_NO_CONTEXT) {
                this.mEglContext = null;
                throwEglException("createContext");
            }
            this.mEglSurface = null;
        }

        public boolean createSurface() {
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            }
            if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }
            if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }
            destroySurfaceImp();
            GLTextureView gLTextureView = this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                this.mEglSurface = gLTextureView.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, gLTextureView.getSurfaceTexture(), gLTextureView.mColorSpace);
            } else {
                this.mEglSurface = null;
            }
            EGLSurface eGLSurface = this.mEglSurface;
            if (eGLSurface != null && eGLSurface != EGL10.EGL_NO_SURFACE) {
                EGL10 egl10 = this.mEgl;
                EGLDisplay eGLDisplay = this.mEglDisplay;
                EGLSurface eGLSurface2 = this.mEglSurface;
                if (egl10.eglMakeCurrent(eGLDisplay, eGLSurface2, eGLSurface2, this.mEglContext)) {
                    return true;
                }
                logEglErrorAsWarning(Tag, "eglMakeCurrent", this.mEgl.eglGetError());
                return false;
            }
            if (this.mEgl.eglGetError() == 12299) {
                Log.e(Tag, "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
            }
            return false;
        }

        GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLTextureView gLTextureView = this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                if (gLTextureView.mGLWrapper != null) {
                    gl = gLTextureView.mGLWrapper.wrap(gl);
                }
                if ((gLTextureView.mDebugFlags & 3) != 0) {
                    return GLDebugHelper.wrap(gl, (gLTextureView.mDebugFlags & 1) == 0 ? 0 : 1, (gLTextureView.mDebugFlags & 2) != 0 ? new LogWriter() : null);
                }
            }
            return gl;
        }

        public int swap() {
            return !this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface) ? this.mEgl.eglGetError() : R2.drawable.v8_theme_antique_decoration_1;
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            EGLSurface eGLSurface = this.mEglSurface;
            if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                return;
            }
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            GLTextureView gLTextureView = this.mGLTextureViewWeakRef.get();
            if (gLTextureView != null) {
                gLTextureView.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
            }
            this.mEglSurface = null;
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLTextureView gLTextureView = this.mGLTextureViewWeakRef.get();
                if (gLTextureView != null) {
                    gLTextureView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            EGLDisplay eGLDisplay = this.mEglDisplay;
            if (eGLDisplay != null) {
                this.mEgl.eglTerminate(eGLDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String str) {
            throwEglException(str, this.mEgl.eglGetError());
        }

        public static void throwEglException(String str, int i) {
            throw new RuntimeException(formatEglError(str, i));
        }

        public static void logEglErrorAsWarning(String str, String str2, int i) {
            Log.w(str, formatEglError(str2, i));
        }

        public static String formatEglError(String str, int i) {
            return str + " failed: " + eglGetErrorString(i);
        }

        static String eglGetErrorString(int i) {
            switch (i) {
                case R2.drawable.v8_theme_antique_decoration_1 /* 12288 */:
                    return "EGL_SUCCESS";
                case R2.drawable.v8_theme_antique_decoration_2 /* 12289 */:
                    return "EGL_NOT_INITIALIZED";
                case R2.drawable.v8_theme_antique_decoration_3 /* 12290 */:
                    return "EGL_BAD_ACCESS";
                case R2.drawable.v8_theme_antique_decoration_4 /* 12291 */:
                    return "EGL_BAD_ALLOC";
                case R2.drawable.v8_theme_antique_h_1 /* 12292 */:
                    return "EGL_BAD_ATTRIBUTE";
                case R2.drawable.v8_theme_antique_h_2 /* 12293 */:
                    return "EGL_BAD_CONFIG";
                case R2.drawable.v8_theme_antique_h_3 /* 12294 */:
                    return "EGL_BAD_CONTEXT";
                case R2.drawable.v8_theme_antique_v /* 12295 */:
                    return "EGL_BAD_CURRENT_SURFACE";
                case R2.drawable.v8_theme_arty_bg_bottom_left /* 12296 */:
                    return "EGL_BAD_DISPLAY";
                case R2.drawable.v8_theme_arty_bg_bottom_right /* 12297 */:
                    return "EGL_BAD_MATCH";
                case R2.drawable.v8_theme_arty_bg_left /* 12298 */:
                    return "EGL_BAD_NATIVE_PIXMAP";
                case R2.drawable.v8_theme_arty_bg_left_inset /* 12299 */:
                    return "EGL_BAD_NATIVE_WINDOW";
                case R2.drawable.v8_theme_arty_bg_right /* 12300 */:
                    return "EGL_BAD_PARAMETER";
                case R2.drawable.v8_theme_arty_bg_right_inset /* 12301 */:
                    return "EGL_BAD_SURFACE";
                case R2.drawable.v8_theme_arty_bg_top_left /* 12302 */:
                    return "EGL_CONTEXT_LOST";
                default:
                    return "0x" + Integer.toHexString(i);
            }
        }
    }

    public static class GLThread extends Thread {
        public EglHelper mEglHelper;
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLTextureView> mGLTextureViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private boolean mPaused;
        private boolean mRenderComplete;
        private boolean mRequestPaused;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private int mWidth = 0;
        private int mHeight = 0;
        private int mRenderMode = 1;
        private boolean mRequestRender = true;
        private boolean mWantRenderNotification = false;
        private ArrayList<Runnable> mEventQueue = new ArrayList<>();
        private boolean mSizeChanged = true;
        private Runnable mFinishDrawingRunnable = null;

        GLThread(WeakReference<GLTextureView> weakReference) {
            this.mGLTextureViewWeakRef = weakReference;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException unused) {
            } finally {
                GLTextureView.sGLThreadManager.threadExiting(this);
            }
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLTextureView.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:104:0x0176, code lost:
        
            r9 = com.miui.libhandwritingpage.view.GLTextureView.sGLThreadManager;
         */
        /* JADX WARN: Code restructure failed: missing block: B:105:0x017a, code lost:
        
            monitor-enter(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:107:0x017c, code lost:
        
            r19.mFinishedCreatingEglSurface = true;
            com.miui.libhandwritingpage.view.GLTextureView.sGLThreadManager.notifyAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:108:0x0185, code lost:
        
            monitor-exit(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:109:0x0186, code lost:
        
            r9 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:113:0x018b, code lost:
        
            if (r10 == false) goto L115;
         */
        /* JADX WARN: Code restructure failed: missing block: B:114:0x018d, code lost:
        
            r7 = (javax.microedition.khronos.opengles.GL10) r19.mEglHelper.createGL();
            r10 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:115:0x0197, code lost:
        
            if (r8 == false) goto L120;
         */
        /* JADX WARN: Code restructure failed: missing block: B:116:0x0199, code lost:
        
            r0 = r19.mGLTextureViewWeakRef.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:117:0x01a1, code lost:
        
            if (r0 == null) goto L119;
         */
        /* JADX WARN: Code restructure failed: missing block: B:118:0x01a3, code lost:
        
            r0.mRenderer.onSurfaceCreated(r7, r19.mEglHelper.mEglConfig);
         */
        /* JADX WARN: Code restructure failed: missing block: B:119:0x01ae, code lost:
        
            r8 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:120:0x01af, code lost:
        
            if (r11 == false) goto L125;
         */
        /* JADX WARN: Code restructure failed: missing block: B:121:0x01b1, code lost:
        
            r0 = r19.mGLTextureViewWeakRef.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:122:0x01b9, code lost:
        
            if (r0 == null) goto L124;
         */
        /* JADX WARN: Code restructure failed: missing block: B:123:0x01bb, code lost:
        
            r0.mRenderer.onSurfaceChanged(r7, r13, r14);
         */
        /* JADX WARN: Code restructure failed: missing block: B:124:0x01c2, code lost:
        
            r11 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:125:0x01c3, code lost:
        
            r0 = r19.mGLTextureViewWeakRef.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:126:0x01cb, code lost:
        
            if (r0 == null) goto L130;
         */
        /* JADX WARN: Code restructure failed: missing block: B:127:0x01cd, code lost:
        
            r0.mRenderer.onDrawFrame(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:128:0x01d4, code lost:
        
            if (r6 == null) goto L130;
         */
        /* JADX WARN: Code restructure failed: missing block: B:129:0x01d6, code lost:
        
            r6.run();
            r6 = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:130:0x01da, code lost:
        
            r0 = r19.mEglHelper.swap();
         */
        /* JADX WARN: Code restructure failed: missing block: B:131:0x01e2, code lost:
        
            if (r0 == 12288) goto L144;
         */
        /* JADX WARN: Code restructure failed: missing block: B:133:0x01e6, code lost:
        
            if (r0 == 12302) goto L143;
         */
        /* JADX WARN: Code restructure failed: missing block: B:134:0x01e8, code lost:
        
            com.miui.libhandwritingpage.view.GLTextureView.EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", r0);
            r2 = com.miui.libhandwritingpage.view.GLTextureView.sGLThreadManager;
         */
        /* JADX WARN: Code restructure failed: missing block: B:135:0x01f5, code lost:
        
            monitor-enter(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:136:0x01f6, code lost:
        
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:137:0x01f7, code lost:
        
            r19.mSurfaceIsBad = true;
            com.miui.libhandwritingpage.view.GLTextureView.sGLThreadManager.notifyAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:138:0x0200, code lost:
        
            monitor-exit(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:143:0x0205, code lost:
        
            r0 = true;
            r3 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:144:0x0208, code lost:
        
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:145:0x020b, code lost:
        
            r3 = r3;
         */
        /* JADX WARN: Code restructure failed: missing block: B:146:0x020d, code lost:
        
            if (r12 == false) goto L183;
         */
        /* JADX WARN: Code restructure failed: missing block: B:147:0x020f, code lost:
        
            r4 = r0;
            r12 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x014d, code lost:
        
            if (r15 == null) goto L92;
         */
        /* JADX WARN: Code restructure failed: missing block: B:90:0x014f, code lost:
        
            r15.run();
            r15 = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:92:0x0156, code lost:
        
            if (r9 == false) goto L113;
         */
        /* JADX WARN: Code restructure failed: missing block: B:94:0x015e, code lost:
        
            if (r19.mEglHelper.createSurface() != false) goto L104;
         */
        /* JADX WARN: Code restructure failed: missing block: B:95:0x0160, code lost:
        
            r16 = com.miui.libhandwritingpage.view.GLTextureView.sGLThreadManager;
         */
        /* JADX WARN: Code restructure failed: missing block: B:96:0x0164, code lost:
        
            monitor-enter(r16);
         */
        /* JADX WARN: Code restructure failed: missing block: B:98:0x0166, code lost:
        
            r19.mFinishedCreatingEglSurface = true;
            r19.mSurfaceIsBad = true;
            com.miui.libhandwritingpage.view.GLTextureView.sGLThreadManager.notifyAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:99:0x0171, code lost:
        
            monitor-exit(r16);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void guardedRun() throws java.lang.InterruptedException {
            /*
                Method dump skipped, instruction units count: 591
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.libhandwritingpage.view.GLTextureView.GLThread.guardedRun():void");
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        private boolean readyToDraw() {
            if (this.mPaused || !this.mHasSurface || this.mSurfaceIsBad || this.mWidth <= 0 || this.mHeight <= 0) {
                return false;
            }
            return this.mRequestRender || this.mRenderMode == 1;
        }

        public void setRenderMode(int i) {
            if (i >= 0 && i <= 1) {
                synchronized (GLTextureView.sGLThreadManager) {
                    this.mRenderMode = i;
                    GLTextureView.sGLThreadManager.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("renderMode");
        }

        public int getRenderMode() {
            int i;
            synchronized (GLTextureView.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRequestRender = true;
                GLTextureView.sGLThreadManager.notifyAll();
            }
        }

        public void requestRenderAndNotify(Runnable runnable) {
            synchronized (GLTextureView.sGLThreadManager) {
                if (Thread.currentThread() != this) {
                    this.mWantRenderNotification = true;
                    this.mRequestRender = true;
                    this.mRenderComplete = false;
                    this.mFinishDrawingRunnable = runnable;
                    GLTextureView.sGLThreadManager.notifyAll();
                }
            }
        }

        public void surfaceCreated() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mHasSurface = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRequestPaused = true;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int i, int i2) {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mWidth = i;
                this.mHeight = i2;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                if (Thread.currentThread() != this) {
                    GLTextureView.sGLThreadManager.notifyAll();
                    while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                        try {
                            GLTextureView.sGLThreadManager.wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }

        public void requestExitAndWait() {
            synchronized (GLTextureView.sGLThreadManager) {
                this.mShouldExit = true;
                GLTextureView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        GLTextureView.sGLThreadManager.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLTextureView.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable runnable) {
            if (runnable != null) {
                synchronized (GLTextureView.sGLThreadManager) {
                    this.mEventQueue.add(runnable);
                    GLTextureView.sGLThreadManager.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("r must not be null");
        }
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            flushBuilder();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            flushBuilder();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v(GLTextureView.TAG, this.mBuilder.toString());
                StringBuilder sb = this.mBuilder;
                sb.delete(0, sb.length());
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";

        private GLThreadManager() {
        }

        public synchronized void threadExiting(GLThread gLThread) {
            gLThread.mExited = true;
            notifyAll();
        }

        public void releaseEglContextLocked(GLThread gLThread) {
            notifyAll();
        }
    }
}
