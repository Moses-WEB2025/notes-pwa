package xc.richtext.view.img;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import xc.engine.R;
import xc.engine.api.CreationPageSDK;

/* JADX INFO: loaded from: classes3.dex */
public class SpannedImageManager {
    public static final SpannedImageManager SPANNED_IMAGE_MANAGER = new SpannedImageManager();
    public final ExecutorService mFixedThreadPool = Executors.newFixedThreadPool(3);

    public static class LoadRunnable implements Runnable {
        public ImageLoadCallback mImageLoadCallback;
        public String mImagePath;
        private final ReadWriteLock mReadWriteLock;
        public RectF mRectF;
        public final WeakReference<View> mViewRef;
        public Bitmap mBitmap = null;
        public volatile boolean isNeedRelease = false;
        public volatile boolean isCancelled = false;

        public LoadRunnable(String str, RectF rectF, View view, ReadWriteLock readWriteLock, ImageLoadCallback imageLoadCallback) {
            this.mRectF = rectF;
            this.mImagePath = str;
            this.mViewRef = new WeakReference<>(view);
            this.mReadWriteLock = readWriteLock;
            this.mImageLoadCallback = imageLoadCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            Bitmap bitmapDecodeFile;
            if (this.isCancelled) {
                return;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            File file = new File(this.mImagePath);
            Log.d("SpannedImageManager", "load image path = " + this.mImagePath + ", exists = " + file.exists());
            if (!file.exists()) {
                loadPlaceholderImage();
                return;
            }
            BitmapFactory.decodeFile(this.mImagePath, options);
            if (options.outWidth == 0 || options.outHeight == 0) {
                bitmapDecodeFile = null;
            } else {
                float fWidth = this.mRectF.width();
                options.inJustDecodeBounds = false;
                int i = 1;
                while (i < Math.round(options.outWidth / fWidth)) {
                    i <<= 1;
                }
                options.inSampleSize = i;
                bitmapDecodeFile = BitmapFactory.decodeFile(this.mImagePath, options);
            }
            this.mBitmap = bitmapDecodeFile;
            if (bitmapDecodeFile == null) {
                notifyLoadFinish(null, false);
                return;
            }
            this.isNeedRelease = true;
            if (this.isCancelled) {
                synchronized (this) {
                    releaseRes();
                    this.isNeedRelease = false;
                }
            } else {
                View view = this.mViewRef.get();
                if (view != null) {
                    view.postInvalidate();
                }
                notifyLoadFinish(this.mBitmap, false);
            }
        }

        private void notifyLoadFinish(Bitmap bitmap, boolean z) {
            ImageLoadCallback imageLoadCallback = this.mImageLoadCallback;
            if (imageLoadCallback != null) {
                imageLoadCallback.onLoadFinish(bitmap, z);
            }
        }

        private void loadPlaceholderImage() {
            Drawable drawable = CreationPageSDK.sApplication.getResources().getDrawable(R.drawable.place_holder);
            if (drawable != null) {
                int iWidth = (int) this.mRectF.width();
                int iHeight = (int) this.mRectF.height();
                if (iWidth == 0 || iHeight == 0) {
                    notifyLoadFinish(null, false);
                    return;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iWidth, iHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                this.isNeedRelease = true;
                notifyLoadFinish(bitmapCreateBitmap, true);
            }
        }

        public void release() {
            this.isCancelled = true;
            if (this.isNeedRelease) {
                synchronized (this) {
                    releaseRes();
                    this.isNeedRelease = false;
                }
            }
        }

        private void releaseRes() {
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                ReadWriteLock readWriteLock = this.mReadWriteLock;
                if (readWriteLock != null) {
                    readWriteLock.writeLock().lock();
                    try {
                        this.mBitmap.recycle();
                        this.mReadWriteLock.writeLock().unlock();
                    } catch (Throwable th) {
                        this.mReadWriteLock.writeLock().unlock();
                        throw th;
                    }
                } else {
                    this.mBitmap.recycle();
                }
            }
            this.mBitmap = null;
        }
    }
}
