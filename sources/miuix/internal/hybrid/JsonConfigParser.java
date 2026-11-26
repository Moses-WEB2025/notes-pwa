package miuix.internal.hybrid;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes5.dex */
public class JsonConfigParser implements ConfigParser {
    private static final String KEY_CONTENT = "content";
    private static final String KEY_FEATURES = "features";
    private static final String KEY_NAME = "name";
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_PARAMS = "params";
    private static final String KEY_PERMISSIONS = "permissions";
    private static final String KEY_SIGNATURE = "signature";
    private static final String KEY_SUBDOMAINS = "subdomains";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_VALUE = "value";
    private static final String KEY_VENDOR = "vendor";
    private JSONObject mJson;

    private Config buildCompleteConfig(Config config, Map<String, Object> map) {
        return config;
    }

    private JsonConfigParser(JSONObject jSONObject) {
        this.mJson = jSONObject;
    }

    public static JsonConfigParser createFromString(String str) throws HybridException {
        try {
            return createFromJSONObject(new JSONObject(str));
        } catch (JSONException e) {
            throw new HybridException(201, e.getMessage());
        }
    }

    public static JsonConfigParser createFromJSONObject(JSONObject jSONObject) {
        return new JsonConfigParser(jSONObject);
    }

    @Override // miuix.internal.hybrid.ConfigParser
    public Config parse(Map<String, Object> map) throws HybridException {
        Config config = new Config();
        try {
            JSONObject jSONObject = this.mJson;
            Security security = new Security();
            security.setSignature(jSONObject.getString("signature"));
            security.setTimestamp(jSONObject.getLong("timestamp"));
            config.setSecurity(security);
            config.setVendor(jSONObject.getString(KEY_VENDOR));
            config.setContent(jSONObject.optString("content"));
            parseFeatures(config, jSONObject);
            parsePermissions(config, jSONObject);
            return buildCompleteConfig(config, map);
        } catch (JSONException e) {
            throw new HybridException(201, e.getMessage());
        }
    }

    private void parseFeatures(Config config, JSONObject jSONObject) throws JSONException {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(KEY_FEATURES);
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                Feature feature = new Feature();
                feature.setName(jSONObject2.getString("name"));
                JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("params");
                if (jSONArrayOptJSONArray2 != null) {
                    for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                        JSONObject jSONObject3 = jSONArrayOptJSONArray2.getJSONObject(i2);
                        feature.setParam(jSONObject3.getString("name"), jSONObject3.getString("value"));
                    }
                }
                config.addFeature(feature);
            }
        }
    }

    private void parsePermissions(Config config, JSONObject jSONObject) throws JSONException {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(KEY_PERMISSIONS);
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                Permission permission = new Permission();
                permission.setUri(jSONObject2.getString("origin"));
                permission.setApplySubdomains(jSONObject2.optBoolean(KEY_SUBDOMAINS));
                config.addPermission(permission);
            }
        }
    }
}
