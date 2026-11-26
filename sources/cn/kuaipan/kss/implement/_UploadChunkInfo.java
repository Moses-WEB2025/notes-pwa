package cn.kuaipan.kss.implement;

import cn.kuaipan.kss.KssDef;
import org.json.JSONObject;

/* JADX INFO: compiled from: HttpClientUpload.java */
/* JADX INFO: loaded from: classes.dex */
class _UploadChunkInfo {
    private String m_stateStr = null;
    private String m_uploadID = null;
    private String m_commitMeta = null;

    _UploadChunkInfo() {
    }

    public String getUploadID() {
        return this.m_uploadID;
    }

    public String getCommitMeta() {
        return this.m_commitMeta;
    }

    public void reset() {
        this.m_stateStr = null;
        this.m_uploadID = null;
        this.m_commitMeta = null;
    }

    public KssDef.KssAPIResult parseInfo(String str) {
        KssDef.KssAPIResult kssAPIResult = KssDef.KssAPIResult.OK;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("stat");
            this.m_stateStr = string;
            if (string.equals("CONTINUE_UPLOAD")) {
                if (this.m_uploadID == null && jSONObject.has("upload_id")) {
                    this.m_uploadID = jSONObject.getString("upload_id");
                }
                if (this.m_uploadID == null) {
                    return KssDef.KssAPIResult.Error;
                }
            } else if (this.m_stateStr.equals("BLOCK_COMPLETED")) {
                if (jSONObject.has("commit_meta")) {
                    this.m_commitMeta = jSONObject.getString("commit_meta");
                    return kssAPIResult;
                }
            } else if (needReRequestMetadata(this.m_stateStr)) {
                return KssDef.KssAPIResult.NeedRequest;
            }
            return kssAPIResult;
        } catch (Exception e) {
            e.printStackTrace();
            return KssDef.KssAPIResult.Error;
        }
    }

    private boolean needReRequestMetadata(String str) {
        String[] strArr = {cn.kuaipan.android.kss.KssDef.ERR_INVALID_FILE_META, cn.kuaipan.android.kss.KssDef.ERR_INVALID_BLOCK_META, cn.kuaipan.android.kss.KssDef.ERR_INVALID_UPLOAD_ID, cn.kuaipan.android.kss.KssDef.ERR_BLOCK_CORRUPTED};
        for (int i = 0; i < 4; i++) {
            if (str.equals(strArr[i])) {
                return true;
            }
        }
        return false;
    }
}
