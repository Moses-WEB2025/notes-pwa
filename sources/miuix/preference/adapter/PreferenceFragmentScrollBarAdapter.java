package miuix.preference.adapter;

import androidx.recyclerview.widget.RecyclerView;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.internal.app.widget.ActionBarView;
import miuix.miuixbasewidget.widget.ScrollableView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes6.dex */
public class PreferenceFragmentScrollBarAdapter implements ScrollableView {
    private ActionBar mActionBar;
    private ActionBarView mActionBarView;
    private RecyclerView mRecyclerView;
    private boolean mSpringBackEnable;
    private SpringBackLayout mSpringBackLayout;
    private ScrollableView.OnActionDownListener onActionDownListener;
    private ScrollableView.OnActionUpListener onActionUpListener;
    private RecyclerView.OnScrollListener recyclerViewScrollListener;
    private ScrollableView.OnScrollListener scrollListener;

    public PreferenceFragmentScrollBarAdapter(ActionBar actionBar, RecyclerView recyclerView, SpringBackLayout springBackLayout) {
        this.mRecyclerView = recyclerView;
        this.mActionBar = actionBar;
        if (actionBar != null) {
            this.mActionBarView = (ActionBarView) actionBar.getActionBarView();
        }
        this.mSpringBackLayout = springBackLayout;
        if (springBackLayout != null) {
            this.mSpringBackEnable = springBackLayout.springBackEnable();
        }
        initListeners();
    }

    private void initListeners() {
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: miuix.preference.adapter.PreferenceFragmentScrollBarAdapter.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if ((i == 1 || i == 2) && PreferenceFragmentScrollBarAdapter.this.scrollListener != null) {
                    PreferenceFragmentScrollBarAdapter.this.scrollListener.onScroll(PreferenceFragmentScrollBarAdapter.this.getScrollY(), PreferenceFragmentScrollBarAdapter.this.getScrollRange());
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (PreferenceFragmentScrollBarAdapter.this.scrollListener != null) {
                    PreferenceFragmentScrollBarAdapter.this.scrollListener.onScroll(PreferenceFragmentScrollBarAdapter.this.getScrollY(), PreferenceFragmentScrollBarAdapter.this.getScrollRange());
                }
            }
        };
        this.recyclerViewScrollListener = onScrollListener;
        this.mRecyclerView.addOnScrollListener(onScrollListener);
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public int getScrollY() {
        int iComputeVerticalScrollOffset = this.mRecyclerView.computeVerticalScrollOffset();
        ActionBarView actionBarView = this.mActionBarView;
        return (actionBarView == null || !actionBarView.isResizable()) ? iComputeVerticalScrollOffset : (iComputeVerticalScrollOffset + this.mActionBarView.getExpandedHeight()) - this.mActionBarView.getHeight();
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public int getScrollRange() {
        int iComputeVerticalScrollRange = this.mRecyclerView.computeVerticalScrollRange() - this.mRecyclerView.computeVerticalScrollExtent();
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView != null && actionBarView.isResizable()) {
            return (this.mActionBarView.getExpandedHeight() - this.mActionBarView.getCollapsedHeight()) + Math.max(0, iComputeVerticalScrollRange);
        }
        return Math.max(0, iComputeVerticalScrollRange);
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void scrollTo(int i) {
        SpringBackLayout springBackLayout = this.mSpringBackLayout;
        if (springBackLayout != null && this.mSpringBackEnable) {
            springBackLayout.setSpringBackEnable(false);
        }
        this.mRecyclerView.nestedScrollBy(0, i - getScrollY());
        SpringBackLayout springBackLayout2 = this.mSpringBackLayout;
        if (springBackLayout2 == null || !this.mSpringBackEnable) {
            return;
        }
        springBackLayout2.setSpringBackEnable(true);
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void setOnScrollListener(ScrollableView.OnScrollListener onScrollListener) {
        this.scrollListener = onScrollListener;
    }

    public void setOnActionDownListener(ScrollableView.OnActionDownListener onActionDownListener) {
        this.onActionDownListener = onActionDownListener;
    }

    public void setOnActionUpListener(ScrollableView.OnActionUpListener onActionUpListener) {
        this.onActionUpListener = onActionUpListener;
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void actionUpEventCallBack() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.startNestedScroll(2, 0);
            this.mRecyclerView.stopNestedScroll(0);
        }
        ScrollableView.OnActionUpListener onActionUpListener = this.onActionUpListener;
        if (onActionUpListener != null) {
            onActionUpListener.onActionUp();
        }
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void actionDownEventCallBack() {
        ScrollableView.OnActionDownListener onActionDownListener = this.onActionDownListener;
        if (onActionDownListener != null) {
            onActionDownListener.onActionDown();
        }
    }

    public void removeListeners() {
        RecyclerView.OnScrollListener onScrollListener = this.recyclerViewScrollListener;
        if (onScrollListener != null) {
            this.mRecyclerView.removeOnScrollListener(onScrollListener);
        }
    }

    public void refresh() {
        ScrollableView.OnScrollListener onScrollListener = this.scrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScroll(getScrollY(), getScrollRange());
        }
    }
}
