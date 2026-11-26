package cn.kuaipan.android.exception;

import cn.kuaipan.android.KssConfig;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.kss.TransferStep;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ConnectionPendingException;
import javax.net.ssl.SSLException;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class ErrorHelper {
    public static boolean isNetworkException(Throwable th) {
        if (th == null) {
            return false;
        }
        if ((th instanceof IKscError) && th.getCause() != null) {
            th = th.getCause();
        }
        return (th instanceof SocketException) || (th instanceof ConnectTimeoutException) || (th instanceof ConnectionPendingException) || (th instanceof SocketTimeoutException) || (th instanceof ConnectionClosedException) || (th instanceof HttpRetryException) || (th instanceof NoHttpResponseException) || (th instanceof ProtocolException) || (th instanceof ClientProtocolException) || (th instanceof SSLException) || (th instanceof UnknownHostException) || (th instanceof UnknownServiceException) || (th instanceof HttpException) || (th instanceof NetworkException);
    }

    public static boolean isStopByCallerException(Throwable th) {
        return testAndGetStopByCallerException(th) != null;
    }

    public static Throwable transformIfStopByCallerException(Throwable th) {
        KscTransferStopByCallerException kscTransferStopByCallerExceptionTestAndGetStopByCallerException = testAndGetStopByCallerException(th);
        return kscTransferStopByCallerExceptionTestAndGetStopByCallerException != null ? kscTransferStopByCallerExceptionTestAndGetStopByCallerException : th;
    }

    private static KscTransferStopByCallerException testAndGetStopByCallerException(Throwable th) {
        if (th == null) {
            return null;
        }
        Throwable[] thArr = {th, th.getCause()};
        for (int i = 0; i < 2; i++) {
            Throwable th2 = thArr[i];
            if (th2 instanceof KscTransferStopByCallerException) {
                return (KscTransferStopByCallerException) th2;
            }
        }
        return null;
    }

    public static InterruptedException isInterrupted(Throwable th) {
        if (th == null) {
            return null;
        }
        if (th instanceof InterruptedException) {
            return (InterruptedException) th;
        }
        if (KssConfig.FIX_OUT_OF_THIN_AIR_INTERRUPT_EXCEPTION) {
            return null;
        }
        if (th instanceof ClosedByInterruptException) {
            return (InterruptedException) new InterruptedException().initCause(th);
        }
        if (!(th instanceof InterruptedIOException) || (th instanceof ConnectTimeoutException) || (th instanceof SocketTimeoutException)) {
            return null;
        }
        return (InterruptedException) new InterruptedException().initCause(th);
    }

    public static void handleInterruptException(Throwable th) throws InterruptedException {
        if (th == null) {
            return;
        }
        if (th instanceof InterruptedException) {
            throw ((InterruptedException) th);
        }
        if (KssConfig.FIX_OUT_OF_THIN_AIR_INTERRUPT_EXCEPTION) {
            return;
        }
        if (th instanceof ClosedByInterruptException) {
            throw ((InterruptedException) new InterruptedException().initCause(th));
        }
        if ((th instanceof InterruptedIOException) && !(th instanceof ConnectTimeoutException) && !(th instanceof SocketTimeoutException)) {
            throw ((InterruptedException) new InterruptedException().initCause(th));
        }
    }

    public static void throwError(KscHttpResponse kscHttpResponse, TransferStep transferStep) throws InterruptedException, KscException {
        Throwable error = kscHttpResponse == null ? null : kscHttpResponse.getError();
        if (error != null) {
            if (error instanceof RuntimeException) {
                throw ((RuntimeException) error);
            }
            throw KscException.newException(error, kscHttpResponse.dump(), transferStep);
        }
    }
}
