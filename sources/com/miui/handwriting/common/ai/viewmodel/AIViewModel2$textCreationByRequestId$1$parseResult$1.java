package com.miui.handwriting.common.ai.viewmodel;

import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.handwriting.common.ai.anno.CreationType;
import com.miui.handwriting.common.ai.data.CreationData;
import com.miui.handwriting.common.ai.data.ReferenceItem;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.viewmodel.cache.RecreateInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: AIViewModel2.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, d2 = {"<anonymous>", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreationByRequestId$1$parseResult$1", f = "AIViewModel2.kt", i = {}, l = {410}, m = "invokeSuspend", n = {}, s = {})
final class AIViewModel2$textCreationByRequestId$1$parseResult$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    final /* synthetic */ int $index;
    final /* synthetic */ String $mid;
    final /* synthetic */ String $prompt;
    final /* synthetic */ RecreateInfo $recreateInfo;
    final /* synthetic */ ReferenceItem $referenceItem;
    final /* synthetic */ List<String> $resultList;
    final /* synthetic */ ThemeItem $theme;
    int label;
    final /* synthetic */ AIViewModel2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AIViewModel2$textCreationByRequestId$1$parseResult$1(int i, List<String> list, AIViewModel2 aIViewModel2, ReferenceItem referenceItem, ThemeItem themeItem, String str, RecreateInfo recreateInfo, String str2, Continuation<? super AIViewModel2$textCreationByRequestId$1$parseResult$1> continuation) {
        super(1, continuation);
        this.$index = i;
        this.$resultList = list;
        this.this$0 = aIViewModel2;
        this.$referenceItem = referenceItem;
        this.$theme = themeItem;
        this.$mid = str;
        this.$recreateInfo = recreateInfo;
        this.$prompt = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new AIViewModel2$textCreationByRequestId$1$parseResult$1(this.$index, this.$resultList, this.this$0, this.$referenceItem, this.$theme, this.$mid, this.$recreateInfo, this.$prompt, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super Unit> continuation) {
        return ((AIViewModel2$textCreationByRequestId$1$parseResult$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$index != 0) {
                this.label = 1;
                if (DelayKt.delay(1000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Log.i("AIViewModel2", "textCreation start parse result");
        Result result = (Result) new Gson().fromJson(this.$resultList.get(this.$index), new TypeToken<Result<CreationData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$textCreationByRequestId$1$parseResult$1$response$1
        }.getType());
        Log.d("AIViewModel2", "textCreation result:" + this.$resultList.get(this.$index) + CoreConstants.COMMA_CHAR + result);
        this.this$0.postSmallImageResult(result, this.$referenceItem, this.$theme, new RecreateInfo(this.$mid, this.$recreateInfo.getRequestId(), this.$recreateInfo.getServerKey(), this.$recreateInfo.getIv(), CreationType.CREATION_TEXT), this.$prompt);
        return Unit.INSTANCE;
    }
}
