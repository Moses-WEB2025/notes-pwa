package net.lingala.zip4j.io.inputstream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: classes6.dex */
public class ZipStandardSplitInputStream extends SplitInputStream {
    private int lastSplitZipFileNumber;

    public ZipStandardSplitInputStream(File file, boolean z, int i) throws FileNotFoundException {
        super(file, z, i);
        this.lastSplitZipFileNumber = i;
    }

    @Override // net.lingala.zip4j.io.inputstream.SplitInputStream
    protected File getNextSplitFile(int i) throws IOException {
        String str;
        if (i == this.lastSplitZipFileNumber) {
            return this.zipFile;
        }
        String canonicalPath = this.zipFile.getCanonicalPath();
        if (i < 9) {
            str = ".z0";
        } else {
            str = ".z";
        }
        return new File(canonicalPath.substring(0, canonicalPath.lastIndexOf(".")) + str + (i + 1));
    }
}
