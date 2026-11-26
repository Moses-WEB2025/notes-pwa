package miui.cloud.sync;

/* JADX INFO: loaded from: classes5.dex */
public class VipInfo {
    public final boolean autoRenew;
    public final long expireTime;
    public final String level;
    public final String vipName;

    public VipInfo(String str, String str2) {
        this.vipName = str;
        this.level = str2;
        this.expireTime = -1L;
        this.autoRenew = false;
    }

    public VipInfo(String str, String str2, long j, boolean z) {
        this.vipName = str;
        this.level = str2;
        this.expireTime = j;
        this.autoRenew = z;
    }
}
