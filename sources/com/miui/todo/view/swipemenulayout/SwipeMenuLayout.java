package com.miui.todo.view.swipemenulayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.LiteUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import java.util.Locale;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class SwipeMenuLayout extends FrameLayout implements Controller {
    public static final int DEFAULT_SCROLLER_DURATION = 500;
    private static final String TAG = "SwipeMenuLayout";
    public static boolean sForbidSwipe = false;
    private View mContentView;
    private int mContentViewId;
    protected int mDefaultRadius;
    private int mDownRawX;
    private int mDownX;
    private int mDownY;
    private float mDragOffset;
    private boolean mDragging;
    private boolean mEnableScale;
    private IFolme mFolme;
    private boolean mLaseDragState;
    private boolean mLastMenuOpenStateOnComputeScrollEnd;
    private int mLastRawX;
    private int mLastRawY;
    private int mLastX;
    private int mLastY;
    private boolean mLeftAlwaysClose;
    private int mLeftViewId;
    private boolean mMovable;
    private float mOpenPercent;
    private int mOriginScrollX;
    private int mRadiusId;
    private boolean mRightAlwaysClose;
    private int mRightViewId;
    private int mScaledMaximumFlingVelocity;
    private int mScaledMinimumFlingVelocity;
    private int mScaledTouchSlop;
    private OverScroller mScroller;
    private int mScrollerDuration;
    private Horizontal mSwipeCurrentHorizontal;
    private LeftHorizontal mSwipeLeftHorizontal;
    private SimpleSwipeListener mSwipeListener;
    private RightHorizontal mSwipeRightHorizontal;
    private AnimState mTouchDownScale;
    private AnimState mTouchUpScale;
    private VelocityTracker mVelocityTracker;
    private boolean performLongClick;
    private boolean shouldResetSwipe;
    private boolean swipeDefaultMode;
    private boolean swipeEnable;

    public SwipeMenuLayout(Context context) {
        this(context, null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SwipeMenuLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLeftViewId = 0;
        this.mContentViewId = 0;
        this.mRightViewId = 0;
        this.mRadiusId = 0;
        this.mOpenPercent = 0.5f;
        this.mScrollerDuration = 500;
        this.swipeEnable = true;
        this.swipeDefaultMode = false;
        this.mLeftAlwaysClose = false;
        this.mRightAlwaysClose = false;
        this.mOriginScrollX = 0;
        this.mLaseDragState = false;
        this.mFolme = null;
        this.mTouchDownScale = new AnimState("touchDownScale");
        this.mTouchUpScale = new AnimState("touchUpScale").add(ViewProperty.SCALE_X, 1.0d).add(ViewProperty.SCALE_Y, 1.0d);
        this.performLongClick = false;
        this.mMovable = true;
        this.mEnableScale = true;
        this.mLastMenuOpenStateOnComputeScrollEnd = false;
        setClickable(true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwipeMenuLayout);
        this.mLeftViewId = typedArrayObtainStyledAttributes.getResourceId(4, this.mLeftViewId);
        this.mContentViewId = typedArrayObtainStyledAttributes.getResourceId(2, this.mContentViewId);
        this.mRightViewId = typedArrayObtainStyledAttributes.getResourceId(5, this.mRightViewId);
        this.mRadiusId = typedArrayObtainStyledAttributes.getResourceId(3, this.mRadiusId);
        typedArrayObtainStyledAttributes.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mScaledTouchSlop = 50;
        this.mScaledMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScaledMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mScroller = new OverScroller(getContext(), new FolmeScrollInterpolator());
        setWillNotDraw(false);
        this.mDefaultRadius = (int) getDimen(getContext(), this.mRadiusId);
        if (LiteUtils.isLiteOrMiddle()) {
            return;
        }
        this.mFolme = Folme.useAt(this);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.mLeftViewId;
        if (i != 0 && this.mSwipeLeftHorizontal == null) {
            this.mSwipeLeftHorizontal = new LeftHorizontal(findViewById(i), this.mDefaultRadius);
        }
        int i2 = this.mRightViewId;
        if (i2 != 0 && this.mSwipeRightHorizontal == null) {
            this.mSwipeRightHorizontal = new RightHorizontal(findViewById(i2), this.mDefaultRadius);
        }
        int i3 = this.mContentViewId;
        if (i3 != 0 && this.mContentView == null) {
            this.mContentView = findViewById(i3);
            return;
        }
        TextView textView = new TextView(getContext());
        textView.setClickable(true);
        textView.setGravity(17);
        textView.setTextSize(16.0f);
        textView.setText("You may not have set the ContentView.");
        this.mContentView = textView;
        addView(textView);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IFolme iFolme = this.mFolme;
        if (iFolme != null) {
            iFolme.state().to(this.mTouchUpScale, new AnimConfig[0]);
        }
    }

    public void setScaleEnable(boolean z) {
        this.mEnableScale = z;
        if (z) {
            if (LiteUtils.isLiteOrMiddle()) {
                return;
            }
            this.mFolme = Folme.useAt(this);
            return;
        }
        this.mFolme = null;
    }

    public void setSwipeEnable(boolean z) {
        this.swipeEnable = z;
    }

    public boolean isSwipeEnable() {
        return this.swipeEnable && !sForbidSwipe;
    }

    public void setMovable(boolean z) {
        this.mMovable = z;
    }

    public void setOpenPercent(float f) {
        this.mOpenPercent = f;
    }

    public float getOpenPercent() {
        return this.mOpenPercent;
    }

    public void setScrollerDuration(int i) {
        this.mScrollerDuration = i;
    }

    public void setSwipeListener(SimpleSwipeListener simpleSwipeListener) {
        this.mSwipeListener = simpleSwipeListener;
    }

    public void setHandReleaseDragMode(boolean z, boolean z2) {
        this.mLeftAlwaysClose = z;
        this.mRightAlwaysClose = z2;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (!isSwipeEnable()) {
            Logger.INSTANCE.d(TAG, "onInterceptTouchEvent/isSwipeEnable == flase");
            return zOnInterceptTouchEvent;
        }
        Logger.INSTANCE.d(TAG, "onInterceptTouchEvent/isSwipeEnable == true");
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownRawX = (int) motionEvent.getRawX();
            int x = (int) motionEvent.getX();
            this.mLastX = x;
            this.mDownX = x;
            int y = (int) motionEvent.getY();
            this.mLastY = y;
            this.mDownY = y;
            return false;
        }
        if (action == 1) {
            Horizontal horizontal = this.mSwipeCurrentHorizontal;
            boolean z = horizontal != null && horizontal.isClickOnContentView(getWidth(), motionEvent.getX());
            Logger.INSTANCE.d(TAG, "onInterceptTouchEvent/ACTION_UP - isMenuOpen: " + isMenuOpen() + " isClick: " + z);
            if (!isMenuOpen() || !z) {
                return false;
            }
            smoothCloseMenu();
            return true;
        }
        if (action != 2) {
            if (action != 3) {
                return zOnInterceptTouchEvent;
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            return false;
        }
        int rawX = (int) ((motionEvent.getRawX() * getScaleX()) - this.mDownRawX);
        int y2 = (int) ((motionEvent.getY() * getScaleY()) - this.mDownY);
        int iAbs = Math.abs(rawX);
        Logger.INSTANCE.d(TAG, "onInterceptTouchEvent/ACTION_MOVE - deltaX: " + iAbs + " disY: " + y2);
        return iAbs > this.mScaledTouchSlop && iAbs > Math.abs(y2);
    }

    @Override // android.view.View
    public boolean performLongClick() {
        Logger.INSTANCE.d(TAG, "performLongClick()");
        boolean zPerformLongClick = super.performLongClick();
        if (this.mFolme != null && isLongClickable()) {
            this.performLongClick = true;
        }
        return zPerformLongClick;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int actualMoveValue;
        boolean z2;
        boolean z3 = true;
        boolean z4 = false;
        if (!isSwipeEnable()) {
            Logger.INSTANCE.d(TAG, " isSwipeEnable = false performLongClick = " + this.performLongClick + StringUtils.SPACE + motionEvent.toString());
            if (this.mFolme != null) {
                if (motionEvent.getAction() == 0) {
                    this.mLastRawX = (int) motionEvent.getRawX();
                    this.mLastRawY = (int) motionEvent.getRawY();
                    this.mTouchDownScale.add(ViewProperty.SCALE_X, 0.9300000071525574d);
                    this.mTouchDownScale.add(ViewProperty.SCALE_Y, 0.9300000071525574d);
                    this.mFolme.state().to(this.mTouchDownScale, new AnimConfig[0]);
                    this.performLongClick = false;
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    this.performLongClick = false;
                    IFolme iFolme = this.mFolme;
                    if (iFolme != null) {
                        iFolme.state().to(this.mTouchUpScale, new AnimConfig[0]);
                    }
                } else {
                    int rawX = (int) (this.mLastRawX - motionEvent.getRawX());
                    int rawY = (int) (this.mLastRawY - motionEvent.getRawY());
                    if (this.mFolme != null && getScaleX() != 1.0f && (Math.abs(rawX) > this.mScaledTouchSlop || Math.abs(rawY) > this.mScaledTouchSlop)) {
                        this.mFolme.state().to(this.mTouchUpScale, new AnimConfig[0]);
                    }
                }
            }
            return super.onTouchEvent(motionEvent);
        }
        Logger.INSTANCE.d(TAG, " isSwipeEnable = true " + motionEvent.toString());
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mLastRawX = (int) motionEvent.getRawX();
            this.mLastRawY = (int) motionEvent.getRawY();
            this.mLastX = (int) motionEvent.getX();
            this.mLastY = (int) motionEvent.getY();
            if (isMenuOpen()) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (this.mFolme != null && !isMenuOpen()) {
                double dMin = Math.min((getHeight() / 80000.0f) + 0.93f, 1.0f);
                this.mTouchDownScale.add(ViewProperty.SCALE_X, dMin);
                this.mTouchDownScale.add(ViewProperty.SCALE_Y, dMin);
                this.mFolme.state().to(this.mTouchDownScale, new AnimConfig[0]);
            }
            this.mOriginScrollX = this.mContentView.getScrollX();
        } else if (action == 1) {
            int x = (int) (this.mDownX - motionEvent.getX());
            int rawX2 = (int) (this.mLastRawX - motionEvent.getRawX());
            int y = (int) (this.mDownY - motionEvent.getY());
            IFolme iFolme2 = this.mFolme;
            if (iFolme2 != null) {
                iFolme2.state().to(this.mTouchUpScale, new AnimConfig[0]);
            }
            this.mDragOffset = 0.0f;
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mScaledMaximumFlingVelocity);
            int xVelocity = (int) this.mVelocityTracker.getXVelocity();
            int iAbs = Math.abs(xVelocity);
            this.mOriginScrollX = 0;
            Logger.INSTANCE.i(TAG, "UP disX: " + x + " ,disRawX:  " + rawX2 + " ,disY: " + y + " ,velocity: " + iAbs + " ,velocityX: " + xVelocity + " ,mDragging: " + this.mDragging + " ,mScaledMinimumFlingVelocity:" + this.mScaledMinimumFlingVelocity);
            if (iAbs > this.mScaledMinimumFlingVelocity) {
                if (this.mSwipeCurrentHorizontal != null) {
                    getSwipeDuration(motionEvent, iAbs);
                    if (this.mSwipeCurrentHorizontal instanceof RightHorizontal) {
                        if (isRTL() ? this.mLeftAlwaysClose : this.mRightAlwaysClose) {
                            closeMenuForce(this.mScrollerDuration);
                        } else if (xVelocity < 200) {
                            if (this.mDragging) {
                                judgeOpenClose();
                            }
                        } else {
                            closeMenuNoForce(this.mScrollerDuration);
                        }
                    } else {
                        if (isRTL() ? this.mRightAlwaysClose : this.mLeftAlwaysClose) {
                            closeMenuForce(this.mScrollerDuration);
                        } else if (xVelocity > -200) {
                            if (this.mDragging) {
                                judgeOpenClose();
                            }
                        } else {
                            closeMenuNoForce(this.mScrollerDuration);
                        }
                    }
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            } else {
                Horizontal horizontal = this.mSwipeCurrentHorizontal;
                if (horizontal == null) {
                    z = false;
                } else if (horizontal instanceof RightHorizontal) {
                    z = isRTL() ? this.mLeftAlwaysClose : this.mRightAlwaysClose;
                } else {
                    z = isRTL() ? this.mRightAlwaysClose : this.mLeftAlwaysClose;
                }
                if (Math.abs(x) > this.mScaledTouchSlop || Math.abs(y) > this.mScaledTouchSlop || this.mDragging) {
                    if (!z) {
                        judgeOpenClose();
                    } else {
                        closeMenuForce(this.mScrollerDuration);
                    }
                }
            }
            this.mVelocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mDragging = false;
            if ((Math.abs(x) > this.mScaledTouchSlop && Math.abs(rawX2) > this.mScaledTouchSlop) || Math.abs(y) > this.mScaledTouchSlop || isLeftMenuOpen() || isRightMenuOpen()) {
                motionEvent.setAction(3);
                Logger.INSTANCE.d(TAG, "action up super.onTouchEvent(ev) + " + x);
                super.onTouchEvent(motionEvent);
                return true;
            }
        } else if (action == 2) {
            int rawX3 = (int) (this.mLastRawX - motionEvent.getRawX());
            int rawY2 = (int) (this.mLastRawY - motionEvent.getRawY());
            int x2 = (int) (this.mLastX - motionEvent.getX());
            int y2 = (int) (this.mLastY - motionEvent.getY());
            if (this.mFolme != null && getScaleX() != 1.0f && (Math.abs(rawX3) > this.mScaledTouchSlop || Math.abs(rawY2) > this.mScaledTouchSlop)) {
                this.mFolme.state().to(this.mTouchUpScale, new AnimConfig[0]);
            }
            getCurrentHorizontal(x2);
            boolean z5 = this.mSwipeCurrentHorizontal instanceof LeftHorizontal;
            Logger.INSTANCE.d(TAG, "mDragging：" + this.mDragging + " ,Math.abs(disRawX) + " + Math.abs(rawX3) + " ,Math.abs(disX) + " + Math.abs(x2) + " ,mScaledTouchSlop :" + this.mScaledTouchSlop + " , Math.abs(disY): " + Math.abs(y2) + " , Math.abs(disRawY): " + Math.abs(rawY2));
            Logger.INSTANCE.d(TAG, "isMenuOpen：" + isMenuOpen() + " ,superLite: " + LiteUtils.isSuperLite());
            if ((!this.mDragging && Math.abs(rawX3) > this.mScaledTouchSlop && Math.abs(rawX3) > Math.abs(y2)) || isMenuOpen()) {
                this.mDragging = true;
                this.mDragOffset = 0.0f;
                motionEvent.setAction(3);
                Logger.INSTANCE.d(TAG, " swipe start, requestDisallowInterceptTouchEvent");
                SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
                if (simpleSwipeListener != null) {
                    simpleSwipeListener.onStart(z5);
                }
            }
            if (this.mDragging) {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                if (this.swipeDefaultMode) {
                    this.mDragOffset = (getScrollX() * 1.0f) / this.mSwipeCurrentHorizontal.getMenuWidth();
                    scrollBy(x2, 0);
                } else {
                    int scrollX = this.mContentView.getScrollX();
                    int i = this.mDefaultRadius;
                    if ((z5 && x2 <= 0) || (!z5 && x2 >= 0)) {
                        z3 = false;
                    }
                    int menuMaxWidth = this.mSwipeCurrentHorizontal.getMenuMaxWidth() - i;
                    if (z5) {
                        menuMaxWidth = -menuMaxWidth;
                    }
                    if (this.mLaseDragState != z3) {
                        this.mLaseDragState = z3;
                        this.mOriginScrollX = scrollX;
                    }
                    float scrollX2 = (this.mContentView.getScrollX() * 1.0f) / menuMaxWidth;
                    this.mDragOffset = scrollX2;
                    int i2 = this.mOriginScrollX + x2;
                    this.mOriginScrollX = i2;
                    if (!z3) {
                        int i3 = z5 ? menuMaxWidth - i2 : i2 - menuMaxWidth;
                        if (i3 <= 0) {
                            menuMaxWidth = i2;
                        }
                        if (i3 < 0) {
                            i3 = 0;
                        }
                        actualMoveValue = (int) getActualMoveValue(i3);
                        if (z5) {
                            actualMoveValue = -actualMoveValue;
                        }
                    } else {
                        int i4 = z5 ? i2 : -i2;
                        if (i4 >= 0) {
                            i2 = 0;
                        }
                        if (i4 < 0) {
                            i4 = 0;
                        }
                        int actualMoveValue2 = (int) getActualMoveValue(i4);
                        if (!z5) {
                            actualMoveValue2 = -actualMoveValue2;
                        }
                        menuMaxWidth = i2;
                        actualMoveValue = actualMoveValue2;
                    }
                    this.mContentView.scrollTo(actualMoveValue + menuMaxWidth, 0);
                    updateCurrentMenuViewStateOnScroll(scrollX2);
                }
                if (this.mSwipeListener != null) {
                    if (isRTL()) {
                        z5 = !z5;
                    }
                    float fAbs = Math.abs(this.mDragOffset);
                    this.mSwipeListener.onUpdate(this, fAbs <= 1.0f ? fAbs : 1.0f, z5);
                }
                this.mLastX = (int) motionEvent.getX();
                this.mLastY = (int) motionEvent.getY();
                this.shouldResetSwipe = false;
            }
        } else if (action == 3) {
            Logger.INSTANCE.d(TAG, "onTouchEvent: performLongClick = " + this.performLongClick + " movable = " + this.mMovable + " scaleX = " + getScaleX());
            if (this.mFolme != null && (!this.performLongClick || !this.mMovable || getScaleX() != 1.0f)) {
                this.mFolme.state().to(this.mTouchUpScale, new AnimConfig[0]);
            }
            this.performLongClick = false;
            this.mDragging = false;
            this.mDragOffset = 0.0f;
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            } else {
                Horizontal horizontal2 = this.mSwipeCurrentHorizontal;
                if (horizontal2 != null) {
                    if (horizontal2 instanceof RightHorizontal) {
                        z2 = isRTL() ? this.mLeftAlwaysClose : this.mRightAlwaysClose;
                    } else {
                        z2 = isRTL() ? this.mRightAlwaysClose : this.mLeftAlwaysClose;
                    }
                    z4 = z2;
                }
                if (!z4) {
                    judgeOpenClose();
                } else {
                    closeMenuForce(this.mScrollerDuration);
                }
            }
        }
        Logger.INSTANCE.d(TAG, "onTouchEvent return：" + super.onTouchEvent(motionEvent));
        return super.onTouchEvent(motionEvent);
    }

    private void getCurrentHorizontal(int i) {
        if (this.mSwipeCurrentHorizontal == null || this.shouldResetSwipe) {
            if (i < 0) {
                LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
                if (leftHorizontal != null) {
                    this.mSwipeCurrentHorizontal = leftHorizontal;
                    return;
                } else {
                    this.mSwipeCurrentHorizontal = this.mSwipeRightHorizontal;
                    return;
                }
            }
            RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
            if (rightHorizontal != null) {
                this.mSwipeCurrentHorizontal = rightHorizontal;
            } else {
                this.mSwipeCurrentHorizontal = this.mSwipeLeftHorizontal;
            }
        }
    }

    private float getActualMoveValue(float f) {
        float width = this.mContentView.findViewById(R.id.item_content).getWidth();
        float fMin = Math.min(f / width, 1.0f);
        float f2 = 13.0f * fMin;
        float f3 = f2 * fMin;
        return ((((fMin * f3) / 75.0f) - (f3 / 25.0f)) + (f2 / 25.0f)) * width;
    }

    private void updateCurrentMenuViewStateOnScroll(float f) {
        Horizontal horizontal = this.mSwipeCurrentHorizontal;
        if (horizontal == null) {
            return;
        }
        if (f > 1.0f) {
            float f2 = f - 1.0f;
            Folme.useAt(horizontal.getMenuView()).state().to(ViewProperty.TRANSLATION_X, Float.valueOf((-(f2 >= 0.0f ? f2 : 0.0f)) * this.mContentView.getScrollX() * 0.25f));
            if (this.mFolme != null) {
                Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.SCALE_X, Float.valueOf(0.9f));
                Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.SCALE_Y, Float.valueOf(0.9f));
                Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.ALPHA, Float.valueOf(0.81f));
                return;
            }
            return;
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        Folme.useAt(horizontal.getMenuView()).state().to(ViewProperty.TRANSLATION_X, Float.valueOf(0.0f));
        if (this.mFolme != null) {
            float f3 = (0.2f * f) + 0.7f;
            Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.SCALE_X, Float.valueOf(f3));
            Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.SCALE_Y, Float.valueOf(f3));
            Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.ALPHA, Float.valueOf(0.80999994f * f * f));
        }
    }

    private void updateCurrentMenuViewStateAfterScroll(boolean z, boolean z2) {
        Horizontal horizontal = this.mSwipeCurrentHorizontal;
        if (horizontal == null) {
            return;
        }
        Folme.useAt(horizontal.getMenuView()).state().to(ViewProperty.TRANSLATION_X, Float.valueOf(0.0f));
        if (this.mFolme != null) {
            float f = z ? 1.0f : 0.7f;
            float f2 = z ? 1.0f : 0.7f;
            Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.SCALE_X, Float.valueOf(f));
            Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.SCALE_Y, Float.valueOf(f));
            Folme.useAt(this.mSwipeCurrentHorizontal.getMenuView()).state().to(ViewProperty.ALPHA, Float.valueOf(f2));
        }
        if (z2) {
            HapticFeedbackTool.getInstance().performTouchUp(this.mSwipeCurrentHorizontal.getMenuView());
        }
    }

    private int getSwipeDuration(MotionEvent motionEvent, int i) {
        int iAbs;
        int x = (int) (motionEvent.getX() - getMyScrollX());
        int menuWidth = this.mSwipeCurrentHorizontal.getMenuWidth();
        int i2 = menuWidth / 2;
        float f = menuWidth;
        float f2 = i2;
        float fDistanceInfluenceForSnapDuration = f2 + (distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(x) * 1.0f) / f)) * f2);
        if (i > 0) {
            iAbs = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / i) * 1000.0f) * 4;
        } else {
            iAbs = (int) (((Math.abs(x) / f) + 1.0f) * 100.0f);
        }
        return Math.min(iAbs, this.mScrollerDuration);
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((float) (((double) (f - 0.5f)) * 0.4712389167638204d));
    }

    private void judgeOpenClose() {
        Logger.INSTANCE.d(TAG, "judgeOpenClose");
        judgeOpenClose(this.mScrollerDuration);
    }

    private void judgeOpenClose(int i) {
        if (this.mSwipeCurrentHorizontal != null) {
            int myScrollX = getMyScrollX();
            Horizontal horizontal = this.mSwipeCurrentHorizontal;
            if (!(horizontal instanceof RightHorizontal) ? myScrollX <= (-(horizontal.getMenuView().getWidth() * this.mOpenPercent)) : myScrollX >= horizontal.getMenuView().getWidth() * this.mOpenPercent) {
                openMenuAfterJudge(i);
            } else {
                closeMenuAfterJudge(i);
            }
        }
    }

    private void judgeOpenClose(int i, int i2) {
        if (this.mSwipeCurrentHorizontal != null) {
            int myScrollX = getMyScrollX();
            Horizontal horizontal = this.mSwipeCurrentHorizontal;
            if (!(horizontal instanceof RightHorizontal) ? myScrollX <= (-(horizontal.getMenuView().getWidth() * this.mOpenPercent)) : myScrollX >= horizontal.getMenuView().getWidth() * this.mOpenPercent) {
                openMenuAfterJudge();
            } else {
                closeMenuAfterJudge();
            }
        }
    }

    public void closeMenuByTouchDown() {
        Horizontal horizontal;
        updateCurrentMenuViewStateAfterScroll(false, false);
        smoothCloseMenu();
        SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
        if (simpleSwipeListener == null || (horizontal = this.mSwipeCurrentHorizontal) == null) {
            return;
        }
        this.mLastMenuOpenStateOnComputeScrollEnd = false;
        simpleSwipeListener.onMenuClose(horizontal instanceof LeftHorizontal);
    }

    private void openMenuAfterJudge() {
        openMenuAfterJudge(this.mScrollerDuration);
    }

    private void openMenuAfterJudge(int i) {
        updateCurrentMenuViewStateAfterScroll(true, false);
        smoothOpenMenu(i);
    }

    private void closeMenuAfterJudge() {
        closeMenuAfterJudge(this.mScrollerDuration);
    }

    private void closeMenuAfterJudge(int i) {
        Horizontal horizontal;
        updateCurrentMenuViewStateAfterScroll(false, false);
        smoothCloseMenu(i);
        SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
        if (simpleSwipeListener == null || (horizontal = this.mSwipeCurrentHorizontal) == null) {
            return;
        }
        this.mLastMenuOpenStateOnComputeScrollEnd = false;
        simpleSwipeListener.onMenuClose(horizontal instanceof LeftHorizontal);
    }

    public void closeMenuByTouchDown(int i) {
        Horizontal horizontal;
        updateCurrentMenuViewStateAfterScroll(false, false);
        smoothCloseMenu(i);
        SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
        if (simpleSwipeListener == null || (horizontal = this.mSwipeCurrentHorizontal) == null) {
            return;
        }
        this.mLastMenuOpenStateOnComputeScrollEnd = false;
        simpleSwipeListener.onMenuClose(horizontal instanceof LeftHorizontal);
    }

    private void openMenuNoForce(int i) {
        Horizontal horizontal;
        updateCurrentMenuViewStateAfterScroll(true, false);
        smoothOpenMenu(i);
        SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
        if (simpleSwipeListener == null || (horizontal = this.mSwipeCurrentHorizontal) == null) {
            return;
        }
        simpleSwipeListener.onMenuOpen(horizontal instanceof LeftHorizontal);
    }

    private void closeMenuNoForce(int i) {
        Horizontal horizontal;
        Logger.INSTANCE.i(TAG, "closeMenuNoForce");
        updateCurrentMenuViewStateAfterScroll(false, false);
        smoothCloseMenu(i);
        SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
        if (simpleSwipeListener == null || (horizontal = this.mSwipeCurrentHorizontal) == null) {
            return;
        }
        this.mLastMenuOpenStateOnComputeScrollEnd = false;
        simpleSwipeListener.onMenuClose(horizontal instanceof LeftHorizontal);
    }

    private void closeMenuForce(int i) {
        Logger.INSTANCE.i(TAG, "closeMenuForce");
        updateCurrentMenuViewStateAfterScroll(false, false);
        smoothCloseMenu(i);
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        Horizontal horizontal = this.mSwipeCurrentHorizontal;
        if (horizontal == null) {
            super.scrollTo(i, i2);
            return;
        }
        Horizontal.Checker checkerCheckXY = horizontal.checkXY(i, i2);
        this.shouldResetSwipe = checkerCheckXY.shouldResetSwipe;
        if (checkerCheckXY.x != getScrollX()) {
            super.scrollTo(checkerCheckXY.x, checkerCheckXY.y);
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mSwipeCurrentHorizontal != null) {
            if (this.mScroller.computeScrollOffset()) {
                if (this.mSwipeCurrentHorizontal instanceof RightHorizontal) {
                    if (this.swipeDefaultMode) {
                        scrollTo(Math.abs(this.mScroller.getCurrX()), 0);
                    } else {
                        View view = this.mContentView;
                        if (view != null) {
                            view.scrollTo(this.mScroller.getCurrX(), 0);
                        }
                    }
                } else if (this.swipeDefaultMode) {
                    scrollTo(-Math.abs(this.mScroller.getCurrX()), 0);
                } else {
                    View view2 = this.mContentView;
                    if (view2 != null) {
                        view2.scrollTo(this.mScroller.getCurrX(), 0);
                    }
                }
                invalidate();
                if (this.mScroller.getFinalX() == 0 || this.mLastMenuOpenStateOnComputeScrollEnd || !isMenuOpen() || this.mDragging) {
                    return;
                }
                Logger.INSTANCE.i(TAG, " swipe mScroller.computeScrollOffset() onMenuOpenAfterScrollEnd");
                onMenuOpenAfterScrollEnd();
                this.mLastMenuOpenStateOnComputeScrollEnd = true;
                return;
            }
            if (this.mDragging) {
                return;
            }
            this.mLastMenuOpenStateOnComputeScrollEnd = isMenuOpen();
        }
    }

    public boolean hasLeftMenu() {
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        return leftHorizontal != null && leftHorizontal.canSwipe();
    }

    public boolean hasRightMenu() {
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        return rightHorizontal != null && rightHorizontal.canSwipe();
    }

    private int getMyScrollX() {
        if (this.swipeDefaultMode) {
            return getScrollX();
        }
        View view = this.mContentView;
        if (view != null) {
            return view.getScrollX();
        }
        Logger.INSTANCE.e(TAG, "getMyScrollX mContentView = null");
        return 0;
    }

    private void onMenuOpenAfterScrollEnd() {
        View childAt = ((ViewGroup) this.mSwipeCurrentHorizontal.getMenuView()).getChildAt(0);
        if (this.mFolme != null) {
            Folme.useAt(childAt).touch().handleTouchOf(childAt, new AnimConfig[0]);
        }
        SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
        if (simpleSwipeListener != null) {
            simpleSwipeListener.onMenuOpen(this.mSwipeCurrentHorizontal instanceof LeftHorizontal);
        }
        HapticFeedbackTool.getInstance().performTouchUp(this.mSwipeCurrentHorizontal.getMenuView());
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isMenuOpen() {
        return isLeftMenuOpen() || isRightMenuOpen();
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isLeftMenuOpen() {
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        return leftHorizontal != null && leftHorizontal.isMenuOpen(getMyScrollX() - this.mDefaultRadius);
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isRightMenuOpen() {
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        return rightHorizontal != null && rightHorizontal.isMenuOpen(getMyScrollX() + this.mDefaultRadius);
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isCompleteOpen() {
        return isLeftCompleteOpen() || isRightMenuOpen();
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isLeftCompleteOpen() {
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        return (leftHorizontal == null || leftHorizontal.isCompleteClose(getMyScrollX())) ? false : true;
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isRightCompleteOpen() {
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        return (rightHorizontal == null || rightHorizontal.isCompleteClose(getMyScrollX())) ? false : true;
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isMenuOpenNotEqual() {
        return isLeftMenuOpenNotEqual() || isRightMenuOpenNotEqual();
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isLeftMenuOpenNotEqual() {
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        return leftHorizontal != null && leftHorizontal.isMenuOpenNotEqual(getMyScrollX());
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public boolean isRightMenuOpenNotEqual() {
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        return rightHorizontal != null && rightHorizontal.isMenuOpenNotEqual(getMyScrollX());
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothOpenMenu() {
        smoothOpenMenu(this.mScrollerDuration);
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothOpenLeftMenu() {
        smoothOpenLeftMenu(this.mScrollerDuration);
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothOpenRightMenu() {
        smoothOpenRightMenu(this.mScrollerDuration);
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothOpenLeftMenu(int i) {
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        if (leftHorizontal != null) {
            this.mSwipeCurrentHorizontal = leftHorizontal;
            smoothOpenMenu(i);
        }
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothOpenRightMenu(int i) {
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        if (rightHorizontal != null) {
            this.mSwipeCurrentHorizontal = rightHorizontal;
            smoothOpenMenu(i);
        }
    }

    private void smoothOpenMenu(int i) {
        Logger.INSTANCE.d(TAG, "smoothOpenMenu duration = " + i + " ,mSwipeCurrentHorizontal: " + this.mSwipeCurrentHorizontal);
        if (this.mSwipeCurrentHorizontal != null) {
            if (!this.mScroller.isFinished()) {
                Logger.INSTANCE.d(TAG, "smoothOpenMenu abortAnimation");
                this.mScroller.abortAnimation();
            }
            this.mSwipeCurrentHorizontal.autoOpenMenu(this.mScroller, getMyScrollX(), i);
            invalidate();
        }
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothCloseMenu() {
        smoothCloseMenu(this.mScrollerDuration);
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothCloseLeftMenu() {
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        if (leftHorizontal != null) {
            this.mSwipeCurrentHorizontal = leftHorizontal;
            smoothCloseMenu();
        }
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothCloseRightMenu() {
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        if (rightHorizontal != null) {
            this.mSwipeCurrentHorizontal = rightHorizontal;
            smoothCloseMenu();
        }
    }

    @Override // com.miui.todo.view.swipemenulayout.Controller
    public void smoothCloseMenu(int i) {
        Logger.INSTANCE.d(TAG, "smoothCloseMenu mSwipeCurrentHorizontal: " + this.mSwipeCurrentHorizontal);
        if (this.mSwipeCurrentHorizontal != null) {
            if (!this.mScroller.isFinished()) {
                Logger.INSTANCE.d(TAG, "smoothCloseMenu abortAnimation");
                this.mScroller.abortAnimation();
            }
            View childAt = ((ViewGroup) this.mSwipeCurrentHorizontal.getMenuView()).getChildAt(0);
            if (this.mFolme != null) {
                Logger.INSTANCE.d(TAG, "smoothCloseMenu ignoreTouchOf");
                Folme.useAt(childAt).touch().ignoreTouchOf(childAt);
            }
            this.mSwipeCurrentHorizontal.autoCloseMenu(this.mScroller, getMyScrollX(), i);
            invalidate();
            SimpleSwipeListener simpleSwipeListener = this.mSwipeListener;
            if (simpleSwipeListener != null) {
                simpleSwipeListener.onReset(this.mSwipeCurrentHorizontal instanceof LeftHorizontal);
            }
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int scrollX = this.mContentView.getScrollX();
        getDimen(getContext(), this.mRadiusId);
        this.mContentView.findViewById(R.id.item_content).getClipBounds();
        if (this.mSwipeCurrentHorizontal != null) {
            int iAbs = Math.abs(scrollX);
            int menuMaxWidth = this.mSwipeCurrentHorizontal.getMenuMaxWidth();
            int i = menuMaxWidth - iAbs;
            Rect clipBounds = this.mSwipeCurrentHorizontal.getMenuView().getClipBounds();
            if (clipBounds != null) {
                Horizontal horizontal = this.mSwipeCurrentHorizontal;
                if (horizontal instanceof LeftHorizontal) {
                    clipBounds.set(0, 0, menuMaxWidth - i, horizontal.getMenuView().getHeight());
                    this.mSwipeCurrentHorizontal.getMenuView().setClipBounds(clipBounds);
                } else {
                    clipBounds.set(i, 0, menuMaxWidth, horizontal.getMenuView().getHeight());
                }
            }
            this.mSwipeCurrentHorizontal.getMenuView().setClipBounds(clipBounds);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeightAndState;
        int paddingLeft = getPaddingLeft();
        int width = getWidth();
        View view = this.mContentView;
        if (view != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            int measuredWidthAndState = this.mContentView.getMeasuredWidthAndState();
            if (measuredWidthAndState <= width) {
                width = measuredWidthAndState;
            }
            measuredHeightAndState = this.mContentView.getMeasuredHeightAndState();
            int paddingTop = getPaddingTop() + layoutParams.topMargin;
            this.mContentView.layout(paddingLeft, paddingTop, paddingLeft + width, paddingTop + measuredHeightAndState);
            Rect rect = new Rect();
            View viewFindViewById = this.mContentView.findViewById(R.id.item_content);
            rect.set(0, -200, viewFindViewById.getMeasuredWidthAndState(), viewFindViewById.getMeasuredHeightAndState() + 200);
            viewFindViewById.setClipBounds(rect);
        } else {
            width = 0;
            measuredHeightAndState = 0;
        }
        LeftHorizontal leftHorizontal = this.mSwipeLeftHorizontal;
        if (leftHorizontal != null) {
            View menuView = leftHorizontal.getMenuView();
            int measuredWidthAndState2 = menuView.getMeasuredWidthAndState();
            int measuredHeightAndState2 = menuView.getMeasuredHeightAndState();
            this.mSwipeLeftHorizontal.saveMenuMaxWidth(measuredWidthAndState2);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) menuView.getLayoutParams();
            int paddingTop2 = getPaddingTop() + layoutParams2.topMargin;
            int marginStart = layoutParams2.getMarginStart();
            menuView.measure(View.MeasureSpec.makeMeasureSpec(measuredWidthAndState2, 1073741824), View.MeasureSpec.makeMeasureSpec((measuredHeightAndState - layoutParams2.topMargin) - layoutParams2.bottomMargin, 1073741824));
            if (this.swipeDefaultMode) {
                menuView.layout(-measuredWidthAndState2, paddingTop2, 0, ((measuredHeightAndState + paddingTop2) - layoutParams2.topMargin) - layoutParams2.bottomMargin);
            } else {
                menuView.layout(marginStart, paddingTop2, measuredWidthAndState2 + marginStart, ((measuredHeightAndState + paddingTop2) - layoutParams2.topMargin) - layoutParams2.bottomMargin);
                Rect rect2 = new Rect();
                rect2.set(0, 0, 0, measuredHeightAndState2);
                menuView.setClipBounds(rect2);
            }
        }
        RightHorizontal rightHorizontal = this.mSwipeRightHorizontal;
        if (rightHorizontal != null) {
            View menuView2 = rightHorizontal.getMenuView();
            int measuredWidthAndState3 = menuView2.getMeasuredWidthAndState();
            int measuredHeightAndState3 = menuView2.getMeasuredHeightAndState();
            this.mSwipeRightHorizontal.saveMenuMaxWidth(measuredWidthAndState3);
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) menuView2.getLayoutParams();
            int paddingTop3 = getPaddingTop() + layoutParams3.topMargin;
            int marginEnd = layoutParams3.getMarginEnd();
            int measuredWidthAndState4 = getMeasuredWidthAndState();
            if (this.swipeDefaultMode) {
                menuView2.layout(measuredWidthAndState4, paddingTop3, measuredWidthAndState3 + measuredWidthAndState4, measuredHeightAndState3 + paddingTop3);
                return;
            }
            menuView2.layout((width - measuredWidthAndState3) - marginEnd, paddingTop3, width - marginEnd, paddingTop3 + measuredHeightAndState3);
            Rect rect3 = new Rect();
            rect3.set(0, 0, 0, measuredHeightAndState3);
            menuView2.setClipBounds(rect3);
        }
    }

    private boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    private void updateViewCornerRadius(View view, float[] fArr) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setCornerRadii(fArr);
        view.setBackground(gradientDrawable);
    }

    public int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private float getDimen(Context context, int i) {
        return context.getResources().getDimension(i);
    }
}
