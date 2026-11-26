package com.xiaomi.aicr;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: classes4.dex */
public interface IGlobalCollectCatcherInterface extends IInterface {

    public static class Default implements IGlobalCollectCatcherInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.IGlobalCollectCatcherInterface
        public Map<String, Bitmap> detectImages() throws RemoteException {
            return null;
        }
    }

    Map<String, Bitmap> detectImages() throws RemoteException;

    public static abstract class Stub extends Binder implements IGlobalCollectCatcherInterface {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.IGlobalCollectCatcherInterface";
        static final int TRANSACTION_detectImages = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGlobalCollectCatcherInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGlobalCollectCatcherInterface)) {
                return (IGlobalCollectCatcherInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            Map<String, Bitmap> mapDetectImages = detectImages();
            parcel2.writeNoException();
            if (mapDetectImages == null) {
                parcel2.writeInt(-1);
            } else {
                parcel2.writeInt(mapDetectImages.size());
                mapDetectImages.forEach(new BiConsumer() { // from class: com.xiaomi.aicr.IGlobalCollectCatcherInterface$Stub$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        IGlobalCollectCatcherInterface.Stub.lambda$onTransact$0(parcel2, (String) obj, (Bitmap) obj2);
                    }
                });
            }
            return true;
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, Bitmap bitmap) {
            parcel.writeString(str);
            if (bitmap != null) {
                parcel.writeInt(1);
                bitmap.writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IGlobalCollectCatcherInterface {
            public static IGlobalCollectCatcherInterface sDefaultImpl;
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

            @Override // com.xiaomi.aicr.IGlobalCollectCatcherInterface
            public Map<String, Bitmap> detectImages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detectImages();
                    }
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: com.xiaomi.aicr.IGlobalCollectCatcherInterface$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null);
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IGlobalCollectCatcherInterface iGlobalCollectCatcherInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iGlobalCollectCatcherInterface == null) {
                return false;
            }
            Proxy.sDefaultImpl = iGlobalCollectCatcherInterface;
            return true;
        }

        public static IGlobalCollectCatcherInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
