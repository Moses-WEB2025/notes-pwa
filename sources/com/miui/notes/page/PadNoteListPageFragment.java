package com.miui.notes.page;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import com.google.gson.Gson;
import com.miui.common.app.ShareViewModel;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.NoteQueryStat;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.floatwindow.feature.note.NoteOperationHelper;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.assist.EmptyNavigatorInfo;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.detail.PadDetailFragmentClassManager;
import com.miui.notes.home.PadActivityController;
import com.miui.notes.home.viewmodel.SelectNoteData;
import com.miui.notes.model.MindDataHelper;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.NoteModel;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.store.ItemStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.BitmapCacheManager;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.tapshareutils.TapShareDataPrepareUtils;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.pad.feature.notes.multihandwrite.CreationEditActivity;
import com.xiaomi.mirror.widget.MiuiDragShadowBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.strategy.ActionBarConfig;
import miuix.appcompat.app.strategy.ActionBarSpec;
import miuix.core.util.MiuixUIUtils;
import miuix.navigator.NavigationActionBarStrategy;
import miuix.navigator.Navigator;
import miuix.navigator.NavigatorStrategy;
import miuix.navigator.navigatorinfo.UpdateDetailFragmentNavInfo;
import miuix.responsive.map.ScreenSpec;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteListPageFragment extends NoteListPageFragment {
    private static final String TAG = "PadNoteListPageFragment";
    private float mDensity;
    private ShareViewModel shareViewModel;
    private long mHideNoteId = -2;
    private boolean mInitSelect = false;
    private Long lastSelectId = 0L;
    private boolean lastNightMode = false;
    private int lastListDataCount = 0;

    static /* synthetic */ void lambda$onViewInflated$8(Boolean bool) {
    }

    @Override // miuix.appcompat.app.Fragment
    protected boolean isResponsiveEnabled() {
        return true;
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.shareViewModel = ((NoteApp) requireActivity().getApplication()).getGlobalSharedViewModel();
        this.mIsInPrivate = false;
        PreferenceUtils.setIsInPrivate(this.mIsInPrivate);
        Logger.INSTANCE.d(TAG, "NotePage onCreate savedInstanceState:" + (bundle != null));
        if (bundle != null) {
            this.lastNightMode = bundle.getBoolean("isNightModeChange");
        } else {
            this.lastNightMode = UIUtils.isNightMode(getContext());
        }
        LiveDataEventBus.with(LiveDataEventBus.BUS_HOMEPAGE_ARRAY_WAY, Boolean.class).observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$0((Boolean) obj);
            }
        });
        LiveDataEventBus.with(LiveDataEventBus.BUS_EXIT_LIST_MULTI, Boolean.class).observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(Boolean bool) {
        Logger.INSTANCE.d(TAG, "homepage_array_way is isSingleColumnMode:" + bool + " mAdapter.isGridMode() " + this.mAdapter.isGridMode());
        if (bool.booleanValue() != this.mAdapter.isGridMode()) {
            updateGridMode(bool.booleanValue());
            setBlankViewImageAndText();
            if (bool.booleanValue()) {
                deleteEmptyNote();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(Boolean bool) {
        if (bool.booleanValue()) {
            finishActionMode();
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment
    protected void setPasswordSuccess(int i) {
        if (5102 != i) {
            if (5101 == i) {
                this.mPasswordController.confirmPrivacyPassword(i);
            }
        } else {
            if (this.mRecyclerViewWrapper != null && this.mRecyclerViewWrapper.getCheckedItemIds().length != 0) {
                hideCheckedNotes(this.mRecyclerViewWrapper.getCheckedItemIds());
                return;
            }
            long j = this.mHideNoteId;
            if (j != -2) {
                hideCheckedNotes(new long[]{j});
                this.mHideNoteId = -2L;
            }
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected boolean onLongClickImpl(View view) {
        if (Utils.isFastClick(0, 500L)) {
            return false;
        }
        if (this.mActionMode == null && canEnterEditMode()) {
            if (computeMenuAddVisibility() == 0) {
                showCreateBtn(false, "PadNoteListPageFragment.longClickImpl");
            }
            updateFolderLayout(false);
            this.mRecyclerViewWrapper.setItemChecked(view, true);
            this.mRecyclerViewWrapper.startActionMode(createEditModeCallback(), this);
            this.mSearchBar.setEnabled(false);
        } else {
            startDrag(view);
        }
        return true;
    }

    private void startDrag(View view) {
        long[] checkedItemIds = getRecyclerWrapper().getCheckedItemIds();
        StringBuilder sb = new StringBuilder();
        int length = checkedItemIds.length;
        for (int i = 0; i < length; i++) {
            sb.append(checkedItemIds[i]);
            if (i != length - 1) {
                sb.append(",");
            }
        }
        ClipData clipDataNewPlainText = ClipData.newPlainText(PadActivityController.NOTE_LIST_DRAG_LABEL, sb.toString());
        MiuiDragShadowBuilder miuiDragShadowBuilder = new MiuiDragShadowBuilder(getContext());
        miuiDragShadowBuilder.mAttrs.thumbnailCountInColor = ContextCompat.getColor(getContext(), R.color.item_icon_color);
        miuiDragShadowBuilder.setCount(length);
        miuiDragShadowBuilder.setAlpha(1.0f);
        Bitmap bitmapCaptureSnapshot = ImageUtils.INSTANCE.captureSnapshot(view);
        if (bitmapCaptureSnapshot != null) {
            miuiDragShadowBuilder.setThumb(bitmapCaptureSnapshot);
        }
        view.startDragAndDrop(clipDataNewPlainText, miuiDragShadowBuilder, new Object(), 0);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void initView(View view) {
        super.initView(view);
        if (this.mBtnHandWriteAdd != null && RomUtils.isPadDevice()) {
            this.mBtnHandWriteAdd.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$initView$2(view2);
                }
            });
        } else {
            Logger.INSTANCE.e(TAG, "mBtnHandWriteAdd is null");
        }
        view.setBackgroundColor(NoteApp.getInstance().getColor(R.color.pad_homepage_note_list_bg));
        if (getActionBar() != null) {
            getActionBar().setBackgroundDrawable(new ColorDrawable(NoteApp.getInstance().getColor(R.color.pad_homepage_note_list_bg)));
        }
        if (!UIUtils.isHyperOsAndAbove()) {
            getActionBar().setActionBarStrategy(new MIUIActionBarStrategy());
        }
        this.mAdapter.setSingleScreen(Navigator.get(this).getNavigationMode() == Navigator.Mode.C);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        if (Utils.isFastClick(view.getId(), 500L)) {
            return;
        }
        BaseHybridNoteEditFragment.removeAiFloatingWindow();
        if (this.mRecyclerView != null) {
            this.mRecyclerView.stopScroll();
        }
        this.mAdapter.frozeData(true);
        this.isEmptyList = false;
        if (Navigator.get(this).isContentOpen()) {
            Navigator.get(this).requestFocus();
        }
        PreferenceUtils.setPreferenceFirstCreateHandWrite(NoteApp.getInstance(), this.dataCount == 0 || this.mAdapter.getItemCount() == 0);
        NotesListActivity notesListActivity = (NotesListActivity) requireActivity();
        CreationEditActivity.launchFromCreate(notesListActivity, this.homeViewModel.createHandWriteMultiNote(), false, true, (ActivityResultLauncher) Objects.requireNonNull(notesListActivity.getReceivePadMultiData()));
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void setFolderVisible() {
        super.setFolderVisible();
        if (!UIUtils.isHyperOsAndAbove() && !this.mIsFromPrivate) {
            this.mFolderLayout.setVisibility(0);
        } else {
            if (!RomUtils.isPadDevice() || Navigator.get(this).getNavigationMode() == Navigator.Mode.C) {
                return;
            }
            this.mFolderLayout.setVisibility(8);
        }
    }

    private void updateGridMode(boolean z) {
        Logger.INSTANCE.d(TAG, "updateGridMode " + z);
        boolean z2 = false;
        if (RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1) {
            z2 = true;
        }
        this.mAdapter.setGridMode(z2);
        if (this.homeViewModel.isGridMode() != z2) {
            updateRecyclerViewLayoutManager(z2, z);
        } else {
            updateRecyclerViewLayoutManagerWithoutSetStrategy(z2);
        }
    }

    @Override // miuix.appcompat.app.Fragment
    public void onUpdateArguments(Bundle bundle) {
        super.onUpdateArguments(bundle);
        Logger.INSTANCE.d(TAG, "NotePage onUpdateArguments:" + bundle);
        if (bundle != null && bundle.containsKey("data_id")) {
            openFolder(bundle.getLong("data_id"));
        }
        if (this.mRecyclerViewWrapper != null) {
            this.mRecyclerViewWrapper.finishActionMode();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("isNightModeChange", this.lastNightMode);
        Log.d(TAG, "onSaveInstanceState lastNightMode: " + this.lastNightMode);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
        super.onViewInflated(view, bundle);
        setFolderVisible();
        Logger.INSTANCE.d(TAG, "onViewInflated");
        updateGridMode(true);
        this.homeViewModel.getListDataCursor().observe(getViewLifecycleOwner(), new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$3((Cursor) obj);
            }
        });
        this.homeViewModel.getSearchDataCursor().observe(getViewLifecycleOwner(), new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$4((Cursor) obj);
            }
        });
        this.homeViewModel.getSelectNoteLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$5((SelectNoteData) obj);
            }
        });
        this.homeViewModel.getCreateEntityData().observe(getViewLifecycleOwner(), new Observer<NoteLoadDataEntity>() { // from class: com.miui.notes.page.PadNoteListPageFragment.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(NoteLoadDataEntity noteLoadDataEntity) {
                boolean z = (noteLoadDataEntity == null || noteLoadDataEntity.getNoteType() != 3 || PadNoteListPageFragment.this.getActivity() == null || !PadNoteListPageFragment.this.getActivity().isInMultiWindowMode() || Navigator.get(PadNoteListPageFragment.this.getThisFragment()).getNavigationMode() == Navigator.Mode.NLC || Navigator.get(PadNoteListPageFragment.this.getThisFragment()).getNavigationMode() == Navigator.Mode.LC) ? false : true;
                if (!z) {
                    PreferenceUtils.setPreferenceFromCreate(NoteApp.getInstance(), false);
                }
                boolean zIsNightMode = UIUtils.isNightMode(PadNoteListPageFragment.this.getContext());
                boolean z2 = zIsNightMode != PadNoteListPageFragment.this.lastNightMode;
                Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "CreateEntityChange: " + z + ", isNightChangeMode: " + z2);
                if (!z2) {
                    PadNoteListPageFragment.this.homeViewModel.postSelectNote(noteLoadDataEntity, true ^ z);
                }
                PadNoteListPageFragment.this.lastNightMode = zIsNightMode;
            }
        });
        Logger.INSTANCE.d(TAG, "observe page switch");
        this.homeViewModel.getPageSelectedLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$6((Integer) obj);
            }
        });
        this.homeViewModel.getEditStatus().observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$7((Boolean) obj);
            }
        });
        this.homeViewModel.getKeyBoardShowStatus().observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PadNoteListPageFragment.lambda$onViewInflated$8((Boolean) obj);
            }
        });
        this.homeViewModel.getHideNoteLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$9((Long) obj);
            }
        });
        this.homeViewModel.getUnHideNoteLiveData().observe(this, new Observer() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onViewInflated$10((Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$3(Cursor cursor) {
        if (cursor.isClosed()) {
            return;
        }
        Logger.INSTANCE.d(TAG, "onViewInflated ,getListDataCursor:" + cursor.getCount());
        onNotesLoadFinished(cursor);
        refreshFolderInfo();
        cachePhoneNumberResource();
        BitmapCacheManager.resize(requireActivity());
        boolean z = (this.lastListDataCount <= 0 || cursor.getCount() == this.lastListDataCount) ? this.mAdapter.getSelectId() < 0 && cursor.getCount() > 0 : true;
        this.lastListDataCount = cursor.getCount();
        try {
            if (!isResumed() && !z) {
                NoteLoadDataEntity selectedNote = this.homeViewModel.getSelectedNote();
                if (selectedNote != null && this.homeViewModel.getPageSelected() == 0 && selectedNote.getNoteType() == 3 && Navigator.get(this).isContentOpen()) {
                    Navigator.get(this).requestFocus();
                    return;
                }
                return;
            }
            checkSelectedNote(cursor);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "onChanged: error " + Log.getStackTraceString(new Throwable(e.getMessage())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$4(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        if (!this.homeViewModel.isSearchMode() || cursor.getCount() == 0) {
            if (this.homeViewModel.isSearchMode() && cursor.getCount() == 0) {
                this.homeViewModel.setSelectNote(null, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
                return;
            }
            return;
        }
        String searchToken = this.mBindContext.getSearchToken();
        if (TextUtils.equals(searchToken, this.mLastSearchText)) {
            Logger.INSTANCE.d(TAG, "searchDataCursor changed, search text not changed, skip auto select");
            return;
        }
        this.mLastSearchText = searchToken;
        NoteLoadDataEntity selectedNote = this.homeViewModel.getSelectedNote();
        if (selectedNote != null) {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                long j = cursor.getLong(0);
                if (j == selectedNote.getNoteId()) {
                    NoteLoadDataEntity loadDataFromCursor = getLoadDataFromCursor(cursor);
                    if (this.homeViewModel.isAiSearchMode() && this.mBindContext.getAiQueryNoteList(j) != null) {
                        loadDataFromCursor.setHighlightKeywords(this.mBindContext.getAiQueryNoteList(j).getHighlightKeywords());
                    }
                    Logger.INSTANCE.d(TAG, "searchDataCursor changed, update highlight for current note");
                    this.homeViewModel.setSelectNote(loadDataFromCursor, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
                    return;
                }
            }
        }
        cursor.moveToPosition(0);
        NoteLoadDataEntity loadDataFromCursor2 = getLoadDataFromCursor(cursor);
        Logger.INSTANCE.d(TAG, "searchDataCursor changed, select first search result");
        this.homeViewModel.setSelectNote(loadDataFromCursor2, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$5(SelectNoteData selectNoteData) {
        Logger.INSTANCE.d(TAG, "SelectNoteDataEntity onChanged entity:" + (selectNoteData.getEntity() == null ? "null" : Long.valueOf(selectNoteData.getEntity().getNoteId())));
        if (selectNoteData.getEntity() != null) {
            if (!this.mInitSelect) {
                ItemCache item = this.mAdapter.getItem(0);
                if (item != null) {
                    NoteContentStat.IS_STICK = item.isStick();
                }
                this.mInitSelect = true;
            }
            this.mAdapter.setSelectId(selectNoteData.getEntity().getNoteId());
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$6(Integer num) {
        Logger.INSTANCE.d(TAG, "on page selected live data changed with  pageIndex:" + num);
        if (num.intValue() == 0) {
            onSelected(true);
        } else {
            finishActionMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$7(Boolean bool) {
        Logger.INSTANCE.d(TAG, "onChanged isEditStatus: " + bool);
        if (bool.booleanValue()) {
            showCreateBtn(false, "PadNoteListPageFragment.observer edit status true");
        } else if (this.mBtnContentAdd != null) {
            showCreateBtn(computeMenuAddVisibility() == 0, "PadNoteListPageFragment.observer edit status with false");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$9(Long l) {
        if (getContext() == null || this.mPasswordController == null) {
            return;
        }
        this.mHideNoteId = l.longValue();
        this.mPasswordController.setPassword(5102);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewInflated$10(Long l) {
        if (getContext() != null) {
            unHideCheckedNotes(new long[]{l.longValue()});
        }
    }

    private void checkSelectedNote(Cursor cursor) {
        boolean z;
        NoteLoadDataEntity selectedNote = this.homeViewModel.getSelectedNote();
        Logger.INSTANCE.d(TAG, "checkSelectedNote:" + selectedNote);
        if (selectedNote == null || selectedNote.getNoteId() != -2) {
            if (cursor.getCount() == 0) {
                Logger.INSTANCE.d(TAG, "checkSelectedNote cursor is empty,entity:" + this.homeViewModel.getSelectedNoteWrapper());
                SelectNoteData selectedNoteWrapper = this.homeViewModel.getSelectedNoteWrapper();
                if (selectedNoteWrapper == null || selectedNoteWrapper.getFolderId() != this.homeViewModel.getFolderId() || (selectedNoteWrapper.getEntity() != null && !isNoteExistInCurrentFolder(selectedNoteWrapper.getEntity().getNoteId()))) {
                    this.homeViewModel.setSelectNote(null, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
                    return;
                }
            }
            if (selectedNote != null) {
                Logger.INSTANCE.d(TAG, "checkSelectedNote id:" + selectedNote.getNoteId());
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    long j = cursor.getLong(0);
                    if (j == selectedNote.getNoteId()) {
                        Logger.INSTANCE.d(TAG, " position:" + cursor.getPosition() + " cursor id:" + j);
                        NoteLoadDataEntity loadDataFromCursor = getLoadDataFromCursor(cursor);
                        if (this.homeViewModel.isSearchMode() && !TextUtils.equals(this.mBindContext.getSearchToken(), this.mLastSearchText)) {
                            this.mLastSearchText = this.mBindContext.getSearchToken();
                            if (this.homeViewModel.isAiSearchMode() && this.mBindContext.getAiQueryNoteList(j) != null) {
                                loadDataFromCursor.setHighlightKeywords(this.mBindContext.getAiQueryNoteList(j).getHighlightKeywords());
                            }
                            this.homeViewModel.setSelectNote(loadDataFromCursor, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
                            z = false;
                        } else {
                            z = this.mAdapter.getSelectId() != j;
                            this.mAdapter.setSelectId(j);
                            this.mAdapter.notifyDataSetChanged();
                        }
                        if (z) {
                            this.homeViewModel.setSelectNote(loadDataFromCursor, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
                        } else {
                            Logger.INSTANCE.d(TAG, "checkSelectedNote id:" + selectedNote.getNoteId() + " is same");
                        }
                        if (this.homeViewModel.isSearchMode()) {
                            return;
                        }
                        this.mLastSearchText = null;
                        return;
                    }
                }
            }
            try {
                Intent intent = requireActivity().getIntent();
                boolean z2 = intent != null && intent.getBooleanExtra(EditActivity.KEYGUARD_SHORTCUT, false);
                Logger.INSTANCE.d(TAG, "isFromKeyGard  " + z2);
                if (z2 && selectedNote != null && isNoteExistInCurrentFolder(selectedNote.getNoteId())) {
                    intent.removeExtra(EditActivity.KEYGUARD_SHORTCUT);
                    return;
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "checkSelectedNote: error " + Log.getStackTraceString(new Throwable(e.getMessage())));
            }
            if (this.homeViewModel.isSearchMode()) {
                Logger.INSTANCE.d(TAG, "checkSelectedNote in search mode, skip fallback to first item");
                return;
            }
            cursor.moveToPosition(0);
            NoteLoadDataEntity loadDataFromCursor2 = getLoadDataFromCursor(cursor);
            this.mLastSearchText = this.mBindContext.getSearchToken();
            this.homeViewModel.setSelectNote(loadDataFromCursor2, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
        }
    }

    private void toDetailFragment(NoteLoadDataEntity noteLoadDataEntity, String str) {
        Logger.INSTANCE.d(TAG, "toDetailFragment " + str);
        if (this.homeViewModel.getPageSelected() == 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", noteLoadDataEntity);
            Navigator.get(this).navigate(new UpdateDetailFragmentNavInfo(-1, PadDetailFragmentClassManager.observeClass(noteLoadDataEntity, 3), bundle));
        }
    }

    private boolean isNoteExistInCurrentFolder(long j) {
        Context context = getContext();
        if (context == null) {
            Logger.INSTANCE.d(TAG, "isNoteExistInCurrentFolder: Context is null for noteId: " + j);
            return false;
        }
        try {
            Cursor cursorQueryById = NoteStore.queryById(context, j);
            if (cursorQueryById != null) {
                try {
                    if (cursorQueryById.getCount() != 0) {
                        cursorQueryById.moveToFirst();
                        long foldId = ItemStore.getFoldId(cursorQueryById);
                        long folderId = this.homeViewModel.getFolderId();
                        Logger.INSTANCE.d(TAG, "isNoteExistInCurrentFolder note id:" + j + " foldId=" + foldId + ", currentFolderId=" + folderId);
                        boolean z = foldId == folderId || folderId == -4097;
                        if (cursorQueryById != null) {
                            cursorQueryById.close();
                        }
                        return z;
                    }
                } catch (Throwable th) {
                    if (cursorQueryById != null) {
                        try {
                            cursorQueryById.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            Logger.INSTANCE.d(TAG, "isNoteExistInCurrentFolder: No note found with noteId: " + j);
            if (cursorQueryById != null) {
                cursorQueryById.close();
            }
            return false;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "isNoteExistInCurrentFolder Error checking note existence in current folder: " + j + ",e:" + e);
            return false;
        }
    }

    @Override // miuix.navigator.NavigatorFragmentListener
    public void onNavigatorModeChanged(Navigator.Mode mode, Navigator.Mode mode2) {
        super.onNavigatorModeChanged(mode, mode2);
        finishActionMode();
        Log.d(TAG, "onNavigatorModeChanged, PadPageMode:" + mode2 + " isContentOpen:" + Navigator.get(this).isContentOpen() + ", OldMode:" + mode);
        boolean preferenceFromCreate = PreferenceUtils.getPreferenceFromCreate(NoteApp.getInstance());
        if (this.homeViewModel.getPageSelected() == 0) {
            if (shouldHandleContentModeTransition(mode2, mode)) {
                NoteLoadDataEntity selectedNote = this.homeViewModel.getSelectedNote();
                if (selectedNote != null && selectedNote.getNoteType() == 3) {
                    Navigator.get(this).requestFocus(true);
                }
                handleContentModeTransition(preferenceFromCreate);
                return;
            }
            handleOtherModeTransition();
            if (this.mAdapter != null && this.mAdapter.getItemCount() == 0) {
                this.homeViewModel.setSelectNote(null, Navigator.get(this).getNavigationMode() != Navigator.Mode.C, false);
            }
        }
        handleNonNoteSelectedCase(mode2, preferenceFromCreate);
    }

    private boolean shouldHandleContentModeTransition(Navigator.Mode mode, Navigator.Mode mode2) {
        if (mode == Navigator.Mode.C) {
            return mode2 == Navigator.Mode.NLC || mode2 == Navigator.Mode.LC;
        }
        return false;
    }

    private void handleContentModeTransition(boolean z) {
        NoteLoadDataEntity selectedNote = this.homeViewModel.getSelectedNote();
        if (selectedNote == null) {
            return;
        }
        this.mAdapter.setSingleScreen(true);
        this.mAdapter.notifyDataSetChanged();
        if (!shouldRedirectToEditPage(selectedNote, z) || RomUtils.isFoldDevice()) {
            return;
        }
        Log.d(TAG, "onNavigatorModeChanged: handwrite note to edit page");
        NotesListActivity notesListActivity = (NotesListActivity) requireActivity();
        CreationEditActivity.launchFromCreate(notesListActivity, selectedNote, true, false, (ActivityResultLauncher) Objects.requireNonNull(notesListActivity.getReceivePadMultiData()));
    }

    private boolean shouldRedirectToEditPage(NoteLoadDataEntity noteLoadDataEntity, boolean z) {
        return getActivity() != null && getActivity().isInMultiWindowMode() && noteLoadDataEntity.getNoteType() == 3 && !z;
    }

    private void handleOtherModeTransition() {
        Log.d(TAG, "handleOtherModeTransition: ");
        Navigator.get(this).navigate(new EmptyNavigatorInfo((int) (this.homeViewModel.getFolderId() - (-4097))));
        this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleOtherModeTransition$11();
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleOtherModeTransition$11() {
        try {
            toDetailFragment(this.homeViewModel.getSelectedNote(), "onNavigatorModeChanged");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleNonNoteSelectedCase(Navigator.Mode mode, boolean z) {
        this.mAdapter.setSingleScreen(mode == Navigator.Mode.C);
        this.mAdapter.notifyDataSetChanged();
        PreferenceUtils.setPreferenceFromCreate(NoteApp.getInstance(), false);
        setBlankViewImageAndText();
        setFolderVisible();
    }

    private void handleTapShare(Intent intent) {
        Logger.INSTANCE.d(TAG, "handleTapShare: handle TapShare intent");
        Bundle extras = intent.getExtras();
        if (extras == null) {
            Logger.INSTANCE.d(TAG, "handleTapShare: bundle is null");
            return;
        }
        ArrayList parcelableArrayList = extras.getParcelableArrayList("tap_share_uris");
        if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
            Logger.INSTANCE.d(TAG, "handleTapShare: tapShareUris is null or empty");
            return;
        }
        boolean z = extras.getBoolean("isAppleDevice", false);
        Logger.INSTANCE.d(TAG, "handleTapShare: isAppleDevice = " + z);
        new TapShareNotesInsertTask(z).execute(parcelableArrayList);
        try {
            Intent intent2 = requireActivity().getIntent();
            intent2.removeExtra("tap_share_uris");
            intent2.setData(null);
            intent2.setAction("android.intent.action.VIEW");
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "handleTapShare clear extras error: " + e.getMessage());
        }
    }

    public class TapShareNotesInsertTask extends AsyncTask<List<Uri>, Void, List<Long>> {
        private final boolean mIsAppleDevice;

        public TapShareNotesInsertTask(boolean z) {
            this.mIsAppleDevice = z;
        }

        @Override // android.os.AsyncTask
        @Deprecated
        protected void onPreExecute() {
            super.onPreExecute();
            Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: Starting to insert notes");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public List<Long> doInBackground(List<Uri>... listArr) {
            List<Uri> list = listArr[0];
            Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask uriList size: " + list.size());
            ArrayList arrayList = new ArrayList();
            try {
                if (this.mIsAppleDevice) {
                    Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: Restoring from iOS device");
                    WorkingNote workingNoteRestoreNoteFromIOS = TapShareDataPrepareUtils.restoreNoteFromIOS(PadNoteListPageFragment.this.getActivity(), list.get(0));
                    if (workingNoteRestoreNoteFromIOS != null && workingNoteRestoreNoteFromIOS.getNoteId() > 0) {
                        arrayList.add(Long.valueOf(workingNoteRestoreNoteFromIOS.getNoteId()));
                        Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: iOS note restored, id: " + workingNoteRestoreNoteFromIOS.getNoteId());
                        return arrayList;
                    }
                } else {
                    Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: Restoring from Android device");
                    List<Long> listRestoreNotesFromUris = TapShareDataPrepareUtils.restoreNotesFromUris(PadNoteListPageFragment.this.getActivity(), list);
                    Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask restored noteIds: " + listRestoreNotesFromUris);
                    if (listRestoreNotesFromUris != null) {
                        arrayList.addAll(listRestoreNotesFromUris);
                    }
                }
                return arrayList;
            } catch (Exception e) {
                Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: Error restoring notes: " + e.getMessage());
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(List<Long> list) {
            super.onPostExecute(list);
            Toast.makeText(PadNoteListPageFragment.this.getActivity(), PadNoteListPageFragment.this.getString(R.string.tapshare_restore_notes_toast), 0).show();
            if (list == null || list.isEmpty()) {
                Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: No notes were restored");
            } else {
                Toast.makeText(PadNoteListPageFragment.this.getActivity(), PadNoteListPageFragment.this.getString(R.string.tapshare_restore_notes_success_toast), 0).show();
                Logger.INSTANCE.d(PadNoteListPageFragment.TAG, "TapShareNotesInsertTask: Successfully restored " + list.size() + " notes");
            }
        }
    }

    private void forceShowContent() {
        Logger.INSTANCE.d(TAG, "forceShowContent " + this.homeViewModel.getPageSelected());
        final boolean zIsNightMode = UIUtils.isNightMode(NoteApp.getInstance());
        if (this.homeViewModel.getPageSelected() == 0) {
            this.handler.post(new Runnable() { // from class: com.miui.notes.page.PadNoteListPageFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$forceShowContent$12(zIsNightMode);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceShowContent$12(boolean z) {
        if (this.isEmptyList && isAdded()) {
            Logger.INSTANCE.d(TAG, "forceShowContent-requestFocus");
            try {
                Navigator.get(this).requestFocus(true);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "requestFocus err,exception:" + e);
            }
        }
        ResourceManager.isNightMode = z;
    }

    @Override // miuix.appcompat.app.Fragment, miuix.responsive.interfaces.IResponsive
    public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
        super.onResponsiveLayout(configuration, screenSpec, z);
        Logger.INSTANCE.d(TAG, "onResponsiveLayout,sc:" + screenSpec.screenMode + " C mode:" + (Navigator.get(this).getNavigationMode() == Navigator.Mode.C) + " change:" + z);
        if (RomUtils.isFoldDevice()) {
            setBlankViewImageAndText();
            this.mAdapter.setSingleScreen(Navigator.get(this).getNavigationMode() == Navigator.Mode.C);
            this.mAdapter.notifyDataSetChanged();
        }
        forceShowContent();
    }

    protected void updateRecyclerViewLayoutManager(boolean z, boolean z2) {
        int type = getResponsiveState().getType();
        Navigator.Mode navigationMode = Navigator.get(this).getNavigationMode();
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_base);
        final int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_horizontal_common);
        Logger.INSTANCE.d(TAG, "updateRecyclerViewGridMode gridMode:" + z + " getResponsiveState().getType():" + getResponsiveState().getType() + " Mode:" + navigationMode);
        if (z) {
            if (this.mRvLayoutManager == null || this.mRvLayoutManager.getSpanCount() <= 1) {
                if (z2) {
                    Navigator.get(this).requestFocus();
                }
                this.homeViewModel.setCurrentGridMode(true);
                NavigatorStrategy navigatorStrategy = new NavigatorStrategy();
                navigatorStrategy.setCompactMode(Navigator.Mode.C).setRegularMode(Navigator.Mode.C, Navigator.Mode.C).setLargeMode(Navigator.Mode.NLC);
                Navigator.get(this).setStrategy(navigatorStrategy);
            }
            Logger.INSTANCE.d(TAG, "updateRecyclerViewGridMode: getResponsiveState().getType():" + getResponsiveState().getType());
            int fontLevel = MiuixUIUtils.getFontLevel(getContext());
            int i = 2;
            if (type == 1) {
                if (fontLevel == 2) {
                    i = 1;
                }
            } else if (fontLevel != 2) {
                i = 3;
            }
            this.mGridItemSpace = dimensionPixelSize2 / 2;
            this.mRecyclerView.removeItemDecoration(this.mItemDecoration);
            this.mRecyclerView.addItemDecoration(this.mItemDecoration);
            this.mRvLayoutManager = new NoteGridLayoutManager(i, 1);
            this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
            this.mRvLayoutManager.setAutoMeasureEnabled(false);
            this.mRecyclerView.setPaddingRelative(this.mGridItemSpace, dimensionPixelSize, this.mGridItemSpace, UIUtils.dip2px(getContext(), 58.0f));
            this.mSpringBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.PadNoteListPageFragment.2
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    int dimensionPixelOffset = PadNoteListPageFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                    outline.setRoundRect(dimensionPixelSize2, dimensionPixelSize, view.getWidth() - dimensionPixelSize2, view.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
                }
            });
            this.mSpringBackLayout.setClipToOutline(true);
            return;
        }
        Logger.INSTANCE.d(TAG, "updateRecyclerViewGridMode list:" + z);
        this.mRecyclerView.removeItemDecoration(this.mItemDecoration);
        this.mRvLayoutManager = new NoteGridLayoutManager(1, 1);
        this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
        this.mRecyclerView.setPaddingRelative(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2, UIUtils.dip2px(getContext(), 40.0f));
        this.mSpringBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.PadNoteListPageFragment.3
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                int dimensionPixelOffset = PadNoteListPageFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                outline.setRoundRect(dimensionPixelSize2, dimensionPixelSize, view.getWidth() - dimensionPixelSize2, view.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
            }
        });
        this.mSpringBackLayout.setClipToOutline(true);
        this.homeViewModel.setCurrentGridMode(false);
        NavigatorStrategy navigatorStrategy2 = new NavigatorStrategy();
        navigatorStrategy2.setCompactMode(Navigator.Mode.C).setRegularMode((this.mIsFromPrivate || !UIUtils.isHyperOsAndAbove()) ? Navigator.Mode.LC : Navigator.Mode.NLC, Navigator.Mode.LC).setLargeMode((this.mIsFromPrivate || !UIUtils.isHyperOsAndAbove()) ? Navigator.Mode.LC : Navigator.Mode.NLC);
        Navigator.get(this).setStrategy(navigatorStrategy2);
    }

    protected void updateRecyclerViewLayoutManagerWithoutSetStrategy(boolean z) {
        int type = getResponsiveState().getType();
        Navigator.Mode navigationMode = Navigator.get(this).getNavigationMode();
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_base);
        final int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_horizontal_common);
        Logger.INSTANCE.d(TAG, "updateRecyclerViewGridMode gridMode:" + z + " getResponsiveState().getType():" + getResponsiveState().getType() + " Mode:" + navigationMode);
        if (z) {
            Logger.INSTANCE.d(TAG, "updateRecyclerViewGridMode: getResponsiveState().getType():" + getResponsiveState().getType());
            int fontLevel = MiuixUIUtils.getFontLevel(getContext());
            int i = 2;
            if (type == 1) {
                if (fontLevel == 2) {
                    i = 1;
                }
            } else if (fontLevel != 2) {
                i = 3;
            }
            this.mGridItemSpace = dimensionPixelSize2 / 2;
            this.mRecyclerView.removeItemDecoration(this.mItemDecoration);
            this.mRecyclerView.addItemDecoration(this.mItemDecoration);
            this.mRvLayoutManager = new NoteGridLayoutManager(i, 1);
            this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
            this.mRvLayoutManager.setAutoMeasureEnabled(false);
            this.mRecyclerView.setPaddingRelative(this.mGridItemSpace, dimensionPixelSize, this.mGridItemSpace, UIUtils.dip2px(getContext(), 58.0f));
            this.mSpringBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.PadNoteListPageFragment.4
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    int dimensionPixelOffset = PadNoteListPageFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                    outline.setRoundRect(dimensionPixelSize2, dimensionPixelSize, view.getWidth() - dimensionPixelSize2, view.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
                }
            });
            this.mSpringBackLayout.setClipToOutline(true);
            return;
        }
        Logger.INSTANCE.d(TAG, "updateRecyclerViewGridMode list:" + z);
        this.mRecyclerView.removeItemDecoration(this.mItemDecoration);
        this.mRvLayoutManager = new NoteGridLayoutManager(1, 1);
        this.mRecyclerView.setLayoutManager(this.mRvLayoutManager);
        this.mRecyclerView.setPaddingRelative(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2, UIUtils.dip2px(getContext(), 40.0f));
        this.mSpringBackLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.page.PadNoteListPageFragment.5
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                int dimensionPixelOffset = PadNoteListPageFragment.this.getResources().getDimensionPixelOffset(R.dimen.common_note_list_item_bg_radius);
                outline.setRoundRect(dimensionPixelSize2, dimensionPixelSize, view.getWidth() - dimensionPixelSize2, view.getHeight() + dimensionPixelOffset, dimensionPixelOffset);
            }
        });
        this.mSpringBackLayout.setClipToOutline(true);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void setBlankViewImageAndText() {
        if (this.mBlankView != null) {
            boolean z = Navigator.get(this).getNavigationMode() != Navigator.Mode.C;
            if (this.homeViewModel.isSearchMode()) {
                if (z) {
                    this.mBlankView.setVisibility(8);
                    return;
                }
                this.mBlankView.setVisibility(0);
                this.mBlankView.setIcon(R.drawable.note_blank_icon);
                this.mBlankView.setTitle(getString(R.string.data_empty_note));
                return;
            }
            this.mBlankView.setVisibility(0);
            if (z) {
                this.mBlankView.setIcon(0);
                this.mBlankView.setTitle(getString(R.string.pad_note_create_hint));
            } else {
                this.mBlankView.setIcon(R.drawable.note_blank_icon);
                this.mBlankView.setTitle(getString(R.string.data_empty_note));
            }
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        Cursor value;
        super.onResume();
        Logger.INSTANCE.d(TAG, "onResume");
        PreferenceUtils.setPreferenceFromCreate(NoteApp.getInstance(), false);
        if (this.homeViewModel.getPageSelected() != 0 || this.homeViewModel.getSelectedNote() != null || this.mAdapter == null || this.mAdapter.getItemCount() <= 0 || Navigator.get(this).getNavigationMode() == Navigator.Mode.C || (value = this.homeViewModel.getListDataCursor().getValue()) == null || value.isClosed() || value.getCount() <= 0) {
            return;
        }
        checkSelectedNote(value);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        dismissDialog();
        super.onDestroyView();
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void addNote(View view) {
        this.homeViewModel.createNormalNote();
        this.isEmptyList = false;
        NotesPageStat.reportNewNote();
        NotesPageStat.QUIT_TYPE = NotesPageStat.GO_NEW_NOTES;
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void openNoteFromClick(View view) {
        int childLayoutPosition = this.mRecyclerView.getChildLayoutPosition(view);
        ItemCache item = this.mAdapter.getItem(childLayoutPosition);
        if (item == null || item.getCacheType() != 0) {
            return;
        }
        NoteContentStat.mOldStick = NoteContentStat.IS_STICK;
        NoteContentStat.IS_STICK = item.isStick();
        NoteCache noteCache = (NoteCache) item.getCacheObject();
        long id = noteCache.getNote().getId();
        Navigator.Mode navigationMode = Navigator.get(this).getNavigationMode();
        Log.d(TAG, "openNoteFromClick: ,LastSelectID:" + this.lastSelectId + ",CurrentSelectID:" + id + ",CurrentSelectMode:" + navigationMode);
        if (!Objects.equals(this.lastSelectId, Long.valueOf(id)) || navigationMode == Navigator.Mode.C) {
            if (this.homeViewModel.isAiSearchMode() && this.mBindContext.getAiQueryNoteList(id) != null) {
                noteCache.setHighlightKeywords(this.mBindContext.getAiQueryNoteList(id).getHighlightKeywords());
            } else {
                noteCache.setHighlightKeywords(null);
            }
            this.homeViewModel.onOpenNote(this.homeViewModel.getFolderId(), noteCache, TextUtils.isEmpty(this.mBindContext.getSearchToken()) ? this.mBindContext.getSearchTokenBackUp() : this.mBindContext.getSearchToken());
            if (this.homeViewModel.isSearchMode()) {
                Logger.INSTANCE.d(TAG, "openNoteFromClick in search mode");
                NoteQueryStat.reportNoteSearchResultPositiveClickEvent(String.valueOf(this.mAdapter.getRealItemCount()), this.mBindContext.getSearchToken(), childLayoutPosition, String.valueOf(this.homeViewModel.isAiSearchMode()));
            }
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void openSearchResultNote(View view, NoteCache noteCache, String str) {
        this.homeViewModel.onOpenNote(this.homeViewModel.getFolderId(), noteCache, str);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    public void deliverIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        Logger.INSTANCE.d(TAG, "deliverIntent action = " + action);
        if (TextUtils.isEmpty(action)) {
            return;
        }
        boolean zEquals = NoteIntent.ACTION_SEARCH_NOTES.equals(action);
        boolean z = intent.getData() != null && "mishare".equals(intent.getData().getScheme()) && "/notes/Share".equals(intent.getData().getPath());
        if (!zEquals) {
            if (z) {
                handleTapShare(intent);
                return;
            }
            return;
        }
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY);
        String stringExtra = intent.getStringExtra(SearchCallback.TextSearch.TEXT_SEARCH_KEY);
        String stringExtra2 = intent.getStringExtra(SearchCallback.TextSearch.TEXT_SEARCH_KEY_FROM_AI_ACTION);
        if (stringExtra != null && stringExtra.length() > 0) {
            onTextSearch(stringExtra, 2);
            return;
        }
        if (stringArrayListExtra == null || stringArrayListExtra.size() <= 0) {
            if (stringExtra2 != null) {
                onTextSearch(stringExtra2, 3);
                return;
            } else {
                Logger.INSTANCE.e(TAG, "Intent has no extra query text!");
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = stringArrayListExtra.iterator();
        while (it.hasNext()) {
            String phoneNum = TextProcessUtils.formatPhoneNum(it.next());
            if (!TextUtils.isEmpty(phoneNum)) {
                sb.append(phoneNum).append(StringUtils.SPACE);
            }
        }
        onTextSearch(sb.toString(), 1);
    }

    private NoteLoadDataEntity getLoadDataFromCursor(Cursor cursor) {
        int i;
        int i2;
        String str;
        boolean z;
        NoteLoadDataEntity noteLoadDataEntity;
        Logger.INSTANCE.d(TAG, "getLoadDataFromCursor");
        long j = cursor.getLong(0);
        ArrayList<String> highlightKeywords = (this.homeViewModel == null || !this.homeViewModel.isAiSearchMode() || this.mBindContext.getAiQueryNoteList(j) == null) ? null : this.mBindContext.getAiQueryNoteList(j).getHighlightKeywords();
        long j2 = cursor.getLong(1);
        long j3 = cursor.getLong(9);
        if (NoteModel.NoteType.TYPE_MIND.equals(cursor.getString(18))) {
            i = 1;
        } else {
            i = 3;
            if (!"handwrite".equals(cursor.getString(18)) && !"multi_handwrite".equals(cursor.getString(18))) {
                i = 0;
            }
        }
        boolean zHasNewStyle = NoteOperationHelper.hasNewStyle(cursor.getString(4));
        String string = cursor.getString(19);
        if (!TextUtils.isEmpty(string) && string.startsWith(MindDataHelper.getMindDataTag())) {
            MindEntity mindEntity = (MindEntity) new Gson().fromJson(string.substring(15), MindEntity.class);
            int i3 = !mindEntity.isMap ? 1 : 0;
            str = mindEntity.title;
            i2 = i3;
        } else {
            i2 = 0;
            str = "";
        }
        if (i != 1) {
            z = zHasNewStyle;
            noteLoadDataEntity = new NoteLoadDataEntity(j, j2, this.mBindContext.getSearchToken(), i, j3, false, highlightKeywords);
        } else {
            z = zHasNewStyle;
            if (!TextUtils.isEmpty(string) && string.startsWith(MindDataHelper.getMindDataTag())) {
                noteLoadDataEntity = new NoteLoadDataEntity(j, j2, this.mBindContext.getSearchToken(), i, j3, i2, 1, str, false, highlightKeywords);
            } else {
                noteLoadDataEntity = new NoteLoadDataEntity(j, j2, this.mBindContext.getSearchToken(), i, j3, i2, 0, "", false, highlightKeywords);
            }
        }
        noteLoadDataEntity.mHasNewStyle = z;
        Logger.INSTANCE.d(TAG, "getLoadDataFromCursor finished");
        return noteLoadDataEntity;
    }

    @Override // com.miui.notes.page.BaseListPageFragment
    protected void onFoldChangeScreen() {
        super.onFoldChangeScreen();
        dismissDialog();
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Logger.INSTANCE.d(TAG, "onConfigurationChanged");
        if (RomUtils.isFoldDevice()) {
            float f = getResources().getDisplayMetrics().density;
            boolean z = false;
            if (RomUtils.isFoldDevice() && PreferenceUtils.getNoteHomePageArrayWay(getActivity(), 0) == 1) {
                z = true;
            }
            updateRecyclerViewLayoutManagerWithoutSetStrategy(z);
            if (this.mDensity == f || !isAdded()) {
                return;
            }
            this.mDensity = f;
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.pad_folder_list_height);
            if (this.tagParent != null) {
                ViewGroup.LayoutParams layoutParams = this.tagParent.getLayoutParams();
                layoutParams.height = dimensionPixelSize;
                this.tagParent.setLayoutParams(layoutParams);
            }
            if (this.mFolderRv != null) {
                this.mFolderRv.setAdapter(this.mFolderListAdapter);
            }
            if (isAdded()) {
                this.mRecyclerView.getRecycledViewPool().clear();
            }
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseListPageFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Logger.INSTANCE.d(TAG, "onPause");
        if (this.mRecyclerViewWrapper != null) {
            this.mRecyclerViewWrapper.finishActionMode();
        }
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.ShortcutsCallback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.homeViewModel.getPageSelected() == 0 && keyEvent.hasModifiers(4096) && keyEvent.getRepeatCount() == 0) {
            if (i == 41) {
                if (Utils.isFastClick(0, 500L)) {
                    return true;
                }
                this.homeViewModel.createMindNote();
                return true;
            }
            if (i == 42) {
                if (Utils.isFastClick(0, 500L)) {
                    return true;
                }
                this.homeViewModel.createNormalNote();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    static class MIUIActionBarStrategy extends NavigationActionBarStrategy {
        MIUIActionBarStrategy() {
        }

        @Override // miuix.navigator.NavigationActionBarStrategy, miuix.appcompat.app.strategy.IActionBarStrategy
        public ActionBarConfig config(ActionBar actionBar, ActionBarSpec actionBarSpec) {
            ActionBarConfig actionBarConfigConfig = super.config(actionBar, actionBarSpec);
            if (actionBarConfigConfig == null) {
                actionBarConfigConfig = new ActionBarConfig();
            }
            actionBarConfigConfig.resizable = true;
            return actionBarConfigConfig;
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode actionMode) {
        Intent value = this.homeViewModel.getIntent().getValue();
        if (value != null && NoteIntent.ACTION_SEARCH_NOTES.equals(value.getAction())) {
            Logger.INSTANCE.d(TAG, "onDestroySearchMode change homeViewModel intent");
            this.homeViewModel.setIntent(new Intent("android.intent.action.MAIN"));
        }
        super.onDestroySearchMode(actionMode);
    }
}
