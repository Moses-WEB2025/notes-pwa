package com.xiaomi.aicr.plugin;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.aicr.vision.VisionObject;
import com.xiaomi.aicr.vision.ocr.OCRRes;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface IVisionService extends IInterface {

    public static class Default implements IVisionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IVisionService
        public int doDetect(int i, List<VisionObject> list, VisionAttribute visionAttribute) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IVisionService
        public OCRRes.Location[] doOCRDetect(int i) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IVisionService
        public OCRRes.OCRResult doOCRRecognize(int i) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IVisionService
        public String getOCRVersion() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IVisionService
        public int setImage(Bitmap bitmap) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IVisionService
        public int setImageParcelFileDesciptor(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return 0;
        }
    }

    int doDetect(int i, List<VisionObject> list, VisionAttribute visionAttribute) throws RemoteException;

    OCRRes.Location[] doOCRDetect(int i) throws RemoteException;

    OCRRes.OCRResult doOCRRecognize(int i) throws RemoteException;

    String getOCRVersion() throws RemoteException;

    int setImage(Bitmap bitmap) throws RemoteException;

    int setImageParcelFileDesciptor(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IVisionService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.plugin.IVisionService";
        static final int TRANSACTION_doDetect = 5;
        static final int TRANSACTION_doOCRDetect = 3;
        static final int TRANSACTION_doOCRRecognize = 4;
        static final int TRANSACTION_getOCRVersion = 6;
        static final int TRANSACTION_setImage = 1;
        static final int TRANSACTION_setImageParcelFileDesciptor = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVisionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVisionService)) {
                return (IVisionService) iInterfaceQueryLocalInterface;
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
                    int image = setImage(parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(image);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int imageParcelFileDesciptor = setImageParcelFileDesciptor(parcel.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(imageParcelFileDesciptor);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    OCRRes.Location[] locationArrDoOCRDetect = doOCRDetect(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(locationArrDoOCRDetect, 1);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    OCRRes.OCRResult oCRResultDoOCRRecognize = doOCRRecognize(parcel.readInt());
                    parcel2.writeNoException();
                    if (oCRResultDoOCRRecognize != null) {
                        parcel2.writeInt(1);
                        oCRResultDoOCRRecognize.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    int i3 = parcel.readInt();
                    ArrayList arrayList = new ArrayList();
                    int iDoDetect = doDetect(i3, arrayList, parcel.readInt() != 0 ? VisionAttribute.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDoDetect);
                    parcel2.writeTypedList(arrayList);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    String oCRVersion = getOCRVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(oCRVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVisionService {
            public static IVisionService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.plugin.IVisionService
            public int setImage(Bitmap bitmap) throws RemoteException {
                int image;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bitmap != null) {
                        parcelObtain.writeInt(1);
                        bitmap.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        image = Stub.getDefaultImpl().setImage(bitmap);
                    } else {
                        parcelObtain2.readException();
                        image = parcelObtain2.readInt();
                    }
                    return image;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IVisionService
            public int setImageParcelFileDesciptor(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                int imageParcelFileDesciptor;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcelObtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        imageParcelFileDesciptor = Stub.getDefaultImpl().setImageParcelFileDesciptor(parcelFileDescriptor);
                    } else {
                        parcelObtain2.readException();
                        imageParcelFileDesciptor = parcelObtain2.readInt();
                    }
                    return imageParcelFileDesciptor;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IVisionService
            public OCRRes.Location[] doOCRDetect(int i) throws RemoteException {
                OCRRes.Location[] locationArrDoOCRDetect;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        locationArrDoOCRDetect = Stub.getDefaultImpl().doOCRDetect(i);
                    } else {
                        parcelObtain2.readException();
                        locationArrDoOCRDetect = (OCRRes.Location[]) parcelObtain2.createTypedArray(OCRRes.Location.CREATOR);
                    }
                    return locationArrDoOCRDetect;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IVisionService
            public OCRRes.OCRResult doOCRRecognize(int i) throws RemoteException {
                OCRRes.OCRResult oCRResultCreateFromParcel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        oCRResultCreateFromParcel = Stub.getDefaultImpl().doOCRRecognize(i);
                    } else {
                        parcelObtain2.readException();
                        oCRResultCreateFromParcel = parcelObtain2.readInt() != 0 ? OCRRes.OCRResult.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return oCRResultCreateFromParcel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IVisionService
            public int doDetect(int i, List<VisionObject> list, VisionAttribute visionAttribute) throws RemoteException {
                int iDoDetect;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (visionAttribute != null) {
                        parcelObtain.writeInt(1);
                        visionAttribute.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iDoDetect = Stub.getDefaultImpl().doDetect(i, list, visionAttribute);
                    } else {
                        parcelObtain2.readException();
                        iDoDetect = parcelObtain2.readInt();
                        parcelObtain2.readTypedList(list, VisionObject.CREATOR);
                    }
                    return iDoDetect;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IVisionService
            public String getOCRVersion() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getOCRVersion();
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

        public static boolean setDefaultImpl(IVisionService iVisionService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iVisionService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iVisionService;
            return true;
        }

        public static IVisionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
