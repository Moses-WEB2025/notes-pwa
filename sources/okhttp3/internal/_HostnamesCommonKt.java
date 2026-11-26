package okhttp3.internal;

import ch.qos.logback.core.CoreConstants;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.io.EOFException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.internal.idn.IdnaMappingTableInstanceKt;
import okhttp3.internal.idn.Punycode;
import okio.Buffer;

/* JADX INFO: compiled from: -HostnamesCommon.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\u001a\f\u0010\u0005\u001a\u00020\u0003*\u00020\u0004H\u0000\u001a\f\u0010\u0006\u001a\u00020\u0003*\u00020\u0004H\u0000\u001a\"\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001a0\u0010\r\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0000\u001a\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\bH\u0000\u001a\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0000\u001a\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\bH\u0002\u001a\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\bH\u0000\u001a\u000e\u0010\u0014\u001a\u0004\u0018\u00010\u0004*\u00020\u0004H\u0000\u001a\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"VERIFY_AS_IP_ADDRESS", "Lkotlin/text/Regex;", "canParseAsIpAddress", "", "", "containsInvalidLabelLengths", "containsInvalidHostnameAsciiCodes", "decodeIpv6", "", HtmlParser.InputElement.TAG, "pos", "", "limit", "decodeIpv4Suffix", RemoteDeviceInfo.KEY_ADDRESS, "addressOffset", "inet6AddressToAscii", "canonicalizeInetAddress", "isMappedIpv4Address", "inet4AddressToAscii", "toCanonicalHost", "idnToAscii", "host", "okhttp"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class _HostnamesCommonKt {
    private static final Regex VERIFY_AS_IP_ADDRESS = new Regex("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    public static final boolean canParseAsIpAddress(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return VERIFY_AS_IP_ADDRESS.matches(str);
    }

    public static final boolean containsInvalidLabelLengths(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int length = str.length();
        if (1 <= length && length < 254) {
            int i = 0;
            while (true) {
                int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, '.', i, false, 4, (Object) null);
                int length2 = iIndexOf$default == -1 ? str.length() - i : iIndexOf$default - i;
                if (1 <= length2 && length2 < 64) {
                    if (iIndexOf$default == -1 || iIndexOf$default == str.length() - 1) {
                        break;
                    }
                    i = iIndexOf$default + 1;
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean containsInvalidHostnameAsciiCodes(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (Intrinsics.compare((int) cCharAt, 31) <= 0 || Intrinsics.compare((int) cCharAt, 127) >= 0 || StringsKt.indexOf$default((CharSequence) " #%/:?@[\\]", cCharAt, 0, false, 6, (Object) null) != -1) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x006b  */
    /* JADX WARN: Code duplicated, block: B:33:0x0075 A[LOOP:1: B:30:0x0069->B:33:0x0075, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:53:0x007b A[EDGE_INSN: B:53:0x007b->B:34:0x007b BREAK  A[LOOP:1: B:30:0x0069->B:33:0x0075], SYNTHETIC] */
    public static final byte[] decodeIpv6(String input, int i, int i2) {
        int i3;
        int i4;
        int hexDigit;
        input = input;
        Intrinsics.checkNotNullParameter(input, "input");
        byte[] bArr = new byte[16];
        int i5 = i;
        int i6 = 0;
        int i7 = -1;
        int i8 = -1;
        while (i5 < i2) {
            if (i6 == 16) {
                return null;
            }
            int i9 = i5 + 2;
            if (i9 <= i2 && StringsKt.startsWith$default(input, "::", i5, false, 4, (Object) null)) {
                if (i7 != -1) {
                    return null;
                }
                i6 += 2;
                if (i9 == i2) {
                    i7 = i6;
                    break;
                }
                i7 = i6;
                i8 = i9;
                i3 = 0;
                i5 = i8;
                while (i5 < i2) {
                    hexDigit = _UtilCommonKt.parseHexDigit(input.charAt(i5));
                    if (hexDigit != -1) {
                        break;
                        break;
                    }
                    i3 = (i3 << 4) + hexDigit;
                    i5++;
                }
                i4 = i5 - i8;
                if (i4 != 0) {
                }
                return null;
            }
            if (i6 != 0) {
                if (!StringsKt.startsWith$default(input, ":", i5, false, 4, (Object) null)) {
                    if (!StringsKt.startsWith$default(input, ".", i5, false, 4, (Object) null) || !decodeIpv4Suffix(input, i8, i2, bArr, i6 - 2)) {
                        return null;
                    }
                    i6 += 2;
                    break;
                }
                i5++;
            }
            i8 = i5;
            i3 = 0;
            i5 = i8;
            while (i5 < i2) {
                hexDigit = _UtilCommonKt.parseHexDigit(input.charAt(i5));
                if (hexDigit != -1) {
                    break;
                }
                i3 = (i3 << 4) + hexDigit;
                i5++;
            }
            i4 = i5 - i8;
            if (i4 != 0 || i4 > 4) {
                return null;
            }
            int i10 = i6 + 1;
            bArr[i6] = (byte) ((i3 >>> 8) & 255);
            i6 += 2;
            bArr[i10] = (byte) (i3 & 255);
        }
        if (i6 != 16) {
            if (i7 == -1) {
                return null;
            }
            ArraysKt.copyInto(bArr, bArr, 16 - (i6 - i7), i7, i6);
            ArraysKt.fill(bArr, (byte) 0, i7, (16 - i6) + i7);
        }
        return bArr;
    }

    public static final boolean decodeIpv4Suffix(String input, int i, int i2, byte[] address, int i3) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(address, "address");
        int i4 = i3;
        while (i < i2) {
            if (i4 == address.length) {
                return false;
            }
            if (i4 != i3) {
                if (input.charAt(i) != '.') {
                    return false;
                }
                i++;
            }
            int i5 = i;
            int i6 = 0;
            while (i5 < i2) {
                char cCharAt = input.charAt(i5);
                if (Intrinsics.compare((int) cCharAt, 48) < 0 || Intrinsics.compare((int) cCharAt, 57) > 0) {
                    break;
                }
                if ((i6 == 0 && i != i5) || (i6 = ((i6 * 10) + cCharAt) - 48) > 255) {
                    return false;
                }
                i5++;
            }
            if (i5 - i == 0) {
                return false;
            }
            address[i4] = (byte) i6;
            i4++;
            i = i5;
        }
        return i4 == i3 + 4;
    }

    public static final String inet6AddressToAscii(byte[] address) {
        Intrinsics.checkNotNullParameter(address, "address");
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < address.length) {
            int i5 = i3;
            while (i5 < 16 && address[i5] == 0 && address[i5 + 1] == 0) {
                i5 += 2;
            }
            int i6 = i5 - i3;
            if (i6 > i4 && i6 >= 4) {
                i = i3;
                i4 = i6;
            }
            i3 = i5 + 2;
        }
        Buffer buffer = new Buffer();
        while (i2 < address.length) {
            if (i2 == i) {
                buffer.writeByte(58);
                i2 += i4;
                if (i2 == 16) {
                    buffer.writeByte(58);
                }
            } else {
                if (i2 > 0) {
                    buffer.writeByte(58);
                }
                buffer.writeHexadecimalUnsignedLong((_UtilCommonKt.and(address[i2], 255) << 8) | _UtilCommonKt.and(address[i2 + 1], 255));
                i2 += 2;
            }
        }
        return buffer.readUtf8();
    }

    public static final byte[] canonicalizeInetAddress(byte[] address) {
        Intrinsics.checkNotNullParameter(address, "address");
        return isMappedIpv4Address(address) ? ArraysKt.sliceArray(address, RangesKt.until(12, 16)) : address;
    }

    private static final boolean isMappedIpv4Address(byte[] bArr) {
        if (bArr.length != 16) {
            return false;
        }
        for (int i = 0; i < 10; i++) {
            if (bArr[i] != 0) {
                return false;
            }
        }
        return bArr[10] == -1 && bArr[11] == -1;
    }

    public static final String inet4AddressToAscii(byte[] address) {
        Intrinsics.checkNotNullParameter(address, "address");
        if (address.length != 4) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return new Buffer().writeDecimalLong(_UtilCommonKt.and(address[0], 255)).writeByte(46).writeDecimalLong(_UtilCommonKt.and(address[1], 255)).writeByte(46).writeDecimalLong(_UtilCommonKt.and(address[2], 255)).writeByte(46).writeDecimalLong(_UtilCommonKt.and(address[3], 255)).readUtf8();
    }

    public static final String toCanonicalHost(String str) {
        byte[] bArrDecodeIpv6;
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) ":", false, 2, (Object) null)) {
            if (StringsKt.startsWith$default(str, "[", false, 2, (Object) null) && StringsKt.endsWith$default(str, "]", false, 2, (Object) null)) {
                bArrDecodeIpv6 = decodeIpv6(str, 1, str.length() - 1);
            } else {
                bArrDecodeIpv6 = decodeIpv6(str, 0, str.length());
            }
            if (bArrDecodeIpv6 == null) {
                return null;
            }
            byte[] bArrCanonicalizeInetAddress = canonicalizeInetAddress(bArrDecodeIpv6);
            if (bArrCanonicalizeInetAddress.length == 16) {
                return inet6AddressToAscii(bArrCanonicalizeInetAddress);
            }
            if (bArrCanonicalizeInetAddress.length == 4) {
                return inet4AddressToAscii(bArrCanonicalizeInetAddress);
            }
            throw new AssertionError("Invalid IPv6 address: '" + str + CoreConstants.SINGLE_QUOTE_CHAR);
        }
        String strIdnToAscii = idnToAscii(str);
        if (strIdnToAscii == null || strIdnToAscii.length() == 0 || containsInvalidHostnameAsciiCodes(strIdnToAscii) || containsInvalidLabelLengths(strIdnToAscii)) {
            return null;
        }
        return strIdnToAscii;
    }

    public static final String idnToAscii(String host) throws EOFException {
        Intrinsics.checkNotNullParameter(host, "host");
        Buffer bufferWriteUtf8 = new Buffer().writeUtf8(host);
        Buffer buffer = new Buffer();
        while (!bufferWriteUtf8.exhausted()) {
            if (!IdnaMappingTableInstanceKt.getIDNA_MAPPING_TABLE().map(bufferWriteUtf8.readUtf8CodePoint(), buffer)) {
                return null;
            }
        }
        bufferWriteUtf8.writeUtf8(_NormalizeJvmKt.normalizeNfc(buffer.readUtf8()));
        String strDecode = Punycode.INSTANCE.decode(bufferWriteUtf8.readUtf8());
        if (strDecode != null && Intrinsics.areEqual(strDecode, _NormalizeJvmKt.normalizeNfc(strDecode))) {
            return Punycode.INSTANCE.encode(strDecode);
        }
        return null;
    }
}
