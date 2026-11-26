package com.miui.notes.ai;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.R;
import com.miui.notes.ai.data.input.RequestType;
import com.miui.notes.ai.data.input.UserInputHolder;
import com.miui.notes.ai.floatingwindow.AiFloatingWindow;
import com.miui.notes.ai.floatingwindow.MiuiFolme;
import com.miui.notes.ai.network.GlobalAiTextRequestManager;
import com.miui.notes.ai.network.NetworkError;
import com.miui.notes.ai.network.RequestState;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.ui.AiButtomItem;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.NoteSchemaUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.StringUtils;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class NoteAiFloatingWindow extends AiFloatingWindow {
    private static final int MAX_WORD_LENGTH = 20000;
    private final String TAG;
    private IAiActionListener mAiActionListener;
    private View mAiBack;
    private AiButtomItem mAiBackText;
    private View mAiCopy;
    private View mAiDone;
    private int mAiEndTextCount;
    private View mAiErrorImage;
    private View mAiInsert;
    private View mAiRefresh;
    private AiButtomItem mAiRefreshText;
    private int mAiTextUsed;
    private WebView mAiWebView;
    private Context mContext;
    private View mFloatingContentView;
    private View mGeneratingStop;
    private View mGeneratingView;
    private boolean mInTitle;
    private int mNoteWordCount;
    private INoteAiEditor mOtherEditor;
    private TextView mPanelAiTitle;
    private boolean mSaveData;
    private View mTitleLayout;

    public interface IAiActionListener {
        Rect getNoteSize();

        String getNotesContent();

        void onCancelAiAction(boolean z, boolean z2);

        void onNotifyRefreshComplete(boolean z);

        void onPanelPositionChanged(Rect rect);

        void onRefreshAiAction();

        void selectAll();

        void showSelectionEnhanced();
    }

    public NoteAiFloatingWindow(Context context, INoteAiEditor iNoteAiEditor) {
        super(context);
        this.TAG = "NoteAiFloatingWindow";
        this.mAiEndTextCount = 0;
        this.mAiTextUsed = 0;
        this.mOtherEditor = iNoteAiEditor;
        this.mContext = context;
        this.mSaveData = false;
    }

    public NoteAiFloatingWindow(Context context, INoteAiEditor iNoteAiEditor, AiTextWidgetService aiTextWidgetService) {
        super(context);
        this.TAG = "NoteAiFloatingWindow";
        this.mAiEndTextCount = 0;
        this.mAiTextUsed = 0;
        this.mOtherEditor = iNoteAiEditor;
        this.mContext = context;
        this.mSaveData = false;
        this.mAiTextWidgetService = aiTextWidgetService;
        mIsNoteAi = true;
    }

    public void setAiActionListener(IAiActionListener iAiActionListener) {
        this.mAiActionListener = iAiActionListener;
    }

    public void setInTitle(boolean z) {
        this.mInTitle = z;
    }

    public void setNoteWordCount(int i) {
        this.mNoteWordCount = i;
    }

    public boolean getInTitle() {
        return this.mInTitle;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void addToWindow() {
        super.addToWindow();
        INoteAiEditor iNoteAiEditor = this.mOtherEditor;
        if (iNoteAiEditor != null) {
            iNoteAiEditor.onFloatingWindowVisible();
        }
    }

    public void setTextCount(int i) {
        AiFloatingWindow.mAiEndTextCount = i;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void clearEditorFlowState() {
        INoteAiEditor iNoteAiEditor = this.mOtherEditor;
        if (iNoteAiEditor != null) {
            iNoteAiEditor.clearFlowStatus();
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public void showAiFloatingWindow(String str, Rect rect, int i, String str2, String str3, String str4, boolean z) {
        INoteAiEditor iNoteAiEditor;
        super.showAiFloatingWindow(str, rect, i, str2, str3, str4, z);
        if (this.mIsSupportLLMWriting && (iNoteAiEditor = this.mOtherEditor) != null) {
            iNoteAiEditor.onFloatingWindowVisible();
        }
        IAiActionListener iAiActionListener = this.mAiActionListener;
        if (iAiActionListener != null) {
            AiFloatingWindow.mStartAllCount = NoteSchemaUtil.getPlainTextCount(iAiActionListener.getNotesContent());
            AiFloatingWindow.mInTitle = this.mInTitle;
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow, android.view.View.OnClickListener
    public void onClick(View view) {
        INoteAiEditor iNoteAiEditor;
        changeInit();
        if (this.mInTitle) {
            super.onClick(view);
            return;
        }
        int id = view.getId();
        if (this.mHasStopGenerating && !TextUtils.isEmpty(this.mAiSavedData) && id == R.id.ai_refesh && (this.mRequestState == RequestState.STATE_COMPLETE || this.mRequestState == RequestState.STATE_ERROR)) {
            return;
        }
        if (this.mHasStopGenerating && id == R.id.ai_refesh && this.mFinishFlow && (this.mRequestState == RequestState.STATE_COMPLETE || this.mRequestState == RequestState.STATE_ERROR)) {
            Logger.INSTANCE.i("NoteAiFloatingWindow", "stopGenerating onclick return");
            return;
        }
        if (id == R.id.ai_insert) {
            mElementName = AITextWidgetStat.AI_INSERT;
            if (this.mNoteWordCount >= 20000) {
                showToast(StringUtils.obtainLocalInteger(20000, R.plurals.ai_cannot_add_content_toast_text, this.mContext));
                return;
            }
            if (this.mOtherEditor != null && this.mAiSavedData != null) {
                this.mOtherEditor.insertSummary((RomUtils.isInternationalBuild() ? this.mAiSavedData.replace(this.mAiEndTip, "") : this.mAiSavedData).trim());
                this.mAiTextUsed = 1;
            }
            Toast.makeText(this.mContext, R.string.ai_already_insert_toast_text, 0).show();
            trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_USED_CLOSE);
            detachFromWindow();
        } else if (id == R.id.ai_done) {
            mElementName = AITextWidgetStat.AI_DONE;
            this.mSaveData = true;
            INoteAiEditor iNoteAiEditor2 = this.mOtherEditor;
            if (iNoteAiEditor2 != null) {
                iNoteAiEditor2.onDone();
            }
            this.mAiTextUsed = 1;
            Logger.INSTANCE.i("NoteAiFloatingWindow", "mCorrectMisNum = " + mCorrectMisNum);
            if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_CHECK && mCorrectMisNum == 0) {
                this.mAiTextUsed = 0;
            }
            detachFromWindow();
        } else if (id == R.id.ai_copy) {
            Logger.INSTANCE.i("NoteAiFloatingWindow", "doCopy");
            this.mAiTextUsed = 1;
            super.onClick(view);
        } else if (id == R.id.ai_refesh) {
            INoteAiEditor iNoteAiEditor3 = this.mOtherEditor;
            if (iNoteAiEditor3 != null) {
                iNoteAiEditor3.restoreToSavedScrollPosition();
            }
            super.onClick(view);
        } else {
            if (TextUtils.isEmpty(NoteSchemaUtil.extractPlainText(UserInputHolder.getInstance().getContentInput()).trim()) && (id == R.id.ai_summary_material_effect || id == R.id.ai_check_material_effect || id == R.id.ai_extension_material_effect || id == R.id.ai_general || id == R.id.ai_professional || id == R.id.ai_easy || id == R.id.ai_friendly)) {
                showToast(this.mContext.getString(R.string.select_text_before_using));
                return;
            }
            if (this.mNoteWordCount >= 20000 && (id == R.id.ai_check_material_effect || id == R.id.ai_extension_material_effect || id == R.id.ai_general || id == R.id.ai_professional || id == R.id.ai_easy || id == R.id.ai_friendly || id == R.id.ai_recommend_send_icon)) {
                showToast(StringUtils.obtainLocalInteger(20000, R.plurals.ai_cannot_add_content_toast_text, this.mContext));
                return;
            }
            if ((id == R.id.ai_check_material_effect || id == R.id.ai_extension_material_effect || id == R.id.ai_general || id == R.id.ai_professional || id == R.id.ai_easy || id == R.id.ai_friendly || id == R.id.ai_recommend_send_icon) && (iNoteAiEditor = this.mOtherEditor) != null) {
                iNoteAiEditor.saveScrollPosition();
            }
            super.onClick(view);
        }
        if (mElementName != null) {
            super.trackReportAiUsedOrAiCanceled(UserInputHolder.getInstance().getRequestType(), mElementName, false);
            mElementName = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    /* JADX INFO: renamed from: onTouchPolish */
    public void lambda$initEditView$31(int i) {
        if (TextUtils.isEmpty(NoteSchemaUtil.extractPlainText(UserInputHolder.getInstance().getContentInput()).trim())) {
            showToast(this.mContext.getString(R.string.select_text_before_using));
            return;
        }
        if (this.mNoteWordCount >= 20000) {
            showToast(StringUtils.obtainLocalInteger(20000, R.plurals.ai_cannot_add_content_toast_text, this.mContext));
            return;
        }
        INoteAiEditor iNoteAiEditor = this.mOtherEditor;
        if (iNoteAiEditor != null) {
            iNoteAiEditor.saveScrollPosition();
        }
        clearEditorFlowState();
        super.lambda$initEditView$31(i);
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public void setUiState(RequestState requestState) {
        if (this.mAiActionListener != null && requestState == RequestState.STATE_COMPLETE) {
            AiFloatingWindow.mEndAllCount = NoteSchemaUtil.getPlainTextCount(this.mAiActionListener.getNotesContent());
        }
        super.setUiState(requestState);
        RequestType requestType = UserInputHolder.getInstance().getRequestType();
        if (requestType == RequestType.TYPE_INPUT || this.mInTitle) {
            if (requestState == RequestState.STATE_LOADING && requestType == RequestType.TYPE_INPUT) {
                resetGenerateWindowSize(true);
                return;
            }
            return;
        }
        this.mAiWebView = getAiWebView();
        this.mAiInsert = getAiInsertView();
        this.mAiCopy = getAiCopyView();
        this.mAiDone = getAiDoneView();
        this.mAiRefresh = getAiRefreshView();
        this.mAiRefreshText = getAiRefreshTextView();
        this.mAiBack = getAiBackView();
        this.mAiBackText = getAiBackTextView();
        this.mGeneratingStop = getGeneratingStopView();
        this.mPanelAiTitle = getPanelTitleView();
        int i = AnonymousClass2.$SwitchMap$com$miui$notes$ai$network$RequestState[requestState.ordinal()];
        if (i == 1) {
            View view = this.mGeneratingStop;
            if (view != null && view.getVisibility() == 8) {
                MiuiFolme.visibleView(this.mGeneratingStop);
            }
            if (requestType == RequestType.TYPE_SUMMARY) {
                WebView webView = this.mAiWebView;
                if (webView != null) {
                    webView.setVisibility(0);
                }
                resetGenerateWindowSize(true);
            } else {
                WebView webView2 = this.mAiWebView;
                if (webView2 != null) {
                    webView2.setVisibility(8);
                }
                resetGenerateWindowSize(false);
            }
            if (requestType == RequestType.TYPE_SUMMARY || requestType == RequestType.TYPE_INPUT) {
                this.mCloseButton.setVisibility(0);
                return;
            } else {
                this.mCloseButton.setVisibility(8);
                return;
            }
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            this.mAiErrorImage = getAiErrorImage();
            if (requestType != RequestType.TYPE_SUMMARY) {
                MiuiFolme.goneView(this.mAiErrorImage);
            }
            if (requestType == RequestType.TYPE_SUMMARY || requestType == RequestType.TYPE_INPUT) {
                MiuiFolme.visibleView(this.mCloseButton);
                MiuiFolme.alphaShowView(this.mCloseButton);
                return;
            } else {
                MiuiFolme.goneView(this.mCloseButton);
                return;
            }
        }
        if (requestType == RequestType.TYPE_SUMMARY) {
            WebView webView3 = this.mAiWebView;
            if (webView3 != null) {
                webView3.setVisibility(0);
            }
            View view2 = this.mAiInsert;
            if (view2 != null) {
                view2.setVisibility(0);
                if (TextUtils.isEmpty(this.mAiSavedData)) {
                    Folme.clean(this.mAiInsert);
                    this.mAiInsert.setEnabled(false);
                    this.mAiInsert.setAlpha(0.5f);
                } else {
                    Folme.useAt(this.mAiInsert).touch().handleTouchOf(this.mAiInsert, new AnimConfig[0]);
                    this.mAiInsert.setEnabled(true);
                    this.mAiInsert.setAlpha(1.0f);
                }
            }
            this.mCloseButton.setVisibility(0);
            return;
        }
        if (requestType == RequestType.TYPE_RECOMMEND) {
            Logger.INSTANCE.i("NoteAiFloatingWindow", "setUIState TYPE_RECOMMEND: " + this.mIsDeleteLabel + "  , mDeleteLabel :" + this.mDeleteLabel);
            if (this.mIsDeleteLabel) {
                this.mPanelAiTitle.setText(this.mDeleteLabel);
                this.mPanelAiTitle.setSingleLine(false);
            }
            this.mAiRefreshText.setText(this.mContext.getString(R.string.ai_text_widget_rewrite));
            MiuiFolme.goneView(this.mAiCopy, this.mAiWebView);
            MiuiFolme.visibleView(this.mAiRefresh, this.mAiDone);
            resetGenerateWindowSize(false);
            return;
        }
        if (requestType == RequestType.TYPE_INPUT) {
            this.mCloseButton.setVisibility(0);
            return;
        }
        this.mAiBackText.setImageDrawable(this.mContext.getDrawable(R.drawable.ai_cancel_icon));
        this.mAiBackText.setText(this.mContext.getString(R.string.ai_text_widget_cancel));
        MiuiFolme.goneView(this.mAiCopy, this.mAiWebView, this.mCloseButton);
        MiuiFolme.visibleView(this.mAiBack, this.mAiDone);
        resetGenerateWindowSize(false);
    }

    /* JADX INFO: renamed from: com.miui.notes.ai.NoteAiFloatingWindow$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$notes$ai$network$RequestState;

        static {
            int[] iArr = new int[RequestState.values().length];
            $SwitchMap$com$miui$notes$ai$network$RequestState = iArr;
            try {
                iArr[RequestState.STATE_LOADING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$notes$ai$network$RequestState[RequestState.STATE_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void resetGenerateWindowSize(final boolean z) {
        Logger.INSTANCE.d("NoteAiFloatingWindow", "note resetGenerateWindowSize");
        this.mGeneratingView = getGeneratingView();
        this.mTitleLayout = getTitleLayoutView();
        this.mFloatingContentView = getFloatingContentView();
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mGeneratingView.getLayoutParams();
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$resetGenerateWindowSize$0(z, layoutParams);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetGenerateWindowSize$0(boolean z, RelativeLayout.LayoutParams layoutParams) {
        if (z) {
            layoutParams.height = this.mFloatingContentView.getHeight() - this.mTitleLayout.getHeight();
        } else {
            layoutParams.height = (int) this.mContext.getResources().getDimension(R.dimen.ai_window_no_webview_height);
        }
        this.mGeneratingView.setLayoutParams(layoutParams);
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public WebView getAiWebView() {
        return super.getAiWebView();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public View getGeneratingView() {
        return super.getGeneratingView();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public View getFloatingContentView() {
        return super.getFloatingContentView();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public View getAiCopyView() {
        return super.getAiCopyView();
    }

    public String getAiTitle() {
        return this.mAiTitleData;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow, com.miui.notes.ai.data.DataListener
    public void listenString(final com.miui.notes.ai.data.output.AiOutputData aiOutputData) {
        Logger.INSTANCE.i("NoteAiFloatingWindow", "listenString, [DONE].equals(result.resultContentStream): " + GlobalAiTextRequestManager.FLOW_DONE_TAG.equals(aiOutputData.resultContentStream));
        if (this.mInTitle || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_SUMMARY || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT) {
            super.listenString(aiOutputData);
        } else {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$listenString$2(aiOutputData);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$listenString$2(com.miui.notes.ai.data.output.AiOutputData aiOutputData) {
        String str;
        INoteAiEditor iNoteAiEditor;
        if (!this.mFirst) {
            this.endRequestTime = System.currentTimeMillis();
            this.mFirst = true;
        }
        mHasFlowData = true;
        AiFloatingWindow.mAiTraceId = aiOutputData.traceId;
        if (GlobalAiTextRequestManager.FLOW_DONE_TAG.equals(aiOutputData.resultContentStream)) {
            if (this.mFirstInvokeFlow && (iNoteAiEditor = this.mOtherEditor) != null) {
                iNoteAiEditor.addText(org.apache.commons.lang3.StringUtils.SPACE, true, false);
            }
            if (this.mIsDeleteLabel) {
                Logger.INSTANCE.i("NoteAiFloatingWindow", "listenString done delete return");
                return;
            }
            Logger.INSTANCE.i("NoteAiFloatingWindow", "listenString done");
            this.mAiDoneData = aiOutputData.resultContent;
            this.mAiTitleData = aiOutputData.theme;
            if (!TextUtils.isEmpty(aiOutputData.theme) && TextUtils.isEmpty(aiOutputData.resultContent)) {
                this.mAiDoneData = aiOutputData.theme;
                this.mAiTitleData = "";
            }
            if (!TextUtils.isEmpty(NoteSchemaUtil.hasThemeLabel(aiOutputData.resultContent))) {
                this.mAiTitleData = NoteSchemaUtil.hasThemeLabel(aiOutputData.resultContent);
                this.mAiDoneData = this.mAiDoneData.replaceAll("<theme>(.*?)</theme>", "").trim();
            }
            mCorrectMisNum = aiOutputData.correctNums;
            return;
        }
        if (this.mOtherEditor != null) {
            if (aiOutputData.reasoning != null) {
                Logger.INSTANCE.i("NoteAiFloatingWindow", "listenString, reasoning length: " + aiOutputData.reasoning.length());
                str = aiOutputData.reasoning;
                this.mThinkCount += str.length();
            } else {
                str = aiOutputData.resultContentStream;
                if (this.mIsThinkContent && this.mCloudIsDevice) {
                    this.mThinkTime = System.currentTimeMillis() - this.mStartRequestTime;
                    this.mIsThinkContent = false;
                    this.mFirstInvokeFlow = true;
                }
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (!TextUtils.isEmpty(NoteSchemaUtil.hasDeleteLabel(str))) {
                this.mIsDeleteLabel = true;
                Logger.INSTANCE.i("NoteAiFloatingWindow", "listenString, hasDeleteLabel: " + NoteSchemaUtil.hasDeleteLabel(str));
                this.mDeleteLabel = NoteSchemaUtil.hasDeleteLabel(str);
                stopGenerating(this.mContext.getString(R.string.ai_content_generates_reminders));
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$listenString$1();
                    }
                }, 100L);
            } else {
                if (this.mIsThinkContent && "</think>".equals(str)) {
                    Logger.INSTANCE.i("NoteAiFloatingWindow", "listenString, is </think> : ");
                    this.mIsThinkContent = false;
                    this.mFirstInvokeFlow = true;
                }
                if (!TextUtils.isEmpty(NoteSchemaUtil.hasOneTrackLabel(str))) {
                    int[] iArrMatchOneTrackLabel = NoteSchemaUtil.matchOneTrackLabel(str);
                    this.mThinkTime = iArrMatchOneTrackLabel[0];
                    this.mThinkCount = iArrMatchOneTrackLabel[1];
                } else if (this.mFirstInvokeFlow) {
                    this.mOtherEditor.addText(str, true, this.mIsThinkContent);
                    this.mFirstInvokeFlow = false;
                } else {
                    this.mOtherEditor.addText(str, false, this.mIsThinkContent);
                }
            }
            this.mLastFlowData = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$listenString$1() {
        revertFlowData();
        setUiState(RequestState.STATE_COMPLETE);
    }

    public boolean getIsDeleteLabel() {
        return this.mIsDeleteLabel;
    }

    public String getDeleteLabel() {
        return this.mDeleteLabel;
    }

    public void flowDown() {
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$flowDown$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flowDown$4() {
        INoteAiEditor iNoteAiEditor;
        Logger.INSTANCE.i("NoteAiFloatingWindow", "note flowDown, stopGenerating: " + this.mHasStopGenerating);
        this.mOnFlowDone = true;
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$flowDown$3();
            }
        }, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
        if (!this.mHasStopGenerating && (iNoteAiEditor = this.mOtherEditor) != null) {
            iNoteAiEditor.beforeRefreshNewContent();
        }
        this.mHasStopGenerating = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flowDown$3() {
        this.mOnFlowDone = false;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow, com.miui.notes.ai.ui.FloatingDecorView.OnScreenChangeListener, com.miui.notes.ai.AiTextWidgetService.OnScreenChangeListener
    public void onConfigChange() {
        super.onConfigChange();
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow.1
            @Override // java.lang.Runnable
            public void run() {
                NoteAiFloatingWindow.this.onPanelChangeEnd();
            }
        }, 500L);
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow, com.miui.notes.ai.editor.IWebViewListener
    public void notifyRefreshComplete() {
        Logger.INSTANCE.i("NoteAiFloatingWindow", "notifyRefreshComplete");
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyRefreshComplete$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyRefreshComplete$5() {
        if (this.mRequestState == RequestState.STATE_LOADING) {
            setUiState(RequestState.STATE_COMPLETE);
        }
        boolean z = false;
        this.mOnFlowDone = false;
        IAiActionListener iAiActionListener = this.mAiActionListener;
        if (iAiActionListener != null) {
            if (this.mIsNeedMatchThinkOneTrack && this.mThinkCount != 0) {
                z = true;
            }
            iAiActionListener.onNotifyRefreshComplete(z);
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void handleErrorCodeNumber(NetworkError networkError) {
        super.handleErrorCodeNumber(networkError);
        RequestType requestType = UserInputHolder.getInstance().getRequestType();
        if (requestType == RequestType.TYPE_SUMMARY || requestType == RequestType.TYPE_INPUT) {
            return;
        }
        MiuiFolme.goneView(getAiErrorImage());
        isAiErrorImageShow = false;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow, com.miui.notes.ai.data.DataListener
    public void finishListen() {
        Logger.INSTANCE.i("NoteAiFloatingWindow", "finishListen");
        if (this.mInTitle || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_SUMMARY || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT) {
            super.finishListen();
        } else {
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$finishListen$6();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishListen$6() {
        Logger.INSTANCE.i("NoteAiFloatingWindow", "note finishListen, data empty: " + TextUtils.isEmpty(this.mAiDoneData) + ", mRequestState:" + this.mRequestState);
        if (this.mRequestState == RequestState.STATE_ERROR || this.mOtherEditor == null) {
            return;
        }
        if (this.mLastFlowData != null && !this.mLastFlowData.endsWith(org.apache.commons.lang3.StringUtils.LF)) {
            this.mOtherEditor.addText(org.apache.commons.lang3.StringUtils.LF, false, this.mIsThinkContent);
        }
        this.mOtherEditor.finishFlow(false, this.mAiDoneData + this.mAiEndTip, "");
        this.mFinishFlow = true;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void handleOutSideEvent(MotionEvent motionEvent) {
        if ((motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f && motionEvent.getRawX() == 0.0f && motionEvent.getRawY() == 0.0f) || ((motionEvent.getX() == 0.0f && motionEvent.getY() == DisplayUtil.getScreenMetrics(this.mContext).heightPixels && motionEvent.getRawX() == 0.0f && motionEvent.getRawY() == DisplayUtil.getScreenMetrics(this.mContext).heightPixels) || this.mRequestState.equals(RequestState.STATE_RECOMMEND))) {
            super.trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_BLANK_AREA_CLOSE);
            detachFromWindow();
        } else if (this.mRequestState.equals(RequestState.STATE_INPUT)) {
            Utils.hideSoftInput(this.mInput);
            setUiState(RequestState.STATE_PANEL);
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void detachFromWindow() {
        Logger.INSTANCE.i("NoteAiFloatingWindow", "detachFromWindow mOtherEditor: " + this.mOtherEditor + ", mIsNeedMatchThinkOneTrack :" + this.mIsNeedMatchThinkOneTrack);
        if (this.mOtherEditor != null) {
            if (!this.mSaveData) {
                cancelAiAction(true);
            }
            this.mOtherEditor.onFloatingWindowClose(this.mSaveData, this.mAiTextUsed);
            if (!this.mSaveData && this.mRequestState != RequestState.STATE_PANEL && this.mRequestState != RequestState.STATE_RECOMMEND && UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_SUMMARY && UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_INPUT) {
                this.mOtherEditor.revert();
            }
            if (this.mIsNeedMatchThinkOneTrack) {
                this.mOtherEditor.trimAIStatement();
            }
        }
        this.mSaveData = false;
        if (BaseHybridNoteEditFragment.mNoteAiTextWidgetService != null) {
            BaseHybridNoteEditFragment.mNoteAiTextWidgetService.stopSelf();
            BaseHybridNoteEditFragment.mNoteAiTextWidgetService = null;
        }
        super.detachFromWindow();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void detachFromWindowFromForeInfoChange() {
        Logger.INSTANCE.d("NoteAiFloatingWindow", "detachFromWindowFromForeInfoChange,mOtherEditor:" + this.mOtherEditor + ",mHandler:" + this.mHandler);
        this.mHandler.post(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$detachFromWindowFromForeInfoChange$7();
            }
        });
        if (BaseHybridNoteEditFragment.mNoteAiTextWidgetService != null) {
            BaseHybridNoteEditFragment.mNoteAiTextWidgetService.stopSelf();
            BaseHybridNoteEditFragment.mNoteAiTextWidgetService = null;
        }
        super.detachFromWindowFromForeInfoChange();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detachFromWindowFromForeInfoChange$7() {
        if (this.mOtherEditor != null) {
            if (!this.mSaveData) {
                cancelAiAction(true);
            }
            this.mOtherEditor.onFloatingWindowClose(this.mSaveData, this.mAiTextUsed);
            if (!this.mSaveData && this.mRequestState != RequestState.STATE_PANEL && this.mRequestState != RequestState.STATE_RECOMMEND && UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_SUMMARY && UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_INPUT) {
                this.mOtherEditor.revert();
            }
            if (this.mIsNeedMatchThinkOneTrack) {
                this.mOtherEditor.trimAIStatement();
            }
        }
        this.mSaveData = false;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public void dismissWindow() {
        Logger.INSTANCE.d("NoteAiFloatingWindow", "dismissWindow start, mOtherEditor: " + this.mOtherEditor);
        if (this.mOtherEditor != null) {
            if (this.mIsNeedMatchThinkOneTrack) {
                this.mOtherEditor.trimAIStatement();
            }
            this.mOtherEditor.onFloatingWindowClose(this.mSaveData, this.mAiTextUsed);
        }
        this.mOtherEditor = null;
        super.dismissWindow();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void cancelAiAction(boolean z) {
        super.cancelAiAction(z);
        IAiActionListener iAiActionListener = this.mAiActionListener;
        if (iAiActionListener != null) {
            iAiActionListener.onCancelAiAction(z, this.mIsDeleteLabel);
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void refreshAiAction() {
        IAiActionListener iAiActionListener;
        super.refreshAiAction();
        if (this.mInTitle || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_SUMMARY || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT || (iAiActionListener = this.mAiActionListener) == null) {
            return;
        }
        iAiActionListener.onRefreshAiAction();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void stopGenerating(String str) {
        if (this.mInTitle || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_SUMMARY || UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT) {
            super.stopGenerating(str);
            return;
        }
        if (this.mOtherEditor != null) {
            this.mHasStopGenerating = !TextUtils.isEmpty(str);
            if (this.mHasStopGenerating) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ai.NoteAiFloatingWindow$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$stopGenerating$8();
                    }
                }, 1500L);
            }
            this.mOtherEditor.stopInput(str);
        }
        if (this.mAINetWorkUtils != null) {
            this.mAINetWorkUtils.stopRequest();
            if (this.mCloudIsDevice) {
                this.mAINetWorkUtils = null;
                if (this.mIsThinkContent) {
                    this.mThinkTime = System.currentTimeMillis() - this.mStartRequestTime;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopGenerating$8() {
        this.mHasStopGenerating = false;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void doWebLoadingAnim() {
        RequestType requestType = UserInputHolder.getInstance().getRequestType();
        if (requestType == RequestType.TYPE_SUMMARY || requestType == RequestType.TYPE_INPUT || this.mInTitle) {
            super.doWebLoadingAnim();
        }
        INoteAiEditor iNoteAiEditor = this.mOtherEditor;
        if (iNoteAiEditor != null) {
            iNoteAiEditor.onDataLoading();
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected boolean canRecommendItemEnable() {
        if (this.mNoteWordCount < 20000) {
            return true;
        }
        showToast(StringUtils.obtainLocalInteger(20000, R.plurals.ai_cannot_add_content_toast_text, this.mContext));
        return false;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected Rect onPanelChangeEnd() {
        Rect rectOnPanelChangeEnd = super.onPanelChangeEnd();
        if (this.mAiActionListener != null && UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_SUMMARY && UserInputHolder.getInstance().getRequestType() != RequestType.TYPE_INPUT) {
            Logger.INSTANCE.d("NoteAiFloatingWindow", "onPanelChangeEnd");
            this.mAiActionListener.onPanelPositionChanged(rectOnPanelChangeEnd);
        }
        return rectOnPanelChangeEnd;
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    public void setErrorCodeHandle(String str) {
        super.setErrorCodeHandle(str);
        if (UserInputHolder.getInstance().getRequestType().equals(RequestType.TYPE_SUMMARY) || UserInputHolder.getInstance().getRequestType().equals(RequestType.TYPE_INPUT)) {
            MiuiFolme.visibleView(this.mCloseButton);
            MiuiFolme.alphaShowView(this.mCloseButton);
        } else {
            MiuiFolme.goneView(getAiErrorImage());
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void revertFlowData() {
        Logger.INSTANCE.i("NoteAiFloatingWindow", "revertFlowData: " + UserInputHolder.getInstance().getRequestType());
        if (UserInputHolder.getInstance().getRequestType() == RequestType.TYPE_INPUT) {
            super.revertFlowData();
            return;
        }
        IAiActionListener iAiActionListener = this.mAiActionListener;
        if (iAiActionListener != null) {
            iAiActionListener.onCancelAiAction(false, this.mIsDeleteLabel);
        }
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void startRequestAiData() {
        IAiActionListener iAiActionListener = this.mAiActionListener;
        if (iAiActionListener != null) {
            iAiActionListener.showSelectionEnhanced();
        }
        super.startRequestAiData();
    }

    @Override // com.miui.notes.ai.floatingwindow.AiFloatingWindow
    protected void selectAllTextIfNecessary() {
        IAiActionListener iAiActionListener = this.mAiActionListener;
        if (iAiActionListener != null) {
            iAiActionListener.selectAll();
        }
    }

    public void closeTrackReport() {
        super.trackReportAiClose(UserInputHolder.getInstance().getRequestType(), AITextWidgetStat.AI_UP_LEFT_BACK_CLOSE);
    }
}
