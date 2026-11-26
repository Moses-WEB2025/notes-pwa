package okio;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: -JvmPlatform.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\f\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\u0000\u001a\f\u0010\b\u001a\u00060\u0007j\u0002`\tH\u0000\u001a:\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\u00060\u0007j\u0002`\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\rH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000e*\n\u0010\u0004\"\u00020\u00052\u00020\u0005*\n\u0010\u0006\"\u00020\u00072\u00020\u0007*\n\u0010\u000f\"\u00020\u00102\u00020\u0010*\n\u0010\u0011\"\u00020\u00122\u00020\u0012*\n\u0010\u0013\"\u00020\u00142\u00020\u0014*\n\u0010\u0015\"\u00020\u00162\u00020\u0016*\n\u0010\u0017\"\u00020\u00182\u00020\u0018*\n\u0010\u0019\"\u00020\u001a2\u00020\u001a*\n\u0010\u001b\"\u00020\u001c2\u00020\u001c\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001d"}, d2 = {"toUtf8String", "", "", "asUtf8ToByteArray", "ArrayIndexOutOfBoundsException", "Ljava/lang/ArrayIndexOutOfBoundsException;", "Lock", "Ljava/util/concurrent/locks/ReentrantLock;", "newLock", "Lokio/Lock;", "withLock", ExifInterface.GPS_DIRECTION_TRUE, "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "IOException", "Ljava/io/IOException;", "ProtocolException", "Ljava/net/ProtocolException;", "EOFException", "Ljava/io/EOFException;", "FileNotFoundException", "Ljava/io/FileNotFoundException;", "Closeable", "Ljava/io/Closeable;", "Deflater", "Ljava/util/zip/Deflater;", "Inflater", "Ljava/util/zip/Inflater;", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class _JvmPlatformKt {
    public static final String toUtf8String(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return new String(bArr, Charsets.UTF_8);
    }

    public static final byte[] asUtf8ToByteArray(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return bytes;
    }

    public static final ReentrantLock newLock() {
        return new ReentrantLock();
    }

    public static final <T> T withLock(ReentrantLock reentrantLock, Function0<? extends T> action) {
        Intrinsics.checkNotNullParameter(reentrantLock, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        ReentrantLock reentrantLock2 = reentrantLock;
        reentrantLock2.lock();
        try {
            return action.invoke();
        } finally {
            reentrantLock2.unlock();
        }
    }
}
