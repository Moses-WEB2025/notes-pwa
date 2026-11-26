package cn.kuaipan.android.http;

/* JADX INFO: loaded from: classes.dex */
public class KscSpeedMonitor {
    private final String mHost;
    private long mLatestUpdate = KscSpeedManager.current();
    private final KscSpeedManager mManager;

    public KscSpeedMonitor(KscSpeedManager kscSpeedManager, String str) {
        this.mManager = kscSpeedManager;
        this.mHost = str;
    }

    public void recode(long j, long j2, long j3) {
        KscSpeedManager kscSpeedManager = this.mManager;
        if (kscSpeedManager != null) {
            kscSpeedManager.recoder(this.mHost, j, j2, j3);
            if (j2 > this.mLatestUpdate) {
                this.mLatestUpdate = j2;
            }
        }
    }

    public void recode(long j) {
        if (this.mManager != null) {
            long jCurrent = KscSpeedManager.current();
            this.mManager.recoder(this.mHost, this.mLatestUpdate, jCurrent, j);
            this.mLatestUpdate = jCurrent;
        }
    }
}
