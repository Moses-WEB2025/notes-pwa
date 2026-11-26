package com.xiaomi.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IDownloadListener extends IInterface {

    public static class Default implements IDownloadListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public void cancelDownload(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public boolean checkVersion(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public boolean deleteFile(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public void download(String str, String str2, IDownloadCallback iDownloadCallback) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public String getCloudVersion(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public int getDownloadStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public String getLocalVersion(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public void pauseDownload(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public void removeTask(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public void resumeDownload(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadListener
        public void startDownload(String str) throws RemoteException {
        }
    }

    void cancelDownload(String str) throws RemoteException;

    boolean checkVersion(String str, int i) throws RemoteException;

    boolean deleteFile(String str) throws RemoteException;

    void download(String str, String str2, IDownloadCallback iDownloadCallback) throws RemoteException;

    String getCloudVersion(String str) throws RemoteException;

    int getDownloadStatus(String str) throws RemoteException;

    String getLocalVersion(String str) throws RemoteException;

    void pauseDownload(String str) throws RemoteException;

    void removeTask(String str) throws RemoteException;

    void resumeDownload(String str) throws RemoteException;

    void startDownload(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IDownloadListener {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.IDownloadListener";
        static final int TRANSACTION_cancelDownload = 6;
        static final int TRANSACTION_checkVersion = 9;
        static final int TRANSACTION_deleteFile = 2;
        static final int TRANSACTION_download = 1;
        static final int TRANSACTION_getCloudVersion = 11;
        static final int TRANSACTION_getDownloadStatus = 7;
        static final int TRANSACTION_getLocalVersion = 10;
        static final int TRANSACTION_pauseDownload = 4;
        static final int TRANSACTION_removeTask = 8;
        static final int TRANSACTION_resumeDownload = 5;
        static final int TRANSACTION_startDownload = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDownloadListener)) {
                return (IDownloadListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    download(parcel.readString(), parcel.readString(), IDownloadCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zDeleteFile = deleteFile(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zDeleteFile ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    startDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    pauseDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    resumeDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancelDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    int downloadStatus = getDownloadStatus(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(downloadStatus);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeTask(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zCheckVersion = checkVersion(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zCheckVersion ? 1 : 0);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    String localVersion = getLocalVersion(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(localVersion);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    String cloudVersion = getCloudVersion(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(cloudVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDownloadListener {
            public static IDownloadListener sDefaultImpl;
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

            @Override // com.xiaomi.aicr.IDownloadListener
            public void download(String str, String str2, IDownloadCallback iDownloadCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iDownloadCallback != null ? iDownloadCallback.asBinder() : null);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().download(str, str2, iDownloadCallback);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public boolean deleteFile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteFile(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public void startDownload(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDownload(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public void pauseDownload(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pauseDownload(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public void resumeDownload(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resumeDownload(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public void cancelDownload(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelDownload(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public int getDownloadStatus(String str) throws RemoteException {
                int downloadStatus;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        downloadStatus = Stub.getDefaultImpl().getDownloadStatus(str);
                    } else {
                        parcelObtain2.readException();
                        downloadStatus = parcelObtain2.readInt();
                    }
                    return downloadStatus;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public void removeTask(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeTask(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public boolean checkVersion(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkVersion(str, i);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadListener
            public String getLocalVersion(String str) throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getLocalVersion(str);
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

            @Override // com.xiaomi.aicr.IDownloadListener
            public String getCloudVersion(String str) throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getCloudVersion(str);
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

        public static boolean setDefaultImpl(IDownloadListener iDownloadListener) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iDownloadListener == null) {
                return false;
            }
            Proxy.sDefaultImpl = iDownloadListener;
            return true;
        }

        public static IDownloadListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
