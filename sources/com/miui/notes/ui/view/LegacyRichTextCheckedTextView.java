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
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class LegacyRichTextCheckedTextView extends AppCompatCheckedTextView {
    private static final String TAG = "LegacyRichTextCheckedTextView";
    private static Drawable mBgCheckedLeft;
    private static Drawable mBgCheckedRight;
    private static Drawable mBgCheckedSingle;
    private static Drawable mBgUncheckedLeft;
    private static Drawable mBgUncheckedRight;
    private static Drawable mBgUncheckedSingle;
    private static Boolean mUiModeIsNight;
    private StateListDrawable mDrawableLeft;
    private StateListDrawable mDrawableRight;
    private StateListDrawable mDrawableSingle;
    private int mPadding;

    private static void initBgDrawable(Context context) {
    }

    public void updateBgDrawable(Context context, Theme theme) {
    }

    public LegacyRichTextCheckedTextView(Context context) {
        this(context, null);
    }

    public LegacyRichTextCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LegacyRichTextCheckedTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPadding = 40;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RichTextIcon);
        this.mDrawableSingle = (StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(2);
        typedArrayObtainStyledAttributes.recycle();
        Boolean bool = mUiModeIsNight;
        if (bool == null) {
            mUiModeIsNight = Boolean.valueOf(DisplayUtils.isNightMode());
            initBgDrawable(context);
        } else if (bool.booleanValue() != DisplayUtils.isNightMode()) {
            mUiModeIsNight = Boolean.valueOf(DisplayUtils.isNightMode());
            initBgDrawable(context);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDrawableSingle != null) {
            setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableSingle);
        }
        if (this.mDrawableLeft != null) {
            setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableLeft);
        }
        if (this.mDrawableRight != null) {
            setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableRight);
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
        StateListDrawable stateListDrawable2 = this.mDrawableLeft;
        if (stateListDrawable2 != null) {
            stateListDrawable2.selectDrawable(!isChecked() ? 1 : 0);
        }
        StateListDrawable stateListDrawable3 = this.mDrawableRight;
        if (stateListDrawable3 != null) {
            stateListDrawable3.selectDrawable(!isChecked() ? 1 : 0);
        }
    }

    public void setDrawable(Drawable drawable) {
        if (this.mDrawableSingle == drawable || this.mDrawableLeft == drawable || this.mDrawableRight == drawable) {
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
            } else if (this.mDrawableLeft != null) {
                this.mDrawableLeft = stateListDrawable;
                setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableLeft);
                invalidate();
                return;
            } else {
                if (this.mDrawableRight != null) {
                    this.mDrawableRight = stateListDrawable;
                    setDrawableBounds(getMeasuredWidth(), getMeasuredHeight(), this.mDrawableRight);
                    invalidate();
                    return;
                }
                Logger.INSTANCE.w(TAG, "seems impossible?");
                return;
            }
        }
        Logger.INSTANCE.w(TAG, "only accept instance of StateListDrawable");
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawableSingle != null) {
            isChecked();
            this.mDrawableSingle.draw(canvas);
        } else if (this.mDrawableLeft != null) {
            isChecked();
            this.mDrawableLeft.draw(canvas);
        } else if (this.mDrawableRight != null) {
            isChecked();
            this.mDrawableRight.draw(canvas);
        }
    }

    private static void setDrawableBounds(int i, int i2, Drawable drawable) {
        int intrinsicWidth = (int) (((i - drawable.getIntrinsicWidth()) * 1.0f) / 2.0f);
        int intrinsicHeight = (int) (((i2 - drawable.getIntrinsicHeight()) * 1.0f) / 2.0f);
        drawable.setBounds(intrinsicWidth, intrinsicHeight, drawable.getIntrinsicWidth() + intrinsicWidth, drawable.getIntrinsicHeight() + intrinsicHeight);
    }
}
