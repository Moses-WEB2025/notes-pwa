package com.miui.notes.ai.local;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.data.input.PolishStyle;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.llm.IDeviceSearchCallback;
import com.xiaomi.aicr.llm.LLMManager;
import com.xiaomi.aicr.llm.TextEditProcessResult;
import kotlin.Metadata;
import kotlin.ResultKt;
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
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: LocalLLMManagerUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\u0018\u0000 /2\u00020\u0001:\u0001/B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0012H\u0002J@\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\fH\u0002J\u0018\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010 \u001a\u00020\u001aH\u0002J\u0018\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010 \u001a\u00020\u001aH\u0002J\u0018\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010 \u001a\u00020\u001aH\u0002J \u0010#\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\fH\u0002J(\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\fH\u0002J\b\u0010%\u001a\u00020\tH\u0002J\u0006\u0010&\u001a\u00020\u0012J\u0006\u0010'\u001a\u00020\u0012J6\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\fJ\u000e\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u0010J\u0006\u0010+\u001a\u00020\u0012J\u0006\u0010,\u001a\u00020\tJ\u000e\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lcom/miui/notes/ai/local/LocalLLMManagerUtils;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mLLMManager", "Lcom/xiaomi/aicr/llm/LLMManager;", "isConnected", "", "isDeviceConnected", "maxRetryCount", "", "currentRetryCount", "isNeedSearchDevice", "mLlmSearchListener", "Lcom/miui/notes/ai/local/LLMSearchListener;", "initManager", "", "initLLMManagerConnection", "retryConnection", "Lkotlinx/coroutines/flow/Flow;", "Lcom/xiaomi/aicr/llm/TextEditProcessResult;", "requestType", "Lcom/miui/notes/ai/data/input/RequestType;", "contentInput", "", "polishType", "code", SmartAction.Feature.QUERY, "addThink", "addTextEditSummaryRequest", "inputText", "addTextEditExpandRequest", "addTextEditCorrectRequest", "addTextEditPolishRequest", "addTextEditCOTRequest", "isLLMSupported", "cancelRequest", "stopRequest", "doRequest", "searchDeviceLLM", "llmSearchListener", "cancelSearchDeviceLLM", "textEditCheck", "initModel", "mode", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LocalLLMManagerUtils {
    private static final String BIZ_CODE = "note";
    private static final String TAG = "LocalLLMManagerUtils";
    private final Context context;
    private int currentRetryCount;
    private boolean isConnected;
    private boolean isDeviceConnected;
    private boolean isNeedSearchDevice;
    private LLMManager mLLMManager;
    private LLMSearchListener mLlmSearchListener;
    private final int maxRetryCount;

    /* JADX INFO: compiled from: LocalLLMManagerUtils.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RequestType.values().length];
            try {
                iArr[RequestType.TYPE_SUMMARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RequestType.TYPE_CHECK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RequestType.TYPE_EXTENSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[RequestType.TYPE_POLISH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[RequestType.TYPE_INPUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[RequestType.TYPE_RECOMMEND.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LocalLLMManagerUtils(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.maxRetryCount = 3;
    }

    public final void initManager() {
        if (isLLMSupported() || RomUtils.isLLMAIWriting(this.context)) {
            initLLMManagerConnection();
        } else {
            Logger.INSTANCE.e(TAG, "This device doesn't support MIAI Engine");
        }
    }

    private final void initLLMManagerConnection() {
        this.mLLMManager = new LLMManager(this.context, new AiServiceConnection() { // from class: com.miui.notes.ai.local.LocalLLMManagerUtils.initLLMManagerConnection.1
            @Override // com.xiaomi.aicr.common.AiServiceConnection
            public void onServiceConnected(int connectStatus) {
                super.onServiceConnected(connectStatus);
                LocalLLMManagerUtils.this.isConnected = true;
                LocalLLMManagerUtils.this.currentRetryCount = 0;
                Logger.INSTANCE.d(LocalLLMManagerUtils.TAG, "LLMManager Service Connected");
                if (LocalLLMManagerUtils.this.isNeedSearchDevice) {
                    LLMSearchListener lLMSearchListener = LocalLLMManagerUtils.this.mLlmSearchListener;
                    if (lLMSearchListener != null) {
                        LocalLLMManagerUtils.this.searchDeviceLLM(lLMSearchListener);
                    }
                    LocalLLMManagerUtils.this.isNeedSearchDevice = false;
                }
            }

            @Override // com.xiaomi.aicr.common.AiServiceConnection
            public void onServiceDisconnected() {
                super.onServiceDisconnected();
                LocalLLMManagerUtils.this.isConnected = false;
                Logger.INSTANCE.d(LocalLLMManagerUtils.TAG, "LLMManager Service Disconnected");
            }

            @Override // com.xiaomi.aicr.common.AiServiceConnection
            public void onError(int code) {
                super.onError(code);
                LocalLLMManagerUtils.this.isConnected = false;
                Logger.INSTANCE.e(LocalLLMManagerUtils.TAG, "LLMManager Service Connect Error: " + code);
            }
        }, null, 4, null);
    }

    private final Flow<TextEditProcessResult> retryConnection(RequestType requestType, String contentInput, int polishType, int code, String query, int addThink) {
        if (this.currentRetryCount < this.maxRetryCount) {
            LLMManager lLMManager = this.mLLMManager;
            Intrinsics.checkNotNull(lLMManager);
            if (!lLMManager.isConnected()) {
                cancelRequest();
                initLLMManagerConnection();
            }
            this.currentRetryCount++;
            Logger.INSTANCE.d(TAG, "Attempting to retry connection: Attempt " + this.currentRetryCount);
            return doRequest(requestType, contentInput, polishType, query, addThink);
        }
        return FlowKt.callbackFlow(new C02641(code, null));
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.local.LocalLLMManagerUtils$retryConnection$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LocalLLMManagerUtils.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/xiaomi/aicr/llm/TextEditProcessResult;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.local.LocalLLMManagerUtils$retryConnection$1", f = "LocalLLMManagerUtils.kt", i = {0}, l = {91}, m = "invokeSuspend", n = {"$this$callbackFlow"}, s = {"L$0"})
    static final class C02641 extends SuspendLambda implements Function2<ProducerScope<? super TextEditProcessResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $code;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02641(int i, Continuation<? super C02641> continuation) {
            super(2, continuation);
            this.$code = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02641 c02641 = LocalLLMManagerUtils.this.new C02641(this.$code, continuation);
            c02641.L$0 = obj;
            return c02641;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super TextEditProcessResult> producerScope, Continuation<? super Unit> continuation) {
            return ((C02641) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProducerScope producerScope = (ProducerScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.e(LocalLLMManagerUtils.TAG, "Failed to connect after " + LocalLLMManagerUtils.this.maxRetryCount + " attempts");
                producerScope.mo4054trySendJP2dKIU(new TextEditProcessResult("", "", this.$code, 0));
                this.L$0 = SpillingKt.nullOutSpilledVariable(producerScope);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, new Function0() { // from class: com.miui.notes.ai.local.LocalLLMManagerUtils$retryConnection$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LocalLLMManagerUtils.C02641.invokeSuspend$lambda$0();
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
            Logger.INSTANCE.d(LocalLLMManagerUtils.TAG, "CallbackFlow closed, perform cleanup if needed");
            return Unit.INSTANCE;
        }
    }

    private final Flow<TextEditProcessResult> addTextEditSummaryRequest(String inputText) {
        Logger.INSTANCE.d(TAG, "addTextEditSummaryRequest");
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            return lLMManager.addTextEditSummaryRequest("note", inputText);
        }
        return null;
    }

    private final Flow<TextEditProcessResult> addTextEditExpandRequest(String inputText) {
        Logger.INSTANCE.d(TAG, "addTextEditExpandRequest");
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            return lLMManager.addTextEditExpandRequest("note", inputText);
        }
        return null;
    }

    private final Flow<TextEditProcessResult> addTextEditCorrectRequest(String inputText) {
        Logger logger = Logger.INSTANCE;
        LLMManager lLMManager = this.mLLMManager;
        logger.d(TAG, "addTextEditCorrectRequest mLLMManager!!.isConnected = " + (lLMManager != null ? Boolean.valueOf(lLMManager.isConnected()) : null));
        LLMManager lLMManager2 = this.mLLMManager;
        if (lLMManager2 != null) {
            return lLMManager2.addTextEditCorrectRequest("note", inputText);
        }
        return null;
    }

    private final Flow<TextEditProcessResult> addTextEditPolishRequest(String inputText, int polishType) {
        Logger.INSTANCE.d(TAG, "addTextEditPolishRequest");
        int value = PolishStyle.TYPE_COMMON.getValue();
        String str = LLMManager.POLISH_STYLE_GENERAL;
        if (polishType != value) {
            if (polishType == PolishStyle.TYPE_PROFESSIONAL.getValue()) {
                str = LLMManager.POLISH_STYLE_PROFESSIONAL;
            } else if (polishType == PolishStyle.TYPE_EASY.getValue()) {
                str = LLMManager.POLISH_STYLE_CONCISE;
            } else if (polishType == PolishStyle.TYPE_FRIENDLY.getValue()) {
                str = LLMManager.POLISH_STYLE_FRIENDLY;
            }
        }
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            return lLMManager.addTextEditPolishRequest("note", inputText, str);
        }
        return null;
    }

    private final Flow<TextEditProcessResult> addTextEditCOTRequest(String inputText, String query, int addThink) {
        Logger.INSTANCE.d(TAG, "addTextEditCOTRequest");
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            return lLMManager.addTextEditCOTRequest(inputText, query, addThink);
        }
        return null;
    }

    private final boolean isLLMSupported() {
        boolean zIsLLMSupport = LLMManager.INSTANCE.isLLMSupport(this.context, 9003);
        Logger.INSTANCE.d(TAG, "LLMManager is supported: " + zIsLLMSupport);
        return zIsLLMSupport;
    }

    public final void cancelRequest() {
        Logger.INSTANCE.d(TAG, "cancel LLMManager Connection");
        try {
            LLMManager lLMManager = this.mLLMManager;
            if (lLMManager != null) {
                Intrinsics.checkNotNull(lLMManager);
                if (lLMManager.isConnected()) {
                    Logger.INSTANCE.d(TAG, "cancel LLMManager Connection for still bind");
                    LLMManager lLMManager2 = this.mLLMManager;
                    if (lLMManager2 != null) {
                        lLMManager2.releaseConnect();
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public final void stopRequest() {
        Logger.INSTANCE.d(TAG, "stopAITextEditRequest mLLMManager：" + this.mLLMManager);
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            lLMManager.refresh();
        }
        this.currentRetryCount = 0;
    }

    public final Flow<TextEditProcessResult> doRequest(RequestType requestType, String contentInput, int polishType, String query, int addThink) {
        Intrinsics.checkNotNullParameter(requestType, "requestType");
        Intrinsics.checkNotNullParameter(contentInput, "contentInput");
        Intrinsics.checkNotNullParameter(query, "query");
        Logger.INSTANCE.d(TAG, "doRequest mLLMManager: " + this.mLLMManager);
        LLMManager lLMManager = this.mLLMManager;
        Intrinsics.checkNotNull(lLMManager);
        int iTextEditCheck = lLMManager.textEditCheck("");
        Logger.INSTANCE.d(TAG, "doRequest res: " + iTextEditCheck);
        if (iTextEditCheck != 0 && iTextEditCheck != 11 && iTextEditCheck != 3) {
            return FlowKt.callbackFlow(new AnonymousClass1(iTextEditCheck, null));
        }
        LLMManager lLMManager2 = this.mLLMManager;
        Intrinsics.checkNotNull(lLMManager2);
        if (lLMManager2.isConnected() && iTextEditCheck == 0) {
            switch (WhenMappings.$EnumSwitchMapping$0[requestType.ordinal()]) {
                case 1:
                    return addTextEditSummaryRequest(contentInput);
                case 2:
                    return addTextEditCorrectRequest(contentInput);
                case 3:
                    return addTextEditExpandRequest(contentInput);
                case 4:
                    return addTextEditPolishRequest(contentInput, polishType);
                case 5:
                    return addTextEditCOTRequest(contentInput, query, addThink);
                case 6:
                    return addTextEditCOTRequest(contentInput, query, addThink);
                default:
                    return null;
            }
        }
        return retryConnection(requestType, contentInput, polishType, iTextEditCheck, query, addThink);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.local.LocalLLMManagerUtils$doRequest$1, reason: invalid class name */
    /* JADX INFO: compiled from: LocalLLMManagerUtils.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/xiaomi/aicr/llm/TextEditProcessResult;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.ai.local.LocalLLMManagerUtils$doRequest$1", f = "LocalLLMManagerUtils.kt", i = {0}, l = {192}, m = "invokeSuspend", n = {"$this$callbackFlow"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<ProducerScope<? super TextEditProcessResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $res;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(int i, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$res = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$res, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super TextEditProcessResult> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProducerScope producerScope = (ProducerScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                producerScope.mo4054trySendJP2dKIU(new TextEditProcessResult("", "", this.$res, 0));
                this.L$0 = SpillingKt.nullOutSpilledVariable(producerScope);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, new Function0() { // from class: com.miui.notes.ai.local.LocalLLMManagerUtils$doRequest$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LocalLLMManagerUtils.AnonymousClass1.invokeSuspend$lambda$0();
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
            Logger.INSTANCE.d(LocalLLMManagerUtils.TAG, "CallbackFlow closed, perform cleanup if needed");
            return Unit.INSTANCE;
        }
    }

    public final void searchDeviceLLM(final LLMSearchListener llmSearchListener) {
        Intrinsics.checkNotNullParameter(llmSearchListener, "llmSearchListener");
        Logger.INSTANCE.i(TAG, "searchDeviceLLM mLLMManager ：" + this.mLLMManager);
        if (!this.isConnected && !this.isDeviceConnected) {
            Logger.INSTANCE.d(TAG, "LLMManager Service not Connected,search return");
            this.isNeedSearchDevice = true;
            this.mLlmSearchListener = llmSearchListener;
        } else {
            if (this.isDeviceConnected) {
                Logger.INSTANCE.i(TAG, "searchDeviceLLM is connected,return");
                return;
            }
            LLMManager lLMManager = this.mLLMManager;
            if (lLMManager != null) {
                lLMManager.textEditSearchDevice(new IDeviceSearchCallback.Stub() { // from class: com.miui.notes.ai.local.LocalLLMManagerUtils.searchDeviceLLM.1
                    @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
                    public void onConnected() {
                        Logger.INSTANCE.i(LocalLLMManagerUtils.TAG, "searchDeviceLLM connect");
                        llmSearchListener.onConnected();
                        this.isDeviceConnected = true;
                    }

                    @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
                    public void onDisConnected() {
                        Logger.INSTANCE.i(LocalLLMManagerUtils.TAG, "searchDeviceLLM disconnect");
                        llmSearchListener.onDisConnected();
                        this.isDeviceConnected = false;
                    }

                    @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
                    public void onFailed(int error) {
                        Logger.INSTANCE.i(LocalLLMManagerUtils.TAG, "searchDeviceLLM error: " + error);
                        llmSearchListener.onFailed(error);
                        this.isDeviceConnected = false;
                    }
                });
            }
        }
    }

    public final void cancelSearchDeviceLLM() {
        Logger.INSTANCE.i(TAG, "cancelSearchDeviceLLM mLLMManager ：" + this.mLLMManager);
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            lLMManager.cancelTextEditSearchDevice();
        }
    }

    public final boolean textEditCheck() {
        Logger logger = Logger.INSTANCE;
        LLMManager lLMManager = this.mLLMManager;
        logger.i(TAG, "textEditCheck mLLMManager ：" + (lLMManager != null ? Integer.valueOf(lLMManager.textEditCheck("")) : null));
        LLMManager lLMManager2 = this.mLLMManager;
        return lLMManager2 != null && lLMManager2.textEditCheck("") == 0;
    }

    public final void initModel(String mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Logger.INSTANCE.i(TAG, "initModel mode: " + mode + " , mLLMManager ：" + this.mLLMManager);
        LLMManager lLMManager = this.mLLMManager;
        if (lLMManager != null) {
            lLMManager.initModel(mode, -1);
        }
    }
}
