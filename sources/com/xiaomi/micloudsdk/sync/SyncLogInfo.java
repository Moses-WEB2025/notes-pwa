package com.xiaomi.micloudsdk.sync;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.verificationsdk.internal.Constants;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
abstract class SyncLogInfo {
    protected final String authority;

    public abstract String generateLogInfoString();

    SyncLogInfo(String str) {
        this.authority = str;
    }

    protected StringBuilder appendSyncExtras(StringBuilder sb, Bundle bundle) {
        sb.append("extras:").append("{");
        sb.append(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE + "=" + bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false)).append(",");
        sb.append("force=" + bundle.getBoolean(Constants.FORCE, false)).append(",");
        sb.append("ignore_backoff=" + bundle.getBoolean("ignore_backoff", false)).append(",");
        sb.append("upload=" + bundle.getBoolean("upload", false));
        String string = bundle.getString(MiCloudConstants.SYNC.SYNC_EXTRAS_XIAOMI_REQUEST_TYPE);
        if (!TextUtils.isEmpty(string)) {
            sb.append(",").append("xiaomi_request=" + string);
        }
        return sb.append(StringSubstitutor.DEFAULT_VAR_END);
    }

    protected static String getDeltaTime(long j, long j2) {
        long j3 = (j2 - j) / 1000;
        if (j3 < 60) {
            return String.valueOf(j3);
        }
        if (j3 < 3600) {
            return String.format("%02d:%02d", Long.valueOf(j3 / 60), Long.valueOf(j3 % 60));
        }
        long j4 = j3 % 3600;
        return String.format("%02d:%02d:%02d", Long.valueOf(j3 / 3600), Long.valueOf(j4 / 60), Long.valueOf(j4 % 60));
    }

    protected static String formatTime(long j) {
        Time time = new Time();
        time.set(j);
        return time.format("%Y-%m-%d %H:%M:%S");
    }

    static class SyncStartLogInfo extends SyncLogInfo {
        private final Bundle mExtras;
        private final long mStartTime;

        public SyncStartLogInfo(String str, long j, Bundle bundle) {
            super(str);
            this.mStartTime = j;
            this.mExtras = bundle;
        }

        @Override // com.xiaomi.micloudsdk.sync.SyncLogInfo
        public String generateLogInfoString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Start Sync:{authority:").append(this.authority).append(", start:").append(formatTime(this.mStartTime)).append(", ");
            appendSyncExtras(sb, this.mExtras);
            sb.append(StringSubstitutor.DEFAULT_VAR_END);
            return sb.toString();
        }
    }

    static class SyncEndLogInfo extends SyncLogInfo {
        private final long mEndTime;
        private final String mErrorMsg;
        private final boolean mIsSuccess;
        private final long mStartTime;

        SyncEndLogInfo(String str, boolean z, long j, long j2, String str2) {
            super(str);
            this.mIsSuccess = z;
            this.mStartTime = j;
            this.mEndTime = j2;
            if (str2 == null) {
                this.mErrorMsg = "";
            } else {
                this.mErrorMsg = str2;
            }
        }

        @Override // com.xiaomi.micloudsdk.sync.SyncLogInfo
        public String generateLogInfoString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mIsSuccess ? "Success" : "Error").append(":{authority:").append(this.authority).append(", start:").append(formatTime(this.mStartTime)).append(", end:").append(formatTime(this.mEndTime)).append(", delta:").append(getDeltaTime(this.mStartTime, this.mEndTime)).append(", error:");
            sb.append(this.mErrorMsg).append(StringSubstitutor.DEFAULT_VAR_END);
            return sb.toString();
        }
    }
}
