package com.miui.creation.taskservice;

import android.content.ContentValues;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface ITaskServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.miui.creation.taskservice.ITaskServiceCallback";

    public static class Default implements ITaskServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void backToHome() throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void classificationForAi(boolean z, String str) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void copyAsText(String str) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportCompleted(boolean z, String str, int i) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportOnProgress(int i) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportPreviewFail() throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public boolean exportThumbnailFail() throws RemoteException {
            return false;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToAI(boolean z, String str, int i) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToPcCompleted(String str) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void onGenComplete(long j, ContentValues contentValues) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void recognizeAsText(String str, int i) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void showExportPreview(String str, int i, String str2, List<String> list, String str3) throws RemoteException {
        }
    }

    void backToHome() throws RemoteException;

    void classificationForAi(boolean z, String str) throws RemoteException;

    void copyAsText(String str) throws RemoteException;

    void exportCompleted(boolean z, String str, int i) throws RemoteException;

    void exportOnProgress(int i) throws RemoteException;

    void exportPreviewFail() throws RemoteException;

    boolean exportThumbnailFail() throws RemoteException;

    void exportToAI(boolean z, String str, int i) throws RemoteException;

    void exportToPcCompleted(String str) throws RemoteException;

    void onGenComplete(long j, ContentValues contentValues) throws RemoteException;

    void recognizeAsText(String str, int i) throws RemoteException;

    void showExportPreview(String str, int i, String str2, List<String> list, String str3) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskServiceCallback {
        static final int TRANSACTION_backToHome = 10;
        static final int TRANSACTION_classificationForAi = 3;
        static final int TRANSACTION_copyAsText = 2;
        static final int TRANSACTION_exportCompleted = 7;
        static final int TRANSACTION_exportOnProgress = 6;
        static final int TRANSACTION_exportPreviewFail = 5;
        static final int TRANSACTION_exportThumbnailFail = 11;
        static final int TRANSACTION_exportToAI = 8;
        static final int TRANSACTION_exportToPcCompleted = 9;
        static final int TRANSACTION_onGenComplete = 12;
        static final int TRANSACTION_recognizeAsText = 1;
        static final int TRANSACTION_showExportPreview = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ITaskServiceCallback.DESCRIPTOR);
        }

        public static ITaskServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITaskServiceCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITaskServiceCallback)) {
                return (ITaskServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITaskServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    recognizeAsText(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    copyAsText(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    classificationForAi(parcel.readInt() != 0, parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    showExportPreview(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.createStringArrayList(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    exportPreviewFail();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    exportOnProgress(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    exportCompleted(parcel.readInt() != 0, parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    exportToAI(parcel.readInt() != 0, parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    exportToPcCompleted(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    backToHome();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean zExportThumbnailFail = exportThumbnailFail();
                    parcel2.writeNoException();
                    parcel2.writeInt(zExportThumbnailFail ? 1 : 0);
                    return true;
                case 12:
                    onGenComplete(parcel.readLong(), (ContentValues) _Parcel.readTypedObject(parcel, ContentValues.CREATOR));
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITaskServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskServiceCallback.DESCRIPTOR;
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void recognizeAsText(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void copyAsText(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void classificationForAi(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void showExportPreview(String str, int i, String str2, List<String> list, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void exportPreviewFail() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void exportOnProgress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void exportCompleted(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void exportToAI(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void exportToPcCompleted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void backToHome() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public boolean exportThumbnailFail() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.creation.taskservice.ITaskServiceCallback
            public void onGenComplete(long j, ContentValues contentValues) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskServiceCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    _Parcel.writeTypedObject(parcelObtain, contentValues, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
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
