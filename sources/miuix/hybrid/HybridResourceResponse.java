package miuix.hybrid;

import java.io.InputStream;

/* JADX INFO: loaded from: classes5.dex */
public class HybridResourceResponse {
    private String mEncoding;
    private InputStream mInputStream;
    private String mMimeType;

    public HybridResourceResponse(String str, String str2, InputStream inputStream) {
        this.mMimeType = str;
        this.mEncoding = str2;
        this.mInputStream = inputStream;
    }

    public void setMimeType(String str) {
        this.mMimeType = str;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public void setEncoding(String str) {
        this.mEncoding = str;
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    public void setData(InputStream inputStream) {
        this.mInputStream = inputStream;
    }

    public InputStream getData() {
        return this.mInputStream;
    }
}
