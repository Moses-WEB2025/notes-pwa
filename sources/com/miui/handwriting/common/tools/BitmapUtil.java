package com.miui.handwriting.common.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.utils.RomUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes3.dex */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";
    private static final int compressTarget = 31457280;

    public static int getPixelIndex(int i, int i2, int i3, int i4) {
        return (i2 * i3 * i4) + (i * i4);
    }

    public static Bitmap getBigImageByWatermark(Bitmap bitmap, Context context) {
        if (bitmap == null || context == null) {
            return null;
        }
        int i = 1;
        Bitmap bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmapCopy);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        miuix.graphics.BitmapFactory.decodeResource(context.getResources(), getWatermarkDrawable(), options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        while (true) {
            if (i2 / i < bitmapCopy.getWidth() / 2 && i3 / i < bitmapCopy.getHeight() / 2) {
                String str = TAG;
                Log.d(str, "originalWidth:" + i2 + " originalHeight:" + i3 + " sampleSize:" + i);
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inSampleSize = i;
                Bitmap bitmapDecodeResource = miuix.graphics.BitmapFactory.decodeResource(context.getResources(), getWatermarkDrawable(), options2);
                int width = (bitmapCopy.getWidth() - (context.getResources().getDimensionPixelSize(R.dimen.creation_ai_watermark_right) / i)) - bitmapDecodeResource.getWidth();
                int height = (bitmapCopy.getHeight() - (context.getResources().getDimensionPixelSize(R.dimen.creation_ai_watermark_bottom) / i)) - bitmapDecodeResource.getHeight();
                Log.d(str, " x:" + width + " y:" + height + " resultWidth:" + bitmapCopy.getWidth() + " resultHeight:" + bitmapCopy.getHeight() + " watermarkWidth:" + bitmapDecodeResource.getWidth() + " watermarkHeight:" + bitmapDecodeResource.getHeight());
                canvas.drawBitmap(bitmapDecodeResource, width, height, new Paint());
                bitmapDecodeResource.recycle();
                return bitmapCopy;
            }
            i *= 2;
        }
    }

    public static int getWatermarkDrawable() {
        if (RomUtils.isInternationalBuild()) {
            return R.drawable.icon_ai_watermark_en;
        }
        return R.drawable.icon_ai_watermark_zh;
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

    /* JADX WARN: Code duplicated, block: B:95:0x00e7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static void compress(File file) throws Throwable {
        Bitmap bitmapDecodeFile;
        FileOutputStream fileOutputStream;
        IOException e;
        if (file == null || !file.exists()) {
            return;
        }
        Log.d(TAG, "fileSize:" + file.length());
        if (file.length() < 31457280) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        byteArrayOutputStream = null;
        byteArrayOutputStream = null;
        byteArrayOutputStream = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            while ((options.outWidth / i) * (options.outHeight / i) > compressTarget) {
                i *= 2;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = i;
            bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options2);
            try {
                try {
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    try {
                        int i2 = 90;
                        bitmapDecodeFile.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream2);
                        while (byteArrayOutputStream2.toByteArray().length > compressTarget) {
                            byteArrayOutputStream2.reset();
                            i2 -= 10;
                            if (i2 < 0) {
                                break;
                            } else {
                                bitmapDecodeFile.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream2);
                            }
                        }
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            try {
                                try {
                                    try {
                                        fileOutputStream.write(byteArrayOutputStream2.toByteArray());
                                        fileOutputStream.flush();
                                    } catch (IOException e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        if (fileOutputStream != null) {
                                        }
                                        Log.d(TAG, "quality:" + i2 + " sampleSize:" + i + " fileSize:" + file.length());
                                        byteArrayOutputStream2.close();
                                        if (bitmapDecodeFile != null) {
                                            return;
                                        } else {
                                            return;
                                        }
                                    }
                                    fileOutputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                Log.d(TAG, "quality:" + i2 + " sampleSize:" + i + " fileSize:" + file.length());
                                try {
                                    byteArrayOutputStream2.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                                if (bitmapDecodeFile != null || bitmapDecodeFile.isRecycled()) {
                                    return;
                                }
                                bitmapDecodeFile.recycle();
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream2 = fileOutputStream;
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException e6) {
                            fileOutputStream = null;
                            e = e6;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            throw th;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        e.printStackTrace();
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                        }
                        if (bitmapDecodeFile == null || bitmapDecodeFile.isRecycled()) {
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e9) {
                                e9.printStackTrace();
                            }
                        }
                        if (bitmapDecodeFile != null && !bitmapDecodeFile.isRecycled()) {
                            bitmapDecodeFile.recycle();
                            throw th;
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Exception e10) {
                e = e10;
            }
        } catch (Exception e11) {
            e = e11;
            bitmapDecodeFile = null;
        } catch (Throwable th5) {
            th = th5;
            bitmapDecodeFile = null;
        }
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

    public static Bitmap createSolidColorBitmap(int i, int i2, int i3) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(i3);
        return bitmapCreateBitmap;
    }
}
