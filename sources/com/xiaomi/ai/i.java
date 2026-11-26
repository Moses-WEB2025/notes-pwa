package com.xiaomi.ai;

import com.xiaomi.ai.c.a;
import com.xiaomi.ai.c.d;
import com.xiaomi.ai.mibrain.MibrainRequest;
import com.xiaomi.ai.utils.Log;
import com.xiaomi.ai.utils.UploadHelper;
import java.util.Arrays;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
class i implements Runnable {
    final /* synthetic */ SpeechEngine.ParamBuilder a;
    final /* synthetic */ c b;

    i(c cVar, SpeechEngine.ParamBuilder paramBuilder) {
        this.b = cVar;
        this.a = paramBuilder;
    }

    @Override // java.lang.Runnable
    public void run() {
        SpeechError speechError;
        c cVar;
        SpeechEngine.ParamBuilder paramBuilder;
        SpeechError speechError2;
        c cVar2;
        SpeechEngine.ParamBuilder paramBuilder2;
        SpeechError speechError3;
        c cVar3;
        SpeechEngine.ParamBuilder paramBuilder3;
        synchronized (this.b.o) {
            SpeechEngine.ParamBuilder paramBuilder4 = this.b.r;
            SpeechEngine.ParamBuilder paramBuilder5 = this.a;
            if (paramBuilder4 != paramBuilder5) {
                Log.MiLogW("MiSpeechSDK:MiAiEngine", "new task is already begin!\n");
            } else if (paramBuilder5.needAsr || this.a.needVpRec || this.a.needVpReg) {
                if (this.b.k != null) {
                    this.b.k.interrupt();
                }
                if (com.xiaomi.ai.utils.f.a(this.b.mContext)) {
                    try {
                        this.b.m = new MibrainRequest(this.b.b(this.a));
                        this.b.m.setGetAnonymousAuthorizationCallback(this.b.a);
                        Log.MiLogD("MiSpeechSDK:MiAiEngine", " new mMibrainRequest = " + this.b.m);
                        c cVar4 = this.b;
                        c.d dVar = cVar4.new d(this.a, cVar4.m);
                        this.b.m.setRequestListener(dVar);
                        if (this.b.n == null) {
                            this.b.a("startRequest released!");
                            speechError2 = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                            speechError2.errMsg = "startRequest released!";
                            speechError2.errCode = -1;
                            speechError2.errType = 2;
                            cVar2 = this.b;
                            paramBuilder2 = this.a;
                        } else {
                            this.b.n.a(this.b.m);
                            this.b.n.a(dVar);
                            int iStartRequest = this.b.m.startRequest();
                            if (iStartRequest < 0) {
                                String str = "startRequest failed: ret=" + iStartRequest;
                                this.b.a(str);
                                speechError2 = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                                speechError2.errMsg = str;
                                speechError2.errCode = iStartRequest;
                                speechError2.errType = 2;
                                cVar2 = this.b;
                                paramBuilder2 = this.a;
                            } else {
                                if (this.a.needWakeupData) {
                                    if (this.a.wakeupData.length <= 32768) {
                                        this.b.m.addRawData(this.a.wakeupData, 1, false, true);
                                    } else {
                                        int length = this.a.wakeupData.length / 32768;
                                        int length2 = this.a.wakeupData.length % 32768;
                                        int i = 0;
                                        while (i < length) {
                                            int i2 = i + 1;
                                            this.b.m.addRawData(Arrays.copyOfRange(this.a.wakeupData, i * 32768, i2 * 32768), 1, false, i == length + (-1) && length2 == 0);
                                            i = i2;
                                        }
                                        if (length2 != 0) {
                                            this.b.m.addRawData(Arrays.copyOfRange(this.a.wakeupData, length * 32768, this.a.wakeupData.length), 1, false, true);
                                        }
                                    }
                                    this.b.m.addRawData(UploadHelper.generateSpliterJSON().getBytes(), 4, false, false);
                                }
                                c cVar5 = this.b;
                                c cVar6 = this.b;
                                cVar5.k = cVar6.new a(cVar6.m, this.a);
                                dVar.a(this.b.k);
                                this.b.k.start();
                            }
                        }
                        cVar2.notifyError(paramBuilder2, speechError2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        this.b.a("buildRequestMsg failed !!: ");
                        speechError = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                        speechError.errMsg = "buildRequestMsg failed !!: ";
                        speechError.errCode = 1;
                        speechError.errType = 2;
                        cVar = this.b;
                        paramBuilder = this.a;
                        cVar.notifyError(paramBuilder, speechError);
                    }
                } else {
                    this.b.a("Network error ");
                    speechError3 = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                    speechError3.errMsg = "Network error ";
                    speechError3.errCode = 13;
                    speechError3.errType = 2;
                    cVar3 = this.b;
                    paramBuilder3 = this.a;
                    cVar3.notifyError(paramBuilder3, speechError3);
                }
            } else if (com.xiaomi.ai.utils.f.a(this.b.mContext)) {
                try {
                    this.b.m = new MibrainRequest(this.b.b(this.a));
                    this.b.m.setGetAnonymousAuthorizationCallback(this.b.a);
                    Log.MiLogD("MiSpeechSDK:MiAiEngine", " new mMibrainRequest = " + this.b.m);
                    c cVar7 = this.b;
                    this.b.m.setRequestListener(cVar7.new d(this.a, cVar7.m));
                    int iStartRequest2 = this.b.m.startRequest();
                    if (iStartRequest2 < 0) {
                        String str2 = "startRequest failed: ret=" + iStartRequest2;
                        this.b.a(str2);
                        SpeechError speechError4 = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                        speechError4.errMsg = str2;
                        speechError4.errCode = iStartRequest2;
                        speechError4.errType = 2;
                        this.b.notifyError(this.a, speechError4);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    speechError = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                    speechError.errMsg = e2.getMessage();
                    speechError.errCode = 1;
                    speechError.errType = 2;
                    cVar = this.b;
                    paramBuilder = this.a;
                    cVar.notifyError(paramBuilder, speechError);
                }
            } else {
                this.b.a("Network error ");
                speechError3 = new SpeechError(SpeechEngine.ProcessStage.STAGE_REQUESTING);
                speechError3.errMsg = "Network error ";
                speechError3.errCode = 13;
                speechError3.errType = 2;
                cVar3 = this.b;
                paramBuilder3 = this.a;
                cVar3.notifyError(paramBuilder3, speechError3);
            }
        }
    }
}
