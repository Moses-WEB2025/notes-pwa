package com.miui.notes.ai.network;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.GlobalAiTextWidgetServiceHelper;
import com.miui.notes.ai.network.request.AIError;
import com.miui.notes.ai.network.request.AIResult;
import com.miui.notes.ai.network.request.ResponseErrorDetail;
import com.xiaomi.aicr.llm.AICreationResult;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 -2\u00020\u0001:\u0001-B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0017\u001a\u00020\u0018J>\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001bJ6\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001bJh\u0010$\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001b2\u0012\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0007H\u0002J`\u0010$\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u001b2\u0012\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0007H\u0002JF\u0010'\u001a\u00020\u00182\u000e\u0010(\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010)2\u0012\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0007H\u0082@¢\u0006\u0002\u0010*J\u0006\u0010+\u001a\u00020\u0018J\u0006\u0010,\u001a\u00020\u0018R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/miui/notes/ai/network/GlobalAiTextRequestManager;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "_globalAiGenerateTextLiveData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/miui/notes/ai/network/request/AIResult;", "Lcom/xiaomi/aicr/llm/AICreationResult;", "globalAiGenerateTextLiveData", "Landroidx/lifecycle/LiveData;", "getGlobalAiGenerateTextLiveData", "()Landroidx/lifecycle/LiveData;", "_errorLiveData", "Lcom/miui/notes/ai/network/request/AIError;", "Lcom/miui/notes/ai/network/request/ResponseErrorDetail;", "errorLiveData", "getErrorLiveData", "mGlobalAiGenerateTextJob", "Lkotlinx/coroutines/Job;", "mGlobalAiTextRequest", "Lcom/miui/notes/ai/network/GlobalAiTextRequest;", "initManager", "", "requestGenerateText", GlobalAiTextWidgetServiceHelper.KEY_QUERY_ID, "", "packageName", "pageType", "style", "", "emoticonsNum", "wordNum", "userPrompt", "comment", "handleResponse", "data", "errorListener", "handleResponseFlow", "responseFlow", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Landroidx/lifecycle/MutableLiveData;Landroidx/lifecycle/MutableLiveData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopRequest", "cancelRequest", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextRequestManager {
    public static final String FLOW_DONE_TAG = "[DONE]";
    public static final int RESULT_CODE_DISCONNECTED = -1;
    public static final int RESULT_CODE_NET_ERROR = 11;
    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_PARAMS_ERROR = 16;
    public static final String TAG = "GlobalAiTextRequestManager";
    private final MutableLiveData<AIError<ResponseErrorDetail>> _errorLiveData;
    private final MutableLiveData<AIResult<AICreationResult>> _globalAiGenerateTextLiveData;
    private final LiveData<AIError<ResponseErrorDetail>> errorLiveData;
    private final LiveData<AIResult<AICreationResult>> globalAiGenerateTextLiveData;
    private Job mGlobalAiGenerateTextJob;
    private GlobalAiTextRequest mGlobalAiTextRequest;

    public GlobalAiTextRequestManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        MutableLiveData<AIResult<AICreationResult>> mutableLiveData = new MutableLiveData<>();
        this._globalAiGenerateTextLiveData = mutableLiveData;
        this.globalAiGenerateTextLiveData = mutableLiveData;
        MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2 = new MutableLiveData<>();
        this._errorLiveData = mutableLiveData2;
        this.errorLiveData = mutableLiveData2;
        this.mGlobalAiTextRequest = new GlobalAiTextRequest(context);
    }

    public final LiveData<AIResult<AICreationResult>> getGlobalAiGenerateTextLiveData() {
        return this.globalAiGenerateTextLiveData;
    }

    public final LiveData<AIError<ResponseErrorDetail>> getErrorLiveData() {
        return this.errorLiveData;
    }

    public final void initManager() {
        GlobalAiTextRequest globalAiTextRequest = this.mGlobalAiTextRequest;
        if (globalAiTextRequest != null) {
            globalAiTextRequest.initManager();
        }
    }

    public final void requestGenerateText(String queryID, String packageName, String pageType, int style, int emoticonsNum, int wordNum, String userPrompt) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(pageType, "pageType");
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        this.mGlobalAiGenerateTextJob = handleResponse(queryID, packageName, pageType, style, emoticonsNum, wordNum, userPrompt, this._globalAiGenerateTextLiveData, this._errorLiveData);
    }

    public final void requestGenerateText(String queryID, String packageName, String pageType, int comment, int wordNum, String userPrompt) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(pageType, "pageType");
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        this.mGlobalAiGenerateTextJob = handleResponse(queryID, packageName, pageType, comment, wordNum, userPrompt, this._globalAiGenerateTextLiveData, this._errorLiveData);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$1, reason: invalid class name */
    /* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$1", f = "GlobalAiTextRequestManager.kt", i = {}, l = {100}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MutableLiveData<AIResult<AICreationResult>> $data;
        final /* synthetic */ int $emoticonsNum;
        final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
        final /* synthetic */ String $packageName;
        final /* synthetic */ String $pageType;
        final /* synthetic */ String $queryID;
        final /* synthetic */ int $style;
        final /* synthetic */ String $userPrompt;
        final /* synthetic */ int $wordNum;
        int label;
        final /* synthetic */ GlobalAiTextRequestManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(MutableLiveData<AIResult<AICreationResult>> mutableLiveData, GlobalAiTextRequestManager globalAiTextRequestManager, String str, String str2, String str3, int i, int i2, int i3, String str4, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$data = mutableLiveData;
            this.this$0 = globalAiTextRequestManager;
            this.$queryID = str;
            this.$packageName = str2;
            this.$pageType = str3;
            this.$style = i;
            this.$emoticonsNum = i2;
            this.$wordNum = i3;
            this.$userPrompt = str4;
            this.$errorListener = mutableLiveData2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$data, this.this$0, this.$queryID, this.$packageName, this.$pageType, this.$style, this.$emoticonsNum, this.$wordNum, this.$userPrompt, this.$errorListener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.$data.setValue(AIResult.Loading.INSTANCE);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00821(this.this$0, this.$queryID, this.$packageName, this.$pageType, this.$style, this.$emoticonsNum, this.$wordNum, this.$userPrompt, this.$data, this.$errorListener, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$1$1", f = "GlobalAiTextRequestManager.kt", i = {0}, l = {103}, m = "invokeSuspend", n = {"responseFlow"}, s = {"L$0"})
        static final class C00821 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ MutableLiveData<AIResult<AICreationResult>> $data;
            final /* synthetic */ int $emoticonsNum;
            final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
            final /* synthetic */ String $packageName;
            final /* synthetic */ String $pageType;
            final /* synthetic */ String $queryID;
            final /* synthetic */ int $style;
            final /* synthetic */ String $userPrompt;
            final /* synthetic */ int $wordNum;
            Object L$0;
            int label;
            final /* synthetic */ GlobalAiTextRequestManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00821(GlobalAiTextRequestManager globalAiTextRequestManager, String str, String str2, String str3, int i, int i2, int i3, String str4, MutableLiveData<AIResult<AICreationResult>> mutableLiveData, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super C00821> continuation) {
                super(2, continuation);
                this.this$0 = globalAiTextRequestManager;
                this.$queryID = str;
                this.$packageName = str2;
                this.$pageType = str3;
                this.$style = i;
                this.$emoticonsNum = i2;
                this.$wordNum = i3;
                this.$userPrompt = str4;
                this.$data = mutableLiveData;
                this.$errorListener = mutableLiveData2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00821(this.this$0, this.$queryID, this.$packageName, this.$pageType, this.$style, this.$emoticonsNum, this.$wordNum, this.$userPrompt, this.$data, this.$errorListener, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00821) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    GlobalAiTextRequest globalAiTextRequest = this.this$0.mGlobalAiTextRequest;
                    Flow<AICreationResult> flowDoRequest = globalAiTextRequest != null ? globalAiTextRequest.doRequest(this.$queryID, this.$packageName, this.$pageType, this.$style, this.$emoticonsNum, this.$wordNum, this.$userPrompt) : null;
                    this.L$0 = SpillingKt.nullOutSpilledVariable(flowDoRequest);
                    this.label = 1;
                    if (this.this$0.handleResponseFlow(flowDoRequest, this.$data, this.$errorListener, this) == coroutine_suspended) {
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
        }
    }

    private final Job handleResponse(String queryID, String packageName, String pageType, int style, int emoticonsNum, int wordNum, String userPrompt, MutableLiveData<AIResult<AICreationResult>> data, MutableLiveData<AIError<ResponseErrorDetail>> errorListener) {
        return BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(data, this, queryID, packageName, pageType, style, emoticonsNum, wordNum, userPrompt, errorListener, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$2, reason: invalid class name */
    /* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$2", f = "GlobalAiTextRequestManager.kt", i = {}, l = {122}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $comment;
        final /* synthetic */ MutableLiveData<AIResult<AICreationResult>> $data;
        final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
        final /* synthetic */ String $packageName;
        final /* synthetic */ String $pageType;
        final /* synthetic */ String $queryID;
        final /* synthetic */ String $userPrompt;
        final /* synthetic */ int $wordNum;
        int label;
        final /* synthetic */ GlobalAiTextRequestManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(MutableLiveData<AIResult<AICreationResult>> mutableLiveData, GlobalAiTextRequestManager globalAiTextRequestManager, String str, String str2, String str3, int i, int i2, String str4, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$data = mutableLiveData;
            this.this$0 = globalAiTextRequestManager;
            this.$queryID = str;
            this.$packageName = str2;
            this.$pageType = str3;
            this.$comment = i;
            this.$wordNum = i2;
            this.$userPrompt = str4;
            this.$errorListener = mutableLiveData2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$data, this.this$0, this.$queryID, this.$packageName, this.$pageType, this.$comment, this.$wordNum, this.$userPrompt, this.$errorListener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.$data.setValue(AIResult.Loading.INSTANCE);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.this$0, this.$queryID, this.$packageName, this.$pageType, this.$comment, this.$wordNum, this.$userPrompt, this.$data, this.$errorListener, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponse$2$1", f = "GlobalAiTextRequestManager.kt", i = {0}, l = {125}, m = "invokeSuspend", n = {"responseFlow"}, s = {"L$0"})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ int $comment;
            final /* synthetic */ MutableLiveData<AIResult<AICreationResult>> $data;
            final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
            final /* synthetic */ String $packageName;
            final /* synthetic */ String $pageType;
            final /* synthetic */ String $queryID;
            final /* synthetic */ String $userPrompt;
            final /* synthetic */ int $wordNum;
            Object L$0;
            int label;
            final /* synthetic */ GlobalAiTextRequestManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(GlobalAiTextRequestManager globalAiTextRequestManager, String str, String str2, String str3, int i, int i2, String str4, MutableLiveData<AIResult<AICreationResult>> mutableLiveData, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = globalAiTextRequestManager;
                this.$queryID = str;
                this.$packageName = str2;
                this.$pageType = str3;
                this.$comment = i;
                this.$wordNum = i2;
                this.$userPrompt = str4;
                this.$data = mutableLiveData;
                this.$errorListener = mutableLiveData2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, this.$queryID, this.$packageName, this.$pageType, this.$comment, this.$wordNum, this.$userPrompt, this.$data, this.$errorListener, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    GlobalAiTextRequest globalAiTextRequest = this.this$0.mGlobalAiTextRequest;
                    Flow<AICreationResult> flowDoRequest = globalAiTextRequest != null ? globalAiTextRequest.doRequest(this.$queryID, this.$packageName, this.$pageType, this.$comment, this.$wordNum, this.$userPrompt) : null;
                    this.L$0 = SpillingKt.nullOutSpilledVariable(flowDoRequest);
                    this.label = 1;
                    if (this.this$0.handleResponseFlow(flowDoRequest, this.$data, this.$errorListener, this) == coroutine_suspended) {
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
        }
    }

    private final Job handleResponse(String queryID, String packageName, String pageType, int comment, int wordNum, String userPrompt, MutableLiveData<AIResult<AICreationResult>> data, MutableLiveData<AIError<ResponseErrorDetail>> errorListener) {
        return BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass2(data, this, queryID, packageName, pageType, comment, wordNum, userPrompt, errorListener, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object handleResponseFlow(Flow<AICreationResult> flow, final MutableLiveData<AIResult<AICreationResult>> mutableLiveData, final MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super Unit> continuation) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        if (flow != null) {
            Object objCollect = flow.collect(new FlowCollector() { // from class: com.miui.notes.ai.network.GlobalAiTextRequestManager.handleResponseFlow.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation2) {
                    return emit((AICreationResult) obj, (Continuation<? super Unit>) continuation2);
                }

                public final Object emit(AICreationResult aICreationResult, Continuation<? super Unit> continuation2) {
                    Log.d(GlobalAiTextRequestManager.TAG, "handleResponseFlow: code " + aICreationResult.getCode());
                    if (aICreationResult.getCode() == 0) {
                        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(booleanRef, mutableLiveData, aICreationResult, null), 3, null);
                    } else {
                        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C00832(aICreationResult, mutableLiveData2, null), 3, null);
                    }
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponseFlow$2$1, reason: invalid class name */
                /* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
                @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponseFlow$2$1", f = "GlobalAiTextRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ MutableLiveData<AIResult<AICreationResult>> $data;
                    final /* synthetic */ Ref.BooleanRef $isDone;
                    final /* synthetic */ AICreationResult $it;
                    private /* synthetic */ Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    AnonymousClass1(Ref.BooleanRef booleanRef, MutableLiveData<AIResult<AICreationResult>> mutableLiveData, AICreationResult aICreationResult, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.$isDone = booleanRef;
                        this.$data = mutableLiveData;
                        this.$it = aICreationResult;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isDone, this.$data, this.$it, continuation);
                        anonymousClass1.L$0 = obj;
                        return anonymousClass1;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        if (CoroutineScopeKt.isActive(coroutineScope) && !this.$isDone.element) {
                            this.$data.setValue(new AIResult.Complete(this.$it, ""));
                            if (Intrinsics.areEqual(this.$it.getContentStreamResult(), GlobalAiTextRequestManager.FLOW_DONE_TAG)) {
                                this.$data.setValue(AIResult.Finish.INSTANCE);
                                this.$isDone.element = true;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* JADX INFO: renamed from: com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponseFlow$2$2, reason: invalid class name and collision with other inner class name */
                /* JADX INFO: compiled from: GlobalAiTextRequestManager.kt */
                @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                @DebugMetadata(c = "com.miui.notes.ai.network.GlobalAiTextRequestManager$handleResponseFlow$2$2", f = "GlobalAiTextRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                static final class C00832 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
                    final /* synthetic */ AICreationResult $it;
                    private /* synthetic */ Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C00832(AICreationResult aICreationResult, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData, Continuation<? super C00832> continuation) {
                        super(2, continuation);
                        this.$it = aICreationResult;
                        this.$errorListener = mutableLiveData;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        C00832 c00832 = new C00832(this.$it, this.$errorListener, continuation);
                        c00832.L$0 = obj;
                        return c00832;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((C00832) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        if (CoroutineScopeKt.isActive(coroutineScope)) {
                            this.$errorListener.setValue(new AIError.Error(new ResponseErrorDetail(this.$it.getCode(), "", "")));
                        }
                        return Unit.INSTANCE;
                    }
                }
            }, continuation);
            return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    public final void stopRequest() {
        GlobalAiTextRequest globalAiTextRequest = this.mGlobalAiTextRequest;
        if (globalAiTextRequest != null) {
            globalAiTextRequest.stopRequest();
        }
        Job job = this.mGlobalAiGenerateTextJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
    }

    public final void cancelRequest() {
        stopRequest();
        GlobalAiTextRequest globalAiTextRequest = this.mGlobalAiTextRequest;
        if (globalAiTextRequest != null) {
            globalAiTextRequest.cancelRequest();
        }
    }
}
