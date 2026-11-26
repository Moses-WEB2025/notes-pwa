package com.miui.notes.ai.network;

import android.content.Context;
import android.util.Log;
import androidx.core.os.BundleKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.GlobalAiTextWidgetServiceHelper;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.llm.AICreationResult;
import com.xiaomi.aicr.llm.LLMManager;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: GlobalAiTextRequest.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\f\u001a\u00020\u000bH\u0002JF\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0011J>\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0011J\u0010\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0007H\u0002J\u0010\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eH\u0002J\u0006\u0010\u001c\u001a\u00020\u000bJ\u0006\u0010\u001d\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/miui/notes/ai/network/GlobalAiTextRequest;", "", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mLLMManager", "Lcom/xiaomi/aicr/llm/LLMManager;", "mCurrentRetryCount", "", "initManager", "", "initLLMManagerConnection", "doRequest", "Lkotlinx/coroutines/flow/Flow;", "Lcom/xiaomi/aicr/llm/AICreationResult;", GlobalAiTextWidgetServiceHelper.KEY_QUERY_ID, "", "packageName", "pageType", "style", "emoticonsNum", "wordNum", "userPrompt", "comment", "reConnectLLMManager", "llmManager", "handleLLMManagerDisconnected", "stopRequest", "cancelRequest", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextRequest {
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_EMOTICONS_NUM = "emoticonsNums";
    public static final String KEY_PACKAGE_NAME = "packageName";
    public static final String KEY_PAGE_TYPE = "pageType";
    public static final String KEY_STYLE = "style";
    public static final String KEY_USER_PROMPT = "userPrompt";
    public static final String KEY_WORD_NUM = "wordNums";
    public static final int MAX_RETRY_COUNT = 3;
    public static final String TAG = "GlobalAiTextRequest";
    private final Context mContext;
    private int mCurrentRetryCount;
    private LLMManager mLLMManager;

    public GlobalAiTextRequest(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
    }

    public final void initManager() {
        initLLMManagerConnection();
    }

    private final void initLLMManagerConnection() {
        this.mLLMManager = new LLMManager(this.mContext, new AiServiceConnection() { // from class: com.miui.notes.ai.network.GlobalAiTextRequest.initLLMManagerConnection.1
            @Override // com.xiaomi.aicr.common.AiServiceConnection
            public void onServiceConnected(int connectStatus) {
                super.onServiceConnected(connectStatus);
                GlobalAiTextRequest.this.mCurrentRetryCount = 0;
                Log.d(GlobalAiTextRequest.TAG, "LLMManager Service Connected");
            }

            @Override // com.xiaomi.aicr.common.AiServiceConnection
            public void onServiceDisconnected() {
                super.onServiceDisconnected();
                Log.d(GlobalAiTextRequest.TAG, "LLMManager Service Disconnected");
            }

            @Override // com.xiaomi.aicr.common.AiServiceConnection
            public void onError(int code) {
                super.onError(code);
                Log.e(GlobalAiTextRequest.TAG, "LLMManager Service Connect Error: " + code);
            }
        }, null, 4, null);
    }

    public final Flow<AICreationResult> doRequest(String queryID, String packageName, String pageType, int style, int emoticonsNum, int wordNum, String userPrompt) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(pageType, "pageType");
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            if (lLMManager.isConnected()) {
                Log.d(TAG, "doRequest: queryID " + queryID + ", packageName " + packageName + ", pageType " + pageType + ", style " + style + ", emoticonsNum " + emoticonsNum + ", wordNum " + wordNum + ", userPrompt " + userPrompt);
                return lLMManager.addOnLineCreationRequest(queryID, BundleKt.bundleOf(TuplesKt.to("packageName", packageName), TuplesKt.to("pageType", pageType), TuplesKt.to("style", Integer.valueOf(style)), TuplesKt.to("emoticonsNums", Integer.valueOf(emoticonsNum)), TuplesKt.to("wordNums", Integer.valueOf(wordNum)), TuplesKt.to("userPrompt", userPrompt)));
            }
            if (this.mCurrentRetryCount < 3) {
                reConnectLLMManager(lLMManager);
                this.mCurrentRetryCount++;
                return doRequest(queryID, packageName, pageType, style, emoticonsNum, wordNum, userPrompt);
            }
            return handleLLMManagerDisconnected();
        }
        Log.e(TAG, "doRequest: mLLMManager is null");
        return null;
    }

    public final Flow<AICreationResult> doRequest(String queryID, String packageName, String pageType, int comment, int wordNum, String userPrompt) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(pageType, "pageType");
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            if (lLMManager.isConnected()) {
                Log.d(TAG, "doRequest: queryID " + queryID + ", packageName " + packageName + ", pageType " + pageType + ", comment " + comment + ", wordNum " + wordNum + ", userPrompt " + userPrompt);
                return lLMManager.addOnLineCreationRequest(queryID, BundleKt.bundleOf(TuplesKt.to("packageName", packageName), TuplesKt.to("pageType", pageType), TuplesKt.to("comment", Integer.valueOf(comment)), TuplesKt.to("wordNums", Integer.valueOf(wordNum)), TuplesKt.to("userPrompt", userPrompt)));
            }
            if (this.mCurrentRetryCount < 3) {
                reConnectLLMManager(lLMManager);
                this.mCurrentRetryCount++;
                return doRequest(queryID, packageName, pageType, comment, wordNum, userPrompt);
            }
            return handleLLMManagerDisconnected();
        }
        Log.e(TAG, "doRequest: mLLMManager is null");
        return null;
    }

    private final void reConnectLLMManager(LLMManager llmManager) {
        if (llmManager.isConnected()) {
            return;
        }
        cancelRequest();
        initLLMManagerConnection();
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequest$handleLLMManagerDisconnected$1, reason: invalid class name */
    /* JADX INFO: compiled from: GlobalAiTextRequest.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/xiaomi/aicr/llm/AICreationResult;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequest$handleLLMManagerDisconnected$1", f = "GlobalAiTextRequest.kt", i = {0}, l = {160}, m = "invokeSuspend", n = {"$this$callbackFlow"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<ProducerScope<? super AICreationResult>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super AICreationResult> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProducerScope producerScope = (ProducerScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Log.e(GlobalAiTextRequest.TAG, "Failed to connect after 3 attempts");
                producerScope.mo4054trySendJP2dKIU(new AICreationResult("", "", "", -1));
                this.L$0 = SpillingKt.nullOutSpilledVariable(producerScope);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, new Function0() { // from class: com.miui.notes.ai.network.GlobalAiTextRequest$handleLLMManagerDisconnected$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return GlobalAiTextRequest.AnonymousClass1.invokeSuspend$lambda$0();
                    }
                }, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0() {
            Log.d(GlobalAiTextRequest.TAG, "doRequest: CallbackFlow closed, perform cleanup if needed");
            return Unit.INSTANCE;
        }
    }

    private final Flow<AICreationResult> handleLLMManagerDisconnected() {
        return FlowKt.callbackFlow(new AnonymousClass1(null));
    }

    public final void stopRequest() {
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            lLMManager.refresh();
        }
        this.mCurrentRetryCount = 0;
    }

    public final void cancelRequest() {
        try {
            LLMManager lLMManager = this.mLLMManager;
            if (lLMManager == null || !lLMManager.isConnected()) {
                return;
            }
            lLMManager.releaseConnect();
        } catch (Exception e) {
            Log.e(TAG, "cancelRequest: " + e.getMessage());
            e.printStackTrace();
            Log.e(TAG, "cancelRequest: " + Unit.INSTANCE);
        }
    }
}
