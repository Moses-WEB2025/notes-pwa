package com.miui.handwritecommon.api.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.media.ExifInterface;
import android.net.Uri;
import android.print.PrintAttributes;
import android.util.DisplayMetrics;
import android.util.Log;
import com.miui.doodle.document.Layer;
import defpackage.R2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/* JADX INFO: loaded from: classes2.dex */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    public static Bitmap imageSizeCompress(Context context, String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateSampleSize(context, options);
        Log.d(TAG, "imageSizeCompress inSampleSize: " + options.inSampleSize);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static int calculateSampleSize(Context context, BitmapFactory.Options options) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int iMax = Math.max((int) Math.ceil(options.outHeight / displayMetrics.heightPixels), (int) Math.ceil(options.outWidth / displayMetrics.widthPixels));
        if (iMax > 1) {
            return iMax;
        }
        return 1;
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

    public static String bitmapToJPEG(Bitmap bitmap, String str, String str2) {
        if (bitmap == null) {
            return null;
        }
        File file = new File(str);
        if (file.mkdirs()) {
            Log.d("BitmapUtil", str + " dir is already exist");
        }
        File file2 = new File(file, str2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
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

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x006e: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:31:0x006e */
    public static String bitmap2BMP(Bitmap bitmap, String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        FileOutputStream fileOutputStream3 = null;
        try {
            if (bitmap == null) {
                return null;
            }
            try {
                fileOutputStream2 = new FileOutputStream(str + File.separator + str2);
                try {
                    new BitmapEx(bitmap).saveAsBMP(fileOutputStream2);
                    fileOutputStream2.close();
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    return str + File.separator + str2;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (fileOutputStream2 == null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    return null;
                }
            } catch (IOException e3) {
                e = e3;
                fileOutputStream2 = null;
            } catch (Throwable th) {
                th = th;
                if (fileOutputStream3 == null) {
                    try {
                        fileOutputStream3.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    throw th;
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream3 = fileOutputStream;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0091  */
    /* JADX WARN: Code duplicated, block: B:55:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:61:? A[RETURN, SYNTHETIC] */
    public static String bitmap2PDF(Bitmap bitmap, String str, String str2, boolean z) throws Throwable {
        FileOutputStream fileOutputStream;
        Throwable th;
        PdfDocument pdfDocument = new PdfDocument();
        int widthMils = (PrintAttributes.MediaSize.ISO_A4.getWidthMils() * 72) / 1000;
        float width = widthMils / bitmap.getWidth();
        int height = (int) (bitmap.getHeight() * width);
        Matrix matrix = new Matrix();
        matrix.postScale(width, width);
        Paint paint = new Paint(1);
        PdfDocument.Page pageStartPage = pdfDocument.startPage(new PdfDocument.PageInfo.Builder(widthMils, height, 0).create());
        if (!z) {
            pageStartPage.getCanvas().drawBitmap(bitmap, matrix, paint);
        }
        pdfDocument.finishPage(pageStartPage);
        File file = new File(str, str2);
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                try {
                    pdfDocument.writeTo(fileOutputStream);
                    pdfDocument.close();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    pdfDocument.close();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (bitmap == null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        throw th;
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                pdfDocument.close();
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                if (bitmap != null && !bitmap.isRecycled()) {
                }
                if (file.exists()) {
                    return file.getAbsolutePath();
                }
                return null;
            }
        } catch (IOException e5) {
            e = e5;
            fileOutputStream = null;
        } catch (Throwable th3) {
            fileOutputStream = null;
            th = th3;
            pdfDocument.close();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (bitmap == null) {
                throw th;
            }
            throw th;
        }
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static int[] getImageSize(String str, int i, float f) {
        int[] iArr = new int[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        if (f == 90.0f || f == 270.0f) {
            if (i3 > i) {
                i2 = (i2 * i) / i3;
            }
            iArr[0] = i2;
            iArr[1] = i;
            return iArr;
        }
        if (i2 > i) {
            iArr[0] = i;
            iArr[1] = (i3 * i) / i2;
            return iArr;
        }
        i = i3;
        iArr[0] = i2;
        iArr[1] = i;
        return iArr;
    }

    private static int getRotation(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                return R2.array.ai_tag_top_item_gradient_border_colors;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt != 8) {
                return 0;
            }
            return R2.attr.actionBarPauseIcon;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Point getImageSize(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new Point(options.outWidth, options.outHeight);
    }

    public static Point getImageSize(Context context, Uri uri) {
        Point point = new Point(0, 0);
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{Layer.KEY_WIDTH, Layer.KEY_HEIGHT}, null, null, null);
        if (cursorQuery != null && cursorQuery.moveToFirst()) {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow(Layer.KEY_WIDTH);
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow(Layer.KEY_HEIGHT);
            point.x = cursorQuery.getInt(columnIndexOrThrow);
            point.y = cursorQuery.getInt(columnIndexOrThrow2);
            cursorQuery.close();
        }
        return point;
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

    public static String checkAndSaveScaleBitmap(String str, String str2, boolean z) throws Throwable {
        String strSubstring;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i = options.outWidth;
        int i2 = options.outHeight;
        int i3 = (int) (5184000 * 0.6f);
        if (i != 0 && i2 != 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            if (i * i2 > i3) {
                float f = i3 / (options.outWidth * options.outHeight);
                int i4 = (int) (i * f);
                int i5 = (int) (i2 * f);
                if (z) {
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_4444);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    options.inJustDecodeBounds = false;
                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
                    canvas.drawBitmap(bitmapDecodeFile, (Rect) null, new Rect(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight()), paint);
                    bitmapDecodeFile.recycle();
                    canvas.setBitmap(null);
                    int iLastIndexOf = str.lastIndexOf(".");
                    if (iLastIndexOf == -1) {
                        strSubstring = "";
                    } else {
                        strSubstring = str.substring(iLastIndexOf);
                    }
                    String str3 = UUID.randomUUID().toString() + strSubstring;
                    File file = new File(str2);
                    String absolutePath = new File(file, str3).getAbsolutePath();
                    saveBitmapToFile(bitmapCreateBitmap, new File(file, str3));
                    return absolutePath;
                }
            }
        }
        return str;
    }

    public static void saveBitmapToFile(Bitmap bitmap, File file) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    if (file.getParentFile() != null && !file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                        bitmap.recycle();
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                            bitmap.recycle();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                                bitmap.recycle();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }
}
