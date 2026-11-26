package com.xiaomi.aicr.llm;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface ILLMService extends IInterface {

    public static class Default implements ILLMService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int cancelTextEditSearchDevice() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public String creationGetVersion() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public String creationScreenShot() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int initModel(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int messageSummarizeCheck() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int messageSummarizeProcess(String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int messageSummarizeProcessV2(String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, ResultReceiver resultReceiver) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int onLineCreationProcess(String str, Bundle bundle, IAICreationCallback iAICreationCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public void registerDeathLinkCallback(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public void removeDeathLinkCallback(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int textEditCOTProcess(String str, String str2, String str3, ITextEditProcessCallback iTextEditProcessCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int textEditCheck(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int textEditProcess(String str, String str2, String str3, ITextEditProcessCallback iTextEditProcessCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int textEditSearchDevice(IDeviceSearchCallback iDeviceSearchCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.llm.ILLMService
        public int textEditStopAllProcess() throws RemoteException {
            return 0;
        }
    }

    int cancelTextEditSearchDevice() throws RemoteException;

    String creationGetVersion() throws RemoteException;

    String creationScreenShot() throws RemoteException;

    int initModel(String str, int i) throws RemoteException;

    int messageSummarizeCheck() throws RemoteException;

    int messageSummarizeProcess(String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6) throws RemoteException;

    int messageSummarizeProcessV2(String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, ResultReceiver resultReceiver) throws RemoteException;

    int onLineCreationProcess(String str, Bundle bundle, IAICreationCallback iAICreationCallback) throws RemoteException;

    void registerDeathLinkCallback(IBinder iBinder, String str) throws RemoteException;

    void removeDeathLinkCallback(String str) throws RemoteException;

    int textEditCOTProcess(String str, String str2, String str3, ITextEditProcessCallback iTextEditProcessCallback) throws RemoteException;

    int textEditCheck(String str) throws RemoteException;

    int textEditProcess(String str, String str2, String str3, ITextEditProcessCallback iTextEditProcessCallback) throws RemoteException;

    int textEditSearchDevice(IDeviceSearchCallback iDeviceSearchCallback) throws RemoteException;

    int textEditStopAllProcess() throws RemoteException;

    public static abstract class Stub extends Binder implements ILLMService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.llm.ILLMService";
        static final int TRANSACTION_cancelTextEditSearchDevice = 9;
        static final int TRANSACTION_creationGetVersion = 15;
        static final int TRANSACTION_creationScreenShot = 14;
        static final int TRANSACTION_initModel = 12;
        static final int TRANSACTION_messageSummarizeCheck = 2;
        static final int TRANSACTION_messageSummarizeProcess = 1;
        static final int TRANSACTION_messageSummarizeProcessV2 = 10;
        static final int TRANSACTION_onLineCreationProcess = 13;
        static final int TRANSACTION_registerDeathLinkCallback = 6;
        static final int TRANSACTION_removeDeathLinkCallback = 7;
        static final int TRANSACTION_textEditCOTProcess = 11;
        static final int TRANSACTION_textEditCheck = 4;
        static final int TRANSACTION_textEditProcess = 3;
        static final int TRANSACTION_textEditSearchDevice = 8;
        static final int TRANSACTION_textEditStopAllProcess = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILLMService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILLMService)) {
                return (ILLMService) iInterfaceQueryLocalInterface;
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
                    int iMessageSummarizeProcess = messageSummarizeProcess(parcel.readString(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList());
                    parcel2.writeNoException();
                    parcel2.writeInt(iMessageSummarizeProcess);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iMessageSummarizeCheck = messageSummarizeCheck();
                    parcel2.writeNoException();
                    parcel2.writeInt(iMessageSummarizeCheck);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iTextEditProcess = textEditProcess(parcel.readString(), parcel.readString(), parcel.readString(), ITextEditProcessCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iTextEditProcess);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iTextEditCheck = textEditCheck(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iTextEditCheck);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iTextEditStopAllProcess = textEditStopAllProcess();
                    parcel2.writeNoException();
                    parcel2.writeInt(iTextEditStopAllProcess);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerDeathLinkCallback(parcel.readStrongBinder(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeDeathLinkCallback(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iTextEditSearchDevice = textEditSearchDevice(IDeviceSearchCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iTextEditSearchDevice);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iCancelTextEditSearchDevice = cancelTextEditSearchDevice();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCancelTextEditSearchDevice);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iMessageSummarizeProcessV2 = messageSummarizeProcessV2(parcel.readString(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.createStringArrayList(), parcel.readInt() != 0 ? (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMessageSummarizeProcessV2);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iTextEditCOTProcess = textEditCOTProcess(parcel.readString(), parcel.readString(), parcel.readString(), ITextEditProcessCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iTextEditCOTProcess);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iInitModel = initModel(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitModel);
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iOnLineCreationProcess = onLineCreationProcess(parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IAICreationCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iOnLineCreationProcess);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    String strCreationScreenShot = creationScreenShot();
                    parcel2.writeNoException();
                    parcel2.writeString(strCreationScreenShot);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    String strCreationGetVersion = creationGetVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(strCreationGetVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ILLMService {
            public static ILLMService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int messageSummarizeProcess(String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6) throws RemoteException {
                int iMessageSummarizeProcess;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStringList(list3);
                    parcelObtain.writeStringList(list4);
                    parcelObtain.writeStringList(list5);
                    parcelObtain.writeStringList(list6);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iMessageSummarizeProcess = Stub.getDefaultImpl().messageSummarizeProcess(str, list, list2, list3, list4, list5, list6);
                    } else {
                        parcelObtain2.readException();
                        iMessageSummarizeProcess = parcelObtain2.readInt();
                    }
                    return iMessageSummarizeProcess;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int messageSummarizeCheck() throws RemoteException {
                int iMessageSummarizeCheck;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iMessageSummarizeCheck = Stub.getDefaultImpl().messageSummarizeCheck();
                    } else {
                        parcelObtain2.readException();
                        iMessageSummarizeCheck = parcelObtain2.readInt();
                    }
                    return iMessageSummarizeCheck;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int textEditProcess(String str, String str2, String str3, ITextEditProcessCallback iTextEditProcessCallback) throws RemoteException {
                int iTextEditProcess;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongBinder(iTextEditProcessCallback != null ? iTextEditProcessCallback.asBinder() : null);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iTextEditProcess = Stub.getDefaultImpl().textEditProcess(str, str2, str3, iTextEditProcessCallback);
                    } else {
                        parcelObtain2.readException();
                        iTextEditProcess = parcelObtain2.readInt();
                    }
                    return iTextEditProcess;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int textEditCheck(String str) throws RemoteException {
                int iTextEditCheck;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iTextEditCheck = Stub.getDefaultImpl().textEditCheck(str);
                    } else {
                        parcelObtain2.readException();
                        iTextEditCheck = parcelObtain2.readInt();
                    }
                    return iTextEditCheck;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int textEditStopAllProcess() throws RemoteException {
                int iTextEditStopAllProcess;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iTextEditStopAllProcess = Stub.getDefaultImpl().textEditStopAllProcess();
                    } else {
                        parcelObtain2.readException();
                        iTextEditStopAllProcess = parcelObtain2.readInt();
                    }
                    return iTextEditStopAllProcess;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public void registerDeathLinkCallback(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerDeathLinkCallback(iBinder, str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public void removeDeathLinkCallback(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeDeathLinkCallback(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int textEditSearchDevice(IDeviceSearchCallback iDeviceSearchCallback) throws RemoteException {
                int iTextEditSearchDevice;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iDeviceSearchCallback != null ? iDeviceSearchCallback.asBinder() : null);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iTextEditSearchDevice = Stub.getDefaultImpl().textEditSearchDevice(iDeviceSearchCallback);
                    } else {
                        parcelObtain2.readException();
                        iTextEditSearchDevice = parcelObtain2.readInt();
                    }
                    return iTextEditSearchDevice;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int cancelTextEditSearchDevice() throws RemoteException {
                int iCancelTextEditSearchDevice;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iCancelTextEditSearchDevice = Stub.getDefaultImpl().cancelTextEditSearchDevice();
                    } else {
                        parcelObtain2.readException();
                        iCancelTextEditSearchDevice = parcelObtain2.readInt();
                    }
                    return iCancelTextEditSearchDevice;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int messageSummarizeProcessV2(String str, List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, ResultReceiver resultReceiver) throws RemoteException {
                int iMessageSummarizeProcessV2;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStringList(list3);
                    parcelObtain.writeStringList(list4);
                    parcelObtain.writeStringList(list5);
                    parcelObtain.writeStringList(list6);
                    if (resultReceiver != null) {
                        parcelObtain.writeInt(1);
                        resultReceiver.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iMessageSummarizeProcessV2 = Stub.getDefaultImpl().messageSummarizeProcessV2(str, list, list2, list3, list4, list5, list6, resultReceiver);
                    } else {
                        parcelObtain2.readException();
                        iMessageSummarizeProcessV2 = parcelObtain2.readInt();
                    }
                    return iMessageSummarizeProcessV2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int textEditCOTProcess(String str, String str2, String str3, ITextEditProcessCallback iTextEditProcessCallback) throws RemoteException {
                int iTextEditCOTProcess;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongBinder(iTextEditProcessCallback != null ? iTextEditProcessCallback.asBinder() : null);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iTextEditCOTProcess = Stub.getDefaultImpl().textEditCOTProcess(str, str2, str3, iTextEditProcessCallback);
                    } else {
                        parcelObtain2.readException();
                        iTextEditCOTProcess = parcelObtain2.readInt();
                    }
                    return iTextEditCOTProcess;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int initModel(String str, int i) throws RemoteException {
                int iInitModel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iInitModel = Stub.getDefaultImpl().initModel(str, i);
                    } else {
                        parcelObtain2.readException();
                        iInitModel = parcelObtain2.readInt();
                    }
                    return iInitModel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public int onLineCreationProcess(String str, Bundle bundle, IAICreationCallback iAICreationCallback) throws RemoteException {
                int iOnLineCreationProcess;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iAICreationCallback != null ? iAICreationCallback.asBinder() : null);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iOnLineCreationProcess = Stub.getDefaultImpl().onLineCreationProcess(str, bundle, iAICreationCallback);
                    } else {
                        parcelObtain2.readException();
                        iOnLineCreationProcess = parcelObtain2.readInt();
                    }
                    return iOnLineCreationProcess;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.ILLMService
            public String creationScreenShot() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().creationScreenShot();
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

            @Override // com.xiaomi.aicr.llm.ILLMService
            public String creationGetVersion() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().creationGetVersion();
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

        public static boolean setDefaultImpl(ILLMService iLLMService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iLLMService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iLLMService;
            return true;
        }

        public static ILLMService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
