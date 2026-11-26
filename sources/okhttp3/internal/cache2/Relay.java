package okhttp3.internal.cache2;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal.concurrent.Lockable;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* JADX INFO: compiled from: Relay.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u0000 <2\u00020\u0001:\u0002;<B5\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007¢\u0006\u0004\b\u000b\u0010\fJ \u00103\u001a\u0002042\u0006\u00105\u001a\u00020\t2\u0006\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u0007H\u0002J\u0010\u00108\u001a\u0002042\u0006\u00106\u001a\u00020\u0007H\u0002J\u000e\u00109\u001a\u0002042\u0006\u00106\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\tJ\b\u0010:\u001a\u0004\u0018\u00010\u0005R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\b+\u0010#R\u001a\u0010,\u001a\u00020-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u0011\u00102\u001a\u00020%8F¢\u0006\u0006\u001a\u0004\b2\u0010'¨\u0006="}, d2 = {"Lokhttp3/internal/cache2/Relay;", "Lokhttp3/internal/concurrent/Lockable;", "file", "Ljava/io/RandomAccessFile;", "upstream", "Lokio/Source;", "upstreamPos", "", "metadata", "Lokio/ByteString;", "bufferMaxSize", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/io/RandomAccessFile;Lokio/Source;JLokio/ByteString;J)V", "getFile", "()Ljava/io/RandomAccessFile;", "setFile", "(Ljava/io/RandomAccessFile;)V", "getUpstream", "()Lokio/Source;", "setUpstream", "(Lokio/Source;)V", "getUpstreamPos", "()J", "setUpstreamPos", "(J)V", "getBufferMaxSize", "upstreamReader", "Ljava/lang/Thread;", "getUpstreamReader", "()Ljava/lang/Thread;", "setUpstreamReader", "(Ljava/lang/Thread;)V", "upstreamBuffer", "Lokio/Buffer;", "getUpstreamBuffer", "()Lokio/Buffer;", "complete", "", "getComplete", "()Z", "setComplete", "(Z)V", "buffer", "getBuffer", "sourceCount", "", "getSourceCount", "()I", "setSourceCount", "(I)V", "isClosed", "writeHeader", "", "prefix", "upstreamSize", "metadataSize", "writeMetadata", "commit", "newSource", "RelaySource", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Relay implements Lockable {
    private static final long FILE_HEADER_SIZE = 32;
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    private final Buffer buffer;
    private final long bufferMaxSize;
    private boolean complete;
    private RandomAccessFile file;
    private final ByteString metadata;
    private int sourceCount;
    private Source upstream;
    private final Buffer upstreamBuffer;
    private long upstreamPos;
    private Thread upstreamReader;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final ByteString PREFIX_CLEAN = ByteString.INSTANCE.encodeUtf8("OkHttp cache v1\n");
    public static final ByteString PREFIX_DIRTY = ByteString.INSTANCE.encodeUtf8("OkHttp DIRTY :(\n");

    public /* synthetic */ Relay(RandomAccessFile randomAccessFile, Source source, long j, ByteString byteString, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(randomAccessFile, source, j, byteString, j2);
    }

    private Relay(RandomAccessFile randomAccessFile, Source source, long j, ByteString byteString, long j2) {
        this.file = randomAccessFile;
        this.upstream = source;
        this.upstreamPos = j;
        this.metadata = byteString;
        this.bufferMaxSize = j2;
        this.upstreamBuffer = new Buffer();
        this.complete = this.upstream == null;
        this.buffer = new Buffer();
    }

    public final RandomAccessFile getFile() {
        return this.file;
    }

    public final void setFile(RandomAccessFile randomAccessFile) {
        this.file = randomAccessFile;
    }

    public final Source getUpstream() {
        return this.upstream;
    }

    public final void setUpstream(Source source) {
        this.upstream = source;
    }

    public final long getUpstreamPos() {
        return this.upstreamPos;
    }

    public final void setUpstreamPos(long j) {
        this.upstreamPos = j;
    }

    public final long getBufferMaxSize() {
        return this.bufferMaxSize;
    }

    public final Thread getUpstreamReader() {
        return this.upstreamReader;
    }

    public final void setUpstreamReader(Thread thread) {
        this.upstreamReader = thread;
    }

    public final Buffer getUpstreamBuffer() {
        return this.upstreamBuffer;
    }

    public final boolean getComplete() {
        return this.complete;
    }

    public final void setComplete(boolean z) {
        this.complete = z;
    }

    public final Buffer getBuffer() {
        return this.buffer;
    }

    public final int getSourceCount() {
        return this.sourceCount;
    }

    public final void setSourceCount(int i) {
        this.sourceCount = i;
    }

    public final boolean isClosed() {
        return this.file == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeHeader(ByteString prefix, long upstreamSize, long metadataSize) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(prefix);
        buffer.writeLong(upstreamSize);
        buffer.writeLong(metadataSize);
        if (buffer.size() != 32) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        RandomAccessFile randomAccessFile = this.file;
        Intrinsics.checkNotNull(randomAccessFile);
        FileChannel channel = randomAccessFile.getChannel();
        Intrinsics.checkNotNullExpressionValue(channel, "getChannel(...)");
        new FileOperator(channel).write(0L, buffer, 32L);
    }

    private final void writeMetadata(long upstreamSize) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(this.metadata);
        RandomAccessFile randomAccessFile = this.file;
        Intrinsics.checkNotNull(randomAccessFile);
        FileChannel channel = randomAccessFile.getChannel();
        Intrinsics.checkNotNullExpressionValue(channel, "getChannel(...)");
        new FileOperator(channel).write(32 + upstreamSize, buffer, this.metadata.size());
    }

    public final void commit(long upstreamSize) throws IOException {
        writeMetadata(upstreamSize);
        RandomAccessFile randomAccessFile = this.file;
        Intrinsics.checkNotNull(randomAccessFile);
        randomAccessFile.getChannel().force(false);
        writeHeader(PREFIX_CLEAN, upstreamSize, this.metadata.size());
        RandomAccessFile randomAccessFile2 = this.file;
        Intrinsics.checkNotNull(randomAccessFile2);
        randomAccessFile2.getChannel().force(false);
        synchronized (this) {
            this.complete = true;
            Unit unit = Unit.INSTANCE;
        }
        Source source = this.upstream;
        if (source != null) {
            _UtilCommonKt.closeQuietly(source);
        }
        this.upstream = null;
    }

    /* JADX INFO: renamed from: metadata, reason: from getter */
    public final ByteString getMetadata() {
        return this.metadata;
    }

    public final Source newSource() {
        synchronized (this) {
            if (this.file == null) {
                return null;
            }
            this.sourceCount++;
            return new RelaySource();
        }
    }

    /* JADX INFO: compiled from: Relay.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokhttp3/internal/cache2/Relay$RelaySource;", "Lokio/Source;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/cache2/Relay;)V", "timeout", "Lokio/Timeout;", "fileOperator", "Lokhttp3/internal/cache2/FileOperator;", "sourcePos", "", "read", "sink", "Lokio/Buffer;", "byteCount", SplitLayout.TAG_CLOSE, "", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class RelaySource implements Source {
        private FileOperator fileOperator;
        private long sourcePos;
        private final Timeout timeout = new Timeout();

        public RelaySource() {
            RandomAccessFile file = Relay.this.getFile();
            Intrinsics.checkNotNull(file);
            FileChannel channel = file.getChannel();
            Intrinsics.checkNotNullExpressionValue(channel, "getChannel(...)");
            this.fileOperator = new FileOperator(channel);
        }

        @Override // okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            char c;
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (this.fileOperator == null) {
                throw new IllegalStateException("Check failed.");
            }
            Relay relay = Relay.this;
            synchronized (relay) {
                while (true) {
                    if (this.sourcePos != relay.getUpstreamPos()) {
                        long upstreamPos = relay.getUpstreamPos() - relay.getBuffer().size();
                        if (this.sourcePos < upstreamPos) {
                            c = 2;
                            break;
                        }
                        long jMin = Math.min(byteCount, relay.getUpstreamPos() - this.sourcePos);
                        relay.getBuffer().copyTo(sink, this.sourcePos - upstreamPos, jMin);
                        this.sourcePos += jMin;
                        return jMin;
                    }
                    if (!relay.getComplete()) {
                        if (relay.getUpstreamReader() == null) {
                            relay.setUpstreamReader(Thread.currentThread());
                            c = 1;
                            break;
                        }
                        this.timeout.waitUntilNotified(relay);
                    } else {
                        return -1L;
                    }
                }
                if (c == 2) {
                    long jMin2 = Math.min(byteCount, Relay.this.getUpstreamPos() - this.sourcePos);
                    FileOperator fileOperator = this.fileOperator;
                    Intrinsics.checkNotNull(fileOperator);
                    fileOperator.read(this.sourcePos + 32, sink, jMin2);
                    this.sourcePos += jMin2;
                    return jMin2;
                }
                try {
                    Source upstream = Relay.this.getUpstream();
                    Intrinsics.checkNotNull(upstream);
                    long j = upstream.read(Relay.this.getUpstreamBuffer(), Relay.this.getBufferMaxSize());
                    if (j == -1) {
                        Relay relay2 = Relay.this;
                        relay2.commit(relay2.getUpstreamPos());
                        Relay relay3 = Relay.this;
                        synchronized (relay3) {
                            relay3.setUpstreamReader(null);
                            Relay relay4 = relay3;
                            Intrinsics.checkNotNull(relay4, "null cannot be cast to non-null type java.lang.Object");
                            relay4.notifyAll();
                            Unit unit = Unit.INSTANCE;
                        }
                        return -1L;
                    }
                    long jMin3 = Math.min(j, byteCount);
                    Relay.this.getUpstreamBuffer().copyTo(sink, 0L, jMin3);
                    this.sourcePos += jMin3;
                    FileOperator fileOperator2 = this.fileOperator;
                    Intrinsics.checkNotNull(fileOperator2);
                    fileOperator2.write(Relay.this.getUpstreamPos() + 32, Relay.this.getUpstreamBuffer().clone(), j);
                    Relay relay5 = Relay.this;
                    synchronized (relay5) {
                        relay5.getBuffer().write(relay5.getUpstreamBuffer(), j);
                        if (relay5.getBuffer().size() > relay5.getBufferMaxSize()) {
                            relay5.getBuffer().skip(relay5.getBuffer().size() - relay5.getBufferMaxSize());
                        }
                        relay5.setUpstreamPos(relay5.getUpstreamPos() + j);
                        Unit unit2 = Unit.INSTANCE;
                    }
                    Relay relay6 = Relay.this;
                    synchronized (relay6) {
                        relay6.setUpstreamReader(null);
                        Relay relay7 = relay6;
                        Intrinsics.checkNotNull(relay7, "null cannot be cast to non-null type java.lang.Object");
                        relay7.notifyAll();
                        Unit unit3 = Unit.INSTANCE;
                    }
                    return jMin3;
                } catch (Throwable th) {
                    Relay relay8 = Relay.this;
                    synchronized (relay8) {
                        relay8.setUpstreamReader(null);
                        Relay relay9 = relay8;
                        Intrinsics.checkNotNull(relay9, "null cannot be cast to non-null type java.lang.Object");
                        relay9.notifyAll();
                        Unit unit4 = Unit.INSTANCE;
                        throw th;
                    }
                }
            }
        }

        @Override // okio.Source
        /* JADX INFO: renamed from: timeout, reason: from getter */
        public Timeout getTimeout() {
            return this.timeout;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.fileOperator == null) {
                return;
            }
            RandomAccessFile randomAccessFile = null;
            this.fileOperator = null;
            Relay relay = Relay.this;
            synchronized (relay) {
                relay.setSourceCount(relay.getSourceCount() - 1);
                if (relay.getSourceCount() == 0) {
                    RandomAccessFile file = relay.getFile();
                    relay.setFile(null);
                    randomAccessFile = file;
                }
                Unit unit = Unit.INSTANCE;
            }
            if (randomAccessFile != null) {
                _UtilCommonKt.closeQuietly(randomAccessFile);
            }
        }
    }

    /* JADX INFO: compiled from: Relay.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J&\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u000bJ\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lokhttp3/internal/cache2/Relay$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SOURCE_UPSTREAM", "", "SOURCE_FILE", "PREFIX_CLEAN", "Lokio/ByteString;", "PREFIX_DIRTY", "FILE_HEADER_SIZE", "", "edit", "Lokhttp3/internal/cache2/Relay;", "file", "Ljava/io/File;", "upstream", "Lokio/Source;", "metadata", "bufferMaxSize", "read", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Relay edit(File file, Source upstream, ByteString metadata, long bufferMaxSize) throws IOException {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(upstream, "upstream");
            Intrinsics.checkNotNullParameter(metadata, "metadata");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            Relay relay = new Relay(randomAccessFile, upstream, 0L, metadata, bufferMaxSize, null);
            randomAccessFile.setLength(0L);
            relay.writeHeader(Relay.PREFIX_DIRTY, -1L, -1L);
            return relay;
        }

        public final Relay read(File file) throws IOException {
            Intrinsics.checkNotNullParameter(file, "file");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            FileChannel channel = randomAccessFile.getChannel();
            Intrinsics.checkNotNullExpressionValue(channel, "getChannel(...)");
            FileOperator fileOperator = new FileOperator(channel);
            Buffer buffer = new Buffer();
            fileOperator.read(0L, buffer, 32L);
            if (!Intrinsics.areEqual(buffer.readByteString(Relay.PREFIX_CLEAN.size()), Relay.PREFIX_CLEAN)) {
                throw new IOException("unreadable cache file");
            }
            long j = buffer.readLong();
            long j2 = buffer.readLong();
            Buffer buffer2 = new Buffer();
            fileOperator.read(32 + j, buffer2, j2);
            return new Relay(randomAccessFile, null, j, buffer2.readByteString(), 0L, null);
        }
    }
}
