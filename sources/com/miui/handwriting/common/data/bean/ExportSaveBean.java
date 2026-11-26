package com.miui.handwriting.common.data.bean;

import ch.qos.logback.core.CoreConstants;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ExportSaveBean {
    public List<String> filePathList;
    public boolean isSuccess;
    public int progress;
    public int type;

    public void setProgress(int i) {
        this.progress = i;
    }

    public void setData(int i, boolean z, List<String> list) {
        this.type = i;
        this.isSuccess = z;
        this.filePathList = list;
    }

    public String toString() {
        return "ExportSaveBean{type=" + this.type + ", isSuccess=" + this.isSuccess + ", filePathList=" + this.filePathList + ", progress=" + this.progress + CoreConstants.CURLY_RIGHT;
    }
}
