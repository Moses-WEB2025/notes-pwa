package com.miui.richeditor.util;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: NoteClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lorg/json/JSONObject;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1$2$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteClipManager$buildJsonAndPaste$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super JSONObject>, Object> {
    final /* synthetic */ String $textContent;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteClipManager$buildJsonAndPaste$1$2$1(String str, Continuation<? super NoteClipManager$buildJsonAndPaste$1$2$1> continuation) {
        super(2, continuation);
        this.$textContent = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteClipManager$buildJsonAndPaste$1$2$1(this.$textContent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super JSONObject> continuation) {
        return ((NoteClipManager$buildJsonAndPaste$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws JSONException {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", "text");
        jSONObject.put("content", this.$textContent);
        return jSONObject;
    }
}
