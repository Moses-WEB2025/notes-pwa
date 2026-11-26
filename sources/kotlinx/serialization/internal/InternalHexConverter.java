package kotlinx.serialization.internal;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: Platform.common.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lkotlinx/serialization/internal/InternalHexConverter;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "hexCode", "", "parseHexBinary", "", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "hexToInt", "", "ch", "", "printHexBinary", "data", "lowerCase", "", "toHexString", "n", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class InternalHexConverter {
    public static final InternalHexConverter INSTANCE = new InternalHexConverter();
    private static final String hexCode = "0123456789ABCDEF";

    private final int hexToInt(char ch2) {
        if ('0' <= ch2 && ch2 < ':') {
            return ch2 - '0';
        }
        if ('A' <= ch2 && ch2 < 'G') {
            return ch2 - '7';
        }
        if ('a' > ch2 || ch2 >= 'g') {
            return -1;
        }
        return ch2 - 'W';
    }

    private InternalHexConverter() {
    }

    public final byte[] parseHexBinary(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        int length = s.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("HexBinary string must be even length".toString());
        }
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            int iHexToInt = hexToInt(s.charAt(i));
            int i2 = i + 1;
            int iHexToInt2 = hexToInt(s.charAt(i2));
            if (iHexToInt == -1 || iHexToInt2 == -1) {
                throw new IllegalArgumentException(("Invalid hex chars: " + s.charAt(i) + s.charAt(i2)).toString());
            }
            bArr[i / 2] = (byte) ((iHexToInt << 4) + iHexToInt2);
        }
        return bArr;
    }

    public static /* synthetic */ String printHexBinary$default(InternalHexConverter internalHexConverter, byte[] bArr, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return internalHexConverter.printHexBinary(bArr, z);
    }

    public final String printHexBinary(byte[] data, boolean lowerCase) {
        Intrinsics.checkNotNullParameter(data, "data");
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            sb.append("0123456789ABCDEF".charAt((b >> 4) & 15));
            sb.append("0123456789ABCDEF".charAt(b & 15));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        if (!lowerCase) {
            return string;
        }
        String lowerCase2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
        return lowerCase2;
    }

    public final String toHexString(int n) {
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) (n >> (24 - (i * 8)));
        }
        String strTrimStart = StringsKt.trimStart(printHexBinary(bArr, true), '0');
        if (strTrimStart.length() <= 0) {
            strTrimStart = null;
        }
        return strTrimStart == null ? "0" : strTrimStart;
    }
}
