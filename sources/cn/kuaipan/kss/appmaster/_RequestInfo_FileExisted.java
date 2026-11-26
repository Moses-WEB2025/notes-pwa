package cn.kuaipan.kss.appmaster;

import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssUpload;

/* JADX INFO: compiled from: RequestUploadParse.java */
/* JADX INFO: loaded from: classes.dex */
class _RequestInfo_FileExisted implements KssUpload.RequestUploadInfo {
    static KssUpload.RequestUploadInfo requestInfo;

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public int getBlockCount() {
        return 0;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public boolean getBlockIsExist(int i) {
        return false;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getBlockMeta(int i) {
        return null;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getFileMeta() {
        return null;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public int getFileSize() {
        return 0;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getFileStoreID() {
        return null;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getNodeIP(int i) {
        return null;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public int getNodeIPCount() {
        return 0;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public String getProtocol() {
        return null;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public byte[] getSeureKey() {
        return null;
    }

    _RequestInfo_FileExisted() {
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public KssDef.KssAPIResult getResult() {
        return KssDef.KssAPIResult.OK;
    }

    @Override // cn.kuaipan.kss.KssUpload.RequestUploadInfo
    public KssUpload.RequestUploadInfo.RequestUploadState getRequestUploadState() {
        return KssUpload.RequestUploadInfo.RequestUploadState.FileExisted;
    }

    public static KssUpload.RequestUploadInfo getInstance() {
        if (requestInfo == null) {
            requestInfo = new _RequestInfo_FileExisted();
        }
        return requestInfo;
    }
}
