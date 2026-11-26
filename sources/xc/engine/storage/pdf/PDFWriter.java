package xc.engine.storage.pdf;

import android.graphics.Bitmap;
import android.util.Log;

/* JADX INFO: loaded from: classes6.dex */
public class PDFWriter {
    private static final String TAG = "PDFWriter";
    private String filePath;

    public PDFWriter(String str) {
        this.filePath = str;
        Log.w(TAG, "PDFWriter: PDF export not available on Phone");
    }

    public void create() {
        Log.w(TAG, "create: PDF export not available on Phone");
    }

    public void addPage(Bitmap bitmap, String str, int i) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        Log.w(TAG, "addPage: PDF export not available on Phone");
    }

    public void save() {
        Log.w(TAG, "save: PDF export not available on Phone");
    }

    public void close() {
        Log.w(TAG, "close: PDF export not available on Phone");
    }
}
