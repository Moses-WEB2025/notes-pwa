package com.miui.notes.ai.ui.fold.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class PolishTitleTextView extends AppCompatTextView {
    private static final String TAG = "PolishTitleTextView";
    private int currentNarrow;
    private int lastNarrow;

    public PolishTitleTextView(Context context) {
        super(context);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public PolishTitleTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public PolishTitleTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (RomUtils.isFoldDevice()) {
            this.lastNarrow = this.currentNarrow;
            this.currentNarrow = ScreenPosistionUtils.isInNarrowScreen(getContext()) ? 1 : 0;
        }
        if (!RomUtils.isFoldDevice() || this.lastNarrow == this.currentNarrow) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.polish_title_height);
        Logger.INSTANCE.i(TAG, " onConfigurationChanged reset current height " + dimensionPixelSize);
        ((RelativeLayout.LayoutParams) getLayoutParams()).height = dimensionPixelSize;
    }
}
