package com.xiaomi.micloudsdk.sync;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudSyncResult {
    public final Throwable error;
    final int errorCodeLegacy;
    final String errorReasonForStats;
    public final boolean isSuccess;

    private MiCloudSyncResult(boolean z, Throwable th, int i, String str) {
        this.isSuccess = z;
        this.error = th;
        this.errorCodeLegacy = i;
        this.errorReasonForStats = str;
    }

    static MiCloudSyncResult createSuccessResult() {
        return new MiCloudSyncResult(true, null, 0, MiCloudStatConstantsV2.NO_ERROR);
    }

    static MiCloudSyncResult createFailResult(Throwable th, int i, String str) {
        if (th == null) {
            throw new IllegalArgumentException("error must be nonNull");
        }
        return new MiCloudSyncResult(false, th, i, str);
    }

    public String toString() {
        return "MiCloudSyncResult{isSuccess=" + this.isSuccess + ", error=" + this.error + ", errorCodeLegacy=" + this.errorCodeLegacy + ", errorReasonForStats='" + this.errorReasonForStats + "'}";
    }
}
