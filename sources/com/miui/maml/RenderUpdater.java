package com.miui.maml;

import android.os.Handler;
import android.os.SystemClock;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public abstract class RenderUpdater implements RendererController.ISelfUpdateRenderable {
    private boolean mAutoCleanup;
    private long mCreateTime;
    private long mDelay;
    private Handler mHandler;
    protected long mLastUpdateTime;
    protected long mNextUpdateInterval;
    private boolean mPaused;
    protected boolean mPendingRender;
    private ScreenElementRoot mRoot;
    private Runnable mRunUpdater;
    private boolean mSignaled;
    private boolean mStarted;
    private Runnable mUpdater;

    protected abstract void doRenderImp();

    public RenderUpdater(ScreenElementRoot screenElementRoot, Handler handler) {
        this(screenElementRoot, handler, false);
    }

    public RenderUpdater(ScreenElementRoot screenElementRoot, Handler handler, boolean z) {
        this.mUpdater = new Runnable() { // from class: com.miui.maml.RenderUpdater.1
            @Override // java.lang.Runnable
            public void run() {
                RenderUpdater.this.mSignaled = false;
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                RenderUpdater renderUpdater = RenderUpdater.this;
                renderUpdater.mNextUpdateInterval = renderUpdater.mRoot.updateIfNeeded(jElapsedRealtime);
                RenderUpdater.this.mLastUpdateTime = jElapsedRealtime;
                if (RenderUpdater.this.mPendingRender || RenderUpdater.this.mPaused || RenderUpdater.this.mSignaled || RenderUpdater.this.mNextUpdateInterval >= Long.MAX_VALUE) {
                    return;
                }
                RenderUpdater.this.mHandler.postDelayed(RenderUpdater.this.mUpdater, RenderUpdater.this.mNextUpdateInterval);
            }
        };
        this.mRunUpdater = new Runnable() { // from class: com.miui.maml.RenderUpdater.2
            @Override // java.lang.Runnable
            public void run() {
                RenderUpdater.this.doRunUpdater();
                RenderUpdater.this.mStarted = true;
            }
        };
        this.mRoot = screenElementRoot;
        this.mHandler = handler;
        this.mAutoCleanup = z;
    }

    public void setStartDelay(long j, long j2) {
        this.mCreateTime = j;
        this.mDelay = j2;
        if (j2 <= 0) {
            this.mStarted = true;
        }
    }

    public void setAutoCleanup(boolean z) {
        this.mAutoCleanup = z;
    }

    @Override // com.miui.maml.RendererController.IRenderable
    public final void doRender() {
        this.mPendingRender = true;
        doRenderImp();
    }

    public void runUpdater() {
        long jCheckDelay = this.mStarted ? 0L : checkDelay();
        if (jCheckDelay > 0) {
            if (this.mHandler.hasCallbacks(this.mRunUpdater)) {
                return;
            }
            this.mHandler.postDelayed(this.mRunUpdater, jCheckDelay);
        } else {
            doRunUpdater();
            this.mStarted = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRunUpdater() {
        if (this.mSignaled) {
            return;
        }
        this.mSignaled = true;
        this.mHandler.removeCallbacks(this.mUpdater);
        this.mHandler.post(this.mUpdater);
    }

    private long checkDelay() {
        if (this.mDelay <= 0) {
            return 0L;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mCreateTime;
        long j = this.mDelay;
        if (jElapsedRealtime < j) {
            return j - jElapsedRealtime;
        }
        return 0L;
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    @Override // com.miui.maml.RendererController.ISelfUpdateRenderable
    public void triggerUpdate() {
        runUpdater();
    }

    @Override // com.miui.maml.RendererController.ISelfUpdateRenderable
    public void forceUpdate() {
        runUpdater();
    }

    public void doneRender() {
        this.mPendingRender = false;
        if (this.mPaused || this.mSignaled) {
            return;
        }
        long j = this.mNextUpdateInterval;
        if (j < Long.MAX_VALUE) {
            this.mHandler.postDelayed(this.mUpdater, j - (SystemClock.elapsedRealtime() - this.mLastUpdateTime));
        }
    }

    public void cleanUp() {
        this.mHandler.removeCallbacks(this.mUpdater);
        this.mPaused = true;
        this.mRoot.selfFinish();
        this.mSignaled = false;
    }

    public void onPause() {
        this.mRoot.selfPause();
        this.mSignaled = false;
        this.mPaused = true;
    }

    public void onResume() {
        this.mPaused = false;
        this.mRoot.selfResume();
        runUpdater();
    }

    public void init() {
        this.mPaused = false;
        this.mRoot.setRenderControllerRenderable(this);
        this.mRoot.selfInit();
    }

    protected void finalize() throws Throwable {
        if (this.mAutoCleanup) {
            cleanUp();
        }
        super.finalize();
    }
}
