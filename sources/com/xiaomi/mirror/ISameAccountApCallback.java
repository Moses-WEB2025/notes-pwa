package com.xiaomi.mirror;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public interface ISameAccountApCallback extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.mirror.ISameAccountApCallback";

    public static class Default implements ISameAccountApCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.mirror.ISameAccountApCallback
        public void onApConnectedStatusUpdate(int i, Bundle bundle) {
        }

        @Override // com.xiaomi.mirror.ISameAccountApCallback
        public void onApInfoUpdate(Bundle bundle) {
        }
    }

    public static abstract class Stub extends Binder implements ISameAccountApCallback {
        public static final int TRANSACTION_onApConnectedStatusUpdate = 2;
        public static final int TRANSACTION_onApInfoUpdate = 1;

        public static class Proxy implements ISameAccountApCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISameAccountApCallback.DESCRIPTOR;
            }

            @Override // com.xiaomi.mirror.ISameAccountApCallback
            public void onApConnectedStatusUpdate(int i, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISameAccountApCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.mirror.ISameAccountApCallback
            public void onApInfoUpdate(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISameAccountApCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISameAccountApCallback.DESCRIPTOR);
        }

        public static ISameAccountApCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISameAccountApCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISameAccountApCallback)) ? new Proxy(iBinder) : (ISameAccountApCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISameAccountApCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISameAccountApCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onApInfoUpdate((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onApConnectedStatusUpdate(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            }
            return true;
        }
    }

    public static class _Parcel {
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    void onApConnectedStatusUpdate(int i, Bundle bundle);

    void onApInfoUpdate(Bundle bundle);
}
