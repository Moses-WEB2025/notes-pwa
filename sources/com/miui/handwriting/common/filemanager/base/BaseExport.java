package com.miui.handwriting.common.filemanager.base;

import android.content.Context;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseExport implements ExportStrategy {
    protected Context context;
    protected String dirPath;
    protected String entFilePath;
    protected ExportCallback exportCallback;

    public BaseExport setExportCallback(ExportCallback exportCallback) {
        this.exportCallback = exportCallback;
        return this;
    }

    public ExportCallback getExportCallback() {
        return this.exportCallback;
    }

    public Context getContext() {
        return this.context;
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public String getEntFilePath() {
        return this.entFilePath;
    }

    public BaseExport setContext(Context context) {
        this.context = context;
        return this;
    }

    public BaseExport setDirPath(String str) {
        this.dirPath = str;
        return this;
    }

    public BaseExport setEntFilePath(String str) {
        this.entFilePath = str;
        return this;
    }
}
