package com.miui.notes.page;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import androidx.loader.app.LoaderManager;
import com.miui.common.stat.NotesPageStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.adapter.SearchCursorAdapter;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.ui.view.NoteSearchUI;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.richeditor.EditorActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class PhonePrivateNoteListPageFragment extends PhoneNoteListPageFragment implements SearchView.OnQueryTextListener, SearchCallback.OnSearchListener, SearchCallback.TextSearch {
    public static final String TAG = "PrivateNoteListFragment";
    private String mSearchString = "";
    private int mCursorCount = 0;

    @Override // com.miui.notes.page.NoteListPageFragment
    protected int getLayoutRes() {
        return R.layout.new_phone_note_list_fragment_private;
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment, androidx.fragment.app.Fragment
    public void onMultiWindowModeChanged(boolean z) {
    }

    @Override // com.miui.notes.page.NoteListPageFragment, android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        return true;
    }

    @Override // com.miui.notes.page.NoteListPageFragment, android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        this.mIsFromPrivate = true;
        super.onCreate(bundle);
        setThemeRes(getThemeRes());
        this.mIsInPrivate = true;
        Logger.INSTANCE.d(TAG, "onCreate");
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment, com.miui.notes.page.NoteListPageFragment
    protected void initView(View view) {
        super.initView(view);
        this.mBtnContentAdd.setBackgroundColor(getResources().getColor(R.color.subfolder_actionbar_background_color1, null));
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment
    protected BaseNoteListPageFragment.EditModeCallback createEditModeCallback() {
        return new HiddenEditModeCallback();
    }

    private class HiddenEditModeCallback extends BaseNoteListPageFragment.EditModeCallback {
        private HiddenEditModeCallback() {
            super();
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            boolean zOnCreateActionMode = super.onCreateActionMode(actionMode, menu);
            menu.findItem(R.id.move).setVisible(false);
            MenuItem menuItemFindItem = menu.findItem(R.id.hide);
            menuItemFindItem.setTitle(R.string.menu_unhide);
            if (PhonePrivateNoteListPageFragment.this.getContext() != null) {
                menuItemFindItem.setIcon(PhonePrivateNoteListPageFragment.this.getContext().getDrawable(R.drawable.miui_ic_unsecret));
            }
            return zOnCreateActionMode;
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            if (PhonePrivateNoteListPageFragment.this.mSearchBar != null) {
                PhonePrivateNoteListPageFragment.this.mSearchBar.setEnabled(true);
            }
            super.onDestroyActionMode(actionMode);
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (PhonePrivateNoteListPageFragment.this.getRecyclerView() != null) {
                PhonePrivateNoteListPageFragment.this.getRecyclerView().stopScroll();
            }
            if (menuItem.getItemId() == R.id.hide) {
                PhonePrivateNoteListPageFragment phonePrivateNoteListPageFragment = PhonePrivateNoteListPageFragment.this;
                phonePrivateNoteListPageFragment.unHideCheckedNotes(phonePrivateNoteListPageFragment.mRecyclerViewWrapper.getCheckedItemIds());
                return true;
            }
            return super.onActionItemClicked(actionMode, menuItem);
        }
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment, com.miui.notes.page.NoteListPageFragment
    protected void addNote(View view) {
        NotesPageStat.reportNewNote();
        NotesPageStat.QUIT_TYPE = NotesPageStat.GO_NEW_NOTES;
        addNoteFromFab(this, view, Integer.valueOf(getContext().getColor(R.color.subfolder_actionbar_background_color1)), this.homeViewModel.getFolderId(), EditorActionMode.EDIT, true);
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getActivity().getWindow().addFlags(8192);
        if (this.mBtnContentAdd != null) {
            this.mBtnContentAdd.setVisibility(computeMenuAddVisibility());
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mSearchBarTextView.setText("");
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void initAiQueryAndStartSearch(View view) {
        onSearchRequest(view, false, "");
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void onSearchRequest(View view, boolean z, String str) {
        if (this.mRecyclerViewWrapper == null || !this.mRecyclerViewWrapper.isInActionMode()) {
            this.mNestedHeaderLayout.setAutoTriggerClose(false);
            if (this.mNoteSearchUI == null) {
                this.mNoteSearchUI = new NoteSearchUI(this, getParentFragmentManager(), this, this.mBindContext);
                this.mNoteSearchUI.setPrivateSearchMode(true);
            }
            this.mNoteSearchUI.startSearchMode(view, this.mNestedHeaderLayout.getScrollableView());
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onSearchQuery(String str) {
        Logger.INSTANCE.d(TAG, "onSearchQuery via NoteSearchUI: " + str);
        this.mSearchString = str;
        this.mBindContext.setSearchToken(str);
        if (TextUtils.isEmpty(str) || !isAdded()) {
            return;
        }
        this.homeViewModel.startPrivateSearchQuery(LoaderManager.getInstance(this), this.mBindContext, 4);
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void requestSearchData(long j) {
        if (isAdded()) {
            this.homeViewModel.startPrivateSearchQuery(LoaderManager.getInstance(this), this.mBindContext, 4);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onExitSearchMode() {
        Logger.INSTANCE.d(TAG, "onExitSearchMode via NoteSearchUI");
        this.homeViewModel.setSearchMode(false);
        this.mSearchString = "";
        this.mBindContext.setSearchToken("");
        showCreateBtn(true, "PrivateNoteListFragment.onExitSearchMode");
        getLoaderManager().destroyLoader(4);
        requestListData(0L);
        finishIfNeeded();
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onEnterSearchMode() {
        Logger.INSTANCE.d(TAG, "onEnterSearchMode via NoteSearchUI");
        this.homeViewModel.setSearchMode(true);
        showCreateBtn(false, "PrivateNoteListFragment.onEnterSearchMode");
        NotesPageStat.reportSearch();
        NotesPageStat.QUIT_TYPE = NotesPageStat.GO_SEARCH;
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.NoteSearchUI.OnSearchUIListener
    public void onSearchItemClick(View view, Object obj, int i) {
        if (obj instanceof ItemCache) {
            ItemCache itemCache = (ItemCache) obj;
            if (itemCache.getCacheObject() instanceof NoteCache) {
                openSearchResultNote(view, (NoteCache) itemCache.getCacheObject(), this.mSearchString);
            }
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment
    public void requestListData(long j) {
        this.mBindContext.setSearchToken("");
        if (isAdded()) {
            this.homeViewModel.startQuery(LoaderManager.getInstance(this), -4L, this.mBindContext, 3, 0L);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.TextSearch
    public void onTextSearch(String str, int i) {
        this.mSearchWords = str;
        this.mBindContext.setSearchToken(str);
        this.mBindContext.setSearchType(i);
        requestListData(0L);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean z) {
        if (isAdded() && z) {
            this.mNestedHeaderLayout.setInSearchMode(true);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean z) {
        if (isAdded() && !z) {
            this.mNestedHeaderLayout.setInSearchMode(false);
        }
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
        if (this.mSearchModeListener != null) {
            this.mSearchModeListener = null;
        }
        super.onDestroy();
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void onNotesLoadFinished(Cursor cursor) {
        if (this.mRecyclerView.removeCallbacks(this.mPendingStopQueryTask)) {
            this.mRecyclerView.suppressLayout(false);
        }
        this.mCursorCount = cursor.getCount();
        this.mAdapter.setAiSearch(true);
        this.mAdapter.setCursor(new SearchCursorAdapter(cursor, ""));
        onDataEmpty(this.mAdapter.getItemCount() == 0);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mIsInPrivate = false;
    }

    @Override // com.miui.notes.page.PhoneNoteListPageFragment
    protected void toDetailActivityResult() {
        if (getView() == null) {
            return;
        }
        this.homeViewModel.toDetailResultCallback();
        getView().postDelayed(new Runnable() { // from class: com.miui.notes.page.PhonePrivateNoteListPageFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toDetailActivityResult$0();
            }
        }, 400L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toDetailActivityResult$0() {
        if (!isAdded() || getView() == null) {
            return;
        }
        this.mAdapter.frozeData(false);
        if (this.homeViewModel.isSearchMode() && !TextUtils.isEmpty(this.mSearchString)) {
            this.mBindContext.setSearchToken(this.mSearchString);
            requestSearchData(0L);
        } else {
            requestListData(0L);
        }
        Utils.hideSoftInput(getView());
    }
}
