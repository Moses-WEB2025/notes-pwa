package com.miui.doodle.feature.draw;

import com.miui.doodle.document.DoodleDocument;
import com.miui.doodle.document.Layer;
import com.miui.doodle.document.LayerBitmap;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: DoodleView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.feature.draw.DoodleView$cacheDoodleSnapshot$1$1", f = "DoodleView.kt", i = {0, 0, 0, 0}, l = {R2.attr.drag_edge}, m = "invokeSuspend", n = {"$this$runBlocking", "jobs", "midTime", "$i$a$-let-DoodleView$cacheDoodleSnapshot$1$1$2"}, s = {"L$0", "L$2", "J$0", "I$0"})
final class DoodleView$cacheDoodleSnapshot$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ DoodleDocument $doc;
    final /* synthetic */ long $startTime;
    int I$0;
    long J$0;
    long J$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ DoodleView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DoodleView$cacheDoodleSnapshot$1$1(DoodleView doodleView, long j, DoodleDocument doodleDocument, Continuation<? super DoodleView$cacheDoodleSnapshot$1$1> continuation) {
        super(2, continuation);
        this.this$0 = doodleView;
        this.$startTime = j;
        this.$doc = doodleDocument;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DoodleView$cacheDoodleSnapshot$1$1 doodleView$cacheDoodleSnapshot$1$1 = new DoodleView$cacheDoodleSnapshot$1$1(this.this$0, this.$startTime, this.$doc, continuation);
        doodleView$cacheDoodleSnapshot$1$1.L$0 = obj;
        return doodleView$cacheDoodleSnapshot$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DoodleView$cacheDoodleSnapshot$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws InterruptedException {
        DoodleView doodleView;
        long j;
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Iterator<T> it = this.this$0.getLaseSnapShotJobs().iterator();
            while (it.hasNext()) {
                Deferred deferred = (Deferred) it.next();
                if (!deferred.isCompleted()) {
                    Job.DefaultImpls.cancel$default((Job) deferred, (CancellationException) null, 1, (Object) null);
                }
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            Logger.INSTANCE.d(DoodleView.TAG, "doodle do cache cancel laseSnapShotJobs cost " + (jCurrentTimeMillis - this.$startTime));
            this.this$0.setDoCacheSaving(true);
            this.this$0.getLaseSnapShotJobs().clear();
            List<Deferred<Object>> laseSnapShotJobs = this.this$0.getLaseSnapShotJobs();
            DoodleDocument doodleDocument = this.$doc;
            long j2 = this.$startTime;
            DoodleView doodleView2 = this.this$0;
            for (Layer layer : CollectionsKt.toList(doodleDocument.getLayers())) {
                DoodleView doodleView3 = doodleView2;
                laseSnapShotJobs.add(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getDefault(), null, new DoodleView$cacheDoodleSnapshot$1$1$2$1$1(layer, null), 2, null));
                List list = CollectionsKt.toList(layer.getLayerBitmapList());
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : list) {
                    if (((LayerBitmap) obj2).getBitmap() != null) {
                        arrayList.add(obj2);
                    }
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    laseSnapShotJobs.add(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getDefault(), null, new DoodleView$cacheDoodleSnapshot$1$1$2$1$3$1(doodleView3, (LayerBitmap) it2.next(), null), 2, null));
                }
                doodleView2 = doodleView3;
            }
            DoodleView doodleView4 = doodleView2;
            this.L$0 = coroutineScope;
            this.L$1 = doodleView4;
            this.L$2 = SpillingKt.nullOutSpilledVariable(laseSnapShotJobs);
            this.J$0 = jCurrentTimeMillis;
            this.J$1 = j2;
            this.I$0 = 0;
            this.label = 1;
            if (AwaitKt.awaitAll(laseSnapShotJobs, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            doodleView = doodleView4;
            j = j2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j = this.J$1;
            doodleView = (DoodleView) this.L$1;
            ResultKt.throwOnFailure(obj);
        }
        if (!CoroutineScopeKt.isActive(coroutineScope)) {
            Logger.INSTANCE.d(DoodleView.TAG, "cacheDoodleSnapshot cancel");
        } else {
            Logger.INSTANCE.d(DoodleView.TAG, "doodle do cache end cost " + (System.currentTimeMillis() - j) + " and saveTempDoodle");
            doodleView.saveTempDoodle();
        }
        return Unit.INSTANCE;
    }
}
