package cn.kuaipan.android.exception;

import android.text.TextUtils;
import cn.kuaipan.android.kss.TransferStep;

/* JADX INFO: loaded from: classes.dex */
public class ServerException extends KscException {
    private static final long serialVersionUID = 6373467541984892922L;
    private final int statusCode;

    private static int validCode(int i) {
        if (i < 100 || i > 599) {
            return 0;
        }
        return i;
    }

    public ServerException(int i, String str, TransferStep transferStep) {
        super(validCode(i) + 503000, str, transferStep);
        this.statusCode = i;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    @Override // cn.kuaipan.android.exception.KscException, cn.kuaipan.android.exception.IKscError
    public String getSimpleMessage() {
        String str = getClass().getName() + "(ErrCode: " + getErrorCode() + "): StatusCode: " + this.statusCode;
        String strSubstring = (this.detailMessage == null || this.detailMessage.length() <= 500) ? this.detailMessage : this.detailMessage.substring(0, 500);
        return !TextUtils.isEmpty(strSubstring) ? str + ", " + strSubstring : str;
    }
}
