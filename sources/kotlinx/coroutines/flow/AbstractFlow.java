package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.internal.SafeCollector;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u001c\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0086@¢\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH¦@¢\u0006\u0002\u0010\n¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/flow/AbstractFlow;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/CancellableFlow;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectSafely", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class AbstractFlow<T> implements Flow<T>, CancellableFlow<T> {

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.AbstractFlow$collect$1, reason: invalid class name */
    /* JADX INFO: compiled from: Flow.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.AbstractFlow", f = "Flow.kt", i = {0}, l = {R2.array.setting_phone_note_layout_way}, m = "collect", n = {"safeCollector"}, s = {"L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ AbstractFlow<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(AbstractFlow<T> abstractFlow, Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
            this.this$0 = abstractFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.collect(null, this);
        }
    }

    public abstract Object collectSafely(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation);

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        SafeCollector safeCollector;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(this, continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(this, continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            safeCollector = (SafeCollector) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                safeCollector.releaseIntercepted();
                return Unit.INSTANCE;
            } catch (Throwable th) {
                th = th;
                safeCollector.releaseIntercepted();
                throw th;
            }
        }
        ResultKt.throwOnFailure(obj);
        SafeCollector safeCollector2 = new SafeCollector(flowCollector, anonymousClass1.getContext());
        try {
            anonymousClass1.L$0 = safeCollector2;
            anonymousClass1.label = 1;
            if (collectSafely(safeCollector2, anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            safeCollector = safeCollector2;
            safeCollector.releaseIntercepted();
            return Unit.INSTANCE;
        } catch (Throwable th2) {
            th = th2;
            safeCollector = safeCollector2;
            safeCollector.releaseIntercepted();
            throw th;
        }
    }
}
