package xc.richtext.view.img;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import xc.engine.api.CreationPageSDK;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: loaded from: classes3.dex */
public class CsImageDrawModel {
    private static final String RECEIVER_ACTION_RENDER = "com.miui.creation.render";
    public EngineBindContext mEngineBindContext;
    public Paint mPaint;
    private OnPreImageLoadCallback mPreImageLoadCallback;
    public SpannedLayoutModel mSpannedLayoutModel;
    public Bitmap mBitmap = null;
    public Canvas mCanvas = new Canvas();
    private final List<SpannedImageManager.LoadRunnable> mLoadRunnable = new ArrayList();
    private CountDownLatch mCountDownLatch = null;
    public final CopyOnWriteArrayList<BitmapBean> mBitmapBeanList = new CopyOnWriteArrayList<>();

    public interface OnPreImageLoadCallback {
        void onPreLoadFinish();

        void onRefreshImage(int i);
    }

    public CsImageDrawModel() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setDither(true);
    }

    public void refreshBitmap(SpannedData spannedData) {
        this.mCountDownLatch = new CountDownLatch(this.mBitmapBeanList.size());
        for (BitmapBean bitmapBean : this.mBitmapBeanList) {
            if (bitmapBean.isBlank || !bitmapBean.hasLoad.get()) {
                if (bitmapBean.mId == spannedData.mId) {
                    bitmapBean.updateBean(spannedData.getDrawRectF(this.mEngineBindContext.screenRenderProvider), spannedData.mDegrees, spannedData.mSpannedStatus);
                    if (bitmapBean.mBitmap == null || bitmapBean.isBlank) {
                        SpannedImageManager.LoadRunnable loadRunnable = new SpannedImageManager.LoadRunnable(bitmapBean.mPath, bitmapBean.mRectF, null, null, new SyncImageImageLoadCallback(bitmapBean));
                        this.mLoadRunnable.add(loadRunnable);
                        SpannedImageManager.SPANNED_IMAGE_MANAGER.mFixedThreadPool.submit(loadRunnable);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public void registerPreImageLoadCallback(OnPreImageLoadCallback onPreImageLoadCallback) {
        this.mPreImageLoadCallback = onPreImageLoadCallback;
    }

    public void releaseAll() {
        for (SpannedImageManager.LoadRunnable loadRunnable : this.mLoadRunnable) {
            if (loadRunnable != null) {
                loadRunnable.release();
            }
        }
        this.mLoadRunnable.clear();
        for (BitmapBean bitmapBean : this.mBitmapBeanList) {
            if (bitmapBean.mBitmap != null) {
                if (!bitmapBean.mBitmap.isRecycled()) {
                    bitmapBean.mBitmap.recycle();
                }
                bitmapBean.mBitmap = null;
            }
        }
        this.mBitmapBeanList.clear();
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mBitmap.recycle();
        this.mBitmap = null;
    }

    private class SyncImageImageLoadCallback implements ImageLoadCallback {
        public final BitmapBean mBitmapBean;

        public SyncImageImageLoadCallback(BitmapBean bitmapBean) {
            this.mBitmapBean = bitmapBean;
        }

        @Override // xc.richtext.view.img.ImageLoadCallback
        public void onLoadFinish(Bitmap bitmap, boolean z) {
            this.mBitmapBean.mBitmap = bitmap;
            boolean z2 = this.mBitmapBean.isBlank;
            this.mBitmapBean.isBlank = z;
            Intent intent = new Intent();
            intent.setAction(CsImageDrawModel.RECEIVER_ACTION_RENDER);
            LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).sendBroadcast(intent);
            if (CsImageDrawModel.this.mPreImageLoadCallback != null) {
                CsImageDrawModel.this.mPreImageLoadCallback.onPreLoadFinish();
                if (z2 && !z) {
                    CsImageDrawModel.this.mPreImageLoadCallback.onRefreshImage(this.mBitmapBean.mId);
                }
            }
            CsImageDrawModel.this.mCountDownLatch.countDown();
            if (CsImageDrawModel.this.mSpannedLayoutModel != null) {
                CsImageDrawModel.this.mSpannedLayoutModel.mSpannedEditLayout.postInvalidate();
            }
        }
    }
}
