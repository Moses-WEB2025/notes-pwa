package com.xiaomi.ai;

import com.xiaomi.ai.mibrain.GetAnonymousAuthorizationInterface;

/* JADX INFO: loaded from: classes4.dex */
class h implements GetAnonymousAuthorizationInterface {
    final /* synthetic */ c a;

    h(c cVar) {
        this.a = cVar;
    }

    @Override // com.xiaomi.ai.mibrain.GetAnonymousAuthorizationInterface
    public void onGetAnonymousAuthorization(String str) {
        this.a.v = str;
    }
}
