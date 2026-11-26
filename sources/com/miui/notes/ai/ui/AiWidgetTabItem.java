package com.miui.notes.ai.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.ui.material.MaterialEffectContainer;
import com.miui.notes.ai.utils.Constant;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.ai.utils.TextExtKt;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* JADX INFO: loaded from: classes4.dex */
public class AiWidgetTabItem extends LinearLayout {
    private static final String TAG = "AiWidgetTabItem";
    private final int DEFAULT_TYPE;
    private final int GENERIC_TYPE;
    private final int SUMMARY_TYPE;
    private final int TYPE_EASY;
    private final int TYPE_FRIENDLY;
    private final int TYPE_GENERIC;
    private final int TYPE_PROFESSIONAL;
    AiPolishListener aiPolishListener;
    private int currentNarrow;
    private ImageView icon;
    private int iconSize;
    private Drawable imageDrawable;
    private boolean isTabItem;
    private TextView label;
    private int lastNarrow;
    private MaterialEffectContainer materialContainer;
    private String text;
    private int type;

    public interface AiPolishListener {
        void touchPolish(int i);
    }

    public AiWidgetTabItem(Context context) {
        super(context);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        this.DEFAULT_TYPE = 0;
        this.SUMMARY_TYPE = 1;
        this.GENERIC_TYPE = 2;
        this.TYPE_GENERIC = 1;
        this.TYPE_PROFESSIONAL = 2;
        this.TYPE_EASY = 3;
        this.TYPE_FRIENDLY = 4;
    }

    public AiWidgetTabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        this.DEFAULT_TYPE = 0;
        this.SUMMARY_TYPE = 1;
        this.GENERIC_TYPE = 2;
        this.TYPE_GENERIC = 1;
        this.TYPE_PROFESSIONAL = 2;
        this.TYPE_EASY = 3;
        this.TYPE_FRIENDLY = 4;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AiWidgetTabItem);
        this.imageDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.AiWidgetTabItem_src);
        this.text = typedArrayObtainStyledAttributes.getString(R.styleable.AiWidgetTabItem_text);
        this.isTabItem = typedArrayObtainStyledAttributes.getBoolean(R.styleable.AiWidgetTabItem_tab, false);
        this.type = typedArrayObtainStyledAttributes.getInt(R.styleable.AiWidgetTabItem_text_color, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public AiWidgetTabItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        this.DEFAULT_TYPE = 0;
        this.SUMMARY_TYPE = 1;
        this.GENERIC_TYPE = 2;
        this.TYPE_GENERIC = 1;
        this.TYPE_PROFESSIONAL = 2;
        this.TYPE_EASY = 3;
        this.TYPE_FRIENDLY = 4;
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        Resources resources;
        int i;
        Resources resources2;
        int i2;
        super.onFinishInflate();
        ImageView imageView = new ImageView(getContext());
        this.icon = imageView;
        imageView.setImageDrawable(this.imageDrawable);
        boolean z = this.isTabItem;
        if (!z) {
            if (z) {
                resources2 = getResources();
                i2 = R.dimen.ai_widget_tab_polish_icon_size;
            } else {
                resources2 = getResources();
                i2 = R.dimen.ai_widget_tab_icon_polish_size;
            }
            this.iconSize = (int) resources2.getDimension(i2);
            this.materialContainer = (MaterialEffectContainer) View.inflate(getContext(), R.layout.ai_material_item_layout, null);
            int i3 = this.iconSize;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i3, i3);
            layoutParams.gravity = 17;
            this.icon.setPadding((int) getResources().getDimension(R.dimen.ai_widget_tab_icon_padding), (int) getResources().getDimension(R.dimen.ai_widget_tab_icon_padding), (int) getResources().getDimension(R.dimen.ai_widget_tab_icon_padding), (int) getResources().getDimension(R.dimen.ai_widget_tab_icon_padding));
            this.materialContainer.addView(this.icon);
            addView(this.materialContainer, layoutParams);
        } else {
            if (z) {
                resources = getResources();
                i = R.dimen.ai_widget_tab_icon_size;
            } else {
                resources = getResources();
                i = R.dimen.ai_widget_tab_icon_polish_size;
            }
            this.iconSize = (int) resources.getDimension(i);
            int i4 = this.iconSize;
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i4, i4);
            layoutParams2.gravity = 17;
            addView(this.icon, layoutParams2);
        }
        TextView textView = new TextView(getContext());
        this.label = textView;
        textView.setText(this.text);
        this.label.setTextColor(getResources().getColor(R.color.ai_text_color_widget_summary));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.gravity = 17;
        this.label.setGravity(1);
        layoutParams3.topMargin = (int) getResources().getDimension(R.dimen.ai_tab_bottom);
        int i5 = this.type;
        if (i5 == 1) {
            this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_widget_tab_summary_test_size));
        } else if (i5 == 2) {
            this.label.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_widget_tab_generic_test_size));
            this.label.setTextColor(getContext().getColor(R.color.ai_text_color_widget_generic));
            layoutParams3.topMargin += (int) getResources().getDimension(R.dimen.ai_widget_buttom_increase_size);
            this.label.setImportantForAccessibility(2);
        }
        TextExtKt.setTextProperty(this.label, Constant.AI_WIDGET_TAB_TEXT_WGHT);
        addView(this.label, layoutParams3);
        setEnabled(true);
    }

    public View viewTouch() {
        MaterialEffectContainer materialEffectContainer = this.materialContainer;
        if (materialEffectContainer != null) {
            Folme.useAt(materialEffectContainer).touch().setTint(0.0f, 0.0f, 0.0f, 0.0f).setScale(0.93f, ITouchStyle.TouchType.DOWN).handleTouchOf(this.materialContainer, new AnimConfig[0]);
            this.materialContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ai.ui.AiWidgetTabItem$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$viewTouch$0(view);
                }
            });
        }
        return this.materialContainer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$viewTouch$0(View view) {
        this.aiPolishListener.touchPolish(isStingType(this.text));
    }

    private int isStingType(String str) {
        if (getResources().getString(R.string.ai_text_widget_general).equals(str)) {
            return 1;
        }
        if (getResources().getString(R.string.ai_text_widget_professional).equals(str)) {
            return 2;
        }
        if (getResources().getString(R.string.ai_text_widget_easy).equals(str)) {
            return 3;
        }
        return getResources().getString(R.string.ai_text_widget_friendly).equals(str) ? 4 : 0;
    }

    public void setOnScreenChangeListener(AiPolishListener aiPolishListener) {
        this.aiPolishListener = aiPolishListener;
    }
}
