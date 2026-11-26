package com.miui.richeditor.util;

import android.content.Context;
import android.net.Uri;
import com.miui.richeditor.RichEditTextView;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: NoteClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/richeditor/util/SafeSpannableStringBuilder;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1$2$2$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteClipManager$buildSpanStringAndPaste$1$2$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super SafeSpannableStringBuilder>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ RichEditTextView $textView;
    final /* synthetic */ Uri $uri;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteClipManager$buildSpanStringAndPaste$1$2$2$1(Uri uri, RichEditTextView richEditTextView, Context context, Continuation<? super NoteClipManager$buildSpanStringAndPaste$1$2$2$1> continuation) {
        super(2, continuation);
        this.$uri = uri;
        this.$textView = richEditTextView;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteClipManager$buildSpanStringAndPaste$1$2$2$1(this.$uri, this.$textView, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super SafeSpannableStringBuilder> continuation) {
        return ((NoteClipManager$buildSpanStringAndPaste$1$2$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SafeSpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder("");
        NoteClipManager.INSTANCE.appendImage(safeSpannableStringBuilder, this.$uri, this.$textView, this.$context);
        return safeSpannableStringBuilder;
    }
}
