package com.android.soundrecorder.ai.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IAIRecorderServiceCallback extends IInterface {

    public static class Default implements IAIRecorderServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
        public void onAmplitudeChanged(double d, int i) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
        public void onErrorOccurred(int i, String str) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
        public void onStateChanged(int i, long j) throws RemoteException {
        }
    }

    void onAmplitudeChanged(double d, int i) throws RemoteException;

    void onErrorOccurred(int i, String str) throws RemoteException;

    void onStateChanged(int i, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IAIRecorderServiceCallback {
        private static final String DESCRIPTOR = "com.android.soundrecorder.ai.base.IAIRecorderServiceCallback";
        static final int TRANSACTION_onAmplitudeChanged = 3;
        static final int TRANSACTION_onErrorOccurred = 2;
        static final int TRANSACTION_onStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAIRecorderServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAIRecorderServiceCallback)) {
                return (IAIRecorderServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onStateChanged(parcel.readInt(), parcel.readLong());
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onErrorOccurred(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i != 3) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onAmplitudeChanged(parcel.readDouble(), parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IAIRecorderServiceCallback {
            public static IAIRecorderServiceCallback sDefaultImpl;
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

            @Override // com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
            public void onStateChanged(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(i, j);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
            public void onErrorOccurred(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onErrorOccurred(i, str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecorderServiceCallback
            public void onAmplitudeChanged(double d, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeDouble(d);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAmplitudeChanged(d, i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAIRecorderServiceCallback iAIRecorderServiceCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iAIRecorderServiceCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAIRecorderServiceCallback;
            return true;
        }

        public static IAIRecorderServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
