package com.xiaomi.account.authenticator;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes4.dex */
public class AccountChangedBroadcastHelper {
    public static final String EXTRA_ACCOUNT = "extra_account";
    public static final String EXTRA_UPDATE_TYPE = "extra_update_type";
    private static final String SYSTEM_LOGIN_ACCOUNTS_POST_CHANGED_ACTION = "android.accounts.LOGIN_ACCOUNTS_POST_CHANGED";
    private static final String SYSTEM_LOGIN_ACCOUNTS_PRE_CHANGED_ACTION = "android.accounts.LOGIN_ACCOUNTS_PRE_CHANGED";
    public static final int TYPE_ADD = 2;
    public static final int TYPE_REFRESH = 3;
    public static final int TYPE_REMOVE = 1;

    public enum UpdateType {
        PRE_ADD,
        POST_ADD,
        POST_REFRESH,
        PRE_REMOVE,
        POST_REMOVE
    }

    public static String getAccountsPreChangedAction(Context context) {
        return SYSTEM_LOGIN_ACCOUNTS_PRE_CHANGED_ACTION;
    }

    public static String getAccountsPostChangedAction(Context context) {
        return SYSTEM_LOGIN_ACCOUNTS_POST_CHANGED_ACTION;
    }

    /* JADX INFO: renamed from: com.xiaomi.account.authenticator.AccountChangedBroadcastHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType;

        static {
            int[] iArr = new int[UpdateType.values().length];
            $SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType = iArr;
            try {
                iArr[UpdateType.PRE_ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType[UpdateType.POST_ADD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType[UpdateType.POST_REFRESH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType[UpdateType.PRE_REMOVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType[UpdateType.POST_REMOVE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static void sendBroadcast(Context context, Account account, UpdateType updateType) {
        String accountsPreChangedAction;
        if (context == null || updateType == null) {
            throw new IllegalArgumentException();
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaomi$account$authenticator$AccountChangedBroadcastHelper$UpdateType[updateType.ordinal()];
        int i2 = 2;
        if (i == 1) {
            accountsPreChangedAction = getAccountsPreChangedAction(context);
        } else if (i != 2) {
            i2 = 3;
            if (i != 3) {
                if (i == 4) {
                    accountsPreChangedAction = getAccountsPreChangedAction(context);
                } else if (i == 5) {
                    accountsPreChangedAction = getAccountsPostChangedAction(context);
                } else {
                    throw new IllegalStateException("this should not be happen");
                }
                i2 = 1;
            } else {
                accountsPreChangedAction = getAccountsPostChangedAction(context);
            }
        } else {
            accountsPreChangedAction = getAccountsPostChangedAction(context);
        }
        Intent intent = new Intent(accountsPreChangedAction);
        intent.putExtra("extra_account", account);
        intent.putExtra("extra_update_type", i2);
        intent.addFlags(16777216);
        context.sendBroadcast(intent);
    }
}
