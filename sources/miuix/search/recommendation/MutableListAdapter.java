package miuix.search.recommendation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import miuix.search.common.R;

/* JADX INFO: loaded from: classes6.dex */
class MutableListAdapter extends RecyclerView.Adapter<MutableListItem> {
    public static final int FOOT_VIEW = 1;
    public static final int NORMAL_VIEW = 0;
    private Context mContext;
    private SimpleList mItems;

    public MutableListAdapter(Context context, SimpleList simpleList) {
        this.mContext = context;
        this.mItems = simpleList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MutableListItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new MutableListItem(LayoutInflater.from(this.mContext).inflate(R.layout.search_simple_list_item, viewGroup, false), i);
        }
        if (i == 1) {
            return new MutableListItem(LayoutInflater.from(this.mContext).inflate(R.layout.search_simple_list_clear_item, viewGroup, false), i);
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MutableListItem mutableListItem, int i) {
        if (getItemViewType(i) == 0) {
            mutableListItem.setText(this.mItems.get(i));
        } else if (getItemViewType(i) == 1) {
            mutableListItem.setText(this.mContext.getResources().getString(R.string.miuix_search_history_clear));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size() + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i == getItemCount() - 1 ? 1 : 0;
    }
}
