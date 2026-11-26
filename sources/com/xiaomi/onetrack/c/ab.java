package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class ab implements Runnable {
    final /* synthetic */ z a;

    ab(z zVar) {
        this.a = zVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        int iA;
        int i = 0;
        int i2 = 100;
        while (true) {
            try {
                if (com.xiaomi.onetrack.util.r.a) {
                    i2 = 1000;
                }
                if (i >= i2) {
                    return;
                }
                ac acVarB = d.a().b();
                if (acVarB != null && acVarB.a != null && acVarB.a.size() != 0) {
                    this.a.a(acVarB);
                    boolean zA = n.a().a(acVarB);
                    com.xiaomi.onetrack.util.r.a("TransformChannelManager", "addTransformDataToDB success:" + zA);
                    if (zA && (iA = d.a().a(acVarB.c)) == 0) {
                        com.xiaomi.onetrack.util.r.a("TransformChannelManager", "删除数据不成功，deleted：" + iA);
                    }
                    if (acVarB.d) {
                        com.xiaomi.onetrack.util.r.a("TransformChannelManager", "No more records ");
                        return;
                    }
                    i++;
                }
                com.xiaomi.onetrack.util.r.a("TransformChannelManager", "OTToPS,need transform data is null");
                return;
            } catch (Exception e) {
                com.xiaomi.onetrack.util.r.a("TransformChannelManager", "pSDBToOTDB e：" + e.getMessage());
                return;
            }
        }
    }
}
