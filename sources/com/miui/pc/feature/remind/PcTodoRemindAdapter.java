package com.miui.pc.feature.remind;

import android.content.Context;
import android.view.ViewGroup;
import com.miui.common.dataremind.BaseRemindAdapter;
import com.miui.common.dataremind.BaseRemindItemVh;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoRemindAdapter extends BaseRemindAdapter<PcTodoRemindData> {
    public PcTodoRemindAdapter(Context context) {
        super(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseRemindItemVh<PcTodoRemindData> onCreateViewHolder(ViewGroup viewGroup, int i) {
        return PcTodoRemindItemVh.newInstance(viewGroup);
    }

    @Override // com.miui.common.dataremind.BaseRemindAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseRemindItemVh<PcTodoRemindData> baseRemindItemVh, int i) {
        super.onBindViewHolder((BaseRemindItemVh) baseRemindItemVh, i);
        if (this.mData.size() == 1) {
            ((PcTodoRemindItemVh) baseRemindItemVh).setMaxLines(4);
        }
    }
}
