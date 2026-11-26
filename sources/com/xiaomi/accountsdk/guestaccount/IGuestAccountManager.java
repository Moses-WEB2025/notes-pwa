package com.xiaomi.accountsdk.guestaccount;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountFuture;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;

/* JADX INFO: loaded from: classes4.dex */
public interface IGuestAccountManager {
    GuestAccountFuture getGuestAccount(Context context, String str);

    GuestAccountFuture getGuestAccount(Context context, String str, String str2);

    GuestAccountFuture getStoredUserId();

    void injectBackupUtil(GuestAccountBackupUtil guestAccountBackupUtil);

    void injectHardwareInfoFetcher(GuestAccountHardwareInfoFetcher guestAccountHardwareInfoFetcher);

    void injectHttpRequester(GuestAccountHttpRequester guestAccountHttpRequester);

    void injectStringResources(GuestAccountUiHelper.StringResourceInjector stringResourceInjector);

    void onXiaomiAccountCTAAllowed();

    GuestAccountFuture renewServiceToken(Context context, String str);

    void restoreFromBackup();

    void saveToBackup();

    void showErrorDialog(Activity activity, GuestAccountResult guestAccountResult);
}
