package kotlinx.coroutines.guava;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: ListenableFuture.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\rH\u0016J\r\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001e\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0096\u0002¢\u0006\u0002\u0010\u001bJ\u0017\u0010\u001c\u001a\u00028\u00002\b\u0010\u000f\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\u00020\u001f2\n\u0010 \u001a\u00060!j\u0002`\"2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\rH\u0016J\u0010\u0010&\u001a\u00020\r2\u0006\u0010'\u001a\u00020\rH\u0016J\b\u0010(\u001a\u00020)H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u0007\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010\t \n*\f\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lkotlinx/coroutines/guava/JobListenableFuture;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/google/common/util/concurrent/ListenableFuture;", "jobToCancel", "Lkotlinx/coroutines/Job;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlinx/coroutines/Job;)V", "auxFuture", "Lcom/google/common/util/concurrent/SettableFuture;", "", "kotlin.jvm.PlatformType", "Lcom/google/common/util/concurrent/SettableFuture;", "auxFutureIsFailed", "", "complete", "result", "(Ljava/lang/Object;)Z", "completeExceptionallyOrCancel", Constants.TIMESTAMP, "", "isCancelled", CallMethod.METHOD_GET, "()Ljava/lang/Object;", "timeout", "", "unit", "Ljava/util/concurrent/TimeUnit;", "(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;", "getInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "addListener", "", "listener", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "executor", "Ljava/util/concurrent/Executor;", "isDone", ToDoStat.CANCEL, "mayInterruptIfRunning", "toString", "", "kotlinx-coroutines-guava"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class JobListenableFuture<T> implements ListenableFuture<T> {
    private final SettableFuture<Object> auxFuture = SettableFuture.create();
    private boolean auxFutureIsFailed;
    private final Job jobToCancel;

    public JobListenableFuture(Job job) {
        this.jobToCancel = job;
    }

    public final boolean complete(T result) {
        return this.auxFuture.set(result);
    }

    public final boolean completeExceptionallyOrCancel(Throwable t) {
        if (t instanceof CancellationException) {
            return this.auxFuture.set(new Cancelled((CancellationException) t));
        }
        boolean exception = this.auxFuture.setException(t);
        if (exception) {
            this.auxFutureIsFailed = true;
        }
        return exception;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        boolean z;
        if (this.auxFuture.isCancelled()) {
            return true;
        }
        if (isDone() && !this.auxFutureIsFailed) {
            try {
                z = Uninterruptibles.getUninterruptibly(this.auxFuture) instanceof Cancelled;
            } catch (CancellationException unused) {
                z = true;
            } catch (ExecutionException unused2) {
                this.auxFutureIsFailed = true;
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.concurrent.Future
    public T get() {
        return getInternal(this.auxFuture.get());
    }

    @Override // java.util.concurrent.Future
    public T get(long timeout, TimeUnit unit) {
        return getInternal(this.auxFuture.get(timeout, unit));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final T getInternal(Object result) throws Throwable {
        if (result instanceof Cancelled) {
            throw new CancellationException().initCause(((Cancelled) result).exception);
        }
        return result;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable listener, Executor executor) {
        this.auxFuture.addListener(listener, executor);
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.auxFuture.isDone();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!this.auxFuture.cancel(mayInterruptIfRunning)) {
            return false;
        }
        Job.DefaultImpls.cancel$default(this.jobToCancel, (CancellationException) null, 1, (Object) null);
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (isDone()) {
            try {
                Object uninterruptibly = Uninterruptibles.getUninterruptibly(this.auxFuture);
                if (uninterruptibly instanceof Cancelled) {
                    sb.append("CANCELLED, cause=[" + ((Cancelled) uninterruptibly).exception + ']');
                } else {
                    sb.append("SUCCESS, result=[" + uninterruptibly + ']');
                }
            } catch (CancellationException unused) {
                sb.append("CANCELLED");
            } catch (ExecutionException e) {
                sb.append("FAILURE, cause=[" + e.getCause() + ']');
            } catch (Throwable th) {
                sb.append("UNKNOWN, cause=[" + th.getClass() + " thrown from get()]");
            }
        } else {
            sb.append("PENDING, delegate=[" + this.auxFuture + ']');
        }
        sb.append(']');
        return sb.toString();
    }
}
