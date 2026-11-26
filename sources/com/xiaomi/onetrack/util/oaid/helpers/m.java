package com.xiaomi.onetrack.util.oaid.helpers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.xiaomi.onetrack.util.r;

/* JADX INFO: loaded from: classes4.dex */
public class m {
    private static final String a = "VivoDeviceIDHelper";

    public String a(Context context) {
        String string = "";
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), null, null, null, null);
            if (cursorQuery != null) {
                if (cursorQuery.moveToNext()) {
                    string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                }
                cursorQuery.close();
            }
            return string;
        } catch (Exception e) {
            r.a(a, e.getMessage());
            return string;
        }
    }
}
