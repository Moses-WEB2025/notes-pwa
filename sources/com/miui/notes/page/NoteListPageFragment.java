package com.miui.notes.page;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Outline;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.GlobalStat;
import com.miui.common.stat.NoteQueryStat;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.view.ShieldEventLayout;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.hybirdeditor.WebRichEditorClient;
import com.miui.hybirdeditor.jsbridge.HybridEditorScriptInterface;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.adapter.AiSearchCursorAdapter;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ainlsquery.AiQueryCapabilityAccess;
import com.miui.notes.assist.CtaActivityResult;
import com.miui.notes.assist.EmptyNavigatorInfo;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.app.MainHandler;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.basefeature.notelist.BaseNoteListAdapter;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.component.CreateAsrDataDialog;
import com.miui.notes.component.RenameFolderDialog;
import com.miui.notes.component.datafolder.FolderClickCallBack;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.feature.folder.FolderDialogActivity;
import com.miui.notes.feature.notefolderlist.NoteFolderListAdapter;
import com.miui.notes.feature.notelist.view.FolderRecyclerView;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.PermissionNewUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.PhoneNumberCacheHelper;
import com.miui.notes.ui.SyncStateController;
import com.miui.notes.ui.view.BaseNoteRecyclerView;
import com.miui.notes.ui.view.NoteSearchUI;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import com.miui.notes.ui.widget.FloatingActionButton;
import com.miui.pad.feature.notes.handwrite.PadHandWriteFragment;
import com.miui.pad.feature.notes.notelist.SpacesItemDecoration;
import com.miui.support.cardview.CardView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.internal.app.widget.ActionBarImpl;
import miuix.micloudview.MiCloudStateView;
import miuix.micloudview.accounts.ExtraAccountManager;
import miuix.miuixbasewidget.widget.EmptyStateView;
import miuix.navigator.Navigator;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.springback.trigger.BaseTrigger;
import miuix.springback.trigger.DefaultTrigger;
import miuix.springback.view.SpringBackLayout;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NoteListPageFragment extends BaseNoteListPageFragment implements SearchCallback.TextSearch, SearchView.OnQueryTextListener, SearchCallback.OnSearchListener, NoteSearchUI.OnSearchUIListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int REQUEST_CODE_QUERY_ALL = 0;
    public static final int REQUEST_CODE_QUERY_NOTE = 1;
    public static final int REQUEST_CODE_QUERY_PRIVATE_NOTE = 3;
    public static final int REQUEST_CODE_QUERY_SEARCH = 4;
    private static final ExecutorService sExecutorService = Executors.newSingleThreadExecutor();
    private TextView cancleView;
    public BaseNoteListAdapter mAdapter;
    protected View mAiQueryProcess;
    protected EmptyStateView mBlankView;
    protected View mBlankViewContainer;
    protected CreateAsrDataDialog mCreateAsrDataDialog;
    protected String mExitSearchWords;
    protected ShieldEventLayout mFolderLayout;
    protected LinearLayoutManager mFolderLayoutManager;
    protected NoteFolderListAdapter mFolderListAdapter;
    protected FolderRecyclerView mFolderRv;
    protected String mLastSearchText;
    protected LockAction mLockAction;
    protected MiCloudStateView mMiCloudStateView;
    protected NestedHeaderLayout mNestedHeaderLayout;
    protected NoteSearchUI mNoteSearchUI;
    protected BaseNoteRecyclerView mRecyclerView;
    protected EditableRecyclerViewWrapper mRecyclerViewWrapper;
    protected RenameFolderDialog mRenameFolderDialog;
    protected View mRootView;
    protected NoteGridLayoutManager mRvLayoutManager;
    protected View mSearchBar;
    protected TextView mSearchBarTextView;
    protected SearchCallback mSearchModeListener;
    protected String mSearchWords;
    private int mSortWayId;
    protected SpringBackLayout mSpringBackLayout;
    private SyncStateController mSyncStateController;
    private DefaultTrigger mTrigger;
    protected ActivityResultLauncher<Intent> privateActivityLauncher;
    protected ActivityResultLauncher<Intent> searchActivityLauncher;
    protected TextView searchInput;
    protected View tagParent;
    protected ActivityResultLauncher<Intent> toCtaBeforeCloudLauncher;
    private ActivityResultLauncher<Intent> toFolderActivityLauncher;
    private final String TAG = "NoteListPageFragment";
    protected boolean mIsFromPrivate = false;
    private boolean isNeedMonitorScroll = false;
    boolean mIsAutoTriggerOpen = false;
    private int totalScrollY = 0;
    protected CompositeDisposable mPreLoadThemeResDisposable = new CompositeDisposable();
    protected boolean mIsInPrivate = false;
    boolean mCanUseAiQuery = false;
    long mLastChangeTime = 0;
    long mLastChangeId = -2;
    long mLastContentSize = 0;
    long mLastTitleSize = 0;
    protected ContentObserver mAiQueryContentObserver = null;
    protected int dataCount = -1;
    private final boolean registerOnCreate = true;
    private final Handler mainHandler = new MainHandler<NoteListPageFragment>(Looper.getMainLooper(), new WeakReference(this)) { // from class: com.miui.notes.page.NoteListPageFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (getContext() == null || getContext().get() != null) {
                if (message.what == 0) {
                    FloatingActionButton floatingActionButton = message.arg1 == 0 ? NoteListPageFragment.this.mBtnContentAdd : NoteListPageFragment.this.mBtnHandWriteAdd;
                    String string = message.obj.toString();
                    if (floatingActionButton != null) {
                        NoteListPageFragment.this.showCreateBtnViaMsg(floatingActionButton, true, string);
                        if (RomUtils.isPadDevice() && NoteListPageFragment.this.mBtnHandWriteAdd != null) {
                            NoteListPageFragment noteListPageFragment = NoteListPageFragment.this;
                            noteListPageFragment.showCreateBtnViaMsg(noteListPageFragment.mBtnHandWriteAdd, true, string);
                        }
                    }
                }
                if (message.what == 1) {
                    FloatingActionButton floatingActionButton2 = message.arg1 == 0 ? NoteListPageFragment.this.mBtnContentAdd : NoteListPageFragment.this.mBtnHandWriteAdd;
                    String string2 = message.obj.toString();
                    if (floatingActionButton2 != null) {
                        NoteListPageFragment.this.showCreateBtnViaMsg(floatingActionButton2, false, string2);
                        if (!RomUtils.isPadDevice() || NoteListPageFragment.this.mBtnHandWriteAdd == null) {
                            return;
                        }
                        NoteListPageFragment noteListPageFragment2 = NoteListPageFragment.this;
                        noteListPageFragment2.showCreateBtnViaMsg(noteListPageFragment2.mBtnHandWriteAdd, false, string2);
                    }
                }
            }
        }
    };
    protected Runnable mAiQueryRunnable = new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment.2
        @Override // java.lang.Runnable
        public void run() {
            if (NoteListPageFragment.this.homeViewModel == null || !NoteListPageFragment.this.homeViewModel.isAiSearchMode()) {
                return;
            }
            NoteListPageFragment noteListPageFragment = NoteListPageFragment.this;
            noteListPageFragment.onQueryTextChange(noteListPageFragment.mSearchWords);
        }
    };
    protected Runnable mPendingShowDataEmpty = new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment.3
        @Override // java.lang.Runnable
        public void run() {
            NoteListPageFragment.this.mRecyclerView.setVisibility(8);
            NoteListPageFragment.this.mBlankViewContainer.setVisibility(0);
            NoteListPageFragment.this.mSpringBackLayout.setTarget(NoteListPageFragment.this.mBlankViewContainer);
        }
    };
    protected Runnable mPendingStopQueryTask = new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment.4
        @Override // java.lang.Runnable
        public void run() {
            if (NoteListPageFragment.this.isAdded()) {
                NoteListPageFragment.this.getLoaderManager().destroyLoader(0);
                NoteListPageFragment.this.getLoaderManager().destroyLoader(1);
                NoteListPageFragment.this.getLoaderManager().destroyLoader(3);
                NoteListPageFragment.this.getLoaderManager().destroyLoader(4);
                NoteListPageFragment.this.mRecyclerView.suppressLayout(false);
            }
        }
    };
    protected boolean isEmptyList = true;
    private long startTime = 0;
    private final View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: com.miui.notes.page.NoteListPageFragment.13
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Logger.INSTANCE.d("NoteListPageFragment", "mItemClickListener onClick");
            if (NoteListPageFragment.this.mRecyclerViewWrapper.isInActionMode()) {
                NoteListPageFragment.this.mRecyclerViewWrapper.toggleItemChecked(view);
            } else if (NoteListPageFragment.this.mAdapter != null) {
                Utils.hideSoftInput(view);
                NoteListPageFragment.this.mAdapter.frozeData(true);
                NoteListPageFragment.this.openNoteFromClick(view);
                NotesPageStat.QUIT_TYPE = NotesPageStat.GO_DETAILS;
            }
        }
    };
    private final View.OnLongClickListener mItemLongClickListener = new View.OnLongClickListener() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda20
        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            return this.f$0.onLongClickImpl(view);
        }
    };
    protected Disposable mDispose = null;
    private MiCloudStateView.ILayoutUpdateListener mLayoutUpdateListener = new AnonymousClass21();

    protected abstract void addNote(View view);

    abstract void deliverIntent(Intent intent);

    @Override // com.miui.notes.ui.view.SearchCallback.TextSearch
    public void finishIfNeeded() {
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected int getActionModeNoteListMenuRes() {
        return R.menu.notes_list_options;
    }

    protected int getLayoutRes() {
        return R.layout.new_phone_note_list_fragment;
    }

    protected int getThemeRes() {
        return R.style.NavigatorContentChildTheme;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimUpdate(boolean z, float f) {
    }

    protected abstract void openNoteFromClick(View view);

    protected abstract void openSearchResultNote(View view, NoteCache noteCache, String str);

    protected void setBlankViewImageAndText() {
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected BaseNoteListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    public EditableRecyclerViewWrapper getRecyclerWrapper() {
        return this.mRecyclerViewWrapper;
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected void setPasswordSuccess(int i) {
        if (5102 != i) {
            if (5101 == i) {
                this.mPasswordController.confirmPrivacyPassword(i);
            }
        } else {
            EditableRecyclerViewWrapper editableRecyclerViewWrapper = this.mRecyclerViewWrapper;
            if (editableRecyclerViewWrapper != null) {
                hideCheckedNotes(editableRecyclerViewWrapper.getCheckedItemIds());
            }
        }
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerDataObserver();
        Logger.INSTANCE.d("NoteListPageFragment", "onCreate");
        setThemeRes(getThemeRes());
        FragmentActivity activity = getActivity();
        this.mBindContext = new BindContext();
        this.mBindContext.setAiQueryDBProgress(-1);
        this.mSortWayId = PreferenceUtils.getNoteSortWay(activity, 1);
        initAdapterIfNeeded();
        this.mIsInPrivate = false;
        PreferenceUtils.setIsInPrivate(false);
        setExtraHorizontalPaddingEnable(true);
        this.privateActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda18
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$onCreate$1((ActivityResult) obj);
            }
        });
        initSearchActivityLauncher();
        Logger.INSTANCE.d("NoteListPageFragment", "onCreate end");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(ActivityResult activityResult) {
        Logger.INSTANCE.d("NoteListPageFragment", "privateActivityLauncher :" + activityResult.getResultCode());
        if (activityResult.getResultCode() == 10) {
            final NoteLoadDataEntity selectedNote = this.homeViewModel.getSelectedNote();
            if (Navigator.get(this) == null || selectedNote == null || 2 != selectedNote.getNoteType()) {
                return;
            }
            FragmentManager fragmentManager = Navigator.get(this).getByTag(Navigator.TAG_SECONDARY_CONTENT).getFragmentManager();
            Fragment fragmentFindFragmentByTag = fragmentManager.findFragmentByTag(Navigator.TAG_SECONDARY_CONTENT);
            if (fragmentFindFragmentByTag instanceof PadHandWriteFragment) {
                fragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
                final boolean z = Navigator.get(this).getNavigationMode() != Navigator.Mode.C;
                this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCreate$0(selectedNote, z);
                    }
                }, 100L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        this.homeViewModel.setSelectNote(noteLoadDataEntity, z, true);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Logger.INSTANCE.d("NoteListPageFragment", "onInflateView");
        super.onInflateView(layoutInflater, viewGroup, bundle);
        setExtraHorizontalPaddingEnable(true);
        View viewInflate = layoutInflater.inflate(getLayoutRes(), viewGroup, false);
        this.homeViewModel.getIntent().observe(this, new Observer() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.deliverIntent((Intent) obj);
            }
        });
        return viewInflate;
    }

    private void registerDataObserver() {
        Logger.INSTANCE.d("NoteListPageFragment", "registerDataObserver");
        this.homeViewModel.getFolderIdLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda23
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$registerDataObserver$2((Long) obj);
            }
        });
        this.homeViewModel.getToFolderListLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda24
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$registerDataObserver$3((Boolean) obj);
            }
        });
        this.homeViewModel.getPageSelectedLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda25
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$registerDataObserver$4((Integer) obj);
            }
        });
        LiveDataEventBus.with(LiveDataEventBus.SELECT_POSITION, Integer.class).observe(this, new Observer() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$registerDataObserver$5((Integer) obj);
            }
        });
        this.homeViewModel.getStopSearch().observe(this, new Observer() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$registerDataObserver$6((Boolean) obj);
            }
        });
        if (this.mIsFromPrivate) {
            return;
        }
        queryFolderInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDataObserver$2(Long l) {
        Logger.INSTANCE.d("NoteListPageFragment", "FoldId Observe:" + l);
        if (l.longValue() != -4 && getContext() != null) {
            PreferenceUtils.setLongPreference(getContext(), PreferenceUtils.PREF_LAST_FOLDER_ID, l.longValue());
        }
        if (this.homeViewModel.getPageSelected() == 0) {
            Navigator.get(this).navigate(new EmptyNavigatorInfo((int) (l.longValue() - (-4097))));
        }
        requestListData(0L);
        if (!this.homeViewModel.isEditStatus() && this.homeViewModel.getPageSelected() == 0) {
            showCreateBtn(computeMenuAddVisibility() == 0, "NoteListPageFragment.registerDataObserver with hm: " + this.homeViewModel);
        }
        NoteFolderListAdapter noteFolderListAdapter = this.mFolderListAdapter;
        if (noteFolderListAdapter != null) {
            noteFolderListAdapter.refreshSelectedFolder(l.longValue(), this.mFolderRv, this.mFolderLayoutManager, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDataObserver$3(Boolean bool) {
        if (bool.booleanValue()) {
            clickFolderList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDataObserver$4(Integer num) {
        Logger.INSTANCE.d("NoteListPageFragment", "onPageSelected change :" + num);
        if (num.intValue() == 1) {
            finishActionMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDataObserver$5(Integer num) {
        if (num.intValue() == 0) {
            this.startTime = System.currentTimeMillis();
            NotesPageStat.reportNotesEnter(getActivity(), NotesPageStat.mFolderId, true);
        } else {
            NotesPageStat.reportNotesQuit(getActivity(), NotesPageStat.mFolderId, System.currentTimeMillis() - this.startTime, "内部切换", true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDataObserver$6(Boolean bool) {
        if (bool.booleanValue() && this.homeViewModel.isSearchMode()) {
            finishActionMode();
        }
    }

    protected void initSearchActivityLauncher() {
        if (this.searchActivityLauncher == null) {
            this.searchActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda22
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj) {
                    this.f$0.lambda$initSearchActivityLauncher$7((ActivityResult) obj);
                }
            });
            this.mBindContext.setSearchActivityLauncher(this.searchActivityLauncher);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSearchActivityLauncher$7(ActivityResult activityResult) {
        this.mBindContext.setIsNeedDestroySearch(false);
    }

    protected void initAiSearchDataChange() {
        if (this.mAiQueryContentObserver == null) {
            this.mAiQueryContentObserver = new ContentObserver(this.handler) { // from class: com.miui.notes.page.NoteListPageFragment.5
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri, int i) {
                    super.onChange(z, uri, i);
                    Logger.INSTANCE.d("NoteListPageFragment", "onContentChange for ai provider ");
                    NoteListPageFragment.this.judgeNoteIsTextChange(uri);
                }
            };
            NoteApp.getInstance().getContentResolver().registerContentObserver(NotesProvider.AI_QUERY_URI, true, this.mAiQueryContentObserver);
        }
    }

    protected void judgeNoteIsTextChange(Uri uri) {
        long j;
        if (this.homeViewModel.isAiSearchMode()) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.mLastChangeTime > 200) {
                Logger.INSTANCE.d("NoteListPageFragment", "change uri ");
                this.mLastChangeTime = jCurrentTimeMillis;
                if (uri != null) {
                    String path = uri.getPath();
                    if (TextUtils.isEmpty(path)) {
                        return;
                    }
                    try {
                        j = Integer.parseInt(path.substring(path.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1));
                    } catch (NumberFormatException e) {
                        Logger.INSTANCE.d("NoteListPageFragment", "NumberFormatException :" + e);
                        j = -2;
                    }
                    if (j == -2) {
                        return;
                    }
                    if (j == this.mLastChangeId) {
                        Cursor cursorQueryById = NoteStore.queryById(NoteApp.getInstance(), j);
                        if (cursorQueryById != null && !cursorQueryById.isClosed()) {
                            cursorQueryById.moveToFirst();
                            try {
                                try {
                                    int iMax = Math.max(Utils.getStringSize(cursorQueryById.getString(4)), Utils.getStringSize(cursorQueryById.getString(19)));
                                    int stringSize = Utils.getStringSize(cursorQueryById.getString(17));
                                    Logger.INSTANCE.d("NoteListPageFragment", "lastContentSize :" + iMax + " lastTitleSize :" + stringSize);
                                    Logger.INSTANCE.d("NoteListPageFragment", "mLastContentSize :" + this.mLastContentSize + "mLastTitleSize :" + this.mLastTitleSize);
                                    long j2 = iMax;
                                    if (j2 != this.mLastContentSize || stringSize != this.mLastTitleSize) {
                                        this.mLastContentSize = j2;
                                        this.mLastTitleSize = stringSize;
                                        cursorQueryById.close();
                                    } else {
                                        cursorQueryById.close();
                                        return;
                                    }
                                } catch (Exception e2) {
                                    Logger.INSTANCE.d("NoteListPageFragment", "cursor :" + e2);
                                }
                            } catch (Throwable th) {
                                cursorQueryById.close();
                                throw th;
                            }
                        }
                        if (cursorQueryById != null) {
                            cursorQueryById.close();
                        }
                    } else {
                        this.mLastChangeId = j;
                        this.mLastContentSize = 0L;
                        this.mLastTitleSize = 0L;
                    }
                    if (this.handler.hasCallbacks(this.mAiQueryRunnable)) {
                        this.handler.removeCallbacks(this.mAiQueryRunnable);
                    }
                    this.handler.postDelayed(this.mAiQueryRunnable, 1000L);
                }
            }
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        super.onContentInsetChanged(rect);
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView == null) {
            return;
        }
        baseNoteRecyclerView.setPadding(baseNoteRecyclerView.getPaddingLeft(), this.mRecyclerView.getPaddingTop(), this.mRecyclerView.getPaddingRight(), rect.bottom);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mBtnContentAdd.getLayoutParams();
        layoutParams.bottomMargin = rect.bottom + getResources().getDimensionPixelSize(R.dimen.floating_action_bar_margin_bottom);
        this.mBtnContentAdd.setLayoutParams(layoutParams);
        if (this.mBtnHandWriteAdd != null) {
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mBtnHandWriteAdd.getLayoutParams();
            layoutParams2.bottomMargin = rect.bottom + getResources().getDimensionPixelSize(R.dimen.floating_action_bar_margin_bottom);
            this.mBtnHandWriteAdd.setLayoutParams(layoutParams2);
        }
    }

    protected void initView(final View view) {
        Logger.INSTANCE.d("NoteListPageFragment", "initView");
        this.mRootView = view;
        this.mBtnContentAdd = (FloatingActionButton) view.findViewById(R.id.note_add);
        AccessibilityDelegateUtil.setButtonClassAccessibilityDelegate(this.mBtnContentAdd);
        this.mBtnContentAdd.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$8(view, view2);
            }
        });
        this.mBtnHandWriteAdd = (FloatingActionButton) view.findViewById(R.id.handwrite_add);
        ((ViewGroup) this.mRootView).setMotionEventSplittingEnabled(false);
        this.mSpringBackLayout = (SpringBackLayout) view.findViewById(R.id.scrollable_view_drawer);
        this.mBlankViewContainer = view.findViewById(R.id.sv_blank);
        this.mBlankView = (EmptyStateView) view.findViewById(R.id.phone_note_list_blank_view);
        this.mRecyclerView = (BaseNoteRecyclerView) view.findViewById(R.id.recycler_view);
        this.mAiQueryProcess = view.findViewById(R.id.ai_query_process);
        this.mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        this.mRecyclerViewWrapper = new EditableRecyclerViewWrapper(this.mRecyclerView);
        initAdapterIfNeeded();
        this.mRecyclerViewWrapper.setAdapter(this.mAdapter);
        this.mBindContext.setEditableRecyclerViewWrapper(this.mRecyclerViewWrapper);
        this.mNestedHeaderLayout = (NestedHeaderLayout) view.findViewById(R.id.nested_header_layout);
        if (!this.mIsFromPrivate) {
            this.mTrigger = new DefaultTrigger(getContext());
            LockAction lockAction = new LockAction();
            this.mLockAction = lockAction;
            this.mTrigger.addAction(lockAction);
            this.mTrigger.attach(this.mSpringBackLayout);
        }
        registerCoordinateScrollView(this.mNestedHeaderLayout);
        if (getActionBar() != null) {
            getActionBar().registerCoordinatedScrollView(this.mNestedHeaderLayout);
        }
        this.mNestedHeaderLayout.setNestedHeaderChangedListener(new NestedHeaderLayout.NestedHeaderChangedListener() { // from class: com.miui.notes.page.NoteListPageFragment.6
            @Override // miuix.nestedheader.widget.NestedHeaderLayout.NestedHeaderChangedListener
            public void onOverViewBlurStateChanged(boolean z) {
                ActionBar actionBar = NoteListPageFragment.this.getActionBar();
                if (actionBar != null) {
                    ((ActionBarImpl) actionBar).getActionBarContainer().updateBackground(z);
                }
            }
        });
        MiCloudStateView miCloudStateView = (MiCloudStateView) view.findViewById(R.id.micloud_state_view);
        this.mMiCloudStateView = miCloudStateView;
        miCloudStateView.setLayoutUpdateListener(this.mLayoutUpdateListener);
        this.mSyncStateController = new SyncStateController(this.mMiCloudStateView);
        this.mMiCloudStateView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$9(view2);
            }
        });
        if (isHideCloudGuide()) {
            this.mMiCloudStateView.setVisibility(8);
        }
        View viewFindViewById = view.findViewById(R.id.search_bar);
        this.mSearchBar = viewFindViewById;
        this.mSearchBarTextView = (TextView) viewFindViewById.findViewById(android.R.id.input);
        this.mSearchBar.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initView$10(view2);
            }
        });
        this.mSearchBarTextView.setHint(R.string.search_hint);
        initFolderList(view);
        refreshActionEnterPoint();
        Logger.INSTANCE.d("NoteListPageFragment", "initView end");
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.notes.page.NoteListPageFragment.7
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                NoteListPageFragment.this.totalScrollY += i2;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    Logger.INSTANCE.d("RecyclerViewScroll", "Scroll stopped. Total scroll X: " + NoteListPageFragment.this.totalScrollY);
                }
            }
        });
        this.homeViewModel.setIsPassword(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(View view, View view2) {
        bindComposeAction(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9(View view) {
        if (Utils.isFastClick(view.getId(), 500L)) {
            return;
        }
        if (!PreferenceUtils.isCTAAccepted(getContext()) && PermissionUtils.supportNewPermissionStyle()) {
            PermissionNewUtils.showCtaDialog(this.toCtaBeforeCloudLauncher);
        } else {
            fragmentViewCloud();
        }
        NotesPageStat.reportClickEvent(NotesPageStat.TIP_HOMEPAGE_MICLOUD_CLICK, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$10(View view) {
        Logger.INSTANCE.d("NoteListPageFragment", "search bar on click");
        if (Utils.isFastClick(view.getId(), 300L)) {
            return;
        }
        BaseHybridNoteEditFragment.removeAiFloatingWindow();
        initAiQueryAndStartSearch(view);
    }

    protected void initAiQuery(final View view) {
        Logger.INSTANCE.d("NoteListPageFragment", "initAiQuery");
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.lambda$initAiQuery$11(view, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<Object>() { // from class: com.miui.notes.page.NoteListPageFragment.8
            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Object obj) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                NoteListPageFragment.this.mPreLoadThemeResDisposable.add(disposable);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                Logger.INSTANCE.d("NoteListPageFragment", "onComplete:,mCanUseAiQuery :" + NoteListPageFragment.this.mCanUseAiQuery);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAiQuery$11(View view, ObservableEmitter observableEmitter) throws Exception {
        if (AiQueryCapabilityAccess.INSTANCE.getInstance().isSupportAiQuery()) {
            this.mCanUseAiQuery = AiQueryCapabilityAccess.INSTANCE.getInstance().isAiModelInitSuccess();
        }
        if (view != null) {
            onSearchRequest(view, false, "");
        }
        observableEmitter.onComplete();
    }

    protected void initAiQueryAndStartSearch(final View view) {
        Logger.INSTANCE.d("NoteListPageFragment", "initAiQueryAndStartSearch");
        EditableRecyclerViewWrapper editableRecyclerViewWrapper = this.mRecyclerViewWrapper;
        if (editableRecyclerViewWrapper == null || !editableRecyclerViewWrapper.isInActionMode()) {
            this.mNestedHeaderLayout.setAutoTriggerClose(false);
            Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda17
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    this.f$0.lambda$initAiQueryAndStartSearch$12(observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<Object>() { // from class: com.miui.notes.page.NoteListPageFragment.9
                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Object obj) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    NoteListPageFragment.this.mPreLoadThemeResDisposable.add(disposable);
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    Logger.INSTANCE.d("NoteListPageFragment", "initAiQueryAndStartSearch onComplete, mCanUseAiQuery:" + NoteListPageFragment.this.mCanUseAiQuery);
                    if (view == null || !NoteListPageFragment.this.isAdded()) {
                        return;
                    }
                    if (NoteListPageFragment.this.mNoteSearchUI == null) {
                        NoteListPageFragment noteListPageFragment = NoteListPageFragment.this;
                        NoteListPageFragment noteListPageFragment2 = NoteListPageFragment.this;
                        FragmentManager parentFragmentManager = noteListPageFragment2.getParentFragmentManager();
                        NoteListPageFragment noteListPageFragment3 = NoteListPageFragment.this;
                        noteListPageFragment.mNoteSearchUI = new NoteSearchUI(noteListPageFragment2, parentFragmentManager, noteListPageFragment3, noteListPageFragment3.mBindContext);
                    }
                    NoteListPageFragment.this.mNoteSearchUI.startSearchMode(view, NoteListPageFragment.this.mNestedHeaderLayout.getScrollableView());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAiQueryAndStartSearch$12(ObservableEmitter observableEmitter) throws Exception {
        if (AiQueryCapabilityAccess.INSTANCE.getInstance().isSupportAiQuery()) {
            this.mCanUseAiQuery = AiQueryCapabilityAccess.INSTANCE.getInstance().isAiModelInitSuccess();
        }
        observableEmitter.onComplete();
    }

    @Override // com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onSearchItemClick(View view, Object obj, int i) {
        Logger.INSTANCE.d("NoteListPageFragment", "onSearchItemClick via NoteSearchUI, position:" + i);
        if (obj instanceof ItemCache) {
            ItemCache itemCache = (ItemCache) obj;
            if (itemCache.getCacheType() == 0) {
                NoteCache noteCache = (NoteCache) itemCache.getCacheObject();
                String searchTokenBackUp = TextUtils.isEmpty(this.mBindContext.getSearchToken()) ? this.mBindContext.getSearchTokenBackUp() : this.mBindContext.getSearchToken();
                this.homeViewModel.setClickedNotePosition(i);
                openSearchResultNote(view, noteCache, searchTokenBackUp);
            }
        }
    }

    @Override // com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onEnterSearchMode() {
        Logger.INSTANCE.d("NoteListPageFragment", "onEnterSearchMode via NoteSearchUI");
        if (this.mBtnContentAdd.getVisibility() == 0) {
            showCreateBtn(false, "NoteListPageFragment.onEnterSearchMode");
        }
        this.homeViewModel.setSearchMode(true);
        this.homeViewModel.setVpScrollable(false);
        disablePullDown();
        if (Navigator.get(this) != null && Navigator.get(this).getBottomTabView() != null) {
            Navigator.get(this).getBottomTabView().setVisibility(8);
        }
        NotesPageStat.reportSearch();
    }

    @Override // com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onExitSearchMode() {
        Logger.INSTANCE.d("NoteListPageFragment", "onExitSearchMode via NoteSearchUI");
        this.homeViewModel.setSearchMode(false);
        this.homeViewModel.setAiSearchMode(false);
        this.homeViewModel.setVpScrollable(true);
        this.mBindContext.setSearchToken("");
        this.mBindContext.setAiQueryNoteList(null);
        this.mBindContext.setAiQueryDBProgress(-1);
        showCreateBtn(computeMenuAddVisibility() == 0, "NoteListPageFragment.onExitSearchMode");
        enablePullDown();
        if (Navigator.get(this) != null && Navigator.get(this).getBottomTabView() != null) {
            Navigator.get(this).getBottomTabView().setVisibility(0);
        }
        if (getView() != null) {
            getView().post(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onExitSearchMode$13();
                }
            });
            return;
        }
        getLoaderManager().destroyLoader(4);
        requestListData(0L);
        AiQueryCapabilityAccess.INSTANCE.getInstance().destroySearchActivity(this.mBindContext, getActivity(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onExitSearchMode$13() {
        if (isAdded()) {
            getLoaderManager().destroyLoader(4);
            requestListData(0L);
            AiQueryCapabilityAccess.INSTANCE.getInstance().destroySearchActivity(this.mBindContext, getActivity(), false);
        }
    }

    @Override // com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onSearchQuery(final String str) {
        Logger.INSTANCE.d("NoteListPageFragment", "onSearchQuery via NoteSearchUI: " + str);
        if (TextUtils.isEmpty(str)) {
            this.mBindContext.setSearchToken(str);
            this.mBindContext.setAiQueryNoteList(null);
            this.mBindContext.setAiQueryIdArr(null);
            this.mBindContext.setAiQuerySelection(null);
            this.mBindContext.setAiQueryDBProgress(-1);
            this.homeViewModel.setAiSearchMode(false);
            requestSearchData(0L);
            return;
        }
        this.mBindContext.setSearchToken(str);
        this.mExitSearchWords = str;
        final long jCurrentTimeMillis = System.currentTimeMillis();
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda19
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.lambda$onSearchQuery$14(str, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<Pair<Boolean, String>>() { // from class: com.miui.notes.page.NoteListPageFragment.10
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                if (NoteListPageFragment.this.mDispose != null) {
                    NoteListPageFragment.this.mDispose.dispose();
                }
                NoteListPageFragment.this.mDispose = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Pair<Boolean, String> pair) {
                if (!((Boolean) pair.first).booleanValue()) {
                    NoteListPageFragment.this.mBindContext.setAiQueryNoteList(null);
                    NoteListPageFragment.this.homeViewModel.setAiSearchMode(false);
                } else {
                    NoteListPageFragment.this.homeViewModel.setAiSearchMode(true);
                }
                if (TextUtils.isEmpty(NoteListPageFragment.this.mBindContext.getSearchToken())) {
                    NoteListPageFragment.this.mBindContext.setAiQueryDBProgress(-1);
                }
                NoteListPageFragment.this.requestSearchData(jCurrentTimeMillis);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public /* synthetic */ void lambda$onSearchQuery$14(String str, ObservableEmitter observableEmitter) throws Exception {
        Pair pair;
        ArrayList<Long> aiQueryNoteIdArr;
        String str2;
        if (AiQueryCapabilityAccess.INSTANCE.getInstance().isSupportAiQuery() && getContext() != null && this.homeViewModel.isSearchMode()) {
            kotlin.Pair<Boolean, Integer> progress = AiQueryCapabilityAccess.INSTANCE.getInstance().getProgress();
            if (progress.getSecond().intValue() < 100) {
                this.mBindContext.setAiQueryProgress(progress.getFirst().booleanValue(), progress.getSecond().intValue());
            }
        }
        String str3 = null;
        ArrayList<Long> arrayList = null;
        if (!TextUtils.isEmpty(str) && this.mCanUseAiQuery && AiQueryCapabilityAccess.INSTANCE.getInstance().isSupportAiQuery()) {
            if (getContext() != null) {
                kotlin.Pair<Boolean, String> pairStartQuery = AiQueryCapabilityAccess.INSTANCE.getInstance().startQuery(str, 500, new String[0]);
                if (pairStartQuery.getFirst().booleanValue() && !TextUtils.isEmpty(this.mBindContext.getSearchToken())) {
                    String second = pairStartQuery.getSecond();
                    aiQueryNoteIdArr = AiQueryCapabilityAccess.INSTANCE.getInstance().getAiQueryNoteIdArr();
                    str3 = second;
                } else {
                    observableEmitter.onNext(new Pair(false, null));
                    observableEmitter.onComplete();
                    return;
                }
            } else {
                aiQueryNoteIdArr = null;
            }
            this.mBindContext.setAiQueryNoteList(AiQueryCapabilityAccess.INSTANCE.getInstance().getAiQueryNoteList());
            pair = new Pair(true, str3);
            String str4 = str3;
            arrayList = aiQueryNoteIdArr;
            str2 = str4;
        } else {
            pair = new Pair(false, null);
            str2 = null;
        }
        this.mBindContext.setAiQueryIdArr(arrayList);
        this.mBindContext.setAiQuerySelection(str2);
        observableEmitter.onNext(pair);
        observableEmitter.onComplete();
    }

    protected void requestSearchData(long j) {
        Logger.INSTANCE.d("NoteListPageFragment", "requestSearchData, aiSearchMode:" + this.homeViewModel.isAiSearchMode());
        if (isAdded()) {
            if (this.homeViewModel.isAiSearchMode()) {
                NoteStore.aiQuery(LoaderManager.getInstance(this), this.mBindContext.getAiQuerySelection(), 4, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.page.NoteListPageFragment.11
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
                    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                        if ((!NoteListPageFragment.this.homeViewModel.isSearchMode() && !NoteListPageFragment.this.homeViewModel.isAiSearchMode()) || cursor == null || cursor.isClosed()) {
                            return;
                        }
                        NoteListPageFragment.this.homeViewModel.setSearchDataCursor(cursor);
                    }
                });
            } else {
                this.homeViewModel.startSearchQuery(LoaderManager.getInstance(this), this.homeViewModel.getFolderId(), this.mBindContext, 4, j);
            }
        }
    }

    public void bindComposeAction(View view) {
        Logger.INSTANCE.d("NoteListPageFragment", "onBtnContentAdd click");
        if (Utils.isFastClick(view.getId(), 400L)) {
            return;
        }
        BaseHybridNoteEditFragment.removeAiFloatingWindow();
        if (UIUtils.isBrowseMode(getActivity())) {
            Toast.makeText(getContext(), R.string.warn_enter_browse_mode_forbid_create, 0).show();
            return;
        }
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            baseNoteRecyclerView.stopScroll();
        }
        this.mAdapter.frozeData(true);
        if (!LiteUtils.isLiteOrMiddle() && !LiteUtils.isNewLiteOrMiddle()) {
            sExecutorService.execute(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bindComposeAction$15();
                }
            });
        }
        addNote(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindComposeAction$15() {
        WebSettings.getDefaultUserAgent(NoteApp.getInstance());
        new WebRichEditorClient(getContext());
        HybridEditorScriptInterface.initStaticInfo();
        LiteUtils.isSuperLite();
        LiteUtils.isNewLiteOrMiddle();
        LiteUtils.isLiteOrMiddle();
        RomUtils.isPadMode();
        BaseNormalNoteEditFragment.mActionbarView = LayoutInflater.from(NoteApp.getInstance()).inflate(R.layout.v12_phone_edit_header, (ViewGroup) null);
    }

    private void setAccessibilityForCreateBtn() {
        if (this.mBtnContentAdd != null) {
            this.mBtnContentAdd.setContentDescription(getResources().getString(R.string.app_widget_name_create_note));
            this.mBtnContentAdd.performAccessibilityAction(128, null);
            this.mBtnContentAdd.setAccessibilityTraversalBefore(R.id.web_view);
        }
        if (this.mBtnHandWriteAdd != null) {
            this.mBtnHandWriteAdd.setAccessibilityTraversalBefore(R.id.web_view);
        }
    }

    private void initFolderList(View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ShieldEventLayout shieldEventLayout = (ShieldEventLayout) view.findViewById(R.id.folder_layout);
        this.mFolderLayout = shieldEventLayout;
        if (this.mIsFromPrivate) {
            shieldEventLayout.setVisibility(8);
            return;
        }
        setFolderVisible();
        View viewFindViewById = view.findViewById(R.id.folder_tag_parent);
        this.tagParent = viewFindViewById;
        viewFindViewById.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.NoteListPageFragment.12
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), 0);
            }
        });
        this.tagParent.setClipToOutline(true);
        FolderRecyclerView folderRecyclerView = (FolderRecyclerView) view.findViewById(R.id.folder_rv);
        this.mFolderRv = folderRecyclerView;
        folderRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.data_folder_list_item_space), getResources().getDimensionPixelOffset(R.dimen.pad_folder_list_item_space_last)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NoteApp.getInstance(), 0, false);
        this.mFolderLayoutManager = linearLayoutManager;
        this.mFolderRv.setLayoutManager(linearLayoutManager);
        NoteFolderListAdapter noteFolderListAdapter = new NoteFolderListAdapter();
        this.mFolderListAdapter = noteFolderListAdapter;
        noteFolderListAdapter.setFolderClick(new FolderClickCallBack() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda10
            @Override // com.miui.notes.component.datafolder.FolderClickCallBack
            public final void openFolder(View view2, long j, String str, boolean z) {
                this.f$0.lambda$initFolderList$16(view2, j, str, z);
            }
        });
        this.mFolderListAdapter.setFolderItemEntities(this.folderItemEntities);
        this.mFolderRv.setAdapter(this.mFolderListAdapter);
        if (!UIUtils.isHyperOsAndAbove()) {
            ImageView imageView = new ImageView(getAppCompatActivity());
            VectorDrawableCompat.create(getResources(), R.drawable.ic_data_folder_list, null).setTint(getAppCompatActivity().getColor(R.color.theme_default_icon_color_n_c_light));
            imageView.setImageResource(R.drawable.ic_data_folder_list);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            layoutParams.topMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_image_ic_margin);
            if (Utils.isRTL()) {
                layoutParams.rightMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_image_ic_margin);
            } else {
                layoutParams.leftMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_image_ic_margin);
            }
            CardView cardView = new CardView(getAppCompatActivity());
            cardView.addView(imageView, layoutParams);
            cardView.setBackgroundColor(getAppCompatActivity().getColor(R.color.pad_folder_list_bg_solid_color));
            cardView.setRadius(getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.data_folder_list_item_bg_radius));
            cardView.setContentDescription(getAppCompatActivity().getString(R.string.manage_folders));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_bg_size), getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_bg_size));
            layoutParams2.leftMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_ic_margin_start);
            layoutParams2.rightMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_ic_margin_end);
            if (Utils.isRTL()) {
                layoutParams2.leftMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_ic_margin_end);
                layoutParams2.rightMargin = getAppCompatActivity().getResources().getDimensionPixelOffset(R.dimen.common_folder_list_ic_margin_start);
            }
            this.mFolderLayout.addView(cardView, layoutParams2);
            cardView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$initFolderList$17(view2);
                }
            });
        }
        Logger.INSTANCE.i(getClass().getSimpleName(), "initFolderList ,cost :" + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFolderList$16(View view, long j, String str, boolean z) {
        if (Utils.isFastClick(view.getId(), 400L)) {
            return;
        }
        if (this.homeViewModel.isSearchMode()) {
            this.mBindContext.setSearchToken("");
            getLoaderManager().destroyLoader(4);
            this.homeViewModel.setSearchMode(false);
        }
        FolderRecyclerView folderRecyclerView = this.mFolderRv;
        if (folderRecyclerView != null) {
            folderRecyclerView.stopScroll();
        }
        openFolder(j);
        scrollToTop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initFolderList$17(View view) {
        clickFolderList();
    }

    public void clickFolderList() {
        Logger.INSTANCE.d("NoteListPageFragment", "clickFolderList");
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            baseNoteRecyclerView.stopScroll();
        }
        showCreateBtn(false, "NoteListPageFragment.clickFolderList");
        gotoPadFolderListDialog();
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected void showCreateBtn(View view, boolean z, String str) {
        Message messageObtain = Message.obtain();
        int i = !z ? 1 : 0;
        messageObtain.what = i;
        messageObtain.arg1 = view == this.mBtnContentAdd ? 0 : 1;
        messageObtain.obj = str;
        this.mainHandler.removeMessages(i);
        Logger.INSTANCE.d("NoteListPageFragment", "showCreateBtnViaMsg,show:" + z + " with source:" + str);
        this.mainHandler.sendMessageDelayed(messageObtain, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreateBtnViaMsg(FloatingActionButton floatingActionButton, boolean z, String str) {
        if (this.homeViewModel.getFolderId() == -3) {
            return;
        }
        Folme.clean(floatingActionButton);
        Folme.use((View) floatingActionButton).touch().setTint(0);
        Folme.use((View) floatingActionButton).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(floatingActionButton, new AnimConfig[0]);
        if (!z || (!RomUtils.isPadDevice() && this.homeViewModel.getPageSelected() != 0)) {
            Logger.INSTANCE.i("NoteListPageFragment", "showCreateBtn,hide!! with source:" + str);
            floatingActionButton.cancelLongPress();
            AnimConfig animConfig = new AnimConfig();
            animConfig.setEase(EaseManager.getStyle(0, 250.0f, 0.95f, 0.3f));
            Folme.use((View) floatingActionButton).visible().setScale(0.8f, IVisibleStyle.VisibleType.HIDE).hide(animConfig);
            return;
        }
        Logger.INSTANCE.i("NoteListPageFragment", "showCreateBtn,show!! with source:" + str);
        floatingActionButton.setVisibility(0);
        floatingActionButton.setScaleX(0.8f);
        floatingActionButton.setScaleY(0.8f);
        Folme.use((View) floatingActionButton).visible().show(new AnimConfig[0]);
        floatingActionButton.setShadowAlpha(0);
        floatingActionButton.showShadow();
        Folme.use((View) floatingActionButton).state().to(ViewProperty.SCALE_X, Float.valueOf(1.0f)).to(ViewProperty.SCALE_Y, Float.valueOf(1.0f));
    }

    private void initAdapterIfNeeded() {
        if (this.mAdapter == null) {
            BaseNoteListAdapter baseNoteListAdapter = new BaseNoteListAdapter(this.mIsFromPrivate);
            this.mAdapter = baseNoteListAdapter;
            baseNoteListAdapter.setOnClickListener(this.mItemClickListener);
            this.mAdapter.setOnLongClickListener(this.mItemLongClickListener);
            this.mAdapter.setBindContext(this.mBindContext);
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        refreshActionEnterPoint();
    }

    protected boolean onLongClickImpl(View view) {
        if (this.mActionMode != null || !canEnterEditMode()) {
            return false;
        }
        RenameFolderDialog renameFolderDialog = this.mRenameFolderDialog;
        if (renameFolderDialog != null && renameFolderDialog.isShowing()) {
            return false;
        }
        if (computeMenuAddVisibility() == 0) {
            showCreateBtn(false, "NoteListPageFragmentonLongClickImpl");
        }
        updateFolderLayout(false);
        this.mRecyclerViewWrapper.setItemChecked(view, true);
        this.mRecyclerViewWrapper.startActionMode(createEditModeCallback(), this);
        this.mSearchBar.setEnabled(false);
        return true;
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        initView(view);
        setExtraHorizontalPaddingEnable(true);
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    public void requestListData(long j) {
        Logger.INSTANCE.d("NoteListPageFragment", "requestListData,aiSearchMode:" + this.homeViewModel.isAiSearchMode() + ",startTime:" + j);
        if (isAdded()) {
            int i = !this.homeViewModel.isSearchMode() ? 1 : 0;
            if (this.homeViewModel.isAiSearchMode()) {
                startAiQuery(LoaderManager.getInstance(this), this.mBindContext.getAiQuerySelection(), 0, j);
            } else {
                this.homeViewModel.startQuery(LoaderManager.getInstance(this), this.homeViewModel.getFolderId(), this.mBindContext, i, j);
            }
        }
    }

    protected void onNotesLoadFinished(Cursor cursor) {
        SearchCallback searchCallback;
        Logger.INSTANCE.d("NoteListPageFragment", "onNotesLoadFinished ,cursor data:" + cursor.hashCode() + ",isAiSearchMode:" + this.homeViewModel.isAiSearchMode());
        this.dataCount = cursor.getCount();
        if (this.mRecyclerView.removeCallbacks(this.mPendingStopQueryTask)) {
            this.mRecyclerView.suppressLayout(false);
        }
        this.mAdapter.notifyDataSetChanged();
        if (this.homeViewModel.isAiSearchMode()) {
            this.mAdapter.setAiSearch(true);
            this.mAdapter.setCursor(new AiSearchCursorAdapter(cursor, this.mBindContext.getAiQueryIdArr()));
        } else {
            this.mAdapter.setAiSearch(false);
            this.mAdapter.setCursor(cursor);
        }
        onDataEmpty(cursor.getCount() == 0);
        splitPageHandle(cursor);
        this.mMiCloudStateView.setLayoutUpdateListener(this.mLayoutUpdateListener);
        this.mSyncStateController.onDataSetChanged();
        String str = this.mSearchWords;
        if (str != null && ((searchCallback = this.mSearchModeListener) == null || !str.equals(searchCallback.getSearchText()))) {
            SearchCallback searchCallback2 = this.mSearchModeListener;
            if (searchCallback2 != null) {
                searchCallback2.removeTextChangedListener();
            }
            setSearchText(this.mSearchWords);
        }
        this.mSearchWords = null;
    }

    public void openFolder(long j) {
        NotesPageStat.mFolderId = j;
        openFolderByController(j);
    }

    protected void openFolderByController(long j) {
        if (this.homeViewModel.getFolderId() != j) {
            deleteEmptyNote();
        }
        this.homeViewModel.setFolderId(j);
    }

    private void gotoPadFolderListDialog() {
        Utils.hideSoftInput(getView());
        FolderDialogActivity.INSTANCE.open(getContext(), this.toFolderActivityLauncher, this.homeViewModel.getFolderId());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (RomUtils.isPadDevice()) {
            Logger.INSTANCE.d("NoteListPageFragment", "onStart->requestListData");
            requestListData(0L);
        }
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        Logger.INSTANCE.d("NoteListPageFragment", "onResume");
        super.onResume();
        setAccessibilityForCreateBtn();
        this.startTime = System.currentTimeMillis();
        int noteSortWay = PreferenceUtils.getNoteSortWay(getActivity(), 1);
        if (noteSortWay != this.mSortWayId) {
            Logger.INSTANCE.d("NoteListPageFragment", "onResume,sort id change " + this.mSortWayId + ",new sort id:" + noteSortWay);
            this.mSortWayId = noteSortWay;
            requestListData(0L);
        }
        GlobalStat.Status.idGrid = PreferenceUtils.getGridMode(getActivity());
        this.mSyncStateController.onResume();
        if (isHideCloudGuide()) {
            this.mMiCloudStateView.setVisibility(8);
        } else if (!this.homeViewModel.isSearchMode()) {
            this.mMiCloudStateView.setVisibility(0);
        }
        if (NotesListActivity.sSelectedPageIndex == 0 && !this.homeViewModel.isSearchMode()) {
            NotesPageStat.reportNotesEnter(getActivity(), NotesPageStat.mFolderId, true);
        }
        if (this.mBindContext == null || !this.mBindContext.getCheckAiQueryProgress()) {
            return;
        }
        this.mBindContext.setCheckAiQueryProgress(false);
        if (getContext() != null) {
            kotlin.Pair<Boolean, Integer> progress = AiQueryCapabilityAccess.INSTANCE.getInstance().getProgress();
            Logger.INSTANCE.d("NoteListPageFragment", "onResume,progress:" + progress.getSecond());
            if (progress.getSecond().intValue() < 100) {
                this.mBindContext.setAiQueryProgress(progress.getFirst().booleanValue(), progress.getSecond().intValue());
            } else {
                this.mBindContext.setAiQueryDBProgress(-1);
            }
        }
        if (this.isEmptyList) {
            refreshAiQueryProcess();
            return;
        }
        BaseNoteListAdapter baseNoteListAdapter = this.mAdapter;
        if (baseNoteListAdapter != null) {
            baseNoteListAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mSearchBarTextView.setText("");
    }

    @Override // com.miui.notes.page.BaseListPageFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mRecyclerView.stopScroll();
        this.mAdapter.resetDataSetChangedTime();
        this.mSyncStateController.onPause();
        notifyTakeSnapshotQs();
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    public void updateFolderList() {
        if (this.mIsFromPrivate) {
            return;
        }
        setFolderVisible();
        Logger.INSTANCE.d("NoteListPageFragment", "updateFolderList");
        if (this.mFolderListAdapter == null || this.homeViewModel == null) {
            return;
        }
        this.mFolderListAdapter.setFolderItemEntities(this.folderItemEntities);
        this.mFolderListAdapter.notifyDataSetChanged();
        this.mFolderRv.requestLayout();
        if (this.mFolderListAdapter.findFolderPos(this.homeViewModel.getFolderId()) == -1) {
            if (Navigator.get(this).getNavigationMode() == Navigator.Mode.NLC && this.homeViewModel.getFolderId() == 0) {
                Logger.INSTANCE.d("NoteListPageFragment", "setFolderId " + this.homeViewModel.getFolderId());
                this.homeViewModel.setFolderId(0L);
            } else {
                Logger.INSTANCE.d("NoteListPageFragment", "setFolderId default");
                this.homeViewModel.setFolderId(NoteConfig.getDefaultFolderId());
            }
        }
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected void updateFolderLayout(boolean z) {
        ShieldEventLayout shieldEventLayout;
        if (this.mIsFromPrivate || (shieldEventLayout = this.mFolderLayout) == null) {
            return;
        }
        int i = z ? 1 : 4;
        shieldEventLayout.setShieldEvent(!z);
        this.mFolderLayout.setImportantForAccessibility(i);
    }

    protected void updateFolderLayoutVisible(boolean z) {
        if (z) {
            this.mFolderLayout.setShieldEvent(false);
            setFolderVisible();
        } else {
            this.mFolderLayout.setShieldEvent(true);
            this.mFolderLayout.setVisibility(8);
        }
    }

    protected void setFolderVisible() {
        if (this.mIsFromPrivate || (UIUtils.isHyperOsAndAbove() && this.folderItemEntities.size() <= 1)) {
            this.mFolderLayout.setVisibility(8);
        } else {
            this.mFolderLayout.setVisibility(0);
        }
    }

    public void hideNote(long j) {
        if (this.mPasswordController != null) {
            this.mPasswordController.setPassword(5102);
        }
    }

    private void notifyTakeSnapshotQs() {
        LiteUtils.isSuperLite();
    }

    private void splitPageHandle(Cursor cursor) {
        if (this.isNeedMonitorScroll || cursor.getCount() <= 1000) {
            return;
        }
        this.isNeedMonitorScroll = true;
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.miui.notes.page.NoteListPageFragment.14
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                int[] iArr = new int[3];
                NoteListPageFragment.this.mRvLayoutManager.findFirstVisibleItemPositions(iArr);
                int noteNumLimit = NoteApp.getInstance().getNoteNumLimit();
                int iMax = 0;
                for (int i3 = 0; i3 < 3; i3++) {
                    iMax = Math.max(iArr[i3], iMax);
                }
                if (noteNumLimit - iMax < 20) {
                    NoteApp.getInstance().addNoteNumLimit();
                    if (NoteListPageFragment.this.mAdapter != null) {
                        NoteListPageFragment.this.mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    protected void onDataEmpty(boolean z) {
        Logger.INSTANCE.d("NoteListPageFragment", "onDataEmpty:" + z);
        this.isEmptyList = z;
        this.mBlankView.setVisibility(z ? 0 : 8);
        if (z) {
            setBlankViewImageAndText();
            refreshAiQueryProcess();
            if (this.mRecyclerView.getVisibility() != 8) {
                this.mAdapter.setSelectId(-1L);
                this.mAdapter.notifyDataSetChanged();
                this.handler.postDelayed(this.mPendingShowDataEmpty, 300L);
                return;
            }
            return;
        }
        this.handler.removeCallbacks(this.mPendingShowDataEmpty);
        if (this.mRecyclerView.getVisibility() != 0) {
            this.mRecyclerView.setVisibility(0);
            this.mBlankViewContainer.setVisibility(8);
            this.mSpringBackLayout.setTarget(this.mRecyclerView);
        }
    }

    public void refreshAiQueryProcess() {
        if (this.mAiQueryProcess == null || this.mBindContext == null) {
            return;
        }
        int mAiQueryDBProgress = this.mBindContext.getMAiQueryDBProgress();
        TextView textView = (TextView) this.mAiQueryProcess.findViewById(R.id.hint_text);
        int i = 0;
        if (this.mBindContext.getIsNewGetProgress()) {
            View view = this.mAiQueryProcess;
            if (2 != mAiQueryDBProgress && 1 != mAiQueryDBProgress) {
                i = 8;
            }
            view.setVisibility(i);
            if (2 == mAiQueryDBProgress) {
                textView.setText(NoteApp.getInstance().getResources().getString(R.string.new_ai_search_database_building_pause));
            } else if (1 == mAiQueryDBProgress) {
                textView.setText(NoteApp.getInstance().getResources().getString(R.string.ai_search_database_building_progress));
            }
            ((ImageView) this.mAiQueryProcess.findViewById(R.id.hint_image)).setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$refreshAiQueryProcess$18(view2);
                }
            });
            return;
        }
        if (this.mBindContext.isValidAiQueryDBProgress()) {
            this.mAiQueryProcess.setVisibility(0);
            textView.setText(String.format(NoteApp.getInstance().getResources().getString(R.string.note_ai_search_database_building_progress), NumberFormat.getPercentInstance().format(((double) mAiQueryDBProgress) / 100.0d)));
            return;
        }
        this.mAiQueryProcess.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshAiQueryProcess$18(View view) {
        AiQueryCapabilityAccess.INSTANCE.getInstance().startSearchActivity(this.mBindContext);
    }

    protected void scrollToTop() {
        Logger.INSTANCE.d("NoteListPageFragment", "scrollToTop");
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            baseNoteRecyclerView.postDelayed(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$scrollToTop$19();
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scrollToTop$19() {
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            baseNoteRecyclerView.scrollToPosition(0);
        }
    }

    @Override // com.miui.notes.page.BaseListPageFragment
    public boolean onBackPressed() {
        return finishActionMode();
    }

    public void deleteEmptyNote() {
        Logger.INSTANCE.d("NoteListPageFragment", "deleteEmptyNote " + NoteStore.deleteEmpty(NoteApp.getInstance(), null));
    }

    protected void stopQueryNote() {
        Logger.INSTANCE.d("NoteListPageFragment", "stopQueryNote");
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null && !baseNoteRecyclerView.isLayoutSuppressed() && this.mRecyclerView.isAttachedToWindow()) {
            this.mRecyclerView.suppressLayout(true);
            this.mRecyclerView.post(this.mPendingStopQueryTask);
        } else if (isAdded()) {
            getLoaderManager().destroyLoader(0);
            getLoaderManager().destroyLoader(1);
            getLoaderManager().destroyLoader(3);
            getLoaderManager().destroyLoader(4);
        }
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected BaseNoteListPageFragment.EditModeCallback createEditModeCallback() {
        return new BaseNoteListPageFragment.EditModeCallback() { // from class: com.miui.notes.page.NoteListPageFragment.15
            @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                boolean zOnCreateActionMode = super.onCreateActionMode(actionMode, menu);
                NoteListPageFragment.this.disablePullDown();
                return zOnCreateActionMode;
            }

            @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
                NoteListPageFragment.this.enablePullDown();
                if (NoteListPageFragment.this.mSearchBar != null) {
                    NoteListPageFragment.this.mSearchBar.setEnabled(true);
                }
                super.onDestroyActionMode(actionMode);
            }
        };
    }

    private void setSearchText(String str) {
        View view;
        if (str != null) {
            if (!str.isEmpty() || this.mBindContext.getSearchType() == 3) {
                if (this.mSearchModeListener == null && (view = this.mSearchBar) != null) {
                    onSearchRequest(view, true, str);
                    return;
                }
                if (!this.homeViewModel.isSearchMode()) {
                    startActionMode(this.mSearchModeListener);
                }
                realSetSearchText(str);
            }
        }
    }

    protected void onSearchRequest(View view, final boolean z, final String str) {
        Logger.INSTANCE.d("NoteListPageFragment", "onSearchRequest");
        if (this.mRecyclerViewWrapper.isInActionMode()) {
            return;
        }
        Logger.INSTANCE.d("NoteListPageFragment", "onSearchRequest/init search ");
        this.mNestedHeaderLayout.setAutoTriggerClose(false);
        if (this.mSearchModeListener == null) {
            this.mSearchModeListener = new SearchCallback(getActivity(), this, this);
        }
        SearchCallback searchCallback = this.mSearchModeListener;
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        searchCallback.setup(view, nestedHeaderLayout, nestedHeaderLayout.getScrollableView());
        this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSearchRequest$20(z, str);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSearchRequest$20(boolean z, String str) {
        if (this.handler.getContext() == null || !isAdded()) {
            return;
        }
        startActionMode(this.mSearchModeListener);
        if (z) {
            realSetSearchText(str);
        }
    }

    private void realSetSearchText(String str) {
        SearchCallback searchCallback = this.mSearchModeListener;
        if (searchCallback != null) {
            searchCallback.setSearchText(str, false, this.mBindContext.getSearchType() == 2);
        }
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null && baseNoteRecyclerView.isComputingLayout()) {
            return false;
        }
        this.mAdapter.markManualModifyDataTime();
        SearchCallback searchCallback = this.mSearchModeListener;
        if (searchCallback == null) {
            this.homeViewModel.setSearchMode(false);
            return false;
        }
        final String searchText = searchCallback.getSearchText();
        if (searchText == null) {
            this.homeViewModel.setSearchMode(false);
        }
        this.mBindContext.setSearchToken(searchText);
        final long jCurrentTimeMillis = System.currentTimeMillis();
        this.mExitSearchWords = searchText;
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda14
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f$0.lambda$onQueryTextChange$21(searchText, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.Observer<Pair<Boolean, String>>() { // from class: com.miui.notes.page.NoteListPageFragment.16
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                if (NoteListPageFragment.this.mDispose != null) {
                    NoteListPageFragment.this.mDispose.dispose();
                }
                NoteListPageFragment.this.mDispose = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Pair<Boolean, String> pair) {
                if (!((Boolean) pair.first).booleanValue()) {
                    NoteListPageFragment.this.mBindContext.setAiQueryNoteList(null);
                    NoteListPageFragment.this.homeViewModel.setAiSearchMode(false);
                } else {
                    NoteListPageFragment.this.homeViewModel.setAiSearchMode(true);
                }
                if (TextUtils.isEmpty(NoteListPageFragment.this.mBindContext.getSearchToken())) {
                    NoteListPageFragment.this.mBindContext.setAiQueryDBProgress(-1);
                }
                NoteListPageFragment.this.requestListData(jCurrentTimeMillis);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public /* synthetic */ void lambda$onQueryTextChange$21(String str, ObservableEmitter observableEmitter) throws Exception {
        Pair pair;
        ArrayList<Long> aiQueryNoteIdArr;
        String str2;
        if (AiQueryCapabilityAccess.INSTANCE.getInstance().isSupportAiQuery() && getContext() != null && this.homeViewModel.isSearchMode()) {
            kotlin.Pair<Boolean, Integer> progress = AiQueryCapabilityAccess.INSTANCE.getInstance().getProgress();
            if (progress.getSecond().intValue() < 100) {
                this.mBindContext.setAiQueryProgress(progress.getFirst().booleanValue(), progress.getSecond().intValue());
            }
        }
        String str3 = null;
        ArrayList<Long> arrayList = null;
        if (!TextUtils.isEmpty(str) && this.mCanUseAiQuery && AiQueryCapabilityAccess.INSTANCE.getInstance().isSupportAiQuery()) {
            if (getContext() != null) {
                kotlin.Pair<Boolean, String> pairStartQuery = AiQueryCapabilityAccess.INSTANCE.getInstance().startQuery(str, 500, new String[0]);
                if (pairStartQuery.getFirst().booleanValue() && !TextUtils.isEmpty(this.mBindContext.getSearchToken())) {
                    String second = pairStartQuery.getSecond();
                    aiQueryNoteIdArr = AiQueryCapabilityAccess.INSTANCE.getInstance().getAiQueryNoteIdArr();
                    str3 = second;
                } else {
                    observableEmitter.onNext(new Pair(false, null));
                    observableEmitter.onComplete();
                    return;
                }
            } else {
                aiQueryNoteIdArr = null;
            }
            this.mBindContext.setAiQueryNoteList(AiQueryCapabilityAccess.INSTANCE.getInstance().getAiQueryNoteList());
            pair = new Pair(true, str3);
            String str4 = str3;
            arrayList = aiQueryNoteIdArr;
            str2 = str4;
        } else {
            pair = new Pair(false, null);
            str2 = null;
        }
        this.mBindContext.setAiQueryIdArr(arrayList);
        this.mBindContext.setAiQuerySelection(str2);
        observableEmitter.onNext(pair);
        observableEmitter.onComplete();
    }

    @Override // com.miui.notes.ui.view.SearchCallback.TextSearch
    public void onTextSearch(String str, int i) {
        this.homeViewModel.setSearchMode(true);
        this.mSearchWords = str;
        initAiQuery(null);
        if (this.mBindContext != null) {
            this.mAdapter.markManualModifyDataTime();
            this.mBindContext.setSearchToken(str);
            this.mBindContext.setSearchType(i);
            requestListData(0L);
        }
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean z) {
        if (isAdded()) {
            if (z) {
                this.mNestedHeaderLayout.getStickyView().setVisibility(4);
                this.mNestedHeaderLayout.setInSearchMode(true);
            } else {
                this.mNestedHeaderLayout.getStickyView().setVisibility(0);
                this.mRecyclerView.stopScroll();
                updateFolderLayoutVisible(true);
            }
        }
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean z) {
        if (isAdded()) {
            if (z) {
                this.homeViewModel.setVpScrollable(false);
                this.mNestedHeaderLayout.setImportantForAccessibility(0);
                return;
            }
            this.mNestedHeaderLayout.setInSearchMode(false);
            this.homeViewModel.setVpScrollable(true);
            this.mRecyclerView.scrollToPosition(0);
            this.mNestedHeaderLayout.setImportantForAccessibility(0);
            this.mRecyclerView.setImportantForAccessibility(0);
        }
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onCreateSearchMode(ActionMode actionMode, Menu menu) {
        if (this.mBtnContentAdd.getVisibility() == 0) {
            showCreateBtn(false, "NoteListPageFragment.onCreateSearchMode");
        }
        this.homeViewModel.setSearchMode(true);
        disablePullDown();
        View viewFindViewById = getActivity().getWindow().getDecorView().findViewById(R.id.search_panel);
        TextView textView = (TextView) viewFindViewById.findViewById(android.R.id.input);
        this.searchInput = textView;
        textView.setOnFocusChangeListener(new SearchViewFocusChangeListener(this));
        TextView textView2 = (TextView) viewFindViewById.findViewById(R.id.search_text_cancel);
        this.cancleView = textView2;
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(textView2);
        NotesPageStat.reportSearch();
    }

    @Override // com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode actionMode) {
        Logger.INSTANCE.d("NoteListPageFragment", "onDestroySearchMode:" + actionMode + Log.getStackTraceString(new Throwable()));
        this.homeViewModel.setSearchMode(false);
        this.homeViewModel.setAiSearchMode(false);
        if (this.mBindContext != null) {
            this.mBindContext.setSearchToken("");
        }
        BaseNoteListAdapter baseNoteListAdapter = this.mAdapter;
        if (baseNoteListAdapter != null) {
            baseNoteListAdapter.frozeData(true);
        }
        if (this.mBindContext != null) {
            this.mBindContext.setAiQueryNoteList(null);
            this.mBindContext.setAiQueryDBProgress(-1);
        }
        showCreateBtn(computeMenuAddVisibility() == 0, "NoteListPageFragment.onDestroySearchMode");
        enablePullDown();
        finishIfNeeded();
        if (getView() != null) {
            getView().post(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDestroySearchMode$22();
                }
            });
            return;
        }
        getLoaderManager().destroyLoader(4);
        requestListData(0L);
        AiQueryCapabilityAccess.INSTANCE.getInstance().destroySearchActivity(this.mBindContext, getActivity(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroySearchMode$22() {
        if (!isAdded()) {
            BaseNoteListAdapter baseNoteListAdapter = this.mAdapter;
            if (baseNoteListAdapter != null) {
                baseNoteListAdapter.frozeData(false);
                return;
            }
            return;
        }
        getLoaderManager().destroyLoader(4);
        requestListData(0L);
        AiQueryCapabilityAccess.INSTANCE.getInstance().destroySearchActivity(this.mBindContext, getActivity(), false);
    }

    private void startAiQuery(LoaderManager loaderManager, String str, int i, final long j) {
        Logger.INSTANCE.d("NoteListPageFragment", "startAiQuery");
        NoteStore.aiQuery(loaderManager, str, i, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.page.NoteListPageFragment.17
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                if ((NoteListPageFragment.this.homeViewModel.isSearchMode() || NoteListPageFragment.this.homeViewModel.isAiSearchMode()) && !cursor.isClosed()) {
                    NoteListPageFragment.this.homeViewModel.setListDataCursor(cursor);
                    if (!NoteListPageFragment.this.homeViewModel.isSearchMode() || j <= 0) {
                        return;
                    }
                    NoteQueryStat.reportNoteSearchResultEvent(cursor.getCount(), NoteListPageFragment.this.mBindContext.getSearchToken(), (int) (System.currentTimeMillis() - j), true);
                }
            }
        });
    }

    protected class LockAction extends BaseTrigger.SimpleAction {
        private long lastTriggeredTime = System.currentTimeMillis();
        private ImageView mIconBody;
        private ImageView mIconHeader;
        private TextView mLabel;

        public LockAction() {
        }

        @Override // miuix.springback.trigger.BaseTrigger.Action
        protected View onCreateIndicator(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            View viewInflate = layoutInflater.inflate(R.layout.miuix_sbl_simple_indicator, viewGroup, false);
            int dimensionPixelSize = NoteListPageFragment.this.getResources().getDimensionPixelSize(R.dimen.private_entry_text_padding);
            this.mIconHeader = (ImageView) viewInflate.findViewById(R.id.indicator_locked_header);
            this.mIconBody = (ImageView) viewInflate.findViewById(R.id.indicator_locked_body);
            TextView textView = (TextView) viewInflate.findViewById(R.id.label);
            this.mLabel = textView;
            textView.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
            this.mLabel.setGravity(17);
            this.mLabel.setTextAlignment(4);
            return viewInflate;
        }

        @Override // miuix.springback.trigger.BaseTrigger.Action
        protected void onTriggered() {
            Logger.INSTANCE.i("NoteListPageFragment", "Lock onTriggered ");
            if (Boolean.TRUE.equals(NoteListPageFragment.this.homeViewModel.getIsPassword())) {
                Logger.INSTANCE.i("NoteListPageFragment", "Password verification is being performed");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (isFastTriggered(jCurrentTimeMillis) || NoteListPageFragment.this.mPasswordController == null) {
                return;
            }
            NoteListPageFragment.this.homeViewModel.setIsPassword(true);
            NoteListPageFragment.this.mPasswordController.setPassword(5101);
            this.lastTriggeredTime = jCurrentTimeMillis;
        }

        @Override // miuix.springback.trigger.BaseTrigger.Action
        protected void onEntered() {
            Logger.INSTANCE.i("NoteListPageFragment", "Lock onEntered ");
            this.mIconBody.setImageDrawable(NoteListPageFragment.this.getResources().getDrawable(R.drawable.miuix_sbl_simple_indicator_locked_body_gray));
            this.mIconHeader.setImageDrawable(NoteListPageFragment.this.getResources().getDrawable(R.drawable.miuix_sbl_simple_indicator_locked_header_gray));
            this.mLabel.setTextColor(NoteListPageFragment.this.getResources().getColor(R.color.miuix_sbl_locked_text_gray));
            this.mLabel.setText(R.string.private_entry_default);
        }

        @Override // miuix.springback.trigger.BaseTrigger.Action
        protected void onExit() {
            Logger.INSTANCE.i("NoteListPageFragment", "Lock onExit ");
        }

        @Override // miuix.springback.trigger.BaseTrigger.Action
        protected void onFinished() {
            Logger.INSTANCE.i("NoteListPageFragment", "Lock onFinished ");
        }

        @Override // miuix.springback.trigger.BaseTrigger.Action
        protected void onActivated() {
            Logger.INSTANCE.i("NoteListPageFragment", "Lock onActivated ");
            this.mIconBody.setImageDrawable(NoteListPageFragment.this.getResources().getDrawable(R.drawable.miuix_sbl_simple_indicator_locked_body_blue));
            this.mIconHeader.setImageDrawable(NoteListPageFragment.this.getResources().getDrawable(R.drawable.miuix_sbl_simple_indicator_locked_header_blue));
            this.mLabel.setTextColor(NoteListPageFragment.this.getResources().getColor(R.color.miuix_sbl_locked_text_blue));
            this.mLabel.setText(R.string.private_entry_available);
        }

        private boolean isFastTriggered(long j) {
            return j - this.lastTriggeredTime < 500;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disablePullDown() {
        DefaultTrigger defaultTrigger = this.mTrigger;
        if (defaultTrigger == null || !defaultTrigger.containAction(this.mLockAction)) {
            return;
        }
        this.mTrigger.removeAction(this.mLockAction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enablePullDown() {
        DefaultTrigger defaultTrigger = this.mTrigger;
        if (defaultTrigger == null || defaultTrigger.containAction(this.mLockAction)) {
            return;
        }
        this.mTrigger.addAction(this.mLockAction);
    }

    private boolean isHideCloudGuide() {
        if (this.mIsFromPrivate) {
            return true;
        }
        if (!LiteUtils.isMiddle() && !LiteUtils.isSuperLite() && !RomUtils.isInternationalBuild() && !RomUtils.isRedmiDigitSeries()) {
            return false;
        }
        if (!PreferenceUtils.isCTAAccepted(getContext())) {
            return true;
        }
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(NoteApp.getInstance());
        return !ContentResolver.getSyncAutomatically(xiaomiAccount, "notes") || xiaomiAccount == null;
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    public boolean finishActionMode() {
        if (super.finishActionMode()) {
            return true;
        }
        NoteSearchUI noteSearchUI = this.mNoteSearchUI;
        if (noteSearchUI != null && noteSearchUI.isSearchMode()) {
            return this.mNoteSearchUI.finishSearchMode();
        }
        SearchCallback searchCallback = this.mSearchModeListener;
        if (searchCallback != null && searchCallback.finish()) {
            return true;
        }
        View view = this.mSearchBar;
        if (view == null || view.isEnabled()) {
            return false;
        }
        this.mSearchBar.setEnabled(true);
        return false;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Logger.INSTANCE.d("NoteListPageFragment", "onStop");
        if (NotesListActivity.sSelectedPageIndex == 0) {
            NotesPageStat.reportNotesQuit(getActivity(), 0L, System.currentTimeMillis() - this.startTime, NotesPageStat.QUIT_TYPE, true);
        }
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        unregisterCoordinateScrollView(this.mNestedHeaderLayout);
        if (getActionBar() != null) {
            getActionBar().unregisterCoordinatedScrollView(this.mNestedHeaderLayout);
        }
        BaseNoteListAdapter baseNoteListAdapter = this.mAdapter;
        if (baseNoteListAdapter != null) {
            baseNoteListAdapter.frozeData(false);
        }
        CreateAsrDataDialog createAsrDataDialog = this.mCreateAsrDataDialog;
        if (createAsrDataDialog != null && createAsrDataDialog.isShowing()) {
            this.mCreateAsrDataDialog.dismissWithoutAnimation();
        }
        TextView textView = this.searchInput;
        if (textView != null) {
            textView.setOnFocusChangeListener(null);
        }
        BaseNoteRecyclerView baseNoteRecyclerView = this.mRecyclerView;
        if (baseNoteRecyclerView != null) {
            baseNoteRecyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.notes.page.NoteListPageFragment.18
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    NoteListPageFragment.this.mRecyclerViewWrapper.setAdapter(null);
                }
            });
            this.mRecyclerView.getRecycledViewPool().clear();
        }
        this.mRecyclerViewWrapper.finishActionMode();
        PermissionUtils.releaseListeners();
        dismissDialog();
        this.mMiCloudStateView.setLayoutUpdateListener(null);
        this.mMiCloudStateView.removeAllViews();
        this.mMiCloudStateView = null;
        this.mSyncStateController = null;
        this.mBlankView = null;
        PermissionUtils.releaseListeners();
        stopQueryNote();
        LoaderManager.getInstance(this).destroyLoader(3);
        NestedHeaderLayout nestedHeaderLayout = this.mNestedHeaderLayout;
        if (nestedHeaderLayout != null) {
            nestedHeaderLayout.removeNestedHeaderChangedListener();
            Folme.clean(this.mNestedHeaderLayout);
        }
        AiQueryCapabilityAccess.INSTANCE.getInstance().destroySearchActivity(this.mBindContext, getActivity(), false);
        super.onDestroyView();
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        BaseNoteListAdapter baseNoteListAdapter = this.mAdapter;
        if (baseNoteListAdapter != null) {
            baseNoteListAdapter.setOnClickListener(null);
            this.mAdapter.setOnLongClickListener(null);
        }
        if (this.mBtnContentAdd != null) {
            this.mBtnContentAdd.setOnClickListener(null);
        }
        CompositeDisposable compositeDisposable = this.mPreLoadThemeResDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        if (this.mSearchModeListener != null) {
            this.mSearchModeListener = null;
        }
        if (this.mAiQueryContentObserver != null) {
            NoteApp.getInstance().getContentResolver().unregisterContentObserver(this.mAiQueryContentObserver);
        }
        this.mainHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    protected void cachePhoneNumberResource() {
        if (PhoneNumberCacheHelper.hasPhoneNumberResourceCached()) {
            return;
        }
        this.handler.post(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                PhoneNumberCacheHelper.cachePhoneNumberResource();
            }
        });
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected int getHideToastId() {
        return ((RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1) || Navigator.get(this).getNavigationMode() == Navigator.Mode.C) ? R.string.toast_notes_hidden_home_new : R.string.toast_notes_hidden_pad;
    }

    protected static class FolderDeleteRunnable implements Runnable {
        private long mFolderId;

        public FolderDeleteRunnable(long j) {
            this.mFolderId = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            FolderStore.delete(NoteApp.getInstance(), this.mFolderId);
        }
    }

    @Override // com.miui.notes.page.BaseNoteListPageFragment
    protected void initResultLauncher() {
        super.initResultLauncher();
        this.toCtaBeforeCloudLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.page.NoteListPageFragment.19
            @Override // com.miui.notes.assist.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.d("NoteListPageFragment", "toCtaBeforeCloud result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    NoteListPageFragment.this.fragmentViewCloud();
                } else if (activityResult.getResultCode() == -3) {
                    PermissionNewUtils.showCtaDialog(NoteListPageFragment.this.toCtaBeforeCloudLauncher);
                }
            }
        });
        this.toFolderActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.notes.page.NoteListPageFragment.20
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                if (activityResult.getResultCode() == -1) {
                    Intent data = activityResult.getData();
                    long longExtra = data != null ? data.getLongExtra("folderId", -4097L) : -4097L;
                    if (NoteListPageFragment.this.homeViewModel.getFolderId() != longExtra) {
                        NoteListPageFragment.this.deleteEmptyNote();
                    }
                    NoteListPageFragment.this.homeViewModel.setFolderId(longExtra);
                    NoteListPageFragment.this.scrollToTop();
                }
                NoteListPageFragment noteListPageFragment = NoteListPageFragment.this;
                noteListPageFragment.showCreateBtn(noteListPageFragment.computeMenuAddVisibility() == 0, "NoteListPageFragment.initResultLauncher");
            }
        });
    }

    protected void fragmentViewCloud() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ExternalAppUtils.viewCloud(activity);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.page.NoteListPageFragment$21, reason: invalid class name */
    class AnonymousClass21 implements MiCloudStateView.ILayoutUpdateListener {
        AnonymousClass21() {
        }

        @Override // miuix.micloudview.MiCloudStateView.ILayoutUpdateListener
        public void onLayoutUpdate(boolean z, boolean z2, int[] iArr) {
            if (z) {
                if (iArr != null && iArr.length > 0) {
                    int i = 0;
                    for (int i2 : iArr) {
                        i += i2;
                    }
                    if (i == 0) {
                        if (NoteListPageFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                            NoteListPageFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
                            return;
                        }
                        return;
                    }
                }
            } else if (NoteListPageFragment.this.mBindContext.getSearchType() == 0) {
                NoteListPageFragment.this.handler.post(new Runnable() { // from class: com.miui.notes.page.NoteListPageFragment$21$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onLayoutUpdate$0();
                    }
                });
            }
            if (NoteListPageFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                NoteListPageFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLayoutUpdate$0() {
            if (NoteListPageFragment.this.mAdapter.getItemCount() == 0 && NoteListPageFragment.this.homeViewModel.getFolderId() == -4097 && !NoteListPageFragment.this.homeViewModel.isSearchMode()) {
                if (NoteListPageFragment.this.mIsAutoTriggerOpen) {
                    return;
                }
                NoteListPageFragment.this.mIsAutoTriggerOpen = true;
                NoteListPageFragment.this.mNestedHeaderLayout.setAutoTriggerOpen(true);
                return;
            }
            if (NoteListPageFragment.this.mNestedHeaderLayout.isTriggerOpen()) {
                NoteListPageFragment.this.mNestedHeaderLayout.setAutoTriggerClose(true);
            }
        }
    }

    protected void refreshActionEnterPoint() {
        if (this.mLockAction == null || !isAdded()) {
            return;
        }
        if (getResources().getConfiguration().orientation == 2 || (getActivity() != null && getActivity().isInMultiWindowMode())) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.album_action_simple_enter);
            LockAction lockAction = this.mLockAction;
            int iMin = Math.min(dimensionPixelSize, lockAction.mEnterPoint);
            lockAction.mEnterPoint = iMin;
            lockAction.mTriggerPoint = iMin;
        }
    }

    private static class SearchViewFocusChangeListener implements View.OnFocusChangeListener {
        WeakReference<BaseListPageFragment> weakReference;

        public SearchViewFocusChangeListener(BaseListPageFragment baseListPageFragment) {
            this.weakReference = new WeakReference<>(baseListPageFragment);
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            WeakReference<BaseListPageFragment> weakReference = this.weakReference;
            BaseListPageFragment baseListPageFragment = weakReference == null ? null : weakReference.get();
            if (baseListPageFragment == null || baseListPageFragment.homeViewModel == null) {
                return;
            }
            baseListPageFragment.homeViewModel.searchOnFocus(z);
        }
    }
}
