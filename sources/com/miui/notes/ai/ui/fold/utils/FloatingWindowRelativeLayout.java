package com.miui.notes.ai.ui.fold.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class FloatingWindowRelativeLayout extends RelativeLayout {
    private static final String TAG = "FloatingWindowRelativeLayout";
    private int currentNarrow;
    private int lastNarrow;

    public FloatingWindowRelativeLayout(Context context) {
        super(context);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public FloatingWindowRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public FloatingWindowRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public FloatingWindowRelativeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        int dimensionPixelSize;
        super.onConfigurationChanged(configuration);
        if (RomUtils.isFoldDevice()) {
            this.lastNarrow = this.currentNarrow;
            this.currentNarrow = ScreenPosistionUtils.isInNarrowScreen(getContext()) ? 1 : 0;
        }
        if (!RomUtils.isFoldDevice() || this.lastNarrow == this.currentNarrow) {
            return;
        }
        boolean z = DisplayUtil.getScreenMetrics(getContext()).widthPixels < getResources().getDimensionPixelSize(R.dimen.ai_floating_window_width);
        if (ScreenPosistionUtils.isInNarrowScreen(getContext()) && z) {
            dimensionPixelSize = (int) (((double) getResources().getDimensionPixelSize(R.dimen.ai_floating_window_width)) * 0.92d);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.ai_floating_window_width);
        }
        Logger.INSTANCE.i(TAG, " onConfigurationChanged reset current width " + dimensionPixelSize);
        ((FrameLayout.LayoutParams) getLayoutParams()).width = dimensionPixelSize;
    }
}
