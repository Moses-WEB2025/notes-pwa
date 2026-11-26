package cn.kuaipan.android.kss;

/* JADX INFO: loaded from: classes.dex */
public interface IKssUploadRequestResult {
    public static final int STATUS_AUTO_COMMIT = 1;
    public static final int STATUS_NEED_UPLOAD = 0;
    public static final int STATUS_REQUEST_FAILED = -1;

    Block getBlock(int i);

    int getBlockCount();

    String getFileMeta();

    String[] getNodeUrls();

    byte[] getSecureKey();

    boolean isCompleted();

    public static class Block {
        public boolean exist;
        public String meta;

        public Block(String str, boolean z) {
            this.meta = str;
            this.exist = z;
        }

        public boolean isComplete() {
            return this.exist;
        }
    }
}
