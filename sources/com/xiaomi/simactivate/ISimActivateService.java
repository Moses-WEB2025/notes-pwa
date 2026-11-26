package com.xiaomi.simactivate;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface ISimActivateService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.simactivate.ISimActivateService";

    public static class Default implements ISimActivateService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.simactivate.ISimActivateService
        public Bundle invoke(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.simactivate.ISimActivateService
        public int isSupported(String str, Bundle bundle) throws RemoteException {
            return 0;
        }
    }

    Bundle invoke(String str, Bundle bundle) throws RemoteException;

    int isSupported(String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISimActivateService {
        static final int TRANSACTION_invoke = 2;
        static final int TRANSACTION_isSupported = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISimActivateService.DESCRIPTOR);
        }

        public static ISimActivateService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISimActivateService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISimActivateService)) {
                return (ISimActivateService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISimActivateService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISimActivateService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int iIsSupported = isSupported(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(iIsSupported);
            } else if (i == 2) {
                Bundle bundleInvoke = invoke(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, bundleInvoke, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISimActivateService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISimActivateService.DESCRIPTOR;
            }

            @Override // com.xiaomi.simactivate.ISimActivateService
            public int isSupported(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISimActivateService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.ISimActivateService
            public Bundle invoke(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISimActivateService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t != null) {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
