package com.miui.handwriting.common.ai;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.ai.data.Result;
import com.miui.handwriting.common.ai.data.VersionData;
import com.miui.handwriting.common.tools.DeviceUtils;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: VerifyServer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000e\u000fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/miui/handwriting/common/ai/VerifyServer;", "Lkotlinx/coroutines/CoroutineScope;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getServerVersion", "", "mid", "", "callBack", "Lcom/miui/handwriting/common/ai/VerifyServer$CallBack;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "Companion", "CallBack", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class VerifyServer implements CoroutineScope {
    public static final int CODE_AUTHENTICATOR_EXCEPTION = -2;
    private static final String TAG = "VerifyServer";
    private final /* synthetic */ CoroutineScope $$delegate_0 = CoroutineScopeKt.MainScope();

    /* JADX INFO: compiled from: VerifyServer.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lcom/miui/handwriting/common/ai/VerifyServer$CallBack;", "", "onCallBack", "", "result", "Lcom/miui/handwriting/common/ai/data/Result;", "Lcom/miui/handwriting/common/ai/data/VersionData;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface CallBack {
        void onCallBack(Result<VersionData> result);
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.ai.VerifyServer$getServerVersion$1, reason: invalid class name */
    /* JADX INFO: compiled from: VerifyServer.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.ai.VerifyServer$getServerVersion$1", f = "VerifyServer.kt", i = {}, l = {34}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CallBack $callBack;
        final /* synthetic */ String $mid;
        final /* synthetic */ Ref.ObjectRef<Result<VersionData>> $result;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Ref.ObjectRef<Result<VersionData>> objectRef, CallBack callBack, String str, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$result = objectRef;
            this.$callBack = callBack;
            this.$mid = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$result, this.$callBack, this.$mid, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.ai.VerifyServer$getServerVersion$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: VerifyServer.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.ai.VerifyServer$getServerVersion$1$1", f = "VerifyServer.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00491 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $mid;
            final /* synthetic */ Ref.ObjectRef<Result<VersionData>> $result;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00491(String str, Ref.ObjectRef<Result<VersionData>> objectRef, Continuation<? super C00491> continuation) {
                super(2, continuation);
                this.$mid = str;
                this.$result = objectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00491(this.$mid, this.$result, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference failed for: r0v5, types: [T, com.miui.handwriting.common.ai.data.Result] */
            /* JADX WARN: Type inference failed for: r0v8, types: [T, java.lang.Object] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                try {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("miId", this.$mid);
                    map.put("oaid", DeviceUtils.getOAId());
                    map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    String str = CreationAIRequest2.INSTANCE.get(NetConfig.INSTANCE.getURL_GET_VERSION(), null, map, true);
                    Log.d(VerifyServer.TAG, "param:" + map + ", response:" + str);
                    this.$result.element = new Gson().fromJson(str, new TypeToken<Result<VersionData>>() { // from class: com.miui.handwriting.common.ai.VerifyServer.getServerVersion.1.1.1
                    }.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(VerifyServer.TAG, "e:" + e);
                    this.$result.element = new Result(e.getMessage(), false, -1, e.getMessage(), 0L, new VersionData(""));
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00491(this.$mid, this.$result, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Log.d(VerifyServer.TAG, "result:" + this.$result.element);
            this.$callBack.onCallBack(this.$result.element);
            return Unit.INSTANCE;
        }
    }

    public final void getServerVersion(String mid, CallBack callBack) {
        Intrinsics.checkNotNullParameter(mid, "mid");
        Intrinsics.checkNotNullParameter(callBack, "callBack");
        BuildersKt__Builders_commonKt.launch$default(this, null, null, new AnonymousClass1(new Ref.ObjectRef(), callBack, mid, null), 3, null);
    }
}
