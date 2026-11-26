package xc.engine.storage.pdf;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import androidx.hardware.DataSpace;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes6.dex */
public class PDFReader {
    private String filePath;
    private boolean loaded = false;
    private PdfRenderer renderer;

    public PDFReader(String str) {
        this.filePath = str;
    }

    public int load() {
        if (this.loaded) {
            return 0;
        }
        try {
            this.renderer = new PdfRenderer(ParcelFileDescriptor.open(new File(this.filePath), DataSpace.RANGE_LIMITED));
            this.loaded = true;
            return 0;
        } catch (IOException unused) {
            return 2;
        } catch (SecurityException e) {
            String message = e.getMessage();
            return (message == null || !(message.contains("Password required") || message.contains("password required") || message.contains("incorrect password"))) ? 0 : 1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int getPageCount() {
        PdfRenderer pdfRenderer = this.renderer;
        if (pdfRenderer == null) {
            return 0;
        }
        return pdfRenderer.getPageCount();
    }

    public int[] getPageSize(int i) {
        try {
            PdfRenderer.Page pageOpenPage = this.renderer.openPage(i);
            int[] iArr = {pageOpenPage.getWidth(), pageOpenPage.getHeight()};
            pageOpenPage.close();
            return iArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void render(Bitmap bitmap, int i) {
        try {
            PdfRenderer.Page pageOpenPage = this.renderer.openPage(i);
            Matrix matrix = new Matrix();
            matrix.postScale((bitmap.getWidth() * 1.0f) / pageOpenPage.getWidth(), (bitmap.getWidth() * 1.0f) / pageOpenPage.getWidth());
            pageOpenPage.render(bitmap, null, matrix, 2);
            pageOpenPage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (this.loaded) {
            try {
                this.renderer.close();
                this.loaded = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
