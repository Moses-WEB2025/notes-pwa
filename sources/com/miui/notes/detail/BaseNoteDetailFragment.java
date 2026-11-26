package com.miui.notes.detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.base.FragmentActionListener;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.softinputhelper.KeyboardListener;
import com.miui.notes.R;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.model.SearchViewModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.NotesListActivity;
import com.miui.richeditor.OnKeyDownAndShortCutListener;
import com.xiaomi.verificationsdk.internal.Constants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.Fragment;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorFragmentListener;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: compiled from: BaseNoteDetailFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0012\b&\u0018\u0000 \u00ad\u00012\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0004¬\u0001\u00ad\u0001B\u0007¢\u0006\u0004\b\u0005\u0010\u0006J\u001a\u0010D\u001a\u00020E2\b\u0010F\u001a\u0004\u0018\u00010G2\u0006\u0010H\u001a\u00020\u0010H$J\u0012\u0010I\u001a\u00020E2\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\u0010\u0010L\u001a\u00020E2\u0006\u0010M\u001a\u00020NH\u0016J\b\u0010O\u001a\u00020EH\u0016J\b\u0010P\u001a\u00020EH\u0016J\b\u0010Q\u001a\u00020EH\u0016J(\u0010R\u001a\u0004\u0018\u00010/2\b\u0010S\u001a\u0004\u0018\u00010T2\b\u0010U\u001a\u0004\u0018\u00010V2\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\u001a\u0010W\u001a\u00020E2\u0006\u0010X\u001a\u00020/2\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\b\u0010Y\u001a\u00020EH\u0014J\u0012\u0010Z\u001a\u00020E2\b\u0010[\u001a\u0004\u0018\u00010KH\u0016J\b\u0010\\\u001a\u00020EH\u0016J\b\u0010]\u001a\u00020EH\u0016J\u0012\u0010^\u001a\u00020E2\b\u0010_\u001a\u0004\u0018\u000104H\u0016J\u0010\u0010`\u001a\u00020E2\b\u0010a\u001a\u0004\u0018\u00010\u0012J\u0010\u0010b\u001a\u00020E2\b\u0010a\u001a\u0004\u0018\u00010\u0014J\u0014\u0010c\u001a\u0004\u0018\u00010\u00122\b\u0010a\u001a\u0004\u0018\u00010\u0012H\u0014J\u0010\u0010d\u001a\u00020E2\b\u0010F\u001a\u0004\u0018\u00010GJ\b\u0010e\u001a\u00020EH\u0002J\u001a\u0010f\u001a\u00020E2\u0006\u0010g\u001a\u00020\u00162\b\u0010h\u001a\u0004\u0018\u00010KH\u0016J\u0010\u0010i\u001a\u00020E2\u0006\u0010j\u001a\u00020\u0010H\u0016J\u000e\u0010k\u001a\u00020E2\u0006\u0010H\u001a\u00020\u0010J\u0010\u0010m\u001a\u00020E2\u0006\u0010n\u001a\u00020\u0010H\u0016J\b\u0010o\u001a\u00020EH\u0016J\b\u0010p\u001a\u00020EH\u0016J\b\u0010q\u001a\u00020EH\u0016J\b\u0010r\u001a\u00020EH\u0016J\b\u0010s\u001a\u00020\u0010H\u0016J\u0010\u0010u\u001a\u00020E2\u0006\u0010v\u001a\u00020\u0010H\u0014J\u0010\u0010w\u001a\u00020E2\u0006\u0010x\u001a\u00020\u0016H\u0016J\u0010\u0010y\u001a\u00020E2\u0006\u0010z\u001a\u00020\u0010H\u0016J\b\u0010{\u001a\u00020EH\u0016J\u001a\u0010|\u001a\u00020\u00102\u0006\u0010}\u001a\u00020\u00162\b\u0010~\u001a\u0004\u0018\u00010\u007fH\u0016J\u001b\u0010\u0082\u0001\u001a\u00020E2\u0007\u0010\u0083\u0001\u001a\u00020%2\u0007\u0010\u0084\u0001\u001a\u00020\u0010H\u0016J\u001b\u0010\u0087\u0001\u001a\u00020E2\u0006\u0010X\u001a\u00020/2\b\u0010J\u001a\u0004\u0018\u00010KH\u0016J\t\u0010\u0088\u0001\u001a\u00020\u0000H\u0016J\f\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u0001H\u0016J\u0014\u0010\u008b\u0001\u001a\u00020E2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010\fH\u0016J\t\u0010\u008d\u0001\u001a\u00020\u0010H\u0016J\t\u0010\u008e\u0001\u001a\u00020\u0010H\u0016J\t\u0010\u008f\u0001\u001a\u0004\u0018\u00010\fJ\u0011\u0010\u0090\u0001\u001a\u00020E2\u0006\u0010n\u001a\u00020\u0010H\u0016J\t\u0010\u0091\u0001\u001a\u00020EH\u0016J\t\u0010\u0092\u0001\u001a\u00020EH\u0016J\u0013\u0010\u0093\u0001\u001a\u00020E2\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0016J\t\u0010\u0096\u0001\u001a\u00020EH\u0014J\t\u0010\u0097\u0001\u001a\u00020EH\u0016J\u0013\u0010\u0098\u0001\u001a\u00020E2\b\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0004J\u000e\u0010\u009b\u0001\u001a\u00020\b*\u00030\u009c\u0001H\u0002J\u001e\u0010 \u0001\u001a\u00020E2\n\u0010¡\u0001\u001a\u0005\u0018\u00010\u009c\u00012\u0007\u0010¢\u0001\u001a\u00020%H\u0002J\u0013\u0010£\u0001\u001a\u00020E2\b\u0010¤\u0001\u001a\u00030\u009c\u0001H\u0014J%\u0010¥\u0001\u001a\u00020E2\u0007\u0010¢\u0001\u001a\u00020%2\b\u0010¤\u0001\u001a\u00030\u009c\u00012\u0007\u0010¦\u0001\u001a\u00020\bH\u0014J\t\u0010§\u0001\u001a\u00020EH\u0014J\t\u0010¨\u0001\u001a\u00020EH\u0016J\t\u0010©\u0001\u001a\u00020EH\u0016J\t\u0010ª\u0001\u001a\u00020EH\u0016J\u0007\u0010«\u0001\u001a\u00020ER\u0014\u0010\u0007\u001a\u00020\bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00128\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00148\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u00168\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00020\u00188\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0010X\u0094\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\u0012\u0010!\u001a\u00020\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\"\u001a\u00020\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u00020\u00168\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010$\u001a\u00020%8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010&\u001a\u00020%8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\u0004\u0018\u00010\b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R*\u0010(\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010)j\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u0001`*8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001a\u0010+\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001b\"\u0004\b-\u0010\u001dR\u0014\u0010.\u001a\u0004\u0018\u00010/8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\u0004\u0018\u0001018\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u00102\u001a\f\u0012\u0006\u0012\u0004\u0018\u000104\u0018\u0001038\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u00105\u001a\f\u0012\u0006\u0012\u0004\u0018\u000104\u0018\u0001038\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u00106\u001a\u0004\u0018\u0001078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u00108\u001a\u0004\u0018\u0001098\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010:\u001a\u00020\u00108\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010=\u001a\u0004\u0018\u00010>X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u0012\u0010C\u001a\u00020\u00168\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0011\u0010l\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\bl\u0010\u001bR\u0014\u0010t\u001a\u00020\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\bt\u0010\u001bR\u0016\u0010\u0080\u0001\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0080\u0001\u0010\u001bR\u0016\u0010\u0081\u0001\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u0081\u0001\u0010\u001bR\u001d\u0010\u0085\u0001\u001a\u00020\u0010X\u0094\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0085\u0001\u0010\u001b\"\u0005\b\u0086\u0001\u0010\u001dR\u0017\u0010\u009d\u0001\u001a\u00020%8TX\u0094\u0004¢\u0006\b\u001a\u0006\b\u009e\u0001\u0010\u009f\u0001¨\u0006®\u0001"}, d2 = {"Lcom/miui/notes/detail/BaseNoteDetailFragment;", "Lmiuix/appcompat/app/Fragment;", "Lcom/miui/common/tool/softinputhelper/KeyboardListener;", "Lcom/miui/common/base/DialogManagerInterface;", "Lmiuix/navigator/NavigatorFragmentListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "mManagedDialog", "Landroid/app/Dialog;", "mEditText", "Landroid/widget/EditText;", "lastFoldStatus", "", "mOutKeyListenerWrapper", "Lcom/miui/richeditor/OnKeyDownAndShortCutListener;", "mActionListener", "Lcom/miui/common/base/FragmentActionListener;", "mScreenStatus", "", "mScreenSpec", "Lmiuix/responsive/map/ScreenSpec;", "mInSearchMode", "isNeedListenKeyboard", "()Z", "setNeedListenKeyboard", "(Z)V", "mIsKeyboardVisible", "getMIsKeyboardVisible", "setMIsKeyboardVisible", "mIsShow", "mIsFromRebuild", "mSplitMode", "mNoteId", "", "mFolderId", "mSearchText", "mHighLightKeyWords", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "mIsFromPc", "getMIsFromPc", "setMIsFromPc", "rootView", "Landroid/view/View;", "detailViewModel", "Lcom/miui/notes/detail/DetailViewModel;", "toChooseFolderResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "mToShareResult", "mWorkingNote", "Lcom/miui/notes/model/WorkingNote;", "homeViewModel", "Lcom/miui/notes/home/viewmodel/HomeViewModel;", "mNewDoodle", "searchViewModel", "Lcom/miui/notes/model/SearchViewModel;", "mainHandle", "Landroid/os/Handler;", "getMainHandle", "()Landroid/os/Handler;", "setMainHandle", "(Landroid/os/Handler;)V", "mOriginGridMode", "refreshNote", "", "noteEntity", "Lcom/miui/common/base/NoteLoadDataEntity;", "isFromPc", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onAttach", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onResume", "onStart", "onStop", "onInflateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "initBaseResultLauncher", "onActivityCreated", "b", "onDestroyView", "onDestroy", "onNewIntent", "intent", "setOutKeyListener", "listener", "setActionListener", "createKeyListenerWrapper", "changeNote", "clearBaseData", "notifyAction", "actionId", "bundle", "setForAccessibility", "isImportant", "setmIsFromPc", "isReadOnlyMode", "onKeyboardVisibleChanged", "visible", "onKeyboardChangeEnd", "updateWorkingNoteFromOuter", "onEnterActionMode", "onExitActionMode", "onBackPressed", "isContentShow", "setNaviState", "show", "onSplitModeChanged", "newMode", "setInSearchMode", "inSearchMode", "clearFocus", "onKeyShortcut", "keyCode", "event", "Landroid/view/KeyEvent;", "isDataEmpty", "isBlankViewShow", "enterEditMode", "delay", Constants.FORCE, "isSupportNavigator", "setSupportNavigator", "onViewInflated", "getThisFragment", "getThisActivity", "Landroid/app/Activity;", "manageDialog", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "dismissDialog", "isDialogShowing", "getManagedDialog", "onVisibilityChanged", "onDetach", "onPause", "onConfigurationChanged", "configuration", "Landroid/content/res/Configuration;", "onFoldChangeScreen", "startQueryFolder", "showFolderChooser", "cursor", "Landroid/database/Cursor;", "toStr", "", NotifyUtils.KEY_NOTE_ID, "getNoteId", "()J", "openFolderChooser", "checkIds", "folderId", "createFolderAndMoveItems", "checkedIds", "moveCheckedItems", "folderName", "refreshFolderInfo", "showSoftInput", "setWindowsOnFocus", "deleteEmptyPcNote", "handleBack", "NoteLoaderCallbacks", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseNoteDetailFragment extends Fragment implements KeyboardListener, DialogManagerInterface, NavigatorFragmentListener {
    protected static final String BUNDLE_KEY_NEW_DOODLE = "new_doodle";
    protected static final String BUNDLE_KEY_USER_AI_QUERY = "user_ai_query";
    protected static final String BUNDLE_KEY_USER_QUERY = "user_query";
    public static final String INTENT_DATA_ENTITY = "entity";
    protected static final int REQUEST_CODE_QUERY_FOLDER = 2;
    protected static final int REQUEST_CODE_UPDATE_FOLDER = 3;
    protected DetailViewModel detailViewModel;
    protected HomeViewModel homeViewModel;
    protected FragmentActionListener mActionListener;
    private EditText mEditText;
    protected long mFolderId;
    protected ArrayList<String> mHighLightKeyWords;
    protected boolean mInSearchMode;
    private boolean mIsFromPc;
    protected boolean mIsFromRebuild;
    private boolean mIsKeyboardVisible;
    private Dialog mManagedDialog;
    protected boolean mNewDoodle;
    protected int mOriginGridMode;
    protected OnKeyDownAndShortCutListener mOutKeyListenerWrapper;
    protected int mSplitMode;
    protected ActivityResultLauncher<Intent> mToShareResult;
    protected volatile WorkingNote mWorkingNote;
    private Handler mainHandle;
    protected View rootView;
    private SearchViewModel searchViewModel;
    protected ActivityResultLauncher<Intent> toChooseFolderResult;
    private final String TAG = "BaseNoteDetailFragment";
    private boolean lastFoldStatus = true;
    protected int mScreenStatus = 4103;
    protected ScreenSpec mScreenSpec = new ScreenSpec();
    private boolean isNeedListenKeyboard = true;
    protected boolean mIsShow = true;
    protected long mNoteId = -1;
    protected String mSearchText = "";
    private boolean isSupportNavigator = true;

    public void clearFocus() {
    }

    protected OnKeyDownAndShortCutListener createKeyListenerWrapper(OnKeyDownAndShortCutListener listener) {
        return null;
    }

    public void enterEditMode(long delay, boolean force) {
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public BaseNoteDetailFragment getThisFragment() {
        return this;
    }

    protected void initBaseResultLauncher() {
    }

    public boolean isBlankViewShow() {
        return false;
    }

    public void onEnterActionMode() {
    }

    public void onExitActionMode() {
    }

    protected void onFoldChangeScreen() {
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // com.miui.common.tool.softinputhelper.KeyboardListener
    public void onKeyboardChangeEnd() {
    }

    protected abstract void refreshNote(NoteLoadDataEntity noteEntity, boolean isFromPc);

    public void setForAccessibility(boolean isImportant) {
    }

    protected void setNaviState(boolean show) {
    }

    public void setWindowsOnFocus() {
    }

    public void showSoftInput() {
    }

    public void updateWorkingNoteFromOuter() {
    }

    public String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: renamed from: isNeedListenKeyboard, reason: from getter */
    public final boolean getIsNeedListenKeyboard() {
        return this.isNeedListenKeyboard;
    }

    public final void setNeedListenKeyboard(boolean z) {
        this.isNeedListenKeyboard = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean getMIsKeyboardVisible() {
        return this.mIsKeyboardVisible;
    }

    protected void setMIsKeyboardVisible(boolean z) {
        this.mIsKeyboardVisible = z;
    }

    protected final boolean getMIsFromPc() {
        return this.mIsFromPc;
    }

    protected final void setMIsFromPc(boolean z) {
        this.mIsFromPc = z;
    }

    protected final Handler getMainHandle() {
        return this.mainHandle;
    }

    protected final void setMainHandle(Handler handler) {
        this.mainHandle = handler;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.INSTANCE.d(getTAG(), "onCreate");
        Context context = getContext();
        if (context != null) {
            Looper mainLooper = context.getMainLooper();
            Intrinsics.checkNotNullExpressionValue(mainLooper, "getMainLooper(...)");
            this.mainHandle = new MainHandler(mainLooper, new WeakReference(this));
        }
        if (getArguments() != null) {
            this.mNoteId = requireArguments().getLong("data_id", -1L);
            Logger.INSTANCE.d(getTAG(), "set mNoteId:" + this.mNoteId);
            this.mSearchText = requireArguments().getString(NoteLoadDataEntity.SEARCH_TEXT, "");
        }
        this.mIsFromRebuild = savedInstanceState != null;
        this.lastFoldStatus = RomUtils.isFoldDevice() && UIUtils.isFoldInternalScreen(getContext());
        initBaseResultLauncher();
        this.detailViewModel = (DetailViewModel) new ViewModelProvider(this).get(DetailViewModel.class);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        this.homeViewModel = (HomeViewModel) new ViewModelProvider(fragmentActivityRequireActivity).get(HomeViewModel.class);
        FragmentActivity fragmentActivityRequireActivity2 = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity2, "requireActivity(...)");
        this.searchViewModel = (SearchViewModel) new ViewModelProvider(fragmentActivityRequireActivity2).get(SearchViewModel.class);
        this.mOriginGridMode = PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        Logger.INSTANCE.d(getTAG(), " onAttach");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.d(getTAG(), " onResume");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Logger.INSTANCE.d(getTAG(), " onStart");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logger.INSTANCE.d(getTAG(), " onStop");
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.INSTANCE.d(getTAG(), " onInflateView");
        return super.onInflateView(inflater, container, savedInstanceState);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        Logger.INSTANCE.d(getTAG(), " onViewCreated");
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        Logger.INSTANCE.d(getTAG(), " onActivityCreated");
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Logger.INSTANCE.d(getTAG(), " onDestroyView");
        clearBaseData();
        if (RomUtils.isPadMode() && getIsSupportNavigator()) {
            Navigator.get(this).removeNavigatorFragmentListener(this);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Logger.INSTANCE.d(getTAG(), " onDestroy");
        super.onDestroy();
        Handler handler = this.mainHandle;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void onNewIntent(Intent intent) {
        Logger.INSTANCE.d(getTAG(), "onNewIntent " + intent);
    }

    public final void setOutKeyListener(OnKeyDownAndShortCutListener listener) {
        this.mOutKeyListenerWrapper = createKeyListenerWrapper(listener);
    }

    public final void setActionListener(FragmentActionListener listener) {
        this.mActionListener = listener;
    }

    public final void changeNote(NoteLoadDataEntity noteEntity) {
        this.mNoteId = noteEntity != null ? noteEntity.getNoteId() : -1L;
        Logger.INSTANCE.d(getTAG(), "changeNote/set mNoteId:" + this.mNoteId);
        refreshNote(noteEntity, this.mIsFromPc);
        this.mIsFromPc = false;
    }

    private final void clearBaseData() {
        Logger.INSTANCE.d(getTAG(), "clearBaseData/set mNoteId:" + this.mNoteId);
        this.mNoteId = -1L;
        this.mSearchText = "";
        this.mHighLightKeyWords = null;
    }

    public void notifyAction(int actionId, Bundle bundle) {
        FragmentActionListener fragmentActionListener = this.mActionListener;
        if (fragmentActionListener != null) {
            Intrinsics.checkNotNull(fragmentActionListener);
            fragmentActionListener.onAction(actionId, bundle);
        }
    }

    public final void setmIsFromPc(boolean isFromPc) {
        this.mIsFromPc = isFromPc;
    }

    public final boolean isReadOnlyMode() {
        return this.mFolderId == -3;
    }

    public void onKeyboardVisibleChanged(boolean visible) {
        setMIsKeyboardVisible(visible);
    }

    public boolean onBackPressed() {
        Logger.INSTANCE.d(getTAG(), " onBackPressed");
        return false;
    }

    protected boolean isContentShow() {
        return this.mSplitMode != 1;
    }

    public void onSplitModeChanged(int newMode) {
        this.mSplitMode = newMode;
    }

    public void setInSearchMode(boolean inSearchMode) {
        this.mInSearchMode = inSearchMode;
    }

    public boolean isDataEmpty() {
        if (this.mWorkingNote == null) {
            return false;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        return workingNote.isEmpty();
    }

    /* JADX INFO: renamed from: isSupportNavigator, reason: from getter */
    protected boolean getIsSupportNavigator() {
        return this.isSupportNavigator;
    }

    protected void setSupportNavigator(boolean z) {
        this.isSupportNavigator = z;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        Logger.INSTANCE.d(getTAG(), " onViewInflated");
        this.rootView = view;
        if (RomUtils.isPadMode() && getIsSupportNavigator()) {
            Navigator.get(this).addNavigatorFragmentListener(this);
            HomeViewModel homeViewModel = this.homeViewModel;
            Intrinsics.checkNotNull(homeViewModel);
            homeViewModel.getSearchMode().observe(this, new BaseNoteDetailFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.notes.detail.BaseNoteDetailFragment$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return BaseNoteDetailFragment.onViewInflated$lambda$1(this.f$0, (Boolean) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onViewInflated$lambda$1(BaseNoteDetailFragment baseNoteDetailFragment, Boolean bool) {
        Intrinsics.checkNotNull(bool);
        baseNoteDetailFragment.setInSearchMode(bool.booleanValue());
        SearchViewModel searchViewModel = baseNoteDetailFragment.searchViewModel;
        Intrinsics.checkNotNull(searchViewModel);
        searchViewModel.setData(bool.booleanValue());
        return Unit.INSTANCE;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return getActivity();
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
        this.mManagedDialog = dialog;
        this.mEditText = dialog != null ? (EditText) dialog.findViewById(R.id.et_folder_name) : null;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        if (!isDialogShowing()) {
            return false;
        }
        Dialog dialog = this.mManagedDialog;
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
        this.mManagedDialog = null;
        return true;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        Dialog dialog = this.mManagedDialog;
        if (dialog == null) {
            return false;
        }
        Intrinsics.checkNotNull(dialog);
        return dialog.isShowing();
    }

    /* JADX INFO: renamed from: getManagedDialog, reason: from getter */
    public final Dialog getMManagedDialog() {
        return this.mManagedDialog;
    }

    @Override // miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean visible) {
        Dialog dialog;
        super.onVisibilityChanged(visible);
        if (visible || (dialog = this.mManagedDialog) == null) {
            return;
        }
        Intrinsics.checkNotNull(dialog);
        dialog.dismiss();
        this.mManagedDialog = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        Logger.INSTANCE.d(getTAG(), "onDetach");
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            dialog.dismiss();
            this.mManagedDialog = null;
        }
        super.onDetach();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        EditText editText;
        super.onPause();
        Logger.INSTANCE.d(getTAG(), "onPause");
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            if (!dialog.isShowing() || (editText = this.mEditText) == null) {
                return;
            }
            Intrinsics.checkNotNull(editText);
            editText.clearFocus();
            Utils.hideSoftInput(this.mEditText);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.d(getTAG(), "onConfigurationChanged");
        if (!RomUtils.isFoldDevice() || getContext() == null || this.lastFoldStatus == UIUtils.isFoldInternalScreen(getContext())) {
            return;
        }
        this.lastFoldStatus = UIUtils.isFoldInternalScreen(getContext());
        onFoldChangeScreen();
    }

    /* JADX WARN: Code duplicated, block: B:8:0x001a  */
    public void startQueryFolder() {
        long folderId;
        if (this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            if (workingNote.getIsInvalid()) {
                folderId = 0;
            } else {
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                folderId = workingNote2.getFolderId();
            }
        } else {
            folderId = 0;
        }
        this.mFolderId = folderId;
        FolderStore folderStore = FolderStore.INSTANCE;
        LoaderManager loaderManager = getLoaderManager();
        Intrinsics.checkNotNullExpressionValue(loaderManager, "getLoaderManager(...)");
        folderStore.query(loaderManager, 0, 2, new NoteLoaderCallbacks(), "_id <> " + folderId);
    }

    /* JADX INFO: compiled from: BaseNoteDetailFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016J\u0018\u0010\n\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007H\u0016¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/detail/BaseNoteDetailFragment$NoteLoaderCallbacks;", "Lcom/miui/notes/tool/util/CursorLoaderBuilder$DefaultLoaderCallbacks;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/detail/BaseNoteDetailFragment;)V", "onLoadFinished", "", "loader", "Landroidx/loader/content/Loader;", "Landroid/database/Cursor;", "data", "onLoaderReset", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public final class NoteLoaderCallbacks extends CursorLoaderBuilder.DefaultLoaderCallbacks {
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoaderReset(Loader<Cursor> loader) {
            Intrinsics.checkNotNullParameter(loader, "loader");
        }

        public NoteLoaderCallbacks() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Intrinsics.checkNotNullParameter(loader, "loader");
            if (loader.getId() == 2) {
                Logger.INSTANCE.d(BaseNoteDetailFragment.this.getTAG(), "onLoadFinished");
                BaseNoteDetailFragment baseNoteDetailFragment = BaseNoteDetailFragment.this;
                Intrinsics.checkNotNull(data);
                baseNoteDetailFragment.showFolderChooser(data);
            }
        }
    }

    protected final void showFolderChooser(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        long[] jArr = {getNoteId()};
        Logger.INSTANCE.d(getTAG(), "showFolderChooser,ids: " + toStr(jArr));
        if (cursor.getCount() > 0 || this.mFolderId > 0) {
            openFolderChooser(jArr, this.mFolderId);
        } else {
            createFolderAndMoveItems(jArr);
        }
        cursor.close();
        getLoaderManager().destroyLoader(2);
    }

    private final String toStr(long[] jArr) {
        StringBuilder sb = new StringBuilder();
        LongIterator it = ArrayIteratorsKt.iterator(jArr);
        while (it.hasNext()) {
            sb.append("," + it.next().longValue());
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    protected long getNoteId() {
        if (this.mWorkingNote == null) {
            return -1L;
        }
        WorkingNote workingNote = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        if (workingNote.getIsInvalid()) {
            return -1L;
        }
        WorkingNote workingNote2 = this.mWorkingNote;
        Intrinsics.checkNotNull(workingNote2);
        return workingNote2.getNoteId();
    }

    private final void openFolderChooser(long[] checkIds, long folderId) {
        Logger.INSTANCE.d(getTAG(), "openFolderChooser  " + (checkIds != null ? toStr(checkIds) : null) + "-> " + folderId);
        if (getActivity() != null) {
            Utils.hideSoftInput(getView());
            ActivityResultLauncher<Intent> activityResultLauncher = this.toChooseFolderResult;
            Intrinsics.checkNotNull(activityResultLauncher);
            ChooserFolderDialogActivity.INSTANCE.openForResult(this, activityResultLauncher, checkIds, folderId);
        }
    }

    protected void createFolderAndMoveItems(final long[] checkedIds) {
        Intrinsics.checkNotNullParameter(checkedIds, "checkedIds");
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(fragmentActivityRequireActivity);
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.detail.BaseNoteDetailFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BaseNoteDetailFragment.createFolderAndMoveItems$lambda$3(this.f$0, checkedIds, dialogInterface);
            }
        });
        if (getActivity() == null || requireActivity().isFinishing()) {
            return;
        }
        createFolderDialog.show();
        manageDialog(createFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFolderAndMoveItems$lambda$3(BaseNoteDetailFragment baseNoteDetailFragment, long[] jArr, DialogInterface dialogInterface) {
        Intrinsics.checkNotNull(dialogInterface, "null cannot be cast to non-null type com.miui.notes.feature.folder.CreateFolderDialog");
        CreateFolderDialog createFolderDialog = (CreateFolderDialog) dialogInterface;
        if (createFolderDialog.getFolderId() > 0) {
            baseNoteDetailFragment.refreshFolderInfo();
            long folderId = createFolderDialog.getFolderId();
            String folderName = createFolderDialog.getFolderName();
            Intrinsics.checkNotNull(folderName);
            baseNoteDetailFragment.moveCheckedItems(folderId, jArr, folderName);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void moveCheckedItems(long folderId, long[] checkedIds, String folderName) {
        Intrinsics.checkNotNullParameter(checkedIds, "checkedIds");
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        if (this.mWorkingNote != null) {
            this.mFolderId = folderId;
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            workingNote.setFolderId(folderId);
            DetailViewModel detailViewModel = this.detailViewModel;
            Intrinsics.checkNotNull(detailViewModel);
            detailViewModel.move(checkedIds, folderId, folderName);
        }
    }

    protected void refreshFolderInfo() {
        FolderStore folderStore = FolderStore.INSTANCE;
        LoaderManager loaderManager = getLoaderManager();
        Intrinsics.checkNotNullExpressionValue(loaderManager, "getLoaderManager(...)");
        folderStore.query(loaderManager, 1, 3, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.detail.BaseNoteDetailFragment.refreshFolderInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                FolderStore.updateEnableNewDefaultFolderName(BaseNoteApp.INSTANCE.getInstance(), cursor);
                if (cursor != null) {
                    cursor.close();
                }
                BaseNoteDetailFragment.this.getLoaderManager().destroyLoader(3);
            }
        });
    }

    public void deleteEmptyPcNote() {
        if (this.mWorkingNote != null) {
            WorkingNote workingNote = this.mWorkingNote;
            Intrinsics.checkNotNull(workingNote);
            if (workingNote.isEmpty()) {
                WorkingNote workingNote2 = this.mWorkingNote;
                Intrinsics.checkNotNull(workingNote2);
                final long noteId = workingNote2.getNoteId();
                Handler handler = this.mainHandle;
                if (handler != null) {
                    handler.postDelayed(new Runnable() { // from class: com.miui.notes.detail.BaseNoteDetailFragment$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseNoteDetailFragment.deleteEmptyPcNote$lambda$4(this.f$0, noteId);
                        }
                    }, 300L);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteEmptyPcNote$lambda$4(BaseNoteDetailFragment baseNoteDetailFragment, long j) {
        NoteStore.delete(baseNoteDetailFragment.getContext(), new long[]{j});
    }

    public final void handleBack() {
        Logger.INSTANCE.d(getTAG(), "handleBackPress,start");
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type com.miui.notes.ui.NotesListActivity");
        NotesListActivity notesListActivity = (NotesListActivity) fragmentActivityRequireActivity;
        int noteHomePageArrayWay = PreferenceUtils.getNoteHomePageArrayWay(notesListActivity, 1);
        if (noteHomePageArrayWay == 1 || noteHomePageArrayWay == this.mOriginGridMode) {
            Logger.INSTANCE.d(getTAG(), "handleBackPress,popBackStack");
            Navigator.get(getThisFragment()).getFragmentManager().popBackStack();
        } else {
            notesListActivity.finish();
        }
    }
}
