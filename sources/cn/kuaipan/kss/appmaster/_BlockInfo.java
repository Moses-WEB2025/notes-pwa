package cn.kuaipan.kss.appmaster;

import cn.kuaipan.kss.KssDownload;
import cn.kuaipan.kss.utils.Encode;
import org.json.JSONObject;

/* JADX INFO: compiled from: RequestDownloadParse.java */
/* JADX INFO: loaded from: classes.dex */
class _BlockInfo implements KssDownload.BlockDownloadInfo {
    private int m_blockIndex;
    private int m_blockSize = 0;
    private String m_fullURL = null;
    private byte[] m_blockSHA1 = null;

    @Override // cn.kuaipan.kss.KssDownload.BlockDownloadInfo
    public int getBlockIndex() {
        return this.m_blockIndex;
    }

    @Override // cn.kuaipan.kss.KssDownload.BlockDownloadInfo
    public int getBlockSize() {
        return this.m_blockSize;
    }

    @Override // cn.kuaipan.kss.KssDownload.BlockDownloadInfo
    public byte[] getBlockSHA1() {
        return this.m_blockSHA1;
    }

    @Override // cn.kuaipan.kss.KssDownload.BlockDownloadInfo
    public int getDownloadURLCount() {
        String str = this.m_fullURL;
        return (str == null || str.equals("")) ? 0 : 1;
    }

    @Override // cn.kuaipan.kss.KssDownload.BlockDownloadInfo
    public String getBlockDownload_FullURL(int i) {
        return this.m_fullURL;
    }

    public _BlockInfo(int i) {
        this.m_blockIndex = i;
    }

    public boolean parseBlockInfo(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            this.m_blockSHA1 = Encode.hexStringToByteArray(jSONObject.getString("sha1"));
            this.m_fullURL = jSONObject.getJSONArray("urls").getString(0);
            this.m_blockSize = jSONObject.getInt("size");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.m_fullURL = "";
            this.m_blockSize = 0;
            return false;
        }
    }
}
