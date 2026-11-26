package com.xiaomi.onetrack.c;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class z {
    private static final String a = "TransformChannelManager";
    private static z b;

    public static z a() {
        if (b == null) {
            b();
        }
        return b;
    }

    public static void b() {
        if (b == null) {
            synchronized (z.class) {
                if (b == null) {
                    b = new z();
                }
            }
        }
    }

    private z() {
    }

    public void a(String str, boolean z) {
        if (z != com.xiaomi.onetrack.util.s.b(str)) {
            if (z) {
                c();
            } else {
                d();
            }
        }
    }

    public void c() {
        c.a(new aa(this));
    }

    public void d() {
        c.a(new ab(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ac acVar) {
        Iterator<y> it = acVar.a.iterator();
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            y next = it.next();
            String strB = next.b();
            com.xiaomi.onetrack.b.o oVarB = com.xiaomi.onetrack.b.p.a().b(strB);
            if (oVarB == null || TextUtils.isEmpty(oVarB.b)) {
                arrayList.add(Long.valueOf(next.a()));
                it.remove();
            } else {
                next.c(oVarB.b);
                next.d(com.xiaomi.onetrack.b.p.a().e(strB));
                next.a(com.xiaomi.onetrack.util.a.b());
            }
        }
        acVar.c.removeAll(arrayList);
    }
}
