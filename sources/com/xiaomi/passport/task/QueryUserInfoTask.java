package com.xiaomi.passport.task;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.utils.UserInfoSaver;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.data.XMPassportInfo;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class QueryUserInfoTask extends AsyncTask<Void, Void, XiaomiUserCoreInfo> {
    private static final String TAG = "QueryUserInfoTask";
    private final Account mAccount;
    private final QueryUserInfoCallback mCallback;
    private final Context mContext;

    public interface QueryUserInfoCallback {
        void onResult(XiaomiUserCoreInfo xiaomiUserCoreInfo);
    }

    public QueryUserInfoTask(Context context, QueryUserInfoCallback queryUserInfoCallback) {
        this.mContext = context;
        this.mCallback = queryUserInfoCallback;
        this.mAccount = XiaomiAccountManager.get(context).getXiaomiAccount();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public XiaomiUserCoreInfo doInBackground(Void[] voidArr) {
        return getUserCoreInfo(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(XiaomiUserCoreInfo xiaomiUserCoreInfo) {
        QueryUserInfoCallback queryUserInfoCallback = this.mCallback;
        if (queryUserInfoCallback != null) {
            queryUserInfoCallback.onResult(xiaomiUserCoreInfo);
        }
    }

    public static XiaomiUserCoreInfo getUserCoreInfo(Context context) {
        Account xiaomiAccount = XiaomiAccountManager.get(context).getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLogger.log(TAG, "no Xiaomi account, skip to query user info");
            return null;
        }
        XMPassportInfo xMPassportInfoBuild = XMPassportInfo.build(context, "passportapi");
        ArrayList arrayList = new ArrayList();
        arrayList.add(XiaomiUserCoreInfo.Flag.BASE_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.BIND_ADDRESS);
        arrayList.add(XiaomiUserCoreInfo.Flag.EXTRA_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.SETTING_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.FAMILY_INFO);
        if (xMPassportInfoBuild == null) {
            AccountLogger.log(TAG, "passportInfo is null");
            return null;
        }
        for (int i = 0; i < 2; i++) {
            try {
                XiaomiUserCoreInfo xiaomiUserCoreInfo = XMPassport.getXiaomiUserCoreInfo(xMPassportInfoBuild, "passportapi", arrayList);
                if (xiaomiUserCoreInfo != null) {
                    UserInfoSaver.saveXiaomiUserCoreInfo(context, xiaomiAccount, xiaomiUserCoreInfo);
                    return xiaomiUserCoreInfo;
                }
                continue;
            } catch (AccessDeniedException e) {
                AccountLogger.log(TAG, "access denied when get user info", e);
            } catch (AuthenticationFailureException e2) {
                AccountLogger.log(TAG, "auth failure when get user info", e2);
                xMPassportInfoBuild.refreshAuthToken(context);
            } catch (CipherException e3) {
                AccountLogger.log(TAG, "CipherException when get user info", e3);
            } catch (InvalidResponseException e4) {
                AccountLogger.log(TAG, "invalid response when get user info", e4);
            } catch (IOException e5) {
                AccountLogger.log(TAG, "IOException when get user info", e5);
            }
        }
        return null;
    }
}
