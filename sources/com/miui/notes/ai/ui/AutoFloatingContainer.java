package com.miui.notes.ai.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import miuix.core.util.MiuixUIUtils;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes4.dex */
public class AutoFloatingContainer extends RelativeLayout {
    private View mBottomFlagView;
    private View mContent;
    private int mFloatingHeight;
    private int mFloatingWidth;
    private boolean mIsFloatingMode;

    public AutoFloatingContainer(Context context) {
        super(context);
        this.mIsFloatingMode = false;
        init();
    }

    public AutoFloatingContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsFloatingMode = false;
        init();
    }

    public AutoFloatingContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsFloatingMode = false;
        init();
    }

    public AutoFloatingContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsFloatingMode = false;
        init();
    }

    private void init() {
        this.mFloatingHeight = getResources().getDimensionPixelSize(R.dimen.bottom_sheet_floating_height);
        this.mFloatingWidth = getResources().getDimensionPixelSize(R.dimen.bottom_sheet_floating_width);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mContent.getLayoutParams();
        if (supportFloatingMode(getContext().getResources().getDisplayMetrics().density, size2, size)) {
            this.mIsFloatingMode = true;
            layoutParams.height = this.mFloatingHeight;
            layoutParams.width = this.mFloatingWidth;
            layoutParams.topMargin = 0;
            layoutParams.removeRule(12);
            layoutParams.addRule(13, -1);
            layoutParams.removeRule(2);
            this.mContent.setLayoutParams(layoutParams);
            this.mContent.setBackgroundResource(R.drawable.shape_sheet_dialog_floating_bg);
            this.mBottomFlagView.setVisibility(8);
        } else {
            this.mIsFloatingMode = false;
            layoutParams.height = -1;
            layoutParams.width = -1;
            layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.floating_content_margin_top);
            layoutParams.removeRule(13);
            layoutParams.addRule(2, R.id.bottom_flag);
            this.mContent.setLayoutParams(layoutParams);
            this.mContent.setBackgroundResource(R.drawable.shape_sheet_dialog_bg);
            this.mBottomFlagView.setVisibility(0);
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mContent = findViewById(R.id.content);
        this.mBottomFlagView = findViewById(R.id.bottom_flag);
    }

    public boolean isFloatingMode() {
        return this.mIsFloatingMode;
    }

    private boolean supportFloatingMode(float f, int i, int i2) {
        return DeviceHelper.detectType(NoteApp.getInstance()) != 1 && MiuixUIUtils.px2dp(f, (float) i) > 600 && MiuixUIUtils.px2dp(f, (float) i2) > 600;
    }
}
