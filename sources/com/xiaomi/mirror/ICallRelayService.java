package com.xiaomi.mirror;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes4.dex */
public interface ICallRelayService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.mirror.ICallRelayService";

    public static class Default implements ICallRelayService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.mirror.ICallRelayService
        public void registerCallRelayListener(ICallRelayListener iCallRelayListener) {
        }

        @Override // com.xiaomi.mirror.ICallRelayService
        public void release() {
        }

        @Override // com.xiaomi.mirror.ICallRelayService
        public void sendRelayMessage(String str) {
        }

        @Override // com.xiaomi.mirror.ICallRelayService
        public void setCallState(int i) {
        }
    }

    public static abstract class Stub extends Binder implements ICallRelayService {
        public static final int TRANSACTION_registerCallRelayListener = 2;
        public static final int TRANSACTION_release = 4;
        public static final int TRANSACTION_sendRelayMessage = 1;
        public static final int TRANSACTION_setCallState = 3;

        public static class Proxy implements ICallRelayService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallRelayService.DESCRIPTOR;
            }

            @Override // com.xiaomi.mirror.ICallRelayService
            public void registerCallRelayListener(ICallRelayListener iCallRelayListener) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICallRelayService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCallRelayListener);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.mirror.ICallRelayService
            public void release() {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICallRelayService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.mirror.ICallRelayService
            public void sendRelayMessage(String str) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICallRelayService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.mirror.ICallRelayService
            public void setCallState(int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICallRelayService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICallRelayService.DESCRIPTOR);
        }

        public static ICallRelayService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallRelayService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICallRelayService)) ? new Proxy(iBinder) : (ICallRelayService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallRelayService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallRelayService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                sendRelayMessage(parcel.readString());
            } else if (i == 2) {
                registerCallRelayListener(ICallRelayListener.Stub.asInterface(parcel.readStrongBinder()));
            } else if (i == 3) {
                setCallState(parcel.readInt());
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                release();
            }
            return true;
        }
    }

    void registerCallRelayListener(ICallRelayListener iCallRelayListener);

    void release();

    void sendRelayMessage(String str);

    void setCallState(int i);
}
