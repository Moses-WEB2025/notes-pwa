package com.miui.doodle.feature.action;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.miui.doodle.utils.DoodleCacheManager;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AddAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.doodle.feature.action.AddAction$cacheToFile$1$1$1", f = "AddAction.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AddAction$cacheToFile$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
    final /* synthetic */ Bitmap $it;
    int label;
    final /* synthetic */ AddAction this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AddAction$cacheToFile$1$1$1(AddAction addAction, Bitmap bitmap, Continuation<? super AddAction$cacheToFile$1$1$1> continuation) {
        super(2, continuation);
        this.this$0 = addAction;
        this.$it = bitmap;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AddAction$cacheToFile$1$1$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
        return ((AddAction$cacheToFile$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String strSaveImageToFile;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object lock = this.this$0.getLock();
        Bitmap bitmap = this.$it;
        AddAction addAction = this.this$0;
        synchronized (lock) {
            strSaveImageToFile = DoodleCacheManager.INSTANCE.saveImageToFile(bitmap, DoodleCacheManager.HIS_CACHE_HEAD);
            if (!TextUtils.isEmpty(strSaveImageToFile)) {
                bitmap.recycle();
                addAction.setOldLayerBitmap(null);
            }
        }
        return strSaveImageToFile;
    }
}
