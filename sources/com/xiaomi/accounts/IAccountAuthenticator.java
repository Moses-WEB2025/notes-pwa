package com.xiaomi.accounts;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountAuthenticator extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.accounts.IAccountAuthenticator";

    public static class Default implements IAccountAuthenticator {
        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
        }
    }

    void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException;

    void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException;

    void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException;

    void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException;

    void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccountAuthenticator {
        static final int TRANSACTION_addAccount = 1;
        static final int TRANSACTION_confirmCredentials = 2;
        static final int TRANSACTION_editProperties = 6;
        static final int TRANSACTION_getAccountRemovalAllowed = 8;
        static final int TRANSACTION_getAuthToken = 3;
        static final int TRANSACTION_getAuthTokenLabel = 4;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_updateCredentials = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAccountAuthenticator.DESCRIPTOR);
        }

        public static IAccountAuthenticator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAccountAuthenticator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccountAuthenticator)) {
                return (IAccountAuthenticator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAccountAuthenticator.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAccountAuthenticator.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    addAccount(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.createStringArray(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 2:
                    confirmCredentials(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), (Account) _Parcel.readTypedObject(parcel, Account.CREATOR), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 3:
                    getAuthToken(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), (Account) _Parcel.readTypedObject(parcel, Account.CREATOR), parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 4:
                    getAuthTokenLabel(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                    return true;
                case 5:
                    updateCredentials(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), (Account) _Parcel.readTypedObject(parcel, Account.CREATOR), parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 6:
                    editProperties(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                    return true;
                case 7:
                    hasFeatures(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), (Account) _Parcel.readTypedObject(parcel, Account.CREATOR), parcel.createStringArray());
                    return true;
                case 8:
                    getAccountRemovalAllowed(IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder()), (Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccountAuthenticator {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccountAuthenticator.DESCRIPTOR;
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountAuthenticator
            public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccountAuthenticatorResponse);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
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
