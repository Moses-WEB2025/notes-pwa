package com.miui.handwriting.common.view.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.listener.PageStateView;

/* JADX INFO: loaded from: classes3.dex */
public class StateView implements PageStateView {
    private int leftMargin;
    private StateTextView tv_index;
    private TextView tv_location;
    private StateTextView tv_scale;
    private View view;

    public void setTextColor(int i) {
    }

    public StateView(Context context) {
        this.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.creation_page_index_margin_left);
        init(context);
    }

    private void init(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.paint_state_view, (ViewGroup) null, false);
        this.view = viewInflate;
        this.tv_index = (StateTextView) viewInflate.findViewById(R.id.tv_page_index);
        this.tv_scale = (StateTextView) this.view.findViewById(R.id.tv_scale);
        this.tv_location = (TextView) this.view.findViewById(R.id.tv_location);
    }

    @Override // com.miui.libhandwritingpage.listener.PageStateView
    public void update(int i, int i2, float f, float f2, float f3) {
        int i3 = i + 1;
        this.tv_index.setText(i3 + " / " + i2);
        StateTextView stateTextView = this.tv_index;
        stateTextView.setContentDescription(stateTextView.getContext().getString(R.string.page_index_talkback, Integer.valueOf(i3), Integer.valueOf(i2)));
        int i4 = (int) (f * 100.0f);
        this.tv_scale.setText(i4 + "%");
        this.tv_scale.setContentDescription(i4 + "%");
        this.tv_location.setText("x:" + ((int) f2) + "y:" + ((int) f3));
    }

    @Override // com.miui.libhandwritingpage.listener.PageStateView
    public View getView() {
        return this.view;
    }

    public void showPageIndex(boolean z) {
        if (z) {
            this.tv_index.setVisibility(0);
            setLeftMargin(this.tv_scale, 0);
        } else {
            this.tv_index.setVisibility(8);
            setLeftMargin(this.tv_scale, this.leftMargin);
        }
    }

    private void setLeftMargin(View view, int i) {
        if (view == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.leftMargin = i;
        view.setLayoutParams(marginLayoutParams);
    }
}
