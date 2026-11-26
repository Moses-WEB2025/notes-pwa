package com.miui.notes.component;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.common.tool.ContentUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class CustomMindTextView extends AppCompatTextView {
    private CharSequence mContent;
    private int mTextLength;

    public CustomMindTextView(Context context) {
        this(context, null);
    }

    public CustomMindTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomMindTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setMindText(CharSequence charSequence, ArrayList<String> arrayList) {
        SpannableStringBuilder spannableStringBuilderChangeArrHighLight = ContentUtils.changeArrHighLight(charSequence, arrayList);
        this.mContent = spannableStringBuilderChangeArrHighLight;
        setText(spannableStringBuilderChangeArrHighLight);
        TextPaint paint = getPaint();
        paint.setTextSize(getTextSize());
        this.mTextLength = (int) paint.measureText(this.mContent.toString());
    }

    public void setParams(LinearLayout.LayoutParams layoutParams) {
        setLayoutParams(layoutParams);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }
}
