package com.miui.handwriting.common.view.page;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.TimeUtils;
import miuix.androidbasewidget.widget.EditText;
import miuix.autodensity.AutoDensityConfig;

/* JADX INFO: loaded from: classes3.dex */
public class HeaderView {
    private static final String TAG = "HeaderView";
    private final View headerView;
    private final Context mContext;
    public long mCreationTime;
    public TextView mCreationTimeView;
    public EditText mCreationTitle;
    public boolean mIsCreationTitleEnable = false;

    public interface OnTitleChangedListener {
        void onTitleChanged(String str);
    }

    public HeaderView(Context context) {
        this.mContext = context;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.layout_header_view, (ViewGroup) null, false);
        this.headerView = viewInflate;
        this.mCreationTitle = (EditText) viewInflate.findViewById(R.id.creation_title);
        this.mCreationTimeView = (TextView) viewInflate.findViewById(R.id.creation_title_time);
    }

    public void onConfigurationChanged(Configuration configuration) {
        AutoDensityConfig.forceUpdateDensity(this.mContext);
        this.mCreationTitle.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_text));
        this.mCreationTitle.setHintTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_hint));
        this.mCreationTimeView.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_time_color));
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.creation_action_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mCreationTitle.setCompoundDrawablesRelative(drawable, null, null, null);
        this.mCreationTimeView.setText(TimeUtils.formatTimestamp(this.mCreationTime, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
    }

    private void updateView() {
        this.mCreationTitle.setEnabled(false);
        this.mIsCreationTitleEnable = false;
    }
}
