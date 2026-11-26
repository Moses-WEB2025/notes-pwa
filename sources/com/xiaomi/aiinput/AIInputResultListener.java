package com.xiaomi.aiinput;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface AIInputResultListener extends IInterface {
    void onGetFocusedAITextInfo(AiTextInfo aiTextInfo) throws RemoteException;

    void onHandleTextView(int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements AIInputResultListener {
        private static final String DESCRIPTOR = "com.xiaomi.aiinput.AIInputResultListener";
        static final int TRANSACTION_onGetFocusedAITextInfo = 1;
        static final int TRANSACTION_onHandleTextView = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static AIInputResultListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof AIInputResultListener)) {
                return (AIInputResultListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i != 2) {
                    if (i == 1598968902) {
                        parcel2.writeString(DESCRIPTOR);
                        return true;
                    }
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel.enforceInterface(DESCRIPTOR);
                onHandleTextView(parcel.readInt(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            AiTextInfo aiTextInfoCreateFromParcel = parcel.readInt() != 0 ? AiTextInfo.CREATOR.createFromParcel(parcel) : null;
            onGetFocusedAITextInfo(aiTextInfoCreateFromParcel);
            parcel2.writeNoException();
            if (aiTextInfoCreateFromParcel != null) {
                parcel2.writeInt(1);
                aiTextInfoCreateFromParcel.writeToParcel(parcel2, 1);
            } else {
                parcel2.writeInt(0);
            }
            return true;
        }

        private static class Proxy implements AIInputResultListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.xiaomi.aiinput.AIInputResultListener
            public void onGetFocusedAITextInfo(AiTextInfo aiTextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (aiTextInfo != null) {
                        parcelObtain.writeInt(1);
                        aiTextInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        aiTextInfo.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aiinput.AIInputResultListener
            public void onHandleTextView(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
