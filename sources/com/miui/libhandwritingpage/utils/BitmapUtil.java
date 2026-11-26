package com.miui.libhandwritingpage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import xc.engine.utils.FeatureManager;

/* JADX INFO: loaded from: classes3.dex */
public class BitmapUtil {
    private static int importCompressTarget = 4194304;

    public static int getPixelIndex(int i, int i2, int i3, int i4) {
        return (i2 * i3 * i4) + (i * i4);
    }

    public static void saveBitmapToFile(Bitmap bitmap, File file, boolean z) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                fileOutputStream2.flush();
                closeOutputStream(fileOutputStream2, bitmap, z);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                try {
                    closeOutputStream(fileOutputStream, bitmap, z);
                    throw th;
                } catch (IOException e) {
                    e.printStackTrace();
                    closeOutputStream(fileOutputStream, bitmap, z);
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void saveBitmapToFile(Bitmap bitmap, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeOutputStream(FileOutputStream fileOutputStream, Bitmap bitmap, boolean z) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
                if (z) {
                    bitmap.recycle();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getBitmap(String str, PageItemData pageItemData) {
        if (TextUtils.isEmpty(pageItemData.bgImage)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File file = new File(pageItemData.bgImage);
        if (!file.exists()) {
            file = new File(str, pageItemData.bgImage);
        }
        if (!file.exists()) {
            return null;
        }
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static Bitmap changeBitmapColor(Bitmap bitmap, int i) {
        int i2;
        int[] iArr;
        int i3;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int i4 = width * height;
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4];
        bitmap.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i5 = 0;
        while (i5 < i4) {
            int i6 = iArr2[i5];
            if (i6 == 0 || i6 == i) {
                i2 = width;
                iArr = iArr3;
                i3 = i;
            } else {
                iArr3[i5] = i;
                int[] pixelCoordinates = getPixelCoordinates(i5, width, 1);
                i2 = width;
                iArr = iArr3;
                i3 = i;
                setExpandPixelColor(iArr, pixelCoordinates[0], pixelCoordinates[1], i3, i2, 3, 1);
            }
            i5++;
            iArr3 = iArr;
            i = i3;
            width = i2;
        }
        bitmapCreateBitmap.setPixels(iArr3, 0, width, 0, 0, width, height);
        return bitmapCreateBitmap;
    }

    public static int[] getPixelCoordinates(int i, int i2, int i3) {
        int i4 = i2 * i3;
        return new int[]{(i % i4) / i3, i / i4};
    }

    public static void changePixelsColorByPosition(int[] iArr, int i, int i2, int i3, int i4, int i5) {
        int pixelIndex = getPixelIndex(i, i2, i4, i5);
        if (pixelIndex <= 0 || pixelIndex >= iArr.length) {
            return;
        }
        iArr[pixelIndex] = i3;
    }

    public static void setExpandPixelColor(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = i - i5;
        int i8 = i + i5;
        int i9 = i2 - i5;
        int i10 = i2 + i5;
        for (int i11 = i9; i11 <= i10; i11++) {
            for (int i12 = i7; i12 <= i8; i12++) {
                changePixelsColorByPosition(iArr, i12, i11, i3, i4, i6);
            }
        }
    }

    public static Bitmap getOptimizedBitmap(String str, PageItemData pageItemData) {
        if (TextUtils.isEmpty(pageItemData.bgImage)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        File file = new File(pageItemData.bgImage);
        if (!file.exists()) {
            file = new File(str, pageItemData.bgImage);
        }
        if (!file.exists()) {
            return null;
        }
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        if (FeatureManager.getInstance().isSupportHighQuality()) {
            importCompressTarget *= 2;
        }
        while ((i2 / i) * (i3 / i) > importCompressTarget) {
            i *= 2;
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = i;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static String bitmapToPNG(Bitmap bitmap, String str, String str2) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        File file = new File(str);
        if (file.mkdirs()) {
            Log.d("BitmapUtil", str + " dir is already exist");
        }
        File file2 = new File(file, str2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return file2.getAbsolutePath();
    }

    public static Point getImageSize(Bitmap bitmap) {
        return new Point(bitmap.getWidth(), bitmap.getHeight());
    }

    public static Point getImageSize(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new Point(options.outWidth, options.outHeight);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static Point getImageSizeFromUri(Context context, Uri uri) throws Throwable {
        InputStream inputStreamOpenInputStream;
        InputStream inputStream = null;
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    Point point = new Point(options.outWidth, options.outHeight);
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return point;
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenInputStream;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                e.printStackTrace();
                if (inputStreamOpenInputStream == null) {
                    return null;
                }
                try {
                    inputStreamOpenInputStream.close();
                    return null;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return null;
                }
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            inputStreamOpenInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    public static int[] getImageSizeBySize(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i3 = options.outWidth;
        int i4 = options.outHeight;
        int[] iArr = {i3, i4};
        if (i3 <= i || i4 <= i2) {
            if (i3 > i) {
                iArr[0] = i;
                iArr[1] = (i * i4) / i3;
            }
            if (i4 > i2) {
                iArr[0] = (i3 * i2) / i4;
                iArr[1] = i2;
            }
            return iArr;
        }
        if (i3 < i4) {
            iArr[0] = (i3 * i2) / i4;
            iArr[1] = i2;
            return iArr;
        }
        iArr[0] = i;
        iArr[1] = (i * i4) / i3;
        return iArr;
    }
}
