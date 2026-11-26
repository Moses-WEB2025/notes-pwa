package com.xiaomi.security.devicecredential;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public class SecurityDeviceCredentialManager {
    public static final String DESCRIPTOR = "com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager.v1";
    public static final int KEY_TYPE_FINANCIAL = 0;
    public static final int KEY_TYPE_ORDINARY = 1;
    private static final long RETRY_GET_SERVICE_INTERVAL_MILLISEC = 500;
    private static final long RETRY_ON_HARDWARE_SERVICE_NOT_AVAILABLE_INTERVAL_MILLISEC = 500;
    public static final String SERVICE_NAME = "miui.sedc";
    private static final String TAG = "SecurityDeviceCredentialManager";
    public static final int TRANSACTION_forceReload = 4;
    public static final int TRANSACTION_getSecurityDeviceId = 2;
    public static final int TRANSACTION_isThisDeviceSupported = 1;
    public static final int TRANSACTION_sign = 3;
    private static IBinder sService;

    public static class OperationFailedException extends Exception {
        public static final int ERROR_CODE_EMPTY_DATA = -103;
        public static final int ERROR_CODE_FORCE_RELOAD_REFUSED = -108;
        public static final int ERROR_CODE_KEY_TYPE_NOT_SUPPORTED = -102;
        public static final int ERROR_CODE_NOT_SUPPORTED = -100;
        public static final int ERROR_CODE_RELOAD_FAILURE_INTERNAL = -107;
        public static final int ERROR_CODE_RELOAD_FAILURE_NETWORK = -105;
        public static final int ERROR_CODE_RELOAD_FAILURE_NO_AVAILABLE_KEY_ON_SERVER = -106;
        public static final int ERROR_CODE_SEDC_BIND_SERVICE_NOT_SUPPORTED = -110;
        public static final int ERROR_CODE_SIGN_FAIL = -104;
        public static final int ERROR_CODE_TRUST_ZONE_SERVICE_NOT_AVALIABLE = -101;
        public static final int ERROR_CODE_UNKNOWN = -1;
        public final int errorCode;

        public OperationFailedException(int i) {
            this.errorCode = i;
        }

        @Override // java.lang.Throwable
        public String toString() {
            return "OperationFailedException{errorCode=" + this.errorCode + StringSubstitutor.DEFAULT_VAR_END;
        }
    }

    public static boolean isThisDeviceSupported() throws InterruptedException, OperationFailedException, RemoteException {
        IBinder service = getService();
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(DESCRIPTOR);
            service.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0;
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    public static String getSecurityDeviceId() throws InterruptedException, OperationFailedException, RemoteException {
        IBinder service = getService();
        OnGetSecurityDeviceIdFinishListener onGetSecurityDeviceIdFinishListener = new OnGetSecurityDeviceIdFinishListener();
        while (true) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(DESCRIPTOR);
                parcelObtain.writeStrongBinder(onGetSecurityDeviceIdFinishListener);
                service.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain.recycle();
                parcelObtain2.recycle();
                try {
                    return onGetSecurityDeviceIdFinishListener.getSecurityDeviceId();
                } catch (OperationFailedException e) {
                    if (e.errorCode != -101) {
                        throw e;
                    }
                    Log.e(TAG, "getSecurityDeviceId: Hardware service not ready, retry...");
                    Thread.sleep(500L);
                }
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                throw th;
            }
        }
    }

    public static byte[] sign(int i, byte[] bArr, boolean z) throws InterruptedException, OperationFailedException, RemoteException {
        IBinder service = getService();
        OnSignFinishedListener onSignFinishedListener = new OnSignFinishedListener();
        while (true) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(DESCRIPTOR);
                parcelObtain.writeStrongBinder(onSignFinishedListener);
                parcelObtain.writeInt(i);
                parcelObtain.writeByteArray(bArr);
                parcelObtain.writeInt(z ? 1 : 0);
                service.transact(3, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain.recycle();
                parcelObtain2.recycle();
                try {
                    return onSignFinishedListener.getSignResult();
                } catch (OperationFailedException e) {
                    if (e.errorCode != -101) {
                        throw e;
                    }
                    Log.e(TAG, "sign: Hardware service not ready, retry...");
                    Thread.sleep(500L);
                }
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                throw th;
            }
        }
    }

    public static byte[] signWithDeviceCredential(byte[] bArr) throws InterruptedException, OperationFailedException, RemoteException {
        return sign(1, bArr, false);
    }

    public static byte[] signWithDeviceCredential(byte[] bArr, boolean z) throws InterruptedException, OperationFailedException, RemoteException {
        return sign(1, bArr, z);
    }

    public static byte[] signWithFinancialCredential(byte[] bArr) throws InterruptedException, OperationFailedException, RemoteException {
        return sign(0, bArr, false);
    }

    public static byte[] signWithFinancialCredential(byte[] bArr, boolean z) throws InterruptedException, OperationFailedException, RemoteException {
        return sign(0, bArr, z);
    }

    public static void forceReload() throws InterruptedException, OperationFailedException, RemoteException {
        IBinder service = getService();
        OnForceReloadFinishedListener onForceReloadFinishedListener = new OnForceReloadFinishedListener();
        while (true) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(DESCRIPTOR);
                parcelObtain.writeStrongBinder(onForceReloadFinishedListener);
                service.transact(4, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain.recycle();
                parcelObtain2.recycle();
                try {
                    onForceReloadFinishedListener.checkReloadResult();
                    return;
                } catch (OperationFailedException e) {
                    if (e.errorCode != -101) {
                        throw e;
                    }
                    Log.e(TAG, "forceReload: Hardware service not ready, retry...");
                    Thread.sleep(500L);
                }
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                throw th;
            }
        }
    }

    private static synchronized IBinder getService() throws InterruptedException, OperationFailedException {
        boolean zPingBinder;
        if (sService != null) {
            Log.i(TAG, "getService: sService != null. ");
            zPingBinder = sService.pingBinder();
        } else {
            Log.i(TAG, "getService: sService == null. ");
            zPingBinder = false;
        }
        if (!zPingBinder) {
            Log.w(TAG, "getService: binder not alive. ");
            while (true) {
                IBinder service = ServiceManager.getService(SERVICE_NAME);
                sService = service;
                if (service != null) {
                    break;
                }
                Log.e(TAG, "getService: NULL binder, retry...");
                Thread.sleep(500L);
            }
        } else {
            Log.i(TAG, "getService: binder alive. ");
        }
        return sService;
    }

    private static class OnGetSecurityDeviceIdFinishListener extends OnRemoteCallFinishedListener {
        private String mSecurityDeviceId;

        private OnGetSecurityDeviceIdFinishListener() {
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onGetSecurityDeviceIdFinished(String str) {
            this.mSecurityDeviceId = str;
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onSignFinished(byte[] bArr) {
            throw new IllegalStateException("wrong callback!");
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onForceReloadFinished() {
            throw new IllegalStateException("wrong callback!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getSecurityDeviceId() throws InterruptedException, OperationFailedException, RemoteException {
            waitForResult();
            checkResultCode();
            return this.mSecurityDeviceId;
        }
    }

    private static class OnSignFinishedListener extends OnRemoteCallFinishedListener {
        private byte[] mSignResult;

        private OnSignFinishedListener() {
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onGetSecurityDeviceIdFinished(String str) {
            throw new IllegalStateException("wrong callback!");
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onSignFinished(byte[] bArr) {
            this.mSignResult = bArr;
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onForceReloadFinished() {
            throw new IllegalStateException("wrong callback!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte[] getSignResult() throws InterruptedException, OperationFailedException, RemoteException {
            waitForResult();
            checkResultCode();
            return this.mSignResult;
        }
    }

    private static class OnForceReloadFinishedListener extends OnRemoteCallFinishedListener {
        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        protected void onForceReloadFinished() {
        }

        private OnForceReloadFinishedListener() {
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onGetSecurityDeviceIdFinished(String str) {
            throw new IllegalStateException("wrong callback!");
        }

        @Override // com.xiaomi.security.devicecredential.OnRemoteCallFinishedListener
        public void onSignFinished(byte[] bArr) {
            throw new IllegalStateException("wrong callback!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkReloadResult() throws InterruptedException, OperationFailedException, RemoteException {
            waitForResult();
            checkResultCode();
        }
    }
}
