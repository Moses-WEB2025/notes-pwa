package com.xiaomi.ai;

import android.content.SharedPreferences;
import com.xiaomi.ai.mibrain.MibrainOauthManagerCallbacks;
import com.xiaomi.ai.utils.Log;

/* JADX INFO: loaded from: classes4.dex */
class n implements MibrainOauthManagerCallbacks {
    final /* synthetic */ m a;

    n(m mVar) {
        this.a = mVar;
    }

    @Override // com.xiaomi.ai.mibrain.MibrainOauthManagerCallbacks
    public String getOauthCode() {
        Log.MiLogD(m.b, "getOauthCode");
        if (this.a.c != null) {
            return this.a.c.getOauthCode();
        }
        return null;
    }

    @Override // com.xiaomi.ai.mibrain.MibrainOauthManagerCallbacks
    public String getOauthData(String str) {
        return this.a.d.getSharedPreferences(m.a, 0).getString(str, null);
    }

    @Override // com.xiaomi.ai.mibrain.MibrainOauthManagerCallbacks
    public boolean putOauthData(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.a.d.getSharedPreferences(m.a, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
        return true;
    }
}
