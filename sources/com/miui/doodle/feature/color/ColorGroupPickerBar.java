package com.miui.doodle.feature.color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.base.utils.Logger;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorGroupPickerBar.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0004\u0010\u000bJ(\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0014J\u0010\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0014J\u0010\u00102\u001a\u0002002\u0006\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020'2\b\u00106\u001a\u0004\u0018\u00010\u001bJ\u0010\u00107\u001a\u00020'2\b\u00108\u001a\u0004\u0018\u00010\u0001J\u000e\u00109\u001a\u00020'2\u0006\u0010:\u001a\u00020\nJ\u0010\u0010;\u001a\u00020'2\b\u0010<\u001a\u0004\u0018\u00010\u000fJ\b\u0010=\u001a\u00020'H\u0002J\b\u0010>\u001a\u00020'H\u0002J\u0010\u0010?\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010@\u001a\u00020'2\u0006\u0010A\u001a\u00020!2\u0006\u0010B\u001a\u00020!H\u0002J\b\u0010C\u001a\u00020'H\u0002J\u0018\u0010D\u001a\u00020\n2\u0006\u0010A\u001a\u00020!2\u0006\u0010B\u001a\u00020!H\u0002J\u0012\u0010E\u001a\u0004\u0018\u00010\u00182\u0006\u0010F\u001a\u00020\nH\u0002J\b\u0010G\u001a\u00020'H\u0002J\u0010\u0010H\u001a\u00020!2\u0006\u0010A\u001a\u00020!H\u0002R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lcom/miui/doodle/feature/color/ColorGroupPickerBar;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mPaint", "Landroid/graphics/Paint;", "mColorData", "", "mColorItemWidth", "mViewWidth", "mViewHeight", "mBarOffsetY", "mBarHeight", "mBarRadius", "mRects", "Ljava/util/ArrayList;", "Landroid/graphics/Rect;", "mSelectGroup", "mGroupPickListener", "Lcom/miui/doodle/feature/color/ColorGroupPickListener;", "mClickListener", "Landroid/view/View$OnClickListener;", "mColorSelector", "mColorSelectorContent", "mDownX", "", "mDownY", "mLastUpX", "mLastUpY", "mScaledTouchSlop", "onSizeChanged", "", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "oldw", "oldh", "onDraw", "canvas", "Landroid/graphics/Canvas;", "mIsInTouchSelector", "", "mSelectorOffsetXInTouch", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "setGroupPickListener", "listener", "setGroupSelector", "view", "setSelectGroup", "selectGroup", "setColorData", "colorData", "initView", "updateDrawParams", "drawColorRect", "checkSelectGroupOnMove", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "checkSelectGroupAfterMove", "getGroupIndexByTouchPos", "getRectByGroupIndex", "groupIndex", "setSelectorPosInGroup", "checkMovableX", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorGroupPickerBar extends View {
    private int mBarHeight;
    private int mBarOffsetY;
    private int mBarRadius;
    private final View.OnClickListener mClickListener;
    private int[] mColorData;
    private int mColorItemWidth;
    private final View mColorSelector;
    private final View mColorSelectorContent;
    private float mDownX;
    private float mDownY;
    private ColorGroupPickListener mGroupPickListener;
    private boolean mIsInTouchSelector;
    private float mLastUpX;
    private float mLastUpY;
    private final Paint mPaint;
    private final ArrayList<Rect> mRects;
    private int mScaledTouchSlop;
    private int mSelectGroup;
    private float mSelectorOffsetXInTouch;
    private int mViewHeight;
    private int mViewWidth;

    public final void setGroupSelector(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void mClickListener$lambda$0(ColorGroupPickerBar colorGroupPickerBar, View view) {
        Logger.INSTANCE.i("ColorPicker", "mColorSelector.setTranslationX onClick");
        if (colorGroupPickerBar.mGroupPickListener != null) {
            int groupIndexByTouchPos = colorGroupPickerBar.getGroupIndexByTouchPos(colorGroupPickerBar.mLastUpX, colorGroupPickerBar.mLastUpY);
            if (groupIndexByTouchPos >= 0) {
                ColorGroupPickListener colorGroupPickListener = colorGroupPickerBar.mGroupPickListener;
                Intrinsics.checkNotNull(colorGroupPickListener);
                colorGroupPickListener.onGroupPick(groupIndexByTouchPos);
                colorGroupPickerBar.mSelectGroup = groupIndexByTouchPos;
            }
            colorGroupPickerBar.invalidate();
        }
    }

    public ColorGroupPickerBar(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mBarOffsetY = 30;
        this.mBarHeight = 80;
        this.mBarRadius = 40;
        this.mRects = new ArrayList<>();
        this.mClickListener = new View.OnClickListener() { // from class: com.miui.doodle.feature.color.ColorGroupPickerBar$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorGroupPickerBar.mClickListener$lambda$0(this.f$0, view);
            }
        };
        initView();
    }

    public ColorGroupPickerBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.mBarOffsetY = 30;
        this.mBarHeight = 80;
        this.mBarRadius = 40;
        this.mRects = new ArrayList<>();
        this.mClickListener = new View.OnClickListener() { // from class: com.miui.doodle.feature.color.ColorGroupPickerBar$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorGroupPickerBar.mClickListener$lambda$0(this.f$0, view);
            }
        };
        initView();
    }

    public ColorGroupPickerBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.mBarOffsetY = 30;
        this.mBarHeight = 80;
        this.mBarRadius = 40;
        this.mRects = new ArrayList<>();
        this.mClickListener = new View.OnClickListener() { // from class: com.miui.doodle.feature.color.ColorGroupPickerBar$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorGroupPickerBar.mClickListener$lambda$0(this.f$0, view);
            }
        };
        initView();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDrawParams();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        drawColorRect(canvas);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x005e  */
    /* JADX WARN: Code duplicated, block: B:17:0x0066  */
    /* JADX WARN: Code duplicated, block: B:19:0x008d  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        float fAbs;
        int i;
        Intrinsics.checkNotNullParameter(event, "event");
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == 0) {
            this.mDownX = x;
            this.mDownY = y;
            View view = this.mColorSelector;
            if (view != null) {
                view.animate().cancel();
                float translationX = this.mColorSelector.getTranslationX();
                float width = this.mColorSelector.getWidth() + translationX;
                if (x > translationX && x < width) {
                    this.mSelectorOffsetXInTouch = translationX;
                    this.mIsInTouchSelector = true;
                }
            }
        } else if (action == 1) {
            this.mLastUpX = x;
            this.mLastUpY = y;
            if (this.mIsInTouchSelector) {
                this.mIsInTouchSelector = false;
                this.mSelectorOffsetXInTouch = 0.0f;
                Logger logger = Logger.INSTANCE;
                View view2 = this.mColorSelector;
                Intrinsics.checkNotNull(view2);
                logger.i("ColorPicker", "mColorSelector.setTranslationX afterMove start = " + view2.getTranslationX());
                checkSelectGroupAfterMove();
                return true;
            }
            fAbs = Math.abs(x - this.mDownX);
            float fAbs2 = Math.abs(this.mLastUpY - this.mDownY);
            i = this.mScaledTouchSlop;
            if (fAbs < i && fAbs2 < i) {
                performClick();
                return true;
            }
        } else if (action == 2) {
            float f = x - this.mDownX;
            if (this.mIsInTouchSelector) {
                View view3 = this.mColorSelector;
                Intrinsics.checkNotNull(view3);
                view3.animate().cancel();
                float fCheckMovableX = checkMovableX(this.mSelectorOffsetXInTouch + f);
                Logger.INSTANCE.i("ColorPicker", "mColorSelector.setTranslationX onMove endX = " + fCheckMovableX);
                this.mColorSelector.setTranslationX(fCheckMovableX);
                checkSelectGroupOnMove(x, this.mBarOffsetY + 2.0f);
                return true;
            }
        } else if (action == 3) {
            this.mLastUpX = x;
            this.mLastUpY = y;
            if (this.mIsInTouchSelector) {
                this.mIsInTouchSelector = false;
                this.mSelectorOffsetXInTouch = 0.0f;
                Logger logger2 = Logger.INSTANCE;
                View view4 = this.mColorSelector;
                Intrinsics.checkNotNull(view4);
                logger2.i("ColorPicker", "mColorSelector.setTranslationX afterMove start = " + view4.getTranslationX());
                checkSelectGroupAfterMove();
                return true;
            }
            fAbs = Math.abs(x - this.mDownX);
            float fAbs3 = Math.abs(this.mLastUpY - this.mDownY);
            i = this.mScaledTouchSlop;
            if (fAbs < i) {
                performClick();
                return true;
            }
        }
        return true;
    }

    public final void setGroupPickListener(ColorGroupPickListener listener) {
        this.mGroupPickListener = listener;
    }

    public final void setSelectGroup(int selectGroup) {
        this.mSelectGroup = selectGroup;
    }

    public final void setColorData(int[] colorData) {
        this.mColorData = colorData;
        updateDrawParams();
        invalidate();
    }

    private final void initView() {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        setOnClickListener(this.mClickListener);
        this.mBarOffsetY = getContext().getResources().getDimensionPixelOffset(R.dimen.color_pick_bar_offset_y);
        int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.color_selector_content_height);
        this.mBarHeight = dimensionPixelOffset;
        this.mBarRadius = dimensionPixelOffset / 2;
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private final void updateDrawParams() {
        if (this.mViewWidth == getWidth() && this.mViewHeight == getHeight()) {
            return;
        }
        this.mViewWidth = getWidth();
        this.mViewHeight = getHeight();
        int[] iArr = this.mColorData;
        if (iArr == null) {
            return;
        }
        int i = this.mViewWidth;
        Intrinsics.checkNotNull(iArr);
        this.mColorItemWidth = i / iArr.length;
        int i2 = this.mViewWidth;
        int[] iArr2 = this.mColorData;
        Intrinsics.checkNotNull(iArr2);
        int length = i2 % iArr2.length;
        int i3 = length / 2;
        int i4 = length - i3;
        int i5 = this.mColorItemWidth + i3;
        int i6 = this.mBarOffsetY;
        int i7 = this.mBarHeight + i6;
        this.mRects.clear();
        Rect rect = new Rect();
        int i8 = 0;
        rect.set(0, i6, i5, i7);
        this.mRects.add(rect);
        int[] iArr3 = this.mColorData;
        Intrinsics.checkNotNull(iArr3);
        int i9 = 1;
        int length2 = iArr3.length - 1;
        while (i9 < length2) {
            Rect rect2 = new Rect();
            int i10 = this.mColorItemWidth;
            int i11 = ((i9 - 1) * i10) + i5;
            int i12 = i10 + i11;
            rect2.set(i11, i6, i12, i7);
            this.mRects.add(rect2);
            i9++;
            i8 = i12;
        }
        Rect rect3 = new Rect();
        rect3.set(i8, i6, this.mColorItemWidth + i8 + i4, i7);
        this.mRects.add(rect3);
    }

    private final void drawColorRect(Canvas canvas) {
        canvas.save();
        if (this.mColorData == null) {
            return;
        }
        if (this.mRects.size() == 0) {
            updateDrawParams();
        }
        int size = this.mRects.size();
        int[] iArr = this.mColorData;
        Intrinsics.checkNotNull(iArr);
        if (size == iArr.length) {
            Paint paint = this.mPaint;
            int[] iArr2 = this.mColorData;
            Intrinsics.checkNotNull(iArr2);
            paint.setColor(iArr2[0]);
            Rect rect = new Rect(this.mRects.get(0));
            rect.left += this.mBarRadius;
            RectF rectF = new RectF(this.mRects.get(0));
            if (rectF.width() < this.mBarRadius * 2) {
                rectF.right = rectF.left + (this.mBarRadius * 2);
            }
            int i = this.mBarRadius;
            canvas.drawRoundRect(rectF, i, i, this.mPaint);
            canvas.drawRect(rect, this.mPaint);
            Paint paint2 = this.mPaint;
            int[] iArr3 = this.mColorData;
            Intrinsics.checkNotNull(iArr3);
            int[] iArr4 = this.mColorData;
            Intrinsics.checkNotNull(iArr4);
            paint2.setColor(iArr3[iArr4.length - 1]);
            ArrayList<Rect> arrayList = this.mRects;
            int[] iArr5 = this.mColorData;
            Intrinsics.checkNotNull(iArr5);
            Rect rect2 = new Rect(arrayList.get(iArr5.length - 1));
            rect2.right -= this.mBarRadius;
            ArrayList<Rect> arrayList2 = this.mRects;
            int[] iArr6 = this.mColorData;
            Intrinsics.checkNotNull(iArr6);
            RectF rectF2 = new RectF(arrayList2.get(iArr6.length - 1));
            if (rectF2.width() < this.mBarRadius * 2) {
                rectF2.left = rectF2.right - (this.mBarRadius * 2);
            }
            canvas.drawRect(rect2, this.mPaint);
            int i2 = this.mBarRadius;
            canvas.drawRoundRect(rectF2, i2, i2, this.mPaint);
            int[] iArr7 = this.mColorData;
            Intrinsics.checkNotNull(iArr7);
            int length = iArr7.length - 1;
            for (int i3 = 1; i3 < length; i3++) {
                Paint paint3 = this.mPaint;
                int[] iArr8 = this.mColorData;
                Intrinsics.checkNotNull(iArr8);
                paint3.setColor(iArr8[i3]);
                canvas.drawRect(this.mRects.get(i3), this.mPaint);
            }
            setSelectorPosInGroup();
        }
        canvas.restore();
    }

    private final void checkSelectGroupOnMove(float x, float y) {
        int groupIndexByTouchPos = getGroupIndexByTouchPos(x, y);
        if (groupIndexByTouchPos < 0 || this.mSelectGroup == groupIndexByTouchPos) {
            return;
        }
        ColorGroupPickListener colorGroupPickListener = this.mGroupPickListener;
        Intrinsics.checkNotNull(colorGroupPickListener);
        colorGroupPickListener.onGroupPick(groupIndexByTouchPos);
        this.mSelectGroup = groupIndexByTouchPos;
        View view = this.mColorSelectorContent;
        Intrinsics.checkNotNull(view);
        int[] iArr = this.mColorData;
        Intrinsics.checkNotNull(iArr);
        view.setBackgroundColor(iArr[this.mSelectGroup]);
    }

    private final void checkSelectGroupAfterMove() {
        Rect rectByGroupIndex = getRectByGroupIndex(this.mSelectGroup);
        if (rectByGroupIndex != null) {
            float f = rectByGroupIndex.left;
            View view = this.mColorSelectorContent;
            Intrinsics.checkNotNull(view);
            final float x = f - view.getX();
            View view2 = this.mColorSelector;
            Intrinsics.checkNotNull(view2);
            view2.post(new Runnable() { // from class: com.miui.doodle.feature.color.ColorGroupPickerBar$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ColorGroupPickerBar.checkSelectGroupAfterMove$lambda$1(this.f$0, x);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkSelectGroupAfterMove$lambda$1(ColorGroupPickerBar colorGroupPickerBar, float f) {
        colorGroupPickerBar.mColorSelector.animate().setDuration(150L).translationX(colorGroupPickerBar.checkMovableX(f));
    }

    private final int getGroupIndexByTouchPos(float x, float y) {
        int size = this.mRects.size();
        for (int i = 0; i < size; i++) {
            if (this.mRects.get(i).contains((int) x, (int) y)) {
                return i;
            }
        }
        return -1;
    }

    private final Rect getRectByGroupIndex(int groupIndex) {
        if (groupIndex < this.mRects.size()) {
            return this.mRects.get(groupIndex);
        }
        return null;
    }

    private final void setSelectorPosInGroup() {
        Rect rectByGroupIndex;
        if (this.mColorSelector == null || (rectByGroupIndex = getRectByGroupIndex(this.mSelectGroup)) == null) {
            return;
        }
        float f = rectByGroupIndex.left;
        View view = this.mColorSelectorContent;
        Intrinsics.checkNotNull(view);
        this.mColorSelector.setTranslationX(checkMovableX(f - view.getX()));
        View view2 = this.mColorSelectorContent;
        int[] iArr = this.mColorData;
        Intrinsics.checkNotNull(iArr);
        view2.setBackgroundColor(iArr[this.mSelectGroup]);
    }

    private final float checkMovableX(float x) {
        int i = this.mViewWidth;
        View view = this.mColorSelector;
        Intrinsics.checkNotNull(view);
        if (x > i - view.getWidth()) {
            x = this.mViewWidth - this.mColorSelector.getWidth();
        }
        if (x < 0.0f) {
            return 0.0f;
        }
        return x;
    }
}
