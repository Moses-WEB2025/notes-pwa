package com.aios.apptoolsdk.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IOSbotMessenger extends IInterface {
    public static final String DESCRIPTOR = "com.aios.apptoolsdk.aidl.IOSbotMessenger";

    public static class Default implements IOSbotMessenger {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.aios.apptoolsdk.aidl.IOSbotMessenger
        public void sendNotification(String str) throws RemoteException {
        }
    }

    void sendNotification(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IOSbotMessenger {
        static final int TRANSACTION_sendNotification = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IOSbotMessenger.DESCRIPTOR);
        }

        public static IOSbotMessenger asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOSbotMessenger.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOSbotMessenger)) {
                return (IOSbotMessenger) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOSbotMessenger.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOSbotMessenger.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                sendNotification(parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOSbotMessenger {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOSbotMessenger.DESCRIPTOR;
            }

            @Override // com.aios.apptoolsdk.aidl.IOSbotMessenger
            public void sendNotification(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IOSbotMessenger.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
