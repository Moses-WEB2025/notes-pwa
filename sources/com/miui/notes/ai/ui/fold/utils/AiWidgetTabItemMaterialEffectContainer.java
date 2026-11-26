package com.miui.notes.ai.ui.fold.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.miui.notes.ai.ui.material.MaterialEffectContainer;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AiWidgetTabItemMaterialEffectContainer extends MaterialEffectContainer {
    private static final String TAG = "AiWidgetTabItemMaterialEffectContainer";
    private int currentNarrow;
    private int lastNarrow;

    public AiWidgetTabItemMaterialEffectContainer(Context context) {
        super(context);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public AiWidgetTabItemMaterialEffectContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public AiWidgetTabItemMaterialEffectContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    @Override // com.miui.notes.ai.ui.material.MaterialEffectContainer, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (RomUtils.isFoldDevice()) {
            this.lastNarrow = this.currentNarrow;
            this.currentNarrow = ScreenPosistionUtils.isInNarrowScreen(getContext()) ? 1 : 0;
        }
        if (!RomUtils.isFoldDevice() || this.lastNarrow == this.currentNarrow) {
            return;
        }
        ((LinearLayout.LayoutParams) getLayoutParams()).width = DisplayUtil.dip2px(getContext(), 106.67f);
    }
}
