package com.miui.handwriting.common.filemanager;

import android.util.Log;
import com.miui.handwriting.common.engine.export.ImportStrategy;
import com.miui.handwriting.common.filemanager.base.ExportStrategy;

/* JADX INFO: loaded from: classes3.dex */
public class PageFileManager implements ExportStrategy, ImportStrategy {
    private static final String TAG = "PageFileManager";
    private ExportStrategy exportStrategy;
    private ImportStrategy importStrategy;

    public void setExportStrategy(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public void setImportStrategy(ImportStrategy importStrategy) {
        this.importStrategy = importStrategy;
    }

    @Override // com.miui.handwriting.common.filemanager.base.ExportStrategy
    public void exportPdf() {
        this.exportStrategy.exportPdf();
    }

    @Override // com.miui.handwriting.common.filemanager.base.ExportStrategy
    public void exportImage() {
        this.exportStrategy.exportImage();
    }

    @Override // com.miui.handwriting.common.engine.export.ImportStrategy
    public void importPdf() {
        Log.d(TAG, "importPdf");
        this.importStrategy.importPdf();
    }

    @Override // com.miui.handwriting.common.engine.export.ImportStrategy
    public void importImage() {
        this.importStrategy.importImage();
    }

    @Override // com.miui.handwriting.common.engine.export.ImportStrategy
    public void importFiles() {
        this.importStrategy.importFiles();
    }
}
