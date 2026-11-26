package com.miui.handwriting.common.ai.viewmodel;

import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.handwriting.common.ai.data.CreationData;
import com.miui.handwriting.common.ai.data.ReferenceItem;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.viewmodel.cache.RecreateInfo;
import defpackage.R2;
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
@DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncCreation$2$parseResult$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.dividerPreferenceSolidColor}, m = "invokeSuspend", n = {}, s = {})
final class AIViewModel2$asyncCreation$2$parseResult$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    final /* synthetic */ String $creationType;
    final /* synthetic */ int $index;
    final /* synthetic */ String $iv;
    final /* synthetic */ String $mid;
    final /* synthetic */ String $prompt;
    final /* synthetic */ ReferenceItem $referenceItem;
    final /* synthetic */ String $requestId;
    final /* synthetic */ List<String> $resultList;
    final /* synthetic */ String $serverKey;
    final /* synthetic */ ThemeItem $theme;
    int label;
    final /* synthetic */ AIViewModel2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AIViewModel2$asyncCreation$2$parseResult$1(int i, List<String> list, AIViewModel2 aIViewModel2, ReferenceItem referenceItem, ThemeItem themeItem, String str, String str2, String str3, String str4, String str5, String str6, Continuation<? super AIViewModel2$asyncCreation$2$parseResult$1> continuation) {
        super(1, continuation);
        this.$index = i;
        this.$resultList = list;
        this.this$0 = aIViewModel2;
        this.$referenceItem = referenceItem;
        this.$theme = themeItem;
        this.$mid = str;
        this.$requestId = str2;
        this.$serverKey = str3;
        this.$iv = str4;
        this.$creationType = str5;
        this.$prompt = str6;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new AIViewModel2$asyncCreation$2$parseResult$1(this.$index, this.$resultList, this.this$0, this.$referenceItem, this.$theme, this.$mid, this.$requestId, this.$serverKey, this.$iv, this.$creationType, this.$prompt, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super Unit> continuation) {
        return ((AIViewModel2$asyncCreation$2$parseResult$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
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
        Log.i("AIViewModel2", "start parse result");
        Result result = (Result) new Gson().fromJson(this.$resultList.get(this.$index), new TypeToken<Result<CreationData>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncCreation$2$parseResult$1$response$1
        }.getType());
        Log.d("AIViewModel2", "requestCreation result:" + this.$resultList.get(this.$index) + CoreConstants.COMMA_CHAR + result);
        this.this$0.postSmallImageResult(result, this.$referenceItem, this.$theme, new RecreateInfo(this.$mid, this.$requestId, this.$serverKey, this.$iv, this.$creationType), this.$prompt);
        return Unit.INSTANCE;
    }
}
