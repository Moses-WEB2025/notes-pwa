package kotlinx.coroutines.channels;

import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.channels.BufferedChannel", f = "BufferedChannel.kt", i = {0, 0, 0, 0}, l = {R2.color.creation_ai_recommend_unselected_bg}, m = "receiveCatchingOnNoWaiterSuspend-GKJJFZk", n = {"this", "segment", "index", Constants.RANDOM_LONG}, s = {"L$0", "L$1", "I$0", "J$0"})
final class BufferedChannel$receiveCatchingOnNoWaiterSuspend$1 extends ContinuationImpl {
    int I$0;
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BufferedChannel<E> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BufferedChannel$receiveCatchingOnNoWaiterSuspend$1(BufferedChannel<E> bufferedChannel, Continuation<? super BufferedChannel$receiveCatchingOnNoWaiterSuspend$1> continuation) {
        super(continuation);
        this.this$0 = bufferedChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object objM4060receiveCatchingOnNoWaiterSuspendGKJJFZk = this.this$0.m4060receiveCatchingOnNoWaiterSuspendGKJJFZk(null, 0, 0L, this);
        return objM4060receiveCatchingOnNoWaiterSuspendGKJJFZk == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objM4060receiveCatchingOnNoWaiterSuspendGKJJFZk : ChannelResult.m4069boximpl(objM4060receiveCatchingOnNoWaiterSuspendGKJJFZk);
    }
}
