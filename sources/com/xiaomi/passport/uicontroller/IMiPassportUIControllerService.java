package com.xiaomi.passport.uicontroller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.MiLoginResult;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.account.data.NotificationLoginEndParams;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;

/* JADX INFO: loaded from: classes4.dex */
public interface IMiPassportUIControllerService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.passport.uicontroller.IMiPassportUIControllerService";

    public static class Default implements IMiPassportUIControllerService {
        @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
        public void addOrUpdateAccountManager(AccountInfo accountInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
        public MiLoginResult loginByPassword(PasswordLoginParams passwordLoginParams) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
        public MiLoginResult loginByStep2(Step2LoginParams step2LoginParams) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
        public NotificationAuthResult onNotificationAuthEnd(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
        public MiLoginResult onNotificationLoginEnd(NotificationLoginEndParams notificationLoginEndParams) throws RemoteException {
            return null;
        }
    }

    void addOrUpdateAccountManager(AccountInfo accountInfo) throws RemoteException;

    MiLoginResult loginByPassword(PasswordLoginParams passwordLoginParams) throws RemoteException;

    MiLoginResult loginByStep2(Step2LoginParams step2LoginParams) throws RemoteException;

    NotificationAuthResult onNotificationAuthEnd(String str) throws RemoteException;

    MiLoginResult onNotificationLoginEnd(NotificationLoginEndParams notificationLoginEndParams) throws RemoteException;

    public static abstract class Stub extends Binder implements IMiPassportUIControllerService {
        static final int TRANSACTION_addOrUpdateAccountManager = 4;
        static final int TRANSACTION_loginByPassword = 1;
        static final int TRANSACTION_loginByStep2 = 3;
        static final int TRANSACTION_onNotificationAuthEnd = 5;
        static final int TRANSACTION_onNotificationLoginEnd = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMiPassportUIControllerService.DESCRIPTOR);
        }

        public static IMiPassportUIControllerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMiPassportUIControllerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMiPassportUIControllerService)) {
                return (IMiPassportUIControllerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMiPassportUIControllerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMiPassportUIControllerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MiLoginResult miLoginResultLoginByPassword = loginByPassword((PasswordLoginParams) _Parcel.readTypedObject(parcel, PasswordLoginParams.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, miLoginResultLoginByPassword, 1);
            } else if (i == 2) {
                MiLoginResult miLoginResultOnNotificationLoginEnd = onNotificationLoginEnd((NotificationLoginEndParams) _Parcel.readTypedObject(parcel, NotificationLoginEndParams.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, miLoginResultOnNotificationLoginEnd, 1);
            } else if (i == 3) {
                MiLoginResult miLoginResultLoginByStep2 = loginByStep2((Step2LoginParams) _Parcel.readTypedObject(parcel, Step2LoginParams.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, miLoginResultLoginByStep2, 1);
            } else if (i == 4) {
                addOrUpdateAccountManager((AccountInfo) _Parcel.readTypedObject(parcel, AccountInfo.CREATOR));
                parcel2.writeNoException();
            } else if (i == 5) {
                NotificationAuthResult notificationAuthResultOnNotificationAuthEnd = onNotificationAuthEnd(parcel.readString());
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, notificationAuthResultOnNotificationAuthEnd, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMiPassportUIControllerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMiPassportUIControllerService.DESCRIPTOR;
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public MiLoginResult loginByPassword(PasswordLoginParams passwordLoginParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiPassportUIControllerService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, passwordLoginParams, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MiLoginResult) _Parcel.readTypedObject(parcelObtain2, MiLoginResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public MiLoginResult onNotificationLoginEnd(NotificationLoginEndParams notificationLoginEndParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiPassportUIControllerService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, notificationLoginEndParams, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MiLoginResult) _Parcel.readTypedObject(parcelObtain2, MiLoginResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public MiLoginResult loginByStep2(Step2LoginParams step2LoginParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiPassportUIControllerService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, step2LoginParams, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MiLoginResult) _Parcel.readTypedObject(parcelObtain2, MiLoginResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public void addOrUpdateAccountManager(AccountInfo accountInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiPassportUIControllerService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, accountInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.uicontroller.IMiPassportUIControllerService
            public NotificationAuthResult onNotificationAuthEnd(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiPassportUIControllerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationAuthResult) _Parcel.readTypedObject(parcelObtain2, NotificationAuthResult.CREATOR);
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
