package com.xiaomi.accountsdk.guestaccount;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IGuestAccountService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.accountsdk.guestaccount.IGuestAccountService";

    public static class Default implements IGuestAccountService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
        public Bundle getGuestAccount(Bundle bundle, IGuestAccountIntentHandler iGuestAccountIntentHandler) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
        public Bundle getStoredUserId(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
        public void onXiaomiAccountCTAAllowed() throws RemoteException {
        }

        @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
        public Bundle renewServiceToken(Bundle bundle, IGuestAccountIntentHandler iGuestAccountIntentHandler) throws RemoteException {
            return null;
        }
    }

    Bundle getGuestAccount(Bundle bundle, IGuestAccountIntentHandler iGuestAccountIntentHandler) throws RemoteException;

    Bundle getStoredUserId(Bundle bundle) throws RemoteException;

    void onXiaomiAccountCTAAllowed() throws RemoteException;

    Bundle renewServiceToken(Bundle bundle, IGuestAccountIntentHandler iGuestAccountIntentHandler) throws RemoteException;

    public static abstract class Stub extends Binder implements IGuestAccountService {
        static final int TRANSACTION_getGuestAccount = 1;
        static final int TRANSACTION_getStoredUserId = 4;
        static final int TRANSACTION_onXiaomiAccountCTAAllowed = 3;
        static final int TRANSACTION_renewServiceToken = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IGuestAccountService.DESCRIPTOR);
        }

        public static IGuestAccountService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGuestAccountService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGuestAccountService)) {
                return (IGuestAccountService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGuestAccountService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGuestAccountService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Bundle guestAccount = getGuestAccount((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), IGuestAccountIntentHandler.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, guestAccount, 1);
            } else if (i == 2) {
                Bundle bundleRenewServiceToken = renewServiceToken((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), IGuestAccountIntentHandler.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, bundleRenewServiceToken, 1);
            } else if (i == 3) {
                onXiaomiAccountCTAAllowed();
                parcel2.writeNoException();
            } else if (i == 4) {
                Bundle storedUserId = getStoredUserId((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, storedUserId, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGuestAccountService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGuestAccountService.DESCRIPTOR;
            }

            @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
            public Bundle getGuestAccount(Bundle bundle, IGuestAccountIntentHandler iGuestAccountIntentHandler) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGuestAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iGuestAccountIntentHandler);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
            public Bundle renewServiceToken(Bundle bundle, IGuestAccountIntentHandler iGuestAccountIntentHandler) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGuestAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iGuestAccountIntentHandler);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
            public void onXiaomiAccountCTAAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGuestAccountService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountService
            public Bundle getStoredUserId(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGuestAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
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
