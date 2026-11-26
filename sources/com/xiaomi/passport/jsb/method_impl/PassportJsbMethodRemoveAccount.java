package com.xiaomi.passport.jsb.method_impl;

import android.content.Context;
import com.xiaomi.accountsdk.account.action.AccountLogout;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.jsb.PassportFrontendMethodInvoker;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodRemoveAccount extends PassportJsbMethod {
    private BgTask<Boolean> mRemoveAccountTask;

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(final PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context applicationContext = passportJsbWebView.getContext().getApplicationContext();
        if (XiaomiAccountManager.get(applicationContext).getXiaomiAccount() == null) {
            throw new PassportJsbMethodException(105, "no account");
        }
        final String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "callbackId");
        BgTask<Boolean> bgTask = new BgTask<>(new RemoveAccountBgRunnable(applicationContext), new BgTask.SuccessResultRunnable<Boolean>() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodRemoveAccount.1
            @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
            public void run(Boolean bool) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("result", bool);
                    PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, paramsStringFieldOrThrow, jSONObject2);
                } catch (JSONException e) {
                    throw new IllegalStateException(e);
                }
            }
        }, null);
        this.mRemoveAccountTask = bgTask;
        bgTask.execute();
        return new PassportJsbMethodResult(true);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "removeAccount";
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public void release(PassportJsbWebView passportJsbWebView) {
        BgTask<Boolean> bgTask = this.mRemoveAccountTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mRemoveAccountTask = null;
        }
    }

    private static class RemoveAccountBgRunnable implements BgTask.BgTaskRunnable<Boolean> {
        public final Context appContext;

        public RemoveAccountBgRunnable(Context context) {
            this.appContext = context;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public Boolean run() {
            return Boolean.valueOf(AccountLogout.syncRemoveInAppAccountDirectly(this.appContext));
        }
    }
}
