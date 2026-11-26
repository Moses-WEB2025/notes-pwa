package cn.kuaipan.kss;

import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public interface KssUpload {

    public interface RequestUploadInfo {

        public enum RequestUploadState {
            NeedUpload,
            FileExisted
        }

        int getBlockCount();

        boolean getBlockIsExist(int i);

        String getBlockMeta(int i);

        String getFileMeta();

        int getFileSize();

        String getFileStoreID();

        String getNodeIP(int i);

        int getNodeIPCount();

        String getProtocol();

        RequestUploadState getRequestUploadState();

        KssDef.KssAPIResult getResult();

        byte[] getSeureKey();
    }

    public interface UploadResult {
        String getBlockCommitMeta(int i);

        int getBlockCount();

        String getFileMeta();

        KssDef.KssAPIResult getResult();
    }

    public interface UploadTransControl {
    }

    UploadResult getCommitInfo() throws Exception;

    boolean init(RequestUploadInfo requestUploadInfo, KssDef.OnUpDownload onUpDownload, KssDef.NetState netState);

    void terminal();

    KssDef.KssAPIResult upload(InputStream inputStream) throws Exception;

    KssDef.KssAPIResult upload(byte[] bArr) throws Exception;
}
