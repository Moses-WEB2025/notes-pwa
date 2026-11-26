package com.miui.notes.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import com.miui.common.tool.DisplayUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class RichTextCheckedTextView extends AppCompatCheckedTextView {
    private static final String TAG = "RichTextCheckedTextView";
    private static Boolean mUiModeIsNight;
    private StateListDrawable mDrawableSingle;

    public RichTextCheckedTextView(Context context) {
        this(context, null);
    }

    public RichTextCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RichTextCheckedTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RichTextIcon);
        this.mDrawableSingle = (StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(2);
        typedArrayObtainStyledAttributes.recycle();
        Boolean bool = mUiModeIsNight;
        if (bool == null) {
            mUiModeIsNight = Boolean.valueOf(DisplayUtils.isNightMode());
        } else if (bool.booleanValue() != DisplayUtils.isNightMode()) {
            mUiModeIsNight = Boolean.valueOf(DisplayUtils.isNightMode());
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDrawableSingle != null) {
            setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableSingle);
        }
    }

    @Override // android.widget.CheckedTextView, android.widget.Checkable
    public void setChecked(boolean z) {
        boolean zIsChecked = isChecked();
        super.setChecked(z);
        if (zIsChecked != isChecked()) {
            invalidate();
        }
        StateListDrawable stateListDrawable = this.mDrawableSingle;
        if (stateListDrawable != null) {
            stateListDrawable.selectDrawable(!isChecked() ? 1 : 0);
        }
    }

    public void setDrawable(Drawable drawable) {
        if (this.mDrawableSingle == drawable) {
            return;
        }
        if (drawable instanceof StateListDrawable) {
            StateListDrawable stateListDrawable = (StateListDrawable) drawable;
            stateListDrawable.selectDrawable(!isChecked() ? 1 : 0);
            if (this.mDrawableSingle != null) {
                this.mDrawableSingle = stateListDrawable;
                setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableSingle);
                invalidate();
                return;
            }
            return;
        }
        Logger.INSTANCE.w(TAG, "only accept instance of StateListDrawable");
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        StateListDrawable stateListDrawable = this.mDrawableSingle;
        if (stateListDrawable != null) {
            stateListDrawable.draw(canvas);
        }
    }

    private static void setDrawableBounds(int i, int i2, Drawable drawable) {
        int intrinsicWidth = (int) (((i - drawable.getIntrinsicWidth()) * 1.0f) / 2.0f);
        int intrinsicHeight = (int) (((i2 - drawable.getIntrinsicHeight()) * 1.0f) / 2.0f);
        drawable.setBounds(intrinsicWidth, intrinsicHeight, drawable.getIntrinsicWidth() + intrinsicWidth, drawable.getIntrinsicHeight() + intrinsicHeight);
    }
}
