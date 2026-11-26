package com.miui.handwriting.common.view.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface CheckStatus {
    public static final int FINISH_SELECT = -10000;
    public static final int SERVICE_UNKNOWN_ERROR = -9999;
    public static final int USER_NETWORK_ERROR = -1;
    public static final int USER_NOT_AGREE = -2;
    public static final int USER_NOT_LOGIN = -3;
    public static final int USER_STATUS_OK = 0;
}
