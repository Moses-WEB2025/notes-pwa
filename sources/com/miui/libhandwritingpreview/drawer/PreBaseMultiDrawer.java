package com.miui.libhandwritingpreview.drawer;

import android.graphics.Bitmap;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import com.miui.libhandwritingpreview.util.PreDrawHelper;
import java.util.concurrent.locks.ReentrantLock;
import xc.engine.operate.canvas.draw.IMultiDrawer;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PreBaseMultiDrawer implements IMultiDrawer {
    public static final String TAG = "PreBaseMultiDrawer";
    protected volatile boolean isScaleDrawing;
    protected volatile boolean isScaleOperating;
    protected volatile ScaleInfo mBaseScaleInfo;
    public PreDrawHelper mDrawHelper;
    protected boolean mEnableScale;
    protected int mLoadDirection;
    protected final PenPreEngine mPenEngine;
    public final ReentrantLock mReentrantLock;
    protected volatile ScaleInfo mScaleInfo;
    protected volatile ScaleInfo mScrollingScaleInfo;
    protected volatile Bitmap mShapeBitmap;
    protected float mVisibleHeight;
    protected float mVisibleTop;
    protected float mVisibleWidth;
    protected boolean mIsVisible = true;
    protected boolean mIsInfinite = false;

    public PreBaseMultiDrawer(PenPreEngine penPreEngine) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mReentrantLock = reentrantLock;
        this.mBaseScaleInfo = new ScaleInfo();
        this.mScaleInfo = new ScaleInfo();
        this.mScrollingScaleInfo = new ScaleInfo();
        this.isScaleOperating = false;
        this.isScaleDrawing = false;
        this.mLoadDirection = 1;
        this.mPenEngine = penPreEngine;
        this.mDrawHelper = new PreDrawHelper(penPreEngine, reentrantLock);
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setLoadDirection(int i) {
        this.mLoadDirection = i;
        PreDrawHelper preDrawHelper = this.mDrawHelper;
        if (preDrawHelper != null) {
            preDrawHelper.mLoadDirection = i;
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void clearCanvas() {
        PreDrawHelper preDrawHelper = this.mDrawHelper;
        if (preDrawHelper == null) {
            return;
        }
        preDrawHelper.mLock.lock();
        try {
            if (this.mIsInfinite) {
                this.mDrawHelper.mPageItemsManager.clearCanvas();
            }
        } catch (Throwable th) {
            this.mDrawHelper.mLock.unlock();
            this.mDrawHelper.mPenEngine.mCanvasOperator.mCanvasOperateListener.onCanvasDataChanged(null, 11);
            throw th;
        }
    }

    @Override // xc.engine.operate.canvas.draw.IMultiDrawer
    public void setInfiniteCanvasMode(boolean z) {
        this.mIsInfinite = z;
    }
}
