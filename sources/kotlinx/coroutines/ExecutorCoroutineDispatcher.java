package kotlinx.coroutines;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.Closeable;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: Executors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u0000 \r2\u00020\u00012\u00020\u00022\u00060\u0003j\u0002`\u0004:\u0001\rB\u0007¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/io/Closeable;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", SplitLayout.TAG_CLOSE, "", "Key", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class ExecutorCoroutineDispatcher extends CoroutineDispatcher implements Closeable, AutoCloseable {

    /* JADX INFO: renamed from: Key, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public abstract void close();

    public abstract Executor getExecutor();

    /* JADX INFO: renamed from: kotlinx.coroutines.ExecutorCoroutineDispatcher$Key, reason: from kotlin metadata */
    /* JADX INFO: compiled from: Executors.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/ExecutorCoroutineDispatcher$Key;", "Lkotlin/coroutines/AbstractCoroutineContextKey;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion extends AbstractCoroutineContextKey<CoroutineDispatcher, ExecutorCoroutineDispatcher> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
            super(CoroutineDispatcher.INSTANCE, new Function1() { // from class: kotlinx.coroutines.ExecutorCoroutineDispatcher$Key$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ExecutorCoroutineDispatcher.Companion._init_$lambda$0((CoroutineContext.Element) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final ExecutorCoroutineDispatcher _init_$lambda$0(CoroutineContext.Element element) {
            if (element instanceof ExecutorCoroutineDispatcher) {
                return (ExecutorCoroutineDispatcher) element;
            }
            return null;
        }
    }
}
