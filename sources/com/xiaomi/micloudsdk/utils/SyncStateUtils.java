package com.xiaomi.micloudsdk.utils;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncAdapterType;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SyncStateUtils {
    public static boolean isAllSyncOff(Account account, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (ContentResolver.getSyncAutomatically(account, it.next())) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getAllAvailableAuthorities(Context context, Account account) {
        ArrayList arrayList = new ArrayList();
        if (context != null && account != null) {
            for (SyncAdapterType syncAdapterType : ContentResolver.getSyncAdapterTypes()) {
                if (syncAdapterType.isUserVisible() && TextUtils.equals(account.type, syncAdapterType.accountType)) {
                    String str = syncAdapterType.authority;
                    if (context.getPackageManager().resolveContentProvider(str, 128) != null) {
                        arrayList.add(str);
                    }
                }
            }
        }
        return arrayList;
    }
}
