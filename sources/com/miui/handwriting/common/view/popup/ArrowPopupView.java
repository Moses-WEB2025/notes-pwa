package com.miui.handwriting.common.view.popup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.internal.util.DeviceHelper;
import miuix.internal.util.ViewUtils;
import miuix.popupwidget.R;
import miuix.view.CompatViewMethod;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ArrowPopupView extends FrameLayout implements View.OnTouchListener {
    private static final int ANIMATION_REPEAT_COUNT = 8;
    public static final byte ARROW_BOTTOM_LEFT_MODE = 18;
    public static final byte ARROW_BOTTOM_MODE = 16;
    public static final byte ARROW_BOTTOM_RIGHT_MODE = 17;
    public static final byte ARROW_LEFT_MODE = 32;
    public static final byte ARROW_NONE_MODE = 0;
    private static final int ARROW_PADDING = 1;
    public static final byte ARROW_RIGHT_MODE = 64;
    public static final byte ARROW_TOP_LEFT_MODE = 9;
    public static final byte ARROW_TOP_MODE = 8;
    public static final byte ARROW_TOP_RIGHT_MODE = 10;
    public static final int LAYOUT_MODE_LTR = 0;
    public static final int LAYOUT_MODE_RTL = 1;
    public static final int LAYOUT_MODE_UNSPECIFIED = 2;
    private static final String TAG = "ArrowPopupView";
    private static final int TRANSLATION_VALUE = 4;
    private boolean isEnableArrow;
    private boolean isEnableFolme;
    private boolean isEnableShadow;
    private View mAnchor;
    private AnimationSet mAnimationSet;
    private AnimatorSet mAnimator;
    private AppCompatImageView mArrow;
    private Drawable mArrowBottom;
    private Drawable mArrowBottomLeft;
    private Drawable mArrowBottomRight;
    private Drawable mArrowLeft;
    private int mArrowMode;
    private ArrowPopupWindow mArrowPopupWindow;
    private Drawable mArrowRight;
    private int mArrowSpaceLeft;
    private int mArrowSpaceTop;
    private Drawable mArrowTop;
    private Drawable mArrowTopLeft;
    private Drawable mArrowTopRight;
    private Drawable mArrowTopWithTitle;
    private boolean mAutoDismiss;
    private Drawable mBackground;
    private Drawable mBackgroundLeft;
    private Drawable mBackgroundRight;
    private FrameLayout mContentFrame;
    private LinearLayout mContentFrameWrapper;
    private int mElevation;
    private float mFlomeAnimationPivotX;
    private float mFlomeAnimationPivotY;
    private Animation.AnimationListener mHideAnimatorListener;
    private boolean mIsDismissing;
    private int mMinBorder;
    private AppCompatButton mNegativeButton;
    private WrapperOnClickListener mNegativeClickListener;
    private int mOffsetX;
    private int mOffsetY;
    private AppCompatButton mPositiveButton;
    private WrapperOnClickListener mPositiveClickListener;
    private int mRtlMode;
    private int mShadowInset;
    private Animation.AnimationListener mShowAnimationListener;
    private boolean mShowingAnimation;
    private int mSpaceLeft;
    private int mSpaceTop;
    private Drawable mTitleBackground;
    private LinearLayout mTitleLayout;
    private AppCompatTextView mTitleText;
    private Rect mTmpRect;
    private RectF mTmpRectF;
    private View.OnTouchListener mTouchInterceptor;
    private int mTranslationValue;

    @Deprecated
    public float getRollingPercent() {
        return 1.0f;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
    }

    @Deprecated
    public void setRollingPercent(float f) {
    }

    public ArrowPopupView(Context context) {
        this(context, null);
    }

    public ArrowPopupView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.arrowPopupViewStyle);
    }

    public ArrowPopupView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTmpRect = new Rect();
        this.mTmpRectF = new RectF();
        this.mAutoDismiss = true;
        this.mRtlMode = 2;
        this.mShowingAnimation = false;
        this.isEnableArrow = true;
        this.isEnableFolme = false;
        this.isEnableShadow = true;
        this.mFlomeAnimationPivotX = 0.0f;
        this.mFlomeAnimationPivotY = 0.0f;
        this.mShadowInset = 0;
        this.mShowAnimationListener = new Animation.AnimationListener() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                ArrowPopupView.this.mAnimationSet = null;
                if (ArrowPopupView.this.mShowingAnimation) {
                    try {
                        ArrowPopupView.this.animateShowing();
                    } catch (Exception e) {
                        Log.d(ArrowPopupView.TAG, "error:" + e);
                    }
                }
            }
        };
        this.mHideAnimatorListener = new Animation.AnimationListener() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                ArrowPopupView.this.mIsDismissing = false;
                ArrowPopupView.this.mAnimationSet = null;
                ArrowPopupView.this.mArrowPopupWindow.dismiss();
            }
        };
        this.mArrowMode = 0;
        CompatViewMethod.setForceDarkAllowed(this, false);
        this.mAutoDismiss = true;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ArrowPopupView, i, R.style.Widget_ArrowPopupView_DayNight);
        this.mBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_contentBackground);
        this.mBackgroundLeft = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_backgroundLeft);
        this.mBackgroundRight = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_backgroundRight);
        this.mTitleBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_titleBackground);
        this.mArrowTop = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_topArrow);
        this.mArrowTopWithTitle = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_topArrowWithTitle);
        this.mArrowBottom = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_bottomArrow);
        this.mArrowRight = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_rightArrow);
        this.mArrowLeft = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_leftArrow);
        this.mArrowTopLeft = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_topLeftArrow);
        this.mArrowTopRight = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_topRightArrow);
        this.mArrowBottomRight = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_bottomRightArrow);
        this.mArrowBottomLeft = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_bottomLeftArrow);
        this.mElevation = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ArrowPopupView_android_elevation, getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_arrow_popup_window_elevation));
        typedArrayObtainStyledAttributes.recycle();
        this.mMinBorder = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_arrow_popup_window_min_border);
    }

    public void setAutoDismiss(boolean z) {
        this.mAutoDismiss = z;
    }

    public void setLayoutRtlMode(int i) {
        if (i <= 2 && i >= 0) {
            this.mRtlMode = i;
        } else {
            this.mRtlMode = 2;
        }
    }

    public void enableArrow(boolean z) {
        this.isEnableArrow = z;
    }

    public void enableFolme(boolean z) {
        this.isEnableFolme = z;
    }

    public void enableShadow(boolean z) {
        this.isEnableShadow = z;
    }

    public boolean isEnableFolme() {
        return this.isEnableFolme;
    }

    public void setFolmeAnimationPivot(float f, float f2) {
        Log.d("QuickColorPickerPopupWindow", "x = " + f + " y = " + f2);
        this.mFlomeAnimationPivotX = f;
        this.mFlomeAnimationPivotY = f2;
    }

    public void setShadowInset(int i) {
        this.mShadowInset = i;
    }

    public int[] getArrowImageHeightAndWidth(int i) {
        int[] iArr = new int[2];
        if (i == 32) {
            iArr[0] = this.mArrowLeft.getIntrinsicHeight();
            iArr[1] = this.mArrowLeft.getIntrinsicWidth();
            return iArr;
        }
        if (i != 64) {
            switch (i) {
                case 8:
                    iArr[0] = this.mArrowTop.getIntrinsicHeight();
                    iArr[1] = this.mArrowTop.getIntrinsicWidth();
                    break;
                case 9:
                    iArr[0] = this.mArrowTopLeft.getIntrinsicHeight();
                    iArr[1] = this.mArrowTopLeft.getIntrinsicWidth();
                    break;
                case 10:
                    iArr[0] = this.mArrowTopRight.getIntrinsicHeight();
                    iArr[1] = this.mArrowTopRight.getIntrinsicWidth();
                    break;
                default:
                    switch (i) {
                        case 16:
                            iArr[0] = this.mArrowBottom.getIntrinsicHeight();
                            iArr[1] = this.mArrowBottom.getIntrinsicWidth();
                            break;
                        case 17:
                            iArr[0] = this.mArrowBottomRight.getIntrinsicHeight();
                            iArr[1] = this.mArrowBottomRight.getIntrinsicWidth();
                            break;
                        case 18:
                            iArr[0] = this.mArrowBottomLeft.getIntrinsicHeight();
                            iArr[1] = this.mArrowBottomLeft.getIntrinsicWidth();
                            break;
                    }
                    break;
            }
            return iArr;
        }
        iArr[0] = this.mArrowRight.getIntrinsicHeight();
        iArr[1] = this.mArrowRight.getIntrinsicWidth();
        return iArr;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mArrow = (AppCompatImageView) findViewById(R.id.popup_arrow);
        this.mContentFrame = (FrameLayout) findViewById(android.R.id.content);
        this.mContentFrameWrapper = (LinearLayout) findViewById(R.id.content_wrapper);
        this.mContentFrameWrapper.setMinimumHeight(getContext().getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_arrow_popup_view_min_height));
        if (this.mBackgroundLeft != null && this.mBackgroundRight != null) {
            Rect rect = new Rect();
            this.mBackgroundLeft.getPadding(rect);
            this.mContentFrameWrapper.setPadding(rect.top, rect.top, rect.top, rect.top);
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.title_layout);
        this.mTitleLayout = linearLayout;
        linearLayout.setBackground(this.mTitleBackground);
        this.mTitleText = (AppCompatTextView) findViewById(android.R.id.title);
        this.mPositiveButton = (AppCompatButton) findViewById(16908314);
        this.mNegativeButton = (AppCompatButton) findViewById(16908313);
        this.mPositiveClickListener = new WrapperOnClickListener();
        this.mNegativeClickListener = new WrapperOnClickListener();
        this.mPositiveButton.setOnClickListener(this.mPositiveClickListener);
        this.mNegativeButton.setOnClickListener(this.mNegativeClickListener);
    }

    public int getContentFrameWrapperTopPadding() {
        return this.mContentFrameWrapper.getPaddingTop();
    }

    public int getContentFrameWrapperBottomPadding() {
        return this.mContentFrameWrapper.getPaddingBottom();
    }

    public boolean isTitleEmpty() {
        return TextUtils.isEmpty(this.mTitleText.getText());
    }

    public int getTitleHeight() {
        if (this.mTitleLayout.getVisibility() != 8) {
            return this.mTitleLayout.getMeasuredHeight();
        }
        return 0;
    }

    public void addShadow() {
        if (this.isEnableShadow) {
            addShadow(this.mArrow, new ViewOutlineProvider() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.3
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() == 0 || view.getHeight() == 0) {
                        return;
                    }
                    int width = view.getWidth();
                    int height = view.getHeight();
                    boolean z = false;
                    Rect rect = new Rect(0, 0, width, height);
                    if (width > height) {
                        int i = (width - height) / 2;
                        rect.left += i;
                        rect.right -= i;
                    } else {
                        int i2 = (height - width) / 2;
                        rect.top += i2;
                        rect.bottom -= i2;
                    }
                    Path path = new Path();
                    int i3 = ArrowPopupView.this.mArrowMode;
                    if (i3 == 32 || i3 == 64) {
                        if ((ArrowPopupView.this.mRtlMode != 1 && ArrowPopupView.this.mArrowMode == 32) || (ArrowPopupView.this.mRtlMode == 1 && ArrowPopupView.this.mArrowMode == 64)) {
                            z = true;
                        }
                        float f = (rect.bottom + rect.top) / 2.0f;
                        if (z) {
                            path.moveTo(rect.right, rect.top);
                            path.quadTo(-rect.width(), f, rect.right, rect.bottom);
                        } else {
                            path.moveTo(rect.left, rect.top);
                            path.quadTo(rect.right + rect.width(), f, rect.left, rect.bottom);
                        }
                        path.close();
                    } else {
                        switch (i3) {
                            case 8:
                                float f2 = (rect.right + rect.left) / 2.0f;
                                path.moveTo(rect.left, rect.bottom);
                                path.quadTo(f2, -rect.height(), rect.right, rect.bottom);
                                path.close();
                                break;
                            case 9:
                            case 10:
                                if ((ArrowPopupView.this.mRtlMode != 1 && ArrowPopupView.this.mArrowMode == 9) || (ArrowPopupView.this.mRtlMode == 1 && ArrowPopupView.this.mArrowMode == 10)) {
                                    z = true;
                                }
                                path.moveTo(0.0f, ArrowPopupView.this.mArrowTop.getIntrinsicHeight());
                                if (z) {
                                    path.quadTo(0.0f, (-ArrowPopupView.this.mArrowTop.getIntrinsicHeight()) * 0.7f, rect.right, ArrowPopupView.this.mArrowTop.getIntrinsicHeight());
                                } else {
                                    path.quadTo(rect.right, (-ArrowPopupView.this.mArrowTop.getIntrinsicHeight()) * 0.7f, rect.right, ArrowPopupView.this.mArrowTop.getIntrinsicHeight());
                                }
                                path.close();
                                break;
                        }
                    }
                    if (path.isConvex()) {
                        outline.setConvexPath(path);
                    } else {
                        Log.d(ArrowPopupView.TAG, "outline path is not convex");
                        outline.setOval(rect);
                    }
                }
            });
            addShadow(this.mContentFrameWrapper, new ViewOutlineProvider() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.4
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    if (view.getWidth() == 0 || view.getHeight() == 0) {
                        return;
                    }
                    Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                    rect.bottom -= view.getPaddingBottom();
                    rect.top += view.getPaddingTop();
                    rect.right -= view.getPaddingRight();
                    rect.left += view.getPaddingLeft();
                    rect.left += ArrowPopupView.this.mShadowInset;
                    rect.top += ArrowPopupView.this.mShadowInset;
                    rect.right -= ArrowPopupView.this.mShadowInset;
                    rect.bottom -= ArrowPopupView.this.mShadowInset;
                    outline.setRoundRect(rect, ArrowPopupView.this.getContext().getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_arrow_popup_view_round_corners));
                }
            });
        }
    }

    private void addShadow(View view, ViewOutlineProvider viewOutlineProvider) {
        view.setOutlineProvider(viewOutlineProvider);
        view.setElevation(this.mElevation);
    }

    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(-2, -2));
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mContentFrame.removeAllViews();
        if (view != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            this.mContentFrame.addView(view, layoutParams);
        }
    }

    public View getContentView() {
        if (this.mContentFrame.getChildCount() > 0) {
            return this.mContentFrame.getChildAt(0);
        }
        return null;
    }

    public void setContentView(int i) {
        setContentView(LayoutInflater.from(getContext()).inflate(i, (ViewGroup) null));
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitleLayout.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mTitleText.setText(charSequence);
    }

    public void setNegativeButton(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mNegativeButton.setText(charSequence);
        this.mNegativeButton.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mNegativeClickListener.setOnClickListener(onClickListener);
    }

    public void setPositiveButton(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mPositiveButton.setText(charSequence);
        this.mPositiveButton.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mPositiveClickListener.setOnClickListener(onClickListener);
    }

    public AppCompatButton getPositiveButton() {
        return this.mPositiveButton;
    }

    public AppCompatButton getNegativeButton() {
        return this.mNegativeButton;
    }

    private boolean isVerticalMode() {
        return isTopMode() || isBottomMode();
    }

    private boolean isCertainMode(int i) {
        return (this.mArrowMode & i) == i;
    }

    private boolean isLeftMode() {
        return isCertainMode(32);
    }

    private boolean isRightMode() {
        return isCertainMode(64);
    }

    private boolean isTopMode() {
        return isCertainMode(8);
    }

    private boolean isBottomMode() {
        return isCertainMode(16);
    }

    private void arrowLayout() {
        int i = this.mRtlMode;
        int i2 = (i == 1 || (i == 2 && ViewUtils.isLayoutRtl(this))) ? -this.mOffsetX : this.mOffsetX;
        if (isVerticalMode()) {
            arrowVerticalLayout(i2);
        } else {
            arrowHorizontalLayout(i2);
        }
        View contentView = getContentView();
        if (contentView != null) {
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            if (contentView.getMeasuredHeight() > this.mContentFrameWrapper.getMeasuredHeight() - this.mTitleLayout.getMeasuredHeight()) {
                layoutParams.height = this.mContentFrameWrapper.getMeasuredHeight() - this.mTitleLayout.getMeasuredHeight();
                contentView.setLayoutParams(layoutParams);
            } else if (contentView.getMeasuredWidth() > this.mContentFrameWrapper.getMeasuredWidth()) {
                layoutParams.width = this.mContentFrameWrapper.getMeasuredWidth();
                contentView.setLayoutParams(layoutParams);
            }
            if (layoutParams.height <= 0 || layoutParams.width <= 0) {
                Log.w(TAG, "Invalid LayoutPrams of content view, please check the anchor view");
            }
        }
    }

    private void arrowVerticalLayout(int i) {
        int paddingTop;
        int width = this.mAnchor.getWidth();
        int height = this.mAnchor.getHeight();
        int width2 = getWidth();
        int height2 = getHeight();
        int iMax = Math.max(this.mContentFrameWrapper.getMeasuredWidth(), this.mContentFrameWrapper.getMinimumWidth());
        int iMax2 = Math.max(this.mContentFrameWrapper.getMeasuredHeight(), this.mContentFrameWrapper.getMinimumHeight());
        int arrowWidth = getArrowWidth();
        int arrowHeight = getArrowHeight();
        int[] iArr = new int[2];
        this.mAnchor.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        getLocationOnScreen(iArr);
        int i4 = iArr[0];
        int i5 = ((width / 2) + i2) - i4;
        this.mSpaceLeft = i5;
        int i6 = width2 - i5;
        this.mArrowSpaceLeft = (i2 + ((width - arrowWidth) / 2)) - i4;
        this.mSpaceTop = getTop() + this.mOffsetY;
        if (isBottomMode()) {
            this.mSpaceTop += ((i3 - iArr[1]) - iMax2) + (this.mContentFrameWrapper.getPaddingBottom() - arrowHeight);
            paddingTop = (((i3 - iArr[1]) - arrowHeight) + this.mOffsetY) - 1;
        } else if (isTopMode()) {
            int paddingTop2 = this.mSpaceTop + (((i3 + height) - iArr[1]) - this.mContentFrameWrapper.getPaddingTop()) + arrowHeight;
            this.mSpaceTop = paddingTop2;
            paddingTop = paddingTop2 + (this.mContentFrameWrapper.getPaddingTop() - arrowHeight) + 1;
        } else {
            paddingTop = 0;
        }
        int i7 = iMax / 2;
        int i8 = iMax - i7;
        int i9 = this.mSpaceLeft;
        if (i9 >= i7 && i6 >= i8) {
            this.mSpaceLeft = i9 - i7;
        } else if (i6 < i8) {
            this.mSpaceLeft = width2 - iMax;
        } else if (i9 < i7) {
            this.mSpaceLeft = 0;
        }
        int i10 = this.mSpaceLeft + i;
        this.mSpaceLeft = i10;
        int i11 = this.mArrowSpaceLeft + i;
        this.mArrowSpaceLeft = i11;
        if (i11 < 0) {
            this.mArrowSpaceLeft = 0;
        } else if (i11 + arrowWidth > width2) {
            this.mArrowSpaceLeft = i11 - ((i11 + arrowWidth) - width2);
        }
        this.mContentFrameWrapper.layout(Math.max(i10, 0), Math.max(this.mSpaceTop, 0), Math.min(this.mSpaceLeft + iMax, width2), Math.min(this.mSpaceTop + iMax2, height2));
        executeLayoutArrow(arrowWidth, arrowHeight, paddingTop);
    }

    private void executeLayoutArrow(int i, int i2, int i3) {
        int right;
        int i4;
        int right2;
        int bottom;
        int measuredHeight;
        int i5 = this.mRtlMode;
        boolean z = i5 == 1 || (i5 == 2 && ViewUtils.isLayoutRtl(this));
        int i6 = this.mArrowMode;
        if (i6 == 9 || i6 == 10) {
            if ((!z && i6 == 9) || (z && i6 == 10)) {
                right = (this.mContentFrameWrapper.getLeft() + this.mContentFrameWrapper.getPaddingStart()) - 1;
            } else {
                right = (this.mContentFrameWrapper.getRight() - this.mContentFrameWrapper.getPaddingStart()) - i;
            }
            i3 = (i3 + this.mContentFrameWrapper.getPaddingTop()) - i2;
            AppCompatImageView appCompatImageView = this.mArrow;
            appCompatImageView.layout(right, i3, right + i, appCompatImageView.getMeasuredHeight() + i3);
            i4 = right;
        } else if (i6 == 17 || i6 == 18) {
            if ((!z && i6 == 18) || (z && i6 == 17)) {
                right2 = this.mContentFrameWrapper.getLeft() + this.mContentFrameWrapper.getPaddingStart();
                bottom = this.mContentFrameWrapper.getBottom() - this.mContentFrameWrapper.getPaddingBottom();
                measuredHeight = this.mArrow.getMeasuredHeight();
            } else {
                right2 = (this.mContentFrameWrapper.getRight() - this.mContentFrameWrapper.getPaddingEnd()) - i;
                bottom = this.mContentFrameWrapper.getBottom() - this.mContentFrameWrapper.getPaddingBottom();
                measuredHeight = this.mArrow.getMeasuredHeight();
            }
            int i7 = bottom - (measuredHeight - i2);
            i4 = right2;
            if (this.mArrowMode == 18) {
                AppCompatImageView appCompatImageView2 = this.mArrow;
                appCompatImageView2.layout(i4, i7, i4 + i, appCompatImageView2.getMeasuredHeight() + i7);
            }
            i3 = i7 - 5;
        } else {
            i4 = this.mArrowSpaceLeft;
        }
        AppCompatImageView appCompatImageView3 = this.mArrow;
        appCompatImageView3.layout(i4, i3, i + i4, appCompatImageView3.getDrawable().getIntrinsicHeight() + i3);
    }

    private void arrowHorizontalLayout(int i) {
        int i2;
        int i3;
        int paddingLeft;
        int paddingLeft2;
        int[] iArr = new int[2];
        this.mAnchor.getLocationOnScreen(iArr);
        int i4 = iArr[0];
        int i5 = iArr[1];
        getLocationOnScreen(iArr);
        int width = this.mAnchor.getWidth();
        int height = this.mAnchor.getHeight();
        int width2 = getWidth();
        int height2 = getHeight();
        int iMax = Math.max(this.mContentFrameWrapper.getMeasuredWidth(), this.mContentFrameWrapper.getMinimumWidth());
        int iMax2 = Math.max(this.mContentFrameWrapper.getMeasuredHeight(), this.mContentFrameWrapper.getMinimumHeight());
        int arrowWidth = getArrowWidth();
        int arrowHeight = getArrowHeight();
        int i6 = iArr[1];
        int i7 = ((height / 2) + i5) - i6;
        this.mSpaceTop = i7;
        int i8 = height2 - i7;
        this.mArrowSpaceTop = ((i5 + ((height - arrowHeight) / 2)) - i6) + ((this.mContentFrameWrapper.getPaddingTop() - this.mContentFrameWrapper.getPaddingBottom()) / 2);
        int i9 = iMax2 / 2;
        int i10 = iMax2 - i9;
        this.mSpaceLeft = getLeft() + i;
        if (isRightMode()) {
            int i11 = this.mRtlMode;
            if (i11 == 1 || (i11 == 2 && ViewUtils.isLayoutRtl(this))) {
                paddingLeft = this.mSpaceLeft + ((((i4 + width) - this.mContentFrameWrapper.getPaddingLeft()) + arrowWidth) - iArr[0]);
                this.mSpaceLeft = paddingLeft;
                paddingLeft2 = this.mContentFrameWrapper.getPaddingLeft();
                i2 = paddingLeft + (paddingLeft2 - arrowWidth) + 1;
            } else {
                int i12 = this.mSpaceLeft;
                int paddingRight = ((i4 - iMax) + this.mContentFrameWrapper.getPaddingRight()) - arrowWidth;
                i3 = iArr[0];
                this.mSpaceLeft = i12 + (paddingRight - i3);
                i2 = (((i4 - arrowWidth) - i3) + i) - 1;
            }
        } else if (isLeftMode()) {
            int i13 = this.mRtlMode;
            if (i13 == 1 || (i13 == 2 && ViewUtils.isLayoutRtl(this))) {
                int i14 = this.mSpaceLeft;
                int paddingRight2 = ((i4 - iMax) + this.mContentFrameWrapper.getPaddingRight()) - arrowWidth;
                i3 = iArr[0];
                this.mSpaceLeft = i14 + (paddingRight2 - i3) + 1;
                i2 = (((i4 - arrowWidth) - i3) + i) - 1;
            } else {
                paddingLeft = this.mSpaceLeft + ((((i4 + width) - this.mContentFrameWrapper.getPaddingLeft()) + arrowWidth) - iArr[0]);
                this.mSpaceLeft = paddingLeft;
                paddingLeft2 = this.mContentFrameWrapper.getPaddingLeft();
                i2 = paddingLeft + (paddingLeft2 - arrowWidth) + 1;
            }
        } else {
            i2 = 0;
        }
        int i15 = this.mSpaceTop;
        if (i15 >= i9 && i8 >= i10) {
            this.mSpaceTop = (i15 - i9) + this.mOffsetY;
        } else if (i8 < i10) {
            this.mSpaceTop = (height2 - iMax2) + this.mOffsetY;
        } else if (i15 < i9) {
            this.mSpaceTop = this.mOffsetY;
        }
        int i16 = this.mArrowSpaceTop + this.mOffsetY;
        this.mArrowSpaceTop = i16;
        if (i16 < 0) {
            this.mArrowSpaceTop = 0;
        } else if (i16 + arrowHeight > height2) {
            this.mArrowSpaceTop = i16 - ((i16 + arrowHeight) - height2);
        }
        this.mContentFrameWrapper.layout(Math.max(this.mSpaceLeft, 0), Math.max(this.mSpaceTop, 0), Math.min(this.mSpaceLeft + iMax, width2), Math.min(this.mSpaceTop + iMax2, height2));
        AppCompatImageView appCompatImageView = this.mArrow;
        int i17 = this.mArrowSpaceTop;
        appCompatImageView.layout(i2, i17, arrowWidth + i2, arrowHeight + i17);
    }

    public int getArrowWidth() {
        if (!this.isEnableArrow) {
            return 0;
        }
        int measuredWidth = this.mArrow.getMeasuredWidth();
        return measuredWidth == 0 ? this.mArrow.getDrawable().getIntrinsicWidth() : measuredWidth;
    }

    public int getArrowHeight() {
        if (!this.isEnableArrow) {
            return 0;
        }
        int i = this.mArrowMode;
        if (i == 9 || i == 10) {
            return this.mArrowTop.getIntrinsicHeight();
        }
        if (i == 17 || i == 18) {
            return this.mArrowBottom.getIntrinsicHeight();
        }
        int measuredHeight = this.mArrow.getMeasuredHeight();
        return measuredHeight == 0 ? this.mArrow.getDrawable().getIntrinsicHeight() : measuredHeight;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mAnchor.isAttachedToWindow()) {
            if (this.mArrowPopupWindow.isShowing()) {
                this.mArrowPopupWindow.dismiss();
            }
        } else {
            if (this.mArrowMode == 0) {
                adjustArrowMode();
            }
            updateArrowDrawable(this.mArrowMode);
            arrowLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:19:0x0044  */
    /* JADX WARN: Code duplicated, block: B:23:0x004b A[PHI: r3
  0x004b: PHI (r3v7 int) = (r3v6 int), (r3v1 int), (r3v1 int) binds: [B:22:0x0049, B:20:0x0046, B:17:0x0041] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:26:0x0050  */
    /* JADX WARN: Code duplicated, block: B:30:0x0057 A[PHI: r3
  0x0057: PHI (r3v4 int) = (r3v3 int), (r3v1 int), (r3v1 int) binds: [B:29:0x0055, B:27:0x0052, B:25:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x0035. Please report as an issue. */
    public void getAnimationPivot(float[] fArr) {
        float f;
        float f2;
        int top = this.mArrow.getTop();
        int bottom = this.mArrow.getBottom();
        int left = this.mArrow.getLeft();
        int right = this.mArrow.getRight();
        int i = this.mRtlMode;
        boolean z = i == 1 || (i == 2 && ViewUtils.isLayoutRtl(this));
        int i2 = this.mArrowMode;
        if (i2 == 32) {
            f = z ? right : left;
            bottom = (bottom + top) / 2;
        } else if (i2 == 64) {
            f = z ? left : right;
            bottom = (bottom + top) / 2;
        } else {
            switch (i2) {
                case 8:
                    right = (right + left) / 2;
                    f = right;
                    f2 = top;
                    break;
                case 9:
                    if (z) {
                        f = right;
                    } else {
                        f = left;
                    }
                    f2 = top;
                    break;
                case 10:
                    if (z) {
                        f = left;
                    } else {
                        f = right;
                    }
                    f2 = top;
                    break;
                default:
                    switch (i2) {
                        case 16:
                            right = (right + left) / 2;
                            f = right;
                            break;
                        case 17:
                            if (!z) {
                                f = right;
                            } else {
                                f = left;
                            }
                            break;
                        case 18:
                            if (!z) {
                                f = left;
                            } else {
                                f = right;
                            }
                            break;
                        default:
                            f = (right + left) / 2;
                            bottom = (bottom + top) / 2;
                            break;
                    }
                    break;
            }
            fArr[0] = f;
            fArr[1] = f2;
        }
        f2 = bottom;
        fArr[0] = f;
        fArr[1] = f2;
    }

    public void animateToShowFolme() {
        Log.d("QuickColorPickerPopupWindow", "folme animateToShowFolme");
        Log.d(TAG, "onPreDraw: " + this.mContentFrameWrapper.getMeasuredWidth() + StringUtils.SPACE + this.mContentFrameWrapper.getMeasuredHeight());
        AnimConfig animConfig = new AnimConfig();
        animConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(0.78f, 0.36f), new float[0]);
        animConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(0.78f, 0.36f), new float[0]);
        animConfig.setSpecial(ViewProperty.ALPHA, FolmeEase.linear(200L), new float[0]);
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.5
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                Log.e(ArrowPopupView.TAG, "ww onUpdate: alpha" + ArrowPopupView.this.mContentFrameWrapper.getAlpha());
            }
        });
        this.mContentFrameWrapper.setPivotX(this.mFlomeAnimationPivotX);
        this.mContentFrameWrapper.setPivotY(this.mFlomeAnimationPivotY);
        IStateStyle iStateStyleState = Folme.use((View) this.mContentFrameWrapper).state();
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(0.8f);
        IStateStyle to = iStateStyleState.setTo(viewProperty, fValueOf, ViewProperty.SCALE_Y, fValueOf, ViewProperty.ALPHA, Float.valueOf(0.0f));
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        Float fValueOf2 = Float.valueOf(1.0f);
        to.to(viewProperty2, fValueOf2, ViewProperty.SCALE_Y, fValueOf2, ViewProperty.ALPHA, fValueOf2, animConfig);
    }

    public void animateToDismissFolme() {
        Log.d("QuickColorPickerPopupWindow", "folme animateToDismissFolme");
        AnimConfig animConfig = new AnimConfig();
        animConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(0.78f, 0.36f), new float[0]);
        animConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(0.78f, 0.36f), new float[0]);
        animConfig.setSpecial(ViewProperty.ALPHA, FolmeEase.linear(200L), new float[0]);
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.6
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                ArrowPopupView.this.mArrowPopupWindow.dismiss();
                ArrowPopupView.this.setElevation(0.0f);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                Log.e(ArrowPopupView.TAG, "ww onUpdate: alpha" + ArrowPopupView.this.mContentFrameWrapper.getAlpha());
            }
        });
        this.mContentFrameWrapper.setPivotX(this.mFlomeAnimationPivotX);
        this.mContentFrameWrapper.setPivotY(this.mFlomeAnimationPivotY);
        IStateStyle to = Folme.use((View) this.mContentFrameWrapper).state().setTo(ViewProperty.SCALE_X, Float.valueOf(this.mContentFrameWrapper.getScaleX()), ViewProperty.SCALE_Y, Float.valueOf(this.mContentFrameWrapper.getScaleX()), Float.valueOf(this.mContentFrameWrapper.getScaleX()), ViewProperty.ALPHA, Float.valueOf(this.mContentFrameWrapper.getAlpha()));
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(0.8f);
        to.to(viewProperty, fValueOf, ViewProperty.SCALE_Y, fValueOf, ViewProperty.ALPHA, Float.valueOf(0.0f), animConfig);
    }

    public void animateToShow() {
        invalidate();
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.7
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ArrowPopupView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                if (ArrowPopupView.this.mAnimator != null) {
                    ArrowPopupView.this.mAnimator.cancel();
                }
                if (ArrowPopupView.this.mAnimationSet != null) {
                    ArrowPopupView.this.mAnimationSet.cancel();
                }
                ArrowPopupView.this.mAnimationSet = new AnimationSet(true);
                float[] fArr = new float[2];
                ArrowPopupView.this.getAnimationPivot(fArr);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 0.6f, 1.0f, 0, fArr[0], 0, fArr[1]);
                final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(ArrowPopupView.this, "alpha", 0.0f, 1.0f);
                if (!DeviceHelper.isFeatureWholeAnim()) {
                    ArrowPopupView.this.mAnimationSet.setDuration(0L);
                } else {
                    objectAnimatorOfFloat.setDuration(100L);
                    scaleAnimation.setDuration(280L);
                }
                ArrowPopupView.this.mAnimationSet.addAnimation(scaleAnimation);
                objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
                ArrowPopupView.this.mAnimationSet.addAnimation(new Animation() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.7.1
                    @Override // android.view.animation.Animation
                    public void start() {
                        super.start();
                        objectAnimatorOfFloat.start();
                    }
                });
                ArrowPopupView.this.mAnimationSet.setAnimationListener(ArrowPopupView.this.mShowAnimationListener);
                ArrowPopupView.this.mAnimationSet.setInterpolator(new DecelerateInterpolator(1.5f));
                ArrowPopupView arrowPopupView = ArrowPopupView.this;
                arrowPopupView.startAnimation(arrowPopupView.mAnimationSet);
                return true;
            }
        });
    }

    public void animateToDismiss() {
        if (this.mIsDismissing) {
            return;
        }
        AnimatorSet animatorSet = this.mAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AnimationSet animationSet = this.mAnimationSet;
        if (animationSet != null) {
            animationSet.cancel();
        }
        this.mAnimationSet = new AnimationSet(true);
        float[] fArr = new float[2];
        getAnimationPivot(fArr);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, 0, fArr[0], 0, fArr[1]);
        final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
        if (DeviceHelper.isFeatureWholeAnim()) {
            scaleAnimation.setDuration(150L);
            objectAnimatorOfFloat.setDuration(150L);
        } else {
            this.mAnimationSet.setDuration(0L);
        }
        this.mAnimationSet.addAnimation(scaleAnimation);
        objectAnimatorOfFloat.setInterpolator(new AccelerateInterpolator(2.0f));
        this.mAnimationSet.addAnimation(new Animation() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.8
            @Override // android.view.animation.Animation
            public void start() {
                super.start();
                objectAnimatorOfFloat.start();
            }
        });
        this.mAnimationSet.setAnimationListener(this.mHideAnimatorListener);
        this.mAnimationSet.setInterpolator(new AccelerateInterpolator(2.0f));
        startAnimation(this.mAnimationSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateShowing() {
        if (DeviceHelper.isFeatureWholeAnim()) {
            AnimationSet animationSet = this.mAnimationSet;
            if (animationSet != null) {
                animationSet.cancel();
            }
            AnimatorSet animatorSet = this.mAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.mAnimator = animatorSet2;
            animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.9
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ArrowPopupView.this.mArrowPopupWindow.dismiss();
                }
            });
            float f = getContext().getResources().getDisplayMetrics().density * 4.0f;
            Property property = View.TRANSLATION_Y;
            int i = this.mRtlMode;
            boolean z = i == 1 || (i == 2 && ViewUtils.isLayoutRtl(this));
            int i2 = this.mArrowMode;
            if (i2 == 16) {
                f = -f;
            } else if (i2 == 32) {
                if (z) {
                    f = -f;
                }
                property = View.TRANSLATION_X;
            } else if (i2 == 64) {
                if (!z) {
                    f = -f;
                }
                property = View.TRANSLATION_X;
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mContentFrameWrapper, (Property<LinearLayout, Float>) property, 0.0f, f, 0.0f);
            objectAnimatorOfFloat.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimatorOfFloat.setDuration(1200L);
            if (this.mAutoDismiss) {
                objectAnimatorOfFloat.setRepeatCount(8);
            } else {
                objectAnimatorOfFloat.setRepeatCount(-1);
            }
            objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.handwriting.common.view.popup.ArrowPopupView.10
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ArrowPopupView.this.mTranslationValue = ((Float) valueAnimator.getAnimatedValue()).intValue();
                    int iAbs = Math.abs(ArrowPopupView.this.mTranslationValue);
                    ArrowPopupView arrowPopupView = ArrowPopupView.this;
                    arrowPopupView.invalidate(arrowPopupView.mContentFrameWrapper.getLeft() - iAbs, ArrowPopupView.this.mContentFrameWrapper.getTop() - iAbs, ArrowPopupView.this.mContentFrameWrapper.getRight() + iAbs, ArrowPopupView.this.mContentFrameWrapper.getBottom() + iAbs);
                }
            });
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mArrow, (Property<AppCompatImageView, Float>) property, 0.0f, f, 0.0f);
            objectAnimatorOfFloat2.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimatorOfFloat2.setDuration(1200L);
            if (this.mAutoDismiss) {
                objectAnimatorOfFloat2.setRepeatCount(8);
            } else {
                objectAnimatorOfFloat2.setRepeatCount(-1);
            }
            this.mAnimator.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
            this.mAnimator.start();
        }
    }

    private void adjustArrowMode() {
        int[] iArr = new int[2];
        this.mAnchor.getLocationInWindow(iArr);
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = this.mContentFrameWrapper.getMeasuredWidth();
        int measuredHeight = this.mContentFrameWrapper.getMeasuredHeight();
        int height2 = this.mAnchor.getHeight();
        int width2 = this.mAnchor.getWidth();
        SparseIntArray sparseIntArray = new SparseIntArray(4);
        int i = 16;
        sparseIntArray.put(16, iArr[1] - measuredHeight);
        sparseIntArray.put(8, ((height - iArr[1]) - height2) - measuredHeight);
        sparseIntArray.put(64, iArr[0] - measuredWidth);
        sparseIntArray.put(32, ((width - iArr[0]) - width2) - measuredWidth);
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
            int iKeyAt = sparseIntArray.keyAt(i3);
            if (sparseIntArray.get(iKeyAt) >= this.mMinBorder) {
                i = iKeyAt;
                break;
            }
            if (sparseIntArray.get(iKeyAt) > i2) {
                i2 = sparseIntArray.get(iKeyAt);
                i = iKeyAt;
            }
        }
        setArrowMode(i);
    }

    public int getArrowMode() {
        return this.mArrowMode;
    }

    public void setArrowMode(int i) {
        this.mArrowMode = i;
        updateArrowDrawable(i);
    }

    private void updateArrowDrawable(int i) {
        int i2 = this.mRtlMode;
        boolean z = true;
        if (i2 != 1 && (i2 != 2 || !ViewUtils.isLayoutRtl(this))) {
            z = false;
        }
        if (i == 32) {
            this.mArrow.setImageDrawable(z ? this.mArrowRight : this.mArrowLeft);
            return;
        }
        if (i != 64) {
            switch (i) {
                case 8:
                    this.mArrow.setImageDrawable(this.mTitleLayout.getVisibility() == 0 ? this.mArrowTopWithTitle : this.mArrowTop);
                    break;
                case 9:
                    this.mArrow.setImageDrawable(z ? this.mArrowTopRight : this.mArrowTopLeft);
                    break;
                case 10:
                    this.mArrow.setImageDrawable(z ? this.mArrowTopLeft : this.mArrowTopRight);
                    break;
                default:
                    switch (i) {
                        case 16:
                            this.mArrow.setImageDrawable(this.mArrowBottom);
                            break;
                        case 17:
                            this.mArrow.setImageDrawable(z ? this.mArrowBottomLeft : this.mArrowBottomRight);
                            break;
                        case 18:
                            this.mArrow.setImageDrawable(z ? this.mArrowBottomRight : this.mArrowBottomLeft);
                            break;
                    }
                    break;
            }
            return;
        }
        this.mArrow.setImageDrawable(z ? this.mArrowLeft : this.mArrowRight);
    }

    public void setAnchor(View view) {
        this.mAnchor = view;
    }

    public void setOffset(int i, int i2) {
        this.mOffsetX = i;
        this.mOffsetY = i2;
    }

    public void setArrowPopupWindow(ArrowPopupWindow arrowPopupWindow) {
        this.mArrowPopupWindow = arrowPopupWindow;
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.mTouchInterceptor = onTouchListener;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        Rect rect = this.mTmpRect;
        this.mContentFrameWrapper.getHitRect(rect);
        if (motionEvent.getAction() == 0 && !rect.contains(x, y)) {
            this.mArrowPopupWindow.dismiss(true);
            return true;
        }
        View.OnTouchListener onTouchListener = this.mTouchInterceptor;
        return onTouchListener != null && onTouchListener.onTouch(view, motionEvent);
    }

    public void enableShowingAnimation(boolean z) {
        this.mShowingAnimation = z;
    }

    class WrapperOnClickListener implements View.OnClickListener {
        public View.OnClickListener mOnClickListener;

        WrapperOnClickListener() {
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.mOnClickListener = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            ArrowPopupView.this.mArrowPopupWindow.dismiss(true);
        }
    }
}
