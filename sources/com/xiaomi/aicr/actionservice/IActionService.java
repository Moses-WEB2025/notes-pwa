package com.xiaomi.aicr.actionservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IActionService extends IInterface {

    public static class Default implements IActionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.actionservice.IActionService
        public int startActionActivity(String str, String str2, Bundle bundle) throws RemoteException {
            return 0;
        }
    }

    int startActionActivity(String str, String str2, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IActionService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.actionservice.IActionService";
        static final int TRANSACTION_startActionActivity = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IActionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActionService)) {
                return (IActionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            int iStartActionActivity = startActionActivity(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            parcel2.writeInt(iStartActionActivity);
            return true;
        }

        private static class Proxy implements IActionService {
            public static IActionService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.actionservice.IActionService
            public int startActionActivity(String str, String str2, Bundle bundle) throws RemoteException {
                int iStartActionActivity;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iStartActionActivity = Stub.getDefaultImpl().startActionActivity(str, str2, bundle);
                    } else {
                        parcelObtain2.readException();
                        iStartActionActivity = parcelObtain2.readInt();
                    }
                    return iStartActionActivity;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IActionService iActionService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iActionService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iActionService;
            return true;
        }

        public static IActionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
