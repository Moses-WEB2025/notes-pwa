package com.xiaomi.opensdk.file.utils;

import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.kss.TransferStep;
import cn.kuaipan.android.utils.JsonReader;
import cn.kuaipan.android.utils.JsonToken;
import cn.kuaipan.android.utils.JsonUtils;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class FileSDKUtils {
    public static final String J_ENCRYPT_INFO = "encryptInfo";
    public static final String J_EXIST = "existed";
    public static final String J_FILENAME = "filename";
    public static final String J_KSS = "kss";
    public static final String J_LARGE_THUMB = "large";
    public static final String J_MIMETYPE = "mimeType";
    public static final String J_SHA1 = "sha1";
    public static final String J_SIZE = "size";
    public static final String J_SMALL_THUMB = "small";
    public static final String J_STORAGE = "storage";
    public static final String J_UPLOAD_ID = "uploadId";
    public static final String J_UPLOAD_ID_SFS = "upload_id";
    public static final String J_UPLOAD_ID_SFS_V2 = "uploadId";

    public static Map<String, Object> contentKssJsonToMap(JSONObject jSONObject, TransferStep transferStep) throws InterruptedException, KscException {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(jSONObject.toString()));
            Map<String, Object> map = (Map) JsonUtils.parser(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JSONException("Document not end of EOF");
            }
            if (map == null || map.isEmpty()) {
                throw new KscException(ErrorCode.DATA_TYPE_INVALID, jSONObject.toString(), transferStep);
            }
            return map;
        } catch (IOException e) {
            throw KscException.newException(e, "contentKssJsonToMap failed.", transferStep);
        } catch (JSONException e2) {
            throw KscException.newException(e2, "contentKssJsonToMap failed.", transferStep);
        }
    }
}
