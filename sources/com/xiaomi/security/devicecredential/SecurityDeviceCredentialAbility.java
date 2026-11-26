package com.xiaomi.security.devicecredential;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.accountsdk.account.AccountIntent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class SecurityDeviceCredentialAbility implements ServiceConnection {
    private static final Intent[] BIND_SERVICE_INTENTS;
    private static final String TAG = "SecurityDeviceCredentialAbility";
    private CountDownLatch mConnectedSignal;
    private Context mContext;
    private ISecurityDeviceCredentialManager mService;

    static {
        BIND_SERVICE_INTENTS = new Intent[]{intent, intent};
        Intent intent = new Intent("com.xiaomi.account.action.BIND_SECURITY_DEVICE_CREDENTIAL");
        intent.setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
        Intent intent2 = new Intent("com.xiaomi.finddevice.action.BIND_SECURITY_DEVICE_CREDENTIAL");
        intent2.setPackage(AccountIntent.PACKAGE_NAME_FIND_DEVICE);
    }

    public SecurityDeviceCredentialAbility(Context context) {
        this.mContext = context;
    }

    public boolean isThisDeviceSupported() throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        try {
            return getService().isThisDeviceSupported();
        } catch (RemoteException e) {
            rethrowException(e);
            throw new IllegalStateException("should never happen");
        } catch (BindServiceNotSupportException unused) {
            return SecurityDeviceCredentialManager.isThisDeviceSupported();
        }
    }

    public String getSecurityDeviceId() throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        try {
            return getService().getSecurityDeviceId();
        } catch (RemoteException e) {
            rethrowException(e);
            throw new IllegalStateException("should never happen");
        } catch (BindServiceNotSupportException unused) {
            return SecurityDeviceCredentialManager.getSecurityDeviceId();
        }
    }

    public byte[] sign(int i, byte[] bArr, boolean z) throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        try {
            return getService().sign(i, bArr, z);
        } catch (RemoteException e) {
            rethrowException(e);
            throw new IllegalStateException("should never happen");
        } catch (BindServiceNotSupportException unused) {
            return SecurityDeviceCredentialManager.sign(i, bArr, z);
        }
    }

    public byte[] signWithDeviceCredential(byte[] bArr) throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        return sign(1, bArr, false);
    }

    public byte[] signWithDeviceCredential(byte[] bArr, boolean z) throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        return sign(1, bArr, z);
    }

    public byte[] signWithFinancialCredential(byte[] bArr) throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        return sign(0, bArr, false);
    }

    public byte[] signWithFinancialCredential(byte[] bArr, boolean z) throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        return sign(0, bArr, z);
    }

    public void forceReload() throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        try {
            getService().forceReload();
        } catch (RemoteException e) {
            rethrowException(e);
        } catch (BindServiceNotSupportException unused) {
            SecurityDeviceCredentialManager.forceReload();
            return;
        }
        throw new IllegalStateException("should never happen");
    }

    private void rethrowException(RemoteException remoteException) throws SecurityDeviceCredentialManager.OperationFailedException, RemoteException {
        ErrorCodeRemoteException errorCodeRemoteException = ErrorCodeRemoteException.parse(remoteException);
        if (errorCodeRemoteException.errorCode == null) {
            throw errorCodeRemoteException.remoteException;
        }
        throw new SecurityDeviceCredentialManager.OperationFailedException(errorCodeRemoteException.errorCode.intValue());
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mService = ISecurityDeviceCredentialManager.Stub.asInterface(iBinder);
        this.mConnectedSignal.countDown();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        this.mService = null;
        release();
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        this.mService = null;
        release();
    }

    private static class BindServiceNotSupportException extends Exception {
        private BindServiceNotSupportException() {
        }
    }

    private ISecurityDeviceCredentialManager getService() throws InterruptedException, SecurityDeviceCredentialManager.OperationFailedException, BindServiceNotSupportException {
        ISecurityDeviceCredentialManager iSecurityDeviceCredentialManager = this.mService;
        if (iSecurityDeviceCredentialManager != null) {
            return iSecurityDeviceCredentialManager;
        }
        this.mConnectedSignal = new CountDownLatch(1);
        for (Intent intent : BIND_SERVICE_INTENTS) {
            if (this.mContext.bindService(intent, this, 1)) {
                if (!this.mConnectedSignal.await(10L, TimeUnit.SECONDS)) {
                    Log.i(TAG, "wait bind service timeout");
                    throw new SecurityDeviceCredentialManager.OperationFailedException(SecurityDeviceCredentialManager.OperationFailedException.ERROR_CODE_SEDC_BIND_SERVICE_NOT_SUPPORTED);
                }
                Log.i(TAG, "bind service and get");
                return this.mService;
            }
        }
        throw new BindServiceNotSupportException();
    }

    public void release() {
        try {
            this.mContext.unbindService(this);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
