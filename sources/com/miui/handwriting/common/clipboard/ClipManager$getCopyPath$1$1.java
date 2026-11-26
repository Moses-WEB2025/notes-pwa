package com.miui.handwriting.common.clipboard;

import com.xiaomi.onetrack.api.ba;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: ClipManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.handwriting.common.clipboard.ClipManager$getCopyPath$1$1", f = "ClipManager.kt", i = {0, 0}, l = {R2.attr.actionBarSendIcon}, m = "invokeSuspend", n = {"$this$runBlocking", ba.e}, s = {"L$0", "L$1"})
final class ClipManager$getCopyPath$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $it;
    final /* synthetic */ Ref.ObjectRef<String> $targetPath;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ClipManager$getCopyPath$1$1(Ref.ObjectRef<String> objectRef, String str, Continuation<? super ClipManager$getCopyPath$1$1> continuation) {
        super(2, continuation);
        this.$targetPath = objectRef;
        this.$it = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ClipManager$getCopyPath$1$1 clipManager$getCopyPath$1$1 = new ClipManager$getCopyPath$1$1(this.$targetPath, this.$it, continuation);
        clipManager$getCopyPath$1$1.L$0 = obj;
        return clipManager$getCopyPath$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ClipManager$getCopyPath$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref.ObjectRef<String> objectRef;
        T t;
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Deferred deferredAsync$default = BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new AnonymousClass1(this.$it, null), 2, null);
            Ref.ObjectRef<String> objectRef2 = this.$targetPath;
            this.L$0 = SpillingKt.nullOutSpilledVariable(coroutineScope);
            this.L$1 = SpillingKt.nullOutSpilledVariable(deferredAsync$default);
            this.L$2 = objectRef2;
            this.label = 1;
            Object objAwait = deferredAsync$default.await(this);
            if (objAwait == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef = objectRef2;
            t = objAwait;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef = (Ref.ObjectRef) this.L$2;
            ResultKt.throwOnFailure(obj);
            t = obj;
        }
        objectRef.element = t;
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.clipboard.ClipManager$getCopyPath$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: ClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.clipboard.ClipManager$getCopyPath$1$1$1", f = "ClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
        final /* synthetic */ String $it;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$it = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$it, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                String strDownloadImageToTemp = ClipManager.INSTANCE.downloadImageToTemp(this.$it);
                return strDownloadImageToTemp != null ? strDownloadImageToTemp : "";
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
