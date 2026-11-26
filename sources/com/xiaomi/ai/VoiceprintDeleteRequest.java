package com.xiaomi.ai;

import org.json.JSONArray;

/* JADX INFO: loaded from: classes4.dex */
public class VoiceprintDeleteRequest {
    String family;
    JSONArray ids = new JSONArray();
    String vendorStr;

    public void addUserId(String str) {
        this.ids.put(str);
    }

    public void setFamily(String str) {
        this.family = str;
    }
}
