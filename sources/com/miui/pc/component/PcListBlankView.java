package com.miui.pc.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PcListBlankView {
    public static final String NOTE_RES = "note_blank_icon";
    public static final String TODO_RES = "todo_blank_icon";
    private boolean isActive;
    private Context mContext;
    private String mDarkRes;
    protected ImageView mImageView;
    private String mLightRes;
    private ViewGroup mParent;
    protected String mRes;
    private TextView mTvTitle;

    public PcListBlankView(Context context, View view, int i, String str) {
        this.mContext = context;
        this.mRes = str;
        this.mParent = (ViewGroup) view;
        this.mLightRes = str + "_light";
        this.mDarkRes = str + "_dark";
        this.mRes = this.mLightRes;
        TextView textView = (TextView) this.mParent.findViewById(R.id.content);
        this.mTvTitle = textView;
        textView.setText(i);
        this.mImageView = (ImageView) this.mParent.findViewById(R.id.icon);
        if (getView() != null) {
            getView().setClickable(false);
        }
    }

    public View getView() {
        return this.mImageView;
    }

    public void onDataEmpty(boolean z) {
        if (getView() == null) {
            return;
        }
        if (z) {
            getView().setVisibility(0);
            this.mParent.setVisibility(0);
            this.mTvTitle.setVisibility(0);
        } else {
            this.mParent.setVisibility(8);
            getView().setVisibility(8);
            this.mTvTitle.setVisibility(8);
        }
    }

    public boolean isNightMode() {
        return (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
    }
}
