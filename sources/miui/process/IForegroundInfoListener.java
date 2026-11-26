package miui.process;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes5.dex */
public interface IForegroundInfoListener extends IInterface {
    public static final String DESCRIPTOR = "miui.process.IForegroundInfoListener";

    public static class Default implements IForegroundInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // miui.process.IForegroundInfoListener
        public void onForegroundInfoChanged(ForegroundInfo foregroundInfo) throws RemoteException {
        }
    }

    void onForegroundInfoChanged(ForegroundInfo foregroundInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IForegroundInfoListener {
        static final int TRANSACTION_onForegroundInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IForegroundInfoListener.DESCRIPTOR);
        }

        public static IForegroundInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IForegroundInfoListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IForegroundInfoListener)) {
                return (IForegroundInfoListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IForegroundInfoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IForegroundInfoListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onForegroundInfoChanged((ForegroundInfo) _Parcel.readTypedObject(parcel, ForegroundInfo.CREATOR));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IForegroundInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IForegroundInfoListener.DESCRIPTOR;
            }

            @Override // miui.process.IForegroundInfoListener
            public void onForegroundInfoChanged(ForegroundInfo foregroundInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IForegroundInfoListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, foregroundInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t != null) {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
