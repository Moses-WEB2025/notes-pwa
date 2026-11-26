package miuix.search.recommendation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.flexbox.FlexboxLayoutManager;
import miuix.search.common.R;

/* JADX INFO: loaded from: classes6.dex */
abstract class FlexDelegate extends ListDelegate<Flexbox> {
    private RecyclerView.OnChildAttachStateChangeListener mItemAttachStateWatcher = new RecyclerView.OnChildAttachStateChangeListener() { // from class: miuix.search.recommendation.FlexDelegate.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewAttachedToWindow(View view) {
            if (!view.hasOnClickListeners()) {
                view.setOnClickListener(FlexDelegate.this.mItemClickListener);
                return;
            }
            throw new IllegalStateException("Flexbox View shouldn't register any OnClickListener");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewDetachedFromWindow(View view) {
            view.setOnClickListener(null);
        }
    };
    private View.OnClickListener mItemClickListener = new View.OnClickListener() { // from class: miuix.search.recommendation.FlexDelegate.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int adapterPosition = FlexDelegate.this.mRecyclerView.getChildViewHolder(view).getAdapterPosition();
            FlexDelegate flexDelegate = FlexDelegate.this;
            flexDelegate.onItemClick(flexDelegate.mProvider.get(adapterPosition));
        }
    };

    FlexDelegate() {
    }

    @Override // miuix.search.recommendation.ListDelegate
    View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.search_flex_list_layout, viewGroup, false);
    }

    @Override // miuix.search.recommendation.ListDelegate
    void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mItemAttachStateWatcher);
    }

    @Override // miuix.search.recommendation.ListDelegate
    RecyclerView.Adapter<Flexbox> onCreateAdapter(SimpleList simpleList) {
        return new FlexboxAdapter(this.mContext, simpleList);
    }

    @Override // miuix.search.recommendation.ListDelegate
    RecyclerView.LayoutManager onCreateLayoutManager() {
        return new FlexboxLayoutManager(this.mContext, 0, 1);
    }
}
