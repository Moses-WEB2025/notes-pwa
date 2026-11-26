package com.miui.notes.ai.local;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.data.input.PolishStyle;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.network.GlobalAiTextRequestManager;
import com.miui.notes.ai.network.request.AIError;
import com.miui.notes.ai.network.request.AIResult;
import com.miui.notes.ai.network.request.ResponseErrorDetail;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.aicr.llm.TextEditProcessResult;
import defpackage.R2;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: LocalAINotesRequestManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 A2\u00020\u0001:\u0001AB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010#\u001a\u00020$J\u000e\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020'J\u000e\u0010(\u001a\u00020$2\u0006\u0010&\u001a\u00020'J\u0016\u0010)\u001a\u00020$2\u0006\u0010&\u001a\u00020'2\u0006\u0010*\u001a\u00020+J\u000e\u0010,\u001a\u00020$2\u0006\u0010&\u001a\u00020'J\u001e\u0010-\u001a\u00020$2\u0006\u0010&\u001a\u00020'2\u0006\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u00020+J\u001e\u00100\u001a\u00020$2\u0006\u0010&\u001a\u00020'2\u0006\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u00020+JX\u00101\u001a\u00020\u001d2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020'2\u0006\u0010*\u001a\u00020+2\u0006\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u00020+2\u0012\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0012\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\tH\u0002J\u0006\u00107\u001a\u00020$J\u0006\u00108\u001a\u00020$J\u000e\u00109\u001a\u00020$2\u0006\u0010:\u001a\u00020;J\u0006\u0010<\u001a\u00020$J\u0006\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020$2\u0006\u0010@\u001a\u00020'R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\rR\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\rR\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\rR\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lcom/miui/notes/ai/local/LocalAINotesRequestManager;", "", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mLocalLLMManagerUtils", "Lcom/miui/notes/ai/local/LocalLLMManagerUtils;", "_summaryLiveData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/miui/notes/ai/network/request/AIResult;", "Lcom/xiaomi/aicr/llm/TextEditProcessResult;", "get_summaryLiveData", "()Landroidx/lifecycle/MutableLiveData;", "_correctErrorsLiveData", "get_correctErrorsLiveData", "_polishTextLiveData", "get_polishTextLiveData", "_expandTextLiveData", "get_expandTextLiveData", "_inputTextLiveData", "get_inputTextLiveData", "_recommendTextLiveData", "get_recommendTextLiveData", "_errorLiveData", "Lcom/miui/notes/ai/network/request/AIError;", "Lcom/miui/notes/ai/network/request/ResponseErrorDetail;", "get_errorLiveData", "summarizeJob", "Lkotlinx/coroutines/Job;", "correctErrorsJob", "polishTextJob", "expendTextJob", "inputTextJob", "recommendTextJob", "initManager", "", "requestSummarizeText", "inputText", "", "requestCorrectErrors", "requestPolishText", "polishType", "", "requestExpandText", "requestInputCotText", SmartAction.Feature.QUERY, "addThink", "requestRecommendCotText", "handleResponse", "requestType", "Lcom/miui/notes/ai/data/input/RequestType;", "contentInput", "data", "errorListener", "stopRequest", "cancelRequest", "searchDevice", "llmSearchListener", "Lcom/miui/notes/ai/local/LLMSearchListener;", "cancelSearchDeviceLLM", "textEditCheck", "", "initModel", "mode", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LocalAINotesRequestManager {
    public static final int RESULT_CODE_OK = 0;
    private static final String TAG = "LocalAINotesRequestManager";
    private final MutableLiveData<AIResult<TextEditProcessResult>> _correctErrorsLiveData;
    private final MutableLiveData<AIError<ResponseErrorDetail>> _errorLiveData;
    private final MutableLiveData<AIResult<TextEditProcessResult>> _expandTextLiveData;
    private final MutableLiveData<AIResult<TextEditProcessResult>> _inputTextLiveData;
    private final MutableLiveData<AIResult<TextEditProcessResult>> _polishTextLiveData;
    private final MutableLiveData<AIResult<TextEditProcessResult>> _recommendTextLiveData;
    private final MutableLiveData<AIResult<TextEditProcessResult>> _summaryLiveData;
    private Job correctErrorsJob;
    private Job expendTextJob;
    private Job inputTextJob;
    private LocalLLMManagerUtils mLocalLLMManagerUtils;
    private Job polishTextJob;
    private Job recommendTextJob;
    private Job summarizeJob;

    public LocalAINotesRequestManager(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this._summaryLiveData = new MutableLiveData<>();
        this._correctErrorsLiveData = new MutableLiveData<>();
        this._polishTextLiveData = new MutableLiveData<>();
        this._expandTextLiveData = new MutableLiveData<>();
        this._inputTextLiveData = new MutableLiveData<>();
        this._recommendTextLiveData = new MutableLiveData<>();
        this._errorLiveData = new MutableLiveData<>();
        this.mLocalLLMManagerUtils = new LocalLLMManagerUtils(mContext);
    }

    public final MutableLiveData<AIResult<TextEditProcessResult>> get_summaryLiveData() {
        return this._summaryLiveData;
    }

    public final MutableLiveData<AIResult<TextEditProcessResult>> get_correctErrorsLiveData() {
        return this._correctErrorsLiveData;
    }

    public final MutableLiveData<AIResult<TextEditProcessResult>> get_polishTextLiveData() {
        return this._polishTextLiveData;
    }

    public final MutableLiveData<AIResult<TextEditProcessResult>> get_expandTextLiveData() {
        return this._expandTextLiveData;
    }

    public final MutableLiveData<AIResult<TextEditProcessResult>> get_inputTextLiveData() {
        return this._inputTextLiveData;
    }

    public final MutableLiveData<AIResult<TextEditProcessResult>> get_recommendTextLiveData() {
        return this._recommendTextLiveData;
    }

    public final MutableLiveData<AIError<ResponseErrorDetail>> get_errorLiveData() {
        return this._errorLiveData;
    }

    public final void initManager() {
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        if (localLLMManagerUtils != null) {
            localLLMManagerUtils.initManager();
        }
    }

    public final void requestSummarizeText(String inputText) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestSummarizeText");
        this.summarizeJob = handleResponse(RequestType.TYPE_SUMMARY, inputText, PolishStyle.TYPE_UNKNOWN.getValue(), "", 0, this._summaryLiveData, this._errorLiveData);
    }

    public final void requestCorrectErrors(String inputText) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestCorrectErrors");
        this.correctErrorsJob = handleResponse(RequestType.TYPE_CHECK, inputText, PolishStyle.TYPE_UNKNOWN.getValue(), "", 0, this._correctErrorsLiveData, this._errorLiveData);
    }

    public final void requestPolishText(String inputText, int polishType) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestPolishText");
        this.polishTextJob = handleResponse(RequestType.TYPE_POLISH, inputText, polishType, "", 0, this._polishTextLiveData, this._errorLiveData);
    }

    public final void requestExpandText(String inputText) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Logger.INSTANCE.i(TAG, "requestExpandText");
        this.expendTextJob = handleResponse(RequestType.TYPE_EXTENSION, inputText, PolishStyle.TYPE_UNKNOWN.getValue(), "", 0, this._expandTextLiveData, this._errorLiveData);
    }

    public final void requestInputCotText(String inputText, String query, int addThink) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Intrinsics.checkNotNullParameter(query, "query");
        Logger.INSTANCE.i(TAG, "requestInputCotText");
        this.inputTextJob = handleResponse(RequestType.TYPE_INPUT, inputText, PolishStyle.TYPE_UNKNOWN.getValue(), query, addThink, this._inputTextLiveData, this._errorLiveData);
    }

    public final void requestRecommendCotText(String inputText, String query, int addThink) {
        Intrinsics.checkNotNullParameter(inputText, "inputText");
        Intrinsics.checkNotNullParameter(query, "query");
        Logger.INSTANCE.i(TAG, "requestRecommendCotText");
        this.recommendTextJob = handleResponse(RequestType.TYPE_RECOMMEND, inputText, PolishStyle.TYPE_UNKNOWN.getValue(), query, addThink, this._recommendTextLiveData, this._errorLiveData);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1, reason: invalid class name */
    /* JADX INFO: compiled from: LocalAINotesRequestManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1", f = "LocalAINotesRequestManager.kt", i = {}, l = {R2.array.ai_panel_blend_colors}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $addThink;
        final /* synthetic */ String $contentInput;
        final /* synthetic */ MutableLiveData<AIResult<TextEditProcessResult>> $data;
        final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
        final /* synthetic */ int $polishType;
        final /* synthetic */ String $query;
        final /* synthetic */ RequestType $requestType;
        int label;
        final /* synthetic */ LocalAINotesRequestManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(MutableLiveData<AIResult<TextEditProcessResult>> mutableLiveData, LocalAINotesRequestManager localAINotesRequestManager, RequestType requestType, String str, int i, String str2, int i2, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$data = mutableLiveData;
            this.this$0 = localAINotesRequestManager;
            this.$requestType = requestType;
            this.$contentInput = str;
            this.$polishType = i;
            this.$query = str2;
            this.$addThink = i2;
            this.$errorListener = mutableLiveData2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$data, this.this$0, this.$requestType, this.$contentInput, this.$polishType, this.$query, this.$addThink, this.$errorListener, continuation);
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
                Logger.INSTANCE.i(LocalAINotesRequestManager.TAG, "handleResponse data.value : " + this.$data.getValue());
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00761(this.this$0, this.$requestType, this.$contentInput, this.$polishType, this.$query, this.$addThink, this.$errorListener, this.$data, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: LocalAINotesRequestManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1$1", f = "LocalAINotesRequestManager.kt", i = {0}, l = {152}, m = "invokeSuspend", n = {"responseFlow"}, s = {"L$0"})
        static final class C00761 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ int $addThink;
            final /* synthetic */ String $contentInput;
            final /* synthetic */ MutableLiveData<AIResult<TextEditProcessResult>> $data;
            final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
            final /* synthetic */ int $polishType;
            final /* synthetic */ String $query;
            final /* synthetic */ RequestType $requestType;
            Object L$0;
            int label;
            final /* synthetic */ LocalAINotesRequestManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00761(LocalAINotesRequestManager localAINotesRequestManager, RequestType requestType, String str, int i, String str2, int i2, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData, MutableLiveData<AIResult<TextEditProcessResult>> mutableLiveData2, Continuation<? super C00761> continuation) {
                super(2, continuation);
                this.this$0 = localAINotesRequestManager;
                this.$requestType = requestType;
                this.$contentInput = str;
                this.$polishType = i;
                this.$query = str2;
                this.$addThink = i2;
                this.$errorListener = mutableLiveData;
                this.$data = mutableLiveData2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00761(this.this$0, this.$requestType, this.$contentInput, this.$polishType, this.$query, this.$addThink, this.$errorListener, this.$data, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00761) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LocalLLMManagerUtils localLLMManagerUtils = this.this$0.mLocalLLMManagerUtils;
                    Flow<TextEditProcessResult> flowDoRequest = localLLMManagerUtils != null ? localLLMManagerUtils.doRequest(this.$requestType, this.$contentInput, this.$polishType, this.$query, this.$addThink) : null;
                    if (flowDoRequest != null) {
                        final MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData = this.$errorListener;
                        final MutableLiveData<AIResult<TextEditProcessResult>> mutableLiveData2 = this.$data;
                        this.L$0 = SpillingKt.nullOutSpilledVariable(flowDoRequest);
                        this.label = 1;
                        if (flowDoRequest.collect(new FlowCollector() { // from class: com.miui.notes.ai.local.LocalAINotesRequestManager.handleResponse.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                                return emit((TextEditProcessResult) obj2, (Continuation<? super Unit>) continuation);
                            }

                            public final Object emit(TextEditProcessResult textEditProcessResult, Continuation<? super Unit> continuation) {
                                Logger.INSTANCE.e(LocalAINotesRequestManager.TAG, textEditProcessResult.getCode() + " +++ " + textEditProcessResult.getContentResult() + " ++= " + textEditProcessResult.getContentStreamResult() + " +== " + textEditProcessResult.getErrorNum());
                                if (textEditProcessResult.getCode() != 0) {
                                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C00781(textEditProcessResult, mutableLiveData, null), 3, null);
                                } else {
                                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass2(mutableLiveData2, textEditProcessResult, null), 3, null);
                                }
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1$1$1$1, reason: invalid class name and collision with other inner class name */
                            /* JADX INFO: compiled from: LocalAINotesRequestManager.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1$1$1$1", f = "LocalAINotesRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class C00781 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIError<ResponseErrorDetail>> $errorListener;
                                final /* synthetic */ TextEditProcessResult $it;
                                private /* synthetic */ Object L$0;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                C00781(TextEditProcessResult textEditProcessResult, MutableLiveData<AIError<ResponseErrorDetail>> mutableLiveData, Continuation<? super C00781> continuation) {
                                    super(2, continuation);
                                    this.$it = textEditProcessResult;
                                    this.$errorListener = mutableLiveData;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    C00781 c00781 = new C00781(this.$it, this.$errorListener, continuation);
                                    c00781.L$0 = obj;
                                    return c00781;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((C00781) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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

                            /* JADX INFO: renamed from: com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1$1$1$2, reason: invalid class name */
                            /* JADX INFO: compiled from: LocalAINotesRequestManager.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
                            @DebugMetadata(c = "com.miui.notes.ai.local.LocalAINotesRequestManager$handleResponse$1$1$1$2", f = "LocalAINotesRequestManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ MutableLiveData<AIResult<TextEditProcessResult>> $data;
                                final /* synthetic */ TextEditProcessResult $it;
                                private /* synthetic */ Object L$0;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                AnonymousClass2(MutableLiveData<AIResult<TextEditProcessResult>> mutableLiveData, TextEditProcessResult textEditProcessResult, Continuation<? super AnonymousClass2> continuation) {
                                    super(2, continuation);
                                    this.$data = mutableLiveData;
                                    this.$it = textEditProcessResult;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$data, this.$it, continuation);
                                    anonymousClass2.L$0 = obj;
                                    return anonymousClass2;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                                        this.$data.setValue(new AIResult.Complete(this.$it, ""));
                                        if (Intrinsics.areEqual(this.$it.getContentStreamResult(), GlobalAiTextRequestManager.FLOW_DONE_TAG)) {
                                            Logger.INSTANCE.d(LocalAINotesRequestManager.TAG, "it.contentStreamResult == [DONE]");
                                            this.$data.setValue(AIResult.Finish.INSTANCE);
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }
                        }, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
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

    private final Job handleResponse(RequestType requestType, String contentInput, int polishType, String query, int addThink, MutableLiveData<AIResult<TextEditProcessResult>> data, MutableLiveData<AIError<ResponseErrorDetail>> errorListener) {
        return BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(data, this, requestType, contentInput, polishType, query, addThink, errorListener, null), 3, null);
    }

    public final void stopRequest() {
        Logger.INSTANCE.i(TAG, "cancelAllLocal");
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        if (localLLMManagerUtils != null) {
            localLLMManagerUtils.stopRequest();
        }
        Job job = this.summarizeJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        Job job2 = this.correctErrorsJob;
        if (job2 != null) {
            Job.DefaultImpls.cancel$default(job2, (CancellationException) null, 1, (Object) null);
        }
        Job job3 = this.expendTextJob;
        if (job3 != null) {
            Job.DefaultImpls.cancel$default(job3, (CancellationException) null, 1, (Object) null);
        }
        Job job4 = this.polishTextJob;
        if (job4 != null) {
            Job.DefaultImpls.cancel$default(job4, (CancellationException) null, 1, (Object) null);
        }
        Job job5 = this.inputTextJob;
        if (job5 != null) {
            Job.DefaultImpls.cancel$default(job5, (CancellationException) null, 1, (Object) null);
        }
        Job job6 = this.recommendTextJob;
        if (job6 != null) {
            Job.DefaultImpls.cancel$default(job6, (CancellationException) null, 1, (Object) null);
        }
    }

    public final void cancelRequest() {
        Logger.INSTANCE.i(TAG, "releaseAllLocal");
        stopRequest();
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        if (localLLMManagerUtils != null) {
            localLLMManagerUtils.cancelRequest();
        }
    }

    public final void searchDevice(LLMSearchListener llmSearchListener) {
        Intrinsics.checkNotNullParameter(llmSearchListener, "llmSearchListener");
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        if (localLLMManagerUtils != null) {
            localLLMManagerUtils.searchDeviceLLM(llmSearchListener);
        }
    }

    public final void cancelSearchDeviceLLM() {
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        if (localLLMManagerUtils != null) {
            localLLMManagerUtils.cancelSearchDeviceLLM();
        }
    }

    public final boolean textEditCheck() {
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        Boolean boolValueOf = localLLMManagerUtils != null ? Boolean.valueOf(localLLMManagerUtils.textEditCheck()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        return boolValueOf.booleanValue();
    }

    public final void initModel(String mode) {
        Unit unit;
        Intrinsics.checkNotNullParameter(mode, "mode");
        LocalLLMManagerUtils localLLMManagerUtils = this.mLocalLLMManagerUtils;
        if (localLLMManagerUtils != null) {
            localLLMManagerUtils.initModel(mode);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        Intrinsics.checkNotNull(unit);
    }
}
