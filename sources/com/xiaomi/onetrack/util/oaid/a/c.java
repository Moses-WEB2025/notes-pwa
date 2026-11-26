package com.xiaomi.onetrack.util.oaid.a;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface c extends IInterface {
    void a(a.AbstractBinderC0124a abstractBinderC0124a);

    public static final class b implements c {
        private IBinder a;

        public b(IBinder iBinder) {
            this.a = iBinder;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.a;
        }

        @Override // com.xiaomi.onetrack.util.oaid.a.c
        public void a(a.AbstractBinderC0124a abstractBinderC0124a) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                parcelObtain.writeStrongBinder(abstractBinderC0124a);
                this.a.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain2.recycle();
                parcelObtain.recycle();
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                th.printStackTrace();
            }
        }
    }

    public interface a extends IInterface {
        void a(int i, long j, boolean z, float f, double d, String str);

        void a(int i, Bundle bundle);

        /* JADX INFO: renamed from: com.xiaomi.onetrack.util.oaid.a.c$a$a, reason: collision with other inner class name */
        public static abstract class AbstractBinderC0124a extends Binder implements a {
            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            public AbstractBinderC0124a() {
                attachInterface(this, "com.hihonor.cloudservice.oaid.IOAIDCallBack");
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                if (i == 1) {
                    parcel.enforceInterface("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                    a(parcel.readInt(), parcel.readLong(), parcel.readInt() != 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                if (i != 2) {
                    if (i != 1598968902) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    parcel2.writeString("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                    return true;
                }
                parcel.enforceInterface("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                a(parcel.readInt(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
        }
    }
}
