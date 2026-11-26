package com.xiaomi.aicr.plugin;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.aicr.barcode.IBarcodeResultListener;

/* JADX INFO: loaded from: classes4.dex */
public interface IBarcodeService extends IInterface {

    public static class Default implements IBarcodeService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IBarcodeService
        public void detectBarcodeFromBitmap(Bitmap bitmap, String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.plugin.IBarcodeService
        public void registerBarcodeResultListener(IBarcodeResultListener iBarcodeResultListener, String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.plugin.IBarcodeService
        public void unregisterBarcodeResultListener(String str) throws RemoteException {
        }
    }

    void detectBarcodeFromBitmap(Bitmap bitmap, String str) throws RemoteException;

    void registerBarcodeResultListener(IBarcodeResultListener iBarcodeResultListener, String str) throws RemoteException;

    void unregisterBarcodeResultListener(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IBarcodeService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.plugin.IBarcodeService";
        static final int TRANSACTION_detectBarcodeFromBitmap = 3;
        static final int TRANSACTION_registerBarcodeResultListener = 1;
        static final int TRANSACTION_unregisterBarcodeResultListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBarcodeService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBarcodeService)) {
                return (IBarcodeService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                registerBarcodeResultListener(IBarcodeResultListener.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                unregisterBarcodeResultListener(parcel.readString());
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                detectBarcodeFromBitmap(parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                return true;
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBarcodeService {
            public static IBarcodeService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.plugin.IBarcodeService
            public void registerBarcodeResultListener(IBarcodeResultListener iBarcodeResultListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBarcodeResultListener != null ? iBarcodeResultListener.asBinder() : null);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerBarcodeResultListener(iBarcodeResultListener, str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IBarcodeService
            public void unregisterBarcodeResultListener(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(2, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().unregisterBarcodeResultListener(str);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IBarcodeService
            public void detectBarcodeFromBitmap(Bitmap bitmap, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bitmap != null) {
                        parcelObtain.writeInt(1);
                        bitmap.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(3, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().detectBarcodeFromBitmap(bitmap, str);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBarcodeService iBarcodeService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iBarcodeService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iBarcodeService;
            return true;
        }

        public static IBarcodeService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
