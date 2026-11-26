package miui.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes5.dex */
public interface IFreeformCallback extends IInterface {
    void dispatchFreeFormStackModeChanged(int i, MiuiFreeFormManager.MiuiFreeFormStackInfo miuiFreeFormStackInfo) throws RemoteException;

    public static class Default implements IFreeformCallback {
        public Default() {
            throw new RuntimeException("Stub!");
        }

        @Override // miui.app.IFreeformCallback
        public void dispatchFreeFormStackModeChanged(int i, MiuiFreeFormManager.MiuiFreeFormStackInfo miuiFreeFormStackInfo) throws RemoteException {
            throw new RuntimeException("Stub!");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }
    }

    public static abstract class Stub extends Binder implements IFreeformCallback {
        public static IFreeformCallback asInterface(IBinder iBinder) {
            throw new RuntimeException("Stub!");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            throw new RuntimeException("Stub!");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            throw new RuntimeException("Stub!");
        }

        public static boolean setDefaultImpl(IFreeformCallback iFreeformCallback) {
            throw new RuntimeException("Stub!");
        }

        public static IFreeformCallback getDefaultImpl() {
            throw new RuntimeException("Stub!");
        }
    }
}
