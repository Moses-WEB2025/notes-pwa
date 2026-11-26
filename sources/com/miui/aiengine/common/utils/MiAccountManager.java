package com.miui.aiengine.common.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MiAccountManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0007J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0005J\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0012\u001a\u00020\t2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\tJ\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0012\u001a\u00020\t2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005J\u0016\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/aiengine/common/utils/MiAccountManager;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "XIAOMI_ACCOUNT_TYPE", "", "getXiaomiAccount", "Landroid/accounts/Account;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "requestAccountGrant", "", "activity", "Landroid/app/Activity;", "requestCode", "", "isLogin", "", "app", "serviceId", "getAuthToken", "getEncryptUserId", "ctx", "getAuthTokenBundle", "Landroid/os/Bundle;", "invalidAuthToken", Constants.VERIFY_SUCESS_TOKEN, "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiAccountManager extends Logger {
    public static final MiAccountManager INSTANCE = new MiAccountManager();
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";

    private MiAccountManager() {
        super(NetConfig.TAG);
    }

    public final Account getXiaomiAccount(Context context) {
        Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.xiaomi");
        Intrinsics.checkNotNullExpressionValue(accountsByType, "getAccountsByType(...)");
        if (accountsByType.length == 0) {
            return null;
        }
        return accountsByType[0];
    }

    public final void requestAccountGrant(Activity activity, int requestCode) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        activity.startActivityForResult(AccountManager.newChooseAccountIntent(null, null, new String[]{"com.xiaomi"}, true, null, null, null, null), requestCode);
    }

    public final boolean isLogin(Activity app, String serviceId) {
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(serviceId, "serviceId");
        if (getXiaomiAccount(app) != null) {
            return true;
        }
        AccountManager.get(app.getApplicationContext()).addAccount("com.xiaomi", serviceId, null, null, app, null, null);
        return false;
    }

    public static /* synthetic */ String getAuthToken$default(MiAccountManager miAccountManager, Context context, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "hyperos_sec_common";
        }
        return miAccountManager.getAuthToken(context, str);
    }

    public final String getAuthToken(Context app, String serviceId) {
        AccountManagerFuture<Bundle> authToken;
        Bundle result;
        Intrinsics.checkNotNullParameter(app, "app");
        AccountManager accountManager = AccountManager.get(app);
        if (accountManager == null || (authToken = accountManager.getAuthToken(getXiaomiAccount(app), serviceId, (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null)) == null || (result = authToken.getResult()) == null) {
            return null;
        }
        return result.getString("authtoken");
    }

    public final String getEncryptUserId(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            return AccountManager.get(ctx).getUserData(getXiaomiAccount(ctx), "encrypted_user_id");
        } catch (SecurityException e) {
            e.printStackTrace();
            logError("only system app can access this data:" + e);
            return null;
        }
    }

    public static /* synthetic */ Bundle getAuthTokenBundle$default(MiAccountManager miAccountManager, Context context, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "hyperos_sec_common";
        }
        return miAccountManager.getAuthTokenBundle(context, str);
    }

    public final Bundle getAuthTokenBundle(Context app, String serviceId) {
        AccountManagerFuture<Bundle> authToken;
        Intrinsics.checkNotNullParameter(app, "app");
        AccountManager accountManager = AccountManager.get(app);
        if (accountManager == null || (authToken = accountManager.getAuthToken(getXiaomiAccount(app), serviceId, (Bundle) null, (Activity) null, (AccountManagerCallback<Bundle>) null, (Handler) null)) == null) {
            return null;
        }
        return authToken.getResult();
    }

    public final void invalidAuthToken(Context app, String token) {
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(token, "token");
        AccountManager.get(app).invalidateAuthToken("com.xiaomi", token);
    }
}
