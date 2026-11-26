package cn.kuaipan.android.kss.upload;

import android.util.Log;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.kss.KssDef;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

/* JADX INFO: loaded from: classes.dex */
public class ServerExpect implements KssDef {
    private static final String HEADER_FACTORY_MODE = "X-Factory-Mode";
    private static final String HEADER_NEXT_CHUNK_SIZE = "X-Next-Chunk-Size";
    private static final String HEADER_UPLOAD_DELAY = "X-Upload-Delay";
    private static final String LOG_TAG = "ServerExpect";
    private static final int MAX_DELAY = 300;
    public boolean factoryMode = false;
    public int uploadDelay = -1;
    public long nextChunkSize = -1;

    public static ServerExpect getServerExpect(KscHttpResponse kscHttpResponse) {
        HttpResponse response = kscHttpResponse == null ? null : kscHttpResponse.getResponse();
        if (response == null) {
            return null;
        }
        ServerExpect serverExpect = new ServerExpect();
        Header firstHeader = response.getFirstHeader(HEADER_FACTORY_MODE);
        boolean z = false;
        boolean z2 = true;
        if (firstHeader != null) {
            serverExpect.factoryMode = getInt(firstHeader) == 1;
            z = true;
        }
        Header firstHeader2 = response.getFirstHeader(HEADER_UPLOAD_DELAY);
        if (firstHeader2 != null) {
            serverExpect.uploadDelay = getInt(firstHeader2);
            z = true;
        }
        Header firstHeader3 = response.getFirstHeader(HEADER_NEXT_CHUNK_SIZE);
        if (firstHeader3 != null) {
            serverExpect.nextChunkSize = getInt(firstHeader3);
        } else {
            z2 = z;
        }
        if (z2) {
            return serverExpect;
        }
        return null;
    }

    public void validate() {
        long j = this.nextChunkSize;
        if (j >= 0) {
            long j2 = j - (j % 65536);
            this.nextChunkSize = j2;
            long jMin = Math.min(j2, MAX_CHUNKSIZE);
            this.nextChunkSize = jMin;
            this.nextChunkSize = Math.max(jMin, 65536L);
        }
        int i = this.uploadDelay;
        if (i <= 0 || this.factoryMode) {
            return;
        }
        this.uploadDelay = Math.min(i, 300);
    }

    private static int getInt(Header header) {
        if (header == null) {
            return -1;
        }
        try {
            return Integer.parseInt(header.getValue());
        } catch (NumberFormatException unused) {
            Log.w(LOG_TAG, "Failed parser header: " + header);
            return -1;
        }
    }
}
