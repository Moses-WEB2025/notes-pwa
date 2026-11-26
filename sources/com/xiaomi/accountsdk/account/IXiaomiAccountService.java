package com.xiaomi.accountsdk.account;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IXiaomiAccountService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.accountsdk.account.IXiaomiAccountService";

    public static class Default implements IXiaomiAccountService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getAccessToken(Account account, String str, String str2, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getAccountLoginHint() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public ParcelFileDescriptor getAvatarFd(Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public ParcelFileDescriptor getAvatarFdByFileName(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getEmail(Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getEncryptedUserId(Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getNickName(Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getPhone(Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getSnsAccessToken(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public String getUserName(Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public XiaomiAccount getXiaomiAccount(boolean z, Account account) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
        public boolean invalidateSnsAccessToken(String str, String str2) throws RemoteException {
            return false;
        }
    }

    String getAccessToken(Account account, String str, String str2, boolean z) throws RemoteException;

    String getAccountLoginHint() throws RemoteException;

    ParcelFileDescriptor getAvatarFd(Account account) throws RemoteException;

    ParcelFileDescriptor getAvatarFdByFileName(String str) throws RemoteException;

    String getEmail(Account account) throws RemoteException;

    String getEncryptedUserId(Account account) throws RemoteException;

    String getNickName(Account account) throws RemoteException;

    String getPhone(Account account) throws RemoteException;

    String getSnsAccessToken(String str) throws RemoteException;

    String getUserName(Account account) throws RemoteException;

    XiaomiAccount getXiaomiAccount(boolean z, Account account) throws RemoteException;

    boolean invalidateSnsAccessToken(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IXiaomiAccountService {
        static final int TRANSACTION_getAccessToken = 9;
        static final int TRANSACTION_getAccountLoginHint = 12;
        static final int TRANSACTION_getAvatarFd = 6;
        static final int TRANSACTION_getAvatarFdByFileName = 8;
        static final int TRANSACTION_getEmail = 3;
        static final int TRANSACTION_getEncryptedUserId = 4;
        static final int TRANSACTION_getNickName = 2;
        static final int TRANSACTION_getPhone = 5;
        static final int TRANSACTION_getSnsAccessToken = 10;
        static final int TRANSACTION_getUserName = 1;
        static final int TRANSACTION_getXiaomiAccount = 7;
        static final int TRANSACTION_invalidateSnsAccessToken = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IXiaomiAccountService.DESCRIPTOR);
        }

        public static IXiaomiAccountService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IXiaomiAccountService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IXiaomiAccountService)) {
                return (IXiaomiAccountService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IXiaomiAccountService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IXiaomiAccountService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String userName = getUserName((Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(userName);
                    return true;
                case 2:
                    String nickName = getNickName((Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(nickName);
                    return true;
                case 3:
                    String email = getEmail((Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(email);
                    return true;
                case 4:
                    String encryptedUserId = getEncryptedUserId((Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(encryptedUserId);
                    return true;
                case 5:
                    String phone = getPhone((Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(phone);
                    return true;
                case 6:
                    ParcelFileDescriptor avatarFd = getAvatarFd((Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, avatarFd, 1);
                    return true;
                case 7:
                    XiaomiAccount xiaomiAccount = getXiaomiAccount(parcel.readInt() != 0, (Account) _Parcel.readTypedObject(parcel, Account.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, xiaomiAccount, 1);
                    return true;
                case 8:
                    ParcelFileDescriptor avatarFdByFileName = getAvatarFdByFileName(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, avatarFdByFileName, 1);
                    return true;
                case 9:
                    String accessToken = getAccessToken((Account) _Parcel.readTypedObject(parcel, Account.CREATOR), parcel.readString(), parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeString(accessToken);
                    return true;
                case 10:
                    String snsAccessToken = getSnsAccessToken(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(snsAccessToken);
                    return true;
                case 11:
                    boolean zInvalidateSnsAccessToken = invalidateSnsAccessToken(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zInvalidateSnsAccessToken ? 1 : 0);
                    return true;
                case 12:
                    String accountLoginHint = getAccountLoginHint();
                    parcel2.writeNoException();
                    parcel2.writeString(accountLoginHint);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IXiaomiAccountService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IXiaomiAccountService.DESCRIPTOR;
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getUserName(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getNickName(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getEmail(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getEncryptedUserId(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getPhone(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public ParcelFileDescriptor getAvatarFd(Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) _Parcel.readTypedObject(parcelObtain2, ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public XiaomiAccount getXiaomiAccount(boolean z, Account account) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (XiaomiAccount) _Parcel.readTypedObject(parcelObtain2, XiaomiAccount.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public ParcelFileDescriptor getAvatarFdByFileName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) _Parcel.readTypedObject(parcelObtain2, ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getAccessToken(Account account, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getSnsAccessToken(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public boolean invalidateSnsAccessToken(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accountsdk.account.IXiaomiAccountService
            public String getAccountLoginHint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IXiaomiAccountService.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
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
