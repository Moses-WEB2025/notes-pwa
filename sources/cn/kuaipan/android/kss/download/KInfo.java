package cn.kuaipan.android.kss.download;

import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import cn.kuaipan.android.utils.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/* JADX INFO: loaded from: classes.dex */
public class KInfo {
    private static final String KEY_DATA = "data";
    private static final String KEY_HASH = "hash";
    private static final String KEY_LOAD_MAP = "load_map";
    private static final String LOG_TAG = "KInfo";
    private static final String PREFIX = ".";
    private static final String SUFFIX = ".kinfo";
    private final File mFile;
    private final Bundle mData = new Bundle();
    private final Properties mProp = new Properties();

    public KInfo(File file) {
        this.mFile = file;
    }

    public String getHash() {
        return this.mData.getString("hash");
    }

    public void setHash(String str) {
        this.mData.putString("hash", str);
    }

    public boolean loadToMap(LoadMap loadMap) {
        return loadMap.load(this.mData.getBundle(KEY_LOAD_MAP));
    }

    public void setLoadMap(LoadMap loadMap) {
        Bundle bundle = new Bundle();
        loadMap.save(bundle);
        this.mData.putBundle(KEY_LOAD_MAP, bundle);
    }

    public void delete() {
        this.mFile.delete();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.util.Properties] */
    public void save() throws Throwable {
        IOException e;
        Properties properties = this.mProp;
        ?? BundleToString = bundleToString(this.mData);
        properties.put("data", BundleToString);
        ?? r1 = 0;
        try {
            try {
                try {
                    BundleToString = new FileOutputStream(this.mFile);
                    try {
                        this.mProp.store(BundleToString, null);
                        BundleToString.flush();
                        BundleToString.close();
                    } catch (IOException e2) {
                        e = e2;
                        Log.w(LOG_TAG, "Failed save kinfo to " + this.mFile, e);
                        BundleToString.close();
                    }
                } catch (IOException e3) {
                    BundleToString = 0;
                    e = e3;
                } catch (Throwable th) {
                    th = th;
                    try {
                        r1.close();
                    } catch (Throwable unused) {
                    }
                    throw th;
                }
            } catch (Throwable unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
            r1 = BundleToString;
        }
    }

    public void load() throws Throwable {
        FileInputStream fileInputStream;
        IOException e;
        if (!this.mFile.exists()) {
            return;
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(this.mFile);
                try {
                    try {
                        this.mProp.load(fileInputStream);
                        String property = this.mProp.getProperty("data");
                        if (!TextUtils.isEmpty(property)) {
                            Bundle bundleStringToBundle = stringToBundle(property);
                            this.mData.clear();
                            this.mData.putAll(bundleStringToBundle);
                        }
                        fileInputStream.close();
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream2 = fileInputStream;
                        try {
                            fileInputStream2.close();
                        } catch (Throwable unused) {
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    Log.w(LOG_TAG, "Failed load kinfo from " + this.mFile, e);
                    fileInputStream.close();
                }
            } catch (IOException e3) {
                fileInputStream = null;
                e = e3;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2.close();
                throw th;
            }
        } catch (Throwable unused2) {
        }
    }

    public static File getInfoFile(File file) {
        String parent = file.getParent();
        String str = file.getName() + SUFFIX;
        if (!str.startsWith(PREFIX)) {
            str = PREFIX + str;
        }
        return new File(parent, str);
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            bundle.writeToParcel(parcelObtain, 0);
            return Base64.encodeToString(parcelObtain.marshall(), 0);
        } finally {
            parcelObtain.recycle();
        }
    }

    private static Bundle stringToBundle(String str) {
        byte[] bArrDecode = Base64.decode(str, 0);
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
            parcelObtain.setDataPosition(0);
            return (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }
}
