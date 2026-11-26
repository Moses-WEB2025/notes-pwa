package okio.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;

/* JADX INFO: renamed from: okio.internal.-ByteStringNonJs, reason: invalid class name */
/* JADX INFO: compiled from: ByteStringNonJs.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"commonDecodeHex", "Lokio/ByteString;", "", "decodeHexDigit", "", "c", "", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ByteStringNonJs {
    public static final ByteString commonDecodeHex(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException(("Unexpected hex string: " + str).toString());
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) ((decodeHexDigit(str.charAt(i2)) << 4) + decodeHexDigit(str.charAt(i2 + 1)));
        }
        return new ByteString(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int decodeHexDigit(char c) {
        if ('0' <= c && c < ':') {
            return c - '0';
        }
        if ('a' <= c && c < 'g') {
            return c - 'W';
        }
        if ('A' > c || c >= 'G') {
            throw new IllegalArgumentException("Unexpected hex digit: " + c);
        }
        return c - '7';
    }
}
