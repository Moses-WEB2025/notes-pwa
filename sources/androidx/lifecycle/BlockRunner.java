package androidx.lifecycle;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.joran.action.Action;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import defpackage.R2;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: CoroutineLiveData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002Bl\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00127\u0010\u0005\u001a3\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006j\b\u0012\u0004\u0012\u00028\u0000`\u000b¢\u0006\u0002\b\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0011¢\u0006\u0004\b\u0012\u0010\u0013J\b\u0010\u0018\u001a\u00020\tH\u0007J\b\u0010\u0019\u001a\u00020\tH\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000RA\u0010\u0005\u001a3\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006j\b\u0012\u0004\u0012\u00028\u0000`\u000b¢\u0006\u0002\b\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Landroidx/lifecycle/BlockRunner;", ExifInterface.GPS_DIRECTION_TRUE, "", "liveData", "Landroidx/lifecycle/CoroutineLiveData;", "block", "Lkotlin/Function2;", "Landroidx/lifecycle/LiveDataScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "Landroidx/lifecycle/Block;", "timeoutInMs", "", Action.SCOPE_ATTRIBUTE, "Lkotlinx/coroutines/CoroutineScope;", "onDone", "Lkotlin/Function0;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/lifecycle/CoroutineLiveData;Lkotlin/jvm/functions/Function2;JLkotlinx/coroutines/CoroutineScope;Lkotlin/jvm/functions/Function0;)V", "Lkotlin/jvm/functions/Function2;", "runningJob", "Lkotlinx/coroutines/Job;", "cancellationJob", "maybeRun", ToDoStat.CANCEL, "lifecycle-livedata_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class BlockRunner<T> {
    private final Function2<LiveDataScope<T>, Continuation<? super Unit>, Object> block;
    private Job cancellationJob;
    private final CoroutineLiveData<T> liveData;
    private final Function0<Unit> onDone;
    private Job runningJob;
    private final CoroutineScope scope;
    private final long timeoutInMs;

    /* JADX WARN: Multi-variable type inference failed */
    public BlockRunner(CoroutineLiveData<T> liveData, Function2<? super LiveDataScope<T>, ? super Continuation<? super Unit>, ? extends Object> block, long j, CoroutineScope scope, Function0<Unit> onDone) {
        Intrinsics.checkNotNullParameter(liveData, "liveData");
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(onDone, "onDone");
        this.liveData = liveData;
        this.block = block;
        this.timeoutInMs = j;
        this.scope = scope;
        this.onDone = onDone;
    }

    public final void maybeRun() {
        Job job = this.cancellationJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.cancellationJob = null;
        if (this.runningJob != null) {
            return;
        }
        this.runningJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01871(this, null), 3, null);
    }

    /* JADX INFO: renamed from: androidx.lifecycle.BlockRunner$maybeRun$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CoroutineLiveData.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
    @DebugMetadata(c = "androidx.lifecycle.BlockRunner$maybeRun$1", f = "CoroutineLiveData.kt", i = {}, l = {168}, m = "invokeSuspend", n = {}, s = {})
    static final class C01871 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ BlockRunner<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01871(BlockRunner<T> blockRunner, Continuation<? super C01871> continuation) {
            super(2, continuation);
            this.this$0 = blockRunner;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01871 c01871 = new C01871(this.this$0, continuation);
            c01871.L$0 = obj;
            return c01871;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01871) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LiveDataScopeImpl liveDataScopeImpl = new LiveDataScopeImpl(((BlockRunner) this.this$0).liveData, ((CoroutineScope) this.L$0).getCoroutineContext());
                Function2 function2 = ((BlockRunner) this.this$0).block;
                this.label = 1;
                if (function2.invoke(liveDataScopeImpl, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ((BlockRunner) this.this$0).onDone.invoke();
            return Unit.INSTANCE;
        }
    }

    public final void cancel() {
        if (this.cancellationJob != null) {
            throw new IllegalStateException("Cancel call cannot happen without a maybeRun".toString());
        }
        this.cancellationJob = BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getMain().getImmediate(), null, new AnonymousClass1(this, null), 2, null);
    }

    /* JADX INFO: renamed from: androidx.lifecycle.BlockRunner$cancel$1, reason: invalid class name */
    /* JADX INFO: compiled from: CoroutineLiveData.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
    @DebugMetadata(c = "androidx.lifecycle.BlockRunner$cancel$1", f = "CoroutineLiveData.kt", i = {}, l = {R2.array.ai_tag_top_item_gradient_border_colors}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;
        final /* synthetic */ BlockRunner<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(BlockRunner<T> blockRunner, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = blockRunner;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(((BlockRunner) this.this$0).timeoutInMs, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (!((BlockRunner) this.this$0).liveData.hasActiveObservers()) {
                Job job = ((BlockRunner) this.this$0).runningJob;
                if (job != null) {
                    Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
                }
                ((BlockRunner) this.this$0).runningJob = null;
            }
            return Unit.INSTANCE;
        }
    }
}
