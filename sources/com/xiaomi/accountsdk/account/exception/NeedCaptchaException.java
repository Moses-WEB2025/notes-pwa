package com.xiaomi.accountsdk.account.exception;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.ServerErrorCode;

/* JADX INFO: loaded from: classes4.dex */
public class NeedCaptchaException extends AccountException {
    public static final String TYPE_MAN_MACHINE = "manMachine";
    public static final String TYPE_PICTURE_CAPTCHA = "captcha";
    public static final String TYPE_PICTURE_CAPTCHA_VIEW = "captchaView";
    private static final long serialVersionUID = 1;
    private final String mCaptchaType;
    private final String mCaptchaUrl;

    public NeedCaptchaException(String str, String str2) {
        this(ServerErrorCode.ERROR_WRONG_CAPTCHA, "Need captcha code or wrong captcha code", str, str2);
    }

    public NeedCaptchaException(int i, String str, String str2) {
        this(i, str, str2, TYPE_MAN_MACHINE);
    }

    public NeedCaptchaException(int i, String str, String str2, String str3) {
        super(i, str);
        this.mCaptchaUrl = str2;
        this.mCaptchaType = TextUtils.isEmpty(str3) ? TYPE_MAN_MACHINE : str3;
    }

    public String getCaptchaUrl() {
        return this.mCaptchaUrl;
    }

    public String getCaptchaType() {
        return this.mCaptchaType;
    }
}
