package micloud.compat.independent.request;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes5.dex */
public class RequestInjectorCompactInternal implements IRequestInjectorCompat {
    @Override // micloud.compat.independent.request.IRequestInjectorCompat
    public void sendDataInTransferBroadcast(Context context, int i) {
        Intent intent = new Intent("com.xiaomi.action.DATA_IN_TRANSFER");
        intent.setPackage("com.miui.cloudservice");
        intent.putExtra("retryTime", i);
        context.sendBroadcast(intent);
    }
}
