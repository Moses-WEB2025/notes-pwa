package miuix.search.recommendation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import miuix.search.common.R;

/* JADX INFO: loaded from: classes6.dex */
class FlexboxAdapter extends RecyclerView.Adapter<Flexbox> {
    private LayoutInflater mLayoutInflater;
    private SimpleList mProvider;

    public FlexboxAdapter(Context context, SimpleList simpleList) {
        this.mProvider = simpleList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Flexbox onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Flexbox(this.mLayoutInflater.inflate(R.layout.search_flex_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(Flexbox flexbox, int i) {
        flexbox.setText(this.mProvider.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mProvider.size();
    }
}
