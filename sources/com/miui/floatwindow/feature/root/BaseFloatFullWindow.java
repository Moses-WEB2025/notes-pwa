package com.miui.floatwindow.feature.root;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.ReflectUtil;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.PhysicBasedInterpolator;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.floatwindow.feature.note.FwNoteListPresenter;
import com.miui.floatwindow.feature.todo.FwTodoListPresenter;
import com.miui.floatwindow.feature.todo.FwTodoRecyclerView;
import com.miui.floatwindow.frame.HomeStatusController;
import com.miui.floatwindow.frame.HomeStatusListener;
import com.miui.floatwindow.frame.IFloatWindowView;
import com.miui.floatwindow.utils.DockUtils;
import com.miui.floatwindow.utils.GestureBroadcastUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.tool.PreferenceUtils;
import defpackage.R2;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseFloatFullWindow extends LinearLayout {
    public static final String FORCE_FSG_NAV_BAR = "force_fsg_nav_bar";
    private static final int SKIP_GLOBAL_SIDE_BAR_INJECT_MOTION_EVENT = 32;
    static final String TAG = "BaseFloatFullWindow";
    private long actionDownTime;
    protected int guideX;
    protected int guideY;
    protected int guideYInDockMode;
    private boolean isFirstMove;
    private ValueAnimator mAutoFullGuideAnimator;
    private float mBlurRatio;
    private SettingsContentObserver mContentOb;
    protected Context mContext;
    protected int mDisplayHeight;
    private int mEndMargin;
    protected LinearLayout mExcerptDefault;
    protected FrameLayout mExcerptView;
    protected LinearLayout mExcerptedLayout;
    protected View mFloatGuideView;
    protected int mFloatStatus;
    private final WindowManager.LayoutParams mFloatWindowParams;
    IFloatWindowView mFloatWindowView;
    protected View mFullFloatView;
    protected FullGuideTransitionAnimation mFullGuideTransitionAnimation;
    FwNoteListPresenter mFwNotePresenter;
    FwTodoListPresenter mFwTodoPresenter;
    protected View mGuideLineView;
    private boolean mGuideMoveIntercepted;
    protected int mGuideType;
    private final HomeStatusController mHomeStatusController;
    protected boolean mInDockMode;
    private boolean mInGuideAnim;
    private final IntEvaluator mIntEvaluator;
    private final boolean mIsDockSupport;
    private boolean mIsDragFullView;
    private boolean mIsFullViewAnim;
    private boolean mIsFullWindowGesture;
    protected boolean mIsNightMode;
    protected boolean mIsOldToolboxSupportQuickNote;
    private boolean mIsOutIntercept;
    protected boolean mIsSupportMiuiFlags;
    final int mMovedToFullMax;
    final int mMovedToFullMin;
    public int mNewOrientation;
    protected int mOrientation;
    protected PhysicBasedInterpolator mPhysicBasedInterpolator;
    private FrameLayout mRootContentLayout;
    View mRootView;
    private final FwTodoRecyclerView mRvTodo;
    private final Rect mRvTodoRect;
    private boolean mStartFullViewAnim;
    private ImageView mStopExcerptIcon;
    protected View mStubView;
    private ValueAnimator mToGuideAnimator;
    private int mTopMargin;
    protected WebView mWebView;
    private final ValueAnimator mWindowResetAnimator;
    private int moveType;
    protected WindowManager windowManager;
    private float xDownInScreen;
    private float xInView;
    private float xMoved;
    private float xMovedAfterAnim;
    private float xMovedBeforeAnim;
    private float yDownInScreen;
    private float yInView;
    private float yMoved;

    static void markStatForFullFloat(boolean z) {
    }

    /* JADX INFO: renamed from: getExcerptId */
    abstract long getMExcerptId();

    protected abstract void initWebview();

    abstract void onActiveFloatWindow(boolean z);

    protected abstract void onFullWindowShow();

    abstract void startContinuousExcerpt();

    abstract void stopContinuousExcerpt();

    abstract void updateFloatFullView();

    static class FloatWindowType {
        static final int FULL_LAYOUT = 2;
        static final int FULL_WINDOW = 1;
        static final int GUIDE = 0;

        FloatWindowType() {
        }
    }

    private static class MoveType {
        static final int FULL = 1;
        static final int GUIDE = 0;

        private MoveType() {
        }
    }

    protected static class GuideType {
        static final int EXCERPT = 1;
        static final int QUICK_NOTE = 0;

        protected GuideType() {
        }
    }

    protected static class ExcerptFloatType {
        static final int DEFAULT = 0;
        static final int IMG = 2;
        static final int SINGLE_LINK = 3;
        static final int TEXT = 1;

        protected ExcerptFloatType() {
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0165  */
    /* JADX WARN: Code duplicated, block: B:24:0x0173  */
    /* JADX WARN: Code duplicated, block: B:27:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:35:0x0230  */
    /* JADX WARN: Code duplicated, block: B:36:0x0234  */
    /* JADX WARN: Code duplicated, block: B:38:0x023a  */
    public BaseFloatFullWindow(Context context, boolean[] zArr) {
        int iIntValue;
        super(context);
        this.mPhysicBasedInterpolator = new PhysicBasedInterpolator.Builder().setDamping(0.95f).setResponse(0.8f).build();
        this.mIntEvaluator = new IntEvaluator();
        this.mWindowResetAnimator = new ValueAnimator();
        this.mToGuideAnimator = null;
        this.mAutoFullGuideAnimator = null;
        this.mRvTodoRect = new Rect();
        this.mDisplayHeight = 0;
        this.mNewOrientation = 1;
        this.mIsFullViewAnim = false;
        this.mStartFullViewAnim = false;
        this.mIsDragFullView = false;
        this.guideYInDockMode = 0;
        this.mMovedToFullMax = 350;
        this.mMovedToFullMin = 40;
        this.mIsFullWindowGesture = false;
        this.mFullGuideTransitionAnimation = new FullGuideTransitionAnimation();
        this.mIsSupportMiuiFlags = true;
        this.mGuideMoveIntercepted = false;
        Logger.INSTANCE.d(TAG, "BaseFloatFullWindow() new ");
        this.mInDockMode = zArr[0];
        this.mIsOldToolboxSupportQuickNote = zArr[1];
        this.mIsDockSupport = zArr[2];
        initBaseConfig(context);
        updateGuideViewPositionYInDockMode();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mFloatWindowParams = layoutParams;
        this.windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (UIUtils.isNightMode(this.mContext)) {
            this.mContext.setTheme(R.style.NoteTheme_Dark);
        } else {
            this.mContext.setTheme(R.style.NoteTheme_Light);
        }
        SetScreenStatus();
        try {
            if (!UIUtils.isAboveAndroidS()) {
                try {
                    iIntValue = ((Integer) ReflectUtil.INSTANCE.getStaticObjectField(WindowManager.LayoutParams.class, "TYPE_TRUSTED_APPLICATION_OVERLAY", Integer.TYPE)).intValue();
                } catch (Throwable th) {
                    Logger.INSTANCE.d(TAG, "error in " + th);
                    iIntValue = R2.attr.preferenceCheckableItemSetMaskPaddingEnd;
                }
                this.mFloatWindowParams.type = iIntValue;
                this.mFloatWindowParams.format = 1;
                this.mFloatWindowParams.gravity = 8388659;
                refreshGuideWindowParams();
                this.mFloatWindowParams.getClass().getField("privateFlags").set(this.mFloatWindowParams, Integer.valueOf(((Integer) this.mFloatWindowParams.getClass().getField("privateFlags").get(this.mFloatWindowParams)).intValue() | 64));
                requestRedirect(true);
                this.mIsNightMode = DisplayUtils.isNightMode();
                Logger.INSTANCE.d(TAG, "BaseFloatFullWindow mRootView");
                if (isPadUiMode()) {
                    this.mRootView = LayoutInflater.from(context).inflate(R.layout.pad_app_float_layout, this);
                } else {
                    this.mRootView = LayoutInflater.from(context).inflate(R.layout.app_float_layout, this);
                }
                this.mRootContentLayout = (FrameLayout) this.mRootView.findViewById(R.id.float_view);
                this.mFloatGuideView = this.mRootView.findViewById(R.id.float_guide_view);
                FloatFullWindowAccessiabilityDelegate floatFullWindowAccessiabilityDelegate = new FloatFullWindowAccessiabilityDelegate();
                floatFullWindowAccessiabilityDelegate.onInitializeAccessibilityNodeInfo(this.mFloatGuideView, this.mFloatGuideView.createAccessibilityNodeInfo());
                this.mFloatGuideView.setAccessibilityDelegate(floatFullWindowAccessiabilityDelegate);
                if (isFoldNarrowOrPhoneLandScreen()) {
                    this.mFloatGuideView.setVisibility(8);
                }
                initGuideView();
                this.mFullFloatView = this.mRootView.findViewById(R.id.full_float_view);
                if (!isPadUiMode() && RomUtils.isFoldDevice()) {
                    View view = this.mFullFloatView;
                    view.setPaddingRelative(view.getPaddingStart(), this.mTopMargin + UIUtils.dip2px(this.mContext, 12.36f), this.mFullFloatView.getPaddingEnd(), this.mFullFloatView.getPaddingBottom());
                }
                this.mRvTodo = (FwTodoRecyclerView) this.mRootView.findViewById(R.id.rv_todo);
                updateFloatFullView();
                HomeStatusController homeStatusController = new HomeStatusController(this.mContext);
                this.mHomeStatusController = homeStatusController;
                homeStatusController.setHomeStatusListener(new HomeStatusListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.1
                    @Override // com.miui.floatwindow.frame.HomeStatusListener
                    public void onHomeKey() {
                        if (BaseFloatFullWindow.this.mFloatStatus == 2) {
                            Logger.INSTANCE.d(BaseFloatFullWindow.TAG, "HomeKey back to guide");
                            BaseFloatFullWindow.this.startAutoFullGuideTransitionAnimation(0.0f, true);
                        }
                    }

                    @Override // com.miui.floatwindow.frame.HomeStatusListener
                    public void onRecentApps() {
                        if (BaseFloatFullWindow.this.mFloatStatus == 2) {
                            Logger.INSTANCE.d(BaseFloatFullWindow.TAG, "onRecentApps back to guide");
                            BaseFloatFullWindow.this.startAutoFullGuideTransitionAnimation(0.0f, true);
                        }
                    }
                });
                homeStatusController.startListen();
                PreferenceUtils.setIsFullWindow(this.mContext, false);
                this.mWindowResetAnimator.setInterpolator(this.mPhysicBasedInterpolator);
                this.mWindowResetAnimator.setDuration(300L);
                if (this.mInDockMode) {
                    changeGuideLineVisible(false);
                    return;
                }
                if (!isFoldNarrowOrPhoneLandScreen()) {
                    this.mFloatGuideView.setVisibility(0);
                }
                setEnabled(true);
            }
            try {
                ReflectUtil.INSTANCE.callObjectMethod2(layoutParams, "setTrustedOverlay", null, new Object[0]);
                Logger.INSTANCE.d(TAG, "setTrustedOverlay success: " + this);
            } catch (Throwable th2) {
                Logger.INSTANCE.e(TAG, "setTrustedOverlay failed, " + th2);
            }
            this.mFloatWindowParams.getClass().getField("privateFlags").set(this.mFloatWindowParams, Integer.valueOf(((Integer) this.mFloatWindowParams.getClass().getField("privateFlags").get(this.mFloatWindowParams)).intValue() | 64));
        } catch (Exception unused) {
            Logger.INSTANCE.e(TAG, "prohibit Window Animator failed");
        }
        iIntValue = R2.attr.preferenceCheckableItemSetMaskPaddingEnd;
        this.mFloatWindowParams.type = iIntValue;
        this.mFloatWindowParams.format = 1;
        this.mFloatWindowParams.gravity = 8388659;
        refreshGuideWindowParams();
        requestRedirect(true);
        this.mIsNightMode = DisplayUtils.isNightMode();
        Logger.INSTANCE.d(TAG, "BaseFloatFullWindow mRootView");
        if (isPadUiMode()) {
            this.mRootView = LayoutInflater.from(context).inflate(R.layout.pad_app_float_layout, this);
        } else {
            this.mRootView = LayoutInflater.from(context).inflate(R.layout.app_float_layout, this);
        }
        this.mRootContentLayout = (FrameLayout) this.mRootView.findViewById(R.id.float_view);
        this.mFloatGuideView = this.mRootView.findViewById(R.id.float_guide_view);
        FloatFullWindowAccessiabilityDelegate floatFullWindowAccessiabilityDelegate2 = new FloatFullWindowAccessiabilityDelegate();
        floatFullWindowAccessiabilityDelegate2.onInitializeAccessibilityNodeInfo(this.mFloatGuideView, this.mFloatGuideView.createAccessibilityNodeInfo());
        this.mFloatGuideView.setAccessibilityDelegate(floatFullWindowAccessiabilityDelegate2);
        if (isFoldNarrowOrPhoneLandScreen()) {
            this.mFloatGuideView.setVisibility(8);
        }
        initGuideView();
        this.mFullFloatView = this.mRootView.findViewById(R.id.full_float_view);
        if (!isPadUiMode()) {
            View view2 = this.mFullFloatView;
            view2.setPaddingRelative(view2.getPaddingStart(), this.mTopMargin + UIUtils.dip2px(this.mContext, 12.36f), this.mFullFloatView.getPaddingEnd(), this.mFullFloatView.getPaddingBottom());
        }
        this.mRvTodo = (FwTodoRecyclerView) this.mRootView.findViewById(R.id.rv_todo);
        updateFloatFullView();
        HomeStatusController homeStatusController2 = new HomeStatusController(this.mContext);
        this.mHomeStatusController = homeStatusController2;
        homeStatusController2.setHomeStatusListener(new HomeStatusListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.1
            @Override // com.miui.floatwindow.frame.HomeStatusListener
            public void onHomeKey() {
                if (BaseFloatFullWindow.this.mFloatStatus == 2) {
                    Logger.INSTANCE.d(BaseFloatFullWindow.TAG, "HomeKey back to guide");
                    BaseFloatFullWindow.this.startAutoFullGuideTransitionAnimation(0.0f, true);
                }
            }

            @Override // com.miui.floatwindow.frame.HomeStatusListener
            public void onRecentApps() {
                if (BaseFloatFullWindow.this.mFloatStatus == 2) {
                    Logger.INSTANCE.d(BaseFloatFullWindow.TAG, "onRecentApps back to guide");
                    BaseFloatFullWindow.this.startAutoFullGuideTransitionAnimation(0.0f, true);
                }
            }
        });
        homeStatusController2.startListen();
        PreferenceUtils.setIsFullWindow(this.mContext, false);
        this.mWindowResetAnimator.setInterpolator(this.mPhysicBasedInterpolator);
        this.mWindowResetAnimator.setDuration(300L);
        if (this.mInDockMode) {
            changeGuideLineVisible(false);
            return;
        }
        if (!isFoldNarrowOrPhoneLandScreen()) {
            this.mFloatGuideView.setVisibility(0);
        }
        setEnabled(true);
    }

    public void SetScreenStatus() {
        int realWidth = DisplayUtils.getRealWidth(this.mContext);
        int realHeight = DisplayUtils.getRealHeight(this.mContext);
        Logger.INSTANCE.d(TAG, "onServiceConfigChanged screenWidth " + realWidth + " screenHeight " + realHeight);
        if (realHeight >= realWidth) {
            this.mNewOrientation = 1;
        } else {
            this.mNewOrientation = 2;
        }
    }

    public boolean isPadUiMode() {
        return RomUtils.isPadMode() && !UIUtils.isInNarrowScreen(this.mContext);
    }

    public boolean isFoldNarrowOrPhoneLandScreen() {
        return ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(this.mContext)) || !RomUtils.isPadMode()) && this.mNewOrientation == 2;
    }

    public WindowManager.LayoutParams getFloatWindowParams() {
        return this.mFloatWindowParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mRvTodo.isInDragMode()) {
            this.mRvTodo.getGlobalVisibleRect(this.mRvTodoRect);
            motionEvent.offsetLocation(0.0f, -this.mRvTodoRect.top);
            return this.mRvTodo.dispatchTouchEvent(motionEvent);
        }
        if (this.mInGuideAnim) {
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x006b  */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = this.mFloatStatus;
        if (i == 0) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.xInView = motionEvent.getX();
                this.yInView = motionEvent.getY();
                this.xDownInScreen = motionEvent.getRawX();
                this.yDownInScreen = motionEvent.getRawY();
                this.xMoved = 0.0f;
                this.xMovedBeforeAnim = 0.0f;
                this.xMovedAfterAnim = 0.0f;
                this.mIsDragFullView = false;
                this.yMoved = 0.0f;
                this.isFirstMove = true;
                this.actionDownTime = System.currentTimeMillis();
                this.mGuideMoveIntercepted = false;
                onActiveFloatWindow(false);
            } else if (action == 2 && (this.xInView != motionEvent.getX() || this.yInView != motionEvent.getY())) {
                ImageView imageView = this.mStopExcerptIcon;
                if (imageView != null) {
                    int[] iArr = new int[2];
                    imageView.getLocationOnScreen(iArr);
                    int height = this.mStopExcerptIcon.getHeight();
                    if (motionEvent.getRawX() > iArr[0] + this.mStopExcerptIcon.getWidth() || motionEvent.getRawX() < iArr[0] || motionEvent.getRawY() > iArr[1] + height || motionEvent.getRawY() < iArr[1]) {
                        this.mGuideMoveIntercepted = true;
                    }
                } else {
                    this.mGuideMoveIntercepted = true;
                }
            }
            return this.mGuideMoveIntercepted || super.onInterceptTouchEvent(motionEvent);
        }
        if (i != 2 || !DisplayUtils.isSupportGestureLine(this.mContext) || !DisplayUtils.isEnableGestureLine(this.mContext) || motionEvent.getRawY() <= this.mDisplayHeight - DisplayUtils.getNavigationBarHeightFromProp(this.mContext)) {
            return false;
        }
        this.mIsOutIntercept = true;
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        Logger.INSTANCE.v(TAG, "onTouchEvent: action = " + motionEvent.getAction() + " xInScreen = " + rawX + " yInScreen = " + rawY);
        int action = motionEvent.getAction();
        if (action == 1) {
            int i = this.mFloatStatus;
            if (i == 1) {
                if (this.xMoved >= this.mFloatWindowView.getVisibleViewWidth()) {
                    changeFloatViewStatus(2);
                } else {
                    startAutoFullGuideTransitionAnimation(this.xMoved, false);
                }
            } else if (i == 2) {
                if (this.mIsOutIntercept || System.currentTimeMillis() - this.actionDownTime < 150) {
                    startAutoFullGuideTransitionAnimation(this.xMoved, true);
                }
            } else if (i == 0) {
                onActiveFloatWindow(false);
                autoResetPosition();
            }
            this.xMoved = 0.0f;
            this.yMoved = 0.0f;
            this.actionDownTime = 0L;
            this.mIsOutIntercept = false;
            this.mGuideMoveIntercepted = false;
        } else if (action != 2) {
            if (action == 3) {
                onActiveFloatWindow(false);
                autoResetPosition();
                this.xMoved = 0.0f;
                this.yMoved = 0.0f;
                this.actionDownTime = 0L;
                this.mIsOutIntercept = false;
                this.mGuideMoveIntercepted = false;
                startAutoFullGuideTransitionAnimation(0.0f, false);
            }
        } else if (this.mFloatStatus != 2) {
            this.xMoved = Math.abs(rawX - this.xDownInScreen);
            float fAbs = Math.abs(rawY - this.yDownInScreen);
            this.yMoved = fAbs;
            float f = this.xMoved;
            if (f != 0.0f || fAbs != 0.0f || !this.isFirstMove) {
                if (this.isFirstMove) {
                    this.moveType = (this.mGuideType == 1 || fAbs > 2.0f * f) ? 0 : 1;
                    this.isFirstMove = false;
                }
                int i2 = this.moveType;
                if (i2 == 0) {
                    int iMin = Math.min(Math.max((int) ((rawY - this.yInView) - getOneHandOffset()), this.mTopMargin), this.mEndMargin);
                    this.mFloatWindowParams.y = iMin;
                    this.guideY = iMin;
                    updateViewLayout(this.mFloatWindowParams);
                    onActiveFloatWindow(true);
                } else if (i2 == 1) {
                    if (this.mFloatStatus == 0) {
                        if (f > 40.0f && f <= 350.0f) {
                            float f2 = (f - 40.0f) / 310.0f;
                            this.mFloatWindowParams.x = this.mIntEvaluator.evaluate(f2, (Integer) 0, (Integer) 100).intValue();
                            if (f2 > 0.9d) {
                                this.mGuideLineView.setAlpha(1.0f - ((f2 - 0.9f) * 10.0f));
                            }
                            updateViewLayout(this.mFloatWindowParams);
                        } else if (f > 350.0f) {
                            this.guideX = this.mFloatWindowParams.x;
                            changeFloatViewStatus(1);
                        }
                    } else if (this.mStartFullViewAnim && !this.mIsFullViewAnim) {
                        if (!this.mIsDragFullView) {
                            this.mIsDragFullView = true;
                            this.xMovedAfterAnim = f;
                        }
                        float windowShowWidth = (this.xMovedBeforeAnim - getWindowShowWidth()) + ((int) (f - this.xMovedAfterAnim));
                        if (windowShowWidth > 0.0f) {
                            windowShowWidth = getActualMoveValue(windowShowWidth);
                        }
                        this.mFullFloatView.setTranslationX(windowShowWidth);
                        if (windowShowWidth < 0.0f) {
                            setFullViewBgBlur(Math.max(0.0f, Math.min(1.0f, (getWindowShowWidth() + windowShowWidth) / getWindowShowWidth())));
                        }
                        this.windowManager.updateViewLayout(this, this.mFloatWindowParams);
                    }
                }
            }
        }
        return true;
    }

    private float getOneHandOffset() {
        float translationFraction = 0.0f;
        try {
            ((Integer) UserHandle.class.getMethod("myUserId", new Class[0]).invoke(null, new Object[0])).intValue();
            translationFraction = RomUtils.getOneHandedModeActivated(this.mContext.getContentResolver()) ? RomUtils.getTranslationFraction(this.mContext) : 0.0f;
            return translationFraction * this.mDisplayHeight;
        } catch (Exception e) {
            e.printStackTrace();
            return translationFraction;
        }
    }

    private float getActualMoveValue(float f) {
        float f2 = 800;
        float fMin = Math.min(f / f2, 1.0f);
        float f3 = 13.0f * fMin;
        float f4 = f3 * fMin;
        return ((((fMin * f4) / 75.0f) - (f4 / 25.0f)) + (f3 / 25.0f)) * f2;
    }

    public void onServiceConfigChanged(Configuration configuration) {
        Logger.INSTANCE.d(TAG, "onServiceConfigChanged: in base float");
        if (RomUtils.isPadMode() && this.mOrientation != configuration.orientation) {
            if (this.mFloatStatus == 2) {
                changeFloatViewStatus(0);
            }
            updateGuideY(true);
            this.mFloatWindowParams.y = this.guideY;
            updateViewLayout(this.mFloatWindowParams);
        }
        this.mOrientation = configuration.orientation;
    }

    protected void showGuideViewVisible(boolean z) {
        this.mStubView.setVisibility(0);
        if (z && !isFoldNarrowOrPhoneLandScreen()) {
            this.mFloatGuideView.setVisibility(0);
        } else {
            this.mFloatGuideView.setVisibility(8);
        }
    }

    protected void changeFloatViewStatus(int i) {
        Logger.INSTANCE.d(TAG, "while origin Type " + this.mFloatStatus + " changeToType " + i);
        if (this.mFloatStatus == i) {
            return;
        }
        BaseHybridNoteEditFragment.removeAiFloatingWindow();
        if (i == 0) {
            this.mStartFullViewAnim = false;
            this.mRootContentLayout.setOnClickListener(null);
            markStatForFullFloat(false);
            if (!isPadUiMode() && (this.mIsNightMode || UIUtils.isAboveAndroidS())) {
                this.mRootContentLayout.setBackgroundColor(this.mContext.getColor(R.color.todo_float_lucency));
            }
            if (!this.mInDockMode && this.mGuideType == 0) {
                this.mGuideLineView.setVisibility(0);
            }
            this.mFullFloatView.setVisibility(8);
            this.mFloatGuideView.setTranslationY(0.0f);
            post(new Runnable() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$changeFloatViewStatus$0();
                }
            });
        } else if (i == 1) {
            changeToFullWindowState(null);
        } else if (i == 2) {
            onFullWindowShow();
            this.mFloatWindowView.onFullWindowShow();
            requestRedirect(false);
        }
        this.mFloatStatus = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeFloatViewStatus$0() {
        refreshGuideWindowParams();
        if (!isPadUiMode()) {
            setBlurRatio(0.0f);
            updateViewLayout(this.mFloatWindowParams);
        } else if (this.mRootContentLayout.getBackground() != null) {
            this.mRootContentLayout.getBackground().setAlpha(255);
        }
        if (!isFoldNarrowOrPhoneLandScreen()) {
            autoResetPosition();
        }
        this.mGuideLineView.setBackground(this.mContext.getResources().getDrawable(R.drawable.todo_float_guide_bg));
        PreferenceUtils.setIsFullWindow(getContext(), false);
        GestureBroadcastUtils.sendBroadcast(NoteApp.getInstance(), false);
        this.mFloatGuideView.clearAnimation();
        this.mFloatGuideView.setTranslationX((-getWindowShowWidth()) / 2.0f);
        this.mFloatGuideView.animate().translationX(0.0f).setDuration(300L).setInterpolator(this.mPhysicBasedInterpolator).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseFloatFullWindow.this.mFloatGuideView.setTranslationX(0.0f);
                BaseFloatFullWindow.this.mInGuideAnim = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                BaseFloatFullWindow.this.mFloatGuideView.setTranslationX(0.0f);
                BaseFloatFullWindow.this.mInGuideAnim = false;
            }
        }).start();
        if (this.mGuideType != 1) {
            requestRedirect(true);
        }
    }

    protected boolean isInFullWindow() {
        return this.mFloatStatus == 2;
    }

    private void changeToFullWindowState(final Runnable runnable) {
        GestureBroadcastUtils.sendBroadcast(NoteApp.getInstance(), true);
        if (this.mFloatStatus != 0) {
            return;
        }
        this.mDisplayHeight = DisplayUtils.getRealHeight(this.mContext);
        this.mFloatWindowView.preToFullWindow();
        markStatForFullFloat(true);
        this.mGuideLineView.setBackgroundResource(R.color.todo_float_lucency);
        this.mFloatGuideView.setTranslationY(this.guideY);
        this.guideX = 0;
        post(new Runnable() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$changeToFullWindowState$2(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeToFullWindowState$2(Runnable runnable) {
        showGuideViewVisible(false);
        if (!isPadUiMode() && UIUtils.isAboveAndroidS()) {
            this.mRootContentLayout.setBackgroundColor(this.mContext.getColor(R.color.todo_float_full_bg_v31));
        }
        refreshFullWindowParams();
        updateViewLayout(this.mFloatWindowParams);
        this.mFullFloatView.setVisibility(0);
        if (RomUtils.isPadMode()) {
            if (Utils.isRTL()) {
                this.mFullFloatView.setTranslationX(-DisplayUtils.getRealWidth(this.mContext));
            } else {
                this.mFullFloatView.setTranslationX((-getWindowShowWidth()) - this.mContext.getResources().getDimension(R.dimen.pad_global_window_margin_left));
            }
        } else {
            this.mFullFloatView.setTranslationX(-getWindowShowWidth());
        }
        this.mRootContentLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FloatWindowManagerAgent.changeFullLayoutToGuideService();
            }
        });
        if (!isPadUiMode() && !UIUtils.isInFullWindowGestureMode(this.mContext)) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mFullFloatView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, UIUtils.getNavigationBarHeight(this.mContext));
            this.mFullFloatView.setLayoutParams(layoutParams);
        }
        if (!this.mStartFullViewAnim) {
            this.xMovedBeforeAnim = this.xMoved;
            HapticFeedbackTool.getInstance().performFloatWindowOpen(this.mRootView);
            startFullViewShowAnimation(runnable);
            this.mStartFullViewAnim = true;
        }
        PreferenceUtils.setIsFullWindow(getContext(), true);
    }

    private void startFullViewShowAnimation(final Runnable runnable) {
        int windowShowWidth = this.xMoved < ((float) getWindowShowWidth()) ? (int) this.xMoved : getWindowShowWidth();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, this.xMoved / getWindowShowWidth());
        valueAnimatorOfFloat.setDuration(300L).setInterpolator(this.mPhysicBasedInterpolator);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startFullViewShowAnimation$3(valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
        this.mFullFloatView.animate().setDuration(500L).setInterpolator(this.mPhysicBasedInterpolator).translationX((-getWindowShowWidth()) + windowShowWidth).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                BaseFloatFullWindow.this.mIsFullViewAnim = true;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseFloatFullWindow.this.mIsFullViewAnim = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                BaseFloatFullWindow.this.mIsFullViewAnim = false;
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startFullViewShowAnimation$3(ValueAnimator valueAnimator) {
        setFullViewBgBlur(((Float) valueAnimator.getAnimatedValue()).floatValue());
        updateViewLayout(this.mFloatWindowParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFullViewBgBlur(float f) {
        this.mFloatWindowParams.alpha = f;
        this.mBlurRatio = f;
        if (!isPadUiMode()) {
            setBlurRatio(f);
            if (!UIUtils.isAboveAndroidS() || this.mRootContentLayout.getBackground() == null) {
                return;
            }
            this.mRootContentLayout.getBackground().setAlpha((int) (f * 255.0f));
            return;
        }
        if (this.mRootContentLayout.getBackground() != null) {
            this.mRootContentLayout.getBackground().setAlpha((int) (f * 255.0f));
        }
    }

    private void refreshGuideWindowParams() {
        this.mFloatWindowParams.x = this.guideX;
        if (this.mInDockMode) {
            this.mFloatWindowParams.y = this.guideYInDockMode;
        } else {
            this.mFloatWindowParams.y = this.guideY;
        }
        this.mFloatWindowParams.alpha = 1.0f;
        this.mFloatWindowParams.width = -2;
        this.mFloatWindowParams.height = -2;
        this.mFloatWindowParams.flags = R2.attr.iconTintMode;
        if (isPadUiMode()) {
            this.mFloatWindowParams.gravity = 8388659;
        }
        isPadUiMode();
    }

    private void refreshFullWindowParams() {
        this.mDisplayHeight = DisplayUtils.getRealHeight(this.mContext);
        this.mFloatWindowParams.width = -1;
        if (isPadUiMode()) {
            updateFullViewContentSize();
        }
        this.mFloatWindowParams.height = this.mDisplayHeight;
        this.mFloatWindowParams.x = 0;
        this.mFloatWindowParams.y = 0;
        this.mFloatWindowParams.alpha = 1.0f;
        this.mFloatWindowParams.flags = R2.attr.miuix_behavior_halfExpandedRatio;
        if (isPadUiMode()) {
            this.mFloatWindowParams.flags |= 32;
        }
        if (!isPadUiMode()) {
            this.mFloatWindowParams.flags |= 4;
        }
        if (!isPadUiMode()) {
            this.mFloatWindowParams.softInputMode = 16;
        } else {
            this.mFloatWindowParams.softInputMode = 32;
        }
        this.mFloatWindowParams.layoutInDisplayCutoutMode = 1;
    }

    private void updateFullViewContentSize() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mFullFloatView.getLayoutParams();
        marginLayoutParams.width = getWindowShowWidth();
        marginLayoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_global_window_margin_top);
        marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.pad_global_window_margin_top);
        this.mFullFloatView.setLayoutParams(marginLayoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWindowShowWidth() {
        if (isPadUiMode()) {
            if (RomUtils.isFoldDevice()) {
                return (int) (DisplayUtils.getRealWidth(this.mContext) * 0.54302f);
            }
            if (((getResources() == null || getResources().getConfiguration() == null) ? 0 : getResources().getConfiguration().orientation) == 1) {
                return DisplayUtils.getRealWidth(this.mContext) / 2;
            }
            if (DisplayUtils.getRealWidth(this.mContext) == DisplayUtils.getMultiWidth(this.mContext)) {
                return DisplayUtils.getRealWidth(this.mContext) / 2;
            }
            return DisplayUtils.getRealWidth(this.mContext) / 3;
        }
        return DisplayUtils.getRealWidth(this.mContext);
    }

    public void updateScreenOn(boolean z) {
        if (z) {
            this.mFloatWindowParams.flags |= 128;
        } else {
            this.mFloatWindowParams.flags &= -129;
        }
        updateViewLayout(this.mFloatWindowParams);
    }

    protected void startAutoFullGuideTransitionAnimation(float f, boolean z) {
        if (this.mFloatWindowView.canToGuide()) {
            if (getHandler() != null) {
                getHandler().removeCallbacks(this.mFullGuideTransitionAnimation);
            }
            this.mFullGuideTransitionAnimation.setForceGuide(z);
            this.mFullGuideTransitionAnimation.setMovedBeforeUp(f);
            post(this.mFullGuideTransitionAnimation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SettingsContentObserver extends ContentObserver {
        SettingsContentObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            BaseFloatFullWindow.this.saveGuideY();
            final BaseNoteApp noteApp = NoteApp.getInstance();
            BaseFloatFullWindow baseFloatFullWindow = BaseFloatFullWindow.this;
            baseFloatFullWindow.mIsFullWindowGesture = UIUtils.isInFullWindowGestureMode(baseFloatFullWindow.getContext());
            BaseFloatFullWindow.this.updateGuideY(false);
            final boolean z2 = BaseFloatFullWindow.this.mInDockMode;
            Observable.create(new ObservableOnSubscribe() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$SettingsContentObserver$$ExternalSyntheticLambda0
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    BaseFloatFullWindow.SettingsContentObserver.lambda$onChange$0(noteApp, observableEmitter);
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.SettingsContentObserver.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Boolean bool) {
                    BaseFloatFullWindow.this.mInDockMode = bool.booleanValue();
                    if (BaseFloatFullWindow.this.mFloatStatus == 0) {
                        if (!BaseFloatFullWindow.this.mInDockMode) {
                            BaseFloatFullWindow.this.mFloatWindowParams.y = BaseFloatFullWindow.this.guideY;
                        } else {
                            BaseFloatFullWindow.this.mFloatWindowParams.y = BaseFloatFullWindow.this.guideYInDockMode;
                        }
                    }
                    if (z2 != BaseFloatFullWindow.this.mInDockMode) {
                        BaseFloatFullWindow.this.onDockModeChanged(BaseFloatFullWindow.this.mInDockMode);
                    }
                    BaseFloatFullWindow.this.updateViewLayout(BaseFloatFullWindow.this.mFloatWindowParams);
                }
            });
        }

        static /* synthetic */ void lambda$onChange$0(Context context, ObservableEmitter observableEmitter) throws Exception {
            observableEmitter.onNext(Boolean.valueOf(DockUtils.getDockMode(context)));
            observableEmitter.onComplete();
        }
    }

    private synchronized void autoResetPosition() {
        Logger.INSTANCE.d("Notes:Excerpt", "autoResetPosition");
        if (this.mWindowResetAnimator.isRunning()) {
            this.mWindowResetAnimator.cancel();
        }
        showGuideViewVisible(true);
        this.mWindowResetAnimator.setIntValues(this.mFloatWindowParams.x, 0);
        this.mWindowResetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$autoResetPosition$4(valueAnimator);
            }
        });
        this.mWindowResetAnimator.addListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BaseFloatFullWindow.this.mFloatWindowParams.x = 0;
                BaseFloatFullWindow baseFloatFullWindow = BaseFloatFullWindow.this;
                baseFloatFullWindow.updateViewLayout(baseFloatFullWindow.mFloatWindowParams);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                BaseFloatFullWindow.this.mFloatWindowParams.x = 0;
                BaseFloatFullWindow baseFloatFullWindow = BaseFloatFullWindow.this;
                baseFloatFullWindow.updateViewLayout(baseFloatFullWindow.mFloatWindowParams);
            }
        });
        this.mWindowResetAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$autoResetPosition$4(ValueAnimator valueAnimator) {
        this.mFloatWindowParams.x = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mFloatWindowParams.alpha = 1.0f;
        this.mGuideLineView.setAlpha(1.0f);
        updateViewLayout(this.mFloatWindowParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewLayout(WindowManager.LayoutParams layoutParams) {
        if (isAttachedToWindow()) {
            this.windowManager.updateViewLayout(this, layoutParams);
        } else {
            Logger.INSTANCE.i(TAG, " BaseFloatFullWindow not attach to window:");
        }
    }

    protected void saveGuideY() {
        if (!this.mIsFullWindowGesture) {
            PreferenceUtils.setNavigationbarY(this.mContext, this.guideY);
        } else {
            PreferenceUtils.setFullWindowGestureY(this.mContext, this.guideY);
        }
    }

    public void removeContentOb() {
        if (this.mContentOb != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentOb);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGuideY(boolean z) {
        if (this.mIsFullWindowGesture) {
            if (isPadUiMode() && NoteApp.getInstance().getResources().getConfiguration().orientation == 2) {
                this.mEndMargin = this.mDisplayHeight / 7;
            } else {
                this.mEndMargin = this.mDisplayHeight / 3;
            }
            if (z) {
                int i = this.guideY;
                int i2 = this.mEndMargin;
                if (i > i2) {
                    this.guideY = i2;
                }
            } else {
                this.guideY = PreferenceUtils.getFullWindowGestureY(this.mContext);
            }
        } else {
            this.mEndMargin = this.mDisplayHeight - DisplayUtils.getStatusBarHeight(getContext());
            if (!z) {
                this.guideY = PreferenceUtils.getNavigationbarY(this.mContext);
            }
            int i3 = this.guideY;
            int i4 = this.mEndMargin;
            if (i3 > i4) {
                this.guideY = i4;
            }
        }
        if (this.guideY == -1) {
            if (isPadUiMode() && NoteApp.getInstance().getResources().getConfiguration().orientation == 2) {
                this.guideY = 100;
            } else {
                this.guideY = (int) getResources().getDimension(R.dimen.float_guide_view_margin_top);
            }
        }
    }

    public void stopListenHomeKey() {
        this.mHomeStatusController.stopListen();
    }

    private void initBaseConfig(Context context) {
        this.mContext = context;
        this.mContentOb = new SettingsContentObserver();
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(FORCE_FSG_NAV_BAR), false, this.mContentOb);
        if (this.mIsDockSupport) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(DockUtils.KEY_DOCK_MODE_STATUS), false, this.mContentOb);
        }
        if (this.mIsOldToolboxSupportQuickNote) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(DockUtils.KEY_GAMEBOOSTER), true, this.mContentOb);
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(DockUtils.KEY_VTB_BOOSTER), true, this.mContentOb);
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(DockUtils.KEY_CONVERSATION_MODE), true, this.mContentOb);
        }
        this.mDisplayHeight = DisplayUtils.getRealHeight(this.mContext);
        this.mTopMargin = DisplayUtils.getStatusBarHeight(getContext());
        this.mIsFullWindowGesture = UIUtils.isInFullWindowGestureMode(getContext());
        updateGuideY(false);
        this.mFloatStatus = 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && this.mFloatStatus == 2 && keyEvent.getRepeatCount() == 0) {
            startAutoFullGuideTransitionAnimation(0.0f, true);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    private void setBlurRatio(float f) {
        if (UIUtils.isAboveAndroidS()) {
            this.mFloatWindowParams.setBlurBehindRadius((int) (f * 200.0f));
            return;
        }
        try {
            this.mFloatWindowParams.getClass().getField("blurRatio").set(this.mFloatWindowParams, Float.valueOf(f));
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "error in " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showQuickNoteUI$5() {
        startAutoFullGuideTransitionAnimation(this.mFloatWindowView.getVisibleViewWidth(), false);
    }

    public void showQuickNoteUI(boolean z) {
        if (z) {
            changeToFullWindowState(new Runnable() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showQuickNoteUI$5();
                }
            });
            if (this.mFloatStatus != 2) {
                this.mFloatStatus = 1;
                return;
            }
            return;
        }
        if (this.mFloatStatus != 0) {
            startAutoFullGuideTransitionAnimation(0.0f, true);
        }
    }

    public void onDockModeChanged(boolean z) {
        this.mInDockMode = z;
        if (this.mGuideLineView != null) {
            changeGuideLineVisible(!z);
        }
    }

    public void changeGuideLineVisible(boolean z) {
        if (this.mFloatStatus == 0) {
            if (z) {
                if (this.mInDockMode || this.mGuideType == 1) {
                    return;
                }
                this.mFloatGuideView.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$changeGuideLineVisible$6();
                    }
                }, 50L);
                return;
            }
            this.mGuideLineView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGuideLineVisible$6() {
        this.mGuideLineView.setVisibility(0);
    }

    void changeGuideType(int i) {
        if (this.mGuideType == i) {
            return;
        }
        if (i == 0) {
            this.mExcerptView.setVisibility(8);
            if (!this.mInDockMode) {
                this.mGuideLineView.setVisibility(0);
            }
            this.mStubView.setVisibility(0);
            refreshGuideWindowParams();
        } else if (i == 1) {
            this.mExcerptView.setVisibility(0);
            this.mGuideLineView.setVisibility(8);
            this.mStubView.setVisibility(8);
        }
        requestRedirect(i == 0);
        updateViewLayout(this.mFloatWindowParams);
        this.mExcerptView.requestLayout();
        this.mGuideType = i;
    }

    protected void updateGuideViewPositionYInDockMode() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.float_excerpt_view_height);
        this.guideYInDockMode = ((DockUtils.getDockPanelPositionY() - UIUtils.getStatusBarHeight(this.mContext)) - dimensionPixelSize) - this.mContext.getResources().getDimensionPixelSize(R.dimen.float_guide_view_margin_bottom_for_dock);
    }

    protected void initGuideView() {
        View view = this.mRootView;
        if (view == null) {
            Logger.INSTANCE.e(TAG, "initGuideView with RootView null");
            return;
        }
        this.mGuideType = 0;
        this.mExcerptView = (FrameLayout) view.findViewById(R.id.float_excerpt_view);
        this.mExcerptedLayout = (LinearLayout) this.mRootView.findViewById(R.id.float_excerpted);
        LinearLayout linearLayout = (LinearLayout) this.mRootView.findViewById(R.id.float_excerpt_default);
        this.mExcerptDefault = linearLayout;
        this.mStopExcerptIcon = (ImageView) linearLayout.findViewById(R.id.iv_stop_excerpt);
        this.mExcerptView.setVisibility(8);
        this.mGuideLineView = this.mRootView.findViewById(R.id.float_guide_line_view);
        this.mStubView = this.mRootView.findViewById(R.id.stub);
        this.mGuideLineView.setVisibility(0);
        if (UIUtils.isMiproFontSupported()) {
            DisplayUtils.setMiuiMediumTypeFace((TextView) this.mExcerptDefault.findViewById(R.id.excerpt_tv));
        }
    }

    class FullGuideTransitionAnimation implements Runnable {
        private boolean isForceGuide;
        private float movedBeforeUp;

        public FullGuideTransitionAnimation() {
        }

        public void setForceGuide(boolean z) {
            this.isForceGuide = z;
        }

        public void setMovedBeforeUp(float f) {
            this.movedBeforeUp = f;
        }

        @Override // java.lang.Runnable
        public void run() {
            int windowShowWidth;
            int dimension;
            if (this.isForceGuide || this.movedBeforeUp < 350.0f) {
                if (RomUtils.isPadMode()) {
                    if (Utils.isRTL()) {
                        windowShowWidth = DisplayUtils.getRealWidth(BaseFloatFullWindow.this.mContext);
                    } else {
                        dimension = (-BaseFloatFullWindow.this.getWindowShowWidth()) - ((int) BaseFloatFullWindow.this.mContext.getResources().getDimension(R.dimen.pad_global_window_margin_left));
                    }
                } else {
                    windowShowWidth = BaseFloatFullWindow.this.getWindowShowWidth();
                }
                dimension = -windowShowWidth;
            } else {
                dimension = 0;
            }
            final boolean z = dimension != 0;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(BaseFloatFullWindow.this.mBlurRatio, z ? 0.0f : 1.0f);
            valueAnimatorOfFloat.setDuration(300L);
            valueAnimatorOfFloat.setInterpolator(BaseFloatFullWindow.this.mPhysicBasedInterpolator);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$FullGuideTransitionAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$run$0(valueAnimator);
                }
            });
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(BaseFloatFullWindow.this.mFullFloatView.getTranslationX(), dimension);
            if (z) {
                if (BaseFloatFullWindow.this.mToGuideAnimator != null) {
                    Logger.INSTANCE.i("BaseFw", "startAutoFullGuideTransitionAnimation animator break");
                    return;
                }
                BaseFloatFullWindow.this.mToGuideAnimator = valueAnimatorOfFloat2;
            }
            Logger.INSTANCE.i("BaseFw", "startAutoFullGuideTransitionAnimation animator run");
            valueAnimatorOfFloat2.setDuration(300L);
            valueAnimatorOfFloat2.setInterpolator(BaseFloatFullWindow.this.mPhysicBasedInterpolator);
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow$FullGuideTransitionAnimation$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$run$1(valueAnimator);
                }
            });
            valueAnimatorOfFloat2.addListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.root.BaseFloatFullWindow.FullGuideTransitionAnimation.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (z) {
                        BaseFloatFullWindow.this.mInGuideAnim = true;
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (z) {
                        BaseFloatFullWindow.this.changeFloatViewStatus(0);
                        BaseFloatFullWindow.this.mToGuideAnimator = null;
                    } else {
                        BaseFloatFullWindow.this.changeFloatViewStatus(2);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    BaseFloatFullWindow.this.mInGuideAnim = false;
                }
            });
            if (z) {
                if (BaseFloatFullWindow.this.mAutoFullGuideAnimator != null) {
                    BaseFloatFullWindow.this.mAutoFullGuideAnimator.cancel();
                }
                BaseFloatFullWindow.this.mFullFloatView.animate().cancel();
                BaseFloatFullWindow.this.mFloatWindowView.preChangeToGuide();
                valueAnimatorOfFloat2.setStartDelay(350L);
                valueAnimatorOfFloat2.start();
                valueAnimatorOfFloat.setStartDelay(350L);
                valueAnimatorOfFloat.start();
            } else {
                BaseFloatFullWindow.this.mFullFloatView.animate().cancel();
                valueAnimatorOfFloat2.start();
                valueAnimatorOfFloat.start();
            }
            BaseFloatFullWindow.this.mAutoFullGuideAnimator = valueAnimatorOfFloat2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(ValueAnimator valueAnimator) {
            BaseFloatFullWindow.this.setFullViewBgBlur(((Float) valueAnimator.getAnimatedValue()).floatValue());
            BaseFloatFullWindow baseFloatFullWindow = BaseFloatFullWindow.this;
            baseFloatFullWindow.updateViewLayout(baseFloatFullWindow.mFloatWindowParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$1(ValueAnimator valueAnimator) {
            BaseFloatFullWindow.this.mFullFloatView.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    public void requestRedirect(boolean z) {
        WindowManager.LayoutParams layoutParams = this.mFloatWindowParams;
        if (layoutParams == null || !this.mIsSupportMiuiFlags) {
            return;
        }
        try {
            Field field = layoutParams.getClass().getField("miuiFlags");
            field.set(this.mFloatWindowParams, Integer.valueOf(z ? ((Integer) field.get(this.mFloatWindowParams)).intValue() | 32 : 0));
            this.mFloatWindowParams.getClass().getMethod("setTrustedOverlay", new Class[0]).invoke(this.mFloatWindowParams, new Object[0]);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "requestRedirect failed " + e.getMessage());
            this.mIsSupportMiuiFlags = false;
        }
    }

    private class FloatFullWindowAccessiabilityDelegate extends View.AccessibilityDelegate {
        public FloatFullWindowAccessiabilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setClickable(false);
            accessibilityNodeInfo.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK.getId());
        }
    }

    public boolean isShowCoverView() {
        int i = this.mFloatStatus;
        return (i == 0 && this.mGuideType == 0) || i == 1;
    }
}
