package com.miui.maml.component;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.miui.maml.RenderUpdater;
import com.miui.maml.ScreenElementRoot;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class MamlDrawable extends Drawable {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "MamlDrawable";
    private static final int RENDER_TIMEOUT = 100;
    private Handler mHandler;
    private int mHeight;
    private int mIntrinsicHeight;
    private int mIntrinsicWidth;
    private Runnable mInvalidateSelf;
    private boolean mPaused;
    private Runnable mRenderTimeout;
    private ScreenElementRoot mRoot;
    private RenderUpdater mUpdater;
    private int mWidth;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public MamlDrawable(ScreenElementRoot screenElementRoot) {
        this(screenElementRoot, false);
    }

    public MamlDrawable(ScreenElementRoot screenElementRoot, boolean z) {
        this.mPaused = true;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mRenderTimeout = new Runnable() { // from class: com.miui.maml.component.MamlDrawable.1
            @Override // java.lang.Runnable
            public void run() {
                MamlDrawable.this.doPause();
            }
        };
        this.mInvalidateSelf = new Runnable() { // from class: com.miui.maml.component.MamlDrawable.2
            @Override // java.lang.Runnable
            public void run() {
                MamlDrawable.this.invalidateSelf();
            }
        };
        this.mRoot = screenElementRoot;
        setIntrinsicSize((int) screenElementRoot.getWidth(), (int) this.mRoot.getHeight());
        RenderUpdater renderUpdater = new RenderUpdater(this.mRoot, new Handler(), z) { // from class: com.miui.maml.component.MamlDrawable.3
            @Override // com.miui.maml.RenderUpdater
            public void doRenderImp() {
                MamlDrawable.this.mHandler.removeCallbacks(MamlDrawable.this.mRenderTimeout);
                MamlDrawable.this.mHandler.postDelayed(MamlDrawable.this.mRenderTimeout, 100L);
                MamlDrawable.this.mHandler.post(MamlDrawable.this.mInvalidateSelf);
            }
        };
        this.mUpdater = renderUpdater;
        renderUpdater.init();
        this.mUpdater.runUpdater();
    }

    public MamlDrawable setAutoCleanup(boolean z) {
        this.mUpdater.setAutoCleanup(z);
        return this;
    }

    public void setIntrinsicSize(int i, int i2) {
        this.mIntrinsicWidth = i;
        this.mIntrinsicHeight = i2;
    }

    public void cleanUp() {
        logd("cleanUp: ");
        this.mUpdater.cleanUp();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.mWidth = i3 - i;
        this.mHeight = i4 - i2;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mHandler.removeCallbacks(this.mRenderTimeout);
        doResume();
        try {
            int iSave = canvas.save();
            canvas.translate(getBounds().left, getBounds().top);
            canvas.scale(this.mWidth / this.mIntrinsicWidth, this.mHeight / this.mIntrinsicHeight, 0.0f, 0.0f);
            this.mRoot.render(canvas);
            canvas.restoreToCount(iSave);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.toString());
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            Log.e(LOG_TAG, e2.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPause() {
        if (this.mPaused) {
            return;
        }
        logd("doPause: ");
        this.mPaused = true;
        this.mUpdater.onPause();
    }

    private void doResume() {
        if (this.mPaused) {
            logd("doResume: ");
            this.mPaused = false;
            this.mUpdater.onResume();
        }
    }

    public ScreenElementRoot getRoot() {
        return this.mRoot;
    }

    private void logd(CharSequence charSequence) {
        Log.d(LOG_TAG, ((Object) charSequence) + "  [" + toString() + "]");
    }
}
