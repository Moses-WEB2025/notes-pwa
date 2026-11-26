package com.miui.notes.component;

import android.text.TextPaint;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AsyncLayout.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "kotlin.jvm.PlatformType", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.notes.component.AsyncLayout$layoutText$1$result$1", f = "AsyncLayout.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AsyncLayout$layoutText$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super CharSequence>, Object> {
    final /* synthetic */ int $bodyStyle;
    final /* synthetic */ PreviewTextView.AbstractLayout $layout;
    final /* synthetic */ TextPaint $paint;
    final /* synthetic */ int $stubStyle;
    final /* synthetic */ CharSequence $text;
    final /* synthetic */ int $titleStyle;
    final /* synthetic */ PreviewTextView $view;
    final /* synthetic */ int $width;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AsyncLayout$layoutText$1$result$1(PreviewTextView.AbstractLayout abstractLayout, PreviewTextView previewTextView, CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4, Continuation<? super AsyncLayout$layoutText$1$result$1> continuation) {
        super(2, continuation);
        this.$layout = abstractLayout;
        this.$view = previewTextView;
        this.$text = charSequence;
        this.$paint = textPaint;
        this.$titleStyle = i;
        this.$bodyStyle = i2;
        this.$stubStyle = i3;
        this.$width = i4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AsyncLayout$layoutText$1$result$1(this.$layout, this.$view, this.$text, this.$paint, this.$titleStyle, this.$bodyStyle, this.$stubStyle, this.$width, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super CharSequence> continuation) {
        return ((AsyncLayout$layoutText$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.$layout.layoutText(this.$view.getContext(), this.$text, this.$paint, this.$titleStyle, this.$bodyStyle, this.$stubStyle, this.$width);
    }
}
