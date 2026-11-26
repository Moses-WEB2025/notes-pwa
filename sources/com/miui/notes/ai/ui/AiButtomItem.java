package com.miui.notes.ai.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.Constant;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.ai.utils.TextExtKt;

/* JADX INFO: loaded from: classes4.dex */
public class AiButtomItem extends LinearLayout {
    private static final String TAG = "AiButtomItem";
    private int currentNarrow;
    private ImageView icon;
    private Drawable imageDrawable;
    private TextView label;
    private int lastNarrow;
    private String text;

    public AiButtomItem(Context context) {
        super(context);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
    }

    public AiButtomItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AiButtomItem);
        this.imageDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.AiButtomItem_item_src);
        this.text = typedArrayObtainStyledAttributes.getString(R.styleable.AiButtomItem_item_text);
        typedArrayObtainStyledAttributes.recycle();
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public AiButtomItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView imageView = new ImageView(getContext());
        this.icon = imageView;
        imageView.setImageDrawable(this.imageDrawable);
        int dimension = (int) getResources().getDimension(R.dimen.ai_widget_buttom_icon_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimension, dimension);
        layoutParams.gravity = 17;
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.ai_widget_top_icon_size);
        addView(this.icon, layoutParams);
        TextView textView = new TextView(getContext());
        this.label = textView;
        textView.setText(this.text);
        this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_bottom_tab_icon_text_size));
        this.label.setGravity(1);
        DisplayUtil.setMiuiMediumTypeFace(this.label);
        this.label.setTextColor(getContext().getColor(R.color.ai_widget_tab_text_color));
        TextExtKt.setTextProperty(this.label, Constant.AI_BUTTOM_TEXT_WGHT);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.topMargin = (int) getResources().getDimension(R.dimen.ai_widget_top_label_size);
        layoutParams2.bottomMargin = (int) getResources().getDimension(R.dimen.ai_widget_buttom_label_size);
        layoutParams2.leftMargin = (int) getResources().getDimension(R.dimen.ai_widget_left_right_label_size);
        layoutParams2.rightMargin = (int) getResources().getDimension(R.dimen.ai_widget_left_right_label_size);
        layoutParams2.gravity = 17;
        addView(this.label, layoutParams2);
        setEnabled(true);
    }

    public void setText(String str) {
        TextView textView = this.label;
        if (textView != null) {
            textView.setText(str);
            this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_bottom_tab_icon_text_size));
            TextExtKt.setTextProperty(this.label, Constant.AI_BUTTOM_TEXT_WGHT);
        }
    }

    public void setImageDrawable(Drawable drawable) {
        ImageView imageView = this.icon;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public void setIconVisible(boolean z) {
        ImageView imageView = this.icon;
        if (imageView != null) {
            if (z) {
                imageView.setVisibility(0);
                this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_bottom_tab_icon_text_size));
                TextExtKt.setTextProperty(this.label, Constant.AI_BUTTOM_TEXT_WGHT);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.topMargin = (int) getResources().getDimension(R.dimen.ai_widget_top_label_size);
                layoutParams.bottomMargin = (int) getResources().getDimension(R.dimen.ai_widget_buttom_label_size);
                layoutParams.gravity = 17;
                this.label.setLayoutParams(layoutParams);
                return;
            }
            imageView.setVisibility(8);
            this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_bottom_only_back_text_size));
            TextExtKt.setTextProperty(this.label, Constant.AI_BUTTOM_TEXT_WGHT);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.topMargin = (int) getResources().getDimension(R.dimen.ai_bottom_only_back_margin_top);
            layoutParams2.bottomMargin = (int) getResources().getDimension(R.dimen.ai_bottom_only_back_margin_top);
            layoutParams2.gravity = 17;
            this.label.setLayoutParams(layoutParams2);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (RomUtils.isFoldDevice()) {
            this.lastNarrow = this.currentNarrow;
            this.currentNarrow = ScreenPosistionUtils.isInNarrowScreen(getContext()) ? 1 : 0;
        }
        if (!RomUtils.isFoldDevice() || this.lastNarrow == this.currentNarrow) {
            return;
        }
        int dimension = (int) getResources().getDimension(R.dimen.ai_widget_buttom_icon_size);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.icon.getLayoutParams();
        layoutParams.height = dimension;
        layoutParams.width = dimension;
        layoutParams.gravity = 17;
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.ai_widget_top_icon_size);
        this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_bottom_tab_icon_text_size));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.label.getLayoutParams();
        layoutParams2.topMargin = (int) getResources().getDimension(R.dimen.ai_widget_top_label_size);
        layoutParams2.bottomMargin = (int) getResources().getDimension(R.dimen.ai_widget_buttom_label_size);
        layoutParams2.gravity = 17;
    }
}
