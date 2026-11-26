package com.xiaomi.passport.interfaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.xiaomi.passport.jsb.PassportJsbWebViewPageConfig;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AuthenticatorIntentInterface {
    private static volatile AuthenticatorIntentInterface sImpl = new AuthenticatorIntentInterface() { // from class: com.xiaomi.passport.interfaces.AuthenticatorIntentInterface.1
        @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
        public Intent getAccountLoginActivityIntentImpl(Context context) {
            throw new IllegalStateException("should invoke method: set(AuthenticatorIntentInterface impl) first");
        }

        @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
        public Intent getConfirmCredentialActivityIntentImpl(Context context) {
            throw new IllegalStateException("should invoke method: set(AuthenticatorIntentInterface impl) first");
        }

        @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
        public Intent getAccountNotificationActivityIntentImpl(Context context, Parcelable parcelable, String str, String str2, Bundle bundle) {
            throw new IllegalStateException("should invoke method: set(AuthenticatorIntentInterface impl) first");
        }

        @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
        public Intent getQRCodeAuthActivityIntentImpl(Context context, String str, Parcelable parcelable) {
            throw new IllegalStateException("should invoke method: set(AuthenticatorIntentInterface impl) first");
        }

        @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
        public Intent getAccountHomActivityIntentImpl(Context context) {
            throw new IllegalStateException("should invoke method: set(AuthenticatorIntentInterface impl) first");
        }

        @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
        public Intent getJsbWebViewActivityIntentImpl(Context context, PassportJsbWebViewPageConfig passportJsbWebViewPageConfig) {
            throw new IllegalStateException("should invoke method: set(AuthenticatorIntentInterface impl) first");
        }
    };

    public abstract Intent getAccountHomActivityIntentImpl(Context context);

    public abstract Intent getAccountLoginActivityIntentImpl(Context context);

    public abstract Intent getAccountNotificationActivityIntentImpl(Context context, Parcelable parcelable, String str, String str2, Bundle bundle);

    public abstract Intent getConfirmCredentialActivityIntentImpl(Context context);

    public abstract Intent getJsbWebViewActivityIntentImpl(Context context, PassportJsbWebViewPageConfig passportJsbWebViewPageConfig);

    public abstract Intent getQRCodeAuthActivityIntentImpl(Context context, String str, Parcelable parcelable);

    public static synchronized void set(AuthenticatorIntentInterface authenticatorIntentInterface) {
        try {
            if (authenticatorIntentInterface == null) {
                throw new IllegalStateException("param impl can not be null");
            }
            sImpl = authenticatorIntentInterface;
        } catch (Throwable th) {
            throw th;
        }
    }

    public static Intent getAccountLoginActivityIntent(Context context) {
        return sImpl.getAccountLoginActivityIntentImpl(context);
    }

    public static Intent getConfirmCredentialActivityIntent(Context context) {
        return sImpl.getConfirmCredentialActivityIntentImpl(context);
    }

    public static Intent getAccountNotificationActivityIntent(Context context, Parcelable parcelable, String str, String str2, Bundle bundle) {
        return sImpl.getAccountNotificationActivityIntentImpl(context, parcelable, str, str2, bundle);
    }

    public static Intent getQRCodeAuthActivityIntent(Context context, String str, Parcelable parcelable) {
        return sImpl.getQRCodeAuthActivityIntentImpl(context, str, parcelable);
    }

    public static Intent getAccountHomActivityIntent(Context context) {
        return sImpl.getAccountHomActivityIntentImpl(context);
    }

    public static Intent getJsbWebViewActivityIntent(Context context, PassportJsbWebViewPageConfig passportJsbWebViewPageConfig) {
        return sImpl.getJsbWebViewActivityIntentImpl(context, passportJsbWebViewPageConfig);
    }
}
