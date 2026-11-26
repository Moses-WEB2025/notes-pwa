package okhttp3.internal.http2;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okhttp3.Headers;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.concurrent.Lockable;
import okhttp3.internal.http2.flowcontrol.WindowCounter;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Socket;
import okio.Source;
import okio.Timeout;

/* JADX INFO: compiled from: Http2Stream.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 b2\u00020\u00012\u00020\u0002:\u0004`abcB3\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010A\u001a\u00020\u000b2\b\b\u0002\u0010B\u001a\u00020\bJ\b\u0010C\u001a\u0004\u0018\u00010\u000bJ$\u0010D\u001a\u00020E2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020H0G2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010I\u001a\u00020\bJ\u000e\u0010J\u001a\u00020E2\u0006\u0010K\u001a\u00020\u000bJ\u0006\u0010+\u001a\u00020LJ\u0006\u0010/\u001a\u00020LJ\u0018\u0010M\u001a\u00020E2\u0006\u0010N\u001a\u0002022\b\u00107\u001a\u0004\u0018\u000108J\b\u0010O\u001a\u00020EH\u0016J\u000e\u0010P\u001a\u00020E2\u0006\u00101\u001a\u000202J\u001a\u0010Q\u001a\u00020\b2\u0006\u00101\u001a\u0002022\b\u00107\u001a\u0004\u0018\u000108H\u0002J\u0016\u0010R\u001a\u00020E2\u0006\u0010#\u001a\u00020S2\u0006\u0010T\u001a\u00020\u0004J\u0016\u0010U\u001a\u00020E2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\bJ\u000e\u0010V\u001a\u00020E2\u0006\u00101\u001a\u000202J\b\u0010W\u001a\u00020\bH\u0002J\r\u0010X\u001a\u00020EH\u0000¢\u0006\u0002\bYJ\u000e\u0010Z\u001a\u00020E2\u0006\u0010[\u001a\u00020\u0017J\r\u0010\\\u001a\u00020EH\u0000¢\u0006\u0002\b]J\r\u0010^\u001a\u00020EH\u0000¢\u0006\u0002\b_R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R$\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR$\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001a\"\u0004\b\u001f\u0010\u001cR\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010#\u001a\u00060$R\u00020\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0018\u0010'\u001a\u00060(R\u00020\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0018\u0010+\u001a\u00060,R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0018\u0010/\u001a\u00060,R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010.R\u001e\u00101\u001a\u0004\u0018\u0001028@X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001c\u00107\u001a\u0004\u0018\u000108X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u0011\u0010=\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0011\u0010?\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b?\u0010>R\u0011\u0010@\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b@\u0010>¨\u0006d"}, d2 = {"Lokhttp3/internal/http2/Http2Stream;", "Lokhttp3/internal/concurrent/Lockable;", "Lokio/Socket;", "id", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "outFinished", "", "inFinished", "headers", "Lokhttp3/Headers;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILokhttp3/internal/http2/Http2Connection;ZZLokhttp3/Headers;)V", "getId", "()I", "getConnection", "()Lokhttp3/internal/http2/Http2Connection;", "readBytes", "Lokhttp3/internal/http2/flowcontrol/WindowCounter;", "getReadBytes", "()Lokhttp3/internal/http2/flowcontrol/WindowCounter;", "value", "", "writeBytesTotal", "getWriteBytesTotal", "()J", "setWriteBytesTotal$okhttp", "(J)V", "writeBytesMaximum", "getWriteBytesMaximum", "setWriteBytesMaximum$okhttp", "headersQueue", "Ljava/util/ArrayDeque;", "hasResponseHeaders", "source", "Lokhttp3/internal/http2/Http2Stream$FramingSource;", "getSource", "()Lokhttp3/internal/http2/Http2Stream$FramingSource;", "sink", "Lokhttp3/internal/http2/Http2Stream$FramingSink;", "getSink", "()Lokhttp3/internal/http2/Http2Stream$FramingSink;", "readTimeout", "Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "getReadTimeout$okhttp", "()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "writeTimeout", "getWriteTimeout$okhttp", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "getErrorCode$okhttp", "()Lokhttp3/internal/http2/ErrorCode;", "setErrorCode$okhttp", "(Lokhttp3/internal/http2/ErrorCode;)V", "errorException", "Ljava/io/IOException;", "getErrorException$okhttp", "()Ljava/io/IOException;", "setErrorException$okhttp", "(Ljava/io/IOException;)V", "isOpen", "()Z", "isLocallyInitiated", "isSourceComplete", "takeHeaders", "callerIsIdle", "peekTrailers", "writeHeaders", "", "responseHeaders", "", "Lokhttp3/internal/http2/Header;", "flushHeaders", "enqueueTrailers", "trailers", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "rstStatusCode", ToDoStat.CANCEL, "closeLater", "closeInternal", "receiveData", "Lokio/BufferedSource;", "length", "receiveHeaders", "receiveRstStream", "doReadTimeout", "cancelStreamIfNecessary", "cancelStreamIfNecessary$okhttp", "addBytesToWriteWindow", "delta", "checkOutNotClosed", "checkOutNotClosed$okhttp", "waitForIo", "waitForIo$okhttp", "FramingSource", "FramingSink", "Companion", "StreamTimeout", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Http2Stream implements Lockable, Socket {
    public static final long EMIT_BUFFER_SIZE = 16384;
    private final Http2Connection connection;
    private ErrorCode errorCode;
    private IOException errorException;
    private boolean hasResponseHeaders;
    private final ArrayDeque<Headers> headersQueue;
    private final int id;
    private final WindowCounter readBytes;
    private final StreamTimeout readTimeout;
    private final FramingSink sink;
    private final FramingSource source;
    private long writeBytesMaximum;
    private long writeBytesTotal;
    private final StreamTimeout writeTimeout;

    public Http2Stream(int i, Http2Connection connection, boolean z, boolean z2, Headers headers) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        this.id = i;
        this.connection = connection;
        this.readBytes = new WindowCounter(i);
        this.writeBytesMaximum = connection.getPeerSettings().getInitialWindowSize();
        ArrayDeque<Headers> arrayDeque = new ArrayDeque<>();
        this.headersQueue = arrayDeque;
        this.source = new FramingSource(connection.getOkHttpSettings().getInitialWindowSize(), z2);
        this.sink = new FramingSink(z);
        this.readTimeout = new StreamTimeout();
        this.writeTimeout = new StreamTimeout();
        if (headers != null) {
            if (isLocallyInitiated()) {
                throw new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString());
            }
            arrayDeque.add(headers);
        } else if (!isLocallyInitiated()) {
            throw new IllegalStateException("remotely-initiated streams should have headers".toString());
        }
    }

    public final int getId() {
        return this.id;
    }

    public final Http2Connection getConnection() {
        return this.connection;
    }

    public final WindowCounter getReadBytes() {
        return this.readBytes;
    }

    public final long getWriteBytesTotal() {
        return this.writeBytesTotal;
    }

    public final void setWriteBytesTotal$okhttp(long j) {
        this.writeBytesTotal = j;
    }

    public final long getWriteBytesMaximum() {
        return this.writeBytesMaximum;
    }

    public final void setWriteBytesMaximum$okhttp(long j) {
        this.writeBytesMaximum = j;
    }

    @Override // okio.Socket
    public FramingSource getSource() {
        return this.source;
    }

    @Override // okio.Socket
    public FramingSink getSink() {
        return this.sink;
    }

    /* JADX INFO: renamed from: getReadTimeout$okhttp, reason: from getter */
    public final StreamTimeout getReadTimeout() {
        return this.readTimeout;
    }

    /* JADX INFO: renamed from: getWriteTimeout$okhttp, reason: from getter */
    public final StreamTimeout getWriteTimeout() {
        return this.writeTimeout;
    }

    public final void setErrorCode$okhttp(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public final ErrorCode getErrorCode$okhttp() {
        ErrorCode errorCode;
        synchronized (this) {
            errorCode = this.errorCode;
        }
        return errorCode;
    }

    /* JADX INFO: renamed from: getErrorException$okhttp, reason: from getter */
    public final IOException getErrorException() {
        return this.errorException;
    }

    public final void setErrorException$okhttp(IOException iOException) {
        this.errorException = iOException;
    }

    public final boolean isOpen() {
        synchronized (this) {
            if (getErrorCode$okhttp() != null) {
                return false;
            }
            return ((getSource().getFinished() || getSource().getClosed()) && (getSink().getFinished() || getSink().getClosed()) && this.hasResponseHeaders) ? false : true;
        }
    }

    public final boolean isLocallyInitiated() {
        return this.connection.getClient() == ((this.id & 1) == 1);
    }

    public final boolean isSourceComplete() {
        boolean z;
        synchronized (this) {
            z = getSource().getFinished() && getSource().getReadBuffer().exhausted();
        }
        return z;
    }

    public static /* synthetic */ Headers takeHeaders$default(Http2Stream http2Stream, boolean z, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            z = false;
        }
        return http2Stream.takeHeaders(z);
    }

    public final Headers takeHeaders(boolean callerIsIdle) throws IOException {
        Headers headers;
        synchronized (this) {
            while (this.headersQueue.isEmpty() && getErrorCode$okhttp() == null) {
                boolean z = callerIsIdle || doReadTimeout();
                if (z) {
                    this.readTimeout.enter();
                }
                try {
                    waitForIo$okhttp();
                    if (z) {
                        this.readTimeout.exitAndThrowIfTimedOut();
                    }
                } catch (Throwable th) {
                    if (z) {
                        this.readTimeout.exitAndThrowIfTimedOut();
                    }
                    throw th;
                }
            }
            if (!this.headersQueue.isEmpty()) {
                Headers headersRemoveFirst = this.headersQueue.removeFirst();
                Intrinsics.checkNotNullExpressionValue(headersRemoveFirst, "removeFirst(...)");
                headers = headersRemoveFirst;
            } else {
                Throwable streamResetException = this.errorException;
                if (streamResetException == null) {
                    ErrorCode errorCode$okhttp = getErrorCode$okhttp();
                    Intrinsics.checkNotNull(errorCode$okhttp);
                    streamResetException = new StreamResetException(errorCode$okhttp);
                }
                throw streamResetException;
            }
        }
        return headers;
    }

    public final Headers peekTrailers() throws IOException {
        synchronized (this) {
            if (getSource().getFinished() && getSource().getReceiveBuffer().exhausted() && getSource().getReadBuffer().exhausted()) {
                Headers trailers = getSource().getTrailers();
                if (trailers == null) {
                    trailers = Headers.EMPTY;
                }
                return trailers;
            }
            if (getErrorCode$okhttp() == null) {
                return null;
            }
            Throwable streamResetException = this.errorException;
            if (streamResetException == null) {
                ErrorCode errorCode$okhttp = getErrorCode$okhttp();
                Intrinsics.checkNotNull(errorCode$okhttp);
                streamResetException = new StreamResetException(errorCode$okhttp);
            }
            throw streamResetException;
        }
    }

    public final void writeHeaders(List<Header> responseHeaders, boolean outFinished, boolean flushHeaders) throws IOException {
        Intrinsics.checkNotNullParameter(responseHeaders, "responseHeaders");
        Http2Stream http2Stream = this;
        if (_UtilJvmKt.assertionsEnabled && Thread.holdsLock(http2Stream)) {
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }
        synchronized (http2Stream) {
            this.hasResponseHeaders = true;
            if (outFinished) {
                getSink().setFinished(true);
                Http2Stream http2Stream2 = this;
                Intrinsics.checkNotNull(http2Stream2, "null cannot be cast to non-null type java.lang.Object");
                http2Stream2.notifyAll();
            }
            Unit unit = Unit.INSTANCE;
        }
        if (!flushHeaders) {
            synchronized (http2Stream) {
                flushHeaders = this.connection.getWriteBytesTotal() >= this.connection.getWriteBytesMaximum();
                Unit unit2 = Unit.INSTANCE;
            }
        }
        this.connection.writeHeaders$okhttp(this.id, outFinished, responseHeaders);
        if (flushHeaders) {
            this.connection.flush();
        }
    }

    public final void enqueueTrailers(Headers trailers) {
        Intrinsics.checkNotNullParameter(trailers, "trailers");
        synchronized (this) {
            if (getSink().getFinished()) {
                throw new IllegalStateException("already finished".toString());
            }
            if (trailers.size() == 0) {
                throw new IllegalArgumentException("trailers.size() == 0".toString());
            }
            getSink().setTrailers(trailers);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final Timeout readTimeout() {
        return this.readTimeout;
    }

    public final Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public final void close(ErrorCode rstStatusCode, IOException errorException) throws IOException {
        Intrinsics.checkNotNullParameter(rstStatusCode, "rstStatusCode");
        if (closeInternal(rstStatusCode, errorException)) {
            this.connection.writeSynReset$okhttp(this.id, rstStatusCode);
        }
    }

    @Override // okio.Socket
    public void cancel() {
        closeLater(ErrorCode.CANCEL);
    }

    public final void closeLater(ErrorCode errorCode) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        if (closeInternal(errorCode, null)) {
            this.connection.writeSynResetLater$okhttp(this.id, errorCode);
        }
    }

    private final boolean closeInternal(ErrorCode errorCode, IOException errorException) {
        Http2Stream http2Stream = this;
        if (_UtilJvmKt.assertionsEnabled && Thread.holdsLock(http2Stream)) {
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }
        synchronized (http2Stream) {
            if (getErrorCode$okhttp() != null) {
                return false;
            }
            this.errorCode = errorCode;
            this.errorException = errorException;
            Http2Stream http2Stream2 = this;
            Intrinsics.checkNotNull(http2Stream2, "null cannot be cast to non-null type java.lang.Object");
            http2Stream2.notifyAll();
            if (getSource().getFinished() && getSink().getFinished()) {
                return false;
            }
            Unit unit = Unit.INSTANCE;
            this.connection.removeStream$okhttp(this.id);
            return true;
        }
    }

    public final void receiveData(BufferedSource source, int length) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Http2Stream http2Stream = this;
        if (!_UtilJvmKt.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
            getSource().receive$okhttp(source, length);
            return;
        }
        throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
    }

    public final void receiveHeaders(Headers headers, boolean inFinished) {
        boolean zIsOpen;
        Intrinsics.checkNotNullParameter(headers, "headers");
        Http2Stream http2Stream = this;
        if (_UtilJvmKt.assertionsEnabled && Thread.holdsLock(http2Stream)) {
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }
        synchronized (http2Stream) {
            if (!this.hasResponseHeaders || headers.get(Header.RESPONSE_STATUS_UTF8) != null || headers.get(Header.TARGET_METHOD_UTF8) != null) {
                this.hasResponseHeaders = true;
                this.headersQueue.add(headers);
            } else {
                getSource().setTrailers(headers);
            }
            if (inFinished) {
                getSource().setFinished$okhttp(true);
            }
            zIsOpen = isOpen();
            Http2Stream http2Stream2 = this;
            Intrinsics.checkNotNull(http2Stream2, "null cannot be cast to non-null type java.lang.Object");
            http2Stream2.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
        if (zIsOpen) {
            return;
        }
        this.connection.removeStream$okhttp(this.id);
    }

    public final void receiveRstStream(ErrorCode errorCode) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        synchronized (this) {
            if (getErrorCode$okhttp() == null) {
                this.errorCode = errorCode;
                Http2Stream http2Stream = this;
                Intrinsics.checkNotNull(http2Stream, "null cannot be cast to non-null type java.lang.Object");
                http2Stream.notifyAll();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean doReadTimeout() {
        return !this.connection.getClient() || getSink().getClosed() || getSink().getFinished();
    }

    /* JADX INFO: compiled from: Http2Stream.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0019\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u0003H\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u0003H\u0002J\u001d\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001d\u001a\u00020\u0003H\u0000¢\u0006\u0002\b#J\b\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020\u001fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\t\"\u0004\b\u001a\u0010\u000b¨\u0006'"}, d2 = {"Lokhttp3/internal/http2/Http2Stream$FramingSource;", "Lokio/Source;", "maxByteCount", "", ToDoStat.FINISHED, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http2/Http2Stream;JZ)V", "getFinished$okhttp", "()Z", "setFinished$okhttp", "(Z)V", "receiveBuffer", "Lokio/Buffer;", "getReceiveBuffer", "()Lokio/Buffer;", "readBuffer", "getReadBuffer", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "closed", "getClosed$okhttp", "setClosed$okhttp", "read", "sink", "byteCount", "updateConnectionFlowControl", "", "receive", "source", "Lokio/BufferedSource;", "receive$okhttp", "timeout", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class FramingSource implements Source {
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private Headers trailers;
        private final Buffer receiveBuffer = new Buffer();
        private final Buffer readBuffer = new Buffer();

        public FramingSource(long j, boolean z) {
            this.maxByteCount = j;
            this.finished = z;
        }

        /* JADX INFO: renamed from: getFinished$okhttp, reason: from getter */
        public final boolean getFinished() {
            return this.finished;
        }

        public final void setFinished$okhttp(boolean z) {
            this.finished = z;
        }

        public final Buffer getReceiveBuffer() {
            return this.receiveBuffer;
        }

        public final Buffer getReadBuffer() {
            return this.readBuffer;
        }

        public final Headers getTrailers() {
            return this.trailers;
        }

        public final void setTrailers(Headers headers) {
            this.trailers = headers;
        }

        /* JADX INFO: renamed from: getClosed$okhttp, reason: from getter */
        public final boolean getClosed() {
            return this.closed;
        }

        public final void setClosed$okhttp(boolean z) {
            this.closed = z;
        }

        @Override // okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            StreamResetException errorException;
            boolean z;
            long j;
            Intrinsics.checkNotNullParameter(sink, "sink");
            long j2 = 0;
            if (byteCount < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            }
            while (true) {
                Http2Stream http2Stream = Http2Stream.this;
                synchronized (http2Stream) {
                    boolean zDoReadTimeout = http2Stream.doReadTimeout();
                    if (zDoReadTimeout) {
                        http2Stream.getReadTimeout().enter();
                    }
                    try {
                        if (http2Stream.getErrorCode$okhttp() == null || this.finished) {
                            errorException = null;
                        } else {
                            errorException = http2Stream.getErrorException();
                            if (errorException == null) {
                                ErrorCode errorCode$okhttp = http2Stream.getErrorCode$okhttp();
                                Intrinsics.checkNotNull(errorCode$okhttp);
                                errorException = new StreamResetException(errorCode$okhttp);
                            }
                        }
                        if (this.closed) {
                            throw new IOException("stream closed");
                        }
                        z = false;
                        if (this.readBuffer.size() > j2) {
                            Buffer buffer = this.readBuffer;
                            j = buffer.read(sink, Math.min(byteCount, buffer.size()));
                            WindowCounter.update$default(http2Stream.getReadBytes(), j, 0L, 2, null);
                            long unacknowledged = http2Stream.getReadBytes().getUnacknowledged();
                            if (errorException == null && unacknowledged >= http2Stream.getConnection().getOkHttpSettings().getInitialWindowSize() / 2) {
                                http2Stream.getConnection().writeWindowUpdateLater$okhttp(http2Stream.getId(), unacknowledged);
                                WindowCounter.update$default(http2Stream.getReadBytes(), 0L, unacknowledged, 1, null);
                            }
                        } else {
                            if (!this.finished && errorException == null) {
                                http2Stream.waitForIo$okhttp();
                                z = true;
                            }
                            j = -1;
                        }
                        if (zDoReadTimeout) {
                            http2Stream.getReadTimeout().exitAndThrowIfTimedOut();
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        if (zDoReadTimeout) {
                            http2Stream.getReadTimeout().exitAndThrowIfTimedOut();
                        }
                        throw th;
                    }
                }
                Http2Stream.this.getConnection().getFlowControlListener().receivingStreamWindowChanged(Http2Stream.this.getId(), Http2Stream.this.getReadBytes(), this.readBuffer.size());
                if (!z) {
                    if (j != -1) {
                        return j;
                    }
                    if (errorException == null) {
                        return -1L;
                    }
                    throw errorException;
                }
                j2 = 0;
            }
        }

        private final void updateConnectionFlowControl(long read) {
            Http2Stream http2Stream = Http2Stream.this;
            if (!_UtilJvmKt.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                Http2Stream.this.getConnection().updateConnectionFlowControl$okhttp(read);
                return;
            }
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }

        public final void receive$okhttp(BufferedSource source, long byteCount) throws IOException {
            boolean z;
            boolean z2;
            boolean z3;
            Intrinsics.checkNotNullParameter(source, "source");
            Http2Stream http2Stream = Http2Stream.this;
            if (!_UtilJvmKt.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                long j = byteCount;
                while (j > 0) {
                    synchronized (Http2Stream.this) {
                        z = this.finished;
                        z2 = true;
                        z3 = this.readBuffer.size() + j > this.maxByteCount;
                        Unit unit = Unit.INSTANCE;
                    }
                    if (z3) {
                        source.skip(j);
                        Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                        return;
                    }
                    if (z) {
                        source.skip(j);
                        return;
                    }
                    long j2 = source.read(this.receiveBuffer, j);
                    if (j2 == -1) {
                        throw new EOFException();
                    }
                    j -= j2;
                    Http2Stream http2Stream2 = Http2Stream.this;
                    synchronized (http2Stream2) {
                        if (this.closed) {
                            this.receiveBuffer.clear();
                        } else {
                            if (this.readBuffer.size() != 0) {
                                z2 = false;
                            }
                            this.readBuffer.writeAll(this.receiveBuffer);
                            if (z2) {
                                Http2Stream http2Stream3 = http2Stream2;
                                Intrinsics.checkNotNull(http2Stream3, "null cannot be cast to non-null type java.lang.Object");
                                http2Stream3.notifyAll();
                            }
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                updateConnectionFlowControl(byteCount);
                Http2Stream.this.getConnection().getFlowControlListener().receivingStreamWindowChanged(Http2Stream.this.getId(), Http2Stream.this.getReadBytes(), this.readBuffer.size());
                return;
            }
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }

        @Override // okio.Source
        /* JADX INFO: renamed from: timeout */
        public Timeout getTimeout() {
            return Http2Stream.this.getReadTimeout();
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            long size;
            Http2Stream http2Stream = Http2Stream.this;
            synchronized (http2Stream) {
                this.closed = true;
                size = this.readBuffer.size();
                this.readBuffer.clear();
                Http2Stream http2Stream2 = http2Stream;
                Intrinsics.checkNotNull(http2Stream2, "null cannot be cast to non-null type java.lang.Object");
                http2Stream2.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            if (size > 0) {
                updateConnectionFlowControl(size);
            }
            Http2Stream.this.cancelStreamIfNecessary$okhttp();
        }
    }

    public final void cancelStreamIfNecessary$okhttp() throws IOException {
        boolean z;
        boolean zIsOpen;
        Http2Stream http2Stream = this;
        if (_UtilJvmKt.assertionsEnabled && Thread.holdsLock(http2Stream)) {
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }
        synchronized (http2Stream) {
            z = !getSource().getFinished() && getSource().getClosed() && (getSink().getFinished() || getSink().getClosed());
            zIsOpen = isOpen();
            Unit unit = Unit.INSTANCE;
        }
        if (z) {
            close(ErrorCode.CANCEL, null);
        } else {
            if (zIsOpen) {
                return;
            }
            this.connection.removeStream$okhttp(this.id);
        }
    }

    /* JADX INFO: compiled from: Http2Stream.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0003H\u0002J\b\u0010\u001c\u001a\u00020\u0016H\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0016H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0007\"\u0004\b\u0014\u0010\t¨\u0006 "}, d2 = {"Lokhttp3/internal/http2/Http2Stream$FramingSink;", "Lokio/Sink;", ToDoStat.FINISHED, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http2/Http2Stream;Z)V", "getFinished", "()Z", "setFinished", "(Z)V", "sendBuffer", "Lokio/Buffer;", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "closed", "getClosed", "setClosed", "write", "", "source", "byteCount", "", "emitFrame", "outFinishedOnLastFrame", "flush", "timeout", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class FramingSink implements Sink {
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer;
        private Headers trailers;

        public FramingSink(boolean z) {
            this.finished = z;
            this.sendBuffer = new Buffer();
        }

        public /* synthetic */ FramingSink(Http2Stream http2Stream, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z);
        }

        public final boolean getFinished() {
            return this.finished;
        }

        public final void setFinished(boolean z) {
            this.finished = z;
        }

        public final Headers getTrailers() {
            return this.trailers;
        }

        public final void setTrailers(Headers headers) {
            this.trailers = headers;
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final void setClosed(boolean z) {
            this.closed = z;
        }

        @Override // okio.Sink
        public void write(Buffer source, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            Http2Stream http2Stream = Http2Stream.this;
            if (!_UtilJvmKt.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                this.sendBuffer.write(source, byteCount);
                while (this.sendBuffer.size() >= 16384) {
                    emitFrame(false);
                }
                return;
            }
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }

        private final void emitFrame(boolean outFinishedOnLastFrame) throws IOException {
            long jMin;
            boolean z;
            Http2Stream http2Stream = Http2Stream.this;
            synchronized (http2Stream) {
                http2Stream.getWriteTimeout().enter();
                while (http2Stream.getWriteBytesTotal() >= http2Stream.getWriteBytesMaximum() && !this.finished && !this.closed && http2Stream.getErrorCode$okhttp() == null) {
                    try {
                        http2Stream.waitForIo$okhttp();
                    } catch (Throwable th) {
                        http2Stream.getWriteTimeout().exitAndThrowIfTimedOut();
                        throw th;
                    }
                }
                http2Stream.getWriteTimeout().exitAndThrowIfTimedOut();
                http2Stream.checkOutNotClosed$okhttp();
                jMin = Math.min(http2Stream.getWriteBytesMaximum() - http2Stream.getWriteBytesTotal(), this.sendBuffer.size());
                http2Stream.setWriteBytesTotal$okhttp(http2Stream.getWriteBytesTotal() + jMin);
                z = outFinishedOnLastFrame && jMin == this.sendBuffer.size();
                Unit unit = Unit.INSTANCE;
            }
            Http2Stream.this.getWriteTimeout().enter();
            try {
                Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), z, this.sendBuffer, jMin);
            } finally {
                Http2Stream.this.getWriteTimeout().exitAndThrowIfTimedOut();
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            Http2Stream http2Stream = Http2Stream.this;
            if (!_UtilJvmKt.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                Http2Stream http2Stream2 = Http2Stream.this;
                synchronized (http2Stream2) {
                    http2Stream2.checkOutNotClosed$okhttp();
                    Unit unit = Unit.INSTANCE;
                }
                while (this.sendBuffer.size() > 0) {
                    emitFrame(false);
                    Http2Stream.this.getConnection().flush();
                }
                return;
            }
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }

        @Override // okio.Sink
        /* JADX INFO: renamed from: timeout */
        public Timeout getTimeout() {
            return Http2Stream.this.getWriteTimeout();
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            Http2Stream http2Stream = Http2Stream.this;
            if (!_UtilJvmKt.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                Http2Stream http2Stream2 = Http2Stream.this;
                synchronized (http2Stream2) {
                    if (this.closed) {
                        return;
                    }
                    boolean z = http2Stream2.getErrorCode$okhttp() == null;
                    Unit unit = Unit.INSTANCE;
                    if (!Http2Stream.this.getSink().finished) {
                        boolean z2 = this.sendBuffer.size() > 0;
                        if (this.trailers != null) {
                            while (this.sendBuffer.size() > 0) {
                                emitFrame(false);
                            }
                            Http2Connection connection = Http2Stream.this.getConnection();
                            int id = Http2Stream.this.getId();
                            Headers headers = this.trailers;
                            Intrinsics.checkNotNull(headers);
                            connection.writeHeaders$okhttp(id, z, _UtilJvmKt.toHeaderList(headers));
                        } else if (z2) {
                            while (this.sendBuffer.size() > 0) {
                                emitFrame(true);
                            }
                        } else if (z) {
                            Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), true, null, 0L);
                        }
                    }
                    Http2Stream http2Stream3 = Http2Stream.this;
                    synchronized (http2Stream3) {
                        this.closed = true;
                        Http2Stream http2Stream4 = http2Stream3;
                        Intrinsics.checkNotNull(http2Stream4, "null cannot be cast to non-null type java.lang.Object");
                        http2Stream4.notifyAll();
                        Unit unit2 = Unit.INSTANCE;
                    }
                    Http2Stream.this.getConnection().flush();
                    Http2Stream.this.cancelStreamIfNecessary$okhttp();
                    return;
                }
            }
            throw new AssertionError("Thread " + Thread.currentThread().getName() + " MUST NOT hold lock on " + http2Stream);
        }
    }

    public final void addBytesToWriteWindow(long delta) {
        this.writeBytesMaximum += delta;
        if (delta > 0) {
            Http2Stream http2Stream = this;
            Intrinsics.checkNotNull(http2Stream, "null cannot be cast to non-null type java.lang.Object");
            http2Stream.notifyAll();
        }
    }

    public final void checkOutNotClosed$okhttp() throws Throwable {
        if (getSink().getClosed()) {
            throw new IOException("stream closed");
        }
        if (getSink().getFinished()) {
            throw new IOException("stream finished");
        }
        if (getErrorCode$okhttp() != null) {
            Throwable streamResetException = this.errorException;
            if (streamResetException == null) {
                ErrorCode errorCode$okhttp = getErrorCode$okhttp();
                Intrinsics.checkNotNull(errorCode$okhttp);
                streamResetException = new StreamResetException(errorCode$okhttp);
            }
            throw streamResetException;
        }
    }

    public final void waitForIo$okhttp() throws InterruptedIOException {
        try {
            Http2Stream http2Stream = this;
            Intrinsics.checkNotNull(http2Stream, "null cannot be cast to non-null type java.lang.Object");
            http2Stream.wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    /* JADX INFO: compiled from: Http2Stream.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0014J\u0006\u0010\t\u001a\u00020\u0005¨\u0006\n"}, d2 = {"Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "Lokio/AsyncTimeout;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/http2/Http2Stream;)V", "timedOut", "", "newTimeoutException", "Ljava/io/IOException;", "cause", "exitAndThrowIfTimedOut", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class StreamTimeout extends AsyncTimeout {
        public StreamTimeout() {
        }

        @Override // okio.AsyncTimeout
        protected void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
            Http2Stream.this.getConnection().sendDegradedPingLater$okhttp();
        }

        @Override // okio.AsyncTimeout
        protected IOException newTimeoutException(IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }

        public final void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }
    }
}
