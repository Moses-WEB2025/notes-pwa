package kotlin.time;

import com.google.common.net.HttpHeaders;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Clocks.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u0005¨\u0006\u0006"}, d2 = {"asClock", "Lkotlin/time/Clock;", "Lkotlin/time/TimeSource;", HttpHeaders.ReferrerPolicyValues.ORIGIN, "Lkotlin/time/Instant;", "fromTimeSource", "kotlin-stdlib"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class ClocksKt {
    public static final Clock fromTimeSource(final TimeSource timeSource, final Instant origin) {
        Intrinsics.checkNotNullParameter(timeSource, "<this>");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new Clock(timeSource, origin) { // from class: kotlin.time.ClocksKt$asClock$1
            final /* synthetic */ Instant $origin;
            private final TimeMark startMark;

            {
                this.$origin = origin;
                this.startMark = timeSource.markNow();
            }

            @Override // kotlin.time.Clock
            public Instant now() {
                return this.$origin.m3998plusLRDsOJo(this.startMark.mo3888elapsedNowUwyO8pc());
            }
        };
    }
}
