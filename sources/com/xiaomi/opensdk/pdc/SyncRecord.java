package com.xiaomi.opensdk.pdc;

import com.xiaomi.opensdk.pdc.asset.AssetEntity;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SyncRecord {
    public final AssetEntity[] assetEntities;
    public final JSONObject contentJson;
    public final long eTag;
    public final String id;
    public final String parentId;
    public final Constants.RecordStatus status;
    public final String type;
    public final String uniqueKey;

    public SyncRecord(long j, String str, String str2, String str3, String str4, JSONObject jSONObject, AssetEntity[] assetEntityArr, Constants.RecordStatus recordStatus) {
        this.eTag = j;
        this.id = str;
        this.uniqueKey = str2;
        this.parentId = str3;
        this.type = str4;
        this.contentJson = jSONObject;
        this.assetEntities = assetEntityArr;
        this.status = recordStatus;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SyncRecord)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SyncRecord syncRecord = (SyncRecord) obj;
        return new Object[]{Long.valueOf(this.eTag), this.id, this.uniqueKey, this.parentId, this.type, this.contentJson, this.assetEntities, this.status}.equals(new Object[]{Long.valueOf(syncRecord.eTag), syncRecord.id, syncRecord.uniqueKey, syncRecord.parentId, syncRecord.type, syncRecord.contentJson, syncRecord.assetEntities, syncRecord.status});
    }

    public int hashCode() {
        return new Object[]{Long.valueOf(this.eTag), this.id, this.uniqueKey, this.parentId, this.type, this.contentJson, this.assetEntities, this.status}.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("eTag=");
        sb.append(this.eTag);
        sb.append("; id=").append(this.id);
        sb.append("; uniqueKey=").append(this.uniqueKey);
        sb.append("; parentId=").append(this.parentId);
        sb.append("; type=").append(this.type);
        sb.append("; status=").append(this.status);
        sb.append("; content=").append(this.contentJson);
        AssetEntity[] assetEntityArr = this.assetEntities;
        if (assetEntityArr != null) {
            for (AssetEntity assetEntity : assetEntityArr) {
                sb.append("; asset=").append(assetEntity);
            }
        } else {
            sb.append("; no assets");
        }
        return sb.toString();
    }
}
