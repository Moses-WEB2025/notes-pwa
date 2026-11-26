package okhttp3.internal.publicsuffix;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.platform.Platform;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/* JADX INFO: compiled from: BasePublicSuffixList.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b \u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\b\u0010\u001a\u001a\u00020\u0012H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001b"}, d2 = {"Lokhttp3/internal/publicsuffix/BasePublicSuffixList;", "Lokhttp3/internal/publicsuffix/PublicSuffixList;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "listRead", "Ljava/util/concurrent/atomic/AtomicBoolean;", "readCompleteLatch", "Ljava/util/concurrent/CountDownLatch;", "bytes", "Lokio/ByteString;", "getBytes", "()Lokio/ByteString;", "setBytes", "(Lokio/ByteString;)V", "exceptionBytes", "getExceptionBytes", "setExceptionBytes", "readTheList", "", "listSource", "Lokio/Source;", "ensureLoaded", "path", "", "getPath", "()Ljava/lang/Object;", "readTheListUninterruptibly", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BasePublicSuffixList implements PublicSuffixList {
    public ByteString bytes;
    public ByteString exceptionBytes;
    private final AtomicBoolean listRead = new AtomicBoolean(false);
    private final CountDownLatch readCompleteLatch = new CountDownLatch(1);

    public abstract Object getPath();

    public abstract Source listSource();

    @Override // okhttp3.internal.publicsuffix.PublicSuffixList
    public ByteString getBytes() {
        ByteString byteString = this.bytes;
        if (byteString != null) {
            return byteString;
        }
        Intrinsics.throwUninitializedPropertyAccessException("bytes");
        return null;
    }

    public void setBytes(ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<set-?>");
        this.bytes = byteString;
    }

    @Override // okhttp3.internal.publicsuffix.PublicSuffixList
    public ByteString getExceptionBytes() {
        ByteString byteString = this.exceptionBytes;
        if (byteString != null) {
            return byteString;
        }
        Intrinsics.throwUninitializedPropertyAccessException("exceptionBytes");
        return null;
    }

    public void setExceptionBytes(ByteString byteString) {
        Intrinsics.checkNotNullParameter(byteString, "<set-?>");
        this.exceptionBytes = byteString;
    }

    private final void readTheList() throws IOException {
        try {
            BufferedSource bufferedSourceBuffer = Okio.buffer(listSource());
            try {
                BufferedSource bufferedSource = bufferedSourceBuffer;
                ByteString byteString = bufferedSource.readByteString(bufferedSource.readInt());
                ByteString byteString2 = bufferedSource.readByteString(bufferedSource.readInt());
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedSourceBuffer, null);
                synchronized (this) {
                    Intrinsics.checkNotNull(byteString);
                    setBytes(byteString);
                    Intrinsics.checkNotNull(byteString2);
                    setExceptionBytes(byteString2);
                    Unit unit2 = Unit.INSTANCE;
                }
                this.readCompleteLatch.countDown();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedSourceBuffer, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            this.readCompleteLatch.countDown();
            throw th3;
        }
    }

    @Override // okhttp3.internal.publicsuffix.PublicSuffixList
    public void ensureLoaded() {
        if (!this.listRead.get() && this.listRead.compareAndSet(false, true)) {
            readTheListUninterruptibly();
        } else {
            try {
                this.readCompleteLatch.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        if (this.bytes == null) {
            throw new IllegalStateException(("Unable to load " + getPath() + " resource.").toString());
        }
    }

    private final void readTheListUninterruptibly() {
        boolean z = false;
        while (true) {
            try {
                try {
                    readTheList();
                    break;
                } catch (InterruptedIOException unused) {
                    Thread.interrupted();
                    z = true;
                } catch (IOException e) {
                    Platform.INSTANCE.get().log("Failed to read public suffix list", 5, e);
                    if (!z) {
                        return;
                    }
                }
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (!z) {
            return;
        }
        Thread.currentThread().interrupt();
    }
}
