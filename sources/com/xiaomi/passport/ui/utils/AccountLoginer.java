package com.xiaomi.passport.ui.utils;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.passport.PassportUserEnvironment;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.AuthCredential;
import com.xiaomi.passport.ui.internal.AuthProvider;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberManagerFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class AccountLoginer {
    private static final ExecutorService workerExecutor = Executors.newFixedThreadPool(3);
    private final Context appContext;
    private SimpleFutureTask<AccountInfo> runningTask;

    public interface LoginFailedHandler {
        void onLoginFailed(Throwable th);
    }

    public interface LoginSuccessHandler {
        void onLoginSuccess(AccountInfo accountInfo);
    }

    public AccountLoginer(Activity activity) {
        this.appContext = activity.getApplicationContext();
    }

    public void login(AuthProvider authProvider, AuthCredential authCredential, final LoginSuccessHandler loginSuccessHandler, final LoginFailedHandler loginFailedHandler) {
        authProvider.signInAndStoreAccount(this.appContext, authCredential, new BgTask.SuccessResultRunnable<AccountInfo>() { // from class: com.xiaomi.passport.ui.utils.AccountLoginer.1
            @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
            public void run(AccountInfo accountInfo) {
                if (XiaomiAccountManager.get(AccountLoginer.this.appContext).addAccountOrUpdatePassToken(accountInfo)) {
                    loginSuccessHandler.onLoginSuccess(accountInfo);
                } else {
                    loginFailedHandler.onLoginFailed(new IllegalStateException("addOrUpdateAccountManager failed"));
                }
            }
        }, new BgTask.ErrorResultRunnable() { // from class: com.xiaomi.passport.ui.utils.AccountLoginer.2
            @Override // com.xiaomi.passport.task.BgTask.ErrorResultRunnable
            public void run(Throwable th) {
                loginFailedHandler.onLoginFailed(th);
            }
        });
    }

    public void login(final String str, final PhoneAccount phoneAccount, final LoginSuccessHandler loginSuccessHandler, final LoginFailedHandler loginFailedHandler) {
        if (this.runningTask != null) {
            throw new IllegalStateException("exists running task");
        }
        final PhoneTicketLoginParams phoneTicketLoginParamsBuild = new PhoneTicketLoginParams.Builder().serviceId(str).verifiedActivatorPhone(new ActivatorPhoneInfo.Builder().phoneHash(phoneAccount.accountCertification.hashedPhoneNumber).activatorToken(phoneAccount.accountCertification.activatorToken).build()).build();
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.ui.utils.AccountLoginer.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                PhoneTicketLoginParams phoneTicketLoginParamsBuild2;
                if (phoneTicketLoginParamsBuild.hashedEnvFactors == null) {
                    phoneTicketLoginParamsBuild2 = PhoneTicketLoginParams.copyFrom(phoneTicketLoginParamsBuild).hashedEnvFactors(PassportUserEnvironment.Holder.getInstance().getEnvInfoArray(XMPassportSettings.getApplicationContext())).build();
                } else {
                    phoneTicketLoginParamsBuild2 = phoneTicketLoginParamsBuild;
                }
                try {
                    return XMPassport.loginByPhone(phoneTicketLoginParamsBuild2);
                } finally {
                    AccountPhoneNumberManagerFactory.createProperManager(AccountLoginer.this.appContext).invalidateAccountCertification(AccountLoginer.this.appContext, str, phoneAccount.accountCertification);
                }
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.ui.utils.AccountLoginer.3
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    AccountInfo accountInfo = simpleFutureTask2.get();
                    if (XiaomiAccountManager.get(AccountLoginer.this.appContext).addAccountOrUpdatePassToken(accountInfo)) {
                        loginSuccessHandler.onLoginSuccess(accountInfo);
                    } else {
                        loginFailedHandler.onLoginFailed(new IllegalStateException("addOrUpdateAccountManager failed"));
                    }
                } catch (InterruptedException e) {
                    loginFailedHandler.onLoginFailed(e);
                } catch (CancellationException e2) {
                    loginFailedHandler.onLoginFailed(e2);
                } catch (ExecutionException e3) {
                    loginFailedHandler.onLoginFailed(e3.getCause());
                } finally {
                    AccountLoginer.this.onTaskCompleted();
                }
            }
        });
        this.runningTask = simpleFutureTask;
        workerExecutor.submit(simpleFutureTask);
    }

    public void registerAndLogin(final String str, final PhoneAccount phoneAccount, final LoginSuccessHandler loginSuccessHandler, final LoginFailedHandler loginFailedHandler) {
        if (this.runningTask != null) {
            throw new IllegalStateException("exists running task");
        }
        final PhoneTokenRegisterParams phoneTokenRegisterParamsBuild = new PhoneTokenRegisterParams.Builder().serviceId(str).phoneHashActivatorToken(new ActivatorPhoneInfo.Builder().phoneHash(phoneAccount.accountCertification.hashedPhoneNumber).activatorToken(phoneAccount.accountCertification.activatorToken).build()).build();
        SimpleFutureTask<AccountInfo> simpleFutureTask = new SimpleFutureTask<>(new Callable<AccountInfo>() { // from class: com.xiaomi.passport.ui.utils.AccountLoginer.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public AccountInfo call() throws Exception {
                try {
                    return XMPassport.regByPhoneWithToken(phoneTokenRegisterParamsBuild);
                } finally {
                    AccountPhoneNumberManagerFactory.createProperManager(AccountLoginer.this.appContext).invalidateAccountCertification(AccountLoginer.this.appContext, str, phoneAccount.accountCertification);
                }
            }
        }, new SimpleFutureTask.Callback<AccountInfo>() { // from class: com.xiaomi.passport.ui.utils.AccountLoginer.5
            @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
            public void call(SimpleFutureTask<AccountInfo> simpleFutureTask2) {
                try {
                    AccountInfo accountInfo = simpleFutureTask2.get();
                    if (XiaomiAccountManager.get(AccountLoginer.this.appContext).addAccountOrUpdatePassToken(accountInfo)) {
                        loginSuccessHandler.onLoginSuccess(accountInfo);
                    } else {
                        loginFailedHandler.onLoginFailed(new IllegalStateException("addOrUpdateAccountManager failed"));
                    }
                } catch (InterruptedException e) {
                    loginFailedHandler.onLoginFailed(e);
                } catch (CancellationException e2) {
                    loginFailedHandler.onLoginFailed(e2);
                } catch (ExecutionException e3) {
                    loginFailedHandler.onLoginFailed(e3.getCause());
                } finally {
                    AccountLoginer.this.onTaskCompleted();
                }
            }
        });
        this.runningTask = simpleFutureTask;
        workerExecutor.submit(simpleFutureTask);
    }

    public void cancel() {
        SimpleFutureTask<AccountInfo> simpleFutureTask = this.runningTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.runningTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTaskCompleted() {
        this.runningTask = null;
    }
}
