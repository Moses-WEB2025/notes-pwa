package androidx.appfunctions.service.internal;

import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: AggregatedAppFunctionInvoker.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J6\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\n2\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0015H\u0086@¢\u0006\u0002\u0010\u0016R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Landroidx/appfunctions/service/internal/AggregatedAppFunctionInvoker;", "Landroidx/appfunctions/service/internal/AppFunctionInvoker;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "invokers", "", "getInvokers", "()Ljava/util/List;", "supportedFunctionIds", "", "", "getSupportedFunctionIds", "()Ljava/util/Set;", "supportedFunctionIds$delegate", "Lkotlin/Lazy;", "unsafeInvoke", "", "appFunctionContext", "Landroidx/appfunctions/AppFunctionContext;", "functionIdentifier", "parameters", "", "(Landroidx/appfunctions/AppFunctionContext;Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "appfunctions-service"}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class AggregatedAppFunctionInvoker implements AppFunctionInvoker {

    /* JADX INFO: renamed from: supportedFunctionIds$delegate, reason: from kotlin metadata */
    private final Lazy supportedFunctionIds = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.service.internal.AggregatedAppFunctionInvoker$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AggregatedAppFunctionInvoker.supportedFunctionIds_delegate$lambda$0(this.f$0);
        }
    });

    public abstract List<AppFunctionInvoker> getInvokers();

    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    public final Set<String> getSupportedFunctionIds() {
        return (Set) this.supportedFunctionIds.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set supportedFunctionIds_delegate$lambda$0(AggregatedAppFunctionInvoker aggregatedAppFunctionInvoker) {
        if (aggregatedAppFunctionInvoker.getInvokers().isEmpty()) {
            return SetsKt.emptySet();
        }
        List<AppFunctionInvoker> invokers = aggregatedAppFunctionInvoker.getInvokers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(invokers, 10));
        Iterator<T> it = invokers.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionInvoker) it.next()).getSupportedFunctionIds());
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = SetsKt.plus((Set) next, (Iterable) it2.next());
        }
        return (Set) next;
    }

    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    public final Object unsafeInvoke(AppFunctionContext appFunctionContext, String str, Map<String, ? extends Object> map, Continuation<Object> continuation) throws AppFunctionFunctionNotFoundException {
        for (AppFunctionInvoker appFunctionInvoker : getInvokers()) {
            if (appFunctionInvoker.getSupportedFunctionIds().contains(str)) {
                return appFunctionInvoker.unsafeInvoke(appFunctionContext, str, map, continuation);
            }
        }
        throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
    }
}
