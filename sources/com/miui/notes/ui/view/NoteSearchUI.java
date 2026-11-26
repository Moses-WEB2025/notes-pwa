package com.miui.notes.ui.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.search.NoteSearchFragment;
import miuix.view.SearchActionMode;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSearchUI {
    private static final String TAG = "NoteSearchUI";
    private View mAnimView;
    private final BindContext mBindContext;
    private final FragmentManager mFragmentManager;
    private final Fragment mHostFragment;
    private final OnSearchUIListener mListener;
    private SearchActionMode mSearchActionMode;
    private NoteSearchFragment mSearchFragment;
    private View mSearchView;
    private boolean mPrivateSearchMode = false;
    private final TextWatcher mSearchTextWatcher = new TextWatcher() { // from class: com.miui.notes.ui.view.NoteSearchUI.1
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String string = charSequence != null ? charSequence.toString() : null;
            if (NoteSearchUI.this.mSearchFragment == null) {
                return;
            }
            NoteSearchFragment noteSearchFragment = NoteSearchUI.this.mSearchFragment;
            if (string == null) {
                string = "";
            }
            noteSearchFragment.query(string);
        }
    };
    private final SearchActionMode.Callback mSearchActionModeCallback = new SearchActionMode.Callback() { // from class: com.miui.notes.ui.view.NoteSearchUI.2
        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            Logger.INSTANCE.d(NoteSearchUI.TAG, "onCreateActionMode");
            if (NoteSearchUI.this.mSearchFragment == null) {
                NoteSearchUI.this.updateSearchFragment(true, false, true);
            }
            NoteSearchUI.this.mSearchActionMode = (SearchActionMode) actionMode;
            NoteSearchUI.this.mSearchActionMode.setAnchorView(NoteSearchUI.this.mSearchView);
            if (NoteSearchUI.this.mAnimView != null) {
                NoteSearchUI.this.mSearchActionMode.setAnimateView(NoteSearchUI.this.mAnimView);
            }
            if (NoteSearchUI.this.mSearchFragment != null && NoteSearchUI.this.mSearchFragment.getView() != null) {
                NoteSearchUI.this.mSearchActionMode.setResultView(NoteSearchUI.this.mSearchFragment.getView());
            }
            EditText searchInput = NoteSearchUI.this.mSearchActionMode.getSearchInput();
            if (searchInput != null) {
                searchInput.setHint(R.string.search_hint);
                searchInput.addTextChangedListener(NoteSearchUI.this.mSearchTextWatcher);
            }
            if (NoteSearchUI.this.mListener != null) {
                NoteSearchUI.this.mListener.onEnterSearchMode();
            }
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            Logger.INSTANCE.d(NoteSearchUI.TAG, "onDestroyActionMode");
            EditText searchInput = ((SearchActionMode) actionMode).getSearchInput();
            if (searchInput != null) {
                searchInput.removeTextChangedListener(NoteSearchUI.this.mSearchTextWatcher);
            }
            if (NoteSearchUI.this.mSearchActionMode == null) {
                return;
            }
            NoteSearchUI.this.mSearchActionMode = null;
            if (NoteSearchUI.this.mSearchFragment != null) {
                NoteSearchUI.this.updateSearchFragment(false, true, false);
            }
            NoteSearchUI.this.mSearchFragment = null;
            if (NoteSearchUI.this.mListener != null) {
                NoteSearchUI.this.mListener.onExitSearchMode();
            }
        }
    };

    public interface OnSearchUIListener {
        void onEnterSearchMode();

        void onExitSearchMode();

        void onSearchItemClick(View view, Object obj, int i);

        void onSearchQuery(String str);
    }

    public NoteSearchUI(Fragment fragment, FragmentManager fragmentManager, OnSearchUIListener onSearchUIListener, BindContext bindContext) {
        this.mHostFragment = fragment;
        this.mFragmentManager = fragmentManager;
        this.mListener = onSearchUIListener;
        this.mBindContext = bindContext;
    }

    public void setPrivateSearchMode(boolean z) {
        this.mPrivateSearchMode = z;
    }

    public void startSearchMode(View view, View view2) {
        if (view == null) {
            return;
        }
        this.mSearchView = view;
        this.mAnimView = view2;
        if (this.mSearchActionMode == null) {
            view.startActionMode(this.mSearchActionModeCallback);
        }
    }

    public void searchByBody(View view, View view2, String str) {
        if (view == null) {
            return;
        }
        this.mSearchView = view;
        this.mAnimView = view2;
        if (this.mSearchActionMode == null) {
            view.startActionMode(this.mSearchActionModeCallback);
        }
        SearchActionMode searchActionMode = this.mSearchActionMode;
        if (searchActionMode != null) {
            EditText searchInput = searchActionMode.getSearchInput();
            searchInput.setText(str);
            searchInput.setSelection(str.length());
            searchInput.requestFocus();
        }
    }

    public boolean isSearchMode() {
        return this.mSearchActionMode != null;
    }

    public boolean finishSearchMode() {
        Object obj = this.mSearchActionMode;
        if (obj == null) {
            return false;
        }
        ((ActionMode) obj).finish();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSearchFragment(boolean z, boolean z2, boolean z3) {
        FragmentTransaction fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction();
        if (z) {
            NoteSearchFragment noteSearchFragment = (NoteSearchFragment) this.mFragmentManager.findFragmentByTag(NoteSearchFragment.TAG);
            this.mSearchFragment = noteSearchFragment;
            if (noteSearchFragment == null) {
                NoteSearchFragment noteSearchFragment2 = new NoteSearchFragment();
                this.mSearchFragment = noteSearchFragment2;
                fragmentTransactionBeginTransaction.add(android.R.id.content, noteSearchFragment2, NoteSearchFragment.TAG);
            }
            this.mSearchFragment.setQueryListener(new NoteSearchFragment.OnSearchQueryListener() { // from class: com.miui.notes.ui.view.NoteSearchUI$$ExternalSyntheticLambda0
                @Override // com.miui.notes.search.NoteSearchFragment.OnSearchQueryListener
                public final void onSearchQuery(String str) {
                    this.f$0.lambda$updateSearchFragment$0(str);
                }
            });
            this.mSearchFragment.setItemClickListener(new NoteSearchFragment.OnSearchItemClickListener() { // from class: com.miui.notes.ui.view.NoteSearchUI$$ExternalSyntheticLambda1
                @Override // com.miui.notes.search.NoteSearchFragment.OnSearchItemClickListener
                public final void onSearchItemClick(View view, ItemCache itemCache, int i) {
                    this.f$0.lambda$updateSearchFragment$1(view, itemCache, i);
                }
            });
            this.mSearchFragment.setBindContext(this.mBindContext);
            this.mSearchFragment.setPrivateSearchMode(this.mPrivateSearchMode);
        } else if (z2) {
            NoteSearchFragment noteSearchFragment3 = this.mSearchFragment;
            if (noteSearchFragment3 != null) {
                fragmentTransactionBeginTransaction.remove(noteSearchFragment3);
            }
        } else if (z3) {
            NoteSearchFragment noteSearchFragment4 = this.mSearchFragment;
            if (noteSearchFragment4 != null) {
                fragmentTransactionBeginTransaction.show(noteSearchFragment4);
            }
        } else {
            NoteSearchFragment noteSearchFragment5 = this.mSearchFragment;
            if (noteSearchFragment5 != null) {
                fragmentTransactionBeginTransaction.hide(noteSearchFragment5);
            }
        }
        fragmentTransactionBeginTransaction.commitNowAllowingStateLoss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSearchFragment$0(String str) {
        OnSearchUIListener onSearchUIListener = this.mListener;
        if (onSearchUIListener != null) {
            onSearchUIListener.onSearchQuery(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSearchFragment$1(View view, ItemCache itemCache, int i) {
        OnSearchUIListener onSearchUIListener = this.mListener;
        if (onSearchUIListener != null) {
            onSearchUIListener.onSearchItemClick(view, itemCache, i);
        }
    }
}
