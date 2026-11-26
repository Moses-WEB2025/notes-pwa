package com.miui.handwriting.common.ai.viewmodel;

import android.util.Log;
import com.miui.handwriting.common.ai.data.ReferenceItem;
import com.miui.handwriting.common.ai.data.ThemeItem;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AIViewModel2.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncCreation$2$creationList$1$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.dialogPreferredPadding}, m = "invokeSuspend", n = {}, s = {})
final class AIViewModel2$asyncCreation$2$creationList$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
    final /* synthetic */ String $creationType;
    final /* synthetic */ String $image;
    final /* synthetic */ String $iv;
    final /* synthetic */ String $maskCoordinates;
    final /* synthetic */ String $mid;
    final /* synthetic */ String $prompt;
    final /* synthetic */ ReferenceItem $referenceItem;
    final /* synthetic */ String $requestId;
    final /* synthetic */ String $serverKey;
    final /* synthetic */ ThemeItem $theme;
    int label;
    final /* synthetic */ AIViewModel2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AIViewModel2$asyncCreation$2$creationList$1$1(AIViewModel2 aIViewModel2, String str, String str2, String str3, String str4, String str5, ThemeItem themeItem, String str6, ReferenceItem referenceItem, String str7, String str8, Continuation<? super AIViewModel2$asyncCreation$2$creationList$1$1> continuation) {
        super(2, continuation);
        this.this$0 = aIViewModel2;
        this.$mid = str;
        this.$image = str2;
        this.$serverKey = str3;
        this.$iv = str4;
        this.$requestId = str5;
        this.$theme = themeItem;
        this.$prompt = str6;
        this.$referenceItem = referenceItem;
        this.$maskCoordinates = str7;
        this.$creationType = str8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AIViewModel2$asyncCreation$2$creationList$1$1(this.this$0, this.$mid, this.$image, this.$serverKey, this.$iv, this.$requestId, this.$theme, this.$prompt, this.$referenceItem, this.$maskCoordinates, this.$creationType, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
        return ((AIViewModel2$asyncCreation$2$creationList$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object objRequestCreation;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Log.i("AIViewModel2", "start requestCreation");
                this.label = 1;
                objRequestCreation = this.this$0.requestCreation(this.$mid, this.$image, this.$serverKey, this.$iv, this.$requestId, Boxing.boxInt(1), this.$theme, this.$prompt, this.$referenceItem, this.$maskCoordinates, this.$creationType, this);
                if (objRequestCreation == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                objRequestCreation = obj;
            }
            return (String) objRequestCreation;
        } catch (Exception e) {
            Log.e("AIViewModel2", "start requestCreation fail: " + e);
            return "";
        }
    }
}
