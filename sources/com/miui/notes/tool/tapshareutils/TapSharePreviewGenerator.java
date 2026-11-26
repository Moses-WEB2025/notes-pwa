package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;
import com.miui.common.tool.Logger;
import com.miui.notes.tool.util.CompatFileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class TapSharePreviewGenerator {
    private static final String TAG = "TapSharePreviewGenerator";

    public static Bitmap captureView(View view) {
        if (view == null) {
            Logger.INSTANCE.e(TAG, "View is null for screenshot");
            return null;
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(-1);
            view.draw(canvas);
            return bitmapCreateBitmap;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error capturing view: " + e.getMessage());
            return null;
        }
    }

    public static Uri captureViewToFile(Context context, View view, File file) {
        if (context == null || view == null || file == null) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for captureViewToFile");
            return null;
        }
        Bitmap bitmapCaptureView = captureView(view);
        if (bitmapCaptureView == null) {
            return null;
        }
        try {
            return saveBitmapToFile(context, bitmapCaptureView, file);
        } finally {
            if (!bitmapCaptureView.isRecycled()) {
                bitmapCaptureView.recycle();
            }
        }
    }

    public static Uri saveBitmapToFile(Context context, Bitmap bitmap, File file) {
        if (context == null || bitmap == null || file == null) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for saveBitmapToFile");
            return null;
        }
        File file2 = new File(file, "preview_" + System.currentTimeMillis() + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                Uri uriForFile = CompatFileProvider.getUriForFile(context, "com.miui.notes", file2);
                fileOutputStream.close();
                return uriForFile;
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Error saving bitmap to file: " + e.getMessage());
            if (!file2.delete()) {
                Logger.INSTANCE.w(TAG, "Failed to delete incomplete preview file: " + file2.getPath());
            }
            return null;
        }
    }
}
