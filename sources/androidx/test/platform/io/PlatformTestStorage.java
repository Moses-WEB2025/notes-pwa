package androidx.test.platform.io;

import android.net.Uri;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface PlatformTestStorage {
    void addOutputProperties(Map<String, Serializable> properties);

    String getInputArg(String argName);

    Map<String, String> getInputArgs();

    Uri getInputFileUri(String pathname);

    Uri getOutputFileUri(String pathname);

    Map<String, Serializable> getOutputProperties();

    boolean isTestStorageFilePath(String pathname);

    InputStream openInputFile(String pathname) throws FileNotFoundException;

    OutputStream openOutputFile(String pathname) throws FileNotFoundException;

    OutputStream openOutputFile(String pathname, boolean append) throws FileNotFoundException;

    default InputStream openInternalInputFile(String pathname) throws FileNotFoundException {
        return openInputFile(pathname);
    }

    default OutputStream openInternalOutputFile(String pathname) throws FileNotFoundException {
        return openOutputFile(pathname);
    }
}
