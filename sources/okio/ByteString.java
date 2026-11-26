package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Language;
import com.xiaomi.miai.SDKConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okio.internal.ByteStringNonJs;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: ByteString.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0005\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 ^2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002:\u0001^B\u0011\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0016J\u0006\u0010\u0019\u001a\u00020\u0000J\u0006\u0010\u001a\u001a\u00020\u0000J\u0006\u0010\u001b\u001a\u00020\u0000J\u0006\u0010\u001c\u001a\u00020\u0000J\u0015\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u001fJ\u0010\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0000H\u0016J\u0010\u0010\"\u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0000H\u0016J\u0010\u0010#\u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0000H\u0016J\u001d\u0010$\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u0000H\u0010¢\u0006\u0002\b%J\b\u0010&\u001a\u00020\u0010H\u0016J\b\u0010'\u001a\u00020\u0010H\u0016J\b\u0010(\u001a\u00020\u0000H\u0016J\b\u0010)\u001a\u00020\u0000H\u0016J\u001c\u0010*\u001a\u00020\u00002\b\b\u0002\u0010+\u001a\u00020\n2\b\b\u0002\u0010,\u001a\u00020\nH\u0017J\u0015\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\nH\u0010¢\u0006\u0002\b0J\u0016\u00101\u001a\u00020.2\u0006\u00102\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b3J\r\u00105\u001a\u00020\nH\u0010¢\u0006\u0002\b6J\b\u00107\u001a\u00020\u0004H\u0016J\r\u00108\u001a\u00020\u0004H\u0010¢\u0006\u0002\b9J\b\u0010:\u001a\u00020;H\u0016J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J%\u0010<\u001a\u00020=2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\n2\u0006\u0010C\u001a\u00020\nH\u0010¢\u0006\u0002\bDJ(\u0010E\u001a\u00020F2\u0006\u0010B\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u00002\u0006\u0010H\u001a\u00020\n2\u0006\u0010C\u001a\u00020\nH\u0016J(\u0010E\u001a\u00020F2\u0006\u0010B\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u00042\u0006\u0010H\u001a\u00020\n2\u0006\u0010C\u001a\u00020\nH\u0016J,\u0010I\u001a\u00020=2\b\b\u0002\u0010B\u001a\u00020\n2\u0006\u0010J\u001a\u00020\u00042\b\b\u0002\u0010K\u001a\u00020\n2\u0006\u0010C\u001a\u00020\nH\u0016J\u000e\u0010L\u001a\u00020F2\u0006\u0010M\u001a\u00020\u0000J\u000e\u0010L\u001a\u00020F2\u0006\u0010M\u001a\u00020\u0004J\u000e\u0010N\u001a\u00020F2\u0006\u0010O\u001a\u00020\u0000J\u000e\u0010N\u001a\u00020F2\u0006\u0010O\u001a\u00020\u0004J\u001a\u0010P\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u00002\b\b\u0002\u0010Q\u001a\u00020\nH\u0007J\u001a\u0010P\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u00042\b\b\u0002\u0010Q\u001a\u00020\nH\u0017J\u001a\u0010R\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u00002\b\b\u0002\u0010Q\u001a\u00020\nH\u0007J\u001a\u0010R\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u00042\b\b\u0002\u0010Q\u001a\u00020\nH\u0017J\u0013\u0010S\u001a\u00020F2\b\u0010G\u001a\u0004\u0018\u00010TH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0011\u0010U\u001a\u00020\n2\u0006\u0010G\u001a\u00020\u0000H\u0096\u0002J\b\u0010V\u001a\u00020\u0010H\u0016J\u0010\u0010W\u001a\u00020=2\u0006\u0010X\u001a\u00020YH\u0002J\u0010\u0010Z\u001a\u00020=2\u0006\u0010>\u001a\u00020[H\u0002J\u0015\u00103\u001a\u00020.2\u0006\u00102\u001a\u00020\nH\u0007¢\u0006\u0002\b\\J\r\u00104\u001a\u00020\nH\u0007¢\u0006\u0002\b]R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u00104\u001a\u00020\n8G¢\u0006\u0006\u001a\u0004\b4\u0010\f¨\u0006_"}, d2 = {"Lokio/ByteString;", "Ljava/io/Serializable;", "", "data", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "([B)V", "getData$okio", "()[B", "hashCode", "", "getHashCode$okio", "()I", "setHashCode$okio", "(I)V", "utf8", "", "getUtf8$okio", "()Ljava/lang/String;", "setUtf8$okio", "(Ljava/lang/String;)V", TypedValues.Custom.S_STRING, "charset", "Ljava/nio/charset/Charset;", "base64", "md5", "sha1", "sha256", "sha512", "digest", "algorithm", "digest$okio", "hmacSha1", "key", "hmacSha256", "hmacSha512", "hmac", "hmac$okio", "base64Url", "hex", "toAsciiLowercase", "toAsciiUppercase", "substring", "beginIndex", "endIndex", "internalGet", "", "pos", "internalGet$okio", CallMethod.METHOD_GET, "index", "getByte", "size", "getSize", "getSize$okio", "toByteArray", "internalArray", "internalArray$okio", "asByteBuffer", "Ljava/nio/ByteBuffer;", "write", "", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "offset", "byteCount", "write$okio", "rangeEquals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "otherOffset", "copyInto", TypedValues.AttributesType.S_TARGET, "targetOffset", "startsWith", "prefix", "endsWith", "suffix", "indexOf", "fromIndex", "lastIndexOf", "equals", "", "compareTo", "toString", "readObject", Language.LA_IN, "Ljava/io/ObjectInputStream;", "writeObject", "Ljava/io/ObjectOutputStream;", "-deprecated_getByte", "-deprecated_size", "Companion", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public class ByteString implements Serializable, Comparable<ByteString> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1;
    private final byte[] data;
    private transient int hashCode;
    private transient String utf8;

    @JvmStatic
    public static final ByteString decodeBase64(String str) {
        return INSTANCE.decodeBase64(str);
    }

    @JvmStatic
    public static final ByteString decodeHex(String str) {
        return INSTANCE.decodeHex(str);
    }

    @JvmStatic
    public static final ByteString encodeString(String str, Charset charset) {
        return INSTANCE.encodeString(str, charset);
    }

    @JvmStatic
    public static final ByteString encodeUtf8(String str) {
        return INSTANCE.encodeUtf8(str);
    }

    @JvmStatic
    public static final ByteString of(ByteBuffer byteBuffer) {
        return INSTANCE.of(byteBuffer);
    }

    @JvmStatic
    public static final ByteString of(byte... bArr) {
        return INSTANCE.of(bArr);
    }

    @JvmStatic
    public static final ByteString of(byte[] bArr, int i, int i2) {
        return INSTANCE.of(bArr, i, i2);
    }

    @JvmStatic
    public static final ByteString read(InputStream inputStream, int i) throws IOException {
        return INSTANCE.read(inputStream, i);
    }

    public final int indexOf(ByteString other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return indexOf$default(this, other, 0, 2, (Object) null);
    }

    public final int indexOf(byte[] other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return indexOf$default(this, other, 0, 2, (Object) null);
    }

    public final int lastIndexOf(ByteString other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return lastIndexOf$default(this, other, 0, 2, (Object) null);
    }

    public final int lastIndexOf(byte[] other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return lastIndexOf$default(this, other, 0, 2, (Object) null);
    }

    public final ByteString substring() {
        return substring$default(this, 0, 0, 3, null);
    }

    public final ByteString substring(int i) {
        return substring$default(this, i, 0, 2, null);
    }

    public ByteString(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    /* JADX INFO: renamed from: getData$okio, reason: from getter */
    public final byte[] getData() {
        return this.data;
    }

    /* JADX INFO: renamed from: getHashCode$okio, reason: from getter */
    public final int getHashCode() {
        return this.hashCode;
    }

    public final void setHashCode$okio(int i) {
        this.hashCode = i;
    }

    /* JADX INFO: renamed from: getUtf8$okio, reason: from getter */
    public final String getUtf8() {
        return this.utf8;
    }

    public final void setUtf8$okio(String str) {
        this.utf8 = str;
    }

    public String string(Charset charset) {
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(this.data, charset);
    }

    public final ByteString md5() {
        return digest$okio("MD5");
    }

    public final ByteString sha1() {
        return digest$okio("SHA-1");
    }

    public final ByteString sha256() {
        return digest$okio(SDKConstants.DigestAlgorithm.SHA_256);
    }

    public final ByteString sha512() {
        return digest$okio("SHA-512");
    }

    public ByteString digest$okio(String algorithm) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(this.data, 0, size());
        byte[] bArrDigest = messageDigest.digest();
        Intrinsics.checkNotNull(bArrDigest);
        return new ByteString(bArrDigest);
    }

    public ByteString hmacSha1(ByteString key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return hmac$okio("HmacSHA1", key);
    }

    public ByteString hmacSha256(ByteString key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return hmac$okio("HmacSHA256", key);
    }

    public ByteString hmacSha512(ByteString key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return hmac$okio("HmacSHA512", key);
    }

    public ByteString hmac$okio(String algorithm, ByteString key) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(key, "key");
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.toByteArray(), algorithm));
            byte[] bArrDoFinal = mac.doFinal(this.data);
            Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
            return new ByteString(bArrDoFinal);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static /* synthetic */ ByteString substring$default(ByteString byteString, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: substring");
        }
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = SegmentedByteString.getDEFAULT__ByteString_size();
        }
        return byteString.substring(i, i2);
    }

    public final byte getByte(int index) {
        return internalGet$okio(index);
    }

    public final int size() {
        return getSize$okio();
    }

    public ByteBuffer asByteBuffer() {
        ByteBuffer byteBufferAsReadOnlyBuffer = ByteBuffer.wrap(this.data).asReadOnlyBuffer();
        Intrinsics.checkNotNullExpressionValue(byteBufferAsReadOnlyBuffer, "asReadOnlyBuffer(...)");
        return byteBufferAsReadOnlyBuffer;
    }

    public void write(OutputStream out) throws IOException {
        Intrinsics.checkNotNullParameter(out, "out");
        out.write(this.data);
    }

    public void write$okio(Buffer buffer, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        okio.internal.ByteString.commonWrite(this, buffer, offset, byteCount);
    }

    public static /* synthetic */ void copyInto$default(ByteString byteString, int i, byte[] bArr, int i2, int i3, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: copyInto");
        }
        if ((i4 & 1) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        byteString.copyInto(i, bArr, i2, i3);
    }

    public static /* synthetic */ int indexOf$default(ByteString byteString, ByteString byteString2, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return byteString.indexOf(byteString2, i);
    }

    public final int indexOf(ByteString other, int fromIndex) {
        Intrinsics.checkNotNullParameter(other, "other");
        return indexOf(other.internalArray$okio(), fromIndex);
    }

    public static /* synthetic */ int indexOf$default(ByteString byteString, byte[] bArr, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return byteString.indexOf(bArr, i);
    }

    public static /* synthetic */ int lastIndexOf$default(ByteString byteString, ByteString byteString2, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
        }
        if ((i2 & 2) != 0) {
            i = SegmentedByteString.getDEFAULT__ByteString_size();
        }
        return byteString.lastIndexOf(byteString2, i);
    }

    public static /* synthetic */ int lastIndexOf$default(ByteString byteString, byte[] bArr, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
        }
        if ((i2 & 2) != 0) {
            i = SegmentedByteString.getDEFAULT__ByteString_size();
        }
        return byteString.lastIndexOf(bArr, i);
    }

    private final void readObject(ObjectInputStream in) throws IllegalAccessException, NoSuchFieldException, IOException {
        ByteString byteString = INSTANCE.read(in, in.readInt());
        Field declaredField = ByteString.class.getDeclaredField("data");
        declaredField.setAccessible(true);
        declaredField.set(this, byteString.data);
    }

    private final void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(this.data.length);
        out.write(this.data);
    }

    /* JADX INFO: compiled from: ByteString.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u00020\n\"\u00020\u000bH\u0007J%\u0010\f\u001a\u00020\u0007*\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eH\u0007¢\u0006\u0002\b\bJ\u0011\u0010\f\u001a\u00020\u0007*\u00020\u0010H\u0007¢\u0006\u0002\b\bJ\f\u0010\u0011\u001a\u00020\u0007*\u00020\u0012H\u0007J\u001b\u0010\u0013\u001a\u00020\u0007*\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007¢\u0006\u0002\b\u0016J\u000e\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0012H\u0007J\f\u0010\u0018\u001a\u00020\u0007*\u00020\u0012H\u0007J\u0019\u0010\u0019\u001a\u00020\u0007*\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u000eH\u0007¢\u0006\u0002\b\u001bJ\u0017\u0010\u0017\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001c\u001a\u00020\u0012H\u0007¢\u0006\u0002\b\u001dJ\u0015\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0012H\u0007¢\u0006\u0002\b\u001eJ\u001d\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007¢\u0006\u0002\b\u001fJ\u0015\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0012H\u0007¢\u0006\u0002\b J\u0015\u0010\b\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0010H\u0007¢\u0006\u0002\b\"J%\u0010\b\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0007¢\u0006\u0002\b\"J\u001d\u0010\u001b\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u000eH\u0007¢\u0006\u0002\b%R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lokio/ByteString$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "serialVersionUID", "", "EMPTY", "Lokio/ByteString;", "of", "data", "", "", "toByteString", "offset", "", "byteCount", "Ljava/nio/ByteBuffer;", "encodeUtf8", "", "encode", "charset", "Ljava/nio/charset/Charset;", "encodeString", "decodeBase64", "decodeHex", "readByteString", "Ljava/io/InputStream;", "read", TypedValues.Custom.S_STRING, "-deprecated_decodeBase64", "-deprecated_decodeHex", "-deprecated_encodeString", "-deprecated_encodeUtf8", "buffer", "-deprecated_of", "array", "inputstream", "-deprecated_read", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ ByteString of$default(Companion companion, byte[] bArr, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = SegmentedByteString.getDEFAULT__ByteString_size();
            }
            return companion.of(bArr, i, i2);
        }

        private Companion() {
        }

        @JvmStatic
        public final ByteString of(ByteBuffer byteBuffer) {
            Intrinsics.checkNotNullParameter(byteBuffer, "<this>");
            byte[] bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
            return new ByteString(bArr);
        }

        public static /* synthetic */ ByteString encodeString$default(Companion companion, String str, Charset charset, int i, Object obj) {
            if ((i & 1) != 0) {
                charset = Charsets.UTF_8;
            }
            return companion.encodeString(str, charset);
        }

        @JvmStatic
        public final ByteString encodeString(String str, Charset charset) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            Intrinsics.checkNotNullParameter(charset, "charset");
            byte[] bytes = str.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            return new ByteString(bytes);
        }

        @JvmStatic
        public final ByteString read(InputStream inputStream, int i) throws IOException {
            Intrinsics.checkNotNullParameter(inputStream, "<this>");
            if (i < 0) {
                throw new IllegalArgumentException(("byteCount < 0: " + i).toString());
            }
            byte[] bArr = new byte[i];
            int i2 = 0;
            while (i2 < i) {
                int i3 = inputStream.read(bArr, i2, i - i2);
                if (i3 == -1) {
                    throw new EOFException();
                }
                i2 += i3;
            }
            return new ByteString(bArr);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.decodeBase64()", imports = {"okio.ByteString.Companion.decodeBase64"}))
        /* JADX INFO: renamed from: -deprecated_decodeBase64, reason: not valid java name */
        public final ByteString m4531deprecated_decodeBase64(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return decodeBase64(string);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.decodeHex()", imports = {"okio.ByteString.Companion.decodeHex"}))
        /* JADX INFO: renamed from: -deprecated_decodeHex, reason: not valid java name */
        public final ByteString m4532deprecated_decodeHex(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return decodeHex(string);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.encode(charset)", imports = {"okio.ByteString.Companion.encode"}))
        /* JADX INFO: renamed from: -deprecated_encodeString, reason: not valid java name */
        public final ByteString m4533deprecated_encodeString(String string, Charset charset) {
            Intrinsics.checkNotNullParameter(string, "string");
            Intrinsics.checkNotNullParameter(charset, "charset");
            return encodeString(string, charset);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.encodeUtf8()", imports = {"okio.ByteString.Companion.encodeUtf8"}))
        /* JADX INFO: renamed from: -deprecated_encodeUtf8, reason: not valid java name */
        public final ByteString m4534deprecated_encodeUtf8(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return encodeUtf8(string);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "buffer.toByteString()", imports = {"okio.ByteString.Companion.toByteString"}))
        /* JADX INFO: renamed from: -deprecated_of, reason: not valid java name */
        public final ByteString m4535deprecated_of(ByteBuffer buffer) {
            Intrinsics.checkNotNullParameter(buffer, "buffer");
            return of(buffer);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "array.toByteString(offset, byteCount)", imports = {"okio.ByteString.Companion.toByteString"}))
        /* JADX INFO: renamed from: -deprecated_of, reason: not valid java name */
        public final ByteString m4536deprecated_of(byte[] array, int offset, int byteCount) {
            Intrinsics.checkNotNullParameter(array, "array");
            return of(array, offset, byteCount);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "inputstream.readByteString(byteCount)", imports = {"okio.ByteString.Companion.readByteString"}))
        /* JADX INFO: renamed from: -deprecated_read, reason: not valid java name */
        public final ByteString m4537deprecated_read(InputStream inputstream, int byteCount) {
            Intrinsics.checkNotNullParameter(inputstream, "inputstream");
            return read(inputstream, byteCount);
        }

        @JvmStatic
        public final ByteString of(byte... data) {
            Intrinsics.checkNotNullParameter(data, "data");
            byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
            Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
            return new ByteString(bArrCopyOf);
        }

        @JvmStatic
        public final ByteString of(byte[] bArr, int i, int i2) {
            Intrinsics.checkNotNullParameter(bArr, "<this>");
            int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(bArr, i2);
            SegmentedByteString.checkOffsetAndCount(bArr.length, i, iResolveDefaultParameter);
            return new ByteString(ArraysKt.copyOfRange(bArr, i, iResolveDefaultParameter + i));
        }

        @JvmStatic
        public final ByteString encodeUtf8(String str) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            ByteString byteString = new ByteString(_JvmPlatformKt.asUtf8ToByteArray(str));
            byteString.setUtf8$okio(str);
            return byteString;
        }

        @JvmStatic
        public final ByteString decodeBase64(String str) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            byte[] bArrDecodeBase64ToArray = Base64.decodeBase64ToArray(str);
            if (bArrDecodeBase64ToArray != null) {
                return new ByteString(bArrDecodeBase64ToArray);
            }
            return null;
        }

        @JvmStatic
        public final ByteString decodeHex(String str) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            if (str.length() % 2 != 0) {
                throw new IllegalArgumentException(("Unexpected hex string: " + str).toString());
            }
            int length = str.length() / 2;
            byte[] bArr = new byte[length];
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                bArr[i] = (byte) ((ByteStringNonJs.decodeHexDigit(str.charAt(i2)) << 4) + ByteStringNonJs.decodeHexDigit(str.charAt(i2 + 1)));
            }
            return new ByteString(bArr);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to operator function", replaceWith = @ReplaceWith(expression = "this[index]", imports = {}))
    /* JADX INFO: renamed from: -deprecated_getByte, reason: not valid java name */
    public final byte m4529deprecated_getByte(int index) {
        return getByte(index);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = {}))
    /* JADX INFO: renamed from: -deprecated_size, reason: not valid java name */
    public final int m4530deprecated_size() {
        return size();
    }

    public String utf8() {
        String utf8 = getUtf8();
        if (utf8 != null) {
            return utf8;
        }
        String utf8String = _JvmPlatformKt.toUtf8String(internalArray$okio());
        setUtf8$okio(utf8String);
        return utf8String;
    }

    public String base64() {
        return Base64.encodeBase64$default(getData(), null, 1, null);
    }

    public String base64Url() {
        return Base64.encodeBase64(getData(), Base64.getBASE64_URL_SAFE());
    }

    public String hex() {
        char[] cArr = new char[getData().length * 2];
        int i = 0;
        for (byte b : getData()) {
            int i2 = i + 1;
            cArr[i] = okio.internal.ByteString.getHEX_DIGIT_CHARS()[(b >> 4) & 15];
            i += 2;
            cArr[i2] = okio.internal.ByteString.getHEX_DIGIT_CHARS()[b & 15];
        }
        return StringsKt.concatToString(cArr);
    }

    public ByteString toAsciiLowercase() {
        for (int i = 0; i < getData().length; i++) {
            byte b = getData()[i];
            if (b >= 65 && b <= 90) {
                byte[] data = getData();
                byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                bArrCopyOf[i] = (byte) (b + 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b2 = bArrCopyOf[i2];
                    if (b2 >= 65 && b2 <= 90) {
                        bArrCopyOf[i2] = (byte) (b2 + 32);
                    }
                }
                return new ByteString(bArrCopyOf);
            }
        }
        return this;
    }

    public ByteString toAsciiUppercase() {
        for (int i = 0; i < getData().length; i++) {
            byte b = getData()[i];
            if (b >= 97 && b <= 122) {
                byte[] data = getData();
                byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                bArrCopyOf[i] = (byte) (b - 32);
                for (int i2 = i + 1; i2 < bArrCopyOf.length; i2++) {
                    byte b2 = bArrCopyOf[i2];
                    if (b2 >= 97 && b2 <= 122) {
                        bArrCopyOf[i2] = (byte) (b2 - 32);
                    }
                }
                return new ByteString(bArrCopyOf);
            }
        }
        return this;
    }

    public ByteString substring(int beginIndex, int endIndex) {
        int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(this, endIndex);
        if (beginIndex < 0) {
            throw new IllegalArgumentException("beginIndex < 0".toString());
        }
        if (iResolveDefaultParameter > getData().length) {
            throw new IllegalArgumentException(("endIndex > length(" + getData().length + CoreConstants.RIGHT_PARENTHESIS_CHAR).toString());
        }
        if (iResolveDefaultParameter - beginIndex >= 0) {
            return (beginIndex == 0 && iResolveDefaultParameter == getData().length) ? this : new ByteString(ArraysKt.copyOfRange(getData(), beginIndex, iResolveDefaultParameter));
        }
        throw new IllegalArgumentException("endIndex < beginIndex".toString());
    }

    public byte internalGet$okio(int pos) {
        return getData()[pos];
    }

    public int getSize$okio() {
        return getData().length;
    }

    public byte[] toByteArray() {
        byte[] data = getData();
        byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
        return bArrCopyOf;
    }

    public byte[] internalArray$okio() {
        return getData();
    }

    public boolean rangeEquals(int offset, ByteString other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(other, "other");
        return other.rangeEquals(otherOffset, getData(), offset, byteCount);
    }

    public boolean rangeEquals(int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(other, "other");
        return offset >= 0 && offset <= getData().length - byteCount && otherOffset >= 0 && otherOffset <= other.length - byteCount && SegmentedByteString.arrayRangeEquals(getData(), offset, other, otherOffset, byteCount);
    }

    public void copyInto(int offset, byte[] target, int targetOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(target, "target");
        ArraysKt.copyInto(getData(), target, targetOffset, offset, byteCount + offset);
    }

    public final boolean startsWith(ByteString prefix) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return rangeEquals(0, prefix, 0, prefix.size());
    }

    public final boolean startsWith(byte[] prefix) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return rangeEquals(0, prefix, 0, prefix.length);
    }

    public final boolean endsWith(ByteString suffix) {
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return rangeEquals(size() - suffix.size(), suffix, 0, suffix.size());
    }

    public final boolean endsWith(byte[] suffix) {
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return rangeEquals(size() - suffix.length, suffix, 0, suffix.length);
    }

    public int indexOf(byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter(other, "other");
        int length = getData().length - other.length;
        int iMax = Math.max(fromIndex, 0);
        if (iMax > length) {
            return -1;
        }
        while (!SegmentedByteString.arrayRangeEquals(getData(), iMax, other, 0, other.length)) {
            if (iMax == length) {
                return -1;
            }
            iMax++;
        }
        return iMax;
    }

    public final int lastIndexOf(ByteString other, int fromIndex) {
        Intrinsics.checkNotNullParameter(other, "other");
        return lastIndexOf(other.internalArray$okio(), fromIndex);
    }

    public int lastIndexOf(byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (int iMin = Math.min(SegmentedByteString.resolveDefaultParameter(this, fromIndex), getData().length - other.length); -1 < iMin; iMin--) {
            if (SegmentedByteString.arrayRangeEquals(getData(), iMin, other, 0, other.length)) {
                return iMin;
            }
        }
        return -1;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof ByteString) {
            ByteString byteString = (ByteString) other;
            if (byteString.size() == getData().length && byteString.rangeEquals(0, getData(), 0, getData().length)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = getHashCode();
        if (hashCode != 0) {
            return hashCode;
        }
        int iHashCode = Arrays.hashCode(getData());
        setHashCode$okio(iHashCode);
        return iHashCode;
    }

    @Override // java.lang.Comparable
    public int compareTo(ByteString other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int size = size();
        int size2 = other.size();
        int iMin = Math.min(size, size2);
        for (int i = 0; i < iMin; i++) {
            int i2 = getByte(i) & 255;
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

    public String toString() {
        if (getData().length == 0) {
            return "[size=0]";
        }
        int iCodePointIndexToCharIndex = okio.internal.ByteString.codePointIndexToCharIndex(getData(), 64);
        if (iCodePointIndexToCharIndex == -1) {
            if (getData().length <= 64) {
                return "[hex=" + hex() + ']';
            }
            StringBuilder sbAppend = new StringBuilder("[size=").append(getData().length).append(" hex=");
            ByteString byteString = this;
            int iResolveDefaultParameter = SegmentedByteString.resolveDefaultParameter(byteString, 64);
            if (iResolveDefaultParameter > byteString.getData().length) {
                throw new IllegalArgumentException(("endIndex > length(" + byteString.getData().length + CoreConstants.RIGHT_PARENTHESIS_CHAR).toString());
            }
            if (iResolveDefaultParameter < 0) {
                throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }
            if (iResolveDefaultParameter != byteString.getData().length) {
                byteString = new ByteString(ArraysKt.copyOfRange(byteString.getData(), 0, iResolveDefaultParameter));
            }
            return sbAppend.append(byteString.hex()).append("…]").toString();
        }
        String strUtf8 = utf8();
        String strSubstring = strUtf8.substring(0, iCodePointIndexToCharIndex);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String strReplace$default = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(strSubstring, "\\", "\\\\", false, 4, (Object) null), StringUtils.LF, "\\n", false, 4, (Object) null), StringUtils.CR, "\\r", false, 4, (Object) null);
        if (iCodePointIndexToCharIndex < strUtf8.length()) {
            return "[size=" + getData().length + " text=" + strReplace$default + "…]";
        }
        return "[text=" + strReplace$default + ']';
    }
}
