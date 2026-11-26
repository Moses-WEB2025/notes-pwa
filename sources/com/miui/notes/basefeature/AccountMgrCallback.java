package com.miui.notes.basefeature;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.ai.account.ExtraAccountManager;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PermissionNewUtils;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AccountMgrCallback.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0016B!\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006¢\u0006\u0004\b\b\u0010\tJ\u0016\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\"\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0017"}, d2 = {"Lcom/miui/notes/basefeature/AccountMgrCallback;", "Landroid/accounts/AccountManagerCallback;", "Landroid/os/Bundle;", "acceptListener", "Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;", "toAddAccountLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;Landroidx/activity/result/ActivityResultLauncher;)V", "getAcceptListener", "()Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;", "setAcceptListener", "(Lcom/miui/notes/tool/PermissionNewUtils$AcceptListener;)V", "getToAddAccountLauncher", "()Landroidx/activity/result/ActivityResultLauncher;", "setToAddAccountLauncher", "(Landroidx/activity/result/ActivityResultLauncher;)V", "run", "", "future", "Landroid/accounts/AccountManagerFuture;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AccountMgrCallback implements AccountManagerCallback<Bundle> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private PermissionNewUtils.AcceptListener acceptListener;
    private ActivityResultLauncher<Intent> toAddAccountLauncher;

    @JvmStatic
    public static final boolean checkoutIsLoginXiaomiAccount() {
        return INSTANCE.checkoutIsLoginXiaomiAccount();
    }

    public AccountMgrCallback(PermissionNewUtils.AcceptListener acceptListener, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.acceptListener = acceptListener;
        this.toAddAccountLauncher = activityResultLauncher;
    }

    public final PermissionNewUtils.AcceptListener getAcceptListener() {
        return this.acceptListener;
    }

    public final void setAcceptListener(PermissionNewUtils.AcceptListener acceptListener) {
        this.acceptListener = acceptListener;
    }

    public final ActivityResultLauncher<Intent> getToAddAccountLauncher() {
        return this.toAddAccountLauncher;
    }

    public final void setToAddAccountLauncher(ActivityResultLauncher<Intent> activityResultLauncher) {
        this.toAddAccountLauncher = activityResultLauncher;
    }

    @Override // android.accounts.AccountManagerCallback
    public void run(AccountManagerFuture<Bundle> future) {
        Intrinsics.checkNotNullParameter(future, "future");
        try {
            Bundle result = future.getResult();
            Logger.INSTANCE.d("AccountMgrCallback", "on result:" + result + ", intent ");
            Intent intent = (Intent) result.getParcelable("intent");
            if (intent != null) {
                if (this.toAddAccountLauncher != null) {
                    Logger.INSTANCE.d("AccountMgrCallback", "launch login  intent ");
                    ActivityResultLauncher<Intent> activityResultLauncher = this.toAddAccountLauncher;
                    Intrinsics.checkNotNull(activityResultLauncher);
                    activityResultLauncher.launch(intent);
                    return;
                }
                return;
            }
            Logger.INSTANCE.d("AccountMgrCallback", "intent null ");
            if (INSTANCE.checkoutIsLoginXiaomiAccount()) {
                PermissionNewUtils.AcceptListener acceptListener = this.acceptListener;
                if (acceptListener != null) {
                    Intrinsics.checkNotNull(acceptListener);
                    acceptListener.accept();
                    return;
                }
                return;
            }
            Logger.INSTANCE.d("AccountMgrCallback", "not login Account ");
        } catch (AuthenticatorException e) {
            com.miui.common.tool.Logger.INSTANCE.e("AccountMgrCallback", "err " + e);
            PermissionNewUtils.AcceptListener acceptListener2 = this.acceptListener;
            if (acceptListener2 != null) {
                Intrinsics.checkNotNull(acceptListener2);
                acceptListener2.reject();
            }
        } catch (OperationCanceledException e2) {
            com.miui.common.tool.Logger.INSTANCE.e("AccountMgrCallback", "err " + e2);
            PermissionNewUtils.AcceptListener acceptListener3 = this.acceptListener;
            if (acceptListener3 != null) {
                Intrinsics.checkNotNull(acceptListener3);
                acceptListener3.reject();
            }
        } catch (IOException e3) {
            com.miui.common.tool.Logger.INSTANCE.e("AccountMgrCallback", "err " + e3);
            PermissionNewUtils.AcceptListener acceptListener4 = this.acceptListener;
            if (acceptListener4 != null) {
                Intrinsics.checkNotNull(acceptListener4);
                acceptListener4.reject();
            }
        }
    }

    /* JADX INFO: compiled from: AccountMgrCallback.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"Lcom/miui/notes/basefeature/AccountMgrCallback$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "checkoutIsLoginXiaomiAccount", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final boolean checkoutIsLoginXiaomiAccount() {
            return ExtraAccountManager.getXiaomiAccount(BaseNoteApp.INSTANCE.getInstance()) != null;
        }
    }
}
