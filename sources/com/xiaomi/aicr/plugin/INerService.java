package com.xiaomi.aicr.plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.aicr.nlp.Entity;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface INerService extends IInterface {

    public static class Default implements INerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.INerService
        public List<String> cut(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.INerService
        public List<Entity> extract(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.INerService
        public List<Entity> extractForContext(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.INerService
        public List<Entity> extractForScreenRecognition(String str) throws RemoteException {
            return null;
        }
    }

    List<String> cut(String str) throws RemoteException;

    List<Entity> extract(String str) throws RemoteException;

    List<Entity> extractForContext(String str) throws RemoteException;

    List<Entity> extractForScreenRecognition(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements INerService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.plugin.INerService";
        static final int TRANSACTION_cut = 2;
        static final int TRANSACTION_extract = 1;
        static final int TRANSACTION_extractForContext = 4;
        static final int TRANSACTION_extractForScreenRecognition = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INerService)) {
                return (INerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                List<Entity> listExtract = extract(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(listExtract);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                List<String> listCut = cut(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeStringList(listCut);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                List<Entity> listExtractForScreenRecognition = extractForScreenRecognition(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(listExtractForScreenRecognition);
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
            List<Entity> listExtractForContext = extractForContext(parcel.readString());
            parcel2.writeNoException();
            parcel2.writeTypedList(listExtractForContext);
            return true;
        }

        private static class Proxy implements INerService {
            public static INerService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.plugin.INerService
            public List<Entity> extract(String str) throws RemoteException {
                List<Entity> listCreateTypedArrayList;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        listCreateTypedArrayList = Stub.getDefaultImpl().extract(str);
                    } else {
                        parcelObtain2.readException();
                        listCreateTypedArrayList = parcelObtain2.createTypedArrayList(Entity.CREATOR);
                    }
                    return listCreateTypedArrayList;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.INerService
            public List<String> cut(String str) throws RemoteException {
                List<String> listCreateStringArrayList;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        listCreateStringArrayList = Stub.getDefaultImpl().cut(str);
                    } else {
                        parcelObtain2.readException();
                        listCreateStringArrayList = parcelObtain2.createStringArrayList();
                    }
                    return listCreateStringArrayList;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.INerService
            public List<Entity> extractForScreenRecognition(String str) throws RemoteException {
                List<Entity> listCreateTypedArrayList;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        listCreateTypedArrayList = Stub.getDefaultImpl().extractForScreenRecognition(str);
                    } else {
                        parcelObtain2.readException();
                        listCreateTypedArrayList = parcelObtain2.createTypedArrayList(Entity.CREATOR);
                    }
                    return listCreateTypedArrayList;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.INerService
            public List<Entity> extractForContext(String str) throws RemoteException {
                List<Entity> listCreateTypedArrayList;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        listCreateTypedArrayList = Stub.getDefaultImpl().extractForContext(str);
                    } else {
                        parcelObtain2.readException();
                        listCreateTypedArrayList = parcelObtain2.createTypedArrayList(Entity.CREATOR);
                    }
                    return listCreateTypedArrayList;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(INerService iNerService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iNerService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iNerService;
            return true;
        }

        public static INerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
