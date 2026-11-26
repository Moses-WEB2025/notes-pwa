package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class aa implements Runnable {
    final /* synthetic */ z a;

    aa(z zVar) {
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
                ac acVarC = n.a().c();
                if (acVarC != null && acVarC.a != null && acVarC.a.size() != 0) {
                    boolean zA = d.a().a(acVarC);
                    com.xiaomi.onetrack.util.r.a("TransformChannelManager", "addTransformDataToDB success:" + zA);
                    if (zA && (iA = n.a().a(acVarC.c)) == 0) {
                        com.xiaomi.onetrack.util.r.a("TransformChannelManager", "删除数据不成功，deleted：" + iA);
                    }
                    if (acVarC.d) {
                        com.xiaomi.onetrack.util.r.a("TransformChannelManager", "No more records ");
                        return;
                    }
                    i++;
                }
                com.xiaomi.onetrack.util.r.a("TransformChannelManager", "PSToOT,need transform data is null");
                return;
            } catch (Exception e) {
                com.xiaomi.onetrack.util.r.a("TransformChannelManager", "pSDBToOTDB e：" + e.getMessage());
                return;
            }
        }
    }
}
