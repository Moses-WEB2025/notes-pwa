package com.xiaomi.e2ee.appkey;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.utils.Base64;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;
import miuix.internal.hybrid.SignUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AppKeyServiceManager implements ServiceConnection {
    private static final String SERVICE_ACTION = "com.cloud.action.MICLOUD_KEYCHAIN";
    private static final String SERVICE_PACKAGE = "com.miui.cloudservice";
    private static final String TAG = "AppKeyServiceManager_Log";
    private static final long WAIT_FOR_BIND_SERVICE = 30000;
    private static volatile AppKeyServiceManager sInstance;
    private Context mAppContext;
    private volatile AppKeyInfo mAppKeyInfo;
    private volatile boolean mIsAppKeyInit;
    private volatile boolean mIsBind;
    private String mNameSpace;
    private IMiCloudKeyChainService mService;
    private final Object mFlushAppKeyLock = new Object();
    private final Map<Long, AppKeyInfo> mAppKeyPool = new HashMap();
    private final Map<Long, Object> mVersionLockPool = new HashMap();
    private final AtomicInteger mServiceUsingCount = new AtomicInteger();
    private final BroadcastReceiver mE2EEStatusChangeReceiver = new BroadcastReceiver() { // from class: com.xiaomi.e2ee.appkey.AppKeyServiceManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i(AppKeyServiceManager.TAG, "onReceive");
            AppKeyServiceManager.this.setAppKeyExpired();
        }
    };

    private AppKeyServiceManager() {
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mService = IMiCloudKeyChainService.Stub.asInterface(iBinder);
        notifyAll();
        registerE2EEStatusChangeReceiver();
        Log.i(TAG, "onServiceConnected");
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceDisconnected(ComponentName componentName) {
        this.mService = null;
        this.mIsBind = false;
        unRegisterE2EEStatusChangeReceiver();
        Log.i(TAG, "onServiceDisconnected");
    }

    private synchronized IMiCloudKeyChainService getService() throws InterruptedException, RemoteException {
        if (!this.mIsBind) {
            Intent intent = new Intent();
            intent.setAction(SERVICE_ACTION);
            intent.setPackage("com.miui.cloudservice");
            if (!this.mAppContext.bindService(intent, this, 1)) {
                return null;
            }
            this.mIsBind = true;
        }
        if (this.mService == null) {
            wait(30000L);
        }
        IMiCloudKeyChainService iMiCloudKeyChainService = this.mService;
        if (iMiCloudKeyChainService != null) {
            return iMiCloudKeyChainService;
        }
        throw new RemoteException("bind service time out");
    }

    public void release() {
        checkInit();
        this.mAppKeyPool.clear();
        this.mVersionLockPool.clear();
    }

    public static AppKeyServiceManager getInstance() {
        if (sInstance == null) {
            synchronized (AppKeyServiceManager.class) {
                if (sInstance == null) {
                    sInstance = new AppKeyServiceManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context, String str) {
        this.mNameSpace = str;
        this.mAppContext = context.getApplicationContext();
    }

    public AppKeyInfo getAppKeyInfo() throws InterruptedException, E2EEException {
        checkInit();
        if (!this.mIsAppKeyInit) {
            synchronized (this.mFlushAppKeyLock) {
                if (!this.mIsAppKeyInit) {
                    flushAppKeyInfoLocked(true);
                    this.mIsAppKeyInit = true;
                }
            }
        }
        return this.mAppKeyInfo;
    }

    public AppKeyInfo getAppKeyInfo(long j) throws InterruptedException, E2EEException {
        checkInit();
        AppKeyInfo appKeyInfoFromCache = getAppKeyInfoFromCache(j);
        if (appKeyInfoFromCache != null) {
            return appKeyInfoFromCache;
        }
        synchronized (getOrCreateLockByVersion(j)) {
            if (getAppKeyInfoFromCache(j) == null) {
                getAppKeyInfoLocked(j);
            }
        }
        return getAppKeyInfoFromCache(j);
    }

    public void setAppKeyExpired() {
        checkInit();
        Log.i(TAG, "set appkey expired");
        this.mIsAppKeyInit = false;
    }

    public String decryptJsonStr(String str) throws JSONException, E2EEException {
        JSONObject jSONObject = new JSONObject(str);
        jSONObject.put(AppKeyInfo.DECRYPT_APPKEY, decryptRsaAppKey(jSONObject.getString(AppKeyInfo.DECRYPT_APPKEY)));
        return jSONObject.toString();
    }

    public void flushAppKeyInfoLocked(boolean z) throws InterruptedException, E2EEException {
        OnGetAppKeyCallback binder = AppKeyCallBackPool.getBinder();
        String orCreateTransferKeyInKeyStoreLocked = TransferKeyContext.getOrCreateTransferKeyInKeyStoreLocked();
        try {
            try {
                this.mServiceUsingCount.incrementAndGet();
                IMiCloudKeyChainService service = getService();
                if (service == null) {
                    Log.i(TAG, "bind service failed, return");
                } else {
                    if (z) {
                        service.getRemoteAppKeyInfo(this.mNameSpace, orCreateTransferKeyInKeyStoreLocked, binder);
                    } else {
                        service.getAppKeyInfo(this.mNameSpace, orCreateTransferKeyInKeyStoreLocked, binder);
                    }
                    this.mAppKeyInfo = binder.getResult();
                    cacheAppKeyInfo(this.mAppKeyInfo);
                }
                binder.resetCountDown();
                AppKeyCallBackPool.recycle(binder);
                this.mServiceUsingCount.decrementAndGet();
                unbindServiceDelay();
            } catch (RemoteException e) {
                throw new E2EEException(-200, e.getMessage());
            }
        } catch (Throwable th) {
            if (1 != 0) {
                binder.resetCountDown();
                AppKeyCallBackPool.recycle(binder);
            }
            this.mServiceUsingCount.decrementAndGet();
            unbindServiceDelay();
            throw th;
        }
    }

    private synchronized Object getOrCreateLockByVersion(long j) {
        Object obj;
        obj = this.mVersionLockPool.get(Long.valueOf(j));
        if (obj == null) {
            obj = new Object();
            this.mVersionLockPool.put(Long.valueOf(j), obj);
        }
        return obj;
    }

    private AppKeyInfo getAppKeyInfoLocked(long j) throws InterruptedException, E2EEException {
        AppKeyInfo appKeyInfoFromCache = getAppKeyInfoFromCache(j);
        if (appKeyInfoFromCache != null) {
            return appKeyInfoFromCache;
        }
        OnGetAppKeyCallback binder = AppKeyCallBackPool.getBinder();
        String orCreateTransferKeyInKeyStoreLocked = TransferKeyContext.getOrCreateTransferKeyInKeyStoreLocked();
        try {
            try {
                this.mServiceUsingCount.incrementAndGet();
                IMiCloudKeyChainService service = getService();
                if (service == null) {
                    Log.i(TAG, "bind service failed, return");
                    binder.resetCountDown();
                    AppKeyCallBackPool.recycle(binder);
                    this.mServiceUsingCount.decrementAndGet();
                    unbindServiceDelay();
                    return null;
                }
                service.getAppKeyInfoByVersion(this.mNameSpace, j, orCreateTransferKeyInKeyStoreLocked, binder);
                AppKeyInfo result = binder.getResult();
                cacheAppKeyInfo(result);
                binder.resetCountDown();
                AppKeyCallBackPool.recycle(binder);
                this.mServiceUsingCount.decrementAndGet();
                unbindServiceDelay();
                return result;
            } catch (RemoteException e) {
                throw new E2EEException(-200, e.getMessage());
            }
        } catch (Throwable th) {
            if (1 != 0) {
                binder.resetCountDown();
                AppKeyCallBackPool.recycle(binder);
            }
            this.mServiceUsingCount.decrementAndGet();
            unbindServiceDelay();
            throw th;
        }
    }

    private void unbindServiceDelay() {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledExecutorServiceNewScheduledThreadPool.schedule(new Runnable() { // from class: com.xiaomi.e2ee.appkey.AppKeyServiceManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2517x2b686334();
            }
        }, 30000L, TimeUnit.MILLISECONDS);
        scheduledExecutorServiceNewScheduledThreadPool.shutdown();
    }

    /* JADX INFO: renamed from: lambda$unbindServiceDelay$0$com-xiaomi-e2ee-appkey-AppKeyServiceManager, reason: not valid java name */
    /* synthetic */ void m2517x2b686334() {
        if (this.mService != null && this.mIsBind && this.mServiceUsingCount.get() == 0) {
            Log.i(TAG, "unbind keychain service");
            this.mAppContext.unbindService(this);
            this.mIsBind = false;
        }
    }

    private void cacheAppKeyInfo(AppKeyInfo appKeyInfo) {
        if (appKeyInfo != null) {
            this.mAppKeyPool.put(Long.valueOf(appKeyInfo.appKeyVersion), appKeyInfo);
        }
    }

    private void checkInit() {
        if (this.mAppContext == null || this.mNameSpace == null) {
            throw new IllegalArgumentException("context is null or nameSpace is null");
        }
    }

    private AppKeyInfo getAppKeyInfoFromCache(long j) {
        return this.mAppKeyPool.get(Long.valueOf(j));
    }

    private String decryptRsaAppKey(String str) throws E2EEException {
        try {
            PrivateKey transferPrivateKey = TransferKeyContext.getTransferPrivateKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
            cipher.init(2, transferPrivateKey);
            return Base64.encodeToString(cipher.doFinal(Base64.decode(str, 11)), 11);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new E2EEException(E2EEException.DECODE_DATA_ERROR, e);
        }
    }

    private void registerE2EEStatusChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter("com.cloud.e2ee.E2EE_STATUS_CHANGED");
        if (Build.VERSION.SDK_INT >= 33) {
            this.mAppContext.registerReceiver(this.mE2EEStatusChangeReceiver, intentFilter, 2);
        } else {
            this.mAppContext.registerReceiver(this.mE2EEStatusChangeReceiver, intentFilter);
        }
    }

    private void unRegisterE2EEStatusChangeReceiver() {
        this.mAppContext.unregisterReceiver(this.mE2EEStatusChangeReceiver);
    }

    private static class TransferKeyContext {
        private static final String TRANSFER_KEY_ALIAS = "e2ee.transferkey";

        private TransferKeyContext() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static synchronized String getOrCreateTransferKeyInKeyStoreLocked() throws E2EEException {
            KeyStore androidKeyStore = getAndroidKeyStore();
            try {
                KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) androidKeyStore.getEntry(TRANSFER_KEY_ALIAS, null);
                if (privateKeyEntry != null) {
                    return getPubKeyStr(privateKeyEntry.getCertificate().getPublicKey());
                }
                try {
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SignUtils.KEY_RSA, "AndroidKeyStore");
                    keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(TRANSFER_KEY_ALIAS, 3).setDigests("SHA-1").setEncryptionPaddings("OAEPPadding").setBlockModes("ECB").setCertificateSerialNumber(BigInteger.TEN).setCertificateSubject(new X500Principal("CN=e2ee.transferkey")).build());
                    KeyStore.PrivateKeyEntry privateKeyEntry2 = (KeyStore.PrivateKeyEntry) androidKeyStore.getEntry(TRANSFER_KEY_ALIAS, null);
                    if (privateKeyEntry2 != null) {
                        return getPubKeyStr(privateKeyEntry2.getCertificate().getPublicKey());
                    }
                    return getPubKeyStr(keyPairGenerator.generateKeyPair().getPublic());
                } catch (InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | UnrecoverableEntryException e) {
                    throw new E2EEException(E2EEException.ANDROID_KEY_STORE_ERROR, e);
                }
            } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e2) {
                throw new E2EEException(E2EEException.ANDROID_KEY_STORE_ERROR, e2);
            }
            throw th;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PrivateKey getTransferPrivateKey() throws E2EEException {
            try {
                PrivateKey privateKey = (PrivateKey) getAndroidKeyStore().getKey(TRANSFER_KEY_ALIAS, null);
                if (privateKey != null) {
                    return privateKey;
                }
                throw new E2EEException(E2EEException.ANDROID_KEY_STORE_ERROR, "not init e2ee transferKey");
            } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
                throw new E2EEException(E2EEException.ANDROID_KEY_STORE_ERROR, e);
            }
        }

        private static KeyStore getAndroidKeyStore() throws E2EEException {
            try {
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);
                return keyStore;
            } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
                throw new E2EEException(E2EEException.ANDROID_KEY_STORE_ERROR, e);
            }
        }

        private static String getPubKeyStr(PublicKey publicKey) {
            return Base64.encodeToString(publicKey.getEncoded(), 11);
        }
    }
}
