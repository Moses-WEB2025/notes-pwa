package cn.kuaipan.android.kss.upload;

import android.util.Log;
import cn.kuaipan.android.kss.IKssUploadRequestResult;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.utils.OAuthTimeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class KssUploadInfo implements KssDef {
    private static final String LOG_TAG = "KssUploadInfo";
    private long mAppKeyVersion;
    boolean mBroken;
    private String mEncryptedRecordKey;
    ServerExpect mExpectInfo;
    private final UploadFileInfo mFileInfo;
    private final long mGenerateTime;
    private String mRecordIV;
    private final IKssUploadRequestResult mRequestResult;
    private String mUploadId;

    public KssUploadInfo(UploadFileInfo uploadFileInfo, IKssUploadRequestResult iKssUploadRequestResult) {
        this(uploadFileInfo, iKssUploadRequestResult, OAuthTimeUtils.currentTime());
    }

    public KssUploadInfo(UploadFileInfo uploadFileInfo, IKssUploadRequestResult iKssUploadRequestResult, long j) {
        this.mBroken = false;
        this.mExpectInfo = null;
        this.mFileInfo = uploadFileInfo;
        this.mRequestResult = iKssUploadRequestResult;
        this.mGenerateTime = j;
    }

    public void setEncryptedRecordKey(String str) {
        this.mEncryptedRecordKey = str;
    }

    public String getEncryptedRecordKey() {
        return this.mEncryptedRecordKey;
    }

    public String getEncryptedSha1() {
        return this.mFileInfo.getEncryptedSha1();
    }

    public void setAppKeyVersion(long j) {
        this.mAppKeyVersion = j;
    }

    public long getAppKeyVersion() {
        return this.mAppKeyVersion;
    }

    public void setRecordIV(String str) {
        this.mRecordIV = str;
    }

    public String getRecordIV() {
        return this.mRecordIV;
    }

    public UploadFileInfo getFileInfo() {
        return this.mFileInfo;
    }

    public IKssUploadRequestResult getRequestResult() {
        return this.mRequestResult;
    }

    public boolean isCompleted() {
        IKssUploadRequestResult iKssUploadRequestResult = this.mRequestResult;
        return iKssUploadRequestResult != null && iKssUploadRequestResult.isCompleted();
    }

    public long getGenerateTime() {
        return this.mGenerateTime;
    }

    public String getCommitString() {
        Object obj;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("file_meta", this.mRequestResult.getFileMeta());
            int blockCount = this.mRequestResult.getBlockCount();
            JSONArray jSONArray = new JSONArray();
            if (this.mRequestResult != null && blockCount > 0) {
                for (int i = 0; i < blockCount; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("commit_meta", this.mRequestResult.getBlock(i).meta);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("commit_metas", jSONArray);
            obj = jSONObject;
        } catch (JSONException unused) {
            Log.w(LOG_TAG, "Failed generate Json String for UploadRequestResult");
            obj = null;
        }
        return String.valueOf(obj);
    }

    public boolean isBroken() {
        return this.mBroken;
    }

    public void markBroken() {
        this.mBroken = true;
    }

    public String getUploadId() {
        return this.mUploadId;
    }

    public void setUploadId(String str) {
        this.mUploadId = str;
    }

    public void setEncryptInfo(UploadEncryptInfo uploadEncryptInfo) {
        this.mRecordIV = uploadEncryptInfo.getRecordIV();
        this.mAppKeyVersion = uploadEncryptInfo.getAppKeyInfo().appKeyVersion;
        this.mEncryptedRecordKey = uploadEncryptInfo.getEncryptedRecordKey();
        this.mFileInfo.setEncryptedSha1(uploadEncryptInfo.getEncryptedSha1());
    }
}
