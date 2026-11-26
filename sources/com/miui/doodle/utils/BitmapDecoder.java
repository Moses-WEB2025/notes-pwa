package com.miui.doodle.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.Gravity;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.core.util.IOUtils;

/* JADX INFO: compiled from: BitmapDecoder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 =2\u00020\u0001:\u0001=B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\u0006\u0010\nJ\b\u0010&\u001a\u00020'H\u0002J\u0016\u0010(\u001a\u00020\u00102\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010J\u0016\u0010+\u001a\u00020\u00102\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010J\u0018\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010J \u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\u0006\u0010.\u001a\u00020\u0010J*\u0010/\u001a\u0004\u0018\u00010-2\u0006\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\u00102\u0006\u0010.\u001a\u00020\u00102\u0006\u00102\u001a\u00020\u0010H\u0002J\u0018\u00103\u001a\u00020'2\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010H\u0002J\u000e\u00104\u001a\u00020\u00102\u0006\u0010)\u001a\u00020\u0010J\n\u00105\u001a\u0004\u0018\u000106H\u0002J\u0010\u00107\u001a\u0004\u0018\u00010-2\u0006\u0010)\u001a\u00020\u0010J\u0010\u00108\u001a\u0004\u0018\u00010-2\u0006\u00109\u001a\u00020\u0010J\u0010\u0010:\u001a\u00020'2\b\u0010;\u001a\u0004\u0018\u00010<R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001e\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\"\u0010 R\u0013\u0010#\u001a\u0004\u0018\u00010\t8F¢\u0006\u0006\u001a\u0004\b$\u0010%¨\u0006>"}, d2 = {"Lcom/miui/doodle/utils/BitmapDecoder;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", CallMethod.ARG_URI, "Landroid/net/Uri;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/net/Uri;)V", "path", "", "(Landroid/content/Context;Ljava/lang/String;)V", "mContext", "value", "getUri", "()Landroid/net/Uri;", "mWidth", "", "mHeight", "mMimeType", "mMetaLoaded", "", "mLastExpectWidth", "mLastExpectHeight", "mLastScaleRatio", "", "mLastScaleWidth", "mLastScaleHeight", "mLastDecodeWidth", "mLastDecodeHeight", "mOrientation", Layer.KEY_WIDTH, "getWidth", "()I", Layer.KEY_HEIGHT, "getHeight", "mimeType", "getMimeType", "()Ljava/lang/String;", "loadMeta", "", "getScaleWidth", "expectWidth", "expectHeight", "getScaleHeight", "getScaledBitmap", "Landroid/graphics/Bitmap;", "gravity", "clipBitmap", "clipWidth", "clipHeight", "sampleSize", "computeScaleSize", "getPolishedScaledHeight", "computePolishedSize", "Landroid/graphics/Point;", "getPolishedScaledBitmap", "getSquareBitmap", "expectedSize", "releaseTmpCanvas", "canvas", "Landroid/graphics/Canvas;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BitmapDecoder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final float MAX_GOOD_RATIO = 2.5f;
    private static final float MIN_GOOD_RATIO = 0.75f;
    private static final String TAG = "BitmapDecoder";
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
    private int mOrientation;
    private int mWidth;
    private Uri uri;

    public final Uri getUri() {
        return this.uri;
    }

    public BitmapDecoder(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.mContext = context;
        this.uri = uri;
    }

    public BitmapDecoder(Context context, String path) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        this.mContext = context;
        Uri uriFromFile = Uri.fromFile(new File(path));
        Intrinsics.checkNotNullExpressionValue(uriFromFile, "fromFile(...)");
        this.uri = uriFromFile;
    }

    public final int getWidth() throws Throwable {
        loadMeta();
        return this.mWidth;
    }

    public final int getHeight() throws Throwable {
        loadMeta();
        return this.mHeight;
    }

    public final String getMimeType() throws Throwable {
        loadMeta();
        return this.mMimeType;
    }

    private final void loadMeta() throws Throwable {
        InputStream inputStreamOpenInputStream;
        Exception e;
        if (this.mMetaLoaded) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream inputStream = null;
        try {
            inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(this.uri);
            try {
                try {
                    Intrinsics.checkNotNull(inputStreamOpenInputStream);
                    this.mOrientation = new ExifInterface(inputStreamOpenInputStream).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
                    IOUtils.closeQuietly(inputStreamOpenInputStream);
                    inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(this.uri);
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenInputStream;
                    IOUtils.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                Logger.INSTANCE.w(TAG, "Fail to open stream: " + this.uri + e);
            }
        } catch (Exception e3) {
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

    public final int getScaleWidth(int expectWidth, int expectHeight) throws Throwable {
        computeScaleSize(expectWidth, expectHeight);
        return this.mLastScaleWidth;
    }

    public final int getScaleHeight(int expectWidth, int expectHeight) throws Throwable {
        computeScaleSize(expectWidth, expectHeight);
        return this.mLastScaleHeight;
    }

    public final Bitmap getScaledBitmap(int expectWidth, int expectHeight) {
        return getScaledBitmap(expectWidth, expectHeight, 17);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v3, types: [int] */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.graphics.Canvas] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.miui.doodle.utils.BitmapDecoder] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v4, types: [int] */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7, types: [android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r9v8, types: [android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r9v9 */
    public final Bitmap getScaledBitmap(int expectWidth, int expectHeight, int gravity) throws Throwable {
        ?? r10;
        ?? CreateBitmap;
        Canvas canvas;
        computeScaleSize(expectWidth, expectHeight);
        Bitmap bitmapClipBitmap = clipBitmap(this.mLastDecodeWidth, this.mLastDecodeHeight, gravity, (int) this.mLastScaleRatio);
        if (bitmapClipBitmap != null) {
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapClipBitmap, this.mLastScaleWidth, this.mLastScaleHeight, true);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
            if (!Intrinsics.areEqual(bitmapCreateScaledBitmap, bitmapClipBitmap)) {
                bitmapClipBitmap.recycle();
                bitmapClipBitmap = bitmapCreateScaledBitmap;
            }
        }
        if (this.mOrientation == 1 || bitmapClipBitmap == null) {
            return bitmapClipBitmap;
        }
        int width = bitmapClipBitmap.getWidth();
        int height = bitmapClipBitmap.getHeight();
        Matrix matrix = new Matrix();
        int i = this.mOrientation;
        if (i != 3) {
            if (i != 6) {
                CreateBitmap = width;
                r10 = height;
                if (i == 8) {
                    float f = width / 2;
                    matrix.postRotate(270.0f, f, f);
                }
            } else {
                float f2 = height / 2;
                matrix.postRotate(90.0f, f2, f2);
            }
            r10 = width;
            CreateBitmap = height;
        } else {
            matrix.postRotate(180.0f, width / 2, height / 2);
            CreateBitmap = width;
            r10 = height;
        }
        ?? r2 = 0;
        r2 = 0;
        try {
            try {
                CreateBitmap = Bitmap.createBitmap(CreateBitmap, r10, Bitmap.Config.ARGB_8888);
                try {
                    canvas = new Canvas(CreateBitmap);
                    try {
                        canvas.drawBitmap(bitmapClipBitmap, matrix, null);
                        bitmapClipBitmap.recycle();
                        releaseTmpCanvas(canvas);
                        return CreateBitmap;
                    } catch (OutOfMemoryError e) {
                        e = e;
                        Logger.INSTANCE.e(TAG, "Fail to rotateBitmap " + e);
                        bitmapClipBitmap.recycle();
                        if (canvas != null) {
                            releaseTmpCanvas(canvas);
                        }
                        return null;
                    }
                } catch (OutOfMemoryError e2) {
                    e = e2;
                    canvas = null;
                } catch (Throwable unused) {
                    bitmapClipBitmap.recycle();
                    if (r2 != 0) {
                        releaseTmpCanvas(r2);
                    }
                    return CreateBitmap;
                }
            } catch (OutOfMemoryError e3) {
                e = e3;
                canvas = null;
            } catch (Throwable unused2) {
                CreateBitmap = 0;
            }
        } catch (Throwable unused3) {
            r2 = r10;
        }
    }

    private final Bitmap clipBitmap(int clipWidth, int clipHeight, int gravity, int sampleSize) throws Throwable {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        InputStream inputStream = null;
        bitmapDecodeRegion = null;
        Bitmap bitmapDecodeRegion = null;
        InputStream inputStream2 = null;
        inputStream = null;
        try {
            try {
                InputStream inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(this.uri);
                try {
                    options.inSampleSize = sampleSize;
                    Intrinsics.checkNotNull(inputStreamOpenInputStream);
                    BitmapRegionDecoder bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance(inputStreamOpenInputStream, false);
                    Rect rect = new Rect();
                    Gravity.apply(gravity, clipWidth, clipHeight, new Rect(0, 0, getWidth(), getHeight()), rect);
                    bitmapDecodeRegion = bitmapRegionDecoderNewInstance != null ? bitmapRegionDecoderNewInstance.decodeRegion(rect, options) : null;
                    if (bitmapRegionDecoderNewInstance != null) {
                        bitmapRegionDecoderNewInstance.recycle();
                    }
                    IOUtils.closeQuietly(inputStreamOpenInputStream);
                    return bitmapDecodeRegion;
                } catch (FileNotFoundException e) {
                    e = e;
                    bitmap = bitmapDecodeRegion;
                    inputStream = inputStreamOpenInputStream;
                    Logger.INSTANCE.w(TAG, "Fail to open stream: " + this.uri + e);
                    IOUtils.closeQuietly(inputStream);
                    return bitmap;
                } catch (IOException e2) {
                    e = e2;
                    bitmap = bitmapDecodeRegion;
                    inputStream = inputStreamOpenInputStream;
                    Logger.INSTANCE.w(TAG, "Fail to decode stream: " + this.uri + e);
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

    private final void computeScaleSize(int expectWidth, int expectHeight) throws Throwable {
        if (this.mLastExpectWidth == expectWidth && this.mLastExpectHeight == expectHeight) {
            return;
        }
        loadMeta();
        this.mLastScaleWidth = expectWidth;
        int i = this.mWidth;
        float f = i / expectWidth;
        this.mLastScaleRatio = f;
        this.mLastDecodeWidth = i;
        int i2 = this.mHeight;
        this.mLastDecodeHeight = i2;
        if (expectHeight > 0) {
            int i3 = (int) (expectHeight * f);
            this.mLastDecodeHeight = i3;
            if (i3 > i2) {
                this.mLastDecodeHeight = i2;
            }
        }
        this.mLastScaleHeight = (int) ((this.mLastDecodeHeight * expectWidth) / i);
        this.mLastExpectWidth = expectWidth;
        this.mLastExpectHeight = expectHeight;
    }

    public final int getPolishedScaledHeight(int expectWidth) throws Throwable {
        Point pointComputePolishedSize = computePolishedSize();
        if (pointComputePolishedSize != null) {
            return (pointComputePolishedSize.y * expectWidth) / pointComputePolishedSize.x;
        }
        return 0;
    }

    private final Point computePolishedSize() throws Throwable {
        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        float f = width;
        float f2 = height;
        float f3 = f / f2;
        if (0.75f > f3 || f3 > MAX_GOOD_RATIO) {
            if (width > height) {
                width = (int) (f2 * MAX_GOOD_RATIO);
            } else {
                height = (int) (f / 0.75f);
            }
        }
        return new Point(width, height);
    }

    public final Bitmap getPolishedScaledBitmap(int expectWidth) throws Throwable {
        Point pointComputePolishedSize = computePolishedSize();
        if (pointComputePolishedSize == null) {
            return null;
        }
        Bitmap bitmapClipBitmap = clipBitmap(pointComputePolishedSize.x, pointComputePolishedSize.y, 17, (int) Math.ceil(((double) getWidth()) / ((double) expectWidth)));
        if (bitmapClipBitmap != null) {
            if (getWidth() < expectWidth) {
                expectWidth = getWidth();
            }
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapClipBitmap, expectWidth, (bitmapClipBitmap.getHeight() * expectWidth) / bitmapClipBitmap.getWidth(), false);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
            if (!Intrinsics.areEqual(bitmapClipBitmap, bitmapCreateScaledBitmap)) {
                bitmapClipBitmap.recycle();
                return bitmapCreateScaledBitmap;
            }
        }
        return bitmapClipBitmap;
    }

    public final Bitmap getSquareBitmap(int expectedSize) throws Throwable {
        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return null;
        }
        if (width > height) {
            width = height;
        }
        Bitmap bitmapClipBitmap = clipBitmap(width, width, 17, width / expectedSize);
        if (bitmapClipBitmap != null) {
            int iCoerceAtMost = RangesKt.coerceAtMost(expectedSize, getWidth());
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapClipBitmap, iCoerceAtMost, iCoerceAtMost, false);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
            if (!Intrinsics.areEqual(bitmapClipBitmap, bitmapCreateScaledBitmap)) {
                bitmapClipBitmap.recycle();
                return bitmapCreateScaledBitmap;
            }
        }
        return bitmapClipBitmap;
    }

    /* JADX INFO: compiled from: BitmapDecoder.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/doodle/utils/BitmapDecoder$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "MAX_GOOD_RATIO", "", "MIN_GOOD_RATIO", "fitBitmapToDisplay", "Landroid/graphics/Bitmap;", DoodleBitmap.KEY_BITMAP, "disW", "", "disH", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Bitmap fitBitmapToDisplay(Bitmap bitmap, int disW, int disH) {
            Intrinsics.checkNotNullParameter(bitmap, "bitmap");
            if (bitmap.getWidth() >= disW && bitmap.getHeight() >= disH) {
                return bitmap;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.max(bitmap.getWidth(), disW), Math.max(bitmap.getHeight(), disH), Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Logger.INSTANCE.d("DoodleLoadTest", "fitBitmapToDisplay w " + bitmapCreateBitmap.getWidth() + " h " + bitmapCreateBitmap.getHeight());
            canvas.drawBitmap(bitmap, (bitmapCreateBitmap.getWidth() - bitmap.getWidth()) * 0.5f, (bitmapCreateBitmap.getHeight() - bitmap.getHeight()) * 0.5f, (Paint) null);
            return bitmapCreateBitmap;
        }
    }

    public final void releaseTmpCanvas(Canvas canvas) {
        try {
            Method declaredMethod = Canvas.class.getDeclaredMethod("release", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(canvas, new Object[0]);
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "invoke Canvas.release failed " + e);
        }
    }
}
