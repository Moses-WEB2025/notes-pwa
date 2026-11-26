package com.miui.handwriting.common.filemanager.base;

import android.content.Context;
import com.miui.handwriting.common.engine.export.ImportStrategy;
import com.miui.handwriting.common.model.IMiuiHandWritingCommonModel;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseImport implements ImportStrategy {
    protected AppCompatActivity mActivity;
    protected Context mContext;
    protected ImportCallback mImportCallback;
    protected IMiuiHandWritingCommonModel mPageInkModel;

    public BaseImport setImportCallback(ImportCallback importCallback) {
        this.mImportCallback = importCallback;
        return this;
    }

    public ImportCallback getImportCallback() {
        return this.mImportCallback;
    }

    public BaseImport bindActivity(AppCompatActivity appCompatActivity) {
        this.mActivity = appCompatActivity;
        this.mContext = appCompatActivity.getApplicationContext();
        return this;
    }

    public AppCompatActivity getActivity() {
        return this.mActivity;
    }

    public BaseImport setPageInkFunc(IMiuiHandWritingCommonModel iMiuiHandWritingCommonModel) {
        this.mPageInkModel = iMiuiHandWritingCommonModel;
        return this;
    }

    public Context getContext() {
        return this.mContext;
    }
}
