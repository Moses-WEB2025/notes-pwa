package com.xiaomi.opensdk.pdc.asset;

/* JADX INFO: loaded from: classes4.dex */
public class AssetEntity {
    public final String assetID;
    public final boolean existed;
    public final long size;
    public final String type;

    public AssetEntity(String str, String str2, long j) {
        this(str, str2, j, false);
    }

    public AssetEntity(String str, String str2, long j, boolean z) {
        this.type = str;
        this.assetID = str2;
        this.size = j;
        this.existed = z;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AssetEntity) {
            AssetEntity assetEntity = (AssetEntity) obj;
            if (this.type.equals(assetEntity.type) && this.assetID.equals(assetEntity.assetID) && this.size == assetEntity.size) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return "AssetEntity [type=" + this.type + ", assetID=" + this.assetID + ", size=" + this.size + "]";
    }
}
