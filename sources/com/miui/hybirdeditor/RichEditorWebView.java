package com.miui.hybirdeditor;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.webkit.ValueCallback;
import android.widget.ProgressBar;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NestedWebView;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.util.NoteClipManager;
import com.xiaomi.aiinput.AIInputResultListener;
import com.xiaomi.aiinput.AiTextInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import miuix.smartaction.SmartAction;
import miuix.springback.view.SpringBackLayout;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: RichEditorWebView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u009c\u00012\u00020\u00012\u00020\u0002:\u0006\u009a\u0001\u009b\u0001\u009c\u0001B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0007\u0010\bB\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0007\u0010\tJ\u000e\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0018J\b\u0010;\u001a\u00020$H\u0016J\b\u0010<\u001a\u000209H\u0016J\u0006\u0010=\u001a\u000209J\b\u0010>\u001a\u000209H\u0016J \u0010?\u001a\u0002092\u0006\u0010@\u001a\u00020\u00182\u000e\u0010A\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010BH\u0016J\b\u0010C\u001a\u000209H\u0016J\u0010\u0010D\u001a\u00020\r2\u0006\u0010E\u001a\u00020\u0018H\u0016J\b\u0010F\u001a\u00020\rH\u0016J\n\u0010G\u001a\u0004\u0018\u00010HH\u0016J\n\u0010I\u001a\u0004\u0018\u00010\u0018H\u0016J\n\u0010J\u001a\u0004\u0018\u00010\u0018H\u0016J\u0010\u0010K\u001a\u0002092\u0006\u0010L\u001a\u00020\u0018H\u0016J\b\u0010M\u001a\u00020\rH\u0016J\b\u0010N\u001a\u00020\rH\u0016J:\u0010O\u001a\u0002092\u0006\u0010P\u001a\u00020\r2\u0006\u0010Q\u001a\u00020\r2\u0006\u0010R\u001a\u00020\r2\u0006\u0010S\u001a\u00020\r2\b\u0010T\u001a\u0004\u0018\u00010\u00182\b\u0010U\u001a\u0004\u0018\u00010\u0018J\u0018\u0010V\u001a\u00020\r2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0018\u0010[\u001a\u00020\r2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0018\u0010\\\u001a\u00020\r2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0010\u0010]\u001a\u0002092\u0006\u0010^\u001a\u00020\u0018H\u0002J\u0010\u0010_\u001a\u00020\r2\u0006\u0010Y\u001a\u00020ZH\u0002J\u0012\u0010`\u001a\u0004\u0018\u00010a2\u0006\u0010b\u001a\u00020cH\u0016J\u0010\u0010d\u001a\u0002092\b\u0010e\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010f\u001a\u0002092\u0006\u0010g\u001a\u00020hH\u0014J\u0006\u0010i\u001a\u000209J\u0006\u0010j\u001a\u000209J\u000e\u0010k\u001a\u0002092\u0006\u0010l\u001a\u00020\rJ\u0010\u0010m\u001a\u00020\r2\u0006\u0010Y\u001a\u00020nH\u0016J\u000e\u0010r\u001a\u0002092\u0006\u0010s\u001a\u00020(J\u000e\u0010t\u001a\u0002092\u0006\u0010u\u001a\u00020\rJ\u0018\u0010v\u001a\u00020\u001c2\u0006\u0010w\u001a\u00020x2\u0006\u0010y\u001a\u00020XH\u0016J\u0014\u0010z\u001a\u0004\u0018\u00010\u001c2\b\u0010{\u001a\u0004\u0018\u00010\u001cH\u0002J\u0006\u0010|\u001a\u00020\rJ\u0006\u0010}\u001a\u00020\rJ\u0006\u0010~\u001a\u00020\rJ\u0006\u0010\u007f\u001a\u00020\rJ\u0007\u0010\u0080\u0001\u001a\u00020\rJ\u0007\u0010\u0081\u0001\u001a\u00020\rJ\u0012\u0010\u0082\u0001\u001a\u0002092\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010$J\u0007\u0010\u0084\u0001\u001a\u000209J\u0007\u0010\u0085\u0001\u001a\u000209J\t\u0010\u0086\u0001\u001a\u000209H\u0014J\u0010\u0010\u0087\u0001\u001a\u0002092\u0007\u0010\u0088\u0001\u001a\u00020\rJ\u0012\u0010\u0089\u0001\u001a\u0002092\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0018J\u0012\u0010\u008b\u0001\u001a\u0002092\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u0018J\u001c\u0010\u008c\u0001\u001a\u0002092\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u0018J\"\u0010\u0090\u0001\u001a\u00020\r2\t\b\u0002\u0010\u0091\u0001\u001a\u00020.2\u000e\u0010\u0092\u0001\u001a\t\u0012\u0004\u0012\u0002090\u0093\u0001J\u0010\u0010\u0094\u0001\u001a\u0002092\u0007\u0010\u0095\u0001\u001a\u00020\rJ\u000f\u0010\u0096\u0001\u001a\u0002092\u0006\u00105\u001a\u000206R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001e\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010&\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010/\u001a\u00020.8F¢\u0006\u0006\u001a\u0004\b0\u00101R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010o\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\bp\u0010qR\u0014\u0010\u0097\u0001\u001a\u00020\u00188F¢\u0006\b\u001a\u0006\b\u0098\u0001\u0010\u0099\u0001¨\u0006\u009d\u0001"}, d2 = {"Lcom/miui/hybirdeditor/RichEditorWebView;", "Lcom/miui/common/base/NestedWebView;", "Lmiuix/smartaction/SmartAction;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "mKeyShortCutListener", "Lcom/miui/richeditor/OnKeyDownAndShortCutListener;", "mCanSelect", "", "mCanQuery", "mCanPhrase", "mCanTranslate", "mPendingStartActionModeTask", "Ljava/lang/Runnable;", "getMPendingStartActionModeTask", "()Ljava/lang/Runnable;", "setMPendingStartActionModeTask", "(Ljava/lang/Runnable;)V", "mSelectData", "", "mContextData", "mHtmlData", "mActionMode", "Landroid/view/ActionMode;", "mUseNativeActionMode", "isImageClick", "mIsPcMode", "methodSetButtonState", "Ljava/lang/reflect/Method;", "methodSetActionButton", "mLoadingContainer", "Landroid/view/View;", "showLoadingViewTask", "mActivityWeakRef", "Ljava/lang/ref/WeakReference;", "Landroid/app/Activity;", "mCutOperationRunnable", "mIsCursorInTitle", "mNeedConsumeTouch", "mNeedIgnoreViewMode", "mLastUserEditTime", "", "lastUserEditTime", "getLastUserEditTime", "()J", "mJsonObject", "Lorg/json/JSONObject;", "mFeatureConfig", "onEditBarListener", "Lcom/miui/hybirdeditor/RichEditorWebView$OnEditBarListener;", "mShareRunnable", "setFeatureConfig", "", "featureConfig", "getView", "isSmartAction", "stopActionMode", "update", "evaluateJavascript", StringLookupFactory.KEY_SCRIPT, "resultCallback", "Landroid/webkit/ValueCallback;", "clearState", "isSupportFeature", "featureName", "isPasswordInputType", "getObjectForClassify", "", "getContextData", "getSelectData", "insertContentToView", "content", "selectAllText", "selectCurrentWord", "updateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "htmlData", "onKeyPreIme", "keyCode", "", "event", "Landroid/view/KeyEvent;", "onKeyShortcut", "onKeyDown", "markUserEdit", "from", "isEditingKey", "onCreateInputConnection", "Landroid/view/inputmethod/InputConnection;", "outAttrs", "Landroid/view/inputmethod/EditorInfo;", "setKeyShortCutListener", "listener", "onCreateContextMenu", "menu", "Landroid/view/ContextMenu;", "overStartActionMode", "overStopActionMode", "setIsPcMode", "isPadOrPc", "onTouchEvent", "Landroid/view/MotionEvent;", "ignoreViewMode", "getIgnoreViewMode", "()Z", "setActivity", "activity", "setUseNativeActionMode", "useNativeActionMode", "startActionMode", CallMethod.ARG_CALLBACK, "Landroid/view/ActionMode$Callback;", "type", "resolveActionMode", "actionMode", "doCopy", "doShortcutCopy", "doCut", "doShortcutCut", "doPaste", "doShare", "setLoadingContainer", "container", "showLoadingView", "hideLoadingView", "onDetachedFromWindow", "setIsInTitle", "inTitle", "replaceText", "text", "insertText", "handleGetAiTextInfo", "aiInputResultListener", "Lcom/xiaomi/aiinput/AIInputResultListener;", "contentName", "runAfterHideSoftInput", "delayMillis", "block", "Lkotlin/Function0;", "setConsumeTouch", "needConsumeTouch", "setOnEditBarListener", "selectedData", "getSelectedData", "()Ljava/lang/String;", "TextElement", "OnEditBarListener", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RichEditorWebView extends NestedWebView implements SmartAction {
    private static final String TAG = "RichEditorWebView";
    public boolean isImageClick;
    private ActionMode mActionMode;
    private WeakReference<Activity> mActivityWeakRef;
    private boolean mCanPhrase;
    private boolean mCanQuery;
    private boolean mCanSelect;
    private boolean mCanTranslate;
    private String mContextData;
    private final Runnable mCutOperationRunnable;
    private String mFeatureConfig;
    private String mHtmlData;
    private boolean mIsCursorInTitle;
    private boolean mIsPcMode;
    private JSONObject mJsonObject;
    private OnKeyDownAndShortCutListener mKeyShortCutListener;
    private volatile long mLastUserEditTime;
    private View mLoadingContainer;
    private boolean mNeedConsumeTouch;
    private boolean mNeedIgnoreViewMode;
    private Runnable mPendingStartActionModeTask;
    private String mSelectData;
    private Runnable mShareRunnable;
    private boolean mUseNativeActionMode;
    private final Method methodSetActionButton;
    private final Method methodSetButtonState;
    private OnEditBarListener onEditBarListener;
    private Runnable showLoadingViewTask;

    /* JADX INFO: compiled from: RichEditorWebView.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/hybirdeditor/RichEditorWebView$OnEditBarListener;", "", "doCut", "", "doPaste", "doShare", "doCopy", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface OnEditBarListener {
        void doCopy();

        void doCut();

        void doPaste();

        void doShare();
    }

    @Override // miuix.smartaction.SmartAction
    public Object getObjectForClassify() {
        return null;
    }

    @Override // miuix.smartaction.SmartAction
    public void insertContentToView(String content) {
        Intrinsics.checkNotNullParameter(content, "content");
    }

    @Override // miuix.smartaction.SmartAction
    public boolean isPasswordInputType() {
        return false;
    }

    @Override // miuix.smartaction.SmartAction
    public void isSmartAction() {
    }

    @Override // miuix.smartaction.SmartAction
    public boolean selectAllText() {
        return false;
    }

    @Override // miuix.smartaction.SmartAction
    public boolean selectCurrentWord() {
        return false;
    }

    protected final Runnable getMPendingStartActionModeTask() {
        return this.mPendingStartActionModeTask;
    }

    protected final void setMPendingStartActionModeTask(Runnable runnable) {
        this.mPendingStartActionModeTask = runnable;
    }

    /* JADX INFO: renamed from: getLastUserEditTime, reason: from getter */
    public final long getMLastUserEditTime() {
        return this.mLastUserEditTime;
    }

    public RichEditorWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RichEditorWebView(Context context) {
        super(context);
    }

    public final void setFeatureConfig(String featureConfig) {
        Intrinsics.checkNotNullParameter(featureConfig, "featureConfig");
        this.mFeatureConfig = featureConfig;
        try {
            this.mJsonObject = new JSONObject(this.mFeatureConfig);
        } catch (JSONException e) {
            e.printStackTrace();
            this.mJsonObject = null;
        }
    }

    @Override // miuix.smartaction.SmartAction
    public View getView() {
        return this;
    }

    public final void stopActionMode() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            Intrinsics.checkNotNull(actionMode);
            actionMode.finish();
            this.mActionMode = null;
        }
    }

    @Override // miuix.smartaction.SmartAction
    public void update() {
        evaluateJavascript("javascript:(function updateViewState() {var txt;var htmlTxt;var canSelect;var canQuery;var canPhrase;var canTranslate;var isPasswordInputType;if (window.getSelection) {var selection = window.getSelection();if (selection.rangeCount > 0) {range = selection.getRangeAt(0);var clonedSelection = range.cloneContents();var div = document.createElement('div');div.appendChild(clonedSelection);htmlTxt =  div.innerHTML;}txt = window.getSelection().toString();} else if (window.document.getSelection) {txt = window.document.getSelection().toString();} else if (window.document.selection) {txt = window.document.selection.createRange().text;}rich_editor_interface.onUpdateViewState(canSelect, canQuery, canPhrase,canTranslate, txt, htmlTxt);})()", null);
    }

    @Override // android.webkit.WebView
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        Intrinsics.checkNotNullParameter(script, "script");
        if (StringsKt.startsWith$default(script, "javascript:", false, 2, (Object) null)) {
            script = script.substring(11);
            Intrinsics.checkNotNullExpressionValue(script, "substring(...)");
        }
        super.evaluateJavascript("javascript: try {" + script + "} catch(e) { console.log('javascript error',e.message);rich_editor_interface.saveLog(e.message)}", resultCallback);
    }

    @Override // miuix.smartaction.SmartAction
    public void clearState() {
        this.mCanSelect = false;
        this.mCanQuery = false;
        this.mCanPhrase = false;
        this.mCanTranslate = false;
        this.mSelectData = null;
        this.mContextData = null;
    }

    @Override // miuix.smartaction.SmartAction
    public boolean isSupportFeature(String featureName) {
        Intrinsics.checkNotNullParameter(featureName, "featureName");
        JSONObject jSONObject = this.mJsonObject;
        if (jSONObject != null) {
            Intrinsics.checkNotNull(jSONObject);
            if (jSONObject.has(featureName)) {
                JSONObject jSONObject2 = this.mJsonObject;
                Intrinsics.checkNotNull(jSONObject2);
                if (jSONObject2.optInt(featureName) > 0) {
                    if (Intrinsics.areEqual(SmartAction.Feature.SELECT, featureName)) {
                        return this.mCanSelect;
                    }
                    if (Intrinsics.areEqual(SmartAction.Feature.QUERY, featureName)) {
                        return this.mCanQuery;
                    }
                    if (Intrinsics.areEqual(SmartAction.Feature.PHRASE, featureName)) {
                        return this.mCanPhrase;
                    }
                    if (Intrinsics.areEqual(SmartAction.Feature.TRANSLATE, featureName)) {
                        return this.mCanTranslate;
                    }
                }
            }
        }
        return false;
    }

    @Override // miuix.smartaction.SmartAction
    /* JADX INFO: renamed from: getContextData, reason: from getter */
    public String getMContextData() {
        return this.mContextData;
    }

    @Override // miuix.smartaction.SmartAction
    /* JADX INFO: renamed from: getSelectData, reason: from getter */
    public String getMSelectData() {
        return this.mSelectData;
    }

    public final void updateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String htmlData) {
        this.mCanSelect = canSelect;
        this.mCanQuery = canQuery;
        this.mCanPhrase = canPhrase;
        this.mCanTranslate = canTranslate;
        this.mSelectData = selectData;
        this.mContextData = selectData;
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        boolean zOnKeyUp;
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener;
        OnKeyDownAndShortCutListener onKeyDownAndShortCutListener2;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getAction() != 0 || (onKeyDownAndShortCutListener2 = this.mKeyShortCutListener) == null) {
            zOnKeyUp = false;
        } else {
            Intrinsics.checkNotNull(onKeyDownAndShortCutListener2);
            zOnKeyUp = onKeyDownAndShortCutListener2.onKeyDown(keyCode, event);
        }
        if (event.getAction() == 1 && (onKeyDownAndShortCutListener = this.mKeyShortCutListener) != null) {
            Intrinsics.checkNotNull(onKeyDownAndShortCutListener);
            zOnKeyUp = onKeyDownAndShortCutListener.onKeyUp(keyCode, event);
        }
        if (zOnKeyUp) {
            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return super.onKeyShortcut(keyCode, event);
    }

    @Override // android.webkit.WebView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (isEditingKey(event)) {
            markUserEdit("onKeyDown");
        }
        return super.onKeyDown(keyCode, event);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void markUserEdit(String from) {
        this.mLastUserEditTime = SystemClock.elapsedRealtime();
        Logger.INSTANCE.d(TAG, "markUserEdit from=" + from);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isEditingKey(KeyEvent event) {
        int keyCode = event.getKeyCode();
        return keyCode == 67 || keyCode == 112 || keyCode == 66 || keyCode == 160 || event.isPrintingKey();
    }

    @Override // com.miui.common.base.NestedWebView, android.webkit.WebView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Intrinsics.checkNotNullParameter(outAttrs, "outAttrs");
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(outAttrs);
        if (inputConnectionOnCreateInputConnection == null) {
            return null;
        }
        return new InputConnectionWrapper(inputConnectionOnCreateInputConnection) { // from class: com.miui.hybirdeditor.RichEditorWebView.onCreateInputConnection.1
            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                this.markUserEdit("ic.deleteSurroundingText");
                return super.deleteSurroundingText(beforeLength, afterLength);
            }

            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
                this.markUserEdit("ic.deleteSurroundingTextInCodePoints");
                return super.deleteSurroundingTextInCodePoints(beforeLength, afterLength);
            }

            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean commitText(CharSequence text, int newCursorPosition) {
                this.markUserEdit("ic.commitText");
                return super.commitText(text, newCursorPosition);
            }

            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean setComposingText(CharSequence text, int newCursorPosition) {
                this.markUserEdit("ic.setComposingText");
                return super.setComposingText(text, newCursorPosition);
            }

            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean sendKeyEvent(KeyEvent event) {
                if (event != null && event.getAction() == 0 && this.isEditingKey(event)) {
                    this.markUserEdit("ic.sendKeyEvent");
                }
                return super.sendKeyEvent(event);
            }

            @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
            public boolean performContextMenuAction(int id) {
                this.markUserEdit("ic.performContextMenuAction");
                return super.performContextMenuAction(id);
            }
        };
    }

    public final void setKeyShortCutListener(OnKeyDownAndShortCutListener listener) {
        this.mKeyShortCutListener = listener;
    }

    @Override // android.view.View
    protected void onCreateContextMenu(ContextMenu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onCreateContextMenu(menu);
    }

    public final void overStartActionMode() {
        Runnable runnable = this.mPendingStartActionModeTask;
        Intrinsics.checkNotNull(runnable);
        runnable.run();
    }

    public final void overStopActionMode() {
        stopActionMode();
    }

    public final void setIsPcMode(boolean isPadOrPc) {
        this.mIsPcMode = isPadOrPc;
    }

    @Override // com.miui.common.base.NestedWebView, android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        MotionEvent motionEventObtain;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.mIsPcMode) {
            try {
                int pointerCount = event.getPointerCount();
                MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[pointerCount];
                MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[pointerCount];
                for (int i = 0; i < pointerCount; i++) {
                    MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
                    event.getPointerProperties(i, pointerProperties);
                    pointerPropertiesArr[i] = pointerProperties;
                    MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                    event.getPointerCoords(i, pointerCoords);
                    pointerCoordsArr[i] = pointerCoords;
                }
                motionEventObtain = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), event.getPointerCount(), pointerPropertiesArr, pointerCoordsArr, event.getMetaState(), 0, event.getXPrecision(), event.getYPrecision(), event.getDeviceId(), event.getEdgeFlags(), event.getSource(), event.getFlags());
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "Right Click Exception " + e);
                motionEventObtain = null;
            }
        } else {
            motionEventObtain = null;
        }
        if (motionEventObtain == null) {
            motionEventObtain = event;
        }
        if (motionEventObtain.getToolType(0) == 3 && (getParent() instanceof SpringBackLayout) && !RomUtils.isInternationalBuild()) {
            ViewParent parent = getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type miuix.springback.view.SpringBackLayout");
            SpringBackLayout springBackLayout = (SpringBackLayout) parent;
            int action = motionEventObtain.getAction();
            if (action == 0) {
                springBackLayout.setSpringBackEnable(false);
            } else if (action == 1) {
                springBackLayout.setSpringBackEnable(true);
            }
        }
        if (RomUtils.isPadDevice() && motionEventObtain.getButtonState() == 2 && motionEventObtain.getToolType(0) == 3) {
            this.mNeedIgnoreViewMode = true;
        }
        if (motionEventObtain.getAction() == 3 && motionEventObtain.getToolType(0) != 1) {
            try {
                MotionEvent.class.getDeclaredMethod("setAction", Integer.TYPE).invoke(motionEventObtain, 1);
            } catch (Exception unused) {
            }
        }
        if (this.mNeedConsumeTouch) {
            return true;
        }
        return super.onTouchEvent(motionEventObtain);
    }

    public final boolean getIgnoreViewMode() {
        if (!this.mNeedIgnoreViewMode) {
            return false;
        }
        this.mNeedIgnoreViewMode = false;
        return true;
    }

    public final void setActivity(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.mActivityWeakRef = new WeakReference<>(activity);
    }

    public final void setUseNativeActionMode(boolean useNativeActionMode) {
        this.mUseNativeActionMode = useNativeActionMode;
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        ActionMode actionModeStartActionMode = super.startActionMode(callback, type);
        this.mActionMode = actionModeStartActionMode;
        if (this.mUseNativeActionMode) {
            Logger.INSTANCE.i(TAG, "mUseNativeActionMode");
            ActionMode actionMode = this.mActionMode;
            Intrinsics.checkNotNull(actionMode);
            return actionMode;
        }
        ActionMode actionModeResolveActionMode = resolveActionMode(actionModeStartActionMode);
        Intrinsics.checkNotNull(actionModeResolveActionMode);
        return actionModeResolveActionMode;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00ef  */
    private final ActionMode resolveActionMode(ActionMode actionMode) {
        Logger.INSTANCE.d(TAG, "resolveActionMode  " + (actionMode != null ? Integer.valueOf(actionMode.getType()) : null));
        if (actionMode != null) {
            Menu menu = actionMode.getMenu();
            if (!TextUtils.isEmpty(this.mSelectData)) {
                menu.add(0, R.id.shareText, 0, com.miui.notes.R.string.option_menu_share);
            }
            this.mActionMode = actionMode;
            evaluateJavascript("javascript:getCopiedHtml()", new ValueCallback() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda2
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    RichEditorWebView.resolveActionMode$lambda$0(this.f$0, (String) obj);
                }
            });
            Intrinsics.checkNotNull(menu);
            int size = menu.size();
            for (int i = 0; i < size; i++) {
                MenuItem item = menu.getItem(i);
                if (Intrinsics.areEqual(item.getTitle(), getContext().getString(R.string.paste))) {
                    Logger.INSTANCE.d(TAG, "active mode-paste");
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda3
                        @Override // android.view.MenuItem.OnMenuItemClickListener
                        public final boolean onMenuItemClick(MenuItem menuItem) {
                            return RichEditorWebView.resolveActionMode$lambda$1(this.f$0, menuItem);
                        }
                    });
                } else if (item.getItemId() == 16908341) {
                    Logger.INSTANCE.d(TAG, "active mode-share");
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda4
                        @Override // android.view.MenuItem.OnMenuItemClickListener
                        public final boolean onMenuItemClick(MenuItem menuItem) {
                            return RichEditorWebView.resolveActionMode$lambda$3(this.f$0, menuItem);
                        }
                    });
                } else {
                    CharSequence title = item.getTitle();
                    Intrinsics.checkNotNull(title, "null cannot be cast to non-null type kotlin.String");
                    if (Intrinsics.areEqual((String) title, getContext().getString(com.miui.notes.R.string.option_menu_share))) {
                        Logger.INSTANCE.d(TAG, "active mode-share");
                        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda4
                            @Override // android.view.MenuItem.OnMenuItemClickListener
                            public final boolean onMenuItemClick(MenuItem menuItem) {
                                return RichEditorWebView.resolveActionMode$lambda$3(this.f$0, menuItem);
                            }
                        });
                    } else {
                        CharSequence title2 = item.getTitle();
                        Intrinsics.checkNotNull(title2, "null cannot be cast to non-null type kotlin.String");
                        if (Intrinsics.areEqual((String) title2, getContext().getString(R.string.copy))) {
                            Logger.INSTANCE.d(TAG, "active mode-copy");
                            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda5
                                @Override // android.view.MenuItem.OnMenuItemClickListener
                                public final boolean onMenuItemClick(MenuItem menuItem) {
                                    return RichEditorWebView.resolveActionMode$lambda$4(this.f$0, menuItem);
                                }
                            });
                        } else {
                            CharSequence title3 = item.getTitle();
                            Intrinsics.checkNotNull(title3, "null cannot be cast to non-null type kotlin.String");
                            if (Intrinsics.areEqual((String) title3, getContext().getString(R.string.cut))) {
                                Logger.INSTANCE.d(TAG, "active mode-cut");
                                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda6
                                    @Override // android.view.MenuItem.OnMenuItemClickListener
                                    public final boolean onMenuItemClick(MenuItem menuItem) {
                                        return RichEditorWebView.resolveActionMode$lambda$5(this.f$0, menuItem);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        this.mActionMode = actionMode;
        return actionMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void resolveActionMode$lambda$0(RichEditorWebView richEditorWebView, String str) {
        richEditorWebView.mHtmlData = (String) new Gson().fromJson(str, String.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolveActionMode$lambda$1(RichEditorWebView richEditorWebView, MenuItem menuItem) {
        return richEditorWebView.doPaste();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolveActionMode$lambda$3(final RichEditorWebView richEditorWebView, MenuItem menuItem) {
        ActionMode actionMode = richEditorWebView.mActionMode;
        Intrinsics.checkNotNull(actionMode);
        actionMode.finish();
        return runAfterHideSoftInput$default(richEditorWebView, 0L, new Function0() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return RichEditorWebView.resolveActionMode$lambda$3$lambda$2(this.f$0);
            }
        }, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resolveActionMode$lambda$3$lambda$2(RichEditorWebView richEditorWebView) {
        richEditorWebView.doShare();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolveActionMode$lambda$4(RichEditorWebView richEditorWebView, MenuItem menuItem) {
        if (richEditorWebView.mIsCursorInTitle) {
            return false;
        }
        ActionMode actionMode = richEditorWebView.mActionMode;
        Intrinsics.checkNotNull(actionMode);
        actionMode.finish();
        return richEditorWebView.doCopy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolveActionMode$lambda$5(RichEditorWebView richEditorWebView, MenuItem menuItem) {
        if (richEditorWebView.mIsCursorInTitle || Intrinsics.areEqual("", richEditorWebView.mHtmlData)) {
            return false;
        }
        return richEditorWebView.doCut();
    }

    public final boolean doCopy() {
        Logger.INSTANCE.d(TAG, "doCopy");
        String str = this.mHtmlData;
        if (str == null || Intrinsics.areEqual("", str)) {
            Logger.INSTANCE.e(TAG, "doCpy failed with empty data");
            return true;
        }
        NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
        String str2 = this.mHtmlData;
        Intrinsics.checkNotNull(str2);
        noteClipManager.clipString(str2);
        NoteClipManager noteClipManager2 = NoteClipManager.INSTANCE;
        String str3 = this.mHtmlData;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        noteClipManager2.hybridCopy(str3, context);
        return true;
    }

    public final boolean doShortcutCopy() {
        Logger.INSTANCE.d(TAG, "doShortcutCopy :" + this.mIsCursorInTitle);
        if (this.mIsCursorInTitle) {
            return false;
        }
        evaluateJavascript("javascript:getCopiedHtml()", new ValueCallback() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda0
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                RichEditorWebView.doShortcutCopy$lambda$6(this.f$0, (String) obj);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doShortcutCopy$lambda$6(RichEditorWebView richEditorWebView, String str) {
        String str2 = (String) new Gson().fromJson(str, String.class);
        richEditorWebView.mHtmlData = str2;
        if (str2 == null) {
            return;
        }
        NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
        String str3 = richEditorWebView.mHtmlData;
        Intrinsics.checkNotNull(str3);
        noteClipManager.clipString(str3);
        NoteClipManager noteClipManager2 = NoteClipManager.INSTANCE;
        String str4 = richEditorWebView.mHtmlData;
        Context context = richEditorWebView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        noteClipManager2.hybridCopy(str4, context);
    }

    public final boolean doCut() {
        evaluateJavascript("javascript:pastePlainText(" + new Gson().toJson("") + ")", new ValueCallback() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda8
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                RichEditorWebView.doCut$lambda$7(this.f$0, (String) obj);
            }
        });
        return doCopy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doCut$lambda$7(RichEditorWebView richEditorWebView, String str) {
        ActionMode actionMode = richEditorWebView.mActionMode;
        if (actionMode != null) {
            Intrinsics.checkNotNull(actionMode);
            actionMode.finish();
        }
    }

    public final boolean doShortcutCut() {
        if (this.mIsCursorInTitle) {
            return false;
        }
        doShortcutCopy();
        evaluateJavascript("javascript:pastePlainText(" + new Gson().toJson("") + ")", null);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x004e  */
    /* JADX WARN: Code duplicated, block: B:12:0x005b  */
    /* JADX WARN: Code duplicated, block: B:14:0x0071  */
    /* JADX WARN: Code duplicated, block: B:20:0x008c  */
    /* JADX WARN: Code duplicated, block: B:21:0x008e  */
    /* JADX WARN: Code duplicated, block: B:24:0x009b  */
    /* JADX WARN: Code duplicated, block: B:25:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:27:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:31:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:32:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:8:0x004a  */
    public final boolean doPaste() {
        NoteClipManager noteClipManager;
        Context context;
        WeakReference<Activity> weakReference;
        Activity activity;
        if (!this.mIsCursorInTitle) {
            NoteClipManager noteClipManager2 = NoteClipManager.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (noteClipManager2.isHybridExternalPaste(context2)) {
                if (this.mIsCursorInTitle) {
                    NoteClipManager noteClipManager3 = NoteClipManager.INSTANCE;
                    Context context3 = getContext();
                    Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                    noteClipManager3.pasteToTitle(context3, this);
                    return true;
                }
                NoteClipManager noteClipManager4 = NoteClipManager.INSTANCE;
                Context context4 = getContext();
                Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
                noteClipManager4.getClipData(context4, true);
                if (!PreferenceUtils.isCTAAccepted(getContext())) {
                    noteClipManager = NoteClipManager.INSTANCE;
                    context = getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                    if (noteClipManager.isHybridNeedSync(context) && NoteClipManager.INSTANCE.checkNeedNetwork()) {
                        weakReference = this.mActivityWeakRef;
                        if (weakReference == null) {
                            activity = null;
                        } else {
                            Intrinsics.checkNotNull(weakReference);
                            activity = weakReference.get();
                        }
                        if (activity instanceof NotesListActivity) {
                            ((NotesListActivity) activity).showCtaForPaste(null);
                        } else if (activity instanceof EditActivity) {
                            ((EditActivity) activity).showCtaForPaste(null);
                        }
                        return true;
                    }
                }
                if (PreferenceUtils.isCTAAccepted(getContext())) {
                    NoteClipManager noteClipManager5 = NoteClipManager.INSTANCE;
                    Context context5 = getContext();
                    Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
                    noteClipManager5.imageDownloadAndPaste(context5, this, null, true, LiteUtils.isSuperLite());
                } else {
                    NoteClipManager noteClipManager6 = NoteClipManager.INSTANCE;
                    Context context6 = getContext();
                    Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
                    noteClipManager6.buildJsonAndPaste(context6, this);
                }
            } else {
                Logger.INSTANCE.d(TAG, "inner paste");
                evaluateJavascript("javascript:pasteHtmlText(" + new Gson().toJson(NoteClipManager.INSTANCE.getClipString()) + ")", null);
            }
        } else {
            if (this.mIsCursorInTitle) {
                NoteClipManager noteClipManager7 = NoteClipManager.INSTANCE;
                Context context7 = getContext();
                Intrinsics.checkNotNullExpressionValue(context7, "getContext(...)");
                noteClipManager7.pasteToTitle(context7, this);
                return true;
            }
            NoteClipManager noteClipManager8 = NoteClipManager.INSTANCE;
            Context context8 = getContext();
            Intrinsics.checkNotNullExpressionValue(context8, "getContext(...)");
            noteClipManager8.getClipData(context8, true);
            if (!PreferenceUtils.isCTAAccepted(getContext())) {
                noteClipManager = NoteClipManager.INSTANCE;
                context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                if (noteClipManager.isHybridNeedSync(context)) {
                    weakReference = this.mActivityWeakRef;
                    if (weakReference == null) {
                        activity = null;
                    } else {
                        Intrinsics.checkNotNull(weakReference);
                        activity = weakReference.get();
                    }
                    if (activity instanceof NotesListActivity) {
                        ((NotesListActivity) activity).showCtaForPaste(null);
                    } else if (activity instanceof EditActivity) {
                        ((EditActivity) activity).showCtaForPaste(null);
                    }
                    return true;
                }
            }
            if (PreferenceUtils.isCTAAccepted(getContext())) {
                NoteClipManager noteClipManager9 = NoteClipManager.INSTANCE;
                Context context9 = getContext();
                Intrinsics.checkNotNullExpressionValue(context9, "getContext(...)");
                noteClipManager9.imageDownloadAndPaste(context9, this, null, true, LiteUtils.isSuperLite());
            } else {
                NoteClipManager noteClipManager10 = NoteClipManager.INSTANCE;
                Context context10 = getContext();
                Intrinsics.checkNotNullExpressionValue(context10, "getContext(...)");
                noteClipManager10.buildJsonAndPaste(context10, this);
            }
        }
        return true;
    }

    public final boolean doShare() {
        String str = this.mHtmlData;
        if (str == null) {
            return false;
        }
        Intrinsics.checkNotNull(str);
        String strReplace$default = StringsKt.replace$default(Html.fromHtml(new Regex("<p class=\"\" data-indentation=\".\">.?</p>").replace(str, ""), 63).toString(), StringUtils.LF, "", false, 4, (Object) null);
        if (strReplace$default.length() != 0 && getContext() != null) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.removeExtra("android.intent.extra.TEXT");
            intent.putExtra("android.intent.extra.TEXT", strReplace$default);
            getContext().startActivity(Intent.createChooser(intent, null));
        }
        return false;
    }

    public final void setLoadingContainer(View container) {
        this.mLoadingContainer = container;
    }

    public final void showLoadingView() {
        Runnable runnable = new Runnable() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RichEditorWebView.showLoadingView$lambda$8(this.f$0);
            }
        };
        this.showLoadingViewTask = runnable;
        postDelayed(runnable, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showLoadingView$lambda$8(RichEditorWebView richEditorWebView) {
        richEditorWebView.setVisibility(4);
        richEditorWebView.requestLayout();
        View view = richEditorWebView.mLoadingContainer;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.setVisibility(0);
            View view2 = richEditorWebView.mLoadingContainer;
            Intrinsics.checkNotNull(view2);
            ProgressBar progressBar = (ProgressBar) view2.findViewById(com.miui.notes.R.id.progress_bar);
            progressBar.getIndeterminateDrawable().setColorFilter(richEditorWebView.getResources().getColor(com.miui.notes.R.color.edit_fragment_loading_color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public final void hideLoadingView() {
        Runnable runnable = this.showLoadingViewTask;
        if (runnable != null) {
            removeCallbacks(runnable);
            this.showLoadingViewTask = null;
        }
        setVisibility(0);
        View view = this.mLoadingContainer;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.setVisibility(8);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.mCutOperationRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        Runnable runnable2 = this.showLoadingViewTask;
        if (runnable2 != null) {
            removeCallbacks(runnable2);
            this.showLoadingViewTask = null;
        }
        Runnable runnable3 = this.mShareRunnable;
        if (runnable3 != null) {
            removeCallbacks(runnable3);
            this.mShareRunnable = null;
        }
    }

    public final void setIsInTitle(boolean inTitle) {
        this.mIsCursorInTitle = inTitle;
    }

    public final void replaceText(String text) {
        evaluateJavascript("javascript:insertTextForAI(" + new Gson().toJson(text) + ")", null);
    }

    public final void insertText(String text) {
        evaluateJavascript("javascript:insertTextForAI(" + new Gson().toJson(text) + ")", null);
    }

    public final void handleGetAiTextInfo(final AIInputResultListener aiInputResultListener, final String contentName) throws RemoteException {
        Intrinsics.checkNotNullParameter(aiInputResultListener, "aiInputResultListener");
        evaluateJavascript("javascript:getTextForAI()", new ValueCallback() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda7
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                RichEditorWebView.handleGetAiTextInfo$lambda$10(contentName, aiInputResultListener, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleGetAiTextInfo$lambda$10(String str, AIInputResultListener aIInputResultListener, String str2) {
        try {
            TextElement textElement = (TextElement) new Gson().fromJson((String) new Gson().fromJson(str2, String.class), TextElement.class);
            aIInputResultListener.onGetFocusedAITextInfo(new AiTextInfo(textElement.getText(), !textElement.getIsCollapsed(), 0, BaseNoteApp.INSTANCE.getInstance().getPackageName(), str));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "handleGetAiTextInfo error: " + e.getMessage());
        }
    }

    public static /* synthetic */ boolean runAfterHideSoftInput$default(RichEditorWebView richEditorWebView, long j, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 200;
        }
        return richEditorWebView.runAfterHideSoftInput(j, function0);
    }

    public final boolean runAfterHideSoftInput(long delayMillis, final Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Utils.hideSoftInput(this);
        Runnable runnable = this.mShareRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.miui.hybirdeditor.RichEditorWebView$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                block.invoke();
            }
        };
        this.mShareRunnable = runnable2;
        postDelayed(runnable2, delayMillis);
        return false;
    }

    /* JADX INFO: compiled from: RichEditorWebView.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lcom/miui/hybirdeditor/RichEditorWebView$TextElement;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "text", "", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "isCollapsed", "", "()Z", "setCollapsed", "(Z)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class TextElement {
        private boolean isCollapsed;
        private String text;

        public final String getText() {
            return this.text;
        }

        public final void setText(String str) {
            this.text = str;
        }

        /* JADX INFO: renamed from: isCollapsed, reason: from getter */
        public final boolean getIsCollapsed() {
            return this.isCollapsed;
        }

        public final void setCollapsed(boolean z) {
            this.isCollapsed = z;
        }
    }

    public final void setConsumeTouch(boolean needConsumeTouch) {
        this.mNeedConsumeTouch = needConsumeTouch;
    }

    public final void setOnEditBarListener(OnEditBarListener onEditBarListener) {
        Intrinsics.checkNotNullParameter(onEditBarListener, "onEditBarListener");
        this.onEditBarListener = onEditBarListener;
    }

    public final String getSelectedData() {
        return "测试";
    }
}
