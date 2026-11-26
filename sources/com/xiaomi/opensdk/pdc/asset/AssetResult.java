package com.xiaomi.opensdk.pdc.asset;

import com.xiaomi.opensdk.pdc.Constants;
import com.xiaomi.opensdk.pdc.SyncOperation;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AssetResult extends SyncOperation.Result {
    int code;
    JSONObject data;

    public AssetResult(boolean z, String str, Constants.ErrorType errorType, int i, long j, String str2, int i2, JSONObject jSONObject) {
        super(z, str, errorType, i, j, str2);
        this.code = i2;
        this.data = jSONObject;
    }
}
