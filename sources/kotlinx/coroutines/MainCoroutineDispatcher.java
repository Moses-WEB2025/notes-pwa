package kotlinx.coroutines;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlinx.coroutines.internal.LimitedDispatcherKt;

/* JADX INFO: compiled from: MainCoroutineDispatcher.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0016J\u001a\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\bH\u0005R\u0012\u0010\u0004\u001a\u00020\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "toString", "", "limitedParallelism", "parallelism", "", "name", "toStringInternalImpl", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class MainCoroutineDispatcher extends CoroutineDispatcher {
    public abstract MainCoroutineDispatcher getImmediate();

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        String stringInternalImpl = toStringInternalImpl();
        return stringInternalImpl == null ? DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) : stringInternalImpl;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public CoroutineDispatcher limitedParallelism(int parallelism, String name) {
        LimitedDispatcherKt.checkParallelism(parallelism);
        return LimitedDispatcherKt.namedOrThis(this, name);
    }

    protected final String toStringInternalImpl() {
        MainCoroutineDispatcher immediate;
        MainCoroutineDispatcher main = Dispatchers.getMain();
        if (this == main) {
            return "Dispatchers.Main";
        }
        try {
            immediate = main.getImmediate();
        } catch (UnsupportedOperationException unused) {
            immediate = null;
        }
        if (this == immediate) {
            return "Dispatchers.Main.immediate";
        }
        return null;
    }
}
