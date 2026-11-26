package miuix.search.recommendation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.search.common.R;

/* JADX INFO: loaded from: classes6.dex */
abstract class MutableListDelegate extends ListDelegate<MutableListItem> {
    private RecyclerView.OnChildAttachStateChangeListener mItemAttachStateWatcher = new RecyclerView.OnChildAttachStateChangeListener() { // from class: miuix.search.recommendation.MutableListDelegate.2
        @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewAttachedToWindow(View view) {
            MutableListItem mutableListItem = (MutableListItem) MutableListDelegate.this.mRecyclerView.getChildViewHolder(view);
            mutableListItem.content.setOnClickListener(MutableListDelegate.this.mItemClickListener);
            if (mutableListItem.getItemViewType() == 0) {
                mutableListItem.delete.setOnClickListener(MutableListDelegate.this.mItemClickListener);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewDetachedFromWindow(View view) {
            if (MutableListDelegate.this.mRecyclerView == null) {
                return;
            }
            MutableListItem mutableListItem = (MutableListItem) MutableListDelegate.this.mRecyclerView.getChildViewHolder(view);
            mutableListItem.content.setOnClickListener(MutableListDelegate.this.mItemClickListener);
            if (mutableListItem.getItemViewType() == 0) {
                mutableListItem.delete.setOnClickListener(MutableListDelegate.this.mItemClickListener);
            }
        }
    };
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: miuix.search.recommendation.MutableListDelegate$$ExternalSyntheticLambda0
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f$0.m4359lambda$new$0$miuixsearchrecommendationMutableListDelegate(view);
        }
    };
    View rootView;

    MutableListDelegate() {
    }

    @Override // miuix.search.recommendation.ListDelegate
    View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.search_simple_list_layout, viewGroup, false);
        if (this.mProvider == null || this.mProvider.size() == 0) {
            Folme.useAt(this.rootView).visible().setHide();
        } else {
            Folme.useAt(this.rootView).visible().setShow();
        }
        return this.rootView;
    }

    @Override // miuix.search.recommendation.ListDelegate
    void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mItemAttachStateWatcher);
    }

    @Override // miuix.search.recommendation.ListDelegate
    void onDestroyView() {
        removeItemAttachStateWatcher();
        super.onDestroyView();
    }

    @Override // miuix.search.recommendation.ListDelegate
    RecyclerView.Adapter<MutableListItem> onCreateAdapter(SimpleList simpleList) {
        return new MutableListAdapter(this.mContext, simpleList);
    }

    @Override // miuix.search.recommendation.ListDelegate
    RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(this.mContext);
    }

    void onItemDeleted(int i) {
        this.mProvider.remove(i);
        this.mRecyclerView.getAdapter().notifyItemRemoved(i);
        if (this.mProvider.size() == 0) {
            DataSetCleared();
        }
    }

    void removeItemAttachStateWatcher() {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.removeOnChildAttachStateChangeListener(this.mItemAttachStateWatcher);
        }
    }

    void onDataSetCleared() {
        DataSetCleared();
    }

    private void DataSetCleared() {
        View view = this.rootView;
        if (view != null) {
            Folme.useAt(view).visible().hide(new AnimConfig().addListeners(new TransitionListener() { // from class: miuix.search.recommendation.MutableListDelegate.1
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    MutableListDelegate.this.mProvider.clear();
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$miuix-search-recommendation-MutableListDelegate, reason: not valid java name */
    /* synthetic */ void m4359lambda$new$0$miuixsearchrecommendationMutableListDelegate(View view) {
        ViewParent parent = view.getParent();
        View view2 = view;
        while (parent != this.mRecyclerView) {
            view2 = (View) view.getParent();
            parent = view2.getParent();
        }
        MutableListItem mutableListItem = (MutableListItem) this.mRecyclerView.getChildViewHolder(view2);
        if (view == mutableListItem.content && mutableListItem.getItemViewType() == 0) {
            int adapterPosition = mutableListItem.getAdapterPosition();
            if (adapterPosition != -1) {
                onItemClick(this.mProvider.get(adapterPosition));
                return;
            }
            return;
        }
        if (view == mutableListItem.delete && mutableListItem.getItemViewType() == 0) {
            int adapterPosition2 = mutableListItem.getAdapterPosition();
            if (adapterPosition2 != -1) {
                onItemDeleted(adapterPosition2);
                return;
            }
            return;
        }
        if (mutableListItem.getItemViewType() == 1) {
            onDataSetCleared();
        }
    }
}
