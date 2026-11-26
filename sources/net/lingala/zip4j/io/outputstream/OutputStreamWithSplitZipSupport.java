package net.lingala.zip4j.io.outputstream;

import java.io.IOException;

/* JADX INFO: loaded from: classes6.dex */
public interface OutputStreamWithSplitZipSupport {
    int getCurrentSplitFileCounter();

    long getFilePointer() throws IOException;
}
