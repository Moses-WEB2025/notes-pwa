package okio.internal;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okio.Buffer;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Socket;
import okio.Source;

/* JADX INFO: compiled from: DefaultSocket.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001:\u0002\u0016\u0017B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0018"}, d2 = {"Lokio/internal/DefaultSocket;", "Lokio/Socket;", "socket", "Ljava/net/Socket;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/net/Socket;)V", "getSocket", "()Ljava/net/Socket;", "closeBits", "Ljava/util/concurrent/atomic/AtomicInteger;", "source", "Lokio/Source;", "getSource", "()Lokio/Source;", "sink", "Lokio/Sink;", "getSink", "()Lokio/Sink;", ToDoStat.CANCEL, "", "toString", "", "SocketSink", "SocketSource", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DefaultSocket implements Socket {
    private AtomicInteger closeBits;
    private final Sink sink;
    private final java.net.Socket socket;
    private final Source source;

    public DefaultSocket(java.net.Socket socket) {
        Intrinsics.checkNotNullParameter(socket, "socket");
        this.socket = socket;
        this.closeBits = new AtomicInteger();
        this.source = new SocketSource();
        this.sink = new SocketSink();
    }

    public final java.net.Socket getSocket() {
        return this.socket;
    }

    @Override // okio.Socket
    public Source getSource() {
        return this.source;
    }

    @Override // okio.Socket
    public Sink getSink() {
        return this.sink;
    }

    @Override // okio.Socket
    public void cancel() throws IOException {
        this.socket.close();
    }

    public String toString() {
        String string = this.socket.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: compiled from: DefaultSocket.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u0007H\u0016J\b\u0010\r\u001a\u00020\u0007H\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokio/internal/DefaultSocket$SocketSink;", "Lokio/Sink;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/internal/DefaultSocket;)V", "timeout", "Lokio/internal/SocketAsyncTimeout;", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "flush", SplitLayout.TAG_CLOSE, "toString", "", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public final class SocketSink implements Sink {
        private final SocketAsyncTimeout timeout;

        public SocketSink() {
            this.timeout = new SocketAsyncTimeout(DefaultSocket.this.getSocket());
        }

        @Override // okio.Sink
        public void write(Buffer source, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            SegmentedByteString.checkOffsetAndCount(source.size(), 0L, byteCount);
            while (byteCount > 0) {
                this.timeout.throwIfReached();
                Segment segment = source.head;
                Intrinsics.checkNotNull(segment);
                int iMin = (int) Math.min(byteCount, segment.limit - segment.pos);
                SocketAsyncTimeout socketAsyncTimeout = this.timeout;
                DefaultSocket defaultSocket = DefaultSocket.this;
                socketAsyncTimeout.enter();
                try {
                    try {
                        defaultSocket.getSocket().getOutputStream().write(segment.data, segment.pos, iMin);
                        Unit unit = Unit.INSTANCE;
                        if (!socketAsyncTimeout.exit()) {
                            segment.pos += iMin;
                            long j = iMin;
                            byteCount -= j;
                            source.setSize$okio(source.size() - j);
                            if (segment.pos == segment.limit) {
                                source.head = segment.pop();
                                SegmentPool.recycle(segment);
                            }
                        } else {
                            throw socketAsyncTimeout.access$newTimeoutException(null);
                        }
                    } catch (IOException e) {
                        IOException iOExceptionAccess$newTimeoutException = e;
                        if (socketAsyncTimeout.exit()) {
                            iOExceptionAccess$newTimeoutException = socketAsyncTimeout.access$newTimeoutException(iOExceptionAccess$newTimeoutException);
                        }
                        throw iOExceptionAccess$newTimeoutException;
                    }
                } catch (Throwable th) {
                    socketAsyncTimeout.exit();
                    throw th;
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            SocketAsyncTimeout socketAsyncTimeout = this.timeout;
            DefaultSocket defaultSocket = DefaultSocket.this;
            socketAsyncTimeout.enter();
            try {
                try {
                    defaultSocket.getSocket().getOutputStream().flush();
                    Unit unit = Unit.INSTANCE;
                    if (socketAsyncTimeout.exit()) {
                        throw socketAsyncTimeout.access$newTimeoutException(null);
                    }
                } catch (IOException e) {
                    e = e;
                    if (socketAsyncTimeout.exit()) {
                        e = socketAsyncTimeout.access$newTimeoutException(e);
                    }
                    throw e;
                }
            } catch (Throwable th) {
                socketAsyncTimeout.exit();
                throw th;
            }
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            SocketAsyncTimeout socketAsyncTimeout = this.timeout;
            DefaultSocket defaultSocket = DefaultSocket.this;
            socketAsyncTimeout.enter();
            try {
                try {
                    int bitsOrZero = _AtomicKt.setBitsOrZero(defaultSocket.closeBits, 1);
                    if (bitsOrZero == 0) {
                        socketAsyncTimeout.exit();
                        return;
                    }
                    if (bitsOrZero != 3) {
                        if (!defaultSocket.getSocket().isClosed() && !defaultSocket.getSocket().isOutputShutdown()) {
                            defaultSocket.getSocket().getOutputStream().flush();
                            defaultSocket.getSocket().shutdownOutput();
                        }
                        socketAsyncTimeout.exit();
                        return;
                    }
                    defaultSocket.getSocket().close();
                    Unit unit = Unit.INSTANCE;
                    if (socketAsyncTimeout.exit()) {
                        throw socketAsyncTimeout.access$newTimeoutException(null);
                    }
                    return;
                } catch (IOException e) {
                    e = e;
                    if (socketAsyncTimeout.exit()) {
                        e = socketAsyncTimeout.access$newTimeoutException(e);
                    }
                    throw e;
                }
            } catch (Throwable th) {
                socketAsyncTimeout.exit();
                throw th;
            }
            socketAsyncTimeout.exit();
            throw th;
        }

        @Override // okio.Sink
        /* JADX INFO: renamed from: timeout, reason: from getter */
        public SocketAsyncTimeout getTimeout() {
            return this.timeout;
        }

        public String toString() {
            return "sink(" + DefaultSocket.this.getSocket() + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }
    }

    /* JADX INFO: compiled from: DefaultSocket.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lokio/internal/DefaultSocket$SocketSource;", "Lokio/Source;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/internal/DefaultSocket;)V", "timeout", "Lokio/internal/SocketAsyncTimeout;", "read", "", "sink", "Lokio/Buffer;", "byteCount", SplitLayout.TAG_CLOSE, "", "toString", "", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public final class SocketSource implements Source {
        private final SocketAsyncTimeout timeout;

        public SocketSource() {
            this.timeout = new SocketAsyncTimeout(DefaultSocket.this.getSocket());
        }

        @Override // okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (byteCount == 0) {
                return 0L;
            }
            if (byteCount < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
            }
            this.timeout.throwIfReached();
            Segment segmentWritableSegment$okio = sink.writableSegment$okio(1);
            int iMin = (int) Math.min(byteCount, 8192 - segmentWritableSegment$okio.limit);
            try {
                SocketAsyncTimeout socketAsyncTimeout = this.timeout;
                DefaultSocket defaultSocket = DefaultSocket.this;
                socketAsyncTimeout.enter();
                try {
                    try {
                        int i = defaultSocket.getSocket().getInputStream().read(segmentWritableSegment$okio.data, segmentWritableSegment$okio.limit, iMin);
                        if (socketAsyncTimeout.exit()) {
                            throw socketAsyncTimeout.access$newTimeoutException(null);
                        }
                        if (i == -1) {
                            if (segmentWritableSegment$okio.pos != segmentWritableSegment$okio.limit) {
                                return -1L;
                            }
                            sink.head = segmentWritableSegment$okio.pop();
                            SegmentPool.recycle(segmentWritableSegment$okio);
                            return -1L;
                        }
                        segmentWritableSegment$okio.limit += i;
                        long j = i;
                        sink.setSize$okio(sink.size() + j);
                        return j;
                    } catch (IOException e) {
                        e = e;
                        if (socketAsyncTimeout.exit()) {
                            e = socketAsyncTimeout.access$newTimeoutException(e);
                        }
                        throw e;
                    }
                } catch (Throwable th) {
                    socketAsyncTimeout.exit();
                    throw th;
                }
            } catch (AssertionError e2) {
                if (_JavaIoKt.isAndroidGetsocknameError(e2)) {
                    throw new IOException(e2);
                }
                throw e2;
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            SocketAsyncTimeout socketAsyncTimeout = this.timeout;
            DefaultSocket defaultSocket = DefaultSocket.this;
            socketAsyncTimeout.enter();
            try {
                try {
                    int bitsOrZero = _AtomicKt.setBitsOrZero(defaultSocket.closeBits, 2);
                    if (bitsOrZero == 0) {
                        socketAsyncTimeout.exit();
                        return;
                    }
                    if (bitsOrZero != 3) {
                        if (!defaultSocket.getSocket().isClosed() && !defaultSocket.getSocket().isInputShutdown()) {
                            defaultSocket.getSocket().shutdownInput();
                        }
                        socketAsyncTimeout.exit();
                        return;
                    }
                    defaultSocket.getSocket().close();
                    Unit unit = Unit.INSTANCE;
                    if (socketAsyncTimeout.exit()) {
                        throw socketAsyncTimeout.access$newTimeoutException(null);
                    }
                } catch (IOException e) {
                    e = e;
                    if (socketAsyncTimeout.exit()) {
                        e = socketAsyncTimeout.access$newTimeoutException(e);
                    }
                    throw e;
                }
            } catch (Throwable th) {
                socketAsyncTimeout.exit();
                throw th;
            }
        }

        @Override // okio.Source
        /* JADX INFO: renamed from: timeout, reason: from getter */
        public SocketAsyncTimeout getTimeout() {
            return this.timeout;
        }

        public String toString() {
            return "source(" + DefaultSocket.this.getSocket() + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }
    }
}
