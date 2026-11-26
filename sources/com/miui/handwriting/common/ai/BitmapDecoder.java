package com.miui.handwriting.common.ai;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import miuix.core.util.IOUtils;

/* JADX INFO: loaded from: classes3.dex */
public class BitmapDecoder {
    private static final float MAX_GOOD_RATIO = 2.5f;
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
        FileNotFoundException e;
        if (this.mMetaLoaded) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream inputStream = null;
        try {
            inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(this.mUri);
            try {
                try {
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenInputStream;
                    IOUtils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                e = e2;
                Log.w(TAG, "Fail to open stream: " + this.mUri, e);
            }
        } catch (FileNotFoundException e3) {
            inputStreamOpenInputStream = null;
            e = e3;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly(inputStream);
            throw th;
        }
        IOUtils.closeQuietly(inputStreamOpenInputStream);
        this.mWidth = options.outWidth;
        this.mHeight = options.outHeight;
        this.mMimeType = options.outMimeType;
        this.mMetaLoaded = true;
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

    public Bitmap getScaledBitmap(int i, int i2, int i3) throws Throwable {
        Bitmap bitmapCreateScaledBitmap;
        computeScaleSize(i, i2);
        Bitmap bitmapClipBitmap = clipBitmap(this.mLastDecodeWidth, this.mLastDecodeHeight, i3, (int) this.mLastScaleRatio);
        if (bitmapClipBitmap == null || (bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapClipBitmap, this.mLastScaleWidth, this.mLastScaleHeight, true)) == bitmapClipBitmap) {
            return bitmapClipBitmap;
        }
        bitmapClipBitmap.recycle();
        return bitmapCreateScaledBitmap;
    }

    private Bitmap clipBitmap(int i, int i2, int i3, int i4) throws Throwable {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        InputStream inputStream = null;
        bitmapDecodeRegion = null;
        Bitmap bitmapDecodeRegion = null;
        InputStream inputStream2 = null;
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
                } catch (FileNotFoundException e) {
                    e = e;
                    bitmap = bitmapDecodeRegion;
                    inputStream = inputStreamOpenInputStream;
                    Log.w(TAG, "Fail to open stream: " + this.mUri, e);
                    IOUtils.closeQuietly(inputStream);
                    return bitmap;
                } catch (IOException e2) {
                    e = e2;
                    bitmap = bitmapDecodeRegion;
                    inputStream = inputStreamOpenInputStream;
                    Log.w(TAG, "Fail to decode stream: " + this.mUri, e);
                    IOUtils.closeQuietly(inputStream);
                    return bitmap;
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStreamOpenInputStream;
                    IOUtils.closeQuietly(inputStream2);
                    throw th;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                bitmap = null;
            } catch (IOException e4) {
                e = e4;
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
        int i6 = this.mLastDecodeHeight;
        int i7 = (int) ((i6 * i) / i3);
        this.mLastScaleHeight = i7;
        this.mLastExpectWidth = i;
        this.mLastExpectHeight = i2;
        if (i7 > 65500) {
            this.mLastScaleHeight = 65500;
            int i8 = (int) ((65500 * i3) / i6);
            this.mLastScaleWidth = i8;
            this.mLastExpectWidth = i8;
            this.mLastExpectHeight = 65500;
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
