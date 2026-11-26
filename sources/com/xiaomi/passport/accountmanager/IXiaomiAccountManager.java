package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.SecondaryDeviceInfo;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.exception.RequestSecondaryDeviceLoginDataException;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;

/* JADX INFO: loaded from: classes4.dex */
public interface IXiaomiAccountManager extends IAccountManager {

    public enum UpdateType {
        PRE_ADD,
        POST_ADD,
        POST_REFRESH,
        PRE_REMOVE,
        POST_REMOVE
    }

    boolean addAccountOrUpdatePassToken(AccountInfo accountInfo);

    void addXiaomiAccount(String str, Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    Intent getAccountHomeActivityIntent();

    Intent getAccountLoginActivityIntent(String str, Bundle bundle, Parcelable parcelable);

    Intent getAccountNotificationActivityIntent(String str, String str2, Bundle bundle, Parcelable parcelable);

    Intent getConfirmCredentialActivityIntent(Bundle bundle, Parcelable parcelable);

    String getPassToken(Account account);

    Intent getQRCodeAuthActivityIntent(String str, Parcelable parcelable);

    ServiceTokenFuture getServiceToken(Account account, String str, Bundle bundle);

    XiaomiAccountManagerFuture<XiaomiUserCoreInfo> getUserInfo(XiaomiAccountManagerCallback<XiaomiUserCoreInfo> xiaomiAccountManagerCallback, Handler handler);

    Account getXiaomiAccount();

    void handleAccountAuthenticatorResponse(Parcelable parcelable, Bundle bundle);

    boolean hasSetPassword(Account account, String str);

    ServiceTokenFuture invalidateServiceToken(ServiceTokenResult serviceTokenResult);

    boolean isSystemAccount();

    void jumpToAccountLoginAbnormalPage(Context context, String str);

    void jumpToDeviceSettingListPage(Context context);

    XiaomiAccountManagerFuture<XmAccountVisibility> makeAccountVisible(XiaomiAccountManagerCallback<XmAccountVisibility> xiaomiAccountManagerCallback, Handler handler);

    void notifyAccountLoginAbnormal(String str);

    ServiceTokenResult peekServiceToken(Account account, String str, Bundle bundle);

    ServiceTokenFuture refreshServiceToken(Account account, String str, ServiceTokenResult serviceTokenResult, Bundle bundle);

    void release();

    XiaomiAccountManagerFuture<Bundle> removeXiaomiAccount(XiaomiAccountManagerCallback<Bundle> xiaomiAccountManagerCallback, Handler handler);

    String requestSecondaryDeviceLoginData(SecondaryDeviceInfo secondaryDeviceInfo) throws RequestSecondaryDeviceLoginDataException;

    void sendAccountUpdateBroadcast(Account account, UpdateType updateType);

    void setServiceToken(Account account, String str, ServiceTokenResult serviceTokenResult);

    void updateAccountInfo(Account account, AccountInfo accountInfo);

    void updatePassToken(Account account, AccountInfo accountInfo);
}
