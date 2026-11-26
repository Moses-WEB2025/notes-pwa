package androidx.appfunctions.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
@DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1", f = "AppSearchAppFunctionReader.kt", i = {}, l = {107, 107}, m = "emit", n = {}, s = {}, v = 1)
final class AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1.AnonymousClass1<T> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1(AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1.AnonymousClass1<? super T> anonymousClass1, Continuation<? super AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Unit) null, (Continuation<? super Unit>) this);
    }
}
