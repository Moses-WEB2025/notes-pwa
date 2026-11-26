package com.miui.floatwindow.feature.root;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import ch.qos.logback.core.CoreConstants;
import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ExcerptStat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.floatwindow.feature.note.FwNoteListAdapter;
import com.miui.floatwindow.feature.note.FwNoteListPresenter;
import com.miui.floatwindow.feature.note.FwNoteListView;
import com.miui.floatwindow.feature.note.NoteOperationHelper;
import com.miui.floatwindow.feature.note.NoteSchedulerProvider;
import com.miui.floatwindow.feature.todo.FwTodoListPresenter;
import com.miui.floatwindow.feature.todo.FwTodoListView;
import com.miui.floatwindow.frame.IFloatWindowView;
import com.miui.floatwindow.view.FwFullScrollView;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.R;
import com.miui.notes.excerpt.ExcerptDataUtils;
import com.miui.notes.excerpt.ExcerptInfo;
import com.miui.notes.excerpt.ExcerptTitleInfo;
import com.miui.notes.excerpt.HtmlResult;
import com.miui.notes.excerpt.JsUtils;
import com.miui.notes.excerpt.LinkUtils;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.excerpt.ParseTitleInfo;
import com.miui.notes.excerpt.ParseUrlInfo;
import com.miui.notes.excerpt.UrlInfo;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.utils.SchedulerProvider;
import com.miui.todo.utils.TodoUtils;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import defpackage.R2;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: FloatFullWindow.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u008d\u00012\u00020\u0001:\u0006\u008b\u0001\u008c\u0001\u008d\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u00108\u001a\u000209J\b\u0010=\u001a\u000209H\u0014J\b\u0010>\u001a\u000209H\u0002J\u0018\u0010?\u001a\u0002092\b\u0010@\u001a\u0004\u0018\u00010&2\u0006\u0010A\u001a\u00020 J\u0010\u0010B\u001a\u0002092\u0006\u0010C\u001a\u00020\rH\u0016J\b\u0010D\u001a\u000209H\u0016J\b\u0010E\u001a\u000209H\u0016J\b\u0010F\u001a\u00020 H\u0016J\u0006\u0010G\u001a\u000209J\u000e\u0010H\u001a\u0002092\u0006\u0010I\u001a\u00020\rJ\u0006\u0010J\u001a\u000209J\u000e\u0010K\u001a\u0002092\u0006\u0010L\u001a\u00020\u001aJ\u0006\u0010M\u001a\u000209J\b\u0010N\u001a\u000209H\u0002J\u0010\u0010O\u001a\u0002092\u0006\u0010@\u001a\u00020&H\u0002J\u0018\u0010P\u001a\u0002092\b\u0010Q\u001a\u0004\u0018\u00010R2\u0006\u0010S\u001a\u00020\rJ\u0010\u0010T\u001a\u00020&2\u0006\u0010U\u001a\u00020&H\u0002J\u0010\u0010V\u001a\u0002092\b\u0010W\u001a\u0004\u0018\u00010XJ\u0006\u0010Y\u001a\u00020XJ\u0006\u0010Z\u001a\u000209J\b\u0010[\u001a\u000209H\u0014J\u0010\u0010\\\u001a\u0002092\u0006\u0010]\u001a\u00020\rH\u0014J\u0006\u0010^\u001a\u000209J\u000e\u0010_\u001a\u0002092\u0006\u0010`\u001a\u000204J\u0006\u0010a\u001a\u00020\rJ\u0006\u0010b\u001a\u000209J\b\u0010c\u001a\u000209H\u0014J\u0006\u0010h\u001a\u000209J\u001a\u0010j\u001a\u0002092\u0006\u0010j\u001a\u00020&2\b\u0010Q\u001a\u0004\u0018\u00010RH\u0002J\u0018\u0010k\u001a\u0002092\u0006\u0010j\u001a\u00020&2\u0006\u0010Q\u001a\u00020RH\u0002J\u0018\u0010l\u001a\u0002092\u0006\u0010m\u001a\u00020&2\u0006\u0010Q\u001a\u00020RH\u0002J\"\u0010n\u001a\u0002092\u0006\u0010m\u001a\u00020&2\u0006\u0010U\u001a\u00020&2\b\u0010Q\u001a\u0004\u0018\u00010RH\u0002J\u000e\u0010o\u001a\u0002092\u0006\u0010p\u001a\u000204J\b\u0010q\u001a\u000209H\u0014J\b\u0010r\u001a\u000209H\u0014J\u0010\u0010s\u001a\u0002092\u0006\u0010t\u001a\u00020\rH\u0016J\u0010\u0010u\u001a\u0002092\u0006\u0010v\u001a\u00020wH\u0016J\u0010\u0010x\u001a\u0002092\u0006\u0010y\u001a\u00020zH\u0002J\u0018\u0010{\u001a\u00020|2\u0006\u0010}\u001a\u00020~2\u0006\u0010\u007f\u001a\u00020~H\u0002J\u0012\u0010\u0082\u0001\u001a\u0002092\u0007\u0010\u0083\u0001\u001a\u00020&H\u0002J\u001b\u0010\u0085\u0001\u001a\u0002092\u0007\u0010\u0083\u0001\u001a\u00020&2\u0007\u0010\u0086\u0001\u001a\u00020&H\u0002J\t\u0010\u0087\u0001\u001a\u000209H\u0002J\u0007\u0010\u0088\u0001\u001a\u000209J\t\u0010\u0089\u0001\u001a\u000209H\u0002J\t\u0010\u008a\u0001\u001a\u000209H\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0013\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0015\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0018\u00010(R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010:\u001a\u0002048F¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0013\u0010d\u001a\u0004\u0018\u00010e8F¢\u0006\u0006\u001a\u0004\bf\u0010gR\u000e\u0010i\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0080\u0001\u001a\u0005\u0018\u00010\u0081\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0084\u0001\u001a\u0004\u0018\u00010zX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u008e\u0001"}, d2 = {"Lcom/miui/floatwindow/feature/root/FloatFullWindow;", "Lcom/miui/floatwindow/feature/root/BaseFloatFullWindow;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "dockStatus", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;[Z)V", "mTodoView", "Lcom/miui/floatwindow/feature/todo/FwTodoListView;", "mNoteView", "Lcom/miui/floatwindow/feature/note/FwNoteListView;", "isContinuousExcerpt", "", "()Z", "setContinuousExcerpt", "(Z)V", "isClickExcerptNote", "setClickExcerptNote", "isWhiteListLinkExcerpt", "setWhiteListLinkExcerpt", "isPauseContinuousExcerpt", "setPauseContinuousExcerpt", "mIsInSingleExcerpt", "mIsInRecording", "mLastinfo", "Lcom/miui/notes/excerpt/ExcerptInfo;", "getMLastinfo", "()Lcom/miui/notes/excerpt/ExcerptInfo;", "setMLastinfo", "(Lcom/miui/notes/excerpt/ExcerptInfo;)V", "mExcerptId", "", "getMExcerptId", "()J", "setMExcerptId", "(J)V", "mResultValue", "", "mHandler", "Lcom/miui/floatwindow/feature/root/FloatFullWindow$ExcerptStatusHandler;", "mLastUrlInfo", "Lcom/miui/notes/excerpt/UrlInfo;", "mLastParseUrlInfo", "Lcom/miui/notes/excerpt/ParseUrlInfo;", "mParseJs", "mCurrentExcerptContent", "mLastParseTitleInfo", "Lcom/miui/notes/excerpt/ParseTitleInfo;", "mParams", "Landroid/widget/FrameLayout$LayoutParams;", "mExcerptCount", "", "mLastExcerptClickTime", "mSingleExcerptTimeOutTask", "Ljava/lang/Runnable;", "changeToGuide", "", "displayHeight", "getDisplayHeight", "()I", "updateFloatFullView", "initExcerptView", "showToast", "content", "time", "showQuickNoteUI", "show", "startContinuousExcerpt", "stopContinuousExcerpt", "getExcerptId", "pauseContinuousExcerpt", "setRecordState", "isInRecording", "resumeContinuousExcerpt", "excerptInfoChange", Constants.JSON_FILTER_INFO, "saveContinuousExcerpt", "saveLinkWithOutTitle", "startParseTitle", "saveSingleExcerpt", "uuid", "Ljava/util/UUID;", "isFromCardClick", "processBaiduUrl", "url", "restoreExcerptStatus", "saveStatus", "Lcom/miui/floatwindow/feature/root/FloatFullWindow$ExcerptSavedState;", "saveExcerptStatus", "onDataChanged", "onFullWindowShow", "onActiveFloatWindow", "isAlways", "onActiveTodo", "performActiveTotoAnimation", "repeatCount", "clickExcerptNote", "resetClickExcerptNote", "initWebview", "webView", "Landroid/webkit/WebView;", "getWebView", "()Landroid/webkit/WebView;", "clearWebView", "clearWebViewRunable", "parseUrl", "parseUrlTitle", "parseHtmlTitle", "html", "parseHtml", "changeFloatExcerptStatus", "excerptFloatType", "onDetachedFromWindow", "initGuideView", "changeGuideLineVisible", "visible", "onServiceConfigChanged", "newConfig", "Landroid/content/res/Configuration;", "onExcerptClick", "v", "Landroid/view/View;", "getCommonWindowLayoutParams", "Landroid/view/WindowManager$LayoutParams;", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "mFloatToast", "Landroid/widget/Toast;", "showExcerptToast", "msg", "mTipsWindowView", "showExcerptTipsWindowOnce", "prefKey", "showWhiteLinkExcerptLayout", "removeTipsWindow", "initJsScript", "restoreWidthToWrapContent", "ExcerptStatusHandler", "ExcerptSavedState", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatFullWindow extends BaseFloatFullWindow {
    public static final long MAX_SINGLE_EXCERPT_TIME_OUT = 10000;
    private static final int MSG_BACK_TO_DEFAULT = 0;
    private static final int MSG_TO_IDLE = 1;
    private static final int MSG_TO_SAVE_LINK_CARD = 2;
    private static final String PREF_KEY_SHOW_EXCERPT_GUIDE_BUBBLE = "is_show_excerpt_guide_bubble";
    private static final String PREF_KEY_SHOW_EXCERPT_LINK_GUIDE_BUBBLE = "is_show_excerpt_link_guide_bubble";
    public static final String TAG = "FloatFullWindow";
    private final Runnable clearWebViewRunable;
    private boolean isClickExcerptNote;
    private boolean isContinuousExcerpt;
    private boolean isPauseContinuousExcerpt;
    private boolean isWhiteListLinkExcerpt;
    private String mCurrentExcerptContent;
    private int mExcerptCount;
    private long mExcerptId;
    private Toast mFloatToast;
    private ExcerptStatusHandler mHandler;
    private boolean mIsInRecording;
    private boolean mIsInSingleExcerpt;
    private long mLastExcerptClickTime;
    private ParseTitleInfo mLastParseTitleInfo;
    private ParseUrlInfo mLastParseUrlInfo;
    private UrlInfo mLastUrlInfo;
    private ExcerptInfo mLastinfo;
    private FwNoteListView mNoteView;
    private final FrameLayout.LayoutParams mParams;
    private String mParseJs;
    private String mResultValue;
    private final Runnable mSingleExcerptTimeOutTask;
    private View mTipsWindowView;
    private FwTodoListView mTodoView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatFullWindow(Context context, boolean[] dockStatus) {
        super(context, dockStatus);
        Intrinsics.checkNotNullParameter(dockStatus, "dockStatus");
        this.mExcerptId = -1L;
        this.mResultValue = "";
        this.mLastUrlInfo = new UrlInfo("", 0L);
        this.mLastParseUrlInfo = new ParseUrlInfo("", 0L, false);
        this.mSingleExcerptTimeOutTask = new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                FloatFullWindow.mSingleExcerptTimeOutTask$lambda$0(this.f$0);
            }
        };
        this.clearWebViewRunable = new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.clearWebView();
            }
        };
        Logger.INSTANCE.d(TAG, TAG);
        initJsScript();
        Looper mainLooper = Looper.getMainLooper();
        Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
        this.mHandler = new ExcerptStatusHandler(this, mainLooper);
        ViewGroup.LayoutParams layoutParams = this.mFloatGuideView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        this.mParams = (FrameLayout.LayoutParams) layoutParams;
        if (PreferenceUtils.getIsFirstEnableTodo(getContext())) {
            onActiveTodo();
            PreferenceUtils.setIsFirstEnableTodo(getContext(), false);
        }
    }

    /* JADX INFO: renamed from: isContinuousExcerpt, reason: from getter */
    public final boolean getIsContinuousExcerpt() {
        return this.isContinuousExcerpt;
    }

    public final void setContinuousExcerpt(boolean z) {
        this.isContinuousExcerpt = z;
    }

    public final boolean isClickExcerptNote() {
        return this.isClickExcerptNote;
    }

    public final void setClickExcerptNote(boolean z) {
        this.isClickExcerptNote = z;
    }

    /* JADX INFO: renamed from: isWhiteListLinkExcerpt, reason: from getter */
    public final boolean getIsWhiteListLinkExcerpt() {
        return this.isWhiteListLinkExcerpt;
    }

    public final void setWhiteListLinkExcerpt(boolean z) {
        this.isWhiteListLinkExcerpt = z;
    }

    /* JADX INFO: renamed from: isPauseContinuousExcerpt, reason: from getter */
    public final boolean getIsPauseContinuousExcerpt() {
        return this.isPauseContinuousExcerpt;
    }

    public final void setPauseContinuousExcerpt(boolean z) {
        this.isPauseContinuousExcerpt = z;
    }

    public final ExcerptInfo getMLastinfo() {
        return this.mLastinfo;
    }

    public final void setMLastinfo(ExcerptInfo excerptInfo) {
        this.mLastinfo = excerptInfo;
    }

    public final long getMExcerptId() {
        return this.mExcerptId;
    }

    public final void setMExcerptId(long j) {
        this.mExcerptId = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mSingleExcerptTimeOutTask$lambda$0(FloatFullWindow floatFullWindow) {
        FwNoteListView fwNoteListView;
        FwNoteListAdapter listAdapter;
        if (!floatFullWindow.mIsInSingleExcerpt || (fwNoteListView = floatFullWindow.mNoteView) == null) {
            return;
        }
        Intrinsics.checkNotNull(fwNoteListView);
        if (fwNoteListView.getMAdapter() != null) {
            Logger.INSTANCE.i(TAG, "single excerpt time out");
            floatFullWindow.mIsInSingleExcerpt = false;
            FwNoteListView fwNoteListView2 = floatFullWindow.mNoteView;
            if (fwNoteListView2 != null && (listAdapter = fwNoteListView2.getMAdapter()) != null && !listAdapter.getIsExcerptSaving()) {
                FwNoteListView fwNoteListView3 = floatFullWindow.mNoteView;
                Intrinsics.checkNotNull(fwNoteListView3);
                fwNoteListView3.clearExcerptInfo();
            }
            floatFullWindow.mLastinfo = null;
            floatFullWindow.mExcerptId = -1L;
        }
    }

    public final void changeToGuide() {
        if (this.mFloatStatus != 2) {
            return;
        }
        startAutoFullGuideTransitionAnimation(0.0f, true);
        onActiveFloatWindow(false);
    }

    public final int getDisplayHeight() {
        return this.mDisplayHeight;
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    protected void updateFloatFullView() {
        View mRootView = this.mRootView;
        Intrinsics.checkNotNullExpressionValue(mRootView, "mRootView");
        this.mFloatWindowView = new FwFullView(mRootView);
        View viewFindViewById = this.mRootView.findViewById(R.id.note_group);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mNoteView = new FwNoteListView(viewFindViewById);
        this.mTodoView = new FwTodoListView(this.mRootView);
        FwTodoListView fwTodoListView = this.mTodoView;
        Intrinsics.checkNotNull(fwTodoListView);
        this.mFwTodoPresenter = new FwTodoListPresenter(fwTodoListView, SchedulerProvider.getInstance());
        FwNoteListView fwNoteListView = this.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView);
        this.mFwNotePresenter = new FwNoteListPresenter(fwNoteListView, NoteSchedulerProvider.INSTANCE.getInstance());
        FwNoteListView fwNoteListView2 = this.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView2);
        fwNoteListView2.setPresenter(this.mFwNotePresenter);
        this.mRootView.findViewById(R.id.note_toggle_region).setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatFullWindow.updateFloatFullView$lambda$1(this.f$0, view);
            }
        });
        IFloatWindowView iFloatWindowView = this.mFloatWindowView;
        Intrinsics.checkNotNull(iFloatWindowView, "null cannot be cast to non-null type com.miui.floatwindow.feature.root.FwFullView");
        ((FwFullView) iFloatWindowView).setModule(this.mNoteView, this.mTodoView);
        FwNoteListView fwNoteListView3 = this.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView3);
        fwNoteListView3.setParentHelper(this.mFloatWindowView);
        FwTodoListView fwTodoListView2 = this.mTodoView;
        Intrinsics.checkNotNull(fwTodoListView2);
        fwTodoListView2.setParentHelper(this.mFloatWindowView);
        initExcerptView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateFloatFullView$lambda$1(FloatFullWindow floatFullWindow, View view) {
        FwNoteListView fwNoteListView = floatFullWindow.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView);
        FwNoteListView fwNoteListView2 = floatFullWindow.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView2);
        fwNoteListView.setExpand(!fwNoteListView2.getMExpandCached());
    }

    private final void initExcerptView() {
        View viewFindViewById = this.mRootView.findViewById(R.id.continuous_excerpt);
        Folme.useAt(viewFindViewById).touch().handleTouchOf(viewFindViewById, new AnimConfig[0]);
        Intrinsics.checkNotNull(viewFindViewById);
        UIUtils.setFolmeHoverEffect(viewFindViewById);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatFullWindow.initExcerptView$lambda$3(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initExcerptView$lambda$3(FloatFullWindow floatFullWindow, View view) {
        String str;
        Intrinsics.checkNotNullParameter(view, "view");
        if (floatFullWindow.isContinuousExcerpt) {
            FloatWindowManagerAgent.stopContinuousExcerpt();
            ExcerptStat.trackStopExcerpt(floatFullWindow.mExcerptCount);
            return;
        }
        if (Utils.isAboveSDK33()) {
            str = "android.permission.READ_MEDIA_IMAGES";
        } else {
            str = "android.permission.READ_EXTERNAL_STORAGE";
        }
        if (PermissionUtils.isPermissionNotGranted(BaseNoteApp.INSTANCE.getInstance(), str)) {
            floatFullWindow.showToast(TodoUtils.getString(Utils.isAboveSDK33() ? R.string.toast_refuse_to_use_photos_in_excerpt : R.string.toast_refuse_to_use_storage_in_excerpt), 1000L);
            ExcerptStatusHandler excerptStatusHandler = floatFullWindow.mHandler;
            if (excerptStatusHandler != null) {
                excerptStatusHandler.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        FloatWindowManagerAgent.startContinuousExcerpt();
                    }
                }, 1000L);
            }
        } else {
            FloatWindowManagerAgent.startContinuousExcerpt();
        }
        ExcerptStat.reportClickEvent(ExcerptStat.TIP_START_EXCERPT_CLICK, null);
    }

    public final void showToast(String content, long time) {
        if (this.mFloatWindowView != null) {
            this.mFloatWindowView.showToast(content, time);
        }
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    public void showQuickNoteUI(boolean show) {
        if ((this.isContinuousExcerpt || this.mGuideType == 1) && show) {
            Toast.makeText(this.mContext, R.string.excerpting, 1).show();
        } else {
            super.showQuickNoteUI(show);
        }
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    public void startContinuousExcerpt() {
        if (this.isContinuousExcerpt) {
            return;
        }
        this.mLastinfo = null;
        FwNoteListView fwNoteListView = this.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView);
        FwNoteListAdapter listAdapter = fwNoteListView.getMAdapter();
        if (listAdapter != null && !listAdapter.getIsExcerptSaving()) {
            FwNoteListView fwNoteListView2 = this.mNoteView;
            Intrinsics.checkNotNull(fwNoteListView2);
            FwNoteListAdapter listAdapter2 = fwNoteListView2.getMAdapter();
            if (listAdapter2 != null) {
                listAdapter2.clearExcerptInfo();
            }
        }
        View viewFindViewById = this.mExcerptDefault.findViewById(R.id.iv_stop_excerpt);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type android.widget.ImageView");
        ((ImageView) viewFindViewById).setImageResource(R.drawable.ic_stop_excerpt);
        View viewFindViewById2 = this.mExcerptDefault.findViewById(R.id.excerpt_tv);
        Intrinsics.checkNotNull(viewFindViewById2, "null cannot be cast to non-null type android.widget.TextView");
        ((TextView) viewFindViewById2).setText(R.string.excerpting);
        restoreWidthToWrapContent();
        this.isContinuousExcerpt = true;
        changeGuideType(1);
        changeToGuide();
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler == null) {
            return;
        }
        Intrinsics.checkNotNull(excerptStatusHandler);
        excerptStatusHandler.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FloatFullWindow.startContinuousExcerpt$lambda$4(this.f$0);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startContinuousExcerpt$lambda$4(FloatFullWindow floatFullWindow) {
        String string = floatFullWindow.getResources().getString(R.string.excerpt_bubble_tips);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        floatFullWindow.showExcerptTipsWindowOnce(string, PREF_KEY_SHOW_EXCERPT_GUIDE_BUBBLE);
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    public void stopContinuousExcerpt() {
        this.isContinuousExcerpt = false;
        this.mLastinfo = null;
        this.mExcerptId = -1L;
        this.isPauseContinuousExcerpt = false;
        this.mExcerptCount = 0;
        changeGuideType(0);
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    /* JADX INFO: renamed from: getExcerptId, reason: from getter */
    public long getMExcerptId() {
        return this.mExcerptId;
    }

    public final void pauseContinuousExcerpt() {
        Logger.INSTANCE.i(TAG, "pause continuous excerpt");
        this.isPauseContinuousExcerpt = this.isContinuousExcerpt;
    }

    public final void setRecordState(boolean isInRecording) {
        Logger.INSTANCE.d(TAG, "setRecordState: " + isInRecording);
        this.mIsInRecording = isInRecording;
    }

    public final void resumeContinuousExcerpt() {
        Logger.INSTANCE.i(TAG, "resume continuous excerpt");
        this.isPauseContinuousExcerpt = false;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00d7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x00d9  */
    public final void excerptInfoChange(ExcerptInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        Logger.INSTANCE.d(TAG, "excerptInfoChange:" + info + ",isContinuousExcerpt:" + this.isContinuousExcerpt);
        if (this.isPauseContinuousExcerpt || (!RomUtils.isPadMode() && this.mNewOrientation == 2)) {
            Logger.INSTANCE.e(TAG, "excerpt was pause!!!!");
            return;
        }
        this.mExcerptCount++;
        this.mLastinfo = info;
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler != null) {
            Intrinsics.checkNotNull(excerptStatusHandler);
            if (excerptStatusHandler.hasCallbacks(this.mSingleExcerptTimeOutTask)) {
                Logger.INSTANCE.i(TAG, "remove SingleExcerptTimeOutTask");
                ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
                Intrinsics.checkNotNull(excerptStatusHandler2);
                excerptStatusHandler2.removeCallbacks(this.mSingleExcerptTimeOutTask);
            }
        }
        if (this.isContinuousExcerpt) {
            onActiveFloatWindow(false);
            saveContinuousExcerpt();
            return;
        }
        this.mIsInSingleExcerpt = true;
        boolean zIsInFullWindow = isInFullWindow();
        boolean isFirstActiveTodoAnimation = PreferenceUtils.getIsFirstActiveTodoAnimation(getContext());
        Logger.INSTANCE.i(TAG, " SingleExcerpt，isFirstActiveTodoAni：  " + isFirstActiveTodoAnimation + ",fullWin:" + zIsInFullWindow);
        if (zIsInFullWindow && this.mHandler != null) {
            Logger.INSTANCE.i(TAG, "post SingleExcerptTimeOutTask full win");
            ExcerptStatusHandler excerptStatusHandler3 = this.mHandler;
            Intrinsics.checkNotNull(excerptStatusHandler3);
            excerptStatusHandler3.postDelayed(this.mSingleExcerptTimeOutTask, 10000L);
        }
        if (PreferenceUtils.getEnableExcerptLinkFloat(getContext())) {
            ExcerptInfo excerptInfo = this.mLastinfo;
            Intrinsics.checkNotNull(excerptInfo);
            if (LinkUtils.isAccurateValidUrl(excerptInfo.getExcerptContent())) {
                this.isWhiteListLinkExcerpt = true;
                Logger.INSTANCE.d(TAG, "excerptLinkFloat true||isAccurateValidUrl...");
                showWhiteLinkExcerptLayout();
            } else if (isFirstActiveTodoAnimation) {
                Logger.INSTANCE.d(TAG, "firstActive...");
                PreferenceUtils.setIsFirstActiveTodoAnimation(getContext(), false);
                onActiveTodo();
            }
        } else if (isFirstActiveTodoAnimation) {
            Logger.INSTANCE.d(TAG, "firstActive...");
            PreferenceUtils.setIsFirstActiveTodoAnimation(getContext(), false);
            onActiveTodo();
        }
        FwNoteListView fwNoteListView = this.mNoteView;
        Intrinsics.checkNotNull(fwNoteListView);
        fwNoteListView.showSingleExcerptInfo(info, isInFullWindow());
    }

    public final void saveContinuousExcerpt() {
        Logger.INSTANCE.d(TAG, "saveContinuousExcerpt with excerptID:" + this.mExcerptId + ",mTipsWindowView:" + this.mTipsWindowView);
        View view = this.mTipsWindowView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            if (view.isAttachedToWindow()) {
                this.windowManager.removeView(this.mTipsWindowView);
                this.mTipsWindowView = null;
            }
        }
        saveLinkWithOutTitle();
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler != null) {
            Intrinsics.checkNotNull(excerptStatusHandler);
            excerptStatusHandler.removeMessages(0);
        }
        ExcerptInfo excerptInfo = this.mLastinfo;
        Intrinsics.checkNotNull(excerptInfo);
        int excerptType = excerptInfo.getExcerptType();
        Logger.INSTANCE.d(TAG, "saveContinuousExcerpt with excerptID:" + this.mExcerptId + ",type:" + excerptType);
        if (excerptType == 0) {
            changeFloatExcerptStatus(1);
        } else {
            changeFloatExcerptStatus(2);
        }
        ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
        if (excerptStatusHandler2 != null) {
            Intrinsics.checkNotNull(excerptStatusHandler2);
            excerptStatusHandler2.sendEmptyMessageDelayed(0, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
        }
        if (this.mExcerptId != -1) {
            ExcerptInfo excerptInfo2 = this.mLastinfo;
            Intrinsics.checkNotNull(excerptInfo2);
            String excerptContent = excerptInfo2.getExcerptContent();
            if (excerptType == 0 && LinkUtils.isAccurateValidUrl(excerptContent)) {
                startParseTitle(excerptContent);
                return;
            } else {
                ExcerptDataUtils.updateExcerptByClip(getContext(), this.mExcerptId, this.mLastinfo, true ^ LiteUtils.isLiteOrMiddle());
                return;
            }
        }
        if (excerptType == 0) {
            ExcerptInfo excerptInfo3 = this.mLastinfo;
            Intrinsics.checkNotNull(excerptInfo3);
            String excerptContent2 = excerptInfo3.getExcerptContent();
            if (LinkUtils.isAccurateValidUrl(excerptContent2)) {
                Logger.INSTANCE.d(TAG, "saveContinuousExcerpt,startParseTitle ");
                startParseTitle(excerptContent2);
                return;
            }
            Logger.INSTANCE.d(TAG, "saveContinuousExcerpt,saveClipDataExcerpt ");
            Context context = getContext();
            ExcerptInfo excerptInfo4 = this.mLastinfo;
            Intrinsics.checkNotNull(excerptInfo4);
            this.mExcerptId = ExcerptDataUtils.saveClipDataExcerpt(context, excerptInfo4.getUUID(), !LiteUtils.isLiteOrMiddle());
            return;
        }
        ExcerptInfo excerptInfo5 = this.mLastinfo;
        Intrinsics.checkNotNull(excerptInfo5);
        String excerptContent3 = excerptInfo5.getExcerptContent();
        ExcerptInfo excerptInfo6 = this.mLastinfo;
        Intrinsics.checkNotNull(excerptInfo6);
        this.mExcerptId = ExcerptDataUtils.saveImageExcerpt(excerptContent3, excerptInfo6.getUUID());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveLinkWithOutTitle() {
        Logger.INSTANCE.d(TAG, "saveLinkWithOutTitle with id: " + this.mExcerptId);
        ParseTitleInfo parseTitleInfo = this.mLastParseTitleInfo;
        if (parseTitleInfo == null || this.mLastinfo == null) {
            return;
        }
        Intrinsics.checkNotNull(parseTitleInfo);
        String linkUrl = parseTitleInfo.getLinkUrl();
        long j = this.mExcerptId;
        if (j == -1) {
            ExcerptInfo excerptInfo = this.mLastinfo;
            Intrinsics.checkNotNull(excerptInfo);
            this.mExcerptId = ExcerptDataUtils.saveLinkExcerpt(linkUrl, "", excerptInfo.getUUID());
        } else {
            ExcerptDataUtils.updateExcerptLink(j, linkUrl, "");
        }
        this.mLastParseTitleInfo = null;
    }

    private final void startParseTitle(String content) {
        Logger.INSTANCE.d(TAG, "startParseTitle");
        if (!PreferenceUtils.isCTAAccepted(getContext())) {
            long j = this.mExcerptId;
            if (j == -1) {
                ExcerptInfo excerptInfo = this.mLastinfo;
                Intrinsics.checkNotNull(excerptInfo);
                this.mExcerptId = ExcerptDataUtils.saveLinkExcerpt(content, "", excerptInfo.getUUID());
                return;
            }
            ExcerptDataUtils.updateExcerptLink(j, content, "");
            return;
        }
        UUID uuidRandomUUID = UUID.randomUUID();
        this.mLastParseTitleInfo = new ParseTitleInfo(uuidRandomUUID, content);
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler);
        Message messageObtainMessage = excerptStatusHandler.obtainMessage();
        Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "obtainMessage(...)");
        messageObtainMessage.what = 2;
        messageObtainMessage.obj = this.mLastParseTitleInfo;
        ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler2);
        excerptStatusHandler2.sendMessageDelayed(messageObtainMessage, 10000L);
        Intrinsics.checkNotNull(uuidRandomUUID);
        parseUrlTitle(content, uuidRandomUUID);
    }

    public final void saveSingleExcerpt(UUID uuid, boolean isFromCardClick) {
        Logger.INSTANCE.d(TAG, "saveSingleExcerpt");
        this.mIsInSingleExcerpt = false;
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler);
        if (excerptStatusHandler.hasCallbacks(this.mSingleExcerptTimeOutTask)) {
            Logger.INSTANCE.i(TAG, "save,remove SingleExcerptTimeOutTask");
            ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
            Intrinsics.checkNotNull(excerptStatusHandler2);
            excerptStatusHandler2.removeCallbacks(this.mSingleExcerptTimeOutTask);
        }
        ExcerptInfo excerptInfo = this.mLastinfo;
        if (excerptInfo == null) {
            Logger.INSTANCE.e(TAG, "mLastinfo is null");
            return;
        }
        Intrinsics.checkNotNull(excerptInfo);
        this.mCurrentExcerptContent = excerptInfo.getExcerptContent();
        ExcerptInfo excerptInfo2 = this.mLastinfo;
        Intrinsics.checkNotNull(excerptInfo2);
        if (excerptInfo2.getExcerptType() == 0) {
            String str = this.mCurrentExcerptContent;
            Intrinsics.checkNotNull(str);
            if (LinkUtils.isAccurateValidUrl(str)) {
                FwNoteListView fwNoteListView = this.mNoteView;
                Intrinsics.checkNotNull(fwNoteListView);
                fwNoteListView.notifyNoteSaving(uuid, !isFromCardClick);
                String str2 = this.mCurrentExcerptContent;
                Intrinsics.checkNotNull(str2);
                if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "baidu.com", false, 2, (Object) null)) {
                    String str3 = this.mCurrentExcerptContent;
                    Intrinsics.checkNotNull(str3);
                    parseUrl(processBaiduUrl(str3), uuid);
                    return;
                } else {
                    String str4 = this.mCurrentExcerptContent;
                    Intrinsics.checkNotNull(str4);
                    parseUrl(str4, uuid);
                    return;
                }
            }
            ExcerptDataUtils.saveTextExcerpt(this.mCurrentExcerptContent, uuid);
            return;
        }
        ExcerptDataUtils.saveImageExcerpt(this.mCurrentExcerptContent, uuid);
    }

    private final String processBaiduUrl(String url) {
        String strDecodeUrl = Utils.decodeUrl(url, "UTF-8");
        Intrinsics.checkNotNull(strDecodeUrl);
        Matcher matcher = Pattern.compile("(?<=\"url\":\").*?(?=\",)").matcher(strDecodeUrl);
        if (!matcher.find()) {
            return strDecodeUrl;
        }
        String strSubstring = strDecodeUrl.substring(matcher.start(), matcher.end());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final void restoreExcerptStatus(ExcerptSavedState saveStatus) {
        Logger.INSTANCE.i(TAG, "restoreExcerptSaveStatus " + saveStatus);
        if (saveStatus == null) {
            return;
        }
        this.isPauseContinuousExcerpt = saveStatus.getIsPauseContinuousExcerpt();
        this.isWhiteListLinkExcerpt = saveStatus.getIsWhiteListLinkExcerpt();
        this.mExcerptId = saveStatus.getMExcerptId();
        this.mLastinfo = saveStatus.getMLastinfo();
        if (saveStatus.getIsContinuousExcerpt()) {
            startContinuousExcerpt();
            this.isContinuousExcerpt = true;
        }
    }

    public final ExcerptSavedState saveExcerptStatus() {
        Logger.INSTANCE.i(TAG, "saveExcerptStatus");
        ExcerptSavedState excerptSavedState = new ExcerptSavedState();
        excerptSavedState.setContinuousExcerpt(this.isContinuousExcerpt);
        excerptSavedState.setPauseContinuousExcerpt(this.isPauseContinuousExcerpt);
        excerptSavedState.setWhiteListLinkExcerpt(this.isWhiteListLinkExcerpt);
        excerptSavedState.setMExcerptId(this.mExcerptId);
        excerptSavedState.setMLastinfo(this.mLastinfo);
        return excerptSavedState;
    }

    public final void onDataChanged() {
        if (isInFullWindow()) {
            FwTodoListView fwTodoListView = this.mTodoView;
            Intrinsics.checkNotNull(fwTodoListView);
            fwTodoListView.forceRefreshData();
        }
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    protected void onFullWindowShow() {
        final FwFullScrollView fwFullScrollView = (FwFullScrollView) this.mFullFloatView.findViewById(R.id.data_content);
        this.mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow.onFullWindowShow.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                FloatFullWindow.this.mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                fwFullScrollView.setBannerView(FloatFullWindow.this.mRootView.findViewById(R.id.note_group));
                fwFullScrollView.setListContentRv((RecyclerView) FloatFullWindow.this.mRootView.findViewById(R.id.rv_todo));
            }
        });
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler != null) {
            Intrinsics.checkNotNull(excerptStatusHandler);
            if (excerptStatusHandler.hasCallbacks(this.mSingleExcerptTimeOutTask)) {
                return;
            }
            if (this.mIsInSingleExcerpt) {
                Logger.INSTANCE.i(TAG, "post SingleExcerptTimeOutTask");
                ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
                Intrinsics.checkNotNull(excerptStatusHandler2);
                excerptStatusHandler2.postDelayed(this.mSingleExcerptTimeOutTask, 10000L);
            }
            ExcerptStat.reportEnterEvent(ExcerptStat.TIP_EXCERPT_PAGE_ENTER, null);
        }
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    protected void onActiveFloatWindow(boolean isAlways) {
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler == null) {
            return;
        }
        Intrinsics.checkNotNull(excerptStatusHandler);
        excerptStatusHandler.removeMessages(1);
        if (isAlways) {
            return;
        }
        Folme.useAt(this.mExcerptView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
        ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler2);
        excerptStatusHandler2.sendEmptyMessageDelayed(1, 5000L);
    }

    public final void onActiveTodo() {
        Logger.INSTANCE.d(TAG, "onActiveTodo:" + this.mInDockMode + ",mHandler:" + this.mHandler);
        if (this.mInDockMode) {
            return;
        }
        Folme.clean(this.mGuideLineView);
        this.mGuideLineView.clearAnimation();
        performActiveTotoAnimation(0);
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler == null) {
            return;
        }
        Intrinsics.checkNotNull(excerptStatusHandler);
        excerptStatusHandler.removeMessages(0);
        ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler2);
        excerptStatusHandler2.sendEmptyMessageDelayed(0, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
    }

    public final void performActiveTotoAnimation(final int repeatCount) {
        this.mGuideLineView.setBackground(getContext().getResources().getDrawable(R.drawable.todo_float_guide_active_bg));
        AnimState animStateAdd = new AnimState("start").add(ViewProperty.TRANSLATION_X, 0.0d).add(ViewProperty.ALPHA, 0.5d);
        AnimState animStateAdd2 = new AnimState("show").add(ViewProperty.TRANSLATION_X, DisplayUtils.dip2px(getContext(), 5.0f)).add(ViewProperty.ALPHA, 1.0d);
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(EaseManager.getStyle(-2, 0.9f, 0.7f));
        Folme.useAt(this.mGuideLineView).state().fromTo(animStateAdd, animStateAdd2, animConfig).then(animStateAdd, new AnimConfig().setDelay(100L).setEase(EaseManager.getStyle(-2, 0.9f, 1.0f)).addListeners(new TransitionListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow.performActiveTotoAnimation.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object toTag) {
                Intrinsics.checkNotNullParameter(toTag, "toTag");
                super.onComplete(toTag);
                int i = repeatCount;
                if (i != 0) {
                    this.performActiveTotoAnimation(i - 1);
                }
            }
        }));
    }

    /* JADX INFO: renamed from: clickExcerptNote, reason: from getter */
    public final boolean getIsClickExcerptNote() {
        return this.isClickExcerptNote;
    }

    public final void resetClickExcerptNote() {
        this.isClickExcerptNote = false;
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    protected void initWebview() {
        this.mWebView = new WebView(this.mContext);
        WebView.setWebContentsDebuggingEnabled(false);
        WebSettings settings = this.mWebView.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "getSettings(...)");
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(true);
        settings.setAllowContentAccess(false);
        settings.setMixedContentMode(1);
    }

    public final WebView getWebView() {
        Logger.INSTANCE.d(TAG, "getWebView");
        if (this.mWebView == null) {
            initWebview();
        }
        return this.mWebView;
    }

    public final void clearWebView() {
        Logger.INSTANCE.d(TAG, "clearWebView->destroy web view");
        if (this.mWebView != null) {
            this.mWebView.destroy();
        }
        this.mWebView = null;
    }

    private final void parseUrl(String parseUrl, UUID uuid) {
        Logger.INSTANCE.d(TAG, "parseUrl");
        if (!PreferenceUtils.isCTAAccepted(getContext())) {
            NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, false);
            return;
        }
        if (this.mLastParseUrlInfo.isParse() && Math.abs(System.currentTimeMillis() - this.mLastParseUrlInfo.getExcerptTime()) < 60000.0d) {
            FloatWindowManagerAgent.showToast(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.last_excerpt_unfinish_hint), 2000L);
            NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, false);
            return;
        }
        this.mLastParseUrlInfo = new ParseUrlInfo(parseUrl, System.currentTimeMillis(), true);
        if (getWebView() == null) {
            return;
        }
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler != null) {
            Intrinsics.checkNotNull(excerptStatusHandler);
            excerptStatusHandler.removeCallbacks(this.clearWebViewRunable);
        }
        Logger.INSTANCE.d(TAG, "parseUrl :" + (getWebView() != null));
        WebView webView = getWebView();
        Intrinsics.checkNotNull(webView);
        webView.setWebViewClient(new C02251(parseUrl, uuid));
        WebView webView2 = getWebView();
        Intrinsics.checkNotNull(webView2);
        webView2.loadUrl(parseUrl);
        if (this.mHandler != null) {
            Logger.INSTANCE.d(TAG, "post clear web view delay 20s");
            ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
            Intrinsics.checkNotNull(excerptStatusHandler2);
            excerptStatusHandler2.postDelayed(this.clearWebViewRunable, 20000L);
        }
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.root.FloatFullWindow$parseUrl$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FloatFullWindow.kt */
    @Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"com/miui/floatwindow/feature/root/FloatFullWindow$parseUrl$1", "Landroid/webkit/WebViewClient;", "onPageFinished", "", "view", "Landroid/webkit/WebView;", "url", "", "shouldOverrideUrlLoading", "", StatConstants.Event.REQUEST, "Landroid/webkit/WebResourceRequest;", "onReceivedError", SNSAuthProvider.VALUE_SNS_ERROR, "Landroid/webkit/WebResourceError;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class C02251 extends WebViewClient {
        final /* synthetic */ String $parseUrl;
        final /* synthetic */ UUID $uuid;

        C02251(String str, UUID uuid) {
            this.$parseUrl = str;
            this.$uuid = uuid;
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, final String url) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(url, "url");
            Logger.INSTANCE.d(FloatFullWindow.TAG, "parseUrl onPageFinished");
            if (Intrinsics.areEqual(url, FloatFullWindow.this.mLastUrlInfo.getUrl()) && Math.abs(System.currentTimeMillis() - FloatFullWindow.this.mLastUrlInfo.getExcerptTime()) < 3000.0d) {
                FloatFullWindow.this.mLastUrlInfo = new UrlInfo(url, System.currentTimeMillis());
            } else {
                if (FloatFullWindow.this.getWebView() == null) {
                    return;
                }
                WebView webView = FloatFullWindow.this.getWebView();
                Intrinsics.checkNotNull(webView);
                final FloatFullWindow floatFullWindow = FloatFullWindow.this;
                final String str = this.$parseUrl;
                final UUID uuid = this.$uuid;
                webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();", new ValueCallback() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$parseUrl$1$$ExternalSyntheticLambda0
                    @Override // android.webkit.ValueCallback
                    public final void onReceiveValue(Object obj) {
                        FloatFullWindow.C02251.onPageFinished$lambda$0(floatFullWindow, url, str, uuid, (String) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onPageFinished$lambda$0(FloatFullWindow floatFullWindow, String str, String str2, UUID uuid, String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            String str3 = value;
            if (!TextUtils.isEmpty(str3) && !Intrinsics.areEqual(value, "null")) {
                floatFullWindow.mLastUrlInfo = new UrlInfo(str, System.currentTimeMillis());
                if (StringsKt.startsWith$default(str2, "https://mp.weixin.qq.com/", false, 2, (Object) null)) {
                    value = new Regex("(u003Cimg .*?)(display: none)(.*?\\\\\">)").replace(str3, "$1display: inline$3");
                }
                floatFullWindow.parseHtml(value, str2, uuid);
                return;
            }
            NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, false);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(request, "request");
            String string = new StringBuilder().append(request.getUrl()).toString();
            if (TextUtils.isEmpty(string)) {
                return true;
            }
            return (StringsKt.startsWith$default(string, "https://", false, 2, (Object) null) || StringsKt.startsWith$default(string, "http://", false, 2, (Object) null) || StringsKt.startsWith$default(string, "www.", false, 2, (Object) null)) ? false : true;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(error, "error");
            Logger.INSTANCE.d(FloatFullWindow.TAG, "onReceivedError errorCode: " + error);
            FloatFullWindow.this.mLastParseUrlInfo.setParse(false);
        }
    }

    private final void parseUrlTitle(final String parseUrl, final UUID uuid) {
        AsyncTask.execute(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                FloatFullWindow.parseUrlTitle$lambda$7(parseUrl, this, uuid);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseUrlTitle$lambda$7(String str, final FloatFullWindow floatFullWindow, final UUID uuid) {
        try {
            Logger.INSTANCE.d(TAG, "parseUrlTitle");
            final String string = "";
            ResponseBody responseBodyBody = new OkHttpClient().newBuilder().build().newCall(new Request.Builder().url(str).method(PassportSimpleRequest.HTTP_METHOD_GET, null).build()).execute().body();
            if (responseBodyBody != null) {
                string = responseBodyBody.toString();
            }
            if (!TextUtils.isEmpty(string)) {
                floatFullWindow.post(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        FloatFullWindow.parseUrlTitle$lambda$7$lambda$6(this.f$0, string, uuid);
                    }
                });
            } else {
                floatFullWindow.saveLinkWithOutTitle();
            }
        } catch (Exception e) {
            floatFullWindow.saveLinkWithOutTitle();
            Logger.INSTANCE.e(TAG, e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseUrlTitle$lambda$7$lambda$6(FloatFullWindow floatFullWindow, String str, UUID uuid) {
        try {
            floatFullWindow.parseHtmlTitle(str, uuid);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, new StringBuilder().append(e).toString());
        }
    }

    private final void parseHtmlTitle(final String html, final UUID uuid) {
        if (this.mParseJs == null) {
            this.mParseJs = JsUtils.getFromAssets(BaseNoteApp.INSTANCE.getInstance(), "bundle.js");
        }
        Logger.INSTANCE.d(TAG, "parseHtmlTitle ");
        if (getWebView() == null) {
            Logger.INSTANCE.d(TAG, "parseHtmlTitle webview is null,return");
            return;
        }
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler != null) {
            Intrinsics.checkNotNull(excerptStatusHandler);
            excerptStatusHandler.removeCallbacks(this.clearWebViewRunable);
        }
        WebView webView = getWebView();
        Intrinsics.checkNotNull(webView);
        String str = this.mParseJs;
        Intrinsics.checkNotNull(str);
        webView.evaluateJavascript(str, new ValueCallback() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda3
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                FloatFullWindow.parseHtmlTitle$lambda$9(html, this, uuid, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseHtmlTitle$lambda$9(String str, final FloatFullWindow floatFullWindow, final UUID uuid, String str2) {
        String strReplace = new Regex("`").replace(str, "\\`");
        WebView webView = floatFullWindow.getWebView();
        Intrinsics.checkNotNull(webView);
        webView.evaluateJavascript("parseHeader(`" + strReplace + "`) ", new ValueCallback() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda5
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                FloatFullWindow.parseHtmlTitle$lambda$9$lambda$8(this.f$0, uuid, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseHtmlTitle$lambda$9$lambda$8(FloatFullWindow floatFullWindow, UUID uuid, String value1) {
        Intrinsics.checkNotNullParameter(value1, "value1");
        if (!TextUtils.isEmpty(value1) && !Intrinsics.areEqual(value1, "null")) {
            ExcerptTitleInfo excerptTitleInfo = (ExcerptTitleInfo) new Gson().fromJson(value1, ExcerptTitleInfo.class);
            ParseTitleInfo parseTitleInfo = floatFullWindow.mLastParseTitleInfo;
            if (parseTitleInfo != null) {
                Intrinsics.checkNotNull(parseTitleInfo);
                if (Intrinsics.areEqual(parseTitleInfo.getUuid(), uuid)) {
                    long j = floatFullWindow.mExcerptId;
                    if (j == -1) {
                        ParseTitleInfo parseTitleInfo2 = floatFullWindow.mLastParseTitleInfo;
                        Intrinsics.checkNotNull(parseTitleInfo2);
                        floatFullWindow.mExcerptId = ExcerptDataUtils.saveLinkExcerpt(parseTitleInfo2.getLinkUrl(), excerptTitleInfo.getTitle(), uuid);
                    } else {
                        ParseTitleInfo parseTitleInfo3 = floatFullWindow.mLastParseTitleInfo;
                        Intrinsics.checkNotNull(parseTitleInfo3);
                        ExcerptDataUtils.updateExcerptLink(j, parseTitleInfo3.getLinkUrl(), excerptTitleInfo.getTitle());
                    }
                    floatFullWindow.mLastParseTitleInfo = null;
                }
            }
        } else {
            floatFullWindow.saveLinkWithOutTitle();
        }
        ExcerptStatusHandler excerptStatusHandler = floatFullWindow.mHandler;
        if (excerptStatusHandler != null) {
            Intrinsics.checkNotNull(excerptStatusHandler);
            excerptStatusHandler.postDelayed(floatFullWindow.clearWebViewRunable, 20000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void parseHtml(final String html, String url, final UUID uuid) {
        if (this.mParseJs == null) {
            this.mParseJs = JsUtils.getFromAssets(BaseNoteApp.INSTANCE.getInstance(), "bundle.js");
        }
        if (getWebView() == null) {
            return;
        }
        WebView webView = getWebView();
        Intrinsics.checkNotNull(webView);
        String str = this.mParseJs;
        Intrinsics.checkNotNull(str);
        webView.evaluateJavascript(str, new ValueCallback() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda2
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                FloatFullWindow.parseHtml$lambda$12(this.f$0, html, uuid, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseHtml$lambda$12(final FloatFullWindow floatFullWindow, String str, final UUID uuid, String str2) {
        if (floatFullWindow.getWebView() == null) {
            return;
        }
        WebView webView = floatFullWindow.getWebView();
        Intrinsics.checkNotNull(webView);
        webView.evaluateJavascript("(function() { return parseFromHtml(" + str + ") } )()", new ValueCallback() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda13
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                FloatFullWindow.parseHtml$lambda$12$lambda$11(this.f$0, uuid, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseHtml$lambda$12$lambda$11(final FloatFullWindow floatFullWindow, final UUID uuid, final String value1) {
        Intrinsics.checkNotNullParameter(value1, "value1");
        if (!TextUtils.isEmpty(value1) && !Intrinsics.areEqual(value1, "null")) {
            if (Intrinsics.areEqual(value1, floatFullWindow.mResultValue)) {
                return;
            }
            floatFullWindow.mResultValue = value1;
            AsyncTask.execute(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FloatFullWindow.parseHtml$lambda$12$lambda$11$lambda$10(this.f$0, value1, uuid);
                }
            });
            return;
        }
        NotifyUtils.getInstance().notifyExcerptLinkResult(uuid, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void parseHtml$lambda$12$lambda$11$lambda$10(FloatFullWindow floatFullWindow, String str, UUID uuid) {
        floatFullWindow.mLastParseUrlInfo.setParse(false);
        ExcerptDataUtils.saveHtmlExcerpt((HtmlResult) new Gson().fromJson(str, HtmlResult.class), floatFullWindow.mCurrentExcerptContent, uuid);
        floatFullWindow.mResultValue = "";
    }

    public final void changeFloatExcerptStatus(int excerptFloatType) {
        Logger.INSTANCE.d(TAG, "changeFloatExcerptStatus:" + excerptFloatType);
        Folme.useAt(this.mExcerptedLayout).state().clean();
        Folme.useAt(this.mExcerptDefault).state().clean();
        if (excerptFloatType == 0) {
            this.mExcerptDefault.setVisibility(0);
            this.mExcerptedLayout.setVisibility(8);
            this.mExcerptedLayout.setAlpha(0.0f);
            restoreWidthToWrapContent();
            Folme.useAt(this.mExcerptDefault).state().to(ViewProperty.ALPHA, 1, new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.3f)));
            return;
        }
        if (excerptFloatType == 3) {
            View viewFindViewById = this.mExcerptDefault.findViewById(R.id.excerpt_tv);
            Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView) viewFindViewById).setText(R.string.single_link_excerpt_saving);
            restoreWidthToWrapContent();
            Unit unit = Unit.INSTANCE;
            return;
        }
        this.mExcerptedLayout.setVisibility(0);
        ((TextView) this.mExcerptedLayout.findViewById(R.id.float_excerpt_tv)).setText(R.string.excerpted_to_note);
        Logger.INSTANCE.d(TAG, "notify expert save success!!");
        this.mExcerptedLayout.measure(View.MeasureSpec.makeMeasureSpec(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Integer.MIN_VALUE), 0);
        int measuredWidth = this.mExcerptedLayout.getMeasuredWidth();
        int measuredWidth2 = this.mExcerptDefault.getMeasuredWidth();
        this.mParams.width = measuredWidth + DisplayUtils.dip2px(getContext(), 30.0f);
        this.mFloatGuideView.setLayoutParams(this.mParams);
        Folme.useAt(this.mExcerptDefault).state().to(ViewProperty.ALPHA, 0, new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.2f))).setTo(ViewProperty.TRANSLATION_X, 0).to(ViewProperty.TRANSLATION_X, Float.valueOf(measuredWidth2 * 0.2f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.3f)), new AnonymousClass1());
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.root.FloatFullWindow$changeFloatExcerptStatus$1, reason: invalid class name */
    /* JADX INFO: compiled from: FloatFullWindow.kt */
    @Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, d2 = {"com/miui/floatwindow/feature/root/FloatFullWindow$changeFloatExcerptStatus$1", "Lmiuix/animation/listener/TransitionListener;", "onBegin", "", "toTag", "", "onComplete", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AnonymousClass1 extends TransitionListener {
        AnonymousClass1() {
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object toTag) {
            Intrinsics.checkNotNullParameter(toTag, "toTag");
            super.onBegin(toTag);
            if (FloatFullWindow.this.mHandler == null) {
                return;
            }
            ExcerptStatusHandler excerptStatusHandler = FloatFullWindow.this.mHandler;
            Intrinsics.checkNotNull(excerptStatusHandler);
            final FloatFullWindow floatFullWindow = FloatFullWindow.this;
            excerptStatusHandler.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$changeFloatExcerptStatus$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FloatFullWindow.AnonymousClass1.onBegin$lambda$0(floatFullWindow);
                }
            }, 100L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onBegin$lambda$0(FloatFullWindow floatFullWindow) {
            Folme.useAt(floatFullWindow.mExcerptedLayout).state().to(ViewProperty.ALPHA, 1, new AnimConfig().setEase(EaseManager.getStyle(-2, 0.9f, 0.3f)));
            ((LottieAnimationView) floatFullWindow.mExcerptedLayout.findViewById(R.id.float_excerpt_star)).playAnimation();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object toTag) {
            Intrinsics.checkNotNullParameter(toTag, "toTag");
            super.onComplete(toTag);
            FloatFullWindow.this.mExcerptDefault.setVisibility(8);
            FloatFullWindow.this.mExcerptDefault.setAlpha(0.0f);
            FloatFullWindow.this.mExcerptDefault.setTranslationX(0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FloatFullWindow.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/miui/floatwindow/feature/root/FloatFullWindow$ExcerptStatusHandler;", "Landroid/os/Handler;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/floatwindow/feature/root/FloatFullWindow;Landroid/os/Looper;)V", "handleMessage", "", "msg", "Landroid/os/Message;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    final class ExcerptStatusHandler extends Handler {
        final /* synthetic */ FloatFullWindow this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ExcerptStatusHandler(FloatFullWindow floatFullWindow, Looper looper) {
            super(looper);
            Intrinsics.checkNotNullParameter(looper, "looper");
            this.this$0 = floatFullWindow;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            if (msg.what == 0) {
                if (this.this$0.mGuideType == 0) {
                    ViewPropertyAnimator duration = this.this$0.mGuideLineView.animate().alpha(0.1f).setDuration(200L);
                    final FloatFullWindow floatFullWindow = this.this$0;
                    duration.withEndAction(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$ExcerptStatusHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FloatFullWindow.ExcerptStatusHandler.handleMessage$lambda$0(floatFullWindow);
                        }
                    }).start();
                    return;
                }
                this.this$0.changeFloatExcerptStatus(0);
                return;
            }
            if (msg.what == 1) {
                Folme.useAt(this.this$0.mExcerptView).state().to(ViewProperty.ALPHA, Float.valueOf(0.6f));
                return;
            }
            if (msg.what == 2) {
                Object obj = msg.obj;
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.notes.excerpt.ParseTitleInfo");
                ParseTitleInfo parseTitleInfo = (ParseTitleInfo) obj;
                if (this.this$0.mLastParseTitleInfo != null) {
                    ParseTitleInfo parseTitleInfo2 = this.this$0.mLastParseTitleInfo;
                    Intrinsics.checkNotNull(parseTitleInfo2);
                    if (Intrinsics.areEqual(parseTitleInfo2.getUuid(), parseTitleInfo.getUuid())) {
                        this.this$0.saveLinkWithOutTitle();
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void handleMessage$lambda$0(FloatFullWindow floatFullWindow) {
            floatFullWindow.mGuideLineView.setBackground(floatFullWindow.getContext().getResources().getDrawable(R.drawable.todo_float_guide_bg));
            floatFullWindow.mGuideLineView.animate().alpha(1.0f).setDuration(200L).start();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.INSTANCE.i(TAG, "onDetachedFromWindow,handler:" + this.mHandler);
        if (this.mFloatWindowView != null) {
            this.mFloatWindowView.onDetachFromWindow();
        }
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler == null) {
            return;
        }
        Intrinsics.checkNotNull(excerptStatusHandler);
        excerptStatusHandler.removeMessages(0);
        ExcerptStatusHandler excerptStatusHandler2 = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler2);
        excerptStatusHandler2.removeMessages(1);
        ExcerptStatusHandler excerptStatusHandler3 = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler3);
        excerptStatusHandler3.removeMessages(2);
        ExcerptStatusHandler excerptStatusHandler4 = this.mHandler;
        Intrinsics.checkNotNull(excerptStatusHandler4);
        excerptStatusHandler4.removeCallbacksAndMessages(null);
        this.mHandler = null;
        clearWebView();
        this.mLastExcerptClickTime = 0L;
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    protected void initGuideView() {
        super.initGuideView();
        Logger.INSTANCE.d(TAG, "initGuideView");
        this.mExcerptView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatFullWindow.initGuideView$lambda$13(this.f$0, view);
            }
        });
        findViewById(R.id.iv_stop_excerpt).setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatFullWindow.initGuideView$lambda$14(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initGuideView$lambda$13(FloatFullWindow floatFullWindow, View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        floatFullWindow.onExcerptClick(v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initGuideView$lambda$14(FloatFullWindow floatFullWindow, View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        floatFullWindow.onExcerptClick(v);
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    public void changeGuideLineVisible(boolean visible) {
        Logger.INSTANCE.d("Notes:Excerpt", "changeGuideLineVisible");
        super.changeGuideLineVisible(visible);
        if (this.mFloatStatus == 0) {
            if (visible) {
                View view = this.mTipsWindowView;
                if (view != null) {
                    Intrinsics.checkNotNull(view);
                    if (view.isAttachedToWindow()) {
                        View view2 = this.mTipsWindowView;
                        Intrinsics.checkNotNull(view2);
                        view2.setVisibility(0);
                        return;
                    }
                    return;
                }
                return;
            }
            View view3 = this.mTipsWindowView;
            if (view3 != null) {
                Intrinsics.checkNotNull(view3);
                if (view3.isAttachedToWindow()) {
                    View view4 = this.mTipsWindowView;
                    Intrinsics.checkNotNull(view4);
                    view4.setVisibility(4);
                }
            }
        }
    }

    @Override // com.miui.floatwindow.feature.root.BaseFloatFullWindow
    public void onServiceConfigChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        View view = this.mTipsWindowView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            view.performClick();
        }
        SetScreenStatus();
        if (!isPadUiMode()) {
            if (this.mNewOrientation == 2) {
                if (this.mFloatStatus == 2) {
                    changeFloatViewStatus(0);
                    FwTodoListView fwTodoListView = this.mTodoView;
                    if (fwTodoListView != null) {
                        Intrinsics.checkNotNull(fwTodoListView);
                        fwTodoListView.forceExitEditing();
                    }
                }
                showGuideViewVisible(false);
            } else if (this.mNewOrientation == 1) {
                showGuideViewVisible(true);
            }
        }
        super.onServiceConfigChanged(newConfig);
    }

    private final void onExcerptClick(View v) {
        Logger.INSTANCE.d(TAG, "onExcerptClick");
        if (this.isWhiteListLinkExcerpt) {
            this.isWhiteListLinkExcerpt = false;
            changeFloatExcerptStatus(3);
            ExcerptInfo excerptInfo = this.mLastinfo;
            Intrinsics.checkNotNull(excerptInfo);
            saveSingleExcerpt(excerptInfo.getUUID(), false);
            onActiveFloatWindow(false);
            return;
        }
        if (v.getId() == R.id.iv_stop_excerpt) {
            Logger.INSTANCE.d(TAG, "stop excerpt");
            String string = getResources().getString(R.string.toast_excerpt_end);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            showExcerptToast(string);
            stopContinuousExcerpt();
            ExcerptStat.trackStopExcerpt(this.mExcerptCount);
            return;
        }
        if (System.currentTimeMillis() - this.mLastExcerptClickTime < EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS) {
            Logger.INSTANCE.d(TAG, "Click too frequently, ignore click");
            return;
        }
        this.mLastExcerptClickTime = System.currentTimeMillis();
        long j = this.mExcerptId;
        if (j == -1 && this.mLastinfo == null) {
            Logger.INSTANCE.d(TAG, " excerpt failed!!! ");
            String string2 = getResources().getString(R.string.toast_excerpt_no_content);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            showExcerptToast(string2);
            ExcerptStat.trackExcerptingClick("fail", "");
            return;
        }
        if (j == -1) {
            Logger.INSTANCE.d(TAG, " excerpt saving!!! ");
            String string3 = getResources().getString(R.string.toast_excerpt_saved);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            showExcerptToast(string3);
            ExcerptStat.trackExcerptingClick("fail", "");
            return;
        }
        if (this.isPauseContinuousExcerpt) {
            String string4 = getResources().getString(R.string.toast_excerpt_open_note);
            Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
            showExcerptToast(string4);
            ExcerptStat.trackExcerptingClick("fail", "");
            return;
        }
        if (this.mIsInRecording) {
            Logger.INSTANCE.d(TAG, "onExcerptClick in Recording");
            return;
        }
        this.isClickExcerptNote = true;
        NoteOperationHelper.INSTANCE.openNote(this.mExcerptId);
        ExcerptStat.trackExcerptingClick("success", "");
    }

    private final WindowManager.LayoutParams getCommonWindowLayoutParams(float x, float y) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.type = R2.attr.preferenceCheckableItemSetMaskPaddingEnd;
        layoutParams.format = 1;
        layoutParams.gravity = 81;
        layoutParams.flags = 8;
        layoutParams.x = (int) x;
        layoutParams.y = (int) y;
        return layoutParams;
    }

    private final void showExcerptToast(String msg) {
        Toast toast = this.mFloatToast;
        if (toast != null) {
            Intrinsics.checkNotNull(toast);
            toast.cancel();
        }
        Toast toastMakeText = Toast.makeText(getContext(), msg, 0);
        this.mFloatToast = toastMakeText;
        if (toastMakeText != null) {
            toastMakeText.show();
        }
    }

    private final void showExcerptTipsWindowOnce(String msg, String prefKey) {
        ColorStateList colorStateList;
        if (PreferenceUtils.getBooleanPreference(getContext(), prefKey, false) || !isEnabled()) {
            return;
        }
        boolean z = this.mIsNightMode;
        final RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        relativeLayout.setLayoutDirection(0);
        relativeLayout.setAlpha(0.0f);
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.fw_tip_notification, (ViewGroup) null);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type android.widget.LinearLayout");
        final LinearLayout linearLayout = (LinearLayout) viewInflate;
        if (z) {
            colorStateList = ContextCompat.getColorStateList(BaseNoteApp.INSTANCE.getInstance(), R.color.miuix_appcompat_black);
        } else {
            colorStateList = ContextCompat.getColorStateList(BaseNoteApp.INSTANCE.getInstance(), R.color.miuix_appcompat_white);
        }
        Drawable drawableMutate = getResources().getDrawable(R.drawable.excerpt_bubble_hint).mutate();
        Intrinsics.checkNotNullExpressionValue(drawableMutate, "mutate(...)");
        DrawableCompat.setTintList(drawableMutate, colorStateList);
        linearLayout.setBackground(drawableMutate);
        ((TextView) linearLayout.findViewById(android.R.id.message)).setText(msg);
        relativeLayout.addView(linearLayout, new RelativeLayout.LayoutParams(-2, -2));
        WindowManager.LayoutParams commonWindowLayoutParams = getCommonWindowLayoutParams(0.0f, 0.0f);
        commonWindowLayoutParams.width = -1;
        commonWindowLayoutParams.height = -1;
        commonWindowLayoutParams.gravity = 8388659;
        RelativeLayout relativeLayout2 = relativeLayout;
        this.windowManager.addView(relativeLayout2, commonWindowLayoutParams);
        relativeLayout.post(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                FloatFullWindow.showExcerptTipsWindowOnce$lambda$15(relativeLayout, this, linearLayout);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatFullWindow.showExcerptTipsWindowOnce$lambda$16(this.f$0, relativeLayout, view);
            }
        });
        this.mTipsWindowView = relativeLayout2;
        PreferenceUtils.setBooleanPreference(getContext(), prefKey, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showExcerptTipsWindowOnce$lambda$15(RelativeLayout relativeLayout, FloatFullWindow floatFullWindow, LinearLayout linearLayout) {
        int[] iArr = new int[2];
        relativeLayout.getLocationOnScreen(iArr);
        linearLayout.setTranslationY(((floatFullWindow.mInDockMode ? floatFullWindow.guideYInDockMode : floatFullWindow.guideY) - iArr[1]) + DisplayUtils.dip2px(floatFullWindow.getContext(), 65.0f));
        relativeLayout.animate().alpha(1.0f).setInterpolator(floatFullWindow.mPhysicBasedInterpolator).setDuration(300L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showExcerptTipsWindowOnce$lambda$16(FloatFullWindow floatFullWindow, RelativeLayout relativeLayout, View view) {
        floatFullWindow.windowManager.removeView(relativeLayout);
        floatFullWindow.mTipsWindowView = null;
    }

    private final void showWhiteLinkExcerptLayout() {
        View viewFindViewById = this.mExcerptView.findViewById(R.id.iv_stop_excerpt);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type android.widget.ImageView");
        ((ImageView) viewFindViewById).setImageResource(R.drawable.ic_excerpt);
        View viewFindViewById2 = this.mExcerptView.findViewById(R.id.excerpt_tv);
        Intrinsics.checkNotNull(viewFindViewById2, "null cannot be cast to non-null type android.widget.TextView");
        ((TextView) viewFindViewById2).setText(R.string.excerpt);
        restoreWidthToWrapContent();
        changeGuideType(1);
        changeToGuide();
        String string = getResources().getString(R.string.excerpt_link_bubble_tips);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showExcerptTipsWindowOnce(string, PREF_KEY_SHOW_EXCERPT_LINK_GUIDE_BUBBLE);
        onActiveFloatWindow(true);
        ExcerptStatusHandler excerptStatusHandler = this.mHandler;
        if (excerptStatusHandler == null) {
            return;
        }
        Intrinsics.checkNotNull(excerptStatusHandler);
        excerptStatusHandler.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.root.FloatFullWindow$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                FloatFullWindow.showWhiteLinkExcerptLayout$lambda$17(this.f$0);
            }
        }, 3500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showWhiteLinkExcerptLayout$lambda$17(FloatFullWindow floatFullWindow) {
        floatFullWindow.isWhiteListLinkExcerpt = false;
        floatFullWindow.changeGuideType(0);
        floatFullWindow.changeFloatExcerptStatus(0);
        View view = floatFullWindow.mTipsWindowView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            if (view.isAttachedToWindow()) {
                floatFullWindow.windowManager.removeView(floatFullWindow.mTipsWindowView);
                floatFullWindow.mTipsWindowView = null;
            }
        }
    }

    public final void removeTipsWindow() {
        View view = this.mTipsWindowView;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            if (view.isAttachedToWindow()) {
                Logger.INSTANCE.d(TAG, "removeTipsWindow");
                this.windowManager.removeView(this.mTipsWindowView);
                this.mTipsWindowView = null;
            }
        }
    }

    private final void initJsScript() {
        this.mParseJs = JsUtils.getFromAssets(BaseNoteApp.INSTANCE.getInstance(), "bundle.js");
    }

    private final void restoreWidthToWrapContent() {
        this.mParams.width = -2;
        this.mFloatGuideView.setLayoutParams(this.mParams);
        ViewGroup.LayoutParams layoutParams = this.mExcerptDefault.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.width = -2;
        this.mExcerptDefault.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: compiled from: FloatFullWindow.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u0006\"\u0004\b\n\u0010\bR\u001a\u0010\u000b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lcom/miui/floatwindow/feature/root/FloatFullWindow$ExcerptSavedState;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isContinuousExcerpt", "", "()Z", "setContinuousExcerpt", "(Z)V", "isWhiteListLinkExcerpt", "setWhiteListLinkExcerpt", "isPauseContinuousExcerpt", "setPauseContinuousExcerpt", "mLastinfo", "Lcom/miui/notes/excerpt/ExcerptInfo;", "getMLastinfo", "()Lcom/miui/notes/excerpt/ExcerptInfo;", "setMLastinfo", "(Lcom/miui/notes/excerpt/ExcerptInfo;)V", "mExcerptId", "", "getMExcerptId", "()J", "setMExcerptId", "(J)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ExcerptSavedState {
        private boolean isContinuousExcerpt;
        private boolean isPauseContinuousExcerpt;
        private boolean isWhiteListLinkExcerpt;
        private long mExcerptId = -1;
        private ExcerptInfo mLastinfo;

        /* JADX INFO: renamed from: isContinuousExcerpt, reason: from getter */
        public final boolean getIsContinuousExcerpt() {
            return this.isContinuousExcerpt;
        }

        public final void setContinuousExcerpt(boolean z) {
            this.isContinuousExcerpt = z;
        }

        /* JADX INFO: renamed from: isWhiteListLinkExcerpt, reason: from getter */
        public final boolean getIsWhiteListLinkExcerpt() {
            return this.isWhiteListLinkExcerpt;
        }

        public final void setWhiteListLinkExcerpt(boolean z) {
            this.isWhiteListLinkExcerpt = z;
        }

        /* JADX INFO: renamed from: isPauseContinuousExcerpt, reason: from getter */
        public final boolean getIsPauseContinuousExcerpt() {
            return this.isPauseContinuousExcerpt;
        }

        public final void setPauseContinuousExcerpt(boolean z) {
            this.isPauseContinuousExcerpt = z;
        }

        public final ExcerptInfo getMLastinfo() {
            return this.mLastinfo;
        }

        public final void setMLastinfo(ExcerptInfo excerptInfo) {
            this.mLastinfo = excerptInfo;
        }

        public final long getMExcerptId() {
            return this.mExcerptId;
        }

        public final void setMExcerptId(long j) {
            this.mExcerptId = j;
        }
    }
}
