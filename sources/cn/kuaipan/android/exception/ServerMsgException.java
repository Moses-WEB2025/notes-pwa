package cn.kuaipan.android.exception;

import android.text.TextUtils;
import cn.kuaipan.android.kss.TransferStep;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class ServerMsgException extends KscException {
    private static final long serialVersionUID = -681123175263669159L;
    private final String origMessage;
    private final int statusCode;

    public ServerMsgException(int i, String str, TransferStep transferStep) {
        this(i, str, (String) null, transferStep);
    }

    public ServerMsgException(int i, String str, String str2, TransferStep transferStep) {
        super(ServerMsgMap.getErrorCode(i, str), str2, transferStep);
        this.statusCode = i;
        this.origMessage = str;
    }

    public ServerMsgException(int i, int i2, String str, TransferStep transferStep) {
        super(i2, str, transferStep);
        this.statusCode = i;
        this.origMessage = "Message not come from api server.";
    }

    @Override // cn.kuaipan.android.exception.KscException, cn.kuaipan.android.exception.IKscError
    public String getSimpleMessage() {
        String str = getClass().getName() + "(ErrCode: " + getErrorCode() + "): StatusCode: " + this.statusCode;
        if (this.origMessage != null) {
            str = str + ", msg: " + this.origMessage;
        }
        String strSubstring = (this.detailMessage == null || this.detailMessage.length() <= 500) ? this.detailMessage : this.detailMessage.substring(0, 500);
        return !TextUtils.isEmpty(strSubstring) ? str + ", " + strSubstring : str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (TextUtils.isEmpty(this.origMessage)) {
            return super.getMessage();
        }
        return this.origMessage + StringUtils.LF + super.getMessage();
    }

    public String getOrigMessage() {
        return this.origMessage;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
