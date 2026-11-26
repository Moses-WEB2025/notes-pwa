package com.miui.notes.ai.local;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.Observer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.data.input.UserInput;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.data.input.UserStatus;
import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.network.NetworkError;
import com.miui.notes.ai.network.request.AIError;
import com.miui.notes.ai.network.request.AIResult;
import com.miui.notes.ai.network.request.ResponseErrorDetail;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.aicr.llm.TextEditProcessResult;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LocalMIAIUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 =2\u00020\u0001:\u0001=B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0015J\u0006\u0010\u001c\u001a\u00020\u0017J\u0016\u0010)\u001a\u00020\u00172\f\u0010*\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002J\u0016\u0010+\u001a\u00020\u00172\f\u0010,\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002J\u0014\u0010-\u001a\u00020\u00172\n\u0010*\u001a\u0006\u0012\u0002\b\u00030'H\u0002J\u0014\u0010.\u001a\u00020\u00172\n\u0010*\u001a\u0006\u0012\u0002\b\u00030\u001fH\u0002J\b\u0010/\u001a\u00020\u0017H\u0002J\u0006\u00100\u001a\u00020\u0017J\u0006\u00101\u001a\u00020\u0017J\u0006\u00102\u001a\u00020\u0017J\u0016\u00103\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u00104\u001a\u000205J\u0006\u00106\u001a\u00020\u0017J\u0006\u00107\u001a\u00020\u0017J\u0006\u00108\u001a\u000209J\u000e\u0010:\u001a\u00020\u00172\u0006\u0010;\u001a\u00020<R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0'0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/miui/notes/ai/local/LocalMIAIUtils;", "", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mLocalAINotesRequestManager", "Lcom/miui/notes/ai/local/LocalAINotesRequestManager;", "getMLocalAINotesRequestManager", "()Lcom/miui/notes/ai/local/LocalAINotesRequestManager;", "mLocalAINotesRequestManager$delegate", "Lkotlin/Lazy;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "setHandler", "(Landroid/os/Handler;)V", "mDataListener", "Lcom/miui/notes/ai/data/DataListener;", "mLLMSearchListener", "Lcom/miui/notes/ai/local/LLMSearchListener;", "registerDataListener", "", "dataListener", "unregisterDataListener", "setLLMSearchListener", "llmSearchListener", "unregisterSearchListener", "mSummaryObserver", "Landroidx/lifecycle/Observer;", "Lcom/miui/notes/ai/network/request/AIResult;", "Lcom/xiaomi/aicr/llm/TextEditProcessResult;", "mExpendObserver", "mPolishObserver", "mCorrectObserver", "mInputObserver", "mRecommendObserver", "mErrorObserver", "Lcom/miui/notes/ai/network/request/AIError;", "Lcom/miui/notes/ai/network/request/ResponseErrorDetail;", "onAIResultChanged", "resultContentAIResult", "onAICorrectError", "result", "handleErrorResult", "handleUnCompleteResult", "unregisterObserver", "initManager", "stopRequest", "cancelRequest", "startRequest", "addThink", "", "searchDevice", "cancelSearchDevice", "textEditCheck", "", "initModel", "mode", "", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LocalMIAIUtils {
    public static final int STATE_DEVICE_LOW_BATTERY_ERROR = 11;
    public static final int STATE_DEVICE_OVER_HEAT_ERROR = 10;
    public static final int STATE_FOREVER_BANNED_ERROR = 5;
    public static final int STATE_ILLEGAL_ERROR = 3;
    public static final int STATE_LANGUAGE_UNSUPPORTED_ERROR = 13;
    public static final int STATE_NETWORK_ERROR = 12;
    public static final int STATE_NORMAL_ERROR = 2;
    public static final int STATE_NOT_SUPPORT_ERROR = 8;
    public static final int STATE_TEXT_LENGTH_EXCEED_LIMIT_ERROR = 7;
    public static final int STATUS_ACCOUNT_BANNED = 13;
    public static final int STATUS_BUSY = 9;
    public static final int STATUS_DEVICE_DISCONNECT = 14;
    public static final int STATUS_DEVICE_DISCONNECT_ERROR = 14;
    public static final int STATUS_ERROR_FORMAT = 7;
    public static final int STATUS_ERROR_REQUEST = 412;
    public static final int STATUS_HF_BANNED = 12;
    public static final int STATUS_LANGUAGE_UNSUPPORTED = 415;
    public static final int STATUS_LOW_BATTERY = 6;
    public static final int STATUS_NETWORK_ERROR = 11;
    public static final int STATUS_NOT_SUPPORT_ERROR_LONG = 404;
    public static final int STATUS_NO_REMAINING_TIME = 8;
    public static final int STATUS_OVERHEAT = 5;
    public static final int STATUS_PASS = 0;
    public static final int STATUS_TEXT_LENGTH_TOO_LONG = 413;
    public static final int STATUS_TIMEOUT = 10;
    public static final int STATUS_UNCONNECTED = 3;
    public static final int STATUS_UNDOWNLOADED = 1;
    public static final int STATUS_UNKNOWN_ERROR = 4;
    public static final int STATUS_UNSUPPORTED = 2;
    private static final String TAG = "LocalMIAIUtils";
    private Handler handler;
    private final Context mContext;
    private final Observer<AIResult<TextEditProcessResult>> mCorrectObserver;
    private DataListener mDataListener;
    private final Observer<AIError<ResponseErrorDetail>> mErrorObserver;
    private final Observer<AIResult<TextEditProcessResult>> mExpendObserver;
    private final Observer<AIResult<TextEditProcessResult>> mInputObserver;
    private LLMSearchListener mLLMSearchListener;

    /* JADX INFO: renamed from: mLocalAINotesRequestManager$delegate, reason: from kotlin metadata */
    private final Lazy mLocalAINotesRequestManager;
    private final Observer<AIResult<TextEditProcessResult>> mPolishObserver;
    private final Observer<AIResult<TextEditProcessResult>> mRecommendObserver;
    private final Observer<AIResult<TextEditProcessResult>> mSummaryObserver;

    /* JADX INFO: compiled from: LocalMIAIUtils.kt */
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

    public LocalMIAIUtils(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
        this.mLocalAINotesRequestManager = LazyKt.lazy(new Function0() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LocalMIAIUtils.mLocalAINotesRequestManager_delegate$lambda$0(this.f$0);
            }
        });
        this.handler = new Handler(Looper.getMainLooper());
        Observer<AIResult<TextEditProcessResult>> observer = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mSummaryObserver$lambda$1(this.f$0, (AIResult) obj);
            }
        };
        this.mSummaryObserver = observer;
        Observer<AIResult<TextEditProcessResult>> observer2 = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mExpendObserver$lambda$2(this.f$0, (AIResult) obj);
            }
        };
        this.mExpendObserver = observer2;
        Observer<AIResult<TextEditProcessResult>> observer3 = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mPolishObserver$lambda$3(this.f$0, (AIResult) obj);
            }
        };
        this.mPolishObserver = observer3;
        Observer<AIResult<TextEditProcessResult>> observer4 = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mCorrectObserver$lambda$4(this.f$0, (AIResult) obj);
            }
        };
        this.mCorrectObserver = observer4;
        Observer<AIResult<TextEditProcessResult>> observer5 = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mInputObserver$lambda$5(this.f$0, (AIResult) obj);
            }
        };
        this.mInputObserver = observer5;
        Observer<AIResult<TextEditProcessResult>> observer6 = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mRecommendObserver$lambda$6(this.f$0, (AIResult) obj);
            }
        };
        this.mRecommendObserver = observer6;
        Observer<AIError<ResponseErrorDetail>> observer7 = new Observer() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LocalMIAIUtils.mErrorObserver$lambda$7(this.f$0, (AIError) obj);
            }
        };
        this.mErrorObserver = observer7;
        getMLocalAINotesRequestManager().get_summaryLiveData().observeForever(observer);
        getMLocalAINotesRequestManager().get_expandTextLiveData().observeForever(observer2);
        getMLocalAINotesRequestManager().get_polishTextLiveData().observeForever(observer3);
        getMLocalAINotesRequestManager().get_correctErrorsLiveData().observeForever(observer4);
        getMLocalAINotesRequestManager().get_inputTextLiveData().observeForever(observer5);
        getMLocalAINotesRequestManager().get_recommendTextLiveData().observeForever(observer6);
        getMLocalAINotesRequestManager().get_errorLiveData().observeForever(observer7);
    }

    private final LocalAINotesRequestManager getMLocalAINotesRequestManager() {
        return (LocalAINotesRequestManager) this.mLocalAINotesRequestManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LocalAINotesRequestManager mLocalAINotesRequestManager_delegate$lambda$0(LocalMIAIUtils localMIAIUtils) {
        return new LocalAINotesRequestManager(localMIAIUtils.mContext);
    }

    public final Handler getHandler() {
        return this.handler;
    }

    public final void setHandler(Handler handler) {
        Intrinsics.checkNotNullParameter(handler, "<set-?>");
        this.handler = handler;
    }

    private final void registerDataListener(DataListener dataListener) {
        this.mDataListener = dataListener;
    }

    private final void unregisterDataListener() {
        this.mDataListener = null;
    }

    public final void setLLMSearchListener(LLMSearchListener llmSearchListener) {
        Intrinsics.checkNotNullParameter(llmSearchListener, "llmSearchListener");
        this.mLLMSearchListener = llmSearchListener;
    }

    public final void unregisterSearchListener() {
        this.mLLMSearchListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mSummaryObserver$lambda$1(LocalMIAIUtils localMIAIUtils, AIResult resultContentAIResult) {
        Intrinsics.checkNotNullParameter(resultContentAIResult, "resultContentAIResult");
        localMIAIUtils.onAIResultChanged(resultContentAIResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mExpendObserver$lambda$2(LocalMIAIUtils localMIAIUtils, AIResult resultContentAIResult) {
        Intrinsics.checkNotNullParameter(resultContentAIResult, "resultContentAIResult");
        localMIAIUtils.onAIResultChanged(resultContentAIResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mPolishObserver$lambda$3(LocalMIAIUtils localMIAIUtils, AIResult resultContentAIResult) {
        Intrinsics.checkNotNullParameter(resultContentAIResult, "resultContentAIResult");
        localMIAIUtils.onAIResultChanged(resultContentAIResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mCorrectObserver$lambda$4(LocalMIAIUtils localMIAIUtils, AIResult correctErrorTextEditProcessResult) {
        Intrinsics.checkNotNullParameter(correctErrorTextEditProcessResult, "correctErrorTextEditProcessResult");
        localMIAIUtils.onAICorrectError(correctErrorTextEditProcessResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mInputObserver$lambda$5(LocalMIAIUtils localMIAIUtils, AIResult resultContentAIResult) {
        Intrinsics.checkNotNullParameter(resultContentAIResult, "resultContentAIResult");
        localMIAIUtils.onAIResultChanged(resultContentAIResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mRecommendObserver$lambda$6(LocalMIAIUtils localMIAIUtils, AIResult resultContentAIResult) {
        Intrinsics.checkNotNullParameter(resultContentAIResult, "resultContentAIResult");
        localMIAIUtils.onAIResultChanged(resultContentAIResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mErrorObserver$lambda$7(LocalMIAIUtils localMIAIUtils, AIError responseErrorDetail) {
        Intrinsics.checkNotNullParameter(responseErrorDetail, "responseErrorDetail");
        localMIAIUtils.handleErrorResult(responseErrorDetail);
    }

    private final void onAIResultChanged(AIResult<TextEditProcessResult> resultContentAIResult) {
        if (resultContentAIResult instanceof AIResult.Complete) {
            AIResult.Complete complete = (AIResult.Complete) resultContentAIResult;
            String str = ((TextEditProcessResult) complete.getParam()).getContentResult().length() > 0 ? (String) Objects.requireNonNull(((TextEditProcessResult) complete.getParam()).getContentResult()) : "";
            String str2 = ((TextEditProcessResult) complete.getParam()).getContentStreamResult().length() > 0 ? (String) Objects.requireNonNull(((TextEditProcessResult) complete.getParam()).getContentStreamResult()) : "";
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                dataListener.listenString(new AiOutputData(str, str2, ""));
                return;
            }
            return;
        }
        handleUnCompleteResult(resultContentAIResult);
    }

    private final void onAICorrectError(AIResult<TextEditProcessResult> result) {
        if (result instanceof AIResult.Complete) {
            AIResult.Complete complete = (AIResult.Complete) result;
            String str = ((TextEditProcessResult) complete.getParam()).getContentResult().length() > 0 ? (String) Objects.requireNonNull(((TextEditProcessResult) complete.getParam()).getContentResult()) : "";
            String str2 = ((TextEditProcessResult) complete.getParam()).getContentStreamResult().length() > 0 ? (String) Objects.requireNonNull(((TextEditProcessResult) complete.getParam()).getContentStreamResult()) : "";
            int errorNum = ((TextEditProcessResult) complete.getParam()).getErrorNum();
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                Intrinsics.checkNotNull(dataListener);
                dataListener.listenString(new AiOutputData(str, str2, errorNum, ""));
                return;
            }
            return;
        }
        handleUnCompleteResult(result);
    }

    private final void handleErrorResult(AIError<?> resultContentAIResult) {
        NetworkError.Builder builder = new NetworkError.Builder();
        Intrinsics.checkNotNull(resultContentAIResult, "null cannot be cast to non-null type com.miui.notes.ai.network.request.AIError.Error<com.miui.notes.ai.network.request.ResponseErrorDetail>");
        int code = ((ResponseErrorDetail) ((AIError.Error) resultContentAIResult).getParam()).getCode();
        Logger.INSTANCE.d(TAG, "handleErrorResult code: " + code);
        int i = 5;
        if (code == 5) {
            i = 10;
        } else if (code == 6) {
            i = 11;
        } else if (code == 404) {
            i = 8;
        } else if (code == 413) {
            i = 7;
        } else if (code != 415) {
            switch (code) {
                case 11:
                    i = 12;
                    break;
                case 12:
                    i = 3;
                    break;
                case 13:
                    break;
                case 14:
                    i = 14;
                    break;
                default:
                    i = 2;
                    break;
            }
        } else {
            i = 13;
        }
        DataListener dataListener = this.mDataListener;
        Intrinsics.checkNotNull(dataListener);
        dataListener.netError(builder.errorCode(i).build());
    }

    private final void handleUnCompleteResult(AIResult<?> resultContentAIResult) {
        if (resultContentAIResult instanceof AIResult.Loading) {
            Logger.INSTANCE.i(TAG, "state:Loading");
            return;
        }
        if (resultContentAIResult instanceof AIResult.Finish) {
            Logger.INSTANCE.i(TAG, "stream finish");
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                Intrinsics.checkNotNull(dataListener);
                dataListener.finishListen();
            }
        }
    }

    private final void unregisterObserver() {
        this.handler.post(new Runnable() { // from class: com.miui.notes.ai.local.LocalMIAIUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LocalMIAIUtils.unregisterObserver$lambda$8(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unregisterObserver$lambda$8(LocalMIAIUtils localMIAIUtils) {
        localMIAIUtils.getMLocalAINotesRequestManager().get_summaryLiveData().removeObserver(localMIAIUtils.mSummaryObserver);
        localMIAIUtils.getMLocalAINotesRequestManager().get_expandTextLiveData().removeObserver(localMIAIUtils.mExpendObserver);
        localMIAIUtils.getMLocalAINotesRequestManager().get_polishTextLiveData().removeObserver(localMIAIUtils.mPolishObserver);
        localMIAIUtils.getMLocalAINotesRequestManager().get_errorLiveData().removeObserver(localMIAIUtils.mErrorObserver);
        localMIAIUtils.getMLocalAINotesRequestManager().get_correctErrorsLiveData().removeObserver(localMIAIUtils.mCorrectObserver);
        localMIAIUtils.getMLocalAINotesRequestManager().get_inputTextLiveData().removeObserver(localMIAIUtils.mInputObserver);
        localMIAIUtils.getMLocalAINotesRequestManager().get_recommendTextLiveData().removeObserver(localMIAIUtils.mRecommendObserver);
    }

    public final void initManager() {
        getMLocalAINotesRequestManager().initManager();
    }

    public final void stopRequest() {
        UserInputHolder.getInstance().setUserStatus(UserStatus.STOP);
        Logger.INSTANCE.d(TAG, "stopRequest");
        getMLocalAINotesRequestManager().stopRequest();
    }

    public final void cancelRequest() {
        UserInputHolder.getInstance().setUserStatus(UserStatus.STOP);
        Logger.INSTANCE.d(TAG, "cancelRequest");
        unregisterDataListener();
        unregisterSearchListener();
        unregisterObserver();
        getMLocalAINotesRequestManager().cancelRequest();
    }

    public final void startRequest(DataListener dataListener, int addThink) {
        Intrinsics.checkNotNullParameter(dataListener, "dataListener");
        registerDataListener(dataListener);
        UserInput userInputHolder = UserInputHolder.getInstance();
        String contentInput = userInputHolder.getContentInput();
        RequestType requestType = userInputHolder.getRequestType();
        int value = userInputHolder.getPolishStyle().getValue();
        String editInput = userInputHolder.getEditInput();
        Logger.INSTANCE.d(TAG, "startAITextEditRequest, requestType: " + requestType + ", inputSize: " + (contentInput != null ? contentInput.length() : 0) + ", polishStyle: " + value + ", addThink: " + addThink);
        switch (requestType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[requestType.ordinal()]) {
            case 1:
                LocalAINotesRequestManager mLocalAINotesRequestManager = getMLocalAINotesRequestManager();
                Intrinsics.checkNotNull(contentInput);
                mLocalAINotesRequestManager.requestSummarizeText(contentInput);
                break;
            case 2:
                LocalAINotesRequestManager mLocalAINotesRequestManager2 = getMLocalAINotesRequestManager();
                Intrinsics.checkNotNull(contentInput);
                mLocalAINotesRequestManager2.requestCorrectErrors(contentInput);
                break;
            case 3:
                LocalAINotesRequestManager mLocalAINotesRequestManager3 = getMLocalAINotesRequestManager();
                Intrinsics.checkNotNull(contentInput);
                mLocalAINotesRequestManager3.requestExpandText(contentInput);
                break;
            case 4:
                LocalAINotesRequestManager mLocalAINotesRequestManager4 = getMLocalAINotesRequestManager();
                Intrinsics.checkNotNull(contentInput);
                mLocalAINotesRequestManager4.requestPolishText(contentInput, value);
                break;
            case 5:
                LocalAINotesRequestManager mLocalAINotesRequestManager5 = getMLocalAINotesRequestManager();
                Intrinsics.checkNotNull(contentInput);
                Intrinsics.checkNotNull(editInput);
                mLocalAINotesRequestManager5.requestInputCotText(contentInput, editInput, addThink);
                break;
            case 6:
                LocalAINotesRequestManager mLocalAINotesRequestManager6 = getMLocalAINotesRequestManager();
                Intrinsics.checkNotNull(contentInput);
                Intrinsics.checkNotNull(editInput);
                mLocalAINotesRequestManager6.requestRecommendCotText(contentInput, editInput, addThink);
                break;
        }
    }

    public final void searchDevice() {
        Logger.INSTANCE.d(TAG, "searchDevice mLLMSearchListener: " + this.mLLMSearchListener);
        if (this.mLLMSearchListener != null) {
            LocalAINotesRequestManager mLocalAINotesRequestManager = getMLocalAINotesRequestManager();
            LLMSearchListener lLMSearchListener = this.mLLMSearchListener;
            Intrinsics.checkNotNull(lLMSearchListener);
            mLocalAINotesRequestManager.searchDevice(lLMSearchListener);
        }
    }

    public final void cancelSearchDevice() {
        getMLocalAINotesRequestManager().cancelSearchDeviceLLM();
        unregisterSearchListener();
    }

    public final boolean textEditCheck() {
        return getMLocalAINotesRequestManager().textEditCheck();
    }

    public final void initModel(String mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        getMLocalAINotesRequestManager().initModel(mode);
    }
}
