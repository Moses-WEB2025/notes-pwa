package androidx.lifecycle;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;

/* JADX INFO: compiled from: WithLifecycleState.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/lifecycle/LifecycleDestroyedException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "lifecycle-runtime_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class LifecycleDestroyedException extends CancellationException {
    public LifecycleDestroyedException() {
        super(null);
    }
}
