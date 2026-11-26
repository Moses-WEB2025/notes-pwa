package com.xiaomi.security.devicecredential;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: OnRemoteCallFinishedListener.java */
/* JADX INFO: loaded from: classes4.dex */
class OnRemoteCallFinishedListenerProxy implements IOnRemoteCallFinishedListener {
    private final IBinder mRemote;

    public OnRemoteCallFinishedListenerProxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // com.xiaomi.security.devicecredential.IOnRemoteCallFinishedListener
    public void onGetSecurityDeviceIdFinished(int i, String str) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IOnRemoteCallFinishedListener.DESCRIPTOR);
            parcelObtain.writeInt(i);
            parcelObtain.writeString(str);
            this.mRemote.transact(1, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // com.xiaomi.security.devicecredential.IOnRemoteCallFinishedListener
    public void onSignFinished(int i, byte[] bArr) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IOnRemoteCallFinishedListener.DESCRIPTOR);
            parcelObtain.writeInt(i);
            parcelObtain.writeByteArray(bArr);
            this.mRemote.transact(2, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // com.xiaomi.security.devicecredential.IOnRemoteCallFinishedListener
    public void onForceReloadFinished(int i) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IOnRemoteCallFinishedListener.DESCRIPTOR);
            parcelObtain.writeInt(i);
            this.mRemote.transact(3, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }
}
