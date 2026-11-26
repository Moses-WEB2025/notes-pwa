package com.xiaomi.phonenum.bean;

import com.xiaomi.phonenum.http.Response;
import defpackage.R2;

/* JADX INFO: loaded from: classes4.dex */
public enum HttpError {
    ENCRYPT(R2.attr.badgeTextColor),
    DECRYPT(R2.attr.badgeVerticalPadding),
    DATA_NOT_ENABLED(R2.attr.badgeWidePadding),
    NO_CHANGE_NETWORK_STATE_PERMISSION(R2.attr.badgeWidth),
    CELLULAR_NETWORK_NOT_AVAILABLE(R2.attr.badgeWithTextHeight),
    CELLULAR_NETWORK_IO_EXCEPTION(600);

    public final int code;

    HttpError(int i) {
        this.code = i;
    }

    public Response result() {
        return new Response.Builder().code(this.code).body(toString()).build();
    }
}
