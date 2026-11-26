package com.miui.pc.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;
import com.miui.common.tool.CompatUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class SearchInputEditText extends AppCompatEditText {
    private static final int DRAWABLE_BOTTOM = 3;
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_TOP = 1;
    private Drawable mClearDrawable;
    private Drawable mSearchDrawable;

    public SearchInputEditText(Context context) {
        super(context);
        init();
    }

    public SearchInputEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SearchInputEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.pc_search_input_text_background);
        this.mSearchDrawable = getResources().getDrawable(R.drawable.pc_icon_search);
        this.mClearDrawable = getResources().getDrawable(R.drawable.pc_icon_clear);
        setCompoundDrawablePadding(2);
        setCompoundDrawablesWithIntrinsicBounds(this.mSearchDrawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
        CompatUtils.invokeCursorColor(this, getContext().getResources().getColor(R.color.theme_light_cursor_color), getContext().getResources().getColor(R.color.theme_light_cursor_handle_color));
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        setClearIconVisible(length() > 0);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        setClearIconVisible(length() > 0);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Drawable drawable;
        boolean z = true;
        if (motionEvent.getAction() == 1 && (drawable = getCompoundDrawables()[2]) != null && motionEvent.getX() <= getWidth() - getPaddingRight() && motionEvent.getX() >= (getWidth() - getPaddingRight()) - drawable.getBounds().width()) {
            setText("");
            clearFocus();
        } else {
            z = false;
        }
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        if (z && hasFocus()) {
            clearFocus();
        }
        return zOnTouchEvent;
    }

    public void setClearIconVisible(boolean z) {
        setCompoundDrawablesWithIntrinsicBounds(this.mSearchDrawable, getCompoundDrawables()[1], z ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }
}
