package com.xiaomi.security.devicecredential;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public abstract class OnRemoteCallFinishedListener extends Binder implements IOnRemoteCallFinishedListener {
    private static final long REMOTE_CALL_TIMEOUT_DELAY = 300000;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private int mResultCode;

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    protected abstract void onForceReloadFinished();

    protected abstract void onGetSecurityDeviceIdFinished(String str);

    protected abstract void onSignFinished(byte[] bArr);

    protected void waitForResult() throws InterruptedException, RemoteException {
        if (!this.mCountDownLatch.await(300000L, TimeUnit.MILLISECONDS)) {
            throw new RemoteException("remotecall timeout.");
        }
    }

    protected void checkResultCode() throws SecurityDeviceCredentialManager.OperationFailedException {
        if (this.mResultCode != 0) {
            throw new SecurityDeviceCredentialManager.OperationFailedException(this.mResultCode);
        }
    }

    public static IOnRemoteCallFinishedListener asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        return new OnRemoteCallFinishedListenerProxy(iBinder);
    }

    @Override // android.os.Binder
    protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            parcel.enforceInterface(IOnRemoteCallFinishedListener.DESCRIPTOR);
            onGetSecurityDeviceIdFinished(parcel.readInt(), parcel.readString());
            return true;
        }
        if (i == 2) {
            parcel.enforceInterface(IOnRemoteCallFinishedListener.DESCRIPTOR);
            onSignFinished(parcel.readInt(), parcel.createByteArray());
            return true;
        }
        if (i == 3) {
            parcel.enforceInterface(IOnRemoteCallFinishedListener.DESCRIPTOR);
            onForceReloadFinished(parcel.readInt());
            return true;
        }
        if (i == 1598968902) {
            parcel2.writeString(IOnRemoteCallFinishedListener.DESCRIPTOR);
            return true;
        }
        return super.onTransact(i, parcel, parcel2, i2);
    }

    @Override // com.xiaomi.security.devicecredential.IOnRemoteCallFinishedListener
    public final void onGetSecurityDeviceIdFinished(int i, String str) throws RemoteException {
        this.mResultCode = i;
        onGetSecurityDeviceIdFinished(str);
        notifyResultArrive();
    }

    @Override // com.xiaomi.security.devicecredential.IOnRemoteCallFinishedListener
    public final void onSignFinished(int i, byte[] bArr) throws RemoteException {
        this.mResultCode = i;
        onSignFinished(bArr);
        notifyResultArrive();
    }

    @Override // com.xiaomi.security.devicecredential.IOnRemoteCallFinishedListener
    public final void onForceReloadFinished(int i) throws RemoteException {
        this.mResultCode = i;
        onForceReloadFinished();
        notifyResultArrive();
    }

    private void notifyResultArrive() {
        this.mCountDownLatch.countDown();
    }
}
