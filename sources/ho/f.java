package ho;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes4.dex */
public interface f extends IInterface {

    public static abstract class a extends Binder implements f {
        public static final /* synthetic */ int a = 0;

        /* JADX INFO: renamed from: ho.f$a$a, reason: collision with other inner class name */
        public static class C0138a implements f {
            public IBinder a;

            public C0138a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // ho.f
            public final int a(d dVar, h hVar, g gVar, int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.dist.handoff.IAppHandoffService");
                    parcelObtain.writeInt(1);
                    dVar.writeToParcel(parcelObtain, 0);
                    parcelObtain.writeStrongBinder(hVar != null ? (h.a) hVar : null);
                    parcelObtain.writeStrongBinder(gVar != null ? (g.a) gVar : null);
                    parcelObtain.writeInt(i);
                    if (!this.a.transact(1, parcelObtain, parcelObtain2, 0)) {
                        int i2 = a.a;
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // ho.f
            public final void a(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.dist.handoff.IAppHandoffService");
                    parcelObtain.writeInt(i);
                    if (!this.a.transact(3, parcelObtain, parcelObtain2, 0)) {
                        int i2 = a.a;
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // ho.f
            public final void a(int i, int i2, int i3) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.dist.handoff.IAppHandoffService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (!this.a.transact(2, parcelObtain, parcelObtain2, 0)) {
                        int i4 = a.a;
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // ho.f
            public final void a(boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.dist.handoff.IAppHandoffService");
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.a.transact(4, parcelObtain, parcelObtain2, 0)) {
                        int i = a.a;
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.a;
            }
        }
    }

    int a(d dVar, h hVar, g gVar, int i);

    void a(int i);

    void a(int i, int i2, int i3);

    void a(boolean z);
}
