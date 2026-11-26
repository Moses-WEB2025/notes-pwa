package ho;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.text.TextUtils;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import com.xiaomi.dist.handoff.sdk.callback.DeepLinkCallback;

/* JADX INFO: loaded from: classes4.dex */
public interface h extends IInterface {

    public static abstract class a extends Binder implements h {
        public a() {
            attachInterface(this, "com.xiaomi.dist.handoff.ISessionHostCallback");
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            DeepLinkCallback deepLinkCallback;
            DeepLinkCallback deepLinkCallback2;
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.dist.handoff.ISessionHostCallback");
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.dist.handoff.ISessionHostCallback");
                HandoffSession handoffSession = ((c.f) this).a.get();
                String strA = e.a(handoffSession != null ? handoffSession.c : null);
                j.a("HH", "getDeepLink: uri.length=", Integer.valueOf(strA.length()));
                parcel2.writeNoException();
                parcel2.writeString(strA);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("com.xiaomi.dist.handoff.ISessionHostCallback");
                HandoffSession handoffSession2 = ((c.f) this).a.get();
                String strA2 = e.a(handoffSession2 != null ? handoffSession2.d : null);
                if (TextUtils.isEmpty(strA2) || (!strA2.startsWith("http://") && !strA2.startsWith("https://"))) {
                    strA2 = "";
                }
                j.a("HH", "getAppLink: uri.length=", Integer.valueOf(strA2.length()));
                parcel2.writeNoException();
                parcel2.writeString(strA2);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface("com.xiaomi.dist.handoff.ISessionHostCallback");
                boolean zA = ((c.f) this).a();
                parcel2.writeNoException();
                parcel2.writeInt(zA ? 1 : 0);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface("com.xiaomi.dist.handoff.ISessionHostCallback");
                j.a("HH", "onSaveSessionState", (Object[]) null);
                HandoffSession handoffSession3 = ((c.f) this).a.get();
                byte[] bArrOnSaveState = (handoffSession3 == null || (deepLinkCallback = handoffSession3.e) == null) ? new byte[0] : deepLinkCallback.onSaveState();
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrOnSaveState);
                return true;
            }
            if (i != 5) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface("com.xiaomi.dist.handoff.ISessionHostCallback");
            byte[] bArrCreateByteArray = parcel.createByteArray();
            j.a("HH", "onRestoreSessionState", (Object[]) null);
            HandoffSession handoffSession4 = ((c.f) this).a.get();
            if (handoffSession4 != null && (deepLinkCallback2 = handoffSession4.e) != null) {
                deepLinkCallback2.onRestoreState(bArrCreateByteArray);
            }
            parcel2.writeNoException();
            return true;
        }
    }
}
