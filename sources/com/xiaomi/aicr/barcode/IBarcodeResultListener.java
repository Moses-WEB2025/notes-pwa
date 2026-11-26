package com.xiaomi.aicr.barcode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface IBarcodeResultListener extends IInterface {

    public static class Default implements IBarcodeResultListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.barcode.IBarcodeResultListener
        public void onBarcodeResult(List<BarcodeBean> list) throws RemoteException {
        }
    }

    void onBarcodeResult(List<BarcodeBean> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IBarcodeResultListener {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.barcode.IBarcodeResultListener";
        static final int TRANSACTION_onBarcodeResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBarcodeResultListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBarcodeResultListener)) {
                return (IBarcodeResultListener) iInterfaceQueryLocalInterface;
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
            onBarcodeResult(parcel.createTypedArrayList(BarcodeBean.CREATOR));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IBarcodeResultListener {
            public static IBarcodeResultListener sDefaultImpl;
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

            @Override // com.xiaomi.aicr.barcode.IBarcodeResultListener
            public void onBarcodeResult(List<BarcodeBean> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBarcodeResult(list);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBarcodeResultListener iBarcodeResultListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iBarcodeResultListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iBarcodeResultListener;
            return true;
        }

        public static IBarcodeResultListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
