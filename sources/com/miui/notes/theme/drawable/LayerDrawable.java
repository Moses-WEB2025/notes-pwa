package com.miui.notes.theme.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import androidx.core.view.GravityCompat;
import com.miui.common.tool.CompatUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LayerDrawable extends Drawable implements Drawable.Callback {
    public static final int PADDING_MODE_NEST = 0;
    public static final int PADDING_MODE_STACK = 1;
    private static final int UNDEFINED_INSET = Integer.MIN_VALUE;
    private Rect mHotspotBounds;
    LayerState mLayerState;
    private boolean mMutated;
    private int[] mPaddingB;
    private int[] mPaddingL;
    private int[] mPaddingR;
    private int[] mPaddingT;
    private final Rect mTmpContainer;
    private final Rect mTmpOutRect;
    private final Rect mTmpRect;

    public LayerDrawable(Drawable[] drawableArr) {
        this(drawableArr, (LayerState) null);
    }

    LayerDrawable(Drawable[] drawableArr, LayerState layerState) {
        this(layerState, (Resources) null);
        if (drawableArr == null) {
            throw new IllegalArgumentException("layers must be non-null");
        }
        int length = drawableArr.length;
        ChildDrawable[] childDrawableArr = new ChildDrawable[length];
        for (int i = 0; i < length; i++) {
            ChildDrawable childDrawable = new ChildDrawable();
            childDrawableArr[i] = childDrawable;
            childDrawable.mDrawable = drawableArr[i];
            drawableArr[i].setCallback(this);
            this.mLayerState.mChildrenChangingConfigurations |= drawableArr[i].getChangingConfigurations();
        }
        this.mLayerState.mNum = length;
        this.mLayerState.mChildren = childDrawableArr;
        ensurePadding();
        refreshPadding();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    LayerDrawable() {
        this((LayerState) null, (Resources) null);
    }

    LayerDrawable(LayerState layerState, Resources resources) {
        this.mTmpRect = new Rect();
        this.mTmpOutRect = new Rect();
        this.mTmpContainer = new Rect();
        LayerState layerStateCreateConstantState = createConstantState(layerState, resources);
        this.mLayerState = layerStateCreateConstantState;
        if (layerStateCreateConstantState.mNum > 0) {
            ensurePadding();
            refreshPadding();
        }
    }

    LayerState createConstantState(LayerState layerState, Resources resources) {
        return new LayerState(layerState, this, resources);
    }

    int addLayer(ChildDrawable childDrawable) {
        LayerState layerState = this.mLayerState;
        int length = layerState.mChildren != null ? layerState.mChildren.length : 0;
        int i = layerState.mNum;
        if (i >= length) {
            ChildDrawable[] childDrawableArr = new ChildDrawable[length + 10];
            if (i > 0) {
                System.arraycopy(layerState.mChildren, 0, childDrawableArr, 0, i);
            }
            layerState.mChildren = childDrawableArr;
        }
        if (layerState.mChildren != null) {
            layerState.mChildren[i] = childDrawable;
        }
        layerState.mNum++;
        layerState.invalidateCache();
        return i;
    }

    ChildDrawable addLayer(Drawable drawable, int[] iArr, int i, int i2, int i3, int i4, int i5) {
        ChildDrawable childDrawableCreateLayer = createLayer(drawable);
        childDrawableCreateLayer.mId = i;
        childDrawableCreateLayer.mThemeAttrs = iArr;
        childDrawableCreateLayer.mDrawable.setAutoMirrored(isAutoMirrored());
        childDrawableCreateLayer.mInsetL = i2;
        childDrawableCreateLayer.mInsetT = i3;
        childDrawableCreateLayer.mInsetR = i4;
        childDrawableCreateLayer.mInsetB = i5;
        addLayer(childDrawableCreateLayer);
        this.mLayerState.mChildrenChangingConfigurations |= drawable.getChangingConfigurations();
        drawable.setCallback(this);
        return childDrawableCreateLayer;
    }

    private ChildDrawable createLayer(Drawable drawable) {
        ChildDrawable childDrawable = new ChildDrawable();
        childDrawable.mDrawable = drawable;
        return childDrawable;
    }

    public int addLayer(Drawable drawable) {
        ChildDrawable childDrawableCreateLayer = createLayer(drawable);
        int iAddLayer = addLayer(childDrawableCreateLayer);
        ensurePadding();
        refreshChildPadding(iAddLayer, childDrawableCreateLayer);
        return iAddLayer;
    }

    public Drawable findDrawableByLayerId(int i) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i2 = this.mLayerState.mNum - 1; i2 >= 0; i2--) {
            if (childDrawableArr[i2].mId == i) {
                return childDrawableArr[i2].mDrawable;
            }
        }
        return null;
    }

    public void setId(int i, int i2) {
        this.mLayerState.mChildren[i].mId = i2;
    }

    public int getId(int i) {
        if (i >= this.mLayerState.mNum) {
            throw new IndexOutOfBoundsException();
        }
        return this.mLayerState.mChildren[i].mId;
    }

    public int getNumberOfLayers() {
        return this.mLayerState.mNum;
    }

    public boolean setDrawableByLayerId(int i, Drawable drawable) {
        int iFindIndexByLayerId = findIndexByLayerId(i);
        if (iFindIndexByLayerId < 0) {
            return false;
        }
        setDrawable(iFindIndexByLayerId, drawable);
        return true;
    }

    public int findIndexByLayerId(int i) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNum;
        for (int i3 = 0; i3 < i2; i3++) {
            if (childDrawableArr[i3].mId == i) {
                return i3;
            }
        }
        return -1;
    }

    public void setDrawable(int i, Drawable drawable) {
        if (i >= this.mLayerState.mNum) {
            throw new IndexOutOfBoundsException();
        }
        ChildDrawable childDrawable = this.mLayerState.mChildren[i];
        if (childDrawable.mDrawable != null) {
            if (drawable != null) {
                drawable.setBounds(childDrawable.mDrawable.getBounds());
            }
            childDrawable.mDrawable.setCallback(null);
        }
        if (drawable != null) {
            drawable.setCallback(this);
        }
        childDrawable.mDrawable = drawable;
        this.mLayerState.invalidateCache();
        refreshChildPadding(i, childDrawable);
    }

    public Drawable getDrawable(int i) {
        if (i >= this.mLayerState.mNum) {
            throw new IndexOutOfBoundsException();
        }
        return this.mLayerState.mChildren[i].mDrawable;
    }

    public void setLayerSize(int i, int i2, int i3) {
        ChildDrawable childDrawable = this.mLayerState.mChildren[i];
        childDrawable.mWidth = i2;
        childDrawable.mHeight = i3;
    }

    public void setLayerWidth(int i, int i2) {
        this.mLayerState.mChildren[i].mWidth = i2;
    }

    public int getLayerWidth(int i) {
        return this.mLayerState.mChildren[i].mWidth;
    }

    public void setLayerHeight(int i, int i2) {
        this.mLayerState.mChildren[i].mHeight = i2;
    }

    public int getLayerHeight(int i) {
        return this.mLayerState.mChildren[i].mHeight;
    }

    public void setLayerGravity(int i, int i2) {
        this.mLayerState.mChildren[i].mGravity = i2;
    }

    public int getLayerGravity(int i) {
        return this.mLayerState.mChildren[i].mGravity;
    }

    public void setLayerInset(int i, int i2, int i3, int i4, int i5) {
        setLayerInsetInternal(i, i2, i3, i4, i5, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public void setLayerInsetRelative(int i, int i2, int i3, int i4, int i5) {
        setLayerInsetInternal(i, 0, i3, 0, i5, i2, i4);
    }

    public void setLayerInsetLeft(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetL = i2;
    }

    public int getLayerInsetLeft(int i) {
        return this.mLayerState.mChildren[i].mInsetL;
    }

    public void setLayerInsetRight(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetR = i2;
    }

    public int getLayerInsetRight(int i) {
        return this.mLayerState.mChildren[i].mInsetR;
    }

    public void setLayerInsetTop(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetT = i2;
    }

    public int getLayerInsetTop(int i) {
        return this.mLayerState.mChildren[i].mInsetT;
    }

    public void setLayerInsetBottom(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetB = i2;
    }

    public int getLayerInsetBottom(int i) {
        return this.mLayerState.mChildren[i].mInsetB;
    }

    public void setLayerInsetStart(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetS = i2;
    }

    public int getLayerInsetStart(int i) {
        return this.mLayerState.mChildren[i].mInsetS;
    }

    public void setLayerInsetEnd(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetE = i2;
    }

    public int getLayerInsetEnd(int i) {
        return this.mLayerState.mChildren[i].mInsetE;
    }

    private void setLayerInsetInternal(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        ChildDrawable childDrawable = this.mLayerState.mChildren[i];
        childDrawable.mInsetL = i2;
        childDrawable.mInsetT = i3;
        childDrawable.mInsetR = i4;
        childDrawable.mInsetB = i5;
        childDrawable.mInsetS = i6;
        childDrawable.mInsetE = i7;
    }

    public void setPaddingMode(int i) {
        if (this.mLayerState.mPaddingMode != i) {
            this.mLayerState.mPaddingMode = i;
        }
    }

    public int getPaddingMode() {
        return this.mLayerState.mPaddingMode;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mLayerState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        LayerState layerState = this.mLayerState;
        if (layerState.mPaddingMode == 0) {
            computeNestedPadding(rect);
        } else {
            computeStackedPadding(rect);
        }
        if (layerState.mPaddingTop >= 0) {
            rect.top = layerState.mPaddingTop;
        }
        if (layerState.mPaddingBottom >= 0) {
            rect.bottom = layerState.mPaddingBottom;
        }
        int i = layerState.mPaddingStart;
        int i2 = layerState.mPaddingEnd;
        if (i < 0) {
            i = layerState.mPaddingLeft;
        }
        if (i >= 0) {
            rect.left = i;
        }
        if (i2 < 0) {
            i2 = layerState.mPaddingRight;
        }
        if (i2 >= 0) {
            rect.right = i2;
        }
        return (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) ? false : true;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        LayerState layerState = this.mLayerState;
        layerState.mPaddingLeft = i;
        layerState.mPaddingTop = i2;
        layerState.mPaddingRight = i3;
        layerState.mPaddingBottom = i4;
        layerState.mPaddingStart = -1;
        layerState.mPaddingEnd = -1;
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        LayerState layerState = this.mLayerState;
        layerState.mPaddingStart = i;
        layerState.mPaddingTop = i2;
        layerState.mPaddingEnd = i3;
        layerState.mPaddingBottom = i4;
        layerState.mPaddingLeft = -1;
        layerState.mPaddingRight = -1;
    }

    public int getLeftPadding() {
        return this.mLayerState.mPaddingLeft;
    }

    public int getRightPadding() {
        return this.mLayerState.mPaddingRight;
    }

    public int getStartPadding() {
        return this.mLayerState.mPaddingStart;
    }

    public int getEndPadding() {
        return this.mLayerState.mPaddingEnd;
    }

    public int getTopPadding() {
        return this.mLayerState.mPaddingTop;
    }

    public int getBottomPadding() {
        return this.mLayerState.mPaddingBottom;
    }

    private void computeNestedPadding(Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            refreshChildPadding(i2, childDrawableArr[i2]);
            rect.left += this.mPaddingL[i2];
            rect.top += this.mPaddingT[i2];
            rect.right += this.mPaddingR[i2];
            rect.bottom += this.mPaddingB[i2];
        }
    }

    private void computeStackedPadding(Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            refreshChildPadding(i2, childDrawableArr[i2]);
            rect.left = Math.max(rect.left, this.mPaddingL[i2]);
            rect.top = Math.max(rect.top, this.mPaddingT[i2]);
            rect.right = Math.max(rect.right, this.mPaddingR[i2]);
            rect.bottom = Math.max(rect.bottom, this.mPaddingB[i2]);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setVisible(z, z2);
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setDither(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNum;
        for (int i3 = 0; i3 < i2; i3++) {
            Drawable drawable = childDrawableArr[i3].mDrawable;
            if (drawable != null) {
                drawable.setAlpha(i);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable firstNonNullDrawable = getFirstNonNullDrawable();
        if (firstNonNullDrawable != null) {
            return firstNonNullDrawable.getAlpha();
        }
        return super.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    private Drawable getFirstNonNullDrawable() {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                return drawable;
            }
        }
        return null;
    }

    public void setOpacity(int i) {
        this.mLayerState.mOpacityOverride = i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mLayerState.mOpacityOverride != 0) {
            return this.mLayerState.mOpacityOverride;
        }
        return this.mLayerState.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mLayerState.mAutoMirrored = z;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setAutoMirrored(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mLayerState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mLayerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null && drawable.isStateful() && drawable.setState(iArr)) {
                refreshChildPadding(i2, childDrawableArr[i2]);
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNum;
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            Drawable drawable = childDrawableArr[i3].mDrawable;
            if (drawable != null && drawable.setLevel(i)) {
                refreshChildPadding(i3, childDrawableArr[i3]);
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        updateLayerBounds(rect);
    }

    private void updateLayerBounds(Rect rect) {
        Rect rect2 = this.mTmpOutRect;
        boolean z = this.mLayerState.mPaddingMode == 0;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i; i6++) {
            ChildDrawable childDrawable = childDrawableArr[i6];
            Drawable drawable = childDrawable.mDrawable;
            if (drawable != null) {
                Rect rect3 = this.mTmpContainer;
                rect3.set(drawable.getBounds());
                Rect rect4 = rect2;
                rect3.set(rect.left + (childDrawable.mInsetS == Integer.MIN_VALUE ? childDrawable.mInsetL : childDrawable.mInsetS) + i2, rect.top + childDrawable.mInsetT + i3, (rect.right - (childDrawable.mInsetE == Integer.MIN_VALUE ? childDrawable.mInsetR : childDrawable.mInsetE)) - i4, (rect.bottom - childDrawable.mInsetB) - i5);
                rect2 = rect4;
                Gravity.apply(resolveGravity(childDrawable.mGravity, childDrawable.mWidth, childDrawable.mHeight, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()), childDrawable.mWidth < 0 ? drawable.getIntrinsicWidth() : childDrawable.mWidth, childDrawable.mHeight < 0 ? drawable.getIntrinsicHeight() : childDrawable.mHeight, rect3, rect2, 0);
                drawable.setBounds(rect2);
                if (z) {
                    i2 += this.mPaddingL[i6];
                    i4 += this.mPaddingR[i6];
                    i3 += this.mPaddingT[i6];
                    i5 += this.mPaddingB[i6];
                }
            }
        }
    }

    private static int resolveGravity(int i, int i2, int i3, int i4, int i5) {
        if (!Gravity.isHorizontal(i)) {
            i = i2 < 0 ? i | 7 : i | GravityCompat.START;
        }
        if (!Gravity.isVertical(i)) {
            i = i3 < 0 ? i | 112 : i | 48;
        }
        if (i2 < 0 && i4 < 0) {
            i |= 7;
        }
        return (i3 >= 0 || i5 >= 0) ? i : i | 112;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        int i;
        int i2;
        boolean z = this.mLayerState.mPaddingMode == 0;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i3 = this.mLayerState.mNum;
        int i4 = 0;
        int i5 = -1;
        int i6 = 0;
        for (int i7 = 0; i7 < i3; i7++) {
            ChildDrawable childDrawable = childDrawableArr[i7];
            if (childDrawable.mDrawable != null) {
                if (CompatUtils.invokeLayoutDirection(this) == 1) {
                    i = childDrawable.mInsetE == Integer.MIN_VALUE ? childDrawable.mInsetL : childDrawable.mInsetE;
                    i2 = childDrawable.mInsetS == Integer.MIN_VALUE ? childDrawable.mInsetR : childDrawable.mInsetS;
                } else {
                    i = childDrawable.mInsetS == Integer.MIN_VALUE ? childDrawable.mInsetL : childDrawable.mInsetS;
                    i2 = childDrawable.mInsetE == Integer.MIN_VALUE ? childDrawable.mInsetR : childDrawable.mInsetE;
                }
                int intrinsicWidth = (childDrawable.mWidth < 0 ? childDrawable.mDrawable.getIntrinsicWidth() : childDrawable.mWidth) + i + i2 + i6 + i4;
                if (intrinsicWidth > i5) {
                    i5 = intrinsicWidth;
                }
                if (z) {
                    i6 += this.mPaddingL[i7];
                    i4 += this.mPaddingR[i7];
                }
            }
        }
        return i5;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        boolean z = this.mLayerState.mPaddingMode == 0;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNum;
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            ChildDrawable childDrawable = childDrawableArr[i5];
            if (childDrawable.mDrawable != null) {
                int intrinsicHeight = (childDrawable.mHeight < 0 ? childDrawable.mDrawable.getIntrinsicHeight() : childDrawable.mHeight) + childDrawable.mInsetT + childDrawable.mInsetB + i4 + i2;
                if (intrinsicHeight > i3) {
                    i3 = intrinsicHeight;
                }
                if (z) {
                    i4 += this.mPaddingT[i5];
                    i2 += this.mPaddingB[i5];
                }
            }
        }
        return i3;
    }

    private boolean refreshChildPadding(int i, ChildDrawable childDrawable) {
        if (childDrawable.mDrawable == null) {
            return false;
        }
        Rect rect = this.mTmpRect;
        childDrawable.mDrawable.getPadding(rect);
        if (rect.left == this.mPaddingL[i] && rect.top == this.mPaddingT[i] && rect.right == this.mPaddingR[i] && rect.bottom == this.mPaddingB[i]) {
            return false;
        }
        this.mPaddingL[i] = rect.left;
        this.mPaddingT[i] = rect.top;
        this.mPaddingR[i] = rect.right;
        this.mPaddingB[i] = rect.bottom;
        return true;
    }

    void ensurePadding() {
        int i = this.mLayerState.mNum;
        int[] iArr = this.mPaddingL;
        if (iArr == null || iArr.length < i) {
            this.mPaddingL = new int[i];
            this.mPaddingT = new int[i];
            this.mPaddingR = new int[i];
            this.mPaddingB = new int[i];
        }
    }

    void refreshPadding() {
        int i = this.mLayerState.mNum;
        ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            refreshChildPadding(i2, childDrawableArr[i2]);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (!this.mLayerState.canConstantState()) {
            return null;
        }
        this.mLayerState.mChangingConfigurations = getChangingConfigurations();
        return this.mLayerState;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            LayerState layerStateCreateConstantState = createConstantState(this.mLayerState, null);
            this.mLayerState = layerStateCreateConstantState;
            ChildDrawable[] childDrawableArr = layerStateCreateConstantState.mChildren;
            int i = this.mLayerState.mNum;
            for (int i2 = 0; i2 < i; i2++) {
                Drawable drawable = childDrawableArr[i2].mDrawable;
                if (drawable != null) {
                    drawable.mutate();
                }
            }
            this.mMutated = true;
        }
        return this;
    }

    static class ChildDrawable {
        public Drawable mDrawable;
        public int mGravity;
        public int mHeight;
        public int mId;
        public int mInsetB;
        public int mInsetE;
        public int mInsetL;
        public int mInsetR;
        public int mInsetS;
        public int mInsetT;
        public int[] mThemeAttrs;
        public int mWidth;

        public boolean canApplyTheme() {
            return false;
        }

        ChildDrawable() {
            this.mInsetS = Integer.MIN_VALUE;
            this.mInsetE = Integer.MIN_VALUE;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mGravity = 0;
            this.mId = -1;
        }

        ChildDrawable(ChildDrawable childDrawable, LayerDrawable layerDrawable, Resources resources) {
            Drawable.ConstantState constantState;
            Drawable drawableNewDrawable;
            this.mInsetS = Integer.MIN_VALUE;
            this.mInsetE = Integer.MIN_VALUE;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mGravity = 0;
            this.mId = -1;
            Drawable drawable = childDrawable.mDrawable;
            Drawable drawable2 = null;
            if (drawable != null && (constantState = drawable.getConstantState()) != null) {
                if (resources != null) {
                    drawableNewDrawable = constantState.newDrawable(resources);
                } else {
                    drawableNewDrawable = constantState.newDrawable();
                }
                drawableNewDrawable.setCallback(layerDrawable);
                drawableNewDrawable.setBounds(drawable.getBounds());
                drawableNewDrawable.setLevel(drawable.getLevel());
                drawable2 = drawableNewDrawable;
            }
            this.mDrawable = drawable2;
            this.mThemeAttrs = childDrawable.mThemeAttrs;
            this.mInsetL = childDrawable.mInsetL;
            this.mInsetT = childDrawable.mInsetT;
            this.mInsetR = childDrawable.mInsetR;
            this.mInsetB = childDrawable.mInsetB;
            this.mInsetS = childDrawable.mInsetS;
            this.mInsetE = childDrawable.mInsetE;
            this.mWidth = childDrawable.mWidth;
            this.mHeight = childDrawable.mHeight;
            this.mGravity = childDrawable.mGravity;
            this.mId = childDrawable.mId;
        }
    }

    static class LayerState extends Drawable.ConstantState {
        private boolean mAutoMirrored;
        int mChangingConfigurations;
        ChildDrawable[] mChildren;
        int mChildrenChangingConfigurations;
        private boolean mHaveIsStateful;
        private boolean mHaveOpacity;
        private boolean mIsStateful;
        int mNum;
        private int mOpacity;
        int mOpacityOverride;
        int mPaddingBottom;
        int mPaddingEnd;
        int mPaddingLeft;
        private int mPaddingMode;
        int mPaddingRight;
        int mPaddingStart;
        int mPaddingTop;
        int[] mThemeAttrs;

        LayerState(LayerState layerState, LayerDrawable layerDrawable, Resources resources) {
            this.mPaddingTop = -1;
            this.mPaddingBottom = -1;
            this.mPaddingLeft = -1;
            this.mPaddingRight = -1;
            this.mPaddingStart = -1;
            this.mPaddingEnd = -1;
            this.mOpacityOverride = 0;
            this.mAutoMirrored = false;
            this.mPaddingMode = 0;
            if (layerState != null) {
                ChildDrawable[] childDrawableArr = layerState.mChildren;
                int i = layerState.mNum;
                this.mNum = i;
                this.mChildren = new ChildDrawable[i];
                this.mChangingConfigurations = layerState.mChangingConfigurations;
                this.mChildrenChangingConfigurations = layerState.mChildrenChangingConfigurations;
                for (int i2 = 0; i2 < i; i2++) {
                    this.mChildren[i2] = new ChildDrawable(childDrawableArr[i2], layerDrawable, resources);
                }
                this.mHaveOpacity = layerState.mHaveOpacity;
                this.mOpacity = layerState.mOpacity;
                this.mHaveIsStateful = layerState.mHaveIsStateful;
                this.mIsStateful = layerState.mIsStateful;
                this.mAutoMirrored = layerState.mAutoMirrored;
                this.mPaddingMode = layerState.mPaddingMode;
                this.mThemeAttrs = layerState.mThemeAttrs;
                this.mPaddingTop = layerState.mPaddingTop;
                this.mPaddingBottom = layerState.mPaddingBottom;
                this.mPaddingLeft = layerState.mPaddingLeft;
                this.mPaddingRight = layerState.mPaddingRight;
                this.mPaddingStart = layerState.mPaddingStart;
                this.mPaddingEnd = layerState.mPaddingEnd;
                this.mOpacityOverride = layerState.mOpacityOverride;
                return;
            }
            this.mNum = 0;
            this.mChildren = null;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new LayerDrawable(this, (Resources) null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new LayerDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChildrenChangingConfigurations | this.mChangingConfigurations;
        }

        public final int getOpacity() {
            if (this.mHaveOpacity) {
                return this.mOpacity;
            }
            ChildDrawable[] childDrawableArr = this.mChildren;
            int i = this.mNum;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    i2 = -1;
                    break;
                }
                if (childDrawableArr[i2].mDrawable != null) {
                    break;
                }
                i2++;
            }
            int opacity = i2 >= 0 ? childDrawableArr[i2].mDrawable.getOpacity() : -2;
            for (int i3 = i2 + 1; i3 < i; i3++) {
                Drawable drawable = childDrawableArr[i3].mDrawable;
                if (drawable != null) {
                    opacity = Drawable.resolveOpacity(opacity, drawable.getOpacity());
                }
            }
            this.mOpacity = opacity;
            this.mHaveOpacity = true;
            return opacity;
        }

        public final boolean isStateful() {
            if (this.mHaveIsStateful) {
                return this.mIsStateful;
            }
            ChildDrawable[] childDrawableArr = this.mChildren;
            int i = this.mNum;
            boolean z = false;
            for (int i2 = 0; i2 < i; i2++) {
                Drawable drawable = childDrawableArr[i2].mDrawable;
                if (drawable != null && drawable.isStateful()) {
                    z = true;
                    break;
                }
            }
            this.mIsStateful = z;
            this.mHaveIsStateful = true;
            return z;
        }

        public final boolean canConstantState() {
            ChildDrawable[] childDrawableArr = this.mChildren;
            int i = this.mNum;
            for (int i2 = 0; i2 < i; i2++) {
                Drawable drawable = childDrawableArr[i2].mDrawable;
                if (drawable != null && drawable.getConstantState() == null) {
                    return false;
                }
            }
            return true;
        }

        public void invalidateCache() {
            this.mHaveOpacity = false;
            this.mHaveIsStateful = false;
        }
    }
}
