package com.xiaomi.aiinput;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IAIInputTextManagerClient extends IInterface {
    void getFocusedAITextInfo(AIInputResultListener aIInputResultListener) throws RemoteException;

    void insertFocusedAIText(String str, AIInputResultListener aIInputResultListener) throws RemoteException;

    void setFocusedAIText(String str, AIInputResultListener aIInputResultListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IAIInputTextManagerClient {
        private static final String DESCRIPTOR = "com.xiaomi.aiinput.IAIInputTextManagerClient";
        static final int TRANSACTION_getFocusedAITextInfo = 1;
        static final int TRANSACTION_insertFocusedAIText = 3;
        static final int TRANSACTION_setFocusedAIText = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAIInputTextManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAIInputTextManagerClient)) {
                return (IAIInputTextManagerClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                getFocusedAITextInfo(AIInputResultListener.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                setFocusedAIText(parcel.readString(), AIInputResultListener.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                insertFocusedAIText(parcel.readString(), AIInputResultListener.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAIInputTextManagerClient {
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

            @Override // com.xiaomi.aiinput.IAIInputTextManagerClient
            public void getFocusedAITextInfo(AIInputResultListener aIInputResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(aIInputResultListener != null ? aIInputResultListener.asBinder() : null);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aiinput.IAIInputTextManagerClient
            public void setFocusedAIText(String str, AIInputResultListener aIInputResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(aIInputResultListener != null ? aIInputResultListener.asBinder() : null);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aiinput.IAIInputTextManagerClient
            public void insertFocusedAIText(String str, AIInputResultListener aIInputResultListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(aIInputResultListener != null ? aIInputResultListener.asBinder() : null);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
