package cn.kuaipan.android.kss.transferclient.controller.impl;

import cn.kuaipan.android.kss.transferclient.controller.UploadController;
import cn.kuaipan.android.utils.Encode;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.opensdk.file.model.CommitParameter;
import com.xiaomi.opensdk.file.model.RequestUploadParameter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class ThumbnailUploadController implements UploadController {
    private int mCurrentResolution;

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public Map<String, String> getCommitUploadHeaders() {
        return null;
    }

    public abstract File getLargeThumbFile(String str) throws IOException;

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public Map<String, String> getRequestUploadHeaders() {
        return null;
    }

    public abstract File getSmallThumbFile(String str) throws IOException;

    public abstract Map<String, String> getThumbnailCommitUploadParams(int i);

    public abstract Map<String, String> getThumbnailRequestUploadParams(int i);

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public ThumbnailUploadController getThumbnailUploadController() {
        return null;
    }

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public Map<String, String> getRequestUploadParams(int i, RequestUploadParameter requestUploadParameter) throws Throwable {
        JSONObject thumbnailMetaInfo = getThumbnailMetaInfo(requestUploadParameter.getFileName());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("requestThumbMeta", thumbnailMetaInfo);
        String encryptInfoStrOrNull = requestUploadParameter.getEncryptInfoStrOrNull();
        if (encryptInfoStrOrNull != null) {
            jSONObject.put("encryptInfo", new JSONObject(encryptInfoStrOrNull));
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject);
        Map<String, String> thumbnailRequestUploadParams = getThumbnailRequestUploadParams(i);
        if (thumbnailRequestUploadParams == null) {
            thumbnailRequestUploadParams = new HashMap<>();
        }
        thumbnailRequestUploadParams.put("thumbnailInfos", jSONArray.toString());
        return thumbnailRequestUploadParams;
    }

    @Override // cn.kuaipan.android.kss.transferclient.controller.UploadController
    public Map<String, String> getCommitUploadParams(int i, CommitParameter commitParameter) throws JSONException, E2EEException {
        Map<String, String> thumbnailCommitUploadParams = getThumbnailCommitUploadParams(i);
        if (thumbnailCommitUploadParams == null) {
            thumbnailCommitUploadParams = new HashMap<>();
        }
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("kssCommit", new JSONObject(commitParameter.getThumbnailCommitString()));
        jSONObject.put("uploadId", commitParameter.getUploadId());
        String encryptInfoStrOrNull = commitParameter.getEncryptInfoStrOrNull();
        if (encryptInfoStrOrNull != null) {
            jSONObject.put("encryptInfo", new JSONObject(encryptInfoStrOrNull));
        }
        jSONArray.put(jSONObject);
        thumbnailCommitUploadParams.put("commitMetas", jSONArray.toString());
        return thumbnailCommitUploadParams;
    }

    public void setCurrentResolution(int i) {
        this.mCurrentResolution = i;
    }

    public int getCurrentResolution() {
        return this.mCurrentResolution;
    }

    private JSONObject getThumbnailMetaInfo(String str) throws Throwable {
        JSONObject jSONObject = new JSONObject();
        File file = new File(str);
        byte[] bArr = new byte[16384];
        if (!file.exists()) {
            throw new IOException();
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("md5");
                    MessageDigest messageDigest2 = MessageDigest.getInstance("sha1");
                    while (true) {
                        int i = fileInputStream2.read(bArr);
                        if (i >= 0) {
                            messageDigest.update(bArr, 0, i);
                            messageDigest2.update(bArr, 0, i);
                        } else {
                            jSONObject.put("resolution", this.mCurrentResolution);
                            jSONObject.put("sha1", Encode.byteArrayToHexString(messageDigest2.digest()));
                            jSONObject.put("md5", Encode.byteArrayToHexString(messageDigest.digest()));
                            jSONObject.put("size", file.length());
                            fileInputStream2.close();
                            return jSONObject;
                        }
                    }
                } catch (NoSuchAlgorithmException e) {
                    e = e;
                    throw new E2EEException("NoSuchAlgorithmException happened when get ThumbnailMetaInfo", e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
