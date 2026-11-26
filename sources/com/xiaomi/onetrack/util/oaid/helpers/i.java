package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.xiaomi.onetrack.util.r;

/* JADX INFO: loaded from: classes4.dex */
public class i {
    private static final String a = "NubiaDeviceIDHelper";

    public String a(Context context) {
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = context.getContentResolver().acquireContentProviderClient(Uri.parse("content://cn.nubia.identity/identity"));
            Bundle bundleCall = contentProviderClientAcquireContentProviderClient.call("getOAID", null, null);
            if (contentProviderClientAcquireContentProviderClient != null) {
                contentProviderClientAcquireContentProviderClient.close();
            }
            if (bundleCall.getInt("code", -1) != 0) {
                return "";
            }
            return bundleCall.getString("id");
        } catch (Exception e) {
            r.a(a, e.getMessage());
            return "";
        }
    }
}
