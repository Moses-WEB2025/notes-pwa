package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public class TtsRequest {
    String a;
    int b;
    int c;
    int d;
    int e;
    boolean f = false;
    String g;
    NlpRequest h;
    String vendorStr;

    String a() {
        return this.vendorStr;
    }

    public String getTextToSpeak() {
        return this.a;
    }

    public void setExtraNlp(NlpRequest nlpRequest) {
        this.h = nlpRequest;
    }

    public void setTextToSpeak(String str) {
        this.a = str;
    }

    public void setTimeout(int i) {
        this.b = i;
    }

    public void setTtsAsFileUrl(boolean z) {
        this.f = z;
    }

    public void setTtsLang(String str) {
        this.g = str;
    }

    public void setTtsSpeed(int i) {
        this.c = i;
    }

    public void setTtsTone(int i) {
        this.e = i;
    }

    public void setTtsVolume(int i) {
        this.d = i;
    }
}
