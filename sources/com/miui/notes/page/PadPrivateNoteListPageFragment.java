package com.miui.notes.page;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import androidx.loader.app.LoaderManager;
import com.miui.notes.R;
import com.miui.notes.adapter.SearchCursorAdapter;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.provider.NoteProviderAccessUtils;
import com.miui.notes.store.ItemStore;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.notes.ui.widget.FloatingActionButton;

/* JADX INFO: loaded from: classes4.dex */
public class PadPrivateNoteListPageFragment extends PadNoteListPageFragment implements SearchView.OnQueryTextListener, SearchCallback.OnSearchListener, SearchCallback.TextSearch {
    public static final String TAG = "PadPrivateNotePageFragment";
    private String mSearchString = "";
    private Runnable mOpenNotePostTask = null;

    @Override // com.miui.notes.page.NoteListPageFragment
    protected int getLayoutRes() {
        return R.layout.new_phone_note_list_fragment_private;
    }

    @Override // com.miui.notes.page.NoteListPageFragment, android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimUpdate(boolean z, float f) {
    }

    @Override // com.miui.notes.page.PadNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        this.mIsFromPrivate = true;
        super.onCreate(bundle);
        setThemeRes(getThemeRes());
        this.mIsSelected = true;
        this.mIsInPrivate = true;
        PreferenceUtils.setIsInPrivate(this.mIsInPrivate);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        PadPrivateNoteListPageFragment padPrivateNoteListPageFragment;
        super.onActivityCreated(bundle);
        Intent intent = getActivity().getIntent();
        if (intent == null || !NoteIntent.ACTION_VIEW_HIDDEN_NOTE_BY_ID.equals(intent.getAction())) {
            return;
        }
        long longExtra = intent.getLongExtra("android.intent.extra.UID", -1L);
        if (longExtra != -1) {
            padPrivateNoteListPageFragment = this;
            padPrivateNoteListPageFragment.openNote(longExtra, -4L, 1);
        } else {
            padPrivateNoteListPageFragment = this;
        }
        padPrivateNoteListPageFragment.getActivity().setIntent(null);
    }

    public void openNote(final long j, long j2, int i) {
        if (j == -1) {
            openFolder(j2);
            return;
        }
        Cursor cursorQueryPrivateNote = NoteProviderAccessUtils.queryPrivateNote(getContext(), j);
        cursorQueryPrivateNote.moveToFirst();
        if (cursorQueryPrivateNote.getCount() > 0) {
            this.homeViewModel.onOpenNote(j2, new NoteCache(ItemStore.parseNote(cursorQueryPrivateNote)), this.mBindContext.getSearchToken());
            openFolder(j2);
            this.mOpenNotePostTask = new Runnable() { // from class: com.miui.notes.page.PadPrivateNoteListPageFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openNote$0(j);
                }
            };
        } else {
            openFolder(j2);
        }
        cursorQueryPrivateNote.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openNote$0(long j) {
        int searchCursorPositionById = this.mAdapter.getSearchCursorPositionById(j);
        if (searchCursorPositionById < 0 || this.mRvLayoutManager == null) {
            return;
        }
        this.mRvLayoutManager.scrollToPosition(searchCursorPositionById);
        this.mOpenNotePostTask = null;
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            if (!isAdded() || getActivity() == null) {
                return true;
            }
            getActivity().getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.miui.notes.page.PadNoteListPageFragment, com.miui.notes.page.NoteListPageFragment
    protected void initView(View view) {
        this.mBtnContentAdd = (FloatingActionButton) view.findViewById(R.id.content_add);
        this.mBtnHandWriteAdd = (FloatingActionButton) view.findViewById(R.id.handwrite_add);
        super.initView(view);
        this.mSearchBar.setVisibility(8);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment
    protected BaseNoteListPageFragment.EditModeCallback createEditModeCallback() {
        return new PrivateEditModeCallback();
    }

    private class PrivateEditModeCallback extends BaseNoteListPageFragment.EditModeCallback {
        private PrivateEditModeCallback() {
            super();
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            boolean zOnCreateActionMode = super.onCreateActionMode(actionMode, menu);
            menu.findItem(R.id.move).setVisible(false);
            MenuItem menuItemFindItem = menu.findItem(R.id.hide);
            menuItemFindItem.setTitle(R.string.menu_unhide);
            if (PadPrivateNoteListPageFragment.this.getContext() != null) {
                menuItemFindItem.setIcon(PadPrivateNoteListPageFragment.this.getContext().getDrawable(R.drawable.miui_ic_unsecret));
            }
            return zOnCreateActionMode;
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (PadPrivateNoteListPageFragment.this.mRecyclerView != null) {
                PadPrivateNoteListPageFragment.this.mRecyclerView.stopScroll();
            }
            if (menuItem.getItemId() == R.id.hide) {
                PadPrivateNoteListPageFragment padPrivateNoteListPageFragment = PadPrivateNoteListPageFragment.this;
                padPrivateNoteListPageFragment.unHideCheckedNotes(padPrivateNoteListPageFragment.mRecyclerViewWrapper.getCheckedItemIds());
                return true;
            }
            return super.onActionItemClicked(actionMode, menuItem);
        }

        @Override // com.miui.notes.page.BaseNoteListPageFragment.EditModeCallback, android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            super.onDestroyActionMode(actionMode);
        }
    }

    @Override // com.miui.notes.page.PadNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setAccessibilityBtnSequence();
        getActivity().getWindow().addFlags(8192);
        if (this.mOpenNotePostTask != null) {
            this.handler.postDelayed(this.mOpenNotePostTask, 50L);
        }
    }

    @Override // com.miui.notes.page.PadNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseListPageFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.miui.notes.page.NoteListPageFragment, androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mSearchBarTextView.setText("");
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    public void onSearchRequest(View view, final boolean z, final String str) {
        if (this.mRecyclerViewWrapper.isInActionMode()) {
            return;
        }
        if (this.mSearchModeListener == null) {
            this.mSearchModeListener = new SearchCallback(getActivity(), this, this);
        }
        this.mSearchModeListener.setup(view, this.mNestedHeaderLayout, this.mNestedHeaderLayout.getScrollableView());
        if (this.handler != null) {
            this.handler.postDelayed(new Runnable() { // from class: com.miui.notes.page.PadPrivateNoteListPageFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSearchRequest$1(z, str);
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSearchRequest$1(boolean z, String str) {
        startActionMode(this.mSearchModeListener);
        if (z) {
            realSetSearchText(str);
        }
    }

    private void setAccessibilityBtnSequence() {
        if (this.mAdapter.getItemCount() == 0) {
            this.mBlankView.setAccessibilityTraversalAfter(R.id.action_bar_title);
            this.mBtnContentAdd.setAccessibilityTraversalAfter(R.id.phone_note_list_blank_view);
            this.mBtnHandWriteAdd.setAccessibilityTraversalAfter(R.id.content_add);
            this.mBtnHandWriteAdd.setAccessibilityTraversalBefore(R.id.pad_edit_blank_view);
            return;
        }
        this.mRecyclerView.setImportantForAccessibility(1);
        this.mRecyclerView.setAccessibilityTraversalAfter(R.id.action_bar_title);
        this.mBtnContentAdd.setAccessibilityTraversalAfter(R.id.recycler_view);
        this.mBtnHandWriteAdd.setAccessibilityTraversalAfter(R.id.note_add);
        this.mBtnHandWriteAdd.setAccessibilityTraversalBefore(R.id.web_view);
    }

    private void realSetSearchText(String str) {
        if (this.mSearchModeListener != null) {
            this.mSearchModeListener.setSearchText(str, false, this.mBindContext.getSearchType() == 2);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        this.mBindContext.setSearchToken(this.mSearchModeListener.getSearchText());
        requestListData(0L);
        return true;
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment
    public void requestListData(long j) {
        if (this.mBindContext.getSearchToken() != null) {
            this.mSearchString = this.mBindContext.getSearchToken();
        }
        this.mBindContext.setSearchTokenBackUp(this.mBindContext.getSearchToken());
        this.mBindContext.setSearchToken("");
        this.homeViewModel.startQuery(LoaderManager.getInstance(this), this.homeViewModel.getFolderId(), this.mBindContext, 1, 0L);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.TextSearch
    public void onTextSearch(String str, int i) {
        this.mSearchWords = str;
        this.mBindContext.setSearchToken(str);
        this.mBindContext.setSearchType(i);
        requestListData(0L);
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.TextSearch
    public void finishIfNeeded() {
        if ((this.mBindContext.getSearchType() == 1 || this.mBindContext.getSearchType() == 2) && getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStart(boolean z) {
        if (isAdded() && z) {
            this.mNestedHeaderLayout.setAutoTriggerClose(true);
            this.mNestedHeaderLayout.setTriggerViewVisible(false);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onSearchModeAnimStop(boolean z) {
        if (isAdded() && !z) {
            this.mNestedHeaderLayout.setTriggerViewVisible(true);
        }
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onCreateSearchMode(ActionMode actionMode, Menu menu) {
        showCreateBtn(false, "PadPrivateNotePageFragment.onSearchMode");
    }

    @Override // com.miui.notes.page.PadNoteListPageFragment, com.miui.notes.page.NoteListPageFragment, com.miui.notes.ui.view.SearchCallback.OnSearchListener
    public void onDestroySearchMode(ActionMode actionMode) {
        this.mSearchString = "";
        showCreateBtn(true, "PadPrivateNotePageFragment.onDestroySearchMode");
        finishIfNeeded();
    }

    @Override // com.miui.notes.page.NoteListPageFragment, com.miui.notes.page.BaseNoteListPageFragment, com.miui.notes.page.BaseListPageFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this.mSearchModeListener != null) {
            this.mSearchModeListener = null;
        }
        this.mIsInPrivate = false;
        PreferenceUtils.setIsInPrivate(this.mIsInPrivate);
        super.onDestroy();
    }

    @Override // com.miui.notes.page.NoteListPageFragment
    protected void onNotesLoadFinished(Cursor cursor) {
        Logger.INSTANCE.d(TAG, "onNotesLoadFinished: ");
        this.mAdapter.notifyDataSetChanged();
        this.mAdapter.setCursor(new SearchCursorAdapter(cursor, this.mSearchString));
        onDataEmpty(this.mAdapter.getItemCount() == 0);
        setAccessibilityBtnSequence();
        if (this.mSearchWords != null && (this.mSearchModeListener == null || !this.mSearchWords.equals(this.mSearchModeListener.getSearchText()))) {
            if (this.mSearchModeListener != null) {
                this.mSearchModeListener.removeTextChangedListener();
            }
            setSearchText(this.mSearchWords);
        }
        this.mSearchWords = null;
    }

    private void setSearchText(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        if (this.mSearchModeListener == null && this.mSearchBar != null) {
            onSearchRequest(this.mSearchBar, true, str);
        } else {
            startActionMode(this.mSearchModeListener);
            realSetSearchText(str);
        }
    }
}
