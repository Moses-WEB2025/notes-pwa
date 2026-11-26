package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miui.cloud.CloudPushConstants;
import okio.Base64;
import okio.Buffer;
import okio.SegmentedByteString;
import okio._JvmPlatformKt;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: renamed from: okio.internal.-ByteString, reason: invalid class name */
/* JADX INFO: compiled from: ByteString.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\t\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\n\u001a\u00020\u0002*\u00020\u0002H\u0080\b\u001a\r\u0010\u000b\u001a\u00020\u0002*\u00020\u0002H\u0080\b\u001a\u001d\u0010\f\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0080\b\u001a\u0015\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u000eH\u0080\b\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0002H\u0080\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0002H\u0080\b\u001a\r\u0010\u0016\u001a\u00020\u0015*\u00020\u0002H\u0080\b\u001a-\u0010\u0017\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0080\b\u001a-\u0010\u0017\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0080\b\u001a-\u0010\u001d\u001a\u00020\u001e*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0080\b\u001a\u0015\u0010!\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\"\u001a\u00020\u0002H\u0080\b\u001a\u0015\u0010!\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\"\u001a\u00020\u0015H\u0080\b\u001a\u0015\u0010#\u001a\u00020\u0018*\u00020\u00022\u0006\u0010$\u001a\u00020\u0002H\u0080\b\u001a\u0015\u0010#\u001a\u00020\u0018*\u00020\u00022\u0006\u0010$\u001a\u00020\u0015H\u0080\b\u001a\u001d\u0010%\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u000eH\u0080\b\u001a\u001d\u0010'\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010&\u001a\u00020\u000eH\u0080\b\u001a\u001d\u0010'\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u000eH\u0080\b\u001a\u0017\u0010(\u001a\u00020\u0018*\u00020\u00022\b\u0010\u001a\u001a\u0004\u0018\u00010)H\u0080\b\u001a\r\u0010*\u001a\u00020\u000e*\u00020\u0002H\u0080\b\u001a\u0015\u0010+\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002H\u0080\b\u001a\u0011\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020\u0015H\u0080\b\u001a\u001d\u0010.\u001a\u00020\u0002*\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0080\b\u001a\r\u0010/\u001a\u00020\u0002*\u00020\u0001H\u0080\b\u001a\u000f\u00100\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0080\b\u001a$\u00101\u001a\u00020\u001e*\u00020\u00022\u0006\u00102\u001a\u0002032\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0000\u001a\r\u00104\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\u0018\u00105\u001a\u00020\u000e2\u0006\u00106\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u000eH\u0002\"\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u00068"}, d2 = {"commonUtf8", "", "Lokio/ByteString;", "commonBase64", "commonBase64Url", "HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS", "()[C", "commonHex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonSubstring", "beginIndex", "", "endIndex", "commonGetByte", "", "pos", "commonGetSize", "commonToByteArray", "", "commonInternalArray", "commonRangeEquals", "", "offset", RemoteDeviceInfo.MANUFACTURER_OTHER, "otherOffset", "byteCount", "commonCopyInto", "", TypedValues.AttributesType.S_TARGET, "targetOffset", "commonStartsWith", "prefix", "commonEndsWith", "suffix", "commonIndexOf", "fromIndex", "commonLastIndexOf", "commonEquals", "", "commonHashCode", "commonCompareTo", "commonOf", "data", "commonToByteString", "commonEncodeUtf8", "commonDecodeBase64", "commonWrite", "buffer", "Lokio/Buffer;", "commonToString", "codePointIndexToCharIndex", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "codePointCount", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ByteString {
    private static final char[] HEX_DIGIT_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final String commonUtf8(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        String utf8 = byteString.getUtf8();
        if (utf8 != null) {
            return utf8;
        }
        String utf8String = _JvmPlatformKt.toUtf8String(byteString.internalArray$okio());
        byteString.setUtf8$okio(utf8String);
        return utf8String;
    }

    public static final String commonBase64(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        return Base64.encodeBase64$default(byteString.getData(), null, 1, null);
    }

    public static final String commonBase64Url(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        return Base64.encodeBase64(byteString.getData(), Base64.getBASE64_URL_SAFE());
    }

    public static final char[] getHEX_DIGIT_CHARS() {
        return HEX_DIGIT_CHARS;
    }

    public static final String commonHex(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        char[] cArr = new char[byteString.getData().length * 2];
        int i = 0;
        for (byte b : byteString.getData()) {
            int i2 = i + 1;
            cArr[i] = getHEX_DIGIT_CHARS()[(b >> 4) & 15];
            i += 2;
            cArr[i2] = getHEX_DIGIT_CHARS()[b & 15];
        }
        return StringsKt.concatToString(cArr);
    }

    public static final okio.ByteString commonToAsciiLowercase(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        for (int i = 0; i < byteString.getData().length; i++) {
            byte b = byteString.getData()[i];
            if (b >= 65 && b <= 90) {
                byte[] data = byteString.getData();
                byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                bArrCopyOf[i] = (byte) (b + 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b2 = bArrCopyOf[i2];
                    if (b2 >= 65 && b2 <= 90) {
                        bArrCopyOf[i2] = (byte) (b2 + 32);
                    }
                }
                return new okio.ByteString(bArrCopyOf);
            }
        }
        return byteString;
    }

    public static final okio.ByteString commonToAsciiUppercase(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        for (int i = 0; i < byteString.getData().length; i++) {
            byte b = byteString.getData()[i];
            if (b >= 97 && b <= 122) {
                byte[] data = byteString.getData();
                byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                bArrCopyOf[i] = (byte) (b - 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b2 = bArrCopyOf[i2];
                    if (b2 >= 97 && b2 <= 122) {
                        bArrCopyOf[i2] = (byte) (b2 - 32);
                    }
                }
                return new okio.ByteString(bArrCopyOf);
            }
        }
        return byteString;
    }

    public static final okio.ByteString commonSubstring(okio.ByteString byteString, int i, int i2) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(byteString, i2);
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0".toString());
        }
        if (iResolveDefaultParameter > byteString.getData().length) {
            throw new IllegalArgumentException(("endIndex > length(" + byteString.getData().length + CoreConstants.RIGHT_PARENTHESIS_CHAR).toString());
        }
        if (iResolveDefaultParameter - i >= 0) {
            return (i == 0 && iResolveDefaultParameter == byteString.getData().length) ? byteString : new okio.ByteString(ArraysKt.copyOfRange(byteString.getData(), i, iResolveDefaultParameter));
        }
        throw new IllegalArgumentException("endIndex < beginIndex".toString());
    }

    public static final byte commonGetByte(okio.ByteString byteString, int i) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        return byteString.getData()[i];
    }

    public static final int commonGetSize(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        return byteString.getData().length;
    }

    public static final byte[] commonToByteArray(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        byte[] data = byteString.getData();
        byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
        return bArrCopyOf;
    }

    public static final byte[] commonInternalArray(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        return byteString.getData();
    }

    public static final boolean commonRangeEquals(okio.ByteString byteString, int i, okio.ByteString other, int i2, int i3) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return other.rangeEquals(i2, byteString.getData(), i, i3);
    }

    public static final boolean commonRangeEquals(okio.ByteString byteString, int i, byte[] other, int i2, int i3) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return i >= 0 && i <= byteString.getData().length - i3 && i2 >= 0 && i2 <= other.length - i3 && SegmentedByteString.arrayRangeEquals(byteString.getData(), i, other, i2, i3);
    }

    public static final void commonCopyInto(okio.ByteString byteString, int i, byte[] target, int i2, int i3) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        ArraysKt.copyInto(byteString.getData(), target, i2, i, i3 + i);
    }

    public static final boolean commonStartsWith(okio.ByteString byteString, okio.ByteString prefix) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return byteString.rangeEquals(0, prefix, 0, prefix.size());
    }

    public static final boolean commonStartsWith(okio.ByteString byteString, byte[] prefix) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return byteString.rangeEquals(0, prefix, 0, prefix.length);
    }

    public static final boolean commonEndsWith(okio.ByteString byteString, okio.ByteString suffix) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return byteString.rangeEquals(byteString.size() - suffix.size(), suffix, 0, suffix.size());
    }

    public static final boolean commonEndsWith(okio.ByteString byteString, byte[] suffix) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return byteString.rangeEquals(byteString.size() - suffix.length, suffix, 0, suffix.length);
    }

    public static final int commonIndexOf(okio.ByteString byteString, byte[] other, int i) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int length = byteString.getData().length - other.length;
        int iMax = Math.max(i, 0);
        if (iMax > length) {
            return -1;
        }
        while (!SegmentedByteString.arrayRangeEquals(byteString.getData(), iMax, other, 0, other.length)) {
            if (iMax == length) {
                return -1;
            }
            iMax++;
        }
        return iMax;
    }

    public static final int commonLastIndexOf(okio.ByteString byteString, okio.ByteString other, int i) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return byteString.lastIndexOf(other.internalArray$okio(), i);
    }

    public static final int commonLastIndexOf(okio.ByteString byteString, byte[] other, int i) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        for (int iMin = Math.min(SegmentedByteString.resolveDefaultParameter(byteString, i), byteString.getData().length - other.length); -1 < iMin; iMin--) {
            if (SegmentedByteString.arrayRangeEquals(byteString.getData(), iMin, other, 0, other.length)) {
                return iMin;
            }
        }
        return -1;
    }

    public static final boolean commonEquals(okio.ByteString byteString, Object obj) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        if (obj == byteString) {
            return true;
        }
        if (obj instanceof okio.ByteString) {
            okio.ByteString byteString2 = (okio.ByteString) obj;
            if (byteString2.size() == byteString.getData().length && byteString2.rangeEquals(0, byteString.getData(), 0, byteString.getData().length)) {
                return true;
            }
        }
        return false;
    }

    public static final int commonHashCode(okio.ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        int hashCode = byteString.getHashCode();
        if (hashCode != 0) {
            return hashCode;
        }
        int iHashCode = Arrays.hashCode(byteString.getData());
        byteString.setHashCode$okio(iHashCode);
        return iHashCode;
    }

    public static final int commonCompareTo(okio.ByteString byteString, okio.ByteString other) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int size = byteString.size();
        int size2 = other.size();
        int iMin = Math.min(size, size2);
        for (int i = 0; i < iMin; i++) {
            int i2 = byteString.getByte(i) & 255;
            int i3 = other.getByte(i) & 255;
            if (i2 != i3) {
                return i2 < i3 ? -1 : 1;
            }
        }
        if (size == size2) {
            return 0;
        }
        return size < size2 ? -1 : 1;
    }

    public static final okio.ByteString commonOf(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
        return new okio.ByteString(bArrCopyOf);
    }

    public static final okio.ByteString commonToByteString(byte[] bArr, int i, int i2) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(bArr, i2);
        SegmentedByteString.checkOffsetAndCount(bArr.length, i, iResolveDefaultParameter);
        return new okio.ByteString(ArraysKt.copyOfRange(bArr, i, iResolveDefaultParameter + i));
    }

    public static final okio.ByteString commonEncodeUtf8(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        okio.ByteString byteString = new okio.ByteString(_JvmPlatformKt.asUtf8ToByteArray(str));
        byteString.setUtf8$okio(str);
        return byteString;
    }

    public static final okio.ByteString commonDecodeBase64(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bArrDecodeBase64ToArray = Base64.decodeBase64ToArray(str);
        if (bArrDecodeBase64ToArray != null) {
            return new okio.ByteString(bArrDecodeBase64ToArray);
        }
        return null;
    }

    public static final void commonWrite(okio.ByteString byteString, Buffer buffer, int i, int i2) {
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        buffer.write(byteString.getData(), i, i2);
    }

    public static final String commonToString(okio.ByteString byteString) {
        okio.ByteString byteString2 = byteString;
        Intrinsics.checkNotNullParameter(byteString2, "<this>");
        if (byteString2.getData().length == 0) {
            return "[size=0]";
        }
        int iCodePointIndexToCharIndex = codePointIndexToCharIndex(byteString2.getData(), 64);
        if (iCodePointIndexToCharIndex == -1) {
            if (byteString2.getData().length <= 64) {
                return "[hex=" + byteString2.hex() + ']';
            }
            StringBuilder sbAppend = new StringBuilder("[size=").append(byteString2.getData().length).append(" hex=");
            int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(byteString2, 64);
            if (iResolveDefaultParameter > byteString2.getData().length) {
                throw new IllegalArgumentException(("endIndex > length(" + byteString2.getData().length + CoreConstants.RIGHT_PARENTHESIS_CHAR).toString());
            }
            if (iResolveDefaultParameter < 0) {
                throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }
            if (iResolveDefaultParameter != byteString2.getData().length) {
                byteString2 = new okio.ByteString(ArraysKt.copyOfRange(byteString2.getData(), 0, iResolveDefaultParameter));
            }
            return sbAppend.append(byteString2.hex()).append("…]").toString();
        }
        String strUtf8 = byteString2.utf8();
        String strSubstring = strUtf8.substring(0, iCodePointIndexToCharIndex);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String strReplace$default = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(strSubstring, "\\", "\\\\", false, 4, (Object) null), StringUtils.LF, "\\n", false, 4, (Object) null), StringUtils.CR, "\\r", false, 4, (Object) null);
        if (iCodePointIndexToCharIndex < strUtf8.length()) {
            return "[size=" + byteString2.getData().length + " text=" + strReplace$default + "…]";
        }
        return "[text=" + strReplace$default + ']';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int codePointIndexToCharIndex(byte[] bArr, int i) {
        byte b;
        int i2;
        int length = bArr.length;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < length) {
            byte b2 = bArr[i3];
            if (b2 >= 0) {
                int i6 = i5 + 1;
                if (i5 == i) {
                    break;
                }
                if ((b2 != 10 && b2 != 13 && ((b2 >= 0 && b2 < 32) || (127 <= b2 && b2 < 160))) || b2 == 65533) {
                    return -1;
                }
                i4 += b2 < 65536 ? 1 : 2;
                i3++;
                while (true) {
                    i5 = i6;
                    if (i3 >= length || (b = bArr[i3]) < 0) {
                        break;
                    }
                    i3++;
                    i6 = i5 + 1;
                    if (i5 == i) {
                        return i4;
                    }
                    if ((b != 10 && b != 13 && ((b >= 0 && b < 32) || (127 <= b && b < 160))) || b == 65533) {
                        return -1;
                    }
                    i4 += b < 65536 ? 1 : 2;
                }
                Unit unit = Unit.INSTANCE;
            } else if ((b2 >> 5) == -2) {
                int i7 = i3 + 1;
                if (length <= i7) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                byte b3 = bArr[i7];
                if ((b3 & 192) != 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                int i8 = (b2 << 6) ^ (b3 ^ 3968);
                if (i8 < 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                int i9 = i5 + 1;
                if (i5 == i) {
                    break;
                }
                if ((i8 != 10 && i8 != 13 && ((i8 >= 0 && i8 < 32) || (127 <= i8 && i8 < 160))) || i8 == 65533) {
                    return -1;
                }
                i4 += i8 < 65536 ? 1 : 2;
                Unit unit2 = Unit.INSTANCE;
                i3 += 2;
                Unit unit3 = Unit.INSTANCE;
                i5 = i9;
            } else if ((b2 >> 4) == -2) {
                int i10 = i3 + 2;
                if (length <= i10) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                byte b4 = bArr[i3 + 1];
                if ((b4 & 192) != 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                byte b5 = bArr[i10];
                if ((b5 & 192) != 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                int i11 = (b2 << 12) ^ ((b5 ^ (-123008)) ^ (b4 << 6));
                if (i11 < 2048) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                if (55296 <= i11 && i11 < 57344) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                i2 = i5 + 1;
                if (i5 == i) {
                    break;
                }
                if ((i11 != 10 && i11 != 13 && ((i11 >= 0 && i11 < 32) || (127 <= i11 && i11 < 160))) || i11 == 65533) {
                    return -1;
                }
                i4 += i11 < 65536 ? 1 : 2;
                Unit unit4 = Unit.INSTANCE;
                i3 += 3;
                Unit unit5 = Unit.INSTANCE;
                i5 = i2;
            } else {
                if ((b2 >> 3) != -2) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                int i12 = i3 + 3;
                if (length <= i12) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                byte b6 = bArr[i3 + 1];
                if ((b6 & 192) != 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                byte b7 = bArr[i3 + 2];
                if ((b7 & 192) != 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                byte b8 = bArr[i12];
                if ((b8 & 192) != 128) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                int i13 = (b2 << 18) ^ (((b8 ^ 3678080) ^ (b7 << 6)) ^ (b6 << 12));
                if (i13 > 1114111) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                if (55296 <= i13 && i13 < 57344) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                if (i13 < 65536) {
                    if (i5 == i) {
                        break;
                    }
                    return -1;
                }
                i2 = i5 + 1;
                if (i5 == i) {
                    break;
                }
                if ((i13 != 10 && i13 != 13 && ((i13 >= 0 && i13 < 32) || (127 <= i13 && i13 < 160))) || i13 == 65533) {
                    return -1;
                }
                i4 += i13 < 65536 ? 1 : 2;
                Unit unit6 = Unit.INSTANCE;
                i3 += 4;
                Unit unit7 = Unit.INSTANCE;
                i5 = i2;
            }
        }
        return i4;
    }
}
