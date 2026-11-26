package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public class VoiceprintRegisterRequest {
    int disableTextDependent;
    String family;
    DataInputMode mode;
    String query;
    String userId;
    String vendorStr;
    int voiceId;

    public enum DataInputMode {
        DATA_INPUT_MODE_RECORDER,
        DATA_INPUT_MODE_BUFFER
    }

    public void addQueryText(String str) {
        this.query = str;
    }

    public void setDataInputMode(DataInputMode dataInputMode) {
        this.mode = dataInputMode;
    }

    public void setFamilyId(String str) {
        this.family = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setVoiceId(int i) {
        this.voiceId = i;
    }

    public void setVoiceprintDisableTextDependent(int i) {
        this.disableTextDependent = i;
    }
}
