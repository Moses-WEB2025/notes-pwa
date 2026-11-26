package com.xiaomi.mirror;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes4.dex */
public interface IMirrorTakePhotoCallback extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.mirror.IMirrorTakePhotoCallback";

    public static class Default implements IMirrorTakePhotoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.mirror.IMirrorTakePhotoCallback
        public void onTakePhotoChanged(String str, boolean z) {
        }
    }

    public static abstract class Stub extends Binder implements IMirrorTakePhotoCallback {
        public static final int TRANSACTION_onTakePhotoChanged = 1;

        public static class Proxy implements IMirrorTakePhotoCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMirrorTakePhotoCallback.DESCRIPTOR;
            }

            @Override // com.xiaomi.mirror.IMirrorTakePhotoCallback
            public void onTakePhotoChanged(String str, boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMirrorTakePhotoCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMirrorTakePhotoCallback.DESCRIPTOR);
        }

        public static IMirrorTakePhotoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMirrorTakePhotoCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMirrorTakePhotoCallback)) ? new Proxy(iBinder) : (IMirrorTakePhotoCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMirrorTakePhotoCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMirrorTakePhotoCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onTakePhotoChanged(parcel.readString(), parcel.readInt() != 0);
            return true;
        }
    }

    void onTakePhotoChanged(String str, boolean z);
}
