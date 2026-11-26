package com.miui.handwriting.common.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import miuix.androidbasewidget.R;
import miuix.androidbasewidget.widget.EditText;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes3.dex */
public class StateEditText extends EditText {
    private static final Class<?>[] WIDGET_MANAGER_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    private final String TAG;
    public Context context;
    private Drawable[] mExtraDrawables;
    private String mLabel;
    private StaticLayout mLabelLayout;
    private StaticLayout mLabelLayoutSummary;
    private int mLabelLength;
    private int mLabelMaxWidth;
    private String mLabelSummary;
    private int mLabelSummaryLength;
    private boolean mPressed;
    private WidgetManager mWidgetManager;
    private int mWidgetPadding;

    public StateEditText(Context context) {
        this(context, null);
    }

    public StateEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.miuixAppcompatStateEditTextStyle);
    }

    public StateEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = getClass().getSimpleName();
        this.mLabelLayout = null;
        this.mLabelLayoutSummary = null;
        this.context = context;
        initView(context, attributeSet, i);
    }

    private void initView(Context context, AttributeSet attributeSet, int i) {
        String string;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.miuixAppcompatStateEditText, i, com.miui.creation.common.R.style.Widget_StateEditText_DayNight);
            string = typedArrayObtainStyledAttributes.getString(R.styleable.miuixAppcompatStateEditText_miuixAppcompatWidgetManager);
            this.mLabel = typedArrayObtainStyledAttributes.getString(R.styleable.miuixAppcompatStateEditText_miuixAppcompatLabel);
            this.mLabelMaxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.miuixAppcompatStateEditText_miuixAppcompatLabelMaxWidth, 0);
            this.mWidgetPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.miuixAppcompatStateEditText_miuixAppcompatWidgetPadding, 0);
            typedArrayObtainStyledAttributes.recycle();
        } else {
            string = null;
        }
        setWidgetManager(createWidgetManager(context, string, attributeSet));
        this.mExtraDrawables = null;
        WidgetManager widgetManager = this.mWidgetManager;
        if (widgetManager != null) {
            this.mExtraDrawables = widgetManager.getWidgetDrawables();
        }
        setLabel(this.mLabel);
        if (TextUtils.isEmpty(this.mLabel)) {
            return;
        }
        setTextAlignment(6);
    }

    public void setWidgetManager(WidgetManager widgetManager) {
        WidgetManager widgetManager2 = this.mWidgetManager;
        if (widgetManager2 != null) {
            widgetManager2.onDetached();
            this.mExtraDrawables = null;
        }
        this.mWidgetManager = widgetManager;
        if (widgetManager != null) {
            this.mExtraDrawables = widgetManager.getWidgetDrawables();
            this.mWidgetManager.onAttached(this);
        }
    }

    public void setLabel(String str) {
        this.mLabel = str;
        if (this.mLabelMaxWidth > 0) {
            this.mLabelLength = TextUtils.isEmpty(str) ? 0 : Math.min((int) getPaint().measureText(this.mLabel), this.mLabelMaxWidth);
        } else {
            this.mLabelLength = TextUtils.isEmpty(str) ? 0 : (int) getPaint().measureText(this.mLabel);
        }
        if (!TextUtils.isEmpty(this.mLabel)) {
            createLabelLayout();
        }
        invalidate();
    }

    public void setLabelSummary(String str) {
        this.mLabelSummary = str;
        if (this.mLabelMaxWidth > 0) {
            this.mLabelSummaryLength = TextUtils.isEmpty(str) ? 0 : Math.min((int) getPaint().measureText(this.mLabelSummary), this.mLabelMaxWidth);
        } else {
            this.mLabelSummaryLength = TextUtils.isEmpty(str) ? 0 : (int) getPaint().measureText(this.mLabelSummary);
        }
        if (!TextUtils.isEmpty(this.mLabelSummary)) {
            createLabelLayoutSummary();
        }
        invalidate();
    }

    private WidgetManager createWidgetManager(Context context, String str, AttributeSet attributeSet) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Constructor constructor = context.getClassLoader().loadClass(str).asSubclass(WidgetManager.class).getConstructor(WIDGET_MANAGER_CONSTRUCTOR_SIGNATURE);
            constructor.setAccessible(true);
            return (WidgetManager) constructor.newInstance(context, attributeSet);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Can't find WidgetManager: " + str, e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Can't access non-public constructor " + str, e2);
        } catch (InstantiationException e3) {
            throw new IllegalStateException("Could not instantiate the WidgetManager: " + str, e3);
        } catch (NoSuchMethodException e4) {
            throw new IllegalStateException("Error creating WidgetManager " + str, e4);
        } catch (InvocationTargetException e5) {
            throw new IllegalStateException("Could not instantiate the WidgetManager: " + str, e5);
        }
    }

    @Override // android.widget.TextView
    public int getCompoundPaddingRight() {
        int widgetLength;
        int compoundPaddingRight = super.getCompoundPaddingRight();
        if (ViewUtils.isLayoutRtl(this)) {
            widgetLength = getLabelLength();
        } else {
            widgetLength = getWidgetLength();
        }
        return compoundPaddingRight + widgetLength;
    }

    @Override // android.widget.TextView
    public int getCompoundPaddingLeft() {
        int labelLength;
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        if (ViewUtils.isLayoutRtl(this)) {
            labelLength = getWidgetLength();
        } else {
            labelLength = getLabelLength();
        }
        return compoundPaddingLeft + labelLength;
    }

    private int getLabelLength() {
        int i = this.mLabelLength;
        return i + (i == 0 ? 0 : this.mWidgetPadding);
    }

    private int getWidgetLength() {
        Drawable[] drawableArr = this.mExtraDrawables;
        if (drawableArr == null) {
            return 0;
        }
        int intrinsicWidth = 0;
        for (Drawable drawable : drawableArr) {
            intrinsicWidth = intrinsicWidth + drawable.getIntrinsicWidth() + this.mWidgetPadding;
        }
        return intrinsicWidth;
    }

    @Override // miuix.androidbasewidget.widget.EditText, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return dispatchEndDrawableTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent);
    }

    private boolean dispatchEndDrawableTouchEvent(MotionEvent motionEvent) {
        if (this.mWidgetManager != null) {
            return isWidgetResumedEvent(motionEvent);
        }
        return false;
    }

    private boolean isWidgetResumedEvent(MotionEvent motionEvent) {
        if (this.mExtraDrawables != null) {
            int scrollX = getScrollX();
            int i = 0;
            while (true) {
                Drawable[] drawableArr = this.mExtraDrawables;
                if (i >= drawableArr.length) {
                    break;
                }
                Rect bounds = drawableArr[i].getBounds();
                if (motionEvent.getX() < bounds.right - scrollX && motionEvent.getX() > bounds.left - scrollX) {
                    return onWidgetTouchEvent(motionEvent, i);
                }
                i++;
            }
        }
        this.mPressed = false;
        return false;
    }

    private boolean onWidgetTouchEvent(MotionEvent motionEvent, int i) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mPressed = true;
        } else if (action == 1) {
            if (this.mPressed && this.mWidgetManager != null) {
                this.mPressed = false;
                return true;
            }
        } else if (action == 3 && this.mPressed) {
            this.mPressed = false;
        }
        return this.mPressed;
    }

    @Override // miuix.androidbasewidget.widget.EditText, android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (TextUtils.isEmpty(this.mLabel) || this.mLabelLayout == null) {
            return;
        }
        if (this.mLabelMaxWidth == 0 && this.mLabelLength > getMeasuredWidth() / 2) {
            this.mLabelLength = getMeasuredWidth() / 2;
            createLabelLayout();
        }
        int height = this.mLabelLayout.getHeight() + getPaddingTop() + getPaddingBottom();
        if (height > getMeasuredHeight()) {
            setMeasuredDimension(getMeasuredWidth(), height);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawExtraWidget(canvas);
        drawLabel(canvas);
        drawLabelSummary(canvas);
    }

    private void drawLabel(Canvas canvas) {
        float fMax;
        if (TextUtils.isEmpty(this.mLabel) || this.mLabelLayout == null) {
            return;
        }
        int color = getPaint().getColor();
        getPaint().setColor(getCurrentTextColor());
        int paddingStart = getPaddingStart();
        int intrinsicWidth = 0;
        Drawable drawable = getCompoundDrawablesRelative()[0];
        if (drawable != null) {
            intrinsicWidth = this.mWidgetPadding + drawable.getIntrinsicWidth();
        }
        if (!TextUtils.isEmpty(this.mLabelSummary) && this.mLabelLayoutSummary != null) {
            fMax = Math.max(0.0f, (getMeasuredHeight() - this.mLabelLayout.getHeight()) * 0.3f);
        } else {
            fMax = Math.max(0.0f, (getMeasuredHeight() - this.mLabelLayout.getHeight()) * 0.5f);
        }
        canvas.save();
        if (ViewUtils.isLayoutRtl(this)) {
            canvas.translate((((getScrollX() + getWidth()) - intrinsicWidth) - this.mLabelLength) - paddingStart, fMax);
        } else {
            canvas.translate(paddingStart + getScrollX() + intrinsicWidth, fMax);
        }
        this.mLabelLayout.draw(canvas);
        canvas.restore();
        getPaint().setColor(color);
    }

    private void drawLabelSummary(Canvas canvas) {
        if (TextUtils.isEmpty(this.mLabelSummary) || this.mLabelLayoutSummary == null) {
            return;
        }
        int color = getPaint().getColor();
        float textSize = getPaint().getTextSize();
        getPaint().setColor(getResources().getColor(com.miui.creation.common.R.color.page_size_summary_color, null));
        getPaint().setTextSize(getResources().getDimensionPixelSize(com.miui.creation.common.R.dimen.page_size_summary_color));
        int paddingStart = getPaddingStart();
        int intrinsicWidth = 0;
        Drawable drawable = getCompoundDrawablesRelative()[0];
        if (drawable != null) {
            intrinsicWidth = this.mWidgetPadding + drawable.getIntrinsicWidth();
        }
        float fMax = Math.max(0.0f, ((getMeasuredHeight() + this.mLabelLayout.getHeight()) - this.mLabelLayoutSummary.getHeight()) * 0.45f);
        canvas.save();
        if (ViewUtils.isLayoutRtl(this)) {
            canvas.translate((((getScrollX() + getWidth()) - intrinsicWidth) - this.mLabelSummaryLength) - paddingStart, fMax);
        } else {
            canvas.translate(paddingStart + getScrollX() + intrinsicWidth, fMax);
        }
        this.mLabelLayoutSummary.draw(canvas);
        canvas.restore();
        getPaint().setColor(color);
        getPaint().setTextSize(textSize);
    }

    private void drawExtraWidget(Canvas canvas) {
        if (this.mExtraDrawables == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int scrollX = getScrollX();
        int paddingEnd = getPaddingEnd();
        Drawable drawable = getCompoundDrawablesRelative()[2];
        int i = 0;
        int intrinsicWidth = drawable == null ? 0 : drawable.getIntrinsicWidth() + this.mWidgetPadding;
        int i2 = height / 2;
        int i3 = 0;
        while (true) {
            Drawable[] drawableArr = this.mExtraDrawables;
            if (i >= drawableArr.length) {
                return;
            }
            int intrinsicWidth2 = drawableArr[i].getIntrinsicWidth();
            int intrinsicHeight = this.mExtraDrawables[i].getIntrinsicHeight();
            if (ViewUtils.isLayoutRtl(this)) {
                int i4 = scrollX + paddingEnd + intrinsicWidth;
                int i5 = intrinsicHeight / 2;
                this.mExtraDrawables[i].setBounds(i4 + i3, i2 - i5, i4 + intrinsicWidth2 + i3, i5 + i2);
            } else {
                int i6 = ((scrollX + width) - paddingEnd) - intrinsicWidth;
                int i7 = intrinsicHeight / 2;
                this.mExtraDrawables[i].setBounds((i6 - intrinsicWidth2) - i3, i2 - i7, i6 - i3, i7 + i2);
            }
            i3 = this.mWidgetPadding + intrinsicWidth2;
            this.mExtraDrawables[i].draw(canvas);
            i++;
        }
    }

    private void createLabelLayout() {
        String str = this.mLabel;
        this.mLabelLayout = StaticLayout.Builder.obtain(str, 0, str.length(), getPaint(), this.mLabelLength).build();
    }

    private void createLabelLayoutSummary() {
        String str = this.mLabelSummary;
        this.mLabelLayoutSummary = StaticLayout.Builder.obtain(str, 0, str.length(), getPaint(), this.mLabelSummaryLength).build();
    }

    @Override // android.widget.TextView
    public void setInputType(int i) {
        Typeface typeface = getTypeface();
        super.setInputType(i);
        setTypeface(typeface);
    }

    public static abstract class WidgetManager {
        protected abstract Drawable[] getWidgetDrawables();

        protected void onAttached(StateEditText stateEditText) {
        }

        protected void onDetached() {
        }

        protected abstract void onWidgetClick(int i);

        public WidgetManager(Context context, AttributeSet attributeSet) {
        }
    }
}
