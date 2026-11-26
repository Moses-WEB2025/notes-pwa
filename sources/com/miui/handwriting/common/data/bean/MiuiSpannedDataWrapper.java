package com.miui.handwriting.common.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiSpannedDataWrapper implements Serializable {
    private List<MiuiSpannedData> list = new ArrayList();

    public List<MiuiSpannedData> getList() {
        return this.list;
    }

    public void setList(List<MiuiSpannedData> list) {
        this.list = list;
    }
}
