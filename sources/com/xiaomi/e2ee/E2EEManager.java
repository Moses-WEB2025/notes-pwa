package com.xiaomi.e2ee;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.xiaomi.e2ee.appkey.AppKeyInfo;
import com.xiaomi.e2ee.appkey.AppKeyServiceManager;
import com.xiaomi.e2ee.appkey.DecryptKeyEntry;
import com.xiaomi.e2ee.appkey.KeyEntry;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import com.xiaomi.e2ee.utils.ThreadGuard;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public class E2EEManager {
    private static final String E2EE_STATUS_KEY = "hyper_cloud_e2ee_status_micloud";
    private static final String GALLERY_PACKAGE_NAME = "com.miui.gallery";
    private static final String OS_VERSION_KEY = "ro.mi.os.version.code";
    private static final String TAG = "E2EEManager_Log";
    private static volatile E2EEManager sInstance;
    private final AppKeyServiceManager mAppKeyServiceManager = AppKeyServiceManager.getInstance();
    private Context mContext;
    private boolean mIsInit;

    private E2EEManager() {
    }

    public static E2EEManager getInstance() {
        if (sInstance == null) {
            synchronized (E2EEManager.class) {
                if (sInstance == null) {
                    sInstance = new E2EEManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context, String str) {
        Log.i(TAG, "init e2ee");
        checkEmpty(context, str);
        this.mContext = context;
        this.mAppKeyServiceManager.init(context, str);
        this.mIsInit = true;
    }

    private void checkEmpty(Context context, String str) {
        if (context == null || str == null) {
            throw new IllegalArgumentException("context or nameSpace is null");
        }
    }

    public void release() {
        checkInit();
        this.mAppKeyServiceManager.release();
    }

    public void setAppKeyExpired() {
        this.mAppKeyServiceManager.setAppKeyExpired();
    }

    public boolean isE2EEOpen() {
        checkInit();
        try {
            return Settings.Secure.getInt(this.mContext.getContentResolver(), E2EE_STATUS_KEY) == 0;
        } catch (Settings.SettingNotFoundException unused) {
            Log.e(TAG, "not found hyper_cloud_e2ee_status_micloud");
            return false;
        }
    }

    public KeyEntry getKeyEntry() throws InterruptedException, E2EEException {
        ThreadGuard.checkNotRunInMainThread("getKeyEntry method should not run in main thread");
        checkInit();
        AppKeyInfo appKeyInfo = this.mAppKeyServiceManager.getAppKeyInfo();
        if (appKeyInfo == null) {
            return null;
        }
        String randomRecordIV = E2EECommonUtils.getRandomRecordIV();
        return new KeyEntry(RecordKeyHelper.getEncryptedRecordKey(RecordKeyHelper.getRecordKey(), appKeyInfo.decryptAppKey, randomRecordIV), randomRecordIV, appKeyInfo.appKeyVersion);
    }

    public String encryptData(String str, KeyEntry keyEntry) throws InterruptedException, E2EEException {
        ThreadGuard.checkNotRunInMainThread("encryptData method should not run in main thread");
        checkInit();
        return new StrCipher().encrypt(str, keyEntry);
    }

    public String decryptData(String str, KeyEntry keyEntry) throws InterruptedException, E2EEException {
        ThreadGuard.checkNotRunInMainThread("decryptData method should not run in main thread");
        checkInit();
        return new StrCipher().decrypt(str, keyEntry);
    }

    public InputStream encryptStream(InputStream inputStream, KeyEntry keyEntry) throws InterruptedException, E2EEException {
        ThreadGuard.checkNotRunInMainThread("encryptStream method should not run in main thread");
        checkInit();
        return new FileCipher().decrypt(inputStream, keyEntry);
    }

    public InputStream decryptStream(InputStream inputStream, KeyEntry keyEntry) throws InterruptedException, E2EEException {
        ThreadGuard.checkNotRunInMainThread("decryptStream method should not run in main thread");
        checkInit();
        return new FileCipher().decrypt(inputStream, keyEntry);
    }

    public DecryptKeyEntry getDecryptKeyEntryForGallery(KeyEntry keyEntry) throws InterruptedException, E2EEException {
        ThreadGuard.checkNotRunInMainThread("getDecryptKeyEntryForGallery method should not run in main thread");
        checkInit();
        if ("com.miui.gallery".equals(this.mContext.getPackageName())) {
            return new DecryptKeyEntry(E2EECommonUtils.decode(RecordKeyHelper.getDecryptRecordKey(keyEntry.encryptedRecordKey, keyEntry.recordIV, keyEntry.appKeyVersion)), E2EECommonUtils.decode(keyEntry.recordIV));
        }
        throw new IllegalArgumentException("only gallery can use this method");
    }

    public boolean hasInitE2EE() {
        return this.mIsInit;
    }

    private void checkInit() {
        if (!this.mIsInit) {
            throw new IllegalStateException("not init E2EE yet");
        }
    }
}
