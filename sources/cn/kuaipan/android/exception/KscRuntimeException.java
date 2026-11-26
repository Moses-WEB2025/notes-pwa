package cn.kuaipan.android.exception;

/* JADX INFO: loaded from: classes.dex */
public class KscRuntimeException extends RuntimeException implements IKscError {
    private static final long serialVersionUID = 4693852528580738850L;
    private final String detailMessage;
    private final int errCode;

    public KscRuntimeException(int i) {
        this(i, null, null);
    }

    public KscRuntimeException(int i, String str) {
        this(i, str, null);
    }

    public KscRuntimeException(int i, Throwable th) {
        this(i, th == null ? null : th.toString(), th);
    }

    public KscRuntimeException(int i, String str, Throwable th) {
        super("ErrCode:" + i + (str == null ? "" : " details:" + str), KscException.getSerial(th));
        this.errCode = i;
        this.detailMessage = str;
    }

    @Override // cn.kuaipan.android.exception.IKscError
    public String getSimpleMessage() {
        String str = getClass().getName() + "(ErrCode:" + this.errCode + ")";
        String str2 = this.detailMessage;
        return (str2 == null || str2.length() >= 100) ? str : str + ": " + this.detailMessage;
    }

    @Override // cn.kuaipan.android.exception.IKscError
    public int getErrorCode() {
        return this.errCode;
    }
}
