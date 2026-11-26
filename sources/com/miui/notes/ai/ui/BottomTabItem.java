package com.miui.notes.ai.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.common.tool.DisplayUtils;
import com.miui.notes.R;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class BottomTabItem extends LinearLayout {
    private ImageView icon;
    private Drawable imageDrawable;
    private TextView label;
    private String text;

    public BottomTabItem(Context context) {
        super(context);
    }

    public BottomTabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomTabItem);
        try {
            this.imageDrawable = typedArrayObtainStyledAttributes.getDrawable(0);
            this.text = typedArrayObtainStyledAttributes.getString(1);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public BottomTabItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BottomTabItem(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView imageView = new ImageView(getContext());
        this.icon = imageView;
        imageView.setImageDrawable(this.imageDrawable);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.ai_dialog_bottom_tab_icon_size), (int) getResources().getDimension(R.dimen.ai_dialog_bottom_tab_icon_size));
        layoutParams.gravity = 1;
        addView(this.icon, layoutParams);
        TextView textView = new TextView(getContext());
        this.label = textView;
        textView.setHyphenationFrequency(4);
        this.label.setGravity(1);
        this.label.setText(this.text);
        this.label.setTextSize(DisplayUtils.getXmlDef(getContext(), R.dimen.ai_dialog_bottom_label_size));
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 1;
        addView(this.label, layoutParams2);
        setEnabled(true);
    }

    @Override // android.view.View
    public void setEnabled(final boolean z) {
        Folme.clean(this);
        if (z) {
            Folme.use((View) this).touch().setScale(1.0f, ITouchStyle.TouchType.DOWN).setScale(1.0f, ITouchStyle.TouchType.UP).setAlpha(0.6f, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).handleTouchOf(this, new AnimConfig[0]);
        }
        setAlpha(z ? 1.0f : 0.6f);
        setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.ai.ui.BottomTabItem.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return !z;
            }
        });
    }
}
