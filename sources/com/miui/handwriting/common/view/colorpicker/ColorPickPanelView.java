package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.miui.creation.common.R;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickPanelView extends View {
    private static final int COL = 13;
    private MyAccessHelper mAccessHelper;
    private float mBorderPadding;
    private float mBorderwidth;
    private int[] mColors;
    private int mCurrentColor;
    private int mCurrentSelectCol;
    private int mCurrentSelectRow;
    private int mHeight;
    private boolean mInit;
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mOnColorChangeListener;
    private int mPanelStroke;
    private int mRadius;
    private int mRow;
    private float mThumbScale;
    private int mWidth;
    private Paint paint;

    public ColorPickPanelView(Context context) {
        super(context);
        this.mCurrentSelectCol = -1;
        this.mCurrentSelectRow = -1;
        this.mThumbScale = 1.0f;
        init(context);
    }

    public ColorPickPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentSelectCol = -1;
        this.mCurrentSelectRow = -1;
        this.mThumbScale = 1.0f;
        getAttrs(context, attributeSet);
        init(context);
    }

    public ColorPickPanelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentSelectCol = -1;
        this.mCurrentSelectRow = -1;
        this.mThumbScale = 1.0f;
        getAttrs(context, attributeSet);
        init(context);
    }

    public int getCurrentColor() {
        return this.mCurrentColor;
    }

    private void getAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ColorPickPanelView);
        try {
            this.mThumbScale = typedArrayObtainStyledAttributes.getFloat(R.styleable.ColorPickPanelView_select_thumb_scale, 1.0f);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void init(Context context) {
        this.mColors = context.getResources().getIntArray(R.array.creation_colors);
        this.paint = new Paint();
        this.mBorderwidth = context.getResources().getDimension(R.dimen.creation_seekbar_stroke_width) * this.mThumbScale;
        this.mBorderPadding = context.getResources().getDimension(R.dimen.creation_color_picker_panel_stroke_padding);
        this.mPanelStroke = context.getResources().getDimensionPixelOffset(R.dimen.creation_color_picker_panel_stroke);
        this.mRadius = (int) (getResources().getDimensionPixelSize(R.dimen.creation_color_picker_panel_view_corner_radius) * this.mThumbScale);
        MyAccessHelper myAccessHelper = new MyAccessHelper(this);
        this.mAccessHelper = myAccessHelper;
        ViewCompat.setAccessibilityDelegate(this, myAccessHelper);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (!this.mInit) {
            initData();
        }
        drawColors(canvas, this.paint);
        drawSelect(canvas, this.paint);
        drawPanelStroke(canvas, this.paint);
        super.onDraw(canvas);
    }

    private void initData() {
        this.mRow = (int) Math.ceil(((double) this.mColors.length) / 13.0d);
        this.mWidth = getWidth() / 13;
        this.mHeight = getHeight() / this.mRow;
        this.mInit = true;
    }

    private void drawColors(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < this.mRow; i++) {
            for (int i2 = 0; i2 < 13; i2++) {
                paint.setColor(this.mColors[(i * 13) + i2]);
                if (i == 0 && i2 == 0) {
                    int i3 = this.mRadius;
                    canvas.drawCircle(i3, i3, i3, paint);
                    canvas.drawRect(this.mRadius, 0.0f, this.mWidth, this.mHeight, paint);
                    canvas.drawRect(0.0f, this.mRadius, this.mWidth, this.mHeight, paint);
                } else {
                    int i4 = this.mRow;
                    if (i == i4 - 1 && i2 == 0) {
                        int i5 = this.mRadius;
                        int i6 = this.mHeight;
                        canvas.drawCircle(i5, ((i * i6) + i6) - i5, i5, paint);
                        int i7 = this.mHeight;
                        canvas.drawRect(0.0f, i * i7, this.mWidth, ((i * i7) + i7) - this.mRadius, paint);
                        float f = this.mRadius;
                        int i8 = this.mHeight;
                        canvas.drawRect(f, i * i8, this.mWidth, (i * i8) + i8, paint);
                    } else if (i == 0 && i2 == 12) {
                        int i9 = this.mWidth;
                        int i10 = (i2 * i9) + i9;
                        int i11 = this.mRadius;
                        canvas.drawCircle(i10 - i11, i11, i11, paint);
                        int i12 = this.mWidth;
                        canvas.drawRect(i2 * i12, 0.0f, ((i2 * i12) + i12) - this.mRadius, this.mHeight, paint);
                        int i13 = this.mWidth;
                        canvas.drawRect(i2 * i13, this.mRadius, (i2 * i13) + i13, this.mHeight, paint);
                    } else if (i == i4 - 1 && i2 == 12) {
                        int i14 = this.mWidth;
                        int i15 = (i2 * i14) + i14;
                        int i16 = this.mRadius;
                        int i17 = this.mHeight;
                        canvas.drawCircle(i15 - i16, ((i * i17) + i17) - i16, i16, paint);
                        int i18 = this.mWidth;
                        int i19 = this.mHeight;
                        canvas.drawRect(i2 * i18, i * i19, (i2 * i18) + i18, ((i * i19) + i19) - this.mRadius, paint);
                        int i20 = this.mWidth;
                        int i21 = this.mHeight;
                        int i22 = this.mRadius;
                        canvas.drawRect(i2 * i20, (i * i21) + i22, ((i2 * i20) + i20) - i22, (i * i21) + i21, paint);
                    } else {
                        int i23 = this.mWidth;
                        int i24 = this.mHeight;
                        canvas.drawRect(i2 * i23, i * i24, (i2 * i23) + i23, (i * i24) + i24, paint);
                    }
                }
            }
        }
    }

    private void drawSelect(Canvas canvas, Paint paint) {
        if (this.mCurrentSelectRow < 0 || this.mCurrentSelectCol < 0) {
            return;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.mCurrentSelectRow < 1 ? Color.parseColor("#E4E4E4") : -1);
        paint.setStrokeWidth(this.mBorderwidth);
        paint.setAntiAlias(true);
        paint.setFlags(1);
        float f = this.mBorderwidth / 2.0f;
        int i = this.mCurrentSelectCol;
        int i2 = this.mWidth;
        float f2 = (i * i2) + (i2 / 2);
        int i3 = this.mCurrentSelectRow;
        int i4 = this.mHeight;
        canvas.drawCircle(f2, (i3 * i4) + (i4 / 2), ((i4 / 2) - f) - this.mBorderPadding, paint);
    }

    private void drawPanelStroke(Canvas canvas, Paint paint) {
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.color_picker_panel_stroke, null));
        paint.setStrokeWidth(this.mPanelStroke);
        RectF rectF = new RectF(0.0f, 0.0f, this.mWidth * 13, this.mRow * this.mHeight);
        int i = this.mRadius;
        canvas.drawRoundRect(rectF, i, i, paint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void colorChange() {
        if (this.mOnColorChangeListener == null) {
            return;
        }
        updateLabel();
        this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(0, this.mCurrentColor));
    }

    public void setColor(int i) {
        this.mCurrentColor = i;
        this.mCurrentSelectCol = -1;
        this.mCurrentSelectRow = -1;
        for (int i2 : this.mColors) {
            int i3 = this.mCurrentSelectCol + 1;
            this.mCurrentSelectCol = i3;
            if (i3 % 13 == 0) {
                this.mCurrentSelectCol = 0;
                this.mCurrentSelectRow++;
            }
            if (this.mCurrentColor == i2) {
                break;
            }
        }
        updateLabel();
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return true;
        }
        this.mCurrentSelectCol = (int) (motionEvent.getX() / this.mWidth);
        int y = (int) (motionEvent.getY() / this.mHeight);
        this.mCurrentSelectRow = y;
        int i = this.mCurrentSelectCol;
        if (i >= 13 || y >= this.mRow) {
            return true;
        }
        int i2 = (y * 13) + i;
        int[] iArr = this.mColors;
        if (i2 <= iArr.length - 1 && i2 >= 0) {
            this.mCurrentColor = iArr[i2];
            colorChange();
            invalidate();
        }
        return true;
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mOnColorChangeListener = onColorPickerColorChangeListener;
    }

    public void clearSelect() {
        this.mCurrentSelectCol = -1;
        this.mCurrentSelectRow = -1;
        invalidate();
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mAccessHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    private class MyAccessHelper extends ExploreByTouchHelper {
        private Rect mAccessRect;
        private Map<Integer, Pair<Integer, Integer>> mCovertMap;
        private int mSelectIndex;

        public MyAccessHelper(View view) {
            super(view);
            this.mAccessRect = new Rect();
            this.mCovertMap = new ArrayMap();
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            int i;
            ColorPickPanelView colorPickPanelView = ColorPickPanelView.this;
            colorPickPanelView.mCurrentSelectCol = (int) (f / colorPickPanelView.mWidth);
            ColorPickPanelView colorPickPanelView2 = ColorPickPanelView.this;
            colorPickPanelView2.mCurrentSelectRow = (int) (f2 / colorPickPanelView2.mHeight);
            if (ColorPickPanelView.this.mCurrentSelectCol >= 13 || ColorPickPanelView.this.mCurrentSelectRow >= ColorPickPanelView.this.mRow || (i = (ColorPickPanelView.this.mCurrentSelectRow * 13) + ColorPickPanelView.this.mCurrentSelectCol) < 0 || i >= ColorPickPanelView.this.mColors.length) {
                return -1;
            }
            this.mSelectIndex = i;
            return i;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(List<Integer> list) {
            for (int i = 0; i < ColorPickPanelView.this.mColors.length; i++) {
                this.mCovertMap.put(Integer.valueOf(i), ColorPickPanelView.this.convert(i));
                list.add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Pair<Integer, Integer> pair = this.mCovertMap.get(Integer.valueOf(i));
            if (pair == null) {
                ColorPickPanelView.this.mCurrentSelectCol = 0;
                ColorPickPanelView.this.mCurrentSelectRow = 0;
            } else {
                ColorPickPanelView.this.mCurrentSelectCol = pair.second == null ? 0 : ((Integer) pair.second).intValue();
                ColorPickPanelView.this.mCurrentSelectRow = pair.first != null ? ((Integer) pair.first).intValue() : 0;
            }
            this.mAccessRect.set(ColorPickPanelView.this.mCurrentSelectCol * ColorPickPanelView.this.mWidth, ColorPickPanelView.this.mCurrentSelectRow * ColorPickPanelView.this.mHeight, (ColorPickPanelView.this.mCurrentSelectCol * ColorPickPanelView.this.mWidth) + ColorPickPanelView.this.mWidth, (ColorPickPanelView.this.mCurrentSelectRow * ColorPickPanelView.this.mHeight) + ColorPickPanelView.this.mHeight);
            accessibilityNodeInfoCompat.setBoundsInParent(this.mAccessRect);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.addAction(16);
            accessibilityNodeInfoCompat.setContentDescription(ColorPickPanelView.this.getDescription());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            ColorPickPanelView colorPickPanelView = ColorPickPanelView.this;
            colorPickPanelView.mCurrentColor = colorPickPanelView.mColors[this.mSelectIndex];
            ColorPickPanelView.this.colorChange();
            ColorPickPanelView.this.invalidate();
            return true;
        }
    }

    public void updateLabel() {
        setContentDescription(getDescription());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDescription() {
        return getContext().getString(R.string.color_pick_board_talkback) + getContext().getString(R.string.color_pick_talkback, Integer.valueOf(this.mCurrentSelectRow + 1), Integer.valueOf(this.mCurrentSelectCol + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<Integer, Integer> convert(int i) {
        if (i > this.mColors.length) {
            return new Pair<>(0, 0);
        }
        return new Pair<>(Integer.valueOf(i / 13), Integer.valueOf(i % 13));
    }
}
