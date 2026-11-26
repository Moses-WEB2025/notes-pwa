package com.xiaomi.accountsdk.guestaccount;

import android.content.Context;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccount;

/* JADX INFO: loaded from: classes4.dex */
class GuestAccountStorage {
    GuestAccountStorage() {
    }

    GuestAccount getGuestAccount(Context context) {
        return getGuestAccount(context, null);
    }

    GuestAccount getGuestAccount(Context context, String str) {
        return GuestAccountDatabaseHelper.getInstance(context).readGuestAccount(str);
    }

    void savePassToken(Context context, GuestAccount guestAccount) {
        GuestAccountDatabaseHelper.getInstance(context).updatePassToken(guestAccount);
    }

    void saveServiceToken(Context context, GuestAccount guestAccount) {
        GuestAccountDatabaseHelper.getInstance(context).updateServiceToken(guestAccount);
    }

    void removeAll(Context context) {
        GuestAccountDatabaseHelper.getInstance(context).removeAll();
    }

    void removeServiceToken(Context context, String str) {
        GuestAccountDatabaseHelper.getInstance(context).removeServiceToken(str);
    }
}
