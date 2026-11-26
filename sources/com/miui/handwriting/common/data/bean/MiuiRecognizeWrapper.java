package com.miui.handwriting.common.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiRecognizeWrapper implements Serializable {
    private List<MiuiRecognizeData> list = new ArrayList();

    public List<MiuiRecognizeData> getList() {
        return this.list;
    }

    public void setList(List<MiuiRecognizeData> list) {
        this.list = list;
    }
}
