package com.miui.handwriting.common.ai.viewmodel;

import android.util.Log;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.ai.data.ReferenceItem;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.viewmodel.cache.RecreateInfo;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AIViewModel2.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$asyncRecreation$1$creationList$1$1", f = "AIViewModel2.kt", i = {}, l = {R2.attr.horizontalOffsetWithText}, m = "invokeSuspend", n = {}, s = {})
final class AIViewModel2$asyncRecreation$1$creationList$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
    final /* synthetic */ CreationImage $creationImage;
    final /* synthetic */ String $mid;
    final /* synthetic */ String $prompt;
    final /* synthetic */ RecreateInfo $recreateInfo;
    final /* synthetic */ ReferenceItem $referenceItem;
    final /* synthetic */ ThemeItem $theme;
    int label;
    final /* synthetic */ AIViewModel2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AIViewModel2$asyncRecreation$1$creationList$1$1(AIViewModel2 aIViewModel2, String str, RecreateInfo recreateInfo, ThemeItem themeItem, CreationImage creationImage, String str2, ReferenceItem referenceItem, Continuation<? super AIViewModel2$asyncRecreation$1$creationList$1$1> continuation) {
        super(2, continuation);
        this.this$0 = aIViewModel2;
        this.$mid = str;
        this.$recreateInfo = recreateInfo;
        this.$theme = themeItem;
        this.$creationImage = creationImage;
        this.$prompt = str2;
        this.$referenceItem = referenceItem;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AIViewModel2$asyncRecreation$1$creationList$1$1(this.this$0, this.$mid, this.$recreateInfo, this.$theme, this.$creationImage, this.$prompt, this.$referenceItem, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
        return ((AIViewModel2$asyncRecreation$1$creationList$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Log.d("AIViewModel2", "start request reCreation");
                AIViewModel2 aIViewModel2 = this.this$0;
                String str = this.$mid;
                String requestId = this.$recreateInfo.getRequestId();
                String serverKey = this.$recreateInfo.getServerKey();
                String iv = this.$recreateInfo.getIv();
                ThemeItem themeItem = this.$theme;
                if (themeItem == null) {
                    themeItem = this.$creationImage.getThemeItem();
                }
                ThemeItem themeItem2 = themeItem;
                String str2 = this.$prompt;
                ReferenceItem referenceItem = this.$referenceItem;
                String creationType = this.$recreateInfo.getCreationType();
                if (creationType == null) {
                    creationType = "1";
                }
                this.label = 1;
                obj = aIViewModel2.reCreation(str, requestId, serverKey, iv, themeItem2, str2, referenceItem, creationType, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return (String) obj;
        } catch (Exception e) {
            Log.e("AIViewModel2", "start request reCreation fail: " + e);
            return "";
        }
    }
}
