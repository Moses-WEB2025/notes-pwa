package com.market.sdk.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class BitmapFactory extends android.graphics.BitmapFactory {
    static Object sLockForRsContext = new Object();
    static RenderScript sRsContext;
    private static final Paint sSrcInPaint;

    static {
        Paint paint = new Paint(1);
        sSrcInPaint = paint;
        paint.setFilterBitmap(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    protected BitmapFactory() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (bitmap.getConfig() != null) {
            config = bitmap.getConfig();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, config);
        scaleBitmap(bitmap, bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == null || bitmap2 == null) {
            return null;
        }
        if (bitmap.getWidth() == bitmap2.getWidth() && bitmap.getHeight() == bitmap2.getHeight()) {
            return bitmap;
        }
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), paint);
        return bitmap2;
    }

    private static Bitmap copyToEmpty(Bitmap bitmap) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (bitmap.getConfig() != null) {
            config = bitmap.getConfig();
        }
        return Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
    }

    public static Bitmap fastBlur(Bitmap bitmap, int i) {
        Bitmap bitmapCopyToEmpty = copyToEmpty(bitmap);
        fastBlur(bitmap, bitmapCopyToEmpty, i);
        return bitmapCopyToEmpty;
    }

    public static Bitmap fastBlur(Bitmap bitmap, Bitmap bitmap2, int i) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap2 == null || bitmap.getWidth() != bitmap2.getWidth() || bitmap.getHeight() != bitmap2.getHeight()) {
            bitmap2 = copyToEmpty(bitmap);
        }
        fastblur_v17(bitmap, bitmap2, i);
        return bitmap2;
    }

    private static Bitmap fastblur_v17(Bitmap bitmap, Bitmap bitmap2, int i) {
        int i2 = 1;
        while (i > 25) {
            i2 *= 2;
            i /= 2;
        }
        Bitmap bitmapScaleBitmap = i2 == 1 ? bitmap : scaleBitmap(bitmap, Math.max(bitmap.getWidth() / i2, 1), Math.max(bitmap.getHeight() / i2, 1));
        Context context = AppGlobal.getContext();
        if (context.getApplicationContext() == null) {
            context = new ContextWrapper(context) { // from class: com.market.sdk.utils.BitmapFactory.1
                @Override // android.content.ContextWrapper, android.content.Context
                public Context getApplicationContext() {
                    return this;
                }
            };
        }
        try {
            for (Object obj : context.getClassLoader().loadClass("android.graphics.Bitmap$Config").getEnumConstants()) {
                Enum r7 = (Enum) obj;
                if ("RGBA_F16".equals(r7.name()) && bitmapScaleBitmap.getConfig() == r7) {
                    bitmapScaleBitmap = transferF16ToARGB(bitmapScaleBitmap);
                    break;
                }
            }
        } catch (Exception unused) {
        }
        synchronized (sLockForRsContext) {
            if (sRsContext == null) {
                sRsContext = RenderScript.create(context);
            }
            Bitmap bitmap3 = i2 == 1 ? bitmap2 : bitmapScaleBitmap;
            if (bitmapScaleBitmap.getRowBytes() != bitmap3.getRowBytes()) {
                bitmapScaleBitmap = bitmapScaleBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(sRsContext, bitmapScaleBitmap);
            Allocation allocationCreateTyped = Allocation.createTyped(sRsContext, allocationCreateFromBitmap.getType());
            RenderScript renderScript = sRsContext;
            ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            scriptIntrinsicBlurCreate.setRadius(i);
            scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
            scriptIntrinsicBlurCreate.forEach(allocationCreateTyped);
            allocationCreateTyped.copyTo(bitmap3);
            if (bitmap3 != bitmap2) {
                scaleBitmap(bitmap3, bitmap2);
            }
            if (bitmapScaleBitmap != bitmap) {
                bitmapScaleBitmap.recycle();
            }
            if (bitmap3 != bitmap2) {
                bitmap3.recycle();
            }
            allocationCreateFromBitmap.destroy();
            allocationCreateTyped.destroy();
            scriptIntrinsicBlurCreate.destroy();
        }
        return bitmap2;
    }

    private static Bitmap transferF16ToARGB(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == 0 || height == 0) {
            return bitmap;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setFlags(3);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static boolean saveToFile(Bitmap bitmap, String str) throws IOException {
        return saveToFile(bitmap, str, false);
    }

    public static boolean saveToFile(Bitmap bitmap, String str, boolean z) throws Throwable {
        if (bitmap == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                bitmap.compress(z ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                fileOutputStream2.close();
                return true;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
