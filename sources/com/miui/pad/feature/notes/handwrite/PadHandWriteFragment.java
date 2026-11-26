package com.miui.pad.feature.notes.handwrite;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.base.SafeBroadcastReceiver;
import com.miui.common.stat.PadHandWriteStat;
import com.miui.common.stat.StatHelper;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DateFormatUtils;
import com.miui.common.tool.ExternalAppUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.doodle.document.Layer;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.handwritecommon.api.callback.ExportListener;
import com.miui.handwritecommon.api.callback.NotaDataSaveListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener;
import com.miui.handwritecommon.api.util.FilePathUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.detail.BaseNoteDetailFragment;
import com.miui.notes.feature.folder.ChooserFolderDialogActivity;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.pad.feature.notes.share.PadSnsEditActivity;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import miuix.android.content.MiuiIntent;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.internal.util.ViewUtils;
import miuix.navigator.Navigator;
import miuix.responsive.map.ScreenSpec;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PadHandWriteFragment extends BaseNoteDetailFragment implements HandWriteHeaderController.HeaderOperationCallback {
    public static final String BLACK_PAPER_BACKGROUND = "black";
    public static final String DOT_PAPER_BACKGROUND = "dot";
    public static String EXTERNAL_JUMP_STYLUS = "stylus";
    public static String EXTERNAL_JUMP_STYLUS_APP = "app";
    public static String EXTERNAL_JUMP_STYLUS_HOME = "home";
    public static String EXTERNAL_JUMP_STYLUS_KEYGUARD = "keyguard";
    public static String EXTERNAL_JUMP_STYLUS_SCREEN_OFF = "off_screen";
    private static final String EXTRA_TAP_SHARE_MULTIPLE_INFO = "xiaomi.tap.share.MULTIPLE_INFO";
    private static final String EXTRA_TAP_SHARE_SUPPORT = "xiaomi.tap.share.SUPPORT";
    public static String INTENT_SCENE = "scene";
    public static String INTENT_SHORTCUT = "shortcut";
    public static String TAG = "PadHandWriteFragment";
    public static final String WHITE_PAPER_BACKGROUND = "white";
    private int bgIndex;
    private View customActionBarView;
    private ISinglePageHandView handWriteView;
    private String initialCanvasBg;
    private boolean isChanged;
    private boolean isHomeBack;
    public long lastEditTime;
    private ProgressDialog loadingDialog;
    private View loadingView;
    private ActivityResultLauncher<Intent> mGalleryResult;
    private HandWriteHeaderController mHeaderController;
    private ArrayList<String> mHighLightKeyWords;
    private boolean mIsFullScreen;
    private FrameLayout mRootView;
    private ViewUtils.RelativePadding mRootViewInitPadding;
    private Navigator.NavigatorStateListener mStateListener;
    private long mSyncId;
    private String queryText;
    private List<Path> searchPathList;
    private HandWriteWorkingNote mWorkingNote = null;
    public boolean mIsNaviShow = true;
    private boolean firstSetNavi = true;
    private DownloadFileReceiver downloadFileReceiver = new DownloadFileReceiver(this);
    private HandWriteNoteHandler mHandler = new HandWriteNoteHandler();

    static /* synthetic */ void lambda$initView$4(View view) {
    }

    protected int getThemeRes() {
        return R.style.NavigatorSecondaryContentTheme;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(getThemeRes());
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(1);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        TouchStylusBoostMode.INSTANCE.getSInstance().setMode(0);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        ISinglePageHandView iSinglePageHandView;
        super.onContentInsetChanged(rect);
        Logger.INSTANCE.d(TAG, "onContentInsetChanged:" + rect);
        ViewUtils.RelativePadding relativePadding = new ViewUtils.RelativePadding(this.mRootViewInitPadding);
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this.rootView);
        relativePadding.start += zIsLayoutRtl ? rect.right : rect.left;
        if (!RomUtils.isPadDevice() || (!UIUtils.isFreeFormScreen(getResources().getConfiguration()) && !UIUtils.isInMultiWindowMode(getActivity()))) {
            relativePadding.top += (this.mIsNaviShow || RomUtils.isFoldDevice()) ? rect.top : 0;
        } else if (this.firstSetNavi || !this.mIsNaviShow) {
            relativePadding.top = relativePadding.top;
        } else {
            relativePadding.top += rect.top;
        }
        relativePadding.end += zIsLayoutRtl ? rect.left : rect.right;
        relativePadding.applyToView(this.rootView);
        List<Path> list = this.searchPathList;
        if (list == null || list.isEmpty() || (iSinglePageHandView = this.handWriteView) == null) {
            return;
        }
        iSinglePageHandView.setSearchPath(this.searchPathList);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) throws Throwable {
        super.onViewInflated(view, bundle);
        this.mRootViewInitPadding = new ViewUtils.RelativePadding(ViewCompat.getPaddingStart(this.rootView), this.rootView.getPaddingTop(), ViewCompat.getPaddingEnd(this.rootView), this.rootView.getPaddingBottom());
        HandWriteHeaderController handWriteHeaderController = new HandWriteHeaderController(this, this);
        this.mHeaderController = handWriteHeaderController;
        this.customActionBarView = handWriteHeaderController.onInflateView(LayoutInflater.from(getContext()), (ViewGroup) getView(), null);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setCustomView(this.customActionBarView, new ActionBar.LayoutParams(-1, -1));
        if (this.mIsNaviShow) {
            if (RomUtils.isPadDevice() && (UIUtils.isFreeFormScreen(getResources().getConfiguration()) || UIUtils.isInMultiWindowMode(getActivity()))) {
                if (this.firstSetNavi || !this.mIsNaviShow) {
                    this.customActionBarView.setVisibility(8);
                } else {
                    this.customActionBarView.setVisibility(0);
                }
            } else {
                this.customActionBarView.setVisibility(0);
            }
        } else {
            this.customActionBarView.setVisibility(8);
        }
        FragmentActivity activity = getActivity();
        if (activity != null && activity.getResources() != null) {
            getActionBar().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.v8_placeholder));
        }
        setImmersionMenuEnabled(true);
        updateHomeButtonState(this.mScreenSpec);
        if (activity != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.miui.notes.handwrite.download");
            if (Utils.isAboveSDK34()) {
                activity.registerReceiver(this.downloadFileReceiver, intentFilter, 2);
            } else {
                activity.registerReceiver(this.downloadFileReceiver, intentFilter);
            }
        }
        onUpdateArguments(getArguments());
        this.homeViewModel.getToEditModeLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$0((Boolean) obj);
            }
        });
        this.homeViewModel.getCurrentItemLiveData().observe(this, new Observer() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$1((Integer) obj);
            }
        });
        this.searchPathList = new ArrayList();
        this.homeViewModel.setKeyboardChange(false);
        this.mStateListener = new Navigator.NavigatorStateListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.1
            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationOpenFinish() {
                Logger.INSTANCE.d(PadHandWriteFragment.TAG, "onNavigationOpenFinish");
                PadHandWriteFragment.this.handWriteView.getViewContainer().suppressLayout(false);
                PadHandWriteFragment.this.handWriteView.getViewContainer().requestLayout();
                PadHandWriteFragment.this.loadingView.setVisibility(8);
            }

            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationOpenBegin() {
                Logger.INSTANCE.d(PadHandWriteFragment.TAG, "onNavigationOpenBegin");
                PadHandWriteFragment.this.handWriteView.getViewContainer().suppressLayout(true);
                PadHandWriteFragment.this.loadingView.setVisibility(0);
            }

            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationCloseBegin() {
                Logger.INSTANCE.d(PadHandWriteFragment.TAG, "onNavigationCloseBegin");
                PadHandWriteFragment.this.handWriteView.getViewContainer().suppressLayout(true);
                PadHandWriteFragment.this.loadingView.setVisibility(0);
            }

            @Override // miuix.navigator.Navigator.NavigatorStateListener
            public void onNavigationCloseFinish() {
                Logger.INSTANCE.d(PadHandWriteFragment.TAG, "onNavigationCloseFinish");
                PadHandWriteFragment.this.handWriteView.getViewContainer().suppressLayout(false);
                PadHandWriteFragment.this.handWriteView.getViewContainer().requestLayout();
                PadHandWriteFragment.this.loadingView.setVisibility(8);
            }
        };
        Navigator.get(this).addNavigatorStateListener(this.mStateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$0(Boolean bool) {
        Navigator.get(this).closeContent(false);
        getActivity().getWindow().addFlags(524288);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$1(Integer num) {
        if (getView() != null) {
            clickSave();
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_note_edit_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        updateMenu(menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        final OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.2
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                com.miui.common.tool.Logger.INSTANCE.d(PadHandWriteFragment.TAG, "onBackPressedDispatcher,handleOnBackPressed,contentOpen:" + Navigator.get(PadHandWriteFragment.this).isContentOpen());
                if (Navigator.get(PadHandWriteFragment.this).isContentOpen() || !PadHandWriteFragment.this.handleBackEvent()) {
                    com.miui.common.tool.Logger.INSTANCE.d(PadHandWriteFragment.TAG, "handleOnBackPressed,activity consume");
                    setEnabled(false);
                    onBackPressedDispatcher.onBackPressed();
                }
            }
        });
    }

    public void updateMenu(Menu menu) {
        menu.findItem(R.id.send_to_desktop).setVisible(false);
        menu.findItem(R.id.set_alarm).setVisible(false);
        if (isNoteHidden()) {
            menu.findItem(R.id.hide).setVisible(false);
            menu.findItem(R.id.unhide).setVisible(true);
            menu.findItem(R.id.move_to).setVisible(false);
        } else {
            menu.findItem(R.id.hide).setVisible(true);
            menu.findItem(R.id.unhide).setVisible(false);
            menu.findItem(R.id.move_to).setVisible(true);
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete /* 2131362396 */:
                requestDeleteNote();
                PadHandWriteStat.reportMoreClickEvent(ToDoStat.DELETE);
                break;
            case R.id.hide /* 2131362702 */:
                tryHideNote();
                PadHandWriteStat.reportMoreClickEvent(MiuiIntent.COMMAND_ICON_PANEL_HIDE);
                break;
            case R.id.move_to /* 2131362990 */:
                if (this.mWorkingNote != null) {
                    startQueryFolder();
                }
                break;
            case R.id.unhide /* 2131363736 */:
                this.homeViewModel.unHideNote(this.mWorkingNote.noteId);
                PadHandWriteStat.reportMoreClickEvent("unhide");
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
        this.mScreenSpec = screenSpec;
        updateHomeButtonState(this.mScreenSpec);
    }

    @Override // androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        getActivity();
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onContentVisibilityChanged(int i) {
        HandWriteNoteHandler handWriteNoteHandler;
        super.onContentVisibilityChanged(i);
        Logger.INSTANCE.d(TAG, "handwrite onContentVisibilityChanged:" + i);
        if (i == 0) {
            setNaviState(false);
            return;
        }
        if (i == 2) {
            lambda$onContentVisibilityChanged$2();
        } else {
            if (i != 1 || (handWriteNoteHandler = this.mHandler) == null) {
                return;
            }
            handWriteNoteHandler.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onContentVisibilityChanged$2();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: setNaviStateWithJudge, reason: merged with bridge method [inline-methods] */
    public void lambda$onContentVisibilityChanged$2() {
        Navigator navigator = Navigator.get(this);
        if (navigator != null) {
            setNaviState(navigator.getNavigationMode() != Navigator.Mode.C);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void setNaviState(boolean z) {
        ISinglePageHandView iSinglePageHandView;
        if (this.firstSetNavi || z != this.mIsNaviShow) {
            Logger.INSTANCE.d(TAG, "setNaviState:" + z);
            this.firstSetNavi = false;
            this.mIsNaviShow = z;
            if (isAdded()) {
                if (RomUtils.isPadDevice() && this.handWriteView != null && !this.mIsNaviShow) {
                    if (UIUtils.isFreeFormScreen(getResources().getConfiguration()) || UIUtils.isInMultiWindowMode(getActivity())) {
                        this.handWriteView.setToolBoxViewTopPadding(UIUtils.getStatusBarHeight(getActivity()));
                    }
                    this.handWriteView.showToolBoxView();
                }
                if (this.mIsNaviShow) {
                    if (this.mRootView != null && (iSinglePageHandView = this.handWriteView) != null && this.customActionBarView != null) {
                        iSinglePageHandView.setToolBoxViewTopPadding(0);
                        String savingTitle = getSavingTitle(this.handWriteView.isEmpty());
                        this.handWriteView.setTitle(savingTitle);
                        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
                        if (handWriteWorkingNote != null) {
                            handWriteWorkingNote.title = savingTitle;
                        }
                        this.customActionBarView.setVisibility(0);
                    }
                } else if (this.mRootView != null && this.handWriteView != null && RomUtils.isPadDevice()) {
                    this.handWriteView.setToolBoxViewTopPadding(UIUtils.getStatusBarHeight(getActivity()));
                    View view = this.customActionBarView;
                    if (view != null) {
                        view.setVisibility(8);
                    }
                }
                if (RomUtils.isPadDevice()) {
                    if (this.mHandler.hasMessages(2)) {
                        this.mHandler.removeMessages(2);
                    }
                    this.mHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    private void updateHomeButtonState(ScreenSpec screenSpec) {
        boolean z = PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1;
        if (!RomUtils.isFoldDevice()) {
            z = false;
        }
        if (z) {
            this.isHomeBack = true;
        } else {
            this.isHomeBack = screenSpec.type == 1;
        }
        Logger.INSTANCE.d(TAG, "updateHomeButtonState,isHomeBack:" + this.isHomeBack);
    }

    protected void tryHideNote() {
        if (this.handWriteView == null) {
            return;
        }
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        if (handWriteWorkingNote == null || handWriteWorkingNote.noteId < 0 || (TextUtils.isEmpty(this.handWriteView.getTitle()) && this.handWriteView.isEmpty())) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_hide_empty_note, 0).show();
            return;
        }
        if (RomUtils.isFoldDevice() && Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
            onBackPressed();
        }
        this.homeViewModel.hideNote(this.mWorkingNote.noteId);
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle bundle) throws Throwable {
        super.onUpdateArguments(bundle);
        Logger.INSTANCE.d(TAG, "hand Detail newArguments:" + bundle);
        if (bundle != null) {
            refreshNote((NoteLoadDataEntity) bundle.getSerializable("entity"), false);
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void refreshNote(NoteLoadDataEntity noteLoadDataEntity, boolean z) throws Throwable {
        if (noteLoadDataEntity != null) {
            try {
                setArguments(noteLoadDataEntity.toBundle());
                loadNoteFromArguments();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.mWorkingNote = null;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isDataEmpty() {
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView == null) {
            return true;
        }
        return TextUtils.isEmpty(iSinglePageHandView.getTitle()) && this.handWriteView.isEmpty();
    }

    public void loadNoteFromArguments() throws Throwable {
        this.lastEditTime = System.currentTimeMillis();
        long j = getArguments().getLong("data_id", -1L);
        long j2 = getArguments().getLong("folder_id", 0L);
        this.mHighLightKeyWords = getArguments().getStringArrayList(NoteLoadDataEntity.HIGHLIGHT_KEYWORDS);
        long j3 = j2 != -4097 ? j2 : 0L;
        Logger.INSTANCE.d(TAG, "loadNoteFromArguments noteId:" + j);
        if (j != -2) {
            this.queryText = getArguments().getString(NoteLoadDataEntity.SEARCH_TEXT, "");
            HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
            if (handWriteWorkingNote != null && handWriteWorkingNote.noteId == j) {
                if (TextUtils.equals(this.queryText, this.mSearchText)) {
                    return;
                }
                setHighLight(this.mWorkingNote.textContent, this.mWorkingNote.recognizedText, this.mWorkingNote.recognizedFile);
                return;
            }
            deleteEmptyNote();
            this.mWorkingNote = HandWriteWorkingNote.loadNote(j);
            updateUI();
            if (isDataEmpty() || !PadHandWriteStat.isCreateHandWriteNote) {
                return;
            }
            PadHandWriteStat.reportCreateEvent(PadHandWriteStat.HAND_WEITE_EFFECT_CREATE, null);
            PadHandWriteStat.isCreateHandWriteNote = false;
            return;
        }
        this.queryText = "";
        this.mSearchText = "";
        this.mWorkingNote = HandWriteWorkingNote.createNote(j3);
        NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(this.mWorkingNote.noteId, this.mWorkingNote.folderId, "", 2, this.mWorkingNote.modifyTime, false, null);
        if (this.homeViewModel == null) {
            this.homeViewModel = (HomeViewModel) new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        }
        this.homeViewModel.postSelectNote(noteLoadDataEntity, false);
        updateUI();
        if (Navigator.get(this).isContentOpen()) {
            Navigator.get(this).closeContent(false);
        }
    }

    private void deleteEmptyNote() {
        HandWriteWorkingNote handWriteWorkingNote;
        if (!isDataEmpty() || (handWriteWorkingNote = this.mWorkingNote) == null || handWriteWorkingNote.noteId <= 0) {
            return;
        }
        NoteStore.deleteEmpty(NoteApp.getInstance(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingDialog(String str) {
        if (isAdded()) {
            if (this.loadingDialog == null) {
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                this.loadingDialog = progressDialog;
                progressDialog.setIndeterminate(true);
                this.loadingDialog.setCanceledOnTouchOutside(false);
                this.loadingDialog.setCancelable(false);
            }
            this.loadingDialog.setMessage(str);
            this.loadingDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLoadingDialog() {
        ProgressDialog progressDialog = this.loadingDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.loadingDialog.dismiss();
    }

    public void saveWithExit() {
        HandWriteWorkingNote handWriteWorkingNote;
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        if (getContext() == null || (handWriteWorkingNote = this.mWorkingNote) == null || handWriteWorkingNote.noteId < 0 || this.handWriteView == null) {
            backToHomePage();
            return;
        }
        Logger.INSTANCE.d(TAG, "saveWithExit:isDataChanged=" + this.handWriteView.isDataChanged());
        String curCanvasBg = getCurCanvasBg(this.handWriteView.getCurrentBackgroundIndex());
        if (this.handWriteView.isDataChanged() || !TextUtils.equals(this.mWorkingNote.title, this.handWriteView.getTitle()) || !TextUtils.equals(curCanvasBg, this.initialCanvasBg) || this.isChanged) {
            this.isChanged = false;
            if (!TextUtils.equals(curCanvasBg, this.initialCanvasBg)) {
                this.initialCanvasBg = curCanvasBg;
            }
            String entFileName = HandWriteDBHelper.getEntFileName();
            this.handWriteView.save(AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), entFileName), new AnonymousClass3(entFileName));
            return;
        }
        backToHomePage();
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$3, reason: invalid class name */
    class AnonymousClass3 implements NotaDataSaveListener {
        final /* synthetic */ String val$tempEnt;

        AnonymousClass3(String str) {
            this.val$tempEnt = str;
        }

        @Override // com.miui.handwritecommon.api.callback.NotaDataSaveListener
        public void onStartSave() {
            Logger.INSTANCE.d(PadHandWriteFragment.TAG, "saveWithExit:onStartSave");
            if (PadHandWriteFragment.this.getActivity() == null) {
                return;
            }
            PadHandWriteFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onStartSave$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStartSave$0() {
            PadHandWriteFragment padHandWriteFragment = PadHandWriteFragment.this;
            padHandWriteFragment.showLoadingDialog(padHandWriteFragment.getResources().getString(R.string.save_note_dialog));
        }

        @Override // com.miui.handwritecommon.api.callback.NotaDataSaveListener
        public void onSaveComplete(boolean z) {
            if (PadHandWriteFragment.this.getActivity() == null) {
                return;
            }
            Logger.INSTANCE.d(PadHandWriteFragment.TAG, "saveWithExit:onSaveComplete=" + z);
            if (z) {
                PadHandWriteFragment.this.handWriteView.resetDataChangedState();
                if (PadHandWriteFragment.this.saveToDB(this.val$tempEnt, true)) {
                    Logger.INSTANCE.i(PadHandWriteFragment.TAG, "noteId:" + PadHandWriteFragment.this.mWorkingNote.noteId + " saveToDBSuccess");
                    NoteWholeRecognize.INSTANCE.saveSinglePageRecognizeResult(PadHandWriteFragment.this.mWorkingNote.noteId, AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), PadHandWriteFragment.this.mWorkingNote.entName));
                }
            }
            PadHandWriteFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSaveComplete$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSaveComplete$1() {
            PadHandWriteFragment.this.dismissLoadingDialog();
            PadHandWriteFragment.this.backToHomePage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveToDB(String str, boolean z) {
        Logger.INSTANCE.d(TAG, "saveToDB:" + str);
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        if (handWriteWorkingNote == null) {
            return false;
        }
        this.mWorkingNote = HandWriteWorkingNote.loadNote(handWriteWorkingNote.noteId);
        if (z) {
            String strSaveThumbnail = this.handWriteView.saveThumbnail();
            Logger.INSTANCE.d(TAG, "updateThumbnail:" + strSaveThumbnail);
            this.mWorkingNote.thumbnail = AttachmentUtils.saveImageFile(getContext(), new File(strSaveThumbnail));
            ArrayList arrayList = new ArrayList();
            WebImage webImageSaveWebImage = this.handWriteView.saveWebImage();
            if (webImageSaveWebImage != null && webImageSaveWebImage.getImage() != null) {
                webImageSaveWebImage.setImage(AttachmentUtils.saveImageFile(getContext(), new File(webImageSaveWebImage.getImage())));
                arrayList.add(webImageSaveWebImage);
                this.mWorkingNote.webImages = arrayList;
            }
        }
        this.mWorkingNote.canvasBgType = this.initialCanvasBg;
        this.mWorkingNote.isEmptyContent = this.handWriteView.isEmpty();
        HandWriteWorkingNote handWriteWorkingNote2 = this.mWorkingNote;
        handWriteWorkingNote2.title = getSavingTitle(handWriteWorkingNote2.isEmptyContent);
        if (new File(AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), str)).exists()) {
            this.mWorkingNote.images = HandWriteDBHelper.getImages(this.handWriteView.getBitmapsPath(AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), str)));
            String strReNameEnt = HandWriteDBHelper.reNameEnt(NoteApp.getInstance(), str);
            HandWriteDBHelper.deleteFile(NoteApp.getInstance(), str);
            if (!TextUtils.isEmpty(strReNameEnt)) {
                String str2 = this.mWorkingNote.entName;
                this.mWorkingNote.entName = strReNameEnt;
                this.mWorkingNote.modifyTime = System.currentTimeMillis();
                boolean zSaveHandWrite = this.mWorkingNote.saveHandWrite();
                if (!zSaveHandWrite) {
                    zSaveHandWrite = this.mWorkingNote.insertHandWrite();
                }
                if (zSaveHandWrite) {
                    final long sortDate = this.mWorkingNote.getSortDate();
                    if (getActivity() != null && isAdded()) {
                        getActivity().runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$saveToDB$3(sortDate);
                            }
                        });
                    }
                    if (!TextUtils.equals(strReNameEnt, str2)) {
                        HandWriteDBHelper.deleteFile(NoteApp.getInstance(), str2);
                    }
                    Logger.INSTANCE.i(TAG, "noteId:" + this.mWorkingNote.noteId + " save entName:" + strReNameEnt + " delete old:" + str2);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveToDB$3(long j) {
        this.handWriteView.setTitleTime((String) DateFormatUtils.formatDateText(NoteApp.getInstance(), j), (String) DateFormatUtils.formatTimeText(NoteApp.getInstance(), j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backToHomePage() {
        if (!UIUtils.isFreeFormScreen(getResources().getConfiguration()) && !UIUtils.isInMultiWindowMode(getActivity())) {
            this.handWriteView.hideToolBoxView();
        }
        Utils.hideSoftInput(getView());
        if (Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
            handleBack();
        } else {
            Navigator.get(this).openContent(false);
        }
        if (isDataEmpty() || !PadHandWriteStat.isCreateHandWriteNote) {
            return;
        }
        PadHandWriteStat.reportCreateEvent(PadHandWriteStat.HAND_WEITE_EFFECT_CREATE, null);
        PadHandWriteStat.isCreateHandWriteNote = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void autoSave() {
        HandWriteWorkingNote handWriteWorkingNote;
        if (getContext() == null || (handWriteWorkingNote = this.mWorkingNote) == null || handWriteWorkingNote.noteId < 0 || this.handWriteView == null || !isAdded()) {
            return;
        }
        Logger.INSTANCE.d(TAG, "autoSave:isDataChanged=" + this.handWriteView.isDataChanged());
        if (this.handWriteView.isDataChanged()) {
            this.isChanged = true;
        }
        String curCanvasBg = getCurCanvasBg(this.handWriteView.getCurrentBackgroundIndex());
        if (!this.handWriteView.isDataChanged() && TextUtils.equals(this.mWorkingNote.title, this.handWriteView.getTitle()) && TextUtils.equals(curCanvasBg, this.initialCanvasBg)) {
            return;
        }
        if (!TextUtils.equals(curCanvasBg, this.initialCanvasBg)) {
            this.initialCanvasBg = curCanvasBg;
        }
        final String entFileName = HandWriteDBHelper.getEntFileName();
        this.handWriteView.save(AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), entFileName), new NotaDataSaveListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.4
            @Override // com.miui.handwritecommon.api.callback.NotaDataSaveListener
            public void onStartSave() {
            }

            @Override // com.miui.handwritecommon.api.callback.NotaDataSaveListener
            public void onSaveComplete(boolean z) {
                if (z) {
                    Logger.INSTANCE.d(PadHandWriteFragment.TAG, "autoSave:resetDataChangedState=" + z);
                    PadHandWriteFragment.this.handWriteView.resetDataChangedState();
                    if (PadHandWriteFragment.this.saveToDB(entFileName, false)) {
                        Logger.INSTANCE.i(PadHandWriteFragment.TAG, "noteId:" + PadHandWriteFragment.this.mWorkingNote.noteId + " saveToDBSuccess");
                        return;
                    }
                    return;
                }
                Logger.INSTANCE.i(PadHandWriteFragment.TAG, " save fail");
            }
        });
    }

    private String getCurCanvasBg(int i) {
        if (i == HandViewProvider.getSinglePageWhiteBg()) {
            return "white";
        }
        if (i == HandViewProvider.getSinglePageBlackBg()) {
            return "black";
        }
        return i == HandViewProvider.getSinglePageDotBg() ? "dot" : "white";
    }

    private String getSavingTitle(boolean z) {
        String string = getResources().getString(R.string.handwrite_description);
        String title = this.handWriteView.getTitle();
        if (z) {
            if (TextUtils.equals(title, string)) {
                return "";
            }
        } else if (TextUtils.isEmpty(title)) {
            return string;
        }
        return title;
    }

    private void updateUI() throws Throwable {
        ISinglePageHandView iSinglePageHandView;
        this.isChanged = false;
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        if (handWriteWorkingNote != null) {
            this.mSyncId = handWriteWorkingNote.syncId;
            String attachmentPath = !TextUtils.isEmpty(this.mWorkingNote.entName) ? AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), this.mWorkingNote.entName) : null;
            if (this.handWriteView != null) {
                Logger.INSTANCE.d(TAG, "updateUI clear");
                this.handWriteView.setTitleTime((String) DateFormatUtils.formatDateText(NoteApp.getInstance(), this.mWorkingNote.getSortDate()), (String) DateFormatUtils.formatTimeText(NoteApp.getInstance(), this.mWorkingNote.getSortDate()));
                this.handWriteView.setTitle(this.mWorkingNote.title);
                this.handWriteView.clear();
                HandWriteWorkingNote handWriteWorkingNote2 = this.mWorkingNote;
                if (handWriteWorkingNote2 != null) {
                    setCanvasBg(handWriteWorkingNote2.canvasBgType);
                }
                this.handWriteView.setToolBoxBackgroundIndex(this.bgIndex);
                this.handWriteView.load(attachmentPath, new NoteDataLoadListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.5
                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onStartLoad() {
                        ISinglePageHandView iSinglePageHandView2 = PadHandWriteFragment.this.handWriteView;
                        PadHandWriteFragment padHandWriteFragment = PadHandWriteFragment.this;
                        iSinglePageHandView2.showLoading(padHandWriteFragment.getLoadingBgColor(padHandWriteFragment.mWorkingNote.canvasBgType));
                    }

                    @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                    public void onLoadComplete(boolean z) {
                        Logger.INSTANCE.d(PadHandWriteFragment.TAG, "load onLoadComplete:" + z);
                        PadHandWriteFragment.this.handWriteView.resetDataChangedState();
                        PadHandWriteFragment.this.handWriteView.setBackgroundIndex(PadHandWriteFragment.this.bgIndex);
                        if (RomUtils.isPadDevice()) {
                            PadHandWriteFragment.this.handWriteView.hideLoading();
                        }
                    }
                });
            }
            if (this.mIsNaviShow && (iSinglePageHandView = this.handWriteView) != null) {
                iSinglePageHandView.setToHead();
            }
            if (!TextUtils.equals(this.queryText, this.mSearchText)) {
                setHighLight(this.mWorkingNote.textContent, this.mWorkingNote.recognizedText, this.mWorkingNote.recognizedFile);
            }
            View view = this.loadingView;
            if (view != null) {
                view.setVisibility(8);
            }
            if (TextUtils.isEmpty(attachmentPath)) {
                return;
            }
            try {
                if (new File(attachmentPath).exists()) {
                    return;
                }
                Logger.INSTANCE.i(TAG, "noteId:" + this.mWorkingNote.noteId + " Loading entName:" + this.mWorkingNote.entName);
                View view2 = this.loadingView;
                if (view2 != null) {
                    view2.setVisibility(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCanvasBg(String str) {
        this.initialCanvasBg = str;
        this.bgIndex = HandViewProvider.getSinglePageWhiteBg();
        if (TextUtils.isEmpty(str)) {
            if (UIUtils.isNightMode(getActivity())) {
                this.bgIndex = HandViewProvider.getSinglePageBlackBg();
                this.initialCanvasBg = "black";
                return;
            } else {
                this.bgIndex = HandViewProvider.getSinglePageWhiteBg();
                this.initialCanvasBg = "white";
                return;
            }
        }
        str.hashCode();
        if (str.equals("dot")) {
            this.bgIndex = HandViewProvider.getSinglePageDotBg();
            this.initialCanvasBg = "dot";
        } else if (str.equals("black")) {
            this.bgIndex = HandViewProvider.getSinglePageBlackBg();
            this.initialCanvasBg = "black";
        } else {
            this.bgIndex = HandViewProvider.getSinglePageWhiteBg();
            this.initialCanvasBg = "white";
        }
    }

    public int getLoadingBgColor(String str) {
        if (TextUtils.isEmpty(str)) {
            return UIUtils.isNightMode(getActivity()) ? R.color.black : R.color.white;
        }
        if ("black".equals(str)) {
            return R.color.black;
        }
        if (!"white".equals(str)) {
            "dot".equals(str);
        }
        return R.color.white;
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void setInSearchMode(boolean z) {
        super.setInSearchMode(z);
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView == null || z) {
            return;
        }
        iSinglePageHandView.setSearchPath(null);
    }

    private void setHighLight(String str, String str2, String str3) throws Throwable {
        if (this.handWriteView == null) {
            return;
        }
        if (!TextUtils.isEmpty(str3)) {
            str2 = AttachmentUtils.getRecognizeText(NoteApp.getInstance(), str3);
        }
        this.searchPathList.clear();
        ArrayList<String> arrayList = this.mHighLightKeyWords;
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i = 0; i < this.mHighLightKeyWords.size(); i++) {
                setHighLightKeyWords(str, str2, this.mHighLightKeyWords.get(i), this.searchPathList);
            }
        }
        if (TextUtils.isEmpty(this.queryText)) {
            this.mSearchText = "";
            this.handWriteView.setSearchPath(null);
            this.searchPathList.clear();
        } else if (!TextUtils.equals(this.queryText, this.mSearchText)) {
            this.mSearchText = this.queryText;
            setHighLightKeyWords(str, str2, this.queryText, this.searchPathList);
        }
        if (this.searchPathList.isEmpty()) {
            return;
        }
        this.handWriteView.setSearchPath(this.searchPathList);
    }

    private void setHighLightKeyWords(String str, String str2, String str3, List<Path> list) {
        try {
            Matcher matcher = Pattern.compile(str3).matcher(str);
            ArrayList arrayList = new ArrayList();
            while (matcher.find()) {
                arrayList.add(Integer.valueOf(matcher.start()));
            }
            JSONArray jSONArray = new JSONObject(str2).getJSONArray("chars");
            if (arrayList.isEmpty()) {
                return;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                Integer num = (Integer) arrayList.get(i);
                for (int iIntValue = num.intValue(); iIntValue < num.intValue() + str3.length(); iIntValue++) {
                    JSONObject jSONObject = ((JSONObject) jSONArray.get(iIntValue)).getJSONObject("bounding_box");
                    int i2 = (int) Float.parseFloat(jSONObject.getString(AnimatedProperty.PROPERTY_NAME_X));
                    int i3 = (int) Float.parseFloat(jSONObject.getString(AnimatedProperty.PROPERTY_NAME_Y));
                    int i4 = (int) Float.parseFloat(jSONObject.getString(Layer.KEY_WIDTH));
                    int i5 = (int) Float.parseFloat(jSONObject.getString(Layer.KEY_HEIGHT));
                    RectF rectF = new RectF();
                    Path path = new Path();
                    rectF.set(i2, i3, i2 + i4, i3 + i5);
                    path.addRect(rectF, Path.Direction.CW);
                    list.add(path);
                }
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.pad_hand_write, viewGroup, false);
        Logger.INSTANCE.d(TAG, "onInflateView");
        this.handWriteView = HandViewProvider.addSinglePageView(viewGroup2, 2, false, false);
        initView(viewGroup2);
        return viewGroup2;
    }

    private void initView(View view) {
        this.mRootView = (FrameLayout) view;
        View viewFindViewById = view.findViewById(R.id.loading_container);
        this.loadingView = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PadHandWriteFragment.lambda$initView$4(view2);
            }
        });
        StatHelper.isEnable();
        if (RomUtils.isPadDevice() && (UIUtils.isFreeFormScreen(getResources().getConfiguration()) || UIUtils.isInMultiWindowMode(getActivity()))) {
            this.handWriteView.setToolBoxViewTopPadding(UIUtils.getStatusBarHeight(getActivity()));
            this.handWriteView.setCanWrite(true);
        } else {
            this.handWriteView.setCanWrite(false);
        }
        if (RomUtils.isPadDevice()) {
            this.handWriteView.setPreviewOnClick(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$initView$5(view2);
                }
            });
            this.handWriteView.setSingePageOptionListener(new ISinglePageLayoutListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.6
                @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
                public void onShare() {
                }

                @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
                public void onInsertImageClick() {
                    PadHandWriteFragment padHandWriteFragment = PadHandWriteFragment.this;
                    ExternalAppUtils.selectImage(padHandWriteFragment, 1, padHandWriteFragment.mGalleryResult, null);
                }

                @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
                public void onContentChange() {
                    if (PadHandWriteFragment.this.mHandler.hasMessages(1)) {
                        PadHandWriteFragment.this.mHandler.removeMessages(1);
                    }
                    PadHandWriteFragment.this.mHandler.sendEmptyMessageDelayed(1, 5000L);
                }

                @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
                public void onBtnSave() {
                    PadHandWriteFragment.this.saveWithExit();
                }

                @Override // com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener
                public void onTitleChange() {
                    Logger.INSTANCE.d(PadHandWriteFragment.TAG, "onTitleChange:");
                    if (PadHandWriteFragment.this.mHandler.hasMessages(1)) {
                        PadHandWriteFragment.this.mHandler.removeMessages(1);
                    }
                    if (PadHandWriteFragment.this.mWorkingNote == null || TextUtils.equals(PadHandWriteFragment.this.mWorkingNote.title, PadHandWriteFragment.this.handWriteView.getTitle())) {
                        return;
                    }
                    PadHandWriteFragment.this.mWorkingNote.title = PadHandWriteFragment.this.handWriteView.getTitle();
                    PadHandWriteFragment.this.mWorkingNote.modifyTime = System.currentTimeMillis();
                    PadHandWriteFragment.this.mWorkingNote.saveHandWrite();
                }
            });
        } else {
            this.handWriteView.setPreviewOnClick(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$initView$6(view2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        Navigator navigator;
        Utils.hideSoftInput(view);
        if (!this.mIsNaviShow || Utils.isFastClick(0, 500L) || (navigator = Navigator.get(this)) == null) {
            return;
        }
        navigator.closeContent(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(View view) {
        Toast.makeText(NoteApp.getInstance(), getResources().getString(R.string.hand_write_only_support_tablet_editing), 0).show();
    }

    private void clickSave() {
        saveWithExit();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        autoSave();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean onBackPressed() {
        return handleBackEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleBackEvent() {
        saveWithExit();
        if (!this.isHomeBack) {
            return true;
        }
        deleteEmptyNote();
        return true;
    }

    private void updateSoftInputMode(Activity activity) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void addPicToNote(Uri uri) {
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.addImage(uri);
        }
    }

    @Override // com.miui.pad.feature.notes.handwrite.HandWriteHeaderController.HeaderOperationCallback
    public void onPerformAction(int i, Object... objArr) {
        if (i != 1) {
            return;
        }
        if (TextUtils.isEmpty(this.handWriteView.getTitle()) && this.handWriteView.isEmpty()) {
            Toast.makeText(NoteApp.getInstance(), R.string.error_send_empty_note, 0).show();
        } else {
            float contentRangBottom = this.handWriteView.getContentRangBottom();
            Logger.INSTANCE.d(TAG, "getContentRangBottom:" + contentRangBottom);
            if (this.handWriteView != null && contentRangBottom <= 25000.0f) {
                getShareImage();
            } else {
                Toast.makeText(NoteApp.getInstance(), R.string.canvas_is_too_large_to_share, 0).show();
            }
        }
        PadHandWriteStat.reportClickEvent(PadHandWriteStat.HAND_WEITE_SHARE_CLICK, null);
    }

    public boolean isNoteHidden() {
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        return handWriteWorkingNote != null && handWriteWorkingNote.folderId == -4;
    }

    private void getShareImage() {
        if (this.mWorkingNote == null) {
            return;
        }
        this.handWriteView.exportImage(new AnonymousClass7());
    }

    /* JADX INFO: renamed from: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$7, reason: invalid class name */
    class AnonymousClass7 implements ExportListener<Uri> {
        AnonymousClass7() {
        }

        @Override // com.miui.handwritecommon.api.callback.ExportListener
        public void exportFinish(boolean z, final List<? extends Uri> list) {
            PadHandWriteFragment.this.mHandler.post(new Runnable() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$exportFinish$0(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$exportFinish$0(List list) {
            if (!list.isEmpty()) {
                PadHandWriteFragment.this.shareNote(list);
            }
            PadHandWriteFragment.this.dismissLoadingDialog();
        }

        @Override // com.miui.handwritecommon.api.callback.ExportListener
        public void exportStart() {
            AttachmentUtils.deleteHandWriteShareImage();
            PadHandWriteFragment padHandWriteFragment = PadHandWriteFragment.this;
            padHandWriteFragment.showLoadingDialog(padHandWriteFragment.getResources().getString(R.string.dlg_info_creating_message));
        }
    }

    public void shareNote(List<Uri> list) {
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        long length = 0;
        long sortDate = handWriteWorkingNote != null ? handWriteWorkingNote.getSortDate() : 0L;
        HandWriteWorkingNote handWriteWorkingNote2 = this.mWorkingNote;
        long j = handWriteWorkingNote2 != null ? handWriteWorkingNote2.folderId : 0L;
        String str = ((Object) DateFormatUtils.formatDateText(NoteApp.getInstance(), sortDate)) + StringUtils.SPACE + ((Object) DateFormatUtils.formatTimeText(NoteApp.getInstance(), sortDate));
        if (!RomUtils.isOS31Support()) {
            PadSnsEditActivity.INSTANCE.launchActivity(this.mToShareResult, getActivity(), "hand_write", this.handWriteView.getTitle(), str, 4, (ArrayList) list, this.handWriteView.getCurrentBackgroundIndex(), j, null);
            return;
        }
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        ArrayList<Uri> arrayList2 = new ArrayList(list);
        File singlePageExportDir = FilePathUtils.getSinglePageExportDir(getContext());
        ClipData clipDataNewRawUri = null;
        for (Uri uri : arrayList2) {
            if (uri != null) {
                String lastPathSegment = uri.getLastPathSegment();
                if (singlePageExportDir != null && lastPathSegment != null) {
                    File file = new File(singlePageExportDir, lastPathSegment);
                    if (file.exists()) {
                        length += file.length();
                    }
                }
                if (clipDataNewRawUri == null) {
                    clipDataNewRawUri = ClipData.newRawUri("share_image", uri);
                } else {
                    clipDataNewRawUri.addItem(new ClipData.Item(uri));
                }
                arrayList.add(uri);
            }
        }
        boolean z = arrayList.size() > 1;
        Intent intent = new Intent(z ? "android.intent.action.SEND_MULTIPLE" : "android.intent.action.SEND");
        intent.setFlags(1);
        intent.setType("image/png");
        if (!z) {
            intent.putExtra("android.intent.extra.STREAM", arrayList.get(0));
            intent.setClipData(ClipData.newRawUri(null, (Uri) arrayList.get(0)));
        } else {
            intent.setClipData(clipDataNewRawUri);
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        }
        intent.putExtra("xiaomi.tap.share.SUPPORT", true);
        intent.putExtra("xiaomi.tap.share.MULTIPLE_INFO", String.valueOf(length));
        startActivity(Intent.createChooser(intent, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNavi() {
        ISinglePageHandView iSinglePageHandView;
        if (!RomUtils.isPadDevice() || !isAdded() || getActivity() == null || getActivity().isFinishing() || getActivity().isDestroyed() || (iSinglePageHandView = this.handWriteView) == null) {
            return;
        }
        try {
            iSinglePageHandView.setCanWrite(!this.mIsNaviShow);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "setCanWrite error: " + e);
            e.printStackTrace();
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        deleteEmptyNote();
        Logger.INSTANCE.d(TAG, "onDestroyView");
        this.mWorkingNote = null;
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
        }
        ISinglePageHandView iSinglePageHandView = this.handWriteView;
        if (iSinglePageHandView != null) {
            iSinglePageHandView.release();
        }
        try {
            getActivity().unregisterReceiver(this.downloadFileReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isDataEmpty() && PadHandWriteStat.isCreateHandWriteNote) {
            PadHandWriteStat.reportCreateEvent(PadHandWriteStat.HAND_WEITE_EFFECT_CREATE, null);
        }
        PadHandWriteStat.isCreateHandWriteNote = false;
        Navigator.get(this).removeNavigatorStateListener(this.mStateListener);
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    protected void onNoteDeleted(long j) {
        Toast.makeText(NoteApp.getInstance(), R.string.toast_notes_delete_success, 0).show();
        if (this.mActionListener != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("note_id", j);
            this.mActionListener.onAction(27, bundle);
        }
        this.mWorkingNote = null;
        if (Navigator.Mode.C.equals(Navigator.get(this).getNavigationMode()) && isAdded() && getActivity() != null) {
            onBackPressed();
        }
    }

    public void requestDeleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2131951644);
        builder.setTitle(R.string.new_alert_title_delete);
        builder.setMessage(R.string.alert_message_delete_note);
        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PadHandWriteStat.reportDeleteClickEvent(true);
                PadHandWriteFragment.this.deleteAndLoad();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PadHandWriteStat.reportDeleteClickEvent(false);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteAndLoad() {
        AudioUtils.playDeleteRingtone();
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        if (handWriteWorkingNote == null || handWriteWorkingNote.noteId <= 0 || !HandWriteDBHelper.deleteNote(this.mWorkingNote.noteId)) {
            return;
        }
        onNoteDeleted(this.mWorkingNote.noteId);
    }

    private static class DownloadFileReceiver extends SafeBroadcastReceiver<PadHandWriteFragment> {
        public DownloadFileReceiver(PadHandWriteFragment padHandWriteFragment) {
            super(padHandWriteFragment);
        }

        @Override // com.miui.common.base.SafeBroadcastReceiver, android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (hasReference()) {
                final PadHandWriteFragment wkObject = getWkObject();
                if (wkObject.handWriteView == null || wkObject.mWorkingNote == null) {
                    return;
                }
                String str = wkObject.mWorkingNote.entName;
                if (TextUtils.equals(intent.getStringExtra("entName"), str)) {
                    wkObject.setCanvasBg(wkObject.mWorkingNote.canvasBgType);
                    wkObject.handWriteView.load(AttachmentUtils.getAttachmentPath(context, str), new NoteDataLoadListener() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.DownloadFileReceiver.1
                        @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                        public void onStartLoad() {
                        }

                        @Override // com.miui.handwritecommon.api.callback.NoteDataLoadListener
                        public void onLoadComplete(boolean z) {
                            if (wkObject.loadingView != null && wkObject.loadingView.getVisibility() == 0) {
                                wkObject.loadingView.setVisibility(8);
                            }
                            wkObject.handWriteView.setBackgroundIndex(wkObject.bgIndex);
                        }
                    });
                }
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public boolean isBlankViewShow() {
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        return handWriteWorkingNote == null || handWriteWorkingNote.noteId < 0;
    }

    private static class HandWriteNoteHandler extends Handler {
        private static final int SET_CAN_WRITE = 2;
        private static final int TIMED_SAVE = 1;
        private WeakReference<PadHandWriteFragment> mContextRef;

        private HandWriteNoteHandler(PadHandWriteFragment padHandWriteFragment) {
            this.mContextRef = new WeakReference<>(padHandWriteFragment);
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            PadHandWriteFragment padHandWriteFragment = this.mContextRef.get();
            if (padHandWriteFragment == null) {
                return;
            }
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                padHandWriteFragment.setNavi();
            } else {
                try {
                    padHandWriteFragment.autoSave();
                } catch (Exception e) {
                    Logger.INSTANCE.d(PadHandWriteFragment.TAG, e.getMessage());
                }
            }
        }
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    protected void initBaseResultLauncher() {
        this.mGalleryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.10
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                PadHandWriteFragment.this.homeViewModel.toDetailResultCallback();
                Intent data = activityResult.getData();
                if (activityResult.getResultCode() != -1 || data == null) {
                    return;
                }
                PadHandWriteFragment.this.addPicToNote(data.getData());
            }
        });
        this.toChooseFolderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.11
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                PadHandWriteFragment.this.homeViewModel.toDetailResultCallback();
                if (activityResult.getResultCode() == -1) {
                    Intent data = activityResult.getData();
                    if (!PadHandWriteFragment.this.isAdded() || data == null) {
                        return;
                    }
                    long longExtra = data.getLongExtra(ChooserFolderDialogActivity.CHOOSER_FOLDER_SELECT_FOLDER_ID, 0L);
                    if (PadHandWriteFragment.this.mWorkingNote != null) {
                        PadHandWriteFragment.this.mWorkingNote.setFolderId(longExtra);
                    }
                }
            }
        });
        this.mToShareResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.miui.pad.feature.notes.handwrite.PadHandWriteFragment.12
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult activityResult) {
                PadHandWriteFragment.this.homeViewModel.toDetailResultCallback();
            }
        });
    }

    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void startQueryFolder() {
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        if (handWriteWorkingNote == null) {
            this.mFolderId = 0L;
        } else {
            this.mFolderId = handWriteWorkingNote.getFolderId();
        }
        FolderStore.INSTANCE.query(getLoaderManager(), 0, 2, new BaseNoteDetailFragment.NoteLoaderCallbacks(), "_id <> " + this.mFolderId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public long getNoteId() {
        HandWriteWorkingNote handWriteWorkingNote = this.mWorkingNote;
        if (handWriteWorkingNote == null) {
            return -1L;
        }
        return handWriteWorkingNote.noteId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.detail.BaseNoteDetailFragment
    public void moveCheckedItems(long j, long[] jArr, String str) {
        if (this.mWorkingNote != null) {
            this.mFolderId = j;
            this.mWorkingNote.setFolderId(j);
            this.detailViewModel.move(jArr, j, str);
        }
    }
}
