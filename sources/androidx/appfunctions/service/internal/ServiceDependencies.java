package androidx.appfunctions.service.internal;

import androidx.appfunctions.internal.ClassUtilsKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: ServiceDependencies.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÁ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Landroidx/appfunctions/service/internal/ServiceDependencies;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "aggregatedAppFunctionInvoker", "Landroidx/appfunctions/service/internal/AggregatedAppFunctionInvoker;", "getAggregatedAppFunctionInvoker$appfunctions_service", "()Landroidx/appfunctions/service/internal/AggregatedAppFunctionInvoker;", "aggregatedAppFunctionInvoker$delegate", "Lkotlin/Lazy;", "appfunctions-service"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ServiceDependencies {
    public static final ServiceDependencies INSTANCE = new ServiceDependencies();

    /* JADX INFO: renamed from: aggregatedAppFunctionInvoker$delegate, reason: from kotlin metadata */
    private static final Lazy aggregatedAppFunctionInvoker = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.service.internal.ServiceDependencies$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ServiceDependencies.aggregatedAppFunctionInvoker_delegate$lambda$0();
        }
    });

    private ServiceDependencies() {
    }

    public final AggregatedAppFunctionInvoker getAggregatedAppFunctionInvoker$appfunctions_service() {
        return (AggregatedAppFunctionInvoker) aggregatedAppFunctionInvoker.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AggregatedAppFunctionInvoker aggregatedAppFunctionInvoker_delegate$lambda$0() {
        return (AggregatedAppFunctionInvoker) ClassUtilsKt.findImpl(AggregatedAppFunctionInvoker.class, "$", "_Impl");
    }
}
