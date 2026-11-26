package com.xiaomi.mirror;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes4.dex */
public interface IMirrorShareCallback extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.mirror.IMirrorShareCallback";

    public static class Default implements IMirrorShareCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.mirror.IMirrorShareCallback
        public void onStateChanged(String str) {
        }
    }

    public static abstract class Stub extends Binder implements IMirrorShareCallback {
        public static final int TRANSACTION_onStateChanged = 1;

        public static class Proxy implements IMirrorShareCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMirrorShareCallback.DESCRIPTOR;
            }

            @Override // com.xiaomi.mirror.IMirrorShareCallback
            public void onStateChanged(String str) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMirrorShareCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMirrorShareCallback.DESCRIPTOR);
        }

        public static IMirrorShareCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMirrorShareCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMirrorShareCallback)) ? new Proxy(iBinder) : (IMirrorShareCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMirrorShareCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMirrorShareCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onStateChanged(parcel.readString());
            return true;
        }
    }

    void onStateChanged(String str);
}
