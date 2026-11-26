package xc.richtext.view.img;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public class CsImageViewModel {
    private static ReadWriteLock sReadWriteLock;
    private CsImageView mCsImageView;
    public EngineBindContext mEngineBindContext;
    public SpannedImageManager.LoadRunnable mLoadRunable;
    public Paint mPaint;
    public SpannedData mSpannedData;

    public CsImageViewModel(CsImageView csImageView) {
        this.mCsImageView = csImageView;
        sReadWriteLock = new ReentrantReadWriteLock();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setDither(true);
    }

    public void refreshImageView() {
        RectF rectF;
        if (this.mSpannedData == null) {
            return;
        }
        SpannedImageManager.LoadRunnable loadRunnable = this.mLoadRunable;
        if (loadRunnable == null) {
            rectF = new RectF();
        } else {
            rectF = loadRunnable.mRectF;
        }
        if (rectF.width() != this.mSpannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider).width()) {
            loadBitmap(this.mSpannedData);
        }
    }

    private void loadBitmap(SpannedData spannedData) {
        SpannedImageManager.LoadRunnable loadRunnable = this.mLoadRunable;
        Bitmap bitmap = null;
        if (loadRunnable != null) {
            Bitmap bitmap2 = loadRunnable.mBitmap;
            this.mLoadRunable.isCancelled = true;
            this.mLoadRunable = null;
            bitmap = bitmap2;
        }
        SpannedImageManager.LoadRunnable loadRunnable2 = new SpannedImageManager.LoadRunnable(spannedData.mBitmapPath, spannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider), this.mCsImageView, sReadWriteLock, null);
        this.mLoadRunable = loadRunnable2;
        loadRunnable2.mBitmap = bitmap;
        SpannedImageManager.SPANNED_IMAGE_MANAGER.mFixedThreadPool.submit(this.mLoadRunable);
    }

    void release() {
        SpannedImageManager.LoadRunnable loadRunnable;
        if (this.mSpannedData.mSpannedStatus != 1 && (loadRunnable = this.mLoadRunable) != null) {
            loadRunnable.release();
            this.mLoadRunable = null;
        }
        this.mCsImageView = null;
        this.mSpannedData = null;
        this.mPaint = null;
        sReadWriteLock = null;
    }
}
