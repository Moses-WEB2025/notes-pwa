package com.xiaomi.passport;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;

/* JADX INFO: loaded from: classes4.dex */
public interface IPassportServiceTokenService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.passport.IPassportServiceTokenService";

    public static class Default implements IPassportServiceTokenService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public boolean fastCheckSlhPhCompatibility() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public XmAccountVisibility getAccountVisible(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public ServiceTokenResult getServiceToken(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public ServiceTokenResult invalidateServiceToken(ServiceTokenResult serviceTokenResult) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public Bundle requestSecondaryDeviceLoginData(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public XmAccountVisibility setAccountVisible(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public boolean supportAccessAccount() throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.passport.IPassportServiceTokenService
        public boolean supportServiceTokenUIResponse() throws RemoteException {
            return false;
        }
    }

    boolean fastCheckSlhPhCompatibility() throws RemoteException;

    XmAccountVisibility getAccountVisible(String str) throws RemoteException;

    ServiceTokenResult getServiceToken(String str) throws RemoteException;

    ServiceTokenResult invalidateServiceToken(ServiceTokenResult serviceTokenResult) throws RemoteException;

    Bundle requestSecondaryDeviceLoginData(Bundle bundle) throws RemoteException;

    XmAccountVisibility setAccountVisible(String str) throws RemoteException;

    boolean supportAccessAccount() throws RemoteException;

    boolean supportServiceTokenUIResponse() throws RemoteException;

    public static abstract class Stub extends Binder implements IPassportServiceTokenService {
        static final int TRANSACTION_fastCheckSlhPhCompatibility = 4;
        static final int TRANSACTION_getAccountVisible = 7;
        static final int TRANSACTION_getServiceToken = 1;
        static final int TRANSACTION_invalidateServiceToken = 2;
        static final int TRANSACTION_requestSecondaryDeviceLoginData = 8;
        static final int TRANSACTION_setAccountVisible = 6;
        static final int TRANSACTION_supportAccessAccount = 5;
        static final int TRANSACTION_supportServiceTokenUIResponse = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IPassportServiceTokenService.DESCRIPTOR);
        }

        public static IPassportServiceTokenService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPassportServiceTokenService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPassportServiceTokenService)) {
                return (IPassportServiceTokenService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPassportServiceTokenService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPassportServiceTokenService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ServiceTokenResult serviceToken = getServiceToken(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, serviceToken, 1);
                    return true;
                case 2:
                    ServiceTokenResult serviceTokenResultInvalidateServiceToken = invalidateServiceToken((ServiceTokenResult) _Parcel.readTypedObject(parcel, ServiceTokenResult.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, serviceTokenResultInvalidateServiceToken, 1);
                    return true;
                case 3:
                    boolean zSupportServiceTokenUIResponse = supportServiceTokenUIResponse();
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportServiceTokenUIResponse ? 1 : 0);
                    return true;
                case 4:
                    boolean zFastCheckSlhPhCompatibility = fastCheckSlhPhCompatibility();
                    parcel2.writeNoException();
                    parcel2.writeInt(zFastCheckSlhPhCompatibility ? 1 : 0);
                    return true;
                case 5:
                    boolean zSupportAccessAccount = supportAccessAccount();
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportAccessAccount ? 1 : 0);
                    return true;
                case 6:
                    XmAccountVisibility accountVisible = setAccountVisible(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, accountVisible, 1);
                    return true;
                case 7:
                    XmAccountVisibility accountVisible2 = getAccountVisible(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, accountVisible2, 1);
                    return true;
                case 8:
                    Bundle bundleRequestSecondaryDeviceLoginData = requestSecondaryDeviceLoginData((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, bundleRequestSecondaryDeviceLoginData, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPassportServiceTokenService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPassportServiceTokenService.DESCRIPTOR;
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public ServiceTokenResult getServiceToken(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ServiceTokenResult) _Parcel.readTypedObject(parcelObtain2, ServiceTokenResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public ServiceTokenResult invalidateServiceToken(ServiceTokenResult serviceTokenResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, serviceTokenResult, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ServiceTokenResult) _Parcel.readTypedObject(parcelObtain2, ServiceTokenResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public boolean supportServiceTokenUIResponse() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public boolean fastCheckSlhPhCompatibility() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public boolean supportAccessAccount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public XmAccountVisibility setAccountVisible(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (XmAccountVisibility) _Parcel.readTypedObject(parcelObtain2, XmAccountVisibility.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public XmAccountVisibility getAccountVisible(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (XmAccountVisibility) _Parcel.readTypedObject(parcelObtain2, XmAccountVisibility.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportServiceTokenService
            public Bundle requestSecondaryDeviceLoginData(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportServiceTokenService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
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
