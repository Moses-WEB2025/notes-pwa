package androidx.appfunctions.internal;

import android.os.Handler;
import android.os.Looper;
import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.android.HandlerDispatcher;
import kotlinx.coroutines.android.HandlerDispatcherKt;

/* JADX INFO: compiled from: Dispatchers.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\u000b\u0010\u0007¨\u0006\r"}, d2 = {"Landroidx/appfunctions/internal/Dispatchers;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Main", "Lkotlinx/coroutines/CoroutineDispatcher;", "getMain", "()Lkotlinx/coroutines/CoroutineDispatcher;", "Main$delegate", "Lkotlin/Lazy;", "Worker", "getWorker", "Worker$delegate", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Dispatchers {
    public static final Dispatchers INSTANCE = new Dispatchers();

    /* JADX INFO: renamed from: Main$delegate, reason: from kotlin metadata */
    private static final Lazy Main = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dispatchers$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dispatchers.Main_delegate$lambda$0();
        }
    });

    /* JADX INFO: renamed from: Worker$delegate, reason: from kotlin metadata */
    private static final Lazy Worker = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dispatchers$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dispatchers.Worker_delegate$lambda$0();
        }
    });

    private Dispatchers() {
    }

    public final CoroutineDispatcher getMain() {
        return (CoroutineDispatcher) Main.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final HandlerDispatcher Main_delegate$lambda$0() {
        return HandlerDispatcherKt.from$default(new Handler(Looper.getMainLooper()), null, 1, null);
    }

    public final CoroutineDispatcher getWorker() {
        return (CoroutineDispatcher) Worker.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExecutorCoroutineDispatcher Worker_delegate$lambda$0() {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor(...)");
        return ExecutorsKt.from(executorServiceNewSingleThreadExecutor);
    }
}
