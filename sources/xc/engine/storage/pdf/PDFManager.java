package xc.engine.storage.pdf;

/* JADX INFO: loaded from: classes6.dex */
public class PDFManager {

    private static final class InstanceHolder {
        static final PDFManager instance = new PDFManager();

        private InstanceHolder() {
        }
    }

    public static PDFManager getInstance() {
        return InstanceHolder.instance;
    }

    public PDFReader createReader(String str) {
        return new PDFReader(str);
    }

    public PDFWriter createWriter(String str) {
        return new PDFWriter(str);
    }
}
