package okio;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.aicr.paintservice.PaintErrorCode;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Typography;
import miuix.navigation.SplitLayout;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: RealBufferedSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\n\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\n\u001a\u00020\u0007H\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\tH\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J'\u0010\u001b\u001a\u0004\u0018\u0001H\u001f\"\b\b\u0000\u0010\u001f*\u00020 2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u001f0!H\u0016¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010\u000f\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020$H\u0016J \u0010\u000f\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020$2\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u001cH\u0016J\u0010\u0010\u000f\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020'H\u0016J\u0018\u0010%\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010(\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020+H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020.H\u0016J\u0018\u0010,\u001a\u00020+2\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010-\u001a\u00020.H\u0016J\n\u0010/\u001a\u0004\u0018\u00010+H\u0016J\b\u00100\u001a\u00020+H\u0016J\u0010\u00100\u001a\u00020+2\u0006\u00101\u001a\u00020\u0010H\u0016J\b\u00102\u001a\u00020\u001cH\u0016J\b\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u000204H\u0016J\b\u00106\u001a\u00020\u001cH\u0016J\b\u00107\u001a\u00020\u001cH\u0016J\b\u00108\u001a\u00020\u0010H\u0016J\b\u00109\u001a\u00020\u0010H\u0016J\b\u0010:\u001a\u00020\u0010H\u0016J\b\u0010;\u001a\u00020\u0010H\u0016J\u0010\u0010<\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0010\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u0018H\u0016J\u0018\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u00182\u0006\u0010?\u001a\u00020\u0010H\u0016J \u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u00182\u0006\u0010?\u001a\u00020\u00102\u0006\u0010@\u001a\u00020\u0010H\u0016J\u0010\u0010=\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u001aH\u0016J\u0018\u0010=\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u0010H\u0016J \u0010=\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u00102\u0006\u0010@\u001a\u00020\u0010H\u0016J\u0010\u0010B\u001a\u00020\u00102\u0006\u0010C\u001a\u00020\u001aH\u0016J\u0018\u0010B\u001a\u00020\u00102\u0006\u0010C\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u0010H\u0016J\u0018\u0010D\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u001aH\u0016J(\u0010D\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u001a2\u0006\u0010E\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u001cH\u0016J\b\u0010F\u001a\u00020\u0001H\u0016J\b\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u00020\tH\u0016J\b\u0010J\u001a\u00020\u0015H\u0016J\b\u0010K\u001a\u00020LH\u0016J\b\u0010M\u001a\u00020+H\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u00078Ö\u0002X\u0096\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e¨\u0006N"}, d2 = {"Lokio/RealBufferedSource;", "Lokio/BufferedSource;", "source", "Lokio/Source;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/Source;)V", "bufferField", "Lokio/Buffer;", "closed", "", "buffer", "getBuffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "read", "", "sink", "byteCount", "exhausted", "require", "", StatConstants.Event.REQUEST, "readByte", "", "readByteString", "Lokio/ByteString;", SmartAction.Feature.SELECT, "", "options", "Lokio/Options;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lokio/TypedOptions;", "(Lokio/TypedOptions;)Ljava/lang/Object;", "readByteArray", "", "readFully", "offset", "Ljava/nio/ByteBuffer;", "readAll", "Lokio/Sink;", "readUtf8", "", "readString", "charset", "Ljava/nio/charset/Charset;", "readUtf8Line", "readUtf8LineStrict", "limit", "readUtf8CodePoint", "readShort", "", "readShortLe", "readInt", "readIntLe", "readLong", "readLongLe", "readDecimalLong", "readHexadecimalUnsignedLong", "skip", "indexOf", "b", "fromIndex", "toIndex", "bytes", "indexOfElement", "targetBytes", "rangeEquals", "bytesOffset", "peek", "inputStream", "Ljava/io/InputStream;", "isOpen", SplitLayout.TAG_CLOSE, "timeout", "Lokio/Timeout;", "toString", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class RealBufferedSource implements BufferedSource {
    public final Buffer bufferField;
    public boolean closed;
    public final Source source;

    public static /* synthetic */ void getBuffer$annotations() {
    }

    public RealBufferedSource(Source source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
        this.bufferField = new Buffer();
    }

    @Override // okio.BufferedSource, okio.BufferedSink
    public Buffer getBuffer() {
        return this.bufferField;
    }

    @Override // okio.BufferedSource, okio.BufferedSink
    /* JADX INFO: renamed from: buffer, reason: from getter */
    public Buffer getBufferField() {
        return this.bufferField;
    }

    @Override // okio.BufferedSource
    public <T> T select(TypedOptions<T> options) throws IOException {
        Intrinsics.checkNotNullParameter(options, "options");
        int iSelect = select(options.getOptions());
        if (iSelect == -1) {
            return null;
        }
        return options.get(iSelect);
    }

    @Override // okio.BufferedSource
    public int read(byte[] sink) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        return read(sink, 0, sink.length);
    }

    @Override // okio.BufferedSource
    public String readString(long byteCount, Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(charset, "charset");
        require(byteCount);
        return this.bufferField.readString(byteCount, charset);
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict() {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b) throws IOException {
        return indexOf(b, 0L, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b, long fromIndex) {
        return indexOf(b, fromIndex, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return indexOf(bytes, 0L);
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString bytes, long fromIndex) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return indexOf(bytes, fromIndex, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString bytes, long fromIndex, long toIndex) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return okio.internal.RealBufferedSource.commonIndexOf$default(this, bytes, 0, 0, fromIndex, toIndex, 6, null);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString targetBytes) {
        Intrinsics.checkNotNullParameter(targetBytes, "targetBytes");
        return indexOfElement(targetBytes, 0L);
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long offset, ByteString bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        return rangeEquals(offset, bytes, 0, bytes.size());
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: okio.RealBufferedSource.inputStream.1
            @Override // java.io.InputStream
            public int read() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                if (RealBufferedSource.this.bufferField.size() == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.bufferField, 8192L) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.bufferField.readByte() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] data, int offset, int byteCount) throws IOException {
                Intrinsics.checkNotNullParameter(data, "data");
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                SegmentedByteString.checkOffsetAndCount(data.length, offset, byteCount);
                if (RealBufferedSource.this.bufferField.size() == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.bufferField, 8192L) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.bufferField.read(data, offset, byteCount);
            }

            @Override // java.io.InputStream
            public int available() throws IOException {
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                return (int) Math.min(RealBufferedSource.this.bufferField.size(), Integer.MAX_VALUE);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            public String toString() {
                return RealBufferedSource.this + ".inputStream()";
            }

            @Override // java.io.InputStream
            public long transferTo(OutputStream out) throws IOException {
                Intrinsics.checkNotNullParameter(out, "out");
                if (RealBufferedSource.this.closed) {
                    throw new IOException("closed");
                }
                long size = 0;
                while (true) {
                    if (RealBufferedSource.this.bufferField.size() == 0 && RealBufferedSource.this.source.read(RealBufferedSource.this.bufferField, 8192L) == -1) {
                        return size;
                    }
                    size += RealBufferedSource.this.bufferField.size();
                    Buffer.writeTo$default(RealBufferedSource.this.bufferField, out, 0L, 2, null);
                }
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.closed;
    }

    @Override // okio.Source
    public long read(Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (byteCount < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        if (this.bufferField.size() == 0) {
            if (byteCount == 0) {
                return 0L;
            }
            if (this.source.read(this.bufferField, 8192L) == -1) {
                return -1L;
            }
        }
        return this.bufferField.read(sink, Math.min(byteCount, this.bufferField.size()));
    }

    @Override // okio.BufferedSource
    public boolean exhausted() {
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        return this.bufferField.exhausted() && this.source.read(this.bufferField, 8192L) == -1;
    }

    @Override // okio.BufferedSource
    public void require(long byteCount) throws IOException {
        if (!request(byteCount)) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public boolean request(long byteCount) throws IOException {
        if (byteCount < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        while (this.bufferField.size() < byteCount) {
            if (this.source.read(this.bufferField, 8192L) == -1) {
                return false;
            }
        }
        return true;
    }

    @Override // okio.BufferedSource
    public byte readByte() throws IOException {
        require(1L);
        return this.bufferField.readByte();
    }

    @Override // okio.BufferedSource
    public ByteString readByteString() throws IOException {
        this.bufferField.writeAll(this.source);
        return this.bufferField.readByteString();
    }

    @Override // okio.BufferedSource
    public ByteString readByteString(long byteCount) throws IOException {
        require(byteCount);
        return this.bufferField.readByteString(byteCount);
    }

    @Override // okio.BufferedSource
    public int select(Options options) throws EOFException {
        Intrinsics.checkNotNullParameter(options, "options");
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        do {
            int iSelectPrefix = okio.internal.Buffer.selectPrefix(this.bufferField, options, true);
            if (iSelectPrefix != -2) {
                if (iSelectPrefix == -1) {
                    return -1;
                }
                this.bufferField.skip(options.getByteStrings()[iSelectPrefix].size());
                return iSelectPrefix;
            }
        } while (this.source.read(this.bufferField, 8192L) != -1);
        return -1;
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray() throws IOException {
        this.bufferField.writeAll(this.source);
        return this.bufferField.readByteArray();
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray(long byteCount) throws IOException {
        require(byteCount);
        return this.bufferField.readByteArray(byteCount);
    }

    @Override // okio.BufferedSource
    public void readFully(byte[] sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            require(sink.length);
            this.bufferField.readFully(sink);
        } catch (EOFException e) {
            int i = 0;
            while (this.bufferField.size() > 0) {
                Buffer buffer = this.bufferField;
                int i2 = buffer.read(sink, i, (int) buffer.size());
                if (i2 == -1) {
                    throw new AssertionError();
                }
                i += i2;
            }
            throw e;
        }
    }

    @Override // okio.BufferedSource
    public int read(byte[] sink, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = byteCount;
        SegmentedByteString.checkOffsetAndCount(sink.length, offset, j);
        if (this.bufferField.size() == 0) {
            if (byteCount == 0) {
                return 0;
            }
            if (this.source.read(this.bufferField, 8192L) == -1) {
                return -1;
            }
        }
        return this.bufferField.read(sink, offset, (int) Math.min(j, this.bufferField.size()));
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer sink) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (this.bufferField.size() == 0 && this.source.read(this.bufferField, 8192L) == -1) {
            return -1;
        }
        return this.bufferField.read(sink);
    }

    @Override // okio.BufferedSource
    public void readFully(Buffer sink, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            require(byteCount);
            this.bufferField.readFully(sink, byteCount);
        } catch (EOFException e) {
            sink.writeAll(this.bufferField);
            throw e;
        }
    }

    @Override // okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = 0;
        while (this.source.read(this.bufferField, 8192L) != -1) {
            long jCompleteSegmentByteCount = this.bufferField.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j += jCompleteSegmentByteCount;
                sink.write(this.bufferField, jCompleteSegmentByteCount);
            }
        }
        if (this.bufferField.size() <= 0) {
            return j;
        }
        long size = j + this.bufferField.size();
        Buffer buffer = this.bufferField;
        sink.write(buffer, buffer.size());
        return size;
    }

    @Override // okio.BufferedSource
    public String readUtf8() throws IOException {
        this.bufferField.writeAll(this.source);
        return this.bufferField.readUtf8();
    }

    @Override // okio.BufferedSource
    public String readUtf8(long byteCount) throws IOException {
        require(byteCount);
        return this.bufferField.readUtf8(byteCount);
    }

    @Override // okio.BufferedSource
    public String readString(Charset charset) throws IOException {
        Intrinsics.checkNotNullParameter(charset, "charset");
        this.bufferField.writeAll(this.source);
        return this.bufferField.readString(charset);
    }

    @Override // okio.BufferedSource
    public String readUtf8Line() throws IOException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return okio.internal.Buffer.readUtf8Line(this.bufferField, jIndexOf);
        }
        if (this.bufferField.size() != 0) {
            return readUtf8(this.bufferField.size());
        }
        return null;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict(long limit) throws IOException {
        if (limit < 0) {
            throw new IllegalArgumentException(("limit < 0: " + limit).toString());
        }
        long j = limit == Long.MAX_VALUE ? Long.MAX_VALUE : limit + 1;
        long jIndexOf = indexOf((byte) 10, 0L, j);
        if (jIndexOf != -1) {
            return okio.internal.Buffer.readUtf8Line(this.bufferField, jIndexOf);
        }
        if (j < Long.MAX_VALUE && request(j) && this.bufferField.getByte(j - 1) == 13 && request(j + 1) && this.bufferField.getByte(j) == 10) {
            return okio.internal.Buffer.readUtf8Line(this.bufferField, j);
        }
        Buffer buffer = new Buffer();
        Buffer buffer2 = this.bufferField;
        buffer2.copyTo(buffer, 0L, Math.min(32, buffer2.size()));
        throw new EOFException("\\n not found: limit=" + Math.min(this.bufferField.size(), limit) + " content=" + buffer.readByteString().hex() + Typography.ellipsis);
    }

    @Override // okio.BufferedSource
    public int readUtf8CodePoint() throws IOException {
        require(1L);
        byte b = this.bufferField.getByte(0L);
        if ((b & 224) == 192) {
            require(2L);
        } else if ((b & 240) == 224) {
            require(3L);
        } else if ((b & PaintErrorCode.SECURITY_ERROR) == 240) {
            require(4L);
        }
        return this.bufferField.readUtf8CodePoint();
    }

    @Override // okio.BufferedSource
    public short readShort() throws IOException {
        require(2L);
        return this.bufferField.readShort();
    }

    @Override // okio.BufferedSource
    public short readShortLe() throws IOException {
        require(2L);
        return this.bufferField.readShortLe();
    }

    @Override // okio.BufferedSource
    public int readInt() throws IOException {
        require(4L);
        return this.bufferField.readInt();
    }

    @Override // okio.BufferedSource
    public int readIntLe() throws IOException {
        require(4L);
        return this.bufferField.readIntLe();
    }

    @Override // okio.BufferedSource
    public long readLong() throws IOException {
        require(8L);
        return this.bufferField.readLong();
    }

    @Override // okio.BufferedSource
    public long readLongLe() throws IOException {
        require(8L);
        return this.bufferField.readLongLe();
    }

    @Override // okio.BufferedSource
    public long readDecimalLong() throws IOException {
        require(1L);
        long j = 0;
        while (true) {
            long j2 = j + 1;
            if (!request(j2)) {
                break;
            }
            byte b = this.bufferField.getByte(j);
            if ((b < 48 || b > 57) && !(j == 0 && b == 45)) {
                if (j != 0) {
                    break;
                }
                StringBuilder sb = new StringBuilder("Expected a digit or '-' but was 0x");
                String string = Integer.toString(b, CharsKt.checkRadix(16));
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                throw new NumberFormatException(sb.append(string).toString());
            }
            j = j2;
        }
        return this.bufferField.readDecimalLong();
    }

    @Override // okio.BufferedSource
    public long readHexadecimalUnsignedLong() throws IOException {
        require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!request(i2)) {
                break;
            }
            byte b = this.bufferField.getByte(i);
            if ((b < 48 || b > 57) && ((b < 97 || b > 102) && (b < 65 || b > 70))) {
                if (i != 0) {
                    break;
                }
                StringBuilder sb = new StringBuilder("Expected leading [0-9a-fA-F] character but was 0x");
                String string = Integer.toString(b, CharsKt.checkRadix(16));
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                throw new NumberFormatException(sb.append(string).toString());
            }
            i = i2;
        }
        return this.bufferField.readHexadecimalUnsignedLong();
    }

    @Override // okio.BufferedSource
    public void skip(long byteCount) {
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        while (byteCount > 0) {
            if (this.bufferField.size() == 0 && this.source.read(this.bufferField, 8192L) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(byteCount, this.bufferField.size());
            this.bufferField.skip(jMin);
            byteCount -= jMin;
        }
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b, long fromIndex, long toIndex) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        if (0 > fromIndex || fromIndex > toIndex) {
            throw new IllegalArgumentException(("fromIndex=" + fromIndex + " toIndex=" + toIndex).toString());
        }
        long jMax = fromIndex;
        while (jMax < toIndex) {
            byte b2 = b;
            long j = toIndex;
            long jIndexOf = this.bufferField.indexOf(b2, jMax, j);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long size = this.bufferField.size();
            if (size >= j || this.source.read(this.bufferField, 8192L) == -1) {
                break;
            }
            jMax = Math.max(jMax, size);
            b = b2;
            toIndex = j;
        }
        return -1L;
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString targetBytes, long fromIndex) {
        Intrinsics.checkNotNullParameter(targetBytes, "targetBytes");
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        while (true) {
            long jIndexOfElement = this.bufferField.indexOfElement(targetBytes, fromIndex);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            long size = this.bufferField.size();
            if (this.source.read(this.bufferField, 8192L) == -1) {
                return -1L;
            }
            fromIndex = Math.max(fromIndex, size);
        }
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long offset, ByteString bytes, int bytesOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        return byteCount >= 0 && offset >= 0 && bytesOffset >= 0 && bytesOffset + byteCount <= bytes.size() && (byteCount == 0 || okio.internal.RealBufferedSource.commonIndexOf(this, bytes, bytesOffset, byteCount, offset, offset + 1) != -1);
    }

    @Override // okio.BufferedSource
    public BufferedSource peek() {
        return Okio.buffer(new PeekSource(this));
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.source.close();
        this.bufferField.clear();
    }

    @Override // okio.Source
    /* JADX INFO: renamed from: timeout */
    public Timeout getTimeout() {
        return this.source.getTimeout();
    }

    public String toString() {
        return "buffer(" + this.source + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }
}
