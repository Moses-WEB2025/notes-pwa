package cn.kuaipan.kss.appmaster;

import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssUpload;
import cn.kuaipan.kss.utils.Encode;
import java.io.InputStream;
import java.security.MessageDigest;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MasterHelper {
    public static JSONObject genRequestUploadJson(InputStream inputStream) throws Exception {
        JSONArray jSONArray = new JSONArray();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
        int i = KssDef.CHUNKSIZE_MIN;
        byte[] bArr = new byte[i];
        boolean z = true;
        while (z) {
            int i2 = 0;
            while (i2 < KssDef.BLOCKSIZE) {
                int i3 = inputStream.read(bArr, 0, Math.min(i, KssDef.BLOCKSIZE - i2));
                if (i3 < 0) {
                    z = false;
                    break;
                }
                messageDigest.update(bArr, 0, i3);
                messageDigest2.update(bArr, 0, i3);
                i2 += i3;
            }
            if (i2 == 0) {
                break;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sha1", Encode.byteArrayToHexString(messageDigest.digest()));
            jSONObject.put("md5", Encode.byteArrayToHexString(messageDigest2.digest()));
            jSONObject.put("size", i2);
            jSONArray.put(jSONObject);
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("block_infos", jSONArray);
        return jSONObject2;
    }

    public static JSONObject genCommiteUploadJson(KssUpload.UploadResult uploadResult) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("file_meta", uploadResult.getFileMeta());
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < uploadResult.getBlockCount(); i++) {
            jSONArray.put(new JSONObject().put("commit_meta", uploadResult.getBlockCommitMeta(i)));
        }
        jSONObject.put("commit_metas", jSONArray);
        return jSONObject;
    }

    public static String[] parseKssProxiesInfo(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.get("stat").equals("OK")) {
                return null;
            }
            JSONArray jSONArray = jSONObject.getJSONArray(KssDef.KEY_PROXIES);
            int length = jSONArray.length();
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = jSONArray.getJSONObject(i).getString("url");
            }
            return strArr;
        } catch (Exception unused) {
            return null;
        }
    }
}
