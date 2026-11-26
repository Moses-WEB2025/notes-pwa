package com.miui.notes.ui.jsbridge;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class MindShareDataHolder {
    private static volatile MindShareDataHolder instance;
    private Map dataList = new HashMap();

    public static MindShareDataHolder getInstance() {
        if (instance == null) {
            synchronized (MindShareDataHolder.class) {
                if (instance == null) {
                    instance = new MindShareDataHolder();
                }
            }
        }
        return instance;
    }

    private MindShareDataHolder() {
    }

    public void setData(String str, Object obj) {
        this.dataList.put(str, new WeakReference(obj));
    }

    public Object getData(String str) {
        WeakReference weakReference = (WeakReference) this.dataList.get(str);
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
