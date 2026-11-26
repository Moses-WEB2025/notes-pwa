package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public interface MibrainRequestListener {
    void onEventTrack(String str);

    void onInstruction(MibrainRequest mibrainRequest, String str);

    String onNeedUpdateToken();

    void onRequestASRResult(MibrainRequest mibrainRequest, String str, boolean z);

    void onRequestError(MibrainRequest mibrainRequest, String str, int i, int i2);

    String onRequestGetToken(MibrainRequest mibrainRequest, boolean z);

    void onRequestNLpResult(MibrainRequest mibrainRequest, String str);

    void onRequestOtherResult(MibrainRequest mibrainRequest, String str);

    void onRequestSessionEnd(MibrainRequest mibrainRequest);

    void onRequestSessionStart(MibrainRequest mibrainRequest);

    void onRequestTTSResult(MibrainRequest mibrainRequest, String str, byte[] bArr, boolean z);

    void onRequestVoiceprintDeleteResult(MibrainRequest mibrainRequest, String str);

    void onRequestVoiceprintQueryResult(MibrainRequest mibrainRequest, String str);

    void onRequestVoiceprintRecognizedResult(MibrainRequest mibrainRequest, String str);

    void onRequestVoiceprintRegisteredResult(MibrainRequest mibrainRequest, String str, boolean z);
}
