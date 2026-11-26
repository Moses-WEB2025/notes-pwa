package com.miui.notes.ai.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.ScreenPosistionUtils;
import com.miui.notes.ai.utils.TextExtKt;

/* JADX INFO: loaded from: classes4.dex */
public class AiRecommendItem extends LinearLayout {
    private static final String TAG = "AiRecommendItem";
    private int currentNarrow;
    private ImageView icon;
    private Drawable imageDrawable;
    private int lastNarrow;
    private TextView recommend;
    private String text;

    public AiRecommendItem(Context context) {
        super(context);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
    }

    public AiRecommendItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastNarrow = 0;
        this.currentNarrow = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AiRecommendItem);
        this.imageDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.AiRecommendItem_recommend_item_src);
        this.text = typedArrayObtainStyledAttributes.getString(R.styleable.AiRecommendItem_recommend_item_text);
        typedArrayObtainStyledAttributes.recycle();
        if (RomUtils.isFoldDevice()) {
            boolean zIsInNarrowScreen = ScreenPosistionUtils.isInNarrowScreen(context);
            this.lastNarrow = zIsInNarrowScreen ? 1 : 0;
            this.currentNarrow = zIsInNarrowScreen ? 1 : 0;
        }
    }

    public AiRecommendItem(Context context, AttributeSet attributeSet, int i) {
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.ai_recommend_item_icon_width), (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_height));
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.ai_recommend_item_icon_margin_start), (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_margin_top), (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_margin_right), 0);
        addView(this.icon, layoutParams);
        TextView textView = new TextView(getContext());
        this.recommend = textView;
        textView.setText(this.text);
        this.recommend.setMaxLines(2);
        this.recommend.setTextColor(getContext().getColor(R.color.ai_recommend_item_text_color));
        this.recommend.setEllipsize(TextUtils.TruncateAt.END);
        this.recommend.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_recommend_item_text_size));
        TextExtKt.setTextProperty(this.recommend, "400");
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.setMargins(RomUtils.isRTL() ? (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_bottom) : 0, (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_bottom), (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_right), (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_bottom));
        addView(this.recommend, layoutParams2);
        setEnabled(true);
    }

    public void setText(String str) {
        TextView textView = this.recommend;
        if (textView != null) {
            textView.setText(str);
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
        int dimension = (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_width);
        int dimension2 = (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_height);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.icon.getLayoutParams();
        layoutParams.width = dimension;
        layoutParams.height = dimension2;
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.ai_recommend_item_icon_margin_start), (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_margin_top), (int) getResources().getDimension(R.dimen.ai_recommend_item_icon_margin_right), 0);
        this.recommend.setTextSize(1, DisplayUtil.getXmlDef(getContext(), R.dimen.ai_recommend_item_text_size));
        ((LinearLayout.LayoutParams) this.recommend.getLayoutParams()).setMargins(RomUtils.isRTL() ? (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_bottom) : 0, (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_bottom), (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_right), (int) getResources().getDimension(R.dimen.ai_recommend_item_text_margin_bottom));
    }
}
