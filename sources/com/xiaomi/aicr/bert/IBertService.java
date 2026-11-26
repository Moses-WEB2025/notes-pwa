package com.xiaomi.aicr.bert;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IBertService extends IInterface {

    public static class Default implements IBertService {
        @Override // com.xiaomi.aicr.bert.IBertService
        public float[] Berte5Encode(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public Bundle BertnluEncode(String str, float f, float f2, float f3, float f4) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public Bundle BertnluGetScopeResultsInteger() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public Bundle BertnluGetScopeResultsString() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public int getExtractTextFeature(int[] iArr, float[] fArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public int[] getTokenizedE5(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public String getTokenizedNlu() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public int initModel(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.bert.IBertService
        public int releaseModel(String str) throws RemoteException {
            return 0;
        }
    }

    float[] Berte5Encode(int[] iArr) throws RemoteException;

    Bundle BertnluEncode(String str, float f, float f2, float f3, float f4) throws RemoteException;

    Bundle BertnluGetScopeResultsInteger() throws RemoteException;

    Bundle BertnluGetScopeResultsString() throws RemoteException;

    int getExtractTextFeature(int[] iArr, float[] fArr) throws RemoteException;

    int[] getTokenizedE5(String str) throws RemoteException;

    String getTokenizedNlu() throws RemoteException;

    int initModel(String str) throws RemoteException;

    int releaseModel(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IBertService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.bert.IBertService";
        static final int TRANSACTION_Berte5Encode = 4;
        static final int TRANSACTION_BertnluEncode = 5;
        static final int TRANSACTION_BertnluGetScopeResultsInteger = 7;
        static final int TRANSACTION_BertnluGetScopeResultsString = 6;
        static final int TRANSACTION_getExtractTextFeature = 9;
        static final int TRANSACTION_getTokenizedE5 = 2;
        static final int TRANSACTION_getTokenizedNlu = 3;
        static final int TRANSACTION_initModel = 1;
        static final int TRANSACTION_releaseModel = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBertService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBertService)) {
                return (IBertService) iInterfaceQueryLocalInterface;
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
                    int iInitModel = initModel(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitModel);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int[] tokenizedE5 = getTokenizedE5(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeIntArray(tokenizedE5);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    String tokenizedNlu = getTokenizedNlu();
                    parcel2.writeNoException();
                    parcel2.writeString(tokenizedNlu);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    float[] fArrBerte5Encode = Berte5Encode(parcel.createIntArray());
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(fArrBerte5Encode);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    Bundle bundleBertnluEncode = BertnluEncode(parcel.readString(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
                    parcel2.writeNoException();
                    if (bundleBertnluEncode != null) {
                        parcel2.writeInt(1);
                        bundleBertnluEncode.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    Bundle bundleBertnluGetScopeResultsString = BertnluGetScopeResultsString();
                    parcel2.writeNoException();
                    if (bundleBertnluGetScopeResultsString != null) {
                        parcel2.writeInt(1);
                        bundleBertnluGetScopeResultsString.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    Bundle bundleBertnluGetScopeResultsInteger = BertnluGetScopeResultsInteger();
                    parcel2.writeNoException();
                    if (bundleBertnluGetScopeResultsInteger != null) {
                        parcel2.writeInt(1);
                        bundleBertnluGetScopeResultsInteger.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iReleaseModel = releaseModel(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iReleaseModel);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int i3 = parcel.readInt();
                    float[] fArr = i3 < 0 ? null : new float[i3];
                    int extractTextFeature = getExtractTextFeature(iArrCreateIntArray, fArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(extractTextFeature);
                    parcel2.writeFloatArray(fArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBertService {
            public static IBertService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.bert.IBertService
            public int initModel(String str) throws RemoteException {
                int iInitModel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iInitModel = Stub.getDefaultImpl().initModel(str);
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

            @Override // com.xiaomi.aicr.bert.IBertService
            public int[] getTokenizedE5(String str) throws RemoteException {
                int[] iArrCreateIntArray;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iArrCreateIntArray = Stub.getDefaultImpl().getTokenizedE5(str);
                    } else {
                        parcelObtain2.readException();
                        iArrCreateIntArray = parcelObtain2.createIntArray();
                    }
                    return iArrCreateIntArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.bert.IBertService
            public String getTokenizedNlu() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getTokenizedNlu();
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

            @Override // com.xiaomi.aicr.bert.IBertService
            public float[] Berte5Encode(int[] iArr) throws RemoteException {
                float[] fArrCreateFloatArray;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        fArrCreateFloatArray = Stub.getDefaultImpl().Berte5Encode(iArr);
                    } else {
                        parcelObtain2.readException();
                        fArrCreateFloatArray = parcelObtain2.createFloatArray();
                    }
                    return fArrCreateFloatArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.bert.IBertService
            public Bundle BertnluEncode(String str, float f, float f2, float f3, float f4) throws RemoteException {
                Bundle bundleBertnluEncode;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeFloat(f3);
                    parcelObtain.writeFloat(f4);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        bundleBertnluEncode = Stub.getDefaultImpl().BertnluEncode(str, f, f2, f3, f4);
                    } else {
                        parcelObtain2.readException();
                        bundleBertnluEncode = parcelObtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return bundleBertnluEncode;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.bert.IBertService
            public Bundle BertnluGetScopeResultsString() throws RemoteException {
                Bundle bundleBertnluGetScopeResultsString;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        bundleBertnluGetScopeResultsString = Stub.getDefaultImpl().BertnluGetScopeResultsString();
                    } else {
                        parcelObtain2.readException();
                        bundleBertnluGetScopeResultsString = parcelObtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return bundleBertnluGetScopeResultsString;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.bert.IBertService
            public Bundle BertnluGetScopeResultsInteger() throws RemoteException {
                Bundle bundleBertnluGetScopeResultsInteger;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        bundleBertnluGetScopeResultsInteger = Stub.getDefaultImpl().BertnluGetScopeResultsInteger();
                    } else {
                        parcelObtain2.readException();
                        bundleBertnluGetScopeResultsInteger = parcelObtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return bundleBertnluGetScopeResultsInteger;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.bert.IBertService
            public int releaseModel(String str) throws RemoteException {
                int iReleaseModel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iReleaseModel = Stub.getDefaultImpl().releaseModel(str);
                    } else {
                        parcelObtain2.readException();
                        iReleaseModel = parcelObtain2.readInt();
                    }
                    return iReleaseModel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.bert.IBertService
            public int getExtractTextFeature(int[] iArr, float[] fArr) throws RemoteException {
                int extractTextFeature;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (fArr == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(fArr.length);
                    }
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        extractTextFeature = Stub.getDefaultImpl().getExtractTextFeature(iArr, fArr);
                    } else {
                        parcelObtain2.readException();
                        extractTextFeature = parcelObtain2.readInt();
                        parcelObtain2.readFloatArray(fArr);
                    }
                    return extractTextFeature;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBertService iBertService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iBertService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iBertService;
            return true;
        }

        public static IBertService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
