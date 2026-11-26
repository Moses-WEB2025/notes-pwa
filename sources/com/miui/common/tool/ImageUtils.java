package com.miui.common.tool;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.notes.R;
import com.miui.notes.tool.BitmapDecoder;
import com.miui.notes.tool.util.CompatFileProvider;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ImageUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rJ,\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J$\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0002J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u001f\u001a\u0004\u0018\u00010\n2\u0006\u0010 \u001a\u00020\u0005J\u0010\u0010!\u001a\u0004\u0018\u00010\n2\u0006\u0010 \u001a\u00020\u0005J\u0010\u0010\"\u001a\u0004\u0018\u00010\n2\u0006\u0010 \u001a\u00020\u0005J,\u0010#\u001a\u0004\u0018\u00010\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010$\u001a\u0004\u0018\u00010\u001a2\u0006\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u0007J\u0010\u0010'\u001a\u0004\u0018\u00010\n2\u0006\u0010(\u001a\u00020)J\u001a\u0010*\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010$\u001a\u0004\u0018\u00010\u001aJ\u0012\u0010+\u001a\u0004\u0018\u00010\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/miui/common/tool/ImageUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "MAX_IMG_BASE_SIZE", "", "MAX_IMG_BASE_FILE_SIZE", "createThumbnail", "Landroid/graphics/Bitmap;", "imgPath", "isFromStartDrag", "", "isNeedCrop", "scaleBitmap", "opts", "Landroid/graphics/BitmapFactory$Options;", "rotateBitmap", DoodleBitmap.KEY_BITMAP, "mimeType", "releaseCanvas", "", "canvas", "Landroid/graphics/Canvas;", "getImageContentUri", "Landroid/net/Uri;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "imageFile", "Ljava/io/File;", "base64ToBitmap", "base64String", "base64ToBitmap2", "base64PngToBitmap", "loadImage", CallMethod.ARG_URI, Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "captureSnapshot", "view", "Landroid/view/View;", "getThumbnailBitmap", "scaledBitMap", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ImageUtils {
    public static final ImageUtils INSTANCE = new ImageUtils();
    private static final int MAX_IMG_BASE_FILE_SIZE = 16777216;
    private static final int MAX_IMG_BASE_SIZE = 4096;
    private static final String TAG = "Notes:ImageUtils";

    private ImageUtils() {
    }

    public final Bitmap createThumbnail(String imgPath, boolean isFromStartDrag, boolean isNeedCrop) throws Throwable {
        Intrinsics.checkNotNullParameter(imgPath, "imgPath");
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmapScaleBitmap = scaleBitmap(imgPath, options, isFromStartDrag, isNeedCrop);
        if (bitmapScaleBitmap == null) {
            Logger.INSTANCE.e(TAG, "Fail to createThumbnail");
            return null;
        }
        Bitmap bitmapRotateBitmap = rotateBitmap(imgPath, bitmapScaleBitmap, options.outMimeType);
        if (bitmapRotateBitmap != null) {
            return bitmapRotateBitmap;
        }
        Logger.INSTANCE.e(TAG, "Fail to rotateBitmap");
        return null;
    }

    private final Bitmap scaleBitmap(String imgPath, BitmapFactory.Options opts, boolean isFromStartDrag, boolean isNeedCrop) throws Throwable {
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, opts);
        int i = opts.outWidth;
        int i2 = opts.outHeight;
        Bitmap bitmapDecodeBitmap = null;
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        try {
            bitmapDecodeBitmap = miuix.graphics.BitmapFactory.decodeBitmap(imgPath, false);
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Fail to decode " + imgPath + ",exception:" + e);
        }
        if (!isFromStartDrag) {
            return bitmapDecodeBitmap;
        }
        if (!isNeedCrop) {
            if (i <= 4096) {
                return bitmapDecodeBitmap;
            }
            Intrinsics.checkNotNull(bitmapDecodeBitmap);
            return Bitmap.createBitmap(bitmapDecodeBitmap, 0, 0, 4096, (i2 * 4096) / i);
        }
        if (i > i2) {
            Intrinsics.checkNotNull(bitmapDecodeBitmap);
            return Bitmap.createBitmap(bitmapDecodeBitmap, 0, 0, i2 * 5, i2);
        }
        Intrinsics.checkNotNull(bitmapDecodeBitmap);
        return Bitmap.createBitmap(bitmapDecodeBitmap, 0, 0, i, i * 5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v2, types: [int] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.graphics.Canvas] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.miui.common.tool.ImageUtils] */
    private final Bitmap rotateBitmap(String imgPath, Bitmap bitmap, String mimeType) throws Throwable {
        ?? r2;
        int i;
        Canvas canvas;
        OutOfMemoryError e;
        Bitmap bitmapCreateBitmap;
        try {
            int attributeInt = new ExifInterface(imgPath).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 1) {
                return bitmap;
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            if (attributeInt != 3) {
                if (attributeInt != 6) {
                    i = width;
                    r2 = height;
                    if (attributeInt == 8) {
                        float f = width / 2;
                        matrix.postRotate(270.0f, f, f);
                    }
                } else {
                    float f2 = height / 2;
                    matrix.postRotate(90.0f, f2, f2);
                }
                r2 = width;
                i = height;
            } else {
                matrix.postRotate(180.0f, width / 2, height / 2);
                i = width;
                r2 = height;
            }
            try {
                try {
                    try {
                        bitmapCreateBitmap = Bitmap.createBitmap(i, r2, Bitmap.Config.ARGB_8888);
                        try {
                            canvas = new Canvas(bitmapCreateBitmap);
                            try {
                                canvas.drawBitmap(bitmap, matrix, null);
                                bitmap.recycle();
                                releaseCanvas(canvas);
                                return bitmapCreateBitmap;
                            } catch (OutOfMemoryError e2) {
                                e = e2;
                                Logger.INSTANCE.e(TAG, "Fail to rotateBitmap,exception:" + e);
                                bitmap.recycle();
                                if (canvas != null) {
                                    releaseCanvas(canvas);
                                }
                                return bitmapCreateBitmap;
                            }
                        } catch (OutOfMemoryError e3) {
                            canvas = null;
                            e = e3;
                        }
                    } catch (Throwable th) {
                        r2 = 0;
                        th = th;
                        bitmap.recycle();
                        if (r2 != 0) {
                            releaseCanvas(r2);
                        }
                        throw th;
                    }
                } catch (OutOfMemoryError e4) {
                    canvas = null;
                    e = e4;
                    bitmapCreateBitmap = null;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            Logger.INSTANCE.e(TAG, "createThumbnail fail,exception:" + e5);
            return bitmap;
        }
    }

    private final void releaseCanvas(Canvas canvas) {
        try {
            Method declaredMethod = Canvas.class.getDeclaredMethod("release", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(canvas, new Object[0]);
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "invoke Canvas.release failed,exception:" + e);
        }
    }

    public final Uri getImageContentUri(Context context, File imageFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(imageFile, "imageFile");
        Uri uriForFile = CompatFileProvider.getUriForFile(context, context.getPackageName(), imageFile);
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(...)");
        return uriForFile;
    }

    public final Bitmap base64ToBitmap(String base64String) {
        Intrinsics.checkNotNullParameter(base64String, "base64String");
        if (StringsKt.startsWith$default(base64String, "data:image/jpeg;base64,", false, 2, (Object) null)) {
            return base64ToBitmap2(base64String);
        }
        if (StringsKt.startsWith$default(base64String, "data:image/png;base64,", false, 2, (Object) null)) {
            return base64PngToBitmap(base64String);
        }
        return null;
    }

    public final Bitmap base64ToBitmap2(String base64String) {
        Intrinsics.checkNotNullParameter(base64String, "base64String");
        byte[] bArrDecode = Base64.decode(StringsKt.replace$default(base64String, "data:image/jpeg;base64,", "", false, 4, (Object) null), 0);
        return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
    }

    public final Bitmap base64PngToBitmap(String base64String) {
        Intrinsics.checkNotNullParameter(base64String, "base64String");
        byte[] bArrDecode = Base64.decode(StringsKt.replace$default(base64String, "data:image/png;base64,", "", false, 4, (Object) null), 0);
        return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
    }

    public final Bitmap loadImage(Context context, Uri uri, int width, int height) {
        return new BitmapDecoder(context, uri).getScaledBitmap(width, height);
    }

    public final Bitmap captureSnapshot(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (measuredWidth <= 0 || measuredHeight <= 0) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        view.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public final Bitmap getThumbnailBitmap(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Resources resources = context.getResources();
        return loadImage(context, uri, resources.getDimensionPixelSize(R.dimen.sns_preview_width), resources.getDimensionPixelSize(R.dimen.sns_preview_height));
    }

    public final Bitmap scaledBitMap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) Math.max(1.0d, (int) (bitmap.getWidth() * 0.75f)), (int) Math.max(1.0d, (int) (bitmap.getHeight() * 0.75f)), false);
    }
}
