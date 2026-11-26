package cn.kuaipan.android.kss.upload;

import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.utils.ApiDataHelper;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class UploadThumbnailInfo implements KssDef {
    private static final HashSet<String> sReTrySet;
    private static final HashSet<String> sSessionExpiredSet;
    public final int clusterId;
    public final String commitMeta;
    public final String kssState;
    public final String meta;
    public final int resolution;
    public final String sha1;
    public final long size;
    public final String stat;

    static {
        HashSet<String> hashSet = new HashSet<>();
        sSessionExpiredSet = hashSet;
        hashSet.add(KssDef.ERR_INVALID_FILE_META);
        hashSet.add(KssDef.ERR_INVALID_BLOCK_META);
        hashSet.add(KssDef.ERR_INVALID_UPLOAD_ID);
        HashSet<String> hashSet2 = new HashSet<>();
        sReTrySet = hashSet2;
        hashSet2.add(KssDef.ERR_SERVER_EXCEPTION);
        hashSet2.add(KssDef.ERR_STORAGE_REQUEST_ERROR);
        hashSet2.add(KssDef.ERR_STORAGE_REQUEST_FAILED);
    }

    public UploadThumbnailInfo(Map<String, Object> map) {
        this.sha1 = ApiDataHelper.asString(map, "sha1");
        this.size = ApiDataHelper.asNumber(map.get("size"), 0).longValue();
        this.meta = ApiDataHelper.asString(map, "meta");
        this.commitMeta = ApiDataHelper.asString(map, "commit_meta");
        this.kssState = ApiDataHelper.asString(map, KssDef.KEY_KSS_STATE);
        this.clusterId = ApiDataHelper.asNumber(map.get(KssDef.KEY_CLUSTER_ID), 0).intValue();
        this.resolution = ApiDataHelper.asNumber(map.get("resolution"), 0).intValue();
        this.stat = ApiDataHelper.asString(map, "stat");
    }

    public boolean isComplete() {
        return "OK".equalsIgnoreCase(this.kssState) && this.stat == null;
    }

    public boolean needRetry() {
        return KssDef.ERR_BLOCK_CORRUPTED.equalsIgnoreCase(this.stat);
    }

    public boolean canRetry() {
        String str = this.stat;
        return sReTrySet.contains(str == null ? null : str.toUpperCase());
    }

    public boolean isSessionExpired() {
        String str = this.stat;
        return sSessionExpiredSet.contains(str == null ? null : str.toUpperCase());
    }
}
