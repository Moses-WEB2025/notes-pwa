package com.miui.notes.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.view.Gravity;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import java.io.File;
import java.io.InputStream;
import miuix.core.util.IOUtils;

/* JADX INFO: loaded from: classes4.dex */
public class BitmapDecoder {
    private static final float MAX_GOOD_RATIO = 2.5f;
    private static final int MAX_SAFE_DECODE_HEIGHT = 4096;
    private static final float MIN_GOOD_RATIO = 0.75f;
    private static final String TAG = "BitmapDecoder";
    private final int MAX_IMAGE_HEIGHT = 65500;
    private Context mContext;
    private int mHeight;
    private int mLastDecodeHeight;
    private int mLastDecodeWidth;
    private int mLastExpectHeight;
    private int mLastExpectWidth;
    private int mLastScaleHeight;
    private float mLastScaleRatio;
    private int mLastScaleWidth;
    private boolean mMetaLoaded;
    private String mMimeType;
    private Uri mUri;
    private int mWidth;

    public BitmapDecoder(Context context, Uri uri) {
        this.mContext = context;
        this.mUri = uri;
    }

    public BitmapDecoder(Context context, String str) {
        this.mContext = context;
        this.mUri = Uri.fromFile(new File(str));
    }

    public Uri getUri() {
        return this.mUri;
    }

    public int getWidth() throws Throwable {
        loadMeta();
        return this.mWidth;
    }

    public int getHeight() throws Throwable {
        loadMeta();
        return this.mHeight;
    }

    public String getMimeType() throws Throwable {
        loadMeta();
        return this.mMimeType;
    }

    private void loadMeta() throws Throwable {
        InputStream inputStreamOpenInputStream;
        Exception e;
        if (this.mMetaLoaded) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream inputStream = null;
        try {
            try {
                inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(this.mUri);
                try {
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                } catch (Exception e2) {
                    e = e2;
                    Logger.INSTANCE.w(TAG, "Fail to open stream: " + this.mUri + ",exception:" + e);
                }
            } catch (Exception e3) {
                inputStreamOpenInputStream = null;
                e = e3;
            } catch (Throwable th) {
                th = th;
                IOUtils.closeQuietly(inputStream);
                throw th;
            }
            IOUtils.closeQuietly(inputStreamOpenInputStream);
            this.mWidth = options.outWidth;
            this.mHeight = options.outHeight;
            this.mMimeType = options.outMimeType;
            this.mMetaLoaded = true;
        } catch (Throwable th2) {
            th = th2;
            inputStream = inputStreamOpenInputStream;
            IOUtils.closeQuietly(inputStream);
            throw th;
        }
    }

    public int getScaleWidth(int i, int i2) throws Throwable {
        computeScaleSize(i, i2);
        return this.mLastScaleWidth;
    }

    public int getScaleHeight(int i, int i2) throws Throwable {
        computeScaleSize(i, i2);
        return this.mLastScaleHeight;
    }

    public Bitmap getScaledBitmap(int i, int i2) {
        return getScaledBitmap(i, i2, 17);
    }

    public Bitmap getScaledBitmap(int i, int i2, int i3) {
        Bitmap bitmapScaleBitmapManually;
        computeScaleSize(i, i2);
        Bitmap bitmapClipBitmap = clipBitmap(this.mLastDecodeWidth, this.mLastDecodeHeight, i3, (int) this.mLastScaleRatio);
        if (bitmapClipBitmap == null || (bitmapScaleBitmapManually = scaleBitmapManually(bitmapClipBitmap, this.mLastScaleWidth, this.mLastScaleHeight)) == bitmapClipBitmap) {
            return bitmapClipBitmap;
        }
        bitmapClipBitmap.recycle();
        return bitmapScaleBitmapManually;
    }

    private Bitmap scaleBitmapManually(Bitmap bitmap, int i, int i2) {
        if (bitmap == null || i <= 0 || i2 <= 0) {
            return null;
        }
        int height = bitmap.getHeight();
        if (height > 4096) {
            Logger.INSTANCE.d(TAG, "scaleBitmapManually: , sourceHeight=" + height);
            return scaleBitmapInBlocks(bitmap, i, i2);
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            if (bitmapCreateBitmap == null) {
                Logger.INSTANCE.w(TAG, "scaleBitmapManually: createBitmap failed, return source");
                return bitmap;
            }
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            float width = i / bitmap.getWidth();
            float height2 = i2 / bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.setScale(width, height2);
            canvas.drawBitmap(bitmap, matrix, new Paint(3));
            return bitmapCreateBitmap;
        } catch (OutOfMemoryError unused) {
            Logger.INSTANCE.w(TAG, "scaleBitmapManually: OOM, return source");
            return bitmap;
        }
    }

    private Bitmap scaleBitmapInBlocks(Bitmap bitmap, int i, int i2) throws Throwable {
        Bitmap bitmapCreateTargetBitmap = createTargetBitmap(i, i2);
        if (bitmapCreateTargetBitmap == null) {
            Logger.INSTANCE.w(TAG, "scaleBitmapInBlocks: createBitmap failed, return source");
            return bitmap;
        }
        processAllBlocks(bitmap, i, i2 / bitmap.getHeight(), new Canvas(bitmapCreateTargetBitmap), new Paint(3));
        return bitmapCreateTargetBitmap;
    }

    private Bitmap createTargetBitmap(int i, int i2) {
        try {
            return Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError unused) {
            Logger.INSTANCE.w(TAG, "scaleBitmapInBlocks: OOM on createBitmap");
            return null;
        }
    }

    private void processAllBlocks(Bitmap bitmap, int i, float f, Canvas canvas, Paint paint) throws Throwable {
        int height = bitmap.getHeight();
        for (int i2 = 0; i2 < height; i2 += 4096) {
            processImageBlock(bitmap, i2, Math.min(4096, height - i2), i, f, canvas, paint);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v8 */
    private void processImageBlock(Bitmap bitmap, int i, int i2, int i3, float f, Canvas canvas, Paint paint) throws Throwable {
        Bitmap bitmapCreateBitmap;
        Bitmap bitmapCreateScaledBitmap;
        try {
            try {
                bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, i, bitmap.getWidth(), i2);
                if (bitmapCreateBitmap == null) {
                    safeRecycleBitmap(bitmapCreateBitmap, bitmap);
                    safeRecycleBitmap(null, null);
                    return;
                }
                int i4 = (int) (i * f);
                int i5 = (int) (i2 * f);
                if (i5 <= 0) {
                    safeRecycleBitmap(bitmapCreateBitmap, bitmap);
                    safeRecycleBitmap(null, null);
                    return;
                }
                try {
                    bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, i3, i5, true);
                    if (bitmapCreateScaledBitmap != null) {
                        try {
                            canvas.drawBitmap(bitmapCreateScaledBitmap, 0.0f, i4, paint);
                        } catch (OutOfMemoryError unused) {
                            Logger.INSTANCE.w(TAG, "scaleBitmapInBlocks: OOM at block y=" + i);
                            safeRecycleBitmap(bitmapCreateBitmap, bitmap);
                            safeRecycleBitmap(bitmapCreateScaledBitmap, null);
                            return;
                        }
                    }
                    safeRecycleBitmap(bitmapCreateBitmap, bitmap);
                    safeRecycleBitmap(bitmapCreateScaledBitmap, null);
                } catch (OutOfMemoryError unused2) {
                    bitmapCreateScaledBitmap = null;
                } catch (Throwable th) {
                    th = th;
                    i2 = 0;
                    safeRecycleBitmap(bitmapCreateBitmap, bitmap);
                    safeRecycleBitmap(i2, null);
                    throw th;
                }
            } catch (OutOfMemoryError unused3) {
                bitmapCreateScaledBitmap = null;
                bitmapCreateBitmap = null;
            } catch (Throwable th2) {
                th = th2;
                i2 = 0;
                bitmapCreateBitmap = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private void safeRecycleBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == null || bitmap == bitmap2 || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    private Bitmap clipBitmap(int i, int i2, int i3, int i4) throws Throwable {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        InputStream inputStream = null;
        Bitmap bitmapDecodeRegion = null;
        inputStream = null;
        try {
            try {
                InputStream inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(this.mUri);
                try {
                    options.inSampleSize = i4;
                    BitmapRegionDecoder bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance(inputStreamOpenInputStream, false);
                    Rect rect = new Rect();
                    Gravity.apply(i3, i, i2, new Rect(0, 0, getWidth(), getHeight()), rect);
                    bitmapDecodeRegion = bitmapRegionDecoderNewInstance.decodeRegion(rect, options);
                    bitmapRegionDecoderNewInstance.recycle();
                    IOUtils.closeQuietly(inputStreamOpenInputStream);
                    return bitmapDecodeRegion;
                } catch (Exception e) {
                    e = e;
                    bitmap = bitmapDecodeRegion;
                    inputStream = inputStreamOpenInputStream;
                    Logger.INSTANCE.w(TAG, "Fail to open stream: " + this.mUri + "with exception:" + e);
                    IOUtils.closeQuietly(inputStream);
                    return bitmap;
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenInputStream;
                    IOUtils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                bitmap = null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void computeScaleSize(int i, int i2) throws Throwable {
        if (this.mLastExpectWidth == i && this.mLastExpectHeight == i2) {
            return;
        }
        loadMeta();
        this.mLastScaleWidth = i;
        int i3 = this.mWidth;
        float f = i3 / i;
        this.mLastScaleRatio = f;
        this.mLastDecodeWidth = i3;
        int i4 = this.mHeight;
        this.mLastDecodeHeight = i4;
        if (i2 > 0) {
            int i5 = (int) (i2 * f);
            this.mLastDecodeHeight = i5;
            if (i5 > i4) {
                this.mLastDecodeHeight = i4;
            }
        }
        this.mLastScaleHeight = (int) ((this.mLastDecodeHeight * i) / i3);
        this.mLastExpectWidth = i;
        this.mLastExpectHeight = i2;
        int iDip2px = 65500 - (UIUtils.dip2px(NoteApp.getInstance(), NoteApp.getInstance().getResources().getDimension(R.dimen.theme_white_share_frame_top_space)) * 2);
        if (this.mLastScaleHeight >= iDip2px) {
            this.mLastScaleHeight = iDip2px;
            int i6 = (int) ((iDip2px * this.mLastDecodeWidth) / this.mLastDecodeHeight);
            this.mLastScaleWidth = i6;
            this.mLastExpectWidth = i6;
            this.mLastExpectHeight = iDip2px;
        }
    }

    public int getPolishedScaledHeight(int i) throws Throwable {
        Point pointComputePolishedSize = computePolishedSize();
        if (pointComputePolishedSize != null) {
            return (pointComputePolishedSize.y * i) / pointComputePolishedSize.x;
        }
        return 0;
    }

    private Point computePolishedSize() throws Throwable {
        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        float f = width;
        float f2 = height;
        float f3 = f / f2;
        if (f3 < 0.75f || f3 > MAX_GOOD_RATIO) {
            if (width > height) {
                width = (int) (f2 * MAX_GOOD_RATIO);
            } else {
                height = (int) (f / 0.75f);
            }
        }
        return new Point(width, height);
    }

    public Bitmap getPolishedScaledBitmap(int i) throws Throwable {
        Point pointComputePolishedSize = computePolishedSize();
        if (pointComputePolishedSize == null) {
            return null;
        }
        Bitmap bitmapClipBitmap = clipBitmap(pointComputePolishedSize.x, pointComputePolishedSize.y, 17, (int) Math.ceil(((double) getWidth()) / ((double) i)));
        if (bitmapClipBitmap != null) {
            if (getWidth() < i) {
                i = getWidth();
            }
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapClipBitmap, i, (bitmapClipBitmap.getHeight() * i) / bitmapClipBitmap.getWidth(), false);
            if (bitmapClipBitmap != bitmapCreateScaledBitmap) {
                bitmapClipBitmap.recycle();
                return bitmapCreateScaledBitmap;
            }
        }
        return bitmapClipBitmap;
    }

    public Bitmap getSquareBitmap(int i) throws Throwable {
        int iMin;
        Bitmap bitmapCreateScaledBitmap;
        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        if (width > height) {
            width = height;
        }
        Bitmap bitmapClipBitmap = clipBitmap(width, width, 17, width / i);
        if (bitmapClipBitmap == null || bitmapClipBitmap == (bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapClipBitmap, (iMin = Math.min(i, getWidth())), iMin, false))) {
            return bitmapClipBitmap;
        }
        bitmapClipBitmap.recycle();
        return bitmapCreateScaledBitmap;
    }
}
