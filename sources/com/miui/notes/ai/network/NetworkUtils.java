package com.miui.notes.ai.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.Observer;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.data.input.UserStatus;
import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.network.fake.FakeNetworkUtils;
import com.miui.notes.ai.network.request.AIError;
import com.miui.notes.ai.network.request.AIResult;
import com.miui.notes.ai.network.request.CorrectErrorCombineData;
import com.miui.notes.ai.network.request.ResponseErrorDetail;
import com.miui.notes.ai.network.request.ResultContentCombine;
import com.miui.notes.ai.network.request.ResultGenerateCombine;
import com.miui.notes.base.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkUtils {
    public static final int STATE_COMPLETE = 1;
    public static final int STATE_FOREVER_BANNED_ERROR = 5;
    public static final int STATE_ILLEGAL_ERROR = 3;
    public static final int STATE_LANGUAGE_NOT_SUPPORT_ERROR = 15;
    public static final int STATE_LOADING = 0;
    public static final int STATE_NET_ERROR = 6;
    public static final int STATE_NORMAL_ERROR = 2;
    public static final int STATE_NOT_SUPPORT_ERROR = 8;
    public static final int STATE_SYSTEM_UPDATE_ERROR = 9;
    public static final int STATE_TEMP_BANNED_ERROR = 4;
    public static final int STATE_TEXT_LENGTH_EXCEED_LIMIT_ERROR = 7;
    private static final String TAG = "NetworkUtils";
    private AINotesRequestManager mAINotesRequestManager;
    private Context mContext;
    private DataListener mDataListener;
    Handler handler = new Handler(Looper.getMainLooper());
    private Observer<AIResult<ResultContentCombine>> mSummaryObserver = new Observer<AIResult<ResultContentCombine>>() { // from class: com.miui.notes.ai.network.NetworkUtils.1
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIResult<ResultContentCombine> aIResult) {
            NetworkUtils.this.onAIResultChanged(aIResult, RequestType.TYPE_SUMMARY);
        }
    };
    private Observer<AIResult<ResultGenerateCombine>> mGenerateObserver = new Observer<AIResult<ResultGenerateCombine>>() { // from class: com.miui.notes.ai.network.NetworkUtils.2
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIResult<ResultGenerateCombine> aIResult) {
            NetworkUtils.this.onAIGenerateResultChanged(aIResult, RequestType.TYPE_INPUT);
        }
    };
    private Observer<AIResult<ResultContentCombine>> mExpendObserver = new Observer<AIResult<ResultContentCombine>>() { // from class: com.miui.notes.ai.network.NetworkUtils.3
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIResult<ResultContentCombine> aIResult) {
            NetworkUtils.this.onAIResultChanged(aIResult, RequestType.TYPE_EXTENSION);
        }
    };
    private Observer<AIResult<ResultContentCombine>> mPolishObserver = new Observer<AIResult<ResultContentCombine>>() { // from class: com.miui.notes.ai.network.NetworkUtils.4
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIResult<ResultContentCombine> aIResult) {
            NetworkUtils.this.onAIResultChanged(aIResult, RequestType.TYPE_POLISH);
        }
    };
    private Observer<AIResult<CorrectErrorCombineData>> mCorrectObserver = new Observer<AIResult<CorrectErrorCombineData>>() { // from class: com.miui.notes.ai.network.NetworkUtils.5
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIResult<CorrectErrorCombineData> aIResult) {
            NetworkUtils.this.onAICorrectError(aIResult);
        }
    };
    private Observer<AIResult<List<String>>> mRecommendObserver = new Observer<AIResult<List<String>>>() { // from class: com.miui.notes.ai.network.NetworkUtils.6
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIResult<List<String>> aIResult) {
            NetworkUtils.this.onRecommend(aIResult);
        }
    };
    private Observer<AIError<ResponseErrorDetail>> mErrorObserver = new Observer<AIError<ResponseErrorDetail>>() { // from class: com.miui.notes.ai.network.NetworkUtils.7
        @Override // androidx.lifecycle.Observer
        public void onChanged(AIError<ResponseErrorDetail> aIError) {
            NetworkUtils.this.handleErrorResult(aIError);
        }
    };

    public void registerDataListener(DataListener dataListener) {
        this.mDataListener = dataListener;
    }

    public void unregisterDataListener() {
        this.mDataListener = null;
    }

    public NetworkUtils(Context context) {
        AINotesRequestManager aINotesRequestManager = new AINotesRequestManager();
        this.mAINotesRequestManager = aINotesRequestManager;
        this.mContext = context;
        aINotesRequestManager.get_summaryLiveData().observeForever(this.mSummaryObserver);
        this.mAINotesRequestManager.get_generateTextLiveData().observeForever(this.mGenerateObserver);
        this.mAINotesRequestManager.get_expandTextLiveData().observeForever(this.mExpendObserver);
        this.mAINotesRequestManager.get_polishTextLiveData().observeForever(this.mPolishObserver);
        this.mAINotesRequestManager.get_correctErrorsLiveData().observeForever(this.mCorrectObserver);
        this.mAINotesRequestManager.get_recommendLiveData().observeForever(this.mRecommendObserver);
        this.mAINotesRequestManager.get_errorLiveData().observeForever(this.mErrorObserver);
    }

    public void startGetRecommendData(String str, DataListener dataListener) {
        UserInputHolder.getInstance();
        registerDataListener(dataListener);
        startRequestRecommend(str);
    }

    public void startRequest(DataListener dataListener, Boolean bool) {
        registerDataListener(dataListener);
        String contentInput = UserInputHolder.getInstance().getContentInput();
        Logger.INSTANCE.d(TAG, "startRequest, requestType:" + UserInputHolder.getInstance().getRequestType());
        if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_SUMMARY) {
            startSummaryRequest(contentInput);
            return;
        }
        if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_CHECK) {
            startCorrectError(contentInput);
            return;
        }
        if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_RECOMMEND) {
            startGenerateText(contentInput, UserInputHolder.getInstance().getEditInput(), bool);
            return;
        }
        if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_POLISH) {
            startPolishText(contentInput);
        } else if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_EXTENSION) {
            startExpandText(contentInput);
        } else {
            new FakeNetworkUtils().startRequest(this.mDataListener);
        }
    }

    private void startSummaryRequest(String str) {
        this.mAINotesRequestManager.requestSummarizeText(str);
    }

    private void startCorrectError(String str) {
        this.mAINotesRequestManager.requestCorrectErrors(str);
    }

    private void startRequestRecommend(String str) {
        this.mAINotesRequestManager.requestRecommend(str);
    }

    private void startGenerateText(String str, String str2, Boolean bool) {
        this.mAINotesRequestManager.requestGenerateText(str, str2, bool.booleanValue());
    }

    private void startPolishText(String str) {
        this.mAINotesRequestManager.requestPolishText(str);
    }

    private void startExpandText(String str) {
        this.mAINotesRequestManager.requestExpandText(str);
    }

    public void stopRequest() {
        UserInputHolder.getInstance().setUserStatus(UserStatus.STOP);
        unregisterDataListener();
        unregisterObserver();
        this.mAINotesRequestManager.cancelAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAIGenerateResultChanged(AIResult<ResultGenerateCombine> aIResult, RequestType requestType) {
        if (aIResult instanceof AIResult.Complete) {
            try {
                String str = ((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getResultContent() != null ? (String) Objects.requireNonNull(((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getResultContent()) : "";
                String str2 = ((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getResultContentStream() != null ? (String) Objects.requireNonNull(((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getResultContentStream()) : "";
                String str3 = ((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getTheme() != null ? (String) Objects.requireNonNull(((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getTheme()) : "";
                String str4 = ((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getResultContentMode() != null ? (String) Objects.requireNonNull(((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getResultContentMode()) : "";
                String str5 = ((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getReasoning() != null ? (String) Objects.requireNonNull(((ResultGenerateCombine) ((AIResult.Complete) aIResult).component1()).getReasoning()) : null;
                String str6 = (String) Objects.requireNonNull(((AIResult.Complete) aIResult).getTraceId());
                DataListener dataListener = this.mDataListener;
                if (dataListener != null) {
                    dataListener.listenString(new AiOutputData(str, str2, str3, str4, str6, str5));
                    return;
                }
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "onAIResultChanged error:" + e);
                NetworkError.Builder builder = new NetworkError.Builder();
                DataListener dataListener2 = this.mDataListener;
                if (dataListener2 != null) {
                    dataListener2.netError(builder.errorCode(2).build());
                    return;
                }
                return;
            }
        }
        handleUnCompleteResult(aIResult, requestType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAIResultChanged(AIResult<ResultContentCombine> aIResult, RequestType requestType) {
        if (aIResult instanceof AIResult.Complete) {
            try {
                String str = ((ResultContentCombine) ((AIResult.Complete) aIResult).component1()).getResultContent() != null ? (String) Objects.requireNonNull(((ResultContentCombine) ((AIResult.Complete) aIResult).component1()).getResultContent()) : "";
                String str2 = ((ResultContentCombine) ((AIResult.Complete) aIResult).component1()).getResultContentStream() != null ? (String) Objects.requireNonNull(((ResultContentCombine) ((AIResult.Complete) aIResult).component1()).getResultContentStream()) : "";
                String str3 = (String) Objects.requireNonNull(((AIResult.Complete) aIResult).getTraceId());
                DataListener dataListener = this.mDataListener;
                if (dataListener != null) {
                    dataListener.listenString(new AiOutputData(str, str2, str3));
                    return;
                }
                return;
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "onAIResultChanged error:" + e);
                NetworkError.Builder builder = new NetworkError.Builder();
                DataListener dataListener2 = this.mDataListener;
                if (dataListener2 != null) {
                    dataListener2.netError(builder.errorCode(2).build());
                    return;
                }
                return;
            }
        }
        handleUnCompleteResult(aIResult, requestType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAICorrectError(AIResult<CorrectErrorCombineData> aIResult) {
        if (aIResult instanceof AIResult.Complete) {
            AIResult.Complete complete = (AIResult.Complete) aIResult;
            String str = ((CorrectErrorCombineData) complete.component1()).getResultContent() != null ? (String) Objects.requireNonNull(((CorrectErrorCombineData) complete.component1()).getResultContent()) : "";
            String str2 = ((CorrectErrorCombineData) complete.component1()).getResultContentStream() != null ? (String) Objects.requireNonNull(((CorrectErrorCombineData) complete.component1()).getResultContentStream()) : "";
            int correctErrorsNum = ((CorrectErrorCombineData) complete.component1()).getCorrectErrorsNum();
            String traceId = complete.getTraceId();
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                dataListener.listenString(new AiOutputData(str, str2, correctErrorsNum, traceId));
                return;
            }
            return;
        }
        handleUnCompleteResult(aIResult, RequestType.TYPE_CHECK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRecommend(AIResult<List<String>> aIResult) {
        if (aIResult instanceof AIResult.Complete) {
            final ArrayList arrayList = new ArrayList();
            List list = (List) ((AIResult.Complete) aIResult).component1();
            Objects.requireNonNull(arrayList);
            list.forEach(new Consumer() { // from class: com.miui.notes.ai.network.NetworkUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((String) obj);
                }
            });
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                dataListener.listenRecommendData(arrayList);
                return;
            }
            return;
        }
        handleUnCompleteResult(aIResult, RequestType.TYPE_RECOMMEND);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleErrorResult(AIError aIError) {
        NetworkError.Builder builder = new NetworkError.Builder();
        if (aIError instanceof AIError.IllegalError) {
            Logger.INSTANCE.i(TAG, "state:IllegalError");
            AIError.IllegalError illegalError = (AIError.IllegalError) aIError;
            int code = ((ResponseErrorDetail) illegalError.component1()).getCode();
            String message = ((ResponseErrorDetail) illegalError.component1()).getMessage();
            String traceId = ((ResponseErrorDetail) illegalError.component1()).getTraceId();
            DataListener dataListener = this.mDataListener;
            if (dataListener != null) {
                dataListener.netError(builder.errorCode(3).errorMsg("state:IllegalError").detailErrorCode(code).detailErrorMsg(message).traceId(traceId).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.TempBannedError) {
            Logger.INSTANCE.i(TAG, "state:TempBannedError");
            AIError.TempBannedError tempBannedError = (AIError.TempBannedError) aIError;
            int code2 = ((ResponseErrorDetail) tempBannedError.component1()).getCode();
            String message2 = ((ResponseErrorDetail) tempBannedError.component1()).getMessage();
            String traceId2 = ((ResponseErrorDetail) tempBannedError.component1()).getTraceId();
            DataListener dataListener2 = this.mDataListener;
            if (dataListener2 != null) {
                dataListener2.netError(builder.errorCode(4).errorMsg("state:TempBannedError").detailErrorCode(code2).detailErrorMsg(message2).traceId(traceId2).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.ForeverBannedError) {
            Logger.INSTANCE.i(TAG, "state:ForeverBannedError");
            AIError.ForeverBannedError foreverBannedError = (AIError.ForeverBannedError) aIError;
            int code3 = ((ResponseErrorDetail) foreverBannedError.component1()).getCode();
            String message3 = ((ResponseErrorDetail) foreverBannedError.component1()).getMessage();
            String traceId3 = ((ResponseErrorDetail) foreverBannedError.component1()).getTraceId();
            DataListener dataListener3 = this.mDataListener;
            if (dataListener3 != null) {
                dataListener3.netError(builder.errorCode(5).errorMsg("state:ForeverBannedError").detailErrorCode(code3).detailErrorMsg(message3).traceId(traceId3).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.TextLengthLimitError) {
            Logger.INSTANCE.i(TAG, "state:TextLengthLimitError");
            AIError.TextLengthLimitError textLengthLimitError = (AIError.TextLengthLimitError) aIError;
            int code4 = ((ResponseErrorDetail) textLengthLimitError.component1()).getCode();
            String message4 = ((ResponseErrorDetail) textLengthLimitError.component1()).getMessage();
            String traceId4 = ((ResponseErrorDetail) textLengthLimitError.component1()).getTraceId();
            DataListener dataListener4 = this.mDataListener;
            if (dataListener4 != null) {
                dataListener4.netError(builder.errorCode(7).errorMsg("state:TextLengthLimitError").detailErrorCode(code4).detailErrorMsg(message4).traceId(traceId4).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.AuthTokenError) {
            Logger.INSTANCE.i(TAG, "state:AuthTokenError");
            AIError.AuthTokenError authTokenError = (AIError.AuthTokenError) aIError;
            int code5 = ((ResponseErrorDetail) authTokenError.component1()).getCode();
            String message5 = ((ResponseErrorDetail) authTokenError.component1()).getMessage();
            String traceId5 = ((ResponseErrorDetail) authTokenError.component1()).getTraceId();
            DataListener dataListener5 = this.mDataListener;
            if (dataListener5 != null) {
                dataListener5.netError(builder.errorCode(2).errorMsg("state:AuthTokenError").detailErrorCode(code5).detailErrorMsg(message5).traceId(traceId5).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.NotSupportError) {
            Logger.INSTANCE.i(TAG, "state:NotSupportError");
            AIError.NotSupportError notSupportError = (AIError.NotSupportError) aIError;
            int code6 = ((ResponseErrorDetail) notSupportError.component1()).getCode();
            String message6 = ((ResponseErrorDetail) notSupportError.component1()).getMessage();
            String traceId6 = ((ResponseErrorDetail) notSupportError.component1()).getTraceId();
            DataListener dataListener6 = this.mDataListener;
            if (dataListener6 != null) {
                dataListener6.netError(builder.errorCode(8).errorMsg("state:NotSupportError").detailErrorCode(code6).detailErrorMsg(message6).traceId(traceId6).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.SystemUpdateError) {
            Logger.INSTANCE.i(TAG, "state:SystemUpdateError");
            AIError.SystemUpdateError systemUpdateError = (AIError.SystemUpdateError) aIError;
            int code7 = ((ResponseErrorDetail) systemUpdateError.component1()).getCode();
            String message7 = ((ResponseErrorDetail) systemUpdateError.component1()).getMessage();
            String traceId7 = ((ResponseErrorDetail) systemUpdateError.component1()).getTraceId();
            DataListener dataListener7 = this.mDataListener;
            if (dataListener7 != null) {
                dataListener7.netError(builder.errorCode(9).errorMsg("state:SystemUpdateError").detailErrorCode(code7).detailErrorMsg(message7).traceId(traceId7).build());
                return;
            }
            return;
        }
        if (aIError instanceof AIError.LanguageNotSupportError) {
            Logger.INSTANCE.i(TAG, "state:LanguageNotSupportError");
            AIError.LanguageNotSupportError languageNotSupportError = (AIError.LanguageNotSupportError) aIError;
            int code8 = ((ResponseErrorDetail) languageNotSupportError.component1()).getCode();
            String message8 = ((ResponseErrorDetail) languageNotSupportError.component1()).getMessage();
            String traceId8 = ((ResponseErrorDetail) languageNotSupportError.component1()).getTraceId();
            DataListener dataListener8 = this.mDataListener;
            if (dataListener8 != null) {
                dataListener8.netError(builder.errorCode(15).errorMsg("state:LanguageNotSupportError").detailErrorCode(code8).detailErrorMsg(message8).traceId(traceId8).build());
                return;
            }
            return;
        }
        Logger.INSTANCE.i(TAG, "state:NormalError");
        AIError.Error error = (AIError.Error) aIError;
        int code9 = ((ResponseErrorDetail) error.component1()).getCode();
        String message9 = ((ResponseErrorDetail) error.component1()).getMessage();
        String traceId9 = ((ResponseErrorDetail) error.component1()).getTraceId();
        DataListener dataListener9 = this.mDataListener;
        if (dataListener9 != null) {
            dataListener9.netError(builder.errorCode(2).errorMsg("state:NormalError").detailErrorCode(code9).detailErrorMsg(message9).traceId(traceId9).build());
        }
    }

    private void handleUnCompleteResult(AIResult aIResult, RequestType requestType) {
        DataListener dataListener;
        if (aIResult instanceof AIResult.Loading) {
            Logger.INSTANCE.i(TAG, "state:Loading");
            return;
        }
        if (aIResult instanceof AIResult.Finish) {
            Logger.INSTANCE.i(TAG, "stream finish");
            if (requestType == RequestType.TYPE_CHECK) {
                DataListener dataListener2 = this.mDataListener;
                if (dataListener2 != null) {
                    dataListener2.finishListen();
                    return;
                }
                return;
            }
            if (requestType == RequestType.TYPE_RECOMMEND || (dataListener = this.mDataListener) == null) {
                return;
            }
            dataListener.finishListen();
        }
    }

    public void unregisterObserver() {
        this.handler.post(new Runnable() { // from class: com.miui.notes.ai.network.NetworkUtils.8
            @Override // java.lang.Runnable
            public void run() {
                NetworkUtils.this.mAINotesRequestManager.get_summaryLiveData().removeObserver(NetworkUtils.this.mSummaryObserver);
                NetworkUtils.this.mAINotesRequestManager.get_generateTextLiveData().removeObserver(NetworkUtils.this.mGenerateObserver);
                NetworkUtils.this.mAINotesRequestManager.get_expandTextLiveData().removeObserver(NetworkUtils.this.mExpendObserver);
                NetworkUtils.this.mAINotesRequestManager.get_polishTextLiveData().removeObserver(NetworkUtils.this.mPolishObserver);
                NetworkUtils.this.mAINotesRequestManager.get_errorLiveData().removeObserver(NetworkUtils.this.mErrorObserver);
                NetworkUtils.this.mAINotesRequestManager.get_recommendLiveData().removeObserver(NetworkUtils.this.mRecommendObserver);
                NetworkUtils.this.mAINotesRequestManager.get_correctErrorsLiveData().removeObserver(NetworkUtils.this.mCorrectObserver);
            }
        });
    }
}
