package com.miui.notes.ai;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import ch.qos.logback.core.pattern.parser.Parser;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.NetworkUtil;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.hybirdeditor.WebRichEditorClient;
import com.miui.hybirdeditor.jsbridge.AiEditorScriptInterface;
import com.miui.hybirdeditor.jsbridge.EditorScriptInterface;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.network.GlobalAiTextRequestManager;
import com.miui.notes.ai.request.AINotesViewModel;
import com.miui.notes.ai.request.AIResult;
import com.miui.notes.ai.request.CorrectErrorCombineData;
import com.miui.notes.ai.request.LanguageItem;
import com.miui.notes.ai.request.NoteAIRequest;
import com.miui.notes.ai.request.ResultContent;
import com.miui.notes.ai.request.TranslateData;
import com.miui.notes.ai.ui.WaterMarkView;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.theme.DefaultTheme;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.CryptoUtils;
import com.miui.richeditor.schema.NoteSchema;
import com.xiaomi.onetrack.api.ah;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.micloudview.accounts.ExtraAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class AiController implements View.OnClickListener, EditorScriptInterface {
    public static Integer MAX_WORD_COUNT = 5000;
    public static final int RESULT_ACTION_INSERT = 0;
    public static final int RESULT_ACTION_REPLACE = 1;
    public static final int STATE_FINISH = 1;
    public static final int STATE_FLOW = 7;
    public static final int STATE_FOREVER_BANNED_ERROR = 5;
    public static final int STATE_ILLEGAL_ERROR = 3;
    public static final int STATE_LOADING = 0;
    public static final int STATE_NET_ERROR = 6;
    public static final int STATE_NORMAL_ERROR = 2;
    public static final int STATE_TEMP_BANNED_ERROR = 4;
    private static final String TAG = "Notes:AiController";
    public static final int TYPE_CHECK = 2;
    public static final int TYPE_LAYOUT = 1;
    public static final int TYPE_SUMMARY = 0;
    public static final int TYPE_TRANSLATE = 3;
    private IAiActionListener mAiActionListener;
    private String mAiContent;
    private int mAiCorrectNum;
    private String mAiDoneString;
    private AiEditor mAiEditor;
    private String mAiFlowString;
    private AINotesViewModel mAiNotesViewModel;
    private AiOutputData mAiOutputData;
    private AiTranslateController mAiTranslateController;
    private View mCancelIcon;
    private Activity mContext;
    private View mCopyIcon;
    private RichEditorWebView mCore;
    private TextView mErrorButton;
    private View mErrorGroup;
    private TextView mErrorText;
    private View mFlowErrorGroup;
    private View mInsertIcon;
    private LifecycleOwner mLifecycleOwner;
    private TextView mLoadingTextView;
    private WaterMarkView mMarkView;
    private Runnable mNumCorrectRunnable;
    private OnRenderProcessGoneListener mOnRenderProcessGoneListener;
    private String mOriginalContent;
    private boolean mPrepared;
    private View mReTryIcon;
    private View mReplaceIcon;
    private View mRootView;
    private String mTitle;
    private TextView mTitleView;
    private View mTranslateRootView;
    private MainHandler mainHandler;
    private int mState = 0;
    private boolean mFirstInvoke = true;
    private String mAiFlowTotalString = "";
    private String mSaveData = "";
    private int mType = -1;
    private final Handler mHandle = new Handler(Looper.getMainLooper());
    private boolean mHasErrorStopped = false;

    public interface OnRenderProcessGoneListener {
        void onRenderProcessExit(WebView webView);
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public long getContentLength() {
        return 0L;
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public int getDeviceFlag() {
        return 0;
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void showToast(String str) {
    }

    public AiController(Activity activity, AINotesViewModel aINotesViewModel, LifecycleOwner lifecycleOwner) {
        this.mContext = activity;
        this.mAiNotesViewModel = aINotesViewModel;
        this.mLifecycleOwner = lifecycleOwner;
    }

    public void setAndInitView(View view) {
        if (this.mRootView != view) {
            this.mRootView = view;
            View viewFindViewById = view.findViewById(R.id.title_cancel_btn);
            this.mCancelIcon = viewFindViewById;
            Folme.useAt(viewFindViewById).touch().setScale(1.0f, ITouchStyle.TouchType.DOWN).setScale(1.0f, ITouchStyle.TouchType.UP).setAlpha(0.6f, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(this.mCancelIcon, new AnimConfig[0]);
            this.mTitleView = (TextView) view.findViewById(R.id.title_text_view);
            RichEditorWebView richEditorWebView = (RichEditorWebView) view.findViewById(R.id.ai_webView);
            this.mCore = richEditorWebView;
            richEditorWebView.setBackgroundColor(0);
            initAiEditor();
            this.mReTryIcon = view.findViewById(R.id.retry);
            this.mCopyIcon = view.findViewById(R.id.copy);
            this.mInsertIcon = view.findViewById(R.id.insert);
            this.mReplaceIcon = view.findViewById(R.id.replace);
            this.mLoadingTextView = (TextView) view.findViewById(R.id.loading_textview);
            this.mErrorGroup = view.findViewById(R.id.error_warn);
            View viewFindViewById2 = view.findViewById(R.id.flow_error_layout);
            this.mFlowErrorGroup = viewFindViewById2;
            viewFindViewById2.setOnClickListener(this);
            Folme.useAt(this.mFlowErrorGroup).touch().handleTouchOf(this.mFlowErrorGroup, new AnimConfig[0]);
            this.mErrorText = (TextView) view.findViewById(R.id.error_text);
            TextView textView = (TextView) view.findViewById(R.id.error_retry);
            this.mErrorButton = textView;
            textView.setOnClickListener(this);
            this.mMarkView = (WaterMarkView) view.findViewById(R.id.water_mark_view);
            this.mTranslateRootView = view.findViewById(R.id.translate_root);
        }
        initLoadingText();
        initTitleAndBottomTab();
        initTranslateView();
        setUiState();
    }

    private void initLoadingText() {
        String str = this.mContext.getString(R.string.generating) + "   ";
        SpannableString spannableString = new SpannableString(str);
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) this.mContext.getDrawable(R.drawable.three_dots);
        animatedVectorDrawable.setBounds(0, 0, animatedVectorDrawable.getIntrinsicWidth(), animatedVectorDrawable.getIntrinsicHeight());
        animatedVectorDrawable.start();
        spannableString.setSpan(new ImageSpan(animatedVectorDrawable, 2), str.length() - 1, str.length(), 17);
        this.mLoadingTextView.setText(spannableString);
    }

    public void requestAiText(int i, String str, String str2) {
        reset();
        String strConvertToWeb = NoteSchema.convertToWeb(str);
        this.mOriginalContent = strConvertToWeb;
        if (strConvertToWeb.startsWith("<new-format/>") && this.mOriginalContent.length() >= 13) {
            this.mOriginalContent = this.mOriginalContent.substring(13);
        }
        this.mAiContent = "";
        this.mType = i;
        this.mTitle = str2;
        this.mAiCorrectNum = 0;
        if (!NetworkUtil.isNetworkAvailable(NoteApp.getInstance())) {
            this.mState = 6;
            setUiState();
            return;
        }
        Logger.INSTANCE.i(TAG, "request ai type:" + this.mType);
        this.mState = 0;
        if (i == 0) {
            this.mHasErrorStopped = false;
            this.mAiNotesViewModel.requestSummarizeText(this.mOriginalContent);
            this.mAiNotesViewModel.getSummaryLiveData().observe(this.mLifecycleOwner, new Observer<AIResult<ResultContent>>() { // from class: com.miui.notes.ai.AiController.1
                @Override // androidx.lifecycle.Observer
                public void onChanged(AIResult<ResultContent> aIResult) {
                    AiController.this.onAIResultChanged(aIResult, 0);
                }
            });
            return;
        }
        if (i == 1) {
            this.mHasErrorStopped = false;
            this.mAiNotesViewModel.requestFormatText(this.mOriginalContent);
            this.mAiNotesViewModel.getFormatLiveData().observe(this.mLifecycleOwner, new Observer<AIResult<ResultContent>>() { // from class: com.miui.notes.ai.AiController.2
                @Override // androidx.lifecycle.Observer
                public void onChanged(AIResult<ResultContent> aIResult) {
                    AiController.this.onAIResultChanged(aIResult, 1);
                }
            });
        } else if (i == 2) {
            this.mHasErrorStopped = false;
            this.mAiNotesViewModel.requestCorrectErrors(this.mOriginalContent);
            this.mAiNotesViewModel.getCorrectErrorsLiveData().observe(this.mLifecycleOwner, new Observer<AIResult<CorrectErrorCombineData>>() { // from class: com.miui.notes.ai.AiController.3
                @Override // androidx.lifecycle.Observer
                public void onChanged(AIResult<CorrectErrorCombineData> aIResult) {
                    AiController.this.onErrorAIResultChanged(aIResult);
                }
            });
        } else {
            if (i != 3) {
                return;
            }
            this.mHasErrorStopped = false;
            if (this.mAiTranslateController == null) {
                this.mAiTranslateController = new AiTranslateController();
            }
            this.mAiTranslateController.reset();
            this.mAiNotesViewModel.getSupportedLanguageList();
            this.mAiNotesViewModel.getLanguageLiveData().observe(this.mLifecycleOwner, new Observer<AIResult<List<LanguageItem>>>() { // from class: com.miui.notes.ai.AiController.4
                @Override // androidx.lifecycle.Observer
                public void onChanged(AIResult<List<LanguageItem>> aIResult) {
                    Logger.INSTANCE.i(AiController.TAG, "getLanguageLiveData");
                    if (aIResult instanceof AIResult.Complete) {
                        try {
                            List<LanguageItem> list = (List) ((AIResult.Complete) aIResult).component1();
                            if (list != null) {
                                AiController.this.mAiTranslateController.setLangListData(list);
                            }
                            AiController aiController = AiController.this;
                            aiController.requestTranslateText(aiController.mOriginalContent, "", AiController.this.mAiTranslateController.getDefaultLanCode());
                            return;
                        } catch (Exception e) {
                            AiController.this.mState = 2;
                            Logger.INSTANCE.e(AiController.TAG, "getLanguage error:" + e);
                            return;
                        }
                    }
                    AiController.this.handleUnCompleteResult(aIResult);
                    AiController.this.setUiState();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestTranslateText(String str, String str2, String str3) {
        Logger.INSTANCE.i(TAG, "request translate: " + str2 + "-" + str3);
        this.mAiNotesViewModel.requestTranslateText(str, str2, str3);
        this.mAiNotesViewModel.getTranslateLiveData().observe(this.mLifecycleOwner, new Observer<AIResult<TranslateData>>() { // from class: com.miui.notes.ai.AiController.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(AIResult<TranslateData> aIResult) {
                if (aIResult instanceof AIResult.Complete) {
                    Logger.INSTANCE.d(AiController.TAG, "translate complete");
                    AiController.this.mState = 1;
                    try {
                        TranslateData translateData = (TranslateData) ((AIResult.Complete) aIResult).component1();
                        AiController.this.mAiContent = translateData.getResultContent().trim();
                        AiController.this.mAiTranslateController.setResultLangCode(translateData.getSrcLangCode(), translateData.getTargetLangCode());
                    } catch (Exception e) {
                        AiController.this.mState = 2;
                        Logger.INSTANCE.e(AiController.TAG, "translate error:" + e);
                    }
                } else {
                    AiController.this.handleUnCompleteResult(aIResult);
                }
                AiController.this.setUiState();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onErrorAIResultChanged(AIResult<CorrectErrorCombineData> aIResult) {
        if (aIResult instanceof AIResult.Complete) {
            try {
                String resultContent = ((CorrectErrorCombineData) ((AIResult.Complete) aIResult).component1()).getResultContent();
                String resultContentStream = ((CorrectErrorCombineData) ((AIResult.Complete) aIResult).component1()).getResultContentStream();
                int correctErrorsNum = ((CorrectErrorCombineData) ((AIResult.Complete) aIResult).component1()).getCorrectErrorsNum();
                if (!GlobalAiTextRequestManager.FLOW_DONE_TAG.equals(resultContentStream) && resultContentStream != null) {
                    String str = (String) Objects.requireNonNull(resultContentStream);
                    this.mAiFlowString = str;
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    int i = this.mState;
                    if (i == 7 || i == 0) {
                        this.mState = 7;
                    }
                }
                this.mAiDoneString = (String) Objects.requireNonNull(resultContent);
                this.mAiCorrectNum = correctErrorsNum;
                return;
            } catch (Exception unused) {
                this.mState = 2;
            }
        } else if (aIResult instanceof AIResult.Finish) {
            int i2 = this.mState;
            if (i2 != 7 && i2 != 0) {
                return;
            } else {
                this.mState = 1;
            }
        } else {
            handleUnCompleteResult(aIResult);
        }
        setUiState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnCompleteResult(AIResult aIResult) {
        if (aIResult instanceof AIResult.Loading) {
            Logger.INSTANCE.i(TAG, "state:Loading");
            this.mState = 0;
            return;
        }
        if (aIResult instanceof AIResult.IllegalError) {
            Logger.INSTANCE.i(TAG, "state:IllegalError");
            this.mState = 3;
            return;
        }
        if (aIResult instanceof AIResult.TempBannedError) {
            Logger.INSTANCE.i(TAG, "state:TempBannedError");
            this.mState = 4;
            return;
        }
        if (aIResult instanceof AIResult.ForeverBannedError) {
            Logger.INSTANCE.i(TAG, "state:ForeverBannedError");
            this.mState = 5;
        } else if (aIResult instanceof AIResult.AuthTokenError) {
            Logger.INSTANCE.i(TAG, "state:AuthTokenError");
            this.mState = 2;
            refreshAuthToken();
        } else {
            Logger.INSTANCE.i(TAG, "state:NormalError");
            this.mState = 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAIResultChanged(AIResult<ResultContent> aIResult, int i) {
        if (aIResult instanceof AIResult.Complete) {
            try {
                String resultContent = ((ResultContent) ((AIResult.Complete) aIResult).component1()).getResultContent();
                String resultContentStream = ((ResultContent) ((AIResult.Complete) aIResult).component1()).getResultContentStream();
                if (GlobalAiTextRequestManager.FLOW_DONE_TAG.equals(resultContentStream) || resultContentStream == null) {
                    Logger.INSTANCE.i(TAG, "onAIResultChanged: DONE");
                    this.mAiDoneString = (String) Objects.requireNonNull(resultContent);
                    return;
                }
                String str = (String) Objects.requireNonNull(resultContentStream);
                this.mAiFlowString = str;
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                Logger.INSTANCE.i(TAG, "onAIResultChanged: FLOW");
                int i2 = this.mState;
                if (i2 == 7 || i2 == 0) {
                    this.mState = 7;
                }
            } catch (Exception unused) {
                this.mState = 2;
            }
        } else if (aIResult instanceof AIResult.Finish) {
            int i3 = this.mState;
            if (i3 != 7 && i3 != 0) {
                return;
            } else {
                this.mState = 1;
            }
        } else {
            handleUnCompleteResult(aIResult);
        }
        setUiState();
    }

    private void refreshAuthToken() {
        Logger.INSTANCE.d("FloatWindowManager", "refreshAuthToken ");
        AsyncTask.execute(new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws OperationCanceledException, IOException, AuthenticatorException {
                this.f$0.lambda$refreshAuthToken$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshAuthToken$1() throws OperationCanceledException, IOException, AuthenticatorException {
        Thread.currentThread().setName("refreshAuthTokenTask");
        Logger.INSTANCE.d("FloatWindowManager", "refreshAuthToken ..AsyncTask..doInBackground");
        final Bundle authToken = NoteAIRequest.INSTANCE.getAuthToken();
        this.mHandle.post(new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$refreshAuthToken$0(authToken);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshAuthToken$0(Bundle bundle) {
        Activity activity;
        Intent intent = (Intent) bundle.getParcelable("intent");
        if (intent == null || (activity = this.mContext) == null) {
            return;
        }
        activity.startActivity(intent);
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void onCorrectNumChanged(int i) {
        this.mAiCorrectNum += i;
        if (this.mNumCorrectRunnable == null) {
            this.mNumCorrectRunnable = new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCorrectNumChanged$2();
                }
            };
        }
        this.mHandle.removeCallbacks(this.mNumCorrectRunnable);
        this.mHandle.post(this.mNumCorrectRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCorrectNumChanged$2() {
        if (1 == this.mState && this.mType == 2) {
            TextView textView = this.mTitleView;
            int i = this.mAiCorrectNum;
            if (i < 0) {
                i = 0;
            }
            textView.setText(Utils.obtainLocalInteger(i, R.plurals.corrected_title));
        }
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void onFlowDone() {
        Logger.INSTANCE.i(TAG, "onFlowDone");
        this.mFirstInvoke = false;
        if (TextUtils.isEmpty(this.mAiDoneString)) {
            return;
        }
        this.mHandle.post(new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFlowDone$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFlowDone$3() {
        this.mAiEditor.beforeRefreshNewContent();
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void notifySetNewContent() {
        this.mHandle.post(new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifySetNewContent$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySetNewContent$4() {
        Logger.INSTANCE.i(TAG, "notifySetNewContent: ");
        StringBuilder sb = new StringBuilder();
        String str = this.mAiDoneString;
        if (str == null) {
            str = "";
        }
        this.mAiEditor.setDataWithConfig(sb.append(str).append("\n<hr />\n<text indent=\"1\"><s1-size>").append(NoteApp.getInstance().getString(R.string.ai_result_warn)).append("</s1-size></text>").toString(), "");
        this.mAiEditor.refreshNewContent();
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void notifyRefreshComplete() {
        Logger.INSTANCE.d(TAG, "notifyRefreshComplete");
        this.mHandle.post(new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyRefreshComplete$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyRefreshComplete$5() {
        setBottomTabEnable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUiState() {
        if (this.mRootView == null) {
            return;
        }
        Logger.INSTANCE.i(TAG, "setUiState:" + this.mState + ", mPrepared:" + this.mPrepared);
        int i = this.mState;
        if (i == 0) {
            this.mLoadingTextView.setVisibility(0);
            this.mCore.setVisibility(8);
            this.mErrorGroup.setVisibility(8);
            this.mFlowErrorGroup.setVisibility(8);
            setBottomTabEnable(false);
            showMarkView(false);
            if (this.mType == 2) {
                this.mTitleView.setText(R.string.ai_check);
                return;
            }
            return;
        }
        if (i != 1) {
            if (i == 7) {
                IAiActionListener iAiActionListener = this.mAiActionListener;
                if (iAiActionListener != null && !iAiActionListener.isLoginState()) {
                    this.mState = 2;
                    setUiState();
                    return;
                }
                if (this.mPrepared) {
                    showMarkView(true);
                    this.mLoadingTextView.setVisibility(8);
                    this.mCore.setVisibility(0);
                    this.mErrorGroup.setVisibility(8);
                    this.mFlowErrorGroup.setVisibility(8);
                    this.mCore.setVisibility(0);
                    this.mAiEditor.addText(this.mAiFlowString, this.mFirstInvoke);
                    if (this.mFirstInvoke) {
                        this.mFirstInvoke = false;
                    }
                    this.mAiEditor.setEnableEdit(false);
                    setBottomTabEnable(false);
                    return;
                }
                this.mAiFlowTotalString += this.mAiFlowString;
                return;
            }
            handleError();
            return;
        }
        IAiActionListener iAiActionListener2 = this.mAiActionListener;
        if (iAiActionListener2 != null && !iAiActionListener2.isLoginState()) {
            this.mState = 2;
            setUiState();
            return;
        }
        if (this.mPrepared) {
            int i2 = this.mType;
            if (i2 == 3) {
                showMarkView(true);
                this.mLoadingTextView.setVisibility(8);
                this.mCore.setVisibility(0);
                this.mErrorGroup.setVisibility(8);
                this.mFlowErrorGroup.setVisibility(8);
                this.mAiTranslateController.showList();
                this.mAiEditor.setData(this.mAiContent, this.mTitle, true);
                this.mAiEditor.setEnableEdit(false);
                setBottomTabEnable(true);
                return;
            }
            if (i2 == 2) {
                this.mTitleView.setText(Utils.obtainLocalInteger(this.mAiCorrectNum, R.plurals.corrected_title));
            }
            showMarkView(true);
            this.mLoadingTextView.setVisibility(8);
            this.mCore.setVisibility(0);
            this.mErrorGroup.setVisibility(8);
            this.mAiEditor.finishFlow();
            this.mAiEditor.setEnableEdit(false);
            this.mFlowErrorGroup.setVisibility(8);
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x006d  */
    /* JADX WARN: Code duplicated, block: B:27:0x008e  */
    private void handleError() {
        Logger.INSTANCE.i(TAG, "handleError, mHasErrorStopped:" + this.mHasErrorStopped);
        if (this.mRootView == null) {
            return;
        }
        if (!this.mFirstInvoke || !TextUtils.isEmpty(this.mSaveData)) {
            this.mFlowErrorGroup.setVisibility(0);
            this.mErrorGroup.setVisibility(8);
            setBottomTabEnable(true);
            if (this.mType == 2) {
                this.mTitleView.setText(R.string.ai_check);
            }
            if (!this.mHasErrorStopped) {
                this.mAiEditor.stopInput(NoteApp.getInstance().getString(R.string.ai_result_warn));
            }
            this.mHasErrorStopped = true;
            return;
        }
        int i = this.mState;
        if (i == 2) {
            this.mLoadingTextView.setVisibility(8);
            this.mCore.setVisibility(8);
            this.mErrorText.setText(R.string.ai_failed);
            this.mErrorGroup.setVisibility(0);
            this.mErrorButton.setVisibility(this.mFlowErrorGroup.getVisibility() == 8 ? 0 : 8);
        } else if (i == 3) {
            this.mLoadingTextView.setVisibility(8);
            this.mCore.setVisibility(8);
            this.mErrorGroup.setVisibility(0);
            this.mErrorText.setText(R.string.ai_sensitive_content_warn);
            this.mErrorButton.setVisibility(this.mFlowErrorGroup.getVisibility() == 8 ? 0 : 8);
        } else if (i == 4) {
            this.mLoadingTextView.setVisibility(8);
            this.mCore.setVisibility(8);
            this.mErrorGroup.setVisibility(0);
            this.mErrorText.setText(R.string.net_error);
            this.mErrorButton.setVisibility(this.mFlowErrorGroup.getVisibility() == 8 ? 0 : 8);
        } else if (i == 5) {
            this.mLoadingTextView.setVisibility(8);
            this.mCore.setVisibility(8);
            this.mErrorGroup.setVisibility(0);
            this.mErrorText.setText(R.string.ai_feature_disabled);
            this.mErrorButton.setVisibility(this.mFlowErrorGroup.getVisibility() == 8 ? 0 : 8);
        } else if (i == 6) {
            this.mLoadingTextView.setVisibility(8);
            this.mCore.setVisibility(8);
            this.mErrorGroup.setVisibility(0);
            this.mErrorText.setText(R.string.net_error);
            this.mErrorButton.setVisibility(this.mFlowErrorGroup.getVisibility() == 8 ? 0 : 8);
        }
        this.mHasErrorStopped = true;
        setBottomTabEnable(false);
        showMarkView(false);
        if (this.mType == 2) {
            this.mTitleView.setText(R.string.ai_check);
        }
    }

    public void setAiActionListener(IAiActionListener iAiActionListener) {
        this.mAiActionListener = iAiActionListener;
    }

    private void initTitleAndBottomTab() {
        int i;
        this.mCancelIcon.setOnClickListener(this);
        this.mReTryIcon.setOnClickListener(this);
        this.mCopyIcon.setOnClickListener(this);
        this.mInsertIcon.setOnClickListener(this);
        this.mReplaceIcon.setOnClickListener(this);
        setBottomTabEnable(this.mState == 1 && this.mPrepared);
        this.mReTryIcon.setVisibility(this.mType == 0 ? 0 : 8);
        this.mCopyIcon.setVisibility(0);
        this.mInsertIcon.setVisibility(this.mType == 0 ? 0 : 8);
        this.mReplaceIcon.setVisibility(0);
        int i2 = this.mType;
        if (i2 == 1) {
            i = R.string.ai_layout;
        } else if (i2 == 2) {
            i = R.string.ai_check;
        } else {
            i = i2 == 3 ? R.string.ai_translate : R.string.ai_summary;
        }
        this.mTitleView.setText(i);
    }

    public void setOnRenderProcessGoneListener(OnRenderProcessGoneListener onRenderProcessGoneListener) {
        this.mOnRenderProcessGoneListener = onRenderProcessGoneListener;
    }

    private void initAiEditor() {
        AiEditor aiEditor = new AiEditor(this.mCore, new AiEditorScriptInterface(this), null, PreferenceUtils.getFontSize(this.mContext, 1), this.mContext);
        this.mAiEditor = aiEditor;
        aiEditor.setOnRenderProcessGoneListener(new WebRichEditorClient.onRenderProcessGoneListener() { // from class: com.miui.notes.ai.AiController.6
            @Override // com.miui.hybirdeditor.WebRichEditorClient.onRenderProcessGoneListener
            public void onRenderProcessExit(WebView webView) {
                Logger.INSTANCE.i(AiController.TAG, "onRenderProcessExit");
                AiController.this.mPrepared = false;
                if (AiController.this.mOnRenderProcessGoneListener != null) {
                    AiController.this.mOnRenderProcessGoneListener.onRenderProcessExit(webView);
                }
            }
        });
        this.mAiEditor.initWebCore(new DefaultTheme(0));
    }

    private void setBottomTabEnable(boolean z) {
        this.mReTryIcon.setEnabled(z);
        this.mCopyIcon.setEnabled(z);
        this.mInsertIcon.setEnabled(z);
        this.mReplaceIcon.setEnabled(z);
    }

    private void initTranslateView() {
        if (this.mType == 3) {
            this.mTranslateRootView.setVisibility(8);
            if (this.mAiTranslateController == null) {
                this.mAiTranslateController = new AiTranslateController();
            }
            this.mAiTranslateController.setAndInitView(this.mTranslateRootView);
            this.mAiTranslateController.setItemSelectedListener(new AiTranslateController.ItemSelectedListener() { // from class: com.miui.notes.ai.AiController.7
                @Override // com.miui.notes.ai.AiTranslateController.ItemSelectedListener
                public void onItemSelected(String str, String str2) {
                    if (str == null || str2 == null) {
                        return;
                    }
                    Logger.INSTANCE.i(AiController.TAG, "start translate: " + str + "-" + str2);
                    AiController.this.mAiNotesViewModel.cancelAll();
                    AiController.this.mState = 0;
                    AiController.this.mAiEditor.setData("", "", true);
                    AiController aiController = AiController.this;
                    aiController.requestTranslateText(aiController.mOriginalContent, str, str2);
                }
            });
            return;
        }
        this.mTranslateRootView.setVisibility(8);
        AiTranslateController aiTranslateController = this.mAiTranslateController;
        if (aiTranslateController != null) {
            aiTranslateController.dismiss();
            this.mAiTranslateController = null;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        String strTrim;
        AiTranslateController aiTranslateController;
        int i;
        IAiActionListener iAiActionListener;
        if (view.getId() == R.id.title_cancel_btn || (iAiActionListener = this.mAiActionListener) == null || iAiActionListener.isLoginState()) {
            if (this.mState != 1 || (i = this.mType) == 2) {
                String str = this.mSaveData;
                if (str == null) {
                    str = "";
                }
                strTrim = str.replace("\n<hr />\n<text indent=\"1\"><s1-size>" + NoteApp.getInstance().getString(R.string.ai_result_warn) + "</s1-size></text>", "").trim().replace("<text indent=\"1\"><s1-size>" + NoteApp.getInstance().getString(R.string.ai_result_warn) + "</s1-size></text>", "").trim();
            } else if (i == 3) {
                strTrim = this.mAiContent;
            } else {
                strTrim = this.mAiDoneString;
            }
            switch (view.getId()) {
                case R.id.copy /* 2131362246 */:
                    Logger.INSTANCE.i(TAG, "copy");
                    this.mAiEditor.replaceDoc(strTrim, new ValueCallback() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda1
                        @Override // android.webkit.ValueCallback
                        public final void onReceiveValue(Object obj) {
                            this.f$0.lambda$onClick$6(obj);
                        }
                    });
                    IAiActionListener iAiActionListener2 = this.mAiActionListener;
                    if (iAiActionListener2 != null) {
                        iAiActionListener2.onDismiss(-1);
                    }
                    break;
                case R.id.error_retry /* 2131362502 */:
                case R.id.flow_error_layout /* 2131362571 */:
                    Logger.INSTANCE.i(TAG, "error_retry");
                    if (this.mState != 4) {
                        if (this.mType == 3 && (aiTranslateController = this.mAiTranslateController) != null && aiTranslateController.getSrcCode() != null && this.mAiTranslateController.getTargetCode() != null) {
                            requestTranslateText(this.mOriginalContent, this.mAiTranslateController.getSrcCode(), this.mAiTranslateController.getTargetCode());
                        } else {
                            requestAiText(this.mType, this.mOriginalContent, this.mTitle);
                        }
                    }
                    break;
                case R.id.insert /* 2131362756 */:
                    Logger.INSTANCE.i(TAG, "insert");
                    this.mAiNotesViewModel.setAiResultActon(strTrim, 0);
                    IAiActionListener iAiActionListener3 = this.mAiActionListener;
                    if (iAiActionListener3 != null) {
                        iAiActionListener3.onDismiss(0);
                    }
                    break;
                case R.id.replace /* 2131363290 */:
                    Logger.INSTANCE.i(TAG, Parser.REPLACE_CONVERTER_WORD);
                    this.mAiNotesViewModel.setAiResultActon(strTrim, 1);
                    IAiActionListener iAiActionListener4 = this.mAiActionListener;
                    if (iAiActionListener4 != null) {
                        iAiActionListener4.onDismiss(1);
                    }
                    break;
                case R.id.retry /* 2131363296 */:
                    Logger.INSTANCE.i(TAG, ah.L);
                    requestAiText(this.mType, this.mOriginalContent, this.mTitle);
                    break;
                case R.id.title_cancel_btn /* 2131363628 */:
                    Logger.INSTANCE.i(TAG, ToDoStat.CANCEL);
                    IAiActionListener iAiActionListener5 = this.mAiActionListener;
                    if (iAiActionListener5 != null) {
                        iAiActionListener5.onDismiss(-1);
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(Object obj) {
        this.mAiEditor.doCopy(true);
        Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.copy_toast_text), 0).show();
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void onPagePrepared() {
        Logger.INSTANCE.i(TAG, "onPagePrepared: ");
        this.mHandle.post(new Runnable() { // from class: com.miui.notes.ai.AiController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onPagePrepared$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPagePrepared$7() {
        this.mPrepared = true;
        this.mAiEditor.resetScale();
        this.mAiEditor.clearDoc();
        int i = this.mState;
        if (i == 1) {
            setUiState();
        } else if (i == 7) {
            this.mAiFlowString = this.mAiFlowTotalString;
            setUiState();
        }
    }

    @Override // com.miui.hybirdeditor.jsbridge.EditorScriptInterface
    public void saveData(String str) {
        if (str == null) {
            str = "";
        }
        this.mSaveData = str;
    }

    public void reset() {
        this.mAiNotesViewModel.cancelAll();
        int i = this.mType;
        if (i == 0) {
            this.mAiNotesViewModel.getSummaryLiveData().removeObservers(this.mLifecycleOwner);
        } else if (i == 1) {
            this.mAiNotesViewModel.getFormatLiveData().removeObservers(this.mLifecycleOwner);
        } else if (i == 2) {
            this.mAiNotesViewModel.getCorrectErrorsLiveData().removeObservers(this.mLifecycleOwner);
        } else if (i == 3) {
            this.mAiNotesViewModel.getLanguageLiveData().removeObservers(this.mLifecycleOwner);
            this.mAiNotesViewModel.getLanguageLiveData().removeObservers(this.mLifecycleOwner);
        }
        this.mType = -1;
        this.mAiCorrectNum = 0;
        this.mAiDoneString = null;
        this.mAiFlowString = null;
        this.mAiFlowTotalString = "";
        this.mFirstInvoke = true;
        this.mSaveData = null;
        this.mState = 0;
        this.mHasErrorStopped = false;
        this.mAiNotesViewModel.setAiResultActon("", -1);
        AiEditor aiEditor = this.mAiEditor;
        if (aiEditor != null) {
            aiEditor.resetData();
            this.mAiEditor.setData("", "", true);
            this.mAiEditor.stopInput();
            this.mAiEditor.clearDoc();
        }
    }

    public void destroy() {
        reset();
        AiEditor aiEditor = this.mAiEditor;
        if (aiEditor != null) {
            aiEditor.destroy();
        }
        this.mHandle.removeCallbacksAndMessages(null);
    }

    private void showMarkView(boolean z) {
        String str;
        Logger.INSTANCE.i(TAG, "showMarkView:" + z);
        WaterMarkView waterMarkView = this.mMarkView;
        if (waterMarkView != null) {
            if (z) {
                Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(NoteApp.getInstance());
                if (xiaomiAccount != null) {
                    String str2 = xiaomiAccount.name == null ? "" : xiaomiAccount.name;
                    try {
                        str = CryptoUtils.encrypt(str2);
                    } catch (Exception e) {
                        Logger.INSTANCE.i(TAG, "catch exception:" + e.getMessage());
                        e.printStackTrace();
                        str = new String(Base64.encode(str2.getBytes(), 0));
                    }
                    this.mMarkView.setUserId(str);
                    this.mMarkView.setVisibility(0);
                    return;
                }
                Logger.INSTANCE.e(TAG, "showMarkView xiaomiAccount is null!!!");
                return;
            }
            waterMarkView.setVisibility(8);
        }
    }
}
