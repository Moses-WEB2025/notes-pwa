package miuix.search.recommendation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import miuix.recyclerview.widget.MiuiDefaultItemAnimator;
import miuix.search.common.R;

/* JADX INFO: loaded from: classes6.dex */
abstract class ListDelegate<Holder extends RecyclerView.ViewHolder> {
    Context mContext;
    TextView mGroupHeader;
    SimpleList mProvider;
    miuix.recyclerview.widget.RecyclerView mRecyclerView;
    private ListDelegate<Holder>.TitleHolder mTitle;

    abstract RecyclerView.Adapter<Holder> onCreateAdapter(SimpleList simpleList);

    abstract RecyclerView.LayoutManager onCreateLayoutManager();

    abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    abstract void onItemClick(CharSequence charSequence);

    ListDelegate() {
    }

    void onAttach(Context context) {
        this.mContext = context;
    }

    void onViewCreated(View view, Bundle bundle) {
        this.mGroupHeader = (TextView) view.findViewById(R.id.label);
        miuix.recyclerview.widget.RecyclerView recyclerView = (miuix.recyclerview.widget.RecyclerView) view.findViewById(android.R.id.list);
        this.mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(onCreateLayoutManager());
        this.mRecyclerView.setItemAnimator(new MiuiDefaultItemAnimator());
        onDataSetChanged();
        ListDelegate<Holder>.TitleHolder titleHolder = this.mTitle;
        if (titleHolder != null) {
            titleHolder.consume();
            this.mTitle = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void setGroupTitle(final int i) {
        setGroupTitle((ListDelegate<Holder>.TitleHolder) new ListDelegate<Holder>.TitleHolder() { // from class: miuix.search.recommendation.ListDelegate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // miuix.search.recommendation.ListDelegate.TitleHolder
            boolean isEmpty() {
                return i == 0;
            }

            @Override // miuix.search.recommendation.ListDelegate.TitleHolder
            void onConsume() {
                ListDelegate.this.mGroupHeader.setText(i);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    void setGroupTitle(final CharSequence charSequence) {
        setGroupTitle((ListDelegate<Holder>.TitleHolder) new ListDelegate<Holder>.TitleHolder() { // from class: miuix.search.recommendation.ListDelegate.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // miuix.search.recommendation.ListDelegate.TitleHolder
            boolean isEmpty() {
                return TextUtils.isEmpty(charSequence);
            }

            @Override // miuix.search.recommendation.ListDelegate.TitleHolder
            void onConsume() {
                ListDelegate.this.mGroupHeader.setText(charSequence);
            }
        });
    }

    private void setGroupTitle(ListDelegate<Holder>.TitleHolder titleHolder) {
        if (titleHolder.consume()) {
            return;
        }
        this.mTitle = titleHolder;
    }

    void attachDataSet(SimpleList simpleList) {
        if (this.mProvider != simpleList) {
            this.mProvider = simpleList;
            onDataSetChanged();
        }
    }

    void detachDataSet() {
        if (this.mProvider != null) {
            this.mProvider = null;
            onDataSetChanged();
        }
    }

    private void onDataSetChanged() {
        miuix.recyclerview.widget.RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            SimpleList simpleList = this.mProvider;
            if (simpleList != null) {
                recyclerView.setAdapter(onCreateAdapter(simpleList));
            } else {
                recyclerView.setAdapter(null);
            }
        }
    }

    void onDestroyView() {
        this.mRecyclerView = null;
        this.mGroupHeader = null;
    }

    private abstract class TitleHolder {
        abstract boolean isEmpty();

        abstract void onConsume();

        private TitleHolder() {
        }

        final boolean consume() {
            if (ListDelegate.this.mGroupHeader == null) {
                return false;
            }
            if (isEmpty()) {
                ListDelegate.this.mGroupHeader.setVisibility(8);
                return true;
            }
            ListDelegate.this.mGroupHeader.setVisibility(0);
            onConsume();
            return true;
        }
    }
}
