package com.xiaomi.onetrack.b;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class o {
    public static int i = 3;
    public String a;
    public String b;
    public String c;
    public String e;
    public long f;
    private com.xiaomi.onetrack.h.a j;
    public List<String> d = new ArrayList();
    public int g = 0;
    public long h = 0;

    public com.xiaomi.onetrack.h.a a() {
        com.xiaomi.onetrack.h.a aVar = this.j;
        if (aVar != null) {
            return aVar;
        }
        if (!TextUtils.isEmpty(this.e)) {
            this.j = com.xiaomi.onetrack.h.a.a(this.e);
        }
        return this.j;
    }

    public void a(com.xiaomi.onetrack.h.a aVar) {
        if (aVar != null) {
            this.j = aVar;
            if (TextUtils.isEmpty(aVar.g)) {
                return;
            }
            this.e = aVar.g;
        }
    }

    public void b() {
        this.j = null;
        this.e = "";
    }
}
