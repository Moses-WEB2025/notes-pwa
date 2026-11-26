package cn.kuaipan.android.kss.thumbnail;

import com.xiaomi.opensdk.file.model.Parameter;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ThumbnailUploadParameter implements Parameter {
    private String mKssUploadString;
    private String mUploadId;

    public void setUploadId(String str) {
        this.mUploadId = str;
    }

    public String getUploadId() {
        return this.mUploadId;
    }

    public void setKssUploadString(String str) {
        this.mKssUploadString = str;
    }

    @Override // com.xiaomi.opensdk.file.model.Parameter
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jSONObject = new JSONObject(this.mKssUploadString);
        if (jSONObject.has(FileSDKUtils.J_SMALL_THUMB)) {
            return parseJson(jSONObject, FileSDKUtils.J_SMALL_THUMB);
        }
        if (jSONObject.has(FileSDKUtils.J_LARGE_THUMB)) {
            return parseJson(jSONObject, FileSDKUtils.J_LARGE_THUMB);
        }
        throw new JSONException("no value for small or large");
    }

    private JSONObject parseJson(JSONObject jSONObject, String str) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject3 = new JSONObject();
        jSONObject2.put("stat", jSONObject.getString("stat"));
        jSONObject2.put("node_urls", jSONObject.getJSONArray("urls"));
        jSONObject2.put("secure_key", jSONObject.optString("secure_key"));
        jSONObject2.put("contentCacheKey", jSONObject.getString("contentCacheKey"));
        jSONObject2.put("file_meta", jSONObject.getJSONObject(str).getString("meta"));
        jSONObject3.put("block_meta", jSONObject.getJSONObject(str).getString("meta"));
        jSONObject3.put("is_existed", jSONObject.getJSONObject(str).getBoolean("is_existed") ? 1 : 0);
        jSONArray.put(jSONObject3);
        jSONObject2.put("block_metas", jSONArray);
        return jSONObject2;
    }
}
