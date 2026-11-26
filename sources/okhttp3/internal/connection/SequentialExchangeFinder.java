package okhttp3.internal.connection;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.IOException;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SequentialExchangeFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lokhttp3/internal/connection/SequentialExchangeFinder;", "Lokhttp3/internal/connection/ExchangeFinder;", "routePlanner", "Lokhttp3/internal/connection/RoutePlanner;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokhttp3/internal/connection/RoutePlanner;)V", "getRoutePlanner", "()Lokhttp3/internal/connection/RoutePlanner;", "find", "Lokhttp3/internal/connection/RealConnection;", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SequentialExchangeFinder implements ExchangeFinder {
    private final RoutePlanner routePlanner;

    public SequentialExchangeFinder(RoutePlanner routePlanner) {
        Intrinsics.checkNotNullParameter(routePlanner, "routePlanner");
        this.routePlanner = routePlanner;
    }

    @Override // okhttp3.internal.connection.ExchangeFinder
    public RoutePlanner getRoutePlanner() {
        return this.routePlanner;
    }

    @Override // okhttp3.internal.connection.ExchangeFinder
    public RealConnection find() throws Throwable {
        IOException iOException = null;
        while (!getRoutePlanner().isCanceled()) {
            try {
                RoutePlanner.Plan plan = getRoutePlanner().plan();
                if (!plan.getIsReady()) {
                    RoutePlanner.ConnectResult result = plan.getResult();
                    if (result.isSuccess()) {
                        result = plan.mo4518connectTlsEtc();
                    }
                    RoutePlanner.Plan nextPlan = result.getNextPlan();
                    Throwable throwable = result.getThrowable();
                    if (throwable != null) {
                        throw throwable;
                    }
                    if (nextPlan != null) {
                        getRoutePlanner().getDeferredPlans().addFirst(nextPlan);
                    }
                }
                return plan.mo4514handleSuccess();
            } catch (IOException e) {
                if (iOException == null) {
                    iOException = e;
                } else {
                    ExceptionsKt.addSuppressed(iOException, e);
                }
                if (!RoutePlanner.hasNext$default(getRoutePlanner(), null, 1, null)) {
                    throw iOException;
                }
            }
        }
        throw new IOException("Canceled");
    }
}
