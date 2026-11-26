package com.miui.doodle.document;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: DocumentWriter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/doodle/document/DocumentWriter$ZipItem;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.document.DocumentWriter$write$1$4$1", f = "DocumentWriter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class DocumentWriter$write$1$4$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super DocumentWriter.ZipItem>, Object> {
    final /* synthetic */ boolean $ignoreCache;
    final /* synthetic */ String $layerBasePath;
    final /* synthetic */ LayerBitmap $layerBitmap;
    int label;
    final /* synthetic */ DocumentWriter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DocumentWriter$write$1$4$1(DocumentWriter documentWriter, LayerBitmap layerBitmap, String str, boolean z, Continuation<? super DocumentWriter$write$1$4$1> continuation) {
        super(2, continuation);
        this.this$0 = documentWriter;
        this.$layerBitmap = layerBitmap;
        this.$layerBasePath = str;
        this.$ignoreCache = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DocumentWriter$write$1$4$1(this.this$0, this.$layerBitmap, this.$layerBasePath, this.$ignoreCache, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super DocumentWriter.ZipItem> continuation) {
        return ((DocumentWriter$write$1$4$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return this.this$0.addBitmapEntry(this.$layerBitmap.getId(), this.$layerBasePath, this.$layerBitmap.getBitmap(), this.$ignoreCache, this.$layerBitmap.getCacheImageKey());
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
