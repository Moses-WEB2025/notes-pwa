package com.xiaomi.passport.ui.internal.util;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.utils.BitmapUtils;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class UserInfoSaver {
    private static final String TAG = "UserInfoSaver";

    static void saveXiaomiUserCoreInfo(Context context, Account account, XiaomiUserCoreInfo xiaomiUserCoreInfo) {
        if (account == null) {
            AccountLogger.log(TAG, "no Xiaomi account, skip to save user info");
            return;
        }
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_USER_NAME, xiaomiUserCoreInfo.userName);
        xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_NICK_NAME, xiaomiUserCoreInfo.nickName);
        xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_USER_EMAIL, xiaomiUserCoreInfo.emailAddress);
        xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_USER_PHONE, xiaomiUserCoreInfo.safePhone);
        if (xiaomiUserCoreInfo.gender != null) {
            xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_USER_GENDER, xiaomiUserCoreInfo.gender.getType());
        }
        String userData = xiaomiAccountManager.getUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_AVATAR_URL);
        String str = "xiaomi_user_avatar_" + account.name;
        if (saveUserAvatarAsFile(context, xiaomiUserCoreInfo, str, userData)) {
            xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_AVATAR_URL, xiaomiUserCoreInfo.avatarAddress);
            xiaomiAccountManager.setUserData(account, com.xiaomi.accountsdk.account.data.Constants.ACCOUNT_AVATAR_FILE_NAME, str);
        }
    }

    public static boolean saveUserAvatarAsFile(Context context, XiaomiUserCoreInfo xiaomiUserCoreInfo, String str, String str2) {
        String str3 = xiaomiUserCoreInfo.avatarAddress;
        File fileStreamPath = context.getFileStreamPath(str);
        if (str3 == null) {
            return false;
        }
        if (TextUtils.equals(str3, str2) && fileStreamPath.isFile() && fileStreamPath.exists()) {
            return false;
        }
        SimpleRequest.StreamContent asStream = null;
        try {
            asStream = SimpleRequest.getAsStream(str3, null, null);
        } catch (AccessDeniedException e) {
            AccountLogger.log(TAG, "access denied when download avatar", e);
        } catch (AuthenticationFailureException e2) {
            AccountLogger.log(TAG, "auth failed when download avatar", e2);
        } catch (IOException e3) {
            AccountLogger.log(TAG, "IO error when download avatar", e3);
        }
        try {
            if (asStream == null) {
                return false;
            }
            try {
                if (BitmapUtils.saveAsFile(context, asStream.getStream(), str) != null) {
                    asStream.closeStream();
                    return true;
                }
            } catch (IOException e4) {
                AccountLogger.log(TAG, "failed to save avatar", e4);
            }
            asStream.closeStream();
            return false;
        } catch (Throwable th) {
            asStream.closeStream();
            throw th;
        }
    }
}
