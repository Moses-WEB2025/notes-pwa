package com.xiaomi.security.miunifiedencrypt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.accountsdk.utils.AESCoderForGcm;
import com.xiaomi.trustservice.unifiedencryptservice.AesKeyBag;
import com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService;
import java.util.Arrays;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class MiUnifiedEncrypt {
    private static final int GCM_IV_LENGTH = 12;
    private static final int OPERATION_LEVEL_SOFTWARE = 0;
    private static final int OPERATION_LEVEL_STRONGBOX = 2;
    private static final int OPERATION_LEVEL_TEE = 1;
    private static final String SDK_VERSION = "1.0.6.2026.03.11";
    private static final int SECURITY_LEVEL_STRONGBOX = 2;
    private static final int SECURITY_LEVEL_TEE = 1;
    private static final String TAG = "unifiedEncryptSDK";
    private AesKeyBag mAesKeyBag;
    private String mAlgorithm;
    private String mAlias;
    private volatile byte[] mAppKey;
    private final Object mBindLock;
    private String mBlockModes;
    private ServiceConnection mConnection;
    private Context mContext;
    private String mEncryptionPaddings;
    private volatile boolean mIsBinding;
    private volatile boolean mIsBound;
    private int mKeySize;
    private int mOperationLevel;
    private int mPurposes;
    private int mSecurityLevel;
    private IMiUnifiedEncryptService mService;
    private ServiceReadyListener mServiceListener;

    public interface ServiceReadyListener {
        void onServiceLost();

        void onServiceReady();
    }

    public void connectToService(Context context, ServiceReadyListener serviceReadyListener) {
        synchronized (this.mBindLock) {
            getCallerInfo();
            context.getPackageName();
            Process.myPid();
            Process.myUid();
            if (this.mService != null) {
                Log.d(TAG, "Service already connected");
                if (serviceReadyListener != null) {
                    serviceReadyListener.onServiceReady();
                }
                return;
            }
            if (this.mIsBinding) {
                Log.d(TAG, "Service is binding, skip duplicate request");
                if (serviceReadyListener != null) {
                    serviceReadyListener.onServiceLost();
                }
                return;
            }
            this.mServiceListener = serviceReadyListener;
            this.mIsBinding = true;
            if (this.mIsBound) {
                try {
                    context.unbindService(this.mConnection);
                } catch (Exception e) {
                    Log.w(TAG, "unbindService before rebind failed: " + e);
                }
                this.mIsBound = false;
            }
            Intent intent = new Intent();
            intent.setAction(IMiUnifiedEncryptService.DESCRIPTOR);
            intent.setPackage("com.xiaomi.trustservice");
            boolean zBindService = context.bindService(intent, this.mConnection, 1);
            Log.d(TAG, "Service binding result:" + zBindService);
            if (zBindService) {
                this.mIsBound = true;
            } else {
                this.mIsBinding = false;
                this.mService = null;
                Log.d(TAG, "Service binding fail");
                if (serviceReadyListener != null) {
                    serviceReadyListener.onServiceLost();
                }
            }
        }
    }

    private String getCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length && i < 10; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            String className = stackTraceElement.getClassName();
            if (!className.equals(MiUnifiedEncrypt.class.getName()) && !className.startsWith("java.lang.Thread") && !className.startsWith("dalvik.system")) {
                return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
            }
        }
        return "unknown";
    }

    public void disconnectFromService(Context context) {
        synchronized (this.mBindLock) {
            if (this.mConnection != null && this.mIsBound) {
                try {
                    context.unbindService(this.mConnection);
                } catch (Exception e) {
                    Log.w(TAG, "unbindService failed: " + e);
                }
                this.mIsBound = false;
                this.mIsBinding = false;
                this.mService = null;
            } else {
                this.mIsBinding = false;
                this.mService = null;
            }
            throw th;
        }
    }

    public MiUnifiedEncrypt(Context context, String str, int i, int i2, String str2, String str3, String str4, int i3, int i4) {
        this.mContext = null;
        this.mAesKeyBag = null;
        this.mKeySize = 128;
        this.mPurposes = 3;
        this.mEncryptionPaddings = "NoPadding";
        this.mBlockModes = "GCM";
        this.mAlgorithm = "AES";
        this.mSecurityLevel = 1;
        this.mOperationLevel = 0;
        this.mService = null;
        this.mIsBinding = false;
        this.mIsBound = false;
        this.mBindLock = new Object();
        this.mConnection = new ServiceConnection() { // from class: com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MiUnifiedEncrypt.this.mService = IMiUnifiedEncryptService.Stub.asInterface(iBinder);
                Log.d(MiUnifiedEncrypt.TAG, "entry onServiceConnected, class:" + componentName);
                try {
                    Log.d(MiUnifiedEncrypt.TAG, "service version: " + MiUnifiedEncrypt.this.mService.getVersion());
                    Log.d(MiUnifiedEncrypt.TAG, "SDK version:1.0.6.2026.03.11");
                    if (MiUnifiedEncrypt.this.mAesKeyBag == null) {
                        AesKeyBag[] aesKeyBagArr = new AesKeyBag[1];
                        if (MiUnifiedEncrypt.this.mService == null) {
                            Log.w(MiUnifiedEncrypt.TAG, "Service disconnect. Please re-connect.");
                        }
                        int aesKeyBag = MiUnifiedEncrypt.this.mService.getAesKeyBag(MiUnifiedEncrypt.this.mAlias, aesKeyBagArr);
                        if (aesKeyBag == 0) {
                            MiUnifiedEncrypt.this.mAesKeyBag = aesKeyBagArr[0];
                            byte[] bArr = new byte[MiUnifiedEncrypt.this.mAesKeyBag.keyLength / 8];
                            System.arraycopy(MiUnifiedEncrypt.this.mAesKeyBag.key, 0, bArr, 0, MiUnifiedEncrypt.this.mAesKeyBag.keyLength / 8);
                            MiUnifiedEncrypt.this.mAppKey = bArr;
                            Log.w(MiUnifiedEncrypt.TAG, "get aesKeyBag successful");
                        } else {
                            Log.w(MiUnifiedEncrypt.TAG, "get aesKeyBag fail: " + aesKeyBag);
                        }
                    }
                    if (MiUnifiedEncrypt.this.mServiceListener != null) {
                        MiUnifiedEncrypt.this.mServiceListener.onServiceReady();
                        MiUnifiedEncrypt.this.mIsBinding = false;
                    }
                } catch (RemoteException e) {
                    Log.e(MiUnifiedEncrypt.TAG, "connect service fail. " + e);
                    if (MiUnifiedEncrypt.this.mServiceListener != null) {
                        MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                    }
                    MiUnifiedEncrypt.this.mService = null;
                    MiUnifiedEncrypt.this.mIsBinding = false;
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(MiUnifiedEncrypt.TAG, "unifiedEncrypt service disconnect.");
                MiUnifiedEncrypt.this.mIsBinding = false;
                MiUnifiedEncrypt.this.mService = null;
                if (MiUnifiedEncrypt.this.mServiceListener != null) {
                    MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                }
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                Log.e(MiUnifiedEncrypt.TAG, "unifiedEncrypt service binding died.");
                MiUnifiedEncrypt.this.mIsBinding = false;
                MiUnifiedEncrypt.this.mService = null;
                if (MiUnifiedEncrypt.this.mServiceListener != null) {
                    MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                }
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Log.e(MiUnifiedEncrypt.TAG, "unifiedEncrypt service returned null binding.");
                MiUnifiedEncrypt.this.mIsBinding = false;
                MiUnifiedEncrypt.this.mService = null;
                if (MiUnifiedEncrypt.this.mServiceListener != null) {
                    MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                }
            }
        };
        this.mContext = context;
        this.mAlias = str;
        this.mKeySize = i;
        this.mPurposes = i2;
        this.mEncryptionPaddings = str2;
        this.mBlockModes = str3;
        this.mAlgorithm = str4;
        this.mSecurityLevel = i3;
        this.mOperationLevel = i4;
    }

    public MiUnifiedEncrypt(Context context, String str) {
        this.mContext = null;
        this.mAesKeyBag = null;
        this.mKeySize = 128;
        this.mPurposes = 3;
        this.mEncryptionPaddings = "NoPadding";
        this.mBlockModes = "GCM";
        this.mAlgorithm = "AES";
        this.mSecurityLevel = 1;
        this.mOperationLevel = 0;
        this.mService = null;
        this.mIsBinding = false;
        this.mIsBound = false;
        this.mBindLock = new Object();
        this.mConnection = new ServiceConnection() { // from class: com.xiaomi.security.miunifiedencrypt.MiUnifiedEncrypt.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MiUnifiedEncrypt.this.mService = IMiUnifiedEncryptService.Stub.asInterface(iBinder);
                Log.d(MiUnifiedEncrypt.TAG, "entry onServiceConnected, class:" + componentName);
                try {
                    Log.d(MiUnifiedEncrypt.TAG, "service version: " + MiUnifiedEncrypt.this.mService.getVersion());
                    Log.d(MiUnifiedEncrypt.TAG, "SDK version:1.0.6.2026.03.11");
                    if (MiUnifiedEncrypt.this.mAesKeyBag == null) {
                        AesKeyBag[] aesKeyBagArr = new AesKeyBag[1];
                        if (MiUnifiedEncrypt.this.mService == null) {
                            Log.w(MiUnifiedEncrypt.TAG, "Service disconnect. Please re-connect.");
                        }
                        int aesKeyBag = MiUnifiedEncrypt.this.mService.getAesKeyBag(MiUnifiedEncrypt.this.mAlias, aesKeyBagArr);
                        if (aesKeyBag == 0) {
                            MiUnifiedEncrypt.this.mAesKeyBag = aesKeyBagArr[0];
                            byte[] bArr = new byte[MiUnifiedEncrypt.this.mAesKeyBag.keyLength / 8];
                            System.arraycopy(MiUnifiedEncrypt.this.mAesKeyBag.key, 0, bArr, 0, MiUnifiedEncrypt.this.mAesKeyBag.keyLength / 8);
                            MiUnifiedEncrypt.this.mAppKey = bArr;
                            Log.w(MiUnifiedEncrypt.TAG, "get aesKeyBag successful");
                        } else {
                            Log.w(MiUnifiedEncrypt.TAG, "get aesKeyBag fail: " + aesKeyBag);
                        }
                    }
                    if (MiUnifiedEncrypt.this.mServiceListener != null) {
                        MiUnifiedEncrypt.this.mServiceListener.onServiceReady();
                        MiUnifiedEncrypt.this.mIsBinding = false;
                    }
                } catch (RemoteException e) {
                    Log.e(MiUnifiedEncrypt.TAG, "connect service fail. " + e);
                    if (MiUnifiedEncrypt.this.mServiceListener != null) {
                        MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                    }
                    MiUnifiedEncrypt.this.mService = null;
                    MiUnifiedEncrypt.this.mIsBinding = false;
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(MiUnifiedEncrypt.TAG, "unifiedEncrypt service disconnect.");
                MiUnifiedEncrypt.this.mIsBinding = false;
                MiUnifiedEncrypt.this.mService = null;
                if (MiUnifiedEncrypt.this.mServiceListener != null) {
                    MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                }
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                Log.e(MiUnifiedEncrypt.TAG, "unifiedEncrypt service binding died.");
                MiUnifiedEncrypt.this.mIsBinding = false;
                MiUnifiedEncrypt.this.mService = null;
                if (MiUnifiedEncrypt.this.mServiceListener != null) {
                    MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                }
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Log.e(MiUnifiedEncrypt.TAG, "unifiedEncrypt service returned null binding.");
                MiUnifiedEncrypt.this.mIsBinding = false;
                MiUnifiedEncrypt.this.mService = null;
                if (MiUnifiedEncrypt.this.mServiceListener != null) {
                    MiUnifiedEncrypt.this.mServiceListener.onServiceLost();
                }
            }
        };
        this.mContext = context;
        this.mAlias = str;
    }

    public boolean isConnected() {
        return this.mService != null;
    }

    public int registerKey() {
        try {
            if (this.mAlias == null) {
                Log.e(TAG, "alias can not be null!");
                return -3;
            }
            int i = this.mPurposes;
            if (i != 1 && i != 2 && i != 3) {
                Log.e(TAG, "Currently only supported Purpose of KeyProperties.PURPOSE_ENCRYPT and KeyProperties.PURPOSE_DECRYPT");
                return -4;
            }
            String str = this.mEncryptionPaddings;
            if (str != null && str.equals("NoPadding")) {
                String str2 = this.mBlockModes;
                if (str2 != null && str2.equals("GCM")) {
                    if (this.mSecurityLevel != 1) {
                        Log.e(TAG, "Currently only supported SecurityLevel of TEE");
                        return -9;
                    }
                    if (this.mOperationLevel != 0) {
                        Log.e(TAG, "Currently only supported OperationLevel of software");
                        return -9;
                    }
                    String str3 = this.mAlgorithm;
                    if (str3 != null && str3.equals("AES")) {
                        int i2 = this.mKeySize;
                        if (i2 != 128 && i2 != 192 && i2 != 256) {
                            Log.e(TAG, "Currently only supported AES key 128 or 192 or 256!");
                            return -8;
                        }
                        AesKeyBag aesKeyBag = new AesKeyBag();
                        aesKeyBag.alias = this.mAlias;
                        aesKeyBag.purposes = this.mPurposes;
                        aesKeyBag.paddings = this.mEncryptionPaddings;
                        aesKeyBag.blockModes = this.mBlockModes;
                        aesKeyBag.securityLevel = this.mSecurityLevel;
                        aesKeyBag.operationLevel = this.mOperationLevel;
                        aesKeyBag.keyLength = this.mKeySize;
                        IMiUnifiedEncryptService iMiUnifiedEncryptService = this.mService;
                        if (iMiUnifiedEncryptService == null) {
                            Log.e(TAG, "Service disconnect. Please re-connect.");
                            return -17;
                        }
                        int iGenAesKeyBag = iMiUnifiedEncryptService.genAesKeyBag(aesKeyBag);
                        if (iGenAesKeyBag != 0) {
                            Log.e(TAG, "genAesKeyBag fail. " + iGenAesKeyBag);
                        }
                        return iGenAesKeyBag;
                    }
                    Log.e(TAG, "Currently only supported Algorithm of AES");
                    return -6;
                }
                Log.e(TAG, "Currently only supported BlockMode ofGCM");
                return -9;
            }
            Log.e(TAG, "Currently only supported Padding of NoPadding");
            return -12;
        } catch (RemoteException e) {
            Log.e(TAG, "registerKey error:" + e);
            return -1000;
        }
    }

    public int destroyKey() {
        try {
            AesKeyBag aesKeyBag = this.mAesKeyBag;
            if (aesKeyBag != null) {
                Arrays.fill(aesKeyBag.key, (byte) 0);
                this.mAesKeyBag = null;
                Arrays.fill(this.mAppKey, (byte) 0);
                this.mAppKey = null;
            }
            IMiUnifiedEncryptService iMiUnifiedEncryptService = this.mService;
            if (iMiUnifiedEncryptService == null) {
                Log.e(TAG, "Service disconnect. Please re-connect.");
                return -17;
            }
            int iDeleteAesKeyBag = iMiUnifiedEncryptService.deleteAesKeyBag(this.mAlias);
            if (iDeleteAesKeyBag != 0) {
                Log.e(TAG, "destroyKey fail. " + iDeleteAesKeyBag);
            }
            return iDeleteAesKeyBag;
        } catch (RemoteException e) {
            Log.d(TAG, "destroyKey fail. " + e.toString());
            return -1000;
        }
    }

    public Vector<byte[]> operation(Vector<byte[]> vector, int i, int[] iArr) {
        Vector<byte[]> vector2;
        Vector<byte[]> vector3 = null;
        if (iArr == null || iArr.length <= 0) {
            Log.e(TAG, "errorCode is null or <= 0");
            return null;
        }
        iArr[0] = 0;
        try {
            if (vector == null) {
                Log.e(TAG, "messages is null");
                iArr[0] = -3;
                return null;
            }
            if (this.mAesKeyBag == null) {
                AesKeyBag[] aesKeyBagArr = new AesKeyBag[1];
                IMiUnifiedEncryptService iMiUnifiedEncryptService = this.mService;
                if (iMiUnifiedEncryptService == null) {
                    Log.e(TAG, "Service disconnect. Please re-connect.");
                    iArr[0] = -17;
                    return null;
                }
                int aesKeyBag = iMiUnifiedEncryptService.getAesKeyBag(this.mAlias, aesKeyBagArr);
                if (aesKeyBag != 0) {
                    Log.e(TAG, "get aesKeyBag fail: " + aesKeyBag);
                    iArr[0] = aesKeyBag;
                    return null;
                }
                AesKeyBag aesKeyBag2 = aesKeyBagArr[0];
                this.mAesKeyBag = aesKeyBag2;
                byte[] bArr = new byte[aesKeyBag2.keyLength / 8];
                System.arraycopy(this.mAesKeyBag.key, 0, bArr, 0, this.mAesKeyBag.keyLength / 8);
                this.mAppKey = bArr;
                if (this.mAesKeyBag == null) {
                    Log.e(TAG, "get aesKeyBag fail");
                    iArr[0] = -1000;
                    return null;
                }
            }
            Log.d(TAG, "opMode:" + i);
            Vector<byte[]> vector4 = new Vector<>();
            for (byte[] bArr2 : vector) {
                SecretKeySpec secretKeySpec = new SecretKeySpec(this.mAppKey, "AES");
                Cipher cipher = Cipher.getInstance(AESCoderForGcm.AES_GCM_ALGORITHM);
                if (i == 2) {
                    byte[] bArr3 = new byte[12];
                    System.arraycopy(bArr2, 0, bArr3, 0, 12);
                    vector2 = vector3;
                    try {
                        cipher.init(i, secretKeySpec, new GCMParameterSpec(128, bArr3));
                        vector4.add(cipher.doFinal(bArr2, 12, bArr2.length - 12));
                    } catch (Exception e) {
                        e = e;
                        Log.e(TAG, "opertion fail: " + e.toString());
                        iArr[0] = -1000;
                        return vector2;
                    }
                } else {
                    vector2 = vector3;
                    if (i != 1) {
                        Log.d(TAG, "not support opMode:" + i);
                        iArr[0] = -9;
                        return vector2;
                    }
                    cipher.init(i, secretKeySpec);
                    byte[] bArrDoFinal = cipher.doFinal(bArr2, 0, bArr2.length);
                    byte[] iv = cipher.getIV();
                    if (iv.length != 12) {
                        Log.e(TAG, "ivTmp length is " + iv.length);
                        iArr[0] = -11;
                        return vector2;
                    }
                    byte[] bArr4 = new byte[bArrDoFinal.length + iv.length];
                    System.arraycopy(iv, 0, bArr4, 0, iv.length);
                    System.arraycopy(bArrDoFinal, 0, bArr4, iv.length, bArrDoFinal.length);
                    vector4.add(bArr4);
                }
                vector3 = vector2;
            }
            return vector4;
        } catch (Exception e2) {
            e = e2;
            vector2 = null;
        }
    }

    public byte[] operation(byte[] bArr, int i, int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            Log.e(TAG, "errorCode is null or <= 0");
            return null;
        }
        iArr[0] = 0;
        try {
            if (bArr == null) {
                Log.e(TAG, "messages is null");
                iArr[0] = -3;
                return null;
            }
            if (this.mAesKeyBag == null) {
                Log.d(TAG, "get mAesKeyBag");
                AesKeyBag[] aesKeyBagArr = new AesKeyBag[1];
                IMiUnifiedEncryptService iMiUnifiedEncryptService = this.mService;
                if (iMiUnifiedEncryptService == null) {
                    Log.e(TAG, "Service disconnect. Please re-connect.");
                    iArr[0] = -17;
                    return null;
                }
                int aesKeyBag = iMiUnifiedEncryptService.getAesKeyBag(this.mAlias, aesKeyBagArr);
                if (aesKeyBag != 0) {
                    Log.e(TAG, "get aesKeyBag fail: " + aesKeyBag);
                    iArr[0] = aesKeyBag;
                    return null;
                }
                AesKeyBag aesKeyBag2 = aesKeyBagArr[0];
                this.mAesKeyBag = aesKeyBag2;
                byte[] bArr2 = new byte[aesKeyBag2.keyLength / 8];
                System.arraycopy(this.mAesKeyBag.key, 0, bArr2, 0, this.mAesKeyBag.keyLength / 8);
                this.mAppKey = bArr2;
                if (this.mAesKeyBag == null) {
                    Log.e(TAG, "get aesKeyBag fail");
                    iArr[0] = -1000;
                    return null;
                }
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.mAppKey, "AES");
            Cipher cipher = Cipher.getInstance(AESCoderForGcm.AES_GCM_ALGORITHM);
            if (i == 2) {
                cipher.init(i, secretKeySpec, new GCMParameterSpec(128, bArr, 0, 12));
                return cipher.doFinal(bArr, 12, bArr.length - 12);
            }
            if (i != 1) {
                Log.d(TAG, "not support opMode:" + i);
                iArr[0] = -9;
                return null;
            }
            cipher.init(i, secretKeySpec);
            byte[] bArrDoFinal = cipher.doFinal(bArr, 0, bArr.length);
            byte[] iv = cipher.getIV();
            if (iv.length != 12) {
                Log.e(TAG, "ivTmp length is " + iv.length);
                iArr[0] = -11;
                return null;
            }
            byte[] bArr3 = new byte[bArrDoFinal.length + iv.length];
            System.arraycopy(iv, 0, bArr3, 0, iv.length);
            System.arraycopy(bArrDoFinal, 0, bArr3, iv.length, bArrDoFinal.length);
            return bArr3;
        } catch (Exception e) {
            Log.e(TAG, "opertion fail: " + e.toString());
            iArr[0] = -1000;
            return null;
        }
    }

    public static final class Builder {
        private String mAlgorithm;
        private String mAlias;
        private String mBlockModes;
        private Context mContext;
        private String mEncryptionPaddings;
        private int mKeySize;
        private int mOperationLevel;
        private int mPurposes;
        private int mSecurityLevel;

        public Builder(Context context, String str) {
            this.mContext = context;
            this.mAlias = str;
        }

        public Builder setKeyPurposes(int i) {
            this.mPurposes = i;
            return this;
        }

        public Builder setKeyAlgorithm(String str) {
            this.mAlgorithm = str;
            return this;
        }

        public Builder setKeySize(int i) {
            this.mKeySize = i;
            return this;
        }

        public Builder setEncryptionPaddings(String str) {
            this.mEncryptionPaddings = str;
            return this;
        }

        public Builder setBlockModes(String str) {
            this.mBlockModes = str;
            return this;
        }

        public Builder setSecurityLevel(int i) {
            this.mSecurityLevel = i;
            return this;
        }

        public Builder setOperationLevel(int i) {
            this.mOperationLevel = i;
            return this;
        }

        public MiUnifiedEncrypt build() {
            Log.d(MiUnifiedEncrypt.TAG, "build MiUnifiedEncrypt");
            return new MiUnifiedEncrypt(this.mContext, this.mAlias, this.mKeySize, this.mPurposes, this.mEncryptionPaddings, this.mBlockModes, this.mAlgorithm, this.mSecurityLevel, this.mOperationLevel);
        }
    }
}
