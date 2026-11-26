package com.miui.notes.ui.widget;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.miui.common.view.LinkifyEditText;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ClearableEditText extends LinkifyEditText {
    private static final int[] EMPTY_STATE_SET = {R.attr.state_empty};
    private AccessHelper mAccessHelper;
    private Drawable mDrawable;
    private boolean mPressed;
    private boolean mShown;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.miui.notes.R.attr.clearableEditTextStyle);
    }

    public ClearableEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawable = getCompoundDrawablesRelative()[2];
        addTextChangedListener(new TextWatcher() { // from class: com.miui.notes.ui.widget.ClearableEditText.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (ClearableEditText.this.mShown != (editable.length() > 0)) {
                    ClearableEditText clearableEditText = ClearableEditText.this;
                    clearableEditText.mShown = !clearableEditText.mShown;
                    ClearableEditText.this.refreshDrawableState();
                    if (ClearableEditText.this.mAccessHelper != null) {
                        ClearableEditText.this.mAccessHelper.invalidateRoot();
                    }
                }
            }
        });
        AccessHelper accessHelper = new AccessHelper(this);
        this.mAccessHelper = accessHelper;
        ViewCompat.setAccessibilityDelegate(this, accessHelper);
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView
    public void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            throw new IllegalStateException("ClearableEditText can only set drawables by setCompoundDrawablesRelative()");
        }
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView
    public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        this.mDrawable = drawable3;
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mShown) {
            Drawable drawable = this.mDrawable;
            int intrinsicWidth = drawable == null ? 0 : drawable.getIntrinsicWidth();
            if (!isLayoutRtl(this) ? motionEvent.getX() > (getWidth() - getPaddingRight()) - intrinsicWidth : motionEvent.getX() < intrinsicWidth + getPaddingLeft()) {
                onButtonTouchEvent(motionEvent);
                return true;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (!this.mShown) {
            mergeDrawableStates(iArrOnCreateDrawableState, EMPTY_STATE_SET);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mDrawable != null) {
            this.mDrawable.setState(getDrawableState());
            invalidate();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mDrawable;
    }

    @Override // android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    private void onButtonTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            if (isEnabled() && this.mShown) {
                this.mPressed = true;
                return;
            }
            return;
        }
        if (action == 1) {
            if (isEnabled() && this.mPressed) {
                onClearButtonClick();
                return;
            }
            return;
        }
        if (action == 3 && this.mPressed) {
            this.mPressed = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClearButtonClick() {
        setText("");
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        AccessHelper accessHelper = this.mAccessHelper;
        if (accessHelper != null && this.mShown && accessHelper.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    private class AccessHelper extends ExploreByTouchHelper {
        private static final int CHILD_ID = 0;
        private final View forView;
        private final Rect mTempParentBounds;

        public AccessHelper(View view) {
            super(view);
            this.mTempParentBounds = new Rect();
            this.forView = view;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            return (ClearableEditText.this.mShown && isVirtualView(f, f2)) ? 0 : Integer.MIN_VALUE;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
            super.onPopulateEventForHost(accessibilityEvent);
            if (accessibilityEvent.getEventType() == 2048 && !ClearableEditText.this.mShown && this.forView.isFocused()) {
                this.forView.sendAccessibilityEvent(32768);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onPopulateNodeForHost(accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ClearableEditText.class.getName());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(List list) {
            if (ClearableEditText.this.mShown) {
                list.add(0);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getDescription());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setContentDescription(getDescription());
            accessibilityNodeInfoCompat.addAction(16);
            accessibilityNodeInfoCompat.setClassName(Button.class.getName());
            getChildRect(this.mTempParentBounds);
            accessibilityNodeInfoCompat.setBoundsInParent(this.mTempParentBounds);
            accessibilityNodeInfoCompat.setClickable(true);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i == Integer.MIN_VALUE || i2 != 16) {
                return false;
            }
            ClearableEditText.this.onClearButtonClick();
            return true;
        }

        private CharSequence getDescription() {
            return ClearableEditText.this.getResources().getString(com.miui.notes.R.string.clearable_edittext_clear_description);
        }

        private void getChildRect(Rect rect) {
            this.forView.getLocalVisibleRect(this.mTempParentBounds);
            if (ClearableEditText.this.mDrawable == null) {
                return;
            }
            if (ClearableEditText.isLayoutRtl(ClearableEditText.this)) {
                rect.right -= (ClearableEditText.this.getWidth() - ClearableEditText.this.mDrawable.getIntrinsicWidth()) - (ClearableEditText.this.getPaddingLeft() * 2);
            } else {
                rect.left += (ClearableEditText.this.getWidth() - (ClearableEditText.this.getPaddingRight() * 2)) - ClearableEditText.this.mDrawable.getIntrinsicWidth();
            }
        }

        private boolean isVirtualView(float f, float f2) {
            if (ClearableEditText.isLayoutRtl(ClearableEditText.this)) {
                return f < ((float) (ClearableEditText.this.mDrawable.getIntrinsicWidth() + (ClearableEditText.this.getPaddingLeft() * 2)));
            }
            return f > ((float) ((ClearableEditText.this.getWidth() - (ClearableEditText.this.getPaddingRight() * 2)) - ClearableEditText.this.mDrawable.getIntrinsicWidth()));
        }
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }
}
