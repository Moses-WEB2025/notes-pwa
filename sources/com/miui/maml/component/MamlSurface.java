package com.miui.maml.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import com.miui.maml.RendererController;
import com.miui.maml.ResourceLoader;
import com.miui.maml.ResourceManager;
import com.miui.maml.ScreenContext;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Variables;
import com.miui.maml.util.AssetsResourceLoader;
import com.miui.maml.util.FolderResourceLoader;
import com.miui.maml.util.HideSdkDependencyUtils;
import com.miui.maml.util.ZipResourceLoader;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class MamlSurface implements RendererController.IRenderable {
    public static final int MODE_ASSETS_FOLDER = 2;
    public static final int MODE_FOLDER = 3;
    public static final int MODE_ZIP = 1;
    private static final String TAG = "MamlSurface";
    private final ScreenElementRoot.OnExternCommandListener mCommandListener;
    private WallpaperService.Engine mEngine;
    private WeakReference<OnExternCommandListener> mExternCommandListener;
    private volatile boolean mFinished;
    private boolean mIsSuperWallpaper;
    private ScreenElementRoot mRoot;
    private SurfaceHolder mSurfaceHolder;

    public interface OnExternCommandListener {
        void onCommand(String str, Double d, String str2);
    }

    public MamlSurface(Context context, String str, int i, WallpaperService.Engine engine) {
        this(context, str, null, i, engine, false);
    }

    public MamlSurface(Context context, String str, int i, WallpaperService.Engine engine, boolean z) {
        this(context, str, null, i, engine, z);
    }

    public MamlSurface(Context context, String str, String str2, int i, WallpaperService.Engine engine, boolean z) {
        this.mCommandListener = new ScreenElementRoot.OnExternCommandListener() { // from class: com.miui.maml.component.MamlSurface.1
            @Override // com.miui.maml.ScreenElementRoot.OnExternCommandListener
            public void onCommand(String str3, Double d, String str4) {
                OnExternCommandListener onExternCommandListener;
                if (MamlSurface.this.mExternCommandListener == null || (onExternCommandListener = (OnExternCommandListener) MamlSurface.this.mExternCommandListener.get()) == null) {
                    return;
                }
                onExternCommandListener.onCommand(str3, d, str4);
            }
        };
        load(context, getResourceLoader(context, str, str2, i), engine, z);
    }

    public MamlSurface(Context context, ResourceLoader resourceLoader, WallpaperService.Engine engine) {
        this.mCommandListener = new ScreenElementRoot.OnExternCommandListener() { // from class: com.miui.maml.component.MamlSurface.1
            @Override // com.miui.maml.ScreenElementRoot.OnExternCommandListener
            public void onCommand(String str3, Double d, String str4) {
                OnExternCommandListener onExternCommandListener;
                if (MamlSurface.this.mExternCommandListener == null || (onExternCommandListener = (OnExternCommandListener) MamlSurface.this.mExternCommandListener.get()) == null) {
                    return;
                }
                onExternCommandListener.onCommand(str3, d, str4);
            }
        };
        load(context, resourceLoader, engine, false);
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.selfPause();
        }
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.selfResume();
        }
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        finish();
    }

    public ScreenElementRoot getRoot() {
        return this.mRoot;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            return screenElementRoot.onTouch(motionEvent);
        }
        return false;
    }

    @Override // com.miui.maml.RendererController.IRenderable
    public void doRender() {
        SurfaceHolder surfaceHolder;
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot == null || this.mSurfaceHolder == null) {
            return;
        }
        Canvas canvasLockHardwareCanvas = null;
        try {
            try {
                try {
                    try {
                        canvasLockHardwareCanvas = screenElementRoot.isUseHardwareCanvas() ? this.mSurfaceHolder.lockHardwareCanvas() : this.mSurfaceHolder.lockCanvas();
                        if (canvasLockHardwareCanvas != null) {
                            canvasLockHardwareCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                            this.mRoot.render(canvasLockHardwareCanvas);
                            if (canvasLockHardwareCanvas != null) {
                                surfaceHolder = this.mSurfaceHolder;
                                surfaceHolder.unlockCanvasAndPost(canvasLockHardwareCanvas);
                            }
                            this.mRoot.doneRender();
                            return;
                        }
                        Log.d(TAG, ":( fail to lock canvas.");
                        if (canvasLockHardwareCanvas != null) {
                            try {
                                this.mSurfaceHolder.unlockCanvasAndPost(canvasLockHardwareCanvas);
                            } catch (Exception e) {
                                Log.e(TAG, "unlockCanvasAndPost exception.", e);
                            }
                        }
                    } catch (Exception e2) {
                        Log.e(TAG, "render exception.", e2);
                        if (0 != 0) {
                            surfaceHolder = this.mSurfaceHolder;
                        }
                    }
                } catch (OutOfMemoryError e3) {
                    Log.e(TAG, "render oom error.", e3);
                    if (0 != 0) {
                        surfaceHolder = this.mSurfaceHolder;
                    }
                }
            } catch (Exception e4) {
                Log.e(TAG, "unlockCanvasAndPost exception.", e4);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    this.mSurfaceHolder.unlockCanvasAndPost(null);
                } catch (Exception e5) {
                    Log.e(TAG, "unlockCanvasAndPost exception.", e5);
                }
            }
            throw th;
        }
    }

    public SurfaceControl getParentSurfaceControl() {
        WallpaperService.Engine engine;
        if (Build.VERSION.SDK_INT != 29 || (engine = this.mEngine) == null) {
            return null;
        }
        return HideSdkDependencyUtils.SurfaceControl_getInstance_with_engine(engine);
    }

    public SurfaceHolder getSurfaceHolder() {
        return this.mSurfaceHolder;
    }

    public boolean isLoaded() {
        return this.mRoot != null;
    }

    public void setConfig(String str) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setConfig(str);
        }
    }

    public void setAutoDarkenWallpaper(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setAutoDarkenWallpaper(true);
        }
    }

    public void requestUpdate() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.requestUpdate();
        }
    }

    public void setSaveConfigViaProvider(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setSaveConfigViaProvider(z);
        }
    }

    public void setSaveConfigOnlyInPause(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setSaveConfigViaProvider(z);
        }
    }

    public void doneRender() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.doneRender();
        }
    }

    public void sendCommand(String str) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.onCommand(str);
        }
    }

    public void setOnExternCommandListener(OnExternCommandListener onExternCommandListener) {
        this.mExternCommandListener = onExternCommandListener == null ? null : new WeakReference<>(onExternCommandListener);
    }

    public Variables getVariables() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            return screenElementRoot.getVariables();
        }
        return null;
    }

    public boolean isIsSuperWallpaper() {
        return this.mIsSuperWallpaper;
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    private void load(Context context, ResourceLoader resourceLoader, WallpaperService.Engine engine, boolean z) {
        SurfaceHolder surfaceHolder;
        this.mSurfaceHolder = engine.getSurfaceHolder();
        this.mEngine = engine;
        this.mIsSuperWallpaper = z;
        if (resourceLoader != null) {
            ScreenElementRoot screenElementRoot = new ScreenElementRoot(new ScreenContext(context.getApplicationContext(), new ResourceManager(resourceLoader)));
            screenElementRoot.setMamlSurface(this);
            if (screenElementRoot.load()) {
                this.mRoot = screenElementRoot;
                screenElementRoot.setMamlSurfaceOnExternCommandListener(this.mCommandListener);
                this.mRoot.setRenderControllerRenderable(this);
                this.mRoot.attachToVsync();
                this.mRoot.selfInit();
                if (!this.mRoot.isTransparentSurface() || (surfaceHolder = this.mSurfaceHolder) == null) {
                    return;
                }
                surfaceHolder.setFormat(-2);
            }
        }
    }

    private ResourceLoader getResourceLoader(Context context, String str, String str2, int i) {
        if (i == 1) {
            return new ZipResourceLoader(str, str2);
        }
        if (i == 2) {
            return new AssetsResourceLoader(context, str);
        }
        if (i != 3) {
            return null;
        }
        return new FolderResourceLoader(str);
    }

    private void finish() {
        if (this.mFinished) {
            return;
        }
        this.mFinished = true;
        this.mEngine = null;
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.selfFinish();
            this.mRoot.detachFromVsync();
            this.mRoot.getVariables().reset();
            ResourceManager resourceManager = this.mRoot.getContext().mResourceManager;
            ResourceManager.clear();
            this.mRoot = null;
        }
    }
}
