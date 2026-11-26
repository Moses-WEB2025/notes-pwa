package com.miui.creation.taskservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes2.dex */
public interface ICreationTaskInterface extends IInterface {
    public static final String DESCRIPTOR = "com.miui.creation.taskservice.ICreationTaskInterface";

    public static class Default implements ICreationTaskInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void autoSavaThumbnail(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportFile(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportPreviewImage(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportToAi(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void exportToPc(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void removeSearchCallback() throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void saveRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void saveThumbnailAndRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void selectClassificationForAi(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void selectRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ICreationTaskInterface
        public void setSearchCallback(ISearchCallback iSearchCallback) throws RemoteException {
        }
    }

    void autoSavaThumbnail(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void exportFile(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void exportPreviewImage(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void exportToAi(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void exportToPc(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void removeSearchCallback() throws RemoteException;

    void saveRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void saveThumbnailAndRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void selectClassificationForAi(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void selectRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException;

    void setSearchCallback(ISearchCallback iSearchCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ICreationTaskInterface {
        static final int TRANSACTION_autoSavaThumbnail = 1;
        static final int TRANSACTION_exportFile = 4;
        static final int TRANSACTION_exportPreviewImage = 2;
        static final int TRANSACTION_exportToAi = 3;
        static final int TRANSACTION_exportToPc = 5;
        static final int TRANSACTION_removeSearchCallback = 11;
        static final int TRANSACTION_saveRecognition = 8;
        static final int TRANSACTION_saveThumbnailAndRecognition = 6;
        static final int TRANSACTION_selectClassificationForAi = 9;
        static final int TRANSACTION_selectRecognition = 7;
        static final int TRANSACTION_setSearchCallback = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICreationTaskInterface.DESCRIPTOR);
        }

        public static ICreationTaskInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICreationTaskInterface.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICreationTaskInterface)) {
                return (ICreationTaskInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICreationTaskInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICreationTaskInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    autoSavaThumbnail((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    exportPreviewImage((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    exportToAi((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    exportFile((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    exportToPc((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    saveThumbnailAndRecognition((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    selectRecognition((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    saveRecognition((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    selectClassificationForAi((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR), ITaskServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 10:
                    setSearchCallback(ISearchCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 11:
                    removeSearchCallback();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICreationTaskInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICreationTaskInterface.DESCRIPTOR;
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void autoSavaThumbnail(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void exportPreviewImage(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void exportToAi(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void exportFile(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void exportToPc(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void saveThumbnailAndRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void selectRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void saveRecognition(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void selectClassificationForAi(Bundle bundle, ITaskServiceCallback iTaskServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    parcelObtain.writeStrongInterface(iTaskServiceCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void setSearchCallback(ISearchCallback iSearchCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSearchCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ICreationTaskInterface
            public void removeSearchCallback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICreationTaskInterface.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
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
