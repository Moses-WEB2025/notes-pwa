package cn.kuaipan.kss.appmaster;

import android.text.TextUtils;
import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssUpload;
import cn.kuaipan.kss.utils.Encode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class RequestUploadParse implements KssUpload.RequestUploadInfo {
    private KssUpload.RequestUploadInfo.RequestUploadState m_uploadState = KssUpload.RequestUploadInfo.RequestUploadState.NeedUpload;
    private String m_fileStoreID = null;
    private String[] m_nodeIPs = null;
    private String m_protocolsStr = null;
    private byte[] m_secureKey = null;
    private String m_fileMeta = null;
    private String[] m_blockMeta = null;
    private boolean[] m_blockIsExist = null;
    private int m_dataSize = 0;

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public KssDef.KssAPIResult getResult() {
        return KssDef.KssAPIResult.OK;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public KssUpload.RequestUploadInfo.RequestUploadState getRequestUploadState() {
        return this.m_uploadState;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getFileStoreID() {
        return this.m_fileStoreID;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public int getNodeIPCount() {
        return this.m_nodeIPs.length;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getNodeIP(int i) {
        return this.m_nodeIPs[0];
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getProtocol() {
        return this.m_protocolsStr;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public byte[] getSeureKey() {
        return this.m_secureKey;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getFileMeta() {
        return this.m_fileMeta;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public int getBlockCount() {
        String[] strArr = this.m_blockMeta;
        if (strArr == null) {
            return 0;
        }
        return strArr.length;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getBlockMeta(int i) {
        String[] strArr = this.m_blockMeta;
        if (strArr == null || i < 0 || i >= strArr.length) {
            return null;
        }
        return strArr[i];
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public boolean getBlockIsExist(int i) {
        String[] strArr = this.m_blockMeta;
        if (strArr == null || i < 0 || i >= strArr.length) {
            return false;
        }
        return this.m_blockIsExist[i];
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public int getFileSize() {
        return this.m_dataSize;
    }

    public boolean parseRequestUploadInfo(String str) {
        _clear();
        try {
            return parseInfo(new JSONObject(str));
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean parseRequestUploadInfo(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        return parseInfo(jSONObject);
    }

    private void _clear() {
        this.m_nodeIPs = null;
        this.m_protocolsStr = null;
        this.m_secureKey = null;
        this.m_fileMeta = null;
        this.m_blockMeta = null;
        this.m_blockIsExist = null;
        this.m_dataSize = 0;
    }

    private boolean parseInfo(JSONObject jSONObject) {
        try {
            if (jSONObject.getString("stat").equals("FILE_EXISTED")) {
                this.m_uploadState = KssUpload.RequestUploadInfo.RequestUploadState.FileExisted;
                this.m_fileStoreID = jSONObject.getString(KssDef.KEY_STOID);
                return true;
            }
            this.m_uploadState = KssUpload.RequestUploadInfo.RequestUploadState.NeedUpload;
            this.m_fileStoreID = "";
            JSONArray jSONArray = jSONObject.getJSONArray("node_urls");
            if (jSONArray.length() < 1) {
                return false;
            }
            this.m_nodeIPs = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = jSONArray.getString(i);
                int iIndexOf = string.indexOf("://");
                if (this.m_protocolsStr == null && iIndexOf > 0) {
                    this.m_protocolsStr = string.substring(0, iIndexOf);
                }
                this.m_nodeIPs[i] = string.substring(iIndexOf + 3, string.length());
            }
            String strOptString = jSONObject.optString("secure_key");
            if (!TextUtils.isEmpty(strOptString)) {
                this.m_secureKey = Encode.hexStringToByteArray(strOptString);
            }
            this.m_fileMeta = jSONObject.getString("file_meta");
            JSONArray jSONArray2 = jSONObject.getJSONArray("block_metas");
            this.m_blockMeta = new String[jSONArray2.length()];
            this.m_blockIsExist = new boolean[jSONArray2.length()];
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
                int i3 = jSONObject2.getInt("is_existed");
                boolean[] zArr = this.m_blockIsExist;
                boolean z = i3 != 0;
                zArr[i2] = z;
                if (z) {
                    this.m_blockMeta[i2] = jSONObject2.getString("commit_meta");
                } else {
                    this.m_blockMeta[i2] = jSONObject2.getString("block_meta");
                }
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static KssUpload.RequestUploadInfo getFileExitRequestInfo() {
        return _RequestInfo_FileExisted.getInstance();
    }
}
