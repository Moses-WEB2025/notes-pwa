package com.miui.handwriting.common.ai;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;

/* JADX INFO: loaded from: classes3.dex */
public class FlexBoxItem extends RecyclerView.ViewHolder {
    public LinearLayout layout;
    public TextView textView;

    FlexBoxItem(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.tv_content);
        this.layout = (LinearLayout) view.findViewById(R.id.item_root);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }

    public void bind(String str, boolean z) {
        this.textView.setText(str);
        this.layout.setBackground(z ? AppCompatResources.getDrawable(HandWritingViewApplication.INSTANCE.getApplication(), R.drawable.creation_ai_recommend_selected_bg) : AppCompatResources.getDrawable(HandWritingViewApplication.INSTANCE.getApplication(), R.drawable.creation_ai_recommend_unselect_bg));
        this.textView.setTextColor(z ? HandWritingViewApplication.INSTANCE.getApplication().getColor(R.color.creation_ai_text_selected_bg) : HandWritingViewApplication.INSTANCE.getApplication().getColor(R.color.creation_ai_text_unselect_bg));
    }
}
