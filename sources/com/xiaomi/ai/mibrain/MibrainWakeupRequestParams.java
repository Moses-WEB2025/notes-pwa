package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class MibrainWakeupRequestParams {
    String apiKey;
    String appId;
    int channel;
    String codec;
    byte[] data;
    String deviceId;
    String extra;
    int rate;
    String scopeData;
    String token;
    String vendor;
    String word;

    public String getScopeData() {
        return this.scopeData;
    }

    public void setScopeData(String str) {
        this.scopeData = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String str) {
        this.apiKey = str;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String str) {
        this.vendor = str;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String str) {
        this.word = str;
    }

    public String getCodec() {
        return this.codec;
    }

    public void setCodec(String str) {
        this.codec = str;
    }

    public int getChannel() {
        return this.channel;
    }

    public void setChannel(int i) {
        this.channel = i;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int i) {
        this.rate = i;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String str) {
        this.extra = str;
    }
}
