package com.xiaomi.passport;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface ISecurityDeviceSignService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.passport.ISecurityDeviceSignService";

    public static class Default implements ISecurityDeviceSignService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.passport.ISecurityDeviceSignService
        public void sign(ISecurityDeviceSignResponse iSecurityDeviceSignResponse, String str, String str2, Bundle bundle) throws RemoteException {
        }
    }

    void sign(ISecurityDeviceSignResponse iSecurityDeviceSignResponse, String str, String str2, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISecurityDeviceSignService {
        static final int TRANSACTION_sign = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISecurityDeviceSignService.DESCRIPTOR);
        }

        public static ISecurityDeviceSignService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISecurityDeviceSignService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISecurityDeviceSignService)) {
                return (ISecurityDeviceSignService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISecurityDeviceSignService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISecurityDeviceSignService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                sign(ISecurityDeviceSignResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISecurityDeviceSignService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISecurityDeviceSignService.DESCRIPTOR;
            }

            @Override // com.xiaomi.passport.ISecurityDeviceSignService
            public void sign(ISecurityDeviceSignResponse iSecurityDeviceSignResponse, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISecurityDeviceSignService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSecurityDeviceSignResponse);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
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
