package com.miui.handwriting.common.ai.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.handwriting.common.ai.CreationAIRequest2;
import com.miui.handwriting.common.ai.NetConfig;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.ai.data.ReferenceItem;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.data.requestparam.ParamCreationImage;
import com.miui.handwriting.common.ai.viewmodel.cache.RecreateInfo;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AIViewModel2.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestEnhance$1$1$onResourceReady$1", f = "AIViewModel2.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AIViewModel2$requestEnhance$1$1$onResourceReady$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ CreationImage $creationImage;
    final /* synthetic */ HashMap<String, String> $params;
    final /* synthetic */ String $prompt;
    final /* synthetic */ Bitmap $resource;
    final /* synthetic */ ThemeItem $theme;
    int label;
    final /* synthetic */ AIViewModel2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AIViewModel2$requestEnhance$1$1$onResourceReady$1(AIViewModel2 aIViewModel2, Bitmap bitmap, ThemeItem themeItem, String str, HashMap<String, String> map, CreationImage creationImage, Continuation<? super AIViewModel2$requestEnhance$1$1$onResourceReady$1> continuation) {
        super(2, continuation);
        this.this$0 = aIViewModel2;
        this.$resource = bitmap;
        this.$theme = themeItem;
        this.$prompt = str;
        this.$params = map;
        this.$creationImage = creationImage;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AIViewModel2$requestEnhance$1$1$onResourceReady$1(this.this$0, this.$resource, this.$theme, this.$prompt, this.$params, this.$creationImage, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((AIViewModel2$requestEnhance$1$1$onResourceReady$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0130 A[Catch: Exception -> 0x0165, TryCatch #0 {Exception -> 0x0165, blocks: (B:5:0x0012, B:7:0x004f, B:9:0x0059, B:11:0x0061, B:13:0x0068, B:15:0x00d6, B:17:0x00dc, B:19:0x0100, B:21:0x0108, B:23:0x0110, B:25:0x011a, B:27:0x0122, B:30:0x012a, B:37:0x0144, B:38:0x0154, B:32:0x0130, B:34:0x013a, B:35:0x013e, B:18:0x00f6), top: B:46:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x013a A[Catch: Exception -> 0x0165, TryCatch #0 {Exception -> 0x0165, blocks: (B:5:0x0012, B:7:0x004f, B:9:0x0059, B:11:0x0061, B:13:0x0068, B:15:0x00d6, B:17:0x00dc, B:19:0x0100, B:21:0x0108, B:23:0x0110, B:25:0x011a, B:27:0x0122, B:30:0x012a, B:37:0x0144, B:38:0x0154, B:32:0x0130, B:34:0x013a, B:35:0x013e, B:18:0x00f6), top: B:46:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x0144 A[Catch: Exception -> 0x0165, TryCatch #0 {Exception -> 0x0165, blocks: (B:5:0x0012, B:7:0x004f, B:9:0x0059, B:11:0x0061, B:13:0x0068, B:15:0x00d6, B:17:0x00dc, B:19:0x0100, B:21:0x0108, B:23:0x0110, B:25:0x011a, B:27:0x0122, B:30:0x012a, B:37:0x0144, B:38:0x0154, B:32:0x0130, B:34:0x013a, B:35:0x013e, B:18:0x00f6), top: B:46:0x0012 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AIViewModel2 aIViewModel2;
        RecreateInfo recreateInfo;
        String uri;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            this.this$0.setImageWidth(this.$resource.getWidth());
            this.this$0.setImageHeight(this.$resource.getHeight());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.$resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            String strEncodeToString = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            Intrinsics.checkNotNull(strEncodeToString);
            ThemeItem themeItem = this.$theme;
            String strValueOf = String.valueOf(themeItem != null ? Boxing.boxInt(themeItem.getThemeId()) : null);
            ThemeItem themeItem2 = this.$theme;
            ParamCreationImage paramCreationImage = new ParamCreationImage(strEncodeToString, strValueOf, themeItem2 != null ? themeItem2.getTheme() : null, this.$prompt, 1, this.this$0.getImageHeight(), this.this$0.getImageWidth());
            this.$params.put("data", new Gson().toJson(paramCreationImage));
            Log.d("AIViewModel2", "requestEnhance params:" + paramCreationImage);
            String strPost$default = CreationAIRequest2.Companion.post$default(CreationAIRequest2.INSTANCE, NetConfig.INSTANCE.getURL_ENHANCE(), null, this.$params, null, false, 24, null);
            Log.d("AIViewModel2", "requestEnhance response:" + strPost$default);
            Result result = (Result) new Gson().fromJson(strPost$default, new TypeToken<Result<CreationImage>>() { // from class: com.miui.handwriting.common.ai.viewmodel.AIViewModel2$requestEnhance$1$1$onResourceReady$1$result$1
            }.getType());
            if (result != null && result.getCode() == 0) {
                this.$creationImage.setBigImage(Boxing.boxBoolean(true));
                this.$creationImage.setImageUrlBig(((CreationImage) result.getData()).getImageUrl());
            } else {
                this.$creationImage.setBigImage(Boxing.boxBoolean(false));
            }
            if (!this.this$0.getIsUseEdit()) {
                AIViewModel2 aIViewModel3 = this.this$0;
                String str = this.$prompt;
                ThemeItem themeItem3 = this.$theme;
                Integer numBoxInt = themeItem3 != null ? Boxing.boxInt(themeItem3.getThemeId()) : null;
                ReferenceItem referenceItem = this.$creationImage.getReferenceItem();
                if (referenceItem == null || (uri = referenceItem.getUri()) == null) {
                    uri = "";
                }
                if (!aIViewModel3.isSameRequestParam(str, numBoxInt, uri)) {
                    aIViewModel2 = this.this$0;
                    recreateInfo = this.$creationImage.getRecreateInfo();
                    if (aIViewModel2.istPart(recreateInfo != null ? recreateInfo.getCreationType() : null)) {
                        Log.e("AIViewModel2", "is user not in edit");
                        this.this$0._creationBigImageLiveData.postValue(this.$creationImage);
                    }
                } else {
                    Log.e("AIViewModel2", "is user not in edit");
                    this.this$0._creationBigImageLiveData.postValue(this.$creationImage);
                }
            } else {
                aIViewModel2 = this.this$0;
                recreateInfo = this.$creationImage.getRecreateInfo();
                if (aIViewModel2.istPart(recreateInfo != null ? recreateInfo.getCreationType() : null)) {
                    Log.e("AIViewModel2", "is user not in edit");
                    this.this$0._creationBigImageLiveData.postValue(this.$creationImage);
                }
            }
            Log.i("AIViewModel2", "requestEnhance result:" + result);
        } catch (Exception e) {
            Log.e("AIViewModel2", "requestEnhance error:" + e);
        }
        return Unit.INSTANCE;
    }
}
