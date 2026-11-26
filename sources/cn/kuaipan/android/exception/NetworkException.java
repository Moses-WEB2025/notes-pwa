package cn.kuaipan.android.exception;

import android.text.TextUtils;
import cn.kuaipan.android.kss.TransferStep;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class NetworkException extends KscException {
    private static final long serialVersionUID = 3410936099313815279L;
    private final String origMessage;

    public NetworkException(int i, String str, Throwable th, TransferStep transferStep) {
        super(i, str, th, transferStep);
        this.origMessage = th == null ? null : th.getMessage();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (TextUtils.isEmpty(this.origMessage)) {
            return super.getMessage();
        }
        return this.origMessage + StringUtils.LF + super.getMessage();
    }

    @Override // cn.kuaipan.android.exception.KscException, cn.kuaipan.android.exception.IKscError
    public String getSimpleMessage() {
        String str = getClass().getName() + "(ErrCode: " + getErrorCode() + ")";
        Throwable cause = getCause();
        if (cause != null) {
            String str2 = str + " - [" + cause.getClass().getName();
            if (this.origMessage != null) {
                str2 = str2 + ": " + this.origMessage;
            }
            str = str2 + "]";
        }
        return (this.detailMessage == null || this.detailMessage.length() >= 100) ? str : str + ": " + this.detailMessage;
    }
}
