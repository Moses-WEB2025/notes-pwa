package com.miui.todo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.miui.notes.R;
import java.lang.ref.WeakReference;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes4.dex */
public class FloatWindowActionButton extends ImageView {
    private static final int PRESSED_MASK_COLOR = 218103808;
    private static final int SHADOW_ALPHA = 25;
    private static final float SHADOW_RADIUS = 5.45f;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 5.45f;
    private Drawable mDefaultBackground;
    private int mFabColor;
    private boolean mHasFabColor;
    private final boolean mIsShadowEnabled;
    private final int mShadowRadius;
    private final int mShadowXOffset;
    private final int mShadowYOffset;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FloatWindowActionButton(Context context) {
        this(context, null);
    }

    public FloatWindowActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatWindowActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        float f = getContext().getResources().getDisplayMetrics().density;
        this.mShadowXOffset = (int) (0.0f * f);
        int i2 = (int) (f * 5.45f);
        this.mShadowYOffset = i2;
        this.mShadowRadius = i2;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, i, 2131953318);
        this.mIsShadowEnabled = typedArrayObtainStyledAttributes.getBoolean(9, true);
        this.mHasFabColor = typedArrayObtainStyledAttributes.hasValue(6);
        this.mFabColor = typedArrayObtainStyledAttributes.getColor(6, R.attr.colorAccent);
        typedArrayObtainStyledAttributes.recycle();
        initBackground();
        updatePadding();
    }

    private void initBackground() {
        if (getBackground() == null) {
            super.setBackground(createFabBackground());
        } else {
            this.mHasFabColor = false;
        }
    }

    private Drawable getDefaultBackground() {
        if (this.mDefaultBackground == null) {
            this.mFabColor = AttributeResolver.resolveColor(getContext(), R.attr.colorAccent);
            this.mHasFabColor = true;
            this.mDefaultBackground = createFabBackground();
        }
        return this.mDefaultBackground;
    }

    private Drawable createFabBackground() {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShapeWithPadding(this));
        if (this.mIsShadowEnabled) {
            int iComputeShadowColor = computeShadowColor(this.mFabColor);
            setLayerType(2, shapeDrawable.getPaint());
            shapeDrawable.getPaint().setShadowLayer(this.mShadowRadius, this.mShadowXOffset, this.mShadowYOffset, iComputeShadowColor);
        }
        shapeDrawable.getPaint().setColor(this.mFabColor);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(new OvalShapeWithPadding(this));
        shapeDrawable2.getPaint().setColor(PRESSED_MASK_COLOR);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable, shapeDrawable2});
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(PRESSED_ENABLED_STATE_SET, layerDrawable);
        stateListDrawable.addState(ENABLED_SELECTED_STATE_SET, layerDrawable);
        stateListDrawable.addState(EMPTY_STATE_SET, shapeDrawable);
        return stateListDrawable;
    }

    private void updatePadding() {
        if (this.mIsShadowEnabled && this.mHasFabColor) {
            setPadding(Math.max(0, this.mShadowRadius - this.mShadowXOffset), Math.max(0, this.mShadowRadius - this.mShadowYOffset), Math.max(0, this.mShadowRadius + this.mShadowXOffset), Math.max(0, this.mShadowRadius + this.mShadowYOffset));
        } else {
            setPadding(0, 0, 0, 0);
        }
    }

    private int computeShadowColor(int i) {
        return Color.argb(25, Color.red(i), Math.max(0, Color.green(i) - 30), Color.blue(i));
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        this.mHasFabColor = false;
        if (drawable == null) {
            drawable = getDefaultBackground();
        }
        super.setBackground(drawable);
        updatePadding();
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        this.mHasFabColor = false;
        if (i == 0) {
            super.setBackground(getDefaultBackground());
        } else {
            super.setBackgroundResource(i);
        }
        updatePadding();
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        if (!this.mHasFabColor || this.mFabColor != i) {
            this.mFabColor = i;
            super.setBackground(createFabBackground());
            updatePadding();
        }
        this.mHasFabColor = true;
    }

    private static class OvalShapeWithPadding extends OvalShape {
        private WeakReference<View> mViewRef;

        public OvalShapeWithPadding() {
            this.mViewRef = new WeakReference<>(null);
        }

        public OvalShapeWithPadding(View view) {
            this.mViewRef = new WeakReference<>(view);
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            View view = this.mViewRef.get();
            if (view != null) {
                int width = view.getWidth();
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = ((width - paddingLeft) - view.getPaddingRight()) / 2;
                canvas.drawCircle(paddingLeft + paddingRight, paddingTop + paddingRight, paddingRight, paint);
            }
        }
    }
}
