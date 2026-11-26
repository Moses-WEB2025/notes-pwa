package cn.kuaipan.kss.implement;

import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssUpload;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: HttpClientUpload.java */
/* JADX INFO: loaded from: classes.dex */
class _UploadResult implements KssUpload.UploadResult {
    private KssDef.KssAPIResult m_Result = KssDef.KssAPIResult.OK;
    private String m_fileMeta = null;
    private List<String> m_commitMetas = new ArrayList();

    @Override // cn.kuaipan.kss.KssUpload.UploadResult
    public KssDef.KssAPIResult getResult() {
        return this.m_Result;
    }

    @Override // cn.kuaipan.kss.KssUpload.UploadResult
    public String getFileMeta() {
        return this.m_fileMeta;
    }

    @Override // cn.kuaipan.kss.KssUpload.UploadResult
    public int getBlockCount() {
        return this.m_commitMetas.size();
    }

    @Override // cn.kuaipan.kss.KssUpload.UploadResult
    public String getBlockCommitMeta(int i) {
        if (i < 0 || i >= this.m_commitMetas.size()) {
            return null;
        }
        return this.m_commitMetas.get(i);
    }

    public void setFileMeta(String str) {
        this.m_fileMeta = str;
    }

    public void addCommitMeta(String str) {
        this.m_commitMetas.add(str);
    }
}
