package com.xiaomi.accountsdk.guestaccount;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountFuture;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountScopePolicy;
import com.xiaomi.accountsdk.guestaccount.data.GuestAccountType;
import com.xiaomi.accountsdk.guestaccount.data.ServiceArgument;
import com.xiaomi.accountsdk.utils.MiuiOsBuildReflection;
import com.xiaomi.accountsdk.utils.MiuiVersionDev;
import com.xiaomi.accountsdk.utils.MiuiVersionStable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class GuestAccountManager implements IGuestAccountManager {
    protected final Context applicationContext;
    private final GuestAccountUiHelper uiHelper = GuestAccountUiHelper.Holder.sInstance;

    protected abstract GuestAccountFuture getGuestAccountImpl(ServiceArgument serviceArgument, IGuestAccountIntentHandler iGuestAccountIntentHandler);

    protected abstract GuestAccountFuture getStoredUserIdImpl(ServiceArgument serviceArgument);

    protected abstract GuestAccountFuture renewServiceTokenImpl(ServiceArgument serviceArgument, IGuestAccountIntentHandler iGuestAccountIntentHandler);

    public static IGuestAccountManager get(Context context) {
        return get(context, GuestAccountScopePolicy.TRY_MIUI_THEN_APP, GuestAccountType.DEFAULT);
    }

    /* JADX INFO: renamed from: com.xiaomi.accountsdk.guestaccount.GuestAccountManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$guestaccount$data$GuestAccountScopePolicy;

        static {
            int[] iArr = new int[GuestAccountScopePolicy.values().length];
            $SwitchMap$com$xiaomi$accountsdk$guestaccount$data$GuestAccountScopePolicy = iArr;
            try {
                iArr[GuestAccountScopePolicy.USE_APP_GUEST_ACCOUNT_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$guestaccount$data$GuestAccountScopePolicy[GuestAccountScopePolicy.USE_MIUI_GUEST_ACCOUNT_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$guestaccount$data$GuestAccountScopePolicy[GuestAccountScopePolicy.TRY_MIUI_THEN_APP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static IGuestAccountManager get(Context context, GuestAccountScopePolicy guestAccountScopePolicy, GuestAccountType guestAccountType) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        if (guestAccountScopePolicy == null) {
            throw new IllegalArgumentException("policy == null");
        }
        if (guestAccountType == null) {
            throw new IllegalArgumentException("type == null");
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaomi$accountsdk$guestaccount$data$GuestAccountScopePolicy[guestAccountScopePolicy.ordinal()];
        if (i == 1) {
            return GuestAccountManagerImplApp.getInstance(context, guestAccountType);
        }
        if (i == 2) {
            return GuestAccountManagerImplMiui.getInstance(context);
        }
        if (i == 3) {
            if (!GuestAccountManagerImplMiui.isMiuiGuestAccountAvailable(context)) {
                return GuestAccountManagerImplApp.getInstance(context, guestAccountType);
            }
            if (MiuiOsBuildReflection.isStable(false) && MiuiVersionStable.earlyThan(new MiuiVersionStable(8, 2), false)) {
                return GuestAccountManagerImplApp.getInstance(context, guestAccountType);
            }
            if (MiuiOsBuildReflection.isDevButNotAlpha(false) && MiuiVersionDev.earlyThan(new MiuiVersionDev(6, 11, 3), false)) {
                return GuestAccountManagerImplApp.getInstance(context, guestAccountType);
            }
            return GuestAccountManagerImplMiui.getInstance(context);
        }
        throw new IllegalStateException("not here");
    }

    protected GuestAccountManager(Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public final GuestAccountFuture getStoredUserId() {
        return getStoredUserIdImpl(new ServiceArgument().setSdkVersion(GuestAccountConstants.GUEST_ACCOUNT_SDK_VERSION));
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public final GuestAccountFuture getGuestAccount(Context context, String str) {
        return getGuestAccount(context, str, str);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public final GuestAccountFuture getGuestAccount(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sid == null or empty");
        }
        return getGuestAccountImpl(new ServiceArgument().setPSid(str2).setSid(str).setSdkVersion(GuestAccountConstants.GUEST_ACCOUNT_SDK_VERSION), new GuestAccountUiHelper.GuestAccountIntentHandlerImpl(context));
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public final GuestAccountFuture renewServiceToken(Context context, String str) {
        if (str == null) {
            throw new IllegalArgumentException("sid == null");
        }
        return renewServiceTokenImpl(new ServiceArgument().setPSid(str).setSid(str).setSdkVersion(GuestAccountConstants.GUEST_ACCOUNT_SDK_VERSION), new GuestAccountUiHelper.GuestAccountIntentHandlerImpl(context));
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public final void showErrorDialog(Activity activity, GuestAccountResult guestAccountResult) {
        this.uiHelper.showErrorDialog(activity, guestAccountResult);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.IGuestAccountManager
    public final void injectStringResources(GuestAccountUiHelper.StringResourceInjector stringResourceInjector) {
        this.uiHelper.injectStringResources(stringResourceInjector);
    }
}
