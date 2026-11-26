package com.xiaomi.onetrack.util.oaid.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface d extends IInterface {
    String a();

    String a(String str);

    boolean b();

    public static abstract class a extends Binder implements d {

        /* JADX INFO: renamed from: com.xiaomi.onetrack.util.oaid.a.d$a$a, reason: collision with other inner class name */
        public static class C0125a implements d {
            private IBinder a;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return null;
            }

            public C0125a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.d
            public String a() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.d
            public String a(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.d
            public boolean b() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                boolean z = false;
                try {
                    parcelObtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    z = parcelObtain2.readInt() != 0;
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return z;
                } catch (Throwable unused) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return z;
                }
            }
        }

        @Override // android.os.Binder
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                String strA = a();
                parcel2.writeNoException();
                parcel2.writeString(strA);
                return true;
            }
            if (i == 1598968902) {
                parcel2.writeString("com.zui.deviceidservice.IDeviceidInterface");
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                boolean zB = b();
                parcel2.writeNoException();
                parcel2.writeInt(zB ? 1 : 0);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                String strA2 = a(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strA2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public static d a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.zui.deviceidservice.IDeviceidInterface");
            if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof d)) {
                return new C0125a(iBinder);
            }
            return (d) iInterfaceQueryLocalInterface;
        }
    }
}
