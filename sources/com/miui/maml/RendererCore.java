package com.miui.maml;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.util.Log;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class RendererCore {
    private static final String LOG_TAG = "RendererCore";
    private boolean mCleaned;
    private MultipleRenderable mMultipleRenderable;
    private WeakReference<OnReleaseListener> mOnReleaseListener;
    private boolean mReleased;
    private ScreenElementRoot mRoot;

    public interface OnReleaseListener {
        boolean OnRendererCoreReleased(RendererCore rendererCore);
    }

    public RendererCore(ScreenElementRoot screenElementRoot) {
        MultipleRenderable multipleRenderable = new MultipleRenderable();
        this.mMultipleRenderable = multipleRenderable;
        this.mRoot = screenElementRoot;
        screenElementRoot.setRenderControllerRenderable(multipleRenderable);
        this.mRoot.selfInit();
        this.mRoot.attachToVsync();
        this.mRoot.requestUpdate();
    }

    public void setOnReleaseListener(OnReleaseListener onReleaseListener) {
        this.mOnReleaseListener = new WeakReference<>(onReleaseListener);
    }

    public synchronized void addRenderable(RendererController.IRenderable iRenderable) {
        if (this.mCleaned) {
            return;
        }
        this.mMultipleRenderable.add(iRenderable);
        Log.d(LOG_TAG, "add: " + iRenderable + " size:" + this.mMultipleRenderable.size());
        this.mRoot.selfResume();
        this.mReleased = false;
    }

    public synchronized void removeRenderable(RendererController.IRenderable iRenderable) {
        WeakReference<OnReleaseListener> weakReference;
        if (this.mCleaned) {
            return;
        }
        this.mMultipleRenderable.remove(iRenderable);
        Log.d(LOG_TAG, "remove: " + iRenderable + " size:" + this.mMultipleRenderable.size());
        if (this.mMultipleRenderable.size() == 0) {
            this.mRoot.selfPause();
            if (!this.mReleased && (weakReference = this.mOnReleaseListener) != null && weakReference.get() != null && this.mOnReleaseListener.get().OnRendererCoreReleased(this)) {
                cleanUp();
            }
            this.mReleased = true;
        }
    }

    public synchronized void pauseRenderable(RendererController.IRenderable iRenderable) {
        if (this.mCleaned) {
            return;
        }
        if (this.mMultipleRenderable.pause(iRenderable) == 0) {
            Log.d(LOG_TAG, "self pause: " + toString());
            this.mRoot.selfPause();
        }
    }

    public synchronized void resumeRenderable(RendererController.IRenderable iRenderable) {
        if (this.mCleaned) {
            return;
        }
        this.mMultipleRenderable.resume(iRenderable);
        Log.d(LOG_TAG, "self resume: " + toString());
        this.mRoot.selfResume();
    }

    public ScreenElementRoot getRoot() {
        return this.mRoot;
    }

    public void render(Canvas canvas) {
        if (this.mCleaned) {
            return;
        }
        this.mRoot.render(canvas);
    }

    public void cleanUp() {
        this.mCleaned = true;
        Log.d(LOG_TAG, "cleanUp: " + toString());
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.detachFromVsync();
            this.mRoot.selfFinish();
            this.mRoot = null;
        }
    }

    protected void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setColorFilter(colorFilter);
        }
    }
}
