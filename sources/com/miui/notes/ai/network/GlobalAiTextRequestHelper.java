package com.miui.notes.ai.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.lifecycle.Observer;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.GlobalAiTextWidgetServiceHelper;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.network.request.AIError;
import com.miui.notes.ai.network.request.AIResult;
import com.miui.notes.ai.network.request.ResponseErrorDetail;
import com.xiaomi.aicr.llm.AICreationResult;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GlobalAiTextRequestHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\u0018\u0000 +2\u00020\u0001:\u0001+B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0013\u001a\u00020\u0014JF\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0012J>\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0012J\u0016\u0010!\u001a\u00020\u00142\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0014\u0010#\u001a\u00020\u00142\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030\fH\u0002J\u0016\u0010$\u001a\u00020\u00142\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002J\u000e\u0010&\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0012J\u0006\u0010'\u001a\u00020\u0014J\b\u0010(\u001a\u00020\u0014H\u0002J\u0006\u0010)\u001a\u00020\u0014J\u0006\u0010*\u001a\u00020\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/miui/notes/ai/network/GlobalAiTextRequestHelper;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mHandler", "Landroid/os/Handler;", "mGlobalAiTextRequestManager", "Lcom/miui/notes/ai/network/GlobalAiTextRequestManager;", "mAiGenerateTextObserver", "Landroidx/lifecycle/Observer;", "Lcom/miui/notes/ai/network/request/AIResult;", "Lcom/xiaomi/aicr/llm/AICreationResult;", "mErrorObserver", "Lcom/miui/notes/ai/network/request/AIError;", "Lcom/miui/notes/ai/network/request/ResponseErrorDetail;", "mDataListener", "Lcom/miui/notes/ai/data/DataListener;", "initManger", "", "startRequest", GlobalAiTextWidgetServiceHelper.KEY_QUERY_ID, "", "packageName", "pageType", "style", "", "emoticonsNum", "wordNum", "userPrompt", "dataListener", "comment", "onAIResultChanged", "resultContentAIResult", "handleUnCompleteResult", "handleErrorResult", "responseErrorDetail", "registerDataListener", "unregisterDataListener", "unregisterObserver", "stopRequest", "cancelRequest", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class GlobalAiTextRequestHelper {
    public static final String TAG = "GlobalAiNetworkHelper";
    private final Observer<AIResult<AICreationResult>> mAiGenerateTextObserver;
    private DataListener mDataListener;
    private final Observer<AIError<ResponseErrorDetail>> mErrorObserver;
    private final GlobalAiTextRequestManager mGlobalAiTextRequestManager;
    private final Handler mHandler;

    public GlobalAiTextRequestHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mHandler = new Handler(Looper.getMainLooper());
        GlobalAiTextRequestManager globalAiTextRequestManager = new GlobalAiTextRequestManager(context);
        this.mGlobalAiTextRequestManager = globalAiTextRequestManager;
        Observer<AIResult<AICreationResult>> observer = new Observer() { // from class: com.miui.notes.ai.network.GlobalAiTextRequestHelper$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                GlobalAiTextRequestHelper.mAiGenerateTextObserver$lambda$0(this.f$0, (AIResult) obj);
            }
        };
        this.mAiGenerateTextObserver = observer;
        Observer<AIError<ResponseErrorDetail>> observer2 = new Observer() { // from class: com.miui.notes.ai.network.GlobalAiTextRequestHelper$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                GlobalAiTextRequestHelper.mErrorObserver$lambda$1(this.f$0, (AIError) obj);
            }
        };
        this.mErrorObserver = observer2;
        globalAiTextRequestManager.getGlobalAiGenerateTextLiveData().observeForever(observer);
        globalAiTextRequestManager.getErrorLiveData().observeForever(observer2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mAiGenerateTextObserver$lambda$0(GlobalAiTextRequestHelper globalAiTextRequestHelper, AIResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        globalAiTextRequestHelper.onAIResultChanged(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mErrorObserver$lambda$1(GlobalAiTextRequestHelper globalAiTextRequestHelper, AIError it) {
        Intrinsics.checkNotNullParameter(it, "it");
        globalAiTextRequestHelper.handleErrorResult(it);
    }

    public final void initManger() {
        this.mGlobalAiTextRequestManager.initManager();
    }

    public final void startRequest(String queryID, String packageName, String pageType, int style, int emoticonsNum, int wordNum, String userPrompt, DataListener dataListener) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(pageType, "pageType");
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        Intrinsics.checkNotNullParameter(dataListener, "dataListener");
        registerDataListener(dataListener);
        this.mGlobalAiTextRequestManager.requestGenerateText(queryID, packageName, pageType, style, emoticonsNum, wordNum, userPrompt);
    }

    public final void startRequest(String queryID, String packageName, String pageType, int comment, int wordNum, String userPrompt, DataListener dataListener) {
        Intrinsics.checkNotNullParameter(queryID, "queryID");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(pageType, "pageType");
        Intrinsics.checkNotNullParameter(userPrompt, "userPrompt");
        Intrinsics.checkNotNullParameter(dataListener, "dataListener");
        registerDataListener(dataListener);
        this.mGlobalAiTextRequestManager.requestGenerateText(queryID, packageName, pageType, comment, wordNum, userPrompt);
    }

    private final void onAIResultChanged(AIResult<AICreationResult> resultContentAIResult) {
        if (resultContentAIResult instanceof AIResult.Complete) {
            AIResult.Complete complete = (AIResult.Complete) resultContentAIResult;
            String str = ((AICreationResult) complete.getParam()).getContentResult().length() > 0 ? (String) Objects.requireNonNull(((AICreationResult) complete.getParam()).getContentResult()) : "";
            String str2 = ((AICreationResult) complete.getParam()).getContentStreamResult().length() > 0 ? (String) Objects.requireNonNull(((AICreationResult) complete.getParam()).getContentStreamResult()) : "";
            String str3 = ((AICreationResult) complete.getParam()).getTheme().length() > 0 ? (String) Objects.requireNonNull(((AICreationResult) complete.getParam()).getTheme()) : "";
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                dataListener.listenString(new AiOutputData(str, str2, str3, ""));
                return;
            }
            return;
        }
        handleUnCompleteResult(resultContentAIResult);
    }

    private final void handleUnCompleteResult(AIResult<?> resultContentAIResult) {
        if (resultContentAIResult instanceof AIResult.Loading) {
            Log.i(TAG, "handleUnCompleteResult: loading");
            return;
        }
        if (resultContentAIResult instanceof AIResult.Finish) {
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                dataListener.finishListen();
                return;
            }
            return;
        }
        if (!(resultContentAIResult instanceof AIResult.Complete)) {
            throw new NoWhenBranchMatchedException();
        }
    }

    private final void handleErrorResult(AIError<ResponseErrorDetail> responseErrorDetail) {
        Intrinsics.checkNotNull(responseErrorDetail, "null cannot be cast to non-null type com.miui.notes.ai.network.request.AIError.Error<com.miui.notes.ai.network.request.ResponseErrorDetail>");
        int code = ((ResponseErrorDetail) ((AIError.Error) responseErrorDetail).getParam()).getCode();
        DataListener dataListener = this.mDataListener;
        if (dataListener != null) {
            dataListener.netError(new NetworkError.Builder().errorCode(code).build());
        }
    }

    public final void registerDataListener(DataListener dataListener) {
        Intrinsics.checkNotNullParameter(dataListener, "dataListener");
        this.mDataListener = dataListener;
    }

    public final void unregisterDataListener() {
        this.mDataListener = null;
    }

    private final void unregisterObserver() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.network.GlobalAiTextRequestHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GlobalAiTextRequestHelper.unregisterObserver$lambda$2(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unregisterObserver$lambda$2(GlobalAiTextRequestHelper globalAiTextRequestHelper) {
        globalAiTextRequestHelper.mGlobalAiTextRequestManager.getGlobalAiGenerateTextLiveData().removeObserver(globalAiTextRequestHelper.mAiGenerateTextObserver);
        globalAiTextRequestHelper.mGlobalAiTextRequestManager.getErrorLiveData().removeObserver(globalAiTextRequestHelper.mErrorObserver);
    }

    public final void stopRequest() {
        this.mGlobalAiTextRequestManager.stopRequest();
    }

    public final void cancelRequest() {
        unregisterDataListener();
        unregisterObserver();
        this.mGlobalAiTextRequestManager.cancelRequest();
    }
}
