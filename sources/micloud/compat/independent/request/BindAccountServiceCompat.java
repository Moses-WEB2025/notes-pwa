package micloud.compat.independent.request;

import android.content.Context;
import android.content.ServiceConnection;

/* JADX INFO: loaded from: classes5.dex */
public class BindAccountServiceCompat {
    private static IBindAccountServiceCompat sBindAccountServiceImpl = new BindAccountServiceCompatImpl();

    private BindAccountServiceCompat() {
    }

    public static void setBindAccountServiceImpl(IBindAccountServiceCompat iBindAccountServiceCompat) {
        sBindAccountServiceImpl = iBindAccountServiceCompat;
    }

    public static boolean bindAccountService(Context context, ServiceConnection serviceConnection) {
        return sBindAccountServiceImpl.bindAccountService(context, serviceConnection);
    }
}
