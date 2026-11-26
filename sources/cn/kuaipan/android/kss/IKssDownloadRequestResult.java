package cn.kuaipan.android.kss;

import cn.kuaipan.android.kss.download.DownloadEncryptInfo;

/* JADX INFO: loaded from: classes.dex */
public interface IKssDownloadRequestResult {
    public static final int STATUS_REQUEST_FAILED = -1;
    public static final int STATUS_REQUEST_SUCCESS = 0;

    Block getBlock(int i);

    int getBlockCount();

    String[] getBlockUrls(long j);

    DownloadEncryptInfo getDownloadEncryptInfo();

    String getHash();

    String getMessage();

    long getModifyTime();

    byte[] getSecureKey();

    int getStatus();

    long getTotalSize();

    public static class Block {
        public final String encryptedSha1;
        public final String sha1;
        public final long size;
        public final String[] urls;

        public Block(String str, String str2, String[] strArr, long j) {
            this.sha1 = str;
            this.encryptedSha1 = str2;
            this.urls = strArr;
            this.size = j;
        }

        public String[] getUrls() {
            return this.urls;
        }
    }
}
