package com.miui.libhandwritingpage.listener;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface PageChangeListener {
    void onPageAdd(int i);

    void onPageDelete(List<Integer> list);

    void onPageInsert(int i, int i2);

    void onPageSelectChange(int i);
}
