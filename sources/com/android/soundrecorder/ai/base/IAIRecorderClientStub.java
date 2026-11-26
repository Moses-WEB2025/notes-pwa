package com.android.soundrecorder.ai.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IAIRecorderClientStub extends IInterface {

    public static class Default implements IAIRecorderClientStub {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecorderClientStub
        public String register() throws RemoteException {
            return null;
        }
    }

    String register() throws RemoteException;

    public static abstract class Stub extends Binder implements IAIRecorderClientStub {
        private static final String DESCRIPTOR = "com.android.soundrecorder.ai.base.IAIRecorderClientStub";
        static final int TRANSACTION_register = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAIRecorderClientStub asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAIRecorderClientStub)) {
                return (IAIRecorderClientStub) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            String strRegister = register();
            parcel2.writeNoException();
            parcel2.writeString(strRegister);
            return true;
        }

        private static class Proxy implements IAIRecorderClientStub {
            public static IAIRecorderClientStub sDefaultImpl;
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

            @Override // com.android.soundrecorder.ai.base.IAIRecorderClientStub
            public String register() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().register();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAIRecorderClientStub iAIRecorderClientStub) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iAIRecorderClientStub == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAIRecorderClientStub;
            return true;
        }

        public static IAIRecorderClientStub getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
