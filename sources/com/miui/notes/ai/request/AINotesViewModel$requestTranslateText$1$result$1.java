package com.miui.notes.ai.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.common.tool.Logger;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AINotesViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\n"}, d2 = {"<anonymous>", "Lcom/miui/notes/ai/request/AIResult;", "Lcom/miui/notes/ai/request/TranslateData;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.notes.ai.request.AINotesViewModel$requestTranslateText$1$result$1", f = "AINotesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AINotesViewModel$requestTranslateText$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super AIResult<? extends TranslateData>>, Object> {
    final /* synthetic */ String $inputText;
    final /* synthetic */ String $srcLangCode;
    final /* synthetic */ String $targetLangCode;
    int label;
    final /* synthetic */ AINotesViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AINotesViewModel$requestTranslateText$1$result$1(String str, String str2, String str3, AINotesViewModel aINotesViewModel, Continuation<? super AINotesViewModel$requestTranslateText$1$result$1> continuation) {
        super(2, continuation);
        this.$inputText = str;
        this.$srcLangCode = str2;
        this.$targetLangCode = str3;
        this.this$0 = aINotesViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AINotesViewModel$requestTranslateText$1$result$1(this.$inputText, this.$srcLangCode, this.$targetLangCode, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super AIResult<? extends TranslateData>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super AIResult<TranslateData>>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super AIResult<TranslateData>> continuation) {
        return ((AINotesViewModel$requestTranslateText$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("inputText", this.$inputText);
            map.put(AINotesViewModel.PARAM_SRC_LANG_CODE, this.$srcLangCode);
            map.put(AINotesViewModel.PARAM_TARGET_LANG_CODE, this.$targetLangCode);
            map.put("bizCode", AINotesViewModel.VALUE_BIZ_CODE);
            ResponseData responseData = (ResponseData) new Gson().fromJson(NoteAIRequest.INSTANCE.post(AINotesViewModel.INSTANCE.getURL_TRANSLATE(), null, map), new TypeToken<ResponseData<TranslateData>>() { // from class: com.miui.notes.ai.request.AINotesViewModel$requestTranslateText$1$result$1$responseData$1
            }.getType());
            Logger.INSTANCE.i("AINotesViewModel", "translateResultCode:" + responseData.getCode() + ", traceId:" + responseData.getTraceId() + ", message:" + responseData.getMessage());
            AINotesViewModel aINotesViewModel = this.this$0;
            Intrinsics.checkNotNull(responseData);
            return aINotesViewModel.delaResult(responseData);
        } catch (Exception e) {
            Logger.INSTANCE.e("AINotesViewModel", "e:" + e);
            return AIResult.Error.INSTANCE;
        }
    }
}
