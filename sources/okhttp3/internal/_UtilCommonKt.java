package okhttp3.internal;

import androidx.collection.SieveCacheKt;
import androidx.exifinterface.media.ExifInterface;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.io.Closeable;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.FileSystem;
import okio.Options;
import okio.Path;
import okio.Sink;

/* JADX INFO: compiled from: -UtilCommon.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001aG\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u001a\u0010\n\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\b0\u000bj\n\u0012\u0006\b\u0000\u0012\u00020\b`\fH\u0000¢\u0006\u0002\u0010\r\u001aC\u0010\u000e\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\b0\u00072\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\u001a\u0010\n\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\b0\u000bj\n\u0012\u0006\b\u0000\u0012\u00020\b`\fH\u0000¢\u0006\u0002\u0010\u0010\u001a7\u0010\u0011\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0013\u001a\u00020\b2\u0016\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\b0\u000bj\b\u0012\u0004\u0012\u00020\b`\fH\u0000¢\u0006\u0002\u0010\u0014\u001a%\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0013\u001a\u00020\bH\u0000¢\u0006\u0002\u0010\u0016\u001a \u0010\u0017\u001a\u00020\u0012*\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00122\b\b\u0002\u0010\u0019\u001a\u00020\u0012H\u0000\u001a \u0010\u001a\u001a\u00020\u0012*\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00122\b\b\u0002\u0010\u0019\u001a\u00020\u0012H\u0000\u001a\u001e\u0010\u001b\u001a\u00020\b*\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00122\b\b\u0002\u0010\u0019\u001a\u00020\u0012\u001a&\u0010\u001c\u001a\u00020\u0012*\u00020\b2\u0006\u0010\u001d\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00122\b\b\u0002\u0010\u0019\u001a\u00020\u0012\u001a&\u0010\u001c\u001a\u00020\u0012*\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u0018\u001a\u00020\u00122\b\b\u0002\u0010\u0019\u001a\u00020\u0012\u001a\f\u0010 \u001a\u00020\u0012*\u00020\bH\u0000\u001a\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\bH\u0000\u001a\f\u0010#\u001a\u00020\u0012*\u00020\u001fH\u0000\u001a\u0015\u0010$\u001a\u00020\u0012*\u00020%2\u0006\u0010&\u001a\u00020\u0012H\u0080\u0004\u001a\u0015\u0010$\u001a\u00020\u0012*\u00020'2\u0006\u0010&\u001a\u00020\u0012H\u0080\u0004\u001a\u0015\u0010$\u001a\u00020(*\u00020\u00122\u0006\u0010&\u001a\u00020(H\u0080\u0004\u001a\u0014\u0010)\u001a\u00020**\u00020+2\u0006\u0010,\u001a\u00020\u0012H\u0000\u001a\f\u0010-\u001a\u00020\u0012*\u00020.H\u0000\u001a\u001a\u0010/\u001a\u00020*2\f\u00100\u001a\b\u0012\u0004\u0012\u00020*01H\u0080\bø\u0001\u0000\u001a\u0014\u00102\u001a\u00020\u0012*\u0002032\u0006\u00104\u001a\u00020%H\u0000\u001a\u0016\u00105\u001a\u00020\u0012*\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u0012H\u0000\u001a\u0012\u00106\u001a\u00020(*\u00020\b2\u0006\u00107\u001a\u00020(\u001a\u0016\u00108\u001a\u00020\u0012*\u0004\u0018\u00010\b2\u0006\u00107\u001a\u00020\u0012H\u0000\u001a\u000e\u00109\u001a\u00020**\u00060:j\u0002`;\u001a\u0014\u0010<\u001a\u00020\u000f*\u00020=2\u0006\u0010>\u001a\u00020?H\u0000\u001a\u0014\u0010@\u001a\u00020**\u00020=2\u0006\u0010A\u001a\u00020?H\u0000\u001a\u0014\u0010B\u001a\u00020**\u00020=2\u0006\u0010C\u001a\u00020?H\u0000\u001a%\u0010D\u001a\u00020*\"\u0004\b\u0000\u0010E*\b\u0012\u0004\u0012\u0002HE0F2\u0006\u0010G\u001a\u0002HEH\u0000¢\u0006\u0002\u0010H\u001a\"\u0010I\u001a\u00020J*\u00060Kj\u0002`L2\u0010\u0010M\u001a\f\u0012\b\u0012\u00060Kj\u0002`L0NH\u0000\u001a;\u0010O\u001a\b\u0012\u0004\u0012\u0002HP0N\"\u0004\b\u0000\u0010P*\b\u0012\u0004\u0012\u0002HP0Q2\u0017\u0010R\u001a\u0013\u0012\u0004\u0012\u0002HP\u0012\u0004\u0012\u00020\u000f0S¢\u0006\u0002\bTH\u0080\bø\u0001\u0000\u001a \u0010V\u001a\u00020*2\u0006\u0010W\u001a\u00020(2\u0006\u0010X\u001a\u00020(2\u0006\u0010Y\u001a\u00020(H\u0000\u001a0\u0010Z\u001a\b\u0012\u0004\u0012\u0002HP0N\"\u0004\b\u0000\u0010P2\f\u0010[\u001a\b\u0012\u0004\u0012\u0002HP0Q2\f\u00104\u001a\b\u0012\u0004\u0012\u0002HP0QH\u0000\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u000e\u0010U\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\\"}, d2 = {"EMPTY_BYTE_ARRAY", "", "UNICODE_BOMS", "Lokio/Options;", "getUNICODE_BOMS", "()Lokio/Options;", "intersect", "", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)[Ljava/lang/String;", "hasIntersection", "", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)Z", "indexOf", "", "value", "([Ljava/lang/String;Ljava/lang/String;Ljava/util/Comparator;)I", "concat", "([Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "indexOfFirstNonAsciiWhitespace", "startIndex", "endIndex", "indexOfLastNonAsciiWhitespace", "trimSubstring", "delimiterOffset", "delimiters", "delimiter", "", "indexOfControlOrNonAscii", "isSensitiveHeader", "name", "parseHexDigit", "and", "", "mask", "", "", "writeMedium", "", "Lokio/BufferedSink;", "medium", "readMedium", "Lokio/BufferedSource;", "ignoreIoExceptions", "block", "Lkotlin/Function0;", "skipAll", "Lokio/Buffer;", "b", "indexOfNonWhitespace", "toLongOrDefault", "defaultValue", "toNonNegativeInt", "closeQuietly", "Ljava/io/Closeable;", "Lokio/Closeable;", "isCivilized", "Lokio/FileSystem;", "file", "Lokio/Path;", "deleteIfExists", "path", "deleteContents", "directory", "addIfAbsent", ExifInterface.LONGITUDE_EAST, "", "element", "(Ljava/util/List;Ljava/lang/Object;)V", "withSuppressed", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "suppressed", "", "filterList", ExifInterface.GPS_DIRECTION_TRUE, "", "predicate", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "USER_AGENT", "checkOffsetAndCount", "arrayLength", "offset", "count", "interleave", HtmlParser.LinkCardElement.TAG, "okhttp"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class _UtilCommonKt {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final Options UNICODE_BOMS = Options.INSTANCE.of(ByteString.INSTANCE.decodeHex("efbbbf"), ByteString.INSTANCE.decodeHex("feff"), ByteString.INSTANCE.decodeHex("fffe0000"), ByteString.INSTANCE.decodeHex("fffe"), ByteString.INSTANCE.decodeHex("0000feff"));
    public static final String USER_AGENT = "okhttp/5.1.0";

    public static final int and(byte b, int i) {
        return b & i;
    }

    public static final int and(short s, int i) {
        return s & i;
    }

    public static final long and(int i, long j) {
        return ((long) i) & j;
    }

    public static final int parseHexDigit(char c) {
        if ('0' <= c && c < ':') {
            return c - '0';
        }
        if ('a' <= c && c < 'g') {
            return c - 'W';
        }
        if ('A' > c || c >= 'G') {
            return -1;
        }
        return c - '7';
    }

    public static final Options getUNICODE_BOMS() {
        return UNICODE_BOMS;
    }

    public static final String[] intersect(String[] strArr, String[] other, Comparator<? super String> comparator) {
        Intrinsics.checkNotNullParameter(strArr, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            for (String str2 : other) {
                if (comparator.compare(str, str2) == 0) {
                    arrayList.add(str);
                    break;
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static final boolean hasIntersection(String[] strArr, String[] strArr2, Comparator<? super String> comparator) {
        Intrinsics.checkNotNullParameter(strArr, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        if (strArr.length != 0 && strArr2 != null && strArr2.length != 0) {
            for (String str : strArr) {
                Iterator it = ArrayIteratorKt.iterator(strArr2);
                while (it.hasNext()) {
                    if (comparator.compare(str, (String) it.next()) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final String[] concat(String[] strArr, String value) {
        Intrinsics.checkNotNullParameter(strArr, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Object[] objArrCopyOf = Arrays.copyOf(strArr, strArr.length + 1);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
        String[] strArr2 = (String[]) objArrCopyOf;
        strArr2[ArraysKt.getLastIndex(strArr2)] = value;
        return strArr2;
    }

    public static /* synthetic */ int indexOfFirstNonAsciiWhitespace$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return indexOfFirstNonAsciiWhitespace(str, i, i2);
    }

    public static final int indexOfFirstNonAsciiWhitespace(String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int indexOfLastNonAsciiWhitespace$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return indexOfLastNonAsciiWhitespace(str, i, i2);
    }

    public static final int indexOfLastNonAsciiWhitespace(String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int i3 = i2 - 1;
        if (i <= i3) {
            while (true) {
                char cCharAt = str.charAt(i3);
                if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                    return i3 + 1;
                }
                if (i3 == i) {
                    break;
                }
                i3--;
            }
        }
        return i;
    }

    public static /* synthetic */ String trimSubstring$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return trimSubstring(str, i, i2);
    }

    public static final String trimSubstring(String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int iIndexOfFirstNonAsciiWhitespace = indexOfFirstNonAsciiWhitespace(str, i, i2);
        String strSubstring = str.substring(iIndexOfFirstNonAsciiWhitespace, indexOfLastNonAsciiWhitespace(str, iIndexOfFirstNonAsciiWhitespace, i2));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public static /* synthetic */ int delimiterOffset$default(String str, String str2, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = str.length();
        }
        return delimiterOffset(str, str2, i, i2);
    }

    public static final int delimiterOffset(String str, String delimiters, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        while (i < i2) {
            if (StringsKt.contains$default((CharSequence) delimiters, str.charAt(i), false, 2, (Object) null)) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static /* synthetic */ int delimiterOffset$default(String str, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = str.length();
        }
        return delimiterOffset(str, c, i, i2);
    }

    public static final int delimiterOffset(String str, char c, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        while (i < i2) {
            if (str.charAt(i) == c) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static final int indexOfControlOrNonAscii(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (Intrinsics.compare((int) cCharAt, 31) <= 0 || Intrinsics.compare((int) cCharAt, 127) >= 0) {
                return i;
            }
        }
        return -1;
    }

    public static final boolean isSensitiveHeader(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return StringsKt.equals(name, "Authorization", true) || StringsKt.equals(name, "Cookie", true) || StringsKt.equals(name, "Proxy-Authorization", true) || StringsKt.equals(name, "Set-Cookie", true);
    }

    public static final void writeMedium(BufferedSink bufferedSink, int i) throws IOException {
        Intrinsics.checkNotNullParameter(bufferedSink, "<this>");
        bufferedSink.writeByte((i >>> 16) & 255);
        bufferedSink.writeByte((i >>> 8) & 255);
        bufferedSink.writeByte(i & 255);
    }

    public static final int readMedium(BufferedSource bufferedSource) throws IOException {
        Intrinsics.checkNotNullParameter(bufferedSource, "<this>");
        return and(bufferedSource.readByte(), 255) | (and(bufferedSource.readByte(), 255) << 16) | (and(bufferedSource.readByte(), 255) << 8);
    }

    public static final void ignoreIoExceptions(Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            block.invoke();
        } catch (IOException unused) {
        }
    }

    public static final int skipAll(Buffer buffer, byte b) throws EOFException {
        Intrinsics.checkNotNullParameter(buffer, "<this>");
        int i = 0;
        while (!buffer.exhausted() && buffer.getByte(0L) == b) {
            i++;
            buffer.readByte();
        }
        return i;
    }

    public static /* synthetic */ int indexOfNonWhitespace$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return indexOfNonWhitespace(str, i);
    }

    public static final int indexOfNonWhitespace(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int length = str.length();
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt != ' ' && cCharAt != '\t') {
                return i;
            }
            i++;
        }
        return str.length();
    }

    public static final long toLongOrDefault(String str, long j) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static final int toNonNegativeInt(String str, int i) {
        if (str != null) {
            try {
                long j = Long.parseLong(str);
                if (j > SieveCacheKt.NodeLinkMask) {
                    return Integer.MAX_VALUE;
                }
                if (j < 0) {
                    return 0;
                }
                return (int) j;
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public static final void closeQuietly(Closeable closeable) {
        Intrinsics.checkNotNullParameter(closeable, "<this>");
        try {
            closeable.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0037  */
    /* JADX WARN: Code duplicated, block: B:26:0x003c  */
    public static final boolean isCivilized(FileSystem fileSystem, Path file) throws Throwable {
        Throwable th;
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        Sink sink = fileSystem.sink(file);
        try {
            try {
                fileSystem.delete(file);
                if (sink != null) {
                    try {
                        sink.close();
                    } catch (Throwable unused) {
                    }
                }
                return true;
            } catch (IOException unused2) {
                Unit unit = Unit.INSTANCE;
                if (sink != null) {
                    try {
                        sink.close();
                    } catch (Throwable th2) {
                        th = th2;
                        if (th == null) {
                            fileSystem.delete(file);
                            return false;
                        }
                        throw th;
                    }
                }
                th = null;
                if (th == null) {
                    fileSystem.delete(file);
                    return false;
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (sink != null) {
                try {
                    sink.close();
                } catch (Throwable th4) {
                    ExceptionsKt.addSuppressed(th3, th4);
                }
            }
            th = th3;
        }
    }

    public static final void deleteIfExists(FileSystem fileSystem, Path path) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        try {
            fileSystem.delete(path);
        } catch (FileNotFoundException unused) {
        }
    }

    public static final void deleteContents(FileSystem fileSystem, Path directory) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(directory, "directory");
        try {
            IOException iOException = null;
            for (Path path : fileSystem.list(directory)) {
                try {
                    if (fileSystem.metadata(path).getIsDirectory()) {
                        deleteContents(fileSystem, path);
                    }
                    fileSystem.delete(path);
                } catch (IOException e) {
                    if (iOException == null) {
                        iOException = e;
                    }
                }
            }
            if (iOException != null) {
                throw iOException;
            }
        } catch (FileNotFoundException unused) {
        }
    }

    public static final <E> void addIfAbsent(List<E> list, E e) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (list.contains(e)) {
            return;
        }
        list.add(e);
    }

    public static final Throwable withSuppressed(Exception exc, List<? extends Exception> suppressed) {
        Intrinsics.checkNotNullParameter(exc, "<this>");
        Intrinsics.checkNotNullParameter(suppressed, "suppressed");
        Iterator<? extends Exception> it = suppressed.iterator();
        while (it.hasNext()) {
            ExceptionsKt.addSuppressed(exc, it.next());
        }
        return exc;
    }

    public static final <T> List<T> filterList(Iterable<? extends T> iterable, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ArrayList arrayListEmptyList = CollectionsKt.emptyList();
        for (T t : iterable) {
            if (predicate.invoke(t).booleanValue()) {
                if (arrayListEmptyList.isEmpty()) {
                    arrayListEmptyList = new ArrayList();
                }
                Intrinsics.checkNotNull(arrayListEmptyList, "null cannot be cast to non-null type kotlin.collections.MutableList<T of okhttp3.internal._UtilCommonKt.filterList>");
                TypeIntrinsics.asMutableList(arrayListEmptyList).add(t);
            }
        }
        return arrayListEmptyList;
    }

    public static final void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException("length=" + j + ", offset=" + j2 + ", count=" + j2);
        }
    }

    public static final <T> List<T> interleave(Iterable<? extends T> a, Iterable<? extends T> b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Iterator<? extends T> it = a.iterator();
        Iterator<? extends T> it2 = b.iterator();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        while (true) {
            if (!it.hasNext() && !it2.hasNext()) {
                return CollectionsKt.build(listCreateListBuilder);
            }
            if (it.hasNext()) {
                listCreateListBuilder.add(it.next());
            }
            if (it2.hasNext()) {
                listCreateListBuilder.add(it2.next());
            }
        }
    }

    public static final int indexOf(String[] strArr, String value, Comparator<String> comparator) {
        Intrinsics.checkNotNullParameter(strArr, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (comparator.compare(strArr[i], value) == 0) {
                return i;
            }
        }
        return -1;
    }
}
