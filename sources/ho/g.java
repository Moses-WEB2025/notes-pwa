package ho;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.xiaomi.dist.handoff.sdk.callback.HandoffCallback;
import java.util.Arrays;
import java.util.IllegalFormatException;

/* JADX INFO: loaded from: classes4.dex */
public interface g extends IInterface {

    public static abstract class a extends Binder implements g {
        public a() {
            attachInterface(this, "com.xiaomi.dist.handoff.IHandoffCallback");
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.dist.handoff.IHandoffCallback");
                j.a("HH", "onHandoffDone", (Object[]) null);
                HandoffCallback handoffCallback = ((c.b) this).a.get();
                if (handoffCallback != null) {
                    handoffCallback.onHandoffDone();
                }
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.xiaomi.dist.handoff.IHandoffCallback");
                return true;
            }
            parcel.enforceInterface("com.xiaomi.dist.handoff.IHandoffCallback");
            int i3 = parcel.readInt();
            String string = parcel.readString();
            c.b bVar = (c.b) this;
            Object[] objArr = {Integer.valueOf(i3), string};
            try {
                j.a("HH", String.format("onError, code=%s, msg=%s", objArr), (Throwable) null);
            } catch (IllegalFormatException unused) {
                j.a("HH", "onError, code=%s, msg=%s" + Arrays.toString(objArr), (Throwable) null);
            }
            HandoffCallback handoffCallback2 = bVar.a.get();
            if (handoffCallback2 != null) {
                handoffCallback2.onError(i3, string);
            }
            parcel2.writeNoException();
            return true;
        }
    }
}
