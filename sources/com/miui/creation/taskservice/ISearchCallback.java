package com.miui.creation.taskservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes2.dex */
public interface ISearchCallback extends IInterface {
    public static final String DESCRIPTOR = "com.miui.creation.taskservice.ISearchCallback";

    public static class Default implements ISearchCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.creation.taskservice.ISearchCallback
        public void doSearch() throws RemoteException {
        }
    }

    void doSearch() throws RemoteException;

    public static abstract class Stub extends Binder implements ISearchCallback {
        static final int TRANSACTION_doSearch = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISearchCallback.DESCRIPTOR);
        }

        public static ISearchCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISearchCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISearchCallback)) {
                return (ISearchCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISearchCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISearchCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                doSearch();
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISearchCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISearchCallback.DESCRIPTOR;
            }

            @Override // com.miui.creation.taskservice.ISearchCallback
            public void doSearch() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISearchCallback.DESCRIPTOR);
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
