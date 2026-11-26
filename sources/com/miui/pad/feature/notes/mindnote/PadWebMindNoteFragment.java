package com.miui.pad.feature.notes.mindnote;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.FragmentActionListener;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.constant.FrameConstant;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.mindnoteeditor.MindNoteWebView;
import com.miui.notes.R;
import com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface;
import com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.model.NoteModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.BaseEditorActionBarController;
import com.miui.notes.ui.NotesListActivity;
import com.miui.pad.feature.notes.share.PadSnsEditActivity;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.miui.richeditor.theme.Theme;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import miuix.appcompat.app.ActionBar;
import miuix.navigator.Navigator;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: PadWebMindNoteFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u001e\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\"H\u0016J\u0010\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020\u000bH\u0016J\b\u0010(\u001a\u00020\u0016H\u0014J\u0010\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\tH\u0014J\b\u0010+\u001a\u00020\"H\u0016J\u0012\u0010,\u001a\u00020\"2\b\u0010-\u001a\u0004\u0018\u00010$H\u0016J\u001a\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0010\u00100\u001a\u00020\"2\u0006\u00101\u001a\u00020\u000bH\u0002J\b\u00102\u001a\u00020\"H\u0014J\b\u00103\u001a\u00020\"H\u0014J\b\u00104\u001a\u00020\"H\u0014J\f\u00105\u001a\u0006\u0012\u0002\b\u000306H\u0014J\b\u00107\u001a\u000208H\u0014J\u0010\u00109\u001a\u00020\"2\u0006\u0010:\u001a\u00020\u0016H\u0014J\b\u0010;\u001a\u00020\u0016H\u0016J\b\u0010<\u001a\u00020\u000bH\u0016J\b\u0010=\u001a\u00020\u0016H\u0016J\u0010\u0010>\u001a\u00020\"2\u0006\u0010?\u001a\u00020@H\u0016J\u0018\u0010A\u001a\u00020\"2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u000bH\u0014J\b\u0010E\u001a\u00020\u000bH\u0002J\u0010\u0010J\u001a\u00020\u000b2\u0006\u0010K\u001a\u00020LH\u0016J\u0010\u0010M\u001a\u00020\"2\u0006\u0010K\u001a\u00020LH\u0016J\b\u0010N\u001a\u00020\u000bH\u0016J\u0010\u0010O\u001a\u00020\u000b2\u0006\u0010P\u001a\u00020QH\u0016J\b\u0010R\u001a\u00020\"H\u0016J\b\u0010S\u001a\u00020\"H\u0002J\b\u0010T\u001a\u00020\"H\u0016J\b\u0010U\u001a\u00020\"H\u0016J\b\u0010V\u001a\u00020\"H\u0016J \u0010W\u001a\u00020\"2\u0006\u0010X\u001a\u00020Y2\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020\u000bH\u0016J\u0010\u0010]\u001a\u00020\"2\u0006\u0010^\u001a\u00020YH\u0016J\b\u0010_\u001a\u00020\"H\u0014J\u0010\u0010`\u001a\u00020\"2\u0006\u0010a\u001a\u00020\u0005H\u0016J\b\u0010b\u001a\u00020\"H\u0016J\b\u0010c\u001a\u00020\"H\u0016J\b\u0010d\u001a\u00020\u000bH\u0014J\u0010\u0010e\u001a\u00020\"2\u0006\u00101\u001a\u00020\u000bH\u0004J\b\u0010f\u001a\u00020\"H\u0014J\u0010\u0010g\u001a\u00020\"2\u0006\u0010h\u001a\u000208H\u0014J\u0010\u0010j\u001a\u00020\"2\u0006\u0010k\u001a\u00020\u0016H\u0016J\u0010\u0010l\u001a\u00020\"2\u0006\u00101\u001a\u00020\u000bH\u0014J\u0010\u0010m\u001a\u00020\"2\u0006\u0010Z\u001a\u00020[H\u0002J\b\u0010n\u001a\u00020\"H\u0016J\b\u0010o\u001a\u00020\"H\u0004J\u0010\u0010p\u001a\u00020\"2\u0006\u0010q\u001a\u00020rH\u0002J\u0010\u0010w\u001a\u00020\"2\u0006\u0010x\u001a\u00020\u0016H\u0016J\u0010\u0010y\u001a\u00020\"2\u0006\u0010z\u001a\u00020\u0016H\u0004J\u0010\u0010{\u001a\u00020\"2\u0006\u0010k\u001a\u00020\u0016H\u0004J\b\u0010|\u001a\u00020\"H\u0002J\b\u0010}\u001a\u00020\"H\u0002J\b\u0010~\u001a\u00020\"H\u0002J\b\u0010\u007f\u001a\u00020\"H\u0002J\t\u0010\u0080\u0001\u001a\u00020\"H\u0002J\t\u0010\u0081\u0001\u001a\u00020\"H\u0002J\t\u0010\u0082\u0001\u001a\u00020\"H\u0002J\t\u0010\u0083\u0001\u001a\u00020\"H\u0002J\t\u0010\u0084\u0001\u001a\u00020\"H\u0002J\u0012\u0010\u0085\u0001\u001a\u00020\"2\u0007\u0010\u0086\u0001\u001a\u00020\u0016H\u0002J?\u0010\u0087\u0001\u001a\u00020\"2\u0007\u0010\u0088\u0001\u001a\u00020\u000b2\u0007\u0010\u0089\u0001\u001a\u00020\u000b2\u0007\u0010\u008a\u0001\u001a\u00020\u000b2\u0007\u0010\u008b\u0001\u001a\u00020\u000b2\u0007\u0010\u008c\u0001\u001a\u00020\u00052\u0007\u0010\u008d\u0001\u001a\u00020\u0005H\u0016J\t\u0010\u008e\u0001\u001a\u00020\"H\u0014J\u0012\u0010\u008f\u0001\u001a\u00020\"2\u0007\u0010\u0090\u0001\u001a\u00020\u000bH\u0016J\t\u0010\u0091\u0001\u001a\u00020\"H\u0002R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\f\u001a\u0004\u0018\u00010\r@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\"\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\u0010\f\u001a\u0004\u0018\u00010\u0011@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u000bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010F\u001a\u00020G¢\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0014\u0010i\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bi\u0010\u001aR\u0014\u0010s\u001a\u00020t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\bu\u0010v¨\u0006\u0092\u0001"}, d2 = {"Lcom/miui/pad/feature/notes/mindnote/PadWebMindNoteFragment;", "Lcom/miui/notes/basefeature/mindnote/BaseWebMindNoteFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "mWebContent", "Landroid/view/View;", "mIsNaviShow", "", "value", "Lcom/miui/pad/feature/notes/mindnote/PadMindNoteActionBarController;", "actionBarController", "getActionBarController", "()Lcom/miui/pad/feature/notes/mindnote/PadMindNoteActionBarController;", "Lcom/miui/pad/feature/notes/mindnote/PadMindNoteToolBarController;", "toolBarController", "getToolBarController", "()Lcom/miui/pad/feature/notes/mindnote/PadMindNoteToolBarController;", "mUiMode", "", "mEnableChangeUiMode", "mIsFirstCreate", "getMIsFirstCreate", "()Z", "setMIsFirstCreate", "(Z)V", "onBackPressedCallback", "Landroidx/activity/OnBackPressedCallback;", "mDataUpdateTaskReceiver", "Landroid/content/BroadcastReceiver;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onMultiWindowModeChanged", "isInMultiWindowMode", "getLayoutId", "initView", "rootView", "onResume", "onUpdateArguments", "newArguments", "onViewInflated", "view", "keyBoardShowStatusChange", "show", "initPasswordController", "initWebView", "initKeyboardDetector", "createBridgeInterFace", "Lcom/miui/notes/basefeature/mindnote/BaseMindNoteBridgeInterface;", "getSaveDataDuration", "", "updatePaddingBottom", "paddingBottom", "getUiMode", "isMobile", "getEditMarginTopInOverViewMode", "preHandleIntent", "intent", "Landroid/content/Intent;", "setWorkingNote", "workingNote", "Lcom/miui/notes/model/WorkingNote;", "forceEdit", "handleBackPress", "mEditCallback", "Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;", "getMEditCallback", "()Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onPrepareOptionsMenu", "onBackPressed", "onOptionsItemSelected", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onDestroyView", "deleteEmptyNote", "exitWithoutSave", "onEnterActionMode", "onExitActionMode", "onResponsiveLayout", "configuration", "Landroid/content/res/Configuration;", "screenSpec", "Lmiuix/responsive/map/ScreenSpec;", "responsiveLayoutStateChanged", "onConfigurationChanged", "newConfig", "onFoldChangeScreen", "disableLandscape", "currentPage", "onViewMode", "onEditMode", "isEnableSaveEmptyData", "showToolbar", "startToShareActivity", "onNoteDeleted", NotifyUtils.KEY_NOTE_ID, "isDataEmpty", "onSplitModeChanged", "newMode", "setNaviState", "updateHomeButtonState", "onPause", "initActionBar", "updateSoftInputMode", "activity", "Landroid/app/Activity;", "noteEditorPadding", "", "getNoteEditorPadding", "()F", "onExtraPaddingChanged", "extraHorizontalPadding", "changeViewMode", "mode", "onViewModeChanged", NotesTextStat.UNDO, NotesTextStat.REDO, "addChildNode", "addBrotherNode", "deleteNode", "levelIncrease", "levelReduce", "saveAsImage", "share", "changeUiMode", "uiMode", "onUpdateViewState", "canSelect", "canQuery", "canPhrase", "canTranslate", "selectData", "contextData", "doDeleteAndLoad", "setInSearchMode", "inSearchMode", "changeContentMargin", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class PadWebMindNoteFragment extends BaseWebMindNoteFragment {
    private PadMindNoteActionBarController actionBarController;
    private final boolean mEnableChangeUiMode;
    private View mWebContent;
    private OnBackPressedCallback onBackPressedCallback;
    private PadMindNoteToolBarController toolBarController;
    private final String TAG = "PadWebMindNoteFragment";
    private boolean mIsNaviShow = true;
    private int mUiMode = 1;
    private boolean mIsFirstCreate = true;
    private final BroadcastReceiver mDataUpdateTaskReceiver = new BroadcastReceiver() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$mDataUpdateTaskReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            Logger.INSTANCE.d(this.this$0.getTAG(), "onReceive From mDataUpdateTaskReceiver");
            this.this$0.updateWorkingNoteFromOuter();
        }
    };
    private final BaseEditorActionBarController.EditCallback mEditCallback = new PadWebMindNoteFragment$mEditCallback$1(this);

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void disableLandscape(String currentPage) {
        Intrinsics.checkNotNullParameter(currentPage, "currentPage");
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected int getLayoutId() {
        return R.layout.mindnote_webview_layout;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected long getSaveDataDuration() {
        return 1500L;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initKeyboardDetector() {
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initPasswordController() {
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected boolean isEnableSaveEmptyData() {
        return true;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public boolean isMobile() {
        return false;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void updatePaddingBottom(int paddingBottom) {
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public String getTAG() {
        return this.TAG;
    }

    public final PadMindNoteActionBarController getActionBarController() {
        return this.actionBarController;
    }

    public final PadMindNoteToolBarController getToolBarController() {
        return this.toolBarController;
    }

    protected final boolean getMIsFirstCreate() {
        return this.mIsFirstCreate;
    }

    protected final void setMIsFirstCreate(boolean z) {
        this.mIsFirstCreate = z;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeRes(R.style.NavigatorSecondaryContentTheme_Web);
        this.mIsFirstCreate = true;
        this.onBackPressedCallback = new OnBackPressedCallback() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment.onCreate.1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                Logger.INSTANCE.d(PadWebMindNoteFragment.this.getTAG(), "onBackPressedDispatcher,handleOnBackPressed");
                if (PadWebMindNoteFragment.this.handleBackPress()) {
                    return;
                }
                Logger.INSTANCE.d(PadWebMindNoteFragment.this.getTAG(), "handleOnBackPressed,activity consume");
                setEnabled(false);
                PadWebMindNoteFragment.this.requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        };
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this.mLocalBroadcastManager != null) {
            this.mLocalBroadcastManager.unregisterReceiver(this.mDataUpdateTaskReceiver);
        }
        this.toolBarController = null;
        this.actionBarController = null;
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (getActivity() != null) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            updateSoftInputMode(fragmentActivityRequireActivity);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initView(View rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        this.mWebView = (WebView) rootView.findViewById(R.id.web_view);
        this.mWebContent = rootView.findViewById(R.id.web_content);
        ViewGroup.LayoutParams layoutParams = this.mWebView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        int noteEditorPadding = (int) getNoteEditorPadding();
        marginLayoutParams.setMarginStart(noteEditorPadding);
        marginLayoutParams.setMarginEnd(noteEditorPadding);
        this.mWebView.setLayoutParams(marginLayoutParams);
        this.mWebView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                PadWebMindNoteFragment.initView$lambda$0(this.f$0, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        if (this.mWebView instanceof MindNoteWebView) {
            WebView webView = this.mWebView;
            Intrinsics.checkNotNull(webView, "null cannot be cast to non-null type com.miui.mindnoteeditor.MindNoteWebView");
            ((MindNoteWebView) webView).setFeatureConfig("{\n\t\"select\": 1,\n    \"query\": 1,\n\t\"phrase\": 1,\n\t\"translate\": 1\n}");
            WebView webView2 = this.mWebView;
            Intrinsics.checkNotNull(webView2, "null cannot be cast to non-null type com.miui.mindnoteeditor.MindNoteWebView");
            ((MindNoteWebView) webView2).setKeyShortCutListener(new OnKeyDownAndShortCutListener() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment.initView.2
                @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
                public boolean onKeyShortcut(int keyCode, KeyEvent event) {
                    Intrinsics.checkNotNullParameter(event, "event");
                    return false;
                }

                @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
                public boolean onKeyUp(int keyCode, KeyEvent event) {
                    Intrinsics.checkNotNullParameter(event, "event");
                    return false;
                }

                @Override // com.miui.richeditor.OnKeyDownAndShortCutListener
                public boolean onKeyDown(int keyCode, KeyEvent event) {
                    Intrinsics.checkNotNullParameter(event, "event");
                    if (PadWebMindNoteFragment.this.isAdded() && event.hasModifiers(4096) && event.getRepeatCount() == 0) {
                        if (keyCode == 41) {
                            if (!Utils.isFastClick(0, 500L)) {
                                HomeViewModel homeViewModel = PadWebMindNoteFragment.this.homeViewModel;
                                Intrinsics.checkNotNull(homeViewModel);
                                homeViewModel.createMindNote();
                            }
                            return true;
                        }
                        if (keyCode == 42) {
                            if (!Utils.isFastClick(0, 500L)) {
                                HomeViewModel homeViewModel2 = PadWebMindNoteFragment.this.homeViewModel;
                                Intrinsics.checkNotNull(homeViewModel2);
                                homeViewModel2.createNormalNote();
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        WeakReference weakReference = new WeakReference(this.mEditCallback);
        View viewFindViewById = requireActivity().findViewById(R.id.mind_note_tool_group);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        PadMindNoteToolBarController padMindNoteToolBarController = new PadMindNoteToolBarController(viewFindViewById, (BaseEditorActionBarController.EditCallback) weakReference.get());
        this.toolBarController = padMindNoteToolBarController;
        Intrinsics.checkNotNull(padMindNoteToolBarController);
        Theme theme = ResourceManager.getTheme(0);
        Intrinsics.checkNotNullExpressionValue(theme, "getTheme(...)");
        padMindNoteToolBarController.updateStyle(theme);
        if (this.mIsFromRebuild) {
            showToolbar(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$0(PadWebMindNoteFragment padWebMindNoteFragment, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int iAbs = (int) Math.abs(i5 - i7);
        int iAbs2 = (int) Math.abs(i - i3);
        Math.abs(i8 - i6);
        Math.abs(i4 - i2);
        if (iAbs2 != iAbs) {
            if (padWebMindNoteFragment.mMindContentType == 0) {
                int[] screenSizeDp = DisplayUtils.getScreenSizeDp(padWebMindNoteFragment.getContext());
                if (screenSizeDp[0] > screenSizeDp[1]) {
                    padWebMindNoteFragment.setScreenOrientationToWebView(2);
                    return;
                } else {
                    padWebMindNoteFragment.setScreenOrientationToWebView(1);
                    return;
                }
            }
            padWebMindNoteFragment.setScreenOrientationToWebView(1);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        Intrinsics.checkNotNull(onBackPressedCallback);
        onBackPressedCallback.setEnabled(true);
        OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        OnBackPressedCallback onBackPressedCallback2 = this.onBackPressedCallback;
        Intrinsics.checkNotNull(onBackPressedCallback2);
        onBackPressedDispatcher.addCallback(onBackPressedCallback2);
        if (!this.mIsFirstCreate || getView() == null || getActivity() == null || !requireActivity().isInMultiWindowMode() || this.homeViewModel == null) {
            return;
        }
        PadWebMindNoteFragment padWebMindNoteFragment = this;
        if (Navigator.get(padWebMindNoteFragment) == null || Navigator.get(padWebMindNoteFragment).getNavigationMode() != Navigator.Mode.NLC) {
            return;
        }
        requireView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PadWebMindNoteFragment.onResume$lambda$1(this.f$0);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onResume$lambda$1(PadWebMindNoteFragment padWebMindNoteFragment) {
        HomeViewModel homeViewModel = padWebMindNoteFragment.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel);
        homeViewModel.setKeyboardChange(true);
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle newArguments) {
        super.onUpdateArguments(newArguments);
        Logger.INSTANCE.d(getTAG(), "onUpdateArguments");
        if (newArguments != null) {
            refreshNote((NoteLoadDataEntity) newArguments.getSerializable("entity"), false);
        } else {
            Logger.INSTANCE.i(getTAG(), "onUpdateArguments,newArguments is null!!!!");
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        setExtraHorizontalPaddingEnable(true);
        PadMindNoteActionBarController padMindNoteActionBarController = new PadMindNoteActionBarController(this, this.mEditCallback);
        this.actionBarController = padMindNoteActionBarController;
        Intrinsics.checkNotNull(padMindNoteActionBarController);
        Theme theme = ResourceManager.getTheme(0);
        Intrinsics.checkNotNullExpressionValue(theme, "getTheme(...)");
        padMindNoteActionBarController.updateStyle(theme);
        updateHomeButtonState(this.mScreenSpec);
        this.mIsNaviShow = Navigator.get(this).isContentOpen();
        PadMindNoteActionBarController padMindNoteActionBarController2 = this.actionBarController;
        Intrinsics.checkNotNull(padMindNoteActionBarController2);
        padMindNoteActionBarController2.setNaviState(this.mIsNaviShow);
        if (getActivity() != null) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            updateSoftInputMode(fragmentActivityRequireActivity);
        }
        View view2 = getView();
        if (view2 != null) {
            view2.setBackgroundColor(requireView().getResources().getColor(R.color.pad_content_actionbar_bg_color, null));
        }
        initActionBar();
        if (getActionBar() != null) {
            PadMindNoteActionBarController padMindNoteActionBarController3 = this.actionBarController;
            Intrinsics.checkNotNull(padMindNoteActionBarController3);
            ActionBar actionBar = getActionBar();
            Intrinsics.checkNotNull(actionBar);
            View customView = actionBar.getCustomView();
            Intrinsics.checkNotNullExpressionValue(customView, "getCustomView(...)");
            padMindNoteActionBarController3.initializeView(customView);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FrameConstant.ACTION_NOTE_DATA_UPDATE);
        this.mLocalBroadcastManager.registerReceiver(this.mDataUpdateTaskReceiver, intentFilter);
        if (!handleInstanceState(savedInstanceState)) {
            onUpdateArguments(getArguments());
        }
        HomeViewModel homeViewModel = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel);
        PadWebMindNoteFragment padWebMindNoteFragment = this;
        homeViewModel.getKeyBoardShowStatus().observe(padWebMindNoteFragment, new PadWebMindNoteFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PadWebMindNoteFragment.onViewInflated$lambda$2(this.f$0, ((Boolean) obj).booleanValue());
            }
        }));
        HomeViewModel homeViewModel2 = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel2);
        homeViewModel2.getKeyBoardForPadding().observe(padWebMindNoteFragment, new PadWebMindNoteFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PadWebMindNoteFragment.onViewInflated$lambda$3(this.f$0, ((Integer) obj).intValue());
            }
        }));
        HomeViewModel homeViewModel3 = this.homeViewModel;
        Intrinsics.checkNotNull(homeViewModel3);
        homeViewModel3.getCurrentItemLiveData().observe(padWebMindNoteFragment, new PadWebMindNoteFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PadWebMindNoteFragment.onViewInflated$lambda$4(this.f$0, ((Integer) obj).intValue());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onViewInflated$lambda$2(PadWebMindNoteFragment padWebMindNoteFragment, boolean z) {
        padWebMindNoteFragment.keyBoardShowStatusChange(z);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onViewInflated$lambda$3(PadWebMindNoteFragment padWebMindNoteFragment, int i) {
        padWebMindNoteFragment.mWebView.loadUrl("javascript:keyBoardShowStatusChange(" + padWebMindNoteFragment.mIsKeyboardVisible + ")");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onViewInflated$lambda$4(PadWebMindNoteFragment padWebMindNoteFragment, int i) {
        PadMindNoteActionBarController padMindNoteActionBarController = padWebMindNoteFragment.actionBarController;
        if (padMindNoteActionBarController != null && i == 1) {
            Intrinsics.checkNotNull(padMindNoteActionBarController);
            padMindNoteActionBarController.showOrHideNaviState(false);
        }
        return Unit.INSTANCE;
    }

    private final void keyBoardShowStatusChange(boolean show) {
        this.mIsKeyboardVisible = show;
        if (show) {
            showToolbar(true);
        } else {
            showToolbar(false);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void initWebView() {
        super.initWebView();
        this.mWebView.setBackgroundColor(requireActivity().getResources().getColor(R.color.v12_mind_note_webview_background));
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected BaseMindNoteBridgeInterface<?> createBridgeInterFace() {
        return new PadMindNoteBridgeInterface(this);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public int getUiMode() {
        if (this.mEnableChangeUiMode) {
            return this.mUiMode;
        }
        return 1;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public int getEditMarginTopInOverViewMode() {
        if (isAdded()) {
            return requireContext().getResources().getDimensionPixelSize(R.dimen.pad_mind_note_overview_mode_edit_margin_top);
        }
        return 0;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void preHandleIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.preHandleIntent(intent);
        this.mIsFromPrivate = this.mFolderId == -4;
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void setWorkingNote(WorkingNote workingNote, boolean forceEdit) {
        Intrinsics.checkNotNullParameter(workingNote, "workingNote");
        if (isAdded()) {
            Logger logger = Logger.INSTANCE;
            String simpleName = getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
            logger.d(simpleName, "setWorkingNote mWorkingNote " + workingNote);
            this.mWorkingNote = workingNote;
            WorkingNote workingNote2 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote2);
            workingNote2.setWorkingNoteType(NoteModel.NoteType.TYPE_MIND);
            WorkingNote workingNote3 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote3);
            workingNote3.addOnSettingStatusChangedListener(this);
            WorkingNote workingNote4 = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote4);
            if (workingNote4.isHidden()) {
                requireActivity().getWindow().addFlags(8192);
            } else {
                requireActivity().getWindow().clearFlags(8192);
            }
            onViewModeChanged(this.mMindContentType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean handleBackPress() {
        Logger.INSTANCE.d(getTAG(), "handleBackPress");
        if (this.mIsKeyboardVisible) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            Utils.hideSoftInput(fragmentActivityRequireActivity);
        }
        onBackPressed();
        FragmentActivity fragmentActivityRequireActivity2 = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity2, "null cannot be cast to non-null type com.miui.notes.ui.NotesListActivity");
        NotesListActivity notesListActivity = (NotesListActivity) fragmentActivityRequireActivity2;
        if (notesListActivity.getNavigator().getNavigationMode() == Navigator.Mode.C && notesListActivity.getNavigator().isContentOpen()) {
            handleBack();
            Logger.INSTANCE.d(getTAG(), "handleBackPress, content pop");
            return true;
        }
        Logger.INSTANCE.i(getTAG(), "handleBackPress, content  not open,mode:" + notesListActivity.getNavigator().getNavigationMode());
        return false;
    }

    public final BaseEditorActionBarController.EditCallback getMEditCallback() {
        return this.mEditCallback;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.mind_note_edit_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Logger logger = Logger.INSTANCE;
        String tag = getTAG();
        boolean zIsPadDevice = RomUtils.isPadDevice();
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        logger.d(tag, "onPrepareOptionsMenu isPadDevice: " + zIsPadDevice + " isMiuiWidgetSupported(requireContext()) " + UIUtils.isMiuiWidgetSupported(contextRequireContext) + " mWorkingNote " + this.mWorkingNote);
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext(...)");
        if (!UIUtils.isMiuiWidgetSupported(contextRequireContext2) || RomUtils.isPadDevice()) {
            menu.findItem(R.id.send_to_desktop).setVisible(false);
        } else {
            menu.findItem(R.id.send_to_desktop).setVisible(true);
        }
        if (this.mWorkingNote == null) {
            return;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        if (workingNote.isHidden()) {
            menu.findItem(R.id.move_to).setVisible(false);
        } else {
            menu.findItem(R.id.move_to).setVisible(true);
        }
        PadMindNoteActionBarController padMindNoteActionBarController = this.actionBarController;
        Intrinsics.checkNotNull(padMindNoteActionBarController);
        padMindNoteActionBarController.updateMoreMenu(menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        Logger.INSTANCE.d(getTAG(), "onBackPressed");
        Utils.hideSoftInput(getView());
        onViewMode();
        killSaveTask();
        return false;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        switch (item.getItemId()) {
            case R.id.delete /* 2131362396 */:
                requestDeleteNote();
                break;
            case R.id.move_to /* 2131362990 */:
                if (this.mWorkingNote != null) {
                    Logger.INSTANCE.d(getTAG(), "move to");
                    startQueryFolder();
                }
                break;
            case R.id.save_as_pic /* 2131363337 */:
                saveAsImage();
                break;
            case R.id.send_to_desktop /* 2131363407 */:
                sendToDesktop();
                break;
            case R.id.share /* 2131363452 */:
                LiveDataEventBus.with(LiveDataEventBus.SEARCH_CLEAR_FOCUS).setValue(true);
                share();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        deleteEmptyNote();
        killSaveTask();
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
        this.mIsKeyboardVisible = false;
        showToolbar(false);
        if (this.mPasswordController != null) {
            this.mPasswordController.destroy();
        }
    }

    private final void deleteEmptyNote() {
        if (isDataEmpty()) {
            Context context = getContext();
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            NoteStore.delete(context, new long[]{workingNote.getNoteId()});
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void exitWithoutSave() {
        if (isAdded()) {
            Logger.INSTANCE.d(getTAG(), "exitWithoutSave");
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onEnterActionMode() {
        super.onEnterActionMode();
        this.mWebView.clearFocus();
        onViewMode();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onExitActionMode() {
        super.onExitActionMode();
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean responsiveLayoutStateChanged) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(screenSpec, "screenSpec");
        super.onResponsiveLayout(configuration, screenSpec, responsiveLayoutStateChanged);
        this.mScreenStatus = UIUtils.getScreenStatus(getContext(), screenSpec);
        this.mScreenSpec = screenSpec;
        updateHomeButtonState(screenSpec);
        setNaviState(Navigator.get(this).isContentOpen());
        if (this.mScreenSpec.type == 1) {
            if (this.mIsDataPrepare && this.mEnableChangeUiMode) {
                changeUiMode(0);
                return;
            }
            return;
        }
        if (this.mEnableChangeUiMode) {
            changeUiMode(1);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        int[] screenSizeDp = DisplayUtils.getScreenSizeDp(getContext());
        if (screenSizeDp[0] > screenSizeDp[1]) {
            setScreenOrientationToWebView(2);
        } else {
            setScreenOrientationToWebView(1);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void onFoldChangeScreen() {
        super.onFoldChangeScreen();
        dismissImmersionMenu(false);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void onViewMode() {
        super.onViewMode();
        Logger.INSTANCE.d("RKeyboardHeightProvider", "onViewMode ");
        if (isAdded()) {
            requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment.onViewMode.1
                @Override // java.lang.Runnable
                public void run() {
                    if (PadWebMindNoteFragment.this.isAdded()) {
                        if (PadWebMindNoteFragment.this.getActionBarController() != null) {
                            PadMindNoteActionBarController actionBarController = PadWebMindNoteFragment.this.getActionBarController();
                            Intrinsics.checkNotNull(actionBarController);
                            actionBarController.onViewMode();
                        }
                        if (!RomUtils.isFoldDevice() || PadWebMindNoteFragment.this.mWebView == null) {
                            return;
                        }
                        PadWebMindNoteFragment.this.mWebView.clearFocus();
                    }
                }
            });
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void onEditMode() {
        super.onEditMode();
        if (isAdded()) {
            requireActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    PadWebMindNoteFragment.onEditMode$lambda$5(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onEditMode$lambda$5(PadWebMindNoteFragment padWebMindNoteFragment) {
        PadMindNoteActionBarController padMindNoteActionBarController = padWebMindNoteFragment.actionBarController;
        if (padMindNoteActionBarController != null) {
            Intrinsics.checkNotNull(padMindNoteActionBarController);
            padMindNoteActionBarController.onEditMode();
        }
    }

    protected final void showToolbar(boolean show) {
        PadMindNoteToolBarController padMindNoteToolBarController;
        if (this.mUiMode == 1 && (padMindNoteToolBarController = this.toolBarController) != null) {
            if (show) {
                Intrinsics.checkNotNull(padMindNoteToolBarController);
                padMindNoteToolBarController.show();
                HomeViewModel homeViewModel = this.homeViewModel;
                Intrinsics.checkNotNull(homeViewModel);
                homeViewModel.setEditStatus(true);
                return;
            }
            Intrinsics.checkNotNull(padMindNoteToolBarController);
            padMindNoteToolBarController.hide();
            HomeViewModel homeViewModel2 = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel2);
            homeViewModel2.setEditStatus(false);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void startToShareActivity() {
        if (TextUtils.isEmpty(this.mSharedBase64)) {
            return;
        }
        PadSnsEditActivity.Companion companion = PadSnsEditActivity.INSTANCE;
        ActivityResultLauncher<Intent> mToShareResult = this.mToShareResult;
        Intrinsics.checkNotNullExpressionValue(mToShareResult, "mToShareResult");
        companion.launchActivity(mToShareResult, getActivity(), "asd", "", 2, 0, 0L, 0.0f, this.mSharedBase64, this.mWorkingNote, null);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void onNoteDeleted(long noteId) {
        Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.toast_notes_delete_success, 0).show();
        if (this.mActionListener != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", noteId);
            FragmentActionListener fragmentActionListener = this.mActionListener;
            Intrinsics.checkNotNull(fragmentActionListener);
            fragmentActionListener.onAction(27, bundle);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isDataEmpty() {
        if (this.mWorkingNote == null) {
            return false;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        return workingNote.isEmpty();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void onSplitModeChanged(int newMode) {
        super.onSplitModeChanged(newMode);
        updateHomeButtonState(this.mScreenSpec);
        if (newMode == 0) {
            this.mIsShow = true;
            setNaviState(true);
        } else {
            if (newMode != 1) {
                if (newMode != 2) {
                    return;
                }
                this.mIsShow = true;
                setNaviState(false);
                return;
            }
            onKeyboardVisibleChanged(false);
            onViewMode();
            this.mIsShow = false;
            setNaviState(true);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void setNaviState(boolean show) {
        this.mIsNaviShow = show;
        PadMindNoteActionBarController padMindNoteActionBarController = this.actionBarController;
        if (padMindNoteActionBarController != null) {
            Intrinsics.checkNotNull(padMindNoteActionBarController);
            padMindNoteActionBarController.setNaviState(this.mIsNaviShow);
        }
    }

    private final void updateHomeButtonState(ScreenSpec screenSpec) {
        if (RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1) {
            PadMindNoteActionBarController padMindNoteActionBarController = this.actionBarController;
            if (padMindNoteActionBarController != null) {
                Intrinsics.checkNotNull(padMindNoteActionBarController);
                padMindNoteActionBarController.setHomeButtonState(true);
                return;
            }
            return;
        }
        PadWebMindNoteFragment padWebMindNoteFragment = this;
        if (Navigator.get(padWebMindNoteFragment).getNavigationMode() == Navigator.Mode.C) {
            PadMindNoteActionBarController padMindNoteActionBarController2 = this.actionBarController;
            if (padMindNoteActionBarController2 != null) {
                Intrinsics.checkNotNull(padMindNoteActionBarController2);
                padMindNoteActionBarController2.setHomeButtonState(true);
                return;
            }
            return;
        }
        PadMindNoteActionBarController padMindNoteActionBarController3 = this.actionBarController;
        if (padMindNoteActionBarController3 != null) {
            Intrinsics.checkNotNull(padMindNoteActionBarController3);
            padMindNoteActionBarController3.setHomeButtonState(false);
            setNaviState(Navigator.get(padWebMindNoteFragment).isContentOpen());
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment, com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mIsFirstCreate = false;
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        Intrinsics.checkNotNull(onBackPressedCallback);
        onBackPressedCallback.remove();
    }

    protected final void initActionBar() {
        PadMindNoteActionBarController padMindNoteActionBarController = this.actionBarController;
        Intrinsics.checkNotNull(padMindNoteActionBarController);
        View viewOnInflateView = padMindNoteActionBarController.onInflateView(LayoutInflater.from(getContext()), (ViewGroup) getView(), null);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(viewOnInflateView, new androidx.appcompat.app.ActionBar.LayoutParams(-1, -1));
            getActivity();
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.webview_actionbar_bg));
            setImmersionMenuEnabled(true);
        }
    }

    private final void updateSoftInputMode(Activity activity) {
        if (UIUtils.isAboveAndroidR()) {
            if (activity.isInMultiWindowMode() && UIUtils.isSmallWindowMode(activity)) {
                activity.getWindow().setSoftInputMode(0);
                return;
            } else {
                activity.getWindow().setSoftInputMode(48);
                return;
            }
        }
        if (UIUtils.isInFullWindowGestureMode(activity)) {
            return;
        }
        activity.getWindow().setSoftInputMode(16);
    }

    protected final float getNoteEditorPadding() {
        return BaseNoteApp.INSTANCE.getInstance().getResources().getDimensionPixelSize(R.dimen.pad_note_edit_web_mind_content_horizontal_padding);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int extraHorizontalPadding) {
        super.onExtraPaddingChanged(extraHorizontalPadding);
        if (this.mWebView == null || this.mMindContentType != 1) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.mWebView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        int i = marginLayoutParams.leftMargin;
        int noteEditorPadding = (int) (extraHorizontalPadding + getNoteEditorPadding());
        if (i != noteEditorPadding) {
            marginLayoutParams.setMarginStart(noteEditorPadding);
            marginLayoutParams.setMarginEnd(noteEditorPadding);
            this.mWebView.setLayoutParams(marginLayoutParams);
        }
    }

    protected final void changeViewMode(int mode) {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.changeView()");
    }

    protected final void onViewModeChanged(int newMode) {
        this.mMindContentType = newMode;
        PadMindNoteActionBarController padMindNoteActionBarController = this.actionBarController;
        if (padMindNoteActionBarController != null) {
            Intrinsics.checkNotNull(padMindNoteActionBarController);
            padMindNoteActionBarController.setViewMode(newMode);
        }
        PadMindNoteToolBarController padMindNoteToolBarController = this.toolBarController;
        if (padMindNoteToolBarController != null) {
            Intrinsics.checkNotNull(padMindNoteToolBarController);
            padMindNoteToolBarController.setViewMode(newMode);
        }
        changeContentMargin();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void undo() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.undo()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void redo() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.redo()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addChildNode() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.addChild()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addBrotherNode() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.addSibling()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteNode() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.deleteNode()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void levelIncrease() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.overviewForward()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void levelReduce() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.overviewBackward()");
    }

    private final void saveAsImage() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.saveAsImage()");
    }

    private final void share() {
        this.mWebView.loadUrl("javascript:exposedFnsForTablet.share()");
    }

    private final void changeUiMode(int uiMode) {
        ActionBar actionBar;
        this.mUiMode = uiMode;
        this.mWebView.evaluateJavascript("javascript:changeUiMode(" + uiMode + ")", null);
        int i = this.mUiMode;
        if (i == 0) {
            ActionBar actionBar2 = getActionBar();
            if (actionBar2 != null) {
                actionBar2.hide();
                return;
            }
            return;
        }
        if (i != 1 || (actionBar = getActionBar()) == null) {
            return;
        }
        actionBar.show();
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    public void onUpdateViewState(boolean canSelect, boolean canQuery, boolean canPhrase, boolean canTranslate, String selectData, String contextData) {
        Intrinsics.checkNotNullParameter(selectData, "selectData");
        Intrinsics.checkNotNullParameter(contextData, "contextData");
        if (this.mWebView instanceof MindNoteWebView) {
            WebView webView = this.mWebView;
            Intrinsics.checkNotNull(webView, "null cannot be cast to non-null type com.miui.mindnoteeditor.MindNoteWebView");
            ((MindNoteWebView) webView).updateViewState(canSelect, canQuery, canPhrase, canTranslate, selectData, contextData);
        }
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment
    protected void doDeleteAndLoad() {
        PadMindNoteActionBarController padMindNoteActionBarController;
        final long noteId = getNoteId();
        if (this.mHandler != null && (padMindNoteActionBarController = this.actionBarController) != null) {
            Intrinsics.checkNotNull(padMindNoteActionBarController);
            if (padMindNoteActionBarController.getNaviState()) {
                PadMindNoteActionBarController padMindNoteActionBarController2 = this.actionBarController;
                Intrinsics.checkNotNull(padMindNoteActionBarController2);
                padMindNoteActionBarController2.showOrHideNaviState(true);
                this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PadWebMindNoteFragment.doDeleteAndLoad$lambda$6(this.f$0, noteId);
                    }
                }, 200L);
                return;
            }
        }
        this.noteOperationViewModel.delete(new long[]{noteId}, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doDeleteAndLoad$lambda$6(PadWebMindNoteFragment padWebMindNoteFragment, long j) {
        padWebMindNoteFragment.noteOperationViewModel.delete(new long[]{j}, false);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setInSearchMode(boolean inSearchMode) {
        super.setInSearchMode(inSearchMode);
        this.mIsInSearchMode = inSearchMode;
        if (inSearchMode) {
            return;
        }
        this.mSearchText = "";
        this.mHighLightKeyWords = null;
        setDataToWebView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeContentMargin() {
        if (this.mHandler == null || this.mWebView == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.mWebView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        final int noteEditorPadding = (int) getNoteEditorPadding();
        if (Navigator.get(this).isContentOpen() || this.mMindContentType == 1) {
            noteEditorPadding = (int) (getExtraHorizontalPadding() + getNoteEditorPadding());
        }
        if (marginLayoutParams.getMarginStart() == noteEditorPadding) {
            return;
        }
        this.mWebView.setVisibility(8);
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PadWebMindNoteFragment.changeContentMargin$lambda$7(marginLayoutParams, noteEditorPadding, this);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void changeContentMargin$lambda$7(ViewGroup.MarginLayoutParams marginLayoutParams, int i, PadWebMindNoteFragment padWebMindNoteFragment) {
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i);
        padWebMindNoteFragment.mWebView.setLayoutParams(marginLayoutParams);
        padWebMindNoteFragment.mWebView.setVisibility(0);
    }
}
