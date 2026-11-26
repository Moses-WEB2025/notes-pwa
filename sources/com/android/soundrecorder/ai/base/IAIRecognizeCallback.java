package com.android.soundrecorder.ai.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.soundrecorder.ai.base.bean.AISentence;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IAIRecognizeCallback extends IInterface {

    public static class Default implements IAIRecognizeCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
        public void onRecognizeError(int i, String str) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
        public void onRecognizeEvent(int i, String str) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
        public void onRecognizeResultUpdate(List<AISentence> list, boolean z, boolean z2, boolean z3) throws RemoteException {
        }

        @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
        public void onRecognizeStatusChange(int i, List<AISentence> list) throws RemoteException {
        }
    }

    void onRecognizeError(int i, String str) throws RemoteException;

    void onRecognizeEvent(int i, String str) throws RemoteException;

    void onRecognizeResultUpdate(List<AISentence> list, boolean z, boolean z2, boolean z3) throws RemoteException;

    void onRecognizeStatusChange(int i, List<AISentence> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IAIRecognizeCallback {
        private static final String DESCRIPTOR = "com.android.soundrecorder.ai.base.IAIRecognizeCallback";
        static final int TRANSACTION_onRecognizeError = 1;
        static final int TRANSACTION_onRecognizeEvent = 2;
        static final int TRANSACTION_onRecognizeResultUpdate = 4;
        static final int TRANSACTION_onRecognizeStatusChange = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAIRecognizeCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAIRecognizeCallback)) {
                return (IAIRecognizeCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onRecognizeError(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onRecognizeEvent(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                onRecognizeStatusChange(parcel.readInt(), parcel.createTypedArrayList(AISentence.CREATOR));
                parcel2.writeNoException();
                return true;
            }
            if (i != 4) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onRecognizeResultUpdate(parcel.createTypedArrayList(AISentence.CREATOR), parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0);
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IAIRecognizeCallback {
            public static IAIRecognizeCallback sDefaultImpl;
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

            @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
            public void onRecognizeError(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecognizeError(i, str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
            public void onRecognizeEvent(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecognizeEvent(i, str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
            public void onRecognizeStatusChange(int i, List<AISentence> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecognizeStatusChange(i, list);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.soundrecorder.ai.base.IAIRecognizeCallback
            public void onRecognizeResultUpdate(List<AISentence> list, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    parcelObtain.writeInt(z3 ? 1 : 0);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecognizeResultUpdate(list, z, z2, z3);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAIRecognizeCallback iAIRecognizeCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iAIRecognizeCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAIRecognizeCallback;
            return true;
        }

        public static IAIRecognizeCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
