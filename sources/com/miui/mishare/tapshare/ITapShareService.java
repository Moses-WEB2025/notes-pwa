package com.miui.mishare.tapshare;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface ITapShareService extends IInterface {
    public static final String DESCRIPTOR = "com.miui.mishare.tapshare.ITapShareService";

    public static class Default implements ITapShareService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void cancelSend(int i, String str) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public int getServiceVersion() throws RemoteException {
            return 0;
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void notifyHiddenTipsView() throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void notifyShowTipsView() throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void registerTapShareSendCallback(ITapShareSendCallback iTapShareSendCallback, String str) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void registerTapShareSendCallbackV2(ITapShareSendCallback iTapShareSendCallback, String str, TapShareRegisterBean tapShareRegisterBean) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void sendByLinkAndUris(String str, List<Uri> list, TapShareSendBean tapShareSendBean) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void sendByString(String str, TapShareSendBean tapShareSendBean) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void sendByUris(List<Uri> list, TapShareSendBean tapShareSendBean) throws RemoteException {
        }

        @Override // com.miui.mishare.tapshare.ITapShareService
        public void unregisterTapShareSendCallback(ITapShareSendCallback iTapShareSendCallback) throws RemoteException {
        }
    }

    void cancelSend(int i, String str) throws RemoteException;

    int getServiceVersion() throws RemoteException;

    void notifyHiddenTipsView() throws RemoteException;

    void notifyShowTipsView() throws RemoteException;

    void registerTapShareSendCallback(ITapShareSendCallback iTapShareSendCallback, String str) throws RemoteException;

    void registerTapShareSendCallbackV2(ITapShareSendCallback iTapShareSendCallback, String str, TapShareRegisterBean tapShareRegisterBean) throws RemoteException;

    void sendByLinkAndUris(String str, List<Uri> list, TapShareSendBean tapShareSendBean) throws RemoteException;

    void sendByString(String str, TapShareSendBean tapShareSendBean) throws RemoteException;

    void sendByUris(List<Uri> list, TapShareSendBean tapShareSendBean) throws RemoteException;

    void unregisterTapShareSendCallback(ITapShareSendCallback iTapShareSendCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITapShareService {
        static final int TRANSACTION_cancelSend = 5;
        static final int TRANSACTION_getServiceVersion = 9;
        static final int TRANSACTION_notifyHiddenTipsView = 7;
        static final int TRANSACTION_notifyShowTipsView = 6;
        static final int TRANSACTION_registerTapShareSendCallback = 1;
        static final int TRANSACTION_registerTapShareSendCallbackV2 = 8;
        static final int TRANSACTION_sendByLinkAndUris = 10;
        static final int TRANSACTION_sendByString = 4;
        static final int TRANSACTION_sendByUris = 3;
        static final int TRANSACTION_unregisterTapShareSendCallback = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ITapShareService.DESCRIPTOR);
        }

        public static ITapShareService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITapShareService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITapShareService)) {
                return (ITapShareService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITapShareService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITapShareService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    registerTapShareSendCallback(ITapShareSendCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    unregisterTapShareSendCallback(ITapShareSendCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    sendByUris(parcel.createTypedArrayList(Uri.CREATOR), (TapShareSendBean) _Parcel.readTypedObject(parcel, TapShareSendBean.CREATOR));
                    return true;
                case 4:
                    sendByString(parcel.readString(), (TapShareSendBean) _Parcel.readTypedObject(parcel, TapShareSendBean.CREATOR));
                    return true;
                case 5:
                    cancelSend(parcel.readInt(), parcel.readString());
                    return true;
                case 6:
                    notifyShowTipsView();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    notifyHiddenTipsView();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    registerTapShareSendCallbackV2(ITapShareSendCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString(), (TapShareRegisterBean) _Parcel.readTypedObject(parcel, TapShareRegisterBean.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int serviceVersion = getServiceVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(serviceVersion);
                    return true;
                case 10:
                    sendByLinkAndUris(parcel.readString(), parcel.createTypedArrayList(Uri.CREATOR), (TapShareSendBean) _Parcel.readTypedObject(parcel, TapShareSendBean.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITapShareService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITapShareService.DESCRIPTOR;
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void registerTapShareSendCallback(ITapShareSendCallback iTapShareSendCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTapShareSendCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void unregisterTapShareSendCallback(ITapShareSendCallback iTapShareSendCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTapShareSendCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void sendByUris(List<Uri> list, TapShareSendBean tapShareSendBean) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list);
                    _Parcel.writeTypedObject(parcelObtain, tapShareSendBean, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void sendByString(String str, TapShareSendBean tapShareSendBean) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, tapShareSendBean, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void cancelSend(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void notifyShowTipsView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void notifyHiddenTipsView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void registerTapShareSendCallbackV2(ITapShareSendCallback iTapShareSendCallback, String str, TapShareRegisterBean tapShareRegisterBean) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTapShareSendCallback);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, tapShareRegisterBean, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public int getServiceVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.mishare.tapshare.ITapShareService
            public void sendByLinkAndUris(String str, List<Uri> list, TapShareSendBean tapShareSendBean) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapShareService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list);
                    _Parcel.writeTypedObject(parcelObtain, tapShareSendBean, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
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
