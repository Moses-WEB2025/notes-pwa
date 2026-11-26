package com.market.pm.api;

import miuix.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public interface IMarketInstallerContract extends IMarketInstallerCode {
    public static final String ACTION = "com.xiaomi.market.action.INSTALL";
    public static final String EXTRA_APP_CLIENT_ID = "extra_app_client_id";
    public static final String EXTRA_APP_SIGNATURE = "extra_app_signature";
    public static final String EXTRA_CALLER_PACKAGE_NAME = "extra_caller_package_name";
    public static final String EXTRA_NONCE = "extra_nonce";
    public static final String EXTRA_REF = "extra_ref";
    public static final String MARKET_PACKAGE;

    static {
        MARKET_PACKAGE = Build.IS_INTERNATIONAL_BUILD ? "com.xiaomi.mipicks" : "com.xiaomi.market";
    }
}
