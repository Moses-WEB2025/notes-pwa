package com.xiaomi.aicr.plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IMIotAlgoService extends IInterface {

    public static class Default implements IMIotAlgoService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
        public String doCalculation(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
        public String getBrightness() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
        public String getCaculatedCurve() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
        public void initAlgo(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
        public void releaseAlgo() throws RemoteException {
        }
    }

    String doCalculation(String str) throws RemoteException;

    String getBrightness() throws RemoteException;

    String getCaculatedCurve() throws RemoteException;

    void initAlgo(String str) throws RemoteException;

    void releaseAlgo() throws RemoteException;

    public static abstract class Stub extends Binder implements IMIotAlgoService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.plugin.IMIotAlgoService";
        static final int TRANSACTION_doCalculation = 2;
        static final int TRANSACTION_getBrightness = 4;
        static final int TRANSACTION_getCaculatedCurve = 5;
        static final int TRANSACTION_initAlgo = 1;
        static final int TRANSACTION_releaseAlgo = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMIotAlgoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMIotAlgoService)) {
                return (IMIotAlgoService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                initAlgo(parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                String strDoCalculation = doCalculation(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strDoCalculation);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                releaseAlgo();
                parcel2.writeNoException();
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                String brightness = getBrightness();
                parcel2.writeNoException();
                parcel2.writeString(brightness);
                return true;
            }
            if (i != 5) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            String caculatedCurve = getCaculatedCurve();
            parcel2.writeNoException();
            parcel2.writeString(caculatedCurve);
            return true;
        }

        private static class Proxy implements IMIotAlgoService {
            public static IMIotAlgoService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
            public void initAlgo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initAlgo(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
            public String doCalculation(String str) throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().doCalculation(str);
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
            public void releaseAlgo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseAlgo();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
            public String getBrightness() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getBrightness();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IMIotAlgoService
            public String getCaculatedCurve() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getCaculatedCurve();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMIotAlgoService iMIotAlgoService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iMIotAlgoService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iMIotAlgoService;
            return true;
        }

        public static IMIotAlgoService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
