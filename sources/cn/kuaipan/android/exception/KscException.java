package cn.kuaipan.android.exception;

import cn.kuaipan.android.kss.TransferStep;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.stat.MiCloudE2EEEventStatInjector;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

/* JADX INFO: loaded from: classes.dex */
public class KscException extends Exception implements IKscError {
    private static final long serialVersionUID = 7461260166746901326L;
    protected final String detailMessage;
    private final int errCode;
    private final TransferStep transferStep;

    public KscException(int i, TransferStep transferStep) {
        this(i, null, null, transferStep);
    }

    public KscException(int i, String str, TransferStep transferStep) {
        this(i, str, null, transferStep);
    }

    public KscException(int i, Throwable th, TransferStep transferStep) {
        this(i, th == null ? null : th.toString(), th, transferStep);
    }

    public KscException(int i, String str, Throwable th, TransferStep transferStep) {
        super("ErrCode: " + i + (str == null ? "" : StringUtils.LF + str) + (transferStep != null ? StringUtils.LF + transferStep : ""), getSerial(th));
        this.errCode = i;
        this.detailMessage = str;
        this.transferStep = transferStep;
    }

    @Override // cn.kuaipan.android.exception.IKscError
    public int getErrorCode() {
        return this.errCode;
    }

    @Override // cn.kuaipan.android.exception.IKscError
    public String getSimpleMessage() {
        String str = getClass().getName() + "(ErrCode: " + this.errCode + ")";
        String str2 = this.detailMessage;
        return (str2 == null || str2.length() >= 100) ? str : str + ": " + this.detailMessage;
    }

    public static KscException newException(Throwable th, String str, TransferStep transferStep) throws InterruptedException {
        Throwable thTransformIfStopByCallerException = ErrorHelper.transformIfStopByCallerException(th);
        if (thTransformIfStopByCallerException instanceof KscException) {
            return (KscException) thTransformIfStopByCallerException;
        }
        boolean z = thTransformIfStopByCallerException instanceof E2EEException;
        if (z) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent((E2EEException) thTransformIfStopByCallerException);
        }
        ErrorHelper.handleInterruptException(thTransformIfStopByCallerException);
        if (thTransformIfStopByCallerException instanceof ConnectException) {
            return new NetworkException(ErrorCode.NET_ECONNREFUSED, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof SocketException) {
            return new NetworkException(504000, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof SocketTimeoutException) {
            return new NetworkException(ErrorCode.NET_SOCKET_TIMEOUT, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof ConnectTimeoutException) {
            return new NetworkException(ErrorCode.NET_SOCKET_ETIMEDOUT, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof ClientProtocolException) {
            return new NetworkException(ErrorCode.NET_ERROR_HTTP_PROTOCOL, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof UnknownHostException) {
            return new NetworkException(ErrorCode.NET_ERROR_UNKNOW_HOST, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof InvalidKeyException) {
            return new KscException(ErrorCode.INVALID_DATA, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof FileNotFoundException) {
            return new KscException(ErrorCode.IOERR_MISS_FILE, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof IOException) {
            return new KscException(403000, str, thTransformIfStopByCallerException, transferStep);
        }
        if (z) {
            return new KscException(ErrorCode.E2EE_ERROR, str, thTransformIfStopByCallerException, transferStep);
        }
        if (thTransformIfStopByCallerException instanceof RuntimeException) {
            throw ((RuntimeException) thTransformIfStopByCallerException);
        }
        return new KscException(403999, str, thTransformIfStopByCallerException, transferStep);
    }

    static Throwable getSerial(Throwable th) {
        return (th != null && (th instanceof HttpHostConnectException)) ? new HttpHostConnectExceptionWrapper((HttpHostConnectException) th) : th;
    }

    public TransferStep getTransferStep() {
        return this.transferStep;
    }
}
