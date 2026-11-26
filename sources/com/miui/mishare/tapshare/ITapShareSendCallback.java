package com.miui.mishare.tapshare;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ITapShareSendCallback extends IInterface {
    public static final String DESCRIPTOR = "com.miui.mishare.tapshare.ITapShareSendCallback";

    public static class Default implements ITapShareSendCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.mishare.tapshare.ITapShareSendCallback
        public int getSdkVersion() throws RemoteException {
            return 0;
        }

        @Override // com.miui.mishare.tapshare.ITapShareSendCallback
        public void onSendResult(int i) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareSendCallback
        public void onTapShareCancel() throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareSendCallback
        public void onTapShareSend() throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareSendCallback
        public void onTapShareSendV2(Bundle bundle) throws RemoteException {
        }
    }

    int getSdkVersion() throws RemoteException;

    void onSendResult(int i) throws RemoteException;

    void onTapShareCancel() throws RemoteException;

    void onTapShareSend() throws RemoteException;

    void onTapShareSendV2(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ITapShareSendCallback {
        static final int TRANSACTION_getSdkVersion = 4;
        static final int TRANSACTION_onSendResult = 5;
        static final int TRANSACTION_onTapShareCancel = 2;
        static final int TRANSACTION_onTapShareSend = 1;
        static final int TRANSACTION_onTapShareSendV2 = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ITapShareSendCallback.DESCRIPTOR);
        }

        public static ITapShareSendCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITapShareSendCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITapShareSendCallback)) {
                return (ITapShareSendCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITapShareSendCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITapShareSendCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onTapShareSend();
            } else if (i == 2) {
                onTapShareCancel();
            } else if (i == 3) {
                onTapShareSendV2((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            } else if (i == 4) {
                int sdkVersion = getSdkVersion();
                parcel2.writeNoException();
                parcel2.writeInt(sdkVersion);
            } else if (i == 5) {
                onSendResult(parcel.readInt());
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITapShareSendCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITapShareSendCallback.DESCRIPTOR;
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onTapShareSend() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareSendCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onTapShareCancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareSendCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onTapShareSendV2(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareSendCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public int getSdkVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareSendCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onSendResult(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareSendCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
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
