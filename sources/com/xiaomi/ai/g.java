package com.xiaomi.ai;

import com.xiaomi.ai.mibrain.MibrainRequest;
import com.xiaomi.ai.mibrain.MibrainRequestListener;
import com.xiaomi.ai.mibrain.MibrainWakeupRequestParams;

/* JADX INFO: loaded from: classes4.dex */
class g implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ int e;
    final /* synthetic */ String f;
    final /* synthetic */ byte[] g;
    final /* synthetic */ MibrainRequestListener h;
    final /* synthetic */ c i;

    g(c cVar, int i, String str, String str2, String str3, int i2, String str4, byte[] bArr, MibrainRequestListener mibrainRequestListener) {
        this.i = cVar;
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = i2;
        this.f = str4;
        this.g = bArr;
        this.h = mibrainRequestListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        MibrainWakeupRequestParams mibrainWakeupRequestParams = new MibrainWakeupRequestParams();
        mibrainWakeupRequestParams.setApiKey(this.i.u);
        mibrainWakeupRequestParams.setAppId(this.i.mAppId);
        mibrainWakeupRequestParams.setToken((this.i.mUseExternalOAuthToken == 0 && (this.i.t == SpeechEngine.ENGINE_AUTH_MIAI || this.i.t == SpeechEngine.ENGINE_AUTH_MIAO || this.i.t == SpeechEngine.ENGINE_AUTH_ANONYMOUS || this.i.t == SpeechEngine.ENGINE_AUTH_ANONYMOUS_PROXY)) ? null : this.i.mToken);
        mibrainWakeupRequestParams.setChannel(this.a);
        mibrainWakeupRequestParams.setCodec(this.b);
        mibrainWakeupRequestParams.setVendor(this.c);
        mibrainWakeupRequestParams.setWord(this.d);
        mibrainWakeupRequestParams.setRate(this.e);
        mibrainWakeupRequestParams.setExtra(this.f);
        mibrainWakeupRequestParams.setDeviceId(com.xiaomi.ai.utils.n.b(this.i.mContext));
        mibrainWakeupRequestParams.setData(this.g);
        mibrainWakeupRequestParams.setScopeData(com.xiaomi.ai.utils.n.b(com.xiaomi.ai.utils.n.b(this.i.mContext)));
        MibrainRequest mibrainRequest = new MibrainRequest(mibrainWakeupRequestParams);
        mibrainRequest.setRequestListener(this.h);
        mibrainRequest.startWakeupRequest(this.i.d, this.i.t);
    }
}
