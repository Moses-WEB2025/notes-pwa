package com.miui.notes.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.Trace;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.mishare.tapshare.TapShareManager;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.basefeature.notelist.BaseNoteListAdapter;
import com.miui.notes.component.datafolder.FolderItemEntity;
import com.miui.notes.controller.PasswordController;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.feature.folder.CreateFolderDialog;
import com.miui.notes.feature.folder.FolderDialogShaderWarmer;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.home.viewmodel.MoveToFolderNotifyData;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.page.viewmodel.NoteOperationViewModel;
import com.miui.notes.page.viewmodel.Result;
import com.miui.notes.store.FolderStore;
import com.miui.notes.tool.tapshareutils.TapShareFileCleaner;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.tool.util.LongSparseLongArray;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import com.miui.notes.ui.widget.FloatingActionButton;
import com.xiaomi.verificationsdk.internal.Constants;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.internal.util.AsyncInflateLayoutManager;
import miuix.view.EditActionMode;

/* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ï\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0016\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000f*\u0001O\b&\u0018\u0000 ¸\u00012\u00020\u0001:\f³\u0001´\u0001µ\u0001¶\u0001·\u0001¸\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010@\u001a\u00020\u001bH\u0002J\u0012\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010\u0005H\u0002J\b\u0010D\u001a\u00020BH\u0002J\u001a\u0010E\u001a\u00020B2\u0006\u0010F\u001a\u00020G2\b\u0010H\u001a\u0004\u0018\u00010IH\u0016J\b\u0010J\u001a\u00020BH\u0016J\b\u0010K\u001a\u00020BH\u0016J\b\u0010L\u001a\u00020BH\u0002J\b\u0010M\u001a\u00020BH\u0002J\u0012\u0010^\u001a\u00020B2\b\u0010_\u001a\u0004\u0018\u00010IH\u0016J\b\u0010`\u001a\u00020BH\u0014J\u0010\u0010c\u001a\u00020B2\u0006\u0010d\u001a\u00020eH$J\u0010\u0010i\u001a\u00020B2\u0006\u0010j\u001a\u00020\u001bH\u0016J \u0010k\u001a\u00020B2\u0006\u0010l\u001a\u00020\u001b2\u0006\u0010m\u001a\u00020e2\u0006\u0010n\u001a\u00020oH\u0004J\u0018\u0010s\u001a\u00020B2\u0006\u0010l\u001a\u00020\u001b2\u0006\u0010n\u001a\u00020oH\u0002J \u0010t\u001a\u00020B2\u0006\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020o2\u0006\u0010x\u001a\u00020\u0005H\u0004J\u0016\u0010~\u001a\u00020B2\u0006\u0010\u007f\u001a\u00020o2\u0006\u0010u\u001a\u00020vJ\u001b\u0010\u0080\u0001\u001a\u00020B2\b\u0010\u007f\u001a\u0004\u0018\u00010o2\u0006\u0010u\u001a\u00020vH\u0002J\u000f\u0010\u0081\u0001\u001a\u00020B2\u0006\u0010w\u001a\u00020oJ\u0012\u0010\u0082\u0001\u001a\u00020B2\u0007\u0010\u0083\u0001\u001a\u00020\u001bH\u0004J\u001a\u0010\u0084\u0001\u001a\u00020B2\u0007\u0010\u0085\u0001\u001a\u00020\u001b2\u0006\u0010n\u001a\u00020oH\u0004J\u0011\u0010\u0088\u0001\u001a\u00020B2\u0006\u0010n\u001a\u00020oH\u0004J\u000f\u0010\u0089\u0001\u001a\u00020B2\u0006\u0010n\u001a\u00020oJ\u0007\u0010\u008a\u0001\u001a\u00020BJ\t\u0010\u008b\u0001\u001a\u00020eH\u0004J\u001b\u0010\u008d\u0001\u001a\u00020B2\u0007\u0010\u008e\u0001\u001a\u00020\u001b2\u0007\u0010\u008f\u0001\u001a\u00020\u0005H\u0004J$\u0010\u008d\u0001\u001a\u00020B2\u0007\u0010\u0090\u0001\u001a\u00020G2\u0007\u0010\u008e\u0001\u001a\u00020\u001b2\u0007\u0010\u008f\u0001\u001a\u00020\u0005H$J\t\u0010\u0091\u0001\u001a\u00020\u001bH\u0004J\t\u0010\u0092\u0001\u001a\u00020BH\u0004J%\u0010\u0093\u0001\u001a\u00020B2\u0007\u0010\u0094\u0001\u001a\u00020e2\u0011\u0010\u0095\u0001\u001a\f\u0012\u0005\u0012\u00030\u0097\u0001\u0018\u00010\u0096\u0001H\u0002J\u000e\u0010\u0098\u0001\u001a\u00070\u0099\u0001R\u00020\u0000H\u0014J\t\u0010\u009c\u0001\u001a\u00020BH\u0004J\t\u0010\u009d\u0001\u001a\u00020BH\u0004J\u0012\u0010\u009e\u0001\u001a\u00020B2\u0007\u0010\u009f\u0001\u001a\u00020\u001bH\u0014J\u0014\u0010 \u0001\u001a\u00020B2\t\u0010¡\u0001\u001a\u0004\u0018\u00010\u0019H\u0004J\u0019\u0010¢\u0001\u001a\u00020B2\u0007\u0010£\u0001\u001a\u00020\u001b2\u0007\u0010¤\u0001\u001a\u00020\u001bJ\t\u0010¥\u0001\u001a\u00020\u001bH\u0016J\u0007\u0010¦\u0001\u001a\u00020BJ\t\u0010§\u0001\u001a\u00020BH&J\u000f\u0010¨\u0001\u001a\n\u0012\u0005\u0012\u00030ª\u00010©\u0001J\t\u0010«\u0001\u001a\u00020BH\u0016J\u0012\u0010¬\u0001\u001a\u00020B2\u0007\u0010\u00ad\u0001\u001a\u00020vH\u0016J\t\u0010®\u0001\u001a\u00020BH\u0002J\t\u0010¯\u0001\u001a\u00020BH\u0002J\t\u0010°\u0001\u001a\u00020BH\u0002J\t\u0010±\u0001\u001a\u00020BH\u0002J\t\u0010²\u0001\u001a\u00020BH\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0094\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00198\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u001bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u001d\"\u0004\b(\u0010\u001fR\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u00101\u001a\u0004\u0018\u000102X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0018\u00107\u001a\f\u0012\u0006\u0012\u0004\u0018\u000109\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010:\u001a\u00020\u001bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u001d\"\u0004\b<\u0010\u001fR\u000e\u0010=\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010>\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010?R\u0010\u0010N\u001a\u00020OX\u0082\u0004¢\u0006\u0004\n\u0002\u0010PR\u0014\u0010Q\u001a\u0004\u0018\u00010RX¤\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0018\u0010U\u001a\b\u0012\u0002\b\u0003\u0018\u00010VX¦\u0004¢\u0006\u0006\u001a\u0004\bW\u0010XR\u0014\u0010Y\u001a\u0004\u0018\u00010ZX¦\u0004¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u0012\u0010]\u001a\u00020*8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010f\u001a\u00020e8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010g\u001a\u00020h8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010p\u001a\u00020e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bq\u0010rR\u001c\u0010y\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010{\u0018\u00010z8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0011\u0010|\u001a\u00020e8F¢\u0006\u0006\u001a\u0004\b}\u0010rR\u0014\u0010\u0086\u0001\u001a\u00020eX¤\u0004¢\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010rR\u0013\u0010\u008c\u0001\u001a\u00020\u001b8F¢\u0006\u0007\u001a\u0005\b\u008c\u0001\u0010\u001dR\u0014\u0010\u009a\u0001\u001a\u00020eX¤\u0004¢\u0006\u0007\u001a\u0005\b\u009b\u0001\u0010r¨\u0006¹\u0001"}, d2 = {"Lcom/miui/notes/page/BaseNoteListPageFragment;", "Lcom/miui/notes/page/BaseListPageFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "setTAG", "(Ljava/lang/String;)V", "mPasswordController", "Lcom/miui/notes/controller/PasswordController;", "mDeleteNotesDialog", "Lmiuix/appcompat/app/AlertDialog;", "getMDeleteNotesDialog", "()Lmiuix/appcompat/app/AlertDialog;", "setMDeleteNotesDialog", "(Lmiuix/appcompat/app/AlertDialog;)V", "mNoteOperateCompositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "getMNoteOperateCompositeDisposable", "()Lio/reactivex/disposables/CompositeDisposable;", "setMNoteOperateCompositeDisposable", "(Lio/reactivex/disposables/CompositeDisposable;)V", "mActionMode", "Landroid/view/ActionMode;", "mHasUserFolder", "", "getMHasUserFolder", "()Z", "setMHasUserFolder", "(Z)V", "mBindContext", "Lcom/miui/notes/adapter/BindContext;", "getMBindContext", "()Lcom/miui/notes/adapter/BindContext;", "setMBindContext", "(Lcom/miui/notes/adapter/BindContext;)V", "mIsMoved", "getMIsMoved", "setMIsMoved", "mUpdateFolderRunnable", "Ljava/lang/Runnable;", "bar", "Landroid/widget/ProgressBar;", "action", "mRunnable", "progressDialog", "Lmiuix/appcompat/app/ProgressDialog;", "noteOperationViewModel", "Lcom/miui/notes/page/viewmodel/NoteOperationViewModel;", "getNoteOperationViewModel", "()Lcom/miui/notes/page/viewmodel/NoteOperationViewModel;", "setNoteOperationViewModel", "(Lcom/miui/notes/page/viewmodel/NoteOperationViewModel;)V", "toChooseFolderResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "mIsShowBtn", "getMIsShowBtn", "setMIsShowBtn", "mIsTapShareCallbackRegistered", "mIsTapShareSupported", "Ljava/lang/Boolean;", "isTapShareSupported", "showLoading", "", "title", "dismissLoading", "onViewInflated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onDestroyView", "cleanTapShareFiles", "cleanTapShareFilesAsync", "mTapShareSendCallback", "com/miui/notes/page/BaseNoteListPageFragment$mTapShareSendCallback$1", "Lcom/miui/notes/page/BaseNoteListPageFragment$mTapShareSendCallback$1;", "adapter", "Lcom/miui/notes/basefeature/notelist/BaseNoteListAdapter;", "getAdapter", "()Lcom/miui/notes/basefeature/notelist/BaseNoteListAdapter;", "recyclerWrapper", "Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper;", "getRecyclerWrapper", "()Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "mQueryNoteRunnable", "onCreate", "bundle", "initResultLauncher", "mOnSetPasswordListener", "Lcom/miui/notes/controller/PasswordController$OnSetPasswordListener;", "setPasswordSuccess", "userCode", "", "mGridItemSpace", "mItemDecoration", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "onVisibilityChanged", "visible", "showDeleteNotesDialog", Constants.FORCE, "checkItemCount", "ids", "", "itemCount", "getItemCount", "()I", "deleteCheckedItems", "moveCheckedItems", "folderId", "", "checkedIds", "folderName", "folderItemEntities", "", "Lcom/miui/notes/component/datafolder/FolderItemEntity;", "curFoldersNum", "getCurFoldersNum", "moveToFolder", "checkIds", "openFolderChooser", "createFolderAndMoveItems", "setNeedListenKeyBoard", "need", "stickCheckedItems", "stuck", "hideToastId", "getHideToastId", "hideCheckedNotes", "unHideCheckedNotes", "doAsyncAllCheck", "computeMenuAddVisibility", "isInSearchOrActionMode", "showCreateBtn", "show", "source", "btn", "canEnterEditMode", "refreshFolderInfo", "startQueryFolder", "requestCode", "callbacks", "Landroidx/loader/app/LoaderManager$LoaderCallbacks;", "Landroid/database/Cursor;", "createEditModeCallback", "Lcom/miui/notes/page/BaseNoteListPageFragment$EditModeCallback;", "actionModeNoteListMenuRes", "getActionModeNoteListMenuRes", "enterActionMode", "exitActionMode", "updateFolderLayout", "b", "updateActionModeButton2", "mode", "checkTalkBackAndHint", "type", "checked", "finishActionMode", "queryFolderInfo", "updateFolderList", "getSelectedWorkingNotes", "", "Lcom/miui/notes/model/WorkingNote;", "onDestroy", "requestListData", "startTime", "preloadActionModeResources", "preloadActionModeClasses", "preloadActionModeMenu", "preloadActionModeDrawables", "preloadMiSymbolFont", "CommonFolderEditModeCallbackHandler", "CallFolderEditModeCallbackHandler", "PrivateFolderEditModeCallbackHandler", "EditModeCallback", "EditModeCallbackHandler", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseNoteListPageFragment extends BaseListPageFragment {
    private static final long DEFAULT_START_OFFSET = 500;
    public static final int REQUEST_CODE_QUERY_FOLDER = 2;
    protected static final int REQUEST_CODE_QUERY_FOLDER_LIST = 3;
    private ProgressBar bar;
    protected ActionMode mActionMode;
    protected BindContext mBindContext;
    private AlertDialog mDeleteNotesDialog;
    private boolean mHasUserFolder;
    private boolean mIsMoved;
    private boolean mIsShowBtn;
    private boolean mIsTapShareCallbackRegistered;
    private Boolean mIsTapShareSupported;
    protected PasswordController mPasswordController;
    private NoteOperationViewModel noteOperationViewModel;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> toChooseFolderResult;
    private String TAG = "BaseNoteListPageFragment";
    private CompositeDisposable mNoteOperateCompositeDisposable = new CompositeDisposable();
    private final Runnable mUpdateFolderRunnable = new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda8
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.updateFolderList();
        }
    };
    private final Runnable action = new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda9
        @Override // java.lang.Runnable
        public final void run() {
            BaseNoteListPageFragment.action$lambda$1(this.f$0);
        }
    };
    private final Runnable mRunnable = new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda10
        @Override // java.lang.Runnable
        public final void run() {
            BaseNoteListPageFragment.mRunnable$lambda$2(this.f$0);
        }
    };
    private final BaseNoteListPageFragment$mTapShareSendCallback$1 mTapShareSendCallback = new BaseNoteListPageFragment$mTapShareSendCallback$1(this);
    protected Runnable mQueryNoteRunnable = new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda11
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.requestListData(0L);
        }
    };
    private final PasswordController.OnSetPasswordListener mOnSetPasswordListener = new PasswordController.SimpleOnSetPasswordListener() { // from class: com.miui.notes.page.BaseNoteListPageFragment$mOnSetPasswordListener$1
        @Override // com.miui.notes.controller.PasswordController.SimpleOnSetPasswordListener, com.miui.notes.controller.PasswordController.OnSetPasswordListener
        public void onSuccess(int userCode) {
            this.this$0.setPasswordSuccess(userCode);
        }
    };
    protected int mGridItemSpace = -1;
    protected RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() { // from class: com.miui.notes.page.BaseNoteListPageFragment$mItemDecoration$1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int spanCount;
            Intrinsics.checkNotNullParameter(outRect, "outRect");
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(parent, "parent");
            Intrinsics.checkNotNullParameter(state, "state");
            if (parent.getLayoutManager() instanceof NoteGridLayoutManager) {
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager");
                spanCount = ((NoteGridLayoutManager) layoutManager).getSpanCount();
            } else {
                spanCount = 1;
            }
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            outRect.left = this.this$0.mGridItemSpace;
            outRect.right = this.this$0.mGridItemSpace;
            outRect.bottom = this.this$0.mGridItemSpace;
            outRect.top = this.this$0.mGridItemSpace;
            if (childAdapterPosition / spanCount == 0) {
                outRect.top = 0;
            }
        }
    };
    protected List<FolderItemEntity> folderItemEntities = new ArrayList();

    /* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bb\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0012\u0010\b\u001a\u00020\t2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\n\u001a\u00020\tH&J\u001a\u0010\u000b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000eÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/page/BaseNoteListPageFragment$EditModeCallbackHandler;", "", "onCreateActionMode", "", "mode", "Landroid/view/ActionMode;", "menu", "Landroid/view/Menu;", "onDestroyActionMode", "", "updateActionItems", "onActionItemClicked", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private interface EditModeCallbackHandler {
        boolean onActionItemClicked(ActionMode mode, MenuItem item);

        boolean onCreateActionMode(ActionMode mode, Menu menu);

        void onDestroyActionMode(ActionMode mode);

        void updateActionItems();
    }

    protected final void enterActionMode() {
    }

    protected final void exitActionMode() {
    }

    protected abstract int getActionModeNoteListMenuRes();

    protected abstract BaseNoteListAdapter getAdapter();

    protected abstract int getHideToastId();

    public abstract RecyclerView getRecyclerView();

    public abstract EditableRecyclerViewWrapper<?> getRecyclerWrapper();

    public void requestListData(long startTime) {
    }

    protected final void setNeedListenKeyBoard(boolean need) {
    }

    protected abstract void setPasswordSuccess(int userCode);

    protected abstract void showCreateBtn(View btn, boolean show, String source);

    protected void updateFolderLayout(boolean b) {
    }

    public abstract void updateFolderList();

    @Override // com.miui.notes.page.BaseListPageFragment
    protected String getTAG() {
        return this.TAG;
    }

    @Override // com.miui.notes.page.BaseListPageFragment
    protected void setTAG(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.TAG = str;
    }

    protected final AlertDialog getMDeleteNotesDialog() {
        return this.mDeleteNotesDialog;
    }

    protected final void setMDeleteNotesDialog(AlertDialog alertDialog) {
        this.mDeleteNotesDialog = alertDialog;
    }

    protected final CompositeDisposable getMNoteOperateCompositeDisposable() {
        return this.mNoteOperateCompositeDisposable;
    }

    protected final void setMNoteOperateCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.mNoteOperateCompositeDisposable = compositeDisposable;
    }

    protected final boolean getMHasUserFolder() {
        return this.mHasUserFolder;
    }

    protected final void setMHasUserFolder(boolean z) {
        this.mHasUserFolder = z;
    }

    protected final BindContext getMBindContext() {
        BindContext bindContext = this.mBindContext;
        if (bindContext != null) {
            return bindContext;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mBindContext");
        return null;
    }

    protected final void setMBindContext(BindContext bindContext) {
        Intrinsics.checkNotNullParameter(bindContext, "<set-?>");
        this.mBindContext = bindContext;
    }

    public final boolean getMIsMoved() {
        return this.mIsMoved;
    }

    public final void setMIsMoved(boolean z) {
        this.mIsMoved = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void action$lambda$1(BaseNoteListPageFragment baseNoteListPageFragment) {
        ProgressBar progressBar = baseNoteListPageFragment.bar;
        if (progressBar != null) {
            Intrinsics.checkNotNull(progressBar);
            progressBar.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mRunnable$lambda$2(BaseNoteListPageFragment baseNoteListPageFragment) {
        ProgressDialog progressDialog;
        if (baseNoteListPageFragment.getActivity() == null || baseNoteListPageFragment.requireActivity().isFinishing() || (progressDialog = baseNoteListPageFragment.progressDialog) == null) {
            return;
        }
        Intrinsics.checkNotNull(progressDialog);
        progressDialog.show();
    }

    protected final NoteOperationViewModel getNoteOperationViewModel() {
        return this.noteOperationViewModel;
    }

    protected final void setNoteOperationViewModel(NoteOperationViewModel noteOperationViewModel) {
        this.noteOperationViewModel = noteOperationViewModel;
    }

    protected final boolean getMIsShowBtn() {
        return this.mIsShowBtn;
    }

    protected final void setMIsShowBtn(boolean z) {
        this.mIsShowBtn = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isTapShareSupported() {
        Boolean bool = this.mIsTapShareSupported;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        }
        try {
            ApplicationInfo applicationInfo = BaseNoteApp.INSTANCE.getInstance().getPackageManager().getApplicationInfo("com.miui.mishare.connectivity", 128);
            Bundle bundle = applicationInfo != null ? applicationInfo.metaData : null;
            int i = bundle != null ? bundle.getInt("support_bylink_send", -1) : -1;
            Logger.INSTANCE.d(getTAG(), "isTapShareSupported: " + i);
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            Boolean boolValueOf = Boolean.valueOf(z);
            this.mIsTapShareSupported = boolValueOf;
            Intrinsics.checkNotNull(boolValueOf);
            return boolValueOf.booleanValue();
        } catch (Exception e) {
            Log.e(getTAG(), "get appInfo error: " + e);
            this.mIsTapShareSupported = false;
            return false;
        }
    }

    private final void showLoading(String title) {
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog == null) {
            ProgressDialog progressDialog2 = new ProgressDialog(getActivity());
            this.progressDialog = progressDialog2;
            Intrinsics.checkNotNull(progressDialog2);
            progressDialog2.setIndeterminate(true);
            ProgressDialog progressDialog3 = this.progressDialog;
            Intrinsics.checkNotNull(progressDialog3);
            progressDialog3.setCancelable(false);
        } else {
            Intrinsics.checkNotNull(progressDialog);
            progressDialog.dismiss();
        }
        ProgressDialog progressDialog4 = this.progressDialog;
        Intrinsics.checkNotNull(progressDialog4);
        progressDialog4.setMessage(title);
        this.handler.removeCallbacks(this.mRunnable);
        this.handler.postDelayed(this.mRunnable, DEFAULT_START_OFFSET);
    }

    private final void dismissLoading() {
        this.handler.removeCallbacks(this.mRunnable);
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null) {
            Intrinsics.checkNotNull(progressDialog);
            progressDialog.dismiss();
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewInflated(view, savedInstanceState);
        AsyncInflateLayoutManager.getInstance().startAsyncInflateLayout((ViewGroup) new FrameLayout(new ContextThemeWrapper(getContext(), 2131951644)), AlertDialog.DIALOG_CONTENT_LAYOUT, true);
        preloadActionModeResources();
        FolderDialogShaderWarmer folderDialogShaderWarmer = FolderDialogShaderWarmer.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        folderDialogShaderWarmer.warmUpIfNeeded(context);
        NoteOperationViewModel noteOperationViewModel = (NoteOperationViewModel) new ViewModelProvider(this).get(NoteOperationViewModel.class);
        this.noteOperationViewModel = noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        BaseNoteListPageFragment baseNoteListPageFragment = this;
        noteOperationViewModel.getNoteOperation().observe(baseNoteListPageFragment, new Observer() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseNoteListPageFragment.onViewInflated$lambda$3(this.f$0, (Result) obj);
            }
        });
        getHomeViewModel().addObserverForGlobalComposeBtnShow(baseNoteListPageFragment, new Observer() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseNoteListPageFragment.onViewInflated$lambda$4(this.f$0, ((Boolean) obj).booleanValue());
            }
        }, getTAG());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewInflated$lambda$3(BaseNoteListPageFragment baseNoteListPageFragment, Result result) {
        if (result instanceof Result.Loading) {
            baseNoteListPageFragment.showLoading(((Result.Loading) result).getLoadingText());
        } else if (result instanceof Result.Complete) {
            baseNoteListPageFragment.finishActionMode();
            baseNoteListPageFragment.dismissLoading();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewInflated$lambda$4(BaseNoteListPageFragment baseNoteListPageFragment, boolean z) {
        Logger.INSTANCE.i(baseNoteListPageFragment.getTAG(), "onGlobalComposeBtn visible:" + z);
        if (z) {
            if (baseNoteListPageFragment.computeMenuAddVisibility() == 0) {
                baseNoteListPageFragment.showCreateBtn(true, baseNoteListPageFragment.getTAG() + ".observer addButton status with true");
                return;
            }
            return;
        }
        baseNoteListPageFragment.showCreateBtn(false, baseNoteListPageFragment.getTAG() + ".observer addButton status with false");
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        if (this.mIsTapShareCallbackRegistered) {
            try {
                TapShareManager.getInstance().unregisterSendCallback(requireActivity(), this.mTapShareSendCallback);
                this.mIsTapShareCallbackRegistered = false;
                Logger.INSTANCE.d(getTAG(), "onDestroyView unregisterSendCallback");
                cleanTapShareFiles();
            } catch (Exception e) {
                Log.e(getTAG(), "Error unregistering TapShare callback in onDestroyView: " + e.getMessage());
            }
        }
        super.onDestroyView();
        dismissLoading();
        this.progressDialog = null;
    }

    private final void cleanTapShareFiles() {
        try {
            TapShareFileCleaner.asyncCleanAllTapShareFiles(getContext());
            Logger.INSTANCE.d(getTAG(), "Started cleaning TapShare files");
        } catch (Exception e) {
            Log.e(getTAG(), "Error cleaning TapShare files: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cleanTapShareFilesAsync() {
        new Thread(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                BaseNoteListPageFragment.cleanTapShareFilesAsync$lambda$5(this.f$0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cleanTapShareFilesAsync$lambda$5(BaseNoteListPageFragment baseNoteListPageFragment) {
        try {
            TapShareFileCleaner.cleanAllTapShareFilesInCache(baseNoteListPageFragment.getContext());
            Logger.INSTANCE.d(baseNoteListPageFragment.getTAG(), "Cleaned TapShare files on cancel");
        } catch (Exception e) {
            Log.e(baseNoteListPageFragment.getTAG(), "Error cleaning TapShare files on cancel: " + e.getMessage());
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PasswordController passwordController = new PasswordController(this, false);
        this.mPasswordController = passwordController;
        Intrinsics.checkNotNull(passwordController);
        passwordController.setOnSetPasswordListener(this.mOnSetPasswordListener);
        initResultLauncher();
    }

    protected void initResultLauncher() {
        this.toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BaseNoteListPageFragment.initResultLauncher$lambda$7(this.f$0, (ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initResultLauncher$lambda$7(BaseNoteListPageFragment baseNoteListPageFragment, ActivityResult activityResult) {
        Intrinsics.checkNotNull(activityResult);
        if (activityResult.getData() != null) {
            Intent data = activityResult.getData();
            Intrinsics.checkNotNull(data);
            long longExtra = data.getLongExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, 0L);
            Intent data2 = activityResult.getData();
            Intrinsics.checkNotNull(data2);
            String stringExtra = data2.getStringExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER);
            Intent data3 = activityResult.getData();
            Intrinsics.checkNotNull(data3);
            long[] longArrayExtra = data3.getLongArrayExtra(ChooserFolderDialogActivity.CHOOSER_MOVE_NOTE_IDS);
            HomeViewModel homeViewModel = baseNoteListPageFragment.getHomeViewModel();
            Intrinsics.checkNotNull(stringExtra);
            Intrinsics.checkNotNull(longArrayExtra);
            homeViewModel.setMoveToFolderNotifyData(new MoveToFolderNotifyData(longExtra, stringExtra, longArrayExtra));
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean visible) {
        AlertDialog alertDialog;
        super.onVisibilityChanged(visible);
        if (visible || (alertDialog = this.mDeleteNotesDialog) == null) {
            return;
        }
        Intrinsics.checkNotNull(alertDialog);
        if (alertDialog.isShowing()) {
            AlertDialog alertDialog2 = this.mDeleteNotesDialog;
            Intrinsics.checkNotNull(alertDialog2);
            alertDialog2.dismiss();
        }
    }

    protected final void showDeleteNotesDialog(final boolean force, int checkItemCount, final long[] ids) {
        String quantityString;
        Intrinsics.checkNotNullParameter(ids, "ids");
        FragmentActivity activity = getActivity();
        String string = getString(R.string.new_alert_title_delete);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        if (Utils.isUseChinese()) {
            quantityString = getString(R.string.alert_message_delete, Utils.getAllItemTerms(checkItemCount, 0));
        } else {
            quantityString = getResources().getQuantityString(R.plurals.alert_message_delete, checkItemCount, Integer.valueOf(checkItemCount));
        }
        Intrinsics.checkNotNull(activity);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 2131951644);
        builder.setTitle(string);
        builder.setMessage(quantityString);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseNoteListPageFragment.showDeleteNotesDialog$lambda$9(this.f$0, force, ids, dialogInterface, i);
            }
        });
        builder.setAsyncInflatePanelEnabled(true);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = this.mDeleteNotesDialog;
        if (alertDialog != null) {
            Intrinsics.checkNotNull(alertDialog);
            if (alertDialog.isShowing()) {
                AlertDialog alertDialog2 = this.mDeleteNotesDialog;
                Intrinsics.checkNotNull(alertDialog2);
                alertDialog2.dismiss();
            }
        }
        if (LiteUtils.isSuperLite()) {
            builder.setEnableDialogImmersive(true);
        }
        Trace.beginSection(" Fluency_Test:ignore_range");
        try {
            this.mDeleteNotesDialog = builder.show();
            Trace.endSection();
            AlertDialog alertDialog3 = this.mDeleteNotesDialog;
            Intrinsics.checkNotNull(alertDialog3);
            manageDialog(alertDialog3);
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteNotesDialog$lambda$9(final BaseNoteListPageFragment baseNoteListPageFragment, final boolean z, final long[] jArr, DialogInterface dialogInterface, int i) {
        baseNoteListPageFragment.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseNoteListPageFragment.showDeleteNotesDialog$lambda$9$lambda$8(this.f$0, z, jArr);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteNotesDialog$lambda$9$lambda$8(BaseNoteListPageFragment baseNoteListPageFragment, boolean z, long[] jArr) {
        if (baseNoteListPageFragment.isAdded()) {
            baseNoteListPageFragment.deleteCheckedItems(z, jArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getItemCount() {
        BaseNoteListAdapter adapter = getAdapter();
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }

    private final void deleteCheckedItems(final boolean force, final long[] ids) {
        finishActionMode();
        for (long j : ids) {
            if (j == FloatWindowManagerAgent.getExcerptId()) {
                FloatWindowManagerAgent.stopContinuousExcerpt();
                break;
            }
        }
        this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                BaseNoteListPageFragment.deleteCheckedItems$lambda$10(this.f$0, ids, force);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteCheckedItems$lambda$10(BaseNoteListPageFragment baseNoteListPageFragment, long[] jArr, boolean z) {
        if (baseNoteListPageFragment.getRecyclerView() == null) {
            return;
        }
        BaseNoteListAdapter adapter = baseNoteListPageFragment.getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.markManualModifyDataTime();
        NoteOperationViewModel noteOperationViewModel = baseNoteListPageFragment.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        noteOperationViewModel.delete(jArr, z);
    }

    protected final void moveCheckedItems(long folderId, long[] checkedIds, String folderName) {
        Intrinsics.checkNotNullParameter(checkedIds, "checkedIds");
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        BaseNoteListAdapter adapter = getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.markManualModifyDataTime();
        NoteOperationViewModel noteOperationViewModel = this.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        noteOperationViewModel.move(checkedIds, folderId, folderName);
    }

    public final int getCurFoldersNum() {
        List<FolderItemEntity> list = this.folderItemEntities;
        if (list == null) {
            return 0;
        }
        Intrinsics.checkNotNull(list);
        return list.size();
    }

    public final void moveToFolder(long[] checkIds, long folderId) {
        Intrinsics.checkNotNullParameter(checkIds, "checkIds");
        if (getCurFoldersNum() > 1) {
            openFolderChooser(checkIds, folderId);
        } else {
            createFolderAndMoveItems(checkIds);
        }
    }

    private final void openFolderChooser(long[] checkIds, long folderId) {
        if (getActivity() == null || getRecyclerWrapper() == null) {
            return;
        }
        Utils.hideSoftInput(getView());
        ChooserFolderDialogActivity.Companion companion = ChooserFolderDialogActivity.INSTANCE;
        Context context = getContext();
        ActivityResultLauncher<Intent> activityResultLauncher = this.toChooseFolderResult;
        Intrinsics.checkNotNull(activityResultLauncher);
        companion.open(context, activityResultLauncher, checkIds, folderId);
        View view = getView();
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNoteListPageFragment.openFolderChooser$lambda$11(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void openFolderChooser$lambda$11(BaseNoteListPageFragment baseNoteListPageFragment) {
        if (!baseNoteListPageFragment.isAdded() || baseNoteListPageFragment.getView() == null) {
            return;
        }
        baseNoteListPageFragment.finishActionMode();
    }

    public final void createFolderAndMoveItems(final long[] checkedIds) {
        Intrinsics.checkNotNullParameter(checkedIds, "checkedIds");
        setNeedListenKeyBoard(false);
        this.mIsMoved = true;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        CreateFolderDialog createFolderDialog = new CreateFolderDialog(fragmentActivityRequireActivity);
        createFolderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BaseNoteListPageFragment.createFolderAndMoveItems$lambda$12(this.f$0, checkedIds, dialogInterface);
            }
        });
        if (getActivity() == null || requireActivity().isFinishing()) {
            return;
        }
        createFolderDialog.show();
        manageDialog(createFolderDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFolderAndMoveItems$lambda$12(BaseNoteListPageFragment baseNoteListPageFragment, long[] jArr, DialogInterface dialogInterface) {
        Intrinsics.checkNotNull(dialogInterface, "null cannot be cast to non-null type com.miui.notes.feature.folder.CreateFolderDialog");
        CreateFolderDialog createFolderDialog = (CreateFolderDialog) dialogInterface;
        if (createFolderDialog.getFolderId() > 0) {
            HomeViewModel homeViewModel = baseNoteListPageFragment.getHomeViewModel();
            long folderId = createFolderDialog.getFolderId();
            String folderName = createFolderDialog.getFolderName();
            Intrinsics.checkNotNull(folderName);
            homeViewModel.setMoveToFolderNotifyData(new MoveToFolderNotifyData(folderId, folderName, jArr));
            long folderId2 = createFolderDialog.getFolderId();
            String folderName2 = createFolderDialog.getFolderName();
            Intrinsics.checkNotNull(folderName2);
            baseNoteListPageFragment.moveCheckedItems(folderId2, jArr, folderName2);
        }
        baseNoteListPageFragment.finishActionMode();
        baseNoteListPageFragment.setNeedListenKeyBoard(true);
    }

    protected final void stickCheckedItems(boolean stuck, long[] ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        BaseNoteListAdapter adapter = getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.markManualModifyDataTime();
        NoteOperationViewModel noteOperationViewModel = this.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        noteOperationViewModel.stick(ids, stuck);
    }

    protected final void hideCheckedNotes(long[] ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        BaseNoteListAdapter adapter = getAdapter();
        Intrinsics.checkNotNull(adapter);
        adapter.markManualModifyDataTime();
        NoteOperationViewModel noteOperationViewModel = this.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        noteOperationViewModel.hide(ids, getHideToastId());
    }

    public final void unHideCheckedNotes(long[] ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        NoteOperationViewModel noteOperationViewModel = this.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        noteOperationViewModel.unHide(ids);
    }

    public final void doAsyncAllCheck() {
        NoteOperationViewModel noteOperationViewModel = this.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel);
        noteOperationViewModel.getAllCheckStatus().observe(this, new Observer() { // from class: com.miui.notes.page.BaseNoteListPageFragment$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseNoteListPageFragment.doAsyncAllCheck$lambda$13(this.f$0, (Result) obj);
            }
        });
        EditableRecyclerViewWrapper<?> recyclerWrapper = getRecyclerWrapper();
        Intrinsics.checkNotNull(recyclerWrapper);
        boolean z = !recyclerWrapper.isAllItemsChecked();
        NoteOperationViewModel noteOperationViewModel2 = this.noteOperationViewModel;
        Intrinsics.checkNotNull(noteOperationViewModel2);
        noteOperationViewModel2.doAllCheck(z, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doAsyncAllCheck$lambda$13(BaseNoteListPageFragment baseNoteListPageFragment, Result result) {
        if (result instanceof Result.Loading) {
            baseNoteListPageFragment.showLoading(((Result.Loading) result).getLoadingText());
            return;
        }
        if (result instanceof Result.Complete) {
            EditableRecyclerViewWrapper<?> recyclerWrapper = baseNoteListPageFragment.getRecyclerWrapper();
            if (recyclerWrapper != null) {
                recyclerWrapper.notifyAllItemsCheckedStateAfterAsync();
            }
            EditableRecyclerViewWrapper<?> recyclerWrapper2 = baseNoteListPageFragment.getRecyclerWrapper();
            if (recyclerWrapper2 != null) {
                recyclerWrapper2.notifyAllItemCheckedState(((Boolean) ((Result.Complete) result).getParam()).booleanValue());
            }
            baseNoteListPageFragment.dismissLoading();
        }
    }

    protected final int computeMenuAddVisibility() {
        return (isInSearchOrActionMode() || !FolderModel.INSTANCE.canCreateNote(getHomeViewModel().getFolderId())) ? 8 : 0;
    }

    public final boolean isInSearchOrActionMode() {
        if (getHomeViewModel().isSearchMode()) {
            return true;
        }
        if (getRecyclerWrapper() != null) {
            EditableRecyclerViewWrapper<?> recyclerWrapper = getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            if (recyclerWrapper.isInActionMode()) {
                return true;
            }
        }
        return isInEditActionMode();
    }

    protected final void showCreateBtn(boolean show, String source) {
        Intrinsics.checkNotNullParameter(source, "source");
        if (this.mIsShowBtn && show) {
            Logger.INSTANCE.d(getTAG(), "showCreateBtn already show");
            return;
        }
        this.mIsShowBtn = show;
        FloatingActionButton floatingActionButton = this.mBtnContentAdd;
        Intrinsics.checkNotNull(floatingActionButton);
        showCreateBtn(floatingActionButton, show, source);
    }

    protected final boolean canEnterEditMode() {
        return !getHomeViewModel().isSearchMode();
    }

    protected final void refreshFolderInfo() {
        Logger.INSTANCE.d(getTAG(), "refreshFolderInfo");
        startQueryFolder(2, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.page.BaseNoteListPageFragment.refreshFolderInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "refreshFolderInfo/onLoadFinished");
                FragmentActivity activity = BaseNoteListPageFragment.this.getActivity();
                Intrinsics.checkNotNull(activity);
                Context applicationContext = activity.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                BaseNoteListPageFragment.this.setMHasUserFolder(FolderStore.updateEnableNewDefaultFolderName(applicationContext, cursor)[0]);
                if (cursor != null) {
                    cursor.moveToFirst();
                }
                BaseNoteListPageFragment.this.getLoaderManager().destroyLoader(2);
                Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "refreshFolderInfo/onLoadFinished->destroy loader");
            }
        });
    }

    private final void startQueryFolder(int requestCode, LoaderManager.LoaderCallbacks<Cursor> callbacks) {
        FolderStore folderStore = FolderStore.INSTANCE;
        LoaderManager loaderManager = getLoaderManager();
        Intrinsics.checkNotNullExpressionValue(loaderManager, "getLoaderManager(...)");
        folderStore.query(loaderManager, 1, requestCode, callbacks);
    }

    protected EditModeCallback createEditModeCallback() {
        return new EditModeCallback();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0018\n\u0002\b\u0005\b\u0092\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u001a\u0010\u001c\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001d\u001a\u00020\u0005H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001e"}, d2 = {"Lcom/miui/notes/page/BaseNoteListPageFragment$CommonFolderEditModeCallbackHandler;", "Lcom/miui/notes/page/BaseNoteListPageFragment$EditModeCallbackHandler;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/page/BaseNoteListPageFragment;)V", "mStickMenu", "Landroid/view/MenuItem;", "mHideMenu", "mDeleteMenu", "mMoveMenu", "mGroupMenu", "mRenameMenu", "mLastClickTime", "", "onCreateActionMode", "", "mode", "Landroid/view/ActionMode;", "menu", "Landroid/view/Menu;", "onDestroyActionMode", "", "updateActionItems", "isAllCheckedItemSticking", "()Z", "allCheckedItemFolderStatus", "", "getAllCheckedItemFolderStatus", "()[Z", "onActionItemClicked", CloudPushConstants.XML_ITEM, "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    class CommonFolderEditModeCallbackHandler implements EditModeCallbackHandler {
        private MenuItem mDeleteMenu;
        private MenuItem mGroupMenu;
        private MenuItem mHideMenu;
        private long mLastClickTime;
        private MenuItem mMoveMenu;
        private MenuItem mRenameMenu;
        private MenuItem mStickMenu;

        public CommonFolderEditModeCallbackHandler() {
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Intrinsics.checkNotNullParameter(menu, "menu");
            BaseNoteListPageFragment.this.getMenuInflater().inflate(BaseNoteListPageFragment.this.getActionModeNoteListMenuRes(), menu);
            this.mLastClickTime = System.currentTimeMillis();
            this.mHideMenu = menu.findItem(R.id.hide);
            this.mStickMenu = menu.findItem(R.id.stick);
            this.mDeleteMenu = menu.findItem(R.id.delete);
            this.mMoveMenu = menu.findItem(R.id.move);
            this.mGroupMenu = menu.findItem(R.id.group);
            this.mRenameMenu = menu.findItem(R.id.rename);
            BaseNoteListPageFragment.this.getHomeViewModel().changeMultiMode(1);
            BaseNoteListPageFragment.this.getHomeViewModel().setVpScrollable(false);
            BaseNoteListPageFragment.this.enterActionMode();
            if (BaseNoteListPageFragment.this.mIsTapShareCallbackRegistered || BaseNoteListPageFragment.this.getHomeViewModel().getFolderId() == -4) {
                if (BaseNoteListPageFragment.this.getHomeViewModel().getFolderId() == -4) {
                    Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "onCreateActionMode: skip TapShare registration for private folder");
                }
            } else if (BaseNoteListPageFragment.this.isTapShareSupported()) {
                Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "onCreateActionMode: registerSendCallback for common folder");
                int iRegisterSendCallback = TapShareManager.getInstance().registerSendCallback(BaseNoteListPageFragment.this.requireActivity(), BaseNoteListPageFragment.this.mTapShareSendCallback);
                BaseNoteListPageFragment.this.mIsTapShareCallbackRegistered = true;
                Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "onCreateActionMode: registerSendCallback result: " + iRegisterSendCallback);
            }
            return true;
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public void onDestroyActionMode(ActionMode mode) {
            if (BaseNoteListPageFragment.this.computeMenuAddVisibility() == 0) {
                BaseNoteListPageFragment baseNoteListPageFragment = BaseNoteListPageFragment.this;
                baseNoteListPageFragment.showCreateBtn(true, baseNoteListPageFragment.getTAG() + ".onDestroyActionMode");
            }
            BaseNoteListPageFragment.this.getHomeViewModel().changeMultiMode(0);
            BaseNoteListPageFragment.this.getHomeViewModel().setVpScrollable(true);
            BaseNoteListPageFragment.this.exitActionMode();
            if (BaseNoteListPageFragment.this.mIsTapShareCallbackRegistered) {
                try {
                    TapShareManager.getInstance().unregisterSendCallback(BaseNoteListPageFragment.this.requireActivity(), BaseNoteListPageFragment.this.mTapShareSendCallback);
                    BaseNoteListPageFragment.this.mIsTapShareCallbackRegistered = false;
                    Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "onDestroyActionMode: unregisterSendCallback");
                } catch (Exception e) {
                    Log.e(BaseNoteListPageFragment.this.getTAG(), "Error unregistering TapShare callback: " + e.getMessage());
                }
            }
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public void updateActionItems() {
            if (BaseNoteListPageFragment.this.isDialogShowing()) {
                return;
            }
            EditableRecyclerViewWrapper<?> recyclerWrapper = BaseNoteListPageFragment.this.getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            int checkedItemCount = recyclerWrapper.getCheckedItemCount();
            boolean z = checkedItemCount > 0;
            MenuItem menuItem = this.mHideMenu;
            Intrinsics.checkNotNull(menuItem);
            menuItem.setVisible(true);
            MenuItem menuItem2 = this.mDeleteMenu;
            Intrinsics.checkNotNull(menuItem2);
            menuItem2.setVisible(true);
            MenuItem menuItem3 = this.mRenameMenu;
            Intrinsics.checkNotNull(menuItem3);
            menuItem3.setVisible(false);
            boolean[] allCheckedItemFolderStatus = getAllCheckedItemFolderStatus();
            if (BaseNoteListPageFragment.this.getHomeViewModel().getFolderId() != -4097 || BaseNoteListPageFragment.this.getMHasUserFolder() || checkedItemCount <= 1 || !allCheckedItemFolderStatus[0]) {
                MenuItem menuItem4 = this.mMoveMenu;
                Intrinsics.checkNotNull(menuItem4);
                menuItem4.setVisible(!allCheckedItemFolderStatus[1]);
                MenuItem menuItem5 = this.mGroupMenu;
                Intrinsics.checkNotNull(menuItem5);
                menuItem5.setVisible(false);
            } else if (!LiteUtils.isLiteOrMiddle()) {
                MenuItem menuItem6 = this.mMoveMenu;
                Intrinsics.checkNotNull(menuItem6);
                menuItem6.setVisible(false);
                MenuItem menuItem7 = this.mGroupMenu;
                Intrinsics.checkNotNull(menuItem7);
                menuItem7.setVisible(true);
            }
            if (z) {
                if (isAllCheckedItemSticking()) {
                    MenuItem menuItem8 = this.mStickMenu;
                    Intrinsics.checkNotNull(menuItem8);
                    menuItem8.setTitle(R.string.menu_unstick);
                    MenuItem menuItem9 = this.mStickMenu;
                    Intrinsics.checkNotNull(menuItem9);
                    menuItem9.setIcon(R.drawable.miui_ic_unstick);
                } else {
                    MenuItem menuItem10 = this.mStickMenu;
                    Intrinsics.checkNotNull(menuItem10);
                    menuItem10.setTitle(R.string.menu_stick);
                    MenuItem menuItem11 = this.mStickMenu;
                    Intrinsics.checkNotNull(menuItem11);
                    menuItem11.setIcon(R.drawable.miui_ic_stick);
                }
                MenuItem menuItem12 = this.mStickMenu;
                Intrinsics.checkNotNull(menuItem12);
                menuItem12.setEnabled(true);
                return;
            }
            MenuItem menuItem13 = this.mStickMenu;
            Intrinsics.checkNotNull(menuItem13);
            menuItem13.setTitle(R.string.menu_stick);
            MenuItem menuItem14 = this.mStickMenu;
            Intrinsics.checkNotNull(menuItem14);
            menuItem14.setIcon(R.drawable.miui_ic_stick);
            MenuItem menuItem15 = this.mStickMenu;
            Intrinsics.checkNotNull(menuItem15);
            menuItem15.setEnabled(false);
        }

        public final boolean isAllCheckedItemSticking() {
            System.currentTimeMillis();
            EditableRecyclerViewWrapper<?> recyclerWrapper = BaseNoteListPageFragment.this.getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            LongSparseLongArray checkedItems = recyclerWrapper.getCheckedItems();
            int size = checkedItems.size();
            for (int i = 0; i < size; i++) {
                int iValueAt = (int) checkedItems.valueAt(i);
                BaseNoteListAdapter adapter = BaseNoteListPageFragment.this.getAdapter();
                Intrinsics.checkNotNull(adapter);
                if (adapter.getItemStickFromCursor(iValueAt) <= 0) {
                    return false;
                }
            }
            return true;
        }

        public final boolean[] getAllCheckedItemFolderStatus() {
            boolean[] zArr = {true, FolderModel.INSTANCE.isCannotMoveFolder(BaseNoteListPageFragment.this.getHomeViewModel().getFolderId())};
            EditableRecyclerViewWrapper<?> recyclerWrapper = BaseNoteListPageFragment.this.getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            LongSparseLongArray checkedItems = recyclerWrapper.getCheckedItems();
            int size = checkedItems.size();
            for (int i = 0; i < size; i++) {
                int iValueAt = (int) checkedItems.valueAt(i);
                BaseNoteListAdapter adapter = BaseNoteListPageFragment.this.getAdapter();
                Intrinsics.checkNotNull(adapter);
                long itemFoldIdFromCursor = adapter.getItemFoldIdFromCursor(iValueAt);
                if (FolderModel.INSTANCE.isUserFolder(itemFoldIdFromCursor) || FolderModel.INSTANCE.isSpecialFolder(itemFoldIdFromCursor)) {
                    zArr[0] = false;
                }
                if (FolderModel.INSTANCE.isCannotMoveFolder(itemFoldIdFromCursor)) {
                    zArr[1] = true;
                    return zArr;
                }
            }
            return zArr;
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Intrinsics.checkNotNullParameter(item, "item");
            long jCurrentTimeMillis = System.currentTimeMillis() - this.mLastClickTime;
            this.mLastClickTime = System.currentTimeMillis();
            if (BaseNoteListPageFragment.this.getRecyclerView() != null) {
                RecyclerView recyclerView = BaseNoteListPageFragment.this.getRecyclerView();
                Intrinsics.checkNotNull(recyclerView);
                recyclerView.stopScroll();
            }
            switch (item.getItemId()) {
                case R.id.delete /* 2131362396 */:
                    if (Utils.isFastClick(item.getItemId(), 300L)) {
                        return true;
                    }
                    if (BaseNoteListPageFragment.this.getView() != null) {
                        BaseNoteListPageFragment baseNoteListPageFragment = BaseNoteListPageFragment.this;
                        View view = baseNoteListPageFragment.getView();
                        Intrinsics.checkNotNull(view);
                        baseNoteListPageFragment.bar = (ProgressBar) view.findViewById(R.id.frame_progress_bar);
                        ProgressBar progressBar = BaseNoteListPageFragment.this.bar;
                        Intrinsics.checkNotNull(progressBar);
                        progressBar.setVisibility(8);
                        BaseNoteListPageFragment.this.handler.removeCallbacks(BaseNoteListPageFragment.this.action);
                        BaseNoteListPageFragment.this.handler.postDelayed(BaseNoteListPageFragment.this.action, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
                    }
                    if (BaseNoteListPageFragment.this.getRecyclerView() != null) {
                        MainHandler<BaseListPageFragment> mainHandler = BaseNoteListPageFragment.this.handler;
                        final BaseNoteListPageFragment baseNoteListPageFragment2 = BaseNoteListPageFragment.this;
                        mainHandler.postDelayed(new Runnable() { // from class: com.miui.notes.page.BaseNoteListPageFragment$CommonFolderEditModeCallbackHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BaseNoteListPageFragment.CommonFolderEditModeCallbackHandler.onActionItemClicked$lambda$0(baseNoteListPageFragment2);
                            }
                        }, 120L);
                    }
                    return true;
                case R.id.group /* 2131362666 */:
                    if (jCurrentTimeMillis >= 300 && BaseNoteListPageFragment.this.getRecyclerWrapper() != null) {
                        BaseNoteListPageFragment baseNoteListPageFragment3 = BaseNoteListPageFragment.this;
                        EditableRecyclerViewWrapper<?> recyclerWrapper = baseNoteListPageFragment3.getRecyclerWrapper();
                        Intrinsics.checkNotNull(recyclerWrapper);
                        baseNoteListPageFragment3.createFolderAndMoveItems(recyclerWrapper.getCheckedItemIds());
                    }
                    return true;
                case R.id.hide /* 2131362702 */:
                    PasswordController passwordController = BaseNoteListPageFragment.this.mPasswordController;
                    Intrinsics.checkNotNull(passwordController);
                    passwordController.setPassword(5102);
                    return true;
                case R.id.move /* 2131362989 */:
                    if (jCurrentTimeMillis >= 300 && BaseNoteListPageFragment.this.getRecyclerWrapper() != null) {
                        BaseNoteListPageFragment baseNoteListPageFragment4 = BaseNoteListPageFragment.this;
                        EditableRecyclerViewWrapper<?> recyclerWrapper2 = baseNoteListPageFragment4.getRecyclerWrapper();
                        Intrinsics.checkNotNull(recyclerWrapper2);
                        baseNoteListPageFragment4.moveToFolder(recyclerWrapper2.getCheckedItemIds(), BaseNoteListPageFragment.this.getHomeViewModel().getFolderId());
                    }
                    return true;
                case R.id.stick /* 2131363535 */:
                    BaseNoteListPageFragment baseNoteListPageFragment5 = BaseNoteListPageFragment.this;
                    boolean z = !isAllCheckedItemSticking();
                    EditableRecyclerViewWrapper<?> recyclerWrapper3 = BaseNoteListPageFragment.this.getRecyclerWrapper();
                    Intrinsics.checkNotNull(recyclerWrapper3);
                    baseNoteListPageFragment5.stickCheckedItems(z, recyclerWrapper3.getCheckedItemIds());
                    return true;
                default:
                    return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onActionItemClicked$lambda$0(BaseNoteListPageFragment baseNoteListPageFragment) {
            if (!baseNoteListPageFragment.isAdded() || baseNoteListPageFragment.getRecyclerWrapper() == null) {
                return;
            }
            if (baseNoteListPageFragment.bar != null) {
                ProgressBar progressBar = baseNoteListPageFragment.bar;
                Intrinsics.checkNotNull(progressBar);
                progressBar.setVisibility(0);
            }
            EditableRecyclerViewWrapper<?> recyclerWrapper = baseNoteListPageFragment.getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            int checkedItemCount = recyclerWrapper.getCheckedItemCount();
            EditableRecyclerViewWrapper<?> recyclerWrapper2 = baseNoteListPageFragment.getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper2);
            baseNoteListPageFragment.showDeleteNotesDialog(false, checkedItemCount, recyclerWrapper2.getCheckedItemIds());
        }
    }

    /* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/page/BaseNoteListPageFragment$CallFolderEditModeCallbackHandler;", "Lcom/miui/notes/page/BaseNoteListPageFragment$CommonFolderEditModeCallbackHandler;", "Lcom/miui/notes/page/BaseNoteListPageFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/page/BaseNoteListPageFragment;)V", "onCreateActionMode", "", "mode", "Landroid/view/ActionMode;", "menu", "Landroid/view/Menu;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class CallFolderEditModeCallbackHandler extends CommonFolderEditModeCallbackHandler {
        public CallFolderEditModeCallbackHandler() {
            super();
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.CommonFolderEditModeCallbackHandler, com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Intrinsics.checkNotNullParameter(menu, "menu");
            super.onCreateActionMode(mode, menu);
            menu.findItem(R.id.move).setVisible(false);
            return true;
        }
    }

    /* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\u001a\u0010\u000e\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\u0012"}, d2 = {"Lcom/miui/notes/page/BaseNoteListPageFragment$PrivateFolderEditModeCallbackHandler;", "Lcom/miui/notes/page/BaseNoteListPageFragment$CommonFolderEditModeCallbackHandler;", "Lcom/miui/notes/page/BaseNoteListPageFragment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/page/BaseNoteListPageFragment;)V", "onCreateActionMode", "", "mode", "Landroid/view/ActionMode;", "menu", "Landroid/view/Menu;", "onDestroyActionMode", "", "updateActionItems", "onActionItemClicked", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "updateActionModeButton1", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class PrivateFolderEditModeCallbackHandler extends CommonFolderEditModeCallbackHandler {
        public PrivateFolderEditModeCallbackHandler() {
            super();
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.CommonFolderEditModeCallbackHandler, com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Intrinsics.checkNotNullParameter(menu, "menu");
            boolean zOnCreateActionMode = super.onCreateActionMode(mode, menu);
            updateActionModeButton1(mode);
            return zOnCreateActionMode;
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.CommonFolderEditModeCallbackHandler, com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public void onDestroyActionMode(ActionMode mode) {
            super.onDestroyActionMode(mode);
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.CommonFolderEditModeCallbackHandler, com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public void updateActionItems() {
            super.updateActionItems();
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.CommonFolderEditModeCallbackHandler, com.miui.notes.page.BaseNoteListPageFragment.EditModeCallbackHandler
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Intrinsics.checkNotNullParameter(item, "item");
            return super.onActionItemClicked(mode, item);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void updateActionModeButton1(ActionMode mode) {
            Intrinsics.checkNotNull(mode, "null cannot be cast to non-null type miuix.view.EditActionMode");
            EditActionMode editActionMode = (EditActionMode) mode;
            if (UIUtils.isMiuiXSdkSupported()) {
                if (DisplayUtils.isNightMode()) {
                    editActionMode.setButton(16908313, (CharSequence) null, R.drawable.miuix_action_icon_cancel_dark);
                    return;
                } else {
                    editActionMode.setButton(16908313, (CharSequence) null, R.drawable.miuix_action_icon_cancel_light);
                    return;
                }
            }
            editActionMode.setButton(16908313, android.R.string.cancel);
        }
    }

    /* JADX INFO: compiled from: BaseNoteListPageFragment.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0094\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\u0011\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001c\u0010\u0014\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/page/BaseNoteListPageFragment$EditModeCallback;", "Lcom/miui/notes/ui/widget/EditableRecyclerViewWrapper$MultiChoiceModeListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/page/BaseNoteListPageFragment;)V", "mCallbackHandler", "Lcom/miui/notes/page/BaseNoteListPageFragment$EditModeCallbackHandler;", "onAllItemCheckedStateChanged", "", "mode", "Landroid/view/ActionMode;", "checked", "", "onItemCheckedStateChanged", "position", "", "id", "", "onCreateActionMode", "menu", "Landroid/view/Menu;", "onPrepareActionMode", "onActionItemClicked", CloudPushConstants.XML_ITEM, "Landroid/view/MenuItem;", "onDestroyActionMode", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    protected class EditModeCallback implements EditableRecyclerViewWrapper.MultiChoiceModeListener {
        private final EditModeCallbackHandler mCallbackHandler;

        public EditModeCallback() {
            CommonFolderEditModeCallbackHandler commonFolderEditModeCallbackHandler;
            if (BaseNoteListPageFragment.this.getHomeViewModel().getFolderId() == -2) {
                commonFolderEditModeCallbackHandler = BaseNoteListPageFragment.this.new CallFolderEditModeCallbackHandler();
            } else if (BaseNoteListPageFragment.this.getHomeViewModel().getFolderId() == -4) {
                commonFolderEditModeCallbackHandler = BaseNoteListPageFragment.this.new PrivateFolderEditModeCallbackHandler();
            } else {
                commonFolderEditModeCallbackHandler = BaseNoteListPageFragment.this.new CommonFolderEditModeCallbackHandler();
            }
            this.mCallbackHandler = commonFolderEditModeCallbackHandler;
        }

        @Override // com.miui.notes.ui.widget.EditableRecyclerViewWrapper.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode mode, boolean checked) {
            BaseNoteListPageFragment.this.updateActionModeButton2(mode);
            this.mCallbackHandler.updateActionItems();
            BaseNoteListPageFragment.this.checkTalkBackAndHint(false, checked);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            BaseNoteListPageFragment.this.updateActionModeButton2(mode);
            this.mCallbackHandler.updateActionItems();
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Intrinsics.checkNotNullParameter(menu, "menu");
            BaseNoteListPageFragment.this.updateActionModeButton2(mode);
            Logger.INSTANCE.i(BaseNoteListPageFragment.this.getTAG(), "onCreateActionMode");
            BaseNoteListPageFragment.this.mActionMode = mode;
            BaseNoteListPageFragment.this.getHomeViewModel().changeMultiMode(1);
            BaseNoteListPageFragment.this.getHomeViewModel().setVpScrollable(false);
            BaseNoteListPageFragment.this.updateFolderLayout(false);
            return this.mCallbackHandler.onCreateActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            this.mCallbackHandler.updateActionItems();
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(item, "item");
            RecyclerView recyclerView = BaseNoteListPageFragment.this.getRecyclerView();
            if (recyclerView != null) {
                recyclerView.stopScroll();
            }
            if (item.getItemId() == 16908313) {
                mode.finish();
            } else if (item.getItemId() == 16908314) {
                if (!Utils.isFastClick(item.getItemId(), 750L)) {
                    if (BaseNoteListPageFragment.this.getItemCount() <= 500) {
                        EditableRecyclerViewWrapper<?> recyclerWrapper = BaseNoteListPageFragment.this.getRecyclerWrapper();
                        Intrinsics.checkNotNull(recyclerWrapper);
                        EditableRecyclerViewWrapper<?> recyclerWrapper2 = BaseNoteListPageFragment.this.getRecyclerWrapper();
                        Intrinsics.checkNotNull(recyclerWrapper2);
                        recyclerWrapper.setAllItemsChecked(!recyclerWrapper2.isAllItemsChecked());
                    } else {
                        BaseNoteListPageFragment.this.doAsyncAllCheck();
                    }
                } else {
                    Logger.INSTANCE.i(BaseNoteListPageFragment.this.getTAG(), "onActionItemClicked BUTTON2 isFastClick");
                }
            }
            return this.mCallbackHandler.onActionItemClicked(mode, item);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            BaseNoteListPageFragment.this.mActionMode = null;
            BaseNoteListPageFragment.this.getHomeViewModel().changeMultiMode(0);
            BaseNoteListPageFragment.this.getHomeViewModel().setVpScrollable(true);
            this.mCallbackHandler.onDestroyActionMode(mode);
            BaseNoteListPageFragment.this.updateFolderLayout(true);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void updateActionModeButton2(ActionMode mode) {
        Intrinsics.checkNotNull(mode, "null cannot be cast to non-null type miuix.view.EditActionMode");
        EditActionMode editActionMode = (EditActionMode) mode;
        if (UIUtils.isMiuiXSdkSupported()) {
            int i = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_deselect_all_dark : R.drawable.miuix_action_icon_deselect_all_light;
            int i2 = DisplayUtils.isNightMode() ? R.drawable.miuix_action_icon_select_all_dark : R.drawable.miuix_action_icon_select_all_light;
            EditableRecyclerViewWrapper<?> recyclerWrapper = getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            if (!recyclerWrapper.isAllItemsChecked()) {
                i = i2;
            }
            editActionMode.setButton(16908314, (CharSequence) null, i);
        } else {
            EditableRecyclerViewWrapper<?> recyclerWrapper2 = getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper2);
            editActionMode.setButton(16908314, recyclerWrapper2.isAllItemsChecked() ? R.string.miuix_appcompat_action_mode_deselect_all : R.string.miuix_appcompat_action_mode_select_all);
        }
        EditableRecyclerViewWrapper<?> recyclerWrapper3 = getRecyclerWrapper();
        Intrinsics.checkNotNull(recyclerWrapper3);
        checkTalkBackAndHint(true, recyclerWrapper3.isAllItemsChecked());
    }

    public final void checkTalkBackAndHint(boolean type, boolean checked) {
        View decorView = getActivity() != null ? requireActivity().getWindow().getDecorView() : null;
        if (decorView != null) {
            View viewFindViewById = decorView.findViewById(R.id.action_context_bar).findViewById(16908314);
            if (type) {
                viewFindViewById.setContentDescription(getString(checked ? R.string.miuix_appcompat_deselect_all_description : R.string.miuix_appcompat_select_all_description));
            } else {
                viewFindViewById.announceForAccessibility(getString(checked ? R.string.note_select_all_talkback : R.string.note_unselect_all_talkback));
            }
        }
    }

    public boolean finishActionMode() {
        getHomeViewModel().changeMultiMode(0);
        getHomeViewModel().setVpScrollable(true);
        if (getRecyclerWrapper() != null) {
            EditableRecyclerViewWrapper<?> recyclerWrapper = getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            if (recyclerWrapper.isInActionMode()) {
                EditableRecyclerViewWrapper<?> recyclerWrapper2 = getRecyclerWrapper();
                Intrinsics.checkNotNull(recyclerWrapper2);
                recyclerWrapper2.finishActionMode();
                return true;
            }
        }
        return false;
    }

    public final void queryFolderInfo() {
        Logger.INSTANCE.d(getTAG(), "queryFolderInfo");
        FolderStore folderStore = FolderStore.INSTANCE;
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        Intrinsics.checkNotNullExpressionValue(loaderManager, "getInstance(...)");
        folderStore.query(loaderManager, 1, 3, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.page.BaseNoteListPageFragment.queryFolderInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Code duplicated, block: B:49:0x00ed  */
            /* JADX WARN: Code duplicated, block: B:62:0x0099 A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:71:0x003e A[SYNTHETIC] */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                boolean z;
                Intrinsics.checkNotNullParameter(loader, "loader");
                if (BaseNoteListPageFragment.this.folderItemEntities == null) {
                    BaseNoteListPageFragment.this.folderItemEntities = new ArrayList();
                } else {
                    List<FolderItemEntity> list = BaseNoteListPageFragment.this.folderItemEntities;
                    Intrinsics.checkNotNull(list);
                    list.clear();
                }
                Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "queryFolderInfo/onLoadFinished,collect folder list infos");
                FolderItemEntity folderItemEntity = null;
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToPosition(-1);
                    while (cursor.moveToNext()) {
                        long j = cursor.getLong(0);
                        boolean z2 = cursor.getLong(11) > 0;
                        String string = cursor.getString(14);
                        if (j != -1 && j != -3 && j != -4) {
                            if (j < 0) {
                                string = FolderModel.INSTANCE.getSystemSubject(j);
                                if (cursor.getInt(15) != 0) {
                                    if (j != 0) {
                                        FolderItemEntity folderItemEntity2 = new FolderItemEntity(j, string, z2);
                                        List<FolderItemEntity> list2 = BaseNoteListPageFragment.this.folderItemEntities;
                                        Intrinsics.checkNotNull(list2);
                                        list2.add(folderItemEntity2);
                                    }
                                }
                            } else {
                                if (j == 0) {
                                    string = FolderModel.INSTANCE.getSystemSubject(j);
                                    if (cursor.getInt(15) != 0) {
                                        folderItemEntity = new FolderItemEntity(j, string, z2);
                                    }
                                }
                                if (j != 0) {
                                    FolderItemEntity folderItemEntity3 = new FolderItemEntity(j, string, z2);
                                    List<FolderItemEntity> list3 = BaseNoteListPageFragment.this.folderItemEntities;
                                    Intrinsics.checkNotNull(list3);
                                    list3.add(folderItemEntity3);
                                }
                            }
                        }
                    }
                }
                Utils utils = Utils.INSTANCE;
                List<FolderItemEntity> list4 = BaseNoteListPageFragment.this.folderItemEntities;
                Intrinsics.checkNotNull(list4);
                utils.sortFolderItem(list4);
                if (folderItemEntity != null) {
                    List<FolderItemEntity> list5 = BaseNoteListPageFragment.this.folderItemEntities;
                    Intrinsics.checkNotNull(list5);
                    if (list5.isEmpty()) {
                        z = false;
                    } else {
                        if (BaseNoteListPageFragment.this.getMIsMoved()) {
                            List<FolderItemEntity> list6 = BaseNoteListPageFragment.this.folderItemEntities;
                            Intrinsics.checkNotNull(list6);
                            z = list6.size() == 1;
                            BaseNoteListPageFragment.this.setMIsMoved(false);
                        } else {
                            z = false;
                        }
                        List<FolderItemEntity> list7 = BaseNoteListPageFragment.this.folderItemEntities;
                        Intrinsics.checkNotNull(list7);
                        list7.add(folderItemEntity);
                    }
                } else {
                    z = false;
                }
                FolderItemEntity folderItemEntity4 = new FolderItemEntity(-4097L, BaseNoteApp.INSTANCE.getInstance().getString(R.string.pc_all_folder_name), false);
                List<FolderItemEntity> list8 = BaseNoteListPageFragment.this.folderItemEntities;
                Intrinsics.checkNotNull(list8);
                list8.add(0, folderItemEntity4);
                if (z) {
                    BaseNoteListPageFragment.this.handler.removeCallbacks(BaseNoteListPageFragment.this.mUpdateFolderRunnable);
                    Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "query delay 300ms");
                    BaseNoteListPageFragment.this.handler.postDelayed(BaseNoteListPageFragment.this.mUpdateFolderRunnable, 300L);
                } else {
                    BaseNoteListPageFragment.this.handler.removeCallbacks(BaseNoteListPageFragment.this.mUpdateFolderRunnable);
                    Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "query delay 0ms,update folder list");
                    BaseNoteListPageFragment.this.updateFolderList();
                }
            }
        });
    }

    public final List<WorkingNote> getSelectedWorkingNotes() {
        ArrayList arrayList = new ArrayList();
        if (getRecyclerWrapper() != null) {
            EditableRecyclerViewWrapper<?> recyclerWrapper = getRecyclerWrapper();
            Intrinsics.checkNotNull(recyclerWrapper);
            if (recyclerWrapper.isInActionMode()) {
                EditableRecyclerViewWrapper<?> recyclerWrapper2 = getRecyclerWrapper();
                Intrinsics.checkNotNull(recyclerWrapper2);
                long[] checkedItemIds = recyclerWrapper2.getCheckedItemIds();
                if (checkedItemIds.length == 0) {
                    Logger.INSTANCE.d(getTAG(), "No items selected, returning empty list");
                    return arrayList;
                }
                Logger.INSTANCE.d(getTAG(), "Loading " + checkedItemIds.length + " selected notes");
                for (long j : checkedItemIds) {
                    try {
                        WorkingNote workingNoteLoadNote = WorkingNote.INSTANCE.loadNote("_id=" + j, null);
                        if (workingNoteLoadNote != null) {
                            arrayList.add(workingNoteLoadNote);
                        } else {
                            Logger.INSTANCE.w(getTAG(), "Failed to load note with id: " + j + ", workingNote is null");
                        }
                    } catch (Exception e) {
                        Log.e(getTAG(), "Exception while loading note with id: " + j, e);
                    }
                }
                return arrayList;
            }
        }
        Logger.INSTANCE.d(getTAG(), "Not in multi-select mode, returning empty list");
        return arrayList;
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        AsyncInflateLayoutManager.getInstance().release();
        PasswordController passwordController = this.mPasswordController;
        Intrinsics.checkNotNull(passwordController);
        passwordController.destroy();
        CompositeDisposable compositeDisposable = this.mNoteOperateCompositeDisposable;
        if (compositeDisposable != null) {
            Intrinsics.checkNotNull(compositeDisposable);
            compositeDisposable.clear();
        }
        super.onDestroy();
    }

    private final void preloadActionModeResources() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.miui.notes.page.BaseNoteListPageFragment.preloadActionModeResources.1
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                try {
                    BaseNoteListPageFragment.this.preloadActionModeClasses();
                    BaseNoteListPageFragment.this.preloadActionModeMenu();
                    BaseNoteListPageFragment.this.preloadActionModeDrawables();
                    BaseNoteListPageFragment.this.preloadMiSymbolFont();
                    return false;
                } catch (Exception e) {
                    Logger.INSTANCE.d(BaseNoteListPageFragment.this.getTAG(), "preloadActionModeResources error: " + e.getMessage());
                    return false;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preloadActionModeClasses() {
        String[] strArr = {"com.miui.notes.ui.widget.EditableRecyclerViewWrapper$MultiChoiceModeListener", "com.miui.notes.ui.widget.EditableRecyclerViewWrapper$MultiChoiceModeListenerWrapper", "com.miui.notes.page.BaseNoteListPageFragment$EditModeCallback", "com.miui.notes.page.BaseNoteListPageFragment$EditModeCallbackHandler", "com.miui.notes.page.BaseNoteListPageFragment$CommonFolderEditModeCallbackHandler"};
        for (int i = 0; i < 5; i++) {
            try {
                Class.forName(strArr[i], true, getClass().getClassLoader());
            } catch (ClassNotFoundException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preloadActionModeMenu() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        try {
            PopupMenu popupMenu = new PopupMenu(activity, new View(activity));
            popupMenu.getMenuInflater().inflate(getActionModeNoteListMenuRes(), popupMenu.getMenu());
            Menu menu = popupMenu.getMenu();
            int size = menu.size();
            for (int i = 0; i < size; i++) {
                menu.getItem(i).getIcon();
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preloadActionModeDrawables() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        try {
            int[] iArr = {R.drawable.miuix_action_icon_cancel_light, R.drawable.miuix_action_icon_cancel_dark, R.drawable.miuix_action_icon_select_all_light, R.drawable.miuix_action_icon_select_all_dark, R.drawable.miuix_action_icon_deselect_all_light, R.drawable.miuix_action_icon_deselect_all_dark, R.drawable.miuix_icon_trash_light};
            for (int i = 0; i < 7; i++) {
                context.getDrawable(iArr[i]);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void preloadMiSymbolFont() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        try {
            Typeface.createFromAsset(context.getAssets(), "fonts/misymbol_vf.ttf");
        } catch (Exception unused) {
        }
    }
}
