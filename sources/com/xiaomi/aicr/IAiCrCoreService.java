package com.xiaomi.aicr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IAiCrCoreService extends IInterface {

    public static class Default implements IAiCrCoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.IAiCrCoreService
        public IBinder getPluginBinder(ICoreServiceCallback iCoreServiceCallback, String str, String str2, String str3, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.IAiCrCoreService
        public boolean isSupport(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.aicr.IAiCrCoreService
        public void releaseConnect(String str) throws RemoteException {
        }
    }

    IBinder getPluginBinder(ICoreServiceCallback iCoreServiceCallback, String str, String str2, String str3, Bundle bundle) throws RemoteException;

    boolean isSupport(String str) throws RemoteException;

    void releaseConnect(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAiCrCoreService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.IAiCrCoreService";
        static final int TRANSACTION_getPluginBinder = 1;
        static final int TRANSACTION_isSupport = 3;
        static final int TRANSACTION_releaseConnect = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAiCrCoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAiCrCoreService)) {
                return (IAiCrCoreService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                ICoreServiceCallback iCoreServiceCallbackAsInterface = ICoreServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                Bundle bundle = new Bundle();
                IBinder pluginBinder = getPluginBinder(iCoreServiceCallbackAsInterface, string, string2, string3, bundle);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(pluginBinder);
                parcel2.writeInt(1);
                bundle.writeToParcel(parcel2, 1);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                releaseConnect(parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i != 3) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean zIsSupport = isSupport(parcel.readString());
            parcel2.writeNoException();
            parcel2.writeInt(zIsSupport ? 1 : 0);
            return true;
        }

        private static class Proxy implements IAiCrCoreService {
            public static IAiCrCoreService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.xiaomi.aicr.IAiCrCoreService
            public IBinder getPluginBinder(ICoreServiceCallback iCoreServiceCallback, String str, String str2, String str3, Bundle bundle) throws RemoteException {
                IBinder strongBinder;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iCoreServiceCallback != null ? iCoreServiceCallback.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        strongBinder = Stub.getDefaultImpl().getPluginBinder(iCoreServiceCallback, str, str2, str3, bundle);
                    } else {
                        parcelObtain2.readException();
                        strongBinder = parcelObtain2.readStrongBinder();
                        if (parcelObtain2.readInt() != 0) {
                            bundle.readFromParcel(parcelObtain2);
                        }
                    }
                    return strongBinder;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IAiCrCoreService
            public void releaseConnect(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseConnect(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IAiCrCoreService
            public boolean isSupport(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSupport(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAiCrCoreService iAiCrCoreService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iAiCrCoreService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAiCrCoreService;
            return true;
        }

        public static IAiCrCoreService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
