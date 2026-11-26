package xc.engine.storage.ent.file;

/* JADX INFO: loaded from: classes6.dex */
public class FileInfo {
    private String mFilePath;
    private short mFileType;
    private short mFileUse;

    public void setFilePath(String str) {
        this.mFilePath = str;
    }

    public void setFileType(short s) {
        this.mFileType = s;
    }

    public void setFileUse(short s) {
        this.mFileUse = s;
    }

    public short getFileType() {
        return this.mFileType;
    }

    public short getFileUse() {
        return this.mFileUse;
    }

    public String getFilePath() {
        return this.mFilePath;
    }
}
