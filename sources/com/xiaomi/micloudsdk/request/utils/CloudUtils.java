package com.xiaomi.micloudsdk.request.utils;

import android.accounts.Account;
import android.content.Context;
import android.webkit.MimeTypeMap;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.utils.FileMimeUtils;
import miui.accounts.ExtraAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class CloudUtils {
    public static final int CODE_IS_INTERNATIONAL_ACCOUNT = 1;
    public static final int CODE_NOT_INTERNATIONAL_ACCOUNT = 0;
    public static final int CODE_UNKOWN_ERROR = 2;

    public static String getUserAgent() {
        return Request.getUserAgent();
    }

    public static int isInternationalAccount(boolean z) {
        return CloudRelocationUtils.isInternationalAccount(z);
    }

    public static String getRelocatedHost(String str, boolean z) throws CloudServerException {
        return CloudRelocationUtils.getRelocatedHost(str, z);
    }

    public static String updateRequestHost(String str, boolean z) throws CloudServerException {
        return CloudRelocationUtils.updateRequestHost(str, z);
    }

    public static String getMimeType(String str) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileMimeUtils.getFileExt(str));
        if (mimeTypeFromExtension == null) {
            mimeTypeFromExtension = FileMimeUtils.getMimeType(str);
        }
        return mimeTypeFromExtension != null ? mimeTypeFromExtension : "*/*";
    }

    public static Account getXiaomiAccount() {
        return getXiaomiAccount(Request.getContext());
    }

    public static Account getXiaomiAccount(Context context) {
        return ExtraAccountManager.getXiaomiAccount(context);
    }
}
