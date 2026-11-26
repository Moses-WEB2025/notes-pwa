package com.xiaomi.security.devicecredential;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface ISecurityDeviceCredentialManager extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager";

    public static class Default implements ISecurityDeviceCredentialManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
        public void forceReload() throws RemoteException {
        }

        @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
        public String getSecurityDeviceId() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
        public boolean isThisDeviceSupported() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
        public byte[] sign(int i, byte[] bArr, boolean z) throws RemoteException {
            return null;
        }
    }

    void forceReload() throws RemoteException;

    String getSecurityDeviceId() throws RemoteException;

    boolean isThisDeviceSupported() throws RemoteException;

    byte[] sign(int i, byte[] bArr, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISecurityDeviceCredentialManager {
        static final int TRANSACTION_forceReload = 4;
        static final int TRANSACTION_getSecurityDeviceId = 2;
        static final int TRANSACTION_isThisDeviceSupported = 1;
        static final int TRANSACTION_sign = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISecurityDeviceCredentialManager.DESCRIPTOR);
        }

        public static ISecurityDeviceCredentialManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISecurityDeviceCredentialManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISecurityDeviceCredentialManager)) {
                return (ISecurityDeviceCredentialManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISecurityDeviceCredentialManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISecurityDeviceCredentialManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean zIsThisDeviceSupported = isThisDeviceSupported();
                parcel2.writeNoException();
                parcel2.writeInt(zIsThisDeviceSupported ? 1 : 0);
            } else if (i == 2) {
                String securityDeviceId = getSecurityDeviceId();
                parcel2.writeNoException();
                parcel2.writeString(securityDeviceId);
            } else if (i == 3) {
                byte[] bArrSign = sign(parcel.readInt(), parcel.createByteArray(), parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrSign);
            } else if (i == 4) {
                forceReload();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISecurityDeviceCredentialManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISecurityDeviceCredentialManager.DESCRIPTOR;
            }

            @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
            public boolean isThisDeviceSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityDeviceCredentialManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
            public String getSecurityDeviceId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityDeviceCredentialManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
            public byte[] sign(int i, byte[] bArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityDeviceCredentialManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.security.devicecredential.ISecurityDeviceCredentialManager
            public void forceReload() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityDeviceCredentialManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
