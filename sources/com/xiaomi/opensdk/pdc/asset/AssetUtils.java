package com.xiaomi.opensdk.pdc.asset;

import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import com.xiaomi.opensdk.pdc.SyncException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AssetUtils {
    public static AssetEntity[] parseJSONArrayToEntities(JSONArray jSONArray, String str) throws SyncException {
        int length = jSONArray.length();
        AssetEntity[] assetEntityArr = new AssetEntity[length];
        int i = 0;
        while (i < length) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String str2 = str;
                assetEntityArr[i] = new AssetEntity(str2, jSONObject.getString(AssetConsts.J_ASSET_ID), jSONObject.getLong("size"), jSONObject.optBoolean("existed", false));
                i++;
                str = str2;
            } catch (JSONException unused) {
                throw new SyncException("AssetUtils parseJSONArrayToEntities() JSONException");
            }
        }
        return assetEntityArr;
    }

    public static AssetEntity getAssetEntityFromJson(String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(AssetConsts.J_ASSET_INFO);
        return new AssetEntity(str, jSONObject2.getString(AssetConsts.J_ASSET_ID), jSONObject2.getLong("size"), jSONObject.optBoolean("existed", false));
    }

    public static UrlEntity getUrlEntityFromJson(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(FileSDKUtils.J_STORAGE);
        return new UrlEntity(jSONObject2.getLong("expireAt"), jSONObject2.getString(AssetConsts.J_DOWNLOAD_URL));
    }
}
