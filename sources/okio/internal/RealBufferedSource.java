package okio.internal;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.aicr.paintservice.PaintErrorCode;
import java.io.EOFException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Typography;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Timeout;

/* JADX INFO: renamed from: okio.internal.-RealBufferedSource, reason: invalid class name */
/* JADX INFO: compiled from: RealBufferedSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\n\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u0006\u001a\u00020\u0007*\u00020\u0002H\u0080\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\u0015\u0010\n\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u000b\u001a\u00020\f*\u00020\u0002H\u0080\b\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0002H\u0080\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\u0015\u0010\u000f\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0080\b\u001a\r\u0010\u0013\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u0013\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\u0015\u0010\u0015\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u0000\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0010H\u0080\b\u001a\u001d\u0010\u0015\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0018H\u0080\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u0019\u001a\u00020\u001a*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a\u000f\u0010\u001b\u001a\u0004\u0018\u00010\u001a*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001c\u001a\u00020\u001a*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u001e\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a\r\u0010\u001f\u001a\u00020 *\u00020\u0002H\u0080\b\u001a\r\u0010!\u001a\u00020 *\u00020\u0002H\u0080\b\u001a\r\u0010\"\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a\r\u0010#\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a\r\u0010$\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010%\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010&\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010'\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\u0015\u0010(\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0001H\u0080\b\u001a%\u0010)\u001a\u00020\u0001*\u00020\u00022\u0006\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u0001H\u0080\b\u001a:\u0010)\u001a\u00020\u0001*\u00020\u00022\u0006\u0010-\u001a\u00020\u000e2\b\b\u0002\u0010.\u001a\u00020\u00102\b\b\u0002\u0010\u0005\u001a\u00020\u00102\u0006\u0010+\u001a\u00020\u00012\b\b\u0002\u0010,\u001a\u00020\u0001H\u0000\u001a4\u0010/\u001a\u00020\u0007*\u00020\u00042\u0006\u0010-\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u0001H\u0002\u001a\u001d\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u00101\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u0001H\u0080\b\u001a-\u00102\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00012\u0006\u0010-\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0010H\u0080\b\u001a\r\u00103\u001a\u000204*\u00020\u0002H\u0080\b\u001a\r\u00105\u001a\u00020\t*\u00020\u0002H\u0080\b\u001a\r\u00106\u001a\u000207*\u00020\u0002H\u0080\b\u001a\r\u00108\u001a\u00020\u001a*\u00020\u0002H\u0080\b¨\u00069"}, d2 = {"commonRead", "", "Lokio/RealBufferedSource;", "sink", "Lokio/Buffer;", "byteCount", "commonExhausted", "", "commonRequire", "", "commonRequest", "commonReadByte", "", "commonReadByteString", "Lokio/ByteString;", "commonSelect", "", "options", "Lokio/Options;", "commonReadByteArray", "", "commonReadFully", "offset", "commonReadAll", "Lokio/Sink;", "commonReadUtf8", "", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonReadUtf8CodePoint", "commonReadShort", "", "commonReadShortLe", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadDecimalLong", "commonReadHexadecimalUnsignedLong", "commonSkip", "commonIndexOf", "b", "fromIndex", "toIndex", "bytes", "bytesOffset", "isMatchPossibleByExpandingBuffer", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonPeek", "Lokio/BufferedSource;", "commonClose", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class RealBufferedSource {
    public static final long commonRead(okio.RealBufferedSource realBufferedSource, Buffer sink, long j) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (j < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        }
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        if (realBufferedSource.bufferField.size() == 0) {
            if (j == 0) {
                return 0L;
            }
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                return -1L;
            }
        }
        return realBufferedSource.bufferField.read(sink, Math.min(j, realBufferedSource.bufferField.size()));
    }

    public static final boolean commonExhausted(okio.RealBufferedSource realBufferedSource) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        return realBufferedSource.bufferField.exhausted() && realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1;
    }

    public static final void commonRequire(okio.RealBufferedSource realBufferedSource, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (!realBufferedSource.request(j)) {
            throw new EOFException();
        }
    }

    public static final boolean commonRequest(okio.RealBufferedSource realBufferedSource, long j) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (j < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        }
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        while (realBufferedSource.bufferField.size() < j) {
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                return false;
            }
        }
        return true;
    }

    public static final byte commonReadByte(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(1L);
        return realBufferedSource.bufferField.readByte();
    }

    public static final ByteString commonReadByteString(okio.RealBufferedSource realBufferedSource, long j) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(j);
        return realBufferedSource.bufferField.readByteString(j);
    }

    public static final int commonSelect(okio.RealBufferedSource realBufferedSource, Options options) throws EOFException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        do {
            int iSelectPrefix = Buffer.selectPrefix(realBufferedSource.bufferField, options, true);
            if (iSelectPrefix != -2) {
                if (iSelectPrefix == -1) {
                    return -1;
                }
                realBufferedSource.bufferField.skip(options.getByteStrings()[iSelectPrefix].size());
                return iSelectPrefix;
            }
        } while (realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) != -1);
        return -1;
    }

    public static final byte[] commonReadByteArray(okio.RealBufferedSource realBufferedSource, long j) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(j);
        return realBufferedSource.bufferField.readByteArray(j);
    }

    public static final void commonReadFully(okio.RealBufferedSource realBufferedSource, byte[] sink) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            realBufferedSource.require(sink.length);
            realBufferedSource.bufferField.readFully(sink);
        } catch (EOFException e) {
            int i = 0;
            while (realBufferedSource.bufferField.size() > 0) {
                int i2 = realBufferedSource.bufferField.read(sink, i, (int) realBufferedSource.bufferField.size());
                if (i2 == -1) {
                    throw new AssertionError();
                }
                i += i2;
            }
            throw e;
        }
    }

    public static final int commonRead(okio.RealBufferedSource realBufferedSource, byte[] sink, int i, int i2) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = i2;
        SegmentedByteString.checkOffsetAndCount(sink.length, i, j);
        if (realBufferedSource.bufferField.size() == 0) {
            if (i2 == 0) {
                return 0;
            }
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                return -1;
            }
        }
        return realBufferedSource.bufferField.read(sink, i, (int) Math.min(j, realBufferedSource.bufferField.size()));
    }

    public static final void commonReadFully(okio.RealBufferedSource realBufferedSource, Buffer sink, long j) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            realBufferedSource.require(j);
            realBufferedSource.bufferField.readFully(sink, j);
        } catch (EOFException e) {
            sink.writeAll(realBufferedSource.bufferField);
            throw e;
        }
    }

    public static final long commonReadAll(okio.RealBufferedSource realBufferedSource, Sink sink) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = 0;
        while (realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) != -1) {
            long jCompleteSegmentByteCount = realBufferedSource.bufferField.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j += jCompleteSegmentByteCount;
                sink.write(realBufferedSource.bufferField, jCompleteSegmentByteCount);
            }
        }
        if (realBufferedSource.bufferField.size() <= 0) {
            return j;
        }
        long size = j + realBufferedSource.bufferField.size();
        sink.write(realBufferedSource.bufferField, realBufferedSource.bufferField.size());
        return size;
    }

    public static final String commonReadUtf8(okio.RealBufferedSource realBufferedSource, long j) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(j);
        return realBufferedSource.bufferField.readUtf8(j);
    }

    public static final String commonReadUtf8Line(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        long jIndexOf = realBufferedSource.indexOf((byte) 10);
        if (jIndexOf != -1) {
            return Buffer.readUtf8Line(realBufferedSource.bufferField, jIndexOf);
        }
        if (realBufferedSource.bufferField.size() != 0) {
            return realBufferedSource.readUtf8(realBufferedSource.bufferField.size());
        }
        return null;
    }

    public static final String commonReadUtf8LineStrict(okio.RealBufferedSource realBufferedSource, long j) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (j < 0) {
            throw new IllegalArgumentException(("limit < 0: " + j).toString());
        }
        long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
        long jIndexOf = realBufferedSource.indexOf((byte) 10, 0L, j2);
        if (jIndexOf != -1) {
            return Buffer.readUtf8Line(realBufferedSource.bufferField, jIndexOf);
        }
        if (j2 < Long.MAX_VALUE && realBufferedSource.request(j2) && realBufferedSource.bufferField.getByte(j2 - 1) == 13 && realBufferedSource.request(j2 + 1) && realBufferedSource.bufferField.getByte(j2) == 10) {
            return Buffer.readUtf8Line(realBufferedSource.bufferField, j2);
        }
        Buffer buffer = new Buffer();
        realBufferedSource.bufferField.copyTo(buffer, 0L, Math.min(32, realBufferedSource.bufferField.size()));
        throw new EOFException("\\n not found: limit=" + Math.min(realBufferedSource.bufferField.size(), j) + " content=" + buffer.readByteString().hex() + Typography.ellipsis);
    }

    public static final int commonReadUtf8CodePoint(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(1L);
        byte b = realBufferedSource.bufferField.getByte(0L);
        if ((b & 224) == 192) {
            realBufferedSource.require(2L);
        } else if ((b & 240) == 224) {
            realBufferedSource.require(3L);
        } else if ((b & PaintErrorCode.SECURITY_ERROR) == 240) {
            realBufferedSource.require(4L);
        }
        return realBufferedSource.bufferField.readUtf8CodePoint();
    }

    public static final short commonReadShort(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(2L);
        return realBufferedSource.bufferField.readShort();
    }

    public static final short commonReadShortLe(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(2L);
        return realBufferedSource.bufferField.readShortLe();
    }

    public static final int commonReadInt(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(4L);
        return realBufferedSource.bufferField.readInt();
    }

    public static final int commonReadIntLe(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(4L);
        return realBufferedSource.bufferField.readIntLe();
    }

    public static final long commonReadLong(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(8L);
        return realBufferedSource.bufferField.readLong();
    }

    public static final long commonReadLongLe(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(8L);
        return realBufferedSource.bufferField.readLongLe();
    }

    public static final long commonReadDecimalLong(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(1L);
        long j = 0;
        while (true) {
            long j2 = j + 1;
            if (!realBufferedSource.request(j2)) {
                break;
            }
            byte b = realBufferedSource.bufferField.getByte(j);
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
        return realBufferedSource.bufferField.readDecimalLong();
    }

    public static final long commonReadHexadecimalUnsignedLong(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.require(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!realBufferedSource.request(i2)) {
                break;
            }
            byte b = realBufferedSource.bufferField.getByte(i);
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
        return realBufferedSource.bufferField.readHexadecimalUnsignedLong();
    }

    public static final void commonSkip(okio.RealBufferedSource realBufferedSource, long j) throws EOFException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        while (j > 0) {
            if (realBufferedSource.bufferField.size() == 0 && realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j, realBufferedSource.bufferField.size());
            realBufferedSource.bufferField.skip(jMin);
            j -= jMin;
        }
    }

    public static final long commonIndexOf(okio.RealBufferedSource realBufferedSource, byte b, long j, long j2) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        if (0 > j || j > j2) {
            throw new IllegalArgumentException(("fromIndex=" + j + " toIndex=" + j2).toString());
        }
        long jMax = j;
        while (jMax < j2) {
            byte b2 = b;
            long j3 = j2;
            long jIndexOf = realBufferedSource.bufferField.indexOf(b2, jMax, j3);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            long size = realBufferedSource.bufferField.size();
            if (size >= j3 || realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                break;
            }
            jMax = Math.max(jMax, size);
            b = b2;
            j2 = j3;
        }
        return -1L;
    }

    public static /* synthetic */ long commonIndexOf$default(okio.RealBufferedSource realBufferedSource, ByteString byteString, int i, int i2, long j, long j2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        int i4 = i;
        if ((i3 & 4) != 0) {
            i2 = byteString.size();
        }
        return commonIndexOf(realBufferedSource, byteString, i4, i2, j, (i3 & 16) != 0 ? Long.MAX_VALUE : j2);
    }

    public static final long commonIndexOf(okio.RealBufferedSource realBufferedSource, ByteString byteString, int i, int i2, long j, long j2) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        ByteString bytes = byteString;
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        int i3 = i;
        long j3 = i2;
        SegmentedByteString.checkOffsetAndCount(bytes.size(), i3, j3);
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        long jMax = j;
        while (true) {
            long jCommonIndexOf = Buffer.commonIndexOf(realBufferedSource.bufferField, bytes, jMax, j2, i3, i2);
            if (jCommonIndexOf != -1) {
                return jCommonIndexOf;
            }
            long size = (realBufferedSource.bufferField.size() - j3) + 1;
            if (size >= j2) {
                return -1L;
            }
            long j4 = jMax;
            if (!isMatchPossibleByExpandingBuffer(realBufferedSource.bufferField, byteString, i, i2, j4, j2) || realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                return -1L;
            }
            jMax = Math.max(j4, size);
            bytes = byteString;
            i3 = i;
        }
    }

    private static final boolean isMatchPossibleByExpandingBuffer(Buffer buffer, ByteString byteString, int i, int i2, long j, long j2) {
        if (buffer.size() < j2) {
            return true;
        }
        int iMax = (int) Math.max(1L, (buffer.size() - j2) + 1);
        int iMin = ((int) Math.min(i2, (buffer.size() - j) + 1)) - 1;
        if (iMax > iMin) {
            return false;
        }
        int i3 = iMin;
        while (true) {
            Buffer buffer2 = buffer;
            ByteString byteString2 = byteString;
            int i4 = i;
            if (buffer2.rangeEquals(buffer.size() - ((long) i3), byteString2, i4, i3)) {
                return true;
            }
            if (i3 == iMax) {
                return false;
            }
            i3--;
            buffer = buffer2;
            byteString = byteString2;
            i = i4;
        }
    }

    public static final long commonIndexOfElement(okio.RealBufferedSource realBufferedSource, ByteString targetBytes, long j) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(targetBytes, "targetBytes");
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        while (true) {
            long jIndexOfElement = realBufferedSource.bufferField.indexOfElement(targetBytes, j);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            long size = realBufferedSource.bufferField.size();
            if (realBufferedSource.source.read(realBufferedSource.bufferField, 8192L) == -1) {
                return -1L;
            }
            j = Math.max(j, size);
        }
    }

    public static final boolean commonRangeEquals(okio.RealBufferedSource realBufferedSource, long j, ByteString bytes, int i, int i2) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (realBufferedSource.closed) {
            throw new IllegalStateException("closed".toString());
        }
        return i2 >= 0 && j >= 0 && i >= 0 && i + i2 <= bytes.size() && (i2 == 0 || commonIndexOf(realBufferedSource, bytes, i, i2, j, j + 1) != -1);
    }

    public static final BufferedSource commonPeek(okio.RealBufferedSource realBufferedSource) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        return Okio.buffer(new PeekSource(realBufferedSource));
    }

    public static final void commonClose(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (realBufferedSource.closed) {
            return;
        }
        realBufferedSource.closed = true;
        realBufferedSource.source.close();
        realBufferedSource.bufferField.clear();
    }

    public static final Timeout commonTimeout(okio.RealBufferedSource realBufferedSource) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        return realBufferedSource.source.getTimeout();
    }

    public static final String commonToString(okio.RealBufferedSource realBufferedSource) {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        return "buffer(" + realBufferedSource.source + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public static final ByteString commonReadByteString(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.bufferField.writeAll(realBufferedSource.source);
        return realBufferedSource.bufferField.readByteString();
    }

    public static final byte[] commonReadByteArray(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.bufferField.writeAll(realBufferedSource.source);
        return realBufferedSource.bufferField.readByteArray();
    }

    public static final String commonReadUtf8(okio.RealBufferedSource realBufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        realBufferedSource.bufferField.writeAll(realBufferedSource.source);
        return realBufferedSource.bufferField.readUtf8();
    }
}
