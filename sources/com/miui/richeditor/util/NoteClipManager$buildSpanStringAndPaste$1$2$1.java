package com.miui.richeditor.util;

import android.text.TextUtils;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: NoteClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/richeditor/util/SafeSpannableStringBuilder;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildSpanStringAndPaste$1$2$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteClipManager$buildSpanStringAndPaste$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super SafeSpannableStringBuilder>, Object> {
    final /* synthetic */ NoteClipManager.IClipData $clipdata;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteClipManager$buildSpanStringAndPaste$1$2$1(NoteClipManager.IClipData iClipData, Continuation<? super NoteClipManager$buildSpanStringAndPaste$1$2$1> continuation) {
        super(2, continuation);
        this.$clipdata = iClipData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteClipManager$buildSpanStringAndPaste$1$2$1(this.$clipdata, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super SafeSpannableStringBuilder> continuation) {
        return ((NoteClipManager$buildSpanStringAndPaste$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws IOException {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String string = ((NoteClipManager.TextElement) this.$clipdata).getMText().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String str = string;
        if (TextUtils.isEmpty(str) || StringsKt.trim((CharSequence) str).toString().length() == 0) {
            return null;
        }
        SafeSpannableStringBuilder safeSpannableStringBuilder = new SafeSpannableStringBuilder("");
        safeSpannableStringBuilder.append((CharSequence) str);
        safeSpannableStringBuilder.append('\n');
        return safeSpannableStringBuilder;
    }
}
