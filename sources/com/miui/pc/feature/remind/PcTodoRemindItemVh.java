package com.miui.pc.feature.remind;

import android.view.View;
import android.view.ViewGroup;
import com.miui.common.dataremind.BaseRemindItemVh;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoRemindItemVh extends BaseRemindItemVh<PcTodoRemindData> {
    public static PcTodoRemindItemVh newInstance(ViewGroup viewGroup) {
        return new PcTodoRemindItemVh(UIUtils.inflateView(viewGroup, R.layout.pc_remind_list_todo_item));
    }

    public PcTodoRemindItemVh(View view) {
        super(view);
    }

    @Override // com.miui.common.dataremind.BaseRemindItemVh
    public void bind(PcTodoRemindData pcTodoRemindData, int i) {
        this.itemView.setLongClickable(true);
        this.mTvContent.setMaxLines(1);
        this.mTvContent.setText(pcTodoRemindData.mContent);
    }

    public void setMaxLines(int i) {
        this.mTvContent.setMaxLines(i);
    }
}
