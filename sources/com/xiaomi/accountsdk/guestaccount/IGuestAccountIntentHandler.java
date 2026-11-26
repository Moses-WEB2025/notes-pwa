package com.xiaomi.accountsdk.guestaccount;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IGuestAccountIntentHandler extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.accountsdk.guestaccount.IGuestAccountIntentHandler";

    public static class Default implements IGuestAccountIntentHandler {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountIntentHandler
        public boolean handleIntent(Intent intent) throws RemoteException {
            return false;
        }
    }

    boolean handleIntent(Intent intent) throws RemoteException;

    public static abstract class Stub extends Binder implements IGuestAccountIntentHandler {
        static final int TRANSACTION_handleIntent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IGuestAccountIntentHandler.DESCRIPTOR);
        }

        public static IGuestAccountIntentHandler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGuestAccountIntentHandler.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGuestAccountIntentHandler)) {
                return (IGuestAccountIntentHandler) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGuestAccountIntentHandler.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGuestAccountIntentHandler.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean zHandleIntent = handleIntent((Intent) _Parcel.readTypedObject(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zHandleIntent ? 1 : 0);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGuestAccountIntentHandler {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGuestAccountIntentHandler.DESCRIPTOR;
            }

            @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountIntentHandler
            public boolean handleIntent(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGuestAccountIntentHandler.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, intent, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
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
