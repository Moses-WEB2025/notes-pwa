package com.miui.notes.search;

import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.Logger;
import com.miui.common.view.gridlayoutmanager.NoteGridLayoutManager;
import com.miui.notes.R;
import com.miui.notes.adapter.AiSearchCursorAdapter;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.adapter.SearchCursorAdapter;
import com.miui.notes.basefeature.notelist.BaseNoteListAdapter;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.home.viewmodel.HomeViewModel;
import com.miui.notes.tool.PreferenceUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.miuixbasewidget.widget.EmptyStateView;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSearchFragment extends Fragment {
    public static final String TAG = "NoteSearchFragment";
    private BaseNoteListAdapter mAdapter;
    private BindContext mBindContext;
    private EmptyStateView mEmptyView;
    private int mGridItemSpace;
    private HomeViewModel mHomeViewModel;
    private OnSearchItemClickListener mItemClickListener;
    private OnSearchQueryListener mQueryListener;
    private RecyclerView mRecyclerView;
    private boolean mPrivateSearchMode = false;
    private String mSearchText = "";
    private final RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() { // from class: com.miui.notes.search.NoteSearchFragment.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int spanCount = recyclerView.getLayoutManager() instanceof NoteGridLayoutManager ? ((NoteGridLayoutManager) recyclerView.getLayoutManager()).getSpanCount() : 1;
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            rect.left = NoteSearchFragment.this.mGridItemSpace;
            rect.right = NoteSearchFragment.this.mGridItemSpace;
            rect.bottom = NoteSearchFragment.this.mGridItemSpace;
            rect.top = NoteSearchFragment.this.mGridItemSpace;
            if (childAdapterPosition / spanCount == 0) {
                rect.top = 0;
            }
        }
    };

    public interface OnSearchItemClickListener {
        void onSearchItemClick(View view, ItemCache itemCache, int i);
    }

    public interface OnSearchQueryListener {
        void onSearchQuery(String str);
    }

    public void setBindContext(BindContext bindContext) {
        this.mBindContext = bindContext;
    }

    public void setQueryListener(OnSearchQueryListener onSearchQueryListener) {
        this.mQueryListener = onSearchQueryListener;
    }

    public void setItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        this.mItemClickListener = onSearchItemClickListener;
    }

    public void setPrivateSearchMode(boolean z) {
        this.mPrivateSearchMode = z;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        HomeViewModel homeViewModel = (HomeViewModel) new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        this.mHomeViewModel = homeViewModel;
        if (bundle == null || homeViewModel.isSearchMode()) {
            return;
        }
        getParentFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0078  */
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        Logger.INSTANCE.d(TAG, "onCreateView");
        View viewInflate = layoutInflater.inflate(R.layout.fragment_note_search, viewGroup, false);
        this.mRecyclerView = (RecyclerView) viewInflate.findViewById(R.id.search_result_list);
        this.mEmptyView = (EmptyStateView) viewInflate.findViewById(R.id.search_empty_view);
        boolean gridMode = PreferenceUtils.getGridMode(requireContext());
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_base);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.miuix_theme_padding_horizontal_common);
        if (gridMode) {
            i = 2;
            int i2 = dimensionPixelSize2 / 2;
            this.mGridItemSpace = i2;
            RecyclerView recyclerView = this.mRecyclerView;
            recyclerView.setPadding(i2, dimensionPixelSize, i2, recyclerView.getPaddingBottom());
            if (MiuixUIUtils.getFontLevel(requireContext()) == 2) {
            }
            this.mRecyclerView.setLayoutManager(new NoteGridLayoutManager(i, 1));
            if (gridMode) {
                this.mRecyclerView.addItemDecoration(this.mItemDecoration);
            }
            BaseNoteListAdapter baseNoteListAdapter = new BaseNoteListAdapter(this.mPrivateSearchMode);
            this.mAdapter = baseNoteListAdapter;
            baseNoteListAdapter.setGridMode(gridMode);
            this.mAdapter.setBindContext(this.mBindContext);
            this.mAdapter.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.search.NoteSearchFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onCreateView$0(view);
                }
            });
            this.mRecyclerView.setAdapter(this.mAdapter);
            this.mHomeViewModel.getSearchDataCursor().observe(getViewLifecycleOwner(), new Observer() { // from class: com.miui.notes.search.NoteSearchFragment$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    this.f$0.onSearchDataChanged((Cursor) obj);
                }
            });
            return viewInflate;
        }
        this.mGridItemSpace = 0;
        RecyclerView recyclerView2 = this.mRecyclerView;
        recyclerView2.setPadding(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2, recyclerView2.getPaddingBottom());
        i = 1;
        this.mRecyclerView.setLayoutManager(new NoteGridLayoutManager(i, 1));
        if (gridMode) {
            this.mRecyclerView.addItemDecoration(this.mItemDecoration);
        }
        BaseNoteListAdapter baseNoteListAdapter2 = new BaseNoteListAdapter(this.mPrivateSearchMode);
        this.mAdapter = baseNoteListAdapter2;
        baseNoteListAdapter2.setGridMode(gridMode);
        this.mAdapter.setBindContext(this.mBindContext);
        this.mAdapter.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.search.NoteSearchFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreateView$0(view);
            }
        });
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mHomeViewModel.getSearchDataCursor().observe(getViewLifecycleOwner(), new Observer() { // from class: com.miui.notes.search.NoteSearchFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.onSearchDataChanged((Cursor) obj);
            }
        });
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        OnSearchItemClickListener onSearchItemClickListener;
        int childLayoutPosition = this.mRecyclerView.getChildLayoutPosition(view);
        ItemCache item = this.mAdapter.getItem(childLayoutPosition);
        if (item == null || (onSearchItemClickListener = this.mItemClickListener) == null) {
            return;
        }
        onSearchItemClickListener.onSearchItemClick(view, item, childLayoutPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSearchDataChanged(Cursor cursor) {
        if (!this.mHomeViewModel.isSearchMode() || cursor == null || cursor.isClosed()) {
            return;
        }
        this.mAdapter.frozeData(true);
        if (this.mPrivateSearchMode) {
            this.mAdapter.setAiSearch(true);
            this.mAdapter.setCursor(new SearchCursorAdapter(cursor, this.mSearchText));
        } else if (this.mHomeViewModel.isAiSearchMode() && this.mBindContext != null) {
            this.mAdapter.setAiSearch(true);
            this.mAdapter.setCursor(new AiSearchCursorAdapter(cursor, this.mBindContext.getAiQueryIdArr()));
        } else {
            this.mAdapter.setAiSearch(false);
            this.mAdapter.setCursor(cursor);
        }
        this.mAdapter.frozeData(false);
        this.mAdapter.notifyDataSetChanged();
        boolean z = cursor.getCount() == 0 && !TextUtils.isEmpty(this.mSearchText);
        EmptyStateView emptyStateView = this.mEmptyView;
        if (emptyStateView != null) {
            emptyStateView.setVisibility(z ? 0 : 8);
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.setVisibility(z ? 8 : 0);
        }
    }

    public void query(String str) {
        Logger.INSTANCE.d(TAG, "query: " + str);
        this.mSearchText = str;
        OnSearchQueryListener onSearchQueryListener = this.mQueryListener;
        if (onSearchQueryListener != null) {
            onSearchQueryListener.onSearchQuery(str);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.d(TAG, "onResume");
        if (TextUtils.isEmpty(this.mSearchText) || this.mQueryListener == null) {
            return;
        }
        this.mAdapter.notifyDataSetChanged();
        this.mQueryListener.onSearchQuery(this.mSearchText);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        Logger.INSTANCE.d(TAG, "onDestroyView");
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
        this.mRecyclerView = null;
        this.mEmptyView = null;
        this.mAdapter = null;
        this.mQueryListener = null;
        this.mItemClickListener = null;
        super.onDestroyView();
    }
}
