package com.xiaomi.ai;

import android.media.AudioTrack;

/* JADX INFO: loaded from: classes4.dex */
class j implements com.xiaomi.ai.streamplayer.k.a {
    final /* synthetic */ c a;

    j(c cVar) {
        this.a = cVar;
    }

    @Override // com.xiaomi.ai.streamplayer.k.a
    public void a(com.xiaomi.ai.streamplayer.k kVar) {
        synchronized (this.a.o) {
            if (kVar == this.a.i && this.a.i != null) {
                this.a.notifyTtsPlayFinish();
            }
        }
    }

    @Override // com.xiaomi.ai.streamplayer.k.a
    public void a(com.xiaomi.ai.streamplayer.k kVar, int i, String str) {
        synchronized (this.a.o) {
            if (kVar == this.a.i && this.a.i != null) {
                SpeechError speechError = new SpeechError(SpeechEngine.ProcessStage.STAGE_TTS);
                speechError.errMsg = str;
                speechError.errCode = i;
                speechError.errType = 2;
                this.a.notifyTtsError(speechError);
            }
        }
    }

    @Override // com.xiaomi.ai.streamplayer.k.a
    public void a(com.xiaomi.ai.streamplayer.k kVar, AudioTrack audioTrack) {
        synchronized (this.a.o) {
            if (kVar == this.a.i && this.a.i != null) {
                this.a.notifyTtsPlayStart(audioTrack);
            }
        }
    }

    @Override // com.xiaomi.ai.streamplayer.k.a
    public void a(com.xiaomi.ai.streamplayer.k kVar, PCMInfo pCMInfo) {
        synchronized (this.a.o) {
            if (kVar == this.a.i && this.a.i != null) {
                this.a.notifyTtsPCMData(pCMInfo);
            }
        }
    }
}
