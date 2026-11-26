package com.miui.notes.ai.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.common.tool.Logger;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AINotesViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u0004H\n"}, d2 = {"<anonymous>", "Lcom/miui/notes/ai/request/AIResult;", "", "Lcom/miui/notes/ai/request/LanguageItem;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$getSupportedLanguageList$1$result$1", f = "AINotesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AINotesViewModel$getSupportedLanguageList$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super AIResult<? extends List<? extends LanguageItem>>>, Object> {
    int label;

    AINotesViewModel$getSupportedLanguageList$1$result$1(Continuation<? super AINotesViewModel$getSupportedLanguageList$1$result$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AINotesViewModel$getSupportedLanguageList$1$result$1(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super AIResult<? extends List<? extends LanguageItem>>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super AIResult<? extends List<LanguageItem>>>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super AIResult<? extends List<LanguageItem>>> continuation) {
        return ((AINotesViewModel$getSupportedLanguageList$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            ResponseData responseData = (ResponseData) new Gson().fromJson(NoteAIRequest.INSTANCE.get(AINotesViewModel.INSTANCE.getURL_LANGUAGE_LIST(), null, null), new TypeToken<ResponseData<List<? extends LanguageItem>>>() { // from class: com.miui.notes.ai.request.AINotesViewModel$getSupportedLanguageList$1$result$1$responseData$1
            }.getType());
            Logger.INSTANCE.i("AINotesViewModel", "getLanguageResultCode:" + responseData.getCode() + ", traceId:" + responseData.getTraceId());
            return new AIResult.Complete(responseData.getData());
        } catch (Exception e) {
            Logger.INSTANCE.e("AINotesViewModel", "e:" + e);
            return AIResult.Error.INSTANCE;
        }
    }
}
