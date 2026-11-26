package androidx.test.platform.io;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.test.platform.app.InstrumentationRegistry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class FileTestStorage implements PlatformTestStorage {
    private static final String TAG = "FileTestStorage";
    private final TestDirCalculator testDirCalculator = new TestDirCalculator();

    @Override // androidx.test.platform.io.PlatformTestStorage
    public InputStream openInputFile(String pathname) throws FileNotFoundException {
        return new FileInputStream(new File(this.testDirCalculator.getInputDir(), pathname));
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public OutputStream openOutputFile(String pathname) throws FileNotFoundException {
        return openOutputFile(pathname, false);
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public OutputStream openOutputFile(String pathname, boolean append) throws FileNotFoundException {
        File file = new File(this.testDirCalculator.getOutputDir(), pathname);
        Log.d("FileTestStorage", "openOutputFile from " + file.getAbsolutePath());
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new FileNotFoundException("Failed to create output dir " + file.getParentFile().getAbsolutePath());
        }
        return new FileOutputStream(file, append);
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public String getInputArg(String argName) {
        return InstrumentationRegistry.getArguments().getString(argName);
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public Map<String, String> getInputArgs() {
        HashMap map = new HashMap();
        Bundle arguments = InstrumentationRegistry.getArguments();
        for (String str : arguments.keySet()) {
            map.put(str, arguments.getString(str));
        }
        return map;
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public void addOutputProperties(Map<String, Serializable> properties) {
        Log.w(TAG, "Output properties is not supported.");
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public Map<String, Serializable> getOutputProperties() {
        Log.w(TAG, "Output properties is not supported.");
        return Collections.emptyMap();
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public InputStream openInternalInputFile(String pathname) throws FileNotFoundException {
        return openInputFile(pathname);
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public OutputStream openInternalOutputFile(String pathname) throws FileNotFoundException {
        return openOutputFile(pathname);
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public Uri getInputFileUri(String pathname) {
        return Uri.fromFile(new File(this.testDirCalculator.getInputDir(), pathname));
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public Uri getOutputFileUri(String pathname) {
        return Uri.fromFile(new File(this.testDirCalculator.getOutputDir(), pathname));
    }

    @Override // androidx.test.platform.io.PlatformTestStorage
    public boolean isTestStorageFilePath(String pathname) {
        return pathname.startsWith(this.testDirCalculator.getOutputDir().getAbsolutePath());
    }
}
