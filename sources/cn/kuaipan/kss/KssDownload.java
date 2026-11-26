package cn.kuaipan.kss;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public interface KssDownload {

    public interface BlockDownloadInfo {
        String getBlockDownload_FullURL(int i);

        int getBlockIndex();

        byte[] getBlockSHA1();

        int getBlockSize();

        int getDownloadURLCount();
    }

    public interface DownloadTransControl {

        public enum EndState {
            Completed,
            Interrupt,
            Transing,
            DataCorrupted
        }

        int addData(byte[] bArr, int i) throws IOException;

        int getStartPos();

        void setEndState(EndState endState);
    }

    public interface RequestDownloadInfo {
        int getBlockCount();

        BlockDownloadInfo getBlockInfos(int i);

        DownloadEncryptInfo getDownloadEncryptInfo();

        int getFileSize();

        KssDef.KssAPIResult getResult();

        String getSecureKey();
    }

    KssDef.KssAPIResult download(DownloadTransControl downloadTransControl) throws Exception;

    KssDef.KssAPIResult download(OutputStream outputStream, int i) throws Exception;

    boolean init(RequestDownloadInfo requestDownloadInfo, KssDef.OnUpDownload onUpDownload, KssDef.NetState netState);

    void terminal();
}
